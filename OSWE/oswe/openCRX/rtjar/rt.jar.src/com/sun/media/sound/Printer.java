/*     */ package com.sun.media.sound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Printer
/*     */ {
/*     */   static final boolean err = false;
/*     */   static final boolean debug = false;
/*     */   static final boolean trace = false;
/*     */   static final boolean verbose = false;
/*     */   static final boolean release = false;
/*     */   static final boolean SHOW_THREADID = false;
/*     */   static final boolean SHOW_TIMESTAMP = false;
/*     */   
/*     */   public static void err(String paramString) {}
/*     */   
/*     */   public static void debug(String paramString) {}
/*     */   
/*     */   public static void trace(String paramString) {}
/*     */   
/*     */   public static void verbose(String paramString) {}
/*     */   
/*     */   public static void release(String paramString) {}
/*     */   
/* 107 */   private static long startTime = 0L;
/*     */   
/*     */   public static void println(String paramString) {
/* 110 */     String str = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     System.out.println(str + paramString);
/*     */   }
/*     */   
/*     */   public static void println() {
/* 124 */     System.out.println();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/Printer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */