/*    */ package com.sun.jmx.mbeanserver;
/*    */ 
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.WeakHashMap;
/*    */ import javax.management.Descriptor;
/*    */ import javax.management.ImmutableDescriptor;
/*    */ import javax.management.JMX;
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
/*    */ public class DescriptorCache
/*    */ {
/*    */   static DescriptorCache getInstance() {
/* 39 */     return instance;
/*    */   }
/*    */   
/*    */   public static DescriptorCache getInstance(JMX paramJMX) {
/* 43 */     if (paramJMX != null) {
/* 44 */       return instance;
/*    */     }
/* 46 */     return null;
/*    */   }
/*    */   
/*    */   public ImmutableDescriptor get(ImmutableDescriptor paramImmutableDescriptor) {
/* 50 */     WeakReference<ImmutableDescriptor> weakReference = this.map.get(paramImmutableDescriptor);
/* 51 */     ImmutableDescriptor immutableDescriptor = (weakReference == null) ? null : weakReference.get();
/* 52 */     if (immutableDescriptor != null)
/* 53 */       return immutableDescriptor; 
/* 54 */     this.map.put(paramImmutableDescriptor, new WeakReference<>(paramImmutableDescriptor));
/* 55 */     return paramImmutableDescriptor;
/*    */   }
/*    */   
/*    */   public ImmutableDescriptor union(Descriptor... paramVarArgs) {
/* 59 */     return get(ImmutableDescriptor.union(paramVarArgs));
/*    */   }
/*    */   
/* 62 */   private static final DescriptorCache instance = new DescriptorCache();
/* 63 */   private final WeakHashMap<ImmutableDescriptor, WeakReference<ImmutableDescriptor>> map = new WeakHashMap<>();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/DescriptorCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */