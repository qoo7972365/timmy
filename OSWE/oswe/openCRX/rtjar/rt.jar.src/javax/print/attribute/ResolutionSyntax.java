/*     */ package javax.print.attribute;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ResolutionSyntax
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 2706743076526672017L;
/*     */   private int crossFeedResolution;
/*     */   private int feedResolution;
/*     */   public static final int DPI = 100;
/*     */   public static final int DPCM = 254;
/*     */   
/*     */   public ResolutionSyntax(int paramInt1, int paramInt2, int paramInt3) {
/* 133 */     if (paramInt1 < 1) {
/* 134 */       throw new IllegalArgumentException("crossFeedResolution is < 1");
/*     */     }
/* 136 */     if (paramInt2 < 1) {
/* 137 */       throw new IllegalArgumentException("feedResolution is < 1");
/*     */     }
/* 139 */     if (paramInt3 < 1) {
/* 140 */       throw new IllegalArgumentException("units is < 1");
/*     */     }
/*     */     
/* 143 */     this.crossFeedResolution = paramInt1 * paramInt3;
/* 144 */     this.feedResolution = paramInt2 * paramInt3;
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
/*     */   private static int convertFromDphi(int paramInt1, int paramInt2) {
/* 163 */     if (paramInt2 < 1) {
/* 164 */       throw new IllegalArgumentException(": units is < 1");
/*     */     }
/* 166 */     int i = paramInt2 / 2;
/* 167 */     return (paramInt1 + i) / paramInt2;
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
/*     */   public int[] getResolution(int paramInt) {
/* 185 */     return new int[] { getCrossFeedResolution(paramInt), 
/* 186 */         getFeedResolution(paramInt) };
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
/*     */   public int getCrossFeedResolution(int paramInt) {
/* 204 */     return convertFromDphi(this.crossFeedResolution, paramInt);
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
/*     */   public int getFeedResolution(int paramInt) {
/* 221 */     return convertFromDphi(this.feedResolution, paramInt);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString(int paramInt, String paramString) {
/* 244 */     StringBuffer stringBuffer = new StringBuffer();
/* 245 */     stringBuffer.append(getCrossFeedResolution(paramInt));
/* 246 */     stringBuffer.append('x');
/* 247 */     stringBuffer.append(getFeedResolution(paramInt));
/* 248 */     if (paramString != null) {
/* 249 */       stringBuffer.append(' ');
/* 250 */       stringBuffer.append(paramString);
/*     */     } 
/* 252 */     return stringBuffer.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean lessThanOrEquals(ResolutionSyntax paramResolutionSyntax) {
/* 278 */     return (this.crossFeedResolution <= paramResolutionSyntax.crossFeedResolution && this.feedResolution <= paramResolutionSyntax.feedResolution);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 306 */     return (paramObject != null && paramObject instanceof ResolutionSyntax && this.crossFeedResolution == ((ResolutionSyntax)paramObject).crossFeedResolution && this.feedResolution == ((ResolutionSyntax)paramObject).feedResolution);
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
/*     */   public int hashCode() {
/* 318 */     return this.crossFeedResolution & 0xFFFF | (this.feedResolution & 0xFFFF) << 16;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 329 */     StringBuffer stringBuffer = new StringBuffer();
/* 330 */     stringBuffer.append(this.crossFeedResolution);
/* 331 */     stringBuffer.append('x');
/* 332 */     stringBuffer.append(this.feedResolution);
/* 333 */     stringBuffer.append(" dphi");
/* 334 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getCrossFeedResolutionDphi() {
/* 345 */     return this.crossFeedResolution;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getFeedResolutionDphi() {
/* 355 */     return this.feedResolution;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/ResolutionSyntax.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */