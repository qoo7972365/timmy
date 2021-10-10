/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.ListCellRenderer;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicComboBoxRenderer
/*     */   extends JLabel
/*     */   implements ListCellRenderer, Serializable
/*     */ {
/*  58 */   protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
/*  59 */   private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
/*     */ 
/*     */   
/*     */   public BasicComboBoxRenderer() {
/*  63 */     setOpaque(true);
/*  64 */     setBorder(getNoFocusBorder());
/*     */   }
/*     */   
/*     */   private static Border getNoFocusBorder() {
/*  68 */     if (System.getSecurityManager() != null) {
/*  69 */       return SAFE_NO_FOCUS_BORDER;
/*     */     }
/*  71 */     return noFocusBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize() {
/*     */     Dimension dimension;
/*  78 */     if (getText() == null || getText().equals("")) {
/*  79 */       setText(" ");
/*  80 */       dimension = super.getPreferredSize();
/*  81 */       setText("");
/*     */     } else {
/*     */       
/*  84 */       dimension = super.getPreferredSize();
/*     */     } 
/*     */     
/*  87 */     return dimension;
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
/*     */   public Component getListCellRendererComponent(JList paramJList, Object paramObject, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/* 106 */     if (paramBoolean1) {
/* 107 */       setBackground(paramJList.getSelectionBackground());
/* 108 */       setForeground(paramJList.getSelectionForeground());
/*     */     } else {
/*     */       
/* 111 */       setBackground(paramJList.getBackground());
/* 112 */       setForeground(paramJList.getForeground());
/*     */     } 
/*     */     
/* 115 */     setFont(paramJList.getFont());
/*     */     
/* 117 */     if (paramObject instanceof Icon) {
/* 118 */       setIcon((Icon)paramObject);
/*     */     } else {
/*     */       
/* 121 */       setText((paramObject == null) ? "" : paramObject.toString());
/*     */     } 
/* 123 */     return this;
/*     */   }
/*     */   
/*     */   public static class UIResource extends BasicComboBoxRenderer implements javax.swing.plaf.UIResource {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicComboBoxRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */