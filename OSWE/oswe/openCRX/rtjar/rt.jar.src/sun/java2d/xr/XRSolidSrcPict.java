/*    */ package sun.java2d.xr;
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
/*    */ public class XRSolidSrcPict
/*    */ {
/*    */   XRBackend con;
/*    */   XRSurfaceData srcPict;
/*    */   XRColor xrCol;
/*    */   int curPixVal;
/*    */   
/*    */   public XRSolidSrcPict(XRBackend paramXRBackend, int paramInt) {
/* 36 */     this.con = paramXRBackend;
/*    */     
/* 38 */     this.xrCol = new XRColor();
/* 39 */     this.curPixVal = -16777216;
/*    */     
/* 41 */     int i = paramXRBackend.createPixmap(paramInt, 32, 1, 1);
/* 42 */     int j = paramXRBackend.createPicture(i, 0);
/* 43 */     paramXRBackend.setPictureRepeat(j, 1);
/* 44 */     paramXRBackend.renderRectangle(j, (byte)1, XRColor.FULL_ALPHA, 0, 0, 1, 1);
/* 45 */     this.srcPict = new XRSurfaceData.XRInternalSurfaceData(paramXRBackend, j);
/*    */   }
/*    */   
/*    */   public XRSurfaceData prepareSrcPict(int paramInt) {
/* 49 */     if (paramInt != this.curPixVal) {
/* 50 */       this.xrCol.setColorValues(paramInt, false);
/* 51 */       this.con.renderRectangle(this.srcPict.picture, (byte)1, this.xrCol, 0, 0, 1, 1);
/* 52 */       this.curPixVal = paramInt;
/*    */     } 
/*    */     
/* 55 */     return this.srcPict;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRSolidSrcPict.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */