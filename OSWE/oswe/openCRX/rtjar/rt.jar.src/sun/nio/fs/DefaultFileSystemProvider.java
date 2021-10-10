/*    */ package sun.nio.fs;
/*    */ 
/*    */ import java.nio.file.spi.FileSystemProvider;
/*    */ import java.security.AccessController;
/*    */ import sun.security.action.GetPropertyAction;
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
/*    */ public class DefaultFileSystemProvider
/*    */ {
/*    */   private static FileSystemProvider createProvider(String paramString) {
/*    */     Class<?> clazz;
/*    */     try {
/* 43 */       clazz = Class.forName(paramString);
/* 44 */     } catch (ClassNotFoundException classNotFoundException) {
/* 45 */       throw new AssertionError(classNotFoundException);
/*    */     } 
/*    */     try {
/* 48 */       return (FileSystemProvider)clazz.newInstance();
/* 49 */     } catch (IllegalAccessException|InstantiationException illegalAccessException) {
/* 50 */       throw new AssertionError(illegalAccessException);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static FileSystemProvider create() {
/* 59 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("os.name"));
/* 60 */     if (str.equals("SunOS"))
/* 61 */       return createProvider("sun.nio.fs.SolarisFileSystemProvider"); 
/* 62 */     if (str.equals("Linux"))
/* 63 */       return createProvider("sun.nio.fs.LinuxFileSystemProvider"); 
/* 64 */     if (str.contains("OS X"))
/* 65 */       return createProvider("sun.nio.fs.MacOSXFileSystemProvider"); 
/* 66 */     if (str.equals("AIX"))
/* 67 */       return createProvider("sun.nio.fs.AixFileSystemProvider"); 
/* 68 */     throw new AssertionError("Platform not recognized");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/DefaultFileSystemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */