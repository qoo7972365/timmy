/*     */ package com.sun.xml.internal.ws.api.server;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum LazyMOMProvider
/*     */ {
/*  91 */   INSTANCE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile Scope scope;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Set<DefaultScopeChangeListener> listeners;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Set<WSEndpointScopeChangeListener> endpointsWaitingForMOM;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LazyMOMProvider() {
/* 135 */     this.endpointsWaitingForMOM = new HashSet<>();
/* 136 */     this.listeners = new HashSet<>();
/*     */     
/* 138 */     this.scope = Scope.STANDALONE;
/*     */   }
/*     */   
/*     */   public static interface WSEndpointScopeChangeListener
/*     */     extends ScopeChangeListener {}
/*     */   
/*     */   public static interface DefaultScopeChangeListener
/*     */     extends ScopeChangeListener {}
/*     */   
/*     */   public void initMOMForScope(Scope scope) {
/* 148 */     if (this.scope == Scope.GLASSFISH_JMX || (scope == Scope.STANDALONE && (this.scope == Scope.GLASSFISH_JMX || this.scope == Scope.GLASSFISH_NO_JMX)) || this.scope == scope) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 154 */     this.scope = scope;
/*     */     
/* 156 */     fireScopeChanged();
/*     */   }
/*     */   
/*     */   public static interface ScopeChangeListener {
/*     */     void scopeChanged(LazyMOMProvider.Scope param1Scope); }
/*     */   
/*     */   private void fireScopeChanged() {
/* 163 */     for (ScopeChangeListener wsEndpoint : this.endpointsWaitingForMOM) {
/* 164 */       wsEndpoint.scopeChanged(this.scope);
/*     */     }
/*     */     
/* 167 */     for (ScopeChangeListener listener : this.listeners) {
/* 168 */       listener.scopeChanged(this.scope);
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Scope
/*     */   {
/*     */     STANDALONE, GLASSFISH_NO_JMX, GLASSFISH_JMX;
/*     */   }
/*     */   
/*     */   public void registerListener(DefaultScopeChangeListener listener) {
/* 178 */     this.listeners.add(listener);
/*     */     
/* 180 */     if (!isProviderInDefaultScope()) {
/* 181 */       listener.scopeChanged(this.scope);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isProviderInDefaultScope() {
/* 192 */     return (this.scope == Scope.STANDALONE);
/*     */   }
/*     */   
/*     */   public Scope getScope() {
/* 196 */     return this.scope;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEndpoint(WSEndpointScopeChangeListener wsEndpoint) {
/* 208 */     this.endpointsWaitingForMOM.add(wsEndpoint);
/*     */     
/* 210 */     if (!isProviderInDefaultScope()) {
/* 211 */       wsEndpoint.scopeChanged(this.scope);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterEndpoint(WSEndpointScopeChangeListener wsEndpoint) {
/* 221 */     this.endpointsWaitingForMOM.remove(wsEndpoint);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/server/LazyMOMProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */