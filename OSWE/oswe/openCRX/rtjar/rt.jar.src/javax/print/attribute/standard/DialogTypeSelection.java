/*     */ package javax.print.attribute.standard;
/*     */ 
/*     */ import javax.print.attribute.EnumSyntax;
/*     */ import javax.print.attribute.PrintRequestAttribute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DialogTypeSelection
/*     */   extends EnumSyntax
/*     */   implements PrintRequestAttribute
/*     */ {
/*     */   private static final long serialVersionUID = 7518682952133256029L;
/*  61 */   public static final DialogTypeSelection NATIVE = new DialogTypeSelection(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public static final DialogTypeSelection COMMON = new DialogTypeSelection(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DialogTypeSelection(int paramInt) {
/*  76 */     super(paramInt);
/*     */   }
/*     */   
/*  79 */   private static final String[] myStringTable = new String[] { "native", "common" };
/*     */ 
/*     */ 
/*     */   
/*  83 */   private static final DialogTypeSelection[] myEnumValueTable = new DialogTypeSelection[] { NATIVE, COMMON };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String[] getStringTable() {
/*  92 */     return myStringTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EnumSyntax[] getEnumValueTable() {
/*  99 */     return (EnumSyntax[])myEnumValueTable;
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
/*     */   public final Class getCategory() {
/* 114 */     return DialogTypeSelection.class;
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
/*     */   public final String getName() {
/* 128 */     return "dialog-type-selection";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/standard/DialogTypeSelection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */