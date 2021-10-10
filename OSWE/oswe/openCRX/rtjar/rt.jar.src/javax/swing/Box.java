/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.AWTError;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.LayoutManager;
/*     */ import java.beans.ConstructorProperties;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Box
/*     */   extends JComponent
/*     */   implements Accessible
/*     */ {
/*     */   public Box(int paramInt) {
/*  96 */     super.setLayout(new BoxLayout(this, paramInt));
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
/*     */   public static Box createHorizontalBox() {
/* 112 */     return new Box(0);
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
/*     */   public static Box createVerticalBox() {
/* 128 */     return new Box(1);
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
/*     */   public static Component createRigidArea(Dimension paramDimension) {
/* 142 */     return new Filler(paramDimension, paramDimension, paramDimension);
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
/*     */   public static Component createHorizontalStrut(int paramInt) {
/* 165 */     return new Filler(new Dimension(paramInt, 0), new Dimension(paramInt, 0), new Dimension(paramInt, 32767));
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
/*     */   public static Component createVerticalStrut(int paramInt) {
/* 189 */     return new Filler(new Dimension(0, paramInt), new Dimension(0, paramInt), new Dimension(32767, paramInt));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Component createGlue() {
/* 238 */     return new Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 32767));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Component createHorizontalGlue() {
/* 248 */     return new Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Component createVerticalGlue() {
/* 258 */     return new Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(0, 32767));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLayout(LayoutManager paramLayoutManager) {
/* 268 */     throw new AWTError("Illegal request");
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
/*     */   protected void paintComponent(Graphics paramGraphics) {
/* 282 */     if (this.ui != null) {
/*     */       
/* 284 */       super.paintComponent(paramGraphics);
/* 285 */     } else if (isOpaque()) {
/* 286 */       paramGraphics.setColor(getBackground());
/* 287 */       paramGraphics.fillRect(0, 0, getWidth(), getHeight());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Filler
/*     */     extends JComponent
/*     */     implements Accessible
/*     */   {
/*     */     @ConstructorProperties({"minimumSize", "preferredSize", "maximumSize"})
/*     */     public Filler(Dimension param1Dimension1, Dimension param1Dimension2, Dimension param1Dimension3) {
/* 317 */       setMinimumSize(param1Dimension1);
/* 318 */       setPreferredSize(param1Dimension2);
/* 319 */       setMaximumSize(param1Dimension3);
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
/*     */     public void changeShape(Dimension param1Dimension1, Dimension param1Dimension2, Dimension param1Dimension3) {
/* 332 */       setMinimumSize(param1Dimension1);
/* 333 */       setPreferredSize(param1Dimension2);
/* 334 */       setMaximumSize(param1Dimension3);
/* 335 */       revalidate();
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
/*     */     protected void paintComponent(Graphics param1Graphics) {
/* 352 */       if (this.ui != null) {
/*     */         
/* 354 */         super.paintComponent(param1Graphics);
/* 355 */       } else if (isOpaque()) {
/* 356 */         param1Graphics.setColor(getBackground());
/* 357 */         param1Graphics.fillRect(0, 0, getWidth(), getHeight());
/*     */       } 
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
/*     */     public AccessibleContext getAccessibleContext() {
/* 375 */       if (this.accessibleContext == null) {
/* 376 */         this.accessibleContext = new AccessibleBoxFiller();
/*     */       }
/* 378 */       return this.accessibleContext;
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
/*     */     protected class AccessibleBoxFiller
/*     */       extends Component.AccessibleAWTComponent
/*     */     {
/*     */       public AccessibleRole getAccessibleRole() {
/* 397 */         return AccessibleRole.FILLER;
/*     */       }
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
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessibleContext getAccessibleContext() {
/* 416 */     if (this.accessibleContext == null) {
/* 417 */       this.accessibleContext = new AccessibleBox();
/*     */     }
/* 419 */     return this.accessibleContext;
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
/*     */   protected class AccessibleBox
/*     */     extends Container.AccessibleAWTContainer
/*     */   {
/*     */     public AccessibleRole getAccessibleRole() {
/* 438 */       return AccessibleRole.FILLER;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/Box.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */