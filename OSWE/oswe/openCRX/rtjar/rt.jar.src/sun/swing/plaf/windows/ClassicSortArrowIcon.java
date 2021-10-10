/*    */ package sun.swing.plaf.windows;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import java.io.Serializable;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.UIManager;
/*    */ import javax.swing.plaf.UIResource;
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
/*    */ public class ClassicSortArrowIcon
/*    */   implements Icon, UIResource, Serializable
/*    */ {
/*    */   private static final int X_OFFSET = 9;
/*    */   private boolean ascending;
/*    */   
/*    */   public ClassicSortArrowIcon(boolean paramBoolean) {
/* 44 */     this.ascending = paramBoolean;
/*    */   }
/*    */   
/*    */   public void paintIcon(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2) {
/* 48 */     paramInt1 += 9;
/* 49 */     if (this.ascending) {
/* 50 */       paramGraphics.setColor(UIManager.getColor("Table.sortIconHighlight"));
/* 51 */       drawSide(paramGraphics, paramInt1 + 3, paramInt2, -1);
/*    */       
/* 53 */       paramGraphics.setColor(UIManager.getColor("Table.sortIconLight"));
/* 54 */       drawSide(paramGraphics, paramInt1 + 4, paramInt2, 1);
/*    */       
/* 56 */       paramGraphics.fillRect(paramInt1 + 1, paramInt2 + 6, 6, 1);
/*    */     } else {
/*    */       
/* 59 */       paramGraphics.setColor(UIManager.getColor("Table.sortIconHighlight"));
/* 60 */       drawSide(paramGraphics, paramInt1 + 3, paramInt2 + 6, -1);
/* 61 */       paramGraphics.fillRect(paramInt1 + 1, paramInt2, 6, 1);
/*    */       
/* 63 */       paramGraphics.setColor(UIManager.getColor("Table.sortIconLight"));
/* 64 */       drawSide(paramGraphics, paramInt1 + 4, paramInt2 + 6, 1);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void drawSide(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3) {
/* 69 */     byte b = 2;
/* 70 */     if (this.ascending) {
/* 71 */       paramGraphics.fillRect(paramInt1, paramInt2, 1, 2);
/* 72 */       paramInt2++;
/*    */     } else {
/*    */       
/* 75 */       paramGraphics.fillRect(paramInt1, --paramInt2, 1, 2);
/* 76 */       b = -2;
/* 77 */       paramInt2 -= 2;
/*    */     } 
/* 79 */     paramInt1 += paramInt3;
/* 80 */     for (byte b1 = 0; b1 < 2; b1++) {
/* 81 */       paramGraphics.fillRect(paramInt1, paramInt2, 1, 3);
/* 82 */       paramInt1 += paramInt3;
/* 83 */       paramInt2 += b;
/*    */     } 
/* 85 */     if (!this.ascending) {
/* 86 */       paramInt2++;
/*    */     }
/* 88 */     paramGraphics.fillRect(paramInt1, paramInt2, 1, 2);
/*    */   }
/*    */   
/*    */   public int getIconWidth() {
/* 92 */     return 17;
/*    */   }
/*    */   public int getIconHeight() {
/* 95 */     return 9;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/plaf/windows/ClassicSortArrowIcon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */