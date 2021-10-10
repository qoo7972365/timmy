/*     */ package javax.print.attribute.standard;
/*     */ 
/*     */ import javax.print.attribute.Attribute;
/*     */ import javax.print.attribute.EnumSyntax;
/*     */ import javax.print.attribute.PrintServiceAttribute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PrinterState
/*     */   extends EnumSyntax
/*     */   implements PrintServiceAttribute
/*     */ {
/*     */   private static final long serialVersionUID = -649578618346507718L;
/*  57 */   public static final PrinterState UNKNOWN = new PrinterState(0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public static final PrinterState IDLE = new PrinterState(3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final PrinterState PROCESSING = new PrinterState(4);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public static final PrinterState STOPPED = new PrinterState(5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PrinterState(int paramInt) {
/*  82 */     super(paramInt);
/*     */   }
/*     */   
/*  85 */   private static final String[] myStringTable = new String[] { "unknown", null, null, "idle", "processing", "stopped" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   private static final PrinterState[] myEnumValueTable = new PrinterState[] { UNKNOWN, null, null, IDLE, PROCESSING, STOPPED };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String[] getStringTable() {
/* 107 */     return myStringTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EnumSyntax[] getEnumValueTable() {
/* 114 */     return (EnumSyntax[])myEnumValueTable;
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
/*     */   public final Class<? extends Attribute> getCategory() {
/* 127 */     return (Class)PrinterState.class;
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
/*     */   public final String getName() {
/* 139 */     return "printer-state";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/standard/PrinterState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */