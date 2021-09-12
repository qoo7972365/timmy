/*     */ package com.adventnet.appmanager.struts.beans;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.me.apm.cmdb.APMHDSettingsUtil;
/*     */ import com.me.apm.cmdb.APMHelpDeskUtil;
/*     */ import com.me.apm.cmdb.CITypeUtil;
/*     */ import com.me.helpdesk.object.CISettings;
/*     */ import com.me.helpdesk.object.TicketSettings;
/*     */ import com.me.helpdesk.object.TicketSettings.CloseTicketPolicy;
/*     */ import com.me.helpdesk.object.TicketSettings.ReOpenTicketPolicy;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.struts.util.LabelValueBean;
/*     */ 
/*     */ public class APMHDClientUtil
/*     */ {
/*     */   private static void saveTicketSetting(AMActionForm form)
/*     */   {
/*  24 */     TicketSettings ticketSettings = new TicketSettings();
/*  25 */     ticketSettings.setTicketingEnabled(form.isTicketingEnabled());
/*  26 */     ticketSettings.setDynamicTicketingUsingAction(form.isDynamicTicketingUsingAction());
/*  27 */     ticketSettings.setDynamicTicketingUsingForm(form.isDynamicTicketingUsingForm());
/*  28 */     ticketSettings.setReOpenPeriod(form.getReOpenPeriod());
/*  29 */     ticketSettings.setNotesToBeAddedForTicket(form.isNotesToBeAddedForTicket());
/*  30 */     ticketSettings.setReOpenTicketPolicy(form.getReOpenTicketPolicy());
/*  31 */     ticketSettings.setCloseTicketPolicy(form.getCloseTicketPolicy());
/*  32 */     ticketSettings.setTicketLinkToBeShown(form.isTicketLinkToBeShown());
/*  33 */     ticketSettings.setReadonlyTicket(form.isReadonlyTicket());
/*  34 */     ticketSettings.setTicketToBeMappedToCI(form.isTicketToBeMappedToCI());
/*  35 */     ticketSettings.setUpdateTicketForchangeInStatusAlone(form.isUpdateTicketForchangeInStatusAlone());
/*  36 */     ticketSettings.setAllowOverWriteOfReqTemplate(form.isAllowOverWriteOfReqTemplate());
/*  37 */     APMHDSettingsUtil.persistTicketSettings(ticketSettings);
/*     */   }
/*     */   
/*     */   private static void saveCISettings(AMActionForm form) {
/*  41 */     CISettings ciSettings = new CISettings();
/*  42 */     ciSettings.setcISyncEnabled(form.iscISyncEnabled());
/*  43 */     ciSettings.setCiLinksToBeShown(form.isCiLinksToBeShown());
/*  44 */     ciSettings.setCiAttributesToBeSynced(form.isCiAttributesToBeSynced());
/*  45 */     ciSettings.setExcludeFirstLevelMonitorTypes(Arrays.asList(form.getExcludeFirstLevelMonitorTypes()));
/*  46 */     ciSettings.setIncludeSecondLevelMonitorTypes(Arrays.asList(form.getIncludeSecondLevelMonitorTypes()));
/*  47 */     ciSettings.setCiRlMapalongWithListView(form.isCiRlMapalongWithListView());
/*  48 */     ciSettings.setCiToBeDeleted(form.isCiToBeDeleted());
/*  49 */     APMHDSettingsUtil.persistCIsettings(ciSettings);
/*     */   }
/*     */   
/*     */   private static void setTicketSettingsInTheForm(AMActionForm form) {
/*  53 */     TicketSettings ticketSettings = APMHDSettingsUtil.getTicketSettingsCache();
/*  54 */     if ((ticketSettings != null) && (form != null))
/*     */     {
/*  56 */       form.setTicketingEnabled(ticketSettings.isTicketingEnabled());
/*  57 */       form.setDynamicTicketingUsingAction(ticketSettings.isDynamicTicketingUsingAction());
/*  58 */       form.setDynamicTicketingUsingForm(ticketSettings.isDynamicTicketingUsingForm());
/*  59 */       form.setReOpenPeriod(ticketSettings.getReOpenPeriod());
/*  60 */       form.setCloseTicketPolicy(ticketSettings.getCloseTicketPolicy().toString());
/*  61 */       form.setReOpenTicketPolicy(ticketSettings.getReOpenTicketPolicy().toString());
/*  62 */       form.setTicketLinkToBeShown(ticketSettings.isTicketLinkToBeShown());
/*  63 */       form.setReadonlyTicket(ticketSettings.isReadonlyTicket());
/*  64 */       form.setNotesToBeAddedForTicket(ticketSettings.isNotesToBeAddedForTicket());
/*  65 */       form.setTicketToBeMappedToCI(ticketSettings.isTicketToBeMappedToCI());
/*  66 */       form.setUpdateTicketForchangeInStatusAlone(ticketSettings.isUpdateTicketForchangeInStatusAlone());
/*  67 */       form.setAllowOverWriteOfReqTemplate(ticketSettings.isAllowOverWriteOfReqTemplate());
/*     */     }
/*     */   }
/*     */   
/*     */   private static void setSdeskdetails(AMActionForm form) {
/*  72 */     if (form != null) {
/*  73 */       form.setMspDesk(APMHDSettingsUtil.isMSPDesk());
/*     */     }
/*     */   }
/*     */   
/*     */   private static void setCISettingsInTheForm(AMActionForm form)
/*     */   {
/*  79 */     CISettings ciSettings = APMHDSettingsUtil.getCiSettingsCache();
/*  80 */     if (form != null)
/*     */     {
/*  82 */       form.setFirstLevelMonitorTypesOptions(getFirstLevelMonitorTypesOptions());
/*  83 */       form.setSecondLevelMonitorTypesOptions(getSecondLevelMonitorTypesOptions());
/*     */       
/*  85 */       if (ciSettings != null)
/*     */       {
/*  87 */         form.setcISyncEnabled(ciSettings.iscISyncEnabled());
/*  88 */         form.setCiLinksToBeShown(ciSettings.isCiLinksToBeShown());
/*  89 */         form.setCiAttributesToBeSynced(ciSettings.isCiAttributesToBeSynced());
/*  90 */         form.setExcludeFirstLevelMonitorTypesOptions(getExcludeFirstLevelMonitorTypesOptions());
/*  91 */         form.setIncludeSecondLevelMonitorTypesOptions(getIncludeSecondLevelMonitorTypesOptions());
/*  92 */         form.setCiRlMapalongWithListView(ciSettings.isCiRlMapalongWithListView());
/*  93 */         form.setCiToBeDeleted(ciSettings.isCiToBeDeleted());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setAdvancedAPISettingsInTheForm(AMActionForm form)
/*     */   {
/* 100 */     if (form != null)
/*     */     {
/* 102 */       setSdeskdetails(form);
/* 103 */       setCISettingsInTheForm(form);
/* 104 */       setTicketSettingsInTheForm(form);
/*     */     }
/*     */   }
/*     */   
/* 108 */   public static void saveAdvancedAPISettings(AMActionForm form) { if (form != null)
/*     */     {
/* 110 */       saveSdeskdetails(form);
/* 111 */       saveCISettings(form);
/* 112 */       saveTicketSetting(form);
/*     */     }
/*     */   }
/*     */   
/* 116 */   private static void saveSdeskdetails(AMActionForm form) { APMHDSettingsUtil.persistSdesksettings(form.isMspDesk()); }
/*     */   
/*     */ 
/*     */ 
/*     */   private static List<LabelValueBean> getSecondLevelMonitorTypesOptions()
/*     */   {
/* 122 */     return getLabelValueBeans(getSecondLevelMonitortypesQuery(false), "RESOURCETYPE", "RESOURCETYPE");
/*     */   }
/*     */   
/*     */   private static List<LabelValueBean> getIncludeSecondLevelMonitorTypesOptions()
/*     */   {
/* 127 */     return getLabelValueBeans(getSecondLevelMonitortypesQuery(true), "RESOURCETYPE", "RESOURCETYPE");
/*     */   }
/*     */   
/*     */ 
/*     */   private static List<LabelValueBean> getExcludeFirstLevelMonitorTypesOptions()
/*     */   {
/* 133 */     return getLabelValueBeans(getFirstLevelMonitortypesQuery(true), "DISPLAYNAME", "RESOURCETYPE");
/*     */   }
/*     */   
/*     */   private static List<LabelValueBean> getFirstLevelMonitorTypesOptions() {
/* 137 */     return getLabelValueBeans(getFirstLevelMonitortypesQuery(false).toString(), "DISPLAYNAME", "RESOURCETYPE");
/*     */   }
/*     */   
/*     */   private static String getSecondLevelMonitortypesQuery(boolean includeFirstLevelMonitorTypes)
/*     */   {
/* 142 */     StringBuilder query = new StringBuilder("select distinct(aa.RESOURCETYPE) as RESOURCETYPE from AM_ATTRIBUTES aa left join AM_ManagedResourceType mrt on mrt.RESOURCETYPE=aa.RESOURCETYPE left join AM_ManagedResourceType rg on rg.RESOURCEGROUP=aa.RESOURCETYPE where  rg.RESOURCEGROUP is  null and mrt.RESOURCETYPE is null");
/* 143 */     query.append(" and TYPE in (").append(2).append(",").append(1).append(",").append(3).append(",").append(7).append(",").append(0).append(") ");
/* 144 */     List<String> includeTypes = APMHDSettingsUtil.getCiSettingsCache().getIncludeSecondLevelMonitorTypes();
/* 145 */     if ((includeTypes != null) && (includeTypes.size() > 0))
/*     */     {
/* 147 */       StringBuilder temp = new StringBuilder("");
/* 148 */       for (int i = 0; i < includeTypes.size(); i++)
/*     */       {
/* 150 */         temp.append("'").append((String)includeTypes.get(i)).append("'");
/* 151 */         if (i < includeTypes.size() - 1)
/*     */         {
/* 153 */           temp.append(",");
/*     */         }
/*     */       }
/* 156 */       if (!"".equals(temp.toString()))
/*     */       {
/* 158 */         query.append(" and aa.RESOURCETYPE ");
/* 159 */         query.append(includeFirstLevelMonitorTypes ? " IN " : " NOT IN ");
/* 160 */         query.append(" (").append(temp.toString()).append(") ");
/*     */       }
/*     */     }
/* 163 */     else if (includeFirstLevelMonitorTypes)
/*     */     {
/* 165 */       return "";
/*     */     }
/* 167 */     query.append("  order by aa.RESOURCETYPE ");
/* 168 */     return query.toString();
/*     */   }
/*     */   
/*     */   private static String getFirstLevelMonitortypesQuery(boolean excludeFirstLevelMonitorTypes) {
/* 172 */     StringBuilder query = new StringBuilder("select RESOURCETYPE,DISPLAYNAME from AM_ManagedResourceType where RESOURCEGROUP Not in ('NET','HAI') and RESOURCETYPE not in ");
/* 173 */     query.append(APMHelpDeskUtil.getQueryString(CITypeUtil.excludedMonitorTypesFromCMDB));
/*     */     
/* 175 */     if (Constants.sqlManager)
/*     */     {
/* 177 */       query.append(" and RESOURCETYPE in").append(Constants.sqlManagerresourceTypes);
/*     */     }
/*     */     
/*     */ 
/* 181 */     List<String> excludeTypes = APMHDSettingsUtil.getCiSettingsCache().getExcludeFirstLevelMonitorTypes();
/* 182 */     if ((excludeTypes != null) && (!excludeTypes.isEmpty()))
/*     */     {
/* 184 */       StringBuilder temp = new StringBuilder("");
/* 185 */       for (int i = 0; i < excludeTypes.size(); i++)
/*     */       {
/* 187 */         temp.append("'").append((String)excludeTypes.get(i)).append("'");
/* 188 */         if (i < excludeTypes.size() - 1)
/*     */         {
/* 190 */           temp.append(",");
/*     */         }
/*     */       }
/* 193 */       if (!"".equals(temp.toString()))
/*     */       {
/* 195 */         query.append(" and RESOURCETYPE ");
/* 196 */         query.append(excludeFirstLevelMonitorTypes ? " IN " : " NOT IN ");
/* 197 */         query.append(" (").append(temp.toString()).append(") ");
/*     */       }
/*     */     }
/* 200 */     else if (excludeFirstLevelMonitorTypes)
/*     */     {
/* 202 */       return "";
/*     */     }
/*     */     
/* 205 */     query.append(" order by DISPLAYNAME");
/* 206 */     return query.toString();
/*     */   }
/*     */   
/*     */   private static List<LabelValueBean> getLabelValueBeans(String query, String label, String value)
/*     */   {
/* 211 */     List<LabelValueBean> toReturn = new ArrayList();
/* 212 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 215 */       if (!"".equals(query))
/*     */       {
/* 217 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */         
/* 219 */         while (rs.next())
/*     */         {
/*     */ 
/* 222 */           LabelValueBean labelValueBean = new LabelValueBean(FormatUtil.getString(rs.getString(label)), rs.getString(value));
/* 223 */           toReturn.add(labelValueBean);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 229 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 233 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 235 */     return toReturn;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\beans\APMHDClientUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */