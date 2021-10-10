/*    */ package javax.print.attribute;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HashPrintJobAttributeSet
/*    */   extends HashAttributeSet
/*    */   implements PrintJobAttributeSet, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4204473656070350348L;
/*    */   
/*    */   public HashPrintJobAttributeSet() {
/* 49 */     super(PrintJobAttribute.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HashPrintJobAttributeSet(PrintJobAttribute paramPrintJobAttribute) {
/* 62 */     super(paramPrintJobAttribute, PrintJobAttribute.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HashPrintJobAttributeSet(PrintJobAttribute[] paramArrayOfPrintJobAttribute) {
/* 81 */     super((Attribute[])paramArrayOfPrintJobAttribute, PrintJobAttribute.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HashPrintJobAttributeSet(PrintJobAttributeSet paramPrintJobAttributeSet) {
/* 98 */     super(paramPrintJobAttributeSet, PrintJobAttribute.class);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/HashPrintJobAttributeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */