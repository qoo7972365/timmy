/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Window;
/*     */ import sun.awt.ModalExclude;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Popup
/*     */ {
/*     */   private Component component;
/*     */   
/*     */   protected Popup(Component paramComponent1, Component paramComponent2, int paramInt1, int paramInt2) {
/*  84 */     this();
/*  85 */     if (paramComponent2 == null) {
/*  86 */       throw new IllegalArgumentException("Contents must be non-null");
/*     */     }
/*  88 */     reset(paramComponent1, paramComponent2, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Popup() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void show() {
/* 104 */     Component component = getComponent();
/*     */     
/* 106 */     if (component != null) {
/* 107 */       component.show();
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
/*     */   
/*     */   public void hide() {
/* 122 */     Component component = getComponent();
/*     */     
/* 124 */     if (component instanceof JWindow) {
/* 125 */       component.hide();
/* 126 */       ((JWindow)component).getContentPane().removeAll();
/*     */     } 
/* 128 */     dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void dispose() {
/* 135 */     Component component = getComponent();
/* 136 */     Window window = SwingUtilities.getWindowAncestor(component);
/*     */     
/* 138 */     if (component instanceof JWindow) {
/* 139 */       ((Window)component).dispose();
/* 140 */       component = null;
/*     */     } 
/*     */     
/* 143 */     if (window instanceof DefaultFrame) {
/* 144 */       window.dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void reset(Component paramComponent1, Component paramComponent2, int paramInt1, int paramInt2) {
/* 152 */     if (getComponent() == null) {
/* 153 */       this.component = createComponent(paramComponent1);
/*     */     }
/*     */     
/* 156 */     Component component = getComponent();
/*     */     
/* 158 */     if (component instanceof JWindow) {
/* 159 */       JWindow jWindow = (JWindow)getComponent();
/*     */       
/* 161 */       jWindow.setLocation(paramInt1, paramInt2);
/* 162 */       jWindow.getContentPane().add(paramComponent2, "Center");
/* 163 */       jWindow.invalidate();
/* 164 */       jWindow.validate();
/* 165 */       if (jWindow.isVisible())
/*     */       {
/*     */         
/* 168 */         pack();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void pack() {
/* 179 */     Component component = getComponent();
/*     */     
/* 181 */     if (component instanceof Window) {
/* 182 */       ((Window)component).pack();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Window getParentWindow(Component paramComponent) {
/* 192 */     Window window = null;
/*     */     
/* 194 */     if (paramComponent instanceof Window) {
/* 195 */       window = (Window)paramComponent;
/*     */     }
/* 197 */     else if (paramComponent != null) {
/* 198 */       window = SwingUtilities.getWindowAncestor(paramComponent);
/*     */     } 
/* 200 */     if (window == null) {
/* 201 */       window = new DefaultFrame();
/*     */     }
/* 203 */     return window;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Component createComponent(Component paramComponent) {
/* 212 */     if (GraphicsEnvironment.isHeadless())
/*     */     {
/* 214 */       return null;
/*     */     }
/* 216 */     return new HeavyWeightWindow(getParentWindow(paramComponent));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Component getComponent() {
/* 224 */     return this.component;
/*     */   }
/*     */ 
/*     */   
/*     */   static class HeavyWeightWindow
/*     */     extends JWindow
/*     */     implements ModalExclude
/*     */   {
/*     */     HeavyWeightWindow(Window param1Window) {
/* 233 */       super(param1Window);
/* 234 */       setFocusableWindowState(false);
/* 235 */       setType(Window.Type.POPUP);
/*     */ 
/*     */ 
/*     */       
/* 239 */       getRootPane().setUseTrueDoubleBuffering(false);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 244 */         setAlwaysOnTop(true);
/* 245 */       } catch (SecurityException securityException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void update(Graphics param1Graphics) {
/* 252 */       paint(param1Graphics);
/*     */     }
/*     */     
/*     */     public void show() {
/* 256 */       pack();
/* 257 */       if (getWidth() > 0 && getHeight() > 0)
/* 258 */         super.show(); 
/*     */     }
/*     */   }
/*     */   
/*     */   static class DefaultFrame extends Frame {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/Popup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */