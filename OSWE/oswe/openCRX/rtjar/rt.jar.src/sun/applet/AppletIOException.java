/*    */ package sun.applet;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AppletIOException
/*    */   extends IOException
/*    */ {
/* 37 */   private String key = null;
/* 38 */   private Object msgobj = null;
/*    */   
/*    */   public AppletIOException(String paramString) {
/* 41 */     super(paramString);
/* 42 */     this.key = paramString;
/*    */   }
/*    */   
/*    */   public AppletIOException(String paramString, Object paramObject) {
/* 46 */     this(paramString);
/* 47 */     this.msgobj = paramObject;
/*    */   }
/*    */   
/*    */   public String getLocalizedMessage() {
/* 51 */     if (this.msgobj != null) {
/* 52 */       return amh.getMessage(this.key, this.msgobj);
/*    */     }
/* 54 */     return amh.getMessage(this.key);
/*    */   }
/*    */   
/* 57 */   private static AppletMessageHandler amh = new AppletMessageHandler("appletioexception");
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/applet/AppletIOException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */