/*    */ package com.sun.org.glassfish.external.probe.provider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StatsProviderInfo
/*    */ {
/*    */   private String configElement;
/*    */   private PluginPoint pp;
/*    */   private String subTreeRoot;
/*    */   private Object statsProvider;
/*    */   private String configLevelStr;
/*    */   private final String invokerId;
/*    */   
/*    */   public StatsProviderInfo(String configElement, PluginPoint pp, String subTreeRoot, Object statsProvider) {
/* 38 */     this(configElement, pp, subTreeRoot, statsProvider, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StatsProviderInfo(String configElement, PluginPoint pp, String subTreeRoot, Object statsProvider, String invokerId) {
/* 55 */     this.configLevelStr = null; this.configElement = configElement;
/*    */     this.pp = pp;
/*    */     this.subTreeRoot = subTreeRoot;
/*    */     this.statsProvider = statsProvider;
/* 59 */     this.invokerId = invokerId; } public String getConfigElement() { return this.configElement; }
/*    */ 
/*    */   
/*    */   public PluginPoint getPluginPoint() {
/* 63 */     return this.pp;
/*    */   }
/*    */   
/*    */   public String getSubTreeRoot() {
/* 67 */     return this.subTreeRoot;
/*    */   }
/*    */   
/*    */   public Object getStatsProvider() {
/* 71 */     return this.statsProvider;
/*    */   }
/*    */   
/*    */   public String getConfigLevel() {
/* 75 */     return this.configLevelStr;
/*    */   }
/*    */   
/*    */   public void setConfigLevel(String configLevelStr) {
/* 79 */     this.configLevelStr = configLevelStr;
/*    */   }
/*    */   
/*    */   public String getInvokerId() {
/* 83 */     return this.invokerId;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/glassfish/external/probe/provider/StatsProviderInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */