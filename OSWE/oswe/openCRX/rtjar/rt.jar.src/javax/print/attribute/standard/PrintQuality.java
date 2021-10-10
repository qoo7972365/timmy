/*     */ package javax.print.attribute.standard;
/*     */ 
/*     */ import javax.print.attribute.Attribute;
/*     */ import javax.print.attribute.DocAttribute;
/*     */ import javax.print.attribute.EnumSyntax;
/*     */ import javax.print.attribute.PrintJobAttribute;
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
/*     */ public class PrintQuality
/*     */   extends EnumSyntax
/*     */   implements DocAttribute, PrintRequestAttribute, PrintJobAttribute
/*     */ {
/*     */   private static final long serialVersionUID = -3072341285225858365L;
/*  53 */   public static final PrintQuality DRAFT = new PrintQuality(3);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public static final PrintQuality NORMAL = new PrintQuality(4);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final PrintQuality HIGH = new PrintQuality(5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PrintQuality(int paramInt) {
/*  72 */     super(paramInt);
/*     */   }
/*     */   
/*  75 */   private static final String[] myStringTable = new String[] { "draft", "normal", "high" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private static final PrintQuality[] myEnumValueTable = new PrintQuality[] { DRAFT, NORMAL, HIGH };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String[] getStringTable() {
/*  91 */     return (String[])myStringTable.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EnumSyntax[] getEnumValueTable() {
/*  98 */     return (EnumSyntax[])myEnumValueTable.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getOffset() {
/* 105 */     return 3;
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
/*     */   public final Class<? extends Attribute> getCategory() {
/* 119 */     return (Class)PrintQuality.class;
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
/* 132 */     return "print-quality";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/standard/PrintQuality.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */