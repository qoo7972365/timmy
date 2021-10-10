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
/*     */ 
/*     */ 
/*     */ public class MediaTray
/*     */   extends Media
/*     */   implements Attribute
/*     */ {
/*     */   private static final long serialVersionUID = -982503611095214703L;
/*  55 */   public static final MediaTray TOP = new MediaTray(0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public static final MediaTray MIDDLE = new MediaTray(1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public static final MediaTray BOTTOM = new MediaTray(2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public static final MediaTray ENVELOPE = new MediaTray(3);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public static final MediaTray MANUAL = new MediaTray(4);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static final MediaTray LARGE_CAPACITY = new MediaTray(5);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public static final MediaTray MAIN = new MediaTray(6);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public static final MediaTray SIDE = new MediaTray(7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MediaTray(int paramInt) {
/*  99 */     super(paramInt);
/*     */   }
/*     */   
/* 102 */   private static final String[] myStringTable = new String[] { "top", "middle", "bottom", "envelope", "manual", "large-capacity", "main", "side" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   private static final MediaTray[] myEnumValueTable = new MediaTray[] { TOP, MIDDLE, BOTTOM, ENVELOPE, MANUAL, LARGE_CAPACITY, MAIN, SIDE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 129 */     return (String[])myStringTable.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EnumSyntax[] getEnumValueTable() {
/* 136 */     return (EnumSyntax[])myEnumValueTable.clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/standard/MediaTray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */