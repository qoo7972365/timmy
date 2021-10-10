/*    */ package sun.corba;
/*    */ 
/*    */ import com.sun.corba.se.impl.io.ValueUtility;
/*    */ import java.lang.reflect.Method;
/*    */ import sun.misc.JavaOISAccess;
/*    */ import sun.misc.Unsafe;
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
/*    */ public class SharedSecrets
/*    */ {
/* 45 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*    */   
/*    */   private static JavaCorbaAccess javaCorbaAccess;
/*    */   private static final Method getJavaOISAccessMethod;
/*    */   private static JavaOISAccess javaOISAccess;
/*    */   
/*    */   static {
/*    */     try {
/* 53 */       Class<?> clazz = Class.forName("sun.misc.SharedSecrets");
/*    */       
/* 55 */       getJavaOISAccessMethod = clazz.getMethod("getJavaOISAccess", new Class[0]);
/* 56 */     } catch (Exception exception) {
/* 57 */       throw new ExceptionInInitializerError(exception);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static JavaOISAccess getJavaOISAccess() {
/* 62 */     if (javaOISAccess == null) {
/*    */       
/*    */       try {
/* 65 */         javaOISAccess = (JavaOISAccess)getJavaOISAccessMethod.invoke(null, new Object[0]);
/* 66 */       } catch (Exception exception) {
/* 67 */         throw new ExceptionInInitializerError(exception);
/*    */       } 
/*    */     }
/* 70 */     return javaOISAccess;
/*    */   }
/*    */   
/*    */   public static JavaCorbaAccess getJavaCorbaAccess() {
/* 74 */     if (javaCorbaAccess == null)
/*    */     {
/*    */       
/* 77 */       unsafe.ensureClassInitialized(ValueUtility.class);
/*    */     }
/* 79 */     return javaCorbaAccess;
/*    */   }
/*    */   
/*    */   public static void setJavaCorbaAccess(JavaCorbaAccess paramJavaCorbaAccess) {
/* 83 */     javaCorbaAccess = paramJavaCorbaAccess;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/corba/SharedSecrets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */