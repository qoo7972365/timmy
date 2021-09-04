/*       */ package org.apache.jsp.jsp;
/*       */ 
/*       */ import com.adventnet.appmanager.db.AMConnectionPool;
/*       */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*       */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*       */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*       */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*       */ import com.adventnet.appmanager.tags.HiddenParam;
/*       */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*       */ import com.adventnet.appmanager.util.FormatUtil;
/*       */ import com.adventnet.appmanager.util.OEMUtil;
/*       */ import java.io.File;
/*       */ import java.net.InetAddress;
/*       */ import java.sql.ResultSet;
/*       */ import java.util.ArrayList;
/*       */ import java.util.Enumeration;
/*       */ import java.util.HashMap;
/*       */ import java.util.Hashtable;
/*       */ import java.util.Iterator;
/*       */ import java.util.LinkedHashMap;
/*       */ import java.util.List;
/*       */ import java.util.Map;
/*       */ import java.util.Properties;
/*       */ import java.util.StringTokenizer;
/*       */ import java.util.Vector;
/*       */ import javax.servlet.http.HttpServletRequest;
/*       */ import javax.servlet.http.HttpSession;
/*       */ import javax.servlet.jsp.JspFactory;
/*       */ import javax.servlet.jsp.JspWriter;
/*       */ import javax.servlet.jsp.PageContext;
/*       */ import javax.servlet.jsp.tagext.BodyContent;
/*       */ import javax.servlet.jsp.tagext.JspTag;
/*       */ import javax.servlet.jsp.tagext.Tag;
/*       */ import javax.swing.tree.DefaultMutableTreeNode;
/*       */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*       */ import org.apache.jasper.runtime.TagHandlerPool;
/*       */ import org.apache.struts.taglib.bean.DefineTag;
/*       */ import org.apache.struts.taglib.html.CheckboxTag;
/*       */ import org.apache.struts.taglib.html.FormTag;
/*       */ import org.apache.struts.taglib.html.HiddenTag;
/*       */ import org.apache.struts.taglib.html.MultiboxTag;
/*       */ import org.apache.struts.taglib.html.OptionTag;
/*       */ import org.apache.struts.taglib.html.PasswordTag;
/*       */ import org.apache.struts.taglib.html.RadioTag;
/*       */ import org.apache.struts.taglib.html.ResetTag;
/*       */ import org.apache.struts.taglib.html.SelectTag;
/*       */ import org.apache.struts.taglib.html.TextTag;
/*       */ import org.apache.struts.taglib.html.TextareaTag;
/*       */ import org.apache.struts.taglib.logic.IterateTag;
/*       */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*       */ import org.apache.struts.taglib.logic.NotPresentTag;
/*       */ import org.apache.struts.taglib.logic.PresentTag;
/*       */ import org.apache.struts.taglib.tiles.InsertTag;
/*       */ import org.apache.struts.taglib.tiles.PutTag;
/*       */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*       */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*       */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*       */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*       */ 
/*       */ public final class HostDiscoveryForm_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*       */ {
/*       */   public static final String NAME_SEPARATOR = ">";
/*    63 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*       */   public static final String STATUS_SEPARATOR = "#";
/*       */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*    66 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*    67 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*    68 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*       */   public static final String MISTR = "Monitor";
/*       */   public static final String MISTRs = "Monitors";
/*       */   public static final String RCA_SEPARATOR = " --> ";
/*       */   
/*       */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*       */   {
/*    75 */     return getOptions(value, text, tableName, distinct, "");
/*       */   }
/*       */   
/*       */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*       */   {
/*    80 */     ArrayList list = null;
/*    81 */     StringBuffer sbf = new StringBuffer();
/*    82 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
/*    83 */     if (distinct)
/*       */     {
/*    85 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*       */     }
/*       */     else
/*       */     {
/*    89 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*       */     }
/*       */     
/*    92 */     for (int i = 0; i < list.size(); i++)
/*       */     {
/*    94 */       ArrayList row = (ArrayList)list.get(i);
/*    95 */       sbf.append("<option value='" + row.get(0) + "'>");
/*    96 */       if (distinct) {
/*    97 */         sbf.append(row.get(0));
/*       */       } else
/*    99 */         sbf.append(row.get(1));
/*   100 */       sbf.append("</option>");
/*       */     }
/*       */     
/*   103 */     return sbf.toString(); }
/*       */   
/*   105 */   long j = 0L;
/*       */   
/*       */   private String getSeverityImageForAvailability(Object severity) {
/*   108 */     if (severity == null)
/*       */     {
/*   110 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*       */     }
/*   112 */     if (severity.equals("5"))
/*       */     {
/*   114 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*       */     }
/*   116 */     if (severity.equals("1"))
/*       */     {
/*   118 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*   123 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   private String getSeverityImage(Object severity)
/*       */   {
/*   130 */     if (severity == null)
/*       */     {
/*   132 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*       */     }
/*   134 */     if (severity.equals("1"))
/*       */     {
/*   136 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*       */     }
/*   138 */     if (severity.equals("4"))
/*       */     {
/*   140 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*       */     }
/*   142 */     if (severity.equals("5"))
/*       */     {
/*   144 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*   149 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*       */   }
/*       */   
/*       */ 
/*       */   private String getSeverityStateForAvailability(Object severity)
/*       */   {
/*   155 */     if (severity == null)
/*       */     {
/*   157 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*       */     }
/*   159 */     if (severity.equals("5"))
/*       */     {
/*   161 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*       */     }
/*   163 */     if (severity.equals("1"))
/*       */     {
/*   165 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*       */     }
/*       */     
/*       */ 
/*   169 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*       */   }
/*       */   
/*       */ 
/*       */   private String getSeverityState(Object severity)
/*       */   {
/*   175 */     if (severity == null)
/*       */     {
/*   177 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*       */     }
/*   179 */     if (severity.equals("1"))
/*       */     {
/*   181 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*       */     }
/*   183 */     if (severity.equals("4"))
/*       */     {
/*   185 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*       */     }
/*   187 */     if (severity.equals("5"))
/*       */     {
/*   189 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*       */     }
/*       */     
/*       */ 
/*   193 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*       */   }
/*       */   
/*       */ 
/*       */   private String getSeverityImage(int severity)
/*       */   {
/*   199 */     return getSeverityImage("" + severity);
/*       */   }
/*       */   
/*       */ 
/*       */   private String getSeverityImageForAvailability(int severity)
/*       */   {
/*   205 */     if (severity == 5)
/*       */     {
/*   207 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*       */     }
/*   209 */     if (severity == 1)
/*       */     {
/*   211 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*   216 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*       */   }
/*       */   
/*       */ 
/*       */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*       */   {
/*   222 */     if (severity == null)
/*       */     {
/*   224 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*       */     }
/*   226 */     if (severity.equals("5"))
/*       */     {
/*   228 */       if (isAvailability) {
/*   229 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*       */       }
/*       */       
/*   232 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*       */     }
/*       */     
/*   235 */     if ((severity.equals("4")) && (!isAvailability))
/*       */     {
/*   237 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*       */     }
/*   239 */     if (severity.equals("1"))
/*       */     {
/*   241 */       if (isAvailability) {
/*   242 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*       */       }
/*       */       
/*   245 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   252 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   private String getSeverityImageForHealth(String severity)
/*       */   {
/*   259 */     if (severity == null)
/*       */     {
/*   261 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*       */     }
/*   263 */     if (severity.equals("5"))
/*       */     {
/*   265 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*       */     }
/*   267 */     if (severity.equals("4"))
/*       */     {
/*   269 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*       */     }
/*   271 */     if (severity.equals("1"))
/*       */     {
/*   273 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*   278 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*       */   }
/*       */   
/*       */ 
/*       */   private String getas400SeverityImageForHealth(String severity)
/*       */   {
/*   284 */     if (severity == null)
/*       */     {
/*   286 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*       */     }
/*   288 */     if (severity.equals("5"))
/*       */     {
/*   290 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*       */     }
/*   292 */     if (severity.equals("4"))
/*       */     {
/*   294 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*       */     }
/*   296 */     if (severity.equals("1"))
/*       */     {
/*   298 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*   303 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*       */   {
/*   310 */     if (severity == null)
/*       */     {
/*   312 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*       */     }
/*   314 */     if (severity.equals("5"))
/*       */     {
/*   316 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*       */     }
/*   318 */     if (severity.equals("4"))
/*       */     {
/*   320 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*       */     }
/*   322 */     if (severity.equals("1"))
/*       */     {
/*   324 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*   329 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   private String getSearchStrip(String map)
/*       */   {
/*   337 */     StringBuffer out = new StringBuffer();
/*   338 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*   339 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*   340 */     out.append("<tr class=\"breadcrumbs\"> ");
/*   341 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*   342 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*   343 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*   344 */     out.append("</tr>");
/*   345 */     out.append("</form></table>");
/*   346 */     return out.toString();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   private String formatNumberForDotNet(String val)
/*       */   {
/*   353 */     if (val == null)
/*       */     {
/*   355 */       return "-";
/*       */     }
/*       */     
/*   358 */     String ret = FormatUtil.formatNumber(val);
/*   359 */     String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*   360 */     if (ret.indexOf("-1") != -1)
/*       */     {
/*       */ 
/*   363 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*       */     }
/*       */     
/*       */ 
/*   367 */     return ret;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*       */   {
/*   375 */     StringBuffer out = new StringBuffer();
/*   376 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*   377 */     out.append("<tr>");
/*   378 */     for (int i = 0; i < headers.length; i++)
/*       */     {
/*   380 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*       */     }
/*   382 */     out.append("</tr>");
/*   383 */     for (int j = 0; j < tableData.size(); j++)
/*       */     {
/*       */ 
/*       */ 
/*   387 */       if (j % 2 == 0)
/*       */       {
/*   389 */         out.append("<tr class=\"whitegrayborder\">");
/*       */       }
/*       */       else
/*       */       {
/*   393 */         out.append("<tr class=\"yellowgrayborder\">");
/*       */       }
/*       */       
/*   396 */       List rowVector = (List)tableData.get(j);
/*       */       
/*   398 */       for (int k = 0; k < rowVector.size(); k++)
/*       */       {
/*       */ 
/*   401 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*       */       }
/*       */       
/*       */ 
/*   405 */       out.append("</tr>");
/*       */     }
/*   407 */     out.append("</table>");
/*   408 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*   409 */     out.append("<tr>");
/*   410 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*   411 */     out.append("</tr>");
/*   412 */     out.append("</table>");
/*   413 */     return out.toString();
/*       */   }
/*       */   
/*       */ 
/*       */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*       */   {
/*   419 */     StringBuffer out = new StringBuffer();
/*   420 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*   421 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*   422 */     out.append("<tr>");
/*   423 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*   424 */     out.append("<tr>");
/*   425 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*   426 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*   427 */     out.append("</tr>");
/*   428 */     for (int k = 0; k < tableColumns.size(); k++)
/*       */     {
/*       */ 
/*   431 */       out.append("<tr>");
/*   432 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*   433 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*   434 */       out.append("</tr>");
/*       */     }
/*       */     
/*   437 */     out.append("</table>");
/*   438 */     out.append("</table>");
/*   439 */     return out.toString();
/*       */   }
/*       */   
/*       */   private String getAvailabilityImage(String severity)
/*       */   {
/*   444 */     if (severity.equals("0"))
/*       */     {
/*   446 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*       */     }
/*       */     
/*       */ 
/*   450 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*       */   {
/*   457 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session)
/*       */   {
/*   470 */     StringBuffer out = new StringBuffer();
/*   471 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*   472 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*       */     {
/*   474 */       out.append("<tr>");
/*   475 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*   476 */       out.append("</tr>");
/*       */       
/*       */ 
/*   479 */       for (int i = 0; i < quickLinkText.size(); i++)
/*       */       {
/*   481 */         String borderclass = "";
/*       */         
/*       */ 
/*   484 */         borderclass = "class=\"leftlinkstd\"";
/*       */         
/*   486 */         out.append("<tr>");
/*       */         
/*   488 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*   489 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*   490 */         out.append("</tr>");
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*   496 */     out.append("</table><br>");
/*   497 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*   498 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*       */     {
/*   500 */       List sLinks = secondLevelOfLinks[0];
/*   501 */       List sText = secondLevelOfLinks[1];
/*   502 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*       */       {
/*       */ 
/*   505 */         out.append("<tr>");
/*   506 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*   507 */         out.append("</tr>");
/*   508 */         for (int i = 0; i < sText.size(); i++)
/*       */         {
/*   510 */           String borderclass = "";
/*       */           
/*       */ 
/*   513 */           borderclass = "class=\"leftlinkstd\"";
/*       */           
/*   515 */           out.append("<tr>");
/*       */           
/*   517 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*   518 */           if (sLinks.get(i).toString().length() == 0) {
/*   519 */             out.append((String)sText.get(i) + "</td>");
/*       */           }
/*       */           else {
/*   522 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*       */           }
/*   524 */           out.append("</tr>");
/*       */         }
/*       */       }
/*       */     }
/*   528 */     out.append("</table>");
/*   529 */     return out.toString();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*       */   {
/*   536 */     StringBuffer out = new StringBuffer();
/*   537 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*   538 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*       */     {
/*   540 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*       */       {
/*   542 */         out.append("<tr>");
/*   543 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*   544 */         out.append("</tr>");
/*       */         
/*       */ 
/*       */ 
/*   548 */         for (int i = 0; i < quickLinkText.size(); i++)
/*       */         {
/*   550 */           String borderclass = "";
/*       */           
/*       */ 
/*   553 */           borderclass = "class=\"leftlinkstd\"";
/*       */           
/*   555 */           out.append("<tr>");
/*       */           
/*   557 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*   558 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*   559 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*       */           }
/*       */           else {
/*   562 */             out.append((String)quickLinkText.get(i));
/*       */           }
/*       */           
/*   565 */           out.append("</td></tr>");
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*   570 */     out.append("</table><br>");
/*   571 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*   572 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*       */     {
/*   574 */       List sLinks = secondLevelOfLinks[0];
/*   575 */       List sText = secondLevelOfLinks[1];
/*   576 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*       */       {
/*       */ 
/*   579 */         out.append("<tr>");
/*   580 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*   581 */         out.append("</tr>");
/*   582 */         for (int i = 0; i < sText.size(); i++)
/*       */         {
/*   584 */           String borderclass = "";
/*       */           
/*       */ 
/*   587 */           borderclass = "class=\"leftlinkstd\"";
/*       */           
/*   589 */           out.append("<tr>");
/*       */           
/*   591 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*   592 */           if (sLinks.get(i).toString().length() == 0) {
/*   593 */             out.append((String)sText.get(i) + "</td>");
/*       */           }
/*       */           else {
/*   596 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*       */           }
/*   598 */           out.append("</tr>");
/*       */         }
/*       */       }
/*       */     }
/*   602 */     out.append("</table>");
/*   603 */     return out.toString();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private String getSeverityClass(int status)
/*       */   {
/*   616 */     switch (status)
/*       */     {
/*       */     case 1: 
/*   619 */       return "class=\"errorgrayborder\"";
/*       */     
/*       */     case 2: 
/*   622 */       return "class=\"errorgrayborder\"";
/*       */     
/*       */     case 3: 
/*   625 */       return "class=\"errorgrayborder\"";
/*       */     
/*       */     case 4: 
/*   628 */       return "class=\"errorgrayborder\"";
/*       */     
/*       */     case 5: 
/*   631 */       return "class=\"whitegrayborder\"";
/*       */     
/*       */     case 6: 
/*   634 */       return "class=\"whitegrayborder\"";
/*       */     }
/*       */     
/*   637 */     return "class=\"whitegrayborder\"";
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*       */   {
/*   645 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*       */   }
/*       */   
/*       */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*       */   {
/*   650 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*       */   }
/*       */   
/*       */   private String getTruncatedAlertMessage(String messageArg)
/*       */   {
/*   655 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*       */   }
/*       */   
/*       */   private String formatDT(String val)
/*       */   {
/*   660 */     return FormatUtil.formatDT(val);
/*       */   }
/*       */   
/*       */   private String formatDT(Long val)
/*       */   {
/*   665 */     if (val != null)
/*       */     {
/*   667 */       return FormatUtil.formatDT(val.toString());
/*       */     }
/*       */     
/*       */ 
/*   671 */     return "-";
/*       */   }
/*       */   
/*       */   private String formatDTwithOutYear(String val)
/*       */   {
/*   676 */     if (val == null) {
/*   677 */       return val;
/*       */     }
/*       */     try
/*       */     {
/*   681 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*       */     }
/*       */     catch (Exception e) {}
/*       */     
/*       */ 
/*   686 */     return val;
/*       */   }
/*       */   
/*       */ 
/*       */   private String formatDTwithOutYear(Long val)
/*       */   {
/*   692 */     if (val != null)
/*       */     {
/*   694 */       return formatDTwithOutYear(val.toString());
/*       */     }
/*       */     
/*       */ 
/*   698 */     return "-";
/*       */   }
/*       */   
/*       */ 
/*       */   private String formatAlertDT(String val)
/*       */   {
/*   704 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*       */   }
/*       */   
/*       */   private String formatNumber(Object val)
/*       */   {
/*   709 */     return FormatUtil.formatNumber(val);
/*       */   }
/*       */   
/*       */   private String formatNumber(long val) {
/*   713 */     return FormatUtil.formatNumber(val);
/*       */   }
/*       */   
/*       */   private String formatBytesToKB(String val)
/*       */   {
/*   718 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*       */   }
/*       */   
/*       */   private String formatBytesToMB(String val)
/*       */   {
/*   723 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*       */   }
/*       */   
/*       */   private String getHostAddress(HttpServletRequest request) throws Exception
/*       */   {
/*   728 */     String hostaddress = "";
/*   729 */     String ip = request.getHeader("x-forwarded-for");
/*   730 */     if (ip == null)
/*   731 */       ip = request.getRemoteAddr();
/*   732 */     InetAddress add = null;
/*   733 */     if (ip.equals("127.0.0.1")) {
/*   734 */       add = InetAddress.getLocalHost();
/*       */     }
/*       */     else
/*       */     {
/*   738 */       add = InetAddress.getByName(ip);
/*       */     }
/*   740 */     hostaddress = add.getHostName();
/*   741 */     if (hostaddress.indexOf('.') != -1) {
/*   742 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*   743 */       hostaddress = st.nextToken();
/*       */     }
/*       */     
/*       */ 
/*   747 */     return hostaddress;
/*       */   }
/*       */   
/*       */   private String removeBr(String arg)
/*       */   {
/*   752 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*       */   }
/*       */   
/*       */ 
/*       */   private String getSeverityImageForMssqlAvailability(Object severity)
/*       */   {
/*   758 */     if (severity == null)
/*       */     {
/*   760 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*       */     }
/*   762 */     if (severity.equals("5"))
/*       */     {
/*   764 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*       */     }
/*   766 */     if (severity.equals("1"))
/*       */     {
/*   768 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*   773 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*       */   }
/*       */   
/*       */   public String getDependentChildAttribs(String resid, String attributeId)
/*       */   {
/*   778 */     ResultSet set = null;
/*   779 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*   780 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*       */     try {
/*   782 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*   783 */       if (set.next()) { String str1;
/*   784 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*   785 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*       */         }
/*       */         
/*   788 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*       */       }
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*   793 */       e.printStackTrace();
/*       */     }
/*       */     finally {
/*   796 */       AMConnectionPool.closeStatement(set);
/*       */     }
/*   798 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*       */   }
/*       */   
/*       */   public String getConfHealthRCA(String key) {
/*   802 */     StringBuffer rca = new StringBuffer();
/*   803 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*   804 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*       */     }
/*       */     
/*   807 */     int rcalength = key.length();
/*   808 */     String split = "6. ";
/*   809 */     int splitPresent = key.indexOf(split);
/*   810 */     String div1 = "";String div2 = "";
/*   811 */     if ((rcalength < 300) || (splitPresent < 0))
/*       */     {
/*   813 */       if (rcalength > 180) {
/*   814 */         rca.append("<span class=\"rca-critical-text\">");
/*   815 */         getRCATrimmedText(key, rca);
/*   816 */         rca.append("</span>");
/*       */       } else {
/*   818 */         rca.append("<span class=\"rca-critical-text\">");
/*   819 */         rca.append(key);
/*   820 */         rca.append("</span>");
/*       */       }
/*   822 */       return rca.toString();
/*       */     }
/*   824 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*   825 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*   826 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*   827 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*   828 */     getRCATrimmedText(div1, rca);
/*   829 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*       */     
/*       */ 
/*   832 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*   833 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*   834 */     getRCATrimmedText(div2, rca);
/*   835 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*       */     
/*   837 */     return rca.toString();
/*       */   }
/*       */   
/*       */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*       */   {
/*   842 */     String[] st = msg.split("<br>");
/*   843 */     for (int i = 0; i < st.length; i++) {
/*   844 */       String s = st[i];
/*   845 */       if (s.length() > 180) {
/*   846 */         s = s.substring(0, 175) + ".....";
/*       */       }
/*   848 */       rca.append(s + "<br>");
/*       */     }
/*       */   }
/*       */   
/*   852 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*   853 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*       */     }
/*   855 */     return "";
/*       */   }
/*       */   
/*       */   public String getHelpLink(String key) {
/*   859 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*   860 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*   861 */     ResultSet set = null;
/*       */     try {
/*       */       String str1;
/*   864 */       if (key == null) {
/*   865 */         return ret;
/*       */       }
/*       */       
/*   868 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*   869 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*       */       }
/*       */       
/*   872 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*   873 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*   874 */       set = AMConnectionPool.executeQueryStmt(query);
/*   875 */       if (set.next())
/*       */       {
/*   877 */         String helpLink = set.getString("LINK");
/*   878 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*       */         try
/*       */         {
/*   881 */           AMConnectionPool.closeStatement(set);
/*       */         }
/*       */         catch (Exception exc) {}
/*       */         
/*       */ 
/*       */ 
/*   887 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   906 */       return ret;
/*       */     }
/*       */     catch (Exception ex) {}finally
/*       */     {
/*       */       try
/*       */       {
/*   897 */         if (set != null) {
/*   898 */           AMConnectionPool.closeStatement(set);
/*       */         }
/*       */       }
/*       */       catch (Exception nullexc) {}
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Properties getStatus(List entitylist)
/*       */   {
/*   912 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*   913 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*       */     {
/*   915 */       String entityStr = (String)keys.nextElement();
/*   916 */       String mmessage = temp.getProperty(entityStr);
/*   917 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*   918 */       temp.setProperty(entityStr, mmessage);
/*       */     }
/*   920 */     return temp;
/*       */   }
/*       */   
/*       */ 
/*       */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*       */   {
/*   926 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*   927 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*       */     {
/*   929 */       String entityStr = (String)keys.nextElement();
/*   930 */       String mmessage = temp.getProperty(entityStr);
/*   931 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*   932 */       temp.setProperty(entityStr, mmessage);
/*       */     }
/*   934 */     return temp;
/*       */   }
/*       */   
/*       */   private void debug(String debugMessage)
/*       */   {
/*   939 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private String findReplace(String str, String find, String replace)
/*       */   {
/*   949 */     String des = new String();
/*   950 */     while (str.indexOf(find) != -1) {
/*   951 */       des = des + str.substring(0, str.indexOf(find));
/*   952 */       des = des + replace;
/*   953 */       str = str.substring(str.indexOf(find) + find.length());
/*       */     }
/*   955 */     des = des + str;
/*   956 */     return des;
/*       */   }
/*       */   
/*       */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*       */   {
/*       */     try
/*       */     {
/*   963 */       if (alert == null)
/*       */       {
/*   965 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*       */       }
/*   967 */       if ((test == null) || (test.equals("")))
/*       */       {
/*   969 */         return "&nbsp;";
/*       */       }
/*       */       
/*   972 */       if ((alert != null) && (alert.equals("5")))
/*       */       {
/*   974 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*       */       }
/*       */       
/*   977 */       int rcalength = test.length();
/*   978 */       if (rcalength < 300)
/*       */       {
/*   980 */         return test;
/*       */       }
/*       */       
/*       */ 
/*   984 */       StringBuffer out = new StringBuffer();
/*   985 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*   986 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*   987 */       out.append("</div>");
/*   988 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*   989 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*   990 */       return out.toString();
/*       */ 
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*   995 */       ex.printStackTrace();
/*       */     }
/*   997 */     return test;
/*       */   }
/*       */   
/*       */ 
/*       */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*       */   {
/*  1003 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*       */   }
/*       */   
/*       */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*       */   {
/*  1008 */     ArrayList attribIDs = new ArrayList();
/*  1009 */     ArrayList resIDs = new ArrayList();
/*  1010 */     ArrayList entitylist = new ArrayList();
/*       */     
/*  1012 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*       */     {
/*  1014 */       ArrayList row = (ArrayList)monitorList.get(j);
/*       */       
/*  1016 */       String resourceid = "";
/*  1017 */       String resourceType = "";
/*  1018 */       if (type == 2) {
/*  1019 */         resourceid = (String)row.get(0);
/*  1020 */         resourceType = (String)row.get(3);
/*       */       }
/*  1022 */       else if (type == 3) {
/*  1023 */         resourceid = (String)row.get(0);
/*  1024 */         resourceType = "EC2Instance";
/*       */       }
/*       */       else {
/*  1027 */         resourceid = (String)row.get(6);
/*  1028 */         resourceType = (String)row.get(7);
/*       */       }
/*  1030 */       resIDs.add(resourceid);
/*  1031 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/*  1032 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*       */       
/*  1034 */       String healthentity = null;
/*  1035 */       String availentity = null;
/*  1036 */       if (healthid != null) {
/*  1037 */         healthentity = resourceid + "_" + healthid;
/*  1038 */         entitylist.add(healthentity);
/*       */       }
/*       */       
/*  1041 */       if (availid != null) {
/*  1042 */         availentity = resourceid + "_" + availid;
/*  1043 */         entitylist.add(availentity);
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1057 */     Properties alert = getStatus(entitylist);
/*  1058 */     return alert;
/*       */   }
/*       */   
/*       */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*       */   {
/*  1063 */     int size = monitorList.size();
/*       */     
/*  1065 */     String[] severity = new String[size];
/*       */     
/*  1067 */     for (int j = 0; j < monitorList.size(); j++)
/*       */     {
/*  1069 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/*  1070 */       String resourceName1 = (String)row1.get(7);
/*  1071 */       String resourceid1 = (String)row1.get(6);
/*  1072 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/*  1073 */       if (severity[j] == null)
/*       */       {
/*  1075 */         severity[j] = "6";
/*       */       }
/*       */     }
/*       */     
/*  1079 */     for (j = 0; j < severity.length; j++)
/*       */     {
/*  1081 */       for (int k = j + 1; k < severity.length; k++)
/*       */       {
/*  1083 */         int sev = severity[j].compareTo(severity[k]);
/*       */         
/*       */ 
/*  1086 */         if (sev > 0) {
/*  1087 */           ArrayList t = (ArrayList)monitorList.get(k);
/*  1088 */           monitorList.set(k, monitorList.get(j));
/*  1089 */           monitorList.set(j, t);
/*  1090 */           String temp = severity[k];
/*  1091 */           severity[k] = severity[j];
/*  1092 */           severity[j] = temp;
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  1098 */     int z = 0;
/*  1099 */     for (j = 0; j < monitorList.size(); j++)
/*       */     {
/*       */ 
/*  1102 */       int i = 0;
/*  1103 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*       */       {
/*       */ 
/*  1106 */         i++;
/*       */       }
/*       */       else
/*       */       {
/*  1110 */         z++;
/*       */       }
/*       */     }
/*       */     
/*  1114 */     String[] hseverity = new String[monitorList.size()];
/*       */     
/*  1116 */     for (j = 0; j < z; j++)
/*       */     {
/*       */ 
/*  1119 */       hseverity[j] = severity[j];
/*       */     }
/*       */     
/*       */ 
/*  1123 */     for (j = z; j < severity.length; j++)
/*       */     {
/*       */ 
/*  1126 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/*  1127 */       String resourceName1 = (String)row1.get(7);
/*  1128 */       String resourceid1 = (String)row1.get(6);
/*  1129 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/*  1130 */       if (hseverity[j] == null)
/*       */       {
/*  1132 */         hseverity[j] = "6";
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  1137 */     for (j = 0; j < hseverity.length; j++)
/*       */     {
/*  1139 */       for (int k = j + 1; k < hseverity.length; k++)
/*       */       {
/*       */ 
/*  1142 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*       */         
/*       */ 
/*  1145 */         if (hsev > 0) {
/*  1146 */           ArrayList t = (ArrayList)monitorList.get(k);
/*  1147 */           monitorList.set(k, monitorList.get(j));
/*  1148 */           monitorList.set(j, t);
/*  1149 */           String temp1 = hseverity[k];
/*  1150 */           hseverity[k] = hseverity[j];
/*  1151 */           hseverity[j] = temp1;
/*       */         }
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String getAllChildNodestoDisplay(ArrayList singlechilmos, String resIdTOCheck, String currentresourceidtree, Hashtable childmos, Hashtable availhealth, int level, HttpServletRequest request, HashMap extDeviceMap, HashMap site24x7List)
/*       */   {
/*  1163 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/*  1164 */     boolean forInventory = false;
/*  1165 */     String trdisplay = "none";
/*  1166 */     String plusstyle = "inline";
/*  1167 */     String minusstyle = "none";
/*  1168 */     String haidTopLevel = "";
/*  1169 */     if (request.getAttribute("forInventory") != null)
/*       */     {
/*  1171 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*       */       {
/*  1173 */         haidTopLevel = request.getParameter("haid");
/*  1174 */         forInventory = true;
/*  1175 */         trdisplay = "table-row;";
/*  1176 */         plusstyle = "none";
/*  1177 */         minusstyle = "inline";
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     else
/*       */     {
/*  1184 */       haidTopLevel = resIdTOCheck;
/*       */     }
/*       */     
/*  1187 */     ArrayList listtoreturn = new ArrayList();
/*  1188 */     StringBuffer toreturn = new StringBuffer();
/*  1189 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/*  1190 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/*  1191 */     Properties alert = (Properties)availhealth.get("alert");
/*       */     
/*  1193 */     for (int j = 0; j < singlechilmos.size(); j++)
/*       */     {
/*  1195 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/*  1196 */       String childresid = (String)singlerow.get(0);
/*  1197 */       String childresname = (String)singlerow.get(1);
/*  1198 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/*  1199 */       String childtype = ((String)singlerow.get(2) + "").trim();
/*  1200 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/*  1201 */       String shortname = ((String)singlerow.get(4) + "").trim();
/*  1202 */       String unmanagestatus = (String)singlerow.get(5);
/*  1203 */       String actionstatus = (String)singlerow.get(6);
/*  1204 */       String linkclass = "monitorgp-links";
/*  1205 */       String titleforres = childresname;
/*  1206 */       String titilechildresname = childresname;
/*  1207 */       String childimg = "/images/trcont.png";
/*  1208 */       String flag = "enable";
/*  1209 */       String dcstarted = (String)singlerow.get(8);
/*  1210 */       String configMonitor = "";
/*  1211 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/*  1212 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*       */       {
/*  1214 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*       */       }
/*  1216 */       if (singlerow.get(7) != null)
/*       */       {
/*  1218 */         flag = (String)singlerow.get(7);
/*       */       }
/*  1220 */       String haiGroupType = "0";
/*  1221 */       if ("HAI".equals(childtype))
/*       */       {
/*  1223 */         haiGroupType = (String)singlerow.get(9);
/*       */       }
/*  1225 */       childimg = "/images/trend.png";
/*  1226 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/*  1227 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/*  1228 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*       */       {
/*  1230 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*       */       }
/*  1232 */       else if (actionstatus.equals("0"))
/*       */       {
/*  1234 */         actionmsg = FormatUtil.getString("Actions Disabled");
/*  1235 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*       */       }
/*       */       
/*  1238 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*       */       {
/*  1240 */         linkclass = "disabledtext";
/*  1241 */         titleforres = titleforres + "-UnManaged";
/*       */       }
/*  1243 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/*  1244 */       String availmouseover = "";
/*  1245 */       if (alert.getProperty(availkey) != null)
/*       */       {
/*  1247 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*       */       }
/*  1249 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/*  1250 */       String healthmouseover = "";
/*  1251 */       if (alert.getProperty(healthkey) != null)
/*       */       {
/*  1253 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*       */       }
/*       */       
/*  1256 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/*  1257 */       int spacing = 0;
/*  1258 */       if (level >= 1)
/*       */       {
/*  1260 */         spacing = 40 * level;
/*       */       }
/*  1262 */       if (childtype.equals("HAI"))
/*       */       {
/*  1264 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/*  1265 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/*  1266 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*       */         
/*  1268 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/*  1269 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/*  1270 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/*  1271 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/*  1272 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/*  1273 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/*  1274 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/*  1275 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/*  1276 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/*  1277 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/*  1278 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*       */         
/*  1280 */         if (!forInventory)
/*       */         {
/*  1282 */           removefromgroup = "";
/*       */         }
/*       */         
/*  1285 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*       */         
/*  1287 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*       */         {
/*  1289 */           actions = editlink + actions;
/*       */         }
/*  1291 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*       */         {
/*  1293 */           actions = actions + associatelink;
/*       */         }
/*  1295 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/*  1296 */         String arrowimg = "";
/*  1297 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*       */         {
/*  1299 */           actions = "";
/*  1300 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/*  1301 */           checkbox = "";
/*  1302 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*       */         }
/*  1304 */         if (isIt360)
/*       */         {
/*  1306 */           actionimg = "";
/*  1307 */           actions = "";
/*  1308 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/*  1309 */           checkbox = "";
/*       */         }
/*       */         
/*  1312 */         if (!request.isUserInRole("ADMIN"))
/*       */         {
/*  1314 */           actions = "";
/*       */         }
/*  1316 */         if (request.isUserInRole("OPERATOR"))
/*       */         {
/*  1318 */           checkbox = "";
/*       */         }
/*       */         
/*  1321 */         String resourcelink = "";
/*       */         
/*  1323 */         if ((flag != null) && (flag.equals("enable")))
/*       */         {
/*  1325 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*       */         }
/*       */         else
/*       */         {
/*  1329 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*       */         }
/*       */         
/*  1332 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/*  1333 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/*  1334 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/*  1335 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*  1336 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/*  1337 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/*  1338 */         if (!isIt360)
/*       */         {
/*  1340 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*       */         }
/*       */         else
/*       */         {
/*  1344 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*       */         }
/*       */         
/*  1347 */         toreturn.append("</tr>");
/*  1348 */         if (childmos.get(childresid + "") != null)
/*       */         {
/*  1350 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/*  1351 */           toreturn.append(toappend);
/*       */         }
/*       */         else
/*       */         {
/*  1355 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/*  1356 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*       */           {
/*       */ 
/*  1359 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*       */           }
/*       */           
/*       */ 
/*  1363 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*       */           {
/*  1365 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/*  1366 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/*  1367 */             toreturn.append(assocMessage);
/*  1368 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/*  1369 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/*  1370 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/*  1371 */             toreturn.append("</tr>");
/*       */           }
/*       */         }
/*       */       }
/*       */       else
/*       */       {
/*  1377 */         String resourcelink = null;
/*  1378 */         boolean hideEditLink = false;
/*  1379 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*       */         {
/*  1381 */           String link1 = (String)extDeviceMap.get(childresid);
/*  1382 */           hideEditLink = true;
/*  1383 */           if (isIt360)
/*       */           {
/*  1385 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*       */           }
/*       */           else
/*       */           {
/*  1389 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*       */           }
/*  1391 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*       */         {
/*  1393 */           hideEditLink = true;
/*  1394 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/*  1395 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*       */ 
/*       */         }
/*       */         else
/*       */         {
/*  1400 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*       */         }
/*       */         
/*  1403 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/*  1404 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/*  1405 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/*  1406 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*  1407 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/*  1408 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/*  1409 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/*  1410 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/*  1411 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/*  1412 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/*  1413 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/*  1414 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/*  1415 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*       */         
/*  1417 */         if (hideEditLink)
/*       */         {
/*  1419 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*       */         }
/*  1421 */         if (!forInventory)
/*       */         {
/*  1423 */           removefromgroup = "";
/*       */         }
/*  1425 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*  1426 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/*  1427 */           actions = actions + configcustomfields;
/*       */         }
/*  1429 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*       */         {
/*  1431 */           actions = editlink + actions;
/*       */         }
/*  1433 */         String managedLink = "";
/*  1434 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*       */         {
/*  1436 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/*  1437 */           actions = "";
/*  1438 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/*  1439 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*       */           }
/*       */         }
/*  1442 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*       */         {
/*  1444 */           checkbox = "";
/*       */         }
/*       */         
/*  1447 */         if (!request.isUserInRole("ADMIN"))
/*       */         {
/*  1449 */           actions = "";
/*       */         }
/*  1451 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/*  1452 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/*  1453 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/*  1454 */         if (isIt360)
/*       */         {
/*  1456 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*       */         }
/*       */         else
/*       */         {
/*  1460 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*       */         }
/*  1462 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/*  1463 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/*  1464 */         if (!isIt360)
/*       */         {
/*  1466 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*       */         }
/*       */         else
/*       */         {
/*  1470 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*       */         }
/*  1472 */         toreturn.append("</tr>");
/*       */       }
/*       */     }
/*  1475 */     return toreturn.toString();
/*       */   }
/*       */   
/*       */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*       */   {
/*       */     try
/*       */     {
/*  1482 */       StringBuilder toreturn = new StringBuilder();
/*  1483 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/*  1484 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/*  1485 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/*  1486 */       String title = "";
/*  1487 */       message = EnterpriseUtil.decodeString(message);
/*  1488 */       message = FormatUtil.findReplace(message, "'", "\\'");
/*  1489 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*  1490 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*       */       {
/*  1492 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*       */       }
/*  1494 */       else if ("5".equals(severity))
/*       */       {
/*  1496 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*       */       }
/*       */       else
/*       */       {
/*  1500 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*       */       }
/*  1502 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/*  1503 */       toreturn.append(v);
/*       */       
/*  1505 */       toreturn.append(link);
/*  1506 */       if (severity == null)
/*       */       {
/*  1508 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*       */       }
/*  1510 */       else if (severity.equals("5"))
/*       */       {
/*  1512 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*       */       }
/*  1514 */       else if (severity.equals("4"))
/*       */       {
/*  1516 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*       */       }
/*  1518 */       else if (severity.equals("1"))
/*       */       {
/*  1520 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*  1525 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*       */       }
/*  1527 */       toreturn.append("</a>");
/*  1528 */       return toreturn.toString();
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  1532 */       ex.printStackTrace();
/*       */     }
/*  1534 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*       */   }
/*       */   
/*       */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*       */   {
/*       */     try
/*       */     {
/*  1541 */       StringBuilder toreturn = new StringBuilder();
/*  1542 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/*  1543 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/*  1544 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/*  1545 */       if (message == null)
/*       */       {
/*  1547 */         message = "";
/*       */       }
/*       */       
/*  1550 */       message = FormatUtil.findReplace(message, "'", "\\'");
/*  1551 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*       */       
/*  1553 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/*  1554 */       toreturn.append(v);
/*       */       
/*  1556 */       toreturn.append(link);
/*       */       
/*  1558 */       if (severity == null)
/*       */       {
/*  1560 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*       */       }
/*  1562 */       else if (severity.equals("5"))
/*       */       {
/*  1564 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*       */       }
/*  1566 */       else if (severity.equals("1"))
/*       */       {
/*  1568 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*  1573 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*       */       }
/*  1575 */       toreturn.append("</a>");
/*  1576 */       return toreturn.toString();
/*       */     }
/*       */     catch (Exception ex) {}
/*       */     
/*       */ 
/*       */ 
/*  1582 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*       */   }
/*       */   
/*  1585 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/*  1586 */     if (invokeActions != null) {
/*  1587 */       Iterator iterator = invokeActions.keySet().iterator();
/*  1588 */       while (iterator.hasNext()) {
/*  1589 */         String actionid = (String)invokeActions.get((String)iterator.next());
/*  1590 */         if (actionmap.containsKey(actionid)) {
/*  1591 */           actionsavailable.add(actionid);
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*  1596 */     return actionsavailable;
/*       */   }
/*       */   
/*       */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/*  1600 */     String actionLink = "";
/*  1601 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  1602 */     String query = "";
/*  1603 */     ResultSet rs = null;
/*  1604 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/*  1605 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*  1606 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/*  1607 */       actionLink = "method=" + methodName;
/*       */     }
/*  1609 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/*  1610 */       actionLink = methodName;
/*       */     }
/*  1612 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/*  1613 */     Iterator itr = methodarglist.iterator();
/*  1614 */     boolean isfirstparam = true;
/*  1615 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/*  1616 */     while (itr.hasNext()) {
/*  1617 */       HashMap argmap = (HashMap)itr.next();
/*  1618 */       String argtype = (String)argmap.get("TYPE");
/*  1619 */       String argname = (String)argmap.get("IDENTITY");
/*  1620 */       String paramname = (String)argmap.get("PARAMETER");
/*  1621 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*  1622 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/*  1623 */         isfirstparam = false;
/*  1624 */         if (actionLink.indexOf("?") > 0)
/*       */         {
/*  1626 */           actionLink = actionLink + "&";
/*       */         }
/*       */         else
/*       */         {
/*  1630 */           actionLink = actionLink + "?";
/*       */         }
/*       */       }
/*       */       else {
/*  1634 */         actionLink = actionLink + "&";
/*       */       }
/*  1636 */       String paramValue = null;
/*  1637 */       String tempargname = argname;
/*  1638 */       if (commonValues.getProperty(tempargname) != null) {
/*  1639 */         paramValue = commonValues.getProperty(tempargname);
/*       */       }
/*       */       else {
/*  1642 */         if (argtype.equalsIgnoreCase("Argument")) {
/*  1643 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/*  1644 */           if (dbType.equals("mysql")) {
/*  1645 */             argname = "`" + argname + "`";
/*       */           }
/*       */           else {
/*  1648 */             argname = "\"" + argname + "\"";
/*       */           }
/*  1650 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*       */           try {
/*  1652 */             rs = AMConnectionPool.executeQueryStmt(query);
/*  1653 */             if (rs.next()) {
/*  1654 */               paramValue = rs.getString("VALUE");
/*  1655 */               commonValues.setProperty(tempargname, paramValue);
/*       */             }
/*       */           }
/*       */           catch (java.sql.SQLException e) {
/*  1659 */             e.printStackTrace();
/*       */           }
/*       */           finally {
/*       */             try {
/*  1663 */               AMConnectionPool.closeStatement(rs);
/*       */             }
/*       */             catch (Exception exc) {
/*  1666 */               exc.printStackTrace();
/*       */             }
/*       */           }
/*       */         }
/*       */         
/*  1671 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/*  1672 */           paramValue = rowId;
/*       */         }
/*  1674 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/*  1675 */           paramValue = managedObjectName;
/*       */         }
/*  1677 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/*  1678 */           paramValue = resID;
/*       */         }
/*  1680 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/*  1681 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*       */         }
/*       */       }
/*  1684 */       actionLink = actionLink + paramname + "=" + paramValue;
/*       */     }
/*  1686 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/*  1687 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/*  1688 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*       */     }
/*  1690 */     return actionLink;
/*       */   }
/*       */   
/*  1693 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/*  1694 */     String dependentAttribute = null;
/*  1695 */     String align = "left";
/*       */     
/*  1697 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/*  1698 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/*  1699 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/*  1700 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/*  1701 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/*  1702 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/*  1703 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/*  1704 */     if ((displayType != null) && (displayType.equals("Image"))) {
/*  1705 */       align = "center";
/*       */     }
/*       */     
/*  1708 */     boolean iscolumntoDisplay = actionsavailable != null;
/*  1709 */     String actualdata = "";
/*       */     
/*  1711 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/*  1712 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/*  1713 */         actualdata = availValue;
/*       */       }
/*  1715 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/*  1716 */         actualdata = healthValue;
/*       */       } else {
/*       */         try
/*       */         {
/*  1720 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/*  1721 */           actualdata = (String)rowDetails.get(attributeName);
/*       */         }
/*       */         catch (Exception e) {
/*  1724 */           e.printStackTrace();
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  1730 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/*  1731 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/*  1732 */       toreturn.append("<table>");
/*  1733 */       toreturn.append("<tr>");
/*  1734 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/*  1735 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/*  1736 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/*  1737 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/*  1738 */         String toolTip = "";
/*  1739 */         String hideClass = "";
/*  1740 */         String textStyle = "";
/*  1741 */         boolean isreferenced = true;
/*  1742 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/*  1743 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/*  1744 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/*  1745 */           hideClass = "hideddrivetip()";
/*       */         }
/*  1747 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/*  1748 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/*  1749 */           while (valueList.hasMoreTokens()) {
/*  1750 */             String dependentVal = valueList.nextToken();
/*  1751 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/*  1752 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/*  1753 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*       */               }
/*  1755 */               toolTip = "";
/*  1756 */               hideClass = "";
/*  1757 */               isreferenced = false;
/*  1758 */               textStyle = "disabledtext";
/*  1759 */               break;
/*       */             }
/*       */           }
/*       */         }
/*  1763 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/*  1764 */           toolTip = "";
/*  1765 */           hideClass = "";
/*  1766 */           isreferenced = false;
/*  1767 */           textStyle = "disabledtext";
/*  1768 */           if (dependentImageMap != null) {
/*  1769 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/*  1770 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*       */             }
/*       */             else {
/*  1773 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*       */             }
/*       */           }
/*       */         }
/*  1777 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/*  1778 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/*  1779 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/*  1780 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/*  1781 */           String managedObject = (String)rowDetails.get(primaryColId);
/*  1782 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*       */           
/*  1784 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/*  1785 */           if (isreferenced) {
/*  1786 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*       */           }
/*       */           else
/*       */           {
/*  1790 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/*  1791 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/*  1792 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/*  1793 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/*  1794 */             toreturn.append("<span class=\"" + textStyle + "\">");
/*  1795 */             toreturn.append(FormatUtil.getString(displayValue));
/*       */           }
/*  1797 */           toreturn.append("</span>");
/*  1798 */           toreturn.append("</a>");
/*  1799 */           toreturn.append("</td>");
/*       */         }
/*       */       }
/*  1802 */       toreturn.append("</tr>");
/*  1803 */       toreturn.append("</table>");
/*  1804 */       toreturn.append("</td>");
/*       */     } else {
/*  1806 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*       */     }
/*       */     
/*  1809 */     return toreturn.toString();
/*       */   }
/*       */   
/*       */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/*  1813 */     String colTime = null;
/*  1814 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  1815 */     if ((rows != null) && (rows.size() > 0)) {
/*  1816 */       Iterator<String> itr = rows.iterator();
/*  1817 */       String maxColQuery = "";
/*  1818 */       for (;;) { if (itr.hasNext()) {
/*  1819 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/*  1820 */           ResultSet maxCol = null;
/*       */           try {
/*  1822 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/*  1823 */             while (maxCol.next()) {
/*  1824 */               if (colTime == null) {
/*  1825 */                 colTime = Long.toString(maxCol.getLong(1));
/*       */               }
/*       */               else {
/*  1828 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*       */               }
/*       */             }
/*       */             
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1837 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*       */             try {
/*  1839 */               if (maxCol != null)
/*  1840 */                 AMConnectionPool.closeStatement(maxCol);
/*       */             } catch (Exception e) {
/*  1842 */               e.printStackTrace();
/*       */             }
/*       */           }
/*       */           catch (Exception e) {}finally
/*       */           {
/*  1837 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*       */             try {
/*  1839 */               if (maxCol != null)
/*  1840 */                 AMConnectionPool.closeStatement(maxCol);
/*       */             } catch (Exception e) {
/*  1842 */               e.printStackTrace();
/*       */             }
/*       */           }
/*       */         }
/*       */       } }
/*  1847 */     return colTime;
/*       */   }
/*       */   
/*  1850 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/*  1851 */     tablename = null;
/*  1852 */     ResultSet rsTable = null;
/*  1853 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*       */     try {
/*  1855 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/*  1856 */       while (rsTable.next()) {
/*  1857 */         tablename = rsTable.getString("DATATABLE");
/*  1858 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/*  1859 */           tablename = "AM_Script_Numeric_Data_" + baseid;
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1872 */       return tablename;
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*  1863 */       e.printStackTrace();
/*       */     } finally {
/*       */       try {
/*  1866 */         if (rsTable != null)
/*  1867 */           AMConnectionPool.closeStatement(rsTable);
/*       */       } catch (Exception e) {
/*  1869 */         e.printStackTrace();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/*  1875 */     String argsList = "";
/*  1876 */     ArrayList showArgslist = new ArrayList();
/*       */     try {
/*  1878 */       if (showArgsMap.get(row) != null) {
/*  1879 */         showArgslist = (ArrayList)showArgsMap.get(row);
/*  1880 */         if (showArgslist != null) {
/*  1881 */           for (int i = 0; i < showArgslist.size(); i++) {
/*  1882 */             if (argsList.trim().equals("")) {
/*  1883 */               argsList = (String)showArgslist.get(i);
/*       */             }
/*       */             else {
/*  1886 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*       */     catch (Exception e) {
/*  1893 */       e.printStackTrace();
/*  1894 */       return "";
/*       */     }
/*  1896 */     return argsList;
/*       */   }
/*       */   
/*       */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*       */   {
/*  1901 */     String argsList = "";
/*  1902 */     ArrayList hideArgsList = new ArrayList();
/*       */     try
/*       */     {
/*  1905 */       if (hideArgsMap.get(row) != null)
/*       */       {
/*  1907 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/*  1908 */         if (hideArgsList != null)
/*       */         {
/*  1910 */           for (int i = 0; i < hideArgsList.size(); i++)
/*       */           {
/*  1912 */             if (argsList.trim().equals(""))
/*       */             {
/*  1914 */               argsList = (String)hideArgsList.get(i);
/*       */             }
/*       */             else
/*       */             {
/*  1918 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  1926 */       ex.printStackTrace();
/*       */     }
/*  1928 */     return argsList;
/*       */   }
/*       */   
/*       */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/*  1932 */     StringBuilder toreturn = new StringBuilder();
/*  1933 */     StringBuilder addtoreturn = new StringBuilder();
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1940 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/*  1941 */       Iterator itr = tActionList.iterator();
/*  1942 */       while (itr.hasNext()) {
/*  1943 */         Boolean confirmBox = Boolean.valueOf(false);
/*  1944 */         String confirmmsg = "";
/*  1945 */         String link = "";
/*  1946 */         String isJSP = "NO";
/*  1947 */         HashMap tactionMap = (HashMap)itr.next();
/*  1948 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/*  1949 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/*  1950 */         String actionId = (String)tactionMap.get("ACTIONID");
/*  1951 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/*  1952 */           (actionmap.containsKey(actionId))) {
/*  1953 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/*  1954 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/*  1955 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/*  1956 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/*  1957 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*       */           
/*  1959 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1965 */           if (isTableAction) {
/*  1966 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*       */           }
/*       */           else {
/*  1969 */             tableName = "Link";
/*  1970 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/*  1971 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/*  1972 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/*  1973 */             toreturn.append("</a></td>");
/*       */           }
/*  1975 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/*  1976 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/*  1977 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/*  1978 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  1984 */     return toreturn.toString() + addtoreturn.toString();
/*       */   }
/*       */   
/*       */ 
/*       */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*       */   {
/*  1990 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*       */     {
/*  1992 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/*  1993 */       Properties prop = (Properties)node.getUserObject();
/*  1994 */       String mgID = prop.getProperty("label");
/*  1995 */       String mgName = prop.getProperty("value");
/*  1996 */       String isParent = prop.getProperty("isParent");
/*  1997 */       int mgIDint = Integer.parseInt(mgID);
/*  1998 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*       */       {
/*  2000 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*       */       }
/*  2002 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/*  2003 */       if (node.getChildCount() > 0)
/*       */       {
/*  2005 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*       */         {
/*  2007 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*       */         }
/*  2009 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*       */         {
/*  2011 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*       */         }
/*       */         else
/*       */         {
/*  2015 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*       */         }
/*       */         
/*       */ 
/*       */       }
/*  2020 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*       */       {
/*  2022 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*       */       }
/*  2024 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*       */       {
/*  2026 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*       */       }
/*       */       else
/*       */       {
/*  2030 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*       */       }
/*       */       
/*  2033 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/*  2034 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*       */       {
/*  2036 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*       */       }
/*       */       else
/*       */       {
/*  2040 */         builder.append(prop.getProperty("value") + "</a></li>");
/*       */       }
/*  2042 */       if (node.getChildCount() > 0)
/*       */       {
/*  2044 */         builder.append("<UL>");
/*  2045 */         printMGTree(node, builder);
/*  2046 */         builder.append("</UL>");
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*  2051 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/*  2052 */     StringBuffer toReturn = new StringBuffer();
/*  2053 */     String table = "-";
/*       */     try {
/*  2055 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/*  2056 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/*  2057 */       float total = 0.0F;
/*  2058 */       while (it.hasNext()) {
/*  2059 */         String attName = (String)it.next();
/*  2060 */         String data = (String)attidMap.get(attName.toUpperCase());
/*  2061 */         boolean roundOffData = false;
/*  2062 */         if ((data != null) && (!data.equals(""))) {
/*  2063 */           if (data.indexOf(",") != -1) {
/*  2064 */             data = data.replaceAll(",", "");
/*       */           }
/*       */           try {
/*  2067 */             float value = Float.parseFloat(data);
/*  2068 */             if (value == 0.0F) {
/*       */               continue;
/*       */             }
/*  2071 */             total += value;
/*  2072 */             attVsWidthProps.put(attName, value + "");
/*       */           }
/*       */           catch (Exception e) {
/*  2075 */             e.printStackTrace();
/*       */           }
/*       */         }
/*       */       }
/*       */       
/*  2080 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/*  2081 */       while (attVsWidthList.hasNext()) {
/*  2082 */         String attName = (String)attVsWidthList.next();
/*  2083 */         String data = (String)attVsWidthProps.get(attName);
/*  2084 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/*  2085 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/*  2086 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/*  2087 */         String className = (String)graphDetails.get("ClassName");
/*  2088 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/*  2089 */         if (percentage < 1.0F)
/*       */         {
/*  2091 */           data = percentage + "";
/*       */         }
/*  2093 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*       */       }
/*  2095 */       if (toReturn.length() > 0) {
/*  2096 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*       */       }
/*       */     }
/*       */     catch (Exception e) {
/*  2100 */       e.printStackTrace();
/*       */     }
/*  2102 */     return table;
/*       */   }
/*       */   
/*       */ 
/*       */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*       */   {
/*  2108 */     String[] splitvalues = { criticalcondition, criticalThValue };
/*  2109 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/*  2110 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/*  2111 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/*  2112 */       String condition1 = (String)criticalThresholdValues.get(0);
/*  2113 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/*  2114 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/*  2115 */       String condition2 = (String)criticalThresholdValues.get(5);
/*  2116 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*       */       
/*       */ 
/*  2119 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/*  2120 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/*  2121 */       splitvalues[0] = multiplecondition.toString();
/*  2122 */       splitvalues[1] = "";
/*       */     }
/*       */     
/*  2125 */     return splitvalues;
/*       */   }
/*       */   
/*       */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*       */   {
/*  2130 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/*  2131 */     if (thresholdType != 3) {
/*  2132 */       conditionsMap.put("LT", new String[] { "", "<" });
/*  2133 */       conditionsMap.put("GT", new String[] { "", ">" });
/*  2134 */       conditionsMap.put("EQ", new String[] { "", "=" });
/*  2135 */       conditionsMap.put("LE", new String[] { "", "<=" });
/*  2136 */       conditionsMap.put("GE", new String[] { "", ">=" });
/*  2137 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*       */     } else {
/*  2139 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/*  2140 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/*  2141 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/*  2142 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/*  2143 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/*  2144 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*       */     }
/*  2146 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/*  2147 */     if (updateSelected != null) {
/*  2148 */       updateSelected[0] = "selected";
/*       */     }
/*  2150 */     return conditionsMap;
/*       */   }
/*       */   
/*       */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*       */     try {
/*  2155 */       StringBuffer toreturn = new StringBuffer("");
/*  2156 */       if (commaSeparatedMsgId != null) {
/*  2157 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/*  2158 */         int count = 0;
/*  2159 */         while (msgids.hasMoreTokens()) {
/*  2160 */           String id = msgids.nextToken();
/*  2161 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/*  2162 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/*  2163 */           count++;
/*  2164 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/*  2165 */             if (toreturn.length() == 0) {
/*  2166 */               toreturn.append("<table width=\"100%\">");
/*       */             }
/*  2168 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/*  2169 */             if (!image.trim().equals("")) {
/*  2170 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*       */             }
/*  2172 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/*  2173 */             toreturn.append("</tr></tbody></table></td></tr>");
/*       */           }
/*       */         }
/*  2176 */         if (toreturn.length() > 0) {
/*  2177 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*       */         }
/*       */       }
/*       */       
/*  2181 */       return toreturn.toString();
/*       */     }
/*       */     catch (Exception e) {
/*  2184 */       e.printStackTrace(); }
/*  2185 */     return "";
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ArrayList getMGroupsCreatedInAdminServer(ArrayList aListOfAllMonitorGroups)
/*       */   {
/*  2194 */     ArrayList aListAdminMonitorGrps = new ArrayList();
/*       */     try {
/*  2196 */       for (int i = 0; i < aListOfAllMonitorGroups.size(); i++) {
/*  2197 */         ArrayList innerList = (ArrayList)aListOfAllMonitorGroups.get(i);
/*  2198 */         if ((innerList != null) && (innerList.size() >= 2))
/*       */         {
/*       */           try
/*       */           {
/*  2202 */             String strMgId = (String)innerList.get(1);
/*  2203 */             int mgId = Integer.parseInt(strMgId);
/*  2204 */             if (mgId < 10000000) {
/*  2205 */               aListAdminMonitorGrps.add(innerList);
/*       */             }
/*  2207 */             String grpCreatedMasName = com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(strMgId);
/*  2208 */             innerList.add(grpCreatedMasName);
/*       */           }
/*       */           catch (Exception ex1) {}
/*       */         }
/*       */       }
/*       */     } catch (Exception ex2) {
/*  2214 */       ex2.printStackTrace();
/*       */     }
/*  2216 */     return aListAdminMonitorGrps;
/*       */   }
/*       */   
/*  2219 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2225 */   private static Map<String, Long> _jspx_dependants = new HashMap(4);
/*  2226 */   static { _jspx_dependants.put("/jsp/includes/monitorGroups.jsp", Long.valueOf(1473429417000L));
/*  2227 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*  2228 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*  2229 */     _jspx_dependants.put("/jsp/includes/MonitorDiscoveryStatus.jspf", Long.valueOf(1473429417000L));
/*       */   }
/*       */   
/*       */ 
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonblur_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeyup_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyleClass_005fproperty_005fonclick_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fdisabled_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody;
/*       */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*       */   private javax.el.ExpressionFactory _el_expressionfactory;
/*       */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*       */   public Map<String, Long> getDependants()
/*       */   {
/*  2273 */     return _jspx_dependants;
/*       */   }
/*       */   
/*       */   public void _jspInit() {
/*  2277 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2278 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2279 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2280 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2281 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2282 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2283 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2284 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2285 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2286 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2287 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2288 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2289 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2290 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2291 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2292 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2293 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonblur_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2294 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2295 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2296 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2297 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2298 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeyup_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2299 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2300 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2301 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2302 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2303 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2304 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyleClass_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2305 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2306 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2307 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fdisabled_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2308 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2309 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2310 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2311 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2312 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  2313 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  2314 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*       */   }
/*       */   
/*       */   public void _jspDestroy() {
/*  2318 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/*  2319 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  2320 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*  2321 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  2322 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  2323 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  2324 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  2325 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*  2326 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*  2327 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*  2328 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*  2329 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*  2330 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/*  2331 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*  2332 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*  2333 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/*  2334 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonblur_005fnobody.release();
/*  2335 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/*  2336 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*  2337 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.release();
/*  2338 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.release();
/*  2339 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeyup_005fnobody.release();
/*  2340 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/*  2341 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.release();
/*  2342 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*  2343 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.release();
/*  2344 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*  2345 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyleClass_005fproperty_005fonclick_005fnobody.release();
/*  2346 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.release();
/*  2347 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.release();
/*  2348 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fdisabled_005fnobody.release();
/*  2349 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*  2350 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fnobody.release();
/*  2351 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.release();
/*  2352 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.release();
/*  2353 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*       */   }
/*       */   
/*       */ 
/*       */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*       */     throws java.io.IOException, javax.servlet.ServletException
/*       */   {
/*  2360 */     HttpSession session = null;
/*       */     
/*       */ 
/*  2363 */     JspWriter out = null;
/*  2364 */     Object page = this;
/*  2365 */     JspWriter _jspx_out = null;
/*  2366 */     PageContext _jspx_page_context = null;
/*       */     
/*       */     try
/*       */     {
/*  2370 */       response.setContentType("text/html;charset=UTF-8");
/*  2371 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*       */       
/*  2373 */       _jspx_page_context = pageContext;
/*  2374 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  2375 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  2376 */       session = pageContext.getSession();
/*  2377 */       out = pageContext.getOut();
/*  2378 */       _jspx_out = out;
/*       */       
/*  2380 */       out.write("<!DOCTYPE html>\n");
/*  2381 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
/*  2382 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*  2383 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*       */       
/*  2385 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/*  2386 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/*  2387 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*       */       
/*  2389 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*       */       
/*  2391 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*       */       
/*  2393 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*       */       
/*  2395 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/*  2396 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/*  2397 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/*  2398 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*       */       }
/*       */       else {
/*  2401 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*  2402 */         String available = null;
/*  2403 */         available = (String)_jspx_page_context.findAttribute("available");
/*  2404 */         out.write(10);
/*       */         
/*  2406 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/*  2407 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/*  2408 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*       */         
/*  2410 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*       */         
/*  2412 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*       */         
/*  2414 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*       */         
/*  2416 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/*  2417 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/*  2418 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/*  2419 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*       */         }
/*       */         else {
/*  2422 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*  2423 */           String unavailable = null;
/*  2424 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/*  2425 */           out.write(10);
/*       */           
/*  2427 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/*  2428 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/*  2429 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*       */           
/*  2431 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*       */           
/*  2433 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*       */           
/*  2435 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*       */           
/*  2437 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/*  2438 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/*  2439 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/*  2440 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*       */           }
/*       */           else {
/*  2443 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*  2444 */             String unmanaged = null;
/*  2445 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/*  2446 */             out.write(10);
/*       */             
/*  2448 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/*  2449 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/*  2450 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*       */             
/*  2452 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*       */             
/*  2454 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*       */             
/*  2456 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*       */             
/*  2458 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/*  2459 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/*  2460 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/*  2461 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*       */             }
/*       */             else {
/*  2464 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*  2465 */               String scheduled = null;
/*  2466 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/*  2467 */               out.write(10);
/*       */               
/*  2469 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/*  2470 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/*  2471 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*       */               
/*  2473 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*       */               
/*  2475 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*       */               
/*  2477 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*       */               
/*  2479 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/*  2480 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/*  2481 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/*  2482 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*       */               }
/*       */               else {
/*  2485 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*  2486 */                 String critical = null;
/*  2487 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/*  2488 */                 out.write(10);
/*       */                 
/*  2490 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/*  2491 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/*  2492 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*       */                 
/*  2494 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*       */                 
/*  2496 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*       */                 
/*  2498 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*       */                 
/*  2500 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/*  2501 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/*  2502 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/*  2503 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*       */                 }
/*       */                 else {
/*  2506 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*  2507 */                   String clear = null;
/*  2508 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/*  2509 */                   out.write(10);
/*       */                   
/*  2511 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/*  2512 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/*  2513 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*       */                   
/*  2515 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*       */                   
/*  2517 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*       */                   
/*  2519 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*       */                   
/*  2521 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/*  2522 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/*  2523 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/*  2524 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*       */                   }
/*       */                   else {
/*  2527 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*  2528 */                     String warning = null;
/*  2529 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/*  2530 */                     out.write(10);
/*  2531 */                     out.write(10);
/*       */                     
/*  2533 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/*  2534 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*       */                     
/*  2536 */                     out.write(10);
/*  2537 */                     out.write(10);
/*  2538 */                     out.write(10);
/*  2539 */                     out.write(10);
/*  2540 */                     out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  2541 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*       */                       return;
/*  2543 */                     out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  2544 */                     out.write(10);
/*  2545 */                     out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/hostdiscoveryform.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/diagnosepage.js\"></SCRIPT>\n\n");
/*  2546 */                     out.write("<!--$Id$-->\n\n\n\n");
/*       */                     
/*       */                     try
/*       */                     {
/*  2550 */                       boolean isprivilege = false;
/*  2551 */                       if (com.adventnet.appmanager.struts.beans.ClientDBUtil.isPrivilegedUser(request)) {
/*  2552 */                         isprivilege = true;
/*       */                       }
/*  2554 */                       request.setAttribute("checkForMonitorGroup", Boolean.valueOf(isprivilege));
/*       */                       
/*       */ 
/*  2557 */                       ArrayList dynamicSites = AlarmUtil.getSiteMonitorGroups();
/*  2558 */                       if (dynamicSites != null)
/*       */                       {
/*  2560 */                         request.setAttribute("dynamicSites", dynamicSites);
/*       */                       }
/*       */                       
/*  2563 */                       ArrayList mgList = new ArrayList();
/*  2564 */                       if (EnterpriseUtil.isIt360MSPEdition())
/*       */                       {
/*  2566 */                         AMActionForm form = (AMActionForm)request.getAttribute("AMActionForm");
/*  2567 */                         ArrayList orgs = AlarmUtil.getCustomerNames();
/*       */                         
/*  2569 */                         if (orgs != null)
/*       */                         {
/*  2571 */                           request.setAttribute("customers", orgs);
/*       */                         }
/*       */                         
/*       */ 
/*  2575 */                         if (form != null)
/*       */                         {
/*  2577 */                           String customerName = form.getOrganization();
/*  2578 */                           if (customerName != null)
/*       */                           {
/*  2580 */                             mgList = AlarmUtil.getSiteMonitorGroups(customerName);
/*       */                           }
/*       */                           
/*       */                         }
/*       */                         
/*       */ 
/*       */                       }
/*  2587 */                       else if (isprivilege)
/*       */                       {
/*  2589 */                         mgList = AlarmUtil.getConfiguredGroups(request, false, false, true);
/*       */                       }
/*       */                       else
/*       */                       {
/*  2593 */                         mgList = AlarmUtil.getConfiguredGroups(null, false, false, true);
/*       */                       }
/*       */                       
/*  2596 */                       if (mgList != null)
/*       */                       {
/*  2598 */                         request.setAttribute("applications", mgList);
/*  2599 */                         if (EnterpriseUtil.isAdminServer()) {
/*  2600 */                           ArrayList adminMGroups = getMGroupsCreatedInAdminServer(mgList);
/*  2601 */                           request.setAttribute("AllMonitorGroupsInAdminServer", mgList);
/*  2602 */                           request.setAttribute("MonitorGroupsCreatedInAdminServer", adminMGroups);
/*       */                         }
/*       */                       }
/*       */                     }
/*       */                     catch (Exception e)
/*       */                     {
/*  2608 */                       e.printStackTrace();
/*       */                     }
/*       */                     
/*  2611 */                     out.write(10);
/*  2612 */                     out.write(10);
/*  2613 */                     out.write("\n\n<script>\nfunction loadExchange()\n{\n\t//this method - myOnLoad - gets called from BasicLayout.jsp\n\tvar type=document.AMActionForm.type.value;\n\tif(type=='Exchange:25')\n\t{\n\t\tvar version=document.AMActionForm.version.value;\n\t\tif(version==\"2013\"||version==\"2010\"||version==\"2007\")\n\t\t{\n\t\t\tshowDiv('ExchngRoles');\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv('ExchngRoles');\t\n\t\t}\n\t\tif(version==\"unknown\")\n\t\t{\n\t\t\tjavascript:hideDiv('ES2007andAbove');\n\t\t\tjavascript:hideDiv('ExchngRoles');\n\t\t\tjavascript:hideDiv('BelowES2007');\n\t\t}\n\t}\n\telse\n\t{\n\t\thideDiv('ExchngRoles');\t\n\t}\n// alert(document.AMActionForm.type.value);\n// alert(document.AMActionForm.version.value);\n}\nfunction loadSite()\n{\n\tdocument.AMActionForm.haid.options.length=0;\n\tvar formCustomerId = document.AMActionForm.organization.value;\n\tvar siteName;\n\tvar siteId;\n\tvar customerId;\n\tvar rowCount=0;\n\tdocument.AMActionForm.haid.options[rowCount++] = new Option('-Select Site-','-');\n\t");
/*  2614 */                     if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*       */                       return;
/*  2616 */                     out.write("\n}\n\nfunction resetname(name)\n{\n\tif(name='groupname')\n\t{\n\t\tdocument.AMActionForm.groupname.value='';\n\t}\n\tif(name='subgroupname')\n\t{\n\t\tdocument.AMActionForm.subgroupname.value='';\n\t}\n}\nfunction createGroup()\n{\n   var groupName1= document.AMActionForm.groupname.value.trim(); \n\tif(groupName1=='')\n\t{\n\t\talert(\"");
/*  2617 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/*  2618 */                     out.write("\");\n\t\thideDiv('group');\n\t\tshowDiv('creategroup');\n\t\tdocument.AMActionForm.groupname.focus();\n\t\treturn false;\n\t}\n\telse if(groupName1.length>28)\n\t{\n\talert(\"");
/*  2619 */                     out.print(FormatUtil.getString("am.webclient.bs.bsname.length.alert"));
/*  2620 */                     out.write("\");\n\tdocument.AMActionForm.groupname.select();\n\treturn false;\n\t}\n\telse if(groupName1.indexOf(\"-\") != -1 || groupName1.indexOf(\"_\") != -1 || groupName1.indexOf(\"\\\"\") != -1 || groupName1.indexOf(\"'\") != -1)\n\t{\n\talert(\"");
/*  2621 */                     out.print(FormatUtil.getString("it360.sp.customermgmt.specialcharerr"));
/*  2622 */                     out.write("\");\n\tdocument.AMActionForm.groupname.select();\n\treturn false;\n\t}\t\n\telse\n\t{\n\t\thideDiv('creategroup');\n\t\tvar a=document.AMActionForm.groupname.value;\n\t\turl=\"/adminAction.do?method=createMonitorGroup&groupname=\"+encodeURIComponent(a);\n\t\thttp.open(\"GET\",url,true);\n\t\thttp.onreadystatechange = getActionTypes;\n\t\thttp.send(null);\n\t\tshowDiv('group');\n\t}\n}\n\nfunction check()\n{\n\thideDiv(\"creategroup\");\n\thideDiv(\"createsubgroup\");\n\thideDiv(\"groupmessage\");\n\tvar flag='");
/*  2623 */                     out.print(com.adventnet.appmanager.util.Constants.subGroupsEnabled);
/*  2624 */                     out.write("';\n\tif(flag==\"true\")\n\t{\n\t\tif(trimAll(document.AMActionForm.haid.value) == '' || document.AMActionForm.haid.value=='-')\n\t\t{\n\t\t\thideDiv(\"subgroup\");\n\t\t\tshowDiv(\"group\");\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv(\"group\");\n\t\t\tshowDiv(\"subgroup\");\n\t\t}\n\t}\n\telse\n\t{\n\t\tshowDiv(\"group\");\n\t}\n}\n\nfunction createsubGroup()\n{\n   var subgroupName1=document.AMActionForm.subgroupname.value.trim();\n\tif(trimAll(document.AMActionForm.haid.value) == '' || document.AMActionForm.haid.value=='-')\n\t{\n\t\talert(\"");
/*  2625 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.monitoralert"));
/*  2626 */                     out.write("\");\t\t\n\t\tdocument.AMActionForm.haid.focus();\n\t}\n\telse\n\t{\n\t\tif(subgroupName1=='')\n\t\t{\n\t\t\talert(\"");
/*  2627 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/*  2628 */                     out.write("\");\n\t\t\tdocument.AMActionForm.subgroupname.focus();\n\t\t\treturn false;\n\t\t}\n\t\telse if(subgroupName1.length>28)\n\t\t{\n\t\talert(\"");
/*  2629 */                     out.print(FormatUtil.getString("am.webclient.bs.bsname.length.alert"));
/*  2630 */                     out.write("\");\n\t\tdocument.AMActionForm.subgroupname.select();\n\t\treturn false;\n\t\t}\n\t\telse if(subgroupName1.indexOf(\"-\") != -1 || subgroupName1.indexOf(\"_\") != -1 || subgroupName1.indexOf(\"\\\"\") != -1 || subgroupName1.indexOf(\"'\") != -1)\n\t\t{\n\t\talert(\"");
/*  2631 */                     out.print(FormatUtil.getString("it360.sp.customermgmt.specialcharerr"));
/*  2632 */                     out.write("\");\n\t\tdocument.AMActionForm.subgroupname.select();\n\t\treturn false;\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv('createsubgroup');\n\t\t\tvar a=document.AMActionForm.subgroupname.value;\n\t\t\tvar haid=document.AMActionForm.haid.value;\n\t\t\turl=\"/adminAction.do?method=createSubGroup&haid=\"+haid+\"&subgroupname=\"+a;\n\t\t\thttp.open(\"GET\",url,true);\n\t\t\thttp.onreadystatechange = getActionTypes;\n\t\t\thttp.send(null);\n\t\t}\n\t\tshowDiv('subgroup');\n    }\n\n}\n \nfunction getActionTypes()\n{\n    if(http.readyState == 4)\n    {\n\t\tvar result = http.responseText;\n\t\tvar id=result;\n\t\tvar stringtokens=id.split(\",\");\n\t\tsid = stringtokens[2];\n\t\tsname=stringtokens[1];\n\t\tsname=decodeURIComponent(stringtokens[1]);\n\t\tsmessage=stringtokens[0];\n\t\tif (sname==null || sname=='undefined')\n\t\t{\n\t\t\tshowDiv(\"groupmessage\");\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.AMActionForm.haid.options[document.AMActionForm.haid.length] =new Option(sname,sid,false,true);\n\t\t\thideDiv(\"creategroup\");\n\t\t\thideDiv(\"createsubgroup\");\n\t\t\thideDiv(\"group\");\n");
/*  2633 */                     out.write("\t\t\tshowDiv(\"subgroup\");\n\t\t\tshowDiv(\"groupmessage\");\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t  \t}\n\t}\n}\n\n\nfunction changePort()\n{\n\tvar type=document.AMActionForm.type.value;\n\tif(type=='APACHE:80')\n\t{\n\t\tdocument.AMActionForm.serverstatusurl.checked=false;\n\t\tshowApacheStatus();\n\t}\n\tif(document.AMActionForm.sslenabled.checked)\n\t{\n\t\tdocument.AMActionForm.port.value = \"443\";\n\t}\n\telse\n\t{\n\t\tdocument.AMActionForm.port.value = \"80\";\n\t}\n}\n\nfunction snmpVersionSelect(value)\n \t {\n \t     $(\"#testCredentialResult\").hide(\"fast\");\n \t     if(value == 'v1v2')\n \t     {\n \t         $(\"#snmpV1V2\").show(\"slow\");\n \t         $(\"#snmpV3\").hide(\"fast\");\n \t         //$(\"#snmpV3\").fadeOut(\"fast\");\n \t         //$(\"#snmpV1V2\").fadeIn(\"fast\");\n \t         //document.getElementById('snmpV1V2').style.display=\"block\";\n \t         //document.getElementById('snmpV3').style.display=\"none\";\n \t     }\n \t     else\n \t     {\n                 $(\"#snmpV1V2\").hide(\"fast\");\n                 $(\"#snmpV3\").show(\"slow\");\n \t         \n \t         //$(\"#snmpV1V2\").fadeOut(\"fast\");\n");
/*  2634 */                     out.write(" \t         //$(\"#snmpV3\").fadeIn(\"fast\");\n \t         //document.getElementById('snmpV1V2').style.display=\"none\";\n \t         //document.getElementById('snmpV3').style.display=\"block\";\n \t     }\n \t }\n\n \t function showSecurityLevelProps()\n \t {\n \t     if($('select[name=snmpSecurityLevel]').val() == 'NOAUTHNOPRIV')\n \t         {\n \t             $(\"#AuthPrivID\").hide(\"fast\");\n \t             $(\"#AuthNoPrivID\").hide(\"fast\");\n \t         }\n \t         if($('select[name=snmpSecurityLevel]').val() == 'AUTHNOPRIV')\n \t         {\n \t             $(\"#AuthPrivID\").hide(\"fast\");\n \t             $(\"#AuthNoPrivID\").show(\"slow\");\n \t         }\n \t         if($('select[name=snmpSecurityLevel]').val() == 'AUTHPRIV')\n \t         {\n \t             $(\"#AuthPrivID\").show(\"slow\");\n \t             $(\"#AuthNoPrivID\").show(\"slow\");\n \t         }\n \t      //alert($('select[name=snmpSecurityLevel]').val());\n \t }\n\n \t function validateAndPerformTestCredential(value)\n \t {\n \t     if (trimAll(document.AMActionForm.host.value) == \"\")\n \t     {\n \t         alert('");
/*  2635 */                     out.print(FormatUtil.getString("am.webclient.testCredential.hostName"));
/*  2636 */                     out.write("');\n \t         return false;\n \t     }\n \t     if(!isPositiveInteger(document.AMActionForm.snmpPort.value))\n \t     {\n \t         alert('");
/*  2637 */                     out.print(FormatUtil.getString("am.webclient.testCredential.positiveNumbers"));
/*  2638 */                     out.write("');\n \t         return false;\n \t     }\n \t     testCredential(value);\n \t }\n\n \t function closeMessage(idToClose)\n \t {\n \t     $(\"#\"+trimAll(idToClose)).hide(\"slow\");\n \t }\n\n \t function testCredential(value)\n \t {\n \t     cacheid = (new Date()).getTime();\n \t     deviceToCheck = trimAll(document.AMActionForm.host.value);\n \t     snmpPort = document.AMActionForm.snmpPort.value;\n \t     if(value == 'v1v2')\n \t     {\n \t         snmpCommunity = document.AMActionForm.snmpCommunityString.value;\n \t         snmpVersion = \"v1v2\";\n \t         dataString=\"&method=testSnmpCredential&snmpVersion=\"+value+\"&deviceToCheck=\"+deviceToCheck+\"&snmpCommunity=\"+snmpCommunity+\"&snmpPort=\"+snmpPort+\"&cacheid=\"+cacheid;\n \t     }\n \t     if(value == 'v3')\n \t     {\n \t         snmpSecurityLevel = document.AMActionForm.snmpSecurityLevel.value;\n \t         snmpUserName = document.AMActionForm.snmpUserName.value;\n \t         snmpContextName = document.AMActionForm.snmpContextName.value;\n \t         snmpAuthProtocol = document.AMActionForm.snmpAuthProtocol.value;\n");
/*  2639 */                     out.write(" \t         snmpAuthPassword = document.AMActionForm.snmpAuthPassword.value;\n \t         snmpPrivPassword = document.AMActionForm.snmpPrivPassword.value;\n \t         dataString=\"&method=testSnmpCredential&snmpVersion=\"+value+\"&deviceToCheck=\"+deviceToCheck+\"&snmpSecurityLevel=\"+snmpSecurityLevel+\"&snmpPort=\"+snmpPort+\"&snmpUserName=\"+snmpUserName+\"&snmpContextName=\"+snmpContextName+\"&snmpAuthProtocol=\"+snmpAuthProtocol+\"&snmpAuthPassword=\"+snmpAuthPassword+\"&snmpPrivPassword=\"+snmpPrivPassword+\"&cacheid=\"+cacheid;\n \t     }\n \t         $(\"#testCredentialResult\").show(\"fast\");\n \t         //$(\"#testCredentialResult\").addClass(\"bodytext\");\n \t         $(\"#testCredentialResult\").css(\"color\",\"blue\");\n \t         var waitString = \"");
/*  2640 */                     out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.pleasewait"));
/*  2641 */                     out.write("\";\n \t         $(\"#testCredentialResult\").html(\"<font size=2>\"+waitString+\"</font>\");\n \t         //$(\"#testCredentialResult\").append(\"<img src=\\\"/images/icon_cogwheel.gif\\\"/>\");\n \t         $(\"#testCredentialResult\").append(\"<img src=\\\"/images/LoadingTC.gif\\\"/>\");\n \t         $.ajax({\n \t         type: \"POST\",\n \t         url: \"/testCredential.do\", // Action URL\n \t         data: dataString,                                                        // Query String parameters\n \t         success: function(response)\n \t         {\n \t                                $(\"#testCredentialResult\").html(response);        // Set response into particular div ID ..\n \t                               //callbackMethodCalling();\n \t         }\n \t }); \t \n \t }\n\nfunction showSubnetMessage()\n{\n alert('");
/*  2642 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.subnetmask.alert.whatisthis"));
/*  2643 */                     out.write("');\n}\n\nfunction showPrivateKey()\n{\n  if(document.AMActionForm.sshPKAuth.checked)\n  {\n    javascript:hideRow(\"password\");\n    javascript:showRow(\"privateKey\");\n\tjavascript:showRow(\"passphrase\");\n  }\n  else\n  {\n   javascript:showRow(\"password\");\n   javascript:hideRow(\"privateKey\");\n   javascript:hideRow(\"passphrase\");\n  }\n}\n\nfunction showSAPRouterString(routershow){\n\tif(routershow.checked){\n\tjavascript:showRow(\"routerString\");\t\t//NO I18N\n\t}\n\telse{\n\tjavascript:hideRow(\"routerString\");\t\t//NO I18N\n\tdocument.AMActionForm.routerString.value='';\t\t\t//NO I18N\n\t}\n}\nfunction showMssqlInstance(instanceshow)\n{\n\tif(instanceshow.checked)\n\t{\n\t\t javascript:showRow(\"instanceName\");\n\t\t document.AMActionForm.instance.value='MSSQLSERVER';\n\t}\n\telse\n\t{\n\t\t javascript:hideRow(\"instanceName\");\n\t\t document.AMActionForm.instance.value='';\n\t}\n}\n//IT360 CHANGES\nfunction it360licenseValidationAjaxRequest()\n{\n\t");
/*  2644 */                     if (com.adventnet.appmanager.util.Constants.isIt360)
/*       */                     {
/*  2646 */                       out.write("\n\t\tif(http)\n\t\t{\n\t\t\tvar resourcetype=document.AMActionForm.type.value;\n\t\t\tif( resourcetype!=null && resourcetype=='SYSTEM:9999')\n\t\t\t{\n\t\t\t\tresourcetype=document.AMActionForm.os.value;\n\t\t\t}\n\t\t\tif(resourcetype=='Node')\n\t\t\t{\n\t\t\t\treturn;\t\n\t\t\t}\n\t\t\tvar url = '/adminAction.do?method=it360LicenseValidation&resourceType='+resourcetype;\n\t\t\thttp.open(\"GET\",url,false);\n\t\t\thttp.onreadystatechange =handleLicenseViolation;\n\t\t\thttp.send(null);\n\t\t\t\n\t\t}\n\t");
/*       */                     }
/*  2648 */                     out.write("\n}\nfunction handleLicenseViolation()\n{\n\tif (http.readyState == 4)\n\t{\n\t\tvar result = http.responseText ;\n\t\tresult=trimAll(result);\n\t\tif(result!=null && result!=\"\")\n\t\t{\n\t\t\tdocument.AMActionForm.it360LicenseViolation.value='true';\n\t\t\talert(result);\n\t\t\treturn false;\n\t\t}\n\t\t\n\t}\n}\n//IT360 CHANGES\nfunction fnbeforeFormSubmit()\n{\n\t//IT360 CHANGES\n\t");
/*  2649 */                     if (com.adventnet.appmanager.util.Constants.isIt360)
/*       */                     {
/*  2651 */                       out.write("\n\t\tit360licenseValidationAjaxRequest();\n\t\tvar licViol=document.AMActionForm.it360LicenseViolation.value;\n\t\tif(licViol=='true')\n\t\t{\n\t\t\treturn;\n\t\t}\n\t");
/*       */                     }
/*  2653 */                     out.write("\n\t//IT360 CHANGES\n");
/*  2654 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*       */                       return;
/*  2656 */                     out.write(10);
/*  2657 */                     if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_page_context))
/*       */                       return;
/*  2659 */                     out.write("\n\n}\n//jboss7\nfunction showDomainOptions()\n{\n  if(document.AMActionForm.isDomain.checked)\n  {\n    $(\"#domainOptions\").show(\"slow\");\n\tdocument.AMActionForm.LaunchType.value='DOMAIN';\n  }\n  else\n  {\n   $(\"#domainOptions\").hide(\"fast\");\n   document.AMActionForm.LaunchType.value='STANDALONE';\n  }\n}\n//jboss7\n\n\nfunction fnFormSubmit(value)\n{\n\n");
/*  2660 */                     if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*       */                       return;
/*  2662 */                     out.write("\n\n   \t//alert(document.AMActionForm.prompt.value);\n\t//alert(document.AMActionForm.os.value);\n\tif(trimAll(document.AMActionForm.displayname.value) == '')\n\t{\n        alert(\"");
/*  2663 */                     out.print(FormatUtil.getString("am.webclient.newscript.alert.displaynameempty.text"));
/*  2664 */                     out.write("\");\n\t\tdocument.AMActionForm.displayname.select();\n\t\treturn;\n\t}\n    if(trimAll(document.AMActionForm.host.value) == '')\n\t{\n        alert(\"");
/*  2665 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.name"));
/*  2666 */                     out.write("\");\n\t\tdocument.AMActionForm.host.select();\n\t\treturn;\n\t}\n\tdocument.AMActionForm.host.value=trimAll(document.AMActionForm.host.value);\n");
/*       */                     
/*  2668 */                     FreeEditionDetails freeeditiondetails = FreeEditionDetails.getFreeEditionDetails();
/*       */                     
/*  2670 */                     String usertype = freeeditiondetails.getUserType();
/*  2671 */                     if ((usertype != null) && (usertype.equals("F")))
/*       */                     {
/*       */ 
/*       */ 
/*  2675 */                       out.write("\n     var hostname = trimAll(document.AMActionForm.host.value) ;\n\t if(hostname==\"\")\n\t //if(hostname.indexOf(\",\")!=-1)\n\t {\n\t    alert(\"");
/*  2676 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.name"));
/*  2677 */                       out.write("\");\n\t\tdocument.AMActionForm.host.select();\n\t\treturn;\n\n\t }\n");
/*       */                     }
/*       */                     
/*       */ 
/*  2681 */                     out.write("\n   if(trimAll(document.AMActionForm.netmask.value) == '')\n\t{\n        alert(\"");
/*  2682 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.subnetmask"));
/*  2683 */                     out.write("\");\n\t\tdocument.AMActionForm.netmask.select();\n\t\treturn;\n\t}\n\tvar tempType = document.AMActionForm.type.value;\t\n        if(((tempType != 'SNMP:161') && (tempType != 'All:1008') && (tempType != 'SYSTEM:9999') && (tempType != 'MAIL:25')&& (tempType != '.Net:9080')&& (tempType != 'SAP:00'))||( (tempType != 'SNMP:161') &&(tempType != 'All:1008') && (tempType != 'SYSTEM:9999') && (tempType != 'MAIL:25')&& (tempType != '.Net:9080')&& (tempType != 'SAP:00')))\n\t{\n\t    var temp = trimAll(document.AMActionForm.port.value);\n\t    if(temp == '')\n\t     {\n\t\t    alert(\"");
/*  2684 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.port"));
/*  2685 */                     out.write("\");\n\t\t\tdocument.AMActionForm.port.select();\n\t\t\treturn;\n         }\n\n             ");
/*       */                     
/*  2687 */                     if ((usertype != null) && (usertype.equals("F")))
/*       */                     {
/*       */ 
/*  2690 */                       out.write("\n             if(temp.indexOf(\",\")!=-1)\n\t     \t     {\n\t     \t\t    alert(\"");
/*  2691 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.port"));
/*  2692 */                       out.write("\");\n\t     \t\t\tdocument.AMActionForm.port.select();\n\t     \t\t\treturn;\n             }\n             ");
/*       */                     }
/*       */                     
/*       */ 
/*  2696 */                     out.write("\n\n\t}\n\tif(tempType == 'MAIL:25')\n\t{\n\t    var temp = trimAll(document.AMActionForm.port.value);\n\t    if((temp == '') || !(isPositiveInteger(temp)))\n\t\t{\n\t\t    alert(\"");
/*  2697 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.smtpport"));
/*  2698 */                     out.write("\");\n\t\t\tdocument.AMActionForm.port.select();\n\t\t\treturn;\n\t\t}\n\t\ttemp = trimAll(document.AMActionForm.emailid.value);\n\t\tif((temp == '')  )\n\t\t{\n\t\t    alert(\"");
/*  2699 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.email"));
/*  2700 */                     out.write("\");\n\t\t\tdocument.AMActionForm.emailid.select();\n\t\t\treturn;\n\t\t}\n\t\tif (echeck(temp)==false){\n\t\t\talert('");
/*  2701 */                     out.print(FormatUtil.getString("am.webclient.talkback.alert.invalidemailid"));
/*  2702 */                     out.write("')\n\t\t\tdocument.AMActionForm.emailid.select();\n\t\t\tdocument.AMActionForm.emailid.focus()\n\t\t\treturn false\n\t\t}\n\t\tif(document.AMActionForm.popenabled.checked == true)\n\t\t{\n                    temp = trimAll(document.AMActionForm.popHost.value);\n                    if((temp == '')  )\n                    {\n                        alert(\"");
/*  2703 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.popname"));
/*  2704 */                     out.write("\");\n                            document.AMActionForm.popHost.select();\n                            return;\n                    }\n                    temp = trimAll(document.AMActionForm.popPort.value);\n                    if((temp == '') || !(isPositiveInteger(temp)))\n                    {\n                        alert(\"");
/*  2705 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.popport"));
/*  2706 */                     out.write("\");\n                            document.AMActionForm.popPort.select();\n                            return;\n                    }\n\n                    temp = trimAll(document.AMActionForm.username.value);\n                    if((temp == '')  )\n                    {\n                        alert(\"");
/*  2707 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.username"));
/*  2708 */                     out.write("\");\n                            document.AMActionForm.username.select();\n                            return;\n                    }\n                    temp = trimAll(document.AMActionForm.password.value);\n                    if((temp == '')  )\n                    {\n                        if(!confirm('");
/*  2709 */                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*       */                       return;
/*  2711 */                     out.write("'))\n                        {\n                            return;\n                        }\n                    }\n\t\t}\n\t}\n\tif(tempType == 'WEBSPHERE:9080')\n\t{\n\t    var temp = trimAll(document.AMActionForm.soapport.value);\n\t    var version=document.AMActionForm.version.value;\n\t    if((temp == '') || !(isPositiveInteger(temp)))\n\t\t{\n\t\t    alert(\"");
/*  2712 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.port"));
/*  2713 */                     out.write("\");\n\t\t\tdocument.AMActionForm.soapport.select();\n\t\t\treturn;\n\t\t}\n\t\tif(version==undefined || version==0)\n\t\t{\n\t\t\talert(\"");
/*  2714 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.version.alert"));
/*  2715 */                     out.write("\");\n\t\t\tdocument.AMActionForm.version.select();\n\t\t\treturn;\n\t\t}\n\n\t}\n\tif(tempType == 'APACHE:80')\n\t{\n\t\tif(document.AMActionForm.serverstatusurl.checked==true)\n\t\t{\n\t\t\tif(trimAll(document.AMActionForm.apacheurl.value)=='')\n\t\t\t{\n\t\t\t\talert(\"");
/*  2716 */                     out.print(FormatUtil.getString("am.webclient.apache.enterstatusurl"));
/*  2717 */                     out.write("\");\n\t\t\t\treturn;\n\t\t\t}\n\t\t}\n\t}\n\tif(tempType == 'PHP:80')\n\t{\n\t    var serverpath=trimAll(document.AMActionForm.serverpath.value);\n\t    if(serverpath == '')\n\t    {\n                alert(\"");
/*  2718 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.phppath"));
/*  2719 */                     out.write("\");\n                document.AMActionForm.serverpath.select();\n                return;\n\t    }\n\t}\n\tif(tempType != 'All:1008')\n\t{\n\t    var temp = trimAll(document.AMActionForm.pollInterval.value);\n\t    if((temp == '') || !(isPositiveInteger(temp)) || (temp == '0'))\n\t    {\n\t\t\talert(\"");
/*  2720 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.polling"));
/*  2721 */                     out.write("\");\n\t\t\tdocument.AMActionForm.pollInterval.select();\n\t\t\treturn;\n\t    }\n\t}\n\tif((tempType == 'MYSQLDB:3306') || (tempType == 'MSSQLDB:1433')  )\n\t{\n\t   if((tempType == 'MYSQLDB:3306'))\n\t   {\n\t    if(trimAll(document.AMActionForm.instance.value) == '')\n\t\t{\n\t\t    alert(\"");
/*  2722 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.instancename"));
/*  2723 */                     out.write("\");\n\t\t\tdocument.AMActionForm.instance.select();\n\t\t\treturn;\n\t\t}\n\t   }\n\t   if(tempType == 'MSSQLDB:1433')\n\t   {\n\t    var authType=\"\";\n\t    for (var i=0; i < document.AMActionForm.authType.length; i++)\n\t    {\n\t\t   if (document.AMActionForm.authType[i].checked)\n\t\t   {\n\t\t\t    authType= document.AMActionForm.authType[i].value;\n\t\t   }\n\t    }\n\t   }\n\t    if(tempType == 'MSSQLDB:1433' && authType=='Windows')\n\t\t{\n\t      var windowUser = document.AMActionForm.username.value;\n\t\t  if(windowUser == '')\n\t\t  {\n\t\t\t alert(\"");
/*  2724 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.username"));
/*  2725 */                     out.write("\");\n\t\t\t document.AMActionForm.username.select();\n\t\t\t return;\n\t\t  }\n\t\t  else\n\t\t  {\n\t\t\t var hasDomainName = windowUser.indexOf(\"\\\\\");\n\t\t\t if(hasDomainName == -1)\n\t\t\t {\n\t\t       alert(\"");
/*  2726 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.message.winowsAuth.alert"));
/*  2727 */                     out.write("\");\n\t\t\t   document.AMActionForm.username.select();\n\t\t\t   return;\n\t\t\t }\n\t\t  }\n\t\t}\n\t    if(trimAll(document.AMActionForm.username.value) == '')\n\t    {\n\t\t    alert(\"");
/*  2728 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.username"));
/*  2729 */                     out.write("\");\n\t\t    document.AMActionForm.username.select();\n\t\t    return;\n\t    }\n\n\t}\n\tif(tempType == 'ORACLEDB:1521' ||  (tempType == 'DB2:50000') )\n\t{\n\t\tif(trimAll(document.AMActionForm.username.value) == '')\n\t\t{\n\t\t\talert(\"");
/*  2730 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.username"));
/*  2731 */                     out.write("\");\n\t\t\tdocument.AMActionForm.username.select();\n\t\t\treturn;\n\t\t}\n\t\tif(trimAll(document.AMActionForm.password.value) == '')\n\t\t{\n\t\t\talert(\"");
/*  2732 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.password"));
/*  2733 */                     out.write("\");\n\t\t\tdocument.AMActionForm.password.select();\n\t\t\treturn;\n\t\t}\n\n\t    if(trimAll(document.AMActionForm.instance.value) == '')\n\t\t{\n\t\t    alert(\"");
/*  2734 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.instancename"));
/*  2735 */                     out.write("\");\n\t\t\tdocument.AMActionForm.instance.select();\n\t\t\treturn;\n\t\t}\n\t}\n\tif((tempType == 'SYBASEDB:5000'))\n\t{\n\n\t\tif(trimAll(document.AMActionForm.username.value) == '')\n\t\t{\n\t\t\talert(\"");
/*  2736 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.username"));
/*  2737 */                     out.write("\");\n\t\t\tdocument.AMActionForm.username.select();\n\t\t\treturn;\n\t\t}\n\t\tif(trimAll(document.AMActionForm.password.value) == '')\n\t\t{\n\n\n                    if(!confirm('");
/*  2738 */                     if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*       */                       return;
/*  2740 */                     out.write("'))\n                        {\n                            return;\n                        }\n\n\t\t}\n\n\t    if(trimAll(document.AMActionForm.instance.value) == '')\n\t\t{\n\t\t    alert(\"");
/*  2741 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.instancename"));
/*  2742 */                     out.write("\");\n\t\t\tdocument.AMActionForm.instance.select();\n\t\t\treturn;\n\t\t}\n\t}\n\tif((tempType == 'SYSTEM:9999'))\n\t{\n\n\t   if(trimAll(document.AMActionForm.os.value) == 'Node')\n\t    {\n\t     alert(\"");
/*  2743 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.ostype"));
/*  2744 */                     out.write("\");\n\t     document.AMActionForm.os.focus();\n\t     return;\n\t    }\n    \tvar mode=document.AMActionForm.mode[document.AMActionForm.mode.selectedIndex].value;\n\t\tif(mode != 'SNMP' && trimAll(document.AMActionForm.username.value) == '')\n\t\t{\n\t\t\talert(\"");
/*  2745 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.username"));
/*  2746 */                     out.write("\");\n\t\t\tdocument.AMActionForm.username.select();\n\t\t\treturn;\n\t\t}\n\t\tif(mode =='SSH')\n\t\t{\n\t\t\tif(document.AMActionForm.sshPKAuth.checked)\n\t\t\t{\n\t\t\t\tvar sshKey = trimAll(document.AMActionForm.description.value);\n\t\t\t\tif(sshKey =='')\n\t\t\t\t{\n\t\t\t\t\talert(\"");
/*  2747 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh.privateKey.alert"));
/*  2748 */                     out.write("\");\n\t\t\t\t\tdocument.AMActionForm.description.select();\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tvar passWord = trimAll(document.AMActionForm.password.value);\n\t\t\t\tif(passWord=='')\n\t\t\t\t{\n\t\t\t\t\talert(\"");
/*  2749 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.password"));
/*  2750 */                     out.write("\");\n\t\t\t\t\tdocument.AMActionForm.password.select();\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\n\t\tif(mode != 'SNMP' && mode !='SSH'  && trimAll(document.AMActionForm.password.value) == '')\n\t\t{\n\t\t\talert(\"");
/*  2751 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.password"));
/*  2752 */                     out.write("\");\n\t\t\tdocument.AMActionForm.password.select();\n\t\t\treturn;\n\t\t}\n\t\tif(mode == 'SNMP' || mode == 'TELNET' || mode == 'SSH')\n\t\t{\n\t\t\tvar temp=document.AMActionForm.snmptelnetport.value;\n\t\t  if(trimAll(temp) == '' || !(isValidPort(temp)))\n\t\t  {\n\t\t  \talert(\"");
/*  2753 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.port"));
/*  2754 */                     out.write("\");\n\t\t\treturn;\n\t\t  }\n\t\t}\n\n\t    //enableAll();\n\t }\n\n\n\n\n\n\t  if((tempType == 'JBoss:8080'))\n\t   {\n\n\t\t\t   if(document.AMActionForm.version.selectedIndex==0)\n\t\t\t   {\n\t\t\t\t   alert(\"");
/*  2755 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.jbossversion"));
/*  2756 */                     out.write("\");\n\t\t\t\t   return;\n\t\t\t   }\n\n\t   }\n          if((tempType == 'ORACLEAS:7200'))\n\t   \t   {\n\n\n \t   \t\t\t  if(document.AMActionForm.version.selectedIndex==0)\n \t\t\t\t  \t\t\t   {\n \t\t\t\t  \t\t\t\t   alert(\"");
/*  2757 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.oracleasversion"));
/*  2758 */                     out.write("\");\n \t\t\t\t  \t\t\t\t   return;\n \t\t\t   }\n\n \t   }\n\n\t  if(tempType == 'WEBLOGIC:7001' || tempType == 'WLI:7001')\n\t   {\n\n\t\t\t   if(tempType == 'WEBLOGIC:7001' && document.AMActionForm.version.selectedIndex==0)\n\t\t\t   {\n\t\t\t\t   alert(\"");
/*  2759 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.weblogicversion"));
/*  2760 */                     out.write("\");\n\t\t\t\t   return;\n\t\t\t   }\n\n\t   }\n\tif((tempType == 'Tomcat:8080'))\n\t{\n\t\tvar test=document.AMActionForm.version[document.AMActionForm.version.selectedIndex].value;\n\t\tif(test ==0)\n\t\t{\n\t\t\talert(\"");
/*  2761 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.tomcatversion"));
/*  2762 */                     out.write("\");\n\t\t\treturn;\n\t\t}\n\t\tif(test == '5' || test == '6' || test == '7')\n\t\t{\n     \t\tif(trimAll(document.AMActionForm.username.value) == '')\n\t\t\t{\n\t\t\t\talert(\"");
/*  2763 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.username"));
/*  2764 */                     out.write("\");\n\t\t\t\treturn;\n\t\t\t}\n\t\t\tif(trimAll(document.AMActionForm.password.value) == '')\n\t\t\t{\n\t\t\t\talert(\"");
/*  2765 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.password"));
/*  2766 */                     out.write("\");\n\t\t\t\treturn;\n\t\t\t}\n\t\t\tvar managerurl=trimAll(document.AMActionForm.tomcatmanagerurl.value);\n\t\t\tif(managerurl=='') {\n\t\t\t\tmanagerurl=\"/manager\"; //NO I18N\n\t\t\t\tdocument.AMActionForm.tomcatmanagerurl.value=managerurl;\n\t\t\t}\n\t\t\tif(managerurl.indexOf('/')!=0){\n\t\t\t\tdocument.AMActionForm.tomcatmanagerurl.value=\"/\"+managerurl;\n\t\t\t}\n\t\t}\n\t}\n\n\tif((tempType == 'Exchange:25'))\n\t{\n\t\t\tvar test=document.AMActionForm.version[document.AMActionForm.version.selectedIndex].value;\n\t\t\tif(test == 'unknown')\n\t\t\t{\n\t\t\t\talert(\"");
/*  2767 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.exchangeversion"));
/*  2768 */                     out.write("\");\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t\tif(trimAll(document.AMActionForm.username.value) == '')\n\t\t\t\t{\n\t\t\t\t\talert(\"");
/*  2769 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.username"));
/*  2770 */                     out.write("\");\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t\tif(trimAll(document.AMActionForm.password.value) == '')\n\t\t\t\t{\n\t\t\t\t\talert(\"");
/*  2771 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.password"));
/*  2772 */                     out.write("\");\n\t\t\t\t\treturn;\n\t\t\t\t}\n\n\t}\n\n\t\tif((tempType == '.Net:9080'))\n\t\t{\n\n\t\t\t\t\tif(trimAll(document.AMActionForm.username.value) == '')\n\t\t\t\t\t{\n\t\t\t\t\t\talert(\"");
/*  2773 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.username"));
/*  2774 */                     out.write("\");\n\t\t\t\t\t\treturn;\n\t\t\t\t\t}\n\t\t\t\t\tif(trimAll(document.AMActionForm.password.value) == '')\n\t\t\t\t\t{\n\t\t\t\t\t\talert(\"");
/*  2775 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.password"));
/*  2776 */                     out.write("\");\n\t\t\t\t\t\treturn;\n\t\t\t\t\t}\n\n\t\t}\n\n\t\tif((tempType == 'SAP:00'))\n\t\t{\n\t\t\t");
/*       */                     
/*  2778 */                     if (!FreeEditionDetails.getFreeEditionDetails().isSAPAllowed())
/*       */                     {
/*       */ 
/*  2781 */                       out.write("\n\t\t\talert(\"");
/*  2782 */                       out.print(FormatUtil.getString("am.webclient.sap.freeedition.notsupported"));
/*  2783 */                       out.write("\");\n\t\t\treturn;\n\t\t\t");
/*       */                     }
/*       */                     
/*       */ 
/*  2787 */                     out.write("\n\t\t\tif(document.AMActionForm.usedRouterString.checked)\n\t\t\t{\n\t\t\t\tif(trimAll(document.AMActionForm.routerString.value) == '')\n\t\t\t\t{\n\t\t\t\t\talert(\"");
/*  2788 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.routerString"));
/*  2789 */                     out.write("\");\n\t\t\t\t\tdocument.AMActionForm.routerString.select();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t}\n\t\t\tdocument.AMActionForm.routerString.value=trimAll(document.AMActionForm.routerString.value);\n\t\t\tif(trimAll(document.AMActionForm.logonClient.value) == '')\n\t\t\t{\n\t\t\t\talert(\"");
/*  2790 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.logonclient"));
/*  2791 */                     out.write("\");\n\t\t\t\tdocument.AMActionForm.logonClient.select();\n\t\t\t\treturn;\n\t\t\t}\n\t\t\tif(trimAll(document.AMActionForm.port.value) == '')\n\t\t\t{\n\t\t\t\talert(\"");
/*  2792 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.systemnumber"));
/*  2793 */                     out.write("\");\n\t\t\t\tdocument.AMActionForm.port.select();\n\t\t\t\treturn;\n\t\t\t}\n\t\t\tif(trimAll(document.AMActionForm.username.value) == '')\n\t\t\t{\n\t\t\t\talert(\"");
/*  2794 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.username"));
/*  2795 */                     out.write("\");\n\t\t\t\tdocument.AMActionForm.username.select();\n\t\t\t\treturn;\n\t\t\t}\n\t\t\tif(trimAll(document.AMActionForm.password.value) == '')\n\t\t\t{\n\t\t\t\talert(\"");
/*  2796 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.password"));
/*  2797 */                     out.write("\");\n\t\t\t\tdocument.AMActionForm.username.select();\n\t\t\t\treturn;\n\t\t\t}\n\n\t\t}\n\n\tif(tempType == 'SNMP:161')\n\t{\n\t\tvar temp=document.AMActionForm.timeout.value;\n\t\tif(trimAll(temp) == '' || !(isPositiveInteger(temp)))\n\t\t{\n\t\t  \talert(\"");
/*  2798 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.timeout"));
/*  2799 */                     out.write("\");\n\t\t\treturn;\n\t\t}\n\t\tif(trimAll(document.AMActionForm.snmpCommunityString.value) == '')\n\t\t{\n\t\t\talert(\"");
/*  2800 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.community"));
/*  2801 */                     out.write("\");\n\t\t\treturn;\n\t\t}\n\t}\n\tif(value==1 || value==3)\n\t\t{\n\t\tdocument.AMActionForm.addtoha.value=\"true\";\n\t\tif(trimAll(document.AMActionForm.haid.value) == '' || trimAll(document.AMActionForm.haid.value) == '-')\n\t\t\t{\n\t\t\talert(\"");
/*  2802 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.password", new String[] { MGSTR }));
/*  2803 */                     out.write("\");\n\t\t\treturn;\n\t\t\t}\n\t\t}\n\tif(value==2)\n\t\t{\n \t\tdocument.AMActionForm.addtoha.value=\"false\";\n\t\t}\n\t\tif(value==3)\n\t\t{\n\t\t\tdocument.AMActionForm.action=\"/adminAction.do?method=configureHostDiscovery&value=3\";\n\t\t}\n\t\telse\n\t\t{\n    \t\t\tdocument.AMActionForm.action=\"/adminAction.do?method=configureHostDiscovery\";\n    \t\t}\n\n    \t if(value=='istestavailability') {\n    \t \tdocument.AMActionForm.addtoha.value=\"false\";\n    \t \tdocument.AMActionForm.istestavailability.value = 'true';\n    \t }\n\tif(document.AMActionForm.authEnabled && document.AMActionForm.authEnabled.checked)\n\t    {\n\t\ttemp = trimAll(document.AMActionForm.username.value);\n                    if((temp == '')  )\n                    {\n                        alert(\"");
/*  2804 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.username"));
/*  2805 */                     out.write("\");\n                            document.AMActionForm.username.select();\n                            return;\n                    }\n                    temp = trimAll(document.AMActionForm.password.value);\n                    if((temp == '')  )\n                    {\n                        if(!confirm('");
/*  2806 */                     if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*       */                       return;
/*  2808 */                     out.write("'))\n                        {\n                            return;\n                        }\n                    }\n\t    }\n\n\n /***  IT360-1762 ISSUES CHANGES STARTS HERE ***/\n\n\t");
/*  2809 */                     if (EnterpriseUtil.isIt360MSPEdition()) {
/*  2810 */                       out.write("\n\n    if (document.AMActionForm.organization.value== \"-\")\n     {\n    \talert(\"");
/*  2811 */                       out.print(FormatUtil.getString("it360.addnewmonitor.err.checkcustomer"));
/*  2812 */                       out.write("\");\n    \treturn;\n     }\n\n    if (trimAll(document.AMActionForm.haid.value) == '' || document.AMActionForm.haid.value== \"-\")\n     {\n        alert(\"");
/*  2813 */                       out.print(FormatUtil.getString("it360.addnewmonitor.err.checksite"));
/*  2814 */                       out.write("\");\n        return;\n     }\n    ");
/*       */                     }
/*  2816 */                     out.write("\n    \n    ");
/*       */                     
/*  2818 */                     if (EnterpriseUtil.isAdminServer())
/*       */                     {
/*       */ 
/*  2821 */                       out.write("                                \n        if (document.AMActionForm.masSelectType[1].checked) {\n        \tvar selectedVal=document.AMActionForm.masGroupName.value;\n        \tif (selectedVal != null && selectedVal == \"-\") {\n                alert('");
/*  2822 */                       out.print(FormatUtil.getString("am.webclient.admin.add.monitor.select.masgroup.text"));
/*  2823 */                       out.write("');\n                document.AMActionForm.masGroupName.focus();\n                return;\n            }                                \t\n        } else if (document.AMActionForm.masSelectType[2].checked) {\n        \tselectedVal=document.AMActionForm.selectedServer.value;\n        \tif (selectedVal != null && selectedVal == \"-\") {\n                alert('");
/*  2824 */                       out.print(FormatUtil.getString("am.webclient.admin.addmonitor.select.mas.text"));
/*  2825 */                       out.write("');\n                document.AMActionForm.selectedServer.focus();\n                return;\n            }                                 \t\n        }\n    ");
/*       */                     }
/*       */                     
/*       */ 
/*  2829 */                     out.write(" \n    \n    ");
/*       */                     
/*  2831 */                     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  2832 */                     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  2833 */                     _jspx_th_c_005fif_005f3.setParent(null);
/*       */                     
/*  2835 */                     _jspx_th_c_005fif_005f3.setTest("${checkForMonitorGroup}");
/*  2836 */                     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  2837 */                     if (_jspx_eval_c_005fif_005f3 != 0) {
/*       */                       for (;;) {
/*  2839 */                         out.write("\n\t\tvar haidValue = document.AMActionForm.haid.value\n\t\tif(haidValue == '-' || haidValue == ''){\n\t\talert(\"");
/*  2840 */                         out.print(FormatUtil.getString("am.webclient.newmonitor.selectmg.text"));
/*  2841 */                         out.write("\")\n\t\treturn\n\t\t}\n\t");
/*  2842 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  2843 */                         if (evalDoAfterBody != 2)
/*       */                           break;
/*       */                       }
/*       */                     }
/*  2847 */                     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  2848 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*       */                     }
/*       */                     else {
/*  2851 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  2852 */                       out.write("\n\n/***  IT360-1762 ISSUES CHANGES ENDS HERE ***/\n\n\t document.AMActionForm.submit();\n\n}\n\nfunction testAvailability() {\n\tfnFormSubmit('istestavailability');\n\n}\n\nfunction showWTASettings()  {\n\n    if(document.AMActionForm.WTEnabled.checked)\n    {\n        var newDisplay = \"block\";\n        if (document.all) newDisplay = \"block\"; //IE4+ specific code\n        else newDisplay = \"table-row\";\n        //var row1 = document.getElementById(\"usewebserverportrow\");\n        //row1.style.display=newDisplay;\n        var row2 = document.getElementById(\"wtaportrow\");\n        row2.style.display=newDisplay;\n    }\n    else\n    {\n        //document.getElementById(\"usewebserverportrow\").style.display=\"none\";\n        document.getElementById(\"wtaportrow\").style.display=\"none\";\n    }\n}\n\nfunction showAdvancedOptions(wasAdvcheck)\n{\n   if(wasAdvcheck.checked)\n   {\n   javascript:showRow(\"wasAdvanced\");\n   javascript:showRow(\"washelp\");\n   }\n   else\n   {\n   javascript:hideRow(\"wasAdvanced\");\n   javascript:hideRow(\"washelp\");\n   }\n\n\n}\n\n\n\nfunction formReload()\n");
/*  2853 */                       out.write("{\n\n\n\tvar v=document.AMActionForm.type.value;\n\t//var portNo=v.substring(v.indexOf(\":\")+1,v.length);\n\t//document.AMActionForm.method=\"post\";\n    if((v==\"WTA:55555\")||(v==\".Net:9080\"))\n    {\n\n\n       document.AMActionForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&type=\"+v+\"&hideFieldsForIT360=");
/*  2854 */                       out.print(request.getParameter("hideFieldsForIT360"));
/*  2855 */                       out.write("\";\n    enableAll();\n    document.AMActionForm.submit();\n\n\n\n    }\n    else{\n    document.AMActionForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&type=\"+v+\"&hideFieldsForIT360=");
/*  2856 */                       out.print(request.getParameter("hideFieldsForIT360"));
/*  2857 */                       out.write("\";\n    enableAll();\n    document.AMActionForm.submit();\n    }\n}\n\nfunction myOnLoad(){\n\tif(");
/*  2858 */                       out.print(EnterpriseUtil.isAdminServer());
/*  2859 */                       out.write(") {\n\t\tvar radioObj = document.AMActionForm.masSelectType;\n\t\tif (radioObj !=null && radioObj != \"undefined\") {\n\t\t\tvar val='0';\n\t\t\tif (radioObj[1].checked) {\n\t\t\t\tval='1';\n\t\t\t} else if (radioObj[2].checked){\n\t\t\t\tval='2';\n\t\t\t}\n\t\t\tshowAsPerSelection(val);\n\t\t}\t\n\t}\n}\n</script>\n\n\n");
/*       */                       
/*  2861 */                       String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/*  2862 */                       if (hideFieldsForIT360 == null)
/*       */                       {
/*  2864 */                         hideFieldsForIT360 = (String)request.getAttribute("hideFieldsForIT360");
/*       */                       }
/*       */                       
/*  2867 */                       boolean hideFields = false;
/*  2868 */                       String hideStyle = "";
/*  2869 */                       if ((hideFieldsForIT360 != null) && (hideFieldsForIT360.equals("true")))
/*       */                       {
/*  2871 */                         hideFields = true;
/*  2872 */                         hideStyle = "hideContent";
/*       */                       }
/*       */                       
/*  2875 */                       AMActionForm form = (AMActionForm)request.getAttribute("AMActionForm");
/*  2876 */                       String type = null;
/*       */                       
/*  2878 */                       if (form != null)
/*       */                       {
/*  2880 */                         type = form.getType();
/*       */                       }
/*  2882 */                       if ((type == null) || (type.equals("1")))
/*       */                       {
/*  2884 */                         type = "All:1008";
/*  2885 */                         form.setPort("1008");
/*       */                       }
/*  2887 */                       type = type.contains(":") ? type.substring(0, type.indexOf(":")) : type;
/*  2888 */                       request.setAttribute("type", type);
/*  2889 */                       if (type.equals("WEBLOGIC"))
/*       */                       {
/*  2891 */                         request.setAttribute("HelpKey", "Discover WebLogic Server");
/*       */                       }
/*  2893 */                       else if (type.equals("WLI"))
/*       */                       {
/*  2895 */                         request.setAttribute("HelpKey", "Discover WLI Server");
/*       */ 
/*       */                       }
/*  2898 */                       else if (type.equals("WEBSPHERE"))
/*       */                       {
/*  2900 */                         request.setAttribute("HelpKey", "Discover WebSphere Server");
/*       */                       }
/*  2902 */                       else if (type.equals("Tomcat"))
/*       */                       {
/*  2904 */                         request.setAttribute("HelpKey", "Discover Tomcat Server");
/*       */                       }
/*  2906 */                       else if (type.equals("PHP"))
/*       */                       {
/*  2908 */                         request.setAttribute("HelpKey", "Discover PHP Server");
/*       */                       }
/*  2910 */                       else if (type.equals("JBoss"))
/*       */                       {
/*  2912 */                         request.setAttribute("HelpKey", "Discover JBoss Server");
/*       */                       }
/*  2914 */                       else if (type.equals(".Net"))
/*       */                       {
/*  2916 */                         request.setAttribute("HelpKey", "Discover .Net Server");
/*       */                       }
/*  2918 */                       else if (type.equals("WTA"))
/*       */                       {
/*  2920 */                         request.setAttribute("HelpKey", "Discover WTA Monitor");
/*       */                       }
/*  2922 */                       else if (type.equals("MYSQLDB"))
/*       */                       {
/*  2924 */                         request.setAttribute("HelpKey", "Discover MySQL Server");
/*       */                       }
/*  2926 */                       else if (type.equals("MSSQLDB"))
/*       */                       {
/*  2928 */                         request.setAttribute("HelpKey", "Discover MSSQL Server");
/*       */                       }
/*  2930 */                       else if (type.equals("ORACLEDB"))
/*       */                       {
/*  2932 */                         request.setAttribute("HelpKey", "Discover Oracle Server");
/*       */                       }
/*  2934 */                       else if (type.equals("DB2"))
/*       */                       {
/*  2936 */                         request.setAttribute("HelpKey", "Discover DB2 Server");
/*       */                       }
/*  2938 */                       else if (type.equals("SYBASEDB"))
/*       */                       {
/*  2940 */                         request.setAttribute("HelpKey", "Discover Sybase Server");
/*       */                       }
/*  2942 */                       else if (type.equals("MAIL"))
/*       */                       {
/*  2944 */                         request.setAttribute("HelpKey", "Discover Mail Server");
/*       */                       }
/*  2946 */                       else if (type.equals("WEB"))
/*       */                       {
/*  2948 */                         request.setAttribute("HelpKey", "Discover Web Server");
/*       */                       }
/*  2950 */                       else if (type.equals("APACHE"))
/*       */                       {
/*  2952 */                         request.setAttribute("HelpKey", "Discover Apache Server");
/*       */                       }
/*  2954 */                       else if (type.equals("IIS"))
/*       */                       {
/*  2956 */                         request.setAttribute("HelpKey", "Discover IIS Server");
/*       */                       }
/*  2958 */                       else if (type.equals("SERVICE"))
/*       */                       {
/*  2960 */                         request.setAttribute("HelpKey", "Service Monitoring");
/*       */                       }
/*  2962 */                       else if (type.equals("TELNET"))
/*       */                       {
/*  2964 */                         request.setAttribute("HelpKey", "Discover Telnet");
/*       */                       }
/*  2966 */                       else if (type.equals("SNMP"))
/*       */                       {
/*  2968 */                         request.setAttribute("HelpKey", "Discover SNMP AGENT");
/*       */                       }
/*  2970 */                       else if (type.equals("RMI"))
/*       */                       {
/*  2972 */                         request.setAttribute("HelpKey", "Discover RMI-JMX Adapter");
/*       */                       }
/*  2974 */                       else if (type.equals("SYSTEM"))
/*       */                       {
/*  2976 */                         request.setAttribute("HelpKey", "Discover System");
/*       */                       }
/*  2978 */                       else if (type.equals("All"))
/*       */                       {
/*  2980 */                         request.setAttribute("HelpKey", "All Services");
/*       */                       }
/*  2982 */                       else if (type.equals("ORACLEAS"))
/*       */                       {
/*  2984 */                         request.setAttribute("HelpKey", "Discover Oracle AS");
/*  2985 */                       } else if (type.equals("SAP"))
/*       */                       {
/*  2987 */                         request.setAttribute("HelpKey", "Configure SAP");
/*       */                       }
/*  2989 */                       else if (type.equals("JDK1.5"))
/*       */                       {
/*  2991 */                         request.setAttribute("HelpKey", "Configure JDK");
/*       */ 
/*       */                       }
/*  2994 */                       else if (type.equals("Exchange"))
/*       */                       {
/*  2996 */                         request.setAttribute("HelpKey", "Configure Exchange");
/*       */                       }
/*  2998 */                       else if (type.equals("JMX1.2-MX4J-RMI"))
/*       */                       {
/*  3000 */                         request.setAttribute("HelpKey", "Configure JMX");
/*       */                       }
/*       */                       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  3007 */                       out.write(10);
/*       */                       
/*  3009 */                       boolean isDiscoveryComplete = false;
/*  3010 */                       boolean isDiscoverySuccess = false;
/*  3011 */                       String headerandTab = "/jsp/header.jsp?tabtoselect=1";
/*  3012 */                       if (com.adventnet.appmanager.util.Constants.isIt360) {
/*  3013 */                         headerandTab = "/jsp/header.jsp?oldtab=6";
/*       */                       }
/*       */                       
/*  3016 */                       out.write(10);
/*       */                       
/*  3018 */                       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/*  3019 */                       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/*  3020 */                       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*       */                       
/*  3022 */                       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/*  3023 */                       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/*  3024 */                       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*       */                         for (;;)
/*       */                         {
/*  3027 */                           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/*  3028 */                           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/*  3029 */                           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*       */                           
/*  3031 */                           _jspx_th_tiles_005fput_005f0.setName("title");
/*       */                           
/*  3033 */                           _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.newscript.addmonitors.text"));
/*  3034 */                           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/*  3035 */                           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/*  3036 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*       */                           }
/*       */                           
/*  3039 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/*  3040 */                           out.write(10);
/*       */                           
/*  3042 */                           PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/*  3043 */                           _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/*  3044 */                           _jspx_th_tiles_005fput_005f1.setParent(_jspx_th_tiles_005finsert_005f0);
/*       */                           
/*  3046 */                           _jspx_th_tiles_005fput_005f1.setName("Header");
/*       */                           
/*  3048 */                           _jspx_th_tiles_005fput_005f1.setValue(headerandTab);
/*  3049 */                           int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/*  3050 */                           if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/*  3051 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1); return;
/*       */                           }
/*       */                           
/*  3054 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/*  3055 */                           out.write(32);
/*  3056 */                           out.write(10);
/*       */                           
/*  3058 */                           if ((EnterpriseUtil.isIt360MSPEdition()) && (!com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.getInstance().checkCustomerAvailability()))
/*       */                           {
/*       */ 
/*  3061 */                             out.write(10);
/*       */                             
/*  3063 */                             PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/*  3064 */                             _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/*  3065 */                             _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*       */                             
/*  3067 */                             _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*       */                             
/*  3069 */                             _jspx_th_tiles_005fput_005f2.setType("string");
/*  3070 */                             int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/*  3071 */                             if (_jspx_eval_tiles_005fput_005f2 != 0) {
/*  3072 */                               if (_jspx_eval_tiles_005fput_005f2 != 1) {
/*  3073 */                                 out = _jspx_page_context.pushBody();
/*  3074 */                                 _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/*  3075 */                                 _jspx_th_tiles_005fput_005f2.doInitBody();
/*       */                               }
/*       */                               for (;;) {
/*  3078 */                                 out.write(" \n\t\t   <table align=\"center\" valign=\"center\"  width=\"45%\" height=\"10%\" border=\"0\" class=\"messageboxfailure\">\n\t\t\t<tr>\n                        <td align=\"center\" valign=\"center\"><img src=\"/images/prereq_notconfigured.gif\" hspace=\"0\"></td>\n\t\t            \t<td align=\"left\" valign=\"center\" class=\"bodytext\" nowrap ><b>");
/*  3079 */                                 out.print(FormatUtil.getString("am.webclient.custmgmt.associatecustomer"));
/*  3080 */                                 out.write(" </b> </td>\n\t\t\t</tr>\n\t\t</table>\n");
/*  3081 */                                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/*  3082 */                                 if (evalDoAfterBody != 2)
/*       */                                   break;
/*       */                               }
/*  3085 */                               if (_jspx_eval_tiles_005fput_005f2 != 1) {
/*  3086 */                                 out = _jspx_page_context.popBody();
/*       */                               }
/*       */                             }
/*  3089 */                             if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/*  3090 */                               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*       */                             }
/*       */                             
/*  3093 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/*  3094 */                             out.write(10);
/*       */ 
/*       */                           }
/*       */                           else
/*       */                           {
/*       */ 
/*  3100 */                             out.write(10);
/*       */                             
/*  3102 */                             PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/*  3103 */                             _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/*  3104 */                             _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*       */                             
/*  3106 */                             _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*       */                             
/*  3108 */                             _jspx_th_tiles_005fput_005f3.setType("string");
/*  3109 */                             int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/*  3110 */                             if (_jspx_eval_tiles_005fput_005f3 != 0) {
/*  3111 */                               if (_jspx_eval_tiles_005fput_005f3 != 1) {
/*  3112 */                                 out = _jspx_page_context.pushBody();
/*  3113 */                                 _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/*  3114 */                                 _jspx_th_tiles_005fput_005f3.doInitBody();
/*       */                               }
/*       */                               for (;;) {
/*  3117 */                                 out.write(32);
/*       */                                 
/*  3119 */                                 FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/*  3120 */                                 _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  3121 */                                 _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*       */                                 
/*  3123 */                                 _jspx_th_html_005fform_005f0.setAction("/adminAction.do");
/*  3124 */                                 int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  3125 */                                 if (_jspx_eval_html_005fform_005f0 != 0) {
/*       */                                   for (;;) {
/*  3127 */                                     out.write(10);
/*  3128 */                                     out.write(9);
/*  3129 */                                     if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                       return;
/*  3131 */                                     out.write(10);
/*  3132 */                                     if (_jspx_meth_am_005fhiddenparam_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                       return;
/*  3134 */                                     out.write(32);
/*       */                                     
/*  3136 */                                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/*  3137 */                                     _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/*  3138 */                                     _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*       */                                     
/*  3140 */                                     _jspx_th_logic_005fnotEmpty_005f0.setName("discoverystatus");
/*  3141 */                                     int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/*  3142 */                                     if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*       */                                       for (;;) {
/*  3144 */                                         out.write(32);
/*  3145 */                                         out.write("<!--$Id$-->\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/diagnosepage.js\"></SCRIPT>\n");
/*       */                                         
/*  3147 */                                         NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/*  3148 */                                         _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/*  3149 */                                         _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*       */                                         
/*  3151 */                                         _jspx_th_logic_005fnotEmpty_005f1.setName("discoverystatus");
/*  3152 */                                         int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/*  3153 */                                         if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*       */                                           for (;;) {
/*  3155 */                                             out.write(10);
/*       */                                             
/*       */ 
/*  3158 */                                             if ((request.getAttribute("type") == null) || ((!request.getAttribute("type").equals("Script Monitor")) && ((request.getAttribute("basetype") == null) || ((request.getAttribute("basetype") != null) && (!request.getAttribute("basetype").equals("Script Monitor")))) && (!request.getAttribute("type").equals("QENGINE")) && (!request.getAttribute("type").equals("Web Service")) && (!request.getAttribute("type").equals("file")) && (!request.getAttribute("type").equals("directory")) && (!request.getAttribute("type").equals("File System Monitor")) && (!request.getAttribute("type").equals("Ping Monitor")) && (!request.getAttribute("type").equals("SAP-CCMS"))))
/*       */                                             {
/*       */ 
/*  3161 */                                               out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n  <tr>\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/*  3162 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/*  3163 */                                               out.write("</span> </td>\n    <td width=\"7%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/*  3164 */                                               out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/*  3165 */                                               out.write("\n      </span> </td>\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/*  3166 */                                               out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/*  3167 */                                               out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/*  3168 */                                               out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/*  3169 */                                               out.write("\n </span></td>\n  <tr>\n  ");
/*       */                                               
/*  3171 */                                               int failedNumber = 1;
/*       */                                               
/*  3173 */                                               out.write(10);
/*       */                                               
/*  3175 */                                               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/*  3176 */                                               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/*  3177 */                                               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*       */                                               
/*  3179 */                                               _jspx_th_logic_005fiterate_005f0.setName("discoverystatus");
/*       */                                               
/*  3181 */                                               _jspx_th_logic_005fiterate_005f0.setId("row");
/*       */                                               
/*  3183 */                                               _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*       */                                               
/*  3185 */                                               _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/*  3186 */                                               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/*  3187 */                                               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/*  3188 */                                                 ArrayList row = null;
/*  3189 */                                                 Integer i = null;
/*  3190 */                                                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  3191 */                                                   out = _jspx_page_context.pushBody();
/*  3192 */                                                   _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/*  3193 */                                                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*       */                                                 }
/*  3195 */                                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/*  3196 */                                                 i = (Integer)_jspx_page_context.findAttribute("i");
/*       */                                                 for (;;) {
/*  3198 */                                                   out.write("\n<tr>\n<td height=\"18\" class=\"bodytext\">");
/*  3199 */                                                   out.print(row.get(0));
/*  3200 */                                                   out.write("</td>\n<td height=\"18\" class=\"bodytext\">");
/*  3201 */                                                   out.print(row.get(1));
/*  3202 */                                                   out.write("</td>\n\n    <td height=\"18\" class=\"bodytext\">\n      ");
/*       */                                                   
/*  3204 */                                                   if ((row.get(2).equals("Success")) || (row.get(2).equals("Present")))
/*       */                                                   {
/*  3206 */                                                     request.setAttribute("isDiscoverySuccess", "true");
/*       */                                                     
/*  3208 */                                                     out.write("\n      <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/*  3209 */                                                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/*  3210 */                                                     out.write("\" align=\"absmiddle\">\n      ");
/*       */ 
/*       */                                                   }
/*       */                                                   else
/*       */                                                   {
/*  3215 */                                                     request.setAttribute("isDiscoverySuccess", "false");
/*       */                                                     
/*  3217 */                                                     out.write("\n      <img alt=\"");
/*  3218 */                                                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.notiniatedimagetip.text"));
/*  3219 */                                                     out.write("\" src=\"/images/icon_monitor_failure.gif\" align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*       */                                                   }
/*       */                                                   
/*       */ 
/*  3223 */                                                   out.write("\n      <span class=\"bodytextbold\">");
/*  3224 */                                                   out.print(FormatUtil.getString(String.valueOf(row.get(2))));
/*  3225 */                                                   out.write("</span> </td>\n\n      ");
/*       */                                                   
/*  3227 */                                                   pageContext.setAttribute("ret_msg", String.valueOf(row.get(3)));
/*       */                                                   
/*  3229 */                                                   out.write("\n                     ");
/*       */                                                   
/*  3231 */                                                   IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  3232 */                                                   _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  3233 */                                                   _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fiterate_005f0);
/*       */                                                   
/*  3235 */                                                   _jspx_th_c_005fif_005f4.setTest("${ret_msg=='Monitoring Initiated Succesfully'}");
/*  3236 */                                                   int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  3237 */                                                   if (_jspx_eval_c_005fif_005f4 != 0) {
/*       */                                                     for (;;) {
/*  3239 */                                                       out.write("\n                           <td height=\"18\" class=\"bodytext\">");
/*  3240 */                                                       out.print(FormatUtil.getString("am.webclient.discovery.host.successfullyiniated.text"));
/*  3241 */                                                       out.write("\n                                 ");
/*  3242 */                                                       int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  3243 */                                                       if (evalDoAfterBody != 2)
/*       */                                                         break;
/*       */                                                     }
/*       */                                                   }
/*  3247 */                                                   if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  3248 */                                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*       */                                                   }
/*       */                                                   
/*  3251 */                                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  3252 */                                                   out.write("\n                                       ");
/*       */                                                   
/*  3254 */                                                   IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  3255 */                                                   _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/*  3256 */                                                   _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fiterate_005f0);
/*       */                                                   
/*  3258 */                                                   _jspx_th_c_005fif_005f5.setTest("${ret_msg!='Monitoring Initiated Succesfully'}");
/*  3259 */                                                   int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/*  3260 */                                                   if (_jspx_eval_c_005fif_005f5 != 0) {
/*       */                                                     for (;;) {
/*  3262 */                                                       out.write("\n                                             <td height=\"18\" class=\"bodytext\">");
/*  3263 */                                                       out.print(row.get(3));
/*  3264 */                                                       out.write("\n\t\t\t\t\t\t\t\t\t\t\t ");
/*       */                                                       
/*  3266 */                                                       if ((request.getAttribute("type") != null) && (!request.getAttribute("type").equals("All")) && (!request.getAttribute("type").equals("TELNET")) && (!request.getAttribute("type").equals("SNMP")) && (!request.getAttribute("type").equals("SERVICE")) && (!request.getAttribute("type").equals("JMX1.2-MX4J-RMI")) && (!request.getAttribute("type").equals("JDK1.5")) && (!request.getAttribute("type").equals("RMI")) && (!request.getAttribute("type").equals("PHP")) && (!request.getAttribute("type").equals("MAIL")) && (!request.getAttribute("type").equals("ORACLEDB")) && (!request.getAttribute("type").equals("MYSQLDB")) && (!request.getAttribute("type").equals("MSSQLDB")) && (!request.getAttribute("type").equals("DB2")) && (!request.getAttribute("type").equals("WTA")) && (!request.getAttribute("type").equals("SAP")))
/*       */                                                       {
/*  3268 */                                                         if (((String)pageContext.getAttribute("ret_msg")).indexOf(FormatUtil.getString("am.webclient.discovery.host.monitoradded.text")) == -1)
/*       */                                                         {
/*  3270 */                                                           String fWhr = request.getParameter("hideFieldsForIT360");
/*  3271 */                                                           if (fWhr == null)
/*       */                                                           {
/*  3273 */                                                             fWhr = (String)request.getAttribute("hideFieldsForIT360");
/*       */                                                           }
/*       */                                                           
/*  3276 */                                                           if (((fWhr == null) || (!fWhr.equals("true"))) && 
/*  3277 */                                                             (!request.getAttribute("type").equals("SYBASEDB")))
/*       */                                                           {
/*  3279 */                                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"bodytext\" href=\"javascript:void(0)\" onClick=\"getAllDetailsOfHost()\">");
/*  3280 */                                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.diagnose.link"));
/*  3281 */                                                             out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t ");
/*       */                                                           }
/*       */                                                         } }
/*  3284 */                                                       if ((request.getAttribute("showSupportMessage") != null) && (failedNumber == 1) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*       */                                                       {
/*  3286 */                                                         failedNumber++;
/*       */                                                         
/*       */ 
/*  3289 */                                                         out.write("\n\t\t\t\t\t\t\t\t\t\t<br>");
/*  3290 */                                                         out.print(FormatUtil.getString("am.webclient.discovery.host.support.link", new String[] { OEMUtil.getOEMString("product.talkback.mailid"), OEMUtil.getOEMString("product.tollfree.number") }));
/*  3291 */                                                         out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
/*       */                                                       }
/*  3293 */                                                       out.write("\n                                                   ");
/*  3294 */                                                       int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/*  3295 */                                                       if (evalDoAfterBody != 2)
/*       */                                                         break;
/*       */                                                     }
/*       */                                                   }
/*  3299 */                                                   if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/*  3300 */                                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*       */                                                   }
/*       */                                                   
/*  3303 */                                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/*  3304 */                                                   out.write(10);
/*  3305 */                                                   out.write(10);
/*  3306 */                                                   out.write(10);
/*       */                                                   
/*       */ 
/*  3309 */                                                   if (row.size() > 4)
/*       */                                                   {
/*       */ 
/*  3312 */                                                     out.write("<br>\n");
/*  3313 */                                                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)row.get(4) }));
/*  3314 */                                                     out.write(10);
/*       */                                                   }
/*       */                                                   
/*       */ 
/*  3318 */                                                   out.write("\n</td>\n\n</tr>\n");
/*  3319 */                                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/*  3320 */                                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/*  3321 */                                                   i = (Integer)_jspx_page_context.findAttribute("i");
/*  3322 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  3325 */                                                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  3326 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  3329 */                                               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/*  3330 */                                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*       */                                               }
/*       */                                               
/*  3333 */                                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*  3334 */                                               out.write("\n</table>\n");
/*       */ 
/*       */                                             }
/*       */                                             else
/*       */                                             {
/*  3339 */                                               ArrayList al1 = (ArrayList)request.getAttribute("discoverystatus");
/*       */                                               
/*  3341 */                                               out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n<tr>\n");
/*  3342 */                                               String mtype = (String)request.getAttribute("type");
/*  3343 */                                               out.write(10);
/*  3344 */                                               if (mtype.equals("File System Monitor")) {
/*  3345 */                                                 out.write("\n  <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/*  3346 */                                                 out.print(FormatUtil.getString("File/Directory Name"));
/*  3347 */                                                 out.write("</span> </td>\n");
/*  3348 */                                               } else if ((((String)request.getAttribute("type")).equals("Ping Monitor")) || (((String)request.getAttribute("type")).equals("SAP-CCMS"))) {
/*  3349 */                                                 out.write("\n\t<td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/*  3350 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/*  3351 */                                                 out.write("</span> </td>\n");
/*       */                                               } else {
/*  3353 */                                                 out.write("\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/*  3354 */                                                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/*  3355 */                                                 out.write("</span> </td>\n");
/*       */                                               }
/*  3357 */                                               out.write("\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/*  3358 */                                               out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/*  3359 */                                               out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/*  3360 */                                               out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/*  3361 */                                               out.write("</span></td>\n  </tr>\n  <tr>\n   <td height=\"18\" class=\"bodytext\">");
/*  3362 */                                               out.print(al1.get(0));
/*  3363 */                                               out.write("</td>\n   <td height=\"18\" class=\"bodytext\">\n   ");
/*       */                                               
/*  3365 */                                               if (al1.get(1).equals("Success"))
/*       */                                               {
/*  3367 */                                                 request.setAttribute("isDiscoverySuccess", "true");
/*       */                                                 
/*  3369 */                                                 out.write("\n   <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/*  3370 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/*  3371 */                                                 out.write("\" align=\"absmiddle\">\n    ");
/*       */ 
/*       */                                               }
/*       */                                               else
/*       */                                               {
/*  3376 */                                                 request.setAttribute("isDiscoverySuccess", "false");
/*       */                                                 
/*       */ 
/*  3379 */                                                 out.write("\n      <img  src=\"/images/icon_monitor_failure.gif\"  align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*       */                                               }
/*       */                                               
/*       */ 
/*  3383 */                                               out.write("\n<span class=\"bodytextbold\">");
/*  3384 */                                               out.print(FormatUtil.getString(String.valueOf(al1.get(1))));
/*  3385 */                                               out.write("</span> </td>\n");
/*       */                                               
/*  3387 */                                               if (al1.get(1).equals("Success"))
/*       */                                               {
/*  3389 */                                                 boolean isAdminServer = EnterpriseUtil.isAdminServer();
/*  3390 */                                                 if (isAdminServer) {
/*  3391 */                                                   String masDisplayName = (String)al1.get(3);
/*  3392 */                                                   String format = FormatUtil.getString("am.webclient.admin.add.monitor.successfully.configured.text", new String[] { masDisplayName, "", (String)al1.get(2) });
/*       */                                                   
/*  3394 */                                                   out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\">");
/*  3395 */                                                   out.print(format);
/*  3396 */                                                   out.write("</td>\n");
/*       */                                                 }
/*       */                                                 else
/*       */                                                 {
/*  3400 */                                                   out.write("\t\t\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"> ");
/*  3401 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/*  3402 */                                                   out.write("<br> ");
/*  3403 */                                                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)al1.get(2) }));
/*  3404 */                                                   out.write("</td>\n");
/*       */                                                 }
/*       */                                                 
/*       */ 
/*       */                                               }
/*       */                                               else
/*       */                                               {
/*  3411 */                                                 out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"><span class=\"bodytext\">");
/*  3412 */                                                 out.print(al1.get(2));
/*  3413 */                                                 out.write("</span></td>\n");
/*       */                                               }
/*       */                                               
/*       */ 
/*  3417 */                                               out.write("\n  </tr>\n</table>\n\n");
/*       */                                             }
/*       */                                             
/*       */ 
/*  3421 */                                             out.write(10);
/*  3422 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/*  3423 */                                             if (evalDoAfterBody != 2)
/*       */                                               break;
/*       */                                           }
/*       */                                         }
/*  3427 */                                         if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/*  3428 */                                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*       */                                         }
/*       */                                         
/*  3431 */                                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/*  3432 */                                         out.write(10);
/*  3433 */                                         out.write(10);
/*       */                                         
/*  3435 */                                         String discSucc = (String)request.getAttribute("isDiscoverySuccess");
/*  3436 */                                         isDiscoveryComplete = true;
/*  3437 */                                         if ((discSucc != null) && (discSucc.equals("true")))
/*       */                                         {
/*  3439 */                                           isDiscoverySuccess = true;
/*       */                                         }
/*       */                                         
/*  3442 */                                         out.write(10);
/*  3443 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/*  3444 */                                         if (evalDoAfterBody != 2)
/*       */                                           break;
/*       */                                       }
/*       */                                     }
/*  3448 */                                     if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/*  3449 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*       */                                     }
/*       */                                     
/*  3452 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*  3453 */                                     out.write("\n<input type=\"hidden\" name=\"method\" value=\"configureHostDiscovery\">\n<input type=\"hidden\" name=\"istestavailability\" value=\"false\">\n<input type=\"hidden\" name=\"addtoha\" value=\"");
/*  3454 */                                     out.print(request.getParameter("addtoha"));
/*  3455 */                                     out.write("\">\n<input type=\"hidden\" name=\"hideFieldsForIT360\" value=\"");
/*  3456 */                                     out.print(request.getParameter("hideFieldsForIT360"));
/*  3457 */                                     out.write("\">\n<input type=\"hidden\" name=\"it360LicenseViolation\" value=\"false\">\n\n\n\n");
/*       */                                     
/*  3459 */                                     if (request.getAttribute("typeToCheck") != null)
/*       */                                     {
/*       */ 
/*  3462 */                                       out.write("\n      <input type=\"hidden\" name=\"oldTypeToCheck\" value=\"");
/*  3463 */                                       out.print((String)request.getAttribute("typeToCheck"));
/*  3464 */                                       out.write("\">\n   ");
/*       */                                     }
/*       */                                     
/*  3467 */                                     if ((!hideFields) || ((!isDiscoveryComplete) && (hideFields)))
/*       */                                     {
/*       */ 
/*  3470 */                                       out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t\t<td  width=\"70%\" valign=\"top\">\n");
/*       */                                       
/*  3472 */                                       IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  3473 */                                       _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/*  3474 */                                       _jspx_th_c_005fif_005f6.setParent(_jspx_th_html_005fform_005f0);
/*       */                                       
/*  3476 */                                       _jspx_th_c_005fif_005f6.setTest("${!empty  param.redirectto}");
/*  3477 */                                       int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/*  3478 */                                       if (_jspx_eval_c_005fif_005f6 != 0) {
/*       */                                         for (;;) {
/*  3480 */                                           out.write("\n<input type=\"hidden\" name=\"redirectto\" value=\"");
/*  3481 */                                           out.print(request.getParameter("redirectto"));
/*  3482 */                                           out.write(34);
/*  3483 */                                           out.write(62);
/*  3484 */                                           out.write(10);
/*  3485 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/*  3486 */                                           if (evalDoAfterBody != 2)
/*       */                                             break;
/*       */                                         }
/*       */                                       }
/*  3490 */                                       if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/*  3491 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*       */                                       }
/*       */                                       
/*  3494 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/*  3495 */                                       out.write(10);
/*  3496 */                                       JspRuntimeLibrary.include(request, response, "/jsp/newresourcetypes.jsp", out, false);
/*  3497 */                                       out.write("\n\n<TABLE id=\"a\" width=\"99%\" border=\"0\" cellpadding=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\">\n  <tr>\n    <td>  ");
/*       */                                       
/*  3499 */                                       if (type.equals("All"))
/*       */                                       {
/*       */ 
/*  3502 */                                         out.write("<TABLE width=\"100%\" border=\"0\" cellpadding=\"6\" CELLSPACING=\"0\" class=\"lrborder\" >\n\t\t\t  ");
/*       */ 
/*       */                                       }
/*       */                                       else
/*       */                                       {
/*  3507 */                                         out.write("\n\t\t\t  <TABLE width=\"100%\" border=\"0\" cellpadding=\"6\" CELLSPACING=\"0\" class=\"lrborder\" >\n              ");
/*       */                                       }
/*  3509 */                                       if (type.equals("PHP"))
/*       */                                       {
/*  3511 */                                         out.write("\n           <tr class=\"yellowgrayborder\" >\n           <td colspan=\"5\" ><span class=\"bodytext\">");
/*  3512 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.phpstats.message"));
/*  3513 */                                         out.write("</span>\n           </td>\n\n           </tr>\n          ");
/*       */                                       }
/*       */                                       
/*  3516 */                                       out.write("\n\t\t   <tr >\n   \t\t\t\t <TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  3517 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.Displayname"));
/*  3518 */                                       out.write("<span class=\"mandatory\">*</span></label></TD>\n   \t\t\t\t \t<td >");
/*  3519 */                                       if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                         return;
/*  3521 */                                       out.write("</td>\n   \t\t\t\t\n   \t\t   </tr>\n\t\t  ");
/*       */                                       
/*  3523 */                                       if (type.equals("WEBSPHERE"))
/*       */                                       {
/*       */ 
/*  3526 */                                         out.write("\n\t\t<TR>\n\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\" ><label>");
/*  3527 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.deployment"));
/*  3528 */                                         out.write("<span class=\"mandatory\">*</span></label></TD>\n\t\t<TD height=\"28\" colspan=\"2\" ><input type=\"radio\" name=\"mode\" value=\"BASE\"checked ONCLICK=\"javascript:fnTest()\"><span class=\"bodytext\">");
/*  3529 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.base"));
/*  3530 */                                         out.write("&nbsp;&nbsp;&nbsp;<input type=\"radio\" name=\"mode\" value=\"ND\" ONCLICK=\"javascript:fnTest()\" >");
/*  3531 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.nwdeployment"));
/*  3532 */                                         out.write("</span>\n\t\t</TD>\n\t\t</TR>\n\t\t");
/*       */                                       }
/*       */                                       
/*       */ 
/*  3536 */                                       out.write("\n        <TR>\n          <TD height=28 width=\"25%\"  class=\"bodytext label-align addmonitor-label\">\n\t\t         ");
/*       */                                       
/*  3538 */                                       if (type.equals("All"))
/*       */                                       {
/*       */ 
/*  3541 */                                         out.write("\n           <label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/*  3542 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.textmessage.allservices"));
/*  3543 */                                         out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/*  3544 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.hostname"));
/*  3545 */                                         out.write("</a><span class=\"mandatory\">*</span></label>\n            ");
/*       */ 
/*       */                                       }
/*       */                                       else
/*       */                                       {
/*       */ 
/*  3551 */                                         out.write("\n\n\t\t  <label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/*  3552 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.textmessage.hostexample"));
/*  3553 */                                         out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/*  3554 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.hostname"));
/*  3555 */                                         out.write("</a><span class=\"mandatory\">*</span></label></TD>\n\t\t  ");
/*       */                                       }
/*  3557 */                                       out.write("\n\n\n\n\n\n\n          <TD  width=\"75%\">\n\n\t<table width=\"75%\" cellpadding=\"0\" cellspacing=\"0\"><tr>\n<td>\n\n<table cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n<td vlaign=\"top\">");
/*  3558 */                                       if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                         return;
/*  3560 */                                       out.write("</td>\n<td width=\"5\"></td>\n<td> <div id=\"shownormalmessage\" style=\"DISPLAY:none\">\n\n    <div id=\"showndmessage\" style=\"DISPLAY: none\"> <span class=\"footer\">");
/*  3561 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.textmessage.host"));
/*  3562 */                                       out.write("\n      </span> </div>\n</td>\n<td width=\"25\"></td>\n</tr>\n</table>\n\t  </td>\n</tr></table>\n      </td>\n      ");
/*  3563 */                                       if (type.equals("ORACLEAS"))
/*       */                                       {
/*       */ 
/*  3566 */                                         out.write("\n      <TR>\n       \t       <td  width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  3567 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.oracleasversion"));
/*  3568 */                                         out.write("<span class=\"mandatory\">*</span></label></td>\n       \t        <td  width=\"12%\"  class=\"bodytext\" > ");
/*       */                                         
/*  3570 */                                         SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  3571 */                                         _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/*  3572 */                                         _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*       */                                         
/*  3574 */                                         _jspx_th_html_005fselect_005f0.setProperty("version");
/*       */                                         
/*  3576 */                                         _jspx_th_html_005fselect_005f0.setStyleClass("formtext normal");
/*       */                                         
/*  3578 */                                         _jspx_th_html_005fselect_005f0.setOnchange("javascript:onComboChange1(this);");
/*  3579 */                                         int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/*  3580 */                                         if (_jspx_eval_html_005fselect_005f0 != 0) {
/*  3581 */                                           if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  3582 */                                             out = _jspx_page_context.pushBody();
/*  3583 */                                             _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/*  3584 */                                             _jspx_th_html_005fselect_005f0.doInitBody();
/*       */                                           }
/*       */                                           for (;;)
/*       */                                           {
/*  3588 */                                             OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  3589 */                                             _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/*  3590 */                                             _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*       */                                             
/*  3592 */                                             _jspx_th_html_005foption_005f0.setValue("unknown");
/*  3593 */                                             int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/*  3594 */                                             if (_jspx_eval_html_005foption_005f0 != 0) {
/*  3595 */                                               if (_jspx_eval_html_005foption_005f0 != 1) {
/*  3596 */                                                 out = _jspx_page_context.pushBody();
/*  3597 */                                                 _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/*  3598 */                                                 _jspx_th_html_005foption_005f0.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  3601 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.oracleasversionselect"));
/*  3602 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/*  3603 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  3606 */                                               if (_jspx_eval_html_005foption_005f0 != 1) {
/*  3607 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  3610 */                                             if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/*  3611 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*       */                                             }
/*       */                                             
/*  3614 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/*       */                                             
/*  3616 */                                             OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  3617 */                                             _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/*  3618 */                                             _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*       */                                             
/*  3620 */                                             _jspx_th_html_005foption_005f1.setValue("2");
/*  3621 */                                             int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/*  3622 */                                             if (_jspx_eval_html_005foption_005f1 != 0) {
/*  3623 */                                               if (_jspx_eval_html_005foption_005f1 != 1) {
/*  3624 */                                                 out = _jspx_page_context.pushBody();
/*  3625 */                                                 _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/*  3626 */                                                 _jspx_th_html_005foption_005f1.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  3629 */                                                 out.write(32);
/*  3630 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.oracleasversion.upto"));
/*  3631 */                                                 out.write(" 10.1.2 ");
/*  3632 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/*  3633 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  3636 */                                               if (_jspx_eval_html_005foption_005f1 != 1) {
/*  3637 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  3640 */                                             if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/*  3641 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*       */                                             }
/*       */                                             
/*  3644 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/*  3645 */                                             out.write(32);
/*       */                                             
/*  3647 */                                             OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  3648 */                                             _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/*  3649 */                                             _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*       */                                             
/*  3651 */                                             _jspx_th_html_005foption_005f2.setValue("3");
/*  3652 */                                             int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/*  3653 */                                             if (_jspx_eval_html_005foption_005f2 != 0) {
/*  3654 */                                               if (_jspx_eval_html_005foption_005f2 != 1) {
/*  3655 */                                                 out = _jspx_page_context.pushBody();
/*  3656 */                                                 _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/*  3657 */                                                 _jspx_th_html_005foption_005f2.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  3660 */                                                 out.write("10.1.3 & ");
/*  3661 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.oracleasversion.above"));
/*  3662 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/*  3663 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  3666 */                                               if (_jspx_eval_html_005foption_005f2 != 1) {
/*  3667 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  3670 */                                             if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/*  3671 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*       */                                             }
/*       */                                             
/*  3674 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/*  3675 */                                             out.write(32);
/*  3676 */                                             out.write(32);
/*  3677 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/*  3678 */                                             if (evalDoAfterBody != 2)
/*       */                                               break;
/*       */                                           }
/*  3681 */                                           if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  3682 */                                             out = _jspx_page_context.popBody();
/*       */                                           }
/*       */                                         }
/*  3685 */                                         if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/*  3686 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*       */                                         }
/*       */                                         
/*  3689 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/*  3690 */                                         out.write("</td>\n\n      </TR>\n\n      ");
/*       */                                       }
/*       */                                       
/*  3693 */                                       out.write("\n       <TR>\n         <TD height=\"28\" class=\"label-align addmonitor-label\"><label><span class=\"bodytext\">");
/*  3694 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.subnetmask"));
/*  3695 */                                       out.write("</span><span class=\"mandatory\">*</span></label></TD>\n         <TD height=\"28\" colspan=\"2\" > ");
/*  3696 */                                       if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                         return;
/*  3698 */                                       out.write("&nbsp;\n\t\t <span class=\"bodytext\" onclick=\"javascript:toggleRow('DNSname');\"><a href=\"javascript:void(0);\" class=\"footer\">");
/*  3699 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.advancedlink.text"));
/*  3700 */                                       out.write("</a> <img src=\"../images/img_arrow.gif\" border=\"0\"></td>\n\t\t </TR>\n\t\t <tr id=\"DNSname\" ");
/*  3701 */                                       if (_jspx_meth_c_005fif_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                         return;
/*  3703 */                                       out.write(">\n\t\t   <td colspan=\"2\" class=\"cellpadd-none\">\n\t\t        <TABLE  border=\"0\" CELLSPACING=0 CELLPADDING=6 WIDTH=\"100%\"  align=\"center\">\n\t\t          <tr>\n\t\t\t\t    <td width=\"25%\"></td>\n\t\t            <td width=\"75%\" align=\"left\">\n\t\t              ");
/*  3704 */                                       if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                         return;
/*  3706 */                                       out.write("\n\t\t               <span class=\"bodytext\">");
/*  3707 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.dnsname"));
/*  3708 */                                       out.write("</span> &nbsp;<a href=\"javascript:showSubnetMessage()\"class=\"footer\">");
/*  3709 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.dnsnamelink"));
/*  3710 */                                       out.write("</a>\n\t\t            </td>\n\t\t          </tr>\n                </table>\n           </td>\n         </tr>\n\n        ");
/*       */                                       
/*  3712 */                                       if (((!type.trim().equals("SNMP")) && (!type.equals("SYSTEM")) && (!type.equals("MAIL")) && (!type.equals("All")) && (!type.equals(".Net")) && (!type.equals("SAP"))) || ((!type.trim().equals("SNMP")) && (!type.equals("SYSTEM")) && (!type.equals("MAIL")) && (!type.equals("All")) && (!type.equals(".Net")) && (!type.equals("SAP"))))
/*       */                                       {
/*       */ 
/*  3715 */                                         out.write(10);
/*  3716 */                                         out.write(9);
/*       */                                         
/*  3718 */                                         IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  3719 */                                         _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/*  3720 */                                         _jspx_th_c_005fif_005f8.setParent(_jspx_th_html_005fform_005f0);
/*       */                                         
/*  3722 */                                         _jspx_th_c_005fif_005f8.setTest("${type =='JBoss'}");
/*  3723 */                                         int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/*  3724 */                                         if (_jspx_eval_c_005fif_005f8 != 0) {
/*       */                                           for (;;) {
/*  3726 */                                             out.write("\n\t     <tr><td colspan=\"3\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\">\n      \t\t<tr>\n\t\t  <td width=\"26%\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  3727 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.jbossversion"));
/*  3728 */                                             out.write("<span class=\"mandatory\">*</span></label></td>\n\t\t  <td width=\"27%\" class=\"bodytext\"> ");
/*       */                                             
/*  3730 */                                             SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  3731 */                                             _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/*  3732 */                                             _jspx_th_html_005fselect_005f1.setParent(_jspx_th_c_005fif_005f8);
/*       */                                             
/*  3734 */                                             _jspx_th_html_005fselect_005f1.setProperty("version");
/*       */                                             
/*  3736 */                                             _jspx_th_html_005fselect_005f1.setStyleClass("formtext medium");
/*       */                                             
/*  3738 */                                             _jspx_th_html_005fselect_005f1.setOnchange("onComboChange1()");
/*  3739 */                                             int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/*  3740 */                                             if (_jspx_eval_html_005fselect_005f1 != 0) {
/*  3741 */                                               if (_jspx_eval_html_005fselect_005f1 != 1) {
/*  3742 */                                                 out = _jspx_page_context.pushBody();
/*  3743 */                                                 _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/*  3744 */                                                 _jspx_th_html_005fselect_005f1.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  3747 */                                                 out.write("\n\t\t    ");
/*       */                                                 
/*  3749 */                                                 OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  3750 */                                                 _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/*  3751 */                                                 _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f1);
/*       */                                                 
/*  3753 */                                                 _jspx_th_html_005foption_005f3.setValue("unknown");
/*  3754 */                                                 int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/*  3755 */                                                 if (_jspx_eval_html_005foption_005f3 != 0) {
/*  3756 */                                                   if (_jspx_eval_html_005foption_005f3 != 1) {
/*  3757 */                                                     out = _jspx_page_context.pushBody();
/*  3758 */                                                     _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/*  3759 */                                                     _jspx_th_html_005foption_005f3.doInitBody();
/*       */                                                   }
/*       */                                                   for (;;) {
/*  3762 */                                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.jbossversionselect"));
/*  3763 */                                                     int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/*  3764 */                                                     if (evalDoAfterBody != 2)
/*       */                                                       break;
/*       */                                                   }
/*  3767 */                                                   if (_jspx_eval_html_005foption_005f3 != 1) {
/*  3768 */                                                     out = _jspx_page_context.popBody();
/*       */                                                   }
/*       */                                                 }
/*  3771 */                                                 if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/*  3772 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*       */                                                 }
/*       */                                                 
/*  3775 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/*  3776 */                                                 out.write("\n\t\t    ");
/*  3777 */                                                 if (_jspx_meth_html_005foption_005f4(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/*       */                                                   return;
/*  3779 */                                                 out.write(" \n\t\t    ");
/*  3780 */                                                 if (_jspx_meth_html_005foption_005f5(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/*       */                                                   return;
/*  3782 */                                                 out.write("\n\t\t    ");
/*  3783 */                                                 if (_jspx_meth_html_005foption_005f6(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/*       */                                                   return;
/*  3785 */                                                 out.write("\n\t\t    ");
/*       */                                                 
/*  3787 */                                                 OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  3788 */                                                 _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/*  3789 */                                                 _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f1);
/*       */                                                 
/*  3791 */                                                 _jspx_th_html_005foption_005f7.setValue("JBOSS_HTTP402");
/*  3792 */                                                 int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/*  3793 */                                                 if (_jspx_eval_html_005foption_005f7 != 0) {
/*  3794 */                                                   if (_jspx_eval_html_005foption_005f7 != 1) {
/*  3795 */                                                     out = _jspx_page_context.pushBody();
/*  3796 */                                                     _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/*  3797 */                                                     _jspx_th_html_005foption_005f7.doInitBody();
/*       */                                                   }
/*       */                                                   for (;;) {
/*  3800 */                                                     out.write(" 4.0.2 & ");
/*  3801 */                                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.jbossversion.above"));
/*  3802 */                                                     int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/*  3803 */                                                     if (evalDoAfterBody != 2)
/*       */                                                       break;
/*       */                                                   }
/*  3806 */                                                   if (_jspx_eval_html_005foption_005f7 != 1) {
/*  3807 */                                                     out = _jspx_page_context.popBody();
/*       */                                                   }
/*       */                                                 }
/*  3810 */                                                 if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/*  3811 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*       */                                                 }
/*       */                                                 
/*  3814 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/*  3815 */                                                 out.write("\n\t\t\t");
/*  3816 */                                                 if (_jspx_meth_html_005foption_005f8(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/*       */                                                   return;
/*  3818 */                                                 out.write("\n\t\t\t");
/*  3819 */                                                 if (_jspx_meth_html_005foption_005f9(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/*       */                                                   return;
/*  3821 */                                                 out.write("\n\t\t\t");
/*  3822 */                                                 if (_jspx_meth_html_005foption_005f10(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/*       */                                                   return;
/*  3824 */                                                 out.write("\n\t\t    ");
/*  3825 */                                                 int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/*  3826 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  3829 */                                               if (_jspx_eval_html_005fselect_005f1 != 1) {
/*  3830 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  3833 */                                             if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/*  3834 */                                               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*       */                                             }
/*       */                                             
/*  3837 */                                             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/*  3838 */                                             out.write("\n\t\t    </td>\n\t\t    <td width=\"63%\">\n\t\t    <div id=\"message6\"   style=\"DISPLAY: none\">\n\t\t\t\t<span class=\"footer\">");
/*  3839 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.jbossportexample.ver6.text"));
/*  3840 */                                             out.write("</span>\n\t\t    </div>\n\t\t    <div id=\"message7\"   style=\"DISPLAY: none\">\n\t\t    \t<span class=\"footer\">");
/*  3841 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.jbossportexample.ver5.text"));
/*  3842 */                                             out.write("</span>\n\t\t    </div>\n\t\t    </td>\n\t\t    </tr>            \n              </table>\n \t \t</td>\n\t     </tr>\n\t\t\t <tr id=\"isDomain\" style=\"display:none\">\n                <td width=\"26%\" colspan=\"2\">\n                 <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                   <tr>\n                      <td  width=\"26%\" class=\"bodytext\">Is Domain</td>\n                      <td  width=\"74%\" class=\"footer\">\n                      <input name=\"isDomain\" onclick=\"showDomainOptions()\" type=\"checkbox\">\n                      <input type=\"hidden\" name=\"LaunchType\" value=\"STANDALONE\" >\n                       </td>\n                   </tr>\n                  </table>\n                </td>\n             </tr>\n              <tr id=\"domainOptions\" style=\"display:none\">\n                <td width=\"100%\" colspan=\"2\">\n                 <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                   <tr>\n                      <td height=\"40\" width=\"26%\"class=\"bodytext\" >Host Controller Name<span class=\"mandatory\">*</span></td>\n");
/*  3843 */                                             out.write("                      <td height=\"40\" width=\"74%\"><input type=\"text\" name=\"HostController\" class=\"formtext\" size=\"25\" /></td>\n                        </tr>\n                       <tr>\n                      <td height=\"40\" width=\"26%\"class=\"bodytext\" >Server Name<span class=\"mandatory\">*</span></td>\n                       <td height=\"40\" width=\"74%\"><input type=\"text\" name=\"ServerName\" class=\"formtext\" size=\"25\" /></td>\n                       </tr>\n                       </table>\n                 </td>\n                 </tr>\n\t");
/*  3844 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/*  3845 */                                             if (evalDoAfterBody != 2)
/*       */                                               break;
/*       */                                           }
/*       */                                         }
/*  3849 */                                         if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/*  3850 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*       */                                         }
/*       */                                         
/*  3853 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/*  3854 */                                         out.write("\n        <TR>\n          <TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>\n          ");
/*       */                                         
/*  3856 */                                         if (!usertype.equals("F"))
/*       */                                         {
/*  3858 */                                           String tooltip = FormatUtil.getString("am.webclient.hostdiscovery.textmessage.portexample");
/*  3859 */                                           if (type.equals("JDK1.5")) {
/*  3860 */                                             tooltip = FormatUtil.getString("am.webclient.hostdiscovery.textmessage.jdk15portexample");
/*       */                                           }
/*  3862 */                                           if (type.equals("JBoss")) {
/*  3863 */                                             tooltip = FormatUtil.getString("am.webclient.hostdiscovery.textmessage.jbossportexample");
/*       */                                           }
/*       */                                           
/*  3866 */                                           out.write("\n\t\t<a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/*  3867 */                                           out.print(tooltip);
/*  3868 */                                           out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/*  3869 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.port"));
/*  3870 */                                           out.write("</a><span class=\"mandatory\">*</span> ");
/*       */ 
/*       */                                         }
/*       */                                         else
/*       */                                         {
/*  3875 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.port"));
/*  3876 */                                           out.write("<span class=\"mandatory\">*</span>\n\t\t\t");
/*       */                                         }
/*       */                                         
/*       */ 
/*  3880 */                                         out.write("</label></TD>\n          <TD height=\"28\" colspan=\"2\" > ");
/*  3881 */                                         if ((!type.equals("Exchange")) && (!type.equals("PHP")) && (!type.equals("SNMP"))) {
/*  3882 */                                           out.write(32);
/*  3883 */                                           if (!_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context)) {}
/*       */ 
/*       */                                         }
/*  3886 */                                         else if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context)) {
/*       */                                           return;
/*       */                                         }
/*  3889 */                                         out.write("\n\n\n          </TD>\n        </TR>                       \n\n        ");
/*       */                                         
/*  3891 */                                         if ((type.equals("APACHE")) || (type.equals("IIS")) || (type.equals("PHP")))
/*       */                                         {
/*       */ 
/*  3894 */                                           out.write("\n\n\t         <TR>\n\t           <TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><span class=\"bodytext\"> ");
/*  3895 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssl"));
/*  3896 */                                           out.write(" </span></label></TD>\n\t           <TD height=\"28\" colspan=\"2\" > ");
/*  3897 */                                           if (_jspx_meth_html_005fcheckbox_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                             return;
/*  3899 */                                           out.write("</TD>\n\t</TR>\n        ");
/*       */                                         }
/*       */                                         
/*       */ 
/*  3903 */                                         out.write("\n         ");
/*       */                                         
/*  3905 */                                         IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  3906 */                                         _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/*  3907 */                                         _jspx_th_c_005fif_005f9.setParent(_jspx_th_html_005fform_005f0);
/*       */                                         
/*  3909 */                                         _jspx_th_c_005fif_005f9.setTest("${type =='JBoss'}");
/*  3910 */                                         int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/*  3911 */                                         if (_jspx_eval_c_005fif_005f9 != 0) {
/*       */                                           for (;;) {
/*  3913 */                                             out.write("\n\n\t         <TR>\n\t           <TD height=\"28\" class=\"bodytext label-align addmonitor-label\" ><label><span class=\"bodytext\"> ");
/*  3914 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssl"));
/*  3915 */                                             out.write(" </span></label></TD>\n\t           <TD height=\"28\" colspan=\"2\" >");
/*  3916 */                                             if (_jspx_meth_html_005fcheckbox_005f2(_jspx_th_c_005fif_005f9, _jspx_page_context))
/*       */                                               return;
/*  3918 */                                             out.write("\n\t           </TD>\n\t\t\t</TR>\n\t\t");
/*  3919 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/*  3920 */                                             if (evalDoAfterBody != 2)
/*       */                                               break;
/*       */                                           }
/*       */                                         }
/*  3924 */                                         if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/*  3925 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*       */                                         }
/*       */                                         
/*  3928 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/*  3929 */                                         out.write("\n\n\t\t");
/*       */                                         
/*  3931 */                                         IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  3932 */                                         _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/*  3933 */                                         _jspx_th_c_005fif_005f10.setParent(_jspx_th_html_005fform_005f0);
/*       */                                         
/*  3935 */                                         _jspx_th_c_005fif_005f10.setTest("${type =='WEBLOGIC'}");
/*  3936 */                                         int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/*  3937 */                                         if (_jspx_eval_c_005fif_005f10 != 0) {
/*       */                                           for (;;) {
/*  3939 */                                             out.write("\n        <tr>\n          <td class=\"bodytext label-align addmonitor-label\"><label>");
/*  3940 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.weblogicversion"));
/*  3941 */                                             out.write("<span class=\"mandatory\">*</span></label></td>\n          <td class=\"bodytext\"> ");
/*       */                                             
/*  3943 */                                             SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  3944 */                                             _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/*  3945 */                                             _jspx_th_html_005fselect_005f2.setParent(_jspx_th_c_005fif_005f10);
/*       */                                             
/*  3947 */                                             _jspx_th_html_005fselect_005f2.setProperty("version");
/*       */                                             
/*  3949 */                                             _jspx_th_html_005fselect_005f2.setStyleClass("formtext medium");
/*       */                                             
/*  3951 */                                             _jspx_th_html_005fselect_005f2.setOnchange("onComboChange1()");
/*  3952 */                                             int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/*  3953 */                                             if (_jspx_eval_html_005fselect_005f2 != 0) {
/*  3954 */                                               if (_jspx_eval_html_005fselect_005f2 != 1) {
/*  3955 */                                                 out = _jspx_page_context.pushBody();
/*  3956 */                                                 _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/*  3957 */                                                 _jspx_th_html_005fselect_005f2.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  3960 */                                                 out.write("\n            ");
/*       */                                                 
/*  3962 */                                                 OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  3963 */                                                 _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/*  3964 */                                                 _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f2);
/*       */                                                 
/*  3966 */                                                 _jspx_th_html_005foption_005f11.setValue("unknown");
/*  3967 */                                                 int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/*  3968 */                                                 if (_jspx_eval_html_005foption_005f11 != 0) {
/*  3969 */                                                   if (_jspx_eval_html_005foption_005f11 != 1) {
/*  3970 */                                                     out = _jspx_page_context.pushBody();
/*  3971 */                                                     _jspx_th_html_005foption_005f11.setBodyContent((BodyContent)out);
/*  3972 */                                                     _jspx_th_html_005foption_005f11.doInitBody();
/*       */                                                   }
/*       */                                                   for (;;) {
/*  3975 */                                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.weblogicversionselect"));
/*  3976 */                                                     int evalDoAfterBody = _jspx_th_html_005foption_005f11.doAfterBody();
/*  3977 */                                                     if (evalDoAfterBody != 2)
/*       */                                                       break;
/*       */                                                   }
/*  3980 */                                                   if (_jspx_eval_html_005foption_005f11 != 1) {
/*  3981 */                                                     out = _jspx_page_context.popBody();
/*       */                                                   }
/*       */                                                 }
/*  3984 */                                                 if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/*  3985 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11); return;
/*       */                                                 }
/*       */                                                 
/*  3988 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11);
/*  3989 */                                                 out.write("\n            ");
/*  3990 */                                                 if (_jspx_meth_html_005foption_005f12(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/*       */                                                   return;
/*  3992 */                                                 out.write(32);
/*  3993 */                                                 if (_jspx_meth_html_005foption_005f13(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/*       */                                                   return;
/*  3995 */                                                 out.write("\n                              ");
/*  3996 */                                                 if (_jspx_meth_html_005foption_005f14(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/*       */                                                   return;
/*  3998 */                                                 out.write("\n                              ");
/*  3999 */                                                 if (_jspx_meth_html_005foption_005f15(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/*       */                                                   return;
/*  4001 */                                                 out.write("\n                              ");
/*  4002 */                                                 if (_jspx_meth_html_005foption_005f16(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/*       */                                                   return;
/*  4004 */                                                 out.write("\n\t     ");
/*  4005 */                                                 int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/*  4006 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  4009 */                                               if (_jspx_eval_html_005fselect_005f2 != 1) {
/*  4010 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  4013 */                                             if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/*  4014 */                                               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2); return;
/*       */                                             }
/*       */                                             
/*  4017 */                                             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2);
/*  4018 */                                             out.write("\n            ");
/*  4019 */                                             out.write("\n             </td>\n        </tr>\n\n\t\t");
/*  4020 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/*  4021 */                                             if (evalDoAfterBody != 2)
/*       */                                               break;
/*       */                                           }
/*       */                                         }
/*  4025 */                                         if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/*  4026 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*       */                                         }
/*       */                                         
/*  4029 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/*  4030 */                                         out.write(10);
/*  4031 */                                         out.write(9);
/*  4032 */                                         out.write(9);
/*       */                                         
/*  4034 */                                         IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  4035 */                                         _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/*  4036 */                                         _jspx_th_c_005fif_005f11.setParent(_jspx_th_html_005fform_005f0);
/*       */                                         
/*  4038 */                                         _jspx_th_c_005fif_005f11.setTest("${type =='WLI'}");
/*  4039 */                                         int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/*  4040 */                                         if (_jspx_eval_c_005fif_005f11 != 0) {
/*       */                                           for (;;) {
/*  4042 */                                             out.write("\n\t\t<tr>\n\t          <td class=\"bodytext label-align addmonitor-label\"><label>");
/*  4043 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.wli.version"));
/*  4044 */                                             out.write("<span class=\"mandatory\">*</span></label></td>\n\t           <td class=\"bodytext\"> ");
/*  4045 */                                             if (_jspx_meth_html_005fselect_005f3(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*       */                                               return;
/*  4047 */                                             out.write("\n\t\t </td>\n\t\t </tr>\n\t\t");
/*  4048 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/*  4049 */                                             if (evalDoAfterBody != 2)
/*       */                                               break;
/*       */                                           }
/*       */                                         }
/*  4053 */                                         if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/*  4054 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*       */                                         }
/*       */                                         
/*  4057 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/*  4058 */                                         out.write("\n\n\t\t");
/*       */                                         
/*  4060 */                                         IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  4061 */                                         _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/*  4062 */                                         _jspx_th_c_005fif_005f12.setParent(_jspx_th_html_005fform_005f0);
/*       */                                         
/*  4064 */                                         _jspx_th_c_005fif_005f12.setTest("${type =='Tomcat'}");
/*  4065 */                                         int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/*  4066 */                                         if (_jspx_eval_c_005fif_005f12 != 0) {
/*       */                                           for (;;) {
/*  4068 */                                             out.write("\n\t\t<TR>\n\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\" ><label><span class=\"bodytext\"> ");
/*  4069 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssl"));
/*  4070 */                                             out.write(" </span></label></TD>\n\t\t<TD height=\"28\" colspan=\"1\" >");
/*  4071 */                                             if (_jspx_meth_html_005fcheckbox_005f3(_jspx_th_c_005fif_005f12, _jspx_page_context))
/*       */                                               return;
/*  4073 */                                             out.write("\n\t\t</TD>\n\t\t</TR>\n\n\t\t<tr><td colspan=\"3\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\">\n      <tr>\n        <td  width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  4074 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.tomcatversion"));
/*  4075 */                                             out.write("<span class=\"mandatory\">*</span></label></td>\n        <td  width=\"28%\"  class=\"bodytext\" >  &nbsp; ");
/*       */                                             
/*  4077 */                                             SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  4078 */                                             _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/*  4079 */                                             _jspx_th_html_005fselect_005f4.setParent(_jspx_th_c_005fif_005f12);
/*       */                                             
/*  4081 */                                             _jspx_th_html_005fselect_005f4.setProperty("version");
/*       */                                             
/*  4083 */                                             _jspx_th_html_005fselect_005f4.setStyleClass("formtext medium");
/*       */                                             
/*  4085 */                                             _jspx_th_html_005fselect_005f4.setOnchange("javascript:onComboChange(this);");
/*  4086 */                                             int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/*  4087 */                                             if (_jspx_eval_html_005fselect_005f4 != 0) {
/*  4088 */                                               if (_jspx_eval_html_005fselect_005f4 != 1) {
/*  4089 */                                                 out = _jspx_page_context.pushBody();
/*  4090 */                                                 _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/*  4091 */                                                 _jspx_th_html_005fselect_005f4.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  4094 */                                                 out.write("<option value=\"0\" selected>");
/*  4095 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.tomcatversionselect"));
/*  4096 */                                                 out.write("</option>");
/*  4097 */                                                 if (_jspx_meth_html_005foption_005f18(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/*       */                                                   return;
/*  4099 */                                                 out.write(32);
/*  4100 */                                                 if (_jspx_meth_html_005foption_005f19(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/*       */                                                   return;
/*  4102 */                                                 out.write(32);
/*  4103 */                                                 if (_jspx_meth_html_005foption_005f20(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/*       */                                                   return;
/*  4105 */                                                 out.write(32);
/*  4106 */                                                 if (_jspx_meth_html_005foption_005f21(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/*       */                                                   return;
/*  4108 */                                                 out.write(32);
/*  4109 */                                                 if (_jspx_meth_html_005foption_005f22(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/*       */                                                   return;
/*  4111 */                                                 out.write(32);
/*  4112 */                                                 int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/*  4113 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  4116 */                                               if (_jspx_eval_html_005fselect_005f4 != 1) {
/*  4117 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  4120 */                                             if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/*  4121 */                                               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f4); return;
/*       */                                             }
/*       */                                             
/*  4124 */                                             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f4);
/*  4125 */                                             out.write("</td>");
/*  4126 */                                             out.write(" \n\t\t<td width=\"63%\">\n\t\t<div id=\"message3\"   style=\"DISPLAY: none\">\n\t\t\t\t<span class=\"footer\">");
/*  4127 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.message.tomcatversion"));
/*  4128 */                                             out.write("</span> <a target=\"_blank\" href=\"/help/managing-business-applications/application-server-monitor.html#tomcat-server\" class=\"footer\">");
/*  4129 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.moreinfo"));
/*  4130 */                                             out.write("</a></div>\n\n\t\t<div id=\"message4\"   style=\"DISPLAY: none\">\n\t          <span class=\"footer\">");
/*  4131 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.message.tomcatversion"));
/*  4132 */                                             out.write("</span> <a target=\"_blank\" href=\"/help/managing-business-applications/application-server-monitor.html#tomcat-server\" class=\"footer\">");
/*  4133 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.moreinfo"));
/*  4134 */                                             out.write("</a>\n\t\t\t </div>\n\t\t\t   <div id=\"message5\"   style=\"DISPLAY: none\">\n\t      <span class=\"footer\">");
/*  4135 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.message.tomcatversion5.x"));
/*  4136 */                                             out.write("</span> <a target=\"_blank\" href=\"/help/managing-business-applications/application-server-monitor.html#tomcat-server\" class=\"footer\">");
/*  4137 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.moreinfo"));
/*  4138 */                                             out.write("</a>\n\t   </div>\n\t   </td></tr></table>\n \t  </td>\n\t</tr>\n\n\t\t");
/*  4139 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/*  4140 */                                             if (evalDoAfterBody != 2)
/*       */                                               break;
/*       */                                           }
/*       */                                         }
/*  4144 */                                         if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/*  4145 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*       */                                         }
/*       */                                         
/*  4148 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/*  4149 */                                         out.write("\n         ");
/*       */                                       }
/*       */                                       
/*  4152 */                                       if (type.equals("MAIL"))
/*       */                                       {
/*       */ 
/*  4155 */                                         out.write("\n      <TR>\n        <TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  4156 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.smtpport"));
/*  4157 */                                         out.write("<span class=\"mandatory\">*</span></label></TD>\n        <TD height=\"28\" colspan=\"2\"> ");
/*  4158 */                                         if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4160 */                                         out.write("\n        </TD>\n    </TR>\n        <TR>\n          <TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  4161 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.emailid"));
/*  4162 */                                         out.write("<span class=\"mandatory\">*</span></label></TD>\n          <TD height=\"28\" colspan=\"2\"> ");
/*  4163 */                                         if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4165 */                                         out.write("\n          </TD>\n    </TR>\n             <TR >\n                   <TD  class=\"bodytext label-align addmonitor-label\" ><label>");
/*  4166 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.smtpauth"));
/*  4167 */                                         out.write("</label></td>\n\t\t\t\t   <td colspan=\"2\"> ");
/*  4168 */                                         if (_jspx_meth_html_005fcheckbox_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4170 */                                         out.write("</td>\n\t\t\t\t   </tr>\n\t\t\t\t   <tr><td colspan=\"3\">\n                   <div id=\"smtpauthinfo\"   ");
/*  4171 */                                         if (_jspx_meth_c_005fif_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4173 */                                         out.write(">\n\n\t\t                    <TABLE  border=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" align=\"left\">\n      <TR >\n        <TD class=\"bodytext label-align addmonitor-label\" height=\"30\"><label>");
/*  4174 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.smtp.username"));
/*  4175 */                                         out.write("</label>\n        </TD>\n        <TD><input type=\"text\" name=\"smtpUserName\" class=\"formtext default\" autocomplete=\"off\" /> <!--");
/*  4176 */                                         if (_jspx_meth_html_005ftext_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4178 */                                         out.write("--> </TD>\n      </tr>\n      <TR >\n        <TD height=\"30\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  4179 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.smtp.password"));
/*  4180 */                                         out.write("</label>\n        </TD>\n        <TD><input type=\"password\" name=\"smtpPassword\" class=\"formtext default\" autocomplete=\"off\" /> <!--");
/*  4181 */                                         if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4183 */                                         out.write("--> </TD>\n      </tr>\n    </TABLE>\n\n                 </div>\n\n               </TR>\n\t       <tr>\n\t       <td colspan=\"3\" height=\"2\"></td>\n\t       </tr>\n <TR >\n<TD class=\"bodytext label-align addmonitor-label\" valign=\"top\"><label>");
/*  4184 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.enablepop"));
/*  4185 */                                         out.write("</label></td>\n<td > ");
/*  4186 */                                         if (_jspx_meth_html_005fcheckbox_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4188 */                                         out.write("</td></tr>\n<tr><td colspan=\"3\">\n                   <div id=\"popinfo\"   ");
/*  4189 */                                         if (_jspx_meth_c_005fif_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4191 */                                         out.write(">\n\n\n    <TABLE  border=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" align=\"left\" >\n      <TR>\n        <TD height=\"30\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  4192 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.pophost"));
/*  4193 */                                         out.write("<span class=\"mandatory\">*</span></label></TD>\n        <TD  width=\"75%\"> ");
/*  4194 */                                         if (_jspx_meth_html_005ftext_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4196 */                                         out.write("\n        </TD>\n      </TR>\n      <TR>\n        <TD width=\"200\" height=\"30\"  class=\"bodytext label-align addmonitor-label\"><label>");
/*  4197 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.popport"));
/*  4198 */                                         out.write("<span class=\"mandatory\">*</span></label></TD>\n        <TD  > ");
/*  4199 */                                         if (_jspx_meth_html_005ftext_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4201 */                                         out.write("\n        </TD>\n      </TR>\n      <TR>\n        <TD height=\"30\"  class=\"bodytext label-align addmonitor-label\"><label>");
/*  4202 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/*  4203 */                                         out.write("<span class=\"mandatory\"><span class=\"mandatory\">*</span></span></TD>\n        <TD  >");
/*  4204 */                                         if (_jspx_meth_html_005ftext_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4206 */                                         out.write("\n          &nbsp;<span class=\"bodytext\"></span> </TD>\n      </TR>\n      <TR>\n        <TD height=\"30\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  4207 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/*  4208 */                                         out.write("<span class=\"mandatory\">*</span></label></TD>\n        <TD   > ");
/*  4209 */                                         if (_jspx_meth_html_005fpassword_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4211 */                                         out.write("\n        </TD>\n      </TR>\n    </TABLE>\n\n                 </div>\n</td>\n</TR>\n  <TR>\t\n    <TD  height=\"28\" class=\"bodytext\">");
/*  4212 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.message"));
/*  4213 */                                         out.write("<span class=\"mandatory\">*</span></TD>\n    <TD  height=\"28\" colspan=\"2\"> ");
/*  4214 */                                         if (_jspx_meth_html_005ftext_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4216 */                                         out.write("<span class=\"bodytext\"></span>\n    </TD>\n  </TR>\n\n        ");
/*       */                                       }
/*       */                                       
/*  4219 */                                       if (type.equals("PHP"))
/*       */                                       {
/*       */ 
/*  4222 */                                         out.write("\n        <TR>\n       <script>\n                    var host_dis_phpmsg='");
/*  4223 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.phpmessage"));
/*  4224 */                                         out.write("';\n       </script>\n       <TD height=\"28\" class=\"bodytext label-align addmonitor-label\" ><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,host_dis_phpmsg,false,true,'#000000',420,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/*  4225 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.phppath"));
/*  4226 */                                         out.write("</a><span class=\"mandatory\">*</span></label></TD>\n          <TD height=\"28\" colspan=\"2\" > ");
/*  4227 */                                         if (_jspx_meth_html_005ftext_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4229 */                                         out.write("\n          <span class=\"bodytext\">");
/*  4230 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.phpmessage"));
/*  4231 */                                         out.write("</span>\n          </TD>\n        </TR>\n       ");
/*       */                                       }
/*       */                                       
/*       */ 
/*  4235 */                                       out.write("\n\n\t\t");
/*       */                                       
/*  4237 */                                       if (type.equals("SAP"))
/*       */                                       {
/*       */ 
/*  4240 */                                         out.write("\n\t\t        <TR>\n\t\t\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\">");
/*  4241 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.connect.withrouterstring"));
/*  4242 */                                         out.write("</a></label></TD>\n\t\t\t\t<TD height=\"28\" colspan=\"2\" >  ");
/*  4243 */                                         if (_jspx_meth_html_005fcheckbox_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4245 */                                         out.write("\n\t\t\t\t</TD>\n\t\t        </TR>\n\t\t        <TR id=\"routerString\" style=\"display:none\">\n\t\t\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/*  4246 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.routerstring.example"));
/*  4247 */                                         out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/*  4248 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.routerstring"));
/*  4249 */                                         out.write("</a><span class=\"mandatory\">*</span></label></TD>\n\t\t\t\t<TD height=\"28\" colspan=\"2\" > ");
/*  4250 */                                         if (_jspx_meth_html_005ftext_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4252 */                                         out.write("\n\t\t\t\t</TD>\n\t\t        </TR>\n\t\t        <TR>\n\t\t          <TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/*  4253 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.textmessage.logonclient"));
/*  4254 */                                         out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">\n\t\t          ");
/*  4255 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.logonclient"));
/*  4256 */                                         out.write("</a><span class=\"mandatory\">*</span></label></TD>\n\t\t          <TD height=\"28\" colspan=\"2\" > ");
/*  4257 */                                         if (_jspx_meth_html_005ftext_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4259 */                                         out.write("\n\t\t          </TD>\n\t\t        </TR>\n\t\t        <TR>\n\t\t\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/*  4260 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.textmessage.systemnumber"));
/*  4261 */                                         out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/*  4262 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.systemnumber"));
/*  4263 */                                         out.write("</a><span class=\"mandatory\">*</span></label></TD>\n\t\t\t\t<TD height=\"28\" colspan=\"2\" > ");
/*  4264 */                                         if (_jspx_meth_html_005ftext_005f15(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4266 */                                         out.write("\n\t\t\t\t</TD>\n\t\t        </TR>\n\t\t        <TR>\n\t\t\t\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/*  4267 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.textmessage.language"));
/*  4268 */                                         out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/*  4269 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.language"));
/*  4270 */                                         out.write("</a></label></TD>\n\t\t\t\t<TD height=\"28\" colspan=\"2\" > ");
/*  4271 */                                         if (_jspx_meth_html_005ftext_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4273 */                                         out.write("\n\t\t\t\t</TD>\n\t\t        </TR>\n\t\t       ");
/*       */                                       }
/*       */                                       
/*       */ 
/*  4277 */                                       out.write("\n\n\n\t");
/*       */                                       
/*  4279 */                                       if (!type.equals("All"))
/*       */                                       {
/*       */ 
/*  4282 */                                         out.write("\n        <TR>\n        <TD height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\" ><label>");
/*  4283 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.polling"));
/*  4284 */                                         out.write("<span class=\"mandatory\">*</span></label></TD>\n        <TD height=\"28\" colspan=\"2\" > ");
/*  4285 */                                         if (_jspx_meth_html_005ftext_005f17(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  4287 */                                         out.write("<span class=\"footer\">&nbsp;&nbsp;&nbsp;");
/*  4288 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.minutes"));
/*  4289 */                                         out.write("</span></TD>\n        </TR>\n        ");
/*       */                                       }
/*       */                                       
/*  4292 */                                       if (type.equals("SYSTEM"))
/*       */                                       {
/*  4294 */                                         out.write("\n    <tr>\n\n\t <td width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  4295 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.ostype"));
/*  4296 */                                         out.write("<span class=\"mandatory\">*</span></label></td>\n\t <td width=\"75%\"> ");
/*       */                                         
/*  4298 */                                         SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  4299 */                                         _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/*  4300 */                                         _jspx_th_html_005fselect_005f5.setParent(_jspx_th_html_005fform_005f0);
/*       */                                         
/*  4302 */                                         _jspx_th_html_005fselect_005f5.setProperty("os");
/*       */                                         
/*  4304 */                                         _jspx_th_html_005fselect_005f5.setStyleClass("formtext medium");
/*       */                                         
/*  4306 */                                         _jspx_th_html_005fselect_005f5.setOnchange("javascript:changemode()");
/*  4307 */                                         int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/*  4308 */                                         if (_jspx_eval_html_005fselect_005f5 != 0) {
/*  4309 */                                           if (_jspx_eval_html_005fselect_005f5 != 1) {
/*  4310 */                                             out = _jspx_page_context.pushBody();
/*  4311 */                                             _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/*  4312 */                                             _jspx_th_html_005fselect_005f5.doInitBody();
/*       */                                           }
/*       */                                           for (;;) {
/*  4315 */                                             out.write("\n         ");
/*       */                                             
/*  4317 */                                             OptionTag _jspx_th_html_005foption_005f23 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4318 */                                             _jspx_th_html_005foption_005f23.setPageContext(_jspx_page_context);
/*  4319 */                                             _jspx_th_html_005foption_005f23.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                             
/*  4321 */                                             _jspx_th_html_005foption_005f23.setValue("Node");
/*  4322 */                                             int _jspx_eval_html_005foption_005f23 = _jspx_th_html_005foption_005f23.doStartTag();
/*  4323 */                                             if (_jspx_eval_html_005foption_005f23 != 0) {
/*  4324 */                                               if (_jspx_eval_html_005foption_005f23 != 1) {
/*  4325 */                                                 out = _jspx_page_context.pushBody();
/*  4326 */                                                 _jspx_th_html_005foption_005f23.setBodyContent((BodyContent)out);
/*  4327 */                                                 _jspx_th_html_005foption_005f23.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  4330 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.selectos"));
/*  4331 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f23.doAfterBody();
/*  4332 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  4335 */                                               if (_jspx_eval_html_005foption_005f23 != 1) {
/*  4336 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  4339 */                                             if (_jspx_th_html_005foption_005f23.doEndTag() == 5) {
/*  4340 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23); return;
/*       */                                             }
/*       */                                             
/*  4343 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23);
/*  4344 */                                             out.write(10);
/*  4345 */                                             out.write(9);
/*  4346 */                                             out.write(32);
/*       */                                             
/*  4348 */                                             if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("LAMP"))
/*       */                                             {
/*       */ 
/*  4351 */                                               out.write("\n                         ");
/*       */                                               
/*  4353 */                                               OptionTag _jspx_th_html_005foption_005f24 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4354 */                                               _jspx_th_html_005foption_005f24.setPageContext(_jspx_page_context);
/*  4355 */                                               _jspx_th_html_005foption_005f24.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4357 */                                               _jspx_th_html_005foption_005f24.setValue("FreeBSD");
/*  4358 */                                               int _jspx_eval_html_005foption_005f24 = _jspx_th_html_005foption_005f24.doStartTag();
/*  4359 */                                               if (_jspx_eval_html_005foption_005f24 != 0) {
/*  4360 */                                                 if (_jspx_eval_html_005foption_005f24 != 1) {
/*  4361 */                                                   out = _jspx_page_context.pushBody();
/*  4362 */                                                   _jspx_th_html_005foption_005f24.setBodyContent((BodyContent)out);
/*  4363 */                                                   _jspx_th_html_005foption_005f24.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4366 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.freebsd"));
/*  4367 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f24.doAfterBody();
/*  4368 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4371 */                                                 if (_jspx_eval_html_005foption_005f24 != 1) {
/*  4372 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4375 */                                               if (_jspx_th_html_005foption_005f24.doEndTag() == 5) {
/*  4376 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24); return;
/*       */                                               }
/*       */                                               
/*  4379 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24);
/*  4380 */                                               out.write("\n  \t                 ");
/*       */                                               
/*  4382 */                                               OptionTag _jspx_th_html_005foption_005f25 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4383 */                                               _jspx_th_html_005foption_005f25.setPageContext(_jspx_page_context);
/*  4384 */                                               _jspx_th_html_005foption_005f25.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4386 */                                               _jspx_th_html_005foption_005f25.setValue("Linux");
/*  4387 */                                               int _jspx_eval_html_005foption_005f25 = _jspx_th_html_005foption_005f25.doStartTag();
/*  4388 */                                               if (_jspx_eval_html_005foption_005f25 != 0) {
/*  4389 */                                                 if (_jspx_eval_html_005foption_005f25 != 1) {
/*  4390 */                                                   out = _jspx_page_context.pushBody();
/*  4391 */                                                   _jspx_th_html_005foption_005f25.setBodyContent((BodyContent)out);
/*  4392 */                                                   _jspx_th_html_005foption_005f25.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4395 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.linux"));
/*  4396 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f25.doAfterBody();
/*  4397 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4400 */                                                 if (_jspx_eval_html_005foption_005f25 != 1) {
/*  4401 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4404 */                                               if (_jspx_th_html_005foption_005f25.doEndTag() == 5) {
/*  4405 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25); return;
/*       */                                               }
/*       */                                               
/*  4408 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25);
/*  4409 */                                               out.write("\n  \t                 ");
/*       */ 
/*       */                                             }
/*  4412 */                                             else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD"))
/*       */                                             {
/*       */ 
/*  4415 */                                               out.write("\n                                 ");
/*       */                                               
/*  4417 */                                               OptionTag _jspx_th_html_005foption_005f26 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4418 */                                               _jspx_th_html_005foption_005f26.setPageContext(_jspx_page_context);
/*  4419 */                                               _jspx_th_html_005foption_005f26.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4421 */                                               _jspx_th_html_005foption_005f26.setValue("Linux");
/*  4422 */                                               int _jspx_eval_html_005foption_005f26 = _jspx_th_html_005foption_005f26.doStartTag();
/*  4423 */                                               if (_jspx_eval_html_005foption_005f26 != 0) {
/*  4424 */                                                 if (_jspx_eval_html_005foption_005f26 != 1) {
/*  4425 */                                                   out = _jspx_page_context.pushBody();
/*  4426 */                                                   _jspx_th_html_005foption_005f26.setBodyContent((BodyContent)out);
/*  4427 */                                                   _jspx_th_html_005foption_005f26.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4430 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.linux"));
/*  4431 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f26.doAfterBody();
/*  4432 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4435 */                                                 if (_jspx_eval_html_005foption_005f26 != 1) {
/*  4436 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4439 */                                               if (_jspx_th_html_005foption_005f26.doEndTag() == 5) {
/*  4440 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26); return;
/*       */                                               }
/*       */                                               
/*  4443 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26);
/*  4444 */                                               out.write("\n  \t                 ");
/*       */ 
/*       */                                             }
/*       */                                             else
/*       */                                             {
/*       */ 
/*  4450 */                                               out.write(10);
/*  4451 */                                               out.write(9);
/*  4452 */                                               out.write(32);
/*       */                                               
/*  4454 */                                               OptionTag _jspx_th_html_005foption_005f27 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4455 */                                               _jspx_th_html_005foption_005f27.setPageContext(_jspx_page_context);
/*  4456 */                                               _jspx_th_html_005foption_005f27.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4458 */                                               _jspx_th_html_005foption_005f27.setValue("AIX");
/*  4459 */                                               int _jspx_eval_html_005foption_005f27 = _jspx_th_html_005foption_005f27.doStartTag();
/*  4460 */                                               if (_jspx_eval_html_005foption_005f27 != 0) {
/*  4461 */                                                 if (_jspx_eval_html_005foption_005f27 != 1) {
/*  4462 */                                                   out = _jspx_page_context.pushBody();
/*  4463 */                                                   _jspx_th_html_005foption_005f27.setBodyContent((BodyContent)out);
/*  4464 */                                                   _jspx_th_html_005foption_005f27.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4467 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.aix"));
/*  4468 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f27.doAfterBody();
/*  4469 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4472 */                                                 if (_jspx_eval_html_005foption_005f27 != 1) {
/*  4473 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4476 */                                               if (_jspx_th_html_005foption_005f27.doEndTag() == 5) {
/*  4477 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27); return;
/*       */                                               }
/*       */                                               
/*  4480 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27);
/*  4481 */                                               out.write(10);
/*  4482 */                                               out.write(9);
/*  4483 */                                               out.write(32);
/*       */                                               
/*  4485 */                                               OptionTag _jspx_th_html_005foption_005f28 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4486 */                                               _jspx_th_html_005foption_005f28.setPageContext(_jspx_page_context);
/*  4487 */                                               _jspx_th_html_005foption_005f28.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4489 */                                               _jspx_th_html_005foption_005f28.setValue("FreeBSD");
/*  4490 */                                               int _jspx_eval_html_005foption_005f28 = _jspx_th_html_005foption_005f28.doStartTag();
/*  4491 */                                               if (_jspx_eval_html_005foption_005f28 != 0) {
/*  4492 */                                                 if (_jspx_eval_html_005foption_005f28 != 1) {
/*  4493 */                                                   out = _jspx_page_context.pushBody();
/*  4494 */                                                   _jspx_th_html_005foption_005f28.setBodyContent((BodyContent)out);
/*  4495 */                                                   _jspx_th_html_005foption_005f28.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4498 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.freebsd"));
/*  4499 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f28.doAfterBody();
/*  4500 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4503 */                                                 if (_jspx_eval_html_005foption_005f28 != 1) {
/*  4504 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4507 */                                               if (_jspx_th_html_005foption_005f28.doEndTag() == 5) {
/*  4508 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28); return;
/*       */                                               }
/*       */                                               
/*  4511 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28);
/*  4512 */                                               out.write(10);
/*  4513 */                                               out.write(9);
/*  4514 */                                               out.write(32);
/*       */                                               
/*  4516 */                                               OptionTag _jspx_th_html_005foption_005f29 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4517 */                                               _jspx_th_html_005foption_005f29.setPageContext(_jspx_page_context);
/*  4518 */                                               _jspx_th_html_005foption_005f29.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4520 */                                               _jspx_th_html_005foption_005f29.setValue("HP-UX");
/*  4521 */                                               int _jspx_eval_html_005foption_005f29 = _jspx_th_html_005foption_005f29.doStartTag();
/*  4522 */                                               if (_jspx_eval_html_005foption_005f29 != 0) {
/*  4523 */                                                 if (_jspx_eval_html_005foption_005f29 != 1) {
/*  4524 */                                                   out = _jspx_page_context.pushBody();
/*  4525 */                                                   _jspx_th_html_005foption_005f29.setBodyContent((BodyContent)out);
/*  4526 */                                                   _jspx_th_html_005foption_005f29.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4529 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.hpux"));
/*  4530 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f29.doAfterBody();
/*  4531 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4534 */                                                 if (_jspx_eval_html_005foption_005f29 != 1) {
/*  4535 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4538 */                                               if (_jspx_th_html_005foption_005f29.doEndTag() == 5) {
/*  4539 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29); return;
/*       */                                               }
/*       */                                               
/*  4542 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29);
/*  4543 */                                               out.write(10);
/*  4544 */                                               out.write(9);
/*  4545 */                                               out.write(32);
/*       */                                               
/*  4547 */                                               OptionTag _jspx_th_html_005foption_005f30 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4548 */                                               _jspx_th_html_005foption_005f30.setPageContext(_jspx_page_context);
/*  4549 */                                               _jspx_th_html_005foption_005f30.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4551 */                                               _jspx_th_html_005foption_005f30.setValue("AS400/iSeries");
/*  4552 */                                               int _jspx_eval_html_005foption_005f30 = _jspx_th_html_005foption_005f30.doStartTag();
/*  4553 */                                               if (_jspx_eval_html_005foption_005f30 != 0) {
/*  4554 */                                                 if (_jspx_eval_html_005foption_005f30 != 1) {
/*  4555 */                                                   out = _jspx_page_context.pushBody();
/*  4556 */                                                   _jspx_th_html_005foption_005f30.setBodyContent((BodyContent)out);
/*  4557 */                                                   _jspx_th_html_005foption_005f30.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4560 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.as400"));
/*  4561 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f30.doAfterBody();
/*  4562 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4565 */                                                 if (_jspx_eval_html_005foption_005f30 != 1) {
/*  4566 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4569 */                                               if (_jspx_th_html_005foption_005f30.doEndTag() == 5) {
/*  4570 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30); return;
/*       */                                               }
/*       */                                               
/*  4573 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30);
/*  4574 */                                               out.write(10);
/*  4575 */                                               out.write(9);
/*  4576 */                                               out.write(32);
/*       */                                               
/*  4578 */                                               OptionTag _jspx_th_html_005foption_005f31 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4579 */                                               _jspx_th_html_005foption_005f31.setPageContext(_jspx_page_context);
/*  4580 */                                               _jspx_th_html_005foption_005f31.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4582 */                                               _jspx_th_html_005foption_005f31.setValue("Linux");
/*  4583 */                                               int _jspx_eval_html_005foption_005f31 = _jspx_th_html_005foption_005f31.doStartTag();
/*  4584 */                                               if (_jspx_eval_html_005foption_005f31 != 0) {
/*  4585 */                                                 if (_jspx_eval_html_005foption_005f31 != 1) {
/*  4586 */                                                   out = _jspx_page_context.pushBody();
/*  4587 */                                                   _jspx_th_html_005foption_005f31.setBodyContent((BodyContent)out);
/*  4588 */                                                   _jspx_th_html_005foption_005f31.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4591 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.linux"));
/*  4592 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f31.doAfterBody();
/*  4593 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4596 */                                                 if (_jspx_eval_html_005foption_005f31 != 1) {
/*  4597 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4600 */                                               if (_jspx_th_html_005foption_005f31.doEndTag() == 5) {
/*  4601 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31); return;
/*       */                                               }
/*       */                                               
/*  4604 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31);
/*  4605 */                                               out.write(10);
/*  4606 */                                               out.write(9);
/*  4607 */                                               out.write(32);
/*       */                                               
/*  4609 */                                               OptionTag _jspx_th_html_005foption_005f32 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4610 */                                               _jspx_th_html_005foption_005f32.setPageContext(_jspx_page_context);
/*  4611 */                                               _jspx_th_html_005foption_005f32.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4613 */                                               _jspx_th_html_005foption_005f32.setValue("Novell");
/*  4614 */                                               int _jspx_eval_html_005foption_005f32 = _jspx_th_html_005foption_005f32.doStartTag();
/*  4615 */                                               if (_jspx_eval_html_005foption_005f32 != 0) {
/*  4616 */                                                 if (_jspx_eval_html_005foption_005f32 != 1) {
/*  4617 */                                                   out = _jspx_page_context.pushBody();
/*  4618 */                                                   _jspx_th_html_005foption_005f32.setBodyContent((BodyContent)out);
/*  4619 */                                                   _jspx_th_html_005foption_005f32.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4622 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.novell"));
/*  4623 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f32.doAfterBody();
/*  4624 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4627 */                                                 if (_jspx_eval_html_005foption_005f32 != 1) {
/*  4628 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4631 */                                               if (_jspx_th_html_005foption_005f32.doEndTag() == 5) {
/*  4632 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32); return;
/*       */                                               }
/*       */                                               
/*  4635 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32);
/*  4636 */                                               out.write(10);
/*  4637 */                                               out.write(9);
/*  4638 */                                               out.write(32);
/*       */                                               
/*  4640 */                                               OptionTag _jspx_th_html_005foption_005f33 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4641 */                                               _jspx_th_html_005foption_005f33.setPageContext(_jspx_page_context);
/*  4642 */                                               _jspx_th_html_005foption_005f33.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4644 */                                               _jspx_th_html_005foption_005f33.setValue("Mac OS");
/*  4645 */                                               int _jspx_eval_html_005foption_005f33 = _jspx_th_html_005foption_005f33.doStartTag();
/*  4646 */                                               if (_jspx_eval_html_005foption_005f33 != 0) {
/*  4647 */                                                 if (_jspx_eval_html_005foption_005f33 != 1) {
/*  4648 */                                                   out = _jspx_page_context.pushBody();
/*  4649 */                                                   _jspx_th_html_005foption_005f33.setBodyContent((BodyContent)out);
/*  4650 */                                                   _jspx_th_html_005foption_005f33.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4653 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.mac"));
/*  4654 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f33.doAfterBody();
/*  4655 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4658 */                                                 if (_jspx_eval_html_005foption_005f33 != 1) {
/*  4659 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4662 */                                               if (_jspx_th_html_005foption_005f33.doEndTag() == 5) {
/*  4663 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33); return;
/*       */                                               }
/*       */                                               
/*  4666 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33);
/*  4667 */                                               out.write(10);
/*  4668 */                                               out.write(9);
/*  4669 */                                               out.write(32);
/*       */                                               
/*  4671 */                                               OptionTag _jspx_th_html_005foption_005f34 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4672 */                                               _jspx_th_html_005foption_005f34.setPageContext(_jspx_page_context);
/*  4673 */                                               _jspx_th_html_005foption_005f34.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4675 */                                               _jspx_th_html_005foption_005f34.setValue("SUN");
/*  4676 */                                               int _jspx_eval_html_005foption_005f34 = _jspx_th_html_005foption_005f34.doStartTag();
/*  4677 */                                               if (_jspx_eval_html_005foption_005f34 != 0) {
/*  4678 */                                                 if (_jspx_eval_html_005foption_005f34 != 1) {
/*  4679 */                                                   out = _jspx_page_context.pushBody();
/*  4680 */                                                   _jspx_th_html_005foption_005f34.setBodyContent((BodyContent)out);
/*  4681 */                                                   _jspx_th_html_005foption_005f34.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4684 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.sunsolaris"));
/*  4685 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f34.doAfterBody();
/*  4686 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4689 */                                                 if (_jspx_eval_html_005foption_005f34 != 1) {
/*  4690 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4693 */                                               if (_jspx_th_html_005foption_005f34.doEndTag() == 5) {
/*  4694 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34); return;
/*       */                                               }
/*       */                                               
/*  4697 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34);
/*  4698 */                                               out.write(10);
/*  4699 */                                               out.write(9);
/*  4700 */                                               out.write(32);
/*       */                                               
/*  4702 */                                               OptionTag _jspx_th_html_005foption_005f35 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4703 */                                               _jspx_th_html_005foption_005f35.setPageContext(_jspx_page_context);
/*  4704 */                                               _jspx_th_html_005foption_005f35.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4706 */                                               _jspx_th_html_005foption_005f35.setValue("HP-TRU64");
/*  4707 */                                               int _jspx_eval_html_005foption_005f35 = _jspx_th_html_005foption_005f35.doStartTag();
/*  4708 */                                               if (_jspx_eval_html_005foption_005f35 != 0) {
/*  4709 */                                                 if (_jspx_eval_html_005foption_005f35 != 1) {
/*  4710 */                                                   out = _jspx_page_context.pushBody();
/*  4711 */                                                   _jspx_th_html_005foption_005f35.setBodyContent((BodyContent)out);
/*  4712 */                                                   _jspx_th_html_005foption_005f35.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4715 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.hptru64"));
/*  4716 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f35.doAfterBody();
/*  4717 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4720 */                                                 if (_jspx_eval_html_005foption_005f35 != 1) {
/*  4721 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4724 */                                               if (_jspx_th_html_005foption_005f35.doEndTag() == 5) {
/*  4725 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35); return;
/*       */                                               }
/*       */                                               
/*  4728 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35);
/*  4729 */                                               out.write(10);
/*  4730 */                                               out.write(9);
/*  4731 */                                               out.write(32);
/*       */                                               
/*  4733 */                                               OptionTag _jspx_th_html_005foption_005f36 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4734 */                                               _jspx_th_html_005foption_005f36.setPageContext(_jspx_page_context);
/*  4735 */                                               _jspx_th_html_005foption_005f36.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4737 */                                               _jspx_th_html_005foption_005f36.setValue("Windows 2000");
/*  4738 */                                               int _jspx_eval_html_005foption_005f36 = _jspx_th_html_005foption_005f36.doStartTag();
/*  4739 */                                               if (_jspx_eval_html_005foption_005f36 != 0) {
/*  4740 */                                                 if (_jspx_eval_html_005foption_005f36 != 1) {
/*  4741 */                                                   out = _jspx_page_context.pushBody();
/*  4742 */                                                   _jspx_th_html_005foption_005f36.setBodyContent((BodyContent)out);
/*  4743 */                                                   _jspx_th_html_005foption_005f36.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4746 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.win2000"));
/*  4747 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f36.doAfterBody();
/*  4748 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4751 */                                                 if (_jspx_eval_html_005foption_005f36 != 1) {
/*  4752 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4755 */                                               if (_jspx_th_html_005foption_005f36.doEndTag() == 5) {
/*  4756 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36); return;
/*       */                                               }
/*       */                                               
/*  4759 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36);
/*  4760 */                                               out.write(10);
/*  4761 */                                               out.write(9);
/*  4762 */                                               out.write(32);
/*       */                                               
/*  4764 */                                               OptionTag _jspx_th_html_005foption_005f37 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4765 */                                               _jspx_th_html_005foption_005f37.setPageContext(_jspx_page_context);
/*  4766 */                                               _jspx_th_html_005foption_005f37.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4768 */                                               _jspx_th_html_005foption_005f37.setValue("Windows 2003");
/*  4769 */                                               int _jspx_eval_html_005foption_005f37 = _jspx_th_html_005foption_005f37.doStartTag();
/*  4770 */                                               if (_jspx_eval_html_005foption_005f37 != 0) {
/*  4771 */                                                 if (_jspx_eval_html_005foption_005f37 != 1) {
/*  4772 */                                                   out = _jspx_page_context.pushBody();
/*  4773 */                                                   _jspx_th_html_005foption_005f37.setBodyContent((BodyContent)out);
/*  4774 */                                                   _jspx_th_html_005foption_005f37.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4777 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.win2003"));
/*  4778 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f37.doAfterBody();
/*  4779 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4782 */                                                 if (_jspx_eval_html_005foption_005f37 != 1) {
/*  4783 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4786 */                                               if (_jspx_th_html_005foption_005f37.doEndTag() == 5) {
/*  4787 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37); return;
/*       */                                               }
/*       */                                               
/*  4790 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37);
/*  4791 */                                               out.write(10);
/*  4792 */                                               out.write(9);
/*  4793 */                                               out.write(32);
/*       */                                               
/*  4795 */                                               OptionTag _jspx_th_html_005foption_005f38 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4796 */                                               _jspx_th_html_005foption_005f38.setPageContext(_jspx_page_context);
/*  4797 */                                               _jspx_th_html_005foption_005f38.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4799 */                                               _jspx_th_html_005foption_005f38.setValue("Windows XP");
/*  4800 */                                               int _jspx_eval_html_005foption_005f38 = _jspx_th_html_005foption_005f38.doStartTag();
/*  4801 */                                               if (_jspx_eval_html_005foption_005f38 != 0) {
/*  4802 */                                                 if (_jspx_eval_html_005foption_005f38 != 1) {
/*  4803 */                                                   out = _jspx_page_context.pushBody();
/*  4804 */                                                   _jspx_th_html_005foption_005f38.setBodyContent((BodyContent)out);
/*  4805 */                                                   _jspx_th_html_005foption_005f38.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4808 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.winxp"));
/*  4809 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f38.doAfterBody();
/*  4810 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4813 */                                                 if (_jspx_eval_html_005foption_005f38 != 1) {
/*  4814 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4817 */                                               if (_jspx_th_html_005foption_005f38.doEndTag() == 5) {
/*  4818 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38); return;
/*       */                                               }
/*       */                                               
/*  4821 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38);
/*  4822 */                                               out.write(10);
/*  4823 */                                               out.write(9);
/*  4824 */                                               out.write(32);
/*       */                                               
/*  4826 */                                               OptionTag _jspx_th_html_005foption_005f39 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4827 */                                               _jspx_th_html_005foption_005f39.setPageContext(_jspx_page_context);
/*  4828 */                                               _jspx_th_html_005foption_005f39.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4830 */                                               _jspx_th_html_005foption_005f39.setValue("WindowsNT");
/*  4831 */                                               int _jspx_eval_html_005foption_005f39 = _jspx_th_html_005foption_005f39.doStartTag();
/*  4832 */                                               if (_jspx_eval_html_005foption_005f39 != 0) {
/*  4833 */                                                 if (_jspx_eval_html_005foption_005f39 != 1) {
/*  4834 */                                                   out = _jspx_page_context.pushBody();
/*  4835 */                                                   _jspx_th_html_005foption_005f39.setBodyContent((BodyContent)out);
/*  4836 */                                                   _jspx_th_html_005foption_005f39.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4839 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.winnt"));
/*  4840 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f39.doAfterBody();
/*  4841 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4844 */                                                 if (_jspx_eval_html_005foption_005f39 != 1) {
/*  4845 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4848 */                                               if (_jspx_th_html_005foption_005f39.doEndTag() == 5) {
/*  4849 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39); return;
/*       */                                               }
/*       */                                               
/*  4852 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39);
/*  4853 */                                               out.write(10);
/*  4854 */                                               out.write(9);
/*  4855 */                                               out.write(32);
/*       */                                               
/*  4857 */                                               OptionTag _jspx_th_html_005foption_005f40 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4858 */                                               _jspx_th_html_005foption_005f40.setPageContext(_jspx_page_context);
/*  4859 */                                               _jspx_th_html_005foption_005f40.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4861 */                                               _jspx_th_html_005foption_005f40.setValue("Windows Vista");
/*  4862 */                                               int _jspx_eval_html_005foption_005f40 = _jspx_th_html_005foption_005f40.doStartTag();
/*  4863 */                                               if (_jspx_eval_html_005foption_005f40 != 0) {
/*  4864 */                                                 if (_jspx_eval_html_005foption_005f40 != 1) {
/*  4865 */                                                   out = _jspx_page_context.pushBody();
/*  4866 */                                                   _jspx_th_html_005foption_005f40.setBodyContent((BodyContent)out);
/*  4867 */                                                   _jspx_th_html_005foption_005f40.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4870 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.winvista"));
/*  4871 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f40.doAfterBody();
/*  4872 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4875 */                                                 if (_jspx_eval_html_005foption_005f40 != 1) {
/*  4876 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4879 */                                               if (_jspx_th_html_005foption_005f40.doEndTag() == 5) {
/*  4880 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40); return;
/*       */                                               }
/*       */                                               
/*  4883 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40);
/*  4884 */                                               out.write(10);
/*  4885 */                                               out.write(9);
/*  4886 */                                               out.write(32);
/*       */                                               
/*  4888 */                                               OptionTag _jspx_th_html_005foption_005f41 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4889 */                                               _jspx_th_html_005foption_005f41.setPageContext(_jspx_page_context);
/*  4890 */                                               _jspx_th_html_005foption_005f41.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4892 */                                               _jspx_th_html_005foption_005f41.setValue("Windows 7");
/*  4893 */                                               int _jspx_eval_html_005foption_005f41 = _jspx_th_html_005foption_005f41.doStartTag();
/*  4894 */                                               if (_jspx_eval_html_005foption_005f41 != 0) {
/*  4895 */                                                 if (_jspx_eval_html_005foption_005f41 != 1) {
/*  4896 */                                                   out = _jspx_page_context.pushBody();
/*  4897 */                                                   _jspx_th_html_005foption_005f41.setBodyContent((BodyContent)out);
/*  4898 */                                                   _jspx_th_html_005foption_005f41.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4901 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.win7"));
/*  4902 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f41.doAfterBody();
/*  4903 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4906 */                                                 if (_jspx_eval_html_005foption_005f41 != 1) {
/*  4907 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4910 */                                               if (_jspx_th_html_005foption_005f41.doEndTag() == 5) {
/*  4911 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41); return;
/*       */                                               }
/*       */                                               
/*  4914 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41);
/*  4915 */                                               out.write(10);
/*  4916 */                                               out.write(9);
/*  4917 */                                               out.write(32);
/*       */                                               
/*  4919 */                                               OptionTag _jspx_th_html_005foption_005f42 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4920 */                                               _jspx_th_html_005foption_005f42.setPageContext(_jspx_page_context);
/*  4921 */                                               _jspx_th_html_005foption_005f42.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4923 */                                               _jspx_th_html_005foption_005f42.setValue("Windows 2008");
/*  4924 */                                               int _jspx_eval_html_005foption_005f42 = _jspx_th_html_005foption_005f42.doStartTag();
/*  4925 */                                               if (_jspx_eval_html_005foption_005f42 != 0) {
/*  4926 */                                                 if (_jspx_eval_html_005foption_005f42 != 1) {
/*  4927 */                                                   out = _jspx_page_context.pushBody();
/*  4928 */                                                   _jspx_th_html_005foption_005f42.setBodyContent((BodyContent)out);
/*  4929 */                                                   _jspx_th_html_005foption_005f42.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4932 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.winn2008"));
/*  4933 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f42.doAfterBody();
/*  4934 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4937 */                                                 if (_jspx_eval_html_005foption_005f42 != 1) {
/*  4938 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4941 */                                               if (_jspx_th_html_005foption_005f42.doEndTag() == 5) {
/*  4942 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42); return;
/*       */                                               }
/*       */                                               
/*  4945 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42);
/*  4946 */                                               out.write(10);
/*  4947 */                                               out.write(9);
/*  4948 */                                               out.write(32);
/*       */                                               
/*  4950 */                                               OptionTag _jspx_th_html_005foption_005f43 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4951 */                                               _jspx_th_html_005foption_005f43.setPageContext(_jspx_page_context);
/*  4952 */                                               _jspx_th_html_005foption_005f43.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4954 */                                               _jspx_th_html_005foption_005f43.setValue("Windows 8");
/*  4955 */                                               int _jspx_eval_html_005foption_005f43 = _jspx_th_html_005foption_005f43.doStartTag();
/*  4956 */                                               if (_jspx_eval_html_005foption_005f43 != 0) {
/*  4957 */                                                 if (_jspx_eval_html_005foption_005f43 != 1) {
/*  4958 */                                                   out = _jspx_page_context.pushBody();
/*  4959 */                                                   _jspx_th_html_005foption_005f43.setBodyContent((BodyContent)out);
/*  4960 */                                                   _jspx_th_html_005foption_005f43.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4963 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.winn8"));
/*  4964 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f43.doAfterBody();
/*  4965 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4968 */                                                 if (_jspx_eval_html_005foption_005f43 != 1) {
/*  4969 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  4972 */                                               if (_jspx_th_html_005foption_005f43.doEndTag() == 5) {
/*  4973 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43); return;
/*       */                                               }
/*       */                                               
/*  4976 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43);
/*  4977 */                                               out.write(10);
/*  4978 */                                               out.write(9);
/*  4979 */                                               out.write(32);
/*       */                                               
/*  4981 */                                               OptionTag _jspx_th_html_005foption_005f44 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  4982 */                                               _jspx_th_html_005foption_005f44.setPageContext(_jspx_page_context);
/*  4983 */                                               _jspx_th_html_005foption_005f44.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  4985 */                                               _jspx_th_html_005foption_005f44.setValue("Windows 2012");
/*  4986 */                                               int _jspx_eval_html_005foption_005f44 = _jspx_th_html_005foption_005f44.doStartTag();
/*  4987 */                                               if (_jspx_eval_html_005foption_005f44 != 0) {
/*  4988 */                                                 if (_jspx_eval_html_005foption_005f44 != 1) {
/*  4989 */                                                   out = _jspx_page_context.pushBody();
/*  4990 */                                                   _jspx_th_html_005foption_005f44.setBodyContent((BodyContent)out);
/*  4991 */                                                   _jspx_th_html_005foption_005f44.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  4994 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.winn2012"));
/*  4995 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f44.doAfterBody();
/*  4996 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  4999 */                                                 if (_jspx_eval_html_005foption_005f44 != 1) {
/*  5000 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  5003 */                                               if (_jspx_th_html_005foption_005f44.doEndTag() == 5) {
/*  5004 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44); return;
/*       */                                               }
/*       */                                               
/*  5007 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44);
/*  5008 */                                               out.write(10);
/*  5009 */                                               out.write(9);
/*  5010 */                                               out.write(32);
/*       */                                               
/*  5012 */                                               OptionTag _jspx_th_html_005foption_005f45 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  5013 */                                               _jspx_th_html_005foption_005f45.setPageContext(_jspx_page_context);
/*  5014 */                                               _jspx_th_html_005foption_005f45.setParent(_jspx_th_html_005fselect_005f5);
/*       */                                               
/*  5016 */                                               _jspx_th_html_005foption_005f45.setValue("Windows 10");
/*  5017 */                                               int _jspx_eval_html_005foption_005f45 = _jspx_th_html_005foption_005f45.doStartTag();
/*  5018 */                                               if (_jspx_eval_html_005foption_005f45 != 0) {
/*  5019 */                                                 if (_jspx_eval_html_005foption_005f45 != 1) {
/*  5020 */                                                   out = _jspx_page_context.pushBody();
/*  5021 */                                                   _jspx_th_html_005foption_005f45.setBodyContent((BodyContent)out);
/*  5022 */                                                   _jspx_th_html_005foption_005f45.doInitBody();
/*       */                                                 }
/*       */                                                 for (;;) {
/*  5025 */                                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.win10"));
/*  5026 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f45.doAfterBody();
/*  5027 */                                                   if (evalDoAfterBody != 2)
/*       */                                                     break;
/*       */                                                 }
/*  5030 */                                                 if (_jspx_eval_html_005foption_005f45 != 1) {
/*  5031 */                                                   out = _jspx_page_context.popBody();
/*       */                                                 }
/*       */                                               }
/*  5034 */                                               if (_jspx_th_html_005foption_005f45.doEndTag() == 5) {
/*  5035 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45); return;
/*       */                                               }
/*       */                                               
/*  5038 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45);
/*  5039 */                                               out.write("\n\t\t ");
/*       */                                             }
/*       */                                             
/*       */ 
/*  5043 */                                             out.write(10);
/*  5044 */                                             out.write(9);
/*  5045 */                                             out.write(32);
/*  5046 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/*  5047 */                                             if (evalDoAfterBody != 2)
/*       */                                               break;
/*       */                                           }
/*  5050 */                                           if (_jspx_eval_html_005fselect_005f5 != 1) {
/*  5051 */                                             out = _jspx_page_context.popBody();
/*       */                                           }
/*       */                                         }
/*  5054 */                                         if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/*  5055 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f5); return;
/*       */                                         }
/*       */                                         
/*  5058 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f5);
/*  5059 */                                         out.write("</td>\n\t </tr>\n\t <tr id=\"mode\" style=\"Display:none\">\n\t   <td width=\"100%\" colspan=\"2\" class=\"cellpadd-none\">\n\t      <table width=\"100%\" cellpadding=\"6\" cellspacing=\"0\">\n\t       <tr>\n\t        <td  width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  5060 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.mode"));
/*  5061 */                                         out.write("<span class=\"mandatory\">*</span></label></td>\n\t        <td class=\"bodytext\">\n                 <table>\n                 <tr>\n                 <td>\n                     ");
/*       */                                         
/*  5063 */                                         SelectTag _jspx_th_html_005fselect_005f6 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  5064 */                                         _jspx_th_html_005fselect_005f6.setPageContext(_jspx_page_context);
/*  5065 */                                         _jspx_th_html_005fselect_005f6.setParent(_jspx_th_html_005fform_005f0);
/*       */                                         
/*  5067 */                                         _jspx_th_html_005fselect_005f6.setProperty("mode");
/*       */                                         
/*  5069 */                                         _jspx_th_html_005fselect_005f6.setStyleClass("formtext medium");
/*       */                                         
/*  5071 */                                         _jspx_th_html_005fselect_005f6.setOnchange("javascript:changeport()");
/*  5072 */                                         int _jspx_eval_html_005fselect_005f6 = _jspx_th_html_005fselect_005f6.doStartTag();
/*  5073 */                                         if (_jspx_eval_html_005fselect_005f6 != 0) {
/*  5074 */                                           if (_jspx_eval_html_005fselect_005f6 != 1) {
/*  5075 */                                             out = _jspx_page_context.pushBody();
/*  5076 */                                             _jspx_th_html_005fselect_005f6.setBodyContent((BodyContent)out);
/*  5077 */                                             _jspx_th_html_005fselect_005f6.doInitBody();
/*       */                                           }
/*       */                                           for (;;) {
/*  5080 */                                             out.write("\n        \t ");
/*       */                                             
/*  5082 */                                             OptionTag _jspx_th_html_005foption_005f46 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  5083 */                                             _jspx_th_html_005foption_005f46.setPageContext(_jspx_page_context);
/*  5084 */                                             _jspx_th_html_005foption_005f46.setParent(_jspx_th_html_005fselect_005f6);
/*       */                                             
/*  5086 */                                             _jspx_th_html_005foption_005f46.setValue("TELNET");
/*  5087 */                                             int _jspx_eval_html_005foption_005f46 = _jspx_th_html_005foption_005f46.doStartTag();
/*  5088 */                                             if (_jspx_eval_html_005foption_005f46 != 0) {
/*  5089 */                                               if (_jspx_eval_html_005foption_005f46 != 1) {
/*  5090 */                                                 out = _jspx_page_context.pushBody();
/*  5091 */                                                 _jspx_th_html_005foption_005f46.setBodyContent((BodyContent)out);
/*  5092 */                                                 _jspx_th_html_005foption_005f46.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  5095 */                                                 out.write(32);
/*  5096 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.telnet"));
/*  5097 */                                                 out.write(32);
/*  5098 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f46.doAfterBody();
/*  5099 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  5102 */                                               if (_jspx_eval_html_005foption_005f46 != 1) {
/*  5103 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  5106 */                                             if (_jspx_th_html_005foption_005f46.doEndTag() == 5) {
/*  5107 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46); return;
/*       */                                             }
/*       */                                             
/*  5110 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46);
/*  5111 */                                             out.write("\n\t         ");
/*       */                                             
/*  5113 */                                             OptionTag _jspx_th_html_005foption_005f47 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  5114 */                                             _jspx_th_html_005foption_005f47.setPageContext(_jspx_page_context);
/*  5115 */                                             _jspx_th_html_005foption_005f47.setParent(_jspx_th_html_005fselect_005f6);
/*       */                                             
/*  5117 */                                             _jspx_th_html_005foption_005f47.setValue("SSH");
/*  5118 */                                             int _jspx_eval_html_005foption_005f47 = _jspx_th_html_005foption_005f47.doStartTag();
/*  5119 */                                             if (_jspx_eval_html_005foption_005f47 != 0) {
/*  5120 */                                               if (_jspx_eval_html_005foption_005f47 != 1) {
/*  5121 */                                                 out = _jspx_page_context.pushBody();
/*  5122 */                                                 _jspx_th_html_005foption_005f47.setBodyContent((BodyContent)out);
/*  5123 */                                                 _jspx_th_html_005foption_005f47.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  5126 */                                                 out.write(32);
/*  5127 */                                                 out.write(32);
/*  5128 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh"));
/*  5129 */                                                 out.write("    ");
/*  5130 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f47.doAfterBody();
/*  5131 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  5134 */                                               if (_jspx_eval_html_005foption_005f47 != 1) {
/*  5135 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  5138 */                                             if (_jspx_th_html_005foption_005f47.doEndTag() == 5) {
/*  5139 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47); return;
/*       */                                             }
/*       */                                             
/*  5142 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47);
/*  5143 */                                             out.write("\n\t         ");
/*       */                                             
/*  5145 */                                             OptionTag _jspx_th_html_005foption_005f48 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  5146 */                                             _jspx_th_html_005foption_005f48.setPageContext(_jspx_page_context);
/*  5147 */                                             _jspx_th_html_005foption_005f48.setParent(_jspx_th_html_005fselect_005f6);
/*       */                                             
/*  5149 */                                             _jspx_th_html_005foption_005f48.setValue("SNMP");
/*  5150 */                                             int _jspx_eval_html_005foption_005f48 = _jspx_th_html_005foption_005f48.doStartTag();
/*  5151 */                                             if (_jspx_eval_html_005foption_005f48 != 0) {
/*  5152 */                                               if (_jspx_eval_html_005foption_005f48 != 1) {
/*  5153 */                                                 out = _jspx_page_context.pushBody();
/*  5154 */                                                 _jspx_th_html_005foption_005f48.setBodyContent((BodyContent)out);
/*  5155 */                                                 _jspx_th_html_005foption_005f48.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  5158 */                                                 out.write(32);
/*  5159 */                                                 out.write(32);
/*  5160 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.snmp"));
/*  5161 */                                                 out.write("    ");
/*  5162 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f48.doAfterBody();
/*  5163 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  5166 */                                               if (_jspx_eval_html_005foption_005f48 != 1) {
/*  5167 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  5170 */                                             if (_jspx_th_html_005foption_005f48.doEndTag() == 5) {
/*  5171 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48); return;
/*       */                                             }
/*       */                                             
/*  5174 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48);
/*  5175 */                                             out.write("\n\t           ");
/*  5176 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f6.doAfterBody();
/*  5177 */                                             if (evalDoAfterBody != 2)
/*       */                                               break;
/*       */                                           }
/*  5180 */                                           if (_jspx_eval_html_005fselect_005f6 != 1) {
/*  5181 */                                             out = _jspx_page_context.popBody();
/*       */                                           }
/*       */                                         }
/*  5184 */                                         if (_jspx_th_html_005fselect_005f6.doEndTag() == 5) {
/*  5185 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f6); return;
/*       */                                         }
/*       */                                         
/*  5188 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f6);
/*  5189 */                                         out.write("</td> <td id=\"modePort\" class=\"bodytext\">");
/*  5190 */                                         out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/*  5191 */                                         out.write("&nbsp;&nbsp;");
/*  5192 */                                         if (_jspx_meth_html_005ftext_005f18(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5194 */                                         out.write("</td>\n                  </tr>\n                 </table>\n                 </td></tr>\n\t\t  </table>\n\t\t</td>\n\t   </tr>\n           <tr id=\"eventlogstatus\" style=\"display:none\">\n\t            <td width=\"25%\" class=\"bodytext label-align addmonitor-label\"></td>\n\t           <td width=\"75%\" class=\"bodytext\">\n\n\t                 ");
/*  5195 */                                         if (_jspx_meth_html_005fcheckbox_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5197 */                                         out.write("\n\t\t\t\t\t ");
/*  5198 */                                         out.print(FormatUtil.getString("am.webclient.eventlogrules.enableevventlog.text"));
/*  5199 */                                         out.write("\n\n\t             </td>\n           </tr>\n\t  <tr id=\"snmpCommunityString\" >\n        <td class=\"bodytext label-align addmonitor-label\"><label>");
/*  5200 */                                         out.print(FormatUtil.getString("Port"));
/*  5201 */                                         out.write("<span class=\"mandatory\">*</span></label></td>\n                    <td height=\"28\" colspan=\"2\" id=\"snmpPortID\">");
/*  5202 */                                         if (_jspx_meth_html_005ftext_005f19(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5204 */                                         out.write("\n         </td>\n      </tr>\n      ");
/*       */                                       }
/*       */                                       
/*  5207 */                                       if (type.equals("APACHE"))
/*       */                                       {
/*       */ 
/*  5210 */                                         out.write("\n\n\t\t<tr>\n        <td width=\"25%\" class=\"bodytext label-align addmonitor-label\">");
/*  5211 */                                         out.print(FormatUtil.getString("am.webclient.apache.authentication.required"));
/*  5212 */                                         out.write("</td>\n        <td colspan=\"2\">");
/*  5213 */                                         if (_jspx_meth_html_005fcheckbox_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5215 */                                         out.write("</td>\n        </tr>\n        <tr>\n\t\t<TD class=\"bodytext\" colspan=\"3\"> \n\t\t<div id=\"apacheauthinfo\"   ");
/*  5216 */                                         if (_jspx_meth_c_005fif_005f15(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5218 */                                         out.write(">\n\n\t\t<TABLE  border=\"0\" CELLSPACING=0 CELLPADDING=8 WIDTH=\"100%\" align=\"left\">\n\t\t<TR >\n\n\t\t<TD class=\"bodytext label-align addmonitor-label\" height=\"28\" width=\"25%\"><label>");
/*  5219 */                                         out.print(FormatUtil.getString("am.webclient.apache.username.text"));
/*  5220 */                                         out.write("</label></TD>\n\t\t<TD> <input type=\"text\" name=\"apacheUserName\" class=\"formtext default\" autocomplete=\"off\" /><!--");
/*  5221 */                                         if (_jspx_meth_html_005ftext_005f20(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5223 */                                         out.write("--> </TD>\n\t\t</tr>\n\n\t\t<TR>\n\n\t\t<TD class=\"bodytext label-align addmonitor-label\" ><label>");
/*  5224 */                                         out.print(FormatUtil.getString("am.webclient.apache.password.text"));
/*  5225 */                                         out.write("</label></TD>\n\t\t<TD> <input type=\"password\" name=\"apachepassword\" class=\"formtext default\" autocomplete=\"off\" /><!--");
/*  5226 */                                         if (_jspx_meth_html_005fpassword_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5228 */                                         out.write("--> </TD>\n\t\t</tr>\n\t\t</TABLE>\n\n\t\t</div>\n\n\t\t</TR>\n        <tr>\n            <td width=\"25%\" class=\"bodytext label-align addmonitor-label\">");
/*  5229 */                                         out.print(FormatUtil.getString("am.webclient.apache.specifyserverstatusurl"));
/*  5230 */                                         out.write("</td>\n            <td width=\"75%\" colspan=\"2\">");
/*  5231 */                                         if (_jspx_meth_html_005fcheckbox_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5233 */                                         out.write("</td>\n        </tr>\n\t\t<TD height=\"28\" class=\"bodytext\" colspan=\"3\"> \n\t\t<div id=\"serverstatus\"   ");
/*  5234 */                                         if (_jspx_meth_c_005fif_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5236 */                                         out.write(">\n\n                <TABLE  border=\"0\" CELLSPACING=0 CELLPADDING=8 WIDTH=\"100%\" align=\"left\">\n\t\t<TR>\n\n\t\t<TD class=\"bodytext label-align addmonitor-label\" height=\"28\" width=\"25%\"><label>");
/*  5237 */                                         out.print(FormatUtil.getString("am.webclient.apache.statusurl"));
/*  5238 */                                         out.write("</label></TD>\n\n\t\t<TD> ");
/*  5239 */                                         if (_jspx_meth_html_005ftext_005f21(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5241 */                                         out.write(" </TD>\n\t\t</tr>\n                </table>\n\t\t</div>\n\n\t\t</TR>\n\n\t\t");
/*       */                                       }
/*       */                                       
/*       */ 
/*  5245 */                                       if (type.equals("WEBSPHERE"))
/*       */                                       {
/*       */ 
/*  5248 */                                         out.write("\n                        <tr>\n\t\t\t<td class=\"bodytext label-align addmonitor-label\"><label>");
/*  5249 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.websphereversion"));
/*  5250 */                                         out.write("<span class=\"mandatory\">*</span></label></td>\n\t\t\t<td class=\"bodytext\" colspan=\"2\"> ");
/*       */                                         
/*  5252 */                                         SelectTag _jspx_th_html_005fselect_005f7 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  5253 */                                         _jspx_th_html_005fselect_005f7.setPageContext(_jspx_page_context);
/*  5254 */                                         _jspx_th_html_005fselect_005f7.setParent(_jspx_th_html_005fform_005f0);
/*       */                                         
/*  5256 */                                         _jspx_th_html_005fselect_005f7.setProperty("version");
/*       */                                         
/*  5258 */                                         _jspx_th_html_005fselect_005f7.setStyleClass("formtext medium");
/*       */                                         
/*  5260 */                                         _jspx_th_html_005fselect_005f7.setOnchange("onComboChange1()");
/*  5261 */                                         int _jspx_eval_html_005fselect_005f7 = _jspx_th_html_005fselect_005f7.doStartTag();
/*  5262 */                                         if (_jspx_eval_html_005fselect_005f7 != 0) {
/*  5263 */                                           if (_jspx_eval_html_005fselect_005f7 != 1) {
/*  5264 */                                             out = _jspx_page_context.pushBody();
/*  5265 */                                             _jspx_th_html_005fselect_005f7.setBodyContent((BodyContent)out);
/*  5266 */                                             _jspx_th_html_005fselect_005f7.doInitBody();
/*       */                                           }
/*       */                                           for (;;) {
/*  5269 */                                             out.write(" <option value=\"0\" selected>");
/*  5270 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.versionselect"));
/*  5271 */                                             out.write("\n\t\t\t");
/*  5272 */                                             if (_jspx_meth_html_005foption_005f49(_jspx_th_html_005fselect_005f7, _jspx_page_context))
/*       */                                               return;
/*  5274 */                                             out.write(32);
/*  5275 */                                             if (_jspx_meth_html_005foption_005f50(_jspx_th_html_005fselect_005f7, _jspx_page_context)) {
/*       */                                               return;
/*       */                                             }
/*  5278 */                                             OptionTag _jspx_th_html_005foption_005f51 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  5279 */                                             _jspx_th_html_005foption_005f51.setPageContext(_jspx_page_context);
/*  5280 */                                             _jspx_th_html_005foption_005f51.setParent(_jspx_th_html_005fselect_005f7);
/*       */                                             
/*  5282 */                                             _jspx_th_html_005foption_005f51.setValue("7");
/*  5283 */                                             int _jspx_eval_html_005foption_005f51 = _jspx_th_html_005foption_005f51.doStartTag();
/*  5284 */                                             if (_jspx_eval_html_005foption_005f51 != 0) {
/*  5285 */                                               if (_jspx_eval_html_005foption_005f51 != 1) {
/*  5286 */                                                 out = _jspx_page_context.pushBody();
/*  5287 */                                                 _jspx_th_html_005foption_005f51.setBodyContent((BodyContent)out);
/*  5288 */                                                 _jspx_th_html_005foption_005f51.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  5291 */                                                 out.print(FormatUtil.getString("am.websphere.version.7.text"));
/*  5292 */                                                 out.write(32);
/*  5293 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f51.doAfterBody();
/*  5294 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  5297 */                                               if (_jspx_eval_html_005foption_005f51 != 1) {
/*  5298 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  5301 */                                             if (_jspx_th_html_005foption_005f51.doEndTag() == 5) {
/*  5302 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51); return;
/*       */                                             }
/*       */                                             
/*  5305 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51);
/*  5306 */                                             out.write("\n\t\t\t");
/*  5307 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f7.doAfterBody();
/*  5308 */                                             if (evalDoAfterBody != 2)
/*       */                                               break;
/*       */                                           }
/*  5311 */                                           if (_jspx_eval_html_005fselect_005f7 != 1) {
/*  5312 */                                             out = _jspx_page_context.popBody();
/*       */                                           }
/*       */                                         }
/*  5315 */                                         if (_jspx_th_html_005fselect_005f7.doEndTag() == 5) {
/*  5316 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f7); return;
/*       */                                         }
/*       */                                         
/*  5319 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f7);
/*  5320 */                                         out.write("\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<TD height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  5321 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.soapport"));
/*  5322 */                                         out.write("</label>\n\t\t\t<TD height=\"28\" width=\"75%\" colspan=\"2\"><INPUT NAME=\"soapport\" TYPE=\"text\" class=\"formtext small\" VALUE=\"8880\">&nbsp;<span class=\"bodytext\"></span></TD>\n\t\t\t</tr>\n\n\t\t\t <tr >\n\t\t\t <td height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\">&nbsp;</td>\n\t\t\t <td height=\"28\" width=\"75%\" >");
/*  5323 */                                         if (_jspx_meth_html_005fcheckbox_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5325 */                                         out.write("\n             ");
/*  5326 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssl"));
/*  5327 */                                         out.write("</td>\n\t\t\t </tr>\n\t\t\t <tr >\n\t\t\t <td height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\">&nbsp;</td>\n\t\t\t <td height=\"28\" width=\"75%\">");
/*  5328 */                                         if (_jspx_meth_html_005fcheckbox_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5330 */                                         out.write("\n             ");
/*  5331 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.globalsecurity"));
/*  5332 */                                         out.write("</td>\n\t\t\t </tr>\n\t\t\t<TR id=\"username\" ");
/*  5333 */                                         if (_jspx_meth_c_005fif_005f17(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5335 */                                         out.write(" class=\"evenrowbgcolor\">\n\t\t\t<TD height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  5336 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/*  5337 */                                         out.write("</label></TD>\n\t\t\t<TD height=\"28\" width=\"75%\"  colspan=\"2\"><input type=\"text\" name=\"username\" class=\"formtext default\" autocomplete=\"off\" /><!--");
/*  5338 */                                         if (_jspx_meth_html_005ftext_005f22(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5340 */                                         out.write("-->\n\t\t\t&nbsp;  </TD>\n\t\t\t</TR>\n\t\t\t<TR id=\"password\" ");
/*  5341 */                                         if (_jspx_meth_c_005fif_005f18(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5343 */                                         out.write(" class=\"evenrowbgcolor\">\n\t\t\t<TD width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  5344 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/*  5345 */                                         out.write("</label></TD>\n\t\t\t<TD width=\"75%\" height=\"28\"  colspan=\"2\"><input type=\"password\" name=\"password\" class=\"formtext default\" autocomplete=\"off\" /> <!--");
/*  5346 */                                         if (_jspx_meth_html_005fpassword_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5348 */                                         out.write("-->\n\t\t\t&nbsp; </TD>\n\t\t\t</TR>\n\t\t\t<tr id=\"nddetails\" style=\"display:none\">\n\t\t\t<td width=\"100%\" colspan=3 class=\"bodytext\">\n\t\t\t<TABLE width=\"100%\" border=\"0\" cellpadding=\"6\" CELLSPACING=\"0\">\n\t\t\t<tr>\n\t\t\t<td height=\"28\" colspan=\"2\"><span class=\"bodytextbold\">");
/*  5349 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.details.customattribute"));
/*  5350 */                                         out.write("</span>\n\t\t\t</tr>\n\t\t\t<TR>\n\t\t\t<TD height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  5351 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.nwdeployer.host"));
/*  5352 */                                         out.write("<span class=\"mandatory\"></span></label></TD>\n\t\t\t<TD height=\"28\" width=\"75%\"><input type=\"text\" name=\"ndhost\" class=\"formtext default\" />\n\t\t\t</TD>\n\t\t\t</TR>\n\t\t\t<TR>\n\t\t\t<TD width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/*  5353 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.message.soapport"));
/*  5354 */                                         out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/*  5355 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.nwdeployer.port"));
/*  5356 */                                         out.write("</a><span class=\"mandatory\"></span></label></TD>\n\t\t\t<TD width=\"75%\" height=\"28\"> <input type=\"text\" name=\"ndport\" class=\"formtext default\"/> </TD>\n\t\t\t</TR>\n\t\t\t<tr>\n\t\t\t  <td height=\"28\" colspan=\"2\"><input type=\"checkbox\"  onclick=\"showAdvancedOptions(this)\"/><span class=\"bodytext\">");
/*  5357 */                                         out.print(FormatUtil.getString("am.webclient.websphere.wasadvanced.text"));
/*  5358 */                                         out.write("</span>\n\t\t\t</tr>\n\t\t\t<tr id=\"wasAdvanced\" style=\"display:none\">\n\t\t\t<TD width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  5359 */                                         out.print(FormatUtil.getString("am.webclient.websphere.appservers.text"));
/*  5360 */                                         out.write("<span class=\"mandatory\"></span></label></TD>\n\t\t\t<TD width=\"75%\" > <textarea name=\"AppServers\"  rows=\"3\" cols=\"38\" class=\"formtextarea xlarge\"></textarea></TD>\n\t\t\t</TR>\n\t\t\t<tr id=\"washelp\" style=\"display:none\">\n\t\t\t<td width=\"25%\"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<td width=\"75%\" class=\"bodytext\">* eg : node1:server1,server2,server3 ; node2:server1,server2,server3;...<br> * ");
/*  5361 */                                         out.print(FormatUtil.getString("am.webclient.websphere.appservers.help.text"));
/*  5362 */                                         out.write("</td></tr>\n\t\t\t</table>\n\t\t\t<div id=\"basedetails\" style=\"DISPLAY: block\">\n\t\t\t&nbsp;\n\t\t</div>\n\t\t\t</td>\n\t\t\t</tr>\n\t\t");
/*       */                                       }
/*       */                                       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  5369 */                                       out.write("\n        ");
/*  5370 */                                       if ((type.equals("JDK1.5")) || (type.equals("JMX1.2-MX4J-RMI"))) {
/*  5371 */                                         out.write("\n        <TR>\n          <TD width=\"21%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  5372 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.jndi.name"));
/*  5373 */                                         out.write("<span class=\"mandatory\">*</span></label>\n          </TD>\n          <TD height=\"28\" colspan=\"2\">");
/*  5374 */                                         if (_jspx_meth_html_005ftext_005f23(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5376 */                                         out.write("\n          </TD>\n        </TR>\n        <TR>\n\t\t <TD height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\" >");
/*  5377 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.jmxenabled"));
/*  5378 */                                         out.write("</TD>\n\t\t <TD height=\"28\" width=\"75%\" >");
/*  5379 */                                         if (_jspx_meth_html_005fcheckbox_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5381 */                                         out.write("</TD>\n\t\t</TR>\n\t\t<TR id=\"jmxurlrow\" ");
/*  5382 */                                         if (_jspx_meth_c_005fif_005f19(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5384 */                                         out.write(" class=\"evenrowbgcolor\"> ");
/*  5385 */                                         out.write("\n\t\t  <TD width=\"21%\" height=\"28\" class=\"bodytext label-align addmonitor-label\">");
/*  5386 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.jmxurl.name"));
/*  5387 */                                         out.write("</TD>\n\t\t  <TD height=\"28\" colspan=\"2\">");
/*  5388 */                                         if (_jspx_meth_html_005ftext_005f24(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5390 */                                         out.write("</TD>\n        </TR>\n        ");
/*       */                                       }
/*       */                                       
/*       */ 
/*  5394 */                                       if (type.equals("SERVICE"))
/*       */                                       {
/*       */ 
/*  5397 */                                         out.write("\n        <TR>\n          <TD width=\"21%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  5398 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.command.execute"));
/*  5399 */                                         out.write("</label></TD>\n          <TD height=\"28\" colspan=\"2\">");
/*  5400 */                                         if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5402 */                                         out.write("\n            &nbsp; </TD>\n        </TR>\n        <TR>\n          <TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  5403 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.matchcontent"));
/*  5404 */                                         out.write("</label></TD>\n          <TD height=\"28\" colspan=\"2\">");
/*  5405 */                                         if (_jspx_meth_html_005ftext_005f25(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5407 */                                         out.write("\n            &nbsp; </TD>\n        </TR>\n        ");
/*       */                                       }
/*       */                                       
/*       */ 
/*       */ 
/*       */ 
/*  5413 */                                       out.write("\n        ");
/*  5414 */                                       if (type.equals("SNMP")) {
/*  5415 */                                         out.write("\n        <TR>\n          <TD width=\"21%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  5416 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.timeout"));
/*  5417 */                                         out.write("<span class=\"mandatory\">*</span></label>\n          </TD>\n          <TD height=\"28\" colspan=\"2\">");
/*  5418 */                                         if (_jspx_meth_html_005ftext_005f26(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5420 */                                         out.write("\n            &nbsp;<span class=\"footer\">");
/*  5421 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.second"));
/*  5422 */                                         out.write("</span> </TD>\n        </TR>\n       <tr>\n                    <td class=\"bodytext label-align addmonitor-label\"><label>");
/*  5423 */                                         out.print(FormatUtil.getString("Port"));
/*  5424 */                                         out.write("<span class=\"mandatory\">*</span></label>\n                    </td>\n                    <td height=\"28\" colspan=\"2\" id=\"snmpPortID\">");
/*  5425 */                                         if (_jspx_meth_html_005ftext_005f27(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5427 */                                         out.write("\n                    </td>\n                 </tr>\n        \n        ");
/*       */                                       }
/*  5429 */                                       if (type.equals("Exchange"))
/*       */                                       {
/*       */ 
/*  5432 */                                         out.write("\n\t\t\t        <tr>\n\t\t\t          <td class=\"bodytext label-align addmonitor-label\"><label>");
/*  5433 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeversion"));
/*  5434 */                                         out.write("<span class=\"mandatory\">*</span></label></td>\n\t\t\t          <td class=\"bodytext\">\n\t\t\t          ");
/*       */                                         
/*  5436 */                                         SelectTag _jspx_th_html_005fselect_005f8 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  5437 */                                         _jspx_th_html_005fselect_005f8.setPageContext(_jspx_page_context);
/*  5438 */                                         _jspx_th_html_005fselect_005f8.setParent(_jspx_th_html_005fform_005f0);
/*       */                                         
/*  5440 */                                         _jspx_th_html_005fselect_005f8.setProperty("version");
/*       */                                         
/*  5442 */                                         _jspx_th_html_005fselect_005f8.setStyleClass("formtext normal");
/*       */                                         
/*  5444 */                                         _jspx_th_html_005fselect_005f8.setOnchange("javascript:onComboChange2();");
/*  5445 */                                         int _jspx_eval_html_005fselect_005f8 = _jspx_th_html_005fselect_005f8.doStartTag();
/*  5446 */                                         if (_jspx_eval_html_005fselect_005f8 != 0) {
/*  5447 */                                           if (_jspx_eval_html_005fselect_005f8 != 1) {
/*  5448 */                                             out = _jspx_page_context.pushBody();
/*  5449 */                                             _jspx_th_html_005fselect_005f8.setBodyContent((BodyContent)out);
/*  5450 */                                             _jspx_th_html_005fselect_005f8.doInitBody();
/*       */                                           }
/*       */                                           for (;;) {
/*  5453 */                                             out.write("\n\t\t\t            ");
/*       */                                             
/*  5455 */                                             OptionTag _jspx_th_html_005foption_005f52 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  5456 */                                             _jspx_th_html_005foption_005f52.setPageContext(_jspx_page_context);
/*  5457 */                                             _jspx_th_html_005foption_005f52.setParent(_jspx_th_html_005fselect_005f8);
/*       */                                             
/*  5459 */                                             _jspx_th_html_005foption_005f52.setValue("unknown");
/*  5460 */                                             int _jspx_eval_html_005foption_005f52 = _jspx_th_html_005foption_005f52.doStartTag();
/*  5461 */                                             if (_jspx_eval_html_005foption_005f52 != 0) {
/*  5462 */                                               if (_jspx_eval_html_005foption_005f52 != 1) {
/*  5463 */                                                 out = _jspx_page_context.pushBody();
/*  5464 */                                                 _jspx_th_html_005foption_005f52.setBodyContent((BodyContent)out);
/*  5465 */                                                 _jspx_th_html_005foption_005f52.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  5468 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeversion.select"));
/*  5469 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f52.doAfterBody();
/*  5470 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  5473 */                                               if (_jspx_eval_html_005foption_005f52 != 1) {
/*  5474 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  5477 */                                             if (_jspx_th_html_005foption_005f52.doEndTag() == 5) {
/*  5478 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52); return;
/*       */                                             }
/*       */                                             
/*  5481 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52);
/*  5482 */                                             out.write("\n\t\t\t            ");
/*       */                                             
/*  5484 */                                             OptionTag _jspx_th_html_005foption_005f53 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  5485 */                                             _jspx_th_html_005foption_005f53.setPageContext(_jspx_page_context);
/*  5486 */                                             _jspx_th_html_005foption_005f53.setParent(_jspx_th_html_005fselect_005f8);
/*       */                                             
/*  5488 */                                             _jspx_th_html_005foption_005f53.setValue("2013");
/*  5489 */                                             int _jspx_eval_html_005foption_005f53 = _jspx_th_html_005foption_005f53.doStartTag();
/*  5490 */                                             if (_jspx_eval_html_005foption_005f53 != 0) {
/*  5491 */                                               if (_jspx_eval_html_005foption_005f53 != 1) {
/*  5492 */                                                 out = _jspx_page_context.pushBody();
/*  5493 */                                                 _jspx_th_html_005foption_005f53.setBodyContent((BodyContent)out);
/*  5494 */                                                 _jspx_th_html_005foption_005f53.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  5497 */                                                 out.write(32);
/*  5498 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchange2013"));
/*  5499 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f53.doAfterBody();
/*  5500 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  5503 */                                               if (_jspx_eval_html_005foption_005f53 != 1) {
/*  5504 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  5507 */                                             if (_jspx_th_html_005foption_005f53.doEndTag() == 5) {
/*  5508 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53); return;
/*       */                                             }
/*       */                                             
/*  5511 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53);
/*  5512 */                                             out.write("\n\t\t\t            ");
/*       */                                             
/*  5514 */                                             OptionTag _jspx_th_html_005foption_005f54 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  5515 */                                             _jspx_th_html_005foption_005f54.setPageContext(_jspx_page_context);
/*  5516 */                                             _jspx_th_html_005foption_005f54.setParent(_jspx_th_html_005fselect_005f8);
/*       */                                             
/*  5518 */                                             _jspx_th_html_005foption_005f54.setValue("2010");
/*  5519 */                                             int _jspx_eval_html_005foption_005f54 = _jspx_th_html_005foption_005f54.doStartTag();
/*  5520 */                                             if (_jspx_eval_html_005foption_005f54 != 0) {
/*  5521 */                                               if (_jspx_eval_html_005foption_005f54 != 1) {
/*  5522 */                                                 out = _jspx_page_context.pushBody();
/*  5523 */                                                 _jspx_th_html_005foption_005f54.setBodyContent((BodyContent)out);
/*  5524 */                                                 _jspx_th_html_005foption_005f54.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  5527 */                                                 out.write(32);
/*  5528 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchange2010"));
/*  5529 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f54.doAfterBody();
/*  5530 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  5533 */                                               if (_jspx_eval_html_005foption_005f54 != 1) {
/*  5534 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  5537 */                                             if (_jspx_th_html_005foption_005f54.doEndTag() == 5) {
/*  5538 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54); return;
/*       */                                             }
/*       */                                             
/*  5541 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54);
/*  5542 */                                             out.write("\n\t\t\t            ");
/*       */                                             
/*  5544 */                                             OptionTag _jspx_th_html_005foption_005f55 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  5545 */                                             _jspx_th_html_005foption_005f55.setPageContext(_jspx_page_context);
/*  5546 */                                             _jspx_th_html_005foption_005f55.setParent(_jspx_th_html_005fselect_005f8);
/*       */                                             
/*  5548 */                                             _jspx_th_html_005foption_005f55.setValue("2007");
/*  5549 */                                             int _jspx_eval_html_005foption_005f55 = _jspx_th_html_005foption_005f55.doStartTag();
/*  5550 */                                             if (_jspx_eval_html_005foption_005f55 != 0) {
/*  5551 */                                               if (_jspx_eval_html_005foption_005f55 != 1) {
/*  5552 */                                                 out = _jspx_page_context.pushBody();
/*  5553 */                                                 _jspx_th_html_005foption_005f55.setBodyContent((BodyContent)out);
/*  5554 */                                                 _jspx_th_html_005foption_005f55.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  5557 */                                                 out.write(32);
/*  5558 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchange2007"));
/*  5559 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f55.doAfterBody();
/*  5560 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  5563 */                                               if (_jspx_eval_html_005foption_005f55 != 1) {
/*  5564 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  5567 */                                             if (_jspx_th_html_005foption_005f55.doEndTag() == 5) {
/*  5568 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55); return;
/*       */                                             }
/*       */                                             
/*  5571 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55);
/*  5572 */                                             out.write("\n\t\t\t            ");
/*       */                                             
/*  5574 */                                             OptionTag _jspx_th_html_005foption_005f56 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  5575 */                                             _jspx_th_html_005foption_005f56.setPageContext(_jspx_page_context);
/*  5576 */                                             _jspx_th_html_005foption_005f56.setParent(_jspx_th_html_005fselect_005f8);
/*       */                                             
/*  5578 */                                             _jspx_th_html_005foption_005f56.setValue("2003");
/*  5579 */                                             int _jspx_eval_html_005foption_005f56 = _jspx_th_html_005foption_005f56.doStartTag();
/*  5580 */                                             if (_jspx_eval_html_005foption_005f56 != 0) {
/*  5581 */                                               if (_jspx_eval_html_005foption_005f56 != 1) {
/*  5582 */                                                 out = _jspx_page_context.pushBody();
/*  5583 */                                                 _jspx_th_html_005foption_005f56.setBodyContent((BodyContent)out);
/*  5584 */                                                 _jspx_th_html_005foption_005f56.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  5587 */                                                 out.write(32);
/*  5588 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchange2003"));
/*  5589 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f56.doAfterBody();
/*  5590 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  5593 */                                               if (_jspx_eval_html_005foption_005f56 != 1) {
/*  5594 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  5597 */                                             if (_jspx_th_html_005foption_005f56.doEndTag() == 5) {
/*  5598 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56); return;
/*       */                                             }
/*       */                                             
/*  5601 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56);
/*  5602 */                                             out.write("\n\t\t\t            ");
/*       */                                             
/*  5604 */                                             OptionTag _jspx_th_html_005foption_005f57 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  5605 */                                             _jspx_th_html_005foption_005f57.setPageContext(_jspx_page_context);
/*  5606 */                                             _jspx_th_html_005foption_005f57.setParent(_jspx_th_html_005fselect_005f8);
/*       */                                             
/*  5608 */                                             _jspx_th_html_005foption_005f57.setValue("2000");
/*  5609 */                                             int _jspx_eval_html_005foption_005f57 = _jspx_th_html_005foption_005f57.doStartTag();
/*  5610 */                                             if (_jspx_eval_html_005foption_005f57 != 0) {
/*  5611 */                                               if (_jspx_eval_html_005foption_005f57 != 1) {
/*  5612 */                                                 out = _jspx_page_context.pushBody();
/*  5613 */                                                 _jspx_th_html_005foption_005f57.setBodyContent((BodyContent)out);
/*  5614 */                                                 _jspx_th_html_005foption_005f57.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  5617 */                                                 out.write(32);
/*  5618 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchange2000"));
/*  5619 */                                                 out.write(32);
/*  5620 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f57.doAfterBody();
/*  5621 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  5624 */                                               if (_jspx_eval_html_005foption_005f57 != 1) {
/*  5625 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  5628 */                                             if (_jspx_th_html_005foption_005f57.doEndTag() == 5) {
/*  5629 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57); return;
/*       */                                             }
/*       */                                             
/*  5632 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57);
/*  5633 */                                             out.write("\n\t\t\t            ");
/*       */                                             
/*  5635 */                                             OptionTag _jspx_th_html_005foption_005f58 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  5636 */                                             _jspx_th_html_005foption_005f58.setPageContext(_jspx_page_context);
/*  5637 */                                             _jspx_th_html_005foption_005f58.setParent(_jspx_th_html_005fselect_005f8);
/*       */                                             
/*  5639 */                                             _jspx_th_html_005foption_005f58.setValue("5");
/*  5640 */                                             int _jspx_eval_html_005foption_005f58 = _jspx_th_html_005foption_005f58.doStartTag();
/*  5641 */                                             if (_jspx_eval_html_005foption_005f58 != 0) {
/*  5642 */                                               if (_jspx_eval_html_005foption_005f58 != 1) {
/*  5643 */                                                 out = _jspx_page_context.pushBody();
/*  5644 */                                                 _jspx_th_html_005foption_005f58.setBodyContent((BodyContent)out);
/*  5645 */                                                 _jspx_th_html_005foption_005f58.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  5648 */                                                 out.write(32);
/*  5649 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchange5.5"));
/*  5650 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f58.doAfterBody();
/*  5651 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  5654 */                                               if (_jspx_eval_html_005foption_005f58 != 1) {
/*  5655 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  5658 */                                             if (_jspx_th_html_005foption_005f58.doEndTag() == 5) {
/*  5659 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58); return;
/*       */                                             }
/*       */                                             
/*  5662 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58);
/*  5663 */                                             out.write("\n\t\t\t          ");
/*  5664 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f8.doAfterBody();
/*  5665 */                                             if (evalDoAfterBody != 2)
/*       */                                               break;
/*       */                                           }
/*  5668 */                                           if (_jspx_eval_html_005fselect_005f8 != 1) {
/*  5669 */                                             out = _jspx_page_context.popBody();
/*       */                                           }
/*       */                                         }
/*  5672 */                                         if (_jspx_th_html_005fselect_005f8.doEndTag() == 5) {
/*  5673 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f8); return;
/*       */                                         }
/*       */                                         
/*  5676 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f8);
/*  5677 */                                         out.write("\n\t\t\t          </td>\n\t\t\t        </tr>\n\t\t\t       <tr>\n\t\t\t       <td colspan=\"3\">\n\t\t\t        <div id=\"BelowES2007\"  style=\"display:none\">\n\t\t\t\t\t\t<table width=\"100%\" cellpadding=\"6\">\n\t\t\t\t\t\t    <tr>\n\t\t\t\t\t\t        <td class=\"bodytext label-align addmonitor-label align-top\" width=\"25%\"><label>");
/*  5678 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.select"));
/*  5679 */                                         out.write("</label></td>\n\t\t\t\t\t\t        <td class=\"bodytext\" colspan=\"2\" align=\"left\">\n\t\t\t\t\t\t\t\t\t<table border=\"0\" class=\"bodytext\" cellspacing=\"5\">\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td> ");
/*  5680 */                                         if (_jspx_meth_html_005fmultibox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5682 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.msis"));
/*  5683 */                                         out.write(" </td>\n\t\t\t\t\t\t\t\t\t\t<td> ");
/*  5684 */                                         if (_jspx_meth_html_005fmultibox_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5686 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.msrs"));
/*  5687 */                                         out.write(" </td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td> ");
/*  5688 */                                         if (_jspx_meth_html_005fmultibox_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5690 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.msmta"));
/*  5691 */                                         out.write(" </td>\n\t\t\t\t\t\t\t\t\t\t<td> ");
/*  5692 */                                         if (_jspx_meth_html_005fmultibox_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5694 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.msmgmt"));
/*  5695 */                                         out.write(" </td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td> ");
/*  5696 */                                         if (_jspx_meth_html_005fmultibox_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5698 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.smtp"));
/*  5699 */                                         out.write(" </td>\n\t\t\t\t\t\t\t\t\t\t<td> ");
/*  5700 */                                         if (_jspx_meth_html_005fmultibox_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5702 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.imap4"));
/*  5703 */                                         out.write(" </td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td> ");
/*  5704 */                                         if (_jspx_meth_html_005fmultibox_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5706 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.mssa"));
/*  5707 */                                         out.write(" </td>\n\t\t\t\t\t\t\t\t\t\t<td> ");
/*  5708 */                                         if (_jspx_meth_html_005fmultibox_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5710 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.pop3"));
/*  5711 */                                         out.write(" </td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td> ");
/*  5712 */                                         if (_jspx_meth_html_005fmultibox_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5714 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.msra"));
/*  5715 */                                         out.write(" </td>\n\t\t\t\t\t\t\t\t\t\t<td> ");
/*  5716 */                                         if (_jspx_meth_html_005fmultibox_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5718 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.mses"));
/*  5719 */                                         out.write(" </td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t        </td>\n\t\t\t\t\t\t    </tr>\n\t\t\t\t\t\t</table>\n\t\t\t      </div>\t\t\t      \n\t\t\t      <div id=\"ExchngRoles\" style=\"display:block\">\n\t\t\t      \t<table width=\"100%\" cellpadding=\"6\">\n\t\t\t      \t\t<tr> \n\t\t\t\t        \t<td class=\"bodytext label-align addmonitor-label\" width=\"25%\"><label>");
/*  5720 */                                         out.print(FormatUtil.getString("Exchange server role:"));
/*  5721 */                                         out.write("</label></td>\n\t\t\t\t        \t<td class=\"bodytext\">\n\t\t\t\t\t\t\t\t");
/*       */                                         
/*  5723 */                                         SelectTag _jspx_th_html_005fselect_005f9 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  5724 */                                         _jspx_th_html_005fselect_005f9.setPageContext(_jspx_page_context);
/*  5725 */                                         _jspx_th_html_005fselect_005f9.setParent(_jspx_th_html_005fform_005f0);
/*       */                                         
/*  5727 */                                         _jspx_th_html_005fselect_005f9.setProperty("role");
/*       */                                         
/*  5729 */                                         _jspx_th_html_005fselect_005f9.setStyleClass("formtext normal");
/*       */                                         
/*  5731 */                                         _jspx_th_html_005fselect_005f9.setOnchange("javascript:onComboChange2();");
/*  5732 */                                         int _jspx_eval_html_005fselect_005f9 = _jspx_th_html_005fselect_005f9.doStartTag();
/*  5733 */                                         if (_jspx_eval_html_005fselect_005f9 != 0) {
/*  5734 */                                           if (_jspx_eval_html_005fselect_005f9 != 1) {
/*  5735 */                                             out = _jspx_page_context.pushBody();
/*  5736 */                                             _jspx_th_html_005fselect_005f9.setBodyContent((BodyContent)out);
/*  5737 */                                             _jspx_th_html_005fselect_005f9.doInitBody();
/*       */                                           }
/*       */                                           for (;;) {
/*  5740 */                                             out.write("\n\t\t\t\t\t\t\t\t\t");
/*       */                                             
/*  5742 */                                             OptionTag _jspx_th_html_005foption_005f59 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  5743 */                                             _jspx_th_html_005foption_005f59.setPageContext(_jspx_page_context);
/*  5744 */                                             _jspx_th_html_005foption_005f59.setParent(_jspx_th_html_005fselect_005f9);
/*       */                                             
/*  5746 */                                             _jspx_th_html_005foption_005f59.setValue("none");
/*  5747 */                                             int _jspx_eval_html_005foption_005f59 = _jspx_th_html_005foption_005f59.doStartTag();
/*  5748 */                                             if (_jspx_eval_html_005foption_005f59 != 0) {
/*  5749 */                                               if (_jspx_eval_html_005foption_005f59 != 1) {
/*  5750 */                                                 out = _jspx_page_context.pushBody();
/*  5751 */                                                 _jspx_th_html_005foption_005f59.setBodyContent((BodyContent)out);
/*  5752 */                                                 _jspx_th_html_005foption_005f59.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  5755 */                                                 out.print(FormatUtil.getString("None"));
/*  5756 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f59.doAfterBody();
/*  5757 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  5760 */                                               if (_jspx_eval_html_005foption_005f59 != 1) {
/*  5761 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  5764 */                                             if (_jspx_th_html_005foption_005f59.doEndTag() == 5) {
/*  5765 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59); return;
/*       */                                             }
/*       */                                             
/*  5768 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59);
/*  5769 */                                             out.write("\n\t\t\t\t\t\t\t\t\t");
/*       */                                             
/*  5771 */                                             OptionTag _jspx_th_html_005foption_005f60 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  5772 */                                             _jspx_th_html_005foption_005f60.setPageContext(_jspx_page_context);
/*  5773 */                                             _jspx_th_html_005foption_005f60.setParent(_jspx_th_html_005fselect_005f9);
/*       */                                             
/*  5775 */                                             _jspx_th_html_005foption_005f60.setValue("Mailbox");
/*  5776 */                                             int _jspx_eval_html_005foption_005f60 = _jspx_th_html_005foption_005f60.doStartTag();
/*  5777 */                                             if (_jspx_eval_html_005foption_005f60 != 0) {
/*  5778 */                                               if (_jspx_eval_html_005foption_005f60 != 1) {
/*  5779 */                                                 out = _jspx_page_context.pushBody();
/*  5780 */                                                 _jspx_th_html_005foption_005f60.setBodyContent((BodyContent)out);
/*  5781 */                                                 _jspx_th_html_005foption_005f60.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  5784 */                                                 out.write(32);
/*  5785 */                                                 out.print(FormatUtil.getString("Mailbox"));
/*  5786 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f60.doAfterBody();
/*  5787 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  5790 */                                               if (_jspx_eval_html_005foption_005f60 != 1) {
/*  5791 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  5794 */                                             if (_jspx_th_html_005foption_005f60.doEndTag() == 5) {
/*  5795 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60); return;
/*       */                                             }
/*       */                                             
/*  5798 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60);
/*  5799 */                                             out.write("\n\t\t\t\t\t\t\t\t\t");
/*       */                                             
/*  5801 */                                             OptionTag _jspx_th_html_005foption_005f61 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  5802 */                                             _jspx_th_html_005foption_005f61.setPageContext(_jspx_page_context);
/*  5803 */                                             _jspx_th_html_005foption_005f61.setParent(_jspx_th_html_005fselect_005f9);
/*       */                                             
/*  5805 */                                             _jspx_th_html_005foption_005f61.setValue("Transport");
/*  5806 */                                             int _jspx_eval_html_005foption_005f61 = _jspx_th_html_005foption_005f61.doStartTag();
/*  5807 */                                             if (_jspx_eval_html_005foption_005f61 != 0) {
/*  5808 */                                               if (_jspx_eval_html_005foption_005f61 != 1) {
/*  5809 */                                                 out = _jspx_page_context.pushBody();
/*  5810 */                                                 _jspx_th_html_005foption_005f61.setBodyContent((BodyContent)out);
/*  5811 */                                                 _jspx_th_html_005foption_005f61.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  5814 */                                                 out.write(32);
/*  5815 */                                                 out.print(FormatUtil.getString("Edge/Hub Transport"));
/*  5816 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f61.doAfterBody();
/*  5817 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  5820 */                                               if (_jspx_eval_html_005foption_005f61 != 1) {
/*  5821 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  5824 */                                             if (_jspx_th_html_005foption_005f61.doEndTag() == 5) {
/*  5825 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61); return;
/*       */                                             }
/*       */                                             
/*  5828 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61);
/*  5829 */                                             out.write("\n\t\t\t\t\t\t\t\t\t");
/*       */                                             
/*  5831 */                                             OptionTag _jspx_th_html_005foption_005f62 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  5832 */                                             _jspx_th_html_005foption_005f62.setPageContext(_jspx_page_context);
/*  5833 */                                             _jspx_th_html_005foption_005f62.setParent(_jspx_th_html_005fselect_005f9);
/*       */                                             
/*  5835 */                                             _jspx_th_html_005foption_005f62.setValue("Access");
/*  5836 */                                             int _jspx_eval_html_005foption_005f62 = _jspx_th_html_005foption_005f62.doStartTag();
/*  5837 */                                             if (_jspx_eval_html_005foption_005f62 != 0) {
/*  5838 */                                               if (_jspx_eval_html_005foption_005f62 != 1) {
/*  5839 */                                                 out = _jspx_page_context.pushBody();
/*  5840 */                                                 _jspx_th_html_005foption_005f62.setBodyContent((BodyContent)out);
/*  5841 */                                                 _jspx_th_html_005foption_005f62.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  5844 */                                                 out.write(32);
/*  5845 */                                                 out.print(FormatUtil.getString("Client Access Server"));
/*  5846 */                                                 out.write(32);
/*  5847 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f62.doAfterBody();
/*  5848 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  5851 */                                               if (_jspx_eval_html_005foption_005f62 != 1) {
/*  5852 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  5855 */                                             if (_jspx_th_html_005foption_005f62.doEndTag() == 5) {
/*  5856 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f62); return;
/*       */                                             }
/*       */                                             
/*  5859 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f62);
/*  5860 */                                             out.write("\n\t\t\t\t\t\t\t\t");
/*  5861 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f9.doAfterBody();
/*  5862 */                                             if (evalDoAfterBody != 2)
/*       */                                               break;
/*       */                                           }
/*  5865 */                                           if (_jspx_eval_html_005fselect_005f9 != 1) {
/*  5866 */                                             out = _jspx_page_context.popBody();
/*       */                                           }
/*       */                                         }
/*  5869 */                                         if (_jspx_th_html_005fselect_005f9.doEndTag() == 5) {
/*  5870 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f9); return;
/*       */                                         }
/*       */                                         
/*  5873 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f9);
/*  5874 */                                         out.write("\n\t\t\t\t        \t</td>\n\t\t\t\t        </tr>\n\t\t\t\t    </table>\n\t\t\t      </div>\n\t\t\t      <div id=\"ES2007andAbove\"  style=\"display:none\">\n\t\t\t        <table width=\"100%\" cellpadding=\"6\">\n\t\t\t\t      \t<tr>\n\t\t\t\t\t\t\t<td class=\"bodytext label-align addmonitor-label align-top\" width=\"25%\"><label>");
/*  5875 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.select"));
/*  5876 */                                         out.write("</label></td>\n\t\t\t\t\t\t\t<td class=\"bodytext\" colspan=\"2\">\n\t\t\t\t\t\t\t\t<table width=\"100%\" cellspacing=\"5\">\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5877 */                                         if (_jspx_meth_html_005fmultibox_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5879 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.esadt"));
/*  5880 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5881 */                                         if (_jspx_meth_html_005fmultibox_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5883 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.esasu"));
/*  5884 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5885 */                                         if (_jspx_meth_html_005fmultibox_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5887 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.eses"));
/*  5888 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5889 */                                         if (_jspx_meth_html_005fmultibox_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5891 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.esfds"));
/*  5892 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5893 */                                         if (_jspx_meth_html_005fmultibox_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5895 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.esma"));
/*  5896 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5897 */                                         if (_jspx_meth_html_005fmultibox_005f15(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5899 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.pop3"));
/*  5900 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5901 */                                         if (_jspx_meth_html_005fmultibox_005f16(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5903 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.msis"));
/*  5904 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5905 */                                         if (_jspx_meth_html_005fmultibox_005f17(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5907 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.imap4"));
/*  5908 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5909 */                                         if (_jspx_meth_html_005fmultibox_005f18(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5911 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.esms"));
/*  5912 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5913 */                                         if (_jspx_meth_html_005fmultibox_005f19(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5915 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.esmon"));
/*  5916 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5917 */                                         if (_jspx_meth_html_005fmultibox_005f20(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5919 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.esrepl"));
/*  5920 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5921 */                                         if (_jspx_meth_html_005fmultibox_005f21(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5923 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.mssa"));
/*  5924 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5925 */                                         if (_jspx_meth_html_005fmultibox_005f22(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5927 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.essr"));
/*  5928 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5929 */                                         if (_jspx_meth_html_005fmultibox_005f23(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5931 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.essh"));
/*  5932 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5933 */                                         if (_jspx_meth_html_005fmultibox_005f24(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5935 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.estrans"));
/*  5936 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5937 */                                         if (_jspx_meth_html_005fmultibox_005f25(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5939 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.estls"));
/*  5940 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5941 */                                         if (_jspx_meth_html_005fmultibox_005f26(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5943 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.esadam"));
/*  5944 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5945 */                                         if (_jspx_meth_html_005fmultibox_005f27(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5947 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.escs"));
/*  5948 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5949 */                                         if (_jspx_meth_html_005fmultibox_005f28(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5951 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.esse"));
/*  5952 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\">");
/*  5953 */                                         if (_jspx_meth_html_005fmultibox_005f29(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5955 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.esum"));
/*  5956 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\" align=\"left\">");
/*  5957 */                                         if (_jspx_meth_html_005fmultibox_005f30(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5959 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.ab"));
/*  5960 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\" align=\"left\" nowrap>");
/*  5961 */                                         if (_jspx_meth_html_005fmultibox_005f31(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5963 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.fbas"));
/*  5964 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\" align=\"left\">");
/*  5965 */                                         if (_jspx_meth_html_005fmultibox_005f32(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5967 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.mr"));
/*  5968 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\" align=\"left\">");
/*  5969 */                                         if (_jspx_meth_html_005fmultibox_005f33(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5971 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.psh"));
/*  5972 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\" align=\"left\">");
/*  5973 */                                         if (_jspx_meth_html_005fmultibox_005f34(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5975 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.rca"));
/*  5976 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\" align=\"left\">");
/*  5977 */                                         if (_jspx_meth_html_005fmultibox_005f35(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5979 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.throttling"));
/*  5980 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\" align=\"left\" nowrap>");
/*  5981 */                                         if (_jspx_meth_html_005fmultibox_005f36(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5983 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.tls"));
/*  5984 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"bodytext\" align=\"left\">");
/*  5985 */                                         if (_jspx_meth_html_005fmultibox_005f37(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  5987 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.exchangeservice.ess"));
/*  5988 */                                         out.write("</td>\n\t\t\t\t       \t   \t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t     \t</td>\n\t\t\t\t        </tr>\n\t\t\t       \t</table>\n\t\t\t      </div>\n\t\t\t      </td>\n\t\t\t      </tr>\n\t\t\t        ");
/*       */                                       }
/*       */                                       
/*       */ 
/*       */ 
/*  5993 */                                       out.write("\n\n\n\n      </table>\n      <TABLE width=\"100%\" border=\"0\" cellpadding=\"6\" CELLSPACING=\"0\" class=\"lrborder\" >\n        ");
/*       */                                       
/*  5995 */                                       if ((type.equals("ORACLEDB")) || (type.equals("MYSQLDB")) || (type.equals("MSSQLDB")) || (type.equals("WEBLOGIC")) || (type.equals("WLI")) || (type.equals("DB2")) || (type.equals("SYBASEDB")) || (type.equals("JBoss")) || (type.equals("Exchange")) || (type.equals(".Net")) || (type.equals("JMX1.2-MX4J-RMI")) || (type.equals("JDK1.5")))
/*       */                                       {
/*  5997 */                                         out.write("\n\n\n        ");
/*       */                                       }
/*       */                                       
/*       */ 
/*       */ 
/*  6002 */                                       out.write("\n        ");
/*       */                                       
/*  6004 */                                       if (type.equals("Tomcat"))
/*       */                                       {
/*       */ 
/*  6007 */                                         out.write("\n\n\t\t <tr id=\"username\" style=\"display:none\">\n\t\t   <td width=\"100%\" colspan=\"2\">\n\t\t       <table width=\"100%\">\n\t\t         <tr>\n\t\t           <TD  width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6008 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/*  6009 */                                         out.write("<span class=\"mandatory\">*</span></label></TD>\n\t\t           <TD  width=\"75%\" >");
/*  6010 */                                         if (_jspx_meth_html_005ftext_005f28(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6012 */                                         out.write("&nbsp; <span class=\"footer\">");
/*  6013 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.tomcatusermessage"));
/*  6014 */                                         out.write("</span>&nbsp;<a target=\"_blank\" href=\"/help/managing-business-applications/application-server-monitor.html#tomcat-server\" class=\"footer\">");
/*  6015 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.moreinfo"));
/*  6016 */                                         out.write("</a> </TD>\n\t\t          </TR>\n\t\t        </table>\n\t\t    </td>\n\t\t  </tr>\n          <tr id=\"password\" style=\"display:none\">\n\t\t    <td width=\"100%\" colspan=\"2\">\n\t\t\t    <table width=\"100%\">\n\t\t\t     <tr>\n\n          <TD  width=\"25%\" height=\"21\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6017 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/*  6018 */                                         out.write("<span class=\"mandatory\">*</span></label></TD>\n\t\t\t       <TD  width=\"75%\" >");
/*  6019 */                                         if (_jspx_meth_html_005fpassword_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6021 */                                         out.write("\n\t\t\t           &nbsp; <span class=\"bodytext\"></span> </TD>\n\t\t\t     </TR>\n\t\t\t    </table>\n\t\t\t </td>\n\t      </tr>\n\t<TR id=\"tomcatstatus\" style=\"display:none\">\n\t  <TD class=\"bodytext label-align addmonitor-label\" height=\"28\" width=\"25%\"><label>");
/*  6022 */                                         out.print(FormatUtil.getString("am.webclient.tomcat.appname"));
/*  6023 */                                         out.write("</label></TD>\n\t  <TD> ");
/*  6024 */                                         if (_jspx_meth_html_005ftext_005f29(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6026 */                                         out.write("&nbsp;&nbsp;<a target=\"_blank\" href=\"/help/managing-business-applications/application-server-monitor.html#tomcat-manager\" class=\"footer\">");
/*  6027 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.moreinfo"));
/*  6028 */                                         out.write("</a></TD>\n\t</TR>\n        ");
/*       */                                       }
/*       */                                       
/*       */ 
/*       */ 
/*       */ 
/*  6034 */                                       out.write("\n        ");
/*       */                                       
/*       */ 
/*  6037 */                                       if ((type.equals("SYSTEM")) || (type.equals("ORACLEDB")) || (type.equals("MYSQLDB")) || (type.equals("MSSQLDB")) || (type.equals("WEBLOGIC")) || (type.equals("WLI")) || (type.equals("DB2")) || (type.equals("SYBASEDB")) || (type.equals("JBoss")) || (type.equals("Exchange")) || (type.equals(".Net")) || (type.equals("JMX1.2-MX4J-RMI")) || (type.equals("JDK1.5")) || (type.equals("SAP")))
/*       */                                       {
/*       */ 
/*  6040 */                                         String msg = "";
/*       */                                         
/*       */ 
/*       */ 
/*       */ 
/*  6045 */                                         if ((type.equals("MSSQLDB")) || (type.equals("SYBASEDB")) || (type.equals("DB2")))
/*       */                                         {
/*  6047 */                                           msg = FormatUtil.getString("am.webclient.hostdiscovery.db2.message");
/*       */ 
/*       */                                         }
/*  6050 */                                         else if (type.equals("Exchange"))
/*       */                                         {
/*  6052 */                                           msg = FormatUtil.getString("am.webclient.hostdiscovery.usrname.message");
/*       */                                         }
/*       */                                         
/*       */ 
/*  6056 */                                         if ((type.equals("WEBLOGIC")) || (type.equals("JBoss")) || (type.equals("JMX1.2-MX4J-RMI")) || (type.equals("JDK1.5")) || (type.equals("SAP")))
/*       */                                         {
/*  6058 */                                           if ((type.equals("JBoss")) || (type.equals("JMX1.2-MX4J-RMI")) || (type.equals("JDK1.5")))
/*       */                                           {
/*       */ 
/*  6061 */                                             out.write("\n\t\t <tr >\n\t\t <td height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\">&nbsp;</td>\n\t\t <td height=\"28\" width=\"75%\" >");
/*  6062 */                                             if (_jspx_meth_html_005fcheckbox_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                               return;
/*  6064 */                                             out.write("\n         ");
/*  6065 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.jbossauthentication"));
/*  6066 */                                             out.write("</td>\n\t\t </tr>\n\t");
/*       */                                           }
/*       */                                           
/*       */ 
/*  6070 */                                           out.write("\n        <TR id=\"username\" ");
/*  6071 */                                           if (((type.equals("WEBLOGIC")) || (type.equals("WLI"))) || (
/*       */                                           
/*  6073 */                                             (!type.equals("WEBLOGIC")) && (!type.equals("WLI")) && (!type.equals("SAP")) && 
/*  6074 */                                             (_jspx_meth_c_005fif_005f20(_jspx_th_html_005fform_005f0, _jspx_page_context)))) {
/*       */                                             return;
/*       */                                           }
/*  6077 */                                           out.write(">\n          <TD height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6078 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/*  6079 */                                           out.write("<span class=\"mandatory\">*</span></label></TD>\n          <TD height=\"28\" width=\"75%\" ><input type=\"text\" name=\"username\" class=\"formtext default\" autocomplete=\"off\" /><!--");
/*  6080 */                                           if (_jspx_meth_html_005ftext_005f30(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                             return;
/*  6082 */                                           out.write("-->\n             </TD>\n        </TR>\n<TR id=\"password\" ");
/*  6083 */                                           if (((type.equals("WEBLOGIC")) || (type.equals("WLI"))) || (
/*       */                                           
/*  6085 */                                             (!type.equals("WEBLOGIC")) && (!type.equals("WLI")) && (!type.equals("SAP")) && 
/*  6086 */                                             (_jspx_meth_c_005fif_005f21(_jspx_th_html_005fform_005f0, _jspx_page_context)))) {
/*       */                                             return;
/*       */                                           }
/*  6089 */                                           out.write(">\n<TD width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6090 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/*  6091 */                                           out.write("<span class=\"mandatory\">*</span></label></TD>\n<TD width=\"75%\" height=\"28\" ><input type=\"password\" name=\"password\" class=\"formtext default\" autocomplete=\"off\" /><!--");
/*  6092 */                                           if (_jspx_meth_html_005fpassword_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                             return;
/*  6094 */                                           out.write("-->\n&nbsp;<span class=\"footer\">");
/*  6095 */                                           out.print(msg);
/*  6096 */                                           out.write("</span> </TD>\n</TR>\n");
/*       */ 
/*       */                                         }
/*  6099 */                                         else if ((type.equals("Exchange")) || (type.equals(".Net"))) {
/*  6100 */                                           out.write("\n<TD height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6101 */                                           if (type.equals(".Net")) {
/*  6102 */                                             out.write("<a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/*  6103 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.message.net"));
/*  6104 */                                             out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/*  6105 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/*  6106 */                                             out.write("</a> ");
/*       */                                           } else {
/*  6108 */                                             out.write(10);
/*  6109 */                                             out.write(10);
/*  6110 */                                             out.write(32);
/*  6111 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/*  6112 */                                             out.write("</a> ");
/*       */                                           }
/*  6114 */                                           out.write("\n<span class=\"mandatory\">*</span></label></TD>\n<TD height=\"28\" width=\"75%\"><input type=\"text\" name=\"username\" class=\"formtext default\" autocomplete=\"off\" /><!--");
/*  6115 */                                           if (_jspx_meth_html_005ftext_005f31(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                             return;
/*  6117 */                                           out.write("-->&nbsp;<span class=\"footer\"></span>\n</TD>\n</TR>\n<TR>\n<TD width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6118 */                                           if (type.equals("Exchange")) {
/*  6119 */                                             out.write("<a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/*  6120 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.usrname.message"));
/*  6121 */                                             out.write("',false,true,'#000000',500,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/*  6122 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/*  6123 */                                             out.write("</a>");
/*       */                                           } else {
/*  6125 */                                             out.write(10);
/*  6126 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/*  6127 */                                             out.write(32);
/*       */                                           }
/*  6129 */                                           out.write("\n<span class=\"mandatory\">*</span></label></TD>\n<TD width=\"75%\" height=\"28\"><input type=\"password\" name=\"password\" class=\"formtext default\" autocomplete=\"off\" /> <!--");
/*  6130 */                                           if (_jspx_meth_html_005fpassword_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                             return;
/*  6132 */                                           out.write("-->\n&nbsp;</TD>\n</TR>\n");
/*       */ 
/*       */ 
/*       */                                         }
/*  6136 */                                         else if (type.equals("SYSTEM")) {
/*  6137 */                                           out.write("\n\n\n <tr id=\"sshKeyAuth\" style=\"display:none\">\n  <td width=\"100%\" colspan=\"2\" class=\"cellpadd-none\">\n   <table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n    <tr>\n     <TD  width=\"25%\" class=\"bodytext label-align addmonitor-label\"></TD>\n     <TD  width=\"75%\" class=\"footer\"><input name=\"sshPKAuth\" id=\"sshPKAuth\" onclick=\"showPrivateKey()\" type=\"checkbox\">\n     ");
/*  6138 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh.privateKeyMessage"));
/*  6139 */                                           out.write("</TD>\n    </tr>\n   </table>\n  </td>\n </tr>\n<tr id=\"username\" style=\"display:none\">\n<td width=\"100%\" colspan=\"2\" class=\"cellpadd-none\">\n<table width=\"100%\" cellpadding=\"6\" cellspacing=\"0\">\n  <tr>\n   <TD height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/*  6140 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.message.server"));
/*  6141 */                                           out.write("',false,true,'#000000',350,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/*  6142 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/*  6143 */                                           out.write("</a><span class=\"mandatory\">*</span></label></TD>\n   <TD height=\"28\" width=\"75%\">");
/*  6144 */                                           if (_jspx_meth_html_005ftext_005f32(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                             return;
/*  6146 */                                           out.write(" </TD>\n   </TR>\n </table>\n</td>\n</tr>\n<tr id=\"password\" style=\"display:none\">\n<td width=\"100%\" colspan=\"2\" class=\"cellpadd-none\">\n<table width=\"100%\" cellpadding=\"6\" cellspacing=\"0\">\n<tr>\n<TD height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6147 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/*  6148 */                                           out.write("<span class=\"mandatory\">*</span></label></TD>\n<TD height=\"28\" width=\"75%\">");
/*  6149 */                                           if (_jspx_meth_html_005fpassword_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                             return;
/*  6151 */                                           out.write("\n&nbsp; <span class=\"bodytext\"></span> </TD>\n</TR>\n</table>\n</td>\n</tr>\n<tr id=\"privateKey\" style=\"display:none\">\n <td width=\"100%\" colspan=\"2\" class=\"cellpadd-none\">\n  <table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n  <tr>\n   <TD  width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6152 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh.privateKey"));
/*  6153 */                                           out.write("<span class=\"mandatory\">*</span></label>\n   </TD>\n   <TD width=\"75%\">");
/*  6154 */                                           if (_jspx_meth_html_005ftextarea_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                             return;
/*  6156 */                                           out.write(" </TD>\n  </TR>\n  </table>\n </td>\n</tr>\n<tr id=\"passphrase\" style=\"display:none\">\n<td width=\"100%\" colspan=\"2\" class=\"cellpadd-none\">\n<table width=\"100%\" cellpadding=\"6\" cellspacing=\"0\">\n<tr>\n<TD height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\">");
/*  6157 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.passphrase"));
/*  6158 */                                           out.write("</TD>\n<TD height=\"28\" width=\"75%\">");
/*  6159 */                                           if (_jspx_meth_html_005fpassword_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                             return;
/*  6161 */                                           out.write("\n&nbsp; <span class=\"bodytext\"></span> </TD>\n</TR>\n</table>\n</td>\n</tr>\n<TR>\n<td width=\"100%\" colspan=\"2\" class=\"cellpadd-none\">\n <div id=\"prompt\" style=\"display:none\">\n  <table width=\"100%\" cellpadding=\"6\" cellspacing=\"0\">\n   <tr>\n   <script>\n    \tvar host_dis_msg='");
/*  6162 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.message.commandprompt"));
/*  6163 */                                           out.write("';\n   </script>\n    <TD height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,host_dis_msg,false,true,'#000000',500,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/*  6164 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.commandprompt"));
/*  6165 */                                           out.write("</a></label></TD>\n    <TD height=\"28\" width=\"75%\">");
/*  6166 */                                           if (_jspx_meth_html_005ftext_005f33(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                             return;
/*  6168 */                                           out.write("</TD>\n   </TR>\n  </table>\n </div>\n</td>\n</tr>\n\n\n\n");
/*       */                                         }
/*       */                                         else {
/*  6171 */                                           out.write(10);
/*  6172 */                                           if (type.equals("MSSQLDB")) {
/*  6173 */                                             out.write("\n\n <TR>\n\n\t <TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/*  6174 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.message.winowsAuth"));
/*  6175 */                                             out.write("',false,true,'#000000',500,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/*  6176 */                                             out.print(FormatUtil.getString("am.webclient.newmonitor.mssql.authentication.text"));
/*  6177 */                                             out.write("</a><span class=\"mandatory\">*</span></label></TD>\n\t <TD height=\"28\" colspan=\"2\" ><input type=\"radio\" name=\"authType\" value=\"SQL\" checked ><span class=\"bodytext\">");
/*  6178 */                                             out.print(FormatUtil.getString("SQL"));
/*  6179 */                                             out.write("&nbsp;&nbsp;&nbsp;<input type=\"radio\" name=\"authType\" value=\"Windows\" >");
/*  6180 */                                             out.print(FormatUtil.getString("Windows"));
/*  6181 */                                             out.write("</span>\n\t </TD>\n </tr>\n");
/*       */                                           }
/*  6183 */                                           out.write("\n<TR>\n<TD height=\"28\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6184 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/*  6185 */                                           out.write("<span class=\"mandatory\">*</span></label></TD>\n<TD height=\"28\" width=\"75%\" >");
/*  6186 */                                           if (_jspx_meth_html_005ftext_005f34(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                             return;
/*  6188 */                                           out.write(" </TD>\n</TR>\n<TR>\n<TD width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\" ><label>\n");
/*       */                                           
/*  6190 */                                           if ((type.equals("MYSQLDB")) || (type.equals("MSSQLDB")) || (type.equals("SYBASEDB")))
/*       */                                           {
/*  6192 */                                             out.write("\n<a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/*  6193 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.message.passwordleave"));
/*  6194 */                                             out.write("',false,true,'#000000',400,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/*  6195 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/*  6196 */                                             out.write("</a><span class=\"mandatory\">*</span>\n\n            ");
/*       */ 
/*       */                                           }
/*  6199 */                                           else if ((type.equals("DB2")) || (type.equals("SYBASEDB")))
/*       */                                           {
/*  6201 */                                             out.write(10);
/*  6202 */                                             out.write(9);
/*  6203 */                                             out.write(9);
/*  6204 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/*  6205 */                                             out.write("<span class=\"mandatory\">*</span>\n");
/*       */                                           }
/*       */                                           else {
/*  6208 */                                             out.write("\n         ");
/*  6209 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/*  6210 */                                             out.write("<span class=\"mandatory\">*</span>\n            &nbsp;<span class=\"bodytext\">");
/*  6211 */                                             out.print(msg);
/*  6212 */                                             out.write("</span>\n            ");
/*       */                                           }
/*  6214 */                                           out.write("\n</label>\n</TD>\n");
/*       */                                           
/*  6216 */                                           if ((type.equals("MYSQLDB")) || (type.equals("MSSQLDB")) || (type.equals("SYBASEDB")))
/*       */                                           {
/*  6218 */                                             out.write("\n<TD width=\"75%\" height=\"28\" > ");
/*  6219 */                                             if (_jspx_meth_html_005fpassword_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                               return;
/*  6221 */                                             out.write("\n\n            ");
/*       */ 
/*       */                                           }
/*  6224 */                                           else if ((type.equals("DB2")) || (type.equals("SYBASEDB")))
/*       */                                           {
/*  6226 */                                             out.write("\n\t\t<TD width=\"75%\" height=\"28\" > ");
/*  6227 */                                             if (_jspx_meth_html_005fpassword_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                               return;
/*  6229 */                                             out.write(10);
/*       */                                           }
/*       */                                           else {
/*  6232 */                                             out.write("\n          <TD width=\"75%\" height=\"28\" > ");
/*  6233 */                                             if (_jspx_meth_html_005fpassword_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                               return;
/*  6235 */                                             out.write("\n            &nbsp;<span class=\"bodytext\">");
/*  6236 */                                             out.print(msg);
/*  6237 */                                             out.write("</span>\n            ");
/*       */                                           }
/*  6239 */                                           out.write("\n          </TD>\n        </TR>\n        ");
/*       */                                         }
/*       */                                         
/*       */ 
/*       */ 
/*  6244 */                                         out.write(10);
/*  6245 */                                         out.write(10);
/*  6246 */                                         out.write(32);
/*       */                                       }
/*       */                                       
/*  6249 */                                       if (type.equals("SYBASEDB"))
/*       */                                       {
/*  6251 */                                         out.write("\n        <TR>\n          <TD width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6252 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.dbname"));
/*  6253 */                                         out.write("<span class=\"mandatory\">*</span></label></TD>\n          <TD width=\"75%\" height=\"28\" >");
/*  6254 */                                         if (_jspx_meth_html_005ftext_005f35(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6256 */                                         out.write("\n          </TD>\n        </TR>\n        ");
/*       */                                       }
/*       */                                       
/*  6259 */                                       if (type.equals("DB2"))
/*       */                                       {
/*  6261 */                                         out.write("\n        <TR>\n          <TD width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6262 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.dbname"));
/*  6263 */                                         out.write("<span class=\"mandatory\">*</span></label></TD>\n          <TD width=\"75%\" height=\"28\" >");
/*  6264 */                                         if (_jspx_meth_html_005ftext_005f36(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6266 */                                         out.write("\n          </TD>\n        </TR>\n        ");
/*       */                                       }
/*       */                                       
/*  6269 */                                       if (type.equals("ORACLEDB"))
/*       */                                       {
/*  6271 */                                         out.write("\n        <TR>\n          <TD width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6272 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.instancename"));
/*  6273 */                                         out.write(" <span class=\"mandatory\">*</span></label></TD>\n          <TD width=\"75%\" height=\"28\" >");
/*  6274 */                                         if (_jspx_meth_html_005ftext_005f37(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6276 */                                         out.write("\n          </TD>\n        </TR>\n        ");
/*       */                                       }
/*       */                                       
/*  6279 */                                       if (type.equals("MYSQLDB"))
/*       */                                       {
/*  6281 */                                         out.write("\n        <TR>\n          <TD width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6282 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.dbname"));
/*  6283 */                                         out.write("<span class=\"mandatory\">*</span></label></TD>\n          <TD width=\"75%\" height=\"28\" >");
/*  6284 */                                         if (_jspx_meth_html_005ftext_005f38(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6286 */                                         out.write("\n          </TD>\n        </TR>\n        ");
/*       */                                       }
/*       */                                       
/*  6289 */                                       if (type.equals("MSSQLDB"))
/*       */                                       {
/*  6291 */                                         out.write("\n        <TR>\n          <TD width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6292 */                                         out.print(FormatUtil.getString("am.webclient.mssql.namedinstance"));
/*  6293 */                                         out.write("</label></TD>\n          <TD width=\"75%\" height=\"28\"><input type=\"checkbox\"  onclick=\"showMssqlInstance(this)\"/>\n          </TD>\n        </TR>\n        <TR id=\"instanceName\" style=\"display:none\">\n          <TD width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6294 */                                         out.print(FormatUtil.getString("am.webclient.db2.instancename"));
/*  6295 */                                         out.write(" <span class=\"mandatory\">*</span></label></TD>\n          <TD width=\"75%\" height=\"28\" >");
/*  6296 */                                         if (_jspx_meth_html_005ftext_005f39(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6298 */                                         out.write("\n          </TD>\n        </TR>\n        ");
/*       */                                       }
/*       */                                       
/*       */ 
/*  6302 */                                       out.write("\n      </TABLE></td>\n  </tr>\n\n</table>\n\n");
/*  6303 */                                       if ((type.equals("SNMP")) || (type.equals("SYSTEM"))) {
/*  6304 */                                         out.write("\n<table width=\"99%\" class=\"lrborder ");
/*  6305 */                                         out.print(hideStyle);
/*  6306 */                                         out.write("\" border=\"0\" id=\"snmpTableView\">\n\n<tr>\n                <td width=\"25%\" class=\"tablebottom bodytextbold label-align addmonitor-label\">\n                    <label>");
/*  6307 */                                         out.print(FormatUtil.getString("webclient.topo.objectdetails.version"));
/*  6308 */                                         out.write("</label>\n                </td>\n                <td align=\"left\" class=\"tablebottom bodytextbold\">\n                    ");
/*  6309 */                                         if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6311 */                                         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6313 */                                         out.write(" &nbsp; &nbsp;\n                    ");
/*  6314 */                                         if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6316 */                                         if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6318 */                                         out.write("\n                </td>\n        </tr>\n        <tr><td colspan=\"2\" align=\"left\"><span id=\"testCredentialResult\"></span></td></tr>\n         <tr>\n         <td colspan=\"2\" height=\"70\">\n         <div id=\"snmpV1V2\" style=\"display:block\">\n            <table width=\"100%\" cellpadding=\"5\">\n                <tr>\n                    <td width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onmouseover=\"ddrivetip(this,event,'");
/*  6319 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.community.value"));
/*  6320 */                                         out.write("',false,true,'#000000',130,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/*  6321 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.community"));
/*  6322 */                                         out.write("</a><span class=\"mandatory\">*</span></label>\n                    </td>\n                    <td height=\"28\" colspan=\"3\">");
/*  6323 */                                         if (_jspx_meth_html_005fpassword_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6325 */                                         out.write("\n                    </td>\n                </tr>\n                <tr>\n                <td>&nbsp;</td>\n                    <td align=\"left\" colspan=\"4\">\n                         <input name=\"testCredentialButton\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/*  6326 */                                         out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.testCredential"));
/*  6327 */                                         out.write("\" onclick=\"javascript:validateAndPerformTestCredential('v1v2');\">\n                    </td>\n                </tr>\n\n            </table>\n         </div>\n         <div id=\"snmpV3\" style=\"display:none\">\n            <table width=\"100%\" cellpadding=\"6\">\n            <tr>\n                <td width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6328 */                                         out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.securityLevel"));
/*  6329 */                                         out.write("<span class=\"mandatory\">*</span></label></td>\n                <td width=\"75%\">\n\t\t\t          ");
/*       */                                         
/*  6331 */                                         SelectTag _jspx_th_html_005fselect_005f10 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  6332 */                                         _jspx_th_html_005fselect_005f10.setPageContext(_jspx_page_context);
/*  6333 */                                         _jspx_th_html_005fselect_005f10.setParent(_jspx_th_html_005fform_005f0);
/*       */                                         
/*  6335 */                                         _jspx_th_html_005fselect_005f10.setProperty("snmpSecurityLevel");
/*       */                                         
/*  6337 */                                         _jspx_th_html_005fselect_005f10.setStyleClass("formtext medium");
/*       */                                         
/*  6339 */                                         _jspx_th_html_005fselect_005f10.setOnchange("javascript:showSecurityLevelProps();");
/*  6340 */                                         int _jspx_eval_html_005fselect_005f10 = _jspx_th_html_005fselect_005f10.doStartTag();
/*  6341 */                                         if (_jspx_eval_html_005fselect_005f10 != 0) {
/*  6342 */                                           if (_jspx_eval_html_005fselect_005f10 != 1) {
/*  6343 */                                             out = _jspx_page_context.pushBody();
/*  6344 */                                             _jspx_th_html_005fselect_005f10.setBodyContent((BodyContent)out);
/*  6345 */                                             _jspx_th_html_005fselect_005f10.doInitBody();
/*       */                                           }
/*       */                                           for (;;) {
/*  6348 */                                             out.write("\n\t\t\t            ");
/*       */                                             
/*  6350 */                                             OptionTag _jspx_th_html_005foption_005f63 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  6351 */                                             _jspx_th_html_005foption_005f63.setPageContext(_jspx_page_context);
/*  6352 */                                             _jspx_th_html_005foption_005f63.setParent(_jspx_th_html_005fselect_005f10);
/*       */                                             
/*  6354 */                                             _jspx_th_html_005foption_005f63.setValue("NOAUTHNOPRIV");
/*  6355 */                                             int _jspx_eval_html_005foption_005f63 = _jspx_th_html_005foption_005f63.doStartTag();
/*  6356 */                                             if (_jspx_eval_html_005foption_005f63 != 0) {
/*  6357 */                                               if (_jspx_eval_html_005foption_005f63 != 1) {
/*  6358 */                                                 out = _jspx_page_context.pushBody();
/*  6359 */                                                 _jspx_th_html_005foption_005f63.setBodyContent((BodyContent)out);
/*  6360 */                                                 _jspx_th_html_005foption_005f63.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  6363 */                                                 out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.noAuthnoPriv"));
/*  6364 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f63.doAfterBody();
/*  6365 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  6368 */                                               if (_jspx_eval_html_005foption_005f63 != 1) {
/*  6369 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  6372 */                                             if (_jspx_th_html_005foption_005f63.doEndTag() == 5) {
/*  6373 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f63); return;
/*       */                                             }
/*       */                                             
/*  6376 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f63);
/*  6377 */                                             out.write("\n\t\t\t            ");
/*       */                                             
/*  6379 */                                             OptionTag _jspx_th_html_005foption_005f64 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  6380 */                                             _jspx_th_html_005foption_005f64.setPageContext(_jspx_page_context);
/*  6381 */                                             _jspx_th_html_005foption_005f64.setParent(_jspx_th_html_005fselect_005f10);
/*       */                                             
/*  6383 */                                             _jspx_th_html_005foption_005f64.setValue("AUTHNOPRIV");
/*  6384 */                                             int _jspx_eval_html_005foption_005f64 = _jspx_th_html_005foption_005f64.doStartTag();
/*  6385 */                                             if (_jspx_eval_html_005foption_005f64 != 0) {
/*  6386 */                                               if (_jspx_eval_html_005foption_005f64 != 1) {
/*  6387 */                                                 out = _jspx_page_context.pushBody();
/*  6388 */                                                 _jspx_th_html_005foption_005f64.setBodyContent((BodyContent)out);
/*  6389 */                                                 _jspx_th_html_005foption_005f64.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  6392 */                                                 out.write(32);
/*  6393 */                                                 out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.authNoPriv"));
/*  6394 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f64.doAfterBody();
/*  6395 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  6398 */                                               if (_jspx_eval_html_005foption_005f64 != 1) {
/*  6399 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  6402 */                                             if (_jspx_th_html_005foption_005f64.doEndTag() == 5) {
/*  6403 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f64); return;
/*       */                                             }
/*       */                                             
/*  6406 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f64);
/*  6407 */                                             out.write("\n\t\t\t            ");
/*       */                                             
/*  6409 */                                             OptionTag _jspx_th_html_005foption_005f65 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  6410 */                                             _jspx_th_html_005foption_005f65.setPageContext(_jspx_page_context);
/*  6411 */                                             _jspx_th_html_005foption_005f65.setParent(_jspx_th_html_005fselect_005f10);
/*       */                                             
/*  6413 */                                             _jspx_th_html_005foption_005f65.setValue("AUTHPRIV");
/*  6414 */                                             int _jspx_eval_html_005foption_005f65 = _jspx_th_html_005foption_005f65.doStartTag();
/*  6415 */                                             if (_jspx_eval_html_005foption_005f65 != 0) {
/*  6416 */                                               if (_jspx_eval_html_005foption_005f65 != 1) {
/*  6417 */                                                 out = _jspx_page_context.pushBody();
/*  6418 */                                                 _jspx_th_html_005foption_005f65.setBodyContent((BodyContent)out);
/*  6419 */                                                 _jspx_th_html_005foption_005f65.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  6422 */                                                 out.write(32);
/*  6423 */                                                 out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.authPriv"));
/*  6424 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f65.doAfterBody();
/*  6425 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  6428 */                                               if (_jspx_eval_html_005foption_005f65 != 1) {
/*  6429 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  6432 */                                             if (_jspx_th_html_005foption_005f65.doEndTag() == 5) {
/*  6433 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f65); return;
/*       */                                             }
/*       */                                             
/*  6436 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f65);
/*  6437 */                                             out.write("\n\t\t\t          ");
/*  6438 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f10.doAfterBody();
/*  6439 */                                             if (evalDoAfterBody != 2)
/*       */                                               break;
/*       */                                           }
/*  6442 */                                           if (_jspx_eval_html_005fselect_005f10 != 1) {
/*  6443 */                                             out = _jspx_page_context.popBody();
/*       */                                           }
/*       */                                         }
/*  6446 */                                         if (_jspx_th_html_005fselect_005f10.doEndTag() == 5) {
/*  6447 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f10); return;
/*       */                                         }
/*       */                                         
/*  6450 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f10);
/*  6451 */                                         out.write("\n\t         </td>\n\n\t    </tr>\n            <TR>\n                <TD width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6452 */                                         out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.userName"));
/*  6453 */                                         out.write("<span class=\"mandatory\">*</span></label>\n                </TD>\n                <TD width=\"75%\" height=\"28\">");
/*  6454 */                                         if (_jspx_meth_html_005ftext_005f40(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6456 */                                         out.write("\n                </TD>\n             </TR>\n             <tr>\n                <TD width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6457 */                                         out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.contextName"));
/*  6458 */                                         out.write("</label>          </TD><!-- Width edited SNMP v3 alignment -->\n                <TD width=\"75%\" height=\"28\">");
/*  6459 */                                         if (_jspx_meth_html_005ftext_005f41(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6461 */                                         out.write("\n                </TD><!-- Width edited SNMP v3 alignment -->\n            </TR>\n             <TR id=\"AuthNoPrivID\" style=\"display:none\">\n                <td width=\"25%\" id=\"AuthNoPrivID3\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6462 */                                         out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.authPassword"));
/*  6463 */                                         out.write("<span class=\"mandatory\">*</span></label>\n                </td>\n                <TD width=\"75%\" id=\"AuthNoPrivID4\" height=\"28\">");
/*  6464 */                                         if (_jspx_meth_html_005fpassword_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6466 */                                         out.write("\n                </TD>\n                </TR>\n                <tr>\n                <td width=\"25%\" id=\"AuthNoPrivID1\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6467 */                                         out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.authProtocol"));
/*  6468 */                                         out.write("<span class=\"mandatory\">*</span></label>\n                </td>\n                <TD width=\"75%\" id=\"AuthNoPrivID2\"> ");
/*       */                                         
/*  6470 */                                         SelectTag _jspx_th_html_005fselect_005f11 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  6471 */                                         _jspx_th_html_005fselect_005f11.setPageContext(_jspx_page_context);
/*  6472 */                                         _jspx_th_html_005fselect_005f11.setParent(_jspx_th_html_005fform_005f0);
/*       */                                         
/*  6474 */                                         _jspx_th_html_005fselect_005f11.setProperty("snmpAuthProtocol");
/*       */                                         
/*  6476 */                                         _jspx_th_html_005fselect_005f11.setStyleClass("formtext small");
/*       */                                         
/*  6478 */                                         _jspx_th_html_005fselect_005f11.setOnchange("");
/*  6479 */                                         int _jspx_eval_html_005fselect_005f11 = _jspx_th_html_005fselect_005f11.doStartTag();
/*  6480 */                                         if (_jspx_eval_html_005fselect_005f11 != 0) {
/*  6481 */                                           if (_jspx_eval_html_005fselect_005f11 != 1) {
/*  6482 */                                             out = _jspx_page_context.pushBody();
/*  6483 */                                             _jspx_th_html_005fselect_005f11.setBodyContent((BodyContent)out);
/*  6484 */                                             _jspx_th_html_005fselect_005f11.doInitBody();
/*       */                                           }
/*       */                                           for (;;) {
/*  6487 */                                             out.write("\n\t\t\t            ");
/*       */                                             
/*  6489 */                                             OptionTag _jspx_th_html_005foption_005f66 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  6490 */                                             _jspx_th_html_005foption_005f66.setPageContext(_jspx_page_context);
/*  6491 */                                             _jspx_th_html_005foption_005f66.setParent(_jspx_th_html_005fselect_005f11);
/*       */                                             
/*  6493 */                                             _jspx_th_html_005foption_005f66.setValue("MD5");
/*  6494 */                                             int _jspx_eval_html_005foption_005f66 = _jspx_th_html_005foption_005f66.doStartTag();
/*  6495 */                                             if (_jspx_eval_html_005foption_005f66 != 0) {
/*  6496 */                                               if (_jspx_eval_html_005foption_005f66 != 1) {
/*  6497 */                                                 out = _jspx_page_context.pushBody();
/*  6498 */                                                 _jspx_th_html_005foption_005f66.setBodyContent((BodyContent)out);
/*  6499 */                                                 _jspx_th_html_005foption_005f66.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  6502 */                                                 out.print(FormatUtil.getString("MD5"));
/*  6503 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f66.doAfterBody();
/*  6504 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  6507 */                                               if (_jspx_eval_html_005foption_005f66 != 1) {
/*  6508 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  6511 */                                             if (_jspx_th_html_005foption_005f66.doEndTag() == 5) {
/*  6512 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f66); return;
/*       */                                             }
/*       */                                             
/*  6515 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f66);
/*  6516 */                                             out.write("\n\t\t\t            ");
/*       */                                             
/*  6518 */                                             OptionTag _jspx_th_html_005foption_005f67 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  6519 */                                             _jspx_th_html_005foption_005f67.setPageContext(_jspx_page_context);
/*  6520 */                                             _jspx_th_html_005foption_005f67.setParent(_jspx_th_html_005fselect_005f11);
/*       */                                             
/*  6522 */                                             _jspx_th_html_005foption_005f67.setValue("SHA");
/*  6523 */                                             int _jspx_eval_html_005foption_005f67 = _jspx_th_html_005foption_005f67.doStartTag();
/*  6524 */                                             if (_jspx_eval_html_005foption_005f67 != 0) {
/*  6525 */                                               if (_jspx_eval_html_005foption_005f67 != 1) {
/*  6526 */                                                 out = _jspx_page_context.pushBody();
/*  6527 */                                                 _jspx_th_html_005foption_005f67.setBodyContent((BodyContent)out);
/*  6528 */                                                 _jspx_th_html_005foption_005f67.doInitBody();
/*       */                                               }
/*       */                                               for (;;) {
/*  6531 */                                                 out.write(32);
/*  6532 */                                                 out.print(FormatUtil.getString("SHA"));
/*  6533 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f67.doAfterBody();
/*  6534 */                                                 if (evalDoAfterBody != 2)
/*       */                                                   break;
/*       */                                               }
/*  6537 */                                               if (_jspx_eval_html_005foption_005f67 != 1) {
/*  6538 */                                                 out = _jspx_page_context.popBody();
/*       */                                               }
/*       */                                             }
/*  6541 */                                             if (_jspx_th_html_005foption_005f67.doEndTag() == 5) {
/*  6542 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f67); return;
/*       */                                             }
/*       */                                             
/*  6545 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f67);
/*  6546 */                                             out.write("\n\t\t\t          ");
/*  6547 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f11.doAfterBody();
/*  6548 */                                             if (evalDoAfterBody != 2)
/*       */                                               break;
/*       */                                           }
/*  6551 */                                           if (_jspx_eval_html_005fselect_005f11 != 1) {
/*  6552 */                                             out = _jspx_page_context.popBody();
/*       */                                           }
/*       */                                         }
/*  6555 */                                         if (_jspx_th_html_005fselect_005f11.doEndTag() == 5) {
/*  6556 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f11); return;
/*       */                                         }
/*       */                                         
/*  6559 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f11);
/*  6560 */                                         out.write("\n                </TD>\n\n            </TR>\n            <tr id=\"AuthPrivID\" style=\"display:none\">\n                 <td id=\"AuthPrivID1\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/*  6561 */                                         out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.privPassword"));
/*  6562 */                                         out.write("<span class=\"mandatory\">*</span></label>\n                </td>\n                <TD id=\"AuthPrivID2\" height=\"28\">");
/*  6563 */                                         if (_jspx_meth_html_005fpassword_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                           return;
/*  6565 */                                         out.write("\n                </TD>\n            </tr>\n                <tr>\n                <td>&nbsp;</td>\n                    <td align=\"left\">\n                        <input name=\"testCredentialButton\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/*  6566 */                                         out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.testCredential"));
/*  6567 */                                         out.write("\" onclick=\"javascript:validateAndPerformTestCredential('v3');\">\n                    </td>\n                </tr>\n\n            </table>\n         </div>\n</td>\n\n</tr>\n\n</table>\n\n\n\n");
/*       */                                       }
/*       */                                       
/*  6570 */                                       if ((!type.equals("All")) && (EnterpriseUtil.isAdminServer()))
/*       */                                       {
/*       */ 
/*  6573 */                                         out.write(10);
/*  6574 */                                         out.write(9);
/*  6575 */                                         out.write(9);
/*  6576 */                                         JspRuntimeLibrary.include(request, response, "/jsp/includes/ShowGroupsDetails.jsp" + ("/jsp/includes/ShowGroupsDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showDetailsOf", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("managedServerGroups", request.getCharacterEncoding()), out, false);
/*  6577 */                                         out.write(9);
/*  6578 */                                         out.write(10);
/*       */                                       }
/*       */                                       
/*       */ 
/*  6582 */                                       out.write(10);
/*       */                                       
/*  6584 */                                       if ((!type.equals("All")) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */                                       {
/*       */ 
/*  6587 */                                         out.write(10);
/*       */                                         
/*  6589 */                                         IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  6590 */                                         _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/*  6591 */                                         _jspx_th_c_005fif_005f22.setParent(_jspx_th_html_005fform_005f0);
/*       */                                         
/*  6593 */                                         _jspx_th_c_005fif_005f22.setTest("${empty param.wiz && empty param.fromAssociate}");
/*  6594 */                                         int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/*  6595 */                                         if (_jspx_eval_c_005fif_005f22 != 0) {
/*       */                                           for (;;) {
/*  6597 */                                             out.write("\n\t\t\t");
/*  6598 */                                             JspRuntimeLibrary.include(request, response, "/jsp/includes/ShowGroupsDetails.jsp" + ("/jsp/includes/ShowGroupsDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showDetailsOf", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("monitorGroups", request.getCharacterEncoding()), out, false);
/*  6599 */                                             out.write("   \t\t\n\t\t");
/*  6600 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/*  6601 */                                             if (evalDoAfterBody != 2)
/*       */                                               break;
/*       */                                           }
/*       */                                         }
/*  6605 */                                         if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/*  6606 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*       */                                         }
/*       */                                         
/*  6609 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/*  6610 */                                         out.write(10);
/*       */ 
/*       */                                       }
/*       */                                       else
/*       */                                       {
/*       */ 
/*  6616 */                                         out.write("\n<input type=\"hidden\" name=\"haid\" value=\"-\" >\n");
/*       */                                       }
/*       */                                       
/*       */ 
/*  6620 */                                       out.write(10);
/*       */                                       
/*  6622 */                                       IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  6623 */                                       _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/*  6624 */                                       _jspx_th_c_005fif_005f23.setParent(_jspx_th_html_005fform_005f0);
/*       */                                       
/*  6626 */                                       _jspx_th_c_005fif_005f23.setTest("${empty param.wiz}");
/*  6627 */                                       int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/*  6628 */                                       if (_jspx_eval_c_005fif_005f23 != 0) {
/*       */                                         for (;;) {
/*  6630 */                                           out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n<td width=\"25%\" class=\"tablebottom\" style=\"height:30px;\">&nbsp;</td>\n    <td align=\"left\" class=\"tablebottom\">\n    ");
/*  6631 */                                           out.write("\n      <input name=\"button1\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/*  6632 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.addmonitor"));
/*  6633 */                                           out.write("\" onclick=\"javascript:fnbeforeFormSubmit();\">\n      &nbsp; ");
/*       */                                           
/*  6635 */                                           ResetTag _jspx_th_html_005freset_005f0 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.get(ResetTag.class);
/*  6636 */                                           _jspx_th_html_005freset_005f0.setPageContext(_jspx_page_context);
/*  6637 */                                           _jspx_th_html_005freset_005f0.setParent(_jspx_th_c_005fif_005f23);
/*       */                                           
/*  6639 */                                           _jspx_th_html_005freset_005f0.setStyleClass("buttons btn_reset");
/*       */                                           
/*  6641 */                                           _jspx_th_html_005freset_005f0.setValue(FormatUtil.getString("am.webclient.global.Reset.text"));
/*  6642 */                                           int _jspx_eval_html_005freset_005f0 = _jspx_th_html_005freset_005f0.doStartTag();
/*  6643 */                                           if (_jspx_th_html_005freset_005f0.doEndTag() == 5) {
/*  6644 */                                             this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.reuse(_jspx_th_html_005freset_005f0); return;
/*       */                                           }
/*       */                                           
/*  6647 */                                           this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.reuse(_jspx_th_html_005freset_005f0);
/*  6648 */                                           out.write(" &nbsp; <input type=\"button\" value=\"");
/*  6649 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.cancel"));
/*  6650 */                                           out.write("\" class=\"buttons btn_link\" id=\"cancelButtonMod\" onclick=\"history.back();\" />\n    </td>\n  </tr> \n</table>\n");
/*  6651 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/*  6652 */                                           if (evalDoAfterBody != 2)
/*       */                                             break;
/*       */                                         }
/*       */                                       }
/*  6656 */                                       if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/*  6657 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23); return;
/*       */                                       }
/*       */                                       
/*  6660 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/*  6661 */                                       out.write(10);
/*  6662 */                                       out.write(10);
/*       */                                       
/*  6664 */                                       IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  6665 */                                       _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/*  6666 */                                       _jspx_th_c_005fif_005f24.setParent(_jspx_th_html_005fform_005f0);
/*       */                                       
/*  6668 */                                       _jspx_th_c_005fif_005f24.setTest("${!empty param.wiz}");
/*  6669 */                                       int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/*  6670 */                                       if (_jspx_eval_c_005fif_005f24 != 0) {
/*       */                                         for (;;) {
/*  6672 */                                           out.write("\n\n<table class=\"lrbborder\" width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tbody><tr>\n  <td class=\"tablebottom\" align=\"left\" width=\"25%\">&nbsp;</td>\n  <td class=\"tablebottom\" align=\"left\" height=\"31\" width=\"80%\">\n\n     <input type=\"button\" name=\"xx2\" value=\"");
/*  6673 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.back"));
/*  6674 */                                           out.write("\" class=\"buttons btn_back\"  onClick=\"javascript:location.href='/showresource.do?method=associateMonitors&haid=");
/*  6675 */                                           if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f24, _jspx_page_context))
/*       */                                             return;
/*  6677 */                                           out.write("&wiz=true'\">\n     <input type=\"button\" name=\"xx12\" value=\"");
/*  6678 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.create"));
/*  6679 */                                           out.write("\" class=\"buttons btn_highlt\"  onClick=\"javascript:fnFormSubmit(3);\">\n     <input name=\"button1\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/*  6680 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.createandaddmore"));
/*  6681 */                                           out.write("\" onclick=\"javascript:fnFormSubmit(1);\">\n     ");
/*       */                                           
/*  6683 */                                           IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  6684 */                                           _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/*  6685 */                                           _jspx_th_c_005fif_005f25.setParent(_jspx_th_c_005fif_005f24);
/*       */                                           
/*  6687 */                                           _jspx_th_c_005fif_005f25.setTest("${!empty associatedmonitors}");
/*  6688 */                                           int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/*  6689 */                                           if (_jspx_eval_c_005fif_005f25 != 0) {
/*       */                                             for (;;) {
/*  6691 */                                               out.write("\n     <input type=\"button\" name=\"xx\" value=\"");
/*  6692 */                                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.configurealert"));
/*  6693 */                                               out.write("\" class=\"buttons btn_highlt\"  onClick=\"javascript:location.href='/showActionProfiles.do?method=getHAProfiles&haid=");
/*  6694 */                                               if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f25, _jspx_page_context))
/*       */                                                 return;
/*  6696 */                                               out.write("&wiz=true'\">\n     ");
/*  6697 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/*  6698 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  6702 */                                           if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/*  6703 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25); return;
/*       */                                           }
/*       */                                           
/*  6706 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/*  6707 */                                           out.write("\n     <input type=\"button\" name=\"xx1\" value=\"");
/*  6708 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.finish"));
/*  6709 */                                           out.write("\" class=\"buttons btn_highlt\"  onClick=\"javascript:location.href='/showapplication.do?method=showApplication&haid=");
/*  6710 */                                           if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f24, _jspx_page_context))
/*       */                                             return;
/*  6712 */                                           out.write("'\"></td>\n\t </tr>\n\t </tbody>\n\t</table>\n\t<table class=\"lrbborder\" width=\"99%\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\">\n\n\t <tr><td  class=\"bodytext\">");
/*  6713 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.createdesc"));
/*  6714 */                                           out.write("</td></tr>\n\t <tr><td  class=\"bodytext\">");
/*  6715 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.createandaddmoredesc"));
/*  6716 */                                           out.write("</td></tr>\n\t ");
/*       */                                           
/*  6718 */                                           IfTag _jspx_th_c_005fif_005f26 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  6719 */                                           _jspx_th_c_005fif_005f26.setPageContext(_jspx_page_context);
/*  6720 */                                           _jspx_th_c_005fif_005f26.setParent(_jspx_th_c_005fif_005f24);
/*       */                                           
/*  6722 */                                           _jspx_th_c_005fif_005f26.setTest("${!empty associatedmonitors}");
/*  6723 */                                           int _jspx_eval_c_005fif_005f26 = _jspx_th_c_005fif_005f26.doStartTag();
/*  6724 */                                           if (_jspx_eval_c_005fif_005f26 != 0) {
/*       */                                             for (;;) {
/*  6726 */                                               out.write("\n\t <tr><td  class=\"bodytext\">");
/*  6727 */                                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.configurealertdesc"));
/*  6728 */                                               out.write("</td></tr>\n\t ");
/*  6729 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f26.doAfterBody();
/*  6730 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  6734 */                                           if (_jspx_th_c_005fif_005f26.doEndTag() == 5) {
/*  6735 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26); return;
/*       */                                           }
/*       */                                           
/*  6738 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/*  6739 */                                           out.write("\n\t <tr><td  class=\"bodytext\">");
/*  6740 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.finishdesc"));
/*  6741 */                                           out.write("</td></tr>\n  </tr>\n  </table>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"2%\" background=\"/images/wiz_bg_graylind.gif\"><img src=\"../images/wiz_startimage_bottom.gif\" width=\"20\" height=\"19\"></td>\n    <td width=\"94%\" background=\"../images/wiz_bg_grayline_bottom.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"19\"></td>\n    <td width=\"4%\" align=\"right\" background=\"../images/wiz_bg_grayline_bottom.gif\"><img src=\"../images/wiz_endicon_bottom.gif\" width=\"32\" height=\"19\"></td>\n  </tr>\n</table>\n");
/*  6742 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/*  6743 */                                           if (evalDoAfterBody != 2)
/*       */                                             break;
/*       */                                         }
/*       */                                       }
/*  6747 */                                       if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/*  6748 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24); return;
/*       */                                       }
/*       */                                       
/*  6751 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/*  6752 */                                       out.write("\n</td>\n<td width=\"30%\" valign=\"top\">\n\n");
/*  6753 */                                       StringBuffer helpcardKey = new StringBuffer();
/*  6754 */                                       out.write(10);
/*       */                                       
/*  6756 */                                       IfTag _jspx_th_c_005fif_005f27 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  6757 */                                       _jspx_th_c_005fif_005f27.setPageContext(_jspx_page_context);
/*  6758 */                                       _jspx_th_c_005fif_005f27.setParent(_jspx_th_html_005fform_005f0);
/*       */                                       
/*  6760 */                                       _jspx_th_c_005fif_005f27.setTest("${type=='SYSTEM'||type =='WEBLOGIC' || type=='PHP' || type =='TELNET' || type == 'WLI' || type =='JBoss' || type =='Tomcat' || type =='WEBSPHERE' || type =='APACHE' || type == 'WTA' || type == 'ORACLEAS'||type == 'JDK1.5'||type == 'SAP' || type == 'MSSQLDB' || type=='DB2' || type=='ORACLEDB'|| type=='SYBASEDB' || type == 'MYSQLDB' ||type=='JMX1.2-MX4J-RMI' || type=='.Net' || type=='IIS' || type=='SERVICE' || type=='WEB' || type=='Exchange' || type=='MAIL' || type=='SNMP' || type=='All'}");
/*  6761 */                                       int _jspx_eval_c_005fif_005f27 = _jspx_th_c_005fif_005f27.doStartTag();
/*  6762 */                                       if (_jspx_eval_c_005fif_005f27 != 0) {
/*       */                                         for (;;) {
/*  6764 */                                           out.write(10);
/*  6765 */                                           out.write(9);
/*       */                                           
/*  6767 */                                           IfTag _jspx_th_c_005fif_005f28 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  6768 */                                           _jspx_th_c_005fif_005f28.setPageContext(_jspx_page_context);
/*  6769 */                                           _jspx_th_c_005fif_005f28.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  6771 */                                           _jspx_th_c_005fif_005f28.setTest("${type =='SYSTEM'}");
/*  6772 */                                           int _jspx_eval_c_005fif_005f28 = _jspx_th_c_005fif_005f28.doStartTag();
/*  6773 */                                           if (_jspx_eval_c_005fif_005f28 != 0) {
/*       */                                             for (;;) {
/*  6775 */                                               out.write(10);
/*  6776 */                                               out.write(9);
/*  6777 */                                               out.write(32);
/*  6778 */                                               if (form.getOs().equals("AS400/iSeries")) {
/*  6779 */                                                 helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.AS400.helpcard"));
/*       */                                               }
/*  6781 */                                               else if ((form.getOs().startsWith("Windows")) || (form.getOs().startsWith("windows"))) {
/*  6782 */                                                 helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.Windows.helpcard"));
/*       */                                               }
/*       */                                               else {
/*  6785 */                                                 helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.Severs.helpcard"));
/*       */                                               }
/*       */                                               
/*  6788 */                                               out.write(10);
/*  6789 */                                               out.write(9);
/*  6790 */                                               out.write(32);
/*  6791 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f28.doAfterBody();
/*  6792 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  6796 */                                           if (_jspx_th_c_005fif_005f28.doEndTag() == 5) {
/*  6797 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28); return;
/*       */                                           }
/*       */                                           
/*  6800 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/*  6801 */                                           out.write(10);
/*  6802 */                                           out.write(9);
/*  6803 */                                           out.write(32);
/*       */                                           
/*  6805 */                                           IfTag _jspx_th_c_005fif_005f29 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  6806 */                                           _jspx_th_c_005fif_005f29.setPageContext(_jspx_page_context);
/*  6807 */                                           _jspx_th_c_005fif_005f29.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  6809 */                                           _jspx_th_c_005fif_005f29.setTest("${type =='APACHE'}");
/*  6810 */                                           int _jspx_eval_c_005fif_005f29 = _jspx_th_c_005fif_005f29.doStartTag();
/*  6811 */                                           if (_jspx_eval_c_005fif_005f29 != 0) {
/*       */                                             for (;;) {
/*  6813 */                                               out.write("\t\t  \n\t\t    ");
/*  6814 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.apache.helpcard"));
/*  6815 */                                               out.write(10);
/*  6816 */                                               out.write(9);
/*  6817 */                                               out.write(32);
/*  6818 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f29.doAfterBody();
/*  6819 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  6823 */                                           if (_jspx_th_c_005fif_005f29.doEndTag() == 5) {
/*  6824 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29); return;
/*       */                                           }
/*       */                                           
/*  6827 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/*  6828 */                                           out.write("\n   ");
/*  6829 */                                           String workingdir = null;
/*  6830 */                                           String add = null;
/*  6831 */                                           if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows")))
/*       */                                           {
/*       */ 
/*  6834 */                                             workingdir = new File(com.adventnet.nms.util.NmsUtil.getAIM_ROOT()).getAbsoluteFile().getParentFile().getAbsolutePath();
/*       */ 
/*       */                                           }
/*       */                                           else
/*       */                                           {
/*  6839 */                                             workingdir = new File(com.adventnet.nms.util.NmsUtil.getAIM_ROOT()).getAbsoluteFile().getParentFile().getAbsolutePath();
/*       */                                           }
/*       */                                           
/*  6842 */                                           out.write(10);
/*  6843 */                                           out.write(9);
/*       */                                           
/*  6845 */                                           IfTag _jspx_th_c_005fif_005f30 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  6846 */                                           _jspx_th_c_005fif_005f30.setPageContext(_jspx_page_context);
/*  6847 */                                           _jspx_th_c_005fif_005f30.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  6849 */                                           _jspx_th_c_005fif_005f30.setTest("${type =='WEBLOGIC' }");
/*  6850 */                                           int _jspx_eval_c_005fif_005f30 = _jspx_th_c_005fif_005f30.doStartTag();
/*  6851 */                                           if (_jspx_eval_c_005fif_005f30 != 0) {
/*       */                                             for (;;) {
/*  6853 */                                               out.write(10);
/*  6854 */                                               out.write(9);
/*       */                                               
/*  6856 */                                               String[] array = new String[6];
/*       */                                               try
/*       */                                               {
/*  6859 */                                                 add = InetAddress.getLocalHost().getHostName();
/*       */                                               }
/*       */                                               catch (Exception e)
/*       */                                               {
/*  6863 */                                                 System.out.println("Exception in hostdiscoveryform" + e);
/*       */                                               }
/*  6865 */                                               array[0] = add;
/*  6866 */                                               array[1] = (workingdir + File.separator + "<br>working" + File.separator + "classes" + File.separator + "weblogic" + File.separator);
/*  6867 */                                               array[2] = (workingdir + File.separator + "working" + File.separator + "classes" + File.separator + "weblogic" + File.separator);
/*  6868 */                                               array[3] = (workingdir + File.separator + "<br>working" + File.separator + "classes" + File.separator + "weblogic" + File.separator + "version8<br>");
/*  6869 */                                               array[4] = (workingdir + File.separator + "<br>working" + File.separator + "classes" + File.separator + "weblogic" + File.separator + "version9<br>");
/*  6870 */                                               array[5] = (workingdir + File.separator + "<br>working" + File.separator + "classes" + File.separator + "weblogic" + File.separator + "version10<br>");
/*  6871 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.weblogicserver.helpcard", array));
/*       */                                               
/*  6873 */                                               out.write(" \n   ");
/*  6874 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f30.doAfterBody();
/*  6875 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  6879 */                                           if (_jspx_th_c_005fif_005f30.doEndTag() == 5) {
/*  6880 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30); return;
/*       */                                           }
/*       */                                           
/*  6883 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/*  6884 */                                           out.write("\n   ");
/*       */                                           
/*  6886 */                                           IfTag _jspx_th_c_005fif_005f31 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  6887 */                                           _jspx_th_c_005fif_005f31.setPageContext(_jspx_page_context);
/*  6888 */                                           _jspx_th_c_005fif_005f31.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  6890 */                                           _jspx_th_c_005fif_005f31.setTest("${ type =='WLI'}");
/*  6891 */                                           int _jspx_eval_c_005fif_005f31 = _jspx_th_c_005fif_005f31.doStartTag();
/*  6892 */                                           if (_jspx_eval_c_005fif_005f31 != 0) {
/*       */                                             for (;;) {
/*  6894 */                                               out.write(10);
/*  6895 */                                               out.write(9);
/*  6896 */                                               String[] array = new String[2];
/*       */                                               try {
/*  6898 */                                                 add = InetAddress.getLocalHost().getHostName();
/*       */                                               }
/*       */                                               catch (Exception e)
/*       */                                               {
/*  6902 */                                                 System.out.println("Exception in hostdiscoveryform" + e);
/*       */                                               }
/*  6904 */                                               array[0] = add;
/*  6905 */                                               array[1] = (workingdir + "<br>" + File.separator + "working" + File.separator + "classes" + File.separator + "weblogic" + File.separator + "version8<br>");
/*  6906 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.wli.helpcard", array));
/*       */                                               
/*  6908 */                                               out.write(10);
/*  6909 */                                               out.write(9);
/*  6910 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f31.doAfterBody();
/*  6911 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  6915 */                                           if (_jspx_th_c_005fif_005f31.doEndTag() == 5) {
/*  6916 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31); return;
/*       */                                           }
/*       */                                           
/*  6919 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/*  6920 */                                           out.write(10);
/*  6921 */                                           out.write(9);
/*       */                                           
/*  6923 */                                           IfTag _jspx_th_c_005fif_005f32 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  6924 */                                           _jspx_th_c_005fif_005f32.setPageContext(_jspx_page_context);
/*  6925 */                                           _jspx_th_c_005fif_005f32.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  6927 */                                           _jspx_th_c_005fif_005f32.setTest("${type =='.Net'}");
/*  6928 */                                           int _jspx_eval_c_005fif_005f32 = _jspx_th_c_005fif_005f32.doStartTag();
/*  6929 */                                           if (_jspx_eval_c_005fif_005f32 != 0) {
/*       */                                             for (;;) {
/*  6931 */                                               out.write(10);
/*  6932 */                                               out.write(9);
/*  6933 */                                               out.write(9);
/*  6934 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.DotNet.helpcard"));
/*  6935 */                                               out.write(10);
/*  6936 */                                               out.write(9);
/*  6937 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f32.doAfterBody();
/*  6938 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  6942 */                                           if (_jspx_th_c_005fif_005f32.doEndTag() == 5) {
/*  6943 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32); return;
/*       */                                           }
/*       */                                           
/*  6946 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32);
/*  6947 */                                           out.write(10);
/*  6948 */                                           out.write(9);
/*       */                                           
/*  6950 */                                           IfTag _jspx_th_c_005fif_005f33 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  6951 */                                           _jspx_th_c_005fif_005f33.setPageContext(_jspx_page_context);
/*  6952 */                                           _jspx_th_c_005fif_005f33.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  6954 */                                           _jspx_th_c_005fif_005f33.setTest("${type =='JBoss'}");
/*  6955 */                                           int _jspx_eval_c_005fif_005f33 = _jspx_th_c_005fif_005f33.doStartTag();
/*  6956 */                                           if (_jspx_eval_c_005fif_005f33 != 0) {
/*       */                                             for (;;) {
/*  6958 */                                               out.write("\n\t   \t ");
/*  6959 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.jboss.helpcard", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") }));
/*  6960 */                                               out.write(10);
/*  6961 */                                               out.write(9);
/*  6962 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f33.doAfterBody();
/*  6963 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  6967 */                                           if (_jspx_th_c_005fif_005f33.doEndTag() == 5) {
/*  6968 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f33); return;
/*       */                                           }
/*       */                                           
/*  6971 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f33);
/*  6972 */                                           out.write(10);
/*  6973 */                                           out.write(9);
/*       */                                           
/*  6975 */                                           IfTag _jspx_th_c_005fif_005f34 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  6976 */                                           _jspx_th_c_005fif_005f34.setPageContext(_jspx_page_context);
/*  6977 */                                           _jspx_th_c_005fif_005f34.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  6979 */                                           _jspx_th_c_005fif_005f34.setTest("${type =='SiebelEnterpriseServer'}");
/*  6980 */                                           int _jspx_eval_c_005fif_005f34 = _jspx_th_c_005fif_005f34.doStartTag();
/*  6981 */                                           if (_jspx_eval_c_005fif_005f34 != 0) {
/*       */                                             for (;;) {
/*  6983 */                                               out.write("\n\t   \t ");
/*  6984 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.siebel.helpcard", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") }));
/*  6985 */                                               out.write(10);
/*  6986 */                                               out.write(9);
/*  6987 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f34.doAfterBody();
/*  6988 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  6992 */                                           if (_jspx_th_c_005fif_005f34.doEndTag() == 5) {
/*  6993 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f34); return;
/*       */                                           }
/*       */                                           
/*  6996 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f34);
/*  6997 */                                           out.write("\n\t\n\t\n\t");
/*       */                                           
/*  6999 */                                           IfTag _jspx_th_c_005fif_005f35 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7000 */                                           _jspx_th_c_005fif_005f35.setPageContext(_jspx_page_context);
/*  7001 */                                           _jspx_th_c_005fif_005f35.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7003 */                                           _jspx_th_c_005fif_005f35.setTest("${type =='Tomcat'}");
/*  7004 */                                           int _jspx_eval_c_005fif_005f35 = _jspx_th_c_005fif_005f35.doStartTag();
/*  7005 */                                           if (_jspx_eval_c_005fif_005f35 != 0) {
/*       */                                             for (;;) {
/*  7007 */                                               out.write("\n\t\t   ");
/*  7008 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.tomcat.helpcard"));
/*  7009 */                                               out.write(10);
/*  7010 */                                               out.write(9);
/*  7011 */                                               out.write(32);
/*  7012 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f35.doAfterBody();
/*  7013 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7017 */                                           if (_jspx_th_c_005fif_005f35.doEndTag() == 5) {
/*  7018 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f35); return;
/*       */                                           }
/*       */                                           
/*  7021 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f35);
/*  7022 */                                           out.write(10);
/*  7023 */                                           out.write(9);
/*  7024 */                                           out.write(32);
/*       */                                           
/*  7026 */                                           IfTag _jspx_th_c_005fif_005f36 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7027 */                                           _jspx_th_c_005fif_005f36.setPageContext(_jspx_page_context);
/*  7028 */                                           _jspx_th_c_005fif_005f36.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7030 */                                           _jspx_th_c_005fif_005f36.setTest("${type =='WEBSPHERE'}");
/*  7031 */                                           int _jspx_eval_c_005fif_005f36 = _jspx_th_c_005fif_005f36.doStartTag();
/*  7032 */                                           if (_jspx_eval_c_005fif_005f36 != 0) {
/*       */                                             for (;;) {
/*  7034 */                                               out.write("\n\t\t    ");
/*  7035 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.webspheare.helpcard"));
/*  7036 */                                               out.write(10);
/*  7037 */                                               out.write(9);
/*  7038 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f36.doAfterBody();
/*  7039 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7043 */                                           if (_jspx_th_c_005fif_005f36.doEndTag() == 5) {
/*  7044 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f36); return;
/*       */                                           }
/*       */                                           
/*  7047 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f36);
/*  7048 */                                           out.write("\n    ");
/*       */                                           
/*  7050 */                                           IfTag _jspx_th_c_005fif_005f37 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7051 */                                           _jspx_th_c_005fif_005f37.setPageContext(_jspx_page_context);
/*  7052 */                                           _jspx_th_c_005fif_005f37.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7054 */                                           _jspx_th_c_005fif_005f37.setTest("${type =='WTA'}");
/*  7055 */                                           int _jspx_eval_c_005fif_005f37 = _jspx_th_c_005fif_005f37.doStartTag();
/*  7056 */                                           if (_jspx_eval_c_005fif_005f37 != 0) {
/*       */                                             for (;;) {
/*  7058 */                                               out.write("\n\t\t     ");
/*  7059 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.wtajar.helpcard", new String[] { OEMUtil.getOEMString("am.wtajarname.text") }));
/*  7060 */                                               out.write("\n   ");
/*  7061 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f37.doAfterBody();
/*  7062 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7066 */                                           if (_jspx_th_c_005fif_005f37.doEndTag() == 5) {
/*  7067 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f37); return;
/*       */                                           }
/*       */                                           
/*  7070 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f37);
/*  7071 */                                           out.write("\n   ");
/*       */                                           
/*  7073 */                                           IfTag _jspx_th_c_005fif_005f38 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7074 */                                           _jspx_th_c_005fif_005f38.setPageContext(_jspx_page_context);
/*  7075 */                                           _jspx_th_c_005fif_005f38.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7077 */                                           _jspx_th_c_005fif_005f38.setTest("${type =='ORACLEAS'}");
/*  7078 */                                           int _jspx_eval_c_005fif_005f38 = _jspx_th_c_005fif_005f38.doStartTag();
/*  7079 */                                           if (_jspx_eval_c_005fif_005f38 != 0) {
/*       */                                             for (;;) {
/*  7081 */                                               out.write("\n\t\t     ");
/*  7082 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.oracleas.helpcard", new String[] { OEMUtil.getOEMString("product.name") }));
/*  7083 */                                               out.write("\n   ");
/*  7084 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f38.doAfterBody();
/*  7085 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7089 */                                           if (_jspx_th_c_005fif_005f38.doEndTag() == 5) {
/*  7090 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f38); return;
/*       */                                           }
/*       */                                           
/*  7093 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f38);
/*  7094 */                                           out.write("\n   ");
/*       */                                           
/*  7096 */                                           IfTag _jspx_th_c_005fif_005f39 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7097 */                                           _jspx_th_c_005fif_005f39.setPageContext(_jspx_page_context);
/*  7098 */                                           _jspx_th_c_005fif_005f39.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7100 */                                           _jspx_th_c_005fif_005f39.setTest("${type =='JDK1.5'}");
/*  7101 */                                           int _jspx_eval_c_005fif_005f39 = _jspx_th_c_005fif_005f39.doStartTag();
/*  7102 */                                           if (_jspx_eval_c_005fif_005f39 != 0) {
/*       */                                             for (;;) {
/*  7104 */                                               out.write("\n\t\t  ");
/*  7105 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.jdk1.5.helpcard", new String[] { OEMUtil.getOEMString("product.name") }));
/*  7106 */                                               out.write("\n   ");
/*  7107 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f39.doAfterBody();
/*  7108 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7112 */                                           if (_jspx_th_c_005fif_005f39.doEndTag() == 5) {
/*  7113 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f39); return;
/*       */                                           }
/*       */                                           
/*  7116 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f39);
/*  7117 */                                           out.write("\n   ");
/*       */                                           
/*  7119 */                                           IfTag _jspx_th_c_005fif_005f40 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7120 */                                           _jspx_th_c_005fif_005f40.setPageContext(_jspx_page_context);
/*  7121 */                                           _jspx_th_c_005fif_005f40.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7123 */                                           _jspx_th_c_005fif_005f40.setTest("${type =='SAP'}");
/*  7124 */                                           int _jspx_eval_c_005fif_005f40 = _jspx_th_c_005fif_005f40.doStartTag();
/*  7125 */                                           if (_jspx_eval_c_005fif_005f40 != 0) {
/*       */                                             for (;;) {
/*  7127 */                                               out.write("\n\t\t  ");
/*  7128 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.sap.helpcard", new String[] { OEMUtil.getOEMString("product.name"), OEMUtil.getOEMString("company.troubleshoot.link") }));
/*  7129 */                                               out.write("\n    ");
/*  7130 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f40.doAfterBody();
/*  7131 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7135 */                                           if (_jspx_th_c_005fif_005f40.doEndTag() == 5) {
/*  7136 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f40); return;
/*       */                                           }
/*       */                                           
/*  7139 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f40);
/*  7140 */                                           out.write(10);
/*  7141 */                                           out.write(9);
/*       */                                           
/*  7143 */                                           IfTag _jspx_th_c_005fif_005f41 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7144 */                                           _jspx_th_c_005fif_005f41.setPageContext(_jspx_page_context);
/*  7145 */                                           _jspx_th_c_005fif_005f41.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7147 */                                           _jspx_th_c_005fif_005f41.setTest("${type =='DB2'}");
/*  7148 */                                           int _jspx_eval_c_005fif_005f41 = _jspx_th_c_005fif_005f41.doStartTag();
/*  7149 */                                           if (_jspx_eval_c_005fif_005f41 != 0) {
/*       */                                             for (;;) {
/*  7151 */                                               out.write("\n\t\t\t");
/*  7152 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.DB2.helpcard"));
/*  7153 */                                               out.write(10);
/*  7154 */                                               out.write(9);
/*  7155 */                                               out.write(32);
/*  7156 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f41.doAfterBody();
/*  7157 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7161 */                                           if (_jspx_th_c_005fif_005f41.doEndTag() == 5) {
/*  7162 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f41); return;
/*       */                                           }
/*       */                                           
/*  7165 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f41);
/*  7166 */                                           out.write(10);
/*  7167 */                                           out.write(9);
/*  7168 */                                           out.write(32);
/*       */                                           
/*  7170 */                                           IfTag _jspx_th_c_005fif_005f42 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7171 */                                           _jspx_th_c_005fif_005f42.setPageContext(_jspx_page_context);
/*  7172 */                                           _jspx_th_c_005fif_005f42.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7174 */                                           _jspx_th_c_005fif_005f42.setTest("${type =='ORACLEDB'}");
/*  7175 */                                           int _jspx_eval_c_005fif_005f42 = _jspx_th_c_005fif_005f42.doStartTag();
/*  7176 */                                           if (_jspx_eval_c_005fif_005f42 != 0) {
/*       */                                             for (;;) {
/*  7178 */                                               out.write("\n\t\t    ");
/*  7179 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.OracleDB.helpcard"));
/*  7180 */                                               out.write("\n\t  ");
/*  7181 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f42.doAfterBody();
/*  7182 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7186 */                                           if (_jspx_th_c_005fif_005f42.doEndTag() == 5) {
/*  7187 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f42); return;
/*       */                                           }
/*       */                                           
/*  7190 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f42);
/*  7191 */                                           out.write("\n\t  ");
/*       */                                           
/*  7193 */                                           IfTag _jspx_th_c_005fif_005f43 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7194 */                                           _jspx_th_c_005fif_005f43.setPageContext(_jspx_page_context);
/*  7195 */                                           _jspx_th_c_005fif_005f43.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7197 */                                           _jspx_th_c_005fif_005f43.setTest("${type =='SYBASEDB'}");
/*  7198 */                                           int _jspx_eval_c_005fif_005f43 = _jspx_th_c_005fif_005f43.doStartTag();
/*  7199 */                                           if (_jspx_eval_c_005fif_005f43 != 0) {
/*       */                                             for (;;) {
/*  7201 */                                               out.write("\n\t\t    ");
/*  7202 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.SybaseDB.helpcard"));
/*  7203 */                                               out.write(10);
/*  7204 */                                               out.write(9);
/*  7205 */                                               out.write(32);
/*  7206 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f43.doAfterBody();
/*  7207 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7211 */                                           if (_jspx_th_c_005fif_005f43.doEndTag() == 5) {
/*  7212 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f43); return;
/*       */                                           }
/*       */                                           
/*  7215 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f43);
/*  7216 */                                           out.write(10);
/*  7217 */                                           out.write(9);
/*  7218 */                                           out.write(32);
/*       */                                           
/*  7220 */                                           IfTag _jspx_th_c_005fif_005f44 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7221 */                                           _jspx_th_c_005fif_005f44.setPageContext(_jspx_page_context);
/*  7222 */                                           _jspx_th_c_005fif_005f44.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7224 */                                           _jspx_th_c_005fif_005f44.setTest("${type =='MYSQLDB'}");
/*  7225 */                                           int _jspx_eval_c_005fif_005f44 = _jspx_th_c_005fif_005f44.doStartTag();
/*  7226 */                                           if (_jspx_eval_c_005fif_005f44 != 0) {
/*       */                                             for (;;) {
/*  7228 */                                               out.write("\n\t\t   ");
/*  7229 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.mysql.helpcard", new String[] { OEMUtil.getOEMString("product.name") }));
/*  7230 */                                               out.write(10);
/*  7231 */                                               out.write(9);
/*  7232 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f44.doAfterBody();
/*  7233 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7237 */                                           if (_jspx_th_c_005fif_005f44.doEndTag() == 5) {
/*  7238 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f44); return;
/*       */                                           }
/*       */                                           
/*  7241 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f44);
/*  7242 */                                           out.write(10);
/*  7243 */                                           out.write(9);
/*       */                                           
/*  7245 */                                           IfTag _jspx_th_c_005fif_005f45 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7246 */                                           _jspx_th_c_005fif_005f45.setPageContext(_jspx_page_context);
/*  7247 */                                           _jspx_th_c_005fif_005f45.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7249 */                                           _jspx_th_c_005fif_005f45.setTest("${type =='MSSQLDB'}");
/*  7250 */                                           int _jspx_eval_c_005fif_005f45 = _jspx_th_c_005fif_005f45.doStartTag();
/*  7251 */                                           if (_jspx_eval_c_005fif_005f45 != 0) {
/*       */                                             for (;;) {
/*  7253 */                                               out.write("\n\t\t  \t ");
/*  7254 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.mssql.helpcard", new String[] { OEMUtil.getOEMString("product.name") }));
/*  7255 */                                               out.write(10);
/*  7256 */                                               out.write(9);
/*  7257 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f45.doAfterBody();
/*  7258 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7262 */                                           if (_jspx_th_c_005fif_005f45.doEndTag() == 5) {
/*  7263 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f45); return;
/*       */                                           }
/*       */                                           
/*  7266 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f45);
/*  7267 */                                           out.write(10);
/*  7268 */                                           out.write(9);
/*       */                                           
/*  7270 */                                           IfTag _jspx_th_c_005fif_005f46 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7271 */                                           _jspx_th_c_005fif_005f46.setPageContext(_jspx_page_context);
/*  7272 */                                           _jspx_th_c_005fif_005f46.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7274 */                                           _jspx_th_c_005fif_005f46.setTest("${type =='JMX1.2-MX4J-RMI'}");
/*  7275 */                                           int _jspx_eval_c_005fif_005f46 = _jspx_th_c_005fif_005f46.doStartTag();
/*  7276 */                                           if (_jspx_eval_c_005fif_005f46 != 0) {
/*       */                                             for (;;) {
/*  7278 */                                               out.write("\n\t\t\t ");
/*  7279 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.JMX.helpcard"));
/*  7280 */                                               out.write(10);
/*  7281 */                                               out.write(9);
/*  7282 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f46.doAfterBody();
/*  7283 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7287 */                                           if (_jspx_th_c_005fif_005f46.doEndTag() == 5) {
/*  7288 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f46); return;
/*       */                                           }
/*       */                                           
/*  7291 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f46);
/*  7292 */                                           out.write(10);
/*  7293 */                                           out.write(9);
/*       */                                           
/*  7295 */                                           IfTag _jspx_th_c_005fif_005f47 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7296 */                                           _jspx_th_c_005fif_005f47.setPageContext(_jspx_page_context);
/*  7297 */                                           _jspx_th_c_005fif_005f47.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7299 */                                           _jspx_th_c_005fif_005f47.setTest("${type =='IIS'}");
/*  7300 */                                           int _jspx_eval_c_005fif_005f47 = _jspx_th_c_005fif_005f47.doStartTag();
/*  7301 */                                           if (_jspx_eval_c_005fif_005f47 != 0) {
/*       */                                             for (;;) {
/*  7303 */                                               out.write("\n\t\t\t");
/*  7304 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.IIS.helpcard"));
/*  7305 */                                               out.write(10);
/*  7306 */                                               out.write(9);
/*  7307 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f47.doAfterBody();
/*  7308 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7312 */                                           if (_jspx_th_c_005fif_005f47.doEndTag() == 5) {
/*  7313 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f47); return;
/*       */                                           }
/*       */                                           
/*  7316 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f47);
/*  7317 */                                           out.write(10);
/*  7318 */                                           out.write(9);
/*  7319 */                                           out.write(32);
/*       */                                           
/*  7321 */                                           IfTag _jspx_th_c_005fif_005f48 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7322 */                                           _jspx_th_c_005fif_005f48.setPageContext(_jspx_page_context);
/*  7323 */                                           _jspx_th_c_005fif_005f48.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7325 */                                           _jspx_th_c_005fif_005f48.setTest("${type =='MAIL'}");
/*  7326 */                                           int _jspx_eval_c_005fif_005f48 = _jspx_th_c_005fif_005f48.doStartTag();
/*  7327 */                                           if (_jspx_eval_c_005fif_005f48 != 0) {
/*       */                                             for (;;) {
/*  7329 */                                               out.write("\n\t\t\t");
/*  7330 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.Mail.helpcard"));
/*  7331 */                                               out.write(10);
/*  7332 */                                               out.write(9);
/*  7333 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f48.doAfterBody();
/*  7334 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7338 */                                           if (_jspx_th_c_005fif_005f48.doEndTag() == 5) {
/*  7339 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f48); return;
/*       */                                           }
/*       */                                           
/*  7342 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f48);
/*  7343 */                                           out.write(10);
/*  7344 */                                           out.write(9);
/*  7345 */                                           out.write(32);
/*       */                                           
/*  7347 */                                           IfTag _jspx_th_c_005fif_005f49 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7348 */                                           _jspx_th_c_005fif_005f49.setPageContext(_jspx_page_context);
/*  7349 */                                           _jspx_th_c_005fif_005f49.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7351 */                                           _jspx_th_c_005fif_005f49.setTest("${type =='SNMP'}");
/*  7352 */                                           int _jspx_eval_c_005fif_005f49 = _jspx_th_c_005fif_005f49.doStartTag();
/*  7353 */                                           if (_jspx_eval_c_005fif_005f49 != 0) {
/*       */                                             for (;;) {
/*  7355 */                                               out.write("\n\t\t ");
/*  7356 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.SNMP.helpcard"));
/*  7357 */                                               out.write(10);
/*  7358 */                                               out.write(9);
/*  7359 */                                               out.write(32);
/*  7360 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f49.doAfterBody();
/*  7361 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7365 */                                           if (_jspx_th_c_005fif_005f49.doEndTag() == 5) {
/*  7366 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f49); return;
/*       */                                           }
/*       */                                           
/*  7369 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f49);
/*  7370 */                                           out.write(10);
/*  7371 */                                           out.write(9);
/*  7372 */                                           out.write(32);
/*       */                                           
/*  7374 */                                           IfTag _jspx_th_c_005fif_005f50 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7375 */                                           _jspx_th_c_005fif_005f50.setPageContext(_jspx_page_context);
/*  7376 */                                           _jspx_th_c_005fif_005f50.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7378 */                                           _jspx_th_c_005fif_005f50.setTest("${type =='SERVICE'}");
/*  7379 */                                           int _jspx_eval_c_005fif_005f50 = _jspx_th_c_005fif_005f50.doStartTag();
/*  7380 */                                           if (_jspx_eval_c_005fif_005f50 != 0) {
/*       */                                             for (;;) {
/*  7382 */                                               out.write("\n\t\t\t");
/*  7383 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.service.helpcard"));
/*  7384 */                                               out.write(10);
/*  7385 */                                               out.write(9);
/*  7386 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f50.doAfterBody();
/*  7387 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7391 */                                           if (_jspx_th_c_005fif_005f50.doEndTag() == 5) {
/*  7392 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f50); return;
/*       */                                           }
/*       */                                           
/*  7395 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f50);
/*  7396 */                                           out.write(10);
/*  7397 */                                           out.write(9);
/*       */                                           
/*  7399 */                                           IfTag _jspx_th_c_005fif_005f51 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7400 */                                           _jspx_th_c_005fif_005f51.setPageContext(_jspx_page_context);
/*  7401 */                                           _jspx_th_c_005fif_005f51.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7403 */                                           _jspx_th_c_005fif_005f51.setTest("${type =='WEB'}");
/*  7404 */                                           int _jspx_eval_c_005fif_005f51 = _jspx_th_c_005fif_005f51.doStartTag();
/*  7405 */                                           if (_jspx_eval_c_005fif_005f51 != 0) {
/*       */                                             for (;;) {
/*  7407 */                                               out.write("\n\t\t\t");
/*  7408 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.webserver.helpcard"));
/*  7409 */                                               out.write(10);
/*  7410 */                                               out.write(9);
/*  7411 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f51.doAfterBody();
/*  7412 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7416 */                                           if (_jspx_th_c_005fif_005f51.doEndTag() == 5) {
/*  7417 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f51); return;
/*       */                                           }
/*       */                                           
/*  7420 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f51);
/*  7421 */                                           out.write("\n     ");
/*       */                                           
/*  7423 */                                           IfTag _jspx_th_c_005fif_005f52 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7424 */                                           _jspx_th_c_005fif_005f52.setPageContext(_jspx_page_context);
/*  7425 */                                           _jspx_th_c_005fif_005f52.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7427 */                                           _jspx_th_c_005fif_005f52.setTest("${type =='TELNET'}");
/*  7428 */                                           int _jspx_eval_c_005fif_005f52 = _jspx_th_c_005fif_005f52.doStartTag();
/*  7429 */                                           if (_jspx_eval_c_005fif_005f52 != 0) {
/*       */                                             for (;;) {
/*  7431 */                                               out.write("\n\t\t\t");
/*  7432 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.telnet.helpcard"));
/*  7433 */                                               out.write(10);
/*  7434 */                                               out.write(9);
/*  7435 */                                               out.write(32);
/*  7436 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f52.doAfterBody();
/*  7437 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7441 */                                           if (_jspx_th_c_005fif_005f52.doEndTag() == 5) {
/*  7442 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f52); return;
/*       */                                           }
/*       */                                           
/*  7445 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f52);
/*  7446 */                                           out.write(10);
/*  7447 */                                           out.write(9);
/*  7448 */                                           out.write(32);
/*       */                                           
/*  7450 */                                           IfTag _jspx_th_c_005fif_005f53 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7451 */                                           _jspx_th_c_005fif_005f53.setPageContext(_jspx_page_context);
/*  7452 */                                           _jspx_th_c_005fif_005f53.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7454 */                                           _jspx_th_c_005fif_005f53.setTest("${type =='PHP'}");
/*  7455 */                                           int _jspx_eval_c_005fif_005f53 = _jspx_th_c_005fif_005f53.doStartTag();
/*  7456 */                                           if (_jspx_eval_c_005fif_005f53 != 0) {
/*       */                                             for (;;) {
/*  7458 */                                               out.write("\n\t\t\t");
/*  7459 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.php.helpcard"));
/*  7460 */                                               out.write(10);
/*  7461 */                                               out.write(9);
/*  7462 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f53.doAfterBody();
/*  7463 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7467 */                                           if (_jspx_th_c_005fif_005f53.doEndTag() == 5) {
/*  7468 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f53); return;
/*       */                                           }
/*       */                                           
/*  7471 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f53);
/*  7472 */                                           out.write(10);
/*  7473 */                                           out.write(9);
/*       */                                           
/*  7475 */                                           IfTag _jspx_th_c_005fif_005f54 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7476 */                                           _jspx_th_c_005fif_005f54.setPageContext(_jspx_page_context);
/*  7477 */                                           _jspx_th_c_005fif_005f54.setParent(_jspx_th_c_005fif_005f27);
/*       */                                           
/*  7479 */                                           _jspx_th_c_005fif_005f54.setTest("${type =='All'}");
/*  7480 */                                           int _jspx_eval_c_005fif_005f54 = _jspx_th_c_005fif_005f54.doStartTag();
/*  7481 */                                           if (_jspx_eval_c_005fif_005f54 != 0) {
/*       */                                             for (;;) {
/*  7483 */                                               out.write("\n\t\t\t");
/*  7484 */                                               helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.AllServices.helpcard"));
/*  7485 */                                               out.write(10);
/*  7486 */                                               out.write(9);
/*  7487 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f54.doAfterBody();
/*  7488 */                                               if (evalDoAfterBody != 2)
/*       */                                                 break;
/*       */                                             }
/*       */                                           }
/*  7492 */                                           if (_jspx_th_c_005fif_005f54.doEndTag() == 5) {
/*  7493 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f54); return;
/*       */                                           }
/*       */                                           
/*  7496 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f54);
/*  7497 */                                           out.write(10);
/*  7498 */                                           JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(helpcardKey.toString()), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()), out, false);
/*  7499 */                                           out.write(" \n\t  ");
/*  7500 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f27.doAfterBody();
/*  7501 */                                           if (evalDoAfterBody != 2)
/*       */                                             break;
/*       */                                         }
/*       */                                       }
/*  7505 */                                       if (_jspx_th_c_005fif_005f27.doEndTag() == 5) {
/*  7506 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27); return;
/*       */                                       }
/*       */                                       
/*  7509 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/*  7510 */                                       out.write(10);
/*  7511 */                                       out.write(10);
/*       */ 
/*       */                                     }
/*       */                                     else
/*       */                                     {
/*       */ 
/*  7517 */                                       out.write("\n<TABLE width=\"99%\" border=\"0\" cellpadding=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\">\n  <tr>\n    <td align=\"center\">\n      <input name=\"closeButton\" type=\"button\" class=\"buttons btn_link\" value=\"");
/*  7518 */                                       out.print(FormatUtil.getString("Close Window"));
/*  7519 */                                       out.write("\" onclick=\"javascript:closePopUpWindow();\">\n      ");
/*  7520 */                                       if (!isDiscoverySuccess) {
/*  7521 */                                         out.write("\n              ");
/*       */                                         
/*  7523 */                                         ResetTag _jspx_th_html_005freset_005f1 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.get(ResetTag.class);
/*  7524 */                                         _jspx_th_html_005freset_005f1.setPageContext(_jspx_page_context);
/*  7525 */                                         _jspx_th_html_005freset_005f1.setParent(_jspx_th_html_005fform_005f0);
/*       */                                         
/*  7527 */                                         _jspx_th_html_005freset_005f1.setStyleClass("buttons btn_reset");
/*       */                                         
/*  7529 */                                         _jspx_th_html_005freset_005f1.setValue(FormatUtil.getString("am.webclient.goback.readd.txt"));
/*       */                                         
/*  7531 */                                         _jspx_th_html_005freset_005f1.setOnclick("javascript:history.back();");
/*  7532 */                                         int _jspx_eval_html_005freset_005f1 = _jspx_th_html_005freset_005f1.doStartTag();
/*  7533 */                                         if (_jspx_th_html_005freset_005f1.doEndTag() == 5) {
/*  7534 */                                           this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f1); return;
/*       */                                         }
/*       */                                         
/*  7537 */                                         this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f1);
/*  7538 */                                         out.write("\n      ");
/*       */                                       }
/*  7540 */                                       out.write("\n      </td>\n      </tr>\n      </table>\n");
/*       */                                     }
/*       */                                     
/*       */ 
/*  7544 */                                     out.write(10);
/*  7545 */                                     out.write(10);
/*  7546 */                                     out.write(10);
/*  7547 */                                     if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*       */                                       return;
/*  7549 */                                     out.write(10);
/*  7550 */                                     out.write(10);
/*  7551 */                                     out.write(10);
/*  7552 */                                     int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/*  7553 */                                     if (evalDoAfterBody != 2)
/*       */                                       break;
/*       */                                   }
/*       */                                 }
/*  7557 */                                 if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/*  7558 */                                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*       */                                 }
/*       */                                 
/*  7561 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*  7562 */                                 out.write("\n\n  </td>\n        </tr>\n        </table>\n<script>\n function changeUserName()\n   {\n                   val=document.AMActionForm.emailid.value\n                   if(val.indexOf('@')!=-1)\n                   {\n                           document.AMActionForm.username.value=val.substring(0,val.indexOf('@'));\n\n                   }\n                   else\n                   {\n                           document.AMActionForm.username.value=val;\n                   }\n\n\n   }\n\nfunction fnHostChange()\n{\n\t");
/*       */                                 
/*  7564 */                                 if (type.equals("MAIL"))
/*       */                                 {
/*       */ 
/*       */ 
/*  7568 */                                   out.write("\n\n\tdocument.AMActionForm.popHost.value=document.AMActionForm.host.value;\n\n\t");
/*       */                                 }
/*       */                                 
/*       */ 
/*  7572 */                                 out.write("\n\treturn;\n}\n  /*function onComboChange1()\n\t{\n\t\tjavascript:showDiv('username');\n\t\tjavascript:showDiv('password');\n\t\tjavascript:showDiv('authentication');\n\t}*/\n\n\tfunction onComboChange1()\n\t{\n\t var version=document.AMActionForm.version.value;\n\t \t if(version==\"unknown\")\n\t {\n\t \t\talert(\"");
/*  7573 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.version.text", new Object[] { type }));
/*  7574 */                                 out.write("\"); // NO I18N\n\t  return;\n\t }\n\t else if(version=='0')\n\t {\n\t\t alert(\"");
/*  7575 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.version.text", new Object[] { type }));
/*  7576 */                                 out.write("\"); // NO I18N\n\t  return;\n\t }\n\t if(version==\"JBOSS_HTTP60\"){\n\t    document.AMActionForm.port.value=\"1090\";\n\t    document.AMActionForm.port.select();\n\t    javascript:showDiv('message6');\n\t    javascript:hideDiv('message7');\n\t } else if(version==\"JBOSS_HTTP\" ||version==\"JBOSS_HTTP40\" || version==\"JBOSS_HTTP401\" ||version==\"JBOSS_HTTP402\"){ //NO I18N\n\t    document.AMActionForm.port.value=\"8080\";\n\t    javascript:showDiv('message7');\n\t    javascript:hideDiv('message6');\n\t }else if(version=='JBOSS_HTTP7')\n\t {\n\t document.AMActionForm.port.value=\"9990\"; //NO I18N\n\t $(\"#isDomain\").show(\"slow\"); //NO I18N\n\t }\n\t testService(version);\n\t}\n\n\tfunction onComboChange2()\n\t{\n\t var version=document.AMActionForm.version.value;\n\t if(version==\"unknown\")\n\t {\n\t\tjavascript:hideDiv('NotES2007');\n\t\tjavascript:hideDiv('ES2007');\n\t  alert(\"");
/*  7577 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.exchangeversion"));
/*  7578 */                                 out.write("\"); // NO I18N\n\t  return;\n\t }\n\t else if(version=='0')\n\t {\n\t  alert(\"");
/*  7579 */                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.exchangeversion"));
/*  7580 */                                 out.write("\"); // NO I18N\n\t  return;\n\t }\n\t display();\n\t testService(version);\n\t}\n\n\tfunction display()\n\t{\n\t var version=document.AMActionForm.version.value;\n\t if(version==\"2007\" || version==\"2010\")\n\t {\n\t\t javascript:showDiv('ExchngRoles');\n\t\t javascript:showDiv('ES2007andAbove');\n\t\t javascript:hideDiv('BelowES2007');\t\t \n  \t  }\n  \t  if(version!=\"2007\" && version!=\"2010\")\n\t {\n\t\t javascript:hideDiv('ES2007andAbove');\n\t\t javascript:hideDiv('ExchngRoles');\n\t\t javascript:showDiv('BelowES2007');\n  \t  }\n\t}\n\n\n  function onComboChange(obj)\n  {\n   if(obj.value=='5' || obj=='5' || obj.value == '6' || obj == '6' || obj.value == '7' || obj == '7')\n   {\n    javascript:showDiv('message5');\n\tjavascript:hideDiv('message4');\n\tjavascript:hideDiv('message3');\n\tjavascript:showRow('tomcatstatus'); //NO I18N\n    javascript:showRow('username');\n\tjavascript:showRow('password');\n\t//javascript:showDiv('authentication');\n   }\n\n   else\n   {\n\t if(obj.value=='0' || obj=='0')\n\t {\n\t  javascript:hideDiv('message3');\n\t  javascript:hideDiv('message4');\n\t  javascript:hideDiv('message5');\n");
/*  7581 */                                 out.write("\t }\n\n    else if(obj.value=='3' || obj=='3')\n\t{\n\t javascript:hideDiv('message4');\n\t javascript:hideDiv('message5');\n\t javascript:showDiv('message3');\n\t}\n\telse if(obj.value=='4' || obj=='4')\n\t{\n\t javascript:hideDiv('message3');\n\t javascript:hideDiv('message5');\n\t javascript:showDiv('message4');\n\t}\n\tjavascript:hideRow('username');\n\tjavascript:hideRow('password');\n\t//javascript:hideDiv('authentication');\n\tjavascript:hideRow('tomcatstatus'); //NO I18N\n   }\n\tif(obj.value!=undefined)\n\t{\n\t onComboChange1();\n\t}\n  }\n\nfunction showServiceDetail()\n{\n\tif (\"");
/*  7582 */                                 out.print(EnterpriseUtil.isAdminServer());
/*  7583 */                                 out.write("\" == \"true\") {\n\t\treturn;\n\t}\n\tvar hostNameToSend = trimAll(document.AMActionForm.host.value) ;\n\tvar type=document.AMActionForm.type.value;\n\tif(type=='APACHE:80')\n\t{\n\t\tdocument.AMActionForm.serverstatusurl.checked=false;\n\t\tshowApacheStatus();\n\t}\n\tif(hostNameToSend==\"\")\n\t{\n\t\t//document.getElementById(\"showNotRunningMessage\").style.display='none';\n\t\t//document.AMActionForm.host.focus();\n\t\treturn;\n\t}\n\tif(http)\n\t{\n\t\tvar url = '/jsp/HostNameDiscovery.jsp?hostName='+escape(hostNameToSend);\n\t\thttp.open(\"GET\",url,true);\n\t\thttp.onreadystatechange = handleServiceMessage;\n\t\thttp.send(null);\n\t}\n\treturn false;\n}\nfunction handleServiceMessage()\n{\n\tif (http.readyState == 4)\n\t{\n\t\tvar result = http.responseText ;\n\t\tvar element=(document.getElementsByName(\"host\"))[0];\n\t\tvar temp=null;\n\t\tvar isPointerReq=false;\n\t\tvar red=\"#FF0000\";\n\t\tddrivetip(element,temp,result,isPointerReq,true,null,red);\n\t\tsetTimeout(\"hideDialog()\",10000);\n\t}\n}\n\nfunction testService(version)\n{\n\tvar hostNameToTest = trimAll(document.AMActionForm.host.value) ;\n\tvar portToTest = trimAll(document.AMActionForm.port.value) ;\n");
/*  7584 */                                 out.write("\tvar typeToSend = document.AMActionForm.type.value;\n\tvar type=document.AMActionForm.type.value;\n\tif(type=='APACHE:80')\n\t{\n\t\tdocument.AMActionForm.serverstatusurl.checked=false;\n\t\tshowApacheStatus();\n\t}\n\tif(portToTest==\"\")\n\t{\n\t\t//document.getElementById(\"showSeviceMessage\").style.display='none';\n\t\treturn;\n\t}\n\tif(http)\n\t{\n\t\tvar url = '/jsp/HostServiceDiscovery.jsp?hostName='+escape(hostNameToTest)+'&portNumber='+escape(portToTest)+'&typeToSend='+escape(typeToSend)+'&version='+escape(version);\n\t\thttp.open(\"GET\",url,true);\n\t\thttp.onreadystatechange = handlePortMessage;\n\t\thttp.send(null);\n\t}\n\treturn false;\n}\nfunction handlePortMessage()\n{\n\tif (http.readyState == 4)\n\t{\n\t\tvar result = http.responseText ;\n\t\tvar element=(document.getElementsByName(\"port\"))[0];\n\t\tvar temp=null;\n\t\tvar isPointerReq=false;\n\t\tvar red=\"#FF0000\";\n\t\tddrivetip(element,temp,result,isPointerReq,true,red,300);\n\t\tsetTimeout(\"hideDialog()\",11000);\n\t\t//document.getElementById(\"showServiceMessage\").innerHTML = result;\n\t\t//document.getElementById(\"showServiceMessage\").style.display='block';\n");
/*  7585 */                                 out.write("\t}\n}\n\nfunction hideDialog()\n{\n\tstartHideFade(\"dhtmltooltip\",0.04);\n}\n\nfunction showPOP()\n{\n\tif(document.AMActionForm.popenabled.checked)\n\t{\n\t\tdocument.getElementById(\"popinfo\").style.display=\"block\";\n\t}\n\telse\n\t{\n\t\tdocument.getElementById(\"popinfo\").style.display=\"none\";\n\t}\n}\n        function showSMTP()\n        {\n\t\tif(document.AMActionForm.smtpauth.checked)\n\t\t{\n\t\t\tdocument.getElementById(\"smtpauthinfo\").style.display=\"block\";\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.getElementById(\"smtpauthinfo\").style.display=\"none\";\n\t\t}\n           \t//toggleDiv('smtpauthinfo');\n           \tif(document.AMActionForm.smtpUserName.value=='')\n           \t{\n                \tval=document.AMActionForm.emailid.value\n                           if(val.indexOf('@')!=-1)\n                           {\n                                   document.AMActionForm.smtpUserName.value=val.substring(0,val.indexOf('@'));\n\n                           }\n                           else\n                           {\n                                   document.AMActionForm.smtpUserName.value=val;\n");
/*  7586 */                                 out.write("                           }\n           }\n\n        }\n\tfunction showApacheAuth()\n        {\n\t\t\tif(document.AMActionForm.apacheauth.checked)\n\t\t\t{\n\t\t\t\tdocument.getElementById(\"apacheauthinfo\").style.display=\"block\";\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tdocument.getElementById(\"apacheauthinfo\").style.display=\"none\";\n\t\t\t}\n\n        }\n\n    function showApacheStatus()\n        {\n\t\tif(document.AMActionForm.serverstatusurl.checked)\n\t\t{\n\t\t\tdocument.getElementById(\"serverstatus\").style.display=\"block\";\n\t\t\tvar protocol=\"http\";\n\t\t\tif(document.AMActionForm.sslenabled.checked)\n\t\t\t{\n\t\t\t\tprotocol=\"https\";\n\t\t\t}\n\t\t\tvar url=protocol+\"://\"+document.AMActionForm.host.value+\":\"+document.AMActionForm.port.value+\"/server-status?auto\";\n\t\t\tdocument.AMActionForm.apacheurl.value=url;\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.AMActionForm.apacheurl.value=\"\";\n\t\t\tdocument.getElementById(\"serverstatus\").style.display=\"none\";\n\t\t}\n\n        }\n\nfunction handleGlobalSecurity(checkbox)\n{\n\tif(checkbox.checked)\n\t{\n\t\tshowRow(\"username\");\n\t\tshowRow(\"password\");\n\t}\n\telse\n\t{\n\t\thideRow(\"username\");\n");
/*  7587 */                                 out.write("\t\thideRow(\"password\");\n\t}\n}\nfunction handleJMXUrlCheckbox(checkbox)\n{\n\tif(checkbox.checked)\n\t{\n\t\tshowRow(\"jmxurlrow\"); //NO I18N\n\t}\n\telse\n\t{\n\t\thideRow(\"jmxurlrow\"); //NO I18N\n\t}\n}\n</script>\n\n\n");
/*  7588 */                                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/*  7589 */                                 if (evalDoAfterBody != 2)
/*       */                                   break;
/*       */                               }
/*  7592 */                               if (_jspx_eval_tiles_005fput_005f3 != 1) {
/*  7593 */                                 out = _jspx_page_context.popBody();
/*       */                               }
/*       */                             }
/*  7596 */                             if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/*  7597 */                               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*       */                             }
/*       */                             
/*  7600 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/*  7601 */                             out.write(10);
/*  7602 */                             out.write(9);
/*       */                           }
/*       */                           
/*       */ 
/*  7606 */                           out.write(10);
/*  7607 */                           out.write(10);
/*  7608 */                           out.write(10);
/*  7609 */                           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*       */                             return;
/*  7611 */                           out.write(10);
/*  7612 */                           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/*  7613 */                           if (evalDoAfterBody != 2)
/*       */                             break;
/*       */                         }
/*       */                       }
/*  7617 */                       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/*  7618 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*       */                       }
/*       */                       else {
/*  7621 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*  7622 */                         out.write(10);
/*       */                         
/*  7624 */                         if (hideFields)
/*       */                         {
/*       */ 
/*  7627 */                           out.write("\n\t<script>\n\t\t$(document.body).ready(function(){\t\t\t\t//NO I18N\n\t\tdocument.getElementById(\"cancelButtonMod\").onclick = null;\n\t\t$(\"#cancelButtonMod\").click(function(){ //No I18N\n\t\t\tclosePopUpWindow();\n\t\t});\n\t\t});\n\t</script>\n");
/*       */                         }
/*       */                         
/*       */ 
/*  7631 */                         out.write(10);
/*  7632 */                         out.write(10);
/*       */                       }
/*  7634 */                     } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  7635 */         out = _jspx_out;
/*  7636 */         if ((out != null) && (out.getBufferSize() != 0))
/*  7637 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  7638 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*       */       }
/*       */     } finally {
/*  7641 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*       */     }
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  7647 */     PageContext pageContext = _jspx_page_context;
/*  7648 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  7650 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/*  7651 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  7652 */     _jspx_th_c_005fout_005f0.setParent(null);
/*       */     
/*  7654 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*       */     
/*  7656 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/*  7657 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  7658 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  7659 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  7660 */       return true;
/*       */     }
/*  7662 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  7663 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  7668 */     PageContext pageContext = _jspx_page_context;
/*  7669 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  7671 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/*  7672 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/*  7673 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*       */     
/*  7675 */     _jspx_th_c_005fforEach_005f0.setItems("${dynamicSites}");
/*       */     
/*  7677 */     _jspx_th_c_005fforEach_005f0.setVar("a");
/*       */     
/*  7679 */     _jspx_th_c_005fforEach_005f0.setVarStatus("rowCounter");
/*  7680 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*       */     try {
/*  7682 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/*  7683 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*       */         for (;;) {
/*  7685 */           out.write(10);
/*  7686 */           out.write(9);
/*  7687 */           out.write(9);
/*  7688 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  7689 */             return true;
/*  7690 */           out.write("\n\t\tif(formCustomerId == customerId)\n\t\t{\n\t\t\tdocument.AMActionForm.haid.options[rowCount++] = new Option(siteName,siteId);\n\t\t}\n\t");
/*  7691 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/*  7692 */           if (evalDoAfterBody != 2)
/*       */             break;
/*       */         }
/*       */       }
/*  7696 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*  7697 */         return 1;
/*       */     } catch (Throwable _jspx_exception) {
/*       */       for (;;) {
/*  7700 */         int tmp206_205 = 0; int[] tmp206_203 = _jspx_push_body_count_c_005fforEach_005f0; int tmp208_207 = tmp206_203[tmp206_205];tmp206_203[tmp206_205] = (tmp208_207 - 1); if (tmp208_207 <= 0) break;
/*  7701 */         out = _jspx_page_context.popBody(); }
/*  7702 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*       */     } finally {
/*  7704 */       _jspx_th_c_005fforEach_005f0.doFinally();
/*  7705 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*       */     }
/*  7707 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*       */   {
/*  7712 */     PageContext pageContext = _jspx_page_context;
/*  7713 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  7715 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/*  7716 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/*  7717 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*       */     
/*  7719 */     _jspx_th_c_005fforEach_005f1.setItems("${a}");
/*       */     
/*  7721 */     _jspx_th_c_005fforEach_005f1.setVar("b");
/*       */     
/*  7723 */     _jspx_th_c_005fforEach_005f1.setVarStatus("rowCounter1");
/*  7724 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*       */     try {
/*  7726 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/*  7727 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*       */         for (;;) {
/*  7729 */           out.write("\n\t\t\t");
/*  7730 */           boolean bool; if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  7731 */             return true;
/*  7732 */           out.write("\n\t\t\t");
/*  7733 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  7734 */             return true;
/*  7735 */           out.write("\n\t\t\t");
/*  7736 */           if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  7737 */             return true;
/*  7738 */           out.write(10);
/*  7739 */           out.write(9);
/*  7740 */           out.write(9);
/*  7741 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/*  7742 */           if (evalDoAfterBody != 2)
/*       */             break;
/*       */         }
/*       */       }
/*  7746 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*  7747 */         return 1;
/*       */     } catch (Throwable _jspx_exception) {
/*       */       for (;;) {
/*  7750 */         int tmp295_294 = 0; int[] tmp295_292 = _jspx_push_body_count_c_005fforEach_005f1; int tmp297_296 = tmp295_292[tmp295_294];tmp295_292[tmp295_294] = (tmp297_296 - 1); if (tmp297_296 <= 0) break;
/*  7751 */         out = _jspx_page_context.popBody(); }
/*  7752 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*       */     } finally {
/*  7754 */       _jspx_th_c_005fforEach_005f1.doFinally();
/*  7755 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*       */     }
/*  7757 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*       */   {
/*  7762 */     PageContext pageContext = _jspx_page_context;
/*  7763 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  7765 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7766 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  7767 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*       */     
/*  7769 */     _jspx_th_c_005fif_005f0.setTest("${rowCounter1.count == 1}");
/*  7770 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  7771 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*       */       for (;;) {
/*  7773 */         out.write("\n\t\t\t\tsiteName = '");
/*  7774 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  7775 */           return true;
/*  7776 */         out.write("';\n\t\t\t");
/*  7777 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  7778 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*       */     }
/*  7782 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  7783 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  7784 */       return true;
/*       */     }
/*  7786 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  7787 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*       */   {
/*  7792 */     PageContext pageContext = _jspx_page_context;
/*  7793 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  7795 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  7796 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/*  7797 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*       */     
/*  7799 */     _jspx_th_c_005fout_005f1.setValue("${b}");
/*  7800 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/*  7801 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/*  7802 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  7803 */       return true;
/*       */     }
/*  7805 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  7806 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*       */   {
/*  7811 */     PageContext pageContext = _jspx_page_context;
/*  7812 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  7814 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7815 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  7816 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*       */     
/*  7818 */     _jspx_th_c_005fif_005f1.setTest("${rowCounter1.count == 2}");
/*  7819 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  7820 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*       */       for (;;) {
/*  7822 */         out.write("\n\t\t\t\tsiteId = '");
/*  7823 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  7824 */           return true;
/*  7825 */         out.write("';\n\t\t\t");
/*  7826 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  7827 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*       */     }
/*  7831 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  7832 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  7833 */       return true;
/*       */     }
/*  7835 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  7836 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*       */   {
/*  7841 */     PageContext pageContext = _jspx_page_context;
/*  7842 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  7844 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  7845 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/*  7846 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*       */     
/*  7848 */     _jspx_th_c_005fout_005f2.setValue("${b}");
/*  7849 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/*  7850 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/*  7851 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  7852 */       return true;
/*       */     }
/*  7854 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  7855 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*       */   {
/*  7860 */     PageContext pageContext = _jspx_page_context;
/*  7861 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  7863 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  7864 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  7865 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*       */     
/*  7867 */     _jspx_th_c_005fif_005f2.setTest("${rowCounter1.count == 3}");
/*  7868 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  7869 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*       */       for (;;) {
/*  7871 */         out.write("\n\t\t\t\tcustomerId = '");
/*  7872 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  7873 */           return true;
/*  7874 */         out.write("';\n\t\t\t");
/*  7875 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  7876 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*       */     }
/*  7880 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  7881 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  7882 */       return true;
/*       */     }
/*  7884 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  7885 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*       */   {
/*  7890 */     PageContext pageContext = _jspx_page_context;
/*  7891 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  7893 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  7894 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/*  7895 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f2);
/*       */     
/*  7897 */     _jspx_th_c_005fout_005f3.setValue("${b}");
/*  7898 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/*  7899 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/*  7900 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/*  7901 */       return true;
/*       */     }
/*  7903 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/*  7904 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  7909 */     PageContext pageContext = _jspx_page_context;
/*  7910 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  7912 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  7913 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/*  7914 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*       */     
/*  7916 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/*  7917 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/*  7918 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*       */       for (;;) {
/*  7920 */         out.write("\nalertUser();\n");
/*  7921 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/*  7922 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*       */     }
/*  7926 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/*  7927 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  7928 */       return true;
/*       */     }
/*  7930 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  7931 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_logic_005fnotPresent_005f0(PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  7936 */     PageContext pageContext = _jspx_page_context;
/*  7937 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  7939 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  7940 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  7941 */     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*       */     
/*  7943 */     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  7944 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  7945 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*       */       for (;;) {
/*  7947 */         out.write("\n\n\n\tif(trimAll(document.AMActionForm.haid.value) == '' || document.AMActionForm.haid.value==\"-\")\n\t{\n\t\tfnFormSubmit(2);\n\t}\n\telse\n\t{\n\t\tfnFormSubmit(1);\n\t}\n\t");
/*  7948 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  7949 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*       */     }
/*  7953 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  7954 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  7955 */       return true;
/*       */     }
/*  7957 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  7958 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  7963 */     PageContext pageContext = _jspx_page_context;
/*  7964 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  7966 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  7967 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/*  7968 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*       */     
/*  7970 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/*  7971 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/*  7972 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*       */       for (;;) {
/*  7974 */         out.write("\nalertUser();\nreturn false;\n");
/*  7975 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/*  7976 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*       */     }
/*  7980 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/*  7981 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*  7982 */       return true;
/*       */     }
/*  7984 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*  7985 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  7990 */     PageContext pageContext = _jspx_page_context;
/*  7991 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  7993 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/*  7994 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/*  7995 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*  7996 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/*  7997 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/*  7998 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/*  7999 */         out = _jspx_page_context.pushBody();
/*  8000 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/*  8001 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8004 */         out.write("password.empty.message");
/*  8005 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/*  8006 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8009 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/*  8010 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8013 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/*  8014 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/*  8015 */       return true;
/*       */     }
/*  8017 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/*  8018 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8023 */     PageContext pageContext = _jspx_page_context;
/*  8024 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8026 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/*  8027 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/*  8028 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/*  8029 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/*  8030 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/*  8031 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/*  8032 */         out = _jspx_page_context.pushBody();
/*  8033 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/*  8034 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8037 */         out.write("password.empty.message");
/*  8038 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/*  8039 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8042 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/*  8043 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8046 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/*  8047 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/*  8048 */       return true;
/*       */     }
/*  8050 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/*  8051 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8056 */     PageContext pageContext = _jspx_page_context;
/*  8057 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8059 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/*  8060 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/*  8061 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/*  8062 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/*  8063 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/*  8064 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/*  8065 */         out = _jspx_page_context.pushBody();
/*  8066 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/*  8067 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8070 */         out.write("password.empty.message");
/*  8071 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/*  8072 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8075 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/*  8076 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8079 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/*  8080 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/*  8081 */       return true;
/*       */     }
/*  8083 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/*  8084 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8089 */     PageContext pageContext = _jspx_page_context;
/*  8090 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8092 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/*  8093 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/*  8094 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  8096 */     _jspx_th_am_005fhiddenparam_005f0.setName("fromAssociate");
/*  8097 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/*  8098 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/*  8099 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/*  8100 */       return true;
/*       */     }
/*  8102 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/*  8103 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_am_005fhiddenparam_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8108 */     PageContext pageContext = _jspx_page_context;
/*  8109 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8111 */     HiddenParam _jspx_th_am_005fhiddenparam_005f1 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/*  8112 */     _jspx_th_am_005fhiddenparam_005f1.setPageContext(_jspx_page_context);
/*  8113 */     _jspx_th_am_005fhiddenparam_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  8115 */     _jspx_th_am_005fhiddenparam_005f1.setName("wiz");
/*  8116 */     int _jspx_eval_am_005fhiddenparam_005f1 = _jspx_th_am_005fhiddenparam_005f1.doStartTag();
/*  8117 */     if (_jspx_th_am_005fhiddenparam_005f1.doEndTag() == 5) {
/*  8118 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/*  8119 */       return true;
/*       */     }
/*  8121 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/*  8122 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8127 */     PageContext pageContext = _jspx_page_context;
/*  8128 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8130 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  8131 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/*  8132 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  8134 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*       */     
/*  8136 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/*  8137 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/*  8138 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/*  8139 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/*  8140 */       return true;
/*       */     }
/*  8142 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/*  8143 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8148 */     PageContext pageContext = _jspx_page_context;
/*  8149 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8151 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonblur_005fnobody.get(TextTag.class);
/*  8152 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/*  8153 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  8155 */     _jspx_th_html_005ftext_005f1.setProperty("host");
/*       */     
/*  8157 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext default");
/*       */     
/*  8159 */     _jspx_th_html_005ftext_005f1.setOnblur("javascript:showServiceDetail()");
/*  8160 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/*  8161 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/*  8162 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonblur_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/*  8163 */       return true;
/*       */     }
/*  8165 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonblur_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/*  8166 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8171 */     PageContext pageContext = _jspx_page_context;
/*  8172 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8174 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  8175 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/*  8176 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  8178 */     _jspx_th_html_005ftext_005f2.setProperty("netmask");
/*       */     
/*  8180 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext default");
/*  8181 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/*  8182 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/*  8183 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/*  8184 */       return true;
/*       */     }
/*  8186 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/*  8187 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8192 */     PageContext pageContext = _jspx_page_context;
/*  8193 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8195 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  8196 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/*  8197 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  8199 */     _jspx_th_c_005fif_005f7.setTest("${!AMActionForm.resolveDNS}");
/*  8200 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/*  8201 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*       */       for (;;) {
/*  8203 */         out.write("style=\"display:none;\"");
/*  8204 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/*  8205 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*       */     }
/*  8209 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/*  8210 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/*  8211 */       return true;
/*       */     }
/*  8213 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/*  8214 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8219 */     PageContext pageContext = _jspx_page_context;
/*  8220 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8222 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/*  8223 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/*  8224 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  8226 */     _jspx_th_html_005fcheckbox_005f0.setProperty("resolveDNS");
/*  8227 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/*  8228 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/*  8229 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/*  8230 */       return true;
/*       */     }
/*  8232 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/*  8233 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f4(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8238 */     PageContext pageContext = _jspx_page_context;
/*  8239 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8241 */     OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8242 */     _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/*  8243 */     _jspx_th_html_005foption_005f4.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*       */     
/*  8245 */     _jspx_th_html_005foption_005f4.setValue("JBOSS_HTTP");
/*  8246 */     int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/*  8247 */     if (_jspx_eval_html_005foption_005f4 != 0) {
/*  8248 */       if (_jspx_eval_html_005foption_005f4 != 1) {
/*  8249 */         out = _jspx_page_context.pushBody();
/*  8250 */         _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/*  8251 */         _jspx_th_html_005foption_005f4.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8254 */         out.write(" 3.2.x ");
/*  8255 */         int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/*  8256 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8259 */       if (_jspx_eval_html_005foption_005f4 != 1) {
/*  8260 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8263 */     if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/*  8264 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/*  8265 */       return true;
/*       */     }
/*  8267 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/*  8268 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f5(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8273 */     PageContext pageContext = _jspx_page_context;
/*  8274 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8276 */     OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8277 */     _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/*  8278 */     _jspx_th_html_005foption_005f5.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*       */     
/*  8280 */     _jspx_th_html_005foption_005f5.setValue("JBOSS_HTTP40");
/*  8281 */     int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/*  8282 */     if (_jspx_eval_html_005foption_005f5 != 0) {
/*  8283 */       if (_jspx_eval_html_005foption_005f5 != 1) {
/*  8284 */         out = _jspx_page_context.pushBody();
/*  8285 */         _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/*  8286 */         _jspx_th_html_005foption_005f5.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8289 */         out.write(" 4.0 ");
/*  8290 */         int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/*  8291 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8294 */       if (_jspx_eval_html_005foption_005f5 != 1) {
/*  8295 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8298 */     if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/*  8299 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/*  8300 */       return true;
/*       */     }
/*  8302 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/*  8303 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f6(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8308 */     PageContext pageContext = _jspx_page_context;
/*  8309 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8311 */     OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8312 */     _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/*  8313 */     _jspx_th_html_005foption_005f6.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*       */     
/*  8315 */     _jspx_th_html_005foption_005f6.setValue("JBOSS_HTTP401");
/*  8316 */     int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/*  8317 */     if (_jspx_eval_html_005foption_005f6 != 0) {
/*  8318 */       if (_jspx_eval_html_005foption_005f6 != 1) {
/*  8319 */         out = _jspx_page_context.pushBody();
/*  8320 */         _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/*  8321 */         _jspx_th_html_005foption_005f6.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8324 */         out.write(" 4.0.1 ");
/*  8325 */         int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/*  8326 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8329 */       if (_jspx_eval_html_005foption_005f6 != 1) {
/*  8330 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8333 */     if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/*  8334 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/*  8335 */       return true;
/*       */     }
/*  8337 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/*  8338 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f8(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8343 */     PageContext pageContext = _jspx_page_context;
/*  8344 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8346 */     OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8347 */     _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/*  8348 */     _jspx_th_html_005foption_005f8.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*       */     
/*  8350 */     _jspx_th_html_005foption_005f8.setValue("JBOSS_HTTP402");
/*  8351 */     int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/*  8352 */     if (_jspx_eval_html_005foption_005f8 != 0) {
/*  8353 */       if (_jspx_eval_html_005foption_005f8 != 1) {
/*  8354 */         out = _jspx_page_context.pushBody();
/*  8355 */         _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/*  8356 */         _jspx_th_html_005foption_005f8.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8359 */         out.write(" 5.x ");
/*  8360 */         int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/*  8361 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8364 */       if (_jspx_eval_html_005foption_005f8 != 1) {
/*  8365 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8368 */     if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/*  8369 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8);
/*  8370 */       return true;
/*       */     }
/*  8372 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8);
/*  8373 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f9(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8378 */     PageContext pageContext = _jspx_page_context;
/*  8379 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8381 */     OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8382 */     _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/*  8383 */     _jspx_th_html_005foption_005f9.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*       */     
/*  8385 */     _jspx_th_html_005foption_005f9.setValue("JBOSS_HTTP60");
/*  8386 */     int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/*  8387 */     if (_jspx_eval_html_005foption_005f9 != 0) {
/*  8388 */       if (_jspx_eval_html_005foption_005f9 != 1) {
/*  8389 */         out = _jspx_page_context.pushBody();
/*  8390 */         _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/*  8391 */         _jspx_th_html_005foption_005f9.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8394 */         out.write(" 6.x ");
/*  8395 */         int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/*  8396 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8399 */       if (_jspx_eval_html_005foption_005f9 != 1) {
/*  8400 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8403 */     if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/*  8404 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9);
/*  8405 */       return true;
/*       */     }
/*  8407 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9);
/*  8408 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f10(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8413 */     PageContext pageContext = _jspx_page_context;
/*  8414 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8416 */     OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8417 */     _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/*  8418 */     _jspx_th_html_005foption_005f10.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*       */     
/*  8420 */     _jspx_th_html_005foption_005f10.setValue("JBOSS_HTTP7");
/*  8421 */     int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/*  8422 */     if (_jspx_eval_html_005foption_005f10 != 0) {
/*  8423 */       if (_jspx_eval_html_005foption_005f10 != 1) {
/*  8424 */         out = _jspx_page_context.pushBody();
/*  8425 */         _jspx_th_html_005foption_005f10.setBodyContent((BodyContent)out);
/*  8426 */         _jspx_th_html_005foption_005f10.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8429 */         out.write(" 7.x ");
/*  8430 */         int evalDoAfterBody = _jspx_th_html_005foption_005f10.doAfterBody();
/*  8431 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8434 */       if (_jspx_eval_html_005foption_005f10 != 1) {
/*  8435 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8438 */     if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/*  8439 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10);
/*  8440 */       return true;
/*       */     }
/*  8442 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10);
/*  8443 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8448 */     PageContext pageContext = _jspx_page_context;
/*  8449 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8451 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonblur_005fnobody.get(TextTag.class);
/*  8452 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/*  8453 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  8455 */     _jspx_th_html_005ftext_005f3.setProperty("port");
/*       */     
/*  8457 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext small");
/*       */     
/*  8459 */     _jspx_th_html_005ftext_005f3.setOnblur("javascript:testService();");
/*  8460 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/*  8461 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/*  8462 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonblur_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/*  8463 */       return true;
/*       */     }
/*  8465 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonblur_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/*  8466 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8471 */     PageContext pageContext = _jspx_page_context;
/*  8472 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8474 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  8475 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/*  8476 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  8478 */     _jspx_th_html_005ftext_005f4.setProperty("port");
/*       */     
/*  8480 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext small");
/*  8481 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/*  8482 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/*  8483 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/*  8484 */       return true;
/*       */     }
/*  8486 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/*  8487 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fcheckbox_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8492 */     PageContext pageContext = _jspx_page_context;
/*  8493 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8495 */     CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/*  8496 */     _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
/*  8497 */     _jspx_th_html_005fcheckbox_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  8499 */     _jspx_th_html_005fcheckbox_005f1.setProperty("sslenabled");
/*       */     
/*  8501 */     _jspx_th_html_005fcheckbox_005f1.setOnclick("changePort()");
/*  8502 */     int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
/*  8503 */     if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == 5) {
/*  8504 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/*  8505 */       return true;
/*       */     }
/*  8507 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/*  8508 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fcheckbox_005f2(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8513 */     PageContext pageContext = _jspx_page_context;
/*  8514 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8516 */     CheckboxTag _jspx_th_html_005fcheckbox_005f2 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/*  8517 */     _jspx_th_html_005fcheckbox_005f2.setPageContext(_jspx_page_context);
/*  8518 */     _jspx_th_html_005fcheckbox_005f2.setParent((Tag)_jspx_th_c_005fif_005f9);
/*       */     
/*  8520 */     _jspx_th_html_005fcheckbox_005f2.setProperty("sslenabled");
/*  8521 */     int _jspx_eval_html_005fcheckbox_005f2 = _jspx_th_html_005fcheckbox_005f2.doStartTag();
/*  8522 */     if (_jspx_th_html_005fcheckbox_005f2.doEndTag() == 5) {
/*  8523 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/*  8524 */       return true;
/*       */     }
/*  8526 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/*  8527 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f12(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8532 */     PageContext pageContext = _jspx_page_context;
/*  8533 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8535 */     OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8536 */     _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/*  8537 */     _jspx_th_html_005foption_005f12.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*       */     
/*  8539 */     _jspx_th_html_005foption_005f12.setValue("WLS_6_1");
/*  8540 */     int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/*  8541 */     if (_jspx_eval_html_005foption_005f12 != 0) {
/*  8542 */       if (_jspx_eval_html_005foption_005f12 != 1) {
/*  8543 */         out = _jspx_page_context.pushBody();
/*  8544 */         _jspx_th_html_005foption_005f12.setBodyContent((BodyContent)out);
/*  8545 */         _jspx_th_html_005foption_005f12.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8548 */         out.write(54);
/*  8549 */         out.write(46);
/*  8550 */         out.write(49);
/*  8551 */         int evalDoAfterBody = _jspx_th_html_005foption_005f12.doAfterBody();
/*  8552 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8555 */       if (_jspx_eval_html_005foption_005f12 != 1) {
/*  8556 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8559 */     if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/*  8560 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12);
/*  8561 */       return true;
/*       */     }
/*  8563 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12);
/*  8564 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f13(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8569 */     PageContext pageContext = _jspx_page_context;
/*  8570 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8572 */     OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8573 */     _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/*  8574 */     _jspx_th_html_005foption_005f13.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*       */     
/*  8576 */     _jspx_th_html_005foption_005f13.setValue("WLS_7_0");
/*  8577 */     int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/*  8578 */     if (_jspx_eval_html_005foption_005f13 != 0) {
/*  8579 */       if (_jspx_eval_html_005foption_005f13 != 1) {
/*  8580 */         out = _jspx_page_context.pushBody();
/*  8581 */         _jspx_th_html_005foption_005f13.setBodyContent((BodyContent)out);
/*  8582 */         _jspx_th_html_005foption_005f13.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8585 */         out.write("\n            7.0");
/*  8586 */         int evalDoAfterBody = _jspx_th_html_005foption_005f13.doAfterBody();
/*  8587 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8590 */       if (_jspx_eval_html_005foption_005f13 != 1) {
/*  8591 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8594 */     if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/*  8595 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13);
/*  8596 */       return true;
/*       */     }
/*  8598 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13);
/*  8599 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f14(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8604 */     PageContext pageContext = _jspx_page_context;
/*  8605 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8607 */     OptionTag _jspx_th_html_005foption_005f14 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8608 */     _jspx_th_html_005foption_005f14.setPageContext(_jspx_page_context);
/*  8609 */     _jspx_th_html_005foption_005f14.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*       */     
/*  8611 */     _jspx_th_html_005foption_005f14.setValue("WLS_8_1");
/*  8612 */     int _jspx_eval_html_005foption_005f14 = _jspx_th_html_005foption_005f14.doStartTag();
/*  8613 */     if (_jspx_eval_html_005foption_005f14 != 0) {
/*  8614 */       if (_jspx_eval_html_005foption_005f14 != 1) {
/*  8615 */         out = _jspx_page_context.pushBody();
/*  8616 */         _jspx_th_html_005foption_005f14.setBodyContent((BodyContent)out);
/*  8617 */         _jspx_th_html_005foption_005f14.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8620 */         out.write("\n\t                       8.1");
/*  8621 */         int evalDoAfterBody = _jspx_th_html_005foption_005f14.doAfterBody();
/*  8622 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8625 */       if (_jspx_eval_html_005foption_005f14 != 1) {
/*  8626 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8629 */     if (_jspx_th_html_005foption_005f14.doEndTag() == 5) {
/*  8630 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14);
/*  8631 */       return true;
/*       */     }
/*  8633 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14);
/*  8634 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f15(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8639 */     PageContext pageContext = _jspx_page_context;
/*  8640 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8642 */     OptionTag _jspx_th_html_005foption_005f15 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8643 */     _jspx_th_html_005foption_005f15.setPageContext(_jspx_page_context);
/*  8644 */     _jspx_th_html_005foption_005f15.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*       */     
/*  8646 */     _jspx_th_html_005foption_005f15.setValue("WLS_9_0");
/*  8647 */     int _jspx_eval_html_005foption_005f15 = _jspx_th_html_005foption_005f15.doStartTag();
/*  8648 */     if (_jspx_eval_html_005foption_005f15 != 0) {
/*  8649 */       if (_jspx_eval_html_005foption_005f15 != 1) {
/*  8650 */         out = _jspx_page_context.pushBody();
/*  8651 */         _jspx_th_html_005foption_005f15.setBodyContent((BodyContent)out);
/*  8652 */         _jspx_th_html_005foption_005f15.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8655 */         out.write("\n\t                       9.x");
/*  8656 */         int evalDoAfterBody = _jspx_th_html_005foption_005f15.doAfterBody();
/*  8657 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8660 */       if (_jspx_eval_html_005foption_005f15 != 1) {
/*  8661 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8664 */     if (_jspx_th_html_005foption_005f15.doEndTag() == 5) {
/*  8665 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15);
/*  8666 */       return true;
/*       */     }
/*  8668 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15);
/*  8669 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f16(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8674 */     PageContext pageContext = _jspx_page_context;
/*  8675 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8677 */     OptionTag _jspx_th_html_005foption_005f16 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8678 */     _jspx_th_html_005foption_005f16.setPageContext(_jspx_page_context);
/*  8679 */     _jspx_th_html_005foption_005f16.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*       */     
/*  8681 */     _jspx_th_html_005foption_005f16.setValue("WLS_10_0");
/*  8682 */     int _jspx_eval_html_005foption_005f16 = _jspx_th_html_005foption_005f16.doStartTag();
/*  8683 */     if (_jspx_eval_html_005foption_005f16 != 0) {
/*  8684 */       if (_jspx_eval_html_005foption_005f16 != 1) {
/*  8685 */         out = _jspx_page_context.pushBody();
/*  8686 */         _jspx_th_html_005foption_005f16.setBodyContent((BodyContent)out);
/*  8687 */         _jspx_th_html_005foption_005f16.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8690 */         out.write("\n\t                       10.x (11g), 12c");
/*  8691 */         int evalDoAfterBody = _jspx_th_html_005foption_005f16.doAfterBody();
/*  8692 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8695 */       if (_jspx_eval_html_005foption_005f16 != 1) {
/*  8696 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8699 */     if (_jspx_th_html_005foption_005f16.doEndTag() == 5) {
/*  8700 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16);
/*  8701 */       return true;
/*       */     }
/*  8703 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16);
/*  8704 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8709 */     PageContext pageContext = _jspx_page_context;
/*  8710 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8712 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  8713 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/*  8714 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_c_005fif_005f11);
/*       */     
/*  8716 */     _jspx_th_html_005fselect_005f3.setProperty("version");
/*       */     
/*  8718 */     _jspx_th_html_005fselect_005f3.setStyleClass("formtext medium");
/*       */     
/*  8720 */     _jspx_th_html_005fselect_005f3.setOnchange("onComboChange1()");
/*  8721 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/*  8722 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/*  8723 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/*  8724 */         out = _jspx_page_context.pushBody();
/*  8725 */         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/*  8726 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8729 */         out.write("\n                   ");
/*  8730 */         if (_jspx_meth_html_005foption_005f17(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/*  8731 */           return true;
/*  8732 */         out.write("\n\t           ");
/*  8733 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/*  8734 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8737 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/*  8738 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8741 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/*  8742 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3);
/*  8743 */       return true;
/*       */     }
/*  8745 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3);
/*  8746 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f17(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8751 */     PageContext pageContext = _jspx_page_context;
/*  8752 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8754 */     OptionTag _jspx_th_html_005foption_005f17 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8755 */     _jspx_th_html_005foption_005f17.setPageContext(_jspx_page_context);
/*  8756 */     _jspx_th_html_005foption_005f17.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*       */     
/*  8758 */     _jspx_th_html_005foption_005f17.setValue("WLS_8_1");
/*  8759 */     int _jspx_eval_html_005foption_005f17 = _jspx_th_html_005foption_005f17.doStartTag();
/*  8760 */     if (_jspx_eval_html_005foption_005f17 != 0) {
/*  8761 */       if (_jspx_eval_html_005foption_005f17 != 1) {
/*  8762 */         out = _jspx_page_context.pushBody();
/*  8763 */         _jspx_th_html_005foption_005f17.setBodyContent((BodyContent)out);
/*  8764 */         _jspx_th_html_005foption_005f17.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8767 */         out.write("\n                               8.x");
/*  8768 */         int evalDoAfterBody = _jspx_th_html_005foption_005f17.doAfterBody();
/*  8769 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8772 */       if (_jspx_eval_html_005foption_005f17 != 1) {
/*  8773 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8776 */     if (_jspx_th_html_005foption_005f17.doEndTag() == 5) {
/*  8777 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17);
/*  8778 */       return true;
/*       */     }
/*  8780 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17);
/*  8781 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fcheckbox_005f3(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8786 */     PageContext pageContext = _jspx_page_context;
/*  8787 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8789 */     CheckboxTag _jspx_th_html_005fcheckbox_005f3 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/*  8790 */     _jspx_th_html_005fcheckbox_005f3.setPageContext(_jspx_page_context);
/*  8791 */     _jspx_th_html_005fcheckbox_005f3.setParent((Tag)_jspx_th_c_005fif_005f12);
/*       */     
/*  8793 */     _jspx_th_html_005fcheckbox_005f3.setProperty("sslenabled");
/*  8794 */     int _jspx_eval_html_005fcheckbox_005f3 = _jspx_th_html_005fcheckbox_005f3.doStartTag();
/*  8795 */     if (_jspx_th_html_005fcheckbox_005f3.doEndTag() == 5) {
/*  8796 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/*  8797 */       return true;
/*       */     }
/*  8799 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/*  8800 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f18(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8805 */     PageContext pageContext = _jspx_page_context;
/*  8806 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8808 */     OptionTag _jspx_th_html_005foption_005f18 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8809 */     _jspx_th_html_005foption_005f18.setPageContext(_jspx_page_context);
/*  8810 */     _jspx_th_html_005foption_005f18.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*       */     
/*  8812 */     _jspx_th_html_005foption_005f18.setValue("3");
/*  8813 */     int _jspx_eval_html_005foption_005f18 = _jspx_th_html_005foption_005f18.doStartTag();
/*  8814 */     if (_jspx_eval_html_005foption_005f18 != 0) {
/*  8815 */       if (_jspx_eval_html_005foption_005f18 != 1) {
/*  8816 */         out = _jspx_page_context.pushBody();
/*  8817 */         _jspx_th_html_005foption_005f18.setBodyContent((BodyContent)out);
/*  8818 */         _jspx_th_html_005foption_005f18.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8821 */         out.write(" 3.x ");
/*  8822 */         int evalDoAfterBody = _jspx_th_html_005foption_005f18.doAfterBody();
/*  8823 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8826 */       if (_jspx_eval_html_005foption_005f18 != 1) {
/*  8827 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8830 */     if (_jspx_th_html_005foption_005f18.doEndTag() == 5) {
/*  8831 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18);
/*  8832 */       return true;
/*       */     }
/*  8834 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18);
/*  8835 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f19(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8840 */     PageContext pageContext = _jspx_page_context;
/*  8841 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8843 */     OptionTag _jspx_th_html_005foption_005f19 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8844 */     _jspx_th_html_005foption_005f19.setPageContext(_jspx_page_context);
/*  8845 */     _jspx_th_html_005foption_005f19.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*       */     
/*  8847 */     _jspx_th_html_005foption_005f19.setValue("4");
/*  8848 */     int _jspx_eval_html_005foption_005f19 = _jspx_th_html_005foption_005f19.doStartTag();
/*  8849 */     if (_jspx_eval_html_005foption_005f19 != 0) {
/*  8850 */       if (_jspx_eval_html_005foption_005f19 != 1) {
/*  8851 */         out = _jspx_page_context.pushBody();
/*  8852 */         _jspx_th_html_005foption_005f19.setBodyContent((BodyContent)out);
/*  8853 */         _jspx_th_html_005foption_005f19.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8856 */         out.write("4.x ");
/*  8857 */         int evalDoAfterBody = _jspx_th_html_005foption_005f19.doAfterBody();
/*  8858 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8861 */       if (_jspx_eval_html_005foption_005f19 != 1) {
/*  8862 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8865 */     if (_jspx_th_html_005foption_005f19.doEndTag() == 5) {
/*  8866 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19);
/*  8867 */       return true;
/*       */     }
/*  8869 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19);
/*  8870 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f20(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8875 */     PageContext pageContext = _jspx_page_context;
/*  8876 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8878 */     OptionTag _jspx_th_html_005foption_005f20 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8879 */     _jspx_th_html_005foption_005f20.setPageContext(_jspx_page_context);
/*  8880 */     _jspx_th_html_005foption_005f20.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*       */     
/*  8882 */     _jspx_th_html_005foption_005f20.setValue("5");
/*  8883 */     int _jspx_eval_html_005foption_005f20 = _jspx_th_html_005foption_005f20.doStartTag();
/*  8884 */     if (_jspx_eval_html_005foption_005f20 != 0) {
/*  8885 */       if (_jspx_eval_html_005foption_005f20 != 1) {
/*  8886 */         out = _jspx_page_context.pushBody();
/*  8887 */         _jspx_th_html_005foption_005f20.setBodyContent((BodyContent)out);
/*  8888 */         _jspx_th_html_005foption_005f20.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8891 */         out.write(" 5.x ");
/*  8892 */         int evalDoAfterBody = _jspx_th_html_005foption_005f20.doAfterBody();
/*  8893 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8896 */       if (_jspx_eval_html_005foption_005f20 != 1) {
/*  8897 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8900 */     if (_jspx_th_html_005foption_005f20.doEndTag() == 5) {
/*  8901 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20);
/*  8902 */       return true;
/*       */     }
/*  8904 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20);
/*  8905 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f21(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8910 */     PageContext pageContext = _jspx_page_context;
/*  8911 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8913 */     OptionTag _jspx_th_html_005foption_005f21 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8914 */     _jspx_th_html_005foption_005f21.setPageContext(_jspx_page_context);
/*  8915 */     _jspx_th_html_005foption_005f21.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*       */     
/*  8917 */     _jspx_th_html_005foption_005f21.setValue("6");
/*  8918 */     int _jspx_eval_html_005foption_005f21 = _jspx_th_html_005foption_005f21.doStartTag();
/*  8919 */     if (_jspx_eval_html_005foption_005f21 != 0) {
/*  8920 */       if (_jspx_eval_html_005foption_005f21 != 1) {
/*  8921 */         out = _jspx_page_context.pushBody();
/*  8922 */         _jspx_th_html_005foption_005f21.setBodyContent((BodyContent)out);
/*  8923 */         _jspx_th_html_005foption_005f21.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8926 */         out.write(" 6.x ");
/*  8927 */         int evalDoAfterBody = _jspx_th_html_005foption_005f21.doAfterBody();
/*  8928 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8931 */       if (_jspx_eval_html_005foption_005f21 != 1) {
/*  8932 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8935 */     if (_jspx_th_html_005foption_005f21.doEndTag() == 5) {
/*  8936 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21);
/*  8937 */       return true;
/*       */     }
/*  8939 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21);
/*  8940 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f22(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8945 */     PageContext pageContext = _jspx_page_context;
/*  8946 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8948 */     OptionTag _jspx_th_html_005foption_005f22 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  8949 */     _jspx_th_html_005foption_005f22.setPageContext(_jspx_page_context);
/*  8950 */     _jspx_th_html_005foption_005f22.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*       */     
/*  8952 */     _jspx_th_html_005foption_005f22.setValue("7");
/*  8953 */     int _jspx_eval_html_005foption_005f22 = _jspx_th_html_005foption_005f22.doStartTag();
/*  8954 */     if (_jspx_eval_html_005foption_005f22 != 0) {
/*  8955 */       if (_jspx_eval_html_005foption_005f22 != 1) {
/*  8956 */         out = _jspx_page_context.pushBody();
/*  8957 */         _jspx_th_html_005foption_005f22.setBodyContent((BodyContent)out);
/*  8958 */         _jspx_th_html_005foption_005f22.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  8961 */         out.write(" 7.x ");
/*  8962 */         int evalDoAfterBody = _jspx_th_html_005foption_005f22.doAfterBody();
/*  8963 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  8966 */       if (_jspx_eval_html_005foption_005f22 != 1) {
/*  8967 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  8970 */     if (_jspx_th_html_005foption_005f22.doEndTag() == 5) {
/*  8971 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22);
/*  8972 */       return true;
/*       */     }
/*  8974 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22);
/*  8975 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  8980 */     PageContext pageContext = _jspx_page_context;
/*  8981 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  8983 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  8984 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/*  8985 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  8987 */     _jspx_th_html_005ftext_005f5.setProperty("port");
/*       */     
/*  8989 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext small");
/*  8990 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/*  8991 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/*  8992 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/*  8993 */       return true;
/*       */     }
/*  8995 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/*  8996 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9001 */     PageContext pageContext = _jspx_page_context;
/*  9002 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9004 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeyup_005fnobody.get(TextTag.class);
/*  9005 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/*  9006 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9008 */     _jspx_th_html_005ftext_005f6.setProperty("emailid");
/*       */     
/*  9010 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext default");
/*       */     
/*  9012 */     _jspx_th_html_005ftext_005f6.setOnkeyup("javascript:changeUserName()");
/*  9013 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/*  9014 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/*  9015 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeyup_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/*  9016 */       return true;
/*       */     }
/*  9018 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fonkeyup_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/*  9019 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fcheckbox_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9024 */     PageContext pageContext = _jspx_page_context;
/*  9025 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9027 */     CheckboxTag _jspx_th_html_005fcheckbox_005f4 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/*  9028 */     _jspx_th_html_005fcheckbox_005f4.setPageContext(_jspx_page_context);
/*  9029 */     _jspx_th_html_005fcheckbox_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9031 */     _jspx_th_html_005fcheckbox_005f4.setProperty("smtpauth");
/*       */     
/*  9033 */     _jspx_th_html_005fcheckbox_005f4.setValue("true");
/*       */     
/*  9035 */     _jspx_th_html_005fcheckbox_005f4.setOnclick("javascript:showSMTP()");
/*  9036 */     int _jspx_eval_html_005fcheckbox_005f4 = _jspx_th_html_005fcheckbox_005f4.doStartTag();
/*  9037 */     if (_jspx_th_html_005fcheckbox_005f4.doEndTag() == 5) {
/*  9038 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f4);
/*  9039 */       return true;
/*       */     }
/*  9041 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f4);
/*  9042 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9047 */     PageContext pageContext = _jspx_page_context;
/*  9048 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9050 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  9051 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/*  9052 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9054 */     _jspx_th_c_005fif_005f13.setTest("${!AMActionForm.smtpauth}");
/*  9055 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/*  9056 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*       */       for (;;) {
/*  9058 */         out.write("style=\"DISPLAY: none\"");
/*  9059 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/*  9060 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*       */     }
/*  9064 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/*  9065 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/*  9066 */       return true;
/*       */     }
/*  9068 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/*  9069 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9074 */     PageContext pageContext = _jspx_page_context;
/*  9075 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9077 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  9078 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/*  9079 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9081 */     _jspx_th_html_005ftext_005f7.setProperty("smtpUserName");
/*       */     
/*  9083 */     _jspx_th_html_005ftext_005f7.setStyleClass("formtext");
/*  9084 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/*  9085 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/*  9086 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/*  9087 */       return true;
/*       */     }
/*  9089 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/*  9090 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9095 */     PageContext pageContext = _jspx_page_context;
/*  9096 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9098 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/*  9099 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/*  9100 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9102 */     _jspx_th_html_005fpassword_005f0.setProperty("smtpPassword");
/*       */     
/*  9104 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/*  9105 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/*  9106 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/*  9107 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/*  9108 */       return true;
/*       */     }
/*  9110 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/*  9111 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fcheckbox_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9116 */     PageContext pageContext = _jspx_page_context;
/*  9117 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9119 */     CheckboxTag _jspx_th_html_005fcheckbox_005f5 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/*  9120 */     _jspx_th_html_005fcheckbox_005f5.setPageContext(_jspx_page_context);
/*  9121 */     _jspx_th_html_005fcheckbox_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9123 */     _jspx_th_html_005fcheckbox_005f5.setProperty("popenabled");
/*       */     
/*  9125 */     _jspx_th_html_005fcheckbox_005f5.setOnclick("javascript:showPOP()");
/*  9126 */     int _jspx_eval_html_005fcheckbox_005f5 = _jspx_th_html_005fcheckbox_005f5.doStartTag();
/*  9127 */     if (_jspx_th_html_005fcheckbox_005f5.doEndTag() == 5) {
/*  9128 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f5);
/*  9129 */       return true;
/*       */     }
/*  9131 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f5);
/*  9132 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fif_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9137 */     PageContext pageContext = _jspx_page_context;
/*  9138 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9140 */     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  9141 */     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/*  9142 */     _jspx_th_c_005fif_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9144 */     _jspx_th_c_005fif_005f14.setTest("${!AMActionForm.popenabled}");
/*  9145 */     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/*  9146 */     if (_jspx_eval_c_005fif_005f14 != 0) {
/*       */       for (;;) {
/*  9148 */         out.write("style=\"DISPLAY: none\"");
/*  9149 */         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/*  9150 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*       */     }
/*  9154 */     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/*  9155 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/*  9156 */       return true;
/*       */     }
/*  9158 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/*  9159 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9164 */     PageContext pageContext = _jspx_page_context;
/*  9165 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9167 */     TextTag _jspx_th_html_005ftext_005f8 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  9168 */     _jspx_th_html_005ftext_005f8.setPageContext(_jspx_page_context);
/*  9169 */     _jspx_th_html_005ftext_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9171 */     _jspx_th_html_005ftext_005f8.setProperty("popHost");
/*       */     
/*  9173 */     _jspx_th_html_005ftext_005f8.setStyleClass("formtext small");
/*  9174 */     int _jspx_eval_html_005ftext_005f8 = _jspx_th_html_005ftext_005f8.doStartTag();
/*  9175 */     if (_jspx_th_html_005ftext_005f8.doEndTag() == 5) {
/*  9176 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/*  9177 */       return true;
/*       */     }
/*  9179 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/*  9180 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9185 */     PageContext pageContext = _jspx_page_context;
/*  9186 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9188 */     TextTag _jspx_th_html_005ftext_005f9 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  9189 */     _jspx_th_html_005ftext_005f9.setPageContext(_jspx_page_context);
/*  9190 */     _jspx_th_html_005ftext_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9192 */     _jspx_th_html_005ftext_005f9.setProperty("popPort");
/*       */     
/*  9194 */     _jspx_th_html_005ftext_005f9.setStyleClass("formtext small");
/*  9195 */     int _jspx_eval_html_005ftext_005f9 = _jspx_th_html_005ftext_005f9.doStartTag();
/*  9196 */     if (_jspx_th_html_005ftext_005f9.doEndTag() == 5) {
/*  9197 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/*  9198 */       return true;
/*       */     }
/*  9200 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/*  9201 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9206 */     PageContext pageContext = _jspx_page_context;
/*  9207 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9209 */     TextTag _jspx_th_html_005ftext_005f10 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  9210 */     _jspx_th_html_005ftext_005f10.setPageContext(_jspx_page_context);
/*  9211 */     _jspx_th_html_005ftext_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9213 */     _jspx_th_html_005ftext_005f10.setProperty("username");
/*       */     
/*  9215 */     _jspx_th_html_005ftext_005f10.setStyleClass("formtext default");
/*  9216 */     int _jspx_eval_html_005ftext_005f10 = _jspx_th_html_005ftext_005f10.doStartTag();
/*  9217 */     if (_jspx_th_html_005ftext_005f10.doEndTag() == 5) {
/*  9218 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/*  9219 */       return true;
/*       */     }
/*  9221 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/*  9222 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fpassword_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9227 */     PageContext pageContext = _jspx_page_context;
/*  9228 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9230 */     PasswordTag _jspx_th_html_005fpassword_005f1 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/*  9231 */     _jspx_th_html_005fpassword_005f1.setPageContext(_jspx_page_context);
/*  9232 */     _jspx_th_html_005fpassword_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9234 */     _jspx_th_html_005fpassword_005f1.setProperty("password");
/*       */     
/*  9236 */     _jspx_th_html_005fpassword_005f1.setStyleClass("formtext default");
/*  9237 */     int _jspx_eval_html_005fpassword_005f1 = _jspx_th_html_005fpassword_005f1.doStartTag();
/*  9238 */     if (_jspx_th_html_005fpassword_005f1.doEndTag() == 5) {
/*  9239 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
/*  9240 */       return true;
/*       */     }
/*  9242 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
/*  9243 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9248 */     PageContext pageContext = _jspx_page_context;
/*  9249 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9251 */     TextTag _jspx_th_html_005ftext_005f11 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/*  9252 */     _jspx_th_html_005ftext_005f11.setPageContext(_jspx_page_context);
/*  9253 */     _jspx_th_html_005ftext_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9255 */     _jspx_th_html_005ftext_005f11.setProperty("mailMsg");
/*       */     
/*  9257 */     _jspx_th_html_005ftext_005f11.setStyleClass("formtext formtext-custom-mailMsg");
/*       */     
/*  9259 */     _jspx_th_html_005ftext_005f11.setSize("50");
/*  9260 */     int _jspx_eval_html_005ftext_005f11 = _jspx_th_html_005ftext_005f11.doStartTag();
/*  9261 */     if (_jspx_th_html_005ftext_005f11.doEndTag() == 5) {
/*  9262 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/*  9263 */       return true;
/*       */     }
/*  9265 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/*  9266 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9271 */     PageContext pageContext = _jspx_page_context;
/*  9272 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9274 */     TextTag _jspx_th_html_005ftext_005f12 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  9275 */     _jspx_th_html_005ftext_005f12.setPageContext(_jspx_page_context);
/*  9276 */     _jspx_th_html_005ftext_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9278 */     _jspx_th_html_005ftext_005f12.setProperty("serverpath");
/*       */     
/*  9280 */     _jspx_th_html_005ftext_005f12.setValue("/phpstats.php");
/*       */     
/*  9282 */     _jspx_th_html_005ftext_005f12.setStyleClass("formtext default");
/*  9283 */     int _jspx_eval_html_005ftext_005f12 = _jspx_th_html_005ftext_005f12.doStartTag();
/*  9284 */     if (_jspx_th_html_005ftext_005f12.doEndTag() == 5) {
/*  9285 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f12);
/*  9286 */       return true;
/*       */     }
/*  9288 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f12);
/*  9289 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fcheckbox_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9294 */     PageContext pageContext = _jspx_page_context;
/*  9295 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9297 */     CheckboxTag _jspx_th_html_005fcheckbox_005f6 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/*  9298 */     _jspx_th_html_005fcheckbox_005f6.setPageContext(_jspx_page_context);
/*  9299 */     _jspx_th_html_005fcheckbox_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9301 */     _jspx_th_html_005fcheckbox_005f6.setProperty("usedRouterString");
/*       */     
/*  9303 */     _jspx_th_html_005fcheckbox_005f6.setValue("true");
/*       */     
/*  9305 */     _jspx_th_html_005fcheckbox_005f6.setOnclick("javascript:showSAPRouterString(this);");
/*  9306 */     int _jspx_eval_html_005fcheckbox_005f6 = _jspx_th_html_005fcheckbox_005f6.doStartTag();
/*  9307 */     if (_jspx_th_html_005fcheckbox_005f6.doEndTag() == 5) {
/*  9308 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f6);
/*  9309 */       return true;
/*       */     }
/*  9311 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f6);
/*  9312 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9317 */     PageContext pageContext = _jspx_page_context;
/*  9318 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9320 */     TextTag _jspx_th_html_005ftext_005f13 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  9321 */     _jspx_th_html_005ftext_005f13.setPageContext(_jspx_page_context);
/*  9322 */     _jspx_th_html_005ftext_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9324 */     _jspx_th_html_005ftext_005f13.setProperty("routerString");
/*       */     
/*  9326 */     _jspx_th_html_005ftext_005f13.setValue("");
/*       */     
/*  9328 */     _jspx_th_html_005ftext_005f13.setStyleClass("formtext default");
/*  9329 */     int _jspx_eval_html_005ftext_005f13 = _jspx_th_html_005ftext_005f13.doStartTag();
/*  9330 */     if (_jspx_th_html_005ftext_005f13.doEndTag() == 5) {
/*  9331 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f13);
/*  9332 */       return true;
/*       */     }
/*  9334 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f13);
/*  9335 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9340 */     PageContext pageContext = _jspx_page_context;
/*  9341 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9343 */     TextTag _jspx_th_html_005ftext_005f14 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  9344 */     _jspx_th_html_005ftext_005f14.setPageContext(_jspx_page_context);
/*  9345 */     _jspx_th_html_005ftext_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9347 */     _jspx_th_html_005ftext_005f14.setProperty("logonClient");
/*       */     
/*  9349 */     _jspx_th_html_005ftext_005f14.setValue("000");
/*       */     
/*  9351 */     _jspx_th_html_005ftext_005f14.setStyleClass("formtext default");
/*  9352 */     int _jspx_eval_html_005ftext_005f14 = _jspx_th_html_005ftext_005f14.doStartTag();
/*  9353 */     if (_jspx_th_html_005ftext_005f14.doEndTag() == 5) {
/*  9354 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f14);
/*  9355 */       return true;
/*       */     }
/*  9357 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f14);
/*  9358 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f15(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9363 */     PageContext pageContext = _jspx_page_context;
/*  9364 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9366 */     TextTag _jspx_th_html_005ftext_005f15 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  9367 */     _jspx_th_html_005ftext_005f15.setPageContext(_jspx_page_context);
/*  9368 */     _jspx_th_html_005ftext_005f15.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9370 */     _jspx_th_html_005ftext_005f15.setProperty("port");
/*       */     
/*  9372 */     _jspx_th_html_005ftext_005f15.setValue("00");
/*       */     
/*  9374 */     _jspx_th_html_005ftext_005f15.setStyleClass("formtext default");
/*  9375 */     int _jspx_eval_html_005ftext_005f15 = _jspx_th_html_005ftext_005f15.doStartTag();
/*  9376 */     if (_jspx_th_html_005ftext_005f15.doEndTag() == 5) {
/*  9377 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f15);
/*  9378 */       return true;
/*       */     }
/*  9380 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f15);
/*  9381 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9386 */     PageContext pageContext = _jspx_page_context;
/*  9387 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9389 */     TextTag _jspx_th_html_005ftext_005f16 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  9390 */     _jspx_th_html_005ftext_005f16.setPageContext(_jspx_page_context);
/*  9391 */     _jspx_th_html_005ftext_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9393 */     _jspx_th_html_005ftext_005f16.setProperty("language");
/*       */     
/*  9395 */     _jspx_th_html_005ftext_005f16.setValue("EN");
/*       */     
/*  9397 */     _jspx_th_html_005ftext_005f16.setStyleClass("formtext default");
/*  9398 */     int _jspx_eval_html_005ftext_005f16 = _jspx_th_html_005ftext_005f16.doStartTag();
/*  9399 */     if (_jspx_th_html_005ftext_005f16.doEndTag() == 5) {
/*  9400 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f16);
/*  9401 */       return true;
/*       */     }
/*  9403 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f16);
/*  9404 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f17(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9409 */     PageContext pageContext = _jspx_page_context;
/*  9410 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9412 */     TextTag _jspx_th_html_005ftext_005f17 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  9413 */     _jspx_th_html_005ftext_005f17.setPageContext(_jspx_page_context);
/*  9414 */     _jspx_th_html_005ftext_005f17.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9416 */     _jspx_th_html_005ftext_005f17.setProperty("pollInterval");
/*       */     
/*  9418 */     _jspx_th_html_005ftext_005f17.setStyleClass("formtext small");
/*  9419 */     int _jspx_eval_html_005ftext_005f17 = _jspx_th_html_005ftext_005f17.doStartTag();
/*  9420 */     if (_jspx_th_html_005ftext_005f17.doEndTag() == 5) {
/*  9421 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f17);
/*  9422 */       return true;
/*       */     }
/*  9424 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f17);
/*  9425 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f18(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9430 */     PageContext pageContext = _jspx_page_context;
/*  9431 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9433 */     TextTag _jspx_th_html_005ftext_005f18 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  9434 */     _jspx_th_html_005ftext_005f18.setPageContext(_jspx_page_context);
/*  9435 */     _jspx_th_html_005ftext_005f18.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9437 */     _jspx_th_html_005ftext_005f18.setProperty("snmptelnetport");
/*       */     
/*  9439 */     _jspx_th_html_005ftext_005f18.setStyleClass("formtext small");
/*       */     
/*  9441 */     _jspx_th_html_005ftext_005f18.setValue("13");
/*  9442 */     int _jspx_eval_html_005ftext_005f18 = _jspx_th_html_005ftext_005f18.doStartTag();
/*  9443 */     if (_jspx_th_html_005ftext_005f18.doEndTag() == 5) {
/*  9444 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f18);
/*  9445 */       return true;
/*       */     }
/*  9447 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f18);
/*  9448 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fcheckbox_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9453 */     PageContext pageContext = _jspx_page_context;
/*  9454 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9456 */     CheckboxTag _jspx_th_html_005fcheckbox_005f7 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/*  9457 */     _jspx_th_html_005fcheckbox_005f7.setPageContext(_jspx_page_context);
/*  9458 */     _jspx_th_html_005fcheckbox_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9460 */     _jspx_th_html_005fcheckbox_005f7.setProperty("eventlog_status");
/*  9461 */     int _jspx_eval_html_005fcheckbox_005f7 = _jspx_th_html_005fcheckbox_005f7.doStartTag();
/*  9462 */     if (_jspx_th_html_005fcheckbox_005f7.doEndTag() == 5) {
/*  9463 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f7);
/*  9464 */       return true;
/*       */     }
/*  9466 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f7);
/*  9467 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f19(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9472 */     PageContext pageContext = _jspx_page_context;
/*  9473 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9475 */     TextTag _jspx_th_html_005ftext_005f19 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  9476 */     _jspx_th_html_005ftext_005f19.setPageContext(_jspx_page_context);
/*  9477 */     _jspx_th_html_005ftext_005f19.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9479 */     _jspx_th_html_005ftext_005f19.setProperty("snmpPort");
/*       */     
/*  9481 */     _jspx_th_html_005ftext_005f19.setStyleClass("formtext small");
/*  9482 */     int _jspx_eval_html_005ftext_005f19 = _jspx_th_html_005ftext_005f19.doStartTag();
/*  9483 */     if (_jspx_th_html_005ftext_005f19.doEndTag() == 5) {
/*  9484 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f19);
/*  9485 */       return true;
/*       */     }
/*  9487 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f19);
/*  9488 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fcheckbox_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9493 */     PageContext pageContext = _jspx_page_context;
/*  9494 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9496 */     CheckboxTag _jspx_th_html_005fcheckbox_005f8 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/*  9497 */     _jspx_th_html_005fcheckbox_005f8.setPageContext(_jspx_page_context);
/*  9498 */     _jspx_th_html_005fcheckbox_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9500 */     _jspx_th_html_005fcheckbox_005f8.setProperty("apacheauth");
/*       */     
/*  9502 */     _jspx_th_html_005fcheckbox_005f8.setValue("true");
/*       */     
/*  9504 */     _jspx_th_html_005fcheckbox_005f8.setOnclick("javascript:showApacheAuth()");
/*  9505 */     int _jspx_eval_html_005fcheckbox_005f8 = _jspx_th_html_005fcheckbox_005f8.doStartTag();
/*  9506 */     if (_jspx_th_html_005fcheckbox_005f8.doEndTag() == 5) {
/*  9507 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f8);
/*  9508 */       return true;
/*       */     }
/*  9510 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f8);
/*  9511 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9516 */     PageContext pageContext = _jspx_page_context;
/*  9517 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9519 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  9520 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/*  9521 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9523 */     _jspx_th_c_005fif_005f15.setTest("${!AMActionForm.apacheauth}");
/*  9524 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/*  9525 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*       */       for (;;) {
/*  9527 */         out.write("style=\"DISPLAY: none\"");
/*  9528 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/*  9529 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*       */     }
/*  9533 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/*  9534 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/*  9535 */       return true;
/*       */     }
/*  9537 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/*  9538 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f20(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9543 */     PageContext pageContext = _jspx_page_context;
/*  9544 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9546 */     TextTag _jspx_th_html_005ftext_005f20 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  9547 */     _jspx_th_html_005ftext_005f20.setPageContext(_jspx_page_context);
/*  9548 */     _jspx_th_html_005ftext_005f20.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9550 */     _jspx_th_html_005ftext_005f20.setProperty("apacheUserName");
/*       */     
/*  9552 */     _jspx_th_html_005ftext_005f20.setStyleClass("formtext");
/*  9553 */     int _jspx_eval_html_005ftext_005f20 = _jspx_th_html_005ftext_005f20.doStartTag();
/*  9554 */     if (_jspx_th_html_005ftext_005f20.doEndTag() == 5) {
/*  9555 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f20);
/*  9556 */       return true;
/*       */     }
/*  9558 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f20);
/*  9559 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fpassword_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9564 */     PageContext pageContext = _jspx_page_context;
/*  9565 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9567 */     PasswordTag _jspx_th_html_005fpassword_005f2 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/*  9568 */     _jspx_th_html_005fpassword_005f2.setPageContext(_jspx_page_context);
/*  9569 */     _jspx_th_html_005fpassword_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9571 */     _jspx_th_html_005fpassword_005f2.setProperty("apachepassword");
/*       */     
/*  9573 */     _jspx_th_html_005fpassword_005f2.setStyleClass("formtext");
/*  9574 */     int _jspx_eval_html_005fpassword_005f2 = _jspx_th_html_005fpassword_005f2.doStartTag();
/*  9575 */     if (_jspx_th_html_005fpassword_005f2.doEndTag() == 5) {
/*  9576 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f2);
/*  9577 */       return true;
/*       */     }
/*  9579 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f2);
/*  9580 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fcheckbox_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9585 */     PageContext pageContext = _jspx_page_context;
/*  9586 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9588 */     CheckboxTag _jspx_th_html_005fcheckbox_005f9 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/*  9589 */     _jspx_th_html_005fcheckbox_005f9.setPageContext(_jspx_page_context);
/*  9590 */     _jspx_th_html_005fcheckbox_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9592 */     _jspx_th_html_005fcheckbox_005f9.setProperty("serverstatusurl");
/*       */     
/*  9594 */     _jspx_th_html_005fcheckbox_005f9.setValue("true");
/*       */     
/*  9596 */     _jspx_th_html_005fcheckbox_005f9.setOnclick("javascript:showApacheStatus()");
/*  9597 */     int _jspx_eval_html_005fcheckbox_005f9 = _jspx_th_html_005fcheckbox_005f9.doStartTag();
/*  9598 */     if (_jspx_th_html_005fcheckbox_005f9.doEndTag() == 5) {
/*  9599 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f9);
/*  9600 */       return true;
/*       */     }
/*  9602 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f9);
/*  9603 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9608 */     PageContext pageContext = _jspx_page_context;
/*  9609 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9611 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  9612 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/*  9613 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9615 */     _jspx_th_c_005fif_005f16.setTest("${!AMActionForm.serverstatusurl}");
/*  9616 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/*  9617 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*       */       for (;;) {
/*  9619 */         out.write("style=\"DISPLAY: none\"");
/*  9620 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/*  9621 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*       */     }
/*  9625 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/*  9626 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/*  9627 */       return true;
/*       */     }
/*  9629 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/*  9630 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f21(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9635 */     PageContext pageContext = _jspx_page_context;
/*  9636 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9638 */     TextTag _jspx_th_html_005ftext_005f21 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  9639 */     _jspx_th_html_005ftext_005f21.setPageContext(_jspx_page_context);
/*  9640 */     _jspx_th_html_005ftext_005f21.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9642 */     _jspx_th_html_005ftext_005f21.setProperty("apacheurl");
/*       */     
/*  9644 */     _jspx_th_html_005ftext_005f21.setStyleClass("formtext xlarge");
/*  9645 */     int _jspx_eval_html_005ftext_005f21 = _jspx_th_html_005ftext_005f21.doStartTag();
/*  9646 */     if (_jspx_th_html_005ftext_005f21.doEndTag() == 5) {
/*  9647 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f21);
/*  9648 */       return true;
/*       */     }
/*  9650 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f21);
/*  9651 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f49(JspTag _jspx_th_html_005fselect_005f7, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9656 */     PageContext pageContext = _jspx_page_context;
/*  9657 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9659 */     OptionTag _jspx_th_html_005foption_005f49 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  9660 */     _jspx_th_html_005foption_005f49.setPageContext(_jspx_page_context);
/*  9661 */     _jspx_th_html_005foption_005f49.setParent((Tag)_jspx_th_html_005fselect_005f7);
/*       */     
/*  9663 */     _jspx_th_html_005foption_005f49.setValue("5.0");
/*  9664 */     int _jspx_eval_html_005foption_005f49 = _jspx_th_html_005foption_005f49.doStartTag();
/*  9665 */     if (_jspx_eval_html_005foption_005f49 != 0) {
/*  9666 */       if (_jspx_eval_html_005foption_005f49 != 1) {
/*  9667 */         out = _jspx_page_context.pushBody();
/*  9668 */         _jspx_th_html_005foption_005f49.setBodyContent((BodyContent)out);
/*  9669 */         _jspx_th_html_005foption_005f49.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  9672 */         out.write(" 5.x ");
/*  9673 */         int evalDoAfterBody = _jspx_th_html_005foption_005f49.doAfterBody();
/*  9674 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  9677 */       if (_jspx_eval_html_005foption_005f49 != 1) {
/*  9678 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  9681 */     if (_jspx_th_html_005foption_005f49.doEndTag() == 5) {
/*  9682 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49);
/*  9683 */       return true;
/*       */     }
/*  9685 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49);
/*  9686 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005foption_005f50(JspTag _jspx_th_html_005fselect_005f7, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9691 */     PageContext pageContext = _jspx_page_context;
/*  9692 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9694 */     OptionTag _jspx_th_html_005foption_005f50 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  9695 */     _jspx_th_html_005foption_005f50.setPageContext(_jspx_page_context);
/*  9696 */     _jspx_th_html_005foption_005f50.setParent((Tag)_jspx_th_html_005fselect_005f7);
/*       */     
/*  9698 */     _jspx_th_html_005foption_005f50.setValue("6");
/*  9699 */     int _jspx_eval_html_005foption_005f50 = _jspx_th_html_005foption_005f50.doStartTag();
/*  9700 */     if (_jspx_eval_html_005foption_005f50 != 0) {
/*  9701 */       if (_jspx_eval_html_005foption_005f50 != 1) {
/*  9702 */         out = _jspx_page_context.pushBody();
/*  9703 */         _jspx_th_html_005foption_005f50.setBodyContent((BodyContent)out);
/*  9704 */         _jspx_th_html_005foption_005f50.doInitBody();
/*       */       }
/*       */       for (;;) {
/*  9707 */         out.write(54);
/*  9708 */         out.write(46);
/*  9709 */         out.write(120);
/*  9710 */         int evalDoAfterBody = _jspx_th_html_005foption_005f50.doAfterBody();
/*  9711 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*  9714 */       if (_jspx_eval_html_005foption_005f50 != 1) {
/*  9715 */         out = _jspx_page_context.popBody();
/*       */       }
/*       */     }
/*  9718 */     if (_jspx_th_html_005foption_005f50.doEndTag() == 5) {
/*  9719 */       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50);
/*  9720 */       return true;
/*       */     }
/*  9722 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50);
/*  9723 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fcheckbox_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9728 */     PageContext pageContext = _jspx_page_context;
/*  9729 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9731 */     CheckboxTag _jspx_th_html_005fcheckbox_005f10 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/*  9732 */     _jspx_th_html_005fcheckbox_005f10.setPageContext(_jspx_page_context);
/*  9733 */     _jspx_th_html_005fcheckbox_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9735 */     _jspx_th_html_005fcheckbox_005f10.setProperty("sslenabled");
/*  9736 */     int _jspx_eval_html_005fcheckbox_005f10 = _jspx_th_html_005fcheckbox_005f10.doStartTag();
/*  9737 */     if (_jspx_th_html_005fcheckbox_005f10.doEndTag() == 5) {
/*  9738 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f10);
/*  9739 */       return true;
/*       */     }
/*  9741 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f10);
/*  9742 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fcheckbox_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9747 */     PageContext pageContext = _jspx_page_context;
/*  9748 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9750 */     CheckboxTag _jspx_th_html_005fcheckbox_005f11 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/*  9751 */     _jspx_th_html_005fcheckbox_005f11.setPageContext(_jspx_page_context);
/*  9752 */     _jspx_th_html_005fcheckbox_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9754 */     _jspx_th_html_005fcheckbox_005f11.setProperty("authEnabled");
/*       */     
/*  9756 */     _jspx_th_html_005fcheckbox_005f11.setOnclick("handleGlobalSecurity(this)");
/*  9757 */     int _jspx_eval_html_005fcheckbox_005f11 = _jspx_th_html_005fcheckbox_005f11.doStartTag();
/*  9758 */     if (_jspx_th_html_005fcheckbox_005f11.doEndTag() == 5) {
/*  9759 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f11);
/*  9760 */       return true;
/*       */     }
/*  9762 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f11);
/*  9763 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fif_005f17(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9768 */     PageContext pageContext = _jspx_page_context;
/*  9769 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9771 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  9772 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/*  9773 */     _jspx_th_c_005fif_005f17.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9775 */     _jspx_th_c_005fif_005f17.setTest("${!AMActionForm.authEnabled}");
/*  9776 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/*  9777 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*       */       for (;;) {
/*  9779 */         out.write("style=\"display:none\"");
/*  9780 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/*  9781 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*       */     }
/*  9785 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/*  9786 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/*  9787 */       return true;
/*       */     }
/*  9789 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/*  9790 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f22(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9795 */     PageContext pageContext = _jspx_page_context;
/*  9796 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9798 */     TextTag _jspx_th_html_005ftext_005f22 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/*  9799 */     _jspx_th_html_005ftext_005f22.setPageContext(_jspx_page_context);
/*  9800 */     _jspx_th_html_005ftext_005f22.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9802 */     _jspx_th_html_005ftext_005f22.setProperty("username");
/*       */     
/*  9804 */     _jspx_th_html_005ftext_005f22.setStyleClass("formtext");
/*       */     
/*  9806 */     _jspx_th_html_005ftext_005f22.setSize("15");
/*  9807 */     int _jspx_eval_html_005ftext_005f22 = _jspx_th_html_005ftext_005f22.doStartTag();
/*  9808 */     if (_jspx_th_html_005ftext_005f22.doEndTag() == 5) {
/*  9809 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f22);
/*  9810 */       return true;
/*       */     }
/*  9812 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f22);
/*  9813 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fif_005f18(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9818 */     PageContext pageContext = _jspx_page_context;
/*  9819 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9821 */     IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  9822 */     _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/*  9823 */     _jspx_th_c_005fif_005f18.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9825 */     _jspx_th_c_005fif_005f18.setTest("${!AMActionForm.authEnabled}");
/*  9826 */     int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/*  9827 */     if (_jspx_eval_c_005fif_005f18 != 0) {
/*       */       for (;;) {
/*  9829 */         out.write("style=\"display:none\"");
/*  9830 */         int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/*  9831 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*       */     }
/*  9835 */     if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/*  9836 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/*  9837 */       return true;
/*       */     }
/*  9839 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/*  9840 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fpassword_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9845 */     PageContext pageContext = _jspx_page_context;
/*  9846 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9848 */     PasswordTag _jspx_th_html_005fpassword_005f3 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/*  9849 */     _jspx_th_html_005fpassword_005f3.setPageContext(_jspx_page_context);
/*  9850 */     _jspx_th_html_005fpassword_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9852 */     _jspx_th_html_005fpassword_005f3.setProperty("password");
/*       */     
/*  9854 */     _jspx_th_html_005fpassword_005f3.setStyleClass("formtext");
/*       */     
/*  9856 */     _jspx_th_html_005fpassword_005f3.setSize("15");
/*  9857 */     int _jspx_eval_html_005fpassword_005f3 = _jspx_th_html_005fpassword_005f3.doStartTag();
/*  9858 */     if (_jspx_th_html_005fpassword_005f3.doEndTag() == 5) {
/*  9859 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f3);
/*  9860 */       return true;
/*       */     }
/*  9862 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f3);
/*  9863 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f23(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9868 */     PageContext pageContext = _jspx_page_context;
/*  9869 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9871 */     TextTag _jspx_th_html_005ftext_005f23 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  9872 */     _jspx_th_html_005ftext_005f23.setPageContext(_jspx_page_context);
/*  9873 */     _jspx_th_html_005ftext_005f23.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9875 */     _jspx_th_html_005ftext_005f23.setProperty("jndiurl");
/*       */     
/*  9877 */     _jspx_th_html_005ftext_005f23.setStyleClass("formtext default");
/*  9878 */     int _jspx_eval_html_005ftext_005f23 = _jspx_th_html_005ftext_005f23.doStartTag();
/*  9879 */     if (_jspx_th_html_005ftext_005f23.doEndTag() == 5) {
/*  9880 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f23);
/*  9881 */       return true;
/*       */     }
/*  9883 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f23);
/*  9884 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fcheckbox_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9889 */     PageContext pageContext = _jspx_page_context;
/*  9890 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9892 */     CheckboxTag _jspx_th_html_005fcheckbox_005f12 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyleClass_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/*  9893 */     _jspx_th_html_005fcheckbox_005f12.setPageContext(_jspx_page_context);
/*  9894 */     _jspx_th_html_005fcheckbox_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9896 */     _jspx_th_html_005fcheckbox_005f12.setProperty("jmxEnabled");
/*       */     
/*  9898 */     _jspx_th_html_005fcheckbox_005f12.setOnclick("handleJMXUrlCheckbox(this)");
/*       */     
/*  9900 */     _jspx_th_html_005fcheckbox_005f12.setStyleClass("formtext default");
/*  9901 */     int _jspx_eval_html_005fcheckbox_005f12 = _jspx_th_html_005fcheckbox_005f12.doStartTag();
/*  9902 */     if (_jspx_th_html_005fcheckbox_005f12.doEndTag() == 5) {
/*  9903 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f12);
/*  9904 */       return true;
/*       */     }
/*  9906 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f12);
/*  9907 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fif_005f19(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9912 */     PageContext pageContext = _jspx_page_context;
/*  9913 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9915 */     IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  9916 */     _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/*  9917 */     _jspx_th_c_005fif_005f19.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9919 */     _jspx_th_c_005fif_005f19.setTest("${!AMActionForm.jmxEnabled}");
/*  9920 */     int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/*  9921 */     if (_jspx_eval_c_005fif_005f19 != 0) {
/*       */       for (;;) {
/*  9923 */         out.write("style=\"display:none\"");
/*  9924 */         int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/*  9925 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*       */     }
/*  9929 */     if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/*  9930 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/*  9931 */       return true;
/*       */     }
/*  9933 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/*  9934 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f24(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9939 */     PageContext pageContext = _jspx_page_context;
/*  9940 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9942 */     TextTag _jspx_th_html_005ftext_005f24 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/*  9943 */     _jspx_th_html_005ftext_005f24.setPageContext(_jspx_page_context);
/*  9944 */     _jspx_th_html_005ftext_005f24.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9946 */     _jspx_th_html_005ftext_005f24.setProperty("jmxurl");
/*       */     
/*  9948 */     _jspx_th_html_005ftext_005f24.setStyleClass("formtext default");
/*       */     
/*  9950 */     _jspx_th_html_005ftext_005f24.setSize("25");
/*  9951 */     int _jspx_eval_html_005ftext_005f24 = _jspx_th_html_005ftext_005f24.doStartTag();
/*  9952 */     if (_jspx_th_html_005ftext_005f24.doEndTag() == 5) {
/*  9953 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f24);
/*  9954 */       return true;
/*       */     }
/*  9956 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f24);
/*  9957 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9962 */     PageContext pageContext = _jspx_page_context;
/*  9963 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9965 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/*  9966 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/*  9967 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9969 */     _jspx_th_html_005ftextarea_005f0.setProperty("command");
/*       */     
/*  9971 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea xlarge");
/*       */     
/*  9973 */     _jspx_th_html_005ftextarea_005f0.setRows("4");
/*       */     
/*  9975 */     _jspx_th_html_005ftextarea_005f0.setCols("50");
/*  9976 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/*  9977 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/*  9978 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/*  9979 */       return true;
/*       */     }
/*  9981 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/*  9982 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f25(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/*  9987 */     PageContext pageContext = _jspx_page_context;
/*  9988 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/*  9990 */     TextTag _jspx_th_html_005ftext_005f25 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  9991 */     _jspx_th_html_005ftext_005f25.setPageContext(_jspx_page_context);
/*  9992 */     _jspx_th_html_005ftext_005f25.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/*  9994 */     _jspx_th_html_005ftext_005f25.setProperty("search");
/*       */     
/*  9996 */     _jspx_th_html_005ftext_005f25.setStyleClass("formtext default");
/*  9997 */     int _jspx_eval_html_005ftext_005f25 = _jspx_th_html_005ftext_005f25.doStartTag();
/*  9998 */     if (_jspx_th_html_005ftext_005f25.doEndTag() == 5) {
/*  9999 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f25);
/* 10000 */       return true;
/*       */     }
/* 10002 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f25);
/* 10003 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f26(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10008 */     PageContext pageContext = _jspx_page_context;
/* 10009 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10011 */     TextTag _jspx_th_html_005ftext_005f26 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 10012 */     _jspx_th_html_005ftext_005f26.setPageContext(_jspx_page_context);
/* 10013 */     _jspx_th_html_005ftext_005f26.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10015 */     _jspx_th_html_005ftext_005f26.setProperty("timeout");
/*       */     
/* 10017 */     _jspx_th_html_005ftext_005f26.setStyleClass("formtext default");
/* 10018 */     int _jspx_eval_html_005ftext_005f26 = _jspx_th_html_005ftext_005f26.doStartTag();
/* 10019 */     if (_jspx_th_html_005ftext_005f26.doEndTag() == 5) {
/* 10020 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f26);
/* 10021 */       return true;
/*       */     }
/* 10023 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f26);
/* 10024 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f27(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10029 */     PageContext pageContext = _jspx_page_context;
/* 10030 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10032 */     TextTag _jspx_th_html_005ftext_005f27 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 10033 */     _jspx_th_html_005ftext_005f27.setPageContext(_jspx_page_context);
/* 10034 */     _jspx_th_html_005ftext_005f27.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10036 */     _jspx_th_html_005ftext_005f27.setProperty("snmpPort");
/*       */     
/* 10038 */     _jspx_th_html_005ftext_005f27.setStyleClass("formtext small");
/* 10039 */     int _jspx_eval_html_005ftext_005f27 = _jspx_th_html_005ftext_005f27.doStartTag();
/* 10040 */     if (_jspx_th_html_005ftext_005f27.doEndTag() == 5) {
/* 10041 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f27);
/* 10042 */       return true;
/*       */     }
/* 10044 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f27);
/* 10045 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10050 */     PageContext pageContext = _jspx_page_context;
/* 10051 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10053 */     MultiboxTag _jspx_th_html_005fmultibox_005f0 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10054 */     _jspx_th_html_005fmultibox_005f0.setPageContext(_jspx_page_context);
/* 10055 */     _jspx_th_html_005fmultibox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10057 */     _jspx_th_html_005fmultibox_005f0.setValue("MSExchangeIS");
/*       */     
/* 10059 */     _jspx_th_html_005fmultibox_005f0.setProperty("exchangeservice");
/* 10060 */     int _jspx_eval_html_005fmultibox_005f0 = _jspx_th_html_005fmultibox_005f0.doStartTag();
/* 10061 */     if (_jspx_th_html_005fmultibox_005f0.doEndTag() == 5) {
/* 10062 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/* 10063 */       return true;
/*       */     }
/* 10065 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/* 10066 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10071 */     PageContext pageContext = _jspx_page_context;
/* 10072 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10074 */     MultiboxTag _jspx_th_html_005fmultibox_005f1 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10075 */     _jspx_th_html_005fmultibox_005f1.setPageContext(_jspx_page_context);
/* 10076 */     _jspx_th_html_005fmultibox_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10078 */     _jspx_th_html_005fmultibox_005f1.setValue("MSExchangeSRS");
/*       */     
/* 10080 */     _jspx_th_html_005fmultibox_005f1.setProperty("exchangeservice");
/* 10081 */     int _jspx_eval_html_005fmultibox_005f1 = _jspx_th_html_005fmultibox_005f1.doStartTag();
/* 10082 */     if (_jspx_th_html_005fmultibox_005f1.doEndTag() == 5) {
/* 10083 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f1);
/* 10084 */       return true;
/*       */     }
/* 10086 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f1);
/* 10087 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10092 */     PageContext pageContext = _jspx_page_context;
/* 10093 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10095 */     MultiboxTag _jspx_th_html_005fmultibox_005f2 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10096 */     _jspx_th_html_005fmultibox_005f2.setPageContext(_jspx_page_context);
/* 10097 */     _jspx_th_html_005fmultibox_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10099 */     _jspx_th_html_005fmultibox_005f2.setValue("MSExchangeMTA");
/*       */     
/* 10101 */     _jspx_th_html_005fmultibox_005f2.setProperty("exchangeservice");
/* 10102 */     int _jspx_eval_html_005fmultibox_005f2 = _jspx_th_html_005fmultibox_005f2.doStartTag();
/* 10103 */     if (_jspx_th_html_005fmultibox_005f2.doEndTag() == 5) {
/* 10104 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f2);
/* 10105 */       return true;
/*       */     }
/* 10107 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f2);
/* 10108 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10113 */     PageContext pageContext = _jspx_page_context;
/* 10114 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10116 */     MultiboxTag _jspx_th_html_005fmultibox_005f3 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10117 */     _jspx_th_html_005fmultibox_005f3.setPageContext(_jspx_page_context);
/* 10118 */     _jspx_th_html_005fmultibox_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10120 */     _jspx_th_html_005fmultibox_005f3.setValue("MSExchangeMGMT");
/*       */     
/* 10122 */     _jspx_th_html_005fmultibox_005f3.setProperty("exchangeservice");
/* 10123 */     int _jspx_eval_html_005fmultibox_005f3 = _jspx_th_html_005fmultibox_005f3.doStartTag();
/* 10124 */     if (_jspx_th_html_005fmultibox_005f3.doEndTag() == 5) {
/* 10125 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f3);
/* 10126 */       return true;
/*       */     }
/* 10128 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f3);
/* 10129 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10134 */     PageContext pageContext = _jspx_page_context;
/* 10135 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10137 */     MultiboxTag _jspx_th_html_005fmultibox_005f4 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10138 */     _jspx_th_html_005fmultibox_005f4.setPageContext(_jspx_page_context);
/* 10139 */     _jspx_th_html_005fmultibox_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10141 */     _jspx_th_html_005fmultibox_005f4.setValue("SMTPSVC");
/*       */     
/* 10143 */     _jspx_th_html_005fmultibox_005f4.setProperty("exchangeservice");
/* 10144 */     int _jspx_eval_html_005fmultibox_005f4 = _jspx_th_html_005fmultibox_005f4.doStartTag();
/* 10145 */     if (_jspx_th_html_005fmultibox_005f4.doEndTag() == 5) {
/* 10146 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f4);
/* 10147 */       return true;
/*       */     }
/* 10149 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f4);
/* 10150 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10155 */     PageContext pageContext = _jspx_page_context;
/* 10156 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10158 */     MultiboxTag _jspx_th_html_005fmultibox_005f5 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10159 */     _jspx_th_html_005fmultibox_005f5.setPageContext(_jspx_page_context);
/* 10160 */     _jspx_th_html_005fmultibox_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10162 */     _jspx_th_html_005fmultibox_005f5.setValue("IMAP4Svc");
/*       */     
/* 10164 */     _jspx_th_html_005fmultibox_005f5.setProperty("exchangeservice");
/* 10165 */     int _jspx_eval_html_005fmultibox_005f5 = _jspx_th_html_005fmultibox_005f5.doStartTag();
/* 10166 */     if (_jspx_th_html_005fmultibox_005f5.doEndTag() == 5) {
/* 10167 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f5);
/* 10168 */       return true;
/*       */     }
/* 10170 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f5);
/* 10171 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10176 */     PageContext pageContext = _jspx_page_context;
/* 10177 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10179 */     MultiboxTag _jspx_th_html_005fmultibox_005f6 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10180 */     _jspx_th_html_005fmultibox_005f6.setPageContext(_jspx_page_context);
/* 10181 */     _jspx_th_html_005fmultibox_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10183 */     _jspx_th_html_005fmultibox_005f6.setValue("MSExchangeSA");
/*       */     
/* 10185 */     _jspx_th_html_005fmultibox_005f6.setProperty("exchangeservice");
/* 10186 */     int _jspx_eval_html_005fmultibox_005f6 = _jspx_th_html_005fmultibox_005f6.doStartTag();
/* 10187 */     if (_jspx_th_html_005fmultibox_005f6.doEndTag() == 5) {
/* 10188 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f6);
/* 10189 */       return true;
/*       */     }
/* 10191 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f6);
/* 10192 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10197 */     PageContext pageContext = _jspx_page_context;
/* 10198 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10200 */     MultiboxTag _jspx_th_html_005fmultibox_005f7 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10201 */     _jspx_th_html_005fmultibox_005f7.setPageContext(_jspx_page_context);
/* 10202 */     _jspx_th_html_005fmultibox_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10204 */     _jspx_th_html_005fmultibox_005f7.setValue("POP3Svc");
/*       */     
/* 10206 */     _jspx_th_html_005fmultibox_005f7.setProperty("exchangeservice");
/* 10207 */     int _jspx_eval_html_005fmultibox_005f7 = _jspx_th_html_005fmultibox_005f7.doStartTag();
/* 10208 */     if (_jspx_th_html_005fmultibox_005f7.doEndTag() == 5) {
/* 10209 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f7);
/* 10210 */       return true;
/*       */     }
/* 10212 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f7);
/* 10213 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10218 */     PageContext pageContext = _jspx_page_context;
/* 10219 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10221 */     MultiboxTag _jspx_th_html_005fmultibox_005f8 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10222 */     _jspx_th_html_005fmultibox_005f8.setPageContext(_jspx_page_context);
/* 10223 */     _jspx_th_html_005fmultibox_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10225 */     _jspx_th_html_005fmultibox_005f8.setValue("MSExchangeRA");
/*       */     
/* 10227 */     _jspx_th_html_005fmultibox_005f8.setProperty("exchangeservice");
/* 10228 */     int _jspx_eval_html_005fmultibox_005f8 = _jspx_th_html_005fmultibox_005f8.doStartTag();
/* 10229 */     if (_jspx_th_html_005fmultibox_005f8.doEndTag() == 5) {
/* 10230 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f8);
/* 10231 */       return true;
/*       */     }
/* 10233 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f8);
/* 10234 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10239 */     PageContext pageContext = _jspx_page_context;
/* 10240 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10242 */     MultiboxTag _jspx_th_html_005fmultibox_005f9 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10243 */     _jspx_th_html_005fmultibox_005f9.setPageContext(_jspx_page_context);
/* 10244 */     _jspx_th_html_005fmultibox_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10246 */     _jspx_th_html_005fmultibox_005f9.setValue("MSExchangeES");
/*       */     
/* 10248 */     _jspx_th_html_005fmultibox_005f9.setProperty("exchangeservice");
/* 10249 */     int _jspx_eval_html_005fmultibox_005f9 = _jspx_th_html_005fmultibox_005f9.doStartTag();
/* 10250 */     if (_jspx_th_html_005fmultibox_005f9.doEndTag() == 5) {
/* 10251 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f9);
/* 10252 */       return true;
/*       */     }
/* 10254 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f9);
/* 10255 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10260 */     PageContext pageContext = _jspx_page_context;
/* 10261 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10263 */     MultiboxTag _jspx_th_html_005fmultibox_005f10 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10264 */     _jspx_th_html_005fmultibox_005f10.setPageContext(_jspx_page_context);
/* 10265 */     _jspx_th_html_005fmultibox_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10267 */     _jspx_th_html_005fmultibox_005f10.setValue("MSExchangeADTopology");
/*       */     
/* 10269 */     _jspx_th_html_005fmultibox_005f10.setProperty("exchangeservice");
/* 10270 */     int _jspx_eval_html_005fmultibox_005f10 = _jspx_th_html_005fmultibox_005f10.doStartTag();
/* 10271 */     if (_jspx_th_html_005fmultibox_005f10.doEndTag() == 5) {
/* 10272 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f10);
/* 10273 */       return true;
/*       */     }
/* 10275 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f10);
/* 10276 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10281 */     PageContext pageContext = _jspx_page_context;
/* 10282 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10284 */     MultiboxTag _jspx_th_html_005fmultibox_005f11 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10285 */     _jspx_th_html_005fmultibox_005f11.setPageContext(_jspx_page_context);
/* 10286 */     _jspx_th_html_005fmultibox_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10288 */     _jspx_th_html_005fmultibox_005f11.setValue("MSExchangeAntispamUpdate");
/*       */     
/* 10290 */     _jspx_th_html_005fmultibox_005f11.setProperty("exchangeservice");
/* 10291 */     int _jspx_eval_html_005fmultibox_005f11 = _jspx_th_html_005fmultibox_005f11.doStartTag();
/* 10292 */     if (_jspx_th_html_005fmultibox_005f11.doEndTag() == 5) {
/* 10293 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f11);
/* 10294 */       return true;
/*       */     }
/* 10296 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f11);
/* 10297 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10302 */     PageContext pageContext = _jspx_page_context;
/* 10303 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10305 */     MultiboxTag _jspx_th_html_005fmultibox_005f12 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10306 */     _jspx_th_html_005fmultibox_005f12.setPageContext(_jspx_page_context);
/* 10307 */     _jspx_th_html_005fmultibox_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10309 */     _jspx_th_html_005fmultibox_005f12.setValue("MSExchangeEdgeSync");
/*       */     
/* 10311 */     _jspx_th_html_005fmultibox_005f12.setProperty("exchangeservice");
/* 10312 */     int _jspx_eval_html_005fmultibox_005f12 = _jspx_th_html_005fmultibox_005f12.doStartTag();
/* 10313 */     if (_jspx_th_html_005fmultibox_005f12.doEndTag() == 5) {
/* 10314 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f12);
/* 10315 */       return true;
/*       */     }
/* 10317 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f12);
/* 10318 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10323 */     PageContext pageContext = _jspx_page_context;
/* 10324 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10326 */     MultiboxTag _jspx_th_html_005fmultibox_005f13 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10327 */     _jspx_th_html_005fmultibox_005f13.setPageContext(_jspx_page_context);
/* 10328 */     _jspx_th_html_005fmultibox_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10330 */     _jspx_th_html_005fmultibox_005f13.setValue("MSExchangeFDS");
/*       */     
/* 10332 */     _jspx_th_html_005fmultibox_005f13.setProperty("exchangeservice");
/* 10333 */     int _jspx_eval_html_005fmultibox_005f13 = _jspx_th_html_005fmultibox_005f13.doStartTag();
/* 10334 */     if (_jspx_th_html_005fmultibox_005f13.doEndTag() == 5) {
/* 10335 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f13);
/* 10336 */       return true;
/*       */     }
/* 10338 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f13);
/* 10339 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10344 */     PageContext pageContext = _jspx_page_context;
/* 10345 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10347 */     MultiboxTag _jspx_th_html_005fmultibox_005f14 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10348 */     _jspx_th_html_005fmultibox_005f14.setPageContext(_jspx_page_context);
/* 10349 */     _jspx_th_html_005fmultibox_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10351 */     _jspx_th_html_005fmultibox_005f14.setValue("MSExchangeMailboxAssistants");
/*       */     
/* 10353 */     _jspx_th_html_005fmultibox_005f14.setProperty("exchangeservice");
/* 10354 */     int _jspx_eval_html_005fmultibox_005f14 = _jspx_th_html_005fmultibox_005f14.doStartTag();
/* 10355 */     if (_jspx_th_html_005fmultibox_005f14.doEndTag() == 5) {
/* 10356 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f14);
/* 10357 */       return true;
/*       */     }
/* 10359 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f14);
/* 10360 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f15(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10365 */     PageContext pageContext = _jspx_page_context;
/* 10366 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10368 */     MultiboxTag _jspx_th_html_005fmultibox_005f15 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10369 */     _jspx_th_html_005fmultibox_005f15.setPageContext(_jspx_page_context);
/* 10370 */     _jspx_th_html_005fmultibox_005f15.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10372 */     _jspx_th_html_005fmultibox_005f15.setValue("MSExchangePop3");
/*       */     
/* 10374 */     _jspx_th_html_005fmultibox_005f15.setProperty("exchangeservice");
/* 10375 */     int _jspx_eval_html_005fmultibox_005f15 = _jspx_th_html_005fmultibox_005f15.doStartTag();
/* 10376 */     if (_jspx_th_html_005fmultibox_005f15.doEndTag() == 5) {
/* 10377 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f15);
/* 10378 */       return true;
/*       */     }
/* 10380 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f15);
/* 10381 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f16(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10386 */     PageContext pageContext = _jspx_page_context;
/* 10387 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10389 */     MultiboxTag _jspx_th_html_005fmultibox_005f16 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10390 */     _jspx_th_html_005fmultibox_005f16.setPageContext(_jspx_page_context);
/* 10391 */     _jspx_th_html_005fmultibox_005f16.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10393 */     _jspx_th_html_005fmultibox_005f16.setValue("MSExchangeIS");
/*       */     
/* 10395 */     _jspx_th_html_005fmultibox_005f16.setProperty("exchangeservice");
/* 10396 */     int _jspx_eval_html_005fmultibox_005f16 = _jspx_th_html_005fmultibox_005f16.doStartTag();
/* 10397 */     if (_jspx_th_html_005fmultibox_005f16.doEndTag() == 5) {
/* 10398 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f16);
/* 10399 */       return true;
/*       */     }
/* 10401 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f16);
/* 10402 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f17(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10407 */     PageContext pageContext = _jspx_page_context;
/* 10408 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10410 */     MultiboxTag _jspx_th_html_005fmultibox_005f17 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10411 */     _jspx_th_html_005fmultibox_005f17.setPageContext(_jspx_page_context);
/* 10412 */     _jspx_th_html_005fmultibox_005f17.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10414 */     _jspx_th_html_005fmultibox_005f17.setValue("MSExchangeImap4");
/*       */     
/* 10416 */     _jspx_th_html_005fmultibox_005f17.setProperty("exchangeservice");
/* 10417 */     int _jspx_eval_html_005fmultibox_005f17 = _jspx_th_html_005fmultibox_005f17.doStartTag();
/* 10418 */     if (_jspx_th_html_005fmultibox_005f17.doEndTag() == 5) {
/* 10419 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f17);
/* 10420 */       return true;
/*       */     }
/* 10422 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f17);
/* 10423 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f18(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10428 */     PageContext pageContext = _jspx_page_context;
/* 10429 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10431 */     MultiboxTag _jspx_th_html_005fmultibox_005f18 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10432 */     _jspx_th_html_005fmultibox_005f18.setPageContext(_jspx_page_context);
/* 10433 */     _jspx_th_html_005fmultibox_005f18.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10435 */     _jspx_th_html_005fmultibox_005f18.setValue("MSExchangeMailSubmission");
/*       */     
/* 10437 */     _jspx_th_html_005fmultibox_005f18.setProperty("exchangeservice");
/* 10438 */     int _jspx_eval_html_005fmultibox_005f18 = _jspx_th_html_005fmultibox_005f18.doStartTag();
/* 10439 */     if (_jspx_th_html_005fmultibox_005f18.doEndTag() == 5) {
/* 10440 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f18);
/* 10441 */       return true;
/*       */     }
/* 10443 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f18);
/* 10444 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f19(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10449 */     PageContext pageContext = _jspx_page_context;
/* 10450 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10452 */     MultiboxTag _jspx_th_html_005fmultibox_005f19 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10453 */     _jspx_th_html_005fmultibox_005f19.setPageContext(_jspx_page_context);
/* 10454 */     _jspx_th_html_005fmultibox_005f19.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10456 */     _jspx_th_html_005fmultibox_005f19.setValue("MSExchangeMonitoring");
/*       */     
/* 10458 */     _jspx_th_html_005fmultibox_005f19.setProperty("exchangeservice");
/* 10459 */     int _jspx_eval_html_005fmultibox_005f19 = _jspx_th_html_005fmultibox_005f19.doStartTag();
/* 10460 */     if (_jspx_th_html_005fmultibox_005f19.doEndTag() == 5) {
/* 10461 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f19);
/* 10462 */       return true;
/*       */     }
/* 10464 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f19);
/* 10465 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f20(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10470 */     PageContext pageContext = _jspx_page_context;
/* 10471 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10473 */     MultiboxTag _jspx_th_html_005fmultibox_005f20 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10474 */     _jspx_th_html_005fmultibox_005f20.setPageContext(_jspx_page_context);
/* 10475 */     _jspx_th_html_005fmultibox_005f20.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10477 */     _jspx_th_html_005fmultibox_005f20.setValue("MSExchangeRepl");
/*       */     
/* 10479 */     _jspx_th_html_005fmultibox_005f20.setProperty("exchangeservice");
/* 10480 */     int _jspx_eval_html_005fmultibox_005f20 = _jspx_th_html_005fmultibox_005f20.doStartTag();
/* 10481 */     if (_jspx_th_html_005fmultibox_005f20.doEndTag() == 5) {
/* 10482 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f20);
/* 10483 */       return true;
/*       */     }
/* 10485 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f20);
/* 10486 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f21(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10491 */     PageContext pageContext = _jspx_page_context;
/* 10492 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10494 */     MultiboxTag _jspx_th_html_005fmultibox_005f21 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10495 */     _jspx_th_html_005fmultibox_005f21.setPageContext(_jspx_page_context);
/* 10496 */     _jspx_th_html_005fmultibox_005f21.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10498 */     _jspx_th_html_005fmultibox_005f21.setValue("MSExchangeSA");
/*       */     
/* 10500 */     _jspx_th_html_005fmultibox_005f21.setProperty("exchangeservice");
/* 10501 */     int _jspx_eval_html_005fmultibox_005f21 = _jspx_th_html_005fmultibox_005f21.doStartTag();
/* 10502 */     if (_jspx_th_html_005fmultibox_005f21.doEndTag() == 5) {
/* 10503 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f21);
/* 10504 */       return true;
/*       */     }
/* 10506 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f21);
/* 10507 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f22(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10512 */     PageContext pageContext = _jspx_page_context;
/* 10513 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10515 */     MultiboxTag _jspx_th_html_005fmultibox_005f22 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10516 */     _jspx_th_html_005fmultibox_005f22.setPageContext(_jspx_page_context);
/* 10517 */     _jspx_th_html_005fmultibox_005f22.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10519 */     _jspx_th_html_005fmultibox_005f22.setValue("MSExchangeSearch");
/*       */     
/* 10521 */     _jspx_th_html_005fmultibox_005f22.setProperty("exchangeservice");
/* 10522 */     int _jspx_eval_html_005fmultibox_005f22 = _jspx_th_html_005fmultibox_005f22.doStartTag();
/* 10523 */     if (_jspx_th_html_005fmultibox_005f22.doEndTag() == 5) {
/* 10524 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f22);
/* 10525 */       return true;
/*       */     }
/* 10527 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f22);
/* 10528 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f23(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10533 */     PageContext pageContext = _jspx_page_context;
/* 10534 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10536 */     MultiboxTag _jspx_th_html_005fmultibox_005f23 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10537 */     _jspx_th_html_005fmultibox_005f23.setPageContext(_jspx_page_context);
/* 10538 */     _jspx_th_html_005fmultibox_005f23.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10540 */     _jspx_th_html_005fmultibox_005f23.setValue("MSExchangeServiceHost");
/*       */     
/* 10542 */     _jspx_th_html_005fmultibox_005f23.setProperty("exchangeservice");
/* 10543 */     int _jspx_eval_html_005fmultibox_005f23 = _jspx_th_html_005fmultibox_005f23.doStartTag();
/* 10544 */     if (_jspx_th_html_005fmultibox_005f23.doEndTag() == 5) {
/* 10545 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f23);
/* 10546 */       return true;
/*       */     }
/* 10548 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f23);
/* 10549 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f24(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10554 */     PageContext pageContext = _jspx_page_context;
/* 10555 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10557 */     MultiboxTag _jspx_th_html_005fmultibox_005f24 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10558 */     _jspx_th_html_005fmultibox_005f24.setPageContext(_jspx_page_context);
/* 10559 */     _jspx_th_html_005fmultibox_005f24.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10561 */     _jspx_th_html_005fmultibox_005f24.setValue("MSExchangeTransport");
/*       */     
/* 10563 */     _jspx_th_html_005fmultibox_005f24.setProperty("exchangeservice");
/* 10564 */     int _jspx_eval_html_005fmultibox_005f24 = _jspx_th_html_005fmultibox_005f24.doStartTag();
/* 10565 */     if (_jspx_th_html_005fmultibox_005f24.doEndTag() == 5) {
/* 10566 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f24);
/* 10567 */       return true;
/*       */     }
/* 10569 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f24);
/* 10570 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f25(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10575 */     PageContext pageContext = _jspx_page_context;
/* 10576 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10578 */     MultiboxTag _jspx_th_html_005fmultibox_005f25 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10579 */     _jspx_th_html_005fmultibox_005f25.setPageContext(_jspx_page_context);
/* 10580 */     _jspx_th_html_005fmultibox_005f25.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10582 */     _jspx_th_html_005fmultibox_005f25.setValue("MSExchangeTransportLogSearch");
/*       */     
/* 10584 */     _jspx_th_html_005fmultibox_005f25.setProperty("exchangeservice");
/* 10585 */     int _jspx_eval_html_005fmultibox_005f25 = _jspx_th_html_005fmultibox_005f25.doStartTag();
/* 10586 */     if (_jspx_th_html_005fmultibox_005f25.doEndTag() == 5) {
/* 10587 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f25);
/* 10588 */       return true;
/*       */     }
/* 10590 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f25);
/* 10591 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f26(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10596 */     PageContext pageContext = _jspx_page_context;
/* 10597 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10599 */     MultiboxTag _jspx_th_html_005fmultibox_005f26 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10600 */     _jspx_th_html_005fmultibox_005f26.setPageContext(_jspx_page_context);
/* 10601 */     _jspx_th_html_005fmultibox_005f26.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10603 */     _jspx_th_html_005fmultibox_005f26.setValue("ADAM_MSExchange");
/*       */     
/* 10605 */     _jspx_th_html_005fmultibox_005f26.setProperty("exchangeservice");
/* 10606 */     int _jspx_eval_html_005fmultibox_005f26 = _jspx_th_html_005fmultibox_005f26.doStartTag();
/* 10607 */     if (_jspx_th_html_005fmultibox_005f26.doEndTag() == 5) {
/* 10608 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f26);
/* 10609 */       return true;
/*       */     }
/* 10611 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f26);
/* 10612 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f27(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10617 */     PageContext pageContext = _jspx_page_context;
/* 10618 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10620 */     MultiboxTag _jspx_th_html_005fmultibox_005f27 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10621 */     _jspx_th_html_005fmultibox_005f27.setPageContext(_jspx_page_context);
/* 10622 */     _jspx_th_html_005fmultibox_005f27.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10624 */     _jspx_th_html_005fmultibox_005f27.setValue("EdgeCredentialSvc");
/*       */     
/* 10626 */     _jspx_th_html_005fmultibox_005f27.setProperty("exchangeservice");
/* 10627 */     int _jspx_eval_html_005fmultibox_005f27 = _jspx_th_html_005fmultibox_005f27.doStartTag();
/* 10628 */     if (_jspx_th_html_005fmultibox_005f27.doEndTag() == 5) {
/* 10629 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f27);
/* 10630 */       return true;
/*       */     }
/* 10632 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f27);
/* 10633 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f28(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10638 */     PageContext pageContext = _jspx_page_context;
/* 10639 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10641 */     MultiboxTag _jspx_th_html_005fmultibox_005f28 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10642 */     _jspx_th_html_005fmultibox_005f28.setPageContext(_jspx_page_context);
/* 10643 */     _jspx_th_html_005fmultibox_005f28.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10645 */     _jspx_th_html_005fmultibox_005f28.setValue("MSS");
/*       */     
/* 10647 */     _jspx_th_html_005fmultibox_005f28.setProperty("exchangeservice");
/* 10648 */     int _jspx_eval_html_005fmultibox_005f28 = _jspx_th_html_005fmultibox_005f28.doStartTag();
/* 10649 */     if (_jspx_th_html_005fmultibox_005f28.doEndTag() == 5) {
/* 10650 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f28);
/* 10651 */       return true;
/*       */     }
/* 10653 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f28);
/* 10654 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f29(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10659 */     PageContext pageContext = _jspx_page_context;
/* 10660 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10662 */     MultiboxTag _jspx_th_html_005fmultibox_005f29 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10663 */     _jspx_th_html_005fmultibox_005f29.setPageContext(_jspx_page_context);
/* 10664 */     _jspx_th_html_005fmultibox_005f29.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10666 */     _jspx_th_html_005fmultibox_005f29.setValue("MSExchangeUM");
/*       */     
/* 10668 */     _jspx_th_html_005fmultibox_005f29.setProperty("exchangeservice");
/* 10669 */     int _jspx_eval_html_005fmultibox_005f29 = _jspx_th_html_005fmultibox_005f29.doStartTag();
/* 10670 */     if (_jspx_th_html_005fmultibox_005f29.doEndTag() == 5) {
/* 10671 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f29);
/* 10672 */       return true;
/*       */     }
/* 10674 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f29);
/* 10675 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f30(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10680 */     PageContext pageContext = _jspx_page_context;
/* 10681 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10683 */     MultiboxTag _jspx_th_html_005fmultibox_005f30 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10684 */     _jspx_th_html_005fmultibox_005f30.setPageContext(_jspx_page_context);
/* 10685 */     _jspx_th_html_005fmultibox_005f30.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10687 */     _jspx_th_html_005fmultibox_005f30.setValue("MSExchangeAB");
/*       */     
/* 10689 */     _jspx_th_html_005fmultibox_005f30.setProperty("exchangeservice2k7");
/* 10690 */     int _jspx_eval_html_005fmultibox_005f30 = _jspx_th_html_005fmultibox_005f30.doStartTag();
/* 10691 */     if (_jspx_th_html_005fmultibox_005f30.doEndTag() == 5) {
/* 10692 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f30);
/* 10693 */       return true;
/*       */     }
/* 10695 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f30);
/* 10696 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f31(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10701 */     PageContext pageContext = _jspx_page_context;
/* 10702 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10704 */     MultiboxTag _jspx_th_html_005fmultibox_005f31 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10705 */     _jspx_th_html_005fmultibox_005f31.setPageContext(_jspx_page_context);
/* 10706 */     _jspx_th_html_005fmultibox_005f31.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10708 */     _jspx_th_html_005fmultibox_005f31.setValue("MSExchangeFBA");
/*       */     
/* 10710 */     _jspx_th_html_005fmultibox_005f31.setProperty("exchangeservice2k7");
/* 10711 */     int _jspx_eval_html_005fmultibox_005f31 = _jspx_th_html_005fmultibox_005f31.doStartTag();
/* 10712 */     if (_jspx_th_html_005fmultibox_005f31.doEndTag() == 5) {
/* 10713 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f31);
/* 10714 */       return true;
/*       */     }
/* 10716 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f31);
/* 10717 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f32(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10722 */     PageContext pageContext = _jspx_page_context;
/* 10723 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10725 */     MultiboxTag _jspx_th_html_005fmultibox_005f32 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10726 */     _jspx_th_html_005fmultibox_005f32.setPageContext(_jspx_page_context);
/* 10727 */     _jspx_th_html_005fmultibox_005f32.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10729 */     _jspx_th_html_005fmultibox_005f32.setValue("MSExchangeMailboxReplication");
/*       */     
/* 10731 */     _jspx_th_html_005fmultibox_005f32.setProperty("exchangeservice2k7");
/* 10732 */     int _jspx_eval_html_005fmultibox_005f32 = _jspx_th_html_005fmultibox_005f32.doStartTag();
/* 10733 */     if (_jspx_th_html_005fmultibox_005f32.doEndTag() == 5) {
/* 10734 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f32);
/* 10735 */       return true;
/*       */     }
/* 10737 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f32);
/* 10738 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f33(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10743 */     PageContext pageContext = _jspx_page_context;
/* 10744 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10746 */     MultiboxTag _jspx_th_html_005fmultibox_005f33 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10747 */     _jspx_th_html_005fmultibox_005f33.setPageContext(_jspx_page_context);
/* 10748 */     _jspx_th_html_005fmultibox_005f33.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10750 */     _jspx_th_html_005fmultibox_005f33.setValue("MSExchangeProtectedServiceHost");
/*       */     
/* 10752 */     _jspx_th_html_005fmultibox_005f33.setProperty("exchangeservice2k7");
/* 10753 */     int _jspx_eval_html_005fmultibox_005f33 = _jspx_th_html_005fmultibox_005f33.doStartTag();
/* 10754 */     if (_jspx_th_html_005fmultibox_005f33.doEndTag() == 5) {
/* 10755 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f33);
/* 10756 */       return true;
/*       */     }
/* 10758 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f33);
/* 10759 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f34(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10764 */     PageContext pageContext = _jspx_page_context;
/* 10765 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10767 */     MultiboxTag _jspx_th_html_005fmultibox_005f34 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10768 */     _jspx_th_html_005fmultibox_005f34.setPageContext(_jspx_page_context);
/* 10769 */     _jspx_th_html_005fmultibox_005f34.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10771 */     _jspx_th_html_005fmultibox_005f34.setValue("MSExchangeRPC");
/*       */     
/* 10773 */     _jspx_th_html_005fmultibox_005f34.setProperty("exchangeservice2k7");
/* 10774 */     int _jspx_eval_html_005fmultibox_005f34 = _jspx_th_html_005fmultibox_005f34.doStartTag();
/* 10775 */     if (_jspx_th_html_005fmultibox_005f34.doEndTag() == 5) {
/* 10776 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f34);
/* 10777 */       return true;
/*       */     }
/* 10779 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f34);
/* 10780 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f35(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10785 */     PageContext pageContext = _jspx_page_context;
/* 10786 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10788 */     MultiboxTag _jspx_th_html_005fmultibox_005f35 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10789 */     _jspx_th_html_005fmultibox_005f35.setPageContext(_jspx_page_context);
/* 10790 */     _jspx_th_html_005fmultibox_005f35.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10792 */     _jspx_th_html_005fmultibox_005f35.setValue("MSExchangeThrottling");
/*       */     
/* 10794 */     _jspx_th_html_005fmultibox_005f35.setProperty("exchangeservice2k7");
/* 10795 */     int _jspx_eval_html_005fmultibox_005f35 = _jspx_th_html_005fmultibox_005f35.doStartTag();
/* 10796 */     if (_jspx_th_html_005fmultibox_005f35.doEndTag() == 5) {
/* 10797 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f35);
/* 10798 */       return true;
/*       */     }
/* 10800 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f35);
/* 10801 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f36(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10806 */     PageContext pageContext = _jspx_page_context;
/* 10807 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10809 */     MultiboxTag _jspx_th_html_005fmultibox_005f36 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10810 */     _jspx_th_html_005fmultibox_005f36.setPageContext(_jspx_page_context);
/* 10811 */     _jspx_th_html_005fmultibox_005f36.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10813 */     _jspx_th_html_005fmultibox_005f36.setValue("MSExchangeTransportLogSearch");
/*       */     
/* 10815 */     _jspx_th_html_005fmultibox_005f36.setProperty("exchangeservice2k7");
/* 10816 */     int _jspx_eval_html_005fmultibox_005f36 = _jspx_th_html_005fmultibox_005f36.doStartTag();
/* 10817 */     if (_jspx_th_html_005fmultibox_005f36.doEndTag() == 5) {
/* 10818 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f36);
/* 10819 */       return true;
/*       */     }
/* 10821 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f36);
/* 10822 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fmultibox_005f37(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10827 */     PageContext pageContext = _jspx_page_context;
/* 10828 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10830 */     MultiboxTag _jspx_th_html_005fmultibox_005f37 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 10831 */     _jspx_th_html_005fmultibox_005f37.setPageContext(_jspx_page_context);
/* 10832 */     _jspx_th_html_005fmultibox_005f37.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10834 */     _jspx_th_html_005fmultibox_005f37.setValue("MSFTESQL-Exchange");
/*       */     
/* 10836 */     _jspx_th_html_005fmultibox_005f37.setProperty("exchangeservice");
/* 10837 */     int _jspx_eval_html_005fmultibox_005f37 = _jspx_th_html_005fmultibox_005f37.doStartTag();
/* 10838 */     if (_jspx_th_html_005fmultibox_005f37.doEndTag() == 5) {
/* 10839 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f37);
/* 10840 */       return true;
/*       */     }
/* 10842 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f37);
/* 10843 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f28(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10848 */     PageContext pageContext = _jspx_page_context;
/* 10849 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10851 */     TextTag _jspx_th_html_005ftext_005f28 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 10852 */     _jspx_th_html_005ftext_005f28.setPageContext(_jspx_page_context);
/* 10853 */     _jspx_th_html_005ftext_005f28.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10855 */     _jspx_th_html_005ftext_005f28.setProperty("username");
/*       */     
/* 10857 */     _jspx_th_html_005ftext_005f28.setStyleClass("formtext default");
/* 10858 */     int _jspx_eval_html_005ftext_005f28 = _jspx_th_html_005ftext_005f28.doStartTag();
/* 10859 */     if (_jspx_th_html_005ftext_005f28.doEndTag() == 5) {
/* 10860 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f28);
/* 10861 */       return true;
/*       */     }
/* 10863 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f28);
/* 10864 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fpassword_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10869 */     PageContext pageContext = _jspx_page_context;
/* 10870 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10872 */     PasswordTag _jspx_th_html_005fpassword_005f4 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/* 10873 */     _jspx_th_html_005fpassword_005f4.setPageContext(_jspx_page_context);
/* 10874 */     _jspx_th_html_005fpassword_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10876 */     _jspx_th_html_005fpassword_005f4.setProperty("password");
/*       */     
/* 10878 */     _jspx_th_html_005fpassword_005f4.setStyleClass("formtext default");
/* 10879 */     int _jspx_eval_html_005fpassword_005f4 = _jspx_th_html_005fpassword_005f4.doStartTag();
/* 10880 */     if (_jspx_th_html_005fpassword_005f4.doEndTag() == 5) {
/* 10881 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f4);
/* 10882 */       return true;
/*       */     }
/* 10884 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f4);
/* 10885 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f29(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10890 */     PageContext pageContext = _jspx_page_context;
/* 10891 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10893 */     TextTag _jspx_th_html_005ftext_005f29 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 10894 */     _jspx_th_html_005ftext_005f29.setPageContext(_jspx_page_context);
/* 10895 */     _jspx_th_html_005ftext_005f29.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10897 */     _jspx_th_html_005ftext_005f29.setProperty("tomcatmanagerurl");
/*       */     
/* 10899 */     _jspx_th_html_005ftext_005f29.setStyleClass("formtext default");
/* 10900 */     int _jspx_eval_html_005ftext_005f29 = _jspx_th_html_005ftext_005f29.doStartTag();
/* 10901 */     if (_jspx_th_html_005ftext_005f29.doEndTag() == 5) {
/* 10902 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f29);
/* 10903 */       return true;
/*       */     }
/* 10905 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f29);
/* 10906 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fcheckbox_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10911 */     PageContext pageContext = _jspx_page_context;
/* 10912 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10914 */     CheckboxTag _jspx_th_html_005fcheckbox_005f13 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 10915 */     _jspx_th_html_005fcheckbox_005f13.setPageContext(_jspx_page_context);
/* 10916 */     _jspx_th_html_005fcheckbox_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10918 */     _jspx_th_html_005fcheckbox_005f13.setProperty("authEnabled");
/*       */     
/* 10920 */     _jspx_th_html_005fcheckbox_005f13.setOnclick("handleGlobalSecurity(this)");
/* 10921 */     int _jspx_eval_html_005fcheckbox_005f13 = _jspx_th_html_005fcheckbox_005f13.doStartTag();
/* 10922 */     if (_jspx_th_html_005fcheckbox_005f13.doEndTag() == 5) {
/* 10923 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f13);
/* 10924 */       return true;
/*       */     }
/* 10926 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f13);
/* 10927 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fif_005f20(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10932 */     PageContext pageContext = _jspx_page_context;
/* 10933 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10935 */     IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 10936 */     _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 10937 */     _jspx_th_c_005fif_005f20.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10939 */     _jspx_th_c_005fif_005f20.setTest("${!AMActionForm.authEnabled}");
/* 10940 */     int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 10941 */     if (_jspx_eval_c_005fif_005f20 != 0) {
/*       */       for (;;) {
/* 10943 */         out.write("style=\"display:none;\"");
/* 10944 */         int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 10945 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*       */     }
/* 10949 */     if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 10950 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 10951 */       return true;
/*       */     }
/* 10953 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 10954 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f30(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10959 */     PageContext pageContext = _jspx_page_context;
/* 10960 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10962 */     TextTag _jspx_th_html_005ftext_005f30 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 10963 */     _jspx_th_html_005ftext_005f30.setPageContext(_jspx_page_context);
/* 10964 */     _jspx_th_html_005ftext_005f30.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10966 */     _jspx_th_html_005ftext_005f30.setProperty("username");
/*       */     
/* 10968 */     _jspx_th_html_005ftext_005f30.setStyleClass("formtext");
/*       */     
/* 10970 */     _jspx_th_html_005ftext_005f30.setSize("15");
/* 10971 */     int _jspx_eval_html_005ftext_005f30 = _jspx_th_html_005ftext_005f30.doStartTag();
/* 10972 */     if (_jspx_th_html_005ftext_005f30.doEndTag() == 5) {
/* 10973 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f30);
/* 10974 */       return true;
/*       */     }
/* 10976 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f30);
/* 10977 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fif_005f21(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 10982 */     PageContext pageContext = _jspx_page_context;
/* 10983 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 10985 */     IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 10986 */     _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 10987 */     _jspx_th_c_005fif_005f21.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 10989 */     _jspx_th_c_005fif_005f21.setTest("${!AMActionForm.authEnabled}");
/* 10990 */     int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 10991 */     if (_jspx_eval_c_005fif_005f21 != 0) {
/*       */       for (;;) {
/* 10993 */         out.write("style=\"display:none;\"");
/* 10994 */         int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 10995 */         if (evalDoAfterBody != 2)
/*       */           break;
/*       */       }
/*       */     }
/* 10999 */     if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 11000 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 11001 */       return true;
/*       */     }
/* 11003 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 11004 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fpassword_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11009 */     PageContext pageContext = _jspx_page_context;
/* 11010 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11012 */     PasswordTag _jspx_th_html_005fpassword_005f5 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 11013 */     _jspx_th_html_005fpassword_005f5.setPageContext(_jspx_page_context);
/* 11014 */     _jspx_th_html_005fpassword_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11016 */     _jspx_th_html_005fpassword_005f5.setProperty("password");
/*       */     
/* 11018 */     _jspx_th_html_005fpassword_005f5.setStyleClass("formtext");
/*       */     
/* 11020 */     _jspx_th_html_005fpassword_005f5.setSize("15");
/* 11021 */     int _jspx_eval_html_005fpassword_005f5 = _jspx_th_html_005fpassword_005f5.doStartTag();
/* 11022 */     if (_jspx_th_html_005fpassword_005f5.doEndTag() == 5) {
/* 11023 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f5);
/* 11024 */       return true;
/*       */     }
/* 11026 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f5);
/* 11027 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f31(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11032 */     PageContext pageContext = _jspx_page_context;
/* 11033 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11035 */     TextTag _jspx_th_html_005ftext_005f31 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 11036 */     _jspx_th_html_005ftext_005f31.setPageContext(_jspx_page_context);
/* 11037 */     _jspx_th_html_005ftext_005f31.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11039 */     _jspx_th_html_005ftext_005f31.setProperty("username");
/*       */     
/* 11041 */     _jspx_th_html_005ftext_005f31.setStyleClass("formtext");
/*       */     
/* 11043 */     _jspx_th_html_005ftext_005f31.setSize("20");
/* 11044 */     int _jspx_eval_html_005ftext_005f31 = _jspx_th_html_005ftext_005f31.doStartTag();
/* 11045 */     if (_jspx_th_html_005ftext_005f31.doEndTag() == 5) {
/* 11046 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f31);
/* 11047 */       return true;
/*       */     }
/* 11049 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f31);
/* 11050 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fpassword_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11055 */     PageContext pageContext = _jspx_page_context;
/* 11056 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11058 */     PasswordTag _jspx_th_html_005fpassword_005f6 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 11059 */     _jspx_th_html_005fpassword_005f6.setPageContext(_jspx_page_context);
/* 11060 */     _jspx_th_html_005fpassword_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11062 */     _jspx_th_html_005fpassword_005f6.setProperty("password");
/*       */     
/* 11064 */     _jspx_th_html_005fpassword_005f6.setStyleClass("formtext");
/*       */     
/* 11066 */     _jspx_th_html_005fpassword_005f6.setSize("20");
/* 11067 */     int _jspx_eval_html_005fpassword_005f6 = _jspx_th_html_005fpassword_005f6.doStartTag();
/* 11068 */     if (_jspx_th_html_005fpassword_005f6.doEndTag() == 5) {
/* 11069 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f6);
/* 11070 */       return true;
/*       */     }
/* 11072 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f6);
/* 11073 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f32(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11078 */     PageContext pageContext = _jspx_page_context;
/* 11079 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11081 */     TextTag _jspx_th_html_005ftext_005f32 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 11082 */     _jspx_th_html_005ftext_005f32.setPageContext(_jspx_page_context);
/* 11083 */     _jspx_th_html_005ftext_005f32.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11085 */     _jspx_th_html_005ftext_005f32.setProperty("username");
/*       */     
/* 11087 */     _jspx_th_html_005ftext_005f32.setStyleClass("formtext default");
/* 11088 */     int _jspx_eval_html_005ftext_005f32 = _jspx_th_html_005ftext_005f32.doStartTag();
/* 11089 */     if (_jspx_th_html_005ftext_005f32.doEndTag() == 5) {
/* 11090 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f32);
/* 11091 */       return true;
/*       */     }
/* 11093 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f32);
/* 11094 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fpassword_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11099 */     PageContext pageContext = _jspx_page_context;
/* 11100 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11102 */     PasswordTag _jspx_th_html_005fpassword_005f7 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/* 11103 */     _jspx_th_html_005fpassword_005f7.setPageContext(_jspx_page_context);
/* 11104 */     _jspx_th_html_005fpassword_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11106 */     _jspx_th_html_005fpassword_005f7.setProperty("password");
/*       */     
/* 11108 */     _jspx_th_html_005fpassword_005f7.setStyleClass("formtext default");
/* 11109 */     int _jspx_eval_html_005fpassword_005f7 = _jspx_th_html_005fpassword_005f7.doStartTag();
/* 11110 */     if (_jspx_th_html_005fpassword_005f7.doEndTag() == 5) {
/* 11111 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f7);
/* 11112 */       return true;
/*       */     }
/* 11114 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f7);
/* 11115 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftextarea_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11120 */     PageContext pageContext = _jspx_page_context;
/* 11121 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11123 */     TextareaTag _jspx_th_html_005ftextarea_005f1 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 11124 */     _jspx_th_html_005ftextarea_005f1.setPageContext(_jspx_page_context);
/* 11125 */     _jspx_th_html_005ftextarea_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11127 */     _jspx_th_html_005ftextarea_005f1.setProperty("description");
/*       */     
/* 11129 */     _jspx_th_html_005ftextarea_005f1.setStyleClass("formtextarea xlarge");
/*       */     
/* 11131 */     _jspx_th_html_005ftextarea_005f1.setRows("4");
/*       */     
/* 11133 */     _jspx_th_html_005ftextarea_005f1.setCols("50");
/* 11134 */     int _jspx_eval_html_005ftextarea_005f1 = _jspx_th_html_005ftextarea_005f1.doStartTag();
/* 11135 */     if (_jspx_th_html_005ftextarea_005f1.doEndTag() == 5) {
/* 11136 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f1);
/* 11137 */       return true;
/*       */     }
/* 11139 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f1);
/* 11140 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fpassword_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11145 */     PageContext pageContext = _jspx_page_context;
/* 11146 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11148 */     PasswordTag _jspx_th_html_005fpassword_005f8 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 11149 */     _jspx_th_html_005fpassword_005f8.setPageContext(_jspx_page_context);
/* 11150 */     _jspx_th_html_005fpassword_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11152 */     _jspx_th_html_005fpassword_005f8.setProperty("passphrase");
/*       */     
/* 11154 */     _jspx_th_html_005fpassword_005f8.setStyleClass("formtext default");
/*       */     
/* 11156 */     _jspx_th_html_005fpassword_005f8.setSize("25");
/* 11157 */     int _jspx_eval_html_005fpassword_005f8 = _jspx_th_html_005fpassword_005f8.doStartTag();
/* 11158 */     if (_jspx_th_html_005fpassword_005f8.doEndTag() == 5) {
/* 11159 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f8);
/* 11160 */       return true;
/*       */     }
/* 11162 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f8);
/* 11163 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f33(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11168 */     PageContext pageContext = _jspx_page_context;
/* 11169 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11171 */     TextTag _jspx_th_html_005ftext_005f33 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 11172 */     _jspx_th_html_005ftext_005f33.setPageContext(_jspx_page_context);
/* 11173 */     _jspx_th_html_005ftext_005f33.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11175 */     _jspx_th_html_005ftext_005f33.setProperty("prompt");
/*       */     
/* 11177 */     _jspx_th_html_005ftext_005f33.setStyleClass("formtext default");
/* 11178 */     int _jspx_eval_html_005ftext_005f33 = _jspx_th_html_005ftext_005f33.doStartTag();
/* 11179 */     if (_jspx_th_html_005ftext_005f33.doEndTag() == 5) {
/* 11180 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f33);
/* 11181 */       return true;
/*       */     }
/* 11183 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f33);
/* 11184 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f34(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11189 */     PageContext pageContext = _jspx_page_context;
/* 11190 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11192 */     TextTag _jspx_th_html_005ftext_005f34 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 11193 */     _jspx_th_html_005ftext_005f34.setPageContext(_jspx_page_context);
/* 11194 */     _jspx_th_html_005ftext_005f34.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11196 */     _jspx_th_html_005ftext_005f34.setProperty("username");
/*       */     
/* 11198 */     _jspx_th_html_005ftext_005f34.setStyleClass("formtext default");
/* 11199 */     int _jspx_eval_html_005ftext_005f34 = _jspx_th_html_005ftext_005f34.doStartTag();
/* 11200 */     if (_jspx_th_html_005ftext_005f34.doEndTag() == 5) {
/* 11201 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f34);
/* 11202 */       return true;
/*       */     }
/* 11204 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f34);
/* 11205 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fpassword_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11210 */     PageContext pageContext = _jspx_page_context;
/* 11211 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11213 */     PasswordTag _jspx_th_html_005fpassword_005f9 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/* 11214 */     _jspx_th_html_005fpassword_005f9.setPageContext(_jspx_page_context);
/* 11215 */     _jspx_th_html_005fpassword_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11217 */     _jspx_th_html_005fpassword_005f9.setProperty("password");
/*       */     
/* 11219 */     _jspx_th_html_005fpassword_005f9.setStyleClass("formtext default");
/* 11220 */     int _jspx_eval_html_005fpassword_005f9 = _jspx_th_html_005fpassword_005f9.doStartTag();
/* 11221 */     if (_jspx_th_html_005fpassword_005f9.doEndTag() == 5) {
/* 11222 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f9);
/* 11223 */       return true;
/*       */     }
/* 11225 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f9);
/* 11226 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fpassword_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11231 */     PageContext pageContext = _jspx_page_context;
/* 11232 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11234 */     PasswordTag _jspx_th_html_005fpassword_005f10 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/* 11235 */     _jspx_th_html_005fpassword_005f10.setPageContext(_jspx_page_context);
/* 11236 */     _jspx_th_html_005fpassword_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11238 */     _jspx_th_html_005fpassword_005f10.setProperty("password");
/*       */     
/* 11240 */     _jspx_th_html_005fpassword_005f10.setStyleClass("formtext default");
/* 11241 */     int _jspx_eval_html_005fpassword_005f10 = _jspx_th_html_005fpassword_005f10.doStartTag();
/* 11242 */     if (_jspx_th_html_005fpassword_005f10.doEndTag() == 5) {
/* 11243 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f10);
/* 11244 */       return true;
/*       */     }
/* 11246 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f10);
/* 11247 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fpassword_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11252 */     PageContext pageContext = _jspx_page_context;
/* 11253 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11255 */     PasswordTag _jspx_th_html_005fpassword_005f11 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/* 11256 */     _jspx_th_html_005fpassword_005f11.setPageContext(_jspx_page_context);
/* 11257 */     _jspx_th_html_005fpassword_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11259 */     _jspx_th_html_005fpassword_005f11.setProperty("password");
/*       */     
/* 11261 */     _jspx_th_html_005fpassword_005f11.setStyleClass("formtext default");
/* 11262 */     int _jspx_eval_html_005fpassword_005f11 = _jspx_th_html_005fpassword_005f11.doStartTag();
/* 11263 */     if (_jspx_th_html_005fpassword_005f11.doEndTag() == 5) {
/* 11264 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f11);
/* 11265 */       return true;
/*       */     }
/* 11267 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f11);
/* 11268 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f35(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11273 */     PageContext pageContext = _jspx_page_context;
/* 11274 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11276 */     TextTag _jspx_th_html_005ftext_005f35 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 11277 */     _jspx_th_html_005ftext_005f35.setPageContext(_jspx_page_context);
/* 11278 */     _jspx_th_html_005ftext_005f35.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11280 */     _jspx_th_html_005ftext_005f35.setProperty("instance");
/*       */     
/* 11282 */     _jspx_th_html_005ftext_005f35.setStyleClass("formtext default");
/* 11283 */     int _jspx_eval_html_005ftext_005f35 = _jspx_th_html_005ftext_005f35.doStartTag();
/* 11284 */     if (_jspx_th_html_005ftext_005f35.doEndTag() == 5) {
/* 11285 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f35);
/* 11286 */       return true;
/*       */     }
/* 11288 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f35);
/* 11289 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f36(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11294 */     PageContext pageContext = _jspx_page_context;
/* 11295 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11297 */     TextTag _jspx_th_html_005ftext_005f36 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 11298 */     _jspx_th_html_005ftext_005f36.setPageContext(_jspx_page_context);
/* 11299 */     _jspx_th_html_005ftext_005f36.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11301 */     _jspx_th_html_005ftext_005f36.setProperty("instance");
/*       */     
/* 11303 */     _jspx_th_html_005ftext_005f36.setStyleClass("formtext default");
/* 11304 */     int _jspx_eval_html_005ftext_005f36 = _jspx_th_html_005ftext_005f36.doStartTag();
/* 11305 */     if (_jspx_th_html_005ftext_005f36.doEndTag() == 5) {
/* 11306 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f36);
/* 11307 */       return true;
/*       */     }
/* 11309 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f36);
/* 11310 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f37(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11315 */     PageContext pageContext = _jspx_page_context;
/* 11316 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11318 */     TextTag _jspx_th_html_005ftext_005f37 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 11319 */     _jspx_th_html_005ftext_005f37.setPageContext(_jspx_page_context);
/* 11320 */     _jspx_th_html_005ftext_005f37.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11322 */     _jspx_th_html_005ftext_005f37.setProperty("instance");
/*       */     
/* 11324 */     _jspx_th_html_005ftext_005f37.setStyleClass("formtext default");
/* 11325 */     int _jspx_eval_html_005ftext_005f37 = _jspx_th_html_005ftext_005f37.doStartTag();
/* 11326 */     if (_jspx_th_html_005ftext_005f37.doEndTag() == 5) {
/* 11327 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f37);
/* 11328 */       return true;
/*       */     }
/* 11330 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f37);
/* 11331 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f38(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11336 */     PageContext pageContext = _jspx_page_context;
/* 11337 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11339 */     TextTag _jspx_th_html_005ftext_005f38 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 11340 */     _jspx_th_html_005ftext_005f38.setPageContext(_jspx_page_context);
/* 11341 */     _jspx_th_html_005ftext_005f38.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11343 */     _jspx_th_html_005ftext_005f38.setProperty("instance");
/*       */     
/* 11345 */     _jspx_th_html_005ftext_005f38.setStyleClass("formtext default");
/*       */     
/* 11347 */     _jspx_th_html_005ftext_005f38.setValue("mysql");
/* 11348 */     int _jspx_eval_html_005ftext_005f38 = _jspx_th_html_005ftext_005f38.doStartTag();
/* 11349 */     if (_jspx_th_html_005ftext_005f38.doEndTag() == 5) {
/* 11350 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f38);
/* 11351 */       return true;
/*       */     }
/* 11353 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f38);
/* 11354 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f39(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11359 */     PageContext pageContext = _jspx_page_context;
/* 11360 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11362 */     TextTag _jspx_th_html_005ftext_005f39 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 11363 */     _jspx_th_html_005ftext_005f39.setPageContext(_jspx_page_context);
/* 11364 */     _jspx_th_html_005ftext_005f39.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11366 */     _jspx_th_html_005ftext_005f39.setProperty("instance");
/*       */     
/* 11368 */     _jspx_th_html_005ftext_005f39.setStyleClass("formtext default");
/* 11369 */     int _jspx_eval_html_005ftext_005f39 = _jspx_th_html_005ftext_005f39.doStartTag();
/* 11370 */     if (_jspx_th_html_005ftext_005f39.doEndTag() == 5) {
/* 11371 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f39);
/* 11372 */       return true;
/*       */     }
/* 11374 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f39);
/* 11375 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11380 */     PageContext pageContext = _jspx_page_context;
/* 11381 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11383 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fdisabled_005fnobody.get(RadioTag.class);
/* 11384 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 11385 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11387 */     _jspx_th_html_005fradio_005f0.setProperty("snmpVersionValue");
/*       */     
/* 11389 */     _jspx_th_html_005fradio_005f0.setValue("v1v2");
/*       */     
/* 11391 */     _jspx_th_html_005fradio_005f0.setDisabled(false);
/*       */     
/* 11393 */     _jspx_th_html_005fradio_005f0.setOnclick("javascript:snmpVersionSelect('v1v2')");
/*       */     
/* 11395 */     _jspx_th_html_005fradio_005f0.setStyle("position:relative;");
/* 11396 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 11397 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 11398 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fdisabled_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 11399 */       return true;
/*       */     }
/* 11401 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fdisabled_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 11402 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11407 */     PageContext pageContext = _jspx_page_context;
/* 11408 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11410 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 11411 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 11412 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11414 */     _jspx_th_fmt_005fmessage_005f3.setKey("V1/V2");
/* 11415 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 11416 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 11417 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 11418 */       return true;
/*       */     }
/* 11420 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 11421 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11426 */     PageContext pageContext = _jspx_page_context;
/* 11427 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11429 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 11430 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 11431 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11433 */     _jspx_th_html_005fradio_005f1.setProperty("snmpVersionValue");
/*       */     
/* 11435 */     _jspx_th_html_005fradio_005f1.setValue("v3");
/*       */     
/* 11437 */     _jspx_th_html_005fradio_005f1.setOnclick("javascript:snmpVersionSelect('v3')");
/*       */     
/* 11439 */     _jspx_th_html_005fradio_005f1.setStyle("position:relative;");
/* 11440 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 11441 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 11442 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 11443 */       return true;
/*       */     }
/* 11445 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 11446 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11451 */     PageContext pageContext = _jspx_page_context;
/* 11452 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11454 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 11455 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 11456 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11458 */     _jspx_th_fmt_005fmessage_005f4.setKey("V3");
/* 11459 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 11460 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 11461 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 11462 */       return true;
/*       */     }
/* 11464 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 11465 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fpassword_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11470 */     PageContext pageContext = _jspx_page_context;
/* 11471 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11473 */     PasswordTag _jspx_th_html_005fpassword_005f12 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/* 11474 */     _jspx_th_html_005fpassword_005f12.setPageContext(_jspx_page_context);
/* 11475 */     _jspx_th_html_005fpassword_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11477 */     _jspx_th_html_005fpassword_005f12.setProperty("snmpCommunityString");
/*       */     
/* 11479 */     _jspx_th_html_005fpassword_005f12.setStyleClass("formtext small");
/* 11480 */     int _jspx_eval_html_005fpassword_005f12 = _jspx_th_html_005fpassword_005f12.doStartTag();
/* 11481 */     if (_jspx_th_html_005fpassword_005f12.doEndTag() == 5) {
/* 11482 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f12);
/* 11483 */       return true;
/*       */     }
/* 11485 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f12);
/* 11486 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f40(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11491 */     PageContext pageContext = _jspx_page_context;
/* 11492 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11494 */     TextTag _jspx_th_html_005ftext_005f40 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 11495 */     _jspx_th_html_005ftext_005f40.setPageContext(_jspx_page_context);
/* 11496 */     _jspx_th_html_005ftext_005f40.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11498 */     _jspx_th_html_005ftext_005f40.setProperty("snmpUserName");
/*       */     
/* 11500 */     _jspx_th_html_005ftext_005f40.setStyleClass("formtext default");
/* 11501 */     int _jspx_eval_html_005ftext_005f40 = _jspx_th_html_005ftext_005f40.doStartTag();
/* 11502 */     if (_jspx_th_html_005ftext_005f40.doEndTag() == 5) {
/* 11503 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f40);
/* 11504 */       return true;
/*       */     }
/* 11506 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f40);
/* 11507 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005ftext_005f41(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11512 */     PageContext pageContext = _jspx_page_context;
/* 11513 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11515 */     TextTag _jspx_th_html_005ftext_005f41 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 11516 */     _jspx_th_html_005ftext_005f41.setPageContext(_jspx_page_context);
/* 11517 */     _jspx_th_html_005ftext_005f41.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11519 */     _jspx_th_html_005ftext_005f41.setProperty("snmpContextName");
/*       */     
/* 11521 */     _jspx_th_html_005ftext_005f41.setStyleClass("formtext default");
/* 11522 */     int _jspx_eval_html_005ftext_005f41 = _jspx_th_html_005ftext_005f41.doStartTag();
/* 11523 */     if (_jspx_th_html_005ftext_005f41.doEndTag() == 5) {
/* 11524 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f41);
/* 11525 */       return true;
/*       */     }
/* 11527 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f41);
/* 11528 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fpassword_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11533 */     PageContext pageContext = _jspx_page_context;
/* 11534 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11536 */     PasswordTag _jspx_th_html_005fpassword_005f13 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/* 11537 */     _jspx_th_html_005fpassword_005f13.setPageContext(_jspx_page_context);
/* 11538 */     _jspx_th_html_005fpassword_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11540 */     _jspx_th_html_005fpassword_005f13.setProperty("snmpAuthPassword");
/*       */     
/* 11542 */     _jspx_th_html_005fpassword_005f13.setStyleClass("formtext default");
/* 11543 */     int _jspx_eval_html_005fpassword_005f13 = _jspx_th_html_005fpassword_005f13.doStartTag();
/* 11544 */     if (_jspx_th_html_005fpassword_005f13.doEndTag() == 5) {
/* 11545 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f13);
/* 11546 */       return true;
/*       */     }
/* 11548 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f13);
/* 11549 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fpassword_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11554 */     PageContext pageContext = _jspx_page_context;
/* 11555 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11557 */     PasswordTag _jspx_th_html_005fpassword_005f14 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/* 11558 */     _jspx_th_html_005fpassword_005f14.setPageContext(_jspx_page_context);
/* 11559 */     _jspx_th_html_005fpassword_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11561 */     _jspx_th_html_005fpassword_005f14.setProperty("snmpPrivPassword");
/*       */     
/* 11563 */     _jspx_th_html_005fpassword_005f14.setStyleClass("formtext default");
/* 11564 */     int _jspx_eval_html_005fpassword_005f14 = _jspx_th_html_005fpassword_005f14.doStartTag();
/* 11565 */     if (_jspx_th_html_005fpassword_005f14.doEndTag() == 5) {
/* 11566 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f14);
/* 11567 */       return true;
/*       */     }
/* 11569 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f14);
/* 11570 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f24, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11575 */     PageContext pageContext = _jspx_page_context;
/* 11576 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11578 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 11579 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 11580 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f24);
/*       */     
/* 11582 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 11583 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 11584 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 11585 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 11586 */       return true;
/*       */     }
/* 11588 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 11589 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f25, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11594 */     PageContext pageContext = _jspx_page_context;
/* 11595 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11597 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 11598 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 11599 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f25);
/*       */     
/* 11601 */     _jspx_th_c_005fout_005f5.setValue("${param.haid}");
/* 11602 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 11603 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 11604 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 11605 */       return true;
/*       */     }
/* 11607 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 11608 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f24, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11613 */     PageContext pageContext = _jspx_page_context;
/* 11614 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11616 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 11617 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 11618 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f24);
/*       */     
/* 11620 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 11621 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 11622 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 11623 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 11624 */       return true;
/*       */     }
/* 11626 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 11627 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11632 */     PageContext pageContext = _jspx_page_context;
/* 11633 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11635 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 11636 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 11637 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*       */     
/* 11639 */     _jspx_th_html_005fhidden_005f0.setProperty("appmanageros");
/* 11640 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 11641 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 11642 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 11643 */       return true;
/*       */     }
/* 11645 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 11646 */     return false;
/*       */   }
/*       */   
/*       */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*       */   {
/* 11651 */     PageContext pageContext = _jspx_page_context;
/* 11652 */     JspWriter out = _jspx_page_context.getOut();
/*       */     
/* 11654 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 11655 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 11656 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*       */     
/* 11658 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*       */     
/* 11660 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 11661 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 11662 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 11663 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 11664 */       return true;
/*       */     }
/* 11666 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 11667 */     return false;
/*       */   }
/*       */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\HostDiscoveryForm_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */