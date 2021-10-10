/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import sun.awt.AWTAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Autoscroller
/*     */   implements ActionListener
/*     */ {
/*  47 */   private static Autoscroller sharedInstance = new Autoscroller();
/*     */ 
/*     */ 
/*     */   
/*     */   private static MouseEvent event;
/*     */ 
/*     */ 
/*     */   
/*     */   private static Timer timer;
/*     */ 
/*     */   
/*     */   private static JComponent component;
/*     */ 
/*     */ 
/*     */   
/*     */   public static void stop(JComponent paramJComponent) {
/*  63 */     sharedInstance._stop(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRunning(JComponent paramJComponent) {
/*  70 */     return sharedInstance._isRunning(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void processMouseDragged(MouseEvent paramMouseEvent) {
/*  78 */     sharedInstance._processMouseDragged(paramMouseEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void start(JComponent paramJComponent, MouseEvent paramMouseEvent) {
/*  89 */     Point point = paramJComponent.getLocationOnScreen();
/*     */     
/*  91 */     if (component != paramJComponent) {
/*  92 */       _stop(component);
/*     */     }
/*  94 */     component = paramJComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     event = new MouseEvent(component, paramMouseEvent.getID(), paramMouseEvent.getWhen(), paramMouseEvent.getModifiers(), paramMouseEvent.getX() + point.x, paramMouseEvent.getY() + point.y, paramMouseEvent.getXOnScreen(), paramMouseEvent.getYOnScreen(), paramMouseEvent.getClickCount(), paramMouseEvent.isPopupTrigger(), 0);
/*     */     
/* 102 */     AWTAccessor.MouseEventAccessor mouseEventAccessor = AWTAccessor.getMouseEventAccessor();
/* 103 */     mouseEventAccessor.setCausedByTouchEvent(event, mouseEventAccessor
/* 104 */         .isCausedByTouchEvent(paramMouseEvent));
/*     */     
/* 106 */     if (timer == null) {
/* 107 */       timer = new Timer(100, this);
/*     */     }
/*     */     
/* 110 */     if (!timer.isRunning()) {
/* 111 */       timer.start();
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
/*     */   private void _stop(JComponent paramJComponent) {
/* 123 */     if (component == paramJComponent) {
/* 124 */       if (timer != null) {
/* 125 */         timer.stop();
/*     */       }
/* 127 */       timer = null;
/* 128 */       event = null;
/* 129 */       component = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean _isRunning(JComponent paramJComponent) {
/* 138 */     return (paramJComponent == component && timer != null && timer.isRunning());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void _processMouseDragged(MouseEvent paramMouseEvent) {
/* 145 */     JComponent jComponent = (JComponent)paramMouseEvent.getComponent();
/* 146 */     boolean bool = true;
/* 147 */     if (jComponent.isShowing()) {
/* 148 */       Rectangle rectangle = jComponent.getVisibleRect();
/* 149 */       bool = rectangle.contains(paramMouseEvent.getX(), paramMouseEvent.getY());
/*     */     } 
/* 151 */     if (bool) {
/* 152 */       _stop(jComponent);
/*     */     } else {
/* 154 */       start(jComponent, paramMouseEvent);
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
/*     */   public void actionPerformed(ActionEvent paramActionEvent) {
/* 166 */     JComponent jComponent = component;
/*     */     
/* 168 */     if (jComponent == null || !jComponent.isShowing() || event == null) {
/* 169 */       _stop(jComponent);
/*     */       return;
/*     */     } 
/* 172 */     Point point = jComponent.getLocationOnScreen();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     MouseEvent mouseEvent = new MouseEvent(jComponent, event.getID(), event.getWhen(), event.getModifiers(), event.getX() - point.x, event.getY() - point.y, event.getXOnScreen(), event.getYOnScreen(), event.getClickCount(), event.isPopupTrigger(), 0);
/*     */     
/* 182 */     AWTAccessor.MouseEventAccessor mouseEventAccessor = AWTAccessor.getMouseEventAccessor();
/* 183 */     mouseEventAccessor.setCausedByTouchEvent(mouseEvent, mouseEventAccessor
/* 184 */         .isCausedByTouchEvent(event));
/* 185 */     jComponent.superProcessMouseMotionEvent(mouseEvent);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/Autoscroller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */