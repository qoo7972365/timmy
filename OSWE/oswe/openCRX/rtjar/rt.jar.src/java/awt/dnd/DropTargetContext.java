/*     */ package java.awt.dnd;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ import java.awt.dnd.peer.DropTargetContextPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ public class DropTargetContext
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -634158968993743371L;
/*     */   private DropTarget dropTarget;
/*     */   private transient DropTargetContextPeer dropTargetContextPeer;
/*     */   private transient Transferable transferable;
/*     */   
/*     */   DropTargetContext(DropTarget paramDropTarget) {
/*  71 */     this.dropTarget = paramDropTarget;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DropTarget getDropTarget() {
/*  81 */     return this.dropTarget;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getComponent() {
/*  90 */     return this.dropTarget.getComponent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNotify(DropTargetContextPeer paramDropTargetContextPeer) {
/*  99 */     this.dropTargetContextPeer = paramDropTargetContextPeer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNotify() {
/* 107 */     this.dropTargetContextPeer = null;
/* 108 */     this.transferable = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setTargetActions(int paramInt) {
/* 119 */     DropTargetContextPeer dropTargetContextPeer = getDropTargetContextPeer();
/* 120 */     if (dropTargetContextPeer != null) {
/* 121 */       synchronized (dropTargetContextPeer) {
/* 122 */         dropTargetContextPeer.setTargetActions(paramInt);
/* 123 */         getDropTarget().doSetDefaultActions(paramInt);
/*     */       } 
/*     */     } else {
/* 126 */       getDropTarget().doSetDefaultActions(paramInt);
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
/*     */   protected int getTargetActions() {
/* 138 */     DropTargetContextPeer dropTargetContextPeer = getDropTargetContextPeer();
/* 139 */     return (dropTargetContextPeer != null) ? dropTargetContextPeer
/* 140 */       .getTargetActions() : this.dropTarget
/* 141 */       .getDefaultActions();
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
/*     */   public void dropComplete(boolean paramBoolean) throws InvalidDnDOperationException {
/* 155 */     DropTargetContextPeer dropTargetContextPeer = getDropTargetContextPeer();
/* 156 */     if (dropTargetContextPeer != null) {
/* 157 */       dropTargetContextPeer.dropComplete(paramBoolean);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void acceptDrag(int paramInt) {
/* 168 */     DropTargetContextPeer dropTargetContextPeer = getDropTargetContextPeer();
/* 169 */     if (dropTargetContextPeer != null) {
/* 170 */       dropTargetContextPeer.acceptDrag(paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rejectDrag() {
/* 179 */     DropTargetContextPeer dropTargetContextPeer = getDropTargetContextPeer();
/* 180 */     if (dropTargetContextPeer != null) {
/* 181 */       dropTargetContextPeer.rejectDrag();
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
/*     */   protected void acceptDrop(int paramInt) {
/* 194 */     DropTargetContextPeer dropTargetContextPeer = getDropTargetContextPeer();
/* 195 */     if (dropTargetContextPeer != null) {
/* 196 */       dropTargetContextPeer.acceptDrop(paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rejectDrop() {
/* 206 */     DropTargetContextPeer dropTargetContextPeer = getDropTargetContextPeer();
/* 207 */     if (dropTargetContextPeer != null) {
/* 208 */       dropTargetContextPeer.rejectDrop();
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
/*     */   protected DataFlavor[] getCurrentDataFlavors() {
/* 222 */     DropTargetContextPeer dropTargetContextPeer = getDropTargetContextPeer();
/* 223 */     return (dropTargetContextPeer != null) ? dropTargetContextPeer.getTransferDataFlavors() : new DataFlavor[0];
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
/*     */   protected List<DataFlavor> getCurrentDataFlavorsAsList() {
/* 236 */     return Arrays.asList(getCurrentDataFlavors());
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
/*     */   protected boolean isDataFlavorSupported(DataFlavor paramDataFlavor) {
/* 250 */     return getCurrentDataFlavorsAsList().contains(paramDataFlavor);
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
/*     */   protected Transferable getTransferable() throws InvalidDnDOperationException {
/* 262 */     DropTargetContextPeer dropTargetContextPeer = getDropTargetContextPeer();
/* 263 */     if (dropTargetContextPeer == null) {
/* 264 */       throw new InvalidDnDOperationException();
/*     */     }
/* 266 */     if (this.transferable == null) {
/* 267 */       Transferable transferable = dropTargetContextPeer.getTransferable();
/* 268 */       boolean bool = dropTargetContextPeer.isTransferableJVMLocal();
/* 269 */       synchronized (this) {
/* 270 */         if (this.transferable == null) {
/* 271 */           this.transferable = createTransferableProxy(transferable, bool);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 276 */     return this.transferable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DropTargetContextPeer getDropTargetContextPeer() {
/* 287 */     return this.dropTargetContextPeer;
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
/*     */   protected Transferable createTransferableProxy(Transferable paramTransferable, boolean paramBoolean) {
/* 300 */     return new TransferableProxy(paramTransferable, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class TransferableProxy
/*     */     implements Transferable
/*     */   {
/*     */     protected Transferable transferable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean isLocal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private sun.awt.datatransfer.TransferableProxy proxy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     TransferableProxy(Transferable param1Transferable, boolean param1Boolean) {
/* 331 */       this.proxy = new sun.awt.datatransfer.TransferableProxy(param1Transferable, param1Boolean);
/* 332 */       this.transferable = param1Transferable;
/* 333 */       this.isLocal = param1Boolean;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DataFlavor[] getTransferDataFlavors() {
/* 344 */       return this.proxy.getTransferDataFlavors();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isDataFlavorSupported(DataFlavor param1DataFlavor) {
/* 355 */       return this.proxy.isDataFlavorSupported(param1DataFlavor);
/*     */     }
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
/*     */     public Object getTransferData(DataFlavor param1DataFlavor) throws UnsupportedFlavorException, IOException {
/* 376 */       return this.proxy.getTransferData(param1DataFlavor);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/dnd/DropTargetContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */