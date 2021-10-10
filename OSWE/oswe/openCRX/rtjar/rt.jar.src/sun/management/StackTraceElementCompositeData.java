/*     */ package sun.management;
/*     */ 
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import javax.management.openmbean.CompositeDataSupport;
/*     */ import javax.management.openmbean.CompositeType;
/*     */ import javax.management.openmbean.OpenDataException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StackTraceElementCompositeData
/*     */   extends LazyCompositeData
/*     */ {
/*     */   private final StackTraceElement ste;
/*     */   private static final CompositeType stackTraceElementCompositeType;
/*     */   private static final String CLASS_NAME = "className";
/*     */   private static final String METHOD_NAME = "methodName";
/*     */   private static final String FILE_NAME = "fileName";
/*     */   private static final String LINE_NUMBER = "lineNumber";
/*     */   private static final String NATIVE_METHOD = "nativeMethod";
/*     */   
/*     */   private StackTraceElementCompositeData(StackTraceElement paramStackTraceElement) {
/*  42 */     this.ste = paramStackTraceElement;
/*     */   }
/*     */   
/*     */   public StackTraceElement getStackTraceElement() {
/*  46 */     return this.ste;
/*     */   }
/*     */   
/*     */   public static StackTraceElement from(CompositeData paramCompositeData) {
/*  50 */     validateCompositeData(paramCompositeData);
/*     */     
/*  52 */     return new StackTraceElement(getString(paramCompositeData, "className"), 
/*  53 */         getString(paramCompositeData, "methodName"), 
/*  54 */         getString(paramCompositeData, "fileName"), 
/*  55 */         getInt(paramCompositeData, "lineNumber"));
/*     */   }
/*     */   
/*     */   public static CompositeData toCompositeData(StackTraceElement paramStackTraceElement) {
/*  59 */     StackTraceElementCompositeData stackTraceElementCompositeData = new StackTraceElementCompositeData(paramStackTraceElement);
/*  60 */     return stackTraceElementCompositeData.getCompositeData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CompositeData getCompositeData() {
/*  71 */     Object[] arrayOfObject = { this.ste.getClassName(), this.ste.getMethodName(), this.ste.getFileName(), new Integer(this.ste.getLineNumber()), new Boolean(this.ste.isNativeMethod()) };
/*     */     
/*     */     try {
/*  74 */       return new CompositeDataSupport(stackTraceElementCompositeType, stackTraceElementItemNames, arrayOfObject);
/*     */     
/*     */     }
/*  77 */     catch (OpenDataException openDataException) {
/*     */       
/*  79 */       throw new AssertionError(openDataException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  87 */       stackTraceElementCompositeType = (CompositeType)MappedMXBeanType.toOpenType(StackTraceElement.class);
/*  88 */     } catch (OpenDataException openDataException) {
/*     */       
/*  90 */       throw new AssertionError(openDataException);
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
/* 101 */   private static final String[] stackTraceElementItemNames = new String[] { "className", "methodName", "fileName", "lineNumber", "nativeMethod" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -2704607706598396827L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validateCompositeData(CompositeData paramCompositeData) {
/* 114 */     if (paramCompositeData == null) {
/* 115 */       throw new NullPointerException("Null CompositeData");
/*     */     }
/*     */     
/* 118 */     if (!isTypeMatched(stackTraceElementCompositeType, paramCompositeData.getCompositeType()))
/* 119 */       throw new IllegalArgumentException("Unexpected composite type for StackTraceElement"); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/StackTraceElementCompositeData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */