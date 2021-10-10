/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.AWTKeyStroke;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.Set;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComponentView
/*     */   extends View
/*     */ {
/*     */   private Component createdC;
/*     */   private Invalidator c;
/*     */   
/*     */   public ComponentView(Element paramElement) {
/*  78 */     super(paramElement);
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
/*     */   protected Component createComponent() {
/*  90 */     AttributeSet attributeSet = getElement().getAttributes();
/*  91 */     return StyleConstants.getComponent(attributeSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Component getComponent() {
/*  99 */     return this.createdC;
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
/*     */   public void paint(Graphics paramGraphics, Shape paramShape) {
/* 114 */     if (this.c != null) {
/*     */       
/* 116 */       Rectangle rectangle = (paramShape instanceof Rectangle) ? (Rectangle)paramShape : paramShape.getBounds();
/* 117 */       this.c.setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
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
/*     */   public float getPreferredSpan(int paramInt) {
/* 135 */     if (paramInt != 0 && paramInt != 1) {
/* 136 */       throw new IllegalArgumentException("Invalid axis: " + paramInt);
/*     */     }
/* 138 */     if (this.c != null) {
/* 139 */       Dimension dimension = this.c.getPreferredSize();
/* 140 */       if (paramInt == 0) {
/* 141 */         return dimension.width;
/*     */       }
/* 143 */       return dimension.height;
/*     */     } 
/*     */     
/* 146 */     return 0.0F;
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
/*     */   public float getMinimumSpan(int paramInt) {
/* 163 */     if (paramInt != 0 && paramInt != 1) {
/* 164 */       throw new IllegalArgumentException("Invalid axis: " + paramInt);
/*     */     }
/* 166 */     if (this.c != null) {
/* 167 */       Dimension dimension = this.c.getMinimumSize();
/* 168 */       if (paramInt == 0) {
/* 169 */         return dimension.width;
/*     */       }
/* 171 */       return dimension.height;
/*     */     } 
/*     */     
/* 174 */     return 0.0F;
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
/*     */   public float getMaximumSpan(int paramInt) {
/* 191 */     if (paramInt != 0 && paramInt != 1) {
/* 192 */       throw new IllegalArgumentException("Invalid axis: " + paramInt);
/*     */     }
/* 194 */     if (this.c != null) {
/* 195 */       Dimension dimension = this.c.getMaximumSize();
/* 196 */       if (paramInt == 0) {
/* 197 */         return dimension.width;
/*     */       }
/* 199 */       return dimension.height;
/*     */     } 
/*     */     
/* 202 */     return 0.0F;
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
/*     */   public float getAlignment(int paramInt) {
/* 218 */     if (this.c != null) {
/* 219 */       switch (paramInt) {
/*     */         case 0:
/* 221 */           return this.c.getAlignmentX();
/*     */         case 1:
/* 223 */           return this.c.getAlignmentY();
/*     */       } 
/*     */     }
/* 226 */     return super.getAlignment(paramInt);
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
/*     */   public void setParent(View paramView) {
/* 252 */     super.setParent(paramView);
/* 253 */     if (SwingUtilities.isEventDispatchThread()) {
/* 254 */       setComponentParent();
/*     */     } else {
/* 256 */       Runnable runnable = new Runnable() {
/*     */           public void run() {
/* 258 */             Document document = ComponentView.this.getDocument();
/*     */             try {
/* 260 */               if (document instanceof AbstractDocument) {
/* 261 */                 ((AbstractDocument)document).readLock();
/*     */               }
/* 263 */               ComponentView.this.setComponentParent();
/* 264 */               Container container = ComponentView.this.getContainer();
/* 265 */               if (container != null) {
/* 266 */                 ComponentView.this.preferenceChanged(null, true, true);
/* 267 */                 container.repaint();
/*     */               } 
/*     */             } finally {
/* 270 */               if (document instanceof AbstractDocument) {
/* 271 */                 ((AbstractDocument)document).readUnlock();
/*     */               }
/*     */             } 
/*     */           }
/*     */         };
/* 276 */       SwingUtilities.invokeLater(runnable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setComponentParent() {
/* 285 */     View view = getParent();
/* 286 */     if (view != null) {
/* 287 */       Container container = getContainer();
/* 288 */       if (container != null) {
/* 289 */         if (this.c == null) {
/*     */           
/* 291 */           Component component = createComponent();
/* 292 */           if (component != null) {
/* 293 */             this.createdC = component;
/* 294 */             this.c = new Invalidator(component);
/*     */           } 
/*     */         } 
/* 297 */         if (this.c != null && 
/* 298 */           this.c.getParent() == null)
/*     */         {
/*     */           
/* 301 */           container.add(this.c, this);
/* 302 */           container.addPropertyChangeListener("enabled", this.c);
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 307 */     } else if (this.c != null) {
/* 308 */       Container container = this.c.getParent();
/* 309 */       if (container != null) {
/*     */         
/* 311 */         container.remove(this.c);
/* 312 */         container.removePropertyChangeListener("enabled", this.c);
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
/*     */   public Shape modelToView(int paramInt, Shape paramShape, Position.Bias paramBias) throws BadLocationException {
/* 330 */     int i = getStartOffset();
/* 331 */     int j = getEndOffset();
/* 332 */     if (paramInt >= i && paramInt <= j) {
/* 333 */       Rectangle rectangle = paramShape.getBounds();
/* 334 */       if (paramInt == j) {
/* 335 */         rectangle.x += rectangle.width;
/*     */       }
/* 337 */       rectangle.width = 0;
/* 338 */       return rectangle;
/*     */     } 
/* 340 */     throw new BadLocationException(paramInt + " not in range " + i + "," + j, paramInt);
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
/*     */   public int viewToModel(float paramFloat1, float paramFloat2, Shape paramShape, Position.Bias[] paramArrayOfBias) {
/* 355 */     Rectangle rectangle = (Rectangle)paramShape;
/* 356 */     if (paramFloat1 < (rectangle.x + rectangle.width / 2)) {
/* 357 */       paramArrayOfBias[0] = Position.Bias.Forward;
/* 358 */       return getStartOffset();
/*     */     } 
/* 360 */     paramArrayOfBias[0] = Position.Bias.Backward;
/* 361 */     return getEndOffset();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class Invalidator
/*     */     extends Container
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     Dimension min;
/*     */ 
/*     */     
/*     */     Dimension pref;
/*     */ 
/*     */     
/*     */     Dimension max;
/*     */ 
/*     */     
/*     */     float yalign;
/*     */ 
/*     */     
/*     */     float xalign;
/*     */ 
/*     */     
/*     */     Invalidator(Component param1Component) {
/* 386 */       setLayout((LayoutManager)null);
/* 387 */       add(param1Component);
/* 388 */       cacheChildSizes();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void invalidate() {
/* 398 */       super.invalidate();
/* 399 */       if (getParent() != null) {
/* 400 */         ComponentView.this.preferenceChanged(null, true, true);
/*     */       }
/*     */     }
/*     */     
/*     */     public void doLayout() {
/* 405 */       cacheChildSizes();
/*     */     }
/*     */     
/*     */     public void setBounds(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 409 */       super.setBounds(param1Int1, param1Int2, param1Int3, param1Int4);
/* 410 */       if (getComponentCount() > 0) {
/* 411 */         getComponent(0).setSize(param1Int3, param1Int4);
/*     */       }
/* 413 */       cacheChildSizes();
/*     */     }
/*     */     
/*     */     public void validateIfNecessary() {
/* 417 */       if (!isValid()) {
/* 418 */         validate();
/*     */       }
/*     */     }
/*     */     
/*     */     private void cacheChildSizes() {
/* 423 */       if (getComponentCount() > 0) {
/* 424 */         Component component = getComponent(0);
/* 425 */         this.min = component.getMinimumSize();
/* 426 */         this.pref = component.getPreferredSize();
/* 427 */         this.max = component.getMaximumSize();
/* 428 */         this.yalign = component.getAlignmentY();
/* 429 */         this.xalign = component.getAlignmentX();
/*     */       } else {
/* 431 */         this.min = this.pref = this.max = new Dimension(0, 0);
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
/*     */     public void setVisible(boolean param1Boolean) {
/* 444 */       super.setVisible(param1Boolean);
/* 445 */       if (getComponentCount() > 0) {
/* 446 */         getComponent(0).setVisible(param1Boolean);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isShowing() {
/* 456 */       return true;
/*     */     }
/*     */     
/*     */     public Dimension getMinimumSize() {
/* 460 */       validateIfNecessary();
/* 461 */       return this.min;
/*     */     }
/*     */     
/*     */     public Dimension getPreferredSize() {
/* 465 */       validateIfNecessary();
/* 466 */       return this.pref;
/*     */     }
/*     */     
/*     */     public Dimension getMaximumSize() {
/* 470 */       validateIfNecessary();
/* 471 */       return this.max;
/*     */     }
/*     */     
/*     */     public float getAlignmentX() {
/* 475 */       validateIfNecessary();
/* 476 */       return this.xalign;
/*     */     }
/*     */     
/*     */     public float getAlignmentY() {
/* 480 */       validateIfNecessary();
/* 481 */       return this.yalign;
/*     */     }
/*     */     
/*     */     public Set<AWTKeyStroke> getFocusTraversalKeys(int param1Int) {
/* 485 */       return KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 486 */         .getDefaultFocusTraversalKeys(param1Int);
/*     */     }
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 490 */       Boolean bool = (Boolean)param1PropertyChangeEvent.getNewValue();
/* 491 */       if (getComponentCount() > 0)
/* 492 */         getComponent(0).setEnabled(bool.booleanValue()); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/ComponentView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */