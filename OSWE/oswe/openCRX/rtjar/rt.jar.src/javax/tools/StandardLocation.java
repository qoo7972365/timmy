/*     */ package javax.tools;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum StandardLocation
/*     */   implements JavaFileManager.Location
/*     */ {
/*  43 */   CLASS_OUTPUT,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   SOURCE_OUTPUT,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   CLASS_PATH,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   SOURCE_PATH,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   ANNOTATION_PROCESSOR_PATH,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   PLATFORM_CLASS_PATH,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   NATIVE_HEADER_OUTPUT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final ConcurrentMap<String, JavaFileManager.Location> locations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JavaFileManager.Location locationFor(final String name) {
/*  88 */     if (locations.isEmpty())
/*     */     {
/*  90 */       for (StandardLocation standardLocation : values())
/*  91 */         locations.putIfAbsent(standardLocation.getName(), standardLocation); 
/*     */     }
/*  93 */     locations.putIfAbsent(name.toString(), new JavaFileManager.Location() {
/*  94 */           public String getName() { return name; }
/*  95 */           public boolean isOutputLocation() { return name.endsWith("_OUTPUT"); }
/*     */         });
/*  97 */     return locations.get(name);
/*     */   }
/*     */   static {
/* 100 */     locations = new ConcurrentHashMap<>();
/*     */   }
/*     */   public String getName() {
/* 103 */     return name();
/*     */   }
/*     */   public boolean isOutputLocation() {
/* 106 */     switch (this) {
/*     */       case CLASS_OUTPUT:
/*     */       case SOURCE_OUTPUT:
/*     */       case NATIVE_HEADER_OUTPUT:
/* 110 */         return true;
/*     */     } 
/* 112 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/tools/StandardLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */