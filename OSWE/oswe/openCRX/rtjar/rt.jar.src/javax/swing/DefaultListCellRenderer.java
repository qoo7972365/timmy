/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import sun.swing.DefaultLookup;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultListCellRenderer
/*     */   extends JLabel
/*     */   implements ListCellRenderer<Object>, Serializable
/*     */ {
/*  83 */   private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
/*  84 */   private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
/*  85 */   protected static Border noFocusBorder = DEFAULT_NO_FOCUS_BORDER;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultListCellRenderer() {
/*  93 */     setOpaque(true);
/*  94 */     setBorder(getNoFocusBorder());
/*  95 */     setName("List.cellRenderer");
/*     */   }
/*     */   
/*     */   private Border getNoFocusBorder() {
/*  99 */     Border border = DefaultLookup.getBorder(this, this.ui, "List.cellNoFocusBorder");
/* 100 */     if (System.getSecurityManager() != null) {
/* 101 */       if (border != null) return border; 
/* 102 */       return SAFE_NO_FOCUS_BORDER;
/*     */     } 
/* 104 */     if (border != null && (noFocusBorder == null || noFocusBorder == DEFAULT_NO_FOCUS_BORDER))
/*     */     {
/*     */       
/* 107 */       return border;
/*     */     }
/* 109 */     return noFocusBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getListCellRendererComponent(JList<?> paramJList, Object paramObject, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/* 120 */     setComponentOrientation(paramJList.getComponentOrientation());
/*     */     
/* 122 */     Color color1 = null;
/* 123 */     Color color2 = null;
/*     */     
/* 125 */     JList.DropLocation dropLocation = paramJList.getDropLocation();
/* 126 */     if (dropLocation != null && 
/* 127 */       !dropLocation.isInsert() && dropLocation
/* 128 */       .getIndex() == paramInt) {
/*     */       
/* 130 */       color1 = DefaultLookup.getColor(this, this.ui, "List.dropCellBackground");
/* 131 */       color2 = DefaultLookup.getColor(this, this.ui, "List.dropCellForeground");
/*     */       
/* 133 */       paramBoolean1 = true;
/*     */     } 
/*     */     
/* 136 */     if (paramBoolean1) {
/* 137 */       setBackground((color1 == null) ? paramJList.getSelectionBackground() : color1);
/* 138 */       setForeground((color2 == null) ? paramJList.getSelectionForeground() : color2);
/*     */     } else {
/*     */       
/* 141 */       setBackground(paramJList.getBackground());
/* 142 */       setForeground(paramJList.getForeground());
/*     */     } 
/*     */     
/* 145 */     if (paramObject instanceof Icon) {
/* 146 */       setIcon((Icon)paramObject);
/* 147 */       setText("");
/*     */     } else {
/*     */       
/* 150 */       setIcon((Icon)null);
/* 151 */       setText((paramObject == null) ? "" : paramObject.toString());
/*     */     } 
/*     */     
/* 154 */     setEnabled(paramJList.isEnabled());
/* 155 */     setFont(paramJList.getFont());
/*     */     
/* 157 */     Border border = null;
/* 158 */     if (paramBoolean2) {
/* 159 */       if (paramBoolean1) {
/* 160 */         border = DefaultLookup.getBorder(this, this.ui, "List.focusSelectedCellHighlightBorder");
/*     */       }
/* 162 */       if (border == null) {
/* 163 */         border = DefaultLookup.getBorder(this, this.ui, "List.focusCellHighlightBorder");
/*     */       }
/*     */     } else {
/* 166 */       border = getNoFocusBorder();
/*     */     } 
/* 168 */     setBorder(border);
/*     */     
/* 170 */     return this;
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
/*     */   public boolean isOpaque() {
/* 185 */     Color color = getBackground();
/* 186 */     Container container = getParent();
/* 187 */     if (container != null) {
/* 188 */       container = container.getParent();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 193 */     boolean bool = (color != null && container != null && color.equals(container.getBackground()) && container.isOpaque()) ? true : false;
/* 194 */     return (!bool && super.isOpaque());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void repaint() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void revalidate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void repaint(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void repaint(Rectangle paramRectangle) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void firePropertyChange(String paramString, Object paramObject1, Object paramObject2) {
/* 256 */     if (paramString == "text" || ((paramString == "font" || paramString == "foreground") && paramObject1 != paramObject2 && 
/*     */ 
/*     */       
/* 259 */       getClientProperty("html") != null))
/*     */     {
/* 261 */       super.firePropertyChange(paramString, paramObject1, paramObject2);
/*     */     }
/*     */   }
/*     */   
/*     */   public void firePropertyChange(String paramString, byte paramByte1, byte paramByte2) {}
/*     */   
/*     */   public void firePropertyChange(String paramString, char paramChar1, char paramChar2) {}
/*     */   
/*     */   public void firePropertyChange(String paramString, short paramShort1, short paramShort2) {}
/*     */   
/*     */   public void firePropertyChange(String paramString, int paramInt1, int paramInt2) {}
/*     */   
/*     */   public void firePropertyChange(String paramString, long paramLong1, long paramLong2) {}
/*     */   
/*     */   public void firePropertyChange(String paramString, float paramFloat1, float paramFloat2) {}
/*     */   
/*     */   public void firePropertyChange(String paramString, double paramDouble1, double paramDouble2) {}
/*     */   
/*     */   public void firePropertyChange(String paramString, boolean paramBoolean1, boolean paramBoolean2) {}
/*     */   
/*     */   public static class UIResource extends DefaultListCellRenderer implements UIResource {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/DefaultListCellRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */