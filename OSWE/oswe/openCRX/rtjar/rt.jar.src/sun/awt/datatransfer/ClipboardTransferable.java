/*     */ package sun.awt.datatransfer;
/*     */ 
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClipboardTransferable
/*     */   implements Transferable
/*     */ {
/*  58 */   private final HashMap flavorsToData = new HashMap<>();
/*  59 */   private DataFlavor[] flavors = new DataFlavor[0];
/*     */   
/*     */   private final class DataFactory {
/*     */     final long format;
/*     */     
/*     */     DataFactory(long param1Long, byte[] param1ArrayOfbyte) {
/*  65 */       this.format = param1Long;
/*  66 */       this.data = param1ArrayOfbyte;
/*     */     }
/*     */     final byte[] data;
/*     */     public Object getTransferData(DataFlavor param1DataFlavor) throws IOException {
/*  70 */       return DataTransferer.getInstance()
/*  71 */         .translateBytes(this.data, param1DataFlavor, this.format, ClipboardTransferable.this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ClipboardTransferable(SunClipboard paramSunClipboard) {
/*  78 */     paramSunClipboard.openClipboard(null);
/*     */     
/*     */     try {
/*  81 */       long[] arrayOfLong = paramSunClipboard.getClipboardFormats();
/*     */       
/*  83 */       if (arrayOfLong != null && arrayOfLong.length > 0) {
/*     */ 
/*     */ 
/*     */         
/*  87 */         HashMap<Object, Object> hashMap = new HashMap<>(arrayOfLong.length, 1.0F);
/*     */ 
/*     */         
/*  90 */         Map map = DataTransferer.getInstance().getFlavorsForFormats(arrayOfLong, SunClipboard.getDefaultFlavorTable());
/*  91 */         Iterator<DataFlavor> iterator = map.keySet().iterator();
/*  92 */         while (iterator.hasNext()) {
/*     */           
/*  94 */           DataFlavor dataFlavor = iterator.next();
/*  95 */           Long long_ = (Long)map.get(dataFlavor);
/*     */           
/*  97 */           fetchOneFlavor(paramSunClipboard, dataFlavor, long_, hashMap);
/*     */         } 
/*     */         
/* 100 */         DataTransferer.getInstance(); this
/* 101 */           .flavors = DataTransferer.setToSortedDataFlavorArray(this.flavorsToData.keySet());
/*     */       } 
/*     */     } finally {
/* 104 */       paramSunClipboard.closeClipboard();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean fetchOneFlavor(SunClipboard paramSunClipboard, DataFlavor paramDataFlavor, Long paramLong, HashMap<Long, IOException> paramHashMap) {
/* 111 */     if (!this.flavorsToData.containsKey(paramDataFlavor)) {
/* 112 */       long l = paramLong.longValue();
/* 113 */       byte[] arrayOfByte = null;
/*     */       
/* 115 */       if (!paramHashMap.containsKey(paramLong)) {
/*     */         IOException iOException; try {
/* 117 */           arrayOfByte = paramSunClipboard.getClipboardData(l);
/* 118 */         } catch (IOException iOException1) {
/* 119 */           iOException = iOException1;
/* 120 */         } catch (Throwable throwable) {
/* 121 */           throwable.printStackTrace();
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 126 */         paramHashMap.put(paramLong, iOException);
/*     */       } else {
/* 128 */         arrayOfByte = (byte[])paramHashMap.get(paramLong);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 134 */       if (arrayOfByte instanceof IOException) {
/* 135 */         this.flavorsToData.put(paramDataFlavor, arrayOfByte);
/* 136 */         return false;
/* 137 */       }  if (arrayOfByte != null) {
/* 138 */         this.flavorsToData.put(paramDataFlavor, new DataFactory(l, arrayOfByte));
/*     */         
/* 140 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     return false;
/*     */   }
/*     */   
/*     */   public DataFlavor[] getTransferDataFlavors() {
/* 148 */     return (DataFlavor[])this.flavors.clone();
/*     */   }
/*     */   
/*     */   public boolean isDataFlavorSupported(DataFlavor paramDataFlavor) {
/* 152 */     return this.flavorsToData.containsKey(paramDataFlavor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getTransferData(DataFlavor paramDataFlavor) throws UnsupportedFlavorException, IOException {
/* 158 */     if (!isDataFlavorSupported(paramDataFlavor)) {
/* 159 */       throw new UnsupportedFlavorException(paramDataFlavor);
/*     */     }
/* 161 */     Object object = this.flavorsToData.get(paramDataFlavor);
/* 162 */     if (object instanceof IOException)
/*     */     {
/* 164 */       throw (IOException)object; } 
/* 165 */     if (object instanceof DataFactory) {
/*     */       
/* 167 */       DataFactory dataFactory = (DataFactory)object;
/* 168 */       object = dataFactory.getTransferData(paramDataFlavor);
/*     */     } 
/* 170 */     return object;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/datatransfer/ClipboardTransferable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */