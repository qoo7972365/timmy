/*     */ package sun.java2d;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HeadlessGraphicsEnvironment
/*     */   extends GraphicsEnvironment
/*     */ {
/*     */   private GraphicsEnvironment ge;
/*     */   
/*     */   public HeadlessGraphicsEnvironment(GraphicsEnvironment paramGraphicsEnvironment) {
/*  67 */     this.ge = paramGraphicsEnvironment;
/*     */   }
/*     */ 
/*     */   
/*     */   public GraphicsDevice[] getScreenDevices() throws HeadlessException {
/*  72 */     throw new HeadlessException();
/*     */   }
/*     */ 
/*     */   
/*     */   public GraphicsDevice getDefaultScreenDevice() throws HeadlessException {
/*  77 */     throw new HeadlessException();
/*     */   }
/*     */   
/*     */   public Point getCenterPoint() throws HeadlessException {
/*  81 */     throw new HeadlessException();
/*     */   }
/*     */   
/*     */   public Rectangle getMaximumWindowBounds() throws HeadlessException {
/*  85 */     throw new HeadlessException();
/*     */   }
/*     */   
/*     */   public Graphics2D createGraphics(BufferedImage paramBufferedImage) {
/*  89 */     return this.ge.createGraphics(paramBufferedImage);
/*     */   } public Font[] getAllFonts() {
/*  91 */     return this.ge.getAllFonts();
/*     */   }
/*     */   public String[] getAvailableFontFamilyNames() {
/*  94 */     return this.ge.getAvailableFontFamilyNames();
/*     */   }
/*     */   public String[] getAvailableFontFamilyNames(Locale paramLocale) {
/*  97 */     return this.ge.getAvailableFontFamilyNames(paramLocale);
/*     */   }
/*     */   
/*     */   public GraphicsEnvironment getSunGraphicsEnvironment() {
/* 101 */     return this.ge;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/HeadlessGraphicsEnvironment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */