/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import javax.swing.JPasswordField;
/*     */ import sun.swing.SwingUtilities2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PasswordView
/*     */   extends FieldView
/*     */ {
/*     */   public PasswordView(Element paramElement) {
/*  49 */     super(paramElement);
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
/*     */   protected int drawUnselectedText(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws BadLocationException {
/*  68 */     Container container = getContainer();
/*  69 */     if (container instanceof JPasswordField) {
/*  70 */       JPasswordField jPasswordField = (JPasswordField)container;
/*  71 */       if (!jPasswordField.echoCharIsSet()) {
/*  72 */         return super.drawUnselectedText(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */       }
/*  74 */       if (jPasswordField.isEnabled()) {
/*  75 */         paramGraphics.setColor(jPasswordField.getForeground());
/*     */       } else {
/*     */         
/*  78 */         paramGraphics.setColor(jPasswordField.getDisabledTextColor());
/*     */       } 
/*  80 */       char c = jPasswordField.getEchoChar();
/*  81 */       int i = paramInt4 - paramInt3;
/*  82 */       for (byte b = 0; b < i; b++) {
/*  83 */         paramInt1 = drawEchoCharacter(paramGraphics, paramInt1, paramInt2, c);
/*     */       }
/*     */     } 
/*  86 */     return paramInt1;
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
/*     */   protected int drawSelectedText(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws BadLocationException {
/* 106 */     paramGraphics.setColor(this.selected);
/* 107 */     Container container = getContainer();
/* 108 */     if (container instanceof JPasswordField) {
/* 109 */       JPasswordField jPasswordField = (JPasswordField)container;
/* 110 */       if (!jPasswordField.echoCharIsSet()) {
/* 111 */         return super.drawSelectedText(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */       }
/* 113 */       char c = jPasswordField.getEchoChar();
/* 114 */       int i = paramInt4 - paramInt3;
/* 115 */       for (byte b = 0; b < i; b++) {
/* 116 */         paramInt1 = drawEchoCharacter(paramGraphics, paramInt1, paramInt2, c);
/*     */       }
/*     */     } 
/* 119 */     return paramInt1;
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
/*     */   protected int drawEchoCharacter(Graphics paramGraphics, int paramInt1, int paramInt2, char paramChar) {
/* 135 */     ONE[0] = paramChar;
/* 136 */     SwingUtilities2.drawChars(Utilities.getJComponent(this), paramGraphics, ONE, 0, 1, paramInt1, paramInt2);
/*     */     
/* 138 */     return paramInt1 + paramGraphics.getFontMetrics().charWidth(paramChar);
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
/*     */   public Shape modelToView(int paramInt, Shape paramShape, Position.Bias paramBias) throws BadLocationException {
/* 153 */     Container container = getContainer();
/* 154 */     if (container instanceof JPasswordField) {
/* 155 */       JPasswordField jPasswordField = (JPasswordField)container;
/* 156 */       if (!jPasswordField.echoCharIsSet()) {
/* 157 */         return super.modelToView(paramInt, paramShape, paramBias);
/*     */       }
/* 159 */       char c = jPasswordField.getEchoChar();
/* 160 */       FontMetrics fontMetrics = jPasswordField.getFontMetrics(jPasswordField.getFont());
/*     */       
/* 162 */       Rectangle rectangle = adjustAllocation(paramShape).getBounds();
/* 163 */       int i = (paramInt - getStartOffset()) * fontMetrics.charWidth(c);
/* 164 */       rectangle.x += i;
/* 165 */       rectangle.width = 1;
/* 166 */       return rectangle;
/*     */     } 
/* 168 */     return null;
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
/* 183 */     paramArrayOfBias[0] = Position.Bias.Forward;
/* 184 */     int i = 0;
/* 185 */     Container container = getContainer();
/* 186 */     if (container instanceof JPasswordField) {
/* 187 */       JPasswordField jPasswordField = (JPasswordField)container;
/* 188 */       if (!jPasswordField.echoCharIsSet()) {
/* 189 */         return super.viewToModel(paramFloat1, paramFloat2, paramShape, paramArrayOfBias);
/*     */       }
/* 191 */       char c = jPasswordField.getEchoChar();
/* 192 */       int j = jPasswordField.getFontMetrics(jPasswordField.getFont()).charWidth(c);
/* 193 */       paramShape = adjustAllocation(paramShape);
/*     */       
/* 195 */       Rectangle rectangle = (paramShape instanceof Rectangle) ? (Rectangle)paramShape : paramShape.getBounds();
/* 196 */       i = (j > 0) ? (((int)paramFloat1 - rectangle.x) / j) : Integer.MAX_VALUE;
/*     */       
/* 198 */       if (i < 0) {
/* 199 */         i = 0;
/*     */       }
/* 201 */       else if (i > getStartOffset() + getDocument().getLength()) {
/* 202 */         i = getDocument().getLength() - getStartOffset();
/*     */       } 
/*     */     } 
/* 205 */     return getStartOffset() + i;
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
/*     */   public float getPreferredSpan(int paramInt) {
/*     */     Container container;
/* 219 */     switch (paramInt) {
/*     */       case 0:
/* 221 */         container = getContainer();
/* 222 */         if (container instanceof JPasswordField) {
/* 223 */           JPasswordField jPasswordField = (JPasswordField)container;
/* 224 */           if (jPasswordField.echoCharIsSet()) {
/* 225 */             char c = jPasswordField.getEchoChar();
/* 226 */             FontMetrics fontMetrics = jPasswordField.getFontMetrics(jPasswordField.getFont());
/* 227 */             Document document = getDocument();
/* 228 */             return (fontMetrics.charWidth(c) * getDocument().getLength());
/*     */           } 
/*     */         }  break;
/*     */     } 
/* 232 */     return super.getPreferredSpan(paramInt);
/*     */   }
/*     */   
/* 235 */   static char[] ONE = new char[1];
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/PasswordView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */