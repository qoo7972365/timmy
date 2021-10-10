/*     */ package javax.imageio.plugins.bmp;
/*     */ 
/*     */ import com.sun.imageio.plugins.bmp.BMPCompressionTypes;
/*     */ import java.util.Locale;
/*     */ import javax.imageio.ImageWriteParam;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BMPImageWriteParam
/*     */   extends ImageWriteParam
/*     */ {
/*     */   private boolean topDown = false;
/*     */   
/*     */   public BMPImageWriteParam(Locale paramLocale) {
/*  79 */     super(paramLocale);
/*     */ 
/*     */     
/*  82 */     this.compressionTypes = BMPCompressionTypes.getCompressionTypes();
/*     */ 
/*     */     
/*  85 */     this.canWriteCompressed = true;
/*  86 */     this.compressionMode = 3;
/*  87 */     this.compressionType = this.compressionTypes[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BMPImageWriteParam() {
/*  95 */     this((Locale)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTopDown(boolean paramBoolean) {
/* 105 */     this.topDown = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTopDown() {
/* 115 */     return this.topDown;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/plugins/bmp/BMPImageWriteParam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */