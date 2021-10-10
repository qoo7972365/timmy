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
/*     */ public class HashPrintServiceAttributeSet
/*     */   extends HashAttributeSet
/*     */   implements PrintServiceAttributeSet, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6642904616179203070L;
/*     */   
/*     */   public HashPrintServiceAttributeSet() {
/*  48 */     super(PrintServiceAttribute.class);
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
/*     */   public HashPrintServiceAttributeSet(PrintServiceAttribute paramPrintServiceAttribute) {
/*  62 */     super(paramPrintServiceAttribute, PrintServiceAttribute.class);
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
/*     */   public HashPrintServiceAttributeSet(PrintServiceAttribute[] paramArrayOfPrintServiceAttribute) {
/*  81 */     super((Attribute[])paramArrayOfPrintServiceAttribute, PrintServiceAttribute.class);
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
/*     */   public HashPrintServiceAttributeSet(PrintServiceAttributeSet paramPrintServiceAttributeSet) {
/* 100 */     super(paramPrintServiceAttributeSet, PrintServiceAttribute.class);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/HashPrintServiceAttributeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */