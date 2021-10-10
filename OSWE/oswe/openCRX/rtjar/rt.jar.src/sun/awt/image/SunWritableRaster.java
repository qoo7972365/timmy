/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.DataBufferUShort;
/*     */ import java.awt.image.SampleModel;
/*     */ import java.awt.image.WritableRaster;
/*     */ import sun.java2d.StateTrackable;
/*     */ import sun.java2d.StateTrackableDelegate;
/*     */ import sun.java2d.SurfaceData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SunWritableRaster
/*     */   extends WritableRaster
/*     */ {
/*     */   private static DataStealer stealer;
/*     */   private StateTrackableDelegate theTrackable;
/*     */   
/*     */   public static void setDataStealer(DataStealer paramDataStealer) {
/*  62 */     if (stealer != null) {
/*  63 */       throw new InternalError("Attempt to set DataStealer twice");
/*     */     }
/*  65 */     stealer = paramDataStealer;
/*     */   }
/*     */   
/*     */   public static byte[] stealData(DataBufferByte paramDataBufferByte, int paramInt) {
/*  69 */     return stealer.getData(paramDataBufferByte, paramInt);
/*     */   }
/*     */   
/*     */   public static short[] stealData(DataBufferUShort paramDataBufferUShort, int paramInt) {
/*  73 */     return stealer.getData(paramDataBufferUShort, paramInt);
/*     */   }
/*     */   
/*     */   public static int[] stealData(DataBufferInt paramDataBufferInt, int paramInt) {
/*  77 */     return stealer.getData(paramDataBufferInt, paramInt);
/*     */   }
/*     */   
/*     */   public static StateTrackableDelegate stealTrackable(DataBuffer paramDataBuffer) {
/*  81 */     return stealer.getTrackable(paramDataBuffer);
/*     */   }
/*     */   
/*     */   public static void setTrackable(DataBuffer paramDataBuffer, StateTrackableDelegate paramStateTrackableDelegate) {
/*  85 */     stealer.setTrackable(paramDataBuffer, paramStateTrackableDelegate);
/*     */   }
/*     */   
/*     */   public static void makeTrackable(DataBuffer paramDataBuffer) {
/*  89 */     stealer.setTrackable(paramDataBuffer, StateTrackableDelegate.createInstance(StateTrackable.State.STABLE));
/*     */   }
/*     */   
/*     */   public static void markDirty(DataBuffer paramDataBuffer) {
/*  93 */     stealer.getTrackable(paramDataBuffer).markDirty();
/*     */   }
/*     */   
/*     */   public static void markDirty(WritableRaster paramWritableRaster) {
/*  97 */     if (paramWritableRaster instanceof SunWritableRaster) {
/*  98 */       ((SunWritableRaster)paramWritableRaster).markDirty();
/*     */     } else {
/* 100 */       markDirty(paramWritableRaster.getDataBuffer());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void markDirty(Image paramImage) {
/* 105 */     SurfaceData.getPrimarySurfaceData(paramImage).markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SunWritableRaster(SampleModel paramSampleModel, Point paramPoint) {
/* 111 */     super(paramSampleModel, paramPoint);
/* 112 */     this.theTrackable = stealTrackable(this.dataBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SunWritableRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Point paramPoint) {
/* 119 */     super(paramSampleModel, paramDataBuffer, paramPoint);
/* 120 */     this.theTrackable = stealTrackable(paramDataBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SunWritableRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer, Rectangle paramRectangle, Point paramPoint, WritableRaster paramWritableRaster) {
/* 129 */     super(paramSampleModel, paramDataBuffer, paramRectangle, paramPoint, paramWritableRaster);
/* 130 */     this.theTrackable = stealTrackable(paramDataBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void markDirty() {
/* 137 */     this.theTrackable.markDirty();
/*     */   }
/*     */   
/*     */   public static interface DataStealer {
/*     */     byte[] getData(DataBufferByte param1DataBufferByte, int param1Int);
/*     */     
/*     */     short[] getData(DataBufferUShort param1DataBufferUShort, int param1Int);
/*     */     
/*     */     int[] getData(DataBufferInt param1DataBufferInt, int param1Int);
/*     */     
/*     */     StateTrackableDelegate getTrackable(DataBuffer param1DataBuffer);
/*     */     
/*     */     void setTrackable(DataBuffer param1DataBuffer, StateTrackableDelegate param1StateTrackableDelegate);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/SunWritableRaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */