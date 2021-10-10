/*     */ package sun.java2d.loops;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import sun.awt.image.BufImgSurfaceData;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.pipe.Region;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GraphicsPrimitive
/*     */ {
/*     */   private String methodSignature;
/*     */   private int uniqueID;
/* 111 */   private static int unusedPrimID = 1; private SurfaceType sourceType; private CompositeType compositeType; private SurfaceType destType; private long pNativePrim; static HashMap traceMap; public static int traceflags;
/*     */   public static String tracefile;
/*     */   public static PrintStream traceout;
/*     */   public static final int TRACELOG = 1;
/*     */   public static final int TRACETIMESTAMP = 2;
/*     */   public static final int TRACECOUNTS = 4;
/*     */   private String cachedname;
/*     */   
/*     */   public static final synchronized int makePrimTypeID() {
/* 120 */     if (unusedPrimID > 255) {
/* 121 */       throw new InternalError("primitive id overflow");
/*     */     }
/* 123 */     return unusedPrimID++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final synchronized int makeUniqueID(int paramInt, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 131 */     return paramInt << 24 | paramSurfaceType2
/* 132 */       .getUniqueID() << 16 | paramCompositeType
/* 133 */       .getUniqueID() << 8 | paramSurfaceType1
/* 134 */       .getUniqueID();
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
/*     */   protected GraphicsPrimitive(String paramString, int paramInt, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 147 */     this.methodSignature = paramString;
/* 148 */     this.sourceType = paramSurfaceType1;
/* 149 */     this.compositeType = paramCompositeType;
/* 150 */     this.destType = paramSurfaceType2;
/*     */     
/* 152 */     if (paramSurfaceType1 == null || paramCompositeType == null || paramSurfaceType2 == null) {
/* 153 */       this.uniqueID = paramInt << 24;
/*     */     } else {
/* 155 */       this.uniqueID = makeUniqueID(paramInt, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GraphicsPrimitive(long paramLong, String paramString, int paramInt, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 173 */     this.pNativePrim = paramLong;
/* 174 */     this.methodSignature = paramString;
/* 175 */     this.sourceType = paramSurfaceType1;
/* 176 */     this.compositeType = paramCompositeType;
/* 177 */     this.destType = paramSurfaceType2;
/*     */     
/* 179 */     if (paramSurfaceType1 == null || paramCompositeType == null || paramSurfaceType2 == null) {
/* 180 */       this.uniqueID = paramInt << 24;
/*     */     } else {
/* 182 */       this.uniqueID = makeUniqueID(paramInt, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getUniqueID() {
/* 209 */     return this.uniqueID;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getSignature() {
/* 215 */     return this.methodSignature;
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
/*     */   public final int getPrimTypeID() {
/* 227 */     return this.uniqueID >>> 24;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getNativePrim() {
/* 233 */     return this.pNativePrim;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final SurfaceType getSourceType() {
/* 239 */     return this.sourceType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final CompositeType getCompositeType() {
/* 245 */     return this.compositeType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final SurfaceType getDestType() {
/* 251 */     return this.destType;
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
/*     */   public final boolean satisfies(String paramString, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 270 */     if (paramString != this.methodSignature) {
/* 271 */       return false;
/*     */     }
/*     */     while (true) {
/* 274 */       if (paramSurfaceType1 == null) {
/* 275 */         return false;
/*     */       }
/* 277 */       if (paramSurfaceType1.equals(this.sourceType)) {
/*     */         break;
/*     */       }
/* 280 */       paramSurfaceType1 = paramSurfaceType1.getSuperType();
/*     */     } 
/*     */     while (true) {
/* 283 */       if (paramCompositeType == null) {
/* 284 */         return false;
/*     */       }
/* 286 */       if (paramCompositeType.equals(this.compositeType)) {
/*     */         break;
/*     */       }
/* 289 */       paramCompositeType = paramCompositeType.getSuperType();
/*     */     } 
/*     */     while (true) {
/* 292 */       if (paramSurfaceType2 == null) {
/* 293 */         return false;
/*     */       }
/* 295 */       if (paramSurfaceType2.equals(this.destType)) {
/*     */         break;
/*     */       }
/* 298 */       paramSurfaceType2 = paramSurfaceType2.getSuperType();
/*     */     } 
/* 300 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean satisfiesSameAs(GraphicsPrimitive paramGraphicsPrimitive) {
/* 307 */     return (this.methodSignature == paramGraphicsPrimitive.methodSignature && this.sourceType
/* 308 */       .equals(paramGraphicsPrimitive.sourceType) && this.compositeType
/* 309 */       .equals(paramGraphicsPrimitive.compositeType) && this.destType
/* 310 */       .equals(paramGraphicsPrimitive.destType));
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
/*     */   
/*     */   static {
/* 330 */     GetPropertyAction getPropertyAction = new GetPropertyAction("sun.java2d.trace");
/* 331 */     String str = AccessController.<String>doPrivileged(getPropertyAction);
/* 332 */     if (str != null) {
/* 333 */       boolean bool = false;
/* 334 */       int i = 0;
/* 335 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 336 */       while (stringTokenizer.hasMoreTokens()) {
/* 337 */         String str1 = stringTokenizer.nextToken();
/* 338 */         if (str1.equalsIgnoreCase("count")) {
/* 339 */           i |= 0x4; continue;
/* 340 */         }  if (str1.equalsIgnoreCase("log")) {
/* 341 */           i |= 0x1; continue;
/* 342 */         }  if (str1.equalsIgnoreCase("timestamp")) {
/* 343 */           i |= 0x2; continue;
/* 344 */         }  if (str1.equalsIgnoreCase("verbose")) {
/* 345 */           bool = true; continue;
/* 346 */         }  if (str1.regionMatches(true, 0, "out:", 0, 4)) {
/* 347 */           tracefile = str1.substring(4); continue;
/*     */         } 
/* 349 */         if (!str1.equalsIgnoreCase("help")) {
/* 350 */           System.err.println("unrecognized token: " + str1);
/*     */         }
/* 352 */         System.err.println("usage: -Dsun.java2d.trace=[log[,timestamp]],[count],[out:<filename>],[help],[verbose]");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 357 */       if (bool) {
/* 358 */         System.err.print("GraphicsPrimitive logging ");
/* 359 */         if ((i & 0x1) != 0) {
/* 360 */           System.err.println("enabled");
/* 361 */           System.err.print("GraphicsPrimitive timetamps ");
/* 362 */           if ((i & 0x2) != 0) {
/* 363 */             System.err.println("enabled");
/*     */           } else {
/* 365 */             System.err.println("disabled");
/*     */           } 
/*     */         } else {
/* 368 */           System.err.println("[and timestamps] disabled");
/*     */         } 
/* 370 */         System.err.print("GraphicsPrimitive invocation counts ");
/* 371 */         if ((i & 0x4) != 0) {
/* 372 */           System.err.println("enabled");
/*     */         } else {
/* 374 */           System.err.println("disabled");
/*     */         } 
/* 376 */         System.err.print("GraphicsPrimitive trace output to ");
/* 377 */         if (tracefile == null) {
/* 378 */           System.err.println("System.err");
/*     */         } else {
/* 380 */           System.err.println("file '" + tracefile + "'");
/*     */         } 
/*     */       } 
/* 383 */       traceflags = i;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean tracingEnabled() {
/* 388 */     return (traceflags != 0);
/*     */   } protected static interface GeneralBinaryOp {
/*     */     void setPrimitives(Blit param1Blit1, Blit param1Blit2, GraphicsPrimitive param1GraphicsPrimitive, Blit param1Blit3); SurfaceType getSourceType(); CompositeType getCompositeType(); SurfaceType getDestType(); String getSignature(); int getPrimTypeID(); }
/*     */   private static PrintStream getTraceOutputFile() {
/* 392 */     if (traceout == null) {
/* 393 */       if (tracefile != null) {
/* 394 */         FileOutputStream fileOutputStream = AccessController.<FileOutputStream>doPrivileged(new PrivilegedAction<FileOutputStream>()
/*     */             {
/*     */               public FileOutputStream run() {
/*     */                 try {
/* 398 */                   return new FileOutputStream(GraphicsPrimitive.tracefile);
/* 399 */                 } catch (FileNotFoundException fileNotFoundException) {
/* 400 */                   return null;
/*     */                 } 
/*     */               }
/*     */             });
/* 404 */         if (fileOutputStream != null) {
/* 405 */           traceout = new PrintStream(fileOutputStream);
/*     */         } else {
/* 407 */           traceout = System.err;
/*     */         } 
/*     */       } else {
/* 410 */         traceout = System.err;
/*     */       } 
/*     */     }
/* 413 */     return traceout;
/*     */   } protected static interface GeneralUnaryOp {
/*     */     void setPrimitives(Blit param1Blit1, GraphicsPrimitive param1GraphicsPrimitive, Blit param1Blit2); CompositeType getCompositeType(); SurfaceType getDestType(); String getSignature();
/*     */     int getPrimTypeID(); }
/*     */   public static class TraceReporter extends Thread { public static void setShutdownHook() {
/* 418 */       AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */             public Void run() {
/* 420 */               GraphicsPrimitive.TraceReporter traceReporter = new GraphicsPrimitive.TraceReporter();
/* 421 */               traceReporter.setContextClassLoader(null);
/* 422 */               Runtime.getRuntime().addShutdownHook(traceReporter);
/* 423 */               return null;
/*     */             }
/*     */           });
/*     */     }
/*     */     
/*     */     public void run() {
/* 429 */       PrintStream printStream = GraphicsPrimitive.getTraceOutputFile();
/* 430 */       Iterator<Map.Entry> iterator = GraphicsPrimitive.traceMap.entrySet().iterator();
/* 431 */       long l = 0L;
/* 432 */       byte b = 0;
/* 433 */       while (iterator.hasNext()) {
/* 434 */         Map.Entry entry = iterator.next();
/* 435 */         Object object = entry.getKey();
/* 436 */         int[] arrayOfInt = (int[])entry.getValue();
/* 437 */         if (arrayOfInt[0] == 1) {
/* 438 */           printStream.print("1 call to ");
/*     */         } else {
/* 440 */           printStream.print(arrayOfInt[0] + " calls to ");
/*     */         } 
/* 442 */         printStream.println(object);
/* 443 */         b++;
/* 444 */         l += arrayOfInt[0];
/*     */       } 
/* 446 */       if (b == 0) {
/* 447 */         printStream.println("No graphics primitives executed");
/* 448 */       } else if (b > 1) {
/* 449 */         printStream.println(l + " total calls to " + b + " different primitives");
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void tracePrimitive(Object paramObject) {
/* 456 */     if ((traceflags & 0x4) != 0) {
/* 457 */       if (traceMap == null) {
/* 458 */         traceMap = new HashMap<>();
/* 459 */         TraceReporter.setShutdownHook();
/*     */       } 
/* 461 */       Object object = traceMap.get(paramObject);
/* 462 */       if (object == null) {
/* 463 */         object = new int[1];
/* 464 */         traceMap.put(paramObject, object);
/*     */       } 
/* 466 */       ((int[])object)[0] = ((int[])object)[0] + 1;
/*     */     } 
/* 468 */     if ((traceflags & 0x1) != 0) {
/* 469 */       PrintStream printStream = getTraceOutputFile();
/* 470 */       if ((traceflags & 0x2) != 0) {
/* 471 */         printStream.print(System.currentTimeMillis() + ": ");
/*     */       }
/* 473 */       printStream.println(paramObject);
/*     */     } 
/*     */   }
/*     */   protected void setupGeneralBinaryOp(GeneralBinaryOp paramGeneralBinaryOp) {
/*     */     Blit blit2, blit3;
/* 478 */     int i = paramGeneralBinaryOp.getPrimTypeID();
/* 479 */     String str = paramGeneralBinaryOp.getSignature();
/* 480 */     SurfaceType surfaceType1 = paramGeneralBinaryOp.getSourceType();
/* 481 */     CompositeType compositeType = paramGeneralBinaryOp.getCompositeType();
/* 482 */     SurfaceType surfaceType2 = paramGeneralBinaryOp.getDestType();
/*     */ 
/*     */ 
/*     */     
/* 486 */     Blit blit1 = createConverter(surfaceType1, SurfaceType.IntArgb);
/* 487 */     GraphicsPrimitive graphicsPrimitive = GraphicsPrimitiveMgr.locatePrim(i, SurfaceType.IntArgb, compositeType, surfaceType2);
/*     */ 
/*     */     
/* 490 */     if (graphicsPrimitive != null) {
/* 491 */       blit2 = null;
/* 492 */       blit3 = null;
/*     */     } else {
/* 494 */       graphicsPrimitive = getGeneralOp(i, compositeType);
/* 495 */       if (graphicsPrimitive == null) {
/* 496 */         throw new InternalError("Cannot construct general op for " + str + " " + compositeType);
/*     */       }
/*     */       
/* 499 */       blit2 = createConverter(surfaceType2, SurfaceType.IntArgb);
/* 500 */       blit3 = createConverter(SurfaceType.IntArgb, surfaceType2);
/*     */     } 
/*     */     
/* 503 */     paramGeneralBinaryOp.setPrimitives(blit1, blit2, graphicsPrimitive, blit3);
/*     */   }
/*     */   
/*     */   protected void setupGeneralUnaryOp(GeneralUnaryOp paramGeneralUnaryOp) {
/* 507 */     int i = paramGeneralUnaryOp.getPrimTypeID();
/* 508 */     String str = paramGeneralUnaryOp.getSignature();
/* 509 */     CompositeType compositeType = paramGeneralUnaryOp.getCompositeType();
/* 510 */     SurfaceType surfaceType = paramGeneralUnaryOp.getDestType();
/*     */     
/* 512 */     Blit blit1 = createConverter(surfaceType, SurfaceType.IntArgb);
/* 513 */     GraphicsPrimitive graphicsPrimitive = getGeneralOp(i, compositeType);
/* 514 */     Blit blit2 = createConverter(SurfaceType.IntArgb, surfaceType);
/* 515 */     if (blit1 == null || graphicsPrimitive == null || blit2 == null) {
/* 516 */       throw new InternalError("Cannot construct binary op for " + compositeType + " " + surfaceType);
/*     */     }
/*     */ 
/*     */     
/* 520 */     paramGeneralUnaryOp.setPrimitives(blit1, graphicsPrimitive, blit2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Blit createConverter(SurfaceType paramSurfaceType1, SurfaceType paramSurfaceType2) {
/* 526 */     if (paramSurfaceType1.equals(paramSurfaceType2)) {
/* 527 */       return null;
/*     */     }
/* 529 */     Blit blit = Blit.getFromCache(paramSurfaceType1, CompositeType.SrcNoEa, paramSurfaceType2);
/* 530 */     if (blit == null) {
/* 531 */       throw new InternalError("Cannot construct converter for " + paramSurfaceType1 + "=>" + paramSurfaceType2);
/*     */     }
/*     */     
/* 534 */     return blit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static SurfaceData convertFrom(Blit paramBlit, SurfaceData paramSurfaceData1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, SurfaceData paramSurfaceData2) {
/* 541 */     return convertFrom(paramBlit, paramSurfaceData1, paramInt1, paramInt2, paramInt3, paramInt4, paramSurfaceData2, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static SurfaceData convertFrom(Blit paramBlit, SurfaceData paramSurfaceData1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, SurfaceData paramSurfaceData2, int paramInt5) {
/* 550 */     if (paramSurfaceData2 != null) {
/* 551 */       Rectangle rectangle = paramSurfaceData2.getBounds();
/* 552 */       if (paramInt3 > rectangle.width || paramInt4 > rectangle.height) {
/* 553 */         paramSurfaceData2 = null;
/*     */       }
/*     */     } 
/* 556 */     if (paramSurfaceData2 == null) {
/* 557 */       BufferedImage bufferedImage = new BufferedImage(paramInt3, paramInt4, paramInt5);
/* 558 */       paramSurfaceData2 = BufImgSurfaceData.createData(bufferedImage);
/*     */     } 
/* 560 */     paramBlit.Blit(paramSurfaceData1, paramSurfaceData2, AlphaComposite.Src, null, paramInt1, paramInt2, 0, 0, paramInt3, paramInt4);
/*     */     
/* 562 */     return paramSurfaceData2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void convertTo(Blit paramBlit, SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Region paramRegion, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 570 */     if (paramBlit != null) {
/* 571 */       paramBlit.Blit(paramSurfaceData1, paramSurfaceData2, AlphaComposite.Src, paramRegion, 0, 0, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static GraphicsPrimitive getGeneralOp(int paramInt, CompositeType paramCompositeType) {
/* 579 */     return GraphicsPrimitiveMgr.locatePrim(paramInt, SurfaceType.IntArgb, paramCompositeType, SurfaceType.IntArgb);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String simplename(Field[] paramArrayOfField, Object paramObject) {
/* 586 */     for (byte b = 0; b < paramArrayOfField.length; b++) {
/* 587 */       Field field = paramArrayOfField[b];
/*     */       try {
/* 589 */         if (paramObject == field.get(null)) {
/* 590 */           return field.getName();
/*     */         }
/* 592 */       } catch (Exception exception) {}
/*     */     } 
/*     */     
/* 595 */     return "\"" + paramObject.toString() + "\"";
/*     */   }
/*     */   
/*     */   public static String simplename(SurfaceType paramSurfaceType) {
/* 599 */     return simplename(SurfaceType.class.getDeclaredFields(), paramSurfaceType);
/*     */   }
/*     */   
/*     */   public static String simplename(CompositeType paramCompositeType) {
/* 603 */     return simplename(CompositeType.class.getDeclaredFields(), paramCompositeType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 609 */     if (this.cachedname == null) {
/* 610 */       String str = this.methodSignature;
/* 611 */       int i = str.indexOf('(');
/* 612 */       if (i >= 0) {
/* 613 */         str = str.substring(0, i);
/*     */       }
/* 615 */       this
/*     */ 
/*     */ 
/*     */         
/* 619 */         .cachedname = getClass().getName() + "::" + str + "(" + simplename(this.sourceType) + ", " + simplename(this.compositeType) + ", " + simplename(this.destType) + ")";
/*     */     } 
/* 621 */     return this.cachedname;
/*     */   }
/*     */   
/*     */   public abstract GraphicsPrimitive makePrimitive(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2);
/*     */   
/*     */   public abstract GraphicsPrimitive traceWrap();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/GraphicsPrimitive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */