/*    */ package sun.misc;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FilenameFilter;
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
/*    */ public class JarFilter
/*    */   implements FilenameFilter
/*    */ {
/*    */   public boolean accept(File paramFile, String paramString) {
/* 42 */     String str = paramString.toLowerCase();
/* 43 */     return (str.endsWith(".jar") || str.endsWith(".zip"));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/JarFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */