/*     */ package javax.xml.crypto.dsig.spec;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ExcC14NParameterSpec
/*     */   implements C14NMethodParameterSpec
/*     */ {
/*     */   private List<String> preList;
/*     */   public static final String DEFAULT = "#default";
/*     */   
/*     */   public ExcC14NParameterSpec() {
/*  74 */     this.preList = Collections.emptyList();
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
/*     */   public ExcC14NParameterSpec(List<?> paramList) {
/*  91 */     if (paramList == null) {
/*  92 */       throw new NullPointerException("prefixList cannot be null");
/*     */     }
/*  94 */     ArrayList<? extends String> arrayList1 = new ArrayList(paramList); byte b; int i;
/*  95 */     for (b = 0, i = arrayList1.size(); b < i; b++) {
/*  96 */       if (!(arrayList1.get(b) instanceof String)) {
/*  97 */         throw new ClassCastException("not a String");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 102 */     ArrayList<? extends String> arrayList2 = arrayList1;
/*     */     
/* 104 */     this.preList = Collections.unmodifiableList(arrayList2);
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
/*     */   public List getPrefixList() {
/* 119 */     return this.preList;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/crypto/dsig/spec/ExcC14NParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */