/*      */ package com.sun.java.util.jar.pack;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Random;
/*      */ import java.util.zip.Deflater;
/*      */ import java.util.zip.DeflaterOutputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class CodingChooser
/*      */ {
/*      */   int verbose;
/*      */   int effort;
/*      */   boolean optUseHistogram = true;
/*      */   boolean optUsePopulationCoding = true;
/*      */   boolean optUseAdaptiveCoding = true;
/*      */   boolean disablePopCoding;
/*      */   boolean disableRunCoding;
/*      */   boolean topLevel = true;
/*      */   double fuzz;
/*      */   Coding[] allCodingChoices;
/*      */   Choice[] choices;
/*      */   ByteArrayOutputStream context;
/*      */   CodingChooser popHelper;
/*      */   CodingChooser runHelper;
/*      */   Random stress;
/*      */   private int[] values;
/*      */   private int start;
/*      */   private int end;
/*      */   private int[] deltas;
/*      */   private int min;
/*      */   private int max;
/*      */   private Histogram vHist;
/*      */   private Histogram dHist;
/*      */   private int searchOrder;
/*      */   private Choice regularChoice;
/*      */   private Choice bestChoice;
/*      */   private CodingMethod bestMethod;
/*      */   private int bestByteSize;
/*      */   private int bestZipSize;
/*      */   private int targetSize;
/*      */   public static final int MIN_EFFORT = 1;
/*      */   public static final int MID_EFFORT = 5;
/*      */   public static final int MAX_EFFORT = 9;
/*      */   public static final int POP_EFFORT = 4;
/*      */   public static final int RUN_EFFORT = 3;
/*      */   public static final int BYTE_SIZE = 0;
/*      */   public static final int ZIP_SIZE = 1;
/*      */   private Sizer zipSizer;
/*      */   private Deflater zipDef;
/*      */   private DeflaterOutputStream zipOut;
/*      */   private Sizer byteSizer;
/*      */   private Sizer byteOnlySizer;
/*      */   
/*      */   static class Choice
/*      */   {
/*      */     final Coding coding;
/*      */     final int index;
/*      */     final int[] distance;
/*      */     int searchOrder;
/*      */     int minDistance;
/*      */     int zipSize;
/*      */     int byteSize;
/*      */     int histSize;
/*      */     
/*      */     Choice(Coding param1Coding, int param1Int, int[] param1ArrayOfint) {
/*   75 */       this.coding = param1Coding;
/*   76 */       this.index = param1Int;
/*   77 */       this.distance = param1ArrayOfint;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void reset() {
/*   87 */       this.searchOrder = Integer.MAX_VALUE;
/*   88 */       this.minDistance = Integer.MAX_VALUE;
/*   89 */       this.zipSize = this.byteSize = this.histSize = -1;
/*      */     }
/*      */     
/*      */     boolean isExtra() {
/*   93 */       return (this.index < 0);
/*      */     }
/*      */     
/*      */     public String toString() {
/*   97 */       return stringForDebug();
/*      */     }
/*      */     
/*      */     private String stringForDebug() {
/*  101 */       String str = "";
/*  102 */       if (this.searchOrder < Integer.MAX_VALUE)
/*  103 */         str = str + " so: " + this.searchOrder; 
/*  104 */       if (this.minDistance < Integer.MAX_VALUE)
/*  105 */         str = str + " md: " + this.minDistance; 
/*  106 */       if (this.zipSize > 0)
/*  107 */         str = str + " zs: " + this.zipSize; 
/*  108 */       if (this.byteSize > 0)
/*  109 */         str = str + " bs: " + this.byteSize; 
/*  110 */       if (this.histSize > 0)
/*  111 */         str = str + " hs: " + this.histSize; 
/*  112 */       return "Choice[" + this.index + "] " + str + " " + this.coding;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Choice makeExtraChoice(Coding paramCoding) {
/*  174 */     int[] arrayOfInt = new int[this.choices.length];
/*  175 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/*  176 */       Coding coding = (this.choices[b]).coding;
/*  177 */       int i = paramCoding.distanceFrom(coding);
/*  178 */       assert i > 0;
/*  179 */       assert i == coding.distanceFrom(paramCoding);
/*  180 */       arrayOfInt[b] = i;
/*      */     } 
/*  182 */     Choice choice = new Choice(paramCoding, -1, arrayOfInt);
/*  183 */     choice.reset();
/*  184 */     return choice;
/*      */   }
/*      */   
/*      */   ByteArrayOutputStream getContext() {
/*  188 */     if (this.context == null)
/*  189 */       this.context = new ByteArrayOutputStream(65536); 
/*  190 */     return this.context;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void reset(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/*  209 */     this.values = paramArrayOfint;
/*  210 */     this.start = paramInt1;
/*  211 */     this.end = paramInt2;
/*  212 */     this.deltas = null;
/*  213 */     this.min = Integer.MAX_VALUE;
/*  214 */     this.max = Integer.MIN_VALUE;
/*  215 */     this.vHist = null;
/*  216 */     this.dHist = null;
/*  217 */     this.searchOrder = 0;
/*  218 */     this.regularChoice = null;
/*  219 */     this.bestChoice = null;
/*  220 */     this.bestMethod = null;
/*  221 */     this.bestZipSize = Integer.MAX_VALUE;
/*  222 */     this.bestByteSize = Integer.MAX_VALUE;
/*  223 */     this.targetSize = Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   CodingMethod choose(int[] paramArrayOfint1, int paramInt1, int paramInt2, Coding paramCoding, int[] paramArrayOfint2) {
/*  238 */     reset(paramArrayOfint1, paramInt1, paramInt2);
/*      */     
/*  240 */     if (this.effort <= 1 || paramInt1 >= paramInt2) {
/*  241 */       if (paramArrayOfint2 != null) {
/*  242 */         int[] arrayOfInt = computeSizePrivate(paramCoding);
/*  243 */         paramArrayOfint2[0] = arrayOfInt[0];
/*  244 */         paramArrayOfint2[1] = arrayOfInt[1];
/*      */       } 
/*  246 */       return paramCoding;
/*      */     } 
/*      */     
/*  249 */     if (this.optUseHistogram) {
/*  250 */       getValueHistogram();
/*  251 */       getDeltaHistogram();
/*      */     } 
/*      */     int i;
/*  254 */     for (i = paramInt1; i < paramInt2; i++) {
/*  255 */       int i2 = paramArrayOfint1[i];
/*  256 */       if (this.min > i2) this.min = i2; 
/*  257 */       if (this.max < i2) this.max = i2;
/*      */     
/*      */     } 
/*      */     
/*  261 */     i = markUsableChoices(paramCoding);
/*      */     
/*  263 */     if (this.stress != null) {
/*      */       
/*  265 */       int i2 = this.stress.nextInt(i * 2 + 4);
/*  266 */       CodingMethod codingMethod1 = null;
/*  267 */       for (byte b = 0; b < this.choices.length; b++) {
/*  268 */         Choice choice = this.choices[b];
/*  269 */         if (choice.searchOrder >= 0 && i2-- == 0) {
/*  270 */           codingMethod1 = choice.coding;
/*      */           break;
/*      */         } 
/*      */       } 
/*  274 */       if (codingMethod1 == null) {
/*  275 */         if ((i2 & 0x7) != 0) {
/*  276 */           codingMethod1 = paramCoding;
/*      */         } else {
/*      */           
/*  279 */           codingMethod1 = stressCoding(this.min, this.max);
/*      */         } 
/*      */       }
/*  282 */       if (!this.disablePopCoding && this.optUsePopulationCoding && this.effort >= 4)
/*      */       {
/*      */         
/*  285 */         codingMethod1 = stressPopCoding(codingMethod1);
/*      */       }
/*  287 */       if (!this.disableRunCoding && this.optUseAdaptiveCoding && this.effort >= 3)
/*      */       {
/*      */         
/*  290 */         codingMethod1 = stressAdaptiveCoding(codingMethod1);
/*      */       }
/*  292 */       return codingMethod1;
/*      */     } 
/*      */     
/*  295 */     double d = 1.0D; int j;
/*  296 */     for (j = this.effort; j < 9; j++) {
/*  297 */       d /= 1.414D;
/*      */     }
/*  299 */     j = (int)Math.ceil(i * d);
/*      */ 
/*      */     
/*  302 */     this.bestChoice = this.regularChoice;
/*  303 */     evaluate(this.regularChoice);
/*  304 */     int k = updateDistances(this.regularChoice);
/*      */ 
/*      */     
/*  307 */     int m = this.bestZipSize;
/*  308 */     int n = this.bestByteSize;
/*      */     
/*  310 */     if (this.regularChoice.coding == paramCoding && this.topLevel) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  317 */       int i2 = BandStructure.encodeEscapeValue(115, paramCoding);
/*  318 */       if (paramCoding.canRepresentSigned(i2)) {
/*  319 */         int i3 = paramCoding.getLength(i2);
/*      */ 
/*      */         
/*  322 */         this.regularChoice.zipSize -= i3;
/*  323 */         this.bestByteSize = this.regularChoice.byteSize;
/*  324 */         this.bestZipSize = this.regularChoice.zipSize;
/*      */       } 
/*      */     } 
/*      */     
/*  328 */     int i1 = 1;
/*      */     
/*  330 */     while (this.searchOrder < j) {
/*      */       
/*  332 */       if (i1 > k) i1 = 1; 
/*  333 */       int i2 = k / i1;
/*  334 */       int i3 = k / (i1 *= 2) + 1;
/*  335 */       Choice choice = findChoiceNear(this.bestChoice, i2, i3);
/*  336 */       if (choice == null)
/*  337 */         continue;  assert choice.coding.canRepresent(this.min, this.max);
/*  338 */       evaluate(choice);
/*  339 */       int i4 = updateDistances(choice);
/*  340 */       if (choice == this.bestChoice) {
/*  341 */         k = i4;
/*  342 */         if (this.verbose > 5) Utils.log.info("maxd = " + k);
/*      */       
/*      */       } 
/*      */     } 
/*      */     
/*  347 */     Coding coding = this.bestChoice.coding;
/*  348 */     assert coding == this.bestMethod;
/*      */     
/*  350 */     if (this.verbose > 2) {
/*  351 */       Utils.log.info("chooser: plain result=" + this.bestChoice + " after " + this.bestChoice.searchOrder + " rounds, " + (this.regularChoice.zipSize - this.bestZipSize) + " fewer bytes than regular " + paramCoding);
/*      */     }
/*  353 */     this.bestChoice = null;
/*      */     
/*  355 */     if (!this.disablePopCoding && this.optUsePopulationCoding && this.effort >= 4 && this.bestMethod instanceof Coding)
/*      */     {
/*      */ 
/*      */       
/*  359 */       tryPopulationCoding(coding);
/*      */     }
/*      */     
/*  362 */     if (!this.disableRunCoding && this.optUseAdaptiveCoding && this.effort >= 3 && this.bestMethod instanceof Coding)
/*      */     {
/*      */ 
/*      */       
/*  366 */       tryAdaptiveCoding(coding);
/*      */     }
/*      */ 
/*      */     
/*  370 */     if (paramArrayOfint2 != null) {
/*  371 */       paramArrayOfint2[0] = this.bestByteSize;
/*  372 */       paramArrayOfint2[1] = this.bestZipSize;
/*      */     } 
/*  374 */     if (this.verbose > 1) {
/*  375 */       Utils.log.info("chooser: result=" + this.bestMethod + " " + (m - this.bestZipSize) + " fewer bytes than regular " + paramCoding + "; win=" + 
/*      */ 
/*      */           
/*  378 */           pct((m - this.bestZipSize), m));
/*      */     }
/*  380 */     CodingMethod codingMethod = this.bestMethod;
/*  381 */     reset(null, 0, 0);
/*  382 */     return codingMethod;
/*      */   }
/*      */   CodingMethod choose(int[] paramArrayOfint, int paramInt1, int paramInt2, Coding paramCoding) {
/*  385 */     return choose(paramArrayOfint, paramInt1, paramInt2, paramCoding, null);
/*      */   }
/*      */   CodingMethod choose(int[] paramArrayOfint1, Coding paramCoding, int[] paramArrayOfint2) {
/*  388 */     return choose(paramArrayOfint1, 0, paramArrayOfint1.length, paramCoding, paramArrayOfint2);
/*      */   }
/*      */   CodingMethod choose(int[] paramArrayOfint, Coding paramCoding) {
/*  391 */     return choose(paramArrayOfint, 0, paramArrayOfint.length, paramCoding, null);
/*      */   }
/*      */   
/*      */   private int markUsableChoices(Coding paramCoding) {
/*  395 */     byte b1 = 0; byte b2;
/*  396 */     for (b2 = 0; b2 < this.choices.length; b2++) {
/*  397 */       Choice choice = this.choices[b2];
/*  398 */       choice.reset();
/*  399 */       if (!choice.coding.canRepresent(this.min, this.max)) {
/*      */         
/*  401 */         choice.searchOrder = -1;
/*  402 */         if (this.verbose > 1 && choice.coding == paramCoding) {
/*  403 */           Utils.log.info("regular coding cannot represent [" + this.min + ".." + this.max + "]: " + paramCoding);
/*      */         }
/*      */       } else {
/*      */         
/*  407 */         if (choice.coding == paramCoding)
/*  408 */           this.regularChoice = choice; 
/*  409 */         b1++;
/*      */       } 
/*  411 */     }  if (this.regularChoice == null && paramCoding.canRepresent(this.min, this.max)) {
/*  412 */       this.regularChoice = makeExtraChoice(paramCoding);
/*  413 */       if (this.verbose > 1) {
/*  414 */         Utils.log.info("*** regular choice is extra: " + this.regularChoice.coding);
/*      */       }
/*      */     } 
/*  417 */     if (this.regularChoice == null) {
/*  418 */       for (b2 = 0; b2 < this.choices.length; b2++) {
/*  419 */         Choice choice = this.choices[b2];
/*  420 */         if (choice.searchOrder != -1) {
/*  421 */           this.regularChoice = choice;
/*      */           break;
/*      */         } 
/*      */       } 
/*  425 */       if (this.verbose > 1) {
/*  426 */         Utils.log.info("*** regular choice does not apply " + paramCoding);
/*  427 */         Utils.log.info("    using instead " + this.regularChoice.coding);
/*      */       } 
/*      */     } 
/*  430 */     if (this.verbose > 2) {
/*  431 */       Utils.log.info("chooser: #choices=" + b1 + " [" + this.min + ".." + this.max + "]");
/*  432 */       if (this.verbose > 4)
/*  433 */         for (b2 = 0; b2 < this.choices.length; b2++) {
/*  434 */           Choice choice = this.choices[b2];
/*  435 */           if (choice.searchOrder >= 0) {
/*  436 */             Utils.log.info("  " + choice);
/*      */           }
/*      */         }  
/*      */     } 
/*  440 */     return b1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Choice findChoiceNear(Choice paramChoice, int paramInt1, int paramInt2) {
/*  447 */     if (this.verbose > 5)
/*  448 */       Utils.log.info("findChoice " + paramInt1 + ".." + paramInt2 + " near: " + paramChoice); 
/*  449 */     int[] arrayOfInt = paramChoice.distance;
/*  450 */     Choice choice = null;
/*  451 */     for (byte b = 0; b < this.choices.length; b++) {
/*  452 */       Choice choice1 = this.choices[b];
/*  453 */       if (choice1.searchOrder >= this.searchOrder)
/*      */       {
/*      */         
/*  456 */         if (arrayOfInt[b] >= paramInt2 && arrayOfInt[b] <= paramInt1) {
/*      */           
/*  458 */           if (choice1.minDistance >= paramInt2 && choice1.minDistance <= paramInt1) {
/*  459 */             if (this.verbose > 5)
/*  460 */               Utils.log.info("findChoice => good " + choice1); 
/*  461 */             return choice1;
/*      */           } 
/*  463 */           choice = choice1;
/*      */         }  } 
/*      */     } 
/*  466 */     if (this.verbose > 5)
/*  467 */       Utils.log.info("findChoice => found " + choice); 
/*  468 */     return choice;
/*      */   }
/*      */   private void evaluate(Choice paramChoice) {
/*      */     boolean bool;
/*  472 */     assert paramChoice.searchOrder == Integer.MAX_VALUE;
/*  473 */     paramChoice.searchOrder = this.searchOrder++;
/*      */     
/*  475 */     if (paramChoice == this.bestChoice || paramChoice.isExtra()) {
/*  476 */       bool = true;
/*  477 */     } else if (this.optUseHistogram) {
/*  478 */       Histogram histogram = getHistogram(paramChoice.coding.isDelta());
/*  479 */       paramChoice.histSize = (int)Math.ceil(histogram.getBitLength(paramChoice.coding) / 8.0D);
/*  480 */       paramChoice.byteSize = paramChoice.histSize;
/*  481 */       bool = (paramChoice.byteSize <= this.targetSize) ? true : false;
/*      */     } else {
/*  483 */       bool = true;
/*      */     } 
/*  485 */     if (bool) {
/*  486 */       int[] arrayOfInt = computeSizePrivate(paramChoice.coding);
/*  487 */       paramChoice.byteSize = arrayOfInt[0];
/*  488 */       paramChoice.zipSize = arrayOfInt[1];
/*  489 */       if (noteSizes(paramChoice.coding, paramChoice.byteSize, paramChoice.zipSize))
/*  490 */         this.bestChoice = paramChoice; 
/*      */     } 
/*  492 */     if (paramChoice.histSize >= 0 && 
/*  493 */       !$assertionsDisabled && paramChoice.byteSize != paramChoice.histSize) throw new AssertionError();
/*      */     
/*  495 */     if (this.verbose > 4) {
/*  496 */       Utils.log.info("evaluated " + paramChoice);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean noteSizes(CodingMethod paramCodingMethod, int paramInt1, int paramInt2) {
/*  501 */     assert paramInt2 > 0 && paramInt1 > 0;
/*  502 */     boolean bool = (paramInt2 < this.bestZipSize) ? true : false;
/*  503 */     if (this.verbose > 3)
/*  504 */       Utils.log.info("computed size " + paramCodingMethod + " " + paramInt1 + "/zs=" + paramInt2 + ((bool && this.bestMethod != null) ? (" better by " + 
/*      */ 
/*      */           
/*  507 */           pct((this.bestZipSize - paramInt2), paramInt2)) : "")); 
/*  508 */     if (bool) {
/*  509 */       this.bestMethod = paramCodingMethod;
/*  510 */       this.bestZipSize = paramInt2;
/*  511 */       this.bestByteSize = paramInt1;
/*  512 */       this.targetSize = (int)(paramInt1 * this.fuzz);
/*  513 */       return true;
/*      */     } 
/*  515 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int updateDistances(Choice paramChoice) {
/*  522 */     int[] arrayOfInt = paramChoice.distance;
/*  523 */     int i = 0;
/*  524 */     for (byte b = 0; b < this.choices.length; b++) {
/*  525 */       Choice choice = this.choices[b];
/*  526 */       if (choice.searchOrder >= this.searchOrder) {
/*      */         
/*  528 */         int j = arrayOfInt[b];
/*  529 */         if (this.verbose > 5)
/*  530 */           Utils.log.info("evaluate dist " + j + " to " + choice); 
/*  531 */         int k = choice.minDistance;
/*  532 */         if (k > j)
/*  533 */           choice.minDistance = k = j; 
/*  534 */         if (i < j) {
/*  535 */           i = j;
/*      */         }
/*      */       } 
/*      */     } 
/*  539 */     if (this.verbose > 5)
/*  540 */       Utils.log.info("evaluate maxd => " + i); 
/*  541 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void computeSize(CodingMethod paramCodingMethod, int[] paramArrayOfint1, int paramInt1, int paramInt2, int[] paramArrayOfint2) {
/*  548 */     if (paramInt2 <= paramInt1) {
/*  549 */       paramArrayOfint2[1] = 0; paramArrayOfint2[0] = 0;
/*      */       return;
/*      */     } 
/*      */     try {
/*  553 */       resetData();
/*  554 */       paramCodingMethod.writeArrayTo(this.byteSizer, paramArrayOfint1, paramInt1, paramInt2);
/*  555 */       paramArrayOfint2[0] = getByteSize();
/*  556 */       paramArrayOfint2[1] = getZipSize();
/*  557 */     } catch (IOException iOException) {
/*  558 */       throw new RuntimeException(iOException);
/*      */     } 
/*      */   }
/*      */   public void computeSize(CodingMethod paramCodingMethod, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/*  562 */     computeSize(paramCodingMethod, paramArrayOfint1, 0, paramArrayOfint1.length, paramArrayOfint2);
/*      */   }
/*      */   public int[] computeSize(CodingMethod paramCodingMethod, int[] paramArrayOfint, int paramInt1, int paramInt2) {
/*  565 */     int[] arrayOfInt = { 0, 0 };
/*  566 */     computeSize(paramCodingMethod, paramArrayOfint, paramInt1, paramInt2, arrayOfInt);
/*  567 */     return arrayOfInt;
/*      */   }
/*      */   public int[] computeSize(CodingMethod paramCodingMethod, int[] paramArrayOfint) {
/*  570 */     return computeSize(paramCodingMethod, paramArrayOfint, 0, paramArrayOfint.length);
/*      */   }
/*      */   
/*      */   private int[] computeSizePrivate(CodingMethod paramCodingMethod) {
/*  574 */     int[] arrayOfInt = { 0, 0 };
/*  575 */     computeSize(paramCodingMethod, this.values, this.start, this.end, arrayOfInt);
/*  576 */     return arrayOfInt;
/*      */   }
/*      */   public int computeByteSize(CodingMethod paramCodingMethod, int[] paramArrayOfint, int paramInt1, int paramInt2) {
/*  579 */     int i = paramInt2 - paramInt1;
/*  580 */     if (i < 0) {
/*  581 */       return 0;
/*      */     }
/*  583 */     if (paramCodingMethod instanceof Coding) {
/*  584 */       Coding coding = (Coding)paramCodingMethod;
/*  585 */       int j = coding.getLength(paramArrayOfint, paramInt1, paramInt2);
/*      */       int k;
/*  587 */       assert j == (k = countBytesToSizer(paramCodingMethod, paramArrayOfint, paramInt1, paramInt2)) : paramCodingMethod + " : " + j + " != " + k;
/*      */       
/*  589 */       return j;
/*      */     } 
/*  591 */     return countBytesToSizer(paramCodingMethod, paramArrayOfint, paramInt1, paramInt2);
/*      */   }
/*      */   private int countBytesToSizer(CodingMethod paramCodingMethod, int[] paramArrayOfint, int paramInt1, int paramInt2) {
/*      */     try {
/*  595 */       this.byteOnlySizer.reset();
/*  596 */       paramCodingMethod.writeArrayTo(this.byteOnlySizer, paramArrayOfint, paramInt1, paramInt2);
/*  597 */       return this.byteOnlySizer.getSize();
/*  598 */     } catch (IOException iOException) {
/*  599 */       throw new RuntimeException(iOException);
/*      */     } 
/*      */   }
/*      */   
/*      */   int[] getDeltas(int paramInt1, int paramInt2) {
/*  604 */     if ((paramInt1 | paramInt2) != 0)
/*  605 */       return Coding.makeDeltas(this.values, this.start, this.end, paramInt1, paramInt2); 
/*  606 */     if (this.deltas == null) {
/*  607 */       this.deltas = Coding.makeDeltas(this.values, this.start, this.end, 0, 0);
/*      */     }
/*  609 */     return this.deltas;
/*      */   }
/*      */   Histogram getValueHistogram() {
/*  612 */     if (this.vHist == null) {
/*  613 */       this.vHist = new Histogram(this.values, this.start, this.end);
/*  614 */       if (this.verbose > 3) {
/*  615 */         this.vHist.print("vHist", System.out);
/*  616 */       } else if (this.verbose > 1) {
/*  617 */         this.vHist.print("vHist", null, System.out);
/*      */       } 
/*      */     } 
/*  620 */     return this.vHist;
/*      */   }
/*      */   Histogram getDeltaHistogram() {
/*  623 */     if (this.dHist == null) {
/*  624 */       this.dHist = new Histogram(getDeltas(0, 0));
/*  625 */       if (this.verbose > 3) {
/*  626 */         this.dHist.print("dHist", System.out);
/*  627 */       } else if (this.verbose > 1) {
/*  628 */         this.dHist.print("dHist", null, System.out);
/*      */       } 
/*      */     } 
/*  631 */     return this.dHist;
/*      */   }
/*      */   Histogram getHistogram(boolean paramBoolean) {
/*  634 */     return paramBoolean ? getDeltaHistogram() : getValueHistogram();
/*      */   }
/*      */ 
/*      */   
/*      */   private void tryPopulationCoding(Coding paramCoding) {
/*  639 */     Histogram histogram = getValueHistogram();
/*      */ 
/*      */     
/*  642 */     Coding coding1 = paramCoding.getValueCoding();
/*  643 */     Coding coding2 = BandStructure.UNSIGNED5.setL(64);
/*  644 */     Coding coding3 = paramCoding.getValueCoding();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  656 */     int i = 4 + Math.max(coding1.getLength(this.min), coding1
/*  657 */         .getLength(this.max));
/*      */     
/*  659 */     int m = coding2.getLength(0);
/*  660 */     int j = m * (this.end - this.start);
/*      */     
/*  662 */     int k = (int)Math.ceil(histogram.getBitLength(coding3) / 8.0D);
/*      */     
/*  664 */     int n = i + j + k;
/*  665 */     int i1 = 0;
/*      */ 
/*      */     
/*  668 */     int[] arrayOfInt1 = new int[1 + histogram.getTotalLength()];
/*      */ 
/*      */ 
/*      */     
/*  672 */     int i2 = -1;
/*  673 */     int i3 = -1;
/*      */ 
/*      */     
/*  676 */     int[][] arrayOfInt = histogram.getMatrix();
/*  677 */     byte b = -1;
/*  678 */     int i4 = 1;
/*  679 */     int i5 = 0; int i6;
/*  680 */     for (i6 = 1; i6 <= histogram.getTotalLength(); i6++) {
/*      */ 
/*      */ 
/*      */       
/*  684 */       if (i4 == 1) {
/*  685 */         b++;
/*  686 */         i5 = arrayOfInt[b][0];
/*  687 */         i4 = (arrayOfInt[b]).length;
/*      */       } 
/*  689 */       int i8 = arrayOfInt[b][--i4];
/*  690 */       arrayOfInt1[i6] = i8;
/*  691 */       int i9 = coding1.getLength(i8);
/*  692 */       i += i9;
/*      */       
/*  694 */       int i10 = i5;
/*  695 */       int i11 = i6;
/*  696 */       j += (coding2.getLength(i11) - m) * i10;
/*      */ 
/*      */ 
/*      */       
/*  700 */       k -= i9 * i10;
/*  701 */       int i12 = i + j + k;
/*      */       
/*  703 */       if (n > i12) {
/*  704 */         if (i12 <= this.targetSize) {
/*  705 */           i3 = i6;
/*  706 */           if (i2 < 0)
/*  707 */             i2 = i6; 
/*  708 */           if (this.verbose > 4) {
/*  709 */             Utils.log.info("better pop-size at fvc=" + i6 + " by " + 
/*  710 */                 pct((n - i12), n));
/*      */           }
/*      */         } 
/*  713 */         n = i12;
/*  714 */         i1 = i6;
/*      */       } 
/*      */     } 
/*  717 */     if (i2 < 0) {
/*  718 */       if (this.verbose > 1)
/*      */       {
/*  720 */         if (this.verbose > 1) {
/*  721 */           Utils.log.info("no good pop-size; best was " + n + " at " + i1 + " worse by " + 
/*      */ 
/*      */               
/*  724 */               pct((n - this.bestByteSize), this.bestByteSize));
/*      */         }
/*      */       }
/*      */       return;
/*      */     } 
/*  729 */     if (this.verbose > 1) {
/*  730 */       Utils.log.info("initial best pop-size at fvc=" + i1 + " in [" + i2 + ".." + i3 + "] by " + 
/*      */           
/*  732 */           pct((this.bestByteSize - n), this.bestByteSize));
/*      */     }
/*  734 */     i6 = this.bestZipSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  745 */     int[] arrayOfInt2 = PopulationCoding.LValuesCoded;
/*  746 */     ArrayList<Coding> arrayList1 = new ArrayList();
/*  747 */     ArrayList<Coding> arrayList2 = new ArrayList();
/*  748 */     ArrayList<Coding> arrayList3 = new ArrayList();
/*      */     
/*  750 */     if (i1 <= 255) {
/*  751 */       arrayList1.add(BandStructure.BYTE1);
/*      */     } else {
/*  753 */       int i8 = 5;
/*  754 */       boolean bool = (this.effort > 4) ? true : false;
/*  755 */       if (bool)
/*  756 */         arrayList2.add(BandStructure.BYTE1.setS(1)); 
/*  757 */       for (int i9 = arrayOfInt2.length - 1; i9 >= 1; i9--) {
/*  758 */         int i10 = arrayOfInt2[i9];
/*  759 */         Coding coding4 = PopulationCoding.fitTokenCoding(i2, i10);
/*  760 */         Coding coding5 = PopulationCoding.fitTokenCoding(i1, i10);
/*  761 */         Coding coding6 = PopulationCoding.fitTokenCoding(i3, i10);
/*  762 */         if (coding5 != null) {
/*  763 */           if (!arrayList1.contains(coding5))
/*  764 */             arrayList1.add(coding5); 
/*  765 */           if (i8 > coding5.B())
/*  766 */             i8 = coding5.B(); 
/*      */         } 
/*  768 */         if (bool) {
/*  769 */           if (coding6 == null) coding6 = coding5; 
/*  770 */           for (int i11 = coding4.B(); i11 <= coding6.B(); i11++) {
/*  771 */             if (i11 != coding5.B() && 
/*  772 */               i11 != 1) {
/*  773 */               Coding coding = coding6.setB(i11).setS(1);
/*  774 */               if (!arrayList2.contains(coding))
/*  775 */                 arrayList2.add(coding); 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  780 */       for (Iterator<Coding> iterator = arrayList1.iterator(); iterator.hasNext(); ) {
/*  781 */         Coding coding = iterator.next();
/*  782 */         if (coding.B() > i8) {
/*  783 */           iterator.remove();
/*  784 */           arrayList3.add(0, coding);
/*      */         } 
/*      */       } 
/*      */     } 
/*  788 */     ArrayList<Coding> arrayList4 = new ArrayList();
/*  789 */     Iterator<Coding> iterator1 = arrayList1.iterator();
/*  790 */     null = arrayList2.iterator();
/*  791 */     Iterator<Coding> iterator2 = arrayList3.iterator();
/*  792 */     while (iterator1.hasNext() || null.hasNext() || iterator2.hasNext()) {
/*  793 */       if (iterator1.hasNext()) arrayList4.add(iterator1.next()); 
/*  794 */       if (null.hasNext()) arrayList4.add(null.next()); 
/*  795 */       if (iterator2.hasNext()) arrayList4.add(iterator2.next()); 
/*      */     } 
/*  797 */     arrayList1.clear();
/*  798 */     arrayList2.clear();
/*  799 */     arrayList3.clear();
/*  800 */     int i7 = arrayList4.size();
/*  801 */     if (this.effort == 4) {
/*  802 */       i7 = 2;
/*  803 */     } else if (i7 > 4) {
/*  804 */       i7 -= 4;
/*  805 */       i7 = i7 * (this.effort - 4) / 5;
/*      */       
/*  807 */       i7 += 4;
/*      */     } 
/*  809 */     if (arrayList4.size() > i7) {
/*  810 */       if (this.verbose > 4)
/*  811 */         Utils.log.info("allFits before clip: " + arrayList4); 
/*  812 */       arrayList4.subList(i7, arrayList4.size()).clear();
/*      */     } 
/*  814 */     if (this.verbose > 3)
/*  815 */       Utils.log.info("allFits: " + arrayList4); 
/*  816 */     for (Coding coding : arrayList4) {
/*  817 */       int i8; boolean bool = false;
/*  818 */       if (coding.S() == 1) {
/*      */         
/*  820 */         bool = true;
/*  821 */         coding = coding.setS(0);
/*      */       } 
/*      */       
/*  824 */       if (!bool) {
/*  825 */         i8 = i1;
/*  826 */         assert coding.umax() >= i8;
/*  827 */         assert coding.B() == 1 || coding.setB(coding.B() - 1).umax() < i8;
/*      */       } else {
/*  829 */         i8 = Math.min(coding.umax(), i3);
/*  830 */         if (i8 < i2)
/*      */           continue; 
/*  832 */         if (i8 == i1)
/*      */           continue; 
/*      */       } 
/*  835 */       PopulationCoding populationCoding = new PopulationCoding();
/*  836 */       populationCoding.setHistogram(histogram);
/*  837 */       populationCoding.setL(coding.L());
/*  838 */       populationCoding.setFavoredValues(arrayOfInt1, i8);
/*  839 */       assert populationCoding.tokenCoding == coding;
/*  840 */       populationCoding.resortFavoredValues();
/*      */       
/*  842 */       int[] arrayOfInt3 = computePopSizePrivate(populationCoding, coding1, coding3);
/*      */       
/*  844 */       noteSizes(populationCoding, arrayOfInt3[0], 4 + arrayOfInt3[1]);
/*      */     } 
/*  846 */     if (this.verbose > 3) {
/*  847 */       Utils.log.info("measured best pop, size=" + this.bestByteSize + "/zs=" + this.bestZipSize + " better by " + 
/*      */ 
/*      */           
/*  850 */           pct((i6 - this.bestZipSize), i6));
/*  851 */       if (this.bestZipSize < i6) {
/*  852 */         Utils.log.info(">>> POP WINS BY " + (i6 - this.bestZipSize));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] computePopSizePrivate(PopulationCoding paramPopulationCoding, Coding paramCoding1, Coding paramCoding2) {
/*      */     int[] arrayOfInt4;
/*  862 */     if (this.popHelper == null) {
/*  863 */       this.popHelper = new CodingChooser(this.effort, this.allCodingChoices);
/*  864 */       if (this.stress != null)
/*  865 */         this.popHelper.addStressSeed(this.stress.nextInt()); 
/*  866 */       this.popHelper.topLevel = false;
/*  867 */       this.popHelper.verbose--;
/*  868 */       this.popHelper.disablePopCoding = true;
/*  869 */       this.popHelper.disableRunCoding = this.disableRunCoding;
/*  870 */       if (this.effort < 5)
/*      */       {
/*  872 */         this.popHelper.disableRunCoding = true; } 
/*      */     } 
/*  874 */     int i = paramPopulationCoding.fVlen;
/*  875 */     if (this.verbose > 2) {
/*  876 */       Utils.log.info("computePopSizePrivate fvlen=" + i + " tc=" + paramPopulationCoding.tokenCoding);
/*      */       
/*  878 */       Utils.log.info("{ //BEGIN");
/*      */     } 
/*      */ 
/*      */     
/*  882 */     int[] arrayOfInt1 = paramPopulationCoding.fValues;
/*  883 */     int[][] arrayOfInt = paramPopulationCoding.encodeValues(this.values, this.start, this.end);
/*  884 */     int[] arrayOfInt2 = arrayOfInt[0];
/*  885 */     int[] arrayOfInt3 = arrayOfInt[1];
/*  886 */     if (this.verbose > 2)
/*  887 */       Utils.log.info("-- refine on fv[" + i + "] fc=" + paramCoding1); 
/*  888 */     paramPopulationCoding.setFavoredCoding(this.popHelper.choose(arrayOfInt1, 1, 1 + i, paramCoding1));
/*  889 */     if (paramPopulationCoding.tokenCoding instanceof Coding && (this.stress == null || this.stress
/*  890 */       .nextBoolean())) {
/*  891 */       if (this.verbose > 2)
/*  892 */         Utils.log.info("-- refine on tv[" + arrayOfInt2.length + "] tc=" + paramPopulationCoding.tokenCoding); 
/*  893 */       CodingMethod codingMethod = this.popHelper.choose(arrayOfInt2, (Coding)paramPopulationCoding.tokenCoding);
/*  894 */       if (codingMethod != paramPopulationCoding.tokenCoding) {
/*  895 */         if (this.verbose > 2)
/*  896 */           Utils.log.info(">>> refined tc=" + codingMethod); 
/*  897 */         paramPopulationCoding.setTokenCoding(codingMethod);
/*      */       } 
/*      */     } 
/*  900 */     if (arrayOfInt3.length == 0) {
/*  901 */       paramPopulationCoding.setUnfavoredCoding(null);
/*      */     } else {
/*  903 */       if (this.verbose > 2)
/*  904 */         Utils.log.info("-- refine on uv[" + arrayOfInt3.length + "] uc=" + paramPopulationCoding.unfavoredCoding); 
/*  905 */       paramPopulationCoding.setUnfavoredCoding(this.popHelper.choose(arrayOfInt3, paramCoding2));
/*      */     } 
/*  907 */     if (this.verbose > 3) {
/*  908 */       Utils.log.info("finish computePopSizePrivate fvlen=" + i + " fc=" + paramPopulationCoding.favoredCoding + " tc=" + paramPopulationCoding.tokenCoding + " uc=" + paramPopulationCoding.unfavoredCoding);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  913 */       StringBuilder stringBuilder = new StringBuilder();
/*  914 */       stringBuilder.append("fv = {");
/*  915 */       for (byte b = 1; b <= i; b++) {
/*  916 */         if (b % 10 == 0)
/*  917 */           stringBuilder.append('\n'); 
/*  918 */         stringBuilder.append(" ").append(arrayOfInt1[b]);
/*      */       } 
/*  920 */       stringBuilder.append('\n');
/*  921 */       stringBuilder.append("}");
/*  922 */       Utils.log.info(stringBuilder.toString());
/*      */     } 
/*  924 */     if (this.verbose > 2) {
/*  925 */       Utils.log.info("} //END");
/*      */     }
/*  927 */     if (this.stress != null) {
/*  928 */       return null;
/*      */     }
/*      */     
/*      */     try {
/*  932 */       resetData();
/*      */       
/*  934 */       paramPopulationCoding.writeSequencesTo(this.byteSizer, arrayOfInt2, arrayOfInt3);
/*  935 */       arrayOfInt4 = new int[] { getByteSize(), getZipSize() };
/*  936 */     } catch (IOException iOException) {
/*  937 */       throw new RuntimeException(iOException);
/*      */     } 
/*  939 */     int[] arrayOfInt5 = null;
/*  940 */     assert (arrayOfInt5 = computeSizePrivate(paramPopulationCoding)) != null;
/*  941 */     assert arrayOfInt5[0] == arrayOfInt4[0] : arrayOfInt5[false] + " != " + arrayOfInt4[false];
/*      */     
/*  943 */     return arrayOfInt4;
/*      */   }
/*      */   private void tryAdaptiveCoding(Coding paramCoding) {
/*      */     double d2;
/*  947 */     int i = this.bestZipSize;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  952 */     int j = this.start;
/*  953 */     int k = this.end;
/*  954 */     int[] arrayOfInt1 = this.values;
/*  955 */     int m = k - j;
/*  956 */     if (paramCoding.isDelta()) {
/*  957 */       arrayOfInt1 = getDeltas(0, 0);
/*  958 */       j = 0;
/*  959 */       k = arrayOfInt1.length;
/*      */     } 
/*  961 */     int[] arrayOfInt2 = new int[m + 1];
/*  962 */     byte b1 = 0;
/*  963 */     int n = 0;
/*  964 */     for (int i1 = j; i1 < k; i1++) {
/*  965 */       int i3 = arrayOfInt1[i1];
/*  966 */       arrayOfInt2[b1++] = n;
/*  967 */       int i4 = paramCoding.getLength(i3);
/*  968 */       assert i4 < Integer.MAX_VALUE;
/*      */       
/*  970 */       n += i4;
/*      */     } 
/*  972 */     arrayOfInt2[b1++] = n;
/*  973 */     assert b1 == arrayOfInt2.length;
/*  974 */     double d1 = n / m;
/*      */ 
/*      */ 
/*      */     
/*  978 */     if (this.effort >= 5) {
/*  979 */       if (this.effort > 6) {
/*  980 */         d2 = 1.001D;
/*      */       } else {
/*  982 */         d2 = 1.003D;
/*      */       } 
/*  984 */     } else if (this.effort > 3) {
/*  985 */       d2 = 1.01D;
/*      */     } else {
/*  987 */       d2 = 1.03D;
/*      */     } 
/*      */     
/*  990 */     d2 *= d2;
/*  991 */     double d3 = d2 * d2;
/*  992 */     double d4 = d2 * d2 * d2;
/*      */     
/*  994 */     double[] arrayOfDouble1 = new double[1 + this.effort - 3];
/*  995 */     double d5 = Math.log(m);
/*  996 */     for (byte b2 = 0; b2 < arrayOfDouble1.length; b2++) {
/*  997 */       arrayOfDouble1[b2] = Math.exp(d5 * (b2 + 1) / (arrayOfDouble1.length + 1));
/*      */     }
/*  999 */     int[] arrayOfInt3 = new int[arrayOfDouble1.length];
/* 1000 */     byte b3 = 0;
/* 1001 */     for (byte b4 = 0; b4 < arrayOfDouble1.length; b4++) {
/* 1002 */       int i3 = (int)Math.round(arrayOfDouble1[b4]);
/* 1003 */       i3 = AdaptiveCoding.getNextK(i3 - 1);
/* 1004 */       if (i3 > 0 && i3 < m && (
/* 1005 */         !b3 || i3 != arrayOfInt3[b3 - 1]))
/* 1006 */         arrayOfInt3[b3++] = i3; 
/*      */     } 
/* 1008 */     arrayOfInt3 = BandStructure.realloc(arrayOfInt3, b3);
/*      */ 
/*      */ 
/*      */     
/* 1012 */     int[] arrayOfInt4 = new int[arrayOfInt3.length];
/* 1013 */     double[] arrayOfDouble2 = new double[arrayOfInt3.length]; int i2;
/* 1014 */     for (i2 = 0; i2 < arrayOfInt3.length; i2++) {
/* 1015 */       double d; int i3 = arrayOfInt3[i2];
/*      */       
/* 1017 */       if (i3 < 10) {
/* 1018 */         d = d4;
/* 1019 */       } else if (i3 < 100) {
/* 1020 */         d = d3;
/*      */       } else {
/* 1022 */         d = d2;
/* 1023 */       }  arrayOfDouble2[i2] = d;
/* 1024 */       arrayOfInt4[i2] = 4 + (int)Math.ceil(i3 * d1 * d);
/*      */     } 
/* 1026 */     if (this.verbose > 1) {
/* 1027 */       System.out.print("tryAdaptiveCoding [" + m + "] avgS=" + d1 + " fuzz=" + d2 + " meshes: {");
/*      */ 
/*      */       
/* 1030 */       for (i2 = 0; i2 < arrayOfInt3.length; i2++) {
/* 1031 */         System.out.print(" " + arrayOfInt3[i2] + "(" + arrayOfInt4[i2] + ")");
/*      */       }
/* 1033 */       Utils.log.info(" }");
/*      */     } 
/* 1035 */     if (this.runHelper == null) {
/* 1036 */       this.runHelper = new CodingChooser(this.effort, this.allCodingChoices);
/* 1037 */       if (this.stress != null)
/* 1038 */         this.runHelper.addStressSeed(this.stress.nextInt()); 
/* 1039 */       this.runHelper.topLevel = false;
/* 1040 */       this.runHelper.verbose--;
/* 1041 */       this.runHelper.disableRunCoding = true;
/* 1042 */       this.runHelper.disablePopCoding = this.disablePopCoding;
/* 1043 */       if (this.effort < 5)
/*      */       {
/* 1045 */         this.runHelper.disablePopCoding = true; } 
/*      */     } 
/* 1047 */     for (i2 = 0; i2 < m; i2++) {
/* 1048 */       i2 = AdaptiveCoding.getNextK(i2 - 1);
/* 1049 */       if (i2 > m) i2 = m; 
/* 1050 */       for (int i3 = arrayOfInt3.length - 1; i3 >= 0; i3--) {
/* 1051 */         int i4 = arrayOfInt3[i3];
/* 1052 */         int i5 = arrayOfInt4[i3];
/* 1053 */         if (i2 + i4 <= m) {
/* 1054 */           int i6 = arrayOfInt2[i2 + i4] - arrayOfInt2[i2];
/* 1055 */           if (i6 >= i5) {
/*      */             CodingMethod codingMethod1, codingMethod3;
/* 1057 */             int i7 = i2 + i4;
/* 1058 */             int i8 = i6;
/* 1059 */             double d = d1 * arrayOfDouble2[i3];
/* 1060 */             while (i7 < m && i7 - i2 <= m / 2) {
/* 1061 */               int i10 = i7;
/* 1062 */               int i11 = i8;
/* 1063 */               i7 += i4;
/* 1064 */               i7 = i2 + AdaptiveCoding.getNextK(i7 - i2 - 1);
/* 1065 */               if (i7 < 0 || i7 > m)
/* 1066 */                 i7 = m; 
/* 1067 */               i8 = arrayOfInt2[i7] - arrayOfInt2[i2];
/* 1068 */               if (i8 < 4.0D + (i7 - i2) * d) {
/* 1069 */                 i8 = i11;
/* 1070 */                 i7 = i10;
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1074 */             int i9 = i7;
/* 1075 */             if (this.verbose > 2) {
/* 1076 */               Utils.log.info("bulge at " + i2 + "[" + (i7 - i2) + "] of " + 
/* 1077 */                   pct(i8 - d1 * (i7 - i2), d1 * (i7 - i2)));
/*      */               
/* 1079 */               Utils.log.info("{ //BEGIN");
/*      */             } 
/*      */             
/* 1082 */             CodingMethod codingMethod2 = this.runHelper.choose(this.values, this.start + i2, this.start + i7, paramCoding);
/*      */ 
/*      */ 
/*      */             
/* 1086 */             if (codingMethod2 == paramCoding) {
/*      */               
/* 1088 */               codingMethod1 = paramCoding;
/* 1089 */               codingMethod3 = paramCoding;
/*      */             } else {
/* 1091 */               codingMethod1 = this.runHelper.choose(this.values, this.start, this.start + i2, paramCoding);
/*      */ 
/*      */ 
/*      */               
/* 1095 */               codingMethod3 = this.runHelper.choose(this.values, this.start + i7, this.start + m, paramCoding);
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 1100 */             if (this.verbose > 2)
/* 1101 */               Utils.log.info("} //END"); 
/* 1102 */             if (codingMethod1 == codingMethod2 && i2 > 0 && 
/* 1103 */               AdaptiveCoding.isCodableLength(i7)) {
/* 1104 */               i2 = 0;
/*      */             }
/* 1106 */             if (codingMethod2 == codingMethod3 && i7 < m) {
/* 1107 */               i7 = m;
/*      */             }
/* 1109 */             if (codingMethod1 != paramCoding || codingMethod2 != paramCoding || codingMethod3 != paramCoding) {
/*      */               CodingMethod codingMethod;
/*      */ 
/*      */               
/* 1113 */               byte b = 0;
/* 1114 */               if (i7 == m) {
/* 1115 */                 codingMethod = codingMethod2;
/*      */               } else {
/* 1117 */                 codingMethod = new AdaptiveCoding(i7 - i2, codingMethod2, codingMethod3);
/* 1118 */                 b += true;
/*      */               } 
/* 1120 */               if (i2 > 0) {
/* 1121 */                 codingMethod = new AdaptiveCoding(i2, codingMethod1, codingMethod);
/* 1122 */                 b += true;
/*      */               } 
/* 1124 */               int[] arrayOfInt = computeSizePrivate(codingMethod);
/* 1125 */               noteSizes(codingMethod, arrayOfInt[0], arrayOfInt[1] + b);
/*      */             } 
/*      */ 
/*      */             
/* 1129 */             i2 = i9; break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1134 */     if (this.verbose > 3 && 
/* 1135 */       this.bestZipSize < i) {
/* 1136 */       Utils.log.info(">>> RUN WINS BY " + (i - this.bestZipSize));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String pct(double paramDouble1, double paramDouble2) {
/* 1144 */     return (Math.round(paramDouble1 / paramDouble2 * 10000.0D) / 100.0D) + "%";
/*      */   }
/*      */   
/*      */   static class Sizer extends OutputStream { final OutputStream out;
/*      */     private int count;
/*      */     
/*      */     Sizer(OutputStream param1OutputStream) {
/* 1151 */       this.out = param1OutputStream;
/*      */     }
/*      */     Sizer() {
/* 1154 */       this(null);
/*      */     }
/*      */     
/*      */     public void write(int param1Int) throws IOException {
/* 1158 */       this.count++;
/* 1159 */       if (this.out != null) this.out.write(param1Int); 
/*      */     }
/*      */     public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 1162 */       this.count += param1Int2;
/* 1163 */       if (this.out != null) this.out.write(param1ArrayOfbyte, param1Int1, param1Int2); 
/*      */     }
/*      */     public void reset() {
/* 1166 */       this.count = 0;
/*      */     } public int getSize() {
/* 1168 */       return this.count;
/*      */     }
/*      */     public String toString() {
/* 1171 */       String str = super.toString();
/*      */       
/* 1173 */       assert (str = stringForDebug()) != null;
/* 1174 */       return str;
/*      */     }
/*      */     String stringForDebug() {
/* 1177 */       return "<Sizer " + getSize() + ">";
/*      */     } }
/*      */ 
/*      */   
/* 1181 */   CodingChooser(int paramInt, Coding[] paramArrayOfCoding) { this.zipSizer = new Sizer();
/* 1182 */     this.zipDef = new Deflater();
/* 1183 */     this.zipOut = new DeflaterOutputStream(this.zipSizer, this.zipDef);
/* 1184 */     this.byteSizer = new Sizer(this.zipOut);
/* 1185 */     this.byteOnlySizer = new Sizer(); PropMap propMap = Utils.currentPropMap(); if (propMap != null) { this.verbose = Math.max(propMap.getInteger("com.sun.java.util.jar.pack.verbose"), propMap.getInteger("com.sun.java.util.jar.pack.verbose.coding")); this.optUseHistogram = !propMap.getBoolean("com.sun.java.util.jar.pack.no.histogram"); this.optUsePopulationCoding = !propMap.getBoolean("com.sun.java.util.jar.pack.no.population.coding"); this.optUseAdaptiveCoding = !propMap.getBoolean("com.sun.java.util.jar.pack.no.adaptive.coding"); int i = propMap.getInteger("com.sun.java.util.jar.pack.stress.coding"); if (i != 0)
/*      */         this.stress = new Random(i);  }  this.effort = paramInt; this.allCodingChoices = paramArrayOfCoding; this.fuzz = 1.0D + 0.0025D * (paramInt - 5); byte b1 = 0; byte b2; for (b2 = 0; b2 < paramArrayOfCoding.length; b2++) { if (paramArrayOfCoding[b2] != null)
/*      */         b1++;  }  this.choices = new Choice[b1]; b1 = 0; for (b2 = 0; b2 < paramArrayOfCoding.length; b2++) { if (paramArrayOfCoding[b2] != null) { int[] arrayOfInt = new int[this.choices.length]; this.choices[b1++] = new Choice(paramArrayOfCoding[b2], b2, arrayOfInt); }  }  for (b2 = 0; b2 < this.choices.length; b2++) { Coding coding = (this.choices[b2]).coding; assert coding.distanceFrom(coding) == 0; for (byte b = 0; b < b2; b++) { Coding coding1 = (this.choices[b]).coding; int i = coding.distanceFrom(coding1); assert i > 0; assert i == coding1.distanceFrom(coding); (this.choices[b2]).distance[b] = i; (this.choices[b]).distance[b2] = i; }  }
/* 1188 */      } private void resetData() { flushData();
/* 1189 */     this.zipDef.reset();
/* 1190 */     if (this.context != null) {
/*      */       
/*      */       try {
/* 1193 */         this.context.writeTo(this.byteSizer);
/* 1194 */       } catch (IOException iOException) {
/* 1195 */         throw new RuntimeException(iOException);
/*      */       } 
/*      */     }
/* 1198 */     this.zipSizer.reset();
/* 1199 */     this.byteSizer.reset(); }
/*      */   
/*      */   private void flushData() {
/*      */     try {
/* 1203 */       this.zipOut.finish();
/* 1204 */     } catch (IOException iOException) {
/* 1205 */       throw new RuntimeException(iOException);
/*      */     } 
/*      */   }
/*      */   private int getByteSize() {
/* 1209 */     return this.byteSizer.getSize();
/*      */   }
/*      */   private int getZipSize() {
/* 1212 */     flushData();
/* 1213 */     return this.zipSizer.getSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addStressSeed(int paramInt) {
/* 1220 */     if (this.stress == null)
/* 1221 */       return;  this.stress.setSeed(paramInt + (this.stress.nextInt() << 32L));
/*      */   }
/*      */ 
/*      */   
/*      */   private CodingMethod stressPopCoding(CodingMethod paramCodingMethod) {
/* 1226 */     assert this.stress != null;
/*      */     
/* 1228 */     if (!(paramCodingMethod instanceof Coding)) return paramCodingMethod; 
/* 1229 */     Coding coding = ((Coding)paramCodingMethod).getValueCoding();
/* 1230 */     Histogram histogram = getValueHistogram();
/* 1231 */     int i = stressLen(histogram.getTotalLength());
/* 1232 */     if (i == 0) return paramCodingMethod; 
/* 1233 */     ArrayList<Integer> arrayList = new ArrayList();
/* 1234 */     if (this.stress.nextBoolean()) {
/*      */       
/* 1236 */       HashSet<Integer> hashSet = new HashSet();
/* 1237 */       for (int m = this.start; m < this.end; m++) {
/* 1238 */         if (hashSet.add(Integer.valueOf(this.values[m]))) arrayList.add(Integer.valueOf(this.values[m])); 
/*      */       } 
/*      */     } else {
/* 1241 */       int[][] arrayOfInt = histogram.getMatrix();
/* 1242 */       for (byte b1 = 0; b1 < arrayOfInt.length; b1++) {
/* 1243 */         int[] arrayOfInt3 = arrayOfInt[b1];
/* 1244 */         for (byte b2 = 1; b2 < arrayOfInt3.length; b2++) {
/* 1245 */           arrayList.add(Integer.valueOf(arrayOfInt3[b2]));
/*      */         }
/*      */       } 
/*      */     } 
/* 1249 */     int j = this.stress.nextInt();
/* 1250 */     if ((j & 0x7) <= 2) {
/*      */       
/* 1252 */       Collections.shuffle(arrayList, this.stress);
/*      */     } else {
/*      */       
/* 1255 */       if (((j >>>= 3) & 0x7) <= 2) Collections.sort(arrayList); 
/* 1256 */       if (((j >>>= 3) & 0x7) <= 2) Collections.reverse(arrayList); 
/* 1257 */       if (((j >>>= 3) & 0x7) <= 2) Collections.rotate(arrayList, stressLen(arrayList.size())); 
/*      */     } 
/* 1259 */     if (arrayList.size() > i)
/*      */     {
/* 1261 */       if (((j >>>= 3) & 0x7) <= 2) {
/*      */         
/* 1263 */         arrayList.subList(i, arrayList.size()).clear();
/*      */       } else {
/*      */         
/* 1266 */         arrayList.subList(0, arrayList.size() - i).clear();
/*      */       } 
/*      */     }
/* 1269 */     i = arrayList.size();
/* 1270 */     int[] arrayOfInt1 = new int[1 + i];
/* 1271 */     for (byte b = 0; b < i; b++) {
/* 1272 */       arrayOfInt1[1 + b] = ((Integer)arrayList.get(b)).intValue();
/*      */     }
/* 1274 */     PopulationCoding populationCoding = new PopulationCoding();
/* 1275 */     populationCoding.setFavoredValues(arrayOfInt1, i);
/* 1276 */     int[] arrayOfInt2 = PopulationCoding.LValuesCoded; int k;
/* 1277 */     for (k = 0; k < arrayOfInt2.length / 2; k++) {
/* 1278 */       int m = arrayOfInt2[this.stress.nextInt(arrayOfInt2.length)];
/* 1279 */       if (m >= 0 && 
/* 1280 */         PopulationCoding.fitTokenCoding(i, m) != null) {
/* 1281 */         populationCoding.setL(m);
/*      */         break;
/*      */       } 
/*      */     } 
/* 1285 */     if (populationCoding.tokenCoding == null) {
/* 1286 */       int m = k = arrayOfInt1[1];
/* 1287 */       for (byte b1 = 2; b1 <= i; b1++) {
/* 1288 */         int n = arrayOfInt1[b1];
/* 1289 */         if (k > n) k = n; 
/* 1290 */         if (m < n) m = n; 
/*      */       } 
/* 1292 */       populationCoding.tokenCoding = stressCoding(k, m);
/*      */     } 
/*      */     
/* 1295 */     computePopSizePrivate(populationCoding, coding, coding);
/* 1296 */     return populationCoding;
/*      */   }
/*      */ 
/*      */   
/*      */   private CodingMethod stressAdaptiveCoding(CodingMethod paramCodingMethod) {
/* 1301 */     assert this.stress != null;
/*      */     
/* 1303 */     if (!(paramCodingMethod instanceof Coding)) return paramCodingMethod; 
/* 1304 */     Coding coding = (Coding)paramCodingMethod;
/* 1305 */     int i = this.end - this.start;
/* 1306 */     if (i < 2) return paramCodingMethod;
/*      */     
/* 1308 */     int j = stressLen(i - 1) + 1;
/* 1309 */     if (j == i) return paramCodingMethod; 
/*      */     try {
/* 1311 */       assert !this.disableRunCoding;
/* 1312 */       this.disableRunCoding = true;
/* 1313 */       int[] arrayOfInt = (int[])this.values.clone();
/* 1314 */       CodingMethod codingMethod = null;
/* 1315 */       int k = this.end;
/* 1316 */       int m = this.start;
/* 1317 */       for (; k > m; k = n) {
/*      */         
/* 1319 */         int i1, i2 = (k - m < 100) ? -1 : this.stress.nextInt();
/* 1320 */         if ((i2 & 0x7) != 0) {
/* 1321 */           i1 = (j == 1) ? j : (stressLen(j - 1) + 1);
/*      */         } else {
/*      */           
/* 1324 */           int i3 = (i2 >>>= 3) & 0x3;
/* 1325 */           int i4 = (i2 >>>= 3) & 0xFF;
/*      */           while (true) {
/* 1327 */             i1 = AdaptiveCoding.decodeK(i3, i4);
/* 1328 */             if (i1 <= k - m)
/*      */               break; 
/* 1330 */             if (i4 != 3) {
/* 1331 */               i4 = 3; continue;
/*      */             } 
/* 1333 */             i3--;
/*      */           } 
/*      */           
/* 1336 */           assert AdaptiveCoding.isCodableLength(i1);
/*      */         } 
/* 1338 */         if (i1 > k - m) i1 = k - m; 
/* 1339 */         while (!AdaptiveCoding.isCodableLength(i1)) {
/* 1340 */           i1--;
/*      */         }
/* 1342 */         int n = k - i1;
/* 1343 */         assert n < k;
/* 1344 */         assert n >= m;
/*      */         
/* 1346 */         CodingMethod codingMethod1 = choose(arrayOfInt, n, k, coding);
/* 1347 */         if (codingMethod == null) {
/* 1348 */           codingMethod = codingMethod1;
/*      */         } else {
/* 1350 */           codingMethod = new AdaptiveCoding(k - n, codingMethod1, codingMethod);
/*      */         } 
/*      */       } 
/* 1353 */       return codingMethod;
/*      */     } finally {
/* 1355 */       this.disableRunCoding = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Coding stressCoding(int paramInt1, int paramInt2) {
/* 1361 */     assert this.stress != null;
/* 1362 */     for (byte b = 0; b < 100; b++) {
/* 1363 */       Coding coding = Coding.of(this.stress.nextInt(5) + 1, this.stress
/* 1364 */           .nextInt(256) + 1, this.stress
/* 1365 */           .nextInt(3));
/* 1366 */       if (coding.B() == 1) coding = coding.setH(256); 
/* 1367 */       if (coding.H() == 256 && coding.B() >= 5) coding = coding.setB(4); 
/* 1368 */       if (this.stress.nextBoolean()) {
/* 1369 */         Coding coding1 = coding.setD(1);
/* 1370 */         if (coding1.canRepresent(paramInt1, paramInt2)) return coding1; 
/*      */       } 
/* 1372 */       if (coding.canRepresent(paramInt1, paramInt2)) return coding; 
/*      */     } 
/* 1374 */     return BandStructure.UNSIGNED5;
/*      */   }
/*      */ 
/*      */   
/*      */   private int stressLen(int paramInt) {
/* 1379 */     assert this.stress != null;
/* 1380 */     assert paramInt >= 0;
/* 1381 */     int i = this.stress.nextInt(100);
/* 1382 */     if (i < 20)
/* 1383 */       return Math.min(paramInt / 5, i); 
/* 1384 */     if (i < 40) {
/* 1385 */       return paramInt;
/*      */     }
/* 1387 */     return this.stress.nextInt(paramInt);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/CodingChooser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */