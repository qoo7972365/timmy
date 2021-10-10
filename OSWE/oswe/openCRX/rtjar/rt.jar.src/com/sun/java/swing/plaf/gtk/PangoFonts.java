/*     */ package com.sun.java.swing.plaf.gtk;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ import sun.font.FontUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PangoFonts
/*     */ {
/*     */   public static final String CHARS_DIGITS = "0123456789";
/*  60 */   private static double fontScale = 1.0D;
/*     */   static {
/*  62 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*     */     
/*  64 */     if (!GraphicsEnvironment.isHeadless()) {
/*     */       
/*  66 */       GraphicsConfiguration graphicsConfiguration = graphicsEnvironment.getDefaultScreenDevice().getDefaultConfiguration();
/*  67 */       AffineTransform affineTransform = graphicsConfiguration.getNormalizingTransform();
/*  68 */       fontScale = affineTransform.getScaleY();
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
/*     */   static Font lookupFont(String paramString) {
/*  83 */     String str1 = "";
/*  84 */     int i = 0;
/*  85 */     int j = 10;
/*     */     
/*  87 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString);
/*     */     
/*  89 */     while (stringTokenizer.hasMoreTokens()) {
/*  90 */       String str = stringTokenizer.nextToken();
/*     */       
/*  92 */       if (str.equalsIgnoreCase("italic")) {
/*  93 */         i |= 0x2; continue;
/*  94 */       }  if (str.equalsIgnoreCase("bold")) {
/*  95 */         i |= 0x1; continue;
/*  96 */       }  if ("0123456789".indexOf(str.charAt(0)) != -1) {
/*     */         try {
/*  98 */           j = Integer.parseInt(str);
/*  99 */         } catch (NumberFormatException numberFormatException) {}
/*     */         continue;
/*     */       } 
/* 102 */       if (str1.length() > 0) {
/* 103 */         str1 = str1 + " ";
/*     */       }
/*     */       
/* 106 */       str1 = str1 + str;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     double d = j;
/* 163 */     int k = 96;
/*     */     
/* 165 */     Object object = Toolkit.getDefaultToolkit().getDesktopProperty("gnome.Xft/DPI");
/* 166 */     if (object instanceof Integer) {
/* 167 */       k = ((Integer)object).intValue() / 1024;
/* 168 */       if (k == -1) {
/* 169 */         k = 96;
/*     */       }
/* 171 */       if (k < 50) {
/* 172 */         k = 50;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 177 */       d = (k * j) / 72.0D;
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 183 */       d = j * fontScale;
/*     */     } 
/*     */ 
/*     */     
/* 187 */     j = (int)(d + 0.5D);
/* 188 */     if (j < 1) {
/* 189 */       j = 1;
/*     */     }
/*     */     
/* 192 */     String str2 = str1.toLowerCase();
/* 193 */     if (FontUtilities.mapFcName(str2) != null) {
/*     */       
/* 195 */       FontUIResource fontUIResource1 = FontUtilities.getFontConfigFUIR(str2, i, j);
/* 196 */       Font font1 = fontUIResource1.deriveFont(i, (float)d);
/* 197 */       return new FontUIResource(font1);
/*     */     } 
/*     */     
/* 200 */     Font font = new Font(str1, i, j);
/*     */     
/* 202 */     font = font.deriveFont(i, (float)d);
/* 203 */     FontUIResource fontUIResource = new FontUIResource(font);
/* 204 */     return FontUtilities.getCompositeFontUIResource(fontUIResource);
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
/*     */   static int getFontSize(String paramString) {
/* 217 */     int i = 10;
/*     */     
/* 219 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString);
/* 220 */     while (stringTokenizer.hasMoreTokens()) {
/* 221 */       String str = stringTokenizer.nextToken();
/*     */       
/* 223 */       if ("0123456789".indexOf(str.charAt(0)) != -1) {
/*     */         try {
/* 225 */           i = Integer.parseInt(str);
/* 226 */         } catch (NumberFormatException numberFormatException) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 231 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/gtk/PangoFonts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */