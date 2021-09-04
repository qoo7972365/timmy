package com.adventnet.appmanager.webclient.fault.alarm;

import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
import com.adventnet.appmanager.customfields.MyFields;
import com.adventnet.appmanager.db.AMConnectionPool;
import com.adventnet.appmanager.db.DBQueryUtil;
import com.adventnet.appmanager.logging.AMLog;
import com.adventnet.appmanager.reporting.ReportUtilities;
import com.adventnet.appmanager.struts.actions.AMAlarmViewAction;
import com.adventnet.appmanager.struts.beans.AlarmGraphUtil;
import com.adventnet.appmanager.struts.beans.AlarmUtil;
import com.adventnet.appmanager.struts.beans.ClientDBUtil;
import com.adventnet.appmanager.struts.beans.DependantMOUtil;
import com.adventnet.appmanager.util.Constants;
import com.adventnet.appmanager.util.DBUtil;
import com.adventnet.appmanager.util.EnterpriseUtil;
import com.adventnet.appmanager.webclient.util.SavePreferencesAction;
import com.adventnet.nms.fe.common.CustomViewException;
import com.adventnet.nms.fe.common.TableColumn;
import com.adventnet.nms.util.NmsUtil;
import com.adventnet.nms.util.RunProcessInterface;
import com.adventnet.nms.webclient.common.WebClientUtil;
import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
import com.manageengine.it360.sp.util.It360SPUserManagementUtil;
import com.me.apm.cmdb.APMHDSettingsUtil;
import com.me.apm.cmdb.APMHelpDeskUtil;
import com.me.helpdesk.object.TicketSettings;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.json.JSONObject;

public class AlarmViewAction
  extends Action
{
  public ActionForward execute(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException, CustomViewException
  {
    Statement localStatement1 = null;
    Statement localStatement2 = null;
    Statement localStatement3 = null;
    try
    {
      ManagedApplication localManagedApplication = new ManagedApplication();
      localManagedApplication.getReloadPeriod(paramHttpServletRequest);
      Object localObject1 = null;
      Hashtable localHashtable1 = null;
      Vector localVector1 = null;
      TableColumn[] arrayOfTableColumn = null;
      String str1 = "Alerts.5";
      String str2 = null;
      int i = 0;
      int j = 0;
      int k = 0;
      int m = 0;
      int n = 0;
      Cookie[] arrayOfCookie = paramHttpServletRequest.getCookies();
      HashMap localHashMap = new HashMap();
      for (int i1 = 0; i1 < arrayOfCookie.length; i1++)
      {
        if ((arrayOfCookie[i1].getName().equals("alarm_severity")) && (arrayOfCookie[i1].getValue() != null))
        {
          str1 = arrayOfCookie[i1].getValue();
          localHashMap.put("severity", arrayOfCookie[i1].getValue());
          paramHttpServletRequest.setAttribute("alarm_severity", Boolean.valueOf(true));
          j = 1;
        }
        if ((arrayOfCookie[i1].getName().equals("alarm_time")) && (arrayOfCookie[i1].getValue() != null))
        {
          localHashMap.put("time", arrayOfCookie[i1].getValue());
          paramHttpServletRequest.setAttribute("alarm_time", Boolean.valueOf(true));
          k = 1;
        }
        if ((arrayOfCookie[i1].getName().equals("alarm_haid")) && (arrayOfCookie[i1].getValue() != null))
        {
          localHashMap.put("haid", arrayOfCookie[i1].getValue());
          paramHttpServletRequest.setAttribute("alarm_haid", Boolean.valueOf(true));
          m = 1;
        }
        if ((arrayOfCookie[i1].getName().equals("alarm_monitor")) && (arrayOfCookie[i1].getValue() != null))
        {
          localHashMap.put("monitortype", URLDecoder.decode(arrayOfCookie[i1].getValue(), "UTF-8"));
          paramHttpServletRequest.setAttribute("alarm_monitor", Boolean.valueOf(true));
          n = 1;
        }
        if ((arrayOfCookie[i1].getName().equals("alarm_trap_tab")) && (arrayOfCookie[i1].getValue() != null))
        {
          str2 = arrayOfCookie[i1].getValue();
          if (str2.equalsIgnoreCase("unsoltrap")) {
            str1 = "Trap";
          }
          localHashMap.put("trapView", arrayOfCookie[i1].getValue());
          paramHttpServletRequest.setAttribute("apmtrapview", Boolean.valueOf(true));
          paramHttpServletRequest.setAttribute("apmtrapValue", str2);
        }
        if ((arrayOfCookie[i1].getName().equals("alarm_search")) && (arrayOfCookie[i1].getValue() != null))
        {
          localHashMap.put("searchParams", URLDecoder.decode(arrayOfCookie[i1].getValue(), "UTF-8"));
          paramHttpServletRequest.setAttribute("alarm_search", Boolean.valueOf(true));
          i = 1;
        }
      }
      if ((j == 0) && ((k != 0) || (m != 0) || (n != 0) || (i != 0)))
      {
        str1 = "Alerts";
        localHashMap.put("severity", "Alerts");
        paramHttpServletRequest.setAttribute("alarm_severity", Boolean.valueOf(true));
      }
      String str3 = "";
      long l1 = 0L;
      String str4 = paramHttpServletRequest.getParameter("isCorrelated");
      if (str4 != null) {
        paramHttpServletRequest.getSession().setAttribute("isCorrelation", str4);
      }
      String str5 = (String)paramHttpServletRequest.getSession().getAttribute("isCorrelation");
      String str6 = (String)paramHttpServletRequest.getSession().getAttribute("userName");
      String str7 = System.getProperty("am.dbserver.type");
      String str8 = paramHttpServletRequest.getParameter("viewLength");
      String str9 = paramHttpServletRequest.getParameter("FROM_INDEX");
      String str10 = paramHttpServletRequest.getParameter("orderByColumn");
      if (str10 == null) {
        str10 = "MODTIME";
      }
      if (str10.equals("message")) {
        str10 = "MMESSAGE";
      }
      String str11 = paramHttpServletRequest.getParameter("entity");
      String str12 = "1";
      if (paramHttpServletRequest.getParameter("PAGE_NUMBER") != null) {
        str12 = paramHttpServletRequest.getParameter("PAGE_NUMBER");
      }
      if ((str12 == null) || (str12.trim().length() == 0)) {
        str12 = "1";
      }
      String str13 = paramHttpServletRequest.getParameter("severity");
      String str14 = "like";
      if (DBQueryUtil.isPgsql()) {
        str14 = "ilike";
      }
      paramHttpServletRequest.setAttribute("isAlertsPage", "true");
      Vector localVector2 = WebClientUtil.getModuleIncrements(str6, "Alerts");
      String str15 = "";
      boolean bool = false;
      if (EnterpriseUtil.isIt360MSPEdition())
      {
        str15 = paramHttpServletRequest.getRemoteUser();
        localObject2 = new It360SPUserManagementUtil();
        bool = ((It360SPUserManagementUtil)localObject2).isBSGUser(str15);
      }
      paramHttpServletRequest.setAttribute("PAGE_LENGTHS", localVector2);
      Object localObject2 = null;
      Vector localVector3 = new Vector();
      String str16 = "select severity,count(*) from Alert";
      String str17 = paramHttpServletRequest.getParameter("haid");
      if (localHashMap.get("haid") != null) {
        str17 = (String)localHashMap.get("haid");
      }
      ArrayList localArrayList1 = Constants.getAllMonitorLevelResorceTypes(true);
      if (i != 0) {
        localArrayList1.add("'Trap'");
      }
      String str18 = localArrayList1.toString().replace("[", "");
      str18 = str18.toString().replace("]", "");
      String str19 = str18;
      if ("mgalarms".equalsIgnoreCase(str17)) {
        str19 = "'HAI'";
      } else if ("allmonitoralarms".equalsIgnoreCase(str17)) {
        str19 = str18.replace(", 'HAI'", "");
      }
      String str20 = "";
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(" inner join AM_ManagedObject  on RESOURCEID=Alert.source and TYPE in (" + str19 + ") and Alert.entity not like '%_721'");
      str20 = " inner join AM_ManagedObject  on RESOURCEID=Alert.source and TYPE in (" + str18 + ") and Alert.entity not like '%_721'";
      String str21 = "";
      String str22 = " WHERE (GROUPNAME='AppManager_Component') ";
      if ((str5 == null) || ((str5 != null) && (str5.equals("true"))))
      {
        localStringBuilder.setLength(0);
        localStringBuilder.append(" inner join AM_ManagedObject on Alert.SOURCE=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE in (" + str19 + ") and AM_ManagedObject.TYPE not like 'OpManager-Interface%'");
        str20 = " inner join AM_ManagedObject on Alert.SOURCE=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE in (" + str18 + ") and AM_ManagedObject.TYPE not like 'OpManager-Interface%'";
        str22 = " AND (GROUPNAME='AppManager') ";
      }
      String str23 = "";
      String str24 = " group by severity";
      int i2 = 0;
      String str25 = null;
      if ((ClientDBUtil.isPrivilegedUser(paramHttpServletRequest)) || (EnterpriseUtil.isIt360MSPEdition()))
      {
        i2 = 1;
        if (EnterpriseUtil.isIt360MSPEdition())
        {
          if (bool)
          {
            localVector3 = CustomerManagementAPI.filterUserBasedResourceIds(str15, new Vector());
          }
          else
          {
            localObject3 = paramHttpServletRequest.getParameter("allcustomer");
            if ("true".equals(localObject3)) {
              paramHttpServletRequest.getSession().setAttribute("allCustomer", "allCustomer");
            }
            localVector3 = CustomerManagementAPI.filterResourceIds(paramHttpServletRequest, false);
          }
          localObject3 = CustomerManagementAPI.getSiteProp(paramHttpServletRequest);
          if ((localObject3 == null) || (((Properties)localObject3).size() == 0))
          {
            localObject4 = EnterpriseUtil.filterCustSpecificHAIds(paramHttpServletRequest);
            int i3 = ((Vector)localObject4).size();
            if (i3 > 0) {
              for (int i4 = 0; i4 < i3; i4++)
              {
                String str29 = ((Vector)localObject4).get(i4).toString();
                if ((!bool) && (!localVector3.contains(str29))) {
                  localVector3.add(str29);
                }
              }
            }
          }
          localObject4 = new ArrayList(localVector3);
          ((ArrayList)localObject4).add(Integer.valueOf(-1));
          AMLog.debug("selectedMonitors:" + localObject4);
          String str26 = ((ArrayList)localObject4).toString().replace("[", "(");
          str26 = str26.replace("]", ")");
          str23 = " and Alert.source in " + str26 + " ";
        }
        else if (Constants.isUserResourceEnabled())
        {
          str25 = Constants.getLoginUserid(paramHttpServletRequest);
          str23 = " inner join AM_USERRESOURCESTABLE on AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + str25;
        }
        else
        {
          localVector3 = ClientDBUtil.getResourceIdentity(paramHttpServletRequest.getRemoteUser());
          if (localVector3.size() == 0) {
            localVector3.add("-1");
          }
          localObject3 = paramHttpServletRequest.getRemoteUser();
          localObject4 = null;
          try
          {
            localObject4 = localVector3.toString();
            localObject4 = ((String)localObject4).substring(1, ((String)localObject4).length());
            localObject4 = "(" + (String)localObject4;
            localObject4 = ((String)localObject4).substring(0, ((String)localObject4).length() - 1);
            localObject4 = (String)localObject4 + ")";
          }
          catch (Exception localException2)
          {
            localException2.printStackTrace();
          }
          str23 = " and Alert.source in " + (String)localObject4 + " ";
        }
      }
      Object localObject3 = str16 + str20 + str22 + str23 + str24;
      localObject2 = DBUtil.executeQueryToGetAlertCountDetails((String)localObject3);
      paramHttpServletRequest.setAttribute("alertdetails", localObject2);
      Object localObject4 = (String)((Hashtable)localObject2).get("Critical");
      String str27 = (String)((Hashtable)localObject2).get("Warning");
      String str28 = (String)((Hashtable)localObject2).get("Clear");
      int i5 = Integer.parseInt((String)localObject4) + Integer.parseInt(str27) + Integer.parseInt(str28);
      paramHttpServletRequest.setAttribute("totalAlarm", Integer.valueOf(i5));
      SavePreferencesAction localSavePreferencesAction = new SavePreferencesAction();
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      if ((str8 == null) || (str8.equals("")))
      {
        if (localHttpSession.getAttribute("alarm_viewlength") != null)
        {
          str8 = (String)localHttpSession.getAttribute("alarm_viewlength");
        }
        else
        {
          str8 = (String)localVector2.elementAt(0);
          localHttpSession.setAttribute("alarm_viewlength", str8);
          localSavePreferencesAction.savealarmviewlength(paramHttpServletRequest);
        }
      }
      else
      {
        localHttpSession.setAttribute("alarm_viewlength", str8);
        localSavePreferencesAction.savealarmviewlength(paramHttpServletRequest);
      }
      str8 = str8.trim();
      if ((str9 == null) || ("".equalsIgnoreCase(str9.trim()))) {
        str9 = "0";
      }
      int i6 = Integer.parseInt(str9);
      if (i6 > 0) {
        i6 -= 1;
      }
      Object localObject6;
      if ((str1 != null) && (str1.equals("Trap")))
      {
        localObject5 = AMAlarmViewAction.executeTrapView();
        paramHttpServletRequest.setAttribute("list", localObject5);
        if ("true".equalsIgnoreCase(paramHttpServletRequest.getParameter("ajaxRequest")))
        {
          paramHttpServletRequest.setAttribute("ajaxRequest", Boolean.valueOf(true));
          localObject6 = paramActionMapping.findForward("unsolicitedTrapView");
          return (ActionForward)localObject6;
        }
        paramHttpServletRequest.setAttribute("viewId", str1);
        paramHttpServletRequest.setAttribute("reloadLocation", Boolean.valueOf(true));
        localObject6 = paramActionMapping.findForward("trapView");
        return (ActionForward)localObject6;
      }
      APMHelpDeskUtil.setTicketingAttributes(paramHttpServletRequest);
      paramHttpServletRequest.setAttribute("HelpKey", "Alerts Page");
      paramHttpServletRequest.setAttribute("viewId", str1);
      paramHttpServletRequest.setAttribute("displayName", str3);
      Object localObject5 = new ActionMessages();
      isAlertFEInitialized(5);
      Object localObject9;
      try
      {
        localObject6 = "-1";
        localObject7 = "";
        String str30 = "";
        str32 = paramHttpServletRequest.getParameter("timeId");
        if (localHashMap.get("time") != null) {
          str32 = (String)localHashMap.get("time");
        }
        long l4;
        long l7;
        if ("Alerts.2".equalsIgnoreCase(str32))
        {
          l4 = System.currentTimeMillis();
          l7 = l4 - 3600000L;
          localObject7 = " and MODTIME > " + l7 + " ";
        }
        if ("Alerts.32".equalsIgnoreCase(str32))
        {
          l4 = System.currentTimeMillis();
          l7 = l4 - 7200000L;
          localObject7 = " and MODTIME > " + l7 + " ";
        }
        if ("Alerts.34".equalsIgnoreCase(str32))
        {
          l4 = System.currentTimeMillis();
          l7 = l4 - 14400000L;
          localObject7 = " and MODTIME > " + l7 + " ";
        }
        if ("Alerts.36".equalsIgnoreCase(str32))
        {
          l4 = System.currentTimeMillis();
          l7 = l4 - 21600000L;
          localObject7 = " and MODTIME > " + l7 + " ";
        }
        if ("Alerts.37".equalsIgnoreCase(str32))
        {
          l4 = System.currentTimeMillis();
          l7 = l4 - 43200000L;
          localObject7 = " and MODTIME > " + l7 + " ";
        }
        if ("Alerts.4".equalsIgnoreCase(str32))
        {
          l4 = System.currentTimeMillis();
          l7 = l4 - 86400000L;
          localObject7 = " and MODTIME > " + l7 + " ";
        }
        long[] arrayOfLong;
        if ("Alerts.38".equalsIgnoreCase(str32))
        {
          arrayOfLong = ReportUtilities.getTimeStamp("0");
          localObject7 = " and MODTIME > " + arrayOfLong[0] + " ";
        }
        if ("Alerts.39".equalsIgnoreCase(str32))
        {
          arrayOfLong = ReportUtilities.getTimeStamp("3");
          localObject7 = " and MODTIME > " + arrayOfLong[0] + " and MODTIME < " + arrayOfLong[1] + " ";
        }
        if ("Alerts.4".equalsIgnoreCase(str32))
        {
          long l5 = System.currentTimeMillis();
          l7 = l5 - 86400000L;
          localObject7 = " and MODTIME > " + l7 + " ";
        }
        String str33 = "";
        if ("Alerts.1".equalsIgnoreCase(str1))
        {
          str30 = "AND SEVERITY=1 ";
          str33 = "cr";
        }
        if ("Alerts.14".equalsIgnoreCase(str1))
        {
          str30 = "AND SEVERITY=4 ";
          str33 = "wa";
        }
        if ("Alerts.15".equalsIgnoreCase(str1))
        {
          str30 = "AND SEVERITY=5 ";
          str33 = "cl";
        }
        if ("Alerts".equalsIgnoreCase(str1)) {
          str33 = "all";
        }
        if ("Alerts.5".equalsIgnoreCase(str1)) {
          str30 = "AND (SEVERITY=1 OR SEVERITY=4) ";
        }
        Object localObject12;
        if (i != 0)
        {
          localObject8 = new JSONObject((String)localHashMap.get("searchParams"));
          localObject9 = (String)((JSONObject)localObject8).get("alarmSearchStatus");
          localObject10 = (String)((JSONObject)localObject8).get("alarmSearchMessage");
          localObject11 = (String)((JSONObject)localObject8).get("alarmSearchName");
          localObject12 = (String)((JSONObject)localObject8).get("alarmSearchUser");
          localObject14 = (String)((JSONObject)localObject8).get("alarmSearchType");
          if ((localObject14 != null) && (((String)localObject14).trim().length() >= 2))
          {
            localStringBuilder.setLength(0);
            localStringBuilder.append(" join AM_ManagedObject on AM_ManagedObject.RESOURCEID=Alert.SOURCE and Alert.SOURCE in (select RESOURCEID from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and (AM_ManagedResourceType.DISPLAYNAME " + str14 + " '%" + (String)localObject14 + "%'  or AM_ManagedResourceType.SUBGROUP " + str14 + " '%" + (String)localObject14 + "%')) and Alert.entity not like '%_721'");
          }
          if ((localObject10 != null) && (((String)localObject10).trim().length() >= 2)) {
            localStringBuilder.append(" and MMESSAGE " + str14 + " '%" + (String)localObject10 + "%' ");
          }
          if ((localObject11 != null) && (((String)localObject11).trim().length() >= 2)) {
            localStringBuilder.append(" and AM_ManagedObject.DISPLAYNAME " + str14 + " '%" + (String)localObject11 + "%'");
          }
          if ((localObject12 != null) && (((String)localObject12).trim().length() >= 2)) {
            if ("none".equalsIgnoreCase((String)localObject12)) {
              localStringBuilder.append(" and WHO = 'NULL' ");
            } else {
              localStringBuilder.append(" and WHO " + str14 + " '%" + (String)localObject12 + "%' ");
            }
          }
          if (EnterpriseUtil.isAdminServer())
          {
            String str34 = paramHttpServletRequest.getParameter("alarmSearchMASName");
            if ((str34 != null) && (str34.trim().length() >= 2))
            {
              localObject16 = null;
              long l10 = 0L;
              try
              {
                if (("admin server".equalsIgnoreCase(str34)) || ("admin".equalsIgnoreCase(str34)))
                {
                  localStringBuilder.append(" and Alert.SOURCE < " + EnterpriseUtil.RANGE);
                }
                else
                {
                  localObject16 = AMConnectionPool.executeQueryStmt("select ALLOTED_GLOBAL_RANGE from AM_MAS_SERVER where DISPLAYNAME like '%" + str34 + "%'");
                  if (((ResultSet)localObject16).next()) {
                    l10 = ((ResultSet)localObject16).getInt("ALLOTED_GLOBAL_RANGE");
                  } else {
                    localStringBuilder.append(" and Alert.SOURCE = -1");
                  }
                }
              }
              catch (Exception localException5)
              {
                localException5.printStackTrace();
              }
              finally
              {
                if (localObject16 != null) {
                  AMConnectionPool.closeResultSet((ResultSet)localObject16);
                }
              }
              if (l10 > 0L) {
                localStringBuilder.append(" and Alert.SOURCE > " + l10 + " and Alert.SOURCE < " + (l10 + EnterpriseUtil.RANGE));
              }
            }
            paramHttpServletRequest.setAttribute("searchMAS", str34);
          }
          paramHttpServletRequest.setAttribute("searchStatus", localObject9);
          paramHttpServletRequest.setAttribute("searchMessage", localObject10);
          paramHttpServletRequest.setAttribute("searchName", localObject11);
          paramHttpServletRequest.setAttribute("searchType", (String)((JSONObject)localObject8).get("alarmSearchType"));
          paramHttpServletRequest.setAttribute("searchWho", localObject12);
        }
        Object localObject8 = "";
        if ((str17 != null) && (!str17.equalsIgnoreCase("selectmg")) && (str17.trim().length() > 0) && (!"allmonitoralarms".equals(str17)) && (!"mgalarms".equals(str17)))
        {
          localObject9 = new Vector();
          ((Vector)localObject9).add(str17);
          ManagedApplication.getChildIDs((Vector)localObject9, str17);
          localObject10 = DependantMOUtil.getDependantResourceIDS((Vector)localObject9);
          if (((Vector)localObject10).size() > 0)
          {
            if (!Constants.isIt360) {
              ((Vector)localObject10).remove(str17);
            }
            localObject8 = " and " + DependantMOUtil.getCondition("Alert.SOURCE", (Vector)localObject10);
          }
        }
        localObject9 = null;
        Object localObject10 = "";
        if (localHashMap.get("monitortype") != null) {
          localObject9 = URLDecoder.decode((String)localHashMap.get("monitortype"), "UTF-8");
        }
        if ("conftrap".equals(str2)) {
          localObject9 = "Trap";
        } else if ("jmx".equals(str2)) {
          localObject9 = "JMXNotification";
        }
        Object localObject13;
        if (localObject9 != null)
        {
          if ((((String)localObject9).equalsIgnoreCase("Trap")) || (((String)localObject9).equalsIgnoreCase("JMXNotification")))
          {
            localObject9 = (String)localObject9 + ",";
            str30 = "";
            localStringBuilder.setLength(0);
            str22 = " where (GROUPNAME='AppManager') ";
          }
          localObject11 = new String[1];
          if (((String)localObject9).indexOf(",") != -1) {
            localObject11 = ((String)localObject9).split(",");
          }
          try
          {
            localObject12 = new ArrayList(Arrays.asList((Object[])localObject11));
            if (((ArrayList)localObject12).contains("Windows"))
            {
              ((ArrayList)localObject12).remove(((ArrayList)localObject12).indexOf("Windows"));
              ((ArrayList)localObject12).addAll(new ArrayList(Arrays.asList(Constants.server_windows.split(","))));
              if (((ArrayList)localObject12).size() > 0) {
                localObject11 = ((ArrayList)localObject12).toString().substring(1, ((ArrayList)localObject12).toString().length() - 1).split(",");
              }
            }
          }
          catch (Exception localException4)
          {
            localException4.printStackTrace();
          }
          paramHttpServletRequest.setAttribute("checkBoxList", localObject9);
          localObject13 = new Vector();
          if (i2 == 0)
          {
            localObject13 = AMAlarmViewAction.getMonitorIdsforAlarm((String[])localObject11);
          }
          else
          {
            localObject14 = null;
            if (Constants.isUserResourceEnabled()) {
              localObject14 = Constants.getLoginUserid(paramHttpServletRequest);
            } else {
              localObject14 = paramHttpServletRequest.getRemoteUser();
            }
            localObject13 = AMAlarmViewAction.getMonitorIdsforAlarm((String[])localObject11, (String)localObject14);
          }
          localObject10 = " and " + DependantMOUtil.getCondition("Alert.SOURCE", (Vector)localObject13);
        }
        paramHttpServletRequest.setAttribute("timeId", str32);
        paramHttpServletRequest.setAttribute("haid", str17);
        Object localObject11 = AMConnectionPool.getConnection();
        if ((i2 != 0) || (EnterpriseUtil.isIt360MSPEdition()))
        {
          localObject13 = null;
          if (!Constants.isUserResourceEnabled())
          {
            localObject13 = "RESID_LIST_" + System.currentTimeMillis();
            localObject14 = "CREATE TEMPORARY TABLE " + (String)localObject13 + " (RESID VARCHAR(20))";
            if (str7.trim().equals("mssql"))
            {
              localObject13 = "#RESID_LIST_" + System.currentTimeMillis();
              localObject14 = "CREATE TABLE " + (String)localObject13 + " (RESID VARCHAR(20))";
            }
            try
            {
              localStatement1 = ((Connection)localObject11).createStatement();
              localStatement2 = ((Connection)localObject11).createStatement();
              localStatement3 = ((Connection)localObject11).createStatement();
              localStatement1.executeUpdate((String)localObject14);
              localStatement1.close();
              for (int i8 = 0; i8 < localVector3.size(); i8++)
              {
                localObject16 = (String)localVector3.get(i8);
                String str35 = "insert into " + (String)localObject13 + " values ('" + (String)localObject16 + "')";
                localStatement2.addBatch(str35);
              }
              int[] arrayOfInt = localStatement2.executeBatch();
              localStatement2.clearBatch();
              localStatement2.close();
            }
            catch (SQLException localSQLException5)
            {
              localSQLException5.printStackTrace();
            }
            str21 = " inner join " + (String)localObject13 + " on " + DBQueryUtil.castasVarchar("Alert.source") + "= " + (String)localObject13 + ".RESID";
          }
          else
          {
            str21 = " inner join AM_USERRESOURCESTABLE ON AM_USERRESOURCESTABLE.RESOURCEID=Alert.source and AM_USERRESOURCESTABLE.USERID=" + str25;
          }
          if (APMHelpDeskUtil.isTicketingEnabled(paramHttpServletRequest))
          {
            localObject14 = " left join SDP_WORKORDERID sw on sw.ENTITY=Alert.ENTITY ";
            localStringBuilder = new StringBuilder((String)localObject14).append(localStringBuilder.toString());
          }
          localObject14 = "select ";
          if (APMHelpDeskUtil.isTicketingEnabled(paramHttpServletRequest)) {
            localObject14 = (String)localObject14 + " Alert.*,sw.WORKORDERID,sw.SYSID ";
          } else {
            localObject14 = (String)localObject14 + " Alert.* ";
          }
          localObject14 = (String)localObject14 + " from Alert " + localStringBuilder.toString() + str21 + str22 + (String)localObject8 + (String)localObject10 + str30 + (String)localObject7 + " ORDER BY " + str10 + " desc";
          localObject14 = DBQueryUtil.addLimit((String)localObject14, i6, Integer.parseInt(str8), str10, false);
          localHashtable1 = getData((String)localObject14, (Connection)localObject11);
          localObject15 = "select count(*) from Alert " + localStringBuilder.toString() + str21 + str22 + (String)localObject8 + (String)localObject10 + str30 + (String)localObject7;
          localHashtable1.put("count", String.valueOf(getTotalRows((String)localObject15, (Connection)localObject11)));
          if (!Constants.isUserResourceEnabled())
          {
            localObject16 = "DROP TABLE " + (String)localObject13;
            try
            {
              localStatement3.executeUpdate((String)localObject16);
              localStatement3.close();
            }
            catch (SQLException localSQLException6)
            {
              localSQLException6.printStackTrace();
            }
          }
        }
        else
        {
          if (APMHelpDeskUtil.isTicketingEnabled(paramHttpServletRequest))
          {
            localObject13 = " left join SDP_WORKORDERID sw on sw.ENTITY=Alert.ENTITY ";
            localStringBuilder = new StringBuilder((String)localObject13).append(localStringBuilder.toString());
          }
          localObject13 = "select ";
          if (APMHelpDeskUtil.isTicketingEnabled(paramHttpServletRequest)) {
            localObject13 = (String)localObject13 + " Alert.*,sw.WORKORDERID,sw.SYSID ";
          } else {
            localObject13 = (String)localObject13 + " Alert.* ";
          }
          localObject13 = (String)localObject13 + " from Alert " + localStringBuilder.toString() + str22 + (String)localObject8 + (String)localObject10 + str30 + (String)localObject7 + " ORDER BY " + str10 + "  desc";
          localObject13 = DBQueryUtil.addLimit((String)localObject13, i6, Integer.parseInt(str8), str10, false);
          localHashtable1 = getData((String)localObject13, (Connection)localObject11);
          localObject14 = "select count(*) from Alert " + localStringBuilder.toString() + str22 + (String)localObject8 + (String)localObject10 + str30 + (String)localObject7;
          localHashtable1.put("count", String.valueOf(getTotalRows((String)localObject14, (Connection)localObject11)));
        }
        paramHttpServletRequest.setAttribute("stringseverity", str33);
        localVector1 = (Vector)localHashtable1.get("viewData");
        if (localHashtable1.containsKey("sourceList")) {
          paramHttpServletRequest.setAttribute("sourceList", (String)localHashtable1.get("sourceList"));
        }
        int i7 = localVector1.size();
        Object localObject14 = null;
        if (((i2 != 0) || (EnterpriseUtil.isIt360MSPEdition())) && ((str1.equalsIgnoreCase("Alerts")) || (str1.equalsIgnoreCase("Alerts.1")) || (str1.equalsIgnoreCase("Alerts.14")) || (str1.equalsIgnoreCase("Alerts.15")) || (str1.equalsIgnoreCase("Alerts.2")) || (str1.equalsIgnoreCase("Alerts.21")) || (str1.equalsIgnoreCase("Alerts.24")) || (str1.equalsIgnoreCase("Alerts.25")) || (str1.equalsIgnoreCase("Alerts.4")) || (str1.equalsIgnoreCase("Alerts.41")) || (str1.equalsIgnoreCase("Alerts.44")) || (str1.equalsIgnoreCase("Alerts.45")) || (str1.equalsIgnoreCase("Alerts.5")))) {
          localObject14 = new AlarmGraphUtil(str1, localVector3);
        } else {
          localObject14 = new AlarmGraphUtil(str1);
        }
        if (localObject14 != null) {
          paramHttpServletRequest.setAttribute("graph", localObject14);
        }
        Object localObject15 = new ArrayList();
        Object localObject16 = paramHttpServletRequest.getRemoteUser();
        if ((i2 != 0) && (!EnterpriseUtil.isIt360MSPEdition())) {
          localObject15 = AlarmUtil.getApplicationsForOwner((String)localObject16, paramHttpServletRequest);
        } else if (EnterpriseUtil.isIt360MSPEdition())
        {
          if (bool) {
            localObject15 = AlarmUtil.getApplicationsForOwner((String)localObject16, paramHttpServletRequest);
          } else {
            localObject15 = AlarmUtil.getApplicationsForAdmin(paramHttpServletRequest);
          }
        }
        else {
          localObject15 = AlarmUtil.getApplicationsForAdmin();
        }
        AMLog.debug("applications:SP" + localObject15);
        Vector localVector4 = new Vector();
        for (int i10 = 0; i10 < ((ArrayList)localObject15).size(); i10++)
        {
          localObject17 = (String)((ArrayList)((ArrayList)localObject15).get(i10)).get(1);
          localVector4.add(localObject17);
        }
        Vector localVector5 = new Vector();
        if (localObject15 != null) {
          paramHttpServletRequest.setAttribute("applications", localObject15);
        }
        Object localObject17 = new ArrayList();
        localObject17 = MyFields.getCustomFieldforAlarms();
        ArrayList localArrayList2 = DBUtil.getResourceTypesforAlarm();
        paramHttpServletRequest.setAttribute("alarmResTypes", localArrayList2);
        paramHttpServletRequest.setAttribute("customFieldAlarm", localObject17);
        if (localVector1.isEmpty())
        {
          ActionMessage localActionMessage = new ActionMessage("webclient.fault.alarmlist.nodata");
          paramHttpServletRequest.setAttribute("org.apache.struts.action.ACTION_MESSAGE", localObject5);
          localObject14 = new AlarmGraphUtil("NoData");
          paramHttpServletRequest.setAttribute("graph", localObject14);
          String str36 = "/fault/invokeAlarmCV.do?viewId=" + str1 + "&displayName=" + str3 + "&actionToPerform=modify";
          paramHttpServletRequest.setAttribute("actionURL", str36);
          paramHttpServletRequest.setAttribute("msgForUrl", NmsUtil.GetString("webclient.common.messagepage.modifycv"));
          if ("true".equalsIgnoreCase(paramHttpServletRequest.getParameter("ajaxRequest")))
          {
            localActionForward = paramActionMapping.findForward("alarmMessagePage");
            return localActionForward;
          }
          ActionForward localActionForward = paramActionMapping.findForward("messagePage");
          return localActionForward;
        }
      }
      catch (Exception localException3)
      {
        System.out.println("the exception is caught........");
        localException3.printStackTrace();
      }
      arrayOfTableColumn = getDefaultColumns(paramHttpServletRequest);
      l1 = Integer.parseInt((String)localHashtable1.get("count"));
      if (str10 != null)
      {
        if (str10.equals("MMESSAGE")) {
          str10 = "message";
        }
        paramHttpServletRequest.setAttribute("orderByColumn", str10);
      }
      if (str12 == null)
      {
        long l2 = Long.parseLong(str8);
        long l3 = l1 / l2;
        long l6 = l1 % l2;
        if (l6 != 0L) {
          l3 += 1L;
        }
        localObject9 = String.valueOf(l3);
        long l8 = Long.parseLong(str8);
        if ((l3 != 1L) && (l1 % l2 != 0L) && (l8 > l6))
        {
          long l9 = l8 - l6;
          for (int i9 = 0; i9 < l9; i9++) {
            if (!localVector1.isEmpty()) {
              localVector1.remove(localVector1.size() - 1);
            }
          }
        }
        paramHttpServletRequest.setAttribute("PAGENUMBER", localObject9);
      }
      else
      {
        paramHttpServletRequest.setAttribute("PAGENUMBER", str12);
      }
      AlarmUtil.removeNMSAlerts();
      Hashtable localHashtable2 = AlarmUtil.getApplicationAlerts(str13, str1);
      if (localHashtable2 != null) {
        paramHttpServletRequest.setAttribute("appalerts", localHashtable2);
      }
      if ("clearAlarm".equals(paramHttpServletRequest.getParameter("statusMsg")))
      {
        localObject7 = new ActionMessages();
        ((ActionMessages)localObject7).add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("webclient.fault.annotationresponse.annotation.addedit.apm.message"));
        saveMessages(paramHttpServletRequest, (ActionMessages)localObject7);
      }
      Object localObject7 = NmsUtil.GetString("webclient.fault.alarm.pagetitle");
      paramHttpServletRequest.setAttribute("pageTitle", localObject7);
      String str31 = WebClientUtil.getClientParameter(str6, "ASSIGN_ALERT");
      paramHttpServletRequest.setAttribute("isAssign", str31);
      paramHttpServletRequest.setAttribute("RECORDS", new Long(l1));
      paramHttpServletRequest.setAttribute("viewLength", new Integer(str8));
      paramHttpServletRequest.setAttribute("startIndex", str9);
      paramHttpServletRequest.setAttribute("permittedToViewAlert", Boolean.TRUE);
      paramHttpServletRequest.setAttribute("permittedToDeleteAlert", Boolean.TRUE);
      paramHttpServletRequest.setAttribute("permittedToClearAlert", Boolean.TRUE);
      paramHttpServletRequest.setAttribute("permittedToPickup", Boolean.TRUE);
      paramHttpServletRequest.setAttribute("headerList", arrayOfTableColumn);
      paramHttpServletRequest.setAttribute("viewData", localVector1);
      String str32 = "AlarmView.do";
      paramHttpServletRequest.setAttribute("action", str32);
      if (("All".equalsIgnoreCase(str17)) || ("mgalarms".equalsIgnoreCase(str17)) || ("allmonitoralarms".equalsIgnoreCase(str17))) {
        str32 = str32 + "&haid=" + str17;
      }
      if ("All".equalsIgnoreCase(paramHttpServletRequest.getParameter("monitor"))) {
        str32 = str32 + "&monitor=All";
      }
      if ((str1 != null) && (!str1.equals(""))) {
        str32 = str32 + "&viewId=" + str1;
      }
      if ((paramHttpServletRequest.getParameter("header") != null) && (!paramHttpServletRequest.getParameter("header").equals(""))) {
        str32 = str32 + "&header=" + paramHttpServletRequest.getParameter("header");
      }
      paramHttpServletRequest.setAttribute("CUSTOMIZE_COLUMNS_ACTION", "AlarmColumnCustomizer.do");
      try
      {
        if (localStatement1 != null) {
          localStatement1.close();
        }
        if (localStatement2 != null) {
          localStatement2.close();
        }
        if (localStatement3 != null) {
          localStatement3.close();
        }
      }
      catch (SQLException localSQLException1)
      {
        localSQLException1.printStackTrace();
      }
      if (!"true".equalsIgnoreCase(paramHttpServletRequest.getParameter("ajaxRequest"))) {
        break label6468;
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    finally
    {
      try
      {
        if (localStatement1 != null) {
          localStatement1.close();
        }
        if (localStatement2 != null) {
          localStatement2.close();
        }
        if (localStatement3 != null) {
          localStatement3.close();
        }
      }
      catch (SQLException localSQLException9)
      {
        localSQLException9.printStackTrace();
      }
    }
    return paramActionMapping.findForward("alarmListView");
    label6468:
    return paramActionMapping.findForward("alarmView");
  }
  
  private void isAlertFEInitialized(int paramInt)
  {
    int i = 0;
    while (((RunProcessInterface)NmsUtil.runProcessModules.get("com.adventnet.nms.fe.alert.AlertFE") == null) || (!((RunProcessInterface)NmsUtil.runProcessModules.get("com.adventnet.nms.fe.alert.AlertFE")).isInitialized())) {
      try
      {
        if (i == paramInt)
        {
          AMLog.debug("AlertFE is not yet initialized . Giving up after 5 attempts");
          break;
        }
        i++;
        AMLog.debug("AlertFE is not yet initialized.Number of attempt made is : " + i);
        Thread.sleep(1000L);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
  
  public Hashtable getData(String paramString, Connection paramConnection)
  {
    localHashtable = new Hashtable();
    Vector localVector = new Vector();
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      AMConnectionPool localAMConnectionPool = AMConnectionPool.getInstance();
      localResultSet = paramConnection.createStatement().executeQuery(paramString);
      int i = 0;
      while (localResultSet.next())
      {
        i++;
        Properties localProperties = new Properties();
        localProperties.setProperty("modTime", DateFormat.getDateTimeInstance(2, 2).format(new Date(localResultSet.getLong("MODTIME"))));
        localProperties.setProperty("modTimeMillis", "" + localResultSet.getLong("MODTIME"));
        localProperties.setProperty("entity", localResultSet.getString("ENTITY"));
        localProperties.setProperty("severity", getSeverityString(localResultSet.getInt("SEVERITY")));
        localProperties.setProperty("ownerName", localResultSet.getString("OWNERNAME"));
        localProperties.setProperty("message", localResultSet.getString("MMESSAGE"));
        localProperties.setProperty("category", localResultSet.getString("CATEGORY"));
        localProperties.setProperty("source", localResultSet.getString("SOURCE"));
        localStringBuffer.append(localResultSet.getString("SOURCE")).append(",");
        localProperties.setProperty("imgsrc", getImageSrc(localResultSet.getInt("SEVERITY")));
        if ((localResultSet.getString("WHO") == null) || (localResultSet.getString("WHO").equalsIgnoreCase("NULL"))) {
          localProperties.setProperty("who", "");
        } else {
          localProperties.setProperty("who", localResultSet.getString("WHO"));
        }
        try
        {
          if ((APMHDSettingsUtil.getTicketSettingsCache().isTicketingEnabled()) && (localResultSet.getString("WORKORDERID") != null))
          {
            long l = localResultSet.getLong("WORKORDERID");
            String str = localResultSet.getString("SYSID");
            if (l > 0L) {
              localProperties.setProperty("WORKORDERURL", APMHelpDeskUtil.getTicketUrl(l));
            } else if (str != null) {
              localProperties.setProperty("WORKORDERURL", APMHelpDeskUtil.getTicketUrl(str));
            }
          }
        }
        catch (Exception localException5) {}
        localVector.add(localProperties);
      }
      localHashtable.put("viewData", localVector);
      localHashtable.put("count", String.valueOf(i));
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    finally
    {
      if (localResultSet != null) {
        AMConnectionPool.closeResultSet(localResultSet);
      }
    }
    try
    {
      if (localStringBuffer.length() != 0) {
        localHashtable.put("sourceList", localStringBuffer.substring(0, localStringBuffer.length() - 1).toString());
      }
      return localHashtable;
    }
    catch (Exception localException3)
    {
      localException3.printStackTrace();
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException6)
      {
        localException6.printStackTrace();
      }
    }
  }
  
  public int getTotalRows(String paramString, Connection paramConnection)
  {
    int i = 0;
    ResultSet localResultSet = null;
    try
    {
      if (paramConnection == null) {
        paramConnection = AMConnectionPool.getConnection();
      }
      localResultSet = paramConnection.createStatement().executeQuery(paramString);
      if (localResultSet.next()) {
        i = localResultSet.getInt(1);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    finally
    {
      if (localResultSet != null) {
        AMConnectionPool.closeStatement(localResultSet);
      }
    }
    return i;
  }
  
  public String getImageSrc(int paramInt)
  {
    if (paramInt == 1) {
      return "/images/icon_health_critical.gif";
    }
    if (paramInt == 4) {
      return "/images/icon_health_warning.gif";
    }
    if (paramInt == 5) {
      return "/images/icon_health_clear.gif";
    }
    return "/images/icon_health_unknown.gif";
  }
  
  public String getSeverityString(int paramInt)
  {
    if (paramInt == 1) {
      return "Critical";
    }
    if (paramInt == 4) {
      return "Warning";
    }
    if (paramInt == 5) {
      return "Clear";
    }
    return "Unknown";
  }
  
  public TableColumn[] getDefaultColumns(HttpServletRequest paramHttpServletRequest)
  {
    try
    {
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new TableColumn("severity", "Severity", 100));
      localArrayList.add(new TableColumn("message", "Message", 100));
      localArrayList.add(new TableColumn("source", "Source", 100));
      localArrayList.add(new TableColumn("category", "Category", 100));
      localArrayList.add(new TableColumn("who", "Acknowledge", 100));
      localArrayList.add(new TableColumn("modTime", "Date / Time", 100));
      if ((APMHelpDeskUtil.isTicketLinkToBeShown(paramHttpServletRequest)) || (APMHelpDeskUtil.isDynamicTicketingEnabled(paramHttpServletRequest))) {
        localArrayList.add(new TableColumn("Workorderurl", "Workorderurl", 100));
      }
      return (TableColumn[])localArrayList.toArray(new TableColumn[0]);
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static TableColumn[] getEventColumns()
  {
    try
    {
      TableColumn[] arrayOfTableColumn = { new TableColumn("severity", "Severity", 100), new TableColumn("time", "Date / Time", 100), new TableColumn("text", "Message", 100) };
      return arrayOfTableColumn;
    }
    catch (Exception localException) {}
    return null;
  }
}


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\webclient\fault\alarm\AlarmViewAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */