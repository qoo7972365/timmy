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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class XDnDConstants
/*    */ {
/* 36 */   static final XAtom XA_XdndActionCopy = XAtom.get("XdndActionCopy");
/* 37 */   static final XAtom XA_XdndActionMove = XAtom.get("XdndActionMove");
/* 38 */   static final XAtom XA_XdndActionLink = XAtom.get("XdndActionLink");
/* 39 */   static final XAtom XA_XdndActionList = XAtom.get("XdndActionList");
/* 40 */   static final XAtom XA_XdndTypeList = XAtom.get("XdndTypeList");
/* 41 */   static final XAtom XA_XdndAware = XAtom.get("XdndAware");
/* 42 */   static final XAtom XA_XdndProxy = XAtom.get("XdndProxy");
/* 43 */   static final XAtom XA_XdndSelection = XAtom.get("XdndSelection");
/* 44 */   static final XAtom XA_XdndEnter = XAtom.get("XdndEnter");
/* 45 */   static final XAtom XA_XdndPosition = XAtom.get("XdndPosition");
/* 46 */   static final XAtom XA_XdndLeave = XAtom.get("XdndLeave");
/* 47 */   static final XAtom XA_XdndDrop = XAtom.get("XdndDrop");
/* 48 */   static final XAtom XA_XdndStatus = XAtom.get("XdndStatus");
/* 49 */   static final XAtom XA_XdndFinished = XAtom.get("XdndFinished");
/*    */   
/* 51 */   static final XSelection XDnDSelection = new XSelection(XA_XdndSelection);
/*    */   
/*    */   public static final int XDND_MIN_PROTOCOL_VERSION = 3;
/*    */   
/*    */   public static final int XDND_PROTOCOL_VERSION = 5;
/*    */   
/*    */   public static final int XDND_PROTOCOL_MASK = -16777216;
/*    */   
/*    */   public static final int XDND_PROTOCOL_SHIFT = 24;
/*    */   public static final int XDND_DATA_TYPES_BIT = 1;
/*    */   public static final int XDND_ACCEPT_DROP_FLAG = 1;
/*    */   
/*    */   static long getXDnDActionForJavaAction(int paramInt) {
/* 64 */     switch (paramInt) { case 1:
/* 65 */         return XA_XdndActionCopy.getAtom();
/* 66 */       case 2: return XA_XdndActionMove.getAtom();
/* 67 */       case 1073741824: return XA_XdndActionLink.getAtom(); }
/* 68 */      return 0L;
/*    */   }
/*    */ 
/*    */   
/*    */   static int getJavaActionForXDnDAction(long paramLong) {
/* 73 */     if (paramLong == XA_XdndActionCopy.getAtom())
/* 74 */       return 1; 
/* 75 */     if (paramLong == XA_XdndActionMove.getAtom())
/* 76 */       return 2; 
/* 77 */     if (paramLong == XA_XdndActionLink.getAtom()) {
/* 78 */       return 1073741824;
/*    */     }
/* 80 */     return 0;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XDnDConstants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */