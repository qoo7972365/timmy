/*     */ package javax.swing.colorchooser;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DiagramComponent
/*     */   extends JComponent
/*     */   implements MouseListener, MouseMotionListener
/*     */ {
/*     */   private final ColorPanel panel;
/*     */   private final boolean diagram;
/*  42 */   private final Insets insets = new Insets(0, 0, 0, 0);
/*     */   
/*     */   private int width;
/*     */   
/*     */   private int height;
/*     */   private int[] array;
/*     */   private BufferedImage image;
/*     */   
/*     */   DiagramComponent(ColorPanel paramColorPanel, boolean paramBoolean) {
/*  51 */     this.panel = paramColorPanel;
/*  52 */     this.diagram = paramBoolean;
/*  53 */     addMouseListener(this);
/*  54 */     addMouseMotionListener(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintComponent(Graphics paramGraphics) {
/*  59 */     getInsets(this.insets);
/*  60 */     this.width = getWidth() - this.insets.left - this.insets.right;
/*  61 */     this.height = getHeight() - this.insets.top - this.insets.bottom;
/*     */ 
/*     */ 
/*     */     
/*  65 */     boolean bool = (this.image == null || this.width != this.image.getWidth() || this.height != this.image.getHeight()) ? true : false;
/*  66 */     if (bool) {
/*  67 */       int i = this.width * this.height;
/*  68 */       if (this.array == null || this.array.length < i) {
/*  69 */         this.array = new int[i];
/*     */       }
/*  71 */       this.image = new BufferedImage(this.width, this.height, 1);
/*     */     } 
/*     */     
/*  74 */     float f1 = 1.0F / (this.width - 1);
/*  75 */     float f2 = 1.0F / (this.height - 1);
/*     */     
/*  77 */     byte b1 = 0;
/*  78 */     float f3 = 0.0F;
/*  79 */     for (byte b2 = 0; b2 < this.height; b2++, f3 += f2) {
/*  80 */       if (this.diagram) {
/*  81 */         float f = 0.0F;
/*  82 */         for (byte b = 0; b < this.width; b++, f += f1, b1++) {
/*  83 */           this.array[b1] = this.panel.getColor(f, f3);
/*     */         }
/*     */       } else {
/*     */         
/*  87 */         int i = this.panel.getColor(f3);
/*  88 */         for (byte b = 0; b < this.width; b++, b1++) {
/*  89 */           this.array[b1] = i;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     this.image.setRGB(0, 0, this.width, this.height, this.array, 0, this.width);
/*  95 */     paramGraphics.drawImage(this.image, this.insets.left, this.insets.top, this.width, this.height, this);
/*  96 */     if (isEnabled()) {
/*  97 */       this.width--;
/*  98 */       this.height--;
/*  99 */       paramGraphics.setXORMode(Color.WHITE);
/* 100 */       paramGraphics.setColor(Color.BLACK);
/* 101 */       if (this.diagram) {
/* 102 */         int i = getValue(this.panel.getValueX(), this.insets.left, this.width);
/* 103 */         int j = getValue(this.panel.getValueY(), this.insets.top, this.height);
/* 104 */         paramGraphics.drawLine(i - 8, j, i + 8, j);
/* 105 */         paramGraphics.drawLine(i, j - 8, i, j + 8);
/*     */       } else {
/*     */         
/* 108 */         int i = getValue(this.panel.getValueZ(), this.insets.top, this.height);
/* 109 */         paramGraphics.drawLine(this.insets.left, i, this.insets.left + this.width, i);
/*     */       } 
/* 111 */       paramGraphics.setPaintMode();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mousePressed(MouseEvent paramMouseEvent) {
/* 116 */     mouseDragged(paramMouseEvent);
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseReleased(MouseEvent paramMouseEvent) {}
/*     */ 
/*     */   
/*     */   public void mouseClicked(MouseEvent paramMouseEvent) {}
/*     */ 
/*     */   
/*     */   public void mouseEntered(MouseEvent paramMouseEvent) {}
/*     */ 
/*     */   
/*     */   public void mouseExited(MouseEvent paramMouseEvent) {}
/*     */ 
/*     */   
/*     */   public void mouseMoved(MouseEvent paramMouseEvent) {}
/*     */   
/*     */   public void mouseDragged(MouseEvent paramMouseEvent) {
/* 135 */     if (isEnabled()) {
/* 136 */       float f = getValue(paramMouseEvent.getY(), this.insets.top, this.height);
/* 137 */       if (this.diagram) {
/* 138 */         float f1 = getValue(paramMouseEvent.getX(), this.insets.left, this.width);
/* 139 */         this.panel.setValue(f1, f);
/*     */       } else {
/*     */         
/* 142 */         this.panel.setValue(f);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int getValue(float paramFloat, int paramInt1, int paramInt2) {
/* 148 */     return paramInt1 + (int)(paramFloat * paramInt2);
/*     */   }
/*     */   
/*     */   private static float getValue(int paramInt1, int paramInt2, int paramInt3) {
/* 152 */     if (paramInt2 < paramInt1) {
/* 153 */       paramInt1 -= paramInt2;
/* 154 */       return (paramInt1 < paramInt3) ? (paramInt1 / paramInt3) : 1.0F;
/*     */     } 
/*     */ 
/*     */     
/* 158 */     return 0.0F;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/colorchooser/DiagramComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */