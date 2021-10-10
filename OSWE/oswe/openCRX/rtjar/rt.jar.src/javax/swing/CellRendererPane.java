/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CellRendererPane
/*     */   extends Container
/*     */   implements Accessible
/*     */ {
/*     */   protected AccessibleContext accessibleContext;
/*     */   
/*     */   public CellRendererPane() {
/* 191 */     this.accessibleContext = null; setLayout((LayoutManager)null); setVisible(false);
/*     */   }
/*     */   public void invalidate() {}
/*     */   public void paint(Graphics paramGraphics) {} public void update(Graphics paramGraphics) {} protected void addImpl(Component paramComponent, Object paramObject, int paramInt) { if (paramComponent.getParent() == this)
/*     */       return;  super.addImpl(paramComponent, paramObject, paramInt); } public void paintComponent(Graphics paramGraphics, Component paramComponent, Container paramContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) { if (paramComponent == null) { if (paramContainer != null) { Color color = paramGraphics.getColor(); paramGraphics.setColor(paramContainer.getBackground()); paramGraphics.fillRect(paramInt1, paramInt2, paramInt3, paramInt4); paramGraphics.setColor(color); }
/*     */        return; }
/*     */      if (paramComponent.getParent() != this)
/*     */       add(paramComponent);  paramComponent.setBounds(paramInt1, paramInt2, paramInt3, paramInt4); if (paramBoolean)
/*     */       paramComponent.validate();  boolean bool = false; if (paramComponent instanceof JComponent && ((JComponent)paramComponent).isDoubleBuffered()) { bool = true; ((JComponent)paramComponent).setDoubleBuffered(false); }
/*     */      Graphics graphics = paramGraphics.create(paramInt1, paramInt2, paramInt3, paramInt4); try { paramComponent.paint(graphics); }
/*     */     finally { graphics.dispose(); }
/*     */      if (bool && paramComponent instanceof JComponent)
/* 203 */       ((JComponent)paramComponent).setDoubleBuffered(true);  paramComponent.setBounds(-paramInt3, -paramInt4, 0, 0); } public void paintComponent(Graphics paramGraphics, Component paramComponent, Container paramContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { paintComponent(paramGraphics, paramComponent, paramContainer, paramInt1, paramInt2, paramInt3, paramInt4, false); } public AccessibleContext getAccessibleContext() { if (this.accessibleContext == null) {
/* 204 */       this.accessibleContext = new AccessibleCellRendererPane();
/*     */     }
/* 206 */     return this.accessibleContext; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintComponent(Graphics paramGraphics, Component paramComponent, Container paramContainer, Rectangle paramRectangle) {
/*     */     paintComponent(paramGraphics, paramComponent, paramContainer, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*     */     removeAll();
/*     */     paramObjectOutputStream.defaultWriteObject();
/*     */   }
/*     */   
/*     */   protected class AccessibleCellRendererPane
/*     */     extends Container.AccessibleAWTContainer
/*     */   {
/*     */     public AccessibleRole getAccessibleRole() {
/* 224 */       return AccessibleRole.PANEL;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/CellRendererPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */