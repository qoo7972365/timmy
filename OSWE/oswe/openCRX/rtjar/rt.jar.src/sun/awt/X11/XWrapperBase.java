/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.util.logging.PlatformLogger;
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
/*    */ abstract class XWrapperBase
/*    */ {
/* 32 */   static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.wrappers");
/*    */   
/*    */   public String toString() {
/* 35 */     String str = "";
/*    */     
/* 37 */     str = str + getName() + " = " + getFieldsAsString();
/*    */     
/* 39 */     return str;
/*    */   }
/*    */   
/*    */   String getFieldsAsString() {
/* 43 */     return "";
/*    */   }
/*    */   
/*    */   String getName() {
/* 47 */     return "XWrapperBase";
/*    */   }
/*    */   public void zero() {
/* 50 */     log.finest("Cleaning memory");
/* 51 */     if (getPData() != 0L) {
/* 52 */       XlibWrapper.unsafe.setMemory(getPData(), getDataSize(), (byte)0);
/*    */     }
/*    */   }
/*    */   
/*    */   String getWindow(long paramLong) {
/* 57 */     XBaseWindow xBaseWindow = XToolkit.windowToXWindow(paramLong);
/* 58 */     if (xBaseWindow == null) {
/* 59 */       return Long.toHexString(paramLong);
/*    */     }
/* 61 */     return xBaseWindow.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public XEvent clone() {
/* 66 */     long l = XlibWrapper.unsafe.allocateMemory(getDataSize());
/* 67 */     XlibWrapper.unsafe.copyMemory(getPData(), l, getDataSize());
/* 68 */     return new XEvent(l);
/*    */   }
/*    */   
/*    */   public abstract int getDataSize();
/*    */   
/*    */   public abstract long getPData();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XWrapperBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */