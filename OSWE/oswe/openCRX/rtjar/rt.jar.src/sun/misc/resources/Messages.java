/*    */ package sun.misc.resources;
/*    */ 
/*    */ import java.util.ListResourceBundle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Messages
/*    */   extends ListResourceBundle
/*    */ {
/*    */   protected Object[][] getContents() {
/* 43 */     return new Object[][] { { "optpkg.versionerror", "ERROR: Invalid version format used in {0} JAR file. Check the documentation for the supported version format." }, { "optpkg.attributeerror", "ERROR: The required {0} JAR manifest attribute is not set in {1} JAR file." }, { "optpkg.attributeserror", "ERROR: Some required JAR manifest attributes are not set in {0} JAR file." } };
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/resources/Messages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */