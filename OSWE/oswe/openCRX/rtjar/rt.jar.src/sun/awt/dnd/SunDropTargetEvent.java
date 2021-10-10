/*    */ package sun.awt.dnd;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.event.MouseEvent;
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
/*    */ public class SunDropTargetEvent
/*    */   extends MouseEvent
/*    */ {
/*    */   public static final int MOUSE_DROPPED = 502;
/*    */   private final SunDropTargetContextPeer.EventDispatcher dispatcher;
/*    */   
/*    */   public SunDropTargetEvent(Component paramComponent, int paramInt1, int paramInt2, int paramInt3, SunDropTargetContextPeer.EventDispatcher paramEventDispatcher) {
/* 40 */     super(paramComponent, paramInt1, System.currentTimeMillis(), 0, paramInt2, paramInt3, 0, 0, 0, false, 0);
/*    */     
/* 42 */     this.dispatcher = paramEventDispatcher;
/* 43 */     this.dispatcher.registerEvent(this);
/*    */   }
/*    */   
/*    */   public void dispatch() {
/*    */     try {
/* 48 */       this.dispatcher.dispatchEvent(this);
/*    */     } finally {
/* 50 */       this.dispatcher.unregisterEvent(this);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void consume() {
/* 55 */     boolean bool = isConsumed();
/* 56 */     super.consume();
/* 57 */     if (!bool && isConsumed()) {
/* 58 */       this.dispatcher.unregisterEvent(this);
/*    */     }
/*    */   }
/*    */   
/*    */   public SunDropTargetContextPeer.EventDispatcher getDispatcher() {
/* 63 */     return this.dispatcher;
/*    */   }
/*    */   
/*    */   public String paramString() {
/* 67 */     String str = null;
/*    */     
/* 69 */     switch (this.id) {
/*    */       case 502:
/* 71 */         str = "MOUSE_DROPPED";
/*    */ 
/*    */ 
/*    */         
/* 75 */         return str + ",(" + getX() + "," + getY() + ")";
/*    */     } 
/*    */     return super.paramString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/dnd/SunDropTargetEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */