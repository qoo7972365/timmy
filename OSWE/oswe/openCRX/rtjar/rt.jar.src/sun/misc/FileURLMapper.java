/*    */ package sun.misc;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.net.URL;
/*    */ import sun.net.www.ParseUtil;
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
/*    */ 
/*    */ public class FileURLMapper
/*    */ {
/*    */   URL url;
/*    */   String path;
/*    */   
/*    */   public FileURLMapper(URL paramURL) {
/* 49 */     this.url = paramURL;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPath() {
/* 58 */     if (this.path != null) {
/* 59 */       return this.path;
/*    */     }
/* 61 */     String str = this.url.getHost();
/* 62 */     if (str == null || "".equals(str) || "localhost".equalsIgnoreCase(str)) {
/* 63 */       this.path = this.url.getFile();
/* 64 */       this.path = ParseUtil.decode(this.path);
/*    */     } 
/* 66 */     return this.path;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean exists() {
/* 73 */     String str = getPath();
/* 74 */     if (str == null) {
/* 75 */       return false;
/*    */     }
/* 77 */     File file = new File(str);
/* 78 */     return file.exists();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/FileURLMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */