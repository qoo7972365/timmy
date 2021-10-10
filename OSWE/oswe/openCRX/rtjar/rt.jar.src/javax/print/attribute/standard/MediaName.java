/*     */ package javax.print.attribute.standard;
/*     */ 
/*     */ import javax.print.attribute.Attribute;
/*     */ import javax.print.attribute.EnumSyntax;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MediaName
/*     */   extends Media
/*     */   implements Attribute
/*     */ {
/*     */   private static final long serialVersionUID = 4653117714524155448L;
/*  53 */   public static final MediaName NA_LETTER_WHITE = new MediaName(0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public static final MediaName NA_LETTER_TRANSPARENT = new MediaName(1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final MediaName ISO_A4_WHITE = new MediaName(2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final MediaName ISO_A4_TRANSPARENT = new MediaName(3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MediaName(int paramInt) {
/*  79 */     super(paramInt);
/*     */   }
/*     */   
/*  82 */   private static final String[] myStringTable = new String[] { "na-letter-white", "na-letter-transparent", "iso-a4-white", "iso-a4-transparent" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   private static final MediaName[] myEnumValueTable = new MediaName[] { NA_LETTER_WHITE, NA_LETTER_TRANSPARENT, ISO_A4_WHITE, ISO_A4_TRANSPARENT };
/*     */ 
/*     */ 
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
/* 102 */     return (String[])myStringTable.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EnumSyntax[] getEnumValueTable() {
/* 110 */     return (EnumSyntax[])myEnumValueTable.clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/standard/MediaName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */