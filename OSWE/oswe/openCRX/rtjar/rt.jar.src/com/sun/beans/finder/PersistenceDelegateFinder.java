/*    */ package com.sun.beans.finder;
/*    */ 
/*    */ import java.beans.PersistenceDelegate;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PersistenceDelegateFinder
/*    */   extends InstanceFinder<PersistenceDelegate>
/*    */ {
/*    */   private final Map<Class<?>, PersistenceDelegate> registry;
/*    */   
/*    */   public PersistenceDelegateFinder() {
/* 45 */     super(PersistenceDelegate.class, true, "PersistenceDelegate", new String[0]);
/* 46 */     this.registry = new HashMap<>();
/*    */   }
/*    */   
/*    */   public void register(Class<?> paramClass, PersistenceDelegate paramPersistenceDelegate) {
/* 50 */     synchronized (this.registry) {
/* 51 */       if (paramPersistenceDelegate != null) {
/* 52 */         this.registry.put(paramClass, paramPersistenceDelegate);
/*    */       } else {
/*    */         
/* 55 */         this.registry.remove(paramClass);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public PersistenceDelegate find(Class<?> paramClass) {
/*    */     PersistenceDelegate persistenceDelegate;
/* 63 */     synchronized (this.registry) {
/* 64 */       persistenceDelegate = this.registry.get(paramClass);
/*    */     } 
/* 66 */     return (persistenceDelegate != null) ? persistenceDelegate : super.find(paramClass);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/finder/PersistenceDelegateFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */