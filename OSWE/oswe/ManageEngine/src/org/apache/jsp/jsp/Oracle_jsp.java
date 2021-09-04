/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.PerformanceBean;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.oracle.bean.OracleBean;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.data.support.DialChartSupport;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.DialChart;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.TimeChart;
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
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
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
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatNumberTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class Oracle_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*   75 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
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
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
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
/*  725 */     java.net.InetAddress add = null;
/*  726 */     if (ip.equals("127.0.0.1")) {
/*  727 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  731 */       add = java.net.InetAddress.getByName(ip);
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
/* 1713 */           String attributeName = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
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
/* 2154 */           String message = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2155 */           String image = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
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
/* 2184 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2190 */   private static Map<String, Long> _jspx_dependants = new HashMap(5);
/* 2191 */   static { _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2192 */     _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2193 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2194 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/* 2195 */     _jspx_dependants.put("/jsp/includes/HostPerformance.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2231 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2235 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2264 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2268 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2269 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2270 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2271 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2272 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2273 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2274 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2275 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2276 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2277 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2278 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2279 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2280 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2281 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2282 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2283 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2284 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2285 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/* 2286 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2287 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2288 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2289 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2290 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.release();
/* 2291 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2292 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2293 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2294 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2295 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2302 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2305 */     JspWriter out = null;
/* 2306 */     Object page = this;
/* 2307 */     JspWriter _jspx_out = null;
/* 2308 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2312 */       response.setContentType("text/html;charset=UTF-8");
/* 2313 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2315 */       _jspx_page_context = pageContext;
/* 2316 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2317 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2318 */       session = pageContext.getSession();
/* 2319 */       out = pageContext.getOut();
/* 2320 */       _jspx_out = out;
/*      */       
/* 2322 */       out.write("<!DOCTYPE html>\n");
/* 2323 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n");
/*      */       
/* 2325 */       request.setAttribute("HelpKey", "Monitors Oracle Details");
/*      */       
/* 2327 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2328 */       com.adventnet.appmanager.bean.TableSpaceGraph oraclegraph = null;
/* 2329 */       oraclegraph = (com.adventnet.appmanager.bean.TableSpaceGraph)_jspx_page_context.getAttribute("oraclegraph", 2);
/* 2330 */       if (oraclegraph == null) {
/* 2331 */         oraclegraph = new com.adventnet.appmanager.bean.TableSpaceGraph();
/* 2332 */         _jspx_page_context.setAttribute("oraclegraph", oraclegraph, 2);
/*      */       }
/* 2334 */       out.write(10);
/* 2335 */       PerformanceBean perfgraph = null;
/* 2336 */       perfgraph = (PerformanceBean)_jspx_page_context.getAttribute("perfgraph", 2);
/* 2337 */       if (perfgraph == null) {
/* 2338 */         perfgraph = new PerformanceBean();
/* 2339 */         _jspx_page_context.setAttribute("perfgraph", perfgraph, 2);
/*      */       }
/* 2341 */       out.write(10);
/* 2342 */       com.adventnet.appmanager.bean.OracleSgaGraph sgagraph = null;
/* 2343 */       sgagraph = (com.adventnet.appmanager.bean.OracleSgaGraph)_jspx_page_context.getAttribute("sgagraph", 2);
/* 2344 */       if (sgagraph == null) {
/* 2345 */         sgagraph = new com.adventnet.appmanager.bean.OracleSgaGraph();
/* 2346 */         _jspx_page_context.setAttribute("sgagraph", sgagraph, 2);
/*      */       }
/* 2348 */       out.write(10);
/* 2349 */       OracleBean databean = null;
/* 2350 */       databean = (OracleBean)_jspx_page_context.getAttribute("databean", 2);
/* 2351 */       if (databean == null) {
/* 2352 */         databean = new OracleBean();
/* 2353 */         _jspx_page_context.setAttribute("databean", databean, 2);
/*      */       }
/* 2355 */       out.write(10);
/* 2356 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2357 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2358 */       if (wlsGraph == null) {
/* 2359 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2360 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2362 */       out.write(10);
/* 2363 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2365 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2366 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2367 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2369 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2371 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2373 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2375 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2376 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2377 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2378 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2381 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2382 */         String available = null;
/* 2383 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2384 */         out.write(10);
/*      */         
/* 2386 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2387 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2388 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2390 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2392 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2394 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2396 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2397 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2398 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2399 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2402 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2403 */           String unavailable = null;
/* 2404 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2405 */           out.write(10);
/*      */           
/* 2407 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2408 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2409 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2411 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2413 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2415 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2417 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2418 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2419 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2420 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2423 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2424 */             String unmanaged = null;
/* 2425 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2426 */             out.write(10);
/*      */             
/* 2428 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2429 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2430 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2432 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2434 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2436 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2438 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2439 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2440 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2441 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2444 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2445 */               String scheduled = null;
/* 2446 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2447 */               out.write(10);
/*      */               
/* 2449 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2450 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2451 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2453 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2455 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2457 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2459 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2460 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2461 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2462 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2465 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2466 */                 String critical = null;
/* 2467 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2468 */                 out.write(10);
/*      */                 
/* 2470 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2471 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2472 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2474 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2476 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2478 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2480 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2481 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2482 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2483 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2486 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2487 */                   String clear = null;
/* 2488 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2489 */                   out.write(10);
/*      */                   
/* 2491 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2492 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2493 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2495 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2497 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2499 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2501 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2502 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2503 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2504 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2507 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2508 */                     String warning = null;
/* 2509 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2510 */                     out.write(10);
/* 2511 */                     out.write(10);
/*      */                     
/* 2513 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2514 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2516 */                     out.write(10);
/* 2517 */                     out.write(10);
/* 2518 */                     out.write(10);
/* 2519 */                     out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/sap.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n\n            ");
/*      */                     
/*      */ 
/*      */                     try
/*      */                     {
/* 2524 */                       String haid = null;
/* 2525 */                       String resourcename = null;
/*      */                       
/* 2527 */                       String error = (String)request.getAttribute("errormessage");
/*      */                       
/*      */ 
/* 2530 */                       if (error != null)
/*      */                       {
/* 2532 */                         System.out.println(error);
/*      */                       }
/* 2534 */                       int buffergets = 0;
/* 2535 */                       int diskreads = 0;
/* 2536 */                       int lockandwaits = 0;
/* 2537 */                       int averageexecutions = 0;
/* 2538 */                       String displayname = null;
/* 2539 */                       String bgcolor = null;
/* 2540 */                       if (request.getParameter("configure") == null)
/*      */                       {
/*      */ 
/* 2543 */                         displayname = request.getParameter("resourcename");
/*      */                       }
/*      */                       else
/*      */                       {
/* 2547 */                         displayname = (String)request.getAttribute("displayname");
/*      */                       }
/* 2549 */                       resourcename = (String)request.getAttribute("name");
/*      */                       
/* 2551 */                       haid = (String)request.getAttribute("haid");
/* 2552 */                       String resourceid = (String)request.getAttribute("resourceid");
/* 2553 */                       com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI api = (com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI)com.adventnet.nms.util.NmsUtil.getAPI("ApplnDataCollectionAPI");
/*      */                       
/* 2555 */                       com.adventnet.nms.appln.oracle.datacollection.server.model.OracleCollectData oracleCollectData = (com.adventnet.nms.appln.oracle.datacollection.server.model.OracleCollectData)api.getCollectData(resourcename, "ORACLE");
/* 2556 */                       String errormessage = oracleCollectData.getErrorMsg();
/* 2557 */                       String bufferget = "select sql_text, buffer_gets, executions, buffer_gets/decode(executions, 0, 1, executions) bg_per_exe from v$sql where executions &gt; 0  and ROWNUM &lt;= 10 order by executions,bg_per_exe desc";
/* 2558 */                       String diskread = "select sql_text, disk_reads, executions , disk_reads/decode(executions, 0, 1, executions) dr_per_exe from v$sql where executions &gt; 0 and ROWNUM &lt;= 10 order by executions,dr_per_exe desc";
/* 2559 */                       String lockwaiter = "select waiting_session,holding_session,lock_type,mode_held,mode_requested,lock_id1,lock_id2 from dba_waiters";
/* 2560 */                       String lockholder = "select sid,serial#,machine,program,lockwait from v$session where sid in (select holding_session from dba_blockers)";
/* 2561 */                       String lockdetail = "select b.object_name,a.session_id,c.serial#,decode(lmode,0,'None',1,'Null',2,'Row-S (SS)',3,'Row-X (SX)',4,'Share (S)',5,'S/Row-X (SSX)',6,'Exclusive (X)',lmode),decode(l.request,0,'Blocker','Waiting'),d.spid,to_char(c.logon_time,'dd mon yyyy hh24 mi ss'),c.last_call_et/60 from v$locked_object a, dba_objects b, v$session c, v$process d, v$lock l where a.object_id = b.object_id and a.session_id = c.sid and c.paddr = d.addr and l.sid = c.sid";
/* 2562 */                       String buffererror = null;
/* 2563 */                       String lockerror = null;
/* 2564 */                       String diskerror = null;
/*      */                       
/* 2566 */                       int errorcode = 0;
/* 2567 */                       if (error != null)
/*      */                       {
/* 2569 */                         if ((error.indexOf("buffer_gets") != -1) && (error.indexOf("disk_reads") != -1))
/*      */                         {
/* 2571 */                           errorcode = 1;
/* 2572 */                           buffererror = " " + bufferget + " " + diskread;
/*      */                         }
/* 2574 */                         else if (error.indexOf("disk_reads") != -1)
/*      */                         {
/* 2576 */                           errorcode = 1;
/* 2577 */                           buffererror = " " + diskread;
/*      */                         }
/* 2579 */                         else if (error.indexOf("buffer_gets") != -1)
/*      */                         {
/* 2581 */                           errorcode = 1;
/* 2582 */                           buffererror = " " + bufferget;
/*      */                         }
/*      */                         
/* 2585 */                         if ((errormessage.indexOf("waiting_session") != -1) && (errormessage.indexOf("lockwait") != -1) && (errormessage.indexOf("a.session_id") != -1))
/*      */                         {
/* 2587 */                           errorcode = 3;
/* 2588 */                           lockerror = " " + lockwaiter + " " + lockholder + " " + lockdetail;
/*      */                         }
/* 2590 */                         else if ((error.indexOf("waiting_session") != -1) && (errormessage.indexOf("lockwait") != -1))
/*      */                         {
/* 2592 */                           errorcode = 3;
/* 2593 */                           lockerror = " " + lockwaiter + " " + lockholder;
/*      */                         }
/* 2595 */                         else if ((error.indexOf("lockwait") != -1) && (errormessage.indexOf("a.session_id") != -1))
/*      */                         {
/* 2597 */                           errorcode = 3;
/* 2598 */                           lockerror = " " + lockholder + " " + lockdetail;
/*      */                         }
/* 2600 */                         else if ((error.indexOf("waiting_session") != -1) && (errormessage.indexOf("a.session_id") != -1))
/*      */                         {
/* 2602 */                           errorcode = 3;
/* 2603 */                           lockerror = " " + lockwaiter + " " + lockdetail;
/*      */                         }
/* 2605 */                         else if (error.indexOf("waiting_session") != -1)
/*      */                         {
/* 2607 */                           errorcode = 3;
/* 2608 */                           lockerror = " " + lockwaiter;
/*      */                         }
/* 2610 */                         else if (error.indexOf("lockwait") != -1)
/*      */                         {
/* 2612 */                           errorcode = 3;
/* 2613 */                           lockerror = " " + lockholder;
/*      */                         }
/* 2615 */                         else if (error.indexOf("a.session_id") != -1)
/*      */                         {
/* 2617 */                           errorcode = 3;
/* 2618 */                           lockerror = " " + lockdetail;
/*      */                         }
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2627 */                       String details = request.getParameter("details");
/* 2628 */                       if (details == null)
/*      */                       {
/* 2630 */                         details = "Availability";
/*      */                       }
/* 2632 */                       String redirect = "/Oracle.do?name=" + resourcename + "&haid=" + haid + "&resourceid=" + resourceid + "&details=" + details + "&resourcename=" + displayname + "";
/* 2633 */                       String encodeurl = java.net.URLEncoder.encode(redirect);
/* 2634 */                       String showdata = (String)request.getAttribute("showdata");
/* 2635 */                       Hashtable disableTable = com.adventnet.appmanager.util.EnterpriseUtil.getDisableTable();
/* 2636 */                       if ((disableTable.get("ORACLE#BUFFERGETS") != null) && (disableTable.get("ORACLE#BUFFERGETS").equals("true")))
/*      */                       {
/* 2638 */                         request.setAttribute("buffergets", "true");
/* 2639 */                         buffergets = 1;
/*      */                       }
/* 2641 */                       if ((disableTable.get("ORACLE#DISKREADS") != null) && (disableTable.get("ORACLE#DISKREADS").equals("true")))
/*      */                       {
/* 2643 */                         request.setAttribute("diskreads", "true");
/* 2644 */                         diskreads = 1;
/*      */                       }
/* 2646 */                       if ((disableTable.get("ORACLE#LOCKANDWAITS") != null) && (disableTable.get("ORACLE#LOCKANDWAITS").equals("true")))
/*      */                       {
/* 2648 */                         request.setAttribute("lockandwaits", "true");
/* 2649 */                         lockandwaits = 1;
/*      */                       }
/* 2651 */                       if ((disableTable.get("ORACLE#AVERAGEEXECUTIONS") != null) && (disableTable.get("ORACLE#AVERAGEEXECUTIONS").equals("true")))
/*      */                       {
/* 2653 */                         averageexecutions = 1;
/*      */                       }
/* 2655 */                       String search = null;
/* 2656 */                       String tab = "1";
/* 2657 */                       request.setAttribute("breadcumps", search);
/* 2658 */                       request.setAttribute("tabtoselect", tab);
/* 2659 */                       request.setAttribute("configured", "true");
/* 2660 */                       databean.setParameter("httprequest", resourcename);
/* 2661 */                       request.setAttribute("hideLeftArea", "true");
/*      */                       
/* 2663 */                       out.write("\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2664 */                       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                         return;
/* 2666 */                       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n\n\n\n");
/* 2667 */                       if (_jspx_meth_c_005fcatch_005f0(_jspx_page_context))
/*      */                         return;
/* 2669 */                       out.write(32);
/*      */                       
/* 2671 */                       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2672 */                       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2673 */                       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                       
/* 2675 */                       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2676 */                       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2677 */                       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                         for (;;) {
/* 2679 */                           out.write(10);
/* 2680 */                           out.write(10);
/* 2681 */                           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2683 */                           out.write(10);
/* 2684 */                           if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2686 */                           out.write(32);
/* 2687 */                           if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2689 */                           out.write(10);
/* 2690 */                           out.write(10);
/* 2691 */                           if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2693 */                           out.write(10);
/*      */                           
/* 2695 */                           PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2696 */                           _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 2697 */                           _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2699 */                           _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */                           
/* 2701 */                           _jspx_th_tiles_005fput_005f4.setType("string");
/* 2702 */                           int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 2703 */                           if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 2704 */                             if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 2705 */                               out = _jspx_page_context.pushBody();
/* 2706 */                               _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 2707 */                               _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2710 */                               out.write("\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2711 */                               if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 2713 */                               out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n\n<div id=\"bufferdiv\"   class=\"messagebox\" style=\"DISPLAY:none; height:70px; width:93%;  position:relative; padding-left:35px;  font-size:11px; font-family:Arial, Helvetica, sans-serif; \"> <img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\" style=\"position:absolute; top:2spx; right:0px; left:5px; width:25px;\">\n");
/*      */                               
/* 2715 */                               if (buffererror != null)
/*      */                               {
/* 2717 */                                 out.println(FormatUtil.getString("am.webclient.oracle.innertabs.queryexception", new String[] { buffererror }));
/*      */                               }
/*      */                               
/*      */ 
/* 2721 */                               out.write("</div>\n<div id=\"diskdiv\"   class=\"messagebox\" style=\"DISPLAY:none; height:70px; width:93%;  position:relative; padding-left:35px;  font-size:11px; font-family:Arial, Helvetica, sans-serif; \"> <img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\" style=\"position:absolute; top:2spx; right:0px; left:5px; width:25px;\">\n");
/*      */                               
/* 2723 */                               if (diskerror != null) {
/* 2724 */                                 out.println(FormatUtil.getString("am.webclient.oracle.innertabs.queryexception", new String[] { diskerror }));
/*      */                               }
/*      */                               
/* 2727 */                               out.write("</div>\n<div id=\"lockdiv\"   class=\"messagebox\" style=\"DISPLAY:none; height:70px; width:93%;  position:relative; padding-left:35px;  font-size:11px; font-family:Arial, Helvetica, sans-serif; \"> <img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\" style=\"position:absolute; top:2spx; right:0px; left:5px; width:25px;\">\n");
/*      */                               
/* 2729 */                               if (lockerror != null)
/*      */                               {
/* 2731 */                                 out.println(FormatUtil.getString("am.webclient.oracle.innertabs.queryexception", new String[] { lockerror }));
/*      */                               }
/*      */                               
/* 2734 */                               out.write("</div>\n<br>\n");
/*      */                               
/* 2736 */                               if (oracleCollectData.getConfigured())
/*      */                               {
/* 2738 */                                 ArrayList attribIDs = new ArrayList();
/* 2739 */                                 ArrayList resIDs = new ArrayList();
/*      */                                 
/* 2741 */                                 resIDs.add(resourceid);
/* 2742 */                                 databean.setmaxcollectiontime(resourcename);
/* 2743 */                                 ArrayList list = new ArrayList();
/*      */                                 
/*      */ 
/* 2746 */                                 if (resourcename != null)
/*      */                                 {
/* 2748 */                                   list = databean.getTableSpaceFreebytes(resourcename);
/*      */                                 }
/* 2750 */                                 request.setAttribute("list", list);
/*      */                                 
/* 2752 */                                 for (int i = 2400; i < 2407; i++)
/*      */                                 {
/* 2754 */                                   attribIDs.add("" + i);
/*      */                                 }
/* 2756 */                                 Properties alert = getStatus(resIDs, attribIDs);
/* 2757 */                                 String healthStatus = alert.getProperty(resourceid + "#" + "2401");
/* 2758 */                                 String avaStatus = alert.getProperty(resourceid + "#" + "2400");
/*      */                                 
/* 2760 */                                 wlsGraph.setParam(resourceid, "AVAILABILITY");
/* 2761 */                                 perfgraph.setresourceid(Integer.parseInt(resourceid));
/* 2762 */                                 perfgraph.setEntity("Connection Time");
/* 2763 */                                 databean.setmaxcollectiontime(resourcename);
/* 2764 */                                 Properties p = databean.getInstanceDetails(resourcename);
/* 2765 */                                 HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/*      */                                 
/* 2767 */                                 out.write("\n <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    ");
/*      */                                 
/* 2769 */                                 Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2770 */                                 String aid = request.getParameter("haid");
/* 2771 */                                 String haName = null;
/* 2772 */                                 if (aid != null)
/*      */                                 {
/* 2774 */                                   haName = (String)ht.get(aid);
/*      */                                 }
/*      */                                 
/*      */ 
/* 2778 */                                 out.write("\n\n    ");
/*      */                                 
/* 2780 */                                 IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2781 */                                 _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2782 */                                 _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 2784 */                                 _jspx_th_c_005fif_005f2.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2785 */                                 int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2786 */                                 if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                   for (;;) {
/* 2788 */                                     out.write("\n    <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2789 */                                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 2790 */                                     out.write("\n      &gt; ");
/* 2791 */                                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2792 */                                     out.write("\n      &gt; <span class=\"bcactive\"> ");
/* 2793 */                                     out.print(displayname);
/* 2794 */                                     out.write(" </span></td>\n\n    ");
/* 2795 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2796 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2800 */                                 if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2801 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                                 }
/*      */                                 
/* 2804 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2805 */                                 out.write(32);
/*      */                                 
/* 2807 */                                 IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2808 */                                 _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2809 */                                 _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 2811 */                                 _jspx_th_c_005fif_005f3.setTest("${!empty param.haid && (!empty invalidhaid)|| (empty param.haid)}");
/* 2812 */                                 int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2813 */                                 if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                   for (;;) {
/* 2815 */                                     out.write("\n    <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2816 */                                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 2817 */                                     out.write("\n      &gt; ");
/* 2818 */                                     out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes("ORACLE-DB-server"));
/* 2819 */                                     out.write(" &gt;\n\n      <span class=\"bcactive\"> ");
/* 2820 */                                     out.print(displayname);
/* 2821 */                                     out.write(" </span></td>\n    ");
/* 2822 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2823 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2827 */                                 if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2828 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                 }
/*      */                                 
/* 2831 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2832 */                                 out.write(" </tr>\n</table>\n<br>\n<div id=\"edit\" style=\"DISPLAY: none\">\n");
/* 2833 */                                 JspRuntimeLibrary.include(request, response, "/jsp/OracleConfig.jsp?reconfigure=true&configured=true", out, false);
/* 2834 */                                 out.write("\n<br>\n</div>\n\n");
/*      */                                 
/* 2836 */                                 resourcename = (String)request.getAttribute("name");
/*      */                                 
/* 2838 */                                 String datatypestr = request.getParameter("Datatype");
/*      */                                 
/* 2840 */                                 int datatype = 1;
/* 2841 */                                 if (datatypestr != null) {
/* 2842 */                                   datatype = Integer.parseInt(datatypestr);
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/* 2847 */                                 String selected = " ";
/* 2848 */                                 if (datatype == 1) {
/* 2849 */                                   selected = "am.webclient.oracleinnertabs.overview";
/*      */                                 }
/* 2851 */                                 if (datatype == 2) {
/* 2852 */                                   selected = "am.webclient.oracleinnertabs.Tablespace";
/*      */                                 }
/* 2854 */                                 if (datatype == 3) {
/* 2855 */                                   selected = "am.webclient.oracleinnertabs.Session";
/*      */                                 }
/* 2857 */                                 if (datatype == 4)
/*      */                                 {
/* 2859 */                                   selected = "am.webclient.oracleinnertabs.Rollback";
/*      */                                 }
/* 2861 */                                 if (datatype == 5)
/*      */                                 {
/* 2863 */                                   selected = "am.webclient.oracleinnertabs.SGA";
/*      */                                 }
/* 2865 */                                 if (datatype == 6)
/*      */                                 {
/* 2867 */                                   selected = "am.webclient.oracleinnertabs.Query";
/*      */                                 }
/* 2869 */                                 if (datatype == 7)
/*      */                                 {
/* 2871 */                                   selected = "am.webclient.oracleinnertabs.Lock";
/*      */                                 }
/*      */                                 
/*      */ 
/* 2875 */                                 out.write(10);
/*      */                                 
/* 2877 */                                 IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2878 */                                 _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2879 */                                 _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 2881 */                                 _jspx_th_c_005fif_005f4.setTest("${showdata=='2'}");
/* 2882 */                                 int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2883 */                                 if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                   for (;;) {
/* 2885 */                                     out.write(10);
/* 2886 */                                     out.write(10);
/*      */                                     
/* 2888 */                                     if ((buffergets == 1) && (diskreads == 1) && (lockandwaits == 1))
/*      */                                     {
/*      */ 
/* 2891 */                                       out.write(10);
/* 2892 */                                       JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.oracleinnertabs.overview,am.webclient.oracleinnertabs.Tablespace,am.webclient.oracleinnertabs.Session,am.webclient.oracleinnertabs.Rollback,am.webclient.oracleinnertabs.SGA,am.webclient.oracleinnertabs.Query,am.webclient.oracleinnertabs.Lock,am.webclient.oracleinnertabs.scheduledJobs,am.webclient.oracleinnertabs.pgaStats,am.webclient.oracleinnertabs.asm", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.oracleinnertabs.overview,am.webclient.oracleinnertabs.Tablespace,am.webclient.oracleinnertabs.Session,am.webclient.oracleinnertabs.Rollback,am.webclient.oracleinnertabs.SGA,am.webclient.oracleinnertabs.Query,am.webclient.oracleinnertabs.Lock,am.webclient.oracleinnertabs.scheduledJobs,am.webclient.oracleinnertabs.pgaStats,am.webclient.oracleinnertabs.asm", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getoverview,gettablespace,getsession,getrollback,getsga,getbuffergets,getlockandwaits,getsScheduledJobs,getProcessDetails,getASMDetails", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selected), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourcename", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourcename), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("errorcode", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(errorcode), request.getCharacterEncoding()), out, true);
/* 2893 */                                       out.write("\n\t\t                         ");
/* 2894 */                                       if (datatype != 1)
/*      */                                       {
/* 2896 */                                         out.write("\n\n\t\t  \t\t                      ");
/* 2897 */                                         if (datatype == 2)
/*      */                                         {
/* 2899 */                                           out.write("\n\t\t  \t\t                       <div id=\"maindiv\">\n\t\t  \t\t                     <script>\n\n\t\t  \t\t                     gettablespace('");
/* 2900 */                                           out.print(resourceid);
/* 2901 */                                           out.write(39);
/* 2902 */                                           out.write(44);
/* 2903 */                                           out.write(39);
/* 2904 */                                           out.print(resourcename);
/* 2905 */                                           out.write("')\n\t\t  \t\t                      </script>\n\t\t  \t\t                      </div>\n\t\t  \t\t                     ");
/*      */                                         }
/* 2907 */                                         else if (datatype == 3)
/*      */                                         {
/* 2909 */                                           out.write("\n\t\t\t\t\t             <div id=\"maindiv\">\n\t\t\t\t\t            <script>\n\n\t\t\t\t\t            getsession('");
/* 2910 */                                           out.print(resourceid);
/* 2911 */                                           out.write(39);
/* 2912 */                                           out.write(44);
/* 2913 */                                           out.write(39);
/* 2914 */                                           out.print(resourcename);
/* 2915 */                                           out.write("');\n\t\t\t\t\t            </script>\n\t\t\t\t\t            </div>\n\n\t\t  \t\t                       ");
/* 2916 */                                         } else if (datatype == 4)
/*      */                                         {
/* 2918 */                                           out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getrollback('");
/* 2919 */                                           out.print(resourceid);
/* 2920 */                                           out.write(39);
/* 2921 */                                           out.write(44);
/* 2922 */                                           out.write(39);
/* 2923 */                                           out.print(resourcename);
/* 2924 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                          ");
/* 2925 */                                         } else if (datatype == 5)
/*      */                                         {
/* 2927 */                                           out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getsga('");
/* 2928 */                                           out.print(resourceid);
/* 2929 */                                           out.write(39);
/* 2930 */                                           out.write(44);
/* 2931 */                                           out.write(39);
/* 2932 */                                           out.print(resourcename);
/* 2933 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/* 2935 */                                         else if (datatype == 6)
/*      */                                         {
/* 2937 */                                           out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\n\t\t  \t\t                        getbuffergets('");
/* 2938 */                                           out.print(resourceid);
/* 2939 */                                           out.write(39);
/* 2940 */                                           out.write(44);
/* 2941 */                                           out.write(39);
/* 2942 */                                           out.print(resourcename);
/* 2943 */                                           out.write(39);
/* 2944 */                                           out.write(44);
/* 2945 */                                           out.write(39);
/* 2946 */                                           out.print(errorcode);
/* 2947 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/* 2949 */                                         else if (datatype == 7)
/*      */                                         {
/* 2951 */                                           out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getlockandwaits('");
/* 2952 */                                           out.print(resourceid);
/* 2953 */                                           out.write(39);
/* 2954 */                                           out.write(44);
/* 2955 */                                           out.write(39);
/* 2956 */                                           out.print(resourcename);
/* 2957 */                                           out.write(39);
/* 2958 */                                           out.write(44);
/* 2959 */                                           out.write(39);
/* 2960 */                                           out.print(errorcode);
/* 2961 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/*      */                                         
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 2969 */                                         out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\n\t\t\t\t\t\t       getoverview();\n\n\t\t  \t\t                      </script>\n\t\t  \t\t                       </div>\n\t\t  \t\t                      ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 2973 */                                       out.write("\n\n\n\n          ");
/*      */ 
/*      */                                     }
/* 2976 */                                     else if ((buffergets == 1) && (diskreads == 1))
/*      */                                     {
/*      */ 
/* 2979 */                                       out.write(10);
/* 2980 */                                       JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.oracleinnertabs.overview,am.webclient.oracleinnertabs.Tablespace,am.webclient.oracleinnertabs.Session,am.webclient.oracleinnertabs.Rollback,am.webclient.oracleinnertabs.SGA,am.webclient.oracleinnertabs.Query,am.webclient.oracleinnertabs.scheduledJobs,am.webclient.oracleinnertabs.pgaStats,am.webclient.oracleinnertabs.asm", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.oracleinnertabs.overview,am.webclient.oracleinnertabs.Tablespace,am.webclient.oracleinnertabs.Session,am.webclient.oracleinnertabs.Rollback,am.webclient.oracleinnertabs.SGA,am.webclient.oracleinnertabs.Query,am.webclient.oracleinnertabs.scheduledJobs,am.webclient.oracleinnertabs.pgaStats,am.webclient.oracleinnertabs.asm", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getoverview,gettablespace,getsession,getrollback,getsga,getbuffergets,getsScheduledJobs,getProcessDetails,getASMDetails", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selected), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourcename", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourcename), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("errorcode", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(errorcode), request.getCharacterEncoding()), out, true);
/* 2981 */                                       out.write("\n                                        ");
/* 2982 */                                       if (datatype != 1)
/*      */                                       {
/* 2984 */                                         out.write("\n\n\t\t  \t\t                      ");
/* 2985 */                                         if (datatype == 2)
/*      */                                         {
/* 2987 */                                           out.write("\n\t\t  \t\t                       <div id=\"maindiv\">\n\t\t  \t\t                     <script>\n\n\t\t  \t\t                     gettablespace('");
/* 2988 */                                           out.print(resourceid);
/* 2989 */                                           out.write(39);
/* 2990 */                                           out.write(44);
/* 2991 */                                           out.write(39);
/* 2992 */                                           out.print(resourcename);
/* 2993 */                                           out.write("')\n\t\t  \t\t                      </script>\n\t\t  \t\t                      </div>\n\t\t  \t\t                     ");
/*      */                                         }
/* 2995 */                                         else if (datatype == 3)
/*      */                                         {
/* 2997 */                                           out.write("\n\t\t\t\t\t             <div id=\"maindiv\">\n\t\t\t\t\t            <script>\n\n\t\t\t\t\t            getsession('");
/* 2998 */                                           out.print(resourceid);
/* 2999 */                                           out.write(39);
/* 3000 */                                           out.write(44);
/* 3001 */                                           out.write(39);
/* 3002 */                                           out.print(resourcename);
/* 3003 */                                           out.write("');\n\t\t\t\t\t            </script>\n\t\t\t\t\t            </div>\n\n\t\t  \t\t                       ");
/* 3004 */                                         } else if (datatype == 4)
/*      */                                         {
/* 3006 */                                           out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getrollback('");
/* 3007 */                                           out.print(resourceid);
/* 3008 */                                           out.write(39);
/* 3009 */                                           out.write(44);
/* 3010 */                                           out.write(39);
/* 3011 */                                           out.print(resourcename);
/* 3012 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                          ");
/* 3013 */                                         } else if (datatype == 5)
/*      */                                         {
/* 3015 */                                           out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getsga('");
/* 3016 */                                           out.print(resourceid);
/* 3017 */                                           out.write(39);
/* 3018 */                                           out.write(44);
/* 3019 */                                           out.write(39);
/* 3020 */                                           out.print(resourcename);
/* 3021 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/* 3023 */                                         else if (datatype == 6)
/*      */                                         {
/* 3025 */                                           out.write("\n\t\t  \t\t                         <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\n\t\t  \t\t                        getbuffergets('");
/* 3026 */                                           out.print(resourceid);
/* 3027 */                                           out.write(39);
/* 3028 */                                           out.write(44);
/* 3029 */                                           out.write(39);
/* 3030 */                                           out.print(resourcename);
/* 3031 */                                           out.write(39);
/* 3032 */                                           out.write(44);
/* 3033 */                                           out.write(39);
/* 3034 */                                           out.print(errorcode);
/* 3035 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */ 
/*      */                                         }
/*      */                                         
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3044 */                                         out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\n\t\t\t\t\t\t       getoverview();\n\n\t\t  \t\t                      </script>\n\t\t  \t\t                       </div>\n\t\t  \t\t                      ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 3048 */                                       out.write("\n\n          ");
/*      */ 
/*      */                                     }
/* 3051 */                                     else if ((buffergets == 1) && (lockandwaits == 1))
/*      */                                     {
/* 3053 */                                       out.write(10);
/* 3054 */                                       JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.oracleinnertabs.overview,am.webclient.oracleinnertabs.Tablespace,am.webclient.oracleinnertabs.Session,am.webclient.oracleinnertabs.Rollback,am.webclient.oracleinnertabs.SGA,am.webclient.oracleinnertabs.Query,am.webclient.oracleinnertabs.Lock,am.webclient.oracleinnertabs.scheduledJobs,am.webclient.oracleinnertabs.pgaStats,am.webclient.oracleinnertabs.asm", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.oracleinnertabs.overview,am.webclient.oracleinnertabs.Tablespace,am.webclient.oracleinnertabs.Session,am.webclient.oracleinnertabs.Rollback,am.webclient.oracleinnertabs.SGA,am.webclient.oracleinnertabs.Query,am.webclient.oracleinnertabs.Lock,am.webclient.oracleinnertabs.scheduledJobs,am.webclient.oracleinnertabs.pgaStats,am.webclient.oracleinnertabs.asm", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getoverview,gettablespace,getsession,getrollback,getsga,getbuffergets,getsScheduledJobs,getProcessDetails,getASMDetails", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selected), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourcename", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourcename), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("errorcode", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(errorcode), request.getCharacterEncoding()), out, true);
/* 3055 */                                       out.write(10);
/* 3056 */                                       out.write(10);
/* 3057 */                                       if (datatype != 1)
/*      */                                       {
/* 3059 */                                         out.write("\n\n\t\t  \t\t                      ");
/* 3060 */                                         if (datatype == 2)
/*      */                                         {
/* 3062 */                                           out.write("\n\t\t  \t\t                       <div id=\"maindiv\">\n\t\t  \t\t                     <script>\n\n\t\t  \t\t                     gettablespace('");
/* 3063 */                                           out.print(resourceid);
/* 3064 */                                           out.write(39);
/* 3065 */                                           out.write(44);
/* 3066 */                                           out.write(39);
/* 3067 */                                           out.print(resourcename);
/* 3068 */                                           out.write("')\n\t\t  \t\t                      </script>\n\t\t  \t\t                      </div>\n\t\t  \t\t                     ");
/*      */                                         }
/* 3070 */                                         else if (datatype == 3)
/*      */                                         {
/* 3072 */                                           out.write("\n\t\t\t\t\t             <div id=\"maindiv\">\n\t\t\t\t\t            <script>\n\n\t\t\t\t\t            getsession('");
/* 3073 */                                           out.print(resourceid);
/* 3074 */                                           out.write(39);
/* 3075 */                                           out.write(44);
/* 3076 */                                           out.write(39);
/* 3077 */                                           out.print(resourcename);
/* 3078 */                                           out.write("');\n\t\t\t\t\t            </script>\n\t\t\t\t\t            </div>\n\n\t\t  \t\t                       ");
/* 3079 */                                         } else if (datatype == 4)
/*      */                                         {
/* 3081 */                                           out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getrollback('");
/* 3082 */                                           out.print(resourceid);
/* 3083 */                                           out.write(39);
/* 3084 */                                           out.write(44);
/* 3085 */                                           out.write(39);
/* 3086 */                                           out.print(resourcename);
/* 3087 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                          ");
/* 3088 */                                         } else if (datatype == 5)
/*      */                                         {
/* 3090 */                                           out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getsga('");
/* 3091 */                                           out.print(resourceid);
/* 3092 */                                           out.write(39);
/* 3093 */                                           out.write(44);
/* 3094 */                                           out.write(39);
/* 3095 */                                           out.print(resourcename);
/* 3096 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/* 3098 */                                         else if (datatype == 6)
/*      */                                         {
/* 3100 */                                           out.write("\n\t\t  \t\t                         <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getbuffergets('");
/* 3101 */                                           out.print(resourceid);
/* 3102 */                                           out.write(39);
/* 3103 */                                           out.write(44);
/* 3104 */                                           out.write(39);
/* 3105 */                                           out.print(resourcename);
/* 3106 */                                           out.write(39);
/* 3107 */                                           out.write(44);
/* 3108 */                                           out.write(39);
/* 3109 */                                           out.print(errorcode);
/* 3110 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/* 3112 */                                         else if (datatype == 7)
/*      */                                         {
/* 3114 */                                           out.write("\n\t\t  \t\t                         <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getlockandwaits('");
/* 3115 */                                           out.print(resourceid);
/* 3116 */                                           out.write(39);
/* 3117 */                                           out.write(44);
/* 3118 */                                           out.write(39);
/* 3119 */                                           out.print(resourcename);
/* 3120 */                                           out.write(39);
/* 3121 */                                           out.write(44);
/* 3122 */                                           out.write(39);
/* 3123 */                                           out.print(errorcode);
/* 3124 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/*      */                                         
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3132 */                                         out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\n\t\t\t\t\t\t       getoverview();\n\n\t\t  \t\t                      </script>\n\t\t  \t\t                       </div>\n\t\t  \t\t                      ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 3136 */                                       out.write("\n          ");
/*      */                                     }
/* 3138 */                                     else if ((diskreads == 1) && (lockandwaits == 1))
/*      */                                     {
/* 3140 */                                       out.write(10);
/* 3141 */                                       JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.oracleinnertabs.overview,am.webclient.oracleinnertabs.Tablespace,am.webclient.oracleinnertabs.Session,am.webclient.oracleinnertabs.Rollback,am.webclient.oracleinnertabs.SGA,am.webclient.oracleinnertabs.Query,am.webclient.oracleinnertabs.Lock,am.webclient.oracleinnertabs.scheduledJobs,am.webclient.oracleinnertabs.pgaStats,am.webclient.oracleinnertabs.asm", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.oracleinnertabs.overview,am.webclient.oracleinnertabs.Tablespace,am.webclient.oracleinnertabs.Session,am.webclient.oracleinnertabs.Rollback,am.webclient.oracleinnertabs.SGA,am.webclient.oracleinnertabs.Query,am.webclient.oracleinnertabs.Lock,am.webclient.oracleinnertabs.scheduledJobs,am.webclient.oracleinnertabs.pgaStats,am.webclient.oracleinnertabs.asm", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getoverview,gettablespace,getsession,getrollback,getsga,getdiskreads,getlockandwaits,getsScheduledJobs,getProcessDetails,getASMDetails", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selected), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourcename", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourcename), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("errorcode", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(errorcode), request.getCharacterEncoding()), out, true);
/* 3142 */                                       out.write(10);
/* 3143 */                                       out.write(10);
/* 3144 */                                       if (datatype != 1)
/*      */                                       {
/* 3146 */                                         out.write("\n\n\t\t  \t\t                      ");
/* 3147 */                                         if (datatype == 2)
/*      */                                         {
/* 3149 */                                           out.write("\n\t\t  \t\t                       <div id=\"maindiv\">\n\t\t  \t\t                     <script>\n\n\t\t  \t\t                     gettablespace('");
/* 3150 */                                           out.print(resourceid);
/* 3151 */                                           out.write(39);
/* 3152 */                                           out.write(44);
/* 3153 */                                           out.write(39);
/* 3154 */                                           out.print(resourcename);
/* 3155 */                                           out.write("')\n\t\t  \t\t                      </script>\n\t\t  \t\t                      </div>\n\t\t  \t\t                     ");
/*      */                                         }
/* 3157 */                                         else if (datatype == 3)
/*      */                                         {
/* 3159 */                                           out.write("\n\t\t\t\t\t             <div id=\"maindiv\">\n\t\t\t\t\t            <script>\n\n\t\t\t\t\t            getsession('");
/* 3160 */                                           out.print(resourceid);
/* 3161 */                                           out.write(39);
/* 3162 */                                           out.write(44);
/* 3163 */                                           out.write(39);
/* 3164 */                                           out.print(resourcename);
/* 3165 */                                           out.write("');\n\t\t\t\t\t            </script>\n\t\t\t\t\t            </div>\n\n\t\t  \t\t                       ");
/* 3166 */                                         } else if (datatype == 4)
/*      */                                         {
/* 3168 */                                           out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getrollback('");
/* 3169 */                                           out.print(resourceid);
/* 3170 */                                           out.write(39);
/* 3171 */                                           out.write(44);
/* 3172 */                                           out.write(39);
/* 3173 */                                           out.print(resourcename);
/* 3174 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                          ");
/* 3175 */                                         } else if (datatype == 5)
/*      */                                         {
/* 3177 */                                           out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getsga('");
/* 3178 */                                           out.print(resourceid);
/* 3179 */                                           out.write(39);
/* 3180 */                                           out.write(44);
/* 3181 */                                           out.write(39);
/* 3182 */                                           out.print(resourcename);
/* 3183 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/* 3185 */                                         else if (datatype == 6)
/*      */                                         {
/* 3187 */                                           out.write("\n\t\t  \t\t                         <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getbuffergets('");
/* 3188 */                                           out.print(resourceid);
/* 3189 */                                           out.write(39);
/* 3190 */                                           out.write(44);
/* 3191 */                                           out.write(39);
/* 3192 */                                           out.print(resourcename);
/* 3193 */                                           out.write(39);
/* 3194 */                                           out.write(44);
/* 3195 */                                           out.write(39);
/* 3196 */                                           out.print(errorcode);
/* 3197 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/* 3199 */                                         else if (datatype == 7)
/*      */                                         {
/* 3201 */                                           out.write("\n\t\t  \t\t                         <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getlockandwaits('");
/* 3202 */                                           out.print(resourceid);
/* 3203 */                                           out.write(39);
/* 3204 */                                           out.write(44);
/* 3205 */                                           out.write(39);
/* 3206 */                                           out.print(resourcename);
/* 3207 */                                           out.write(39);
/* 3208 */                                           out.write(44);
/* 3209 */                                           out.write(39);
/* 3210 */                                           out.print(errorcode);
/* 3211 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/*      */                                         
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3219 */                                         out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\n\t\t\t\t\t\t       getoverview();\n\n\t\t  \t\t                      </script>\n\t\t  \t\t                       </div>\n\t\t  \t\t                      ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 3223 */                                       out.write(10);
/* 3224 */                                       out.write(10);
/*      */                                     }
/* 3226 */                                     else if (buffergets == 1)
/*      */                                     {
/* 3228 */                                       out.write(10);
/* 3229 */                                       JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.oracleinnertabs.overview,am.webclient.oracleinnertabs.Tablespace,am.webclient.oracleinnertabs.Session,am.webclient.oracleinnertabs.Rollback,am.webclient.oracleinnertabs.SGA,am.webclient.oracleinnertabs.Query,am.webclient.oracleinnertabs.scheduledJobs,am.webclient.oracleinnertabs.pgaStats,am.webclient.oracleinnertabs.asm", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.oracleinnertabs.overview,am.webclient.oracleinnertabs.Tablespace,am.webclient.oracleinnertabs.Session,am.webclient.oracleinnertabs.Rollback,am.webclient.oracleinnertabs.SGA,am.webclient.oracleinnertabs.Query,am.webclient.oracleinnertabs.scheduledJobs,am.webclient.oracleinnertabs.pgaStats,am.webclient.oracleinnertabs.asm", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getoverview,gettablespace,getsession,getrollback,getsga,getbuffergets,getsScheduledJobs,getProcessDetails,getASMDetails", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("overview", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourcename", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourcename), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("errorcode", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(errorcode), request.getCharacterEncoding()), out, true);
/* 3230 */                                       out.write(10);
/* 3231 */                                       out.write(10);
/* 3232 */                                       out.write(9);
/* 3233 */                                       if (datatype != 1)
/*      */                                       {
/* 3235 */                                         out.write("\n\n\t\t\t  \t\t                      ");
/* 3236 */                                         if (datatype == 2)
/*      */                                         {
/* 3238 */                                           out.write("\n\t\t\t  \t\t                       <div id=\"maindiv\">\n\t\t\t  \t\t                     <script>\n\n\t\t\t  \t\t                     gettablespace('");
/* 3239 */                                           out.print(resourceid);
/* 3240 */                                           out.write(39);
/* 3241 */                                           out.write(44);
/* 3242 */                                           out.write(39);
/* 3243 */                                           out.print(resourcename);
/* 3244 */                                           out.write("')\n\t\t\t  \t\t                      </script>\n\t\t\t  \t\t                      </div>\n\t\t\t  \t\t                     ");
/*      */                                         }
/* 3246 */                                         else if (datatype == 3)
/*      */                                         {
/* 3248 */                                           out.write("\n\t\t\t\t\t\t             <div id=\"maindiv\">\n\t\t\t\t\t\t            <script>\n\n\t\t\t\t\t\t            getsession('");
/* 3249 */                                           out.print(resourceid);
/* 3250 */                                           out.write(39);
/* 3251 */                                           out.write(44);
/* 3252 */                                           out.write(39);
/* 3253 */                                           out.print(resourcename);
/* 3254 */                                           out.write("');\n\t\t\t\t\t\t            </script>\n\t\t\t\t\t\t            </div>\n\n\t\t\t  \t\t                       ");
/* 3255 */                                         } else if (datatype == 4)
/*      */                                         {
/* 3257 */                                           out.write("\n\t\t\t  \t\t                        <div id=\"maindiv\">\n\t\t\t  \t\t                        <script>\n\t\t\t  \t\t                        getrollback('");
/* 3258 */                                           out.print(resourceid);
/* 3259 */                                           out.write(39);
/* 3260 */                                           out.write(44);
/* 3261 */                                           out.write(39);
/* 3262 */                                           out.print(resourcename);
/* 3263 */                                           out.write("');\n\t\t\t  \t\t                        </script>\n\t\t\t  \t\t                        </div>\n\t\t\t  \t\t                          ");
/* 3264 */                                         } else if (datatype == 5)
/*      */                                         {
/* 3266 */                                           out.write("\n\t\t\t  \t\t                        <div id=\"maindiv\">\n\t\t\t  \t\t                        <script>\n\t\t\t  \t\t                        getsga('");
/* 3267 */                                           out.print(resourceid);
/* 3268 */                                           out.write(39);
/* 3269 */                                           out.write(44);
/* 3270 */                                           out.write(39);
/* 3271 */                                           out.print(resourcename);
/* 3272 */                                           out.write("');\n\t\t\t  \t\t                        </script>\n\t\t\t  \t\t                        </div>\n\t\t\t  \t\t                        ");
/*      */                                         }
/* 3274 */                                         else if (datatype == 6)
/*      */                                         {
/* 3276 */                                           out.write("\n\t\t\t  \t\t                         <div id=\"maindiv\">\n\t\t\t  \t\t                        <script>\n\t\t\t  \t\t                        getbuffergets('");
/* 3277 */                                           out.print(resourceid);
/* 3278 */                                           out.write(39);
/* 3279 */                                           out.write(44);
/* 3280 */                                           out.write(39);
/* 3281 */                                           out.print(resourcename);
/* 3282 */                                           out.write(39);
/* 3283 */                                           out.write(44);
/* 3284 */                                           out.write(39);
/* 3285 */                                           out.print(errorcode);
/* 3286 */                                           out.write("');\n\t\t\t  \t\t                        </script>\n\t\t\t  \t\t                        </div>\n\t\t\t  \t\t                        ");
/*      */                                         }
/* 3288 */                                         else if (datatype == 7)
/*      */                                         {
/* 3290 */                                           out.write("\n\t\t\t  \t\t                         <div id=\"maindiv\">\n\t\t\t  \t\t                        <script>\n\t\t\t  \t\t                        getlockandwaits('");
/* 3291 */                                           out.print(resourceid);
/* 3292 */                                           out.write(39);
/* 3293 */                                           out.write(44);
/* 3294 */                                           out.write(39);
/* 3295 */                                           out.print(resourcename);
/* 3296 */                                           out.write(39);
/* 3297 */                                           out.write(44);
/* 3298 */                                           out.write(39);
/* 3299 */                                           out.print(errorcode);
/* 3300 */                                           out.write("');\n\t\t\t  \t\t                        </script>\n\t\t\t  \t\t                        </div>\n\t\t\t  \t\t                        ");
/*      */                                         }
/*      */                                         
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3308 */                                         out.write("\n\t\t\t  \t\t                        <div id=\"maindiv\">\n\t\t\t  \t\t                        <script>\n\n\t\t\t\t\t\t\t       getoverview();\n\n\t\t\t  \t\t                      </script>\n\t\t\t  \t\t                       </div>\n\t\t\t  \t\t                      ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 3312 */                                       out.write(10);
/*      */                                     }
/* 3314 */                                     else if (diskreads == 1)
/*      */                                     {
/* 3316 */                                       out.write(10);
/* 3317 */                                       JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.oracleinnertabs.overview,am.webclient.oracleinnertabs.Tablespace,am.webclient.oracleinnertabs.Session,am.webclient.oracleinnertabs.Rollback,am.webclient.oracleinnertabs.SGA,am.webclient.oracleinnertabs.Query,am.webclient.oracleinnertabs.scheduledJobs,am.webclient.oracleinnertabs.pgaStats,am.webclient.oracleinnertabs.asm", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.oracleinnertabs.overview,am.webclient.oracleinnertabs.Tablespace,am.webclient.oracleinnertabs.Session,am.webclient.oracleinnertabs.Rollback,am.webclient.oracleinnertabs.SGA,am.webclient.oracleinnertabs.Query,am.webclient.oracleinnertabs.scheduledJobs,am.webclient.oracleinnertabs.pgaStats,am.webclient.oracleinnertabs.asm", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getoverview,gettablespace,getsession,getrollback,getsga,getdiskreads,getsScheduledJobs,getProcessDetails,getASMDetails", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selected), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourcename", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourcename), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("errorcode", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(errorcode), request.getCharacterEncoding()), out, true);
/* 3318 */                                       out.write(10);
/* 3319 */                                       out.write(10);
/* 3320 */                                       if (datatype != 1)
/*      */                                       {
/* 3322 */                                         out.write("\n\n\t\t  \t\t                      ");
/* 3323 */                                         if (datatype == 2)
/*      */                                         {
/* 3325 */                                           out.write("\n\t\t  \t\t                       <div id=\"maindiv\">\n\t\t  \t\t                     <script>\n\n\t\t  \t\t                     gettablespace('");
/* 3326 */                                           out.print(resourceid);
/* 3327 */                                           out.write(39);
/* 3328 */                                           out.write(44);
/* 3329 */                                           out.write(39);
/* 3330 */                                           out.print(resourcename);
/* 3331 */                                           out.write("')\n\t\t  \t\t                      </script>\n\t\t  \t\t                      </div>\n\t\t  \t\t                     ");
/*      */                                         }
/* 3333 */                                         else if (datatype == 3)
/*      */                                         {
/* 3335 */                                           out.write("\n\t\t\t\t\t             <div id=\"maindiv\">\n\t\t\t\t\t            <script>\n\n\t\t\t\t\t            getsession('");
/* 3336 */                                           out.print(resourceid);
/* 3337 */                                           out.write(39);
/* 3338 */                                           out.write(44);
/* 3339 */                                           out.write(39);
/* 3340 */                                           out.print(resourcename);
/* 3341 */                                           out.write("');\n\t\t\t\t\t            </script>\n\t\t\t\t\t            </div>\n\n\t\t  \t\t                       ");
/* 3342 */                                         } else if (datatype == 4)
/*      */                                         {
/* 3344 */                                           out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getrollback('");
/* 3345 */                                           out.print(resourceid);
/* 3346 */                                           out.write(39);
/* 3347 */                                           out.write(44);
/* 3348 */                                           out.write(39);
/* 3349 */                                           out.print(resourcename);
/* 3350 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                          ");
/* 3351 */                                         } else if (datatype == 5)
/*      */                                         {
/* 3353 */                                           out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getsga('");
/* 3354 */                                           out.print(resourceid);
/* 3355 */                                           out.write(39);
/* 3356 */                                           out.write(44);
/* 3357 */                                           out.write(39);
/* 3358 */                                           out.print(resourcename);
/* 3359 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/* 3361 */                                         else if (datatype == 6)
/*      */                                         {
/* 3363 */                                           out.write("\n\t\t  \t\t                         <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getbuffergets('");
/* 3364 */                                           out.print(resourceid);
/* 3365 */                                           out.write(39);
/* 3366 */                                           out.write(44);
/* 3367 */                                           out.write(39);
/* 3368 */                                           out.print(resourcename);
/* 3369 */                                           out.write(39);
/* 3370 */                                           out.write(44);
/* 3371 */                                           out.write(39);
/* 3372 */                                           out.print(errorcode);
/* 3373 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/* 3375 */                                         else if (datatype == 7)
/*      */                                         {
/* 3377 */                                           out.write("\n\t\t  \t\t                         <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getlockandwaits('");
/* 3378 */                                           out.print(resourceid);
/* 3379 */                                           out.write(39);
/* 3380 */                                           out.write(44);
/* 3381 */                                           out.write(39);
/* 3382 */                                           out.print(resourcename);
/* 3383 */                                           out.write(39);
/* 3384 */                                           out.write(44);
/* 3385 */                                           out.write(39);
/* 3386 */                                           out.print(errorcode);
/* 3387 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/*      */                                         
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3395 */                                         out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\n\t\t\t\t\t\t       getoverview();\n\n\t\t  \t\t                      </script>\n\t\t  \t\t                       </div>\n\t\t  \t\t                      ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 3399 */                                       out.write(10);
/*      */                                     }
/* 3401 */                                     else if (lockandwaits == 1)
/*      */                                     {
/* 3403 */                                       out.write(10);
/* 3404 */                                       JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.oracleinnertabs.overview,am.webclient.oracleinnertabs.Tablespace,am.webclient.oracleinnertabs.Session,am.webclient.oracleinnertabs.Rollback,am.webclient.oracleinnertabs.SGA,am.webclient.oracleinnertabs.Lock,am.webclient.oracleinnertabs.scheduledJobs,am.webclient.oracleinnertabs.pgaStats,am.webclient.oracleinnertabs.asm", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.oracleinnertabs.overview,am.webclient.oracleinnertabs.Tablespace,am.webclient.oracleinnertabs.Session,am.webclient.oracleinnertabs.Rollback,am.webclient.oracleinnertabs.SGA,am.webclient.oracleinnertabs.Lock,am.webclient.oracleinnertabs.scheduledJobs,am.webclient.oracleinnertabs.pgaStats,am.webclient.oracleinnertabs.asm", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getoverview,gettablespace,getsession,getrollback,getsga,getlockandwaits,getsScheduledJobs,getProcessDetails,getASMDetails", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selected), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourcename", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourcename), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("errorcode", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(errorcode), request.getCharacterEncoding()), out, true);
/* 3405 */                                       out.write(10);
/* 3406 */                                       out.write(10);
/* 3407 */                                       if (datatype != 1)
/*      */                                       {
/* 3409 */                                         out.write("\n\n\t\t  \t\t                      ");
/* 3410 */                                         if (datatype == 2)
/*      */                                         {
/* 3412 */                                           out.write("\n\t\t  \t\t                       <div id=\"maindiv\">\n\t\t  \t\t                     <script>\n\n\t\t  \t\t                     gettablespace('");
/* 3413 */                                           out.print(resourceid);
/* 3414 */                                           out.write(39);
/* 3415 */                                           out.write(44);
/* 3416 */                                           out.write(39);
/* 3417 */                                           out.print(resourcename);
/* 3418 */                                           out.write("')\n\t\t  \t\t                      </script>\n\t\t  \t\t                      </div>\n\t\t  \t\t                     ");
/*      */                                         }
/* 3420 */                                         else if (datatype == 3)
/*      */                                         {
/* 3422 */                                           out.write("\n\t\t\t\t\t             <div id=\"maindiv\">\n\t\t\t\t\t            <script>\n\n\t\t\t\t\t            getsession('");
/* 3423 */                                           out.print(resourceid);
/* 3424 */                                           out.write(39);
/* 3425 */                                           out.write(44);
/* 3426 */                                           out.write(39);
/* 3427 */                                           out.print(resourcename);
/* 3428 */                                           out.write("');\n\t\t\t\t\t            </script>\n\t\t\t\t\t            </div>\n\n\t\t  \t\t                       ");
/* 3429 */                                         } else if (datatype == 4)
/*      */                                         {
/* 3431 */                                           out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getrollback('");
/* 3432 */                                           out.print(resourceid);
/* 3433 */                                           out.write(39);
/* 3434 */                                           out.write(44);
/* 3435 */                                           out.write(39);
/* 3436 */                                           out.print(resourcename);
/* 3437 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                          ");
/* 3438 */                                         } else if (datatype == 5)
/*      */                                         {
/* 3440 */                                           out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getsga('");
/* 3441 */                                           out.print(resourceid);
/* 3442 */                                           out.write(39);
/* 3443 */                                           out.write(44);
/* 3444 */                                           out.write(39);
/* 3445 */                                           out.print(resourcename);
/* 3446 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/* 3448 */                                         else if (datatype == 6)
/*      */                                         {
/* 3450 */                                           out.write("\n\t\t  \t\t                         <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getbuffergets('");
/* 3451 */                                           out.print(resourceid);
/* 3452 */                                           out.write(39);
/* 3453 */                                           out.write(44);
/* 3454 */                                           out.write(39);
/* 3455 */                                           out.print(resourcename);
/* 3456 */                                           out.write(39);
/* 3457 */                                           out.write(44);
/* 3458 */                                           out.write(39);
/* 3459 */                                           out.print(errorcode);
/* 3460 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/* 3462 */                                         else if (datatype == 7)
/*      */                                         {
/* 3464 */                                           out.write("\n\t\t  \t\t                         <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getlockandwaits('");
/* 3465 */                                           out.print(resourceid);
/* 3466 */                                           out.write(39);
/* 3467 */                                           out.write(44);
/* 3468 */                                           out.write(39);
/* 3469 */                                           out.print(resourcename);
/* 3470 */                                           out.write(39);
/* 3471 */                                           out.write(44);
/* 3472 */                                           out.write(39);
/* 3473 */                                           out.print(errorcode);
/* 3474 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/* 3476 */                                         else if (datatype == 8)
/*      */                                         {
/* 3478 */                                           out.write("\n\n\t\t  \t\t                      <div id=\"maindiv\">\n\n\t\t  \t\t                     <script>\n\t\t\t\t                     getdiskreads('");
/* 3479 */                                           out.print(resourceid);
/* 3480 */                                           out.write(39);
/* 3481 */                                           out.write(44);
/* 3482 */                                           out.write(39);
/* 3483 */                                           out.print(resourcename);
/* 3484 */                                           out.write(39);
/* 3485 */                                           out.write(44);
/* 3486 */                                           out.write(39);
/* 3487 */                                           out.print(errorcode);
/* 3488 */                                           out.write("');\n\t\t  \t\t                     </script>\n\t\t  \t\t                     </div>\n\n\t\t  \t\t                       ");
/*      */                                         }
/*      */                                         
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 3495 */                                         out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\n\t\t\t\t\t\t       getoverview();\n\n\t\t  \t\t                      </script>\n\t\t  \t\t                       </div>\n\t\t  \t\t                      ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 3499 */                                       out.write("\n          ");
/*      */ 
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/*      */ 
/*      */ 
/* 3507 */                                       out.write(10);
/* 3508 */                                       out.write(10);
/* 3509 */                                       JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.oracleinnertabs.overview,am.webclient.oracleinnertabs.Tablespace,am.webclient.oracleinnertabs.Session,am.webclient.oracleinnertabs.Rollback,am.webclient.oracleinnertabs.SGA,am.webclient.oracleinnertabs.scheduledJobs,am.webclient.oracleinnertabs.pgaStats,am.webclient.oracleinnertabs.asm", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.oracleinnertabs.overview,am.webclient.oracleinnertabs.Tablespace,am.webclient.oracleinnertabs.Session,am.webclient.oracleinnertabs.Rollback,am.webclient.oracleinnertabs.SGA,am.webclient.oracleinnertabs.scheduledJobs,am.webclient.oracleinnertabs.pgaStats,am.webclient.oracleinnertabs.asm", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getoverview,gettablespace,getsession,getrollback,getsga,getsScheduledJobs,getProcessDetails,getASMDetails", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selected), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourcename", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourcename), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("errorcode", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(errorcode), request.getCharacterEncoding()), out, true);
/* 3510 */                                       out.write(10);
/* 3511 */                                       out.write(10);
/* 3512 */                                       if (datatype != 1)
/*      */                                       {
/* 3514 */                                         out.write("\n\n\t\t  \t\t                      ");
/* 3515 */                                         if (datatype == 2)
/*      */                                         {
/* 3517 */                                           out.write("\n\t\t  \t\t                       <div id=\"maindiv\">\n\t\t  \t\t                     <script>\n\n\t\t  \t\t                     gettablespace('");
/* 3518 */                                           out.print(resourceid);
/* 3519 */                                           out.write(39);
/* 3520 */                                           out.write(44);
/* 3521 */                                           out.write(39);
/* 3522 */                                           out.print(resourcename);
/* 3523 */                                           out.write("')\n\t\t  \t\t                      </script>\n\t\t  \t\t                      </div>\n\t\t  \t\t                     ");
/*      */                                         }
/* 3525 */                                         else if (datatype == 3)
/*      */                                         {
/* 3527 */                                           out.write("\n\t\t\t\t\t             <div id=\"maindiv\">\n\t\t\t\t\t            <script>\n\n\t\t\t\t\t            getsession('");
/* 3528 */                                           out.print(resourceid);
/* 3529 */                                           out.write(39);
/* 3530 */                                           out.write(44);
/* 3531 */                                           out.write(39);
/* 3532 */                                           out.print(resourcename);
/* 3533 */                                           out.write("');\n\t\t\t\t\t            </script>\n\t\t\t\t\t            </div>\n\n\t\t  \t\t                       ");
/* 3534 */                                         } else if (datatype == 4)
/*      */                                         {
/* 3536 */                                           out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getrollback('");
/* 3537 */                                           out.print(resourceid);
/* 3538 */                                           out.write(39);
/* 3539 */                                           out.write(44);
/* 3540 */                                           out.write(39);
/* 3541 */                                           out.print(resourcename);
/* 3542 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                          ");
/* 3543 */                                         } else if (datatype == 5)
/*      */                                         {
/* 3545 */                                           out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getsga('");
/* 3546 */                                           out.print(resourceid);
/* 3547 */                                           out.write(39);
/* 3548 */                                           out.write(44);
/* 3549 */                                           out.write(39);
/* 3550 */                                           out.print(resourcename);
/* 3551 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/* 3553 */                                         else if (datatype == 6)
/*      */                                         {
/* 3555 */                                           out.write("\n\t\t  \t\t                         <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getbuffergets('");
/* 3556 */                                           out.print(resourceid);
/* 3557 */                                           out.write(39);
/* 3558 */                                           out.write(44);
/* 3559 */                                           out.write(39);
/* 3560 */                                           out.print(resourcename);
/* 3561 */                                           out.write(39);
/* 3562 */                                           out.write(44);
/* 3563 */                                           out.write(39);
/* 3564 */                                           out.print(errorcode);
/* 3565 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                        </div>\n\t\t  \t\t                        ");
/*      */                                         }
/* 3567 */                                         else if (datatype == 7)
/*      */                                         {
/* 3569 */                                           out.write("\n\t\t  \t\t                         <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\t\t  \t\t                        getlockandwaits('");
/* 3570 */                                           out.print(resourceid);
/* 3571 */                                           out.write(39);
/* 3572 */                                           out.write(44);
/* 3573 */                                           out.write(39);
/* 3574 */                                           out.print(resourcename);
/* 3575 */                                           out.write(39);
/* 3576 */                                           out.write(44);
/* 3577 */                                           out.write(39);
/* 3578 */                                           out.print(errorcode);
/* 3579 */                                           out.write("');\n\t\t  \t\t                        </script>\n\t\t  \t\t                         </div>\n\t\t  \t\t                        ");
/*      */                                         }
/*      */                                         
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3587 */                                         out.write("\n\t\t  \t\t                        <div id=\"maindiv\">\n\t\t  \t\t                        <script>\n\n\t\t\t\t\t\t       getoverview();\n\n\t\t  \t\t                      </script>\n\t\t  \t\t                       </div>\n\t\t  \t\t                      ");
/*      */                                       }
/*      */                                       
/*      */ 
/* 3591 */                                       out.write("\n\n          ");
/*      */                                     }
/* 3593 */                                     out.write("\n        ");
/* 3594 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3595 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3599 */                                 if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3600 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                 }
/*      */                                 
/* 3603 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3604 */                                 out.write("\n          <br>\n");
/* 3605 */                                 if (datatype == 1) {
/* 3606 */                                   out.write("\n\n <div id=\"overview\" style=\"DISPLAY:block\">\n ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3611 */                                   out.write("\n <div id=\"overview\" style=\"DISPLAY:none\">");
/*      */                                 }
/* 3613 */                                 out.write("\n <br>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n  <tr>\n    <td valign=\"top\"> <table width=\"96%\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" class=\"lrbtborder\">\n        <tr>\n          <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 3614 */                                 out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 3615 */                                 out.write(" </td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3616 */                                 out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 3617 */                                 out.write(" </td>\n          <td class=\"monitorinfoodd\" title=\"");
/* 3618 */                                 out.print(displayname);
/* 3619 */                                 out.write(34);
/* 3620 */                                 out.write(62);
/* 3621 */                                 out.print(getTrimmedText(displayname, 40));
/* 3622 */                                 out.write("</td>\n        </tr>\n\t\t");
/* 3623 */                                 out.write("<!--$Id$-->\n");
/*      */                                 
/* 3625 */                                 String hostName = "localhost";
/*      */                                 try {
/* 3627 */                                   hostName = java.net.InetAddress.getLocalHost().getHostName();
/*      */                                 } catch (Exception ex) {
/* 3629 */                                   ex.printStackTrace();
/*      */                                 }
/* 3631 */                                 String portNumber = System.getProperty("webserver.port");
/* 3632 */                                 String styleClass = "monitorinfoodd";
/* 3633 */                                 if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 3634 */                                   styleClass = "whitegrayborder-conf-mon";
/*      */                                 }
/*      */                                 
/* 3637 */                                 out.write(10);
/*      */                                 
/* 3639 */                                 PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3640 */                                 _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3641 */                                 _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3643 */                                 _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 3644 */                                 int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3645 */                                 if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 3647 */                                     out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 3648 */                                     out.print(styleClass);
/* 3649 */                                     out.write(34);
/* 3650 */                                     out.write(62);
/* 3651 */                                     out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 3652 */                                     out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 3653 */                                     out.print(styleClass);
/* 3654 */                                     out.write(34);
/* 3655 */                                     out.write(62);
/* 3656 */                                     out.print(hostName);
/* 3657 */                                     out.write(95);
/* 3658 */                                     out.print(portNumber);
/* 3659 */                                     out.write("</td>\n</tr>\n");
/* 3660 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3661 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3665 */                                 if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3666 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 3669 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3670 */                                 out.write(10);
/* 3671 */                                 out.write("\n        <tr>\n          <td class=\"monitorinfoeven\" valign=\"top\">");
/* 3672 */                                 out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3673 */                                 out.write("</td>\n          <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3674 */                                 out.print(resourceid);
/* 3675 */                                 out.write("&attributeid=2401')\">");
/* 3676 */                                 out.print(getSeverityImageForHealth(healthStatus));
/* 3677 */                                 out.write("</a>\n\t\t  ");
/* 3678 */                                 out.print(getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + "2401" + "#" + "MESSAGE"), "2401", alert.getProperty(resourceid + "#" + "2401"), resourceid));
/* 3679 */                                 out.write("\n\t\t  ");
/* 3680 */                                 if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid, "2401") != 0) {
/* 3681 */                                   out.write("\n\t\t  <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 3682 */                                   out.print(resourceid + "_2401");
/* 3683 */                                   out.write("&monitortype=ORACLE-DB-server')\">");
/* 3684 */                                   out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 3685 */                                   out.write("</a></span>\n          ");
/*      */                                 }
/* 3687 */                                 out.write("\n\t\t  </td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3688 */                                 out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 3689 */                                 out.write(" </td>\n          <td class=\"monitorinfoodd\">");
/* 3690 */                                 out.print(FormatUtil.getString("am.webclient.oracle.servertype"));
/* 3691 */                                 out.write("</td>\n        </tr>\n        ");
/*      */                                 
/* 3693 */                                 if (!p.isEmpty())
/*      */                                 {
/*      */ 
/* 3696 */                                   out.write("\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3697 */                                   out.print(FormatUtil.getString("am.webclient.oracle.version"));
/* 3698 */                                   out.write("</td>\n          <td class=\"monitorinfoeven\">");
/* 3699 */                                   out.print(p.getProperty("VERSION"));
/* 3700 */                                   out.write("</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3701 */                                   out.print(FormatUtil.getString("am.webclient.oracle.starttime"));
/* 3702 */                                   out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 3703 */                                   out.print(p.getProperty("STARTUPTIME"));
/* 3704 */                                   out.write("</td>\n        </tr>\n        ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3708 */                                 out.write("\n        ");
/*      */                                 
/* 3710 */                                 EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3711 */                                 _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 3712 */                                 _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3714 */                                 _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 3715 */                                 int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 3716 */                                 if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                                   for (;;) {
/* 3718 */                                     out.write("\n\t\t<tr>\n\t\t  <td class=\"monitorinfoeven\">");
/* 3719 */                                     out.print(FormatUtil.getString("am.webclient.common.port.text"));
/* 3720 */                                     out.write("</td>\n\t\t  <td class=\"monitorinfoeven\">-</td>\n\t\t</tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3721 */                                     out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 3722 */                                     out.write("</td>\n          <td class=\"monitorinfoeven\">-&nbsp;</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3723 */                                     out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 3724 */                                     out.write("</td>\n          <td class=\"monitorinfoodd\">-</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3725 */                                     out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3726 */                                     out.write("</td>\n          <td class=\"monitorinfoeven\">-</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3727 */                                     out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3728 */                                     out.write("</td>\n          <td class=\"monitorinfoodd\">-</td>\n        </tr>\n        ");
/* 3729 */                                     int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 3730 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3734 */                                 if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 3735 */                                   this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                                 }
/*      */                                 
/* 3738 */                                 this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 3739 */                                 out.write(32);
/*      */                                 
/* 3741 */                                 NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3742 */                                 _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3743 */                                 _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3745 */                                 _jspx_th_logic_005fnotEmpty_005f0.setName("systeminfo");
/* 3746 */                                 int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3747 */                                 if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                   for (;;) {
/* 3749 */                                     out.write("\n\t\t<tr>\n\t\t  <td class=\"monitorinfoeven\">");
/* 3750 */                                     out.print(FormatUtil.getString("am.webclient.common.port.text"));
/* 3751 */                                     out.write("</td>\n\t\t  <td class=\"monitorinfoeven\">");
/* 3752 */                                     out.print((String)systeminfo.get("PORTNO"));
/* 3753 */                                     out.write("</td>\n\t\t</tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3754 */                                     out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 3755 */                                     out.write("</td>\n          <td class=\"monitorinfoeven\"><a href=\"showresource.do?resourceid=");
/* 3756 */                                     out.print(systeminfo.get("host_resid"));
/* 3757 */                                     out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 3758 */                                     out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 30));
/* 3759 */                                     out.write("&nbsp;(");
/* 3760 */                                     out.print(systeminfo.get("HOSTIP"));
/* 3761 */                                     out.write(")</a></td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3762 */                                     out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 3763 */                                     out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 3764 */                                     out.print(FormatUtil.getString((String)systeminfo.get("HOSTOS")));
/* 3765 */                                     out.write("</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoodd\">");
/* 3766 */                                     out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 3767 */                                     out.write("</td>\n          <td class=\"monitorinfoodd\">");
/* 3768 */                                     out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 3769 */                                     out.write("</td>\n        </tr>\n        <tr>\n          <td class=\"monitorinfoeven\">");
/* 3770 */                                     out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 3771 */                                     out.write("</td>\n          <td class=\"monitorinfoeven\">");
/* 3772 */                                     out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 3773 */                                     out.write("</td>\n        </tr>\n\t\t");
/* 3774 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3775 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3779 */                                 if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3780 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                                 }
/*      */                                 
/* 3783 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3784 */                                 out.write("\n       ");
/* 3785 */                                 JspRuntimeLibrary.include(request, response, "MyField_trstrip.jsp", out, false);
/* 3786 */                                 out.write("\n\t\t</table>\n\t\t");
/*      */                                 
/* 3788 */                                 PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3789 */                                 _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3790 */                                 _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3792 */                                 _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 3793 */                                 int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3794 */                                 if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                   for (;;) {
/* 3796 */                                     out.write(10);
/* 3797 */                                     out.write(9);
/* 3798 */                                     out.write(9);
/*      */                                     
/* 3800 */                                     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3801 */                                     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3802 */                                     _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fpresent_005f1);
/*      */                                     
/* 3804 */                                     _jspx_th_c_005fif_005f5.setTest("${showdata=='1'}");
/* 3805 */                                     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3806 */                                     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                       for (;;) {
/* 3808 */                                         out.write("\n<div align=\"center\"><a style=cursor:pointer;><table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" onclick=\"javascript:toggleDiv('edit')\">\n\n<tr>\n              <td>&nbsp;</td>\n            </tr>\n            <tr>\n              <td><table width=\"75%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"getmoredatatable\">\n                  <tr>\n                    <td width=\"13%\" background=\"../images/getmoredata_bg.gif\"><img src=\"../images/icon_getmoredata.gif\" width=\"35\" height=\"35\" border=\"0\" vspace=\"2\" hspace=\"5\"></td>\n                    <td width=\"87%\" background=\"../images/getmoredata_bg.gif\">");
/* 3809 */                                         out.print(FormatUtil.getString("am.webclient.configureimage.oracle.text"));
/* 3810 */                                         out.write("</td>\n                  </tr>\n                </table></td>\n            </tr>\n      </table></a></div>\n\t\t");
/* 3811 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3812 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3816 */                                     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3817 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                     }
/*      */                                     
/* 3820 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3821 */                                     out.write(10);
/* 3822 */                                     out.write(9);
/* 3823 */                                     out.write(9);
/* 3824 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3825 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3829 */                                 if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3830 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                 }
/*      */                                 
/* 3833 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3834 */                                 out.write("\n\t\t</td>\n    <td width=\"40%\" height=\"190\" align=\"left\" valign=\"top\" > <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtborder\">\n        <tr>\n          <td colspan =\"4\" class=\"tableheadingbborder\">");
/* 3835 */                                 out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 3836 */                                 out.write("</td>\n        </tr>\n        <tr>\n          <td colspan =\"4\" ><table  width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n              <tr>\n                <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3837 */                                 if (_jspx_meth_c_005fout_005f2(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3839 */                                 out.write("&period=1&resourcename=");
/* 3840 */                                 if (_jspx_meth_c_005fout_005f3(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3842 */                                 out.write("')\">\n                  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 3843 */                                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3844 */                                 out.write("'></a></td>\n                <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3845 */                                 if (_jspx_meth_c_005fout_005f4(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3847 */                                 out.write("&period=2&resourcename=");
/* 3848 */                                 if (_jspx_meth_c_005fout_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                   return;
/* 3850 */                                 out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 3851 */                                 out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3852 */                                 out.write("'></a></td>\n              </tr>\n            </table></td>\n        </tr>\n        <tr>\n          <td colspan =\"4\" class=\"whitegrayborder\" align=\"center\"> ");
/*      */                                 
/* 3854 */                                 AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 3855 */                                 _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 3856 */                                 _jspx_th_awolf_005fpiechart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3858 */                                 _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */                                 
/* 3860 */                                 _jspx_th_awolf_005fpiechart_005f0.setWidth("250");
/*      */                                 
/* 3862 */                                 _jspx_th_awolf_005fpiechart_005f0.setHeight("200");
/*      */                                 
/* 3864 */                                 _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                                 
/* 3866 */                                 _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */                                 
/* 3868 */                                 _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */                                 
/* 3870 */                                 _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 3871 */                                 int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 3872 */                                 if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 3873 */                                   if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3874 */                                     out = _jspx_page_context.pushBody();
/* 3875 */                                     _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 3876 */                                     _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3879 */                                     out.write("\n            ");
/*      */                                     
/* 3881 */                                     Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3882 */                                     _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3883 */                                     _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                                     
/* 3885 */                                     _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3886 */                                     int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3887 */                                     if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3888 */                                       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3889 */                                         out = _jspx_page_context.pushBody();
/* 3890 */                                         _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3891 */                                         _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3894 */                                         out.write(32);
/*      */                                         
/* 3896 */                                         AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3897 */                                         _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3898 */                                         _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                         
/* 3900 */                                         _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                                         
/* 3902 */                                         _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 3903 */                                         int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3904 */                                         if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3905 */                                           this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                                         }
/*      */                                         
/* 3908 */                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3909 */                                         out.write("\n            ");
/*      */                                         
/* 3911 */                                         AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3912 */                                         _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3913 */                                         _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                         
/* 3915 */                                         _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                                         
/* 3917 */                                         _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 3918 */                                         int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3919 */                                         if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3920 */                                           this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                                         }
/*      */                                         
/* 3923 */                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3924 */                                         out.write(32);
/* 3925 */                                         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3926 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3929 */                                       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3930 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3933 */                                     if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3934 */                                       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                                     }
/*      */                                     
/* 3937 */                                     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3938 */                                     out.write(32);
/* 3939 */                                     int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 3940 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3943 */                                   if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3944 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3947 */                                 if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3948 */                                   this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0); return;
/*      */                                 }
/*      */                                 
/* 3951 */                                 this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3952 */                                 out.write("\n          </td>\n        </tr>\n        <tr>\n\t\t          <td width=\"49%\" colspan=\"2\" height=\"29\" class=\"yellowgrayborder\">");
/* 3953 */                                 out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 3954 */                                 out.write("\n\t\t            :\n\t\t          <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3955 */                                 out.print(resourceid);
/* 3956 */                                 out.write("&attributeid=2400')\">");
/* 3957 */                                 out.print(getSeverityImageForAvailability(avaStatus));
/* 3958 */                                 out.write("</a></td>\n\t\t          <td   width=\"51%\" class=\"yellowgrayborder\" align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3959 */                                 out.print(resourceid);
/* 3960 */                                 out.write("&attributeIDs=2400,2401&attributeToSelect=2400&redirectto=");
/* 3961 */                                 out.print(encodeurl);
/* 3962 */                                 out.write("\" class=\"links\">");
/* 3963 */                                 out.print(ALERTCONFIG_TEXT);
/* 3964 */                                 out.write("</a>&nbsp;</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3965 */                                 JspRuntimeLibrary.include(request, response, "MyField_div.jsp", out, false);
/* 3966 */                                 out.write("</td></tr></table>\n<br>\n");
/*      */                                 
/* 3968 */                                 IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3969 */                                 _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3970 */                                 _jspx_th_c_005fif_005f6.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 3972 */                                 _jspx_th_c_005fif_005f6.setTest("${showdata=='1'}");
/* 3973 */                                 int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3974 */                                 if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                   for (;;) {
/* 3976 */                                     out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n        <tr>\n\t\t<td width=\"100%\" height=\"29\" class=\"tableheadingtrans\" >");
/* 3977 */                                     out.print(FormatUtil.getString("am.webclient.common.responsetime.text"));
/* 3978 */                                     out.write(32);
/* 3979 */                                     out.write(45);
/* 3980 */                                     out.write(32);
/* 3981 */                                     out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3982 */                                     out.write("&nbsp;</td>\n\t\t</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n<tr>\n<td width=\"405\" height=\"127\" valign=\"top\">\n<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"70%\">\n<tr>\n<td width=\"96%\" align=\"right\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3983 */                                     if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 3985 */                                     out.write("&attributeid=2402&period=-7',740,550)\">\n<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 3986 */                                     out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3987 */                                     out.write("'></td>\n<td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3988 */                                     if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 3990 */                                     out.write("&attributeid=2402&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 3991 */                                     out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3992 */                                     out.write("'></td>\n</tr>\n<tr>\n<td colspan=\"2\">\n");
/*      */                                     
/* 3994 */                                     TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3995 */                                     _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 3996 */                                     _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                                     
/* 3998 */                                     _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("perfgraph");
/*      */                                     
/* 4000 */                                     _jspx_th_awolf_005ftimechart_005f0.setWidth("300");
/*      */                                     
/* 4002 */                                     _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */                                     
/* 4004 */                                     _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                                     
/* 4006 */                                     _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                     
/* 4008 */                                     _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.websphere.responsetimeinms"));
/* 4009 */                                     int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 4010 */                                     if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 4011 */                                       if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 4012 */                                         out = _jspx_page_context.pushBody();
/* 4013 */                                         _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 4014 */                                         _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 4017 */                                         out.write(10);
/* 4018 */                                         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 4019 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 4022 */                                       if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 4023 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 4026 */                                     if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 4027 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                                     }
/*      */                                     
/* 4030 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 4031 */                                     out.write("\n</tr>\n</table></td>\n<td width=\"562\" valign=\"top\"> <br> <br>\n<table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n<tr>\n<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4032 */                                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 4034 */                                     out.write("</span></td>\n<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4035 */                                     if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 4037 */                                     out.write("</span></td>\n<td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">");
/* 4038 */                                     if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                       return;
/* 4040 */                                     out.write("</span></td>\n</tr>\n<tr>\n<tr>\n<td width=\"56%\" height=\"19\" class=\"whitegrayborder\" >");
/* 4041 */                                     out.print(FormatUtil.getString("am.webclient.oracle.currentresponsetime"));
/* 4042 */                                     out.write(" </td>\n<td width=\"26%\" height=\"19\" class=\"whitegrayborder\">\n\t");
/*      */                                     
/* 4044 */                                     if (perfgraph.getResponseTime(Integer.parseInt(resourceid)) == -1L)
/*      */                                     {
/*      */ 
/* 4047 */                                       out.write("\n\t\t-\n\t\t");
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/*      */ 
/* 4053 */                                       out.write(10);
/* 4054 */                                       out.write(9);
/* 4055 */                                       out.write(9);
/* 4056 */                                       out.print(formatNumber(perfgraph.getResponseTime(Integer.parseInt(resourceid))));
/* 4057 */                                       out.write("\n\t\tms\n\t\t");
/*      */                                     }
/*      */                                     
/*      */ 
/* 4061 */                                     out.write("\n</td>\n");
/*      */                                     
/* 4063 */                                     String status2402 = alert.getProperty(resourceid + "#" + "2402");
/*      */                                     
/* 4065 */                                     out.write("\n<td class=\"whitegrayborder\" width=\"29%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4066 */                                     out.print(resourceid);
/* 4067 */                                     out.write("&attributeid=2402')\">");
/* 4068 */                                     out.print(getSeverityImage(status2402));
/* 4069 */                                     out.write("&nbsp;</a></td>\n</tr>\n<tr >\n<td  colspan=\"4\" height=\"21\" class=\"yellowgrayborder\" align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4070 */                                     out.print(resourceid);
/* 4071 */                                     out.write("&attributeIDs=2402&attributeToSelect=2402&redirectto=");
/* 4072 */                                     out.print(encodeurl);
/* 4073 */                                     out.write("\" class=\"links\">");
/* 4074 */                                     out.print(ALERTCONFIG_TEXT);
/* 4075 */                                     out.write("</a>&nbsp;</td>\n</tr>\n</table></td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n<tr>\n<td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n</tr>\n</table>\n");
/* 4076 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 4077 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4081 */                                 if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 4082 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                 }
/*      */                                 
/* 4085 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4086 */                                 out.write(10);
/* 4087 */                                 out.write(10);
/*      */                                 
/* 4089 */                                 IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4090 */                                 _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 4091 */                                 _jspx_th_c_005fif_005f7.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 4093 */                                 _jspx_th_c_005fif_005f7.setTest("${showdata=='2'}");
/* 4094 */                                 int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 4095 */                                 if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                   for (;;) {
/* 4097 */                                     out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"50%\" class=\"tableheadingbborder\">");
/* 4098 */                                     out.print(FormatUtil.getString("am.webclient.oracle.connectiontimegraph"));
/* 4099 */                                     out.write(32);
/* 4100 */                                     out.write(45);
/* 4101 */                                     out.write(32);
/* 4102 */                                     out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 4103 */                                     out.write("</td>\n    <td width=\"50%\" class=\"tableheadingbborder\">");
/* 4104 */                                     out.print(FormatUtil.getString("am.webclient.oracle.useractivity"));
/* 4105 */                                     out.write(32);
/* 4106 */                                     out.write(45);
/* 4107 */                                     out.write(32);
/* 4108 */                                     out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 4109 */                                     out.write("\n      ");
/*      */                                     
/* 4111 */                                     sgagraph.setresourceName(resourcename);
/* 4112 */                                     sgagraph.setEntity("USERS");
/* 4113 */                                     Properties p1 = databean.getInstanceStatus(resourcename);
/*      */                                     
/* 4115 */                                     out.write("\n    </td>\n  </tr>\n  <tr>\n    <td><table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" class=\"rbborder\">\n        <tr>\n          <td> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n              <tr>\n                <td width=\"96%\" align=\"right\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4116 */                                     if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 4118 */                                     out.write("&attributeid=2402&period=-7',740,550)\">\n                  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 4119 */                                     out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4120 */                                     out.write("'></td>\n                <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4121 */                                     if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 4123 */                                     out.write("&attributeid=2402&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 4124 */                                     out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4125 */                                     out.write("'></td>\n              </tr>\n            </table></td>\n        </tr>\n        <tr>\n          <td width=\"40%\"> ");
/*      */                                     
/* 4127 */                                     TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 4128 */                                     _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 4129 */                                     _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_c_005fif_005f7);
/*      */                                     
/* 4131 */                                     _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("perfgraph");
/*      */                                     
/* 4133 */                                     _jspx_th_awolf_005ftimechart_005f1.setWidth("300");
/*      */                                     
/* 4135 */                                     _jspx_th_awolf_005ftimechart_005f1.setHeight("170");
/*      */                                     
/* 4137 */                                     _jspx_th_awolf_005ftimechart_005f1.setLegend("false");
/*      */                                     
/* 4139 */                                     _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                     
/* 4141 */                                     _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(FormatUtil.getString("am.webclient.oracle.graph.connectiontime"));
/*      */                                     
/* 4143 */                                     _jspx_th_awolf_005ftimechart_005f1.setDateFormat("HH:mm");
/* 4144 */                                     int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 4145 */                                     if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 4146 */                                       if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 4147 */                                         out = _jspx_page_context.pushBody();
/* 4148 */                                         _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 4149 */                                         _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 4152 */                                         out.write("\n            ");
/* 4153 */                                         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 4154 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 4157 */                                       if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 4158 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 4161 */                                     if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 4162 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                                     }
/*      */                                     
/* 4165 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 4166 */                                     out.write(" </td>\n      </table></td>\n    <td class=\"bottomborder\"><table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" >\n        <tr>\n\t\t\t  <td width=\"96%\" align=\"right\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4167 */                                     if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 4169 */                                     out.write("&attributeid=2405&period=-7',740,550)\">\n\t\t\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 4170 */                                     out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4171 */                                     out.write("'></td>\n\t\t\t<td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4172 */                                     if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 4174 */                                     out.write("&attributeid=2405&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title='");
/* 4175 */                                     out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4176 */                                     out.write("'></td>\n        </tr>\n        <tr>\n          <td width=\"40%\"> ");
/*      */                                     
/* 4178 */                                     TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 4179 */                                     _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 4180 */                                     _jspx_th_awolf_005ftimechart_005f2.setParent(_jspx_th_c_005fif_005f7);
/*      */                                     
/* 4182 */                                     _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("sgagraph");
/*      */                                     
/* 4184 */                                     _jspx_th_awolf_005ftimechart_005f2.setWidth("300");
/*      */                                     
/* 4186 */                                     _jspx_th_awolf_005ftimechart_005f2.setHeight("170");
/*      */                                     
/* 4188 */                                     _jspx_th_awolf_005ftimechart_005f2.setLegend("false");
/*      */                                     
/* 4190 */                                     _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                     
/* 4192 */                                     _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(FormatUtil.getString("am.webclient.oracle.graph.numberofusers"));
/* 4193 */                                     int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 4194 */                                     if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 4195 */                                       if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 4196 */                                         out = _jspx_page_context.pushBody();
/* 4197 */                                         _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 4198 */                                         _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 4201 */                                         out.write("\n            ");
/* 4202 */                                         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 4203 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 4206 */                                       if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 4207 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 4210 */                                     if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 4211 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*      */                                     }
/*      */                                     
/* 4214 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 4215 */                                     out.write(" </td>\n      </table></td>\n  </tr>\n  <tr>\n    <td class=\"rborder\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n        <tr>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4216 */                                     if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 4218 */                                     out.write("</span></td>\n          <td width=\"28%\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4219 */                                     if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 4221 */                                     out.write("</span></td>\n          <td width=\"29%\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4222 */                                     if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 4224 */                                     out.write("</span></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 4225 */                                     out.print(FormatUtil.getString("am.webclient.common.connectiontime.text"));
/* 4226 */                                     out.write(" </td>\n          <td width=\"28%\" class=\"whitegrayborder\">\n            ");
/*      */                                     
/* 4228 */                                     if (perfgraph.getResponseTime(Integer.parseInt(resourceid)) == -1L)
/*      */                                     {
/*      */ 
/* 4231 */                                       out.write("\n            -\n            ");
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/*      */ 
/* 4237 */                                       out.write("\n            ");
/* 4238 */                                       out.print(formatNumber(perfgraph.getResponseTime(Integer.parseInt(resourceid))));
/* 4239 */                                       out.write("\n            ms\n            ");
/*      */                                     }
/*      */                                     
/*      */ 
/* 4243 */                                     out.write("\n          </td>\n          ");
/*      */                                     
/* 4245 */                                     String status2402 = alert.getProperty(resourceid + "#" + "2402");
/*      */                                     
/* 4247 */                                     out.write("\n          <td class=\"whitegrayborder\" width=\"29%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4248 */                                     out.print(resourceid);
/* 4249 */                                     out.write("&attributeid=2402')\">");
/* 4250 */                                     out.print(getSeverityImage(status2402));
/* 4251 */                                     out.write("&nbsp;</a></td>\n        </tr>\n        <tr >\n          <td  colspan=\"3\" height=\"21\" class=\"yellowgrayborder\" align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4252 */                                     out.print(resourceid);
/* 4253 */                                     out.write("&attributeIDs=2402&attributeToSelect=2402&redirectto=");
/* 4254 */                                     out.print(encodeurl);
/* 4255 */                                     out.write("\" class=\"links\">");
/* 4256 */                                     out.print(ALERTCONFIG_TEXT);
/* 4257 */                                     out.write("</a>&nbsp;</td>\n        </tr>\n      </table></td>\n    <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n        <tr>\n          <td height=\"20\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4258 */                                     if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 4260 */                                     out.write("</span></td>\n          <td width=\"28%\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4261 */                                     if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 4263 */                                     out.write("</span></td>\n          <td width=\"29%\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4264 */                                     if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 4266 */                                     out.write("</span></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 4267 */                                     out.print(FormatUtil.getString("am.webclient.oracle.numberofusers"));
/* 4268 */                                     out.write(" </td>\n          <td class=\"whitegrayborder\">\n          ");
/*      */                                     
/* 4270 */                                     if (p1.getProperty("AVGUSERS") == null)
/*      */                                     {
/* 4272 */                                       out.write("\n          \t-\n          ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 4276 */                                       out.write("\n          \t");
/* 4277 */                                       out.print(formatNumber(p1.getProperty("AVGUSERS")));
/* 4278 */                                       out.write("\n          ");
/*      */                                     }
/* 4280 */                                     out.write("\n          </td>\n          ");
/*      */                                     
/* 4282 */                                     String status2405 = alert.getProperty(resourceid + "#" + "2405");
/*      */                                     
/* 4284 */                                     out.write("\n          <td class=\"whitegrayborder\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4285 */                                     out.print(resourceid);
/* 4286 */                                     out.write("&attributeid=2405')\">");
/* 4287 */                                     out.print(getSeverityImage(status2405));
/* 4288 */                                     out.write("&nbsp;</a></td>\n        </tr>\n        <tr >\n          <td  colspan=\"3\" height=\"21\" class=\"yellowgrayborder\" align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4289 */                                     out.print(resourceid);
/* 4290 */                                     out.write("&attributeIDs=2405&attributeToSelect=2405&redirectto=");
/* 4291 */                                     out.print(encodeurl);
/* 4292 */                                     out.write("\" class=\"links\">");
/* 4293 */                                     out.print(ALERTCONFIG_TEXT);
/* 4294 */                                     out.write("</a>&nbsp;</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td height=\"18\">&nbsp;</td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n    <td width=\"72%\" height=\"29\" class=\"tableheading\" >");
/* 4295 */                                     out.print(FormatUtil.getString("am.webclient.oracle.tablespace.leastfreebytes"));
/* 4296 */                                     out.write("\n    </td>\n    <td width=\"28%\" height=\"29\" class=\"tableheading\" align=right><a href=\"/showresource.do?resourceid=");
/* 4297 */                                     out.print(resourceid);
/* 4298 */                                     out.write("&method=showResourceForResourceID&Datatype=2\" class=\"bodytextboldwhiteun\">");
/* 4299 */                                     out.print(FormatUtil.getString("am.webclient.oracle.showalltables"));
/* 4300 */                                     out.write("</a>&nbsp;</td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\" >\n  <tr>\n <td>\n         <tr height=\"22\">\n           <td class=\"columnheading\"  >");
/* 4301 */                                     out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 4302 */                                     out.write("</td>\n\n           <td class=\"columnheading\" align=\"left\">");
/* 4303 */                                     out.print(FormatUtil.getString("am.webclient.oracle.freebytes"));
/* 4304 */                                     out.write(32);
/* 4305 */                                     out.write(40);
/* 4306 */                                     out.print(FormatUtil.getString("MB"));
/* 4307 */                                     out.write(")</td>\n           <td class=\"columnheading\" align=\"left\">");
/* 4308 */                                     out.print(FormatUtil.getString("am.webclient.oracle.percentagefreebytes"));
/* 4309 */                                     out.write("</td>\n\n\n  </tr>\n\n </td>\n </tr>\n    ");
/*      */                                     
/* 4311 */                                     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4312 */                                     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 4313 */                                     _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_c_005fif_005f7);
/* 4314 */                                     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 4315 */                                     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                                       for (;;) {
/* 4317 */                                         out.write("\n      \t");
/*      */                                         
/* 4319 */                                         WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4320 */                                         _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 4321 */                                         _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                         
/* 4323 */                                         _jspx_th_c_005fwhen_005f0.setTest("${!empty list}");
/* 4324 */                                         int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 4325 */                                         if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                           for (;;) {
/* 4327 */                                             out.write("\n    \t");
/*      */                                             
/* 4329 */                                             ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4330 */                                             _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 4331 */                                             _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                             
/* 4333 */                                             _jspx_th_c_005fforEach_005f0.setVar("props");
/*      */                                             
/* 4335 */                                             _jspx_th_c_005fforEach_005f0.setItems("${list}");
/*      */                                             
/* 4337 */                                             _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 4338 */                                             int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                             try {
/* 4340 */                                               int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 4341 */                                               if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                                 for (;;) {
/* 4343 */                                                   out.write("\n    \t");
/*      */                                                   
/* 4345 */                                                   IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4346 */                                                   _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 4347 */                                                   _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                                   
/* 4349 */                                                   _jspx_th_c_005fif_005f8.setTest("${status.count %2 == 1}");
/* 4350 */                                                   int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 4351 */                                                   if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                                     for (;;) {
/* 4353 */                                                       out.write("\n    \t");
/* 4354 */                                                       bgcolor = "whitegrayborder";
/* 4355 */                                                       out.write("\n     \t");
/* 4356 */                                                       int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 4357 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/*      */                                                   }
/* 4361 */                                                   if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 4362 */                                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4434 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4435 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4365 */                                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4366 */                                                   out.write("\n     \t");
/*      */                                                   
/* 4368 */                                                   IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4369 */                                                   _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 4370 */                                                   _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                                   
/* 4372 */                                                   _jspx_th_c_005fif_005f9.setTest("${status.count %2 == 0}");
/* 4373 */                                                   int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 4374 */                                                   if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                                     for (;;) {
/* 4376 */                                                       out.write("\n     \t");
/* 4377 */                                                       bgcolor = "yellowgrayborder";
/* 4378 */                                                       out.write("\n     \t");
/* 4379 */                                                       int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 4380 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/*      */                                                   }
/* 4384 */                                                   if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 4385 */                                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
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
/* 4434 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4435 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4388 */                                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4389 */                                                   out.write("\n     \t");
/* 4390 */                                                   if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
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
/* 4434 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4435 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4392 */                                                   out.write("\n     \t");
/* 4393 */                                                   if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4434 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4435 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4395 */                                                   out.write("\n     \t");
/*      */                                                   
/* 4397 */                                                   attribIDs.add("2417");
/* 4398 */                                                   String tablespace_resid = pageContext.getAttribute("tablespaceresourceid").toString();
/* 4399 */                                                   String free_bytes = pageContext.getAttribute("free_bytes").toString();
/* 4400 */                                                   resIDs.add(tablespace_resid);
/* 4401 */                                                   alert = com.adventnet.appmanager.fault.FaultUtil.getStatus(resIDs, attribIDs);
/*      */                                                   
/* 4403 */                                                   out.write("\n\n \t  <tr>\n          <td align=\"left\" class=\"tableheadingbborder-oracle-normal\" width=\"33%\"  class=\"");
/* 4404 */                                                   out.print(bgcolor);
/* 4405 */                                                   out.write("br\">");
/* 4406 */                                                   if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
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
/* 4434 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4435 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4408 */                                                   out.write("</td>\n\n          <td align=\"left\" class=\"tableheadingbborder-oracle-normal\" width=\"33%\"   class=\"");
/* 4409 */                                                   out.print(bgcolor);
/* 4410 */                                                   out.write("br\" title='");
/* 4411 */                                                   if (_jspx_meth_fmt_005fformatNumber_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
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
/* 4434 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4435 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4413 */                                                   out.write("&nbsp;&nbsp;bytes'> &nbsp;&nbsp;&nbsp;&nbsp; ");
/* 4414 */                                                   out.print(FormatUtil.formatBytesToMB(free_bytes));
/* 4415 */                                                   out.write("</td>\n          <td align=\"left\" class=\"tableheadingbborder-oracle-normal\" width=\"33%\"  class=\"");
/* 4416 */                                                   out.print(bgcolor);
/* 4417 */                                                   out.write("br\"> &nbsp;&nbsp;&nbsp;&nbsp;");
/* 4418 */                                                   if (_jspx_meth_fmt_005fformatNumber_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
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
/* 4434 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4435 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4420 */                                                   out.write("</td>\n\n          </tr>\n          ");
/* 4421 */                                                   int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 4422 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 4426 */                                               if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4434 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 4435 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/*      */                                             }
/*      */                                             catch (Throwable _jspx_exception)
/*      */                                             {
/*      */                                               for (;;)
/*      */                                               {
/* 4430 */                                                 int tmp16126_16125 = 0; int[] tmp16126_16123 = _jspx_push_body_count_c_005fforEach_005f0; int tmp16128_16127 = tmp16126_16123[tmp16126_16125];tmp16126_16123[tmp16126_16125] = (tmp16128_16127 - 1); if (tmp16128_16127 <= 0) break;
/* 4431 */                                                 out = _jspx_page_context.popBody(); }
/* 4432 */                                               _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                             } finally {
/* 4434 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 4435 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                             }
/* 4437 */                                             out.write("\n\t  \t  ");
/* 4438 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 4439 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 4443 */                                         if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 4444 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                                         }
/*      */                                         
/* 4447 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 4448 */                                         out.write("\n\t  \t  ");
/*      */                                         
/* 4450 */                                         OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4451 */                                         _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 4452 */                                         _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 4453 */                                         int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 4454 */                                         if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                           for (;;) {
/* 4456 */                                             out.write("\n\t          <tr>\n\t            <td colspan=\"10\" class=\"bodytextbold\" align=\"center\">");
/* 4457 */                                             out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 4458 */                                             out.write("</td>\n\t          </tr>\n\t  \t  ");
/* 4459 */                                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 4460 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 4464 */                                         if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 4465 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                         }
/*      */                                         
/* 4468 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 4469 */                                         out.write("\n\t  ");
/* 4470 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 4471 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4475 */                                     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 4476 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                                     }
/*      */                                     
/* 4479 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4480 */                                     out.write("\n\n  </table>\n  <br>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>\n  <td valign=\"top\"> <table width=\"95%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n      <tr>\n        <td width=\"72%\" height=\"26\" class=\"tableheading\">");
/* 4481 */                                     out.print(FormatUtil.getString("am.webclient.oracle.databasedetails"));
/* 4482 */                                     out.write(" </td>\n      </tr>\n    </table>\n    <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\">\n      <tr>\n        <td> <table  border=0 cellspacing=0 cellpadding=1 align=center valign=top  WIDTH=\"100%\">\n            <tr height=\"22\">\n              <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4483 */                                     if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 4485 */                                     out.write("</span></td>\n              <td width=\"50%\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4486 */                                     if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 4488 */                                     out.write("</span></td>\n            </tr>\n            ");
/*      */                                     
/* 4490 */                                     if (!p.isEmpty())
/*      */                                     {
/*      */ 
/* 4493 */                                       out.write("\n            <tr>\n              <td align=\"left\" class=\"whitegrayborder\">");
/* 4494 */                                       out.print(FormatUtil.getString("am.webclient.oracle.databasecreatedtime"));
/* 4495 */                                       out.write(" </td>\n              <td align=\"left\" class=\"whitegrayborder\">");
/* 4496 */                                       out.print(p.getProperty("CREATED"));
/* 4497 */                                       out.write("</td>\n            </tr>\n            <tr>\n              <td align=\"left\" class=\"yellowgrayborder\">");
/* 4498 */                                       out.print(FormatUtil.getString("am.webclient.oracle.openmode"));
/* 4499 */                                       out.write("</td>\n              <td align=\"left\" class=\"yellowgrayborder\">");
/* 4500 */                                       out.print(p.getProperty("OPENMODE"));
/* 4501 */                                       out.write("</td>\n            </tr>\n            <tr>\n              <td align=\"left\" class=\"whitegrayborder\">");
/* 4502 */                                       out.print(FormatUtil.getString("am.webclient.oracle.logmode"));
/* 4503 */                                       out.write(" </td>\n              <td align=\"left\" class=\"whitegrayborder\">");
/* 4504 */                                       out.print(p.getProperty("LOGMODE"));
/* 4505 */                                       out.write("</td>\n            </tr>\n            ");
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/*      */ 
/* 4511 */                                       out.write("\n            <tr>\n              <td align=\"left\" class=\"whitegrayborder\">");
/* 4512 */                                       out.print(FormatUtil.getString("am.webclient.oracle.version"));
/* 4513 */                                       out.write(" </td>\n              <td align=\"left\" class=\"whitegrayborder\">-</td>\n            </tr>\n            <tr>\n              <td align=\"left\" class=\"yellowgrayborder\">");
/* 4514 */                                       out.print(FormatUtil.getString("am.webclient.oracle.starttime"));
/* 4515 */                                       out.write("</td>\n              <td align=\"left\" class=\"yellowgrayborder\">-</td>\n            </tr>\n            <tr>\n              <td align=\"left\" class=\"whitegrayborder\">");
/* 4516 */                                       out.print(FormatUtil.getString("am.webclient.oracle.databasecreatedtime"));
/* 4517 */                                       out.write(" </td>\n              <td align=\"left\" class=\"whitegrayborder\">-</td>\n            </tr>\n            <tr>\n              <td align=\"left\" class=\"yellowgrayborder\">");
/* 4518 */                                       out.print(FormatUtil.getString("am.webclient.oracle.openmode"));
/* 4519 */                                       out.write("</td>\n              <td align=\"left\" class=\"yellowgrayborder\">-</td>\n            </tr>\n            <tr>\n              <td align=\"left\" class=\"whitegrayborder\">");
/* 4520 */                                       out.print(FormatUtil.getString("am.webclient.oracle.logmode"));
/* 4521 */                                       out.write(" </td>\n              <td align=\"left\" class=\"whitegrayborder\">-</td>\n            </tr>\n            ");
/*      */                                     }
/*      */                                     
/*      */ 
/* 4525 */                                     out.write("\n          </table></td>\n      </tr>\n    </table></td><td valign=\"top\">\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n    <tr>\n      <td width=\"59%\" height=\"26\" class=\"tableheading\">");
/* 4526 */                                     out.print(FormatUtil.getString("am.webclient.oracle.databasestatus"));
/* 4527 */                                     out.write("\n  </table>\n  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\" valign=\"top\"><tr><td>\n    <table align=center border=0 cellspacing=0 cellpadding=1 valign=top  WIDTH=\"100%\">\n      <tr height=\"22\">\n        <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4528 */                                     if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 4530 */                                     out.write("</span></td>\n        <td width=\"28%\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4531 */                                     if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 4533 */                                     out.write("</span></td>\n        <td width=\"29%\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4534 */                                     if (_jspx_meth_fmt_005fmessage_005f13(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                       return;
/* 4536 */                                     out.write("</span></td>\n      </tr>\n      ");
/*      */                                     
/* 4538 */                                     if (!p1.isEmpty())
/*      */                                     {
/*      */ 
/* 4541 */                                       out.write("\n      <tr>\n        <td align=\"left\" class=\"whitegrayborder\">");
/* 4542 */                                       out.print(FormatUtil.getString("am.webclient.oracle.databasesize"));
/* 4543 */                                       out.write(" </td>\n        <td align=\"left\" class=\"whitegrayborder\" title=\"");
/* 4544 */                                       out.print(formatNumber(p1.getProperty("DBSIZE")));
/* 4545 */                                       out.write(" bytes\"> ");
/* 4546 */                                       out.print(formatBytesToMB(p1.getProperty("DBSIZE")));
/* 4547 */                                       out.write("\n         </td>\n        ");
/*      */                                       
/* 4549 */                                       String status2403 = alert.getProperty(resourceid + "#" + "2403");
/*      */                                       
/* 4551 */                                       out.write("\n        <td class=\"whitegrayborder\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4552 */                                       out.print(resourceid);
/* 4553 */                                       out.write("&attributeid=2403')\">");
/* 4554 */                                       out.print(getSeverityImage(status2403));
/* 4555 */                                       out.write("&nbsp;</td>\n      </tr>\n      ");
/*      */                                       
/* 4557 */                                       if (averageexecutions == 1)
/*      */                                       {
/* 4559 */                                         out.write("\n      <tr>\n        <td align=\"left\" class=\"yellowgrayborder\">");
/* 4560 */                                         out.print(FormatUtil.getString("am.webclient.oracle.averageexecutions"));
/* 4561 */                                         out.write("</td>\n        ");
/*      */                                         
/* 4563 */                                         if (p1.getProperty("AVGEXECS").equals("-1"))
/*      */                                         {
/* 4565 */                                           out.write("\n        <td align=\"left\" class=\"yellowgrayborder\">&nbsp;-</td>\n        ");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 4570 */                                           out.write("\n        <td align=\"left\" class=\"yellowgrayborder\">");
/* 4571 */                                           out.print(formatNumber(p1.getProperty("AVGEXECS")));
/* 4572 */                                           out.write("</td>\n        ");
/*      */                                         }
/*      */                                         
/* 4575 */                                         String status2404 = alert.getProperty(resourceid + "#" + "2404");
/*      */                                         
/* 4577 */                                         out.write("\n        <td class=\"yellowgrayborder\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4578 */                                         out.print(resourceid);
/* 4579 */                                         out.write("&attributeid=2404')\">");
/* 4580 */                                         out.print(getSeverityImage(status2404));
/* 4581 */                                         out.write("&nbsp;</td>\n      </tr>\n      ");
/*      */                                       }
/* 4583 */                                       out.write("\n      <tr>\n        <td align=\"left\" class=\"whitegrayborder\">");
/* 4584 */                                       out.print(FormatUtil.getString("am.webclient.oracle.reads"));
/* 4585 */                                       out.write("</td>\n        <td align=\"left\" class=\"whitegrayborder\">");
/* 4586 */                                       out.print(formatNumber(p1.getProperty("READS")));
/* 4587 */                                       out.write("</td>\n        <td class=\"whitegrayborder\">&nbsp;-</td>\n      </tr>\n      <tr>\n        <td align=\"left\" class=\"yellowgrayborder\">");
/* 4588 */                                       out.print(FormatUtil.getString("am.webclient.oracle.writes"));
/* 4589 */                                       out.write(" </td>\n        <td align=\"left\" class=\"yellowgrayborder\">");
/* 4590 */                                       out.print(formatNumber(p1.getProperty("WRITES")));
/* 4591 */                                       out.write("</td>\n        <td class=\"yellowgrayborder\">&nbsp;-</td>\n      </tr>\n      <tr>\n        <td align=\"left\" class=\"whitegrayborder\">");
/* 4592 */                                       out.print(FormatUtil.getString("am.webclient.oracle.blocksize"));
/* 4593 */                                       out.write(" </td>\n        <td align=\"left\" class=\"whitegrayborder\" title=\"");
/* 4594 */                                       out.print(formatNumber(p1.getProperty("BLOCKSIZE")));
/* 4595 */                                       out.write(" bytes\">");
/* 4596 */                                       out.print(formatBytesToMB(p1.getProperty("BLOCKSIZE")));
/* 4597 */                                       out.write("\n          </td>\n        ");
/*      */                                       
/* 4599 */                                       String status2406 = alert.getProperty(resourceid + "#" + "2406");
/*      */                                       
/* 4601 */                                       out.write("\n        <td  class=\"whitegrayborder\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4602 */                                       out.print(resourceid);
/* 4603 */                                       out.write("&attributeid=2406')\">");
/* 4604 */                                       out.print(getSeverityImage(status2406));
/* 4605 */                                       out.write("&nbsp;</td>\n      </tr>\n      <tr > <td  colspan=\"3\" height=\"21\" class=\"yellowgrayborder\" class=\"bodytext\" align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;\n      ");
/*      */                                       
/* 4607 */                                       if (averageexecutions == 1)
/*      */                                       {
/* 4609 */                                         out.write("\n      <a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4610 */                                         out.print(resourceid);
/* 4611 */                                         out.write("&attributeIDs=2403,2404,2406&attributeToSelect=2403&redirectto=");
/* 4612 */                                         out.print(encodeurl);
/* 4613 */                                         out.write("\" class=\"links\">\n      ");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4617 */                                         out.write("\n      <a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4618 */                                         out.print(resourceid);
/* 4619 */                                         out.write("&attributeIDs=2403,2406&attributeToSelect=2403&redirectto=");
/* 4620 */                                         out.print(encodeurl);
/* 4621 */                                         out.write("\" class=\"links\">\n      ");
/*      */                                       }
/* 4623 */                                       out.write("\n      ");
/* 4624 */                                       out.print(ALERTCONFIG_TEXT);
/* 4625 */                                       out.write("</a>&nbsp;</td> </tr>\n    </table>\n    ");
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/*      */ 
/* 4631 */                                       out.write("\n    <tr>\n      <td align=\"left\" class=\"whitegrayborder\">");
/* 4632 */                                       out.print(FormatUtil.getString("am.webclient.oracle.databasesize"));
/* 4633 */                                       out.write(" </td>\n      <td align=\"left\" class=\"whitegrayborder\">-</td>\n      <td  class=\"whitegrayborder\" >-</td>\n    </tr>\n    <tr>\n      <td align=\"left\" class=\"yellowgrayborder\">");
/* 4634 */                                       out.print(FormatUtil.getString("am.webclient.oracle.averageexecutions"));
/* 4635 */                                       out.write("</td>\n      <td align=\"left\" class=\"yellowgrayborder\">-</td>\n      <td  class=\"yellowgrayborder\" >-</td>\n    </tr>\n    <tr>\n      <td align=\"left\" class=\"yellowgrayborder\">");
/* 4636 */                                       out.print(FormatUtil.getString("am.webclient.oracle.reads"));
/* 4637 */                                       out.write(" </td>\n      <td align=\"left\" class=\"yellowgrayborder\">-</td>\n      <td  class=\"yellowgrayborder\" >-</td>\n    </tr>\n    <tr>\n      <td align=\"left\" class=\"whitegrayborder\">");
/* 4638 */                                       out.print(FormatUtil.getString("am.webclient.oracle.writes"));
/* 4639 */                                       out.write(" </td>\n      <td align=\"left\" class=\"whitegrayborder\">-</td>\n      <td  class=\"whitegrayborder\" >-</td>\n    </tr>\n    <tr>\n      <td align=\"left\" class=\"yellowgrayborder\">");
/* 4640 */                                       out.print(FormatUtil.getString("am.webclient.oracle.blocksize"));
/* 4641 */                                       out.write(" </td>\n      <td align=\"left\" class=\"yellowgrayborder\">-</td>\n      <td  class=\"yellowgrayborder\" >-</td>\n    </tr>\n    <tr > <td  colspan=\"3\" height=\"21\" class=\"whitegrayborder\" class=\"bodytext\" align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4642 */                                       out.print(resourceid);
/* 4643 */                                       out.write("&attributeIDs=2403,2404,2405,2406&attributeToSelect=2403&redirectto=");
/* 4644 */                                       out.print(encodeurl);
/* 4645 */                                       out.write("\" class=\"links\">");
/* 4646 */                                       out.print(ALERTCONFIG_TEXT);
/* 4647 */                                       out.write("</a>&nbsp;</td> </tr>\n  </table>\n  ");
/*      */                                     }
/*      */                                     
/*      */ 
/* 4651 */                                     out.write("</td></tr>\n</table></td>\n</tr> </table>\n\n\n  <br>\n  ");
/*      */                                     
/*      */                                     try
/*      */                                     {
/* 4655 */                                       Properties p2 = new Properties();
/* 4656 */                                       p2 = databean.getSgaStatus(resourcename);
/* 4657 */                                       String Bufferhitraio = p2.getProperty("BUFFERHITRATIO");
/*      */                                       
/*      */ 
/* 4660 */                                       String Datadictratio = p2.getProperty("DICTIONARYHITRATIO");
/*      */                                       
/* 4662 */                                       String Libraryhitratio = p2.getProperty("LIBRARYHITRATIO");
/*      */                                       
/*      */ 
/* 4665 */                                       out.write("\n\n <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n   <tr>\n     <td  class=\"tableheadingbborder\">");
/* 4666 */                                       out.print(FormatUtil.getString("am.webclient.oracle.tablespace.hitratio"));
/* 4667 */                                       out.write("</td>\n     <td class=\"tableheadingbborder\">");
/* 4668 */                                       out.print(FormatUtil.getString("am.webclient.oracle.tablespace.sharedSGA"));
/* 4669 */                                       out.write("</td>\n   </tr>\n   ");
/* 4670 */                                       if (!p2.isEmpty())
/*      */                                       {
/*      */ 
/* 4673 */                                         out.write("\n\n\n <tr>\n     <td width=\"0%\" valign=\"top\" class=\"rborder\">\n          <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n               <tr>\n\t\t\t      <td width=\"33%\">&nbsp</td>\n\t\t\t      <td width=\"33%\">&nbsp</td>\n\t              <td width=\"33%\">\n\t               \t  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t        <tr>\n\t\t\t\t\t      <td width=\"96%\" align=\"right\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4674 */                                         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                           return;
/* 4676 */                                         out.write("&attributeid=2411&period=-7',740,550)\">\n\t\t\t\t\t    \t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"6\" vspace=\"6\" border=\"0\" title='");
/* 4677 */                                         out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4678 */                                         out.write("'></td>\n\t\t\t\t\t      <td width=\"4%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4679 */                                         if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                           return;
/* 4681 */                                         out.write("&attributeid=2411&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"6\" vspace=\"6\" border=\"0\" title='");
/* 4682 */                                         out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4683 */                                         out.write("'></td>\n\t\t\t\t\t    </tr>\n\t\t\t\t      </table>\n\t\t\t\t  </td>\n               </tr>\n\n\n          \t    <tr>\n                 <td width=\"33%\" align=\"right\"><br></td>\n                 <td width=\"33%\" align=\"right\"><br></td>\n                 <td width=\"33%\" align=\"right\"><br></td>\n               </tr>\n\n\n\t\t\t  \t <tr>\n\t                 <td width=\"33%\" align=\"center\" style=\"padding-left:5px;padding-right:5px;\">\n\t\t                   <fieldset >\n\t\t                   \t<span class=\"bodytextbold\" style=\"text-align:center;\">");
/* 4684 */                                         out.print(FormatUtil.getString("am.webclient.oracle.Bufferhitratio"));
/* 4685 */                                         out.write("</span>\n\t\t\t                   <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t                     <tr>\n\t\t\t                       <td align=\"center\"> ");
/* 4686 */                                         JspRuntimeLibrary.include(request, response, "/jsp/helicalgraph.jsp" + ("/jsp/helicalgraph.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.oracle.bufferhitratio")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("percent", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(Bufferhitraio), request.getCharacterEncoding()), out, true);
/* 4687 */                                         out.write(" </td>\n\t\t\t                     </tr>\n\t\t\t                   </table>\n\t\t                   </fieldset>\n\t                   </td>\n\n                 \t\t<td width=\"33%\" align=\"center\" style=\"padding-left:5px;padding-right:5px;\">\n\t\t\t                   <fieldset  >\n\t\t\t                   <span class=\"bodytextbold\" >");
/* 4688 */                                         out.print(FormatUtil.getString("am.webclient.oracle.Datodictionaryratio"));
/* 4689 */                                         out.write("</span>\n\t\t\t                   <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t                     <tr>\n\t\t\t                       <td align=\"center\"> ");
/* 4690 */                                         JspRuntimeLibrary.include(request, response, "/jsp/helicalgraph.jsp" + ("/jsp/helicalgraph.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.oracle.datadictionaryhitratio")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("percent", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(Datadictratio), request.getCharacterEncoding()), out, true);
/* 4691 */                                         out.write(" </td>\n\t\t\t                     </tr>\n\t\t\t                   </table>\n\t\t\t                   </fieldset>\n\t\t                </td>\n\t\t                <td width=\"33%\" align=\"center\" style=\"padding-left:5px;padding-right:5px;\">\n\t\t\t                <fieldset >\n\t\t\t                   <span class=\"bodytextbold\">");
/* 4692 */                                         out.print(FormatUtil.getString("am.webclient.oracle.Libraryhitratio"));
/* 4693 */                                         out.write("</span>\n\t\t\t                   <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t                     <tr>\n\t\t\t                       <td align=\"center\"> ");
/* 4694 */                                         JspRuntimeLibrary.include(request, response, "/jsp/helicalgraph.jsp" + ("/jsp/helicalgraph.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.oracle.libraryhitratio")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("percent", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(Libraryhitratio), request.getCharacterEncoding()), out, true);
/* 4695 */                                         out.write(" </td>\n\t\t\t                     </tr>\n\t\t\t                   </table>\n\t\t\t                 </fieldset>\n\t\t               </td>\n\t\t        </tr>\n             </table>\n\n\n\n                 </td>\n                   <td width=\"50%\" valign=\"center\" class=\"rborder\">\n                   <table width=\"90%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t                   <tr>\n\t\t                    <td colspan=\"4\" align=\"center\">\n\t\t\t                    ");
/*      */                                         
/* 4697 */                                         AMWolf _jspx_th_awolf_005fpiechart_005f1 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 4698 */                                         _jspx_th_awolf_005fpiechart_005f1.setPageContext(_jspx_page_context);
/* 4699 */                                         _jspx_th_awolf_005fpiechart_005f1.setParent(_jspx_th_c_005fif_005f7);
/*      */                                         
/* 4701 */                                         _jspx_th_awolf_005fpiechart_005f1.setDataSetProducer("databean");
/*      */                                         
/* 4703 */                                         _jspx_th_awolf_005fpiechart_005f1.setWidth("350");
/*      */                                         
/* 4705 */                                         _jspx_th_awolf_005fpiechart_005f1.setHeight("290");
/*      */                                         
/* 4707 */                                         _jspx_th_awolf_005fpiechart_005f1.setLegend("true");
/*      */                                         
/* 4709 */                                         _jspx_th_awolf_005fpiechart_005f1.setDecimal(false);
/*      */                                         
/* 4711 */                                         _jspx_th_awolf_005fpiechart_005f1.setUnits("MB");
/* 4712 */                                         int _jspx_eval_awolf_005fpiechart_005f1 = _jspx_th_awolf_005fpiechart_005f1.doStartTag();
/* 4713 */                                         if (_jspx_eval_awolf_005fpiechart_005f1 != 0) {
/* 4714 */                                           if (_jspx_eval_awolf_005fpiechart_005f1 != 1) {
/* 4715 */                                             out = _jspx_page_context.pushBody();
/* 4716 */                                             _jspx_th_awolf_005fpiechart_005f1.setBodyContent((BodyContent)out);
/* 4717 */                                             _jspx_th_awolf_005fpiechart_005f1.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4720 */                                             out.write("\n\t\t\t\t\t\t       \t");
/*      */                                             
/* 4722 */                                             Property _jspx_th_awolf_005fmap_005f1 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 4723 */                                             _jspx_th_awolf_005fmap_005f1.setPageContext(_jspx_page_context);
/* 4724 */                                             _jspx_th_awolf_005fmap_005f1.setParent(_jspx_th_awolf_005fpiechart_005f1);
/*      */                                             
/* 4726 */                                             _jspx_th_awolf_005fmap_005f1.setId("color");
/* 4727 */                                             int _jspx_eval_awolf_005fmap_005f1 = _jspx_th_awolf_005fmap_005f1.doStartTag();
/* 4728 */                                             if (_jspx_eval_awolf_005fmap_005f1 != 0) {
/* 4729 */                                               if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 4730 */                                                 out = _jspx_page_context.pushBody();
/* 4731 */                                                 _jspx_th_awolf_005fmap_005f1.setBodyContent((BodyContent)out);
/* 4732 */                                                 _jspx_th_awolf_005fmap_005f1.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4735 */                                                 out.write("\n\t\t\t\t\t\t        ");
/*      */                                                 
/* 4737 */                                                 AMParam _jspx_th_awolf_005fparam_005f2 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4738 */                                                 _jspx_th_awolf_005fparam_005f2.setPageContext(_jspx_page_context);
/* 4739 */                                                 _jspx_th_awolf_005fparam_005f2.setParent(_jspx_th_awolf_005fmap_005f1);
/*      */                                                 
/* 4741 */                                                 _jspx_th_awolf_005fparam_005f2.setName("3");
/*      */                                                 
/* 4743 */                                                 _jspx_th_awolf_005fparam_005f2.setValue("#2F4F4F");
/* 4744 */                                                 int _jspx_eval_awolf_005fparam_005f2 = _jspx_th_awolf_005fparam_005f2.doStartTag();
/* 4745 */                                                 if (_jspx_th_awolf_005fparam_005f2.doEndTag() == 5) {
/* 4746 */                                                   this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2); return;
/*      */                                                 }
/*      */                                                 
/* 4749 */                                                 this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2);
/* 4750 */                                                 out.write("\n\t\t\t\t\t\t        ");
/*      */                                                 
/* 4752 */                                                 AMParam _jspx_th_awolf_005fparam_005f3 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4753 */                                                 _jspx_th_awolf_005fparam_005f3.setPageContext(_jspx_page_context);
/* 4754 */                                                 _jspx_th_awolf_005fparam_005f3.setParent(_jspx_th_awolf_005fmap_005f1);
/*      */                                                 
/* 4756 */                                                 _jspx_th_awolf_005fparam_005f3.setName("2");
/*      */                                                 
/* 4758 */                                                 _jspx_th_awolf_005fparam_005f3.setValue("#8B008B");
/* 4759 */                                                 int _jspx_eval_awolf_005fparam_005f3 = _jspx_th_awolf_005fparam_005f3.doStartTag();
/* 4760 */                                                 if (_jspx_th_awolf_005fparam_005f3.doEndTag() == 5) {
/* 4761 */                                                   this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3); return;
/*      */                                                 }
/*      */                                                 
/* 4764 */                                                 this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3);
/* 4765 */                                                 out.write("\n\t\t\t\t\t\t       \t");
/*      */                                                 
/* 4767 */                                                 AMParam _jspx_th_awolf_005fparam_005f4 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4768 */                                                 _jspx_th_awolf_005fparam_005f4.setPageContext(_jspx_page_context);
/* 4769 */                                                 _jspx_th_awolf_005fparam_005f4.setParent(_jspx_th_awolf_005fmap_005f1);
/*      */                                                 
/* 4771 */                                                 _jspx_th_awolf_005fparam_005f4.setName("1");
/*      */                                                 
/* 4773 */                                                 _jspx_th_awolf_005fparam_005f4.setValue("#FF9900");
/* 4774 */                                                 int _jspx_eval_awolf_005fparam_005f4 = _jspx_th_awolf_005fparam_005f4.doStartTag();
/* 4775 */                                                 if (_jspx_th_awolf_005fparam_005f4.doEndTag() == 5) {
/* 4776 */                                                   this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f4); return;
/*      */                                                 }
/*      */                                                 
/* 4779 */                                                 this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f4);
/* 4780 */                                                 out.write("\n\t\t\t\t\t\t   \t\t");
/*      */                                                 
/* 4782 */                                                 AMParam _jspx_th_awolf_005fparam_005f5 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4783 */                                                 _jspx_th_awolf_005fparam_005f5.setPageContext(_jspx_page_context);
/* 4784 */                                                 _jspx_th_awolf_005fparam_005f5.setParent(_jspx_th_awolf_005fmap_005f1);
/*      */                                                 
/* 4786 */                                                 _jspx_th_awolf_005fparam_005f5.setName("0");
/*      */                                                 
/* 4788 */                                                 _jspx_th_awolf_005fparam_005f5.setValue("#00CCFF");
/* 4789 */                                                 int _jspx_eval_awolf_005fparam_005f5 = _jspx_th_awolf_005fparam_005f5.doStartTag();
/* 4790 */                                                 if (_jspx_th_awolf_005fparam_005f5.doEndTag() == 5) {
/* 4791 */                                                   this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f5); return;
/*      */                                                 }
/*      */                                                 
/* 4794 */                                                 this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f5);
/* 4795 */                                                 out.write("\n\t\t\t\t\t\t        ");
/* 4796 */                                                 int evalDoAfterBody = _jspx_th_awolf_005fmap_005f1.doAfterBody();
/* 4797 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4800 */                                               if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 4801 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4804 */                                             if (_jspx_th_awolf_005fmap_005f1.doEndTag() == 5) {
/* 4805 */                                               this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1); return;
/*      */                                             }
/*      */                                             
/* 4808 */                                             this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1);
/* 4809 */                                             out.write("\n\t\t\t                    ");
/* 4810 */                                             int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f1.doAfterBody();
/* 4811 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4814 */                                           if (_jspx_eval_awolf_005fpiechart_005f1 != 1) {
/* 4815 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4818 */                                         if (_jspx_th_awolf_005fpiechart_005f1.doEndTag() == 5) {
/* 4819 */                                           this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f1); return;
/*      */                                         }
/*      */                                         
/* 4822 */                                         this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f1);
/* 4823 */                                         out.write("\n\t\t                    </td>\n\t                    </tr>\n                    </table>\n                   </td>\n\n             </tr>\n             </tr>\n           </table>\n          <br>\n ");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4827 */                                         out.write("\n\n\t     <tr>       <td colspan=\"10\" class=\"bodytextbold\" align=\"center\">");
/* 4828 */                                         out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 4829 */                                         out.write("</td> </tr>\n\t     </table>\n\t     <br>\n   ");
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Exception e)
/*      */                                     {
/* 4834 */                                       System.out.println("The exception  is" + e);
/*      */                                     }
/*      */                                     
/*      */ 
/* 4838 */                                     out.write("\n\n\n\n\n\n\n<div id=\"tablespacedetails\"></div>\n\n\n<br>\n\n");
/* 4839 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 4840 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4844 */                                 if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 4845 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                 }
/*      */                                 
/* 4848 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4849 */                                 out.write(10);
/*      */                                 
/* 4851 */                                 IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 4852 */                                 _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 4853 */                                 _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                 
/* 4855 */                                 _jspx_th_logic_005fiterate_005f0.setName("script_ids");
/*      */                                 
/* 4857 */                                 _jspx_th_logic_005fiterate_005f0.setId("attribute");
/*      */                                 
/* 4859 */                                 _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                                 
/* 4861 */                                 _jspx_th_logic_005fiterate_005f0.setType("java.lang.String");
/* 4862 */                                 int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 4863 */                                 if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 4864 */                                   String attribute = null;
/* 4865 */                                   Integer j = null;
/* 4866 */                                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 4867 */                                     out = _jspx_page_context.pushBody();
/* 4868 */                                     _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 4869 */                                     _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                   }
/* 4871 */                                   attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 4872 */                                   j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                   for (;;) {
/* 4874 */                                     out.write(10);
/* 4875 */                                     out.write(9);
/* 4876 */                                     out.write(32);
/*      */                                     
/* 4878 */                                     String query = "select scriptname,displayname from AM_ScriptArgs where resourceid=" + attribute;
/* 4879 */                                     String monitorname1 = null;
/* 4880 */                                     String displayname1 = null;
/*      */                                     try
/*      */                                     {
/* 4883 */                                       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4884 */                                       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 4885 */                                       if (rs.next())
/*      */                                       {
/* 4887 */                                         monitorname1 = rs.getString("scriptname");
/* 4888 */                                         displayname1 = rs.getString("displayname");
/*      */                                       }
/* 4890 */                                       rs.close();
/*      */                                     }
/*      */                                     catch (Exception exc) {}
/*      */                                     
/*      */ 
/* 4895 */                                     String url2 = "/showresource.do?resourceid=" + attribute + "&type=Script Monitor&moname=" + monitorname1 + "&method=showScriptMonitorDetails&resourcename=" + displayname1 + "&fromhost=true";
/* 4896 */                                     String url3 = "/jsp/HostScript.jsp?resourceid=" + attribute + "&type=Script Monitor&moname=" + monitorname1 + "&method=showScriptMonitorDetails&resourcename=" + displayname1 + "&fromhost=true&hostid=" + resourceid;
/*      */                                     
/* 4898 */                                     out.write("\n         <table  width=\"99%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n\t ");
/* 4899 */                                     JspRuntimeLibrary.include(request, response, url2, out, false);
/* 4900 */                                     out.write("\n         <tr>\n         <td width=\"99%\"   class=\"tableheadingtrans\" >\n         <a href=\"showresource.do?method=showResourceForResourceID&resourceid=");
/* 4901 */                                     out.print(attribute);
/* 4902 */                                     out.write("\" class=\"staticlinks\">");
/* 4903 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.scriptmonitor"));
/* 4904 */                                     out.write(32);
/* 4905 */                                     out.write(45);
/* 4906 */                                     out.write(32);
/* 4907 */                                     out.print(displayname1);
/* 4908 */                                     out.write("</a>\n         </td>\n         </tr>\n         <tr>\n         <td>\n         ");
/* 4909 */                                     JspRuntimeLibrary.include(request, response, url3, out, false);
/* 4910 */                                     out.write("\n         </td>\n         </tr>\n         <br>\n         </table>\n         <br>\n         ");
/* 4911 */                                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 4912 */                                     attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 4913 */                                     j = (Integer)_jspx_page_context.findAttribute("j");
/* 4914 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 4917 */                                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 4918 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 4921 */                                 if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 4922 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                 }
/*      */                                 
/* 4925 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 4926 */                                 out.write("\n\t <br>\n\t");
/* 4927 */                                 out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/* 4928 */                                 DialChartSupport dialGraph = null;
/* 4929 */                                 dialGraph = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 4930 */                                 if (dialGraph == null) {
/* 4931 */                                   dialGraph = new DialChartSupport();
/* 4932 */                                   _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */                                 }
/* 4934 */                                 out.write(10);
/*      */                                 
/*      */                                 try
/*      */                                 {
/* 4938 */                                   String hostos = (String)systeminfo.get("HOSTOS");
/* 4939 */                                   String hostname = (String)systeminfo.get("HOSTNAME");
/* 4940 */                                   String hostid = (String)systeminfo.get("host_resid");
/* 4941 */                                   boolean isConf = false;
/* 4942 */                                   if ((systeminfo.get("isConf") != null) && (((String)systeminfo.get("isConf")).equals("true"))) {
/* 4943 */                                     isConf = true;
/*      */                                   }
/* 4945 */                                   com.adventnet.appmanager.db.RepairTables rt = new com.adventnet.appmanager.db.RepairTables();
/* 4946 */                                   Properties property = new Properties();
/* 4947 */                                   if ((hostos != null) && (!hostos.equalsIgnoreCase("unknown")) && (!hostos.equalsIgnoreCase("node")))
/*      */                                   {
/* 4949 */                                     property = com.adventnet.appmanager.db.RepairTables.getValuesForHost(hostname, hostos);
/* 4950 */                                     if ((property != null) && (property.size() > 0))
/*      */                                     {
/* 4952 */                                       String cpuid = property.getProperty("cpuid");
/* 4953 */                                       String memid = property.getProperty("memid");
/* 4954 */                                       String diskid = property.getProperty("diskid");
/* 4955 */                                       String cpuvalue = property.getProperty("CPU Utilization");
/* 4956 */                                       String memvalue = property.getProperty("Memory Utilization");
/* 4957 */                                       String memurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + memid + "&period=0')";
/* 4958 */                                       String cpuurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + cpuid + "&period=0')";
/* 4959 */                                       String diskvalue = property.getProperty("Disk Utilization");
/* 4960 */                                       String diskurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + diskid + "&period=0')";
/*      */                                       
/* 4962 */                                       if (!isConf) {
/* 4963 */                                         out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 4964 */                                         out.print(FormatUtil.getString("am.webclient.serversnapshot.heading"));
/* 4965 */                                         out.write(45);
/* 4966 */                                         if (systeminfo.get("host_resid") != null) {
/* 4967 */                                           out.write("<a href=\"showresource.do?resourceid=");
/* 4968 */                                           out.print(hostid);
/* 4969 */                                           out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 4970 */                                           out.print(hostname);
/* 4971 */                                           out.write("</a>");
/* 4972 */                                         } else { out.println(hostname); }
/* 4973 */                                         out.write("</td>\t");
/* 4974 */                                         out.write("\n  </tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"30%\" valign=\"top\">\n    ");
/* 4975 */                                         out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 4976 */                                         out.write("\n    <table  cellspacing=\"0\" cellpadding=\"3\" border=\"0\" width=\"99%\">\n\n        <tr>\n         ");
/*      */                                         
/*      */ 
/* 4979 */                                         if (cpuvalue != null)
/*      */                                         {
/*      */ 
/* 4982 */                                           dialGraph.setValue(Long.parseLong(cpuvalue));
/* 4983 */                                           out.write("\n         <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4984 */                                           out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4985 */                                           out.write(45);
/* 4986 */                                           out.print(cpuvalue);
/* 4987 */                                           out.write(" %'>\n\n");
/*      */                                           
/* 4989 */                                           DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 4990 */                                           _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 4991 */                                           _jspx_th_awolf_005fdialchart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                           
/* 4993 */                                           _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */                                           
/* 4995 */                                           _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */                                           
/* 4997 */                                           _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */                                           
/* 4999 */                                           _jspx_th_awolf_005fdialchart_005f0.setLegend("false");
/*      */                                           
/* 5001 */                                           _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */                                           
/* 5003 */                                           _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */                                           
/* 5005 */                                           _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/*      */                                           
/* 5007 */                                           _jspx_th_awolf_005fdialchart_005f0.setLink(cpuurl);
/*      */                                           
/* 5009 */                                           _jspx_th_awolf_005fdialchart_005f0.setResourceId(hostid);
/*      */                                           
/* 5011 */                                           _jspx_th_awolf_005fdialchart_005f0.setAttributeId(cpuid);
/* 5012 */                                           int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 5013 */                                           if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 5014 */                                             if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 5015 */                                               out = _jspx_page_context.pushBody();
/* 5016 */                                               _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 5017 */                                               _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 5020 */                                               out.write(10);
/* 5021 */                                               int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 5022 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 5025 */                                             if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 5026 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 5029 */                                           if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 5030 */                                             this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0); return;
/*      */                                           }
/*      */                                           
/* 5033 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 5034 */                                           out.write("\n         </td>\n            ");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 5038 */                                           out.write("\n\n\t<tr>\n\t\t<td><img src=\"../images/spacer.gif\" height=\"30\" ></td></tr>\n\n\n<tr>  \t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5039 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5040 */                                           out.write(32);
/* 5041 */                                           out.write(62);
/* 5042 */                                           out.write(10);
/* 5043 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5044 */                                           out.write("</td></tr>\n \t\t<!--img src='../images/nodata.gif'-->\n<tr>\t\t<td><img src=\"../images/spacer.gif\" height=\"30\"></td></tr>\n\n\n  ");
/*      */                                         }
/* 5046 */                                         out.write("\n      </tr>\n       <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 5047 */                                         if (cpuvalue != null)
/*      */                                         {
/* 5049 */                                           out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5050 */                                           out.print(hostid);
/* 5051 */                                           out.write("&attributeid=");
/* 5052 */                                           out.print(cpuid);
/* 5053 */                                           out.write("&period=-7')\" class='bodytextbold'>");
/* 5054 */                                           out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5055 */                                           out.write(32);
/* 5056 */                                           out.write(45);
/* 5057 */                                           out.write(32);
/* 5058 */                                           out.print(cpuvalue);
/* 5059 */                                           out.write("</a> %\n");
/*      */                                         }
/* 5061 */                                         out.write("\n  </td>\n       </tr>\n       </table>");
/* 5062 */                                         out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5063 */                                         out.write("</td>\n      <td width=\"30%\"> ");
/* 5064 */                                         out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5065 */                                         out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n             <tr>\n");
/*      */                                         
/* 5067 */                                         if (memvalue != null)
/*      */                                         {
/*      */ 
/* 5070 */                                           dialGraph.setValue(Long.parseLong(memvalue));
/* 5071 */                                           out.write("\n            <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5072 */                                           out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5073 */                                           out.write(45);
/* 5074 */                                           out.print(memvalue);
/* 5075 */                                           out.write(" %' >\n\n");
/*      */                                           
/* 5077 */                                           DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5078 */                                           _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 5079 */                                           _jspx_th_awolf_005fdialchart_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                           
/* 5081 */                                           _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */                                           
/* 5083 */                                           _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */                                           
/* 5085 */                                           _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */                                           
/* 5087 */                                           _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */                                           
/* 5089 */                                           _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */                                           
/* 5091 */                                           _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */                                           
/* 5093 */                                           _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/*      */                                           
/* 5095 */                                           _jspx_th_awolf_005fdialchart_005f1.setLink(memurl);
/*      */                                           
/* 5097 */                                           _jspx_th_awolf_005fdialchart_005f1.setResourceId(hostid);
/*      */                                           
/* 5099 */                                           _jspx_th_awolf_005fdialchart_005f1.setAttributeId(memid);
/* 5100 */                                           int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 5101 */                                           if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 5102 */                                             if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 5103 */                                               out = _jspx_page_context.pushBody();
/* 5104 */                                               _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 5105 */                                               _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 5108 */                                               out.write(32);
/* 5109 */                                               int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 5110 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 5113 */                                             if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 5114 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 5117 */                                           if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 5118 */                                             this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1); return;
/*      */                                           }
/*      */                                           
/* 5121 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 5122 */                                           out.write(32);
/* 5123 */                                           out.write("\n            </td>\n            ");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 5127 */                                           out.write("\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n<tr>    <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5128 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5129 */                                           out.write(" >\n\n");
/* 5130 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5131 */                                           out.write("</td></tr>\n<!--img src='../images/nodata.gif'-->\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                         }
/* 5133 */                                         out.write("\n  </tr>\n   <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 5134 */                                         if (memvalue != null)
/*      */                                         {
/* 5136 */                                           out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5137 */                                           out.print(hostid);
/* 5138 */                                           out.write("&attributeid=");
/* 5139 */                                           out.print(memid);
/* 5140 */                                           out.write("&period=-7')\" class='bodytextbold'>");
/* 5141 */                                           out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5142 */                                           out.write(45);
/* 5143 */                                           out.print(memvalue);
/* 5144 */                                           out.write("</a> %\n  ");
/*      */                                         }
/* 5146 */                                         out.write("\n  </td>\n       </tr>\n    </table>");
/* 5147 */                                         out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5148 */                                         out.write("</td>\n      <td width=\"30%\">");
/* 5149 */                                         out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5150 */                                         out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                                         
/*      */ 
/* 5153 */                                         if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                         {
/*      */ 
/*      */ 
/* 5157 */                                           dialGraph.setValue(Long.parseLong(diskvalue));
/* 5158 */                                           out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5159 */                                           out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 5160 */                                           out.write(45);
/* 5161 */                                           out.print(diskvalue);
/* 5162 */                                           out.write("%' >\n");
/*      */                                           
/* 5164 */                                           DialChart _jspx_th_awolf_005fdialchart_005f2 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5165 */                                           _jspx_th_awolf_005fdialchart_005f2.setPageContext(_jspx_page_context);
/* 5166 */                                           _jspx_th_awolf_005fdialchart_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                           
/* 5168 */                                           _jspx_th_awolf_005fdialchart_005f2.setDataSetProducer("dialGraph");
/*      */                                           
/* 5170 */                                           _jspx_th_awolf_005fdialchart_005f2.setWidth("150");
/*      */                                           
/* 5172 */                                           _jspx_th_awolf_005fdialchart_005f2.setHeight("148");
/*      */                                           
/* 5174 */                                           _jspx_th_awolf_005fdialchart_005f2.setLegend("false");
/*      */                                           
/* 5176 */                                           _jspx_th_awolf_005fdialchart_005f2.setXaxisLabel("");
/*      */                                           
/* 5178 */                                           _jspx_th_awolf_005fdialchart_005f2.setYaxisLabel("");
/*      */                                           
/* 5180 */                                           _jspx_th_awolf_005fdialchart_005f2.setDateFormat("HH:mm");
/*      */                                           
/* 5182 */                                           _jspx_th_awolf_005fdialchart_005f2.setLink(diskurl);
/*      */                                           
/* 5184 */                                           _jspx_th_awolf_005fdialchart_005f2.setResourceId(hostid);
/*      */                                           
/* 5186 */                                           _jspx_th_awolf_005fdialchart_005f2.setAttributeId(diskid);
/* 5187 */                                           int _jspx_eval_awolf_005fdialchart_005f2 = _jspx_th_awolf_005fdialchart_005f2.doStartTag();
/* 5188 */                                           if (_jspx_eval_awolf_005fdialchart_005f2 != 0) {
/* 5189 */                                             if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 5190 */                                               out = _jspx_page_context.pushBody();
/* 5191 */                                               _jspx_th_awolf_005fdialchart_005f2.setBodyContent((BodyContent)out);
/* 5192 */                                               _jspx_th_awolf_005fdialchart_005f2.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 5195 */                                               out.write(32);
/* 5196 */                                               int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f2.doAfterBody();
/* 5197 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 5200 */                                             if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 5201 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 5204 */                                           if (_jspx_th_awolf_005fdialchart_005f2.doEndTag() == 5) {
/* 5205 */                                             this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2); return;
/*      */                                           }
/*      */                                           
/* 5208 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2);
/* 5209 */                                           out.write(32);
/* 5210 */                                           out.write(32);
/* 5211 */                                           out.write("\n    </td>\n            ");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 5215 */                                           out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5216 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5217 */                                           out.write(32);
/* 5218 */                                           out.write(62);
/* 5219 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5220 */                                           out.write("\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                         }
/* 5222 */                                         out.write("\n  </tr>\n  <tr>\n\n\n\n  <td align='center'  class='bodytextbold'>\n");
/* 5223 */                                         if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                         {
/* 5225 */                                           out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5226 */                                           out.print(hostid);
/* 5227 */                                           out.write("&attributeid=");
/* 5228 */                                           out.print(diskid);
/* 5229 */                                           out.write("&period=-7')\" class='bodytextbold'>");
/* 5230 */                                           out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 5231 */                                           out.write(45);
/* 5232 */                                           out.print(diskvalue);
/* 5233 */                                           out.write("</a> %\n     ");
/*      */                                         }
/* 5235 */                                         out.write("\n  </td>\n  </tr>\n</table>");
/* 5236 */                                         out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5237 */                                         out.write("</td></tr></table>\n\n");
/*      */                                       } else {
/* 5239 */                                         out.write("\n\n\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\" width=\"100%\" onMouseOver=\"ShowPicture('configureIcons_ifany',1,'hostresource')\" onMouseOut=\"ShowPicture('configureIcons_ifany',0,'hostresource')\">\n\t<tr><td class=\"conf-mon-heading\" align=\"left\" colspan=\"3\">");
/* 5240 */                                         out.print(FormatUtil.getString("am.webclient.serversnapshot.allCaps.heading"));
/* 5241 */                                         out.write("&nbsp;-&nbsp;<a href=\"showresource.do?resourceid=");
/* 5242 */                                         out.print(systeminfo.get("host_resid"));
/* 5243 */                                         out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 5244 */                                         out.print(hostname);
/* 5245 */                                         out.write("</a></td></tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"><div id=\"configureIcons_ifany\"></div></td></tr>\n\t<tr>\n");
/* 5246 */                                         if (cpuvalue != null)
/*      */                                         {
/*      */ 
/* 5249 */                                           dialGraph.setValue(Long.parseLong(cpuvalue));
/* 5250 */                                           out.write("\n         <td align=\"center\" valign=\"center\">\n\t\t\t");
/*      */                                           
/* 5252 */                                           DialChart _jspx_th_awolf_005fdialchart_005f3 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5253 */                                           _jspx_th_awolf_005fdialchart_005f3.setPageContext(_jspx_page_context);
/* 5254 */                                           _jspx_th_awolf_005fdialchart_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                           
/* 5256 */                                           _jspx_th_awolf_005fdialchart_005f3.setDataSetProducer("dialGraph");
/*      */                                           
/* 5258 */                                           _jspx_th_awolf_005fdialchart_005f3.setWidth("150");
/*      */                                           
/* 5260 */                                           _jspx_th_awolf_005fdialchart_005f3.setHeight("148");
/*      */                                           
/* 5262 */                                           _jspx_th_awolf_005fdialchart_005f3.setLegend("false");
/*      */                                           
/* 5264 */                                           _jspx_th_awolf_005fdialchart_005f3.setXaxisLabel("");
/*      */                                           
/* 5266 */                                           _jspx_th_awolf_005fdialchart_005f3.setYaxisLabel("");
/*      */                                           
/* 5268 */                                           _jspx_th_awolf_005fdialchart_005f3.setDateFormat("HH:mm");
/*      */                                           
/* 5270 */                                           _jspx_th_awolf_005fdialchart_005f3.setLink(cpuurl);
/*      */                                           
/* 5272 */                                           _jspx_th_awolf_005fdialchart_005f3.setResourceId(hostid);
/*      */                                           
/* 5274 */                                           _jspx_th_awolf_005fdialchart_005f3.setAttributeId(cpuid);
/* 5275 */                                           int _jspx_eval_awolf_005fdialchart_005f3 = _jspx_th_awolf_005fdialchart_005f3.doStartTag();
/* 5276 */                                           if (_jspx_th_awolf_005fdialchart_005f3.doEndTag() == 5) {
/* 5277 */                                             this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3); return;
/*      */                                           }
/*      */                                           
/* 5280 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3);
/* 5281 */                                           out.write("\n         </td>\n     ");
/*      */                                         }
/*      */                                         else {
/* 5284 */                                           out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5285 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5286 */                                           out.write(39);
/* 5287 */                                           out.write(32);
/* 5288 */                                           out.write(62);
/* 5289 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5290 */                                           out.write("\n \t\t</td>\n\t\t");
/*      */                                         }
/* 5292 */                                         if (memvalue != null) {
/* 5293 */                                           dialGraph.setValue(Long.parseLong(memvalue));
/* 5294 */                                           out.write("\n            <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                           
/* 5296 */                                           DialChart _jspx_th_awolf_005fdialchart_005f4 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5297 */                                           _jspx_th_awolf_005fdialchart_005f4.setPageContext(_jspx_page_context);
/* 5298 */                                           _jspx_th_awolf_005fdialchart_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                           
/* 5300 */                                           _jspx_th_awolf_005fdialchart_005f4.setDataSetProducer("dialGraph");
/*      */                                           
/* 5302 */                                           _jspx_th_awolf_005fdialchart_005f4.setWidth("150");
/*      */                                           
/* 5304 */                                           _jspx_th_awolf_005fdialchart_005f4.setHeight("148");
/*      */                                           
/* 5306 */                                           _jspx_th_awolf_005fdialchart_005f4.setLegend("false");
/*      */                                           
/* 5308 */                                           _jspx_th_awolf_005fdialchart_005f4.setXaxisLabel("");
/*      */                                           
/* 5310 */                                           _jspx_th_awolf_005fdialchart_005f4.setYaxisLabel("");
/*      */                                           
/* 5312 */                                           _jspx_th_awolf_005fdialchart_005f4.setDateFormat("HH:mm");
/*      */                                           
/* 5314 */                                           _jspx_th_awolf_005fdialchart_005f4.setLink(memurl);
/*      */                                           
/* 5316 */                                           _jspx_th_awolf_005fdialchart_005f4.setResourceId(hostid);
/*      */                                           
/* 5318 */                                           _jspx_th_awolf_005fdialchart_005f4.setAttributeId(memid);
/* 5319 */                                           int _jspx_eval_awolf_005fdialchart_005f4 = _jspx_th_awolf_005fdialchart_005f4.doStartTag();
/* 5320 */                                           if (_jspx_th_awolf_005fdialchart_005f4.doEndTag() == 5) {
/* 5321 */                                             this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4); return;
/*      */                                           }
/*      */                                           
/* 5324 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4);
/* 5325 */                                           out.write("\n            </td>\n         ");
/*      */                                         }
/*      */                                         else {
/* 5328 */                                           out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5329 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5330 */                                           out.write(39);
/* 5331 */                                           out.write(32);
/* 5332 */                                           out.write(62);
/* 5333 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5334 */                                           out.write("\n \t\t</td>\n\t\t");
/*      */                                         }
/* 5336 */                                         if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 5337 */                                           dialGraph.setValue(Long.parseLong(diskvalue));
/* 5338 */                                           out.write("\n             <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                           
/* 5340 */                                           DialChart _jspx_th_awolf_005fdialchart_005f5 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5341 */                                           _jspx_th_awolf_005fdialchart_005f5.setPageContext(_jspx_page_context);
/* 5342 */                                           _jspx_th_awolf_005fdialchart_005f5.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                           
/* 5344 */                                           _jspx_th_awolf_005fdialchart_005f5.setDataSetProducer("dialGraph");
/*      */                                           
/* 5346 */                                           _jspx_th_awolf_005fdialchart_005f5.setWidth("150");
/*      */                                           
/* 5348 */                                           _jspx_th_awolf_005fdialchart_005f5.setHeight("148");
/*      */                                           
/* 5350 */                                           _jspx_th_awolf_005fdialchart_005f5.setLegend("false");
/*      */                                           
/* 5352 */                                           _jspx_th_awolf_005fdialchart_005f5.setXaxisLabel("");
/*      */                                           
/* 5354 */                                           _jspx_th_awolf_005fdialchart_005f5.setYaxisLabel("");
/*      */                                           
/* 5356 */                                           _jspx_th_awolf_005fdialchart_005f5.setDateFormat("HH:mm");
/*      */                                           
/* 5358 */                                           _jspx_th_awolf_005fdialchart_005f5.setLink(diskurl);
/*      */                                           
/* 5360 */                                           _jspx_th_awolf_005fdialchart_005f5.setResourceId(hostid);
/*      */                                           
/* 5362 */                                           _jspx_th_awolf_005fdialchart_005f5.setAttributeId(diskid);
/* 5363 */                                           int _jspx_eval_awolf_005fdialchart_005f5 = _jspx_th_awolf_005fdialchart_005f5.doStartTag();
/* 5364 */                                           if (_jspx_th_awolf_005fdialchart_005f5.doEndTag() == 5) {
/* 5365 */                                             this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5); return;
/*      */                                           }
/*      */                                           
/* 5368 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5);
/* 5369 */                                           out.write(32);
/* 5370 */                                           out.write("\n\t          </td>\n\t  ");
/*      */                                         }
/*      */                                         else {
/* 5373 */                                           out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5374 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5375 */                                           out.write(39);
/* 5376 */                                           out.write(32);
/* 5377 */                                           out.write(62);
/* 5378 */                                           out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5379 */                                           out.write("\n \t\t</td>\n\t\t");
/*      */                                         }
/* 5381 */                                         out.write("\n         \t</tr>\n\t<tr id=\"showLinks_hostresource\">\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5382 */                                         out.print(hostid);
/* 5383 */                                         out.write("&attributeid=");
/* 5384 */                                         out.print(cpuid);
/* 5385 */                                         out.write("&period=-7')\" class='tooltip'>");
/* 5386 */                                         out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5387 */                                         out.write(32);
/* 5388 */                                         out.write(45);
/* 5389 */                                         out.write(32);
/* 5390 */                                         if (cpuvalue != null) {
/* 5391 */                                           out.print(cpuvalue);
/*      */                                         }
/* 5393 */                                         out.write(" %</a>\n\t\t</span>\n\t\t</td>\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5394 */                                         out.print(hostid);
/* 5395 */                                         out.write("&attributeid=");
/* 5396 */                                         out.print(memid);
/* 5397 */                                         out.write("&period=-7')\" class='tooltip'>");
/* 5398 */                                         out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5399 */                                         out.write(45);
/* 5400 */                                         if (memvalue != null) {
/* 5401 */                                           out.print(memvalue);
/*      */                                         }
/* 5403 */                                         out.write(" %</a>\n  \t\t</span>\n\t\t</td>\n\t\t<td align=\"center\">\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5404 */                                         out.print(hostid);
/* 5405 */                                         out.write("&attributeid=");
/* 5406 */                                         out.print(diskid);
/* 5407 */                                         out.write("&period=-7')\" class='tooltip'>");
/* 5408 */                                         out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 5409 */                                         out.write(45);
/* 5410 */                                         if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 5411 */                                           out.print(diskvalue);
/*      */                                         }
/* 5413 */                                         out.write(" %</a>\n     \t</span>\n\t\t</td>\n\t</tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n</table>\n         \t\n");
/*      */                                       }
/* 5415 */                                       out.write(10);
/* 5416 */                                       out.write(10);
/*      */                                     }
/*      */                                     
/*      */                                   }
/*      */                                 }
/*      */                                 catch (Exception e)
/*      */                                 {
/* 5423 */                                   e.printStackTrace();
/*      */                                 }
/* 5425 */                                 out.write(10);
/* 5426 */                                 out.write(10);
/*      */                               }
/*      */                               
/*      */ 
/* 5430 */                               out.write("\n</div>\n\n\n<div id=\"maindiv\" style=\"DISPLAY: block\">\n</div>\n\n<br>\n<div id=\"seconddiv\" style=\"DISPLAY: block\">\n</div>\n\n\n<br>\n<div id=\"actionstatus3\" style=\"DISPLAY: none\">");
/* 5431 */                               out.print(FormatUtil.getString("am.webclient.oracle.performanceofdatafiles"));
/* 5432 */                               out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n<div id=\"datafiles\" style=\"DISPLAY: block\"></div>\n\n<br>\n<div id=\"actionstatus4\" style=\"DISPLAY: none\" >");
/* 5433 */                               out.print(FormatUtil.getString("am.webclient.oracle.sessiondetails"));
/* 5434 */                               out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n<div id=\"sessiondetails\"></div>\n<br>\n<div id=\"actionstatus9\" style=\"DISPLAY: none\">");
/* 5435 */                               out.print(FormatUtil.getString("am.webclient.dotnet.sessions"));
/* 5436 */                               out.write("&nbsp;");
/* 5437 */                               out.print(FormatUtil.getString("Summary"));
/* 5438 */                               out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n<div id=\"sessionsummary\" style=\"DISPLAY: block\"></div>\n<br>\n<div id=\"actionstatus5\" style=\"DISPLAY: none>");
/* 5439 */                               out.print(FormatUtil.getString("am.webclient.oracle.sessionwaits"));
/* 5440 */                               out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n<div id=\"sessionwaits\" style=\"DISPLAY: block\"></div>\n<br>\n<div id=\"actionstatus6\"style=\"DISPLAY: none >");
/* 5441 */                               out.print(FormatUtil.getString("am.webclient.oracle.rollbacksegment"));
/* 5442 */                               out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n<div id=\"rollback\" style=\"DISPLAY: block\"></div>\n");
/*      */                               
/* 5444 */                               IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5445 */                               _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 5446 */                               _jspx_th_c_005fif_005f10.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                               
/* 5448 */                               _jspx_th_c_005fif_005f10.setTest("${!empty lockandwaits}");
/* 5449 */                               int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 5450 */                               if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                 for (;;) {
/* 5452 */                                   out.write("\n<div id=\"actionstatus10\" style=\"DISPLAY: none\">");
/* 5453 */                                   out.print(FormatUtil.getString("am.webclient.oracle.locks"));
/* 5454 */                                   out.write("&nbsp;&nbsp;<img src=\"../images/icon_cogwheel.gif\"></div>\n<div id=\"lockdetails\"></div>\n<br>\n");
/* 5455 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 5456 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 5460 */                               if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 5461 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                               }
/*      */                               
/* 5464 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 5465 */                               out.write(10);
/* 5466 */                               if (_jspx_meth_c_005fif_005f11(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 5468 */                               out.write(10);
/* 5469 */                               if (_jspx_meth_c_005fif_005f12(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                 return;
/* 5471 */                               out.write(10);
/* 5472 */                               int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 5473 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 5476 */                             if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 5477 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 5480 */                           if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 5481 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                           }
/*      */                           
/* 5484 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 5485 */                           out.write(32);
/* 5486 */                           if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 5488 */                           out.write(32);
/* 5489 */                           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 5490 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 5494 */                       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 5495 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0); return;
/*      */                       }
/*      */                       
/* 5498 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 5499 */                       out.write(10);
/*      */ 
/*      */                     }
/*      */                     catch (Exception exc)
/*      */                     {
/*      */ 
/* 5505 */                       exc.printStackTrace();
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/* 5510 */                     out.write(10);
/*      */                   }
/* 5512 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5513 */         out = _jspx_out;
/* 5514 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5515 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 5516 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5519 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5525 */     PageContext pageContext = _jspx_page_context;
/* 5526 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5528 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 5529 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5530 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 5532 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 5534 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 5535 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5536 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5537 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5538 */       return true;
/*      */     }
/* 5540 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5541 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5546 */     PageContext pageContext = _jspx_page_context;
/* 5547 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5549 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 5550 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 5551 */     _jspx_th_c_005fcatch_005f0.setParent(null);
/*      */     
/* 5553 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 5554 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 5556 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 5557 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 5559 */           out.write(32);
/* 5560 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 5561 */             return true;
/* 5562 */           out.write(10);
/* 5563 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 5564 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5568 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 5569 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5572 */         int tmp177_176 = 0; int[] tmp177_174 = _jspx_push_body_count_c_005fcatch_005f0; int tmp179_178 = tmp177_174[tmp177_176];tmp177_174[tmp177_176] = (tmp179_178 - 1); if (tmp179_178 <= 0) break;
/* 5573 */         out = _jspx_page_context.popBody(); }
/* 5574 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 5576 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 5577 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 5579 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 5584 */     PageContext pageContext = _jspx_page_context;
/* 5585 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5587 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 5588 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 5589 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 5591 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 5593 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 5594 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 5595 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 5596 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 5597 */       return true;
/*      */     }
/* 5599 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 5600 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5605 */     PageContext pageContext = _jspx_page_context;
/* 5606 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5608 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5609 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 5610 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5612 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 5614 */     _jspx_th_tiles_005fput_005f0.setValue("Oracle Server Details");
/* 5615 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 5616 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 5617 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5618 */       return true;
/*      */     }
/* 5620 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5621 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5626 */     PageContext pageContext = _jspx_page_context;
/* 5627 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5629 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5630 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 5631 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5633 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 5634 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 5635 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 5637 */         out.write(32);
/* 5638 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 5639 */           return true;
/* 5640 */         out.write(10);
/* 5641 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 5642 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5646 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 5647 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5648 */       return true;
/*      */     }
/* 5650 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5651 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5656 */     PageContext pageContext = _jspx_page_context;
/* 5657 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5659 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5660 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 5661 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 5663 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 5665 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 5666 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 5667 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 5668 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5669 */       return true;
/*      */     }
/* 5671 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5672 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5677 */     PageContext pageContext = _jspx_page_context;
/* 5678 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5680 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5681 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 5682 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5684 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 5685 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 5686 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 5688 */         out.write(10);
/* 5689 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 5690 */           return true;
/* 5691 */         out.write(32);
/* 5692 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 5693 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5697 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 5698 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5699 */       return true;
/*      */     }
/* 5701 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5702 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5707 */     PageContext pageContext = _jspx_page_context;
/* 5708 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5710 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5711 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 5712 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 5714 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 5716 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 5717 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 5718 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 5719 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 5720 */       return true;
/*      */     }
/* 5722 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 5723 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5728 */     PageContext pageContext = _jspx_page_context;
/* 5729 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5731 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5732 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 5733 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5735 */     _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */     
/* 5737 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/OracleLeftPage.jsp");
/* 5738 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 5739 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 5740 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 5741 */       return true;
/*      */     }
/* 5743 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 5744 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5749 */     PageContext pageContext = _jspx_page_context;
/* 5750 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5752 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 5753 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5754 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5756 */     _jspx_th_c_005fout_005f1.setValue("${selectedskin}");
/*      */     
/* 5758 */     _jspx_th_c_005fout_005f1.setDefault("${initParam.defaultSkin}");
/* 5759 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5760 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5761 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5762 */       return true;
/*      */     }
/* 5764 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5765 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5770 */     PageContext pageContext = _jspx_page_context;
/* 5771 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5773 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5774 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5775 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5777 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 5778 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5779 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5780 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5781 */       return true;
/*      */     }
/* 5783 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5784 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5789 */     PageContext pageContext = _jspx_page_context;
/* 5790 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5792 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5793 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5794 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5796 */     _jspx_th_c_005fout_005f3.setValue("${param.resourcename}");
/* 5797 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5798 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5799 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5800 */       return true;
/*      */     }
/* 5802 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5803 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5808 */     PageContext pageContext = _jspx_page_context;
/* 5809 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5811 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5812 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5813 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5815 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 5816 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5817 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5818 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5819 */       return true;
/*      */     }
/* 5821 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5822 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5827 */     PageContext pageContext = _jspx_page_context;
/* 5828 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5830 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5831 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5832 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5834 */     _jspx_th_c_005fout_005f5.setValue("${param.resourcename}");
/* 5835 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5836 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5837 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5838 */       return true;
/*      */     }
/* 5840 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5841 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5846 */     PageContext pageContext = _jspx_page_context;
/* 5847 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5849 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5850 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5851 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5853 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 5854 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5855 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5856 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5857 */       return true;
/*      */     }
/* 5859 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5860 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5865 */     PageContext pageContext = _jspx_page_context;
/* 5866 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5868 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5869 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5870 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 5872 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 5873 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5874 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5875 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5876 */       return true;
/*      */     }
/* 5878 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5879 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5884 */     PageContext pageContext = _jspx_page_context;
/* 5885 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5887 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5888 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 5889 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f6);
/* 5890 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 5891 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 5892 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 5893 */         out = _jspx_page_context.pushBody();
/* 5894 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 5895 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5898 */         out.write("table.heading.attribute");
/* 5899 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 5900 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5903 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 5904 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5907 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 5908 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5909 */       return true;
/*      */     }
/* 5911 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5912 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5917 */     PageContext pageContext = _jspx_page_context;
/* 5918 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5920 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5921 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 5922 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fif_005f6);
/* 5923 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 5924 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 5925 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 5926 */         out = _jspx_page_context.pushBody();
/* 5927 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 5928 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5931 */         out.write("table.heading.value");
/* 5932 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 5933 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5936 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 5937 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5940 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 5941 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 5942 */       return true;
/*      */     }
/* 5944 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 5945 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5950 */     PageContext pageContext = _jspx_page_context;
/* 5951 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5953 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5954 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 5955 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f6);
/* 5956 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 5957 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 5958 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 5959 */         out = _jspx_page_context.pushBody();
/* 5960 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 5961 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5964 */         out.write("table.heading.status");
/* 5965 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 5966 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5969 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 5970 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5973 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 5974 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 5975 */       return true;
/*      */     }
/* 5977 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 5978 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5983 */     PageContext pageContext = _jspx_page_context;
/* 5984 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5986 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5987 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5988 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 5990 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 5991 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5992 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5993 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5994 */       return true;
/*      */     }
/* 5996 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5997 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6002 */     PageContext pageContext = _jspx_page_context;
/* 6003 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6005 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6006 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 6007 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6009 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 6010 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 6011 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 6012 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6013 */       return true;
/*      */     }
/* 6015 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6016 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6021 */     PageContext pageContext = _jspx_page_context;
/* 6022 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6024 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6025 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 6026 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6028 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 6029 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 6030 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 6031 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6032 */       return true;
/*      */     }
/* 6034 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6035 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6040 */     PageContext pageContext = _jspx_page_context;
/* 6041 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6043 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6044 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 6045 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6047 */     _jspx_th_c_005fout_005f11.setValue("${param.resourceid}");
/* 6048 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 6049 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 6050 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6051 */       return true;
/*      */     }
/* 6053 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6054 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6059 */     PageContext pageContext = _jspx_page_context;
/* 6060 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6062 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6063 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 6064 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f7);
/* 6065 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 6066 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 6067 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 6068 */         out = _jspx_page_context.pushBody();
/* 6069 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 6070 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6073 */         out.write("table.heading.attribute");
/* 6074 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 6075 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6078 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 6079 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6082 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 6083 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 6084 */       return true;
/*      */     }
/* 6086 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 6087 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6092 */     PageContext pageContext = _jspx_page_context;
/* 6093 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6095 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6096 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 6097 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fif_005f7);
/* 6098 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 6099 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 6100 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 6101 */         out = _jspx_page_context.pushBody();
/* 6102 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 6103 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6106 */         out.write("table.heading.value");
/* 6107 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 6108 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6111 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 6112 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6115 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 6116 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6117 */       return true;
/*      */     }
/* 6119 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6120 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6125 */     PageContext pageContext = _jspx_page_context;
/* 6126 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6128 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6129 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 6130 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fif_005f7);
/* 6131 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 6132 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 6133 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 6134 */         out = _jspx_page_context.pushBody();
/* 6135 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 6136 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6139 */         out.write("table.heading.status");
/* 6140 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 6141 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6144 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 6145 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6148 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 6149 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6150 */       return true;
/*      */     }
/* 6152 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6153 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6158 */     PageContext pageContext = _jspx_page_context;
/* 6159 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6161 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6162 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 6163 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fif_005f7);
/* 6164 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 6165 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 6166 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 6167 */         out = _jspx_page_context.pushBody();
/* 6168 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 6169 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6172 */         out.write("table.heading.attribute");
/* 6173 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 6174 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6177 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 6178 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6181 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 6182 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 6183 */       return true;
/*      */     }
/* 6185 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 6186 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6191 */     PageContext pageContext = _jspx_page_context;
/* 6192 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6194 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6195 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 6196 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_c_005fif_005f7);
/* 6197 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 6198 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 6199 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 6200 */         out = _jspx_page_context.pushBody();
/* 6201 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 6202 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6205 */         out.write("table.heading.value");
/* 6206 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 6207 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6210 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 6211 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6214 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 6215 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 6216 */       return true;
/*      */     }
/* 6218 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 6219 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6224 */     PageContext pageContext = _jspx_page_context;
/* 6225 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6227 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6228 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 6229 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_c_005fif_005f7);
/* 6230 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 6231 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 6232 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 6233 */         out = _jspx_page_context.pushBody();
/* 6234 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 6235 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6238 */         out.write("table.heading.status");
/* 6239 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 6240 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6243 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 6244 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6247 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 6248 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 6249 */       return true;
/*      */     }
/* 6251 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 6252 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6257 */     PageContext pageContext = _jspx_page_context;
/* 6258 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6260 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 6261 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 6262 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6264 */     _jspx_th_c_005fset_005f0.setVar("free_bytes");
/*      */     
/* 6266 */     _jspx_th_c_005fset_005f0.setValue("${props.FREEBYTES}");
/* 6267 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 6268 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 6269 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 6270 */       return true;
/*      */     }
/* 6272 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 6273 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6278 */     PageContext pageContext = _jspx_page_context;
/* 6279 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6281 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 6282 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 6283 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6285 */     _jspx_th_c_005fset_005f1.setVar("tablespaceresourceid");
/*      */     
/* 6287 */     _jspx_th_c_005fset_005f1.setValue("${props.RESOURCEID}");
/* 6288 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 6289 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 6290 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 6291 */       return true;
/*      */     }
/* 6293 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 6294 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6299 */     PageContext pageContext = _jspx_page_context;
/* 6300 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6302 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6303 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 6304 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6306 */     _jspx_th_c_005fout_005f12.setValue("${props.TABLESPACENAME}");
/* 6307 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 6308 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 6309 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6310 */       return true;
/*      */     }
/* 6312 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6313 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6318 */     PageContext pageContext = _jspx_page_context;
/* 6319 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6321 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f0 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 6322 */     _jspx_th_fmt_005fformatNumber_005f0.setPageContext(_jspx_page_context);
/* 6323 */     _jspx_th_fmt_005fformatNumber_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6325 */     _jspx_th_fmt_005fformatNumber_005f0.setValue("${props.FREEBYTES}");
/* 6326 */     int _jspx_eval_fmt_005fformatNumber_005f0 = _jspx_th_fmt_005fformatNumber_005f0.doStartTag();
/* 6327 */     if (_jspx_th_fmt_005fformatNumber_005f0.doEndTag() == 5) {
/* 6328 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 6329 */       return true;
/*      */     }
/* 6331 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 6332 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6337 */     PageContext pageContext = _jspx_page_context;
/* 6338 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6340 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f1 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 6341 */     _jspx_th_fmt_005fformatNumber_005f1.setPageContext(_jspx_page_context);
/* 6342 */     _jspx_th_fmt_005fformatNumber_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6344 */     _jspx_th_fmt_005fformatNumber_005f1.setValue("${props.PERCENTAGEFREEBYTES}");
/* 6345 */     int _jspx_eval_fmt_005fformatNumber_005f1 = _jspx_th_fmt_005fformatNumber_005f1.doStartTag();
/* 6346 */     if (_jspx_th_fmt_005fformatNumber_005f1.doEndTag() == 5) {
/* 6347 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 6348 */       return true;
/*      */     }
/* 6350 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 6351 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6356 */     PageContext pageContext = _jspx_page_context;
/* 6357 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6359 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6360 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 6361 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_c_005fif_005f7);
/* 6362 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 6363 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 6364 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 6365 */         out = _jspx_page_context.pushBody();
/* 6366 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 6367 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6370 */         out.write("table.heading.attribute");
/* 6371 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 6372 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6375 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 6376 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6379 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 6380 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6381 */       return true;
/*      */     }
/* 6383 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6384 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6389 */     PageContext pageContext = _jspx_page_context;
/* 6390 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6392 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6393 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 6394 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_c_005fif_005f7);
/* 6395 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 6396 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 6397 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 6398 */         out = _jspx_page_context.pushBody();
/* 6399 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 6400 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6403 */         out.write("table.heading.value");
/* 6404 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 6405 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6408 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 6409 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6412 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 6413 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6414 */       return true;
/*      */     }
/* 6416 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6417 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6422 */     PageContext pageContext = _jspx_page_context;
/* 6423 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6425 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6426 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 6427 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_c_005fif_005f7);
/* 6428 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 6429 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 6430 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 6431 */         out = _jspx_page_context.pushBody();
/* 6432 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 6433 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6436 */         out.write("table.heading.attribute");
/* 6437 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 6438 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6441 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 6442 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6445 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 6446 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 6447 */       return true;
/*      */     }
/* 6449 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 6450 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6455 */     PageContext pageContext = _jspx_page_context;
/* 6456 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6458 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6459 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 6460 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_c_005fif_005f7);
/* 6461 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 6462 */     if (_jspx_eval_fmt_005fmessage_005f12 != 0) {
/* 6463 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 6464 */         out = _jspx_page_context.pushBody();
/* 6465 */         _jspx_th_fmt_005fmessage_005f12.setBodyContent((BodyContent)out);
/* 6466 */         _jspx_th_fmt_005fmessage_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6469 */         out.write("table.heading.value");
/* 6470 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f12.doAfterBody();
/* 6471 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6474 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 6475 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6478 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 6479 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 6480 */       return true;
/*      */     }
/* 6482 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 6483 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6488 */     PageContext pageContext = _jspx_page_context;
/* 6489 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6491 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6492 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 6493 */     _jspx_th_fmt_005fmessage_005f13.setParent((Tag)_jspx_th_c_005fif_005f7);
/* 6494 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 6495 */     if (_jspx_eval_fmt_005fmessage_005f13 != 0) {
/* 6496 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 6497 */         out = _jspx_page_context.pushBody();
/* 6498 */         _jspx_th_fmt_005fmessage_005f13.setBodyContent((BodyContent)out);
/* 6499 */         _jspx_th_fmt_005fmessage_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6502 */         out.write("table.heading.status");
/* 6503 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f13.doAfterBody();
/* 6504 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6507 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 6508 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6511 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 6512 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 6513 */       return true;
/*      */     }
/* 6515 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 6516 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6521 */     PageContext pageContext = _jspx_page_context;
/* 6522 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6524 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6525 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 6526 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6528 */     _jspx_th_c_005fout_005f13.setValue("${param.resourceid}");
/* 6529 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 6530 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 6531 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6532 */       return true;
/*      */     }
/* 6534 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6535 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6540 */     PageContext pageContext = _jspx_page_context;
/* 6541 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6543 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6544 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 6545 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6547 */     _jspx_th_c_005fout_005f14.setValue("${param.resourceid}");
/* 6548 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 6549 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 6550 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6551 */       return true;
/*      */     }
/* 6553 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6554 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6559 */     PageContext pageContext = _jspx_page_context;
/* 6560 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6562 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6563 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 6564 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6566 */     _jspx_th_c_005fif_005f11.setTest("${!empty buffergets}");
/* 6567 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 6568 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 6570 */         out.write("\n\n<div id=\"buffergets\"></div>\n<br>\n");
/* 6571 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 6572 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6576 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 6577 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 6578 */       return true;
/*      */     }
/* 6580 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 6581 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6586 */     PageContext pageContext = _jspx_page_context;
/* 6587 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6589 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6590 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 6591 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 6593 */     _jspx_th_c_005fif_005f12.setTest("${!empty diskreads}");
/* 6594 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 6595 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 6597 */         out.write("\n<div id=\"diskreads\"></div>\n");
/* 6598 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 6599 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6603 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 6604 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 6605 */       return true;
/*      */     }
/* 6607 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 6608 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6613 */     PageContext pageContext = _jspx_page_context;
/* 6614 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6616 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6617 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 6618 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6620 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 6622 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 6623 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 6624 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 6625 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 6626 */       return true;
/*      */     }
/* 6628 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 6629 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Oracle_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */