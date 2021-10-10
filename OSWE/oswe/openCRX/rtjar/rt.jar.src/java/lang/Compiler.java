/*    */ package java.lang;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Compiler
/*    */ {
/*    */   private static native void initialize();
/*    */   
/*    */   private static native void registerNatives();
/*    */   
/*    */   public static native boolean compileClass(Class<?> paramClass);
/*    */   
/*    */   public static native boolean compileClasses(String paramString);
/*    */   
/*    */   public static native Object command(Object paramObject);
/*    */   
/*    */   public static native void enable();
/*    */   
/*    */   public static native void disable();
/*    */   
/*    */   static {
/* 55 */     registerNatives();
/* 56 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*    */         {
/*    */           public Void run() {
/* 59 */             boolean bool = false;
/* 60 */             String str1 = System.getProperty("java.compiler");
/* 61 */             if (str1 != null && !str1.equals("NONE") && 
/* 62 */               !str1.equals("")) {
/*    */               
/*    */               try {
/* 65 */                 System.loadLibrary(str1);
/* 66 */                 Compiler.initialize();
/* 67 */                 bool = true;
/* 68 */               } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
/* 69 */                 System.err.println("Warning: JIT compiler \"" + str1 + "\" not found. Will use interpreter.");
/*    */               } 
/*    */             }
/*    */             
/* 73 */             String str2 = System.getProperty("java.vm.info");
/* 74 */             if (bool) {
/* 75 */               System.setProperty("java.vm.info", str2 + ", " + str1);
/*    */             } else {
/* 77 */               System.setProperty("java.vm.info", str2 + ", nojit");
/*    */             } 
/* 79 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/Compiler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */