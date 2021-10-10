/*    */ package com.sun.xml.internal.ws.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Properties;
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
/*    */ public final class Version
/*    */ {
/*    */   public final String BUILD_ID;
/*    */   public final String BUILD_VERSION;
/*    */   public final String MAJOR_VERSION;
/*    */   public final String SVN_REVISION;
/* 59 */   public static final Version RUNTIME_VERSION = create(Version.class.getResourceAsStream("version.properties"));
/*    */   
/*    */   private Version(String buildId, String buildVersion, String majorVersion, String svnRev) {
/* 62 */     this.BUILD_ID = fixNull(buildId);
/* 63 */     this.BUILD_VERSION = fixNull(buildVersion);
/* 64 */     this.MAJOR_VERSION = fixNull(majorVersion);
/* 65 */     this.SVN_REVISION = fixNull(svnRev);
/*    */   }
/*    */   
/*    */   public static Version create(InputStream is) {
/* 69 */     Properties props = new Properties();
/*    */     try {
/* 71 */       props.load(is);
/* 72 */     } catch (IOException iOException) {
/*    */     
/* 74 */     } catch (Exception exception) {}
/*    */ 
/*    */ 
/*    */     
/* 78 */     return new Version(props
/* 79 */         .getProperty("build-id"), props
/* 80 */         .getProperty("build-version"), props
/* 81 */         .getProperty("major-version"), props
/* 82 */         .getProperty("svn-revision"));
/*    */   }
/*    */   
/*    */   private String fixNull(String v) {
/* 86 */     if (v == null) return "unknown"; 
/* 87 */     return v;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 91 */     return this.BUILD_VERSION + " svn-revision#" + this.SVN_REVISION;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/Version.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */