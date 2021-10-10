/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.dnd.DragSource;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.TransferHandler;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.dnd.SunDragSourceContextPeer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DragRecognitionSupport
/*     */ {
/*     */   private int motionThreshold;
/*     */   private MouseEvent dndArmedEvent;
/*     */   private JComponent component;
/*     */   
/*     */   private static DragRecognitionSupport getDragRecognitionSupport() {
/*  62 */     DragRecognitionSupport dragRecognitionSupport = (DragRecognitionSupport)AppContext.getAppContext().get(DragRecognitionSupport.class);
/*     */     
/*  64 */     if (dragRecognitionSupport == null) {
/*  65 */       dragRecognitionSupport = new DragRecognitionSupport();
/*  66 */       AppContext.getAppContext().put(DragRecognitionSupport.class, dragRecognitionSupport);
/*     */     } 
/*     */     
/*  69 */     return dragRecognitionSupport;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean mousePressed(MouseEvent paramMouseEvent) {
/*  76 */     return getDragRecognitionSupport().mousePressedImpl(paramMouseEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MouseEvent mouseReleased(MouseEvent paramMouseEvent) {
/*  84 */     return getDragRecognitionSupport().mouseReleasedImpl(paramMouseEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean mouseDragged(MouseEvent paramMouseEvent, BeforeDrag paramBeforeDrag) {
/*  91 */     return getDragRecognitionSupport().mouseDraggedImpl(paramMouseEvent, paramBeforeDrag);
/*     */   }
/*     */   
/*     */   private void clearState() {
/*  95 */     this.dndArmedEvent = null;
/*  96 */     this.component = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int mapDragOperationFromModifiers(MouseEvent paramMouseEvent, TransferHandler paramTransferHandler) {
/* 102 */     if (paramTransferHandler == null || !SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
/* 103 */       return 0;
/*     */     }
/*     */     
/* 106 */     return 
/* 107 */       SunDragSourceContextPeer.convertModifiersToDropAction(paramMouseEvent.getModifiersEx(), paramTransferHandler
/* 108 */         .getSourceActions(this.component));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean mousePressedImpl(MouseEvent paramMouseEvent) {
/* 115 */     this.component = (JComponent)paramMouseEvent.getSource();
/*     */     
/* 117 */     if (mapDragOperationFromModifiers(paramMouseEvent, this.component.getTransferHandler()) != 0) {
/*     */ 
/*     */       
/* 120 */       this.motionThreshold = DragSource.getDragThreshold();
/* 121 */       this.dndArmedEvent = paramMouseEvent;
/* 122 */       return true;
/*     */     } 
/*     */     
/* 125 */     clearState();
/* 126 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MouseEvent mouseReleasedImpl(MouseEvent paramMouseEvent) {
/* 135 */     if (this.dndArmedEvent == null) {
/* 136 */       return null;
/*     */     }
/*     */     
/* 139 */     MouseEvent mouseEvent = null;
/*     */     
/* 141 */     if (paramMouseEvent.getSource() == this.component) {
/* 142 */       mouseEvent = this.dndArmedEvent;
/*     */     }
/*     */     
/* 145 */     clearState();
/* 146 */     return mouseEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean mouseDraggedImpl(MouseEvent paramMouseEvent, BeforeDrag paramBeforeDrag) {
/* 154 */     if (this.dndArmedEvent == null) {
/* 155 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 159 */     if (paramMouseEvent.getSource() != this.component) {
/* 160 */       clearState();
/* 161 */       return false;
/*     */     } 
/*     */     
/* 164 */     int i = Math.abs(paramMouseEvent.getX() - this.dndArmedEvent.getX());
/* 165 */     int j = Math.abs(paramMouseEvent.getY() - this.dndArmedEvent.getY());
/* 166 */     if (i > this.motionThreshold || j > this.motionThreshold) {
/* 167 */       TransferHandler transferHandler = this.component.getTransferHandler();
/* 168 */       int k = mapDragOperationFromModifiers(paramMouseEvent, transferHandler);
/* 169 */       if (k != 0) {
/*     */         
/* 171 */         if (paramBeforeDrag != null) {
/* 172 */           paramBeforeDrag.dragStarting(this.dndArmedEvent);
/*     */         }
/* 174 */         transferHandler.exportAsDrag(this.component, this.dndArmedEvent, k);
/* 175 */         clearState();
/*     */       } 
/*     */     } 
/*     */     
/* 179 */     return true;
/*     */   }
/*     */   
/*     */   public static interface BeforeDrag {
/*     */     void dragStarting(MouseEvent param1MouseEvent);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/DragRecognitionSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */