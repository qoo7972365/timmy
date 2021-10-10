/*    */ package sun.reflect.misc;
/*    */ 
/*    */ import java.lang.reflect.Field;
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
/*    */ public final class FieldUtil
/*    */ {
/*    */   public static Field getField(Class<?> paramClass, String paramString) throws NoSuchFieldException {
/* 40 */     ReflectUtil.checkPackageAccess(paramClass);
/* 41 */     return paramClass.getField(paramString);
/*    */   }
/*    */   
/*    */   public static Field[] getFields(Class<?> paramClass) {
/* 45 */     ReflectUtil.checkPackageAccess(paramClass);
/* 46 */     return paramClass.getFields();
/*    */   }
/*    */   
/*    */   public static Field[] getDeclaredFields(Class<?> paramClass) {
/* 50 */     ReflectUtil.checkPackageAccess(paramClass);
/* 51 */     return paramClass.getDeclaredFields();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/misc/FieldUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */