/*     */ package javax.xml.ws.soap;
/*     */ 
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MTOMFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   public static final String ID = "http://www.w3.org/2004/08/soap/features/http-optimization";
/*     */   protected int threshold;
/*     */   
/*     */   public MTOMFeature() {
/*  81 */     this.enabled = true;
/*  82 */     this.threshold = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MTOMFeature(boolean enabled) {
/*  91 */     this.enabled = enabled;
/*  92 */     this.threshold = 0;
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
/*     */   public MTOMFeature(int threshold) {
/* 106 */     if (threshold < 0)
/* 107 */       throw new WebServiceException("MTOMFeature.threshold must be >= 0, actual value: " + threshold); 
/* 108 */     this.enabled = true;
/* 109 */     this.threshold = threshold;
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
/*     */   public MTOMFeature(boolean enabled, int threshold) {
/* 122 */     if (threshold < 0)
/* 123 */       throw new WebServiceException("MTOMFeature.threshold must be >= 0, actual value: " + threshold); 
/* 124 */     this.enabled = enabled;
/* 125 */     this.threshold = threshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getID() {
/* 132 */     return "http://www.w3.org/2004/08/soap/features/http-optimization";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getThreshold() {
/* 142 */     return this.threshold;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/ws/soap/MTOMFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */