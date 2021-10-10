/*    */ package sun.awt.datatransfer;
/*    */ 
/*    */ import java.awt.datatransfer.DataFlavor;
/*    */ import java.awt.datatransfer.Transferable;
/*    */ import java.awt.datatransfer.UnsupportedFlavorException;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
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
/*    */ public class TransferableProxy
/*    */   implements Transferable
/*    */ {
/*    */   protected final Transferable transferable;
/*    */   protected final boolean isLocal;
/*    */   
/*    */   public TransferableProxy(Transferable paramTransferable, boolean paramBoolean) {
/* 61 */     this.transferable = paramTransferable;
/* 62 */     this.isLocal = paramBoolean;
/*    */   }
/*    */   public DataFlavor[] getTransferDataFlavors() {
/* 65 */     return this.transferable.getTransferDataFlavors();
/*    */   }
/*    */   public boolean isDataFlavorSupported(DataFlavor paramDataFlavor) {
/* 68 */     return this.transferable.isDataFlavorSupported(paramDataFlavor);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getTransferData(DataFlavor paramDataFlavor) throws UnsupportedFlavorException, IOException {
/* 73 */     Object object = this.transferable.getTransferData(paramDataFlavor);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 78 */     if (object != null && this.isLocal && paramDataFlavor.isFlavorSerializedObjectType()) {
/* 79 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*    */       
/* 81 */       ClassLoaderObjectOutputStream classLoaderObjectOutputStream = new ClassLoaderObjectOutputStream(byteArrayOutputStream);
/*    */       
/* 83 */       classLoaderObjectOutputStream.writeObject(object);
/*    */ 
/*    */       
/* 86 */       ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
/*    */ 
/*    */ 
/*    */       
/*    */       try {
/* 91 */         ClassLoaderObjectInputStream classLoaderObjectInputStream = new ClassLoaderObjectInputStream(byteArrayInputStream, classLoaderObjectOutputStream.getClassLoaderMap());
/* 92 */         object = classLoaderObjectInputStream.readObject();
/* 93 */       } catch (ClassNotFoundException classNotFoundException) {
/* 94 */         throw (IOException)(new IOException()).initCause(classNotFoundException);
/*    */       } 
/*    */     } 
/*    */     
/* 98 */     return object;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/datatransfer/TransferableProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */