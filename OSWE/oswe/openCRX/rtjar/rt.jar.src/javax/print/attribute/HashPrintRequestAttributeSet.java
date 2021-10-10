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
/*     */ public class HashPrintRequestAttributeSet
/*     */   extends HashAttributeSet
/*     */   implements PrintRequestAttributeSet, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2364756266107751933L;
/*     */   
/*     */   public HashPrintRequestAttributeSet() {
/*  49 */     super(PrintRequestAttribute.class);
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
/*     */   public HashPrintRequestAttributeSet(PrintRequestAttribute paramPrintRequestAttribute) {
/*  62 */     super(paramPrintRequestAttribute, PrintRequestAttribute.class);
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
/*     */   public HashPrintRequestAttributeSet(PrintRequestAttribute[] paramArrayOfPrintRequestAttribute) {
/*  81 */     super((Attribute[])paramArrayOfPrintRequestAttribute, PrintRequestAttribute.class);
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
/*     */   public HashPrintRequestAttributeSet(PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/* 100 */     super(paramPrintRequestAttributeSet, PrintRequestAttribute.class);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/HashPrintRequestAttributeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */