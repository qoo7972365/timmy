/*     */ package javax.print.attribute.standard;
/*     */ 
/*     */ import javax.print.attribute.Attribute;
/*     */ import javax.print.attribute.SetOfIntegerSyntax;
/*     */ import javax.print.attribute.SupportedValuesAttribute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JobKOctetsSupported
/*     */   extends SetOfIntegerSyntax
/*     */   implements SupportedValuesAttribute
/*     */ {
/*     */   private static final long serialVersionUID = -2867871140549897443L;
/*     */   
/*     */   public JobKOctetsSupported(int paramInt1, int paramInt2) {
/*  68 */     super(paramInt1, paramInt2);
/*  69 */     if (paramInt1 > paramInt2)
/*  70 */       throw new IllegalArgumentException("Null range specified"); 
/*  71 */     if (paramInt1 < 0) {
/*  72 */       throw new IllegalArgumentException("Job K octets value < 0 specified");
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
/*     */ 
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
/*  97 */     return (super.equals(paramObject) && paramObject instanceof JobKOctetsSupported);
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
/*     */   public final Class<? extends Attribute> getCategory() {
/* 112 */     return (Class)JobKOctetsSupported.class;
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
/*     */   public final String getName() {
/* 125 */     return "job-k-octets-supported";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/standard/JobKOctetsSupported.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */