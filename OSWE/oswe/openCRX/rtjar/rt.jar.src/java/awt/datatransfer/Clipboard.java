/*     */ package java.awt.datatransfer;
/*     */ 
/*     */ import java.awt.EventQueue;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import sun.awt.EventListenerAggregate;
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
/*     */ public class Clipboard
/*     */ {
/*     */   String name;
/*     */   protected ClipboardOwner owner;
/*     */   protected Transferable contents;
/*     */   private EventListenerAggregate flavorListeners;
/*     */   private Set<DataFlavor> currentDataFlavors;
/*     */   
/*     */   public Clipboard(String paramString) {
/*  82 */     this.name = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  91 */     return this.name;
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
/*     */   public synchronized void setContents(Transferable paramTransferable, ClipboardOwner paramClipboardOwner) {
/* 120 */     final ClipboardOwner oldOwner = this.owner;
/* 121 */     final Transferable oldContents = this.contents;
/*     */     
/* 123 */     this.owner = paramClipboardOwner;
/* 124 */     this.contents = paramTransferable;
/*     */     
/* 126 */     if (clipboardOwner != null && clipboardOwner != paramClipboardOwner) {
/* 127 */       EventQueue.invokeLater(new Runnable() {
/*     */             public void run() {
/* 129 */               oldOwner.lostOwnership(Clipboard.this, oldContents);
/*     */             }
/*     */           });
/*     */     }
/* 133 */     fireFlavorsChanged();
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
/*     */   public synchronized Transferable getContents(Object paramObject) {
/* 151 */     return this.contents;
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
/*     */   public DataFlavor[] getAvailableDataFlavors() {
/* 169 */     Transferable transferable = getContents(null);
/* 170 */     if (transferable == null) {
/* 171 */       return new DataFlavor[0];
/*     */     }
/* 173 */     return transferable.getTransferDataFlavors();
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
/*     */   public boolean isDataFlavorAvailable(DataFlavor paramDataFlavor) {
/* 192 */     if (paramDataFlavor == null) {
/* 193 */       throw new NullPointerException("flavor");
/*     */     }
/*     */     
/* 196 */     Transferable transferable = getContents(null);
/* 197 */     if (transferable == null) {
/* 198 */       return false;
/*     */     }
/* 200 */     return transferable.isDataFlavorSupported(paramDataFlavor);
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
/*     */   public Object getData(DataFlavor paramDataFlavor) throws UnsupportedFlavorException, IOException {
/* 227 */     if (paramDataFlavor == null) {
/* 228 */       throw new NullPointerException("flavor");
/*     */     }
/*     */     
/* 231 */     Transferable transferable = getContents(null);
/* 232 */     if (transferable == null) {
/* 233 */       throw new UnsupportedFlavorException(paramDataFlavor);
/*     */     }
/* 235 */     return transferable.getTransferData(paramDataFlavor);
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
/*     */   public synchronized void addFlavorListener(FlavorListener paramFlavorListener) {
/* 254 */     if (paramFlavorListener == null) {
/*     */       return;
/*     */     }
/* 257 */     if (this.flavorListeners == null) {
/* 258 */       this.currentDataFlavors = getAvailableDataFlavorSet();
/* 259 */       this.flavorListeners = new EventListenerAggregate(FlavorListener.class);
/*     */     } 
/* 261 */     this.flavorListeners.add(paramFlavorListener);
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
/*     */   public synchronized void removeFlavorListener(FlavorListener paramFlavorListener) {
/* 282 */     if (paramFlavorListener == null || this.flavorListeners == null) {
/*     */       return;
/*     */     }
/* 285 */     this.flavorListeners.remove(paramFlavorListener);
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
/*     */   public synchronized FlavorListener[] getFlavorListeners() {
/* 301 */     return (this.flavorListeners == null) ? new FlavorListener[0] : (FlavorListener[])this.flavorListeners
/* 302 */       .getListenersCopy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireFlavorsChanged() {
/* 313 */     if (this.flavorListeners == null) {
/*     */       return;
/*     */     }
/* 316 */     Set<DataFlavor> set = this.currentDataFlavors;
/* 317 */     this.currentDataFlavors = getAvailableDataFlavorSet();
/* 318 */     if (set.equals(this.currentDataFlavors)) {
/*     */       return;
/*     */     }
/*     */     
/* 322 */     FlavorListener[] arrayOfFlavorListener = (FlavorListener[])this.flavorListeners.getListenersInternal();
/* 323 */     for (byte b = 0; b < arrayOfFlavorListener.length; b++) {
/* 324 */       final FlavorListener listener = arrayOfFlavorListener[b];
/* 325 */       EventQueue.invokeLater(new Runnable() {
/*     */             public void run() {
/* 327 */               listener.flavorsChanged(new FlavorEvent(Clipboard.this));
/*     */             }
/*     */           });
/*     */     } 
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
/*     */   private Set<DataFlavor> getAvailableDataFlavorSet() {
/* 343 */     HashSet<DataFlavor> hashSet = new HashSet();
/* 344 */     Transferable transferable = getContents(null);
/* 345 */     if (transferable != null) {
/* 346 */       DataFlavor[] arrayOfDataFlavor = transferable.getTransferDataFlavors();
/* 347 */       if (arrayOfDataFlavor != null) {
/* 348 */         hashSet.addAll(Arrays.asList(arrayOfDataFlavor));
/*     */       }
/*     */     } 
/* 351 */     return hashSet;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/datatransfer/Clipboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */