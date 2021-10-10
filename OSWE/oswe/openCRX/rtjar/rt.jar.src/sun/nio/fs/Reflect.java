/*    */ package sun.nio.fs;
/*    */ 
/*    */ import java.lang.reflect.AccessibleObject;
/*    */ import java.lang.reflect.Field;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
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
/*    */ class Reflect
/*    */ {
/*    */   private static void setAccessible(final AccessibleObject ao) {
/* 40 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */         {
/*    */           public Object run() {
/* 43 */             ao.setAccessible(true);
/* 44 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static Field lookupField(String paramString1, String paramString2) {
/*    */     try {
/* 53 */       Class<?> clazz = Class.forName(paramString1);
/* 54 */       Field field = clazz.getDeclaredField(paramString2);
/* 55 */       setAccessible(field);
/* 56 */       return field;
/* 57 */     } catch (ClassNotFoundException classNotFoundException) {
/* 58 */       throw new AssertionError(classNotFoundException);
/* 59 */     } catch (NoSuchFieldException noSuchFieldException) {
/* 60 */       throw new AssertionError(noSuchFieldException);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/Reflect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */