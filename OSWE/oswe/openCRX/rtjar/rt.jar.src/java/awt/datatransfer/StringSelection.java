/*     */ package java.awt.datatransfer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
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
/*     */ public class StringSelection
/*     */   implements Transferable, ClipboardOwner
/*     */ {
/*     */   private static final int STRING = 0;
/*     */   private static final int PLAIN_TEXT = 1;
/*  50 */   private static final DataFlavor[] flavors = new DataFlavor[] { DataFlavor.stringFlavor, DataFlavor.plainTextFlavor };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringSelection(String paramString) {
/*  62 */     this.data = paramString;
/*     */   }
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
/*     */   public DataFlavor[] getTransferDataFlavors() {
/*  78 */     return (DataFlavor[])flavors.clone();
/*     */   }
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
/*     */   public boolean isDataFlavorSupported(DataFlavor paramDataFlavor) {
/*  94 */     for (byte b = 0; b < flavors.length; b++) {
/*  95 */       if (paramDataFlavor.equals(flavors[b])) {
/*  96 */         return true;
/*     */       }
/*     */     } 
/*  99 */     return false;
/*     */   }
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
/*     */   public Object getTransferData(DataFlavor paramDataFlavor) throws UnsupportedFlavorException, IOException {
/* 130 */     if (paramDataFlavor.equals(flavors[0]))
/* 131 */       return this.data; 
/* 132 */     if (paramDataFlavor.equals(flavors[1])) {
/* 133 */       return new StringReader((this.data == null) ? "" : this.data);
/*     */     }
/* 135 */     throw new UnsupportedFlavorException(paramDataFlavor);
/*     */   }
/*     */   
/*     */   public void lostOwnership(Clipboard paramClipboard, Transferable paramTransferable) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/datatransfer/StringSelection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */