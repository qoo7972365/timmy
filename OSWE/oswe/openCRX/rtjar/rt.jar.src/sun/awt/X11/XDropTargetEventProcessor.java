/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class XDropTargetEventProcessor
/*     */ {
/*  36 */   private static final XDropTargetEventProcessor theInstance = new XDropTargetEventProcessor();
/*     */ 
/*     */   
/*     */   private static boolean active = false;
/*     */   
/*  41 */   private XDropTargetProtocol protocol = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean doProcessEvent(XEvent paramXEvent) {
/*  46 */     if (paramXEvent.get_type() == 17 && this.protocol != null && paramXEvent
/*     */       
/*  48 */       .get_xany().get_window() == this.protocol.getSourceWindow()) {
/*  49 */       this.protocol.cleanup();
/*  50 */       this.protocol = null;
/*  51 */       return false;
/*     */     } 
/*     */     
/*  54 */     if (paramXEvent.get_type() == 28) {
/*  55 */       XPropertyEvent xPropertyEvent = paramXEvent.get_xproperty();
/*  56 */       if (xPropertyEvent.get_atom() == MotifDnDConstants.XA_MOTIF_DRAG_RECEIVER_INFO
/*  57 */         .getAtom())
/*     */       {
/*  59 */         XDropTargetRegistry.getRegistry().updateEmbedderDropSite(xPropertyEvent.get_window());
/*     */       }
/*     */     } 
/*     */     
/*  63 */     if (paramXEvent.get_type() != 33) {
/*  64 */       return false;
/*     */     }
/*     */     
/*  67 */     boolean bool = false;
/*  68 */     XClientMessageEvent xClientMessageEvent = paramXEvent.get_xclient();
/*     */     
/*  70 */     XDropTargetProtocol xDropTargetProtocol = this.protocol;
/*     */     
/*  72 */     if (this.protocol != null) {
/*  73 */       if (this.protocol.getMessageType(xClientMessageEvent) != 0) {
/*     */         
/*  75 */         bool = this.protocol.processClientMessage(xClientMessageEvent);
/*     */       } else {
/*  77 */         this.protocol = null;
/*     */       } 
/*     */     }
/*     */     
/*  81 */     if (this.protocol == null) {
/*     */       
/*  83 */       Iterator<XDropTargetProtocol> iterator = XDragAndDropProtocols.getDropTargetProtocols();
/*     */       
/*  85 */       while (iterator.hasNext()) {
/*     */         
/*  87 */         XDropTargetProtocol xDropTargetProtocol1 = iterator.next();
/*     */         
/*  89 */         if (xDropTargetProtocol1 == xDropTargetProtocol) {
/*     */           continue;
/*     */         }
/*     */         
/*  93 */         if (xDropTargetProtocol1.getMessageType(xClientMessageEvent) == 0) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/*  98 */         this.protocol = xDropTargetProtocol1;
/*  99 */         bool = this.protocol.processClientMessage(xClientMessageEvent);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 104 */     return bool;
/*     */   }
/*     */   
/*     */   static void reset() {
/* 108 */     theInstance.protocol = null;
/*     */   }
/*     */   
/*     */   static void activate() {
/* 112 */     active = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean processEvent(XEvent paramXEvent) {
/* 119 */     return active ? theInstance.doProcessEvent(paramXEvent) : false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XDropTargetEventProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */