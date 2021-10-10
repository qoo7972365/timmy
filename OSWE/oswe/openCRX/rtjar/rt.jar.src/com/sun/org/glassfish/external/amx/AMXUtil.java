/*    */ package com.sun.org.glassfish.external.amx;
/*    */ 
/*    */ import com.sun.org.glassfish.external.arc.Stability;
/*    */ import com.sun.org.glassfish.external.arc.Taxonomy;
/*    */ import javax.management.ObjectName;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @Taxonomy(stability = Stability.UNCOMMITTED)
/*    */ public final class AMXUtil
/*    */ {
/*    */   public static ObjectName newObjectName(String s) {
/*    */     try {
/* 47 */       return new ObjectName(s);
/*    */     }
/* 49 */     catch (Exception e) {
/*    */       
/* 51 */       throw new RuntimeException("bad ObjectName", e);
/*    */     } 
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
/*    */   public static ObjectName newObjectName(String domain, String props) {
/* 64 */     return newObjectName(domain + ":" + props);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ObjectName getMBeanServerDelegateObjectName() {
/* 72 */     return newObjectName("JMImplementation:type=MBeanServerDelegate");
/*    */   }
/*    */ 
/*    */   
/*    */   public static String prop(String key, String value) {
/* 77 */     return key + "=" + value;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/glassfish/external/amx/AMXUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */