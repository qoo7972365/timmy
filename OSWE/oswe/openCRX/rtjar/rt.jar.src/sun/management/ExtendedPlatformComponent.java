/*    */ package sun.management;
/*    */ 
/*    */ import java.lang.management.PlatformManagedObject;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ public final class ExtendedPlatformComponent
/*    */ {
/*    */   public static List<? extends PlatformManagedObject> getMXBeans() {
/* 43 */     return Collections.emptyList();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T extends PlatformManagedObject> T getMXBean(Class<T> paramClass) {
/* 52 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/ExtendedPlatformComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */