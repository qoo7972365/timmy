/*     */ package com.adventnet.appmanager.struts.form;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Properties;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ 
/*     */ 
/*     */ public class MyPageForm
/*     */   extends ActionForm
/*     */ {
/*  12 */   private String displayName = FormatUtil.getString("am.mypage.mydashboard.text");
/*  13 */   private String description = "";
/*  14 */   private int widgetType = 1;
/*  15 */   private String widgetTypeName = "";
/*  16 */   private ArrayList availMonitorTypes = new ArrayList();
/*  17 */   private String selectedMonitorType = "";
/*  18 */   private ArrayList availAttributes = new ArrayList();
/*  19 */   private String selectedAttribute = "";
/*  20 */   private String[] relatedAttributes = new String[0];
/*  21 */   private ArrayList availTopN = new ArrayList();
/*  22 */   private String selectedTopN = "";
/*  23 */   private ArrayList availMonitors = new ArrayList();
/*     */   private String selectedMonitor;
/*  25 */   private String[] selectedMultiMonitors = new String[0];
/*  26 */   private ArrayList selectedMultiMonitorsList = new ArrayList();
/*  27 */   private ArrayList availMultiMonitors = new ArrayList();
/*  28 */   private ArrayList availGraphTypes = new ArrayList();
/*  29 */   private String selectedGraphType = "";
/*  30 */   private int limitn = 10;
/*  31 */   private String period = "20";
/*  32 */   private int numberOfColumns = 2;
/*  33 */   private int columnWidth1 = 50;
/*  34 */   private int columnWidth2 = 50;
/*  35 */   private int columnWidth3 = 30;
/*  36 */   private int columnWidth4 = 30;
/*  37 */   private String selecttionType = "ALL";
/*  38 */   private ArrayList availMGs = new ArrayList();
/*     */   private String selectedMG;
/*  40 */   private String topnOrder = "DESC";
/*  41 */   private String filterByName = null;
/*  42 */   String url = "";
/*  43 */   private String pageType = "";
/*  44 */   private String selectedTab = "0";
/*     */   
/*  46 */   private ArrayList availBusinessViews = new ArrayList();
/*     */   private String selectedBusinessView;
/*  48 */   private ArrayList availTopologyMapViews = new ArrayList();
/*     */   private String selectedTopologyMapView;
/*  50 */   private int widgetHeight = 500;
/*  51 */   private String allMGsSelectedForTemplatePage = "1";
/*  52 */   private boolean alertStatusForMetrics = false;
/*  53 */   private boolean graphforAdditionalMetric = false;
/*  54 */   private boolean displaySubGroups = false;
/*  55 */   private String customScaleOption = "noscale";
/*  56 */   private String[] selectedItems = new String[0];
/*  57 */   private String[] multiAttributes = new String[0];
/*  58 */   private String selectAllresGrp = "off";
/*  59 */   private String minimumScale = "";
/*  60 */   private String maximumScale = "";
/*  61 */   private boolean mgtemplatepage = false;
/*  62 */   private boolean todaysavailability = false;
/*  63 */   private boolean monitorsStatus = false;
/*     */   
/*     */ 
/*  66 */   private int monitorSelectionType = 1;
/*     */   
/*  68 */   private String[] multiMonitor = new String[0];
/*     */   
/*  70 */   public String[] getMultiMonitor() { return this.multiMonitor; }
/*     */   
/*     */   public void setMultiMonitor(String[] multiMonitor) {
/*  73 */     this.multiMonitor = multiMonitor;
/*     */   }
/*     */   
/*  76 */   public void setMaximumScale(String max) { this.maximumScale = max; }
/*     */   
/*     */   public String getMaximumScale() {
/*  79 */     return this.maximumScale;
/*     */   }
/*     */   
/*     */   public void setMinimumScale(String min) {
/*  83 */     this.minimumScale = min;
/*     */   }
/*     */   
/*  86 */   public String getMinimumScale() { return this.minimumScale; }
/*     */   
/*     */ 
/*     */   public boolean isMgtemplatepage()
/*     */   {
/*  91 */     return this.mgtemplatepage;
/*     */   }
/*     */   
/*     */   public void setMgtemplatepage(boolean mgtemplatepage) {
/*  95 */     this.mgtemplatepage = mgtemplatepage;
/*     */   }
/*     */   
/*     */   public void setAvailBusinessViews(ArrayList availBusinessViews)
/*     */   {
/* 100 */     this.availBusinessViews = availBusinessViews;
/*     */   }
/*     */   
/*     */   public ArrayList getAvailBusinessViews()
/*     */   {
/* 105 */     return this.availBusinessViews;
/*     */   }
/*     */   
/*     */   public void setSelectedBusinessView(String selectedBusinessView)
/*     */   {
/* 110 */     this.selectedBusinessView = selectedBusinessView;
/*     */   }
/*     */   
/*     */   public String getSelectedBusinessView()
/*     */   {
/* 115 */     return this.selectedBusinessView;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setAvailTopologyMapViews(ArrayList availTopologyMapViews)
/*     */   {
/* 121 */     this.availTopologyMapViews = availTopologyMapViews;
/*     */   }
/*     */   
/*     */   public ArrayList getAvailTopologyMapViews()
/*     */   {
/* 126 */     return this.availTopologyMapViews;
/*     */   }
/*     */   
/*     */   public void setSelectedTopologyMapView(String selectedTopologyMapView)
/*     */   {
/* 131 */     this.selectedTopologyMapView = selectedTopologyMapView;
/*     */   }
/*     */   
/*     */   public String getSelectedTopologyMapView()
/*     */   {
/* 136 */     return this.selectedTopologyMapView;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getDisplayName()
/*     */   {
/* 142 */     return this.displayName;
/*     */   }
/*     */   
/*     */   public void setDisplayName(String displayName) {
/* 146 */     this.displayName = displayName;
/*     */   }
/*     */   
/*     */   public String getDescription()
/*     */   {
/* 151 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/* 155 */     this.description = description;
/*     */   }
/*     */   
/*     */   public void setWidgetType(int widgetType)
/*     */   {
/* 160 */     this.widgetType = widgetType;
/*     */   }
/*     */   
/*     */   public int getWidgetType() {
/* 164 */     return this.widgetType;
/*     */   }
/*     */   
/*     */   public ArrayList getAvailMonitorTypes() {
/* 168 */     return this.availMonitorTypes;
/*     */   }
/*     */   
/*     */   public void setAvailMonitorTypes(ArrayList availMonitorTypes) {
/* 172 */     this.availMonitorTypes = availMonitorTypes;
/*     */   }
/*     */   
/*     */   public String getSelectedMonitorType()
/*     */   {
/* 177 */     return this.selectedMonitorType;
/*     */   }
/*     */   
/*     */   public void setSelectedMonitorType(String selectedMonitorType) {
/* 181 */     this.selectedMonitorType = selectedMonitorType;
/*     */   }
/*     */   
/*     */ 
/*     */   public ArrayList getAvailAttributes()
/*     */   {
/* 187 */     return this.availAttributes;
/*     */   }
/*     */   
/*     */   public void setAvailAttributes(ArrayList availAttributes) {
/* 191 */     this.availAttributes = availAttributes;
/*     */   }
/*     */   
/*     */   public String getSelectedAttribute()
/*     */   {
/* 196 */     return this.selectedAttribute;
/*     */   }
/*     */   
/*     */   public void setSelectedAttribute(String selectedAttribute) {
/* 200 */     this.selectedAttribute = selectedAttribute;
/*     */   }
/*     */   
/*     */   public String[] getRelatedAttributes()
/*     */   {
/* 205 */     return this.relatedAttributes;
/*     */   }
/*     */   
/*     */   public void setRelatedAttributes(String[] relatedAttributes) {
/* 209 */     this.relatedAttributes = relatedAttributes;
/*     */   }
/*     */   
/*     */ 
/*     */   public ArrayList getAvailTopN()
/*     */   {
/* 215 */     return this.availTopN;
/*     */   }
/*     */   
/*     */   public void setAvailTopN(ArrayList availTopN) {
/* 219 */     this.availTopN = availTopN;
/*     */   }
/*     */   
/*     */   public String getSelectedTopN()
/*     */   {
/* 224 */     return this.selectedTopN;
/*     */   }
/*     */   
/*     */   public void setSelectedTopN(String selectedTopN) {
/* 228 */     this.selectedTopN = selectedTopN;
/*     */   }
/*     */   
/*     */   public ArrayList getAvailMonitors()
/*     */   {
/* 233 */     return this.availMonitors;
/*     */   }
/*     */   
/*     */   public void setAvailMonitors(ArrayList availMonitors) {
/* 237 */     this.availMonitors = availMonitors;
/*     */   }
/*     */   
/*     */   public String getSelectedMonitor()
/*     */   {
/* 242 */     return this.selectedMonitor;
/*     */   }
/*     */   
/*     */   public void setSelectedMonitor(String selectedMonitor) {
/* 246 */     this.selectedMonitor = selectedMonitor;
/*     */   }
/*     */   
/*     */   public ArrayList getAvailGraphTypes()
/*     */   {
/* 251 */     return this.availGraphTypes;
/*     */   }
/*     */   
/*     */   public void setAvailGraphTypes(ArrayList availGraphTypes) {
/* 255 */     this.availGraphTypes = availGraphTypes;
/*     */   }
/*     */   
/*     */   public String getSelectedGraphType()
/*     */   {
/* 260 */     if ((getPageType().equals("businesspage")) && (this.selectedGraphType.equals("")) && (this.widgetType == 2))
/*     */     {
/* 262 */       return "bignumber";
/*     */     }
/* 264 */     if ((this.selectedGraphType.equals("")) && (this.widgetType == 3))
/*     */     {
/* 266 */       return "nograph";
/*     */     }
/*     */     
/*     */ 
/* 270 */     return this.selectedGraphType;
/*     */   }
/*     */   
/*     */   public void setSelectedGraphType(String selectedGraphType)
/*     */   {
/* 275 */     this.selectedGraphType = selectedGraphType;
/*     */   }
/*     */   
/*     */   public String[] getSelectedMultiMonitors()
/*     */   {
/* 280 */     return this.selectedMultiMonitors;
/*     */   }
/*     */   
/*     */   public void setSelectedMultiMonitors(String[] selectedMultiMonitors)
/*     */   {
/* 285 */     this.selectedMultiMonitors = selectedMultiMonitors;
/*     */   }
/*     */   
/*     */   public ArrayList getAvailMultiMonitors()
/*     */   {
/* 290 */     return this.availMultiMonitors;
/*     */   }
/*     */   
/*     */   public void setAvailMultiMonitors(ArrayList availMultiMonitors) {
/* 294 */     this.availMultiMonitors = availMultiMonitors;
/*     */   }
/*     */   
/*     */   public void setLimitn(int limitn) {
/* 298 */     this.limitn = limitn;
/*     */   }
/*     */   
/*     */   public int getLimitn() {
/* 302 */     return this.limitn;
/*     */   }
/*     */   
/*     */   public String getPeriod() {
/* 306 */     return this.period;
/*     */   }
/*     */   
/*     */   public void setPeriod(String period) {
/* 310 */     this.period = period;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setColumnWidth1(int columnWidth1)
/*     */   {
/* 316 */     this.columnWidth1 = columnWidth1;
/*     */   }
/*     */   
/*     */   public int getColumnWidth1() {
/* 320 */     return this.columnWidth1;
/*     */   }
/*     */   
/*     */   public void setColumnWidth2(int columnWidth2) {
/* 324 */     this.columnWidth2 = columnWidth2;
/*     */   }
/*     */   
/*     */   public int getColumnWidth2() {
/* 328 */     return this.columnWidth2;
/*     */   }
/*     */   
/*     */   public void setColumnWidth3(int columnWidth3) {
/* 332 */     this.columnWidth3 = columnWidth3;
/*     */   }
/*     */   
/*     */   public int getColumnWidth3() {
/* 336 */     return this.columnWidth3;
/*     */   }
/*     */   
/*     */   public void setColumnWidth4(int columnWidth4) {
/* 340 */     this.columnWidth4 = columnWidth4;
/*     */   }
/*     */   
/*     */   public int getColumnWidth4() {
/* 344 */     return this.columnWidth4;
/*     */   }
/*     */   
/*     */   public void setSelecttionType(String selecttionType)
/*     */   {
/* 349 */     this.selecttionType = selecttionType;
/*     */   }
/*     */   
/*     */   public String getSelecttionType() {
/* 353 */     return this.selecttionType;
/*     */   }
/*     */   
/*     */   public ArrayList getAvailMGs()
/*     */   {
/* 358 */     return this.availMGs;
/*     */   }
/*     */   
/*     */   public void setAvailMGs(ArrayList availMGs) {
/* 362 */     this.availMGs = availMGs;
/*     */   }
/*     */   
/*     */   public void setFilterByName(String name) {
/* 366 */     this.filterByName = name;
/*     */   }
/*     */   
/*     */   public String getFilterByName() {
/* 370 */     return this.filterByName;
/*     */   }
/*     */   
/*     */   public void setTopnOrder(String order) {
/* 374 */     this.topnOrder = order;
/*     */   }
/*     */   
/*     */   public String getTopnOrder() {
/* 378 */     return this.topnOrder;
/*     */   }
/*     */   
/*     */   public String getSelectedMG()
/*     */   {
/* 383 */     return this.selectedMG;
/*     */   }
/*     */   
/*     */   public void setSelectedMG(String selectedMG) {
/* 387 */     this.selectedMG = selectedMG;
/*     */   }
/*     */   
/*     */   public int getNumberOfColumns() {
/* 391 */     return this.numberOfColumns;
/*     */   }
/*     */   
/*     */   public void setNumberOfColumns(int numberOfColumns) {
/* 395 */     this.numberOfColumns = numberOfColumns;
/*     */   }
/*     */   
/*     */   public void setSelectedMultiMonitorsList(ArrayList selectedMultiMonitorsList)
/*     */   {
/* 400 */     this.selectedMultiMonitorsList = selectedMultiMonitorsList;
/*     */   }
/*     */   
/*     */   public ArrayList getSelectedMultiMonitorsList() {
/* 404 */     return this.selectedMultiMonitorsList;
/*     */   }
/*     */   
/*     */   public void setPageType(String pageType)
/*     */   {
/* 409 */     this.pageType = pageType;
/*     */   }
/*     */   
/*     */   public String getPageType()
/*     */   {
/* 414 */     return this.pageType;
/*     */   }
/*     */   
/*     */   public void setWidgetTypeName(String widgetTypeName) {
/* 418 */     this.widgetTypeName = widgetTypeName;
/*     */   }
/*     */   
/*     */   public String getWidgetTypeName()
/*     */   {
/* 423 */     return this.widgetTypeName;
/*     */   }
/*     */   
/*     */   public String getUrl()
/*     */   {
/* 428 */     return this.url;
/*     */   }
/*     */   
/*     */   public void setUrl(String url) {
/* 432 */     if (url == null)
/*     */     {
/* 434 */       this.url = "";
/*     */     }
/*     */     else
/*     */     {
/* 438 */       this.url = url;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setSelectedTab(String selectedTab)
/*     */   {
/* 444 */     this.selectedTab = selectedTab;
/*     */   }
/*     */   
/*     */   public String getSelectedTab() {
/* 448 */     return this.selectedTab;
/*     */   }
/*     */   
/*     */   public void setWidgetHeight(int widgetHeight)
/*     */   {
/* 453 */     this.widgetHeight = widgetHeight;
/*     */   }
/*     */   
/*     */   public int getWidgetHeight() {
/* 457 */     return this.widgetHeight;
/*     */   }
/*     */   
/*     */   public String getAllMGsSelectedForTemplatePage() {
/* 461 */     return this.allMGsSelectedForTemplatePage;
/*     */   }
/*     */   
/*     */   public void setAllMGsSelectedForTemplatePage(String allMGsSelectedForTemplatePage) {
/* 465 */     this.allMGsSelectedForTemplatePage = allMGsSelectedForTemplatePage;
/*     */   }
/*     */   
/*     */   public void setGraphforAdditionalMetric(boolean graphforMetrics) {
/* 469 */     this.graphforAdditionalMetric = graphforMetrics;
/*     */   }
/*     */   
/*     */   public boolean isGraphforAdditionalMetric() {
/* 473 */     return this.graphforAdditionalMetric;
/*     */   }
/*     */   
/*     */   public void setDisplaySubGroups(boolean subgroups)
/*     */   {
/* 478 */     this.displaySubGroups = subgroups;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean getDisplaySubGroups()
/*     */   {
/* 485 */     return this.displaySubGroups;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setTodaysavailability(boolean avail)
/*     */   {
/* 491 */     this.todaysavailability = avail;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getTodaysavailability()
/*     */   {
/* 497 */     return this.todaysavailability;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setMonitorsStatus(boolean status)
/*     */   {
/* 503 */     this.monitorsStatus = status;
/*     */   }
/*     */   
/*     */   public boolean getMonitorsStatus() {
/* 507 */     return this.monitorsStatus;
/*     */   }
/*     */   
/*     */   public void setAlertStatusForMetrics(boolean alertStatusForMetrics) {
/* 511 */     this.alertStatusForMetrics = alertStatusForMetrics;
/*     */   }
/*     */   
/*     */   public boolean isalertStatusForMetrics() {
/* 515 */     return this.alertStatusForMetrics;
/*     */   }
/*     */   
/*     */   public void setCustomScaleOption(String scale)
/*     */   {
/* 520 */     this.customScaleOption = scale;
/*     */   }
/*     */   
/*     */   public String getCustomScaleOption() {
/* 524 */     return this.customScaleOption;
/*     */   }
/*     */   
/*     */   public String[] getSelectedItems()
/*     */   {
/* 529 */     return this.selectedItems;
/*     */   }
/*     */   
/*     */   public void setSelectedItems(String[] selectedItems) {
/* 533 */     this.selectedItems = selectedItems;
/*     */   }
/*     */   
/*     */   public String[] getMultiAttributes() {
/* 537 */     return this.multiAttributes;
/*     */   }
/*     */   
/* 540 */   public void setMultiAttributes(String[] multiAttributes) { this.multiAttributes = multiAttributes; }
/*     */   
/*     */ 
/*     */   public String getSelectAllresGrp()
/*     */   {
/* 545 */     return this.selectAllresGrp;
/*     */   }
/*     */   
/*     */   public void setSelectAllresGrp(String selectAllresGrp) {
/* 549 */     this.selectAllresGrp = selectAllresGrp;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMonitorSelectionType()
/*     */   {
/* 555 */     return this.monitorSelectionType;
/*     */   }
/*     */   
/*     */   public void setMonitorSelectionType(int monitorSelectionType) {
/* 559 */     this.monitorSelectionType = monitorSelectionType;
/*     */   }
/*     */   
/* 562 */   private ArrayList<Properties> brules = new ArrayList();
/*     */   
/*     */   public void setBusinessrules(ArrayList<Properties> brules) {
/* 565 */     this.brules = brules;
/*     */   }
/*     */   
/*     */   public ArrayList<Properties> getBusinessrules()
/*     */   {
/* 570 */     return this.brules;
/*     */   }
/*     */   
/* 573 */   private String businessPeriod = "oni";
/*     */   
/*     */   public void setBusinessPeriod(String businessPeriod) {
/* 576 */     this.businessPeriod = businessPeriod;
/*     */   }
/*     */   
/*     */   public String getBusinessPeriod()
/*     */   {
/* 581 */     return this.businessPeriod;
/*     */   }
/*     */   
/* 584 */   private ArrayList businessRuleNames = null;
/*     */   
/* 586 */   public ArrayList<Properties> getBusinessRuleNames() { ArrayList table = new ArrayList();
/* 587 */     ArrayList list = getBusinessrules();
/* 588 */     Properties dataProps1 = new Properties();
/* 589 */     dataProps1.setProperty("label", "-----" + FormatUtil.getString("am.webclient.businesshours.select.default") + "------");
/* 590 */     dataProps1.setProperty("value", "oni");
/* 591 */     table.add(dataProps1);
/* 592 */     for (int i = 0; i < list.size(); i++) {
/* 593 */       Properties props = (Properties)list.get(i);
/* 594 */       table.add(props);
/*     */     }
/*     */     
/*     */ 
/* 598 */     return table;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\form\MyPageForm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */