/*     */ package com.adventnet.appmanager.struts.form;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlashForm
/*     */   extends ActionForm
/*     */ {
/*  11 */   private String displayName = "";
/*  12 */   private String description = "";
/*  13 */   private String bgColor = "#FFFFFF";
/*  14 */   private String lineColor = "0x000000";
/*  15 */   private String labelColor = "0x000000";
/*  16 */   private float linethickness = 0.1F;
/*  17 */   private boolean showLabel = true;
/*     */   
/*  19 */   private int reloadInterval = 14;
/*  20 */   private int refreshInterval = 1;
/*  21 */   private float lineTransparency = 0.1F;
/*  22 */   private ArrayList availableMgs = new ArrayList();
/*  23 */   private ArrayList availableViews = new ArrayList();
/*  24 */   private ArrayList availableViewNames = new ArrayList();
/*  25 */   private String selectedView = "";
/*  26 */   private String[] monitorGroups = new String[0];
/*  27 */   private boolean fromMonitorTab = false;
/*  28 */   private boolean popUp = false;
/*  29 */   private boolean showCriticalMonitors = false;
/*  30 */   private boolean showOnlySubgroups = false;
/*  31 */   private boolean showTopLevelMgs = false;
/*  32 */   private boolean showTopLevelSubMgs = false;
/*  33 */   private String fromWhere = "popUp";
/*  34 */   private int noOfColumns = 6;
/*  35 */   private float xcanvas = 0.0F;
/*  36 */   private float ycanvas = 0.0F;
/*  37 */   private boolean isHtml = true;
/*     */   
/*     */   public void setFromWhere(String fromWhere)
/*     */   {
/*  41 */     this.fromWhere = fromWhere;
/*     */   }
/*     */   
/*     */   public String getFromWhere() {
/*  45 */     return this.fromWhere;
/*     */   }
/*     */   
/*     */   public int getNoOfColumns() {
/*  49 */     return this.noOfColumns;
/*     */   }
/*     */   
/*     */   public void setNoOfColumns(int noOfColumns) {
/*  53 */     this.noOfColumns = noOfColumns;
/*     */   }
/*     */   
/*     */   public boolean isShowTopLevelMgs() {
/*  57 */     return this.showTopLevelMgs;
/*     */   }
/*     */   
/*     */   public void setShowTopLevelMgs(boolean showTopLevelMgs) {
/*  61 */     this.showTopLevelMgs = showTopLevelMgs;
/*     */   }
/*     */   
/*     */   public boolean isShowTopLevelSubMgs() {
/*  65 */     return this.showTopLevelSubMgs;
/*     */   }
/*     */   
/*     */   public void setShowTopLevelSubMgs(boolean showTopLevelSubMg) {
/*  69 */     this.showTopLevelSubMgs = showTopLevelSubMg;
/*     */   }
/*     */   
/*     */   public boolean isShowOnlySubgroups() {
/*  73 */     return this.showOnlySubgroups;
/*     */   }
/*     */   
/*     */   public void setShowOnlySubgroups(boolean showOnlySubgroups) {
/*  77 */     this.showOnlySubgroups = showOnlySubgroups;
/*     */   }
/*     */   
/*     */   public boolean isShowCriticalMonitors()
/*     */   {
/*  82 */     return this.showCriticalMonitors;
/*     */   }
/*     */   
/*     */   public void setShowCriticalMonitors(boolean showCriticalMonitors) {
/*  86 */     this.showCriticalMonitors = showCriticalMonitors;
/*     */   }
/*     */   
/*     */   public String getDisplayName()
/*     */   {
/*  91 */     return this.displayName;
/*     */   }
/*     */   
/*     */   public void setDisplayName(String displayName) {
/*  95 */     this.displayName = displayName;
/*     */   }
/*     */   
/*     */   public String getDescription()
/*     */   {
/* 100 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/* 104 */     this.description = description;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getBgColor()
/*     */   {
/* 110 */     return this.bgColor;
/*     */   }
/*     */   
/*     */   public void setBgColor(String bgColor) {
/* 114 */     this.bgColor = bgColor;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getLineColor()
/*     */   {
/* 120 */     return this.lineColor;
/*     */   }
/*     */   
/*     */   public void setLineColor(String lineColor) {
/* 124 */     this.lineColor = lineColor;
/*     */   }
/*     */   
/*     */   public String getLabelColor()
/*     */   {
/* 129 */     return this.labelColor;
/*     */   }
/*     */   
/*     */   public void setLabelColor(String labelColor) {
/* 133 */     this.labelColor = labelColor;
/*     */   }
/*     */   
/*     */   public boolean isShowLabel()
/*     */   {
/* 138 */     return this.showLabel;
/*     */   }
/*     */   
/*     */   public void setShowLabel(boolean showLabel) {
/* 142 */     this.showLabel = showLabel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getLinethickness()
/*     */   {
/* 149 */     return this.linethickness;
/*     */   }
/*     */   
/*     */   public void setLinethickness(float linethickness) {
/* 153 */     this.linethickness = linethickness;
/*     */   }
/*     */   
/*     */   public int getReloadInterval()
/*     */   {
/* 158 */     return this.reloadInterval;
/*     */   }
/*     */   
/*     */   public void setReloadInterval(int reloadInterval) {
/* 162 */     this.reloadInterval = reloadInterval;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getRefreshInterval()
/*     */   {
/* 168 */     return this.refreshInterval;
/*     */   }
/*     */   
/*     */   public void setRefreshInterval(int refreshInterval) {
/* 172 */     this.refreshInterval = refreshInterval;
/*     */   }
/*     */   
/*     */   public float getLineTransparency()
/*     */   {
/* 177 */     return this.lineTransparency;
/*     */   }
/*     */   
/*     */   public void setLineTransparency(float lineTransparency) {
/* 181 */     this.lineTransparency = lineTransparency;
/*     */   }
/*     */   
/*     */   public ArrayList getAvailableViews()
/*     */   {
/* 186 */     return this.availableViews;
/*     */   }
/*     */   
/*     */   public void setAvailableViews(ArrayList availableViews) {
/* 190 */     this.availableViews = availableViews;
/*     */   }
/*     */   
/*     */   public ArrayList getAvailableViewNames()
/*     */   {
/* 195 */     return this.availableViewNames;
/*     */   }
/*     */   
/*     */   public void setAvailableViewNames(ArrayList availableViewNames) {
/* 199 */     this.availableViewNames = availableViewNames;
/*     */   }
/*     */   
/*     */ 
/*     */   public ArrayList getAvailableMgs()
/*     */   {
/* 205 */     return this.availableMgs;
/*     */   }
/*     */   
/*     */   public void setAvailableMgs(ArrayList availableMgs) {
/* 209 */     this.availableMgs = availableMgs;
/*     */   }
/*     */   
/*     */   public boolean isFromMonitorTab()
/*     */   {
/* 214 */     return this.fromMonitorTab;
/*     */   }
/*     */   
/*     */   public void setFromMonitorTab(boolean fromMonitorTab) {
/* 218 */     this.fromMonitorTab = fromMonitorTab;
/*     */   }
/*     */   
/*     */   public boolean isPopUp()
/*     */   {
/* 223 */     return this.popUp;
/*     */   }
/*     */   
/*     */   public void setPopUp(boolean popUp) {
/* 227 */     this.popUp = popUp;
/*     */   }
/*     */   
/*     */   public String[] getMonitorGroups()
/*     */   {
/* 232 */     return this.monitorGroups;
/*     */   }
/*     */   
/*     */   public void setMonitorGroups(String[] monitorGroups)
/*     */   {
/* 237 */     this.monitorGroups = monitorGroups;
/*     */   }
/*     */   
/*     */   public String getSelectedView()
/*     */   {
/* 242 */     return this.selectedView;
/*     */   }
/*     */   
/*     */   public void setSelectedView(String selectedView) {
/* 246 */     this.selectedView = selectedView;
/*     */   }
/*     */   
/*     */   public void setXcanvas(float xcanvas) {
/* 250 */     this.xcanvas = xcanvas;
/*     */   }
/*     */   
/*     */   public float getXcanvas() {
/* 254 */     return this.xcanvas;
/*     */   }
/*     */   
/*     */   public void setYcanvas(float ycanvas) {
/* 258 */     this.ycanvas = ycanvas;
/*     */   }
/*     */   
/*     */   public float getYcanvas() {
/* 262 */     return this.ycanvas;
/*     */   }
/*     */   
/* 265 */   public boolean getIsHtml() { return this.isHtml; }
/*     */   
/*     */   public void setIsHtml(boolean isHtml) {
/* 268 */     this.isHtml = isHtml;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\form\FlashForm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */