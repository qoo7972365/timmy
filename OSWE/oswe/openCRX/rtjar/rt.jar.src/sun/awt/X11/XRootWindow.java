/*    */ package sun.awt.X11;
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
/*    */ class XRootWindow
/*    */   extends XBaseWindow
/*    */ {
/*    */   private static class LazyHolder
/*    */   {
/*    */     private static final XRootWindow xawtRootWindow;
/*    */     
/*    */     static {
/* 38 */       XToolkit.awtLock();
/*    */       try {
/* 40 */         xawtRootWindow = new XRootWindow();
/* 41 */         xawtRootWindow.init(xawtRootWindow.getDelayedParams().delete("delayed"));
/*    */       } finally {
/* 43 */         XToolkit.awtUnlock();
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   static XRootWindow getInstance() {
/* 49 */     return LazyHolder.xawtRootWindow;
/*    */   }
/*    */   
/*    */   private XRootWindow() {
/* 53 */     super(new XCreateWindowParams(new Object[] { "delayed", Boolean.TRUE, "event mask", 
/* 54 */             Long.valueOf(131072L) }));
/*    */   }
/*    */   
/*    */   public void postInit(XCreateWindowParams paramXCreateWindowParams) {
/* 58 */     super.postInit(paramXCreateWindowParams);
/* 59 */     setWMClass(getWMClass());
/*    */   }
/*    */   
/*    */   protected String getWMName() {
/* 63 */     return XToolkit.getAWTAppClassName();
/*    */   }
/*    */   protected String[] getWMClass() {
/* 66 */     return new String[] { XToolkit.getAWTAppClassName(), XToolkit.getAWTAppClassName() };
/*    */   }
/*    */ 
/*    */   
/*    */   private static long getXRootWindow() {
/* 71 */     return getXAWTRootWindow().getWindow();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XRootWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */