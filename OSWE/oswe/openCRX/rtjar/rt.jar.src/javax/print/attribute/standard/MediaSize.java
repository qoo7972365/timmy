/*     */ package javax.print.attribute.standard;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ import javax.print.attribute.Attribute;
/*     */ import javax.print.attribute.Size2DSyntax;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MediaSize
/*     */   extends Size2DSyntax
/*     */   implements Attribute
/*     */ {
/*     */   private static final long serialVersionUID = -1967958664615414771L;
/*     */   private MediaSizeName mediaName;
/*  59 */   private static HashMap mediaMap = new HashMap<>(100, 10.0F);
/*     */   
/*  61 */   private static Vector sizeVector = new Vector(100, 10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaSize(float paramFloat1, float paramFloat2, int paramInt) {
/*  78 */     super(paramFloat1, paramFloat2, paramInt);
/*  79 */     if (paramFloat1 > paramFloat2) {
/*  80 */       throw new IllegalArgumentException("X dimension > Y dimension");
/*     */     }
/*  82 */     sizeVector.add(this);
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
/*     */   public MediaSize(int paramInt1, int paramInt2, int paramInt3) {
/*  99 */     super(paramInt1, paramInt2, paramInt3);
/* 100 */     if (paramInt1 > paramInt2) {
/* 101 */       throw new IllegalArgumentException("X dimension > Y dimension");
/*     */     }
/* 103 */     sizeVector.add(this);
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
/*     */   public MediaSize(float paramFloat1, float paramFloat2, int paramInt, MediaSizeName paramMediaSizeName) {
/* 122 */     super(paramFloat1, paramFloat2, paramInt);
/* 123 */     if (paramFloat1 > paramFloat2) {
/* 124 */       throw new IllegalArgumentException("X dimension > Y dimension");
/*     */     }
/* 126 */     if (paramMediaSizeName != null && mediaMap.get(paramMediaSizeName) == null) {
/* 127 */       this.mediaName = paramMediaSizeName;
/* 128 */       mediaMap.put(this.mediaName, this);
/*     */     } 
/* 130 */     sizeVector.add(this);
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
/*     */   public MediaSize(int paramInt1, int paramInt2, int paramInt3, MediaSizeName paramMediaSizeName) {
/* 148 */     super(paramInt1, paramInt2, paramInt3);
/* 149 */     if (paramInt1 > paramInt2) {
/* 150 */       throw new IllegalArgumentException("X dimension > Y dimension");
/*     */     }
/* 152 */     if (paramMediaSizeName != null && mediaMap.get(paramMediaSizeName) == null) {
/* 153 */       this.mediaName = paramMediaSizeName;
/* 154 */       mediaMap.put(this.mediaName, this);
/*     */     } 
/* 156 */     sizeVector.add(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaSizeName getMediaSizeName() {
/* 166 */     return this.mediaName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MediaSize getMediaSizeForName(MediaSizeName paramMediaSizeName) {
/* 177 */     return (MediaSize)mediaMap.get(paramMediaSizeName);
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
/*     */   public static MediaSizeName findMedia(float paramFloat1, float paramFloat2, int paramInt) {
/* 203 */     MediaSize mediaSize = ISO.A4;
/*     */     
/* 205 */     if (paramFloat1 <= 0.0F || paramFloat2 <= 0.0F || paramInt < 1) {
/* 206 */       throw new IllegalArgumentException("args must be +ve values");
/*     */     }
/*     */     
/* 209 */     double d = (paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2);
/*     */ 
/*     */     
/* 212 */     float f1 = paramFloat1;
/* 213 */     float f2 = paramFloat2;
/*     */     
/* 215 */     for (byte b = 0; b < sizeVector.size(); b++) {
/* 216 */       MediaSize mediaSize1 = sizeVector.elementAt(b);
/* 217 */       float[] arrayOfFloat = mediaSize1.getSize(paramInt);
/* 218 */       if (paramFloat1 == arrayOfFloat[0] && paramFloat2 == arrayOfFloat[1]) {
/* 219 */         mediaSize = mediaSize1;
/*     */         break;
/*     */       } 
/* 222 */       f1 = paramFloat1 - arrayOfFloat[0];
/* 223 */       f2 = paramFloat2 - arrayOfFloat[1];
/* 224 */       double d1 = (f1 * f1 + f2 * f2);
/* 225 */       if (d1 < d) {
/* 226 */         d = d1;
/* 227 */         mediaSize = mediaSize1;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 232 */     return mediaSize.getMediaSizeName();
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
/*     */   public boolean equals(Object paramObject) {
/* 258 */     return (super.equals(paramObject) && paramObject instanceof MediaSize);
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
/* 272 */     return (Class)MediaSize.class;
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
/* 285 */     return "media-size";
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
/*     */   public static final class ISO
/*     */   {
/* 298 */     public static final MediaSize A0 = new MediaSize(841, 1189, 1000, MediaSizeName.ISO_A0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 303 */     public static final MediaSize A1 = new MediaSize(594, 841, 1000, MediaSizeName.ISO_A1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 308 */     public static final MediaSize A2 = new MediaSize(420, 594, 1000, MediaSizeName.ISO_A2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     public static final MediaSize A3 = new MediaSize(297, 420, 1000, MediaSizeName.ISO_A3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 318 */     public static final MediaSize A4 = new MediaSize(210, 297, 1000, MediaSizeName.ISO_A4);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 323 */     public static final MediaSize A5 = new MediaSize(148, 210, 1000, MediaSizeName.ISO_A5);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 328 */     public static final MediaSize A6 = new MediaSize(105, 148, 1000, MediaSizeName.ISO_A6);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 333 */     public static final MediaSize A7 = new MediaSize(74, 105, 1000, MediaSizeName.ISO_A7);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 338 */     public static final MediaSize A8 = new MediaSize(52, 74, 1000, MediaSizeName.ISO_A8);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 343 */     public static final MediaSize A9 = new MediaSize(37, 52, 1000, MediaSizeName.ISO_A9);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 348 */     public static final MediaSize A10 = new MediaSize(26, 37, 1000, MediaSizeName.ISO_A10);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 353 */     public static final MediaSize B0 = new MediaSize(1000, 1414, 1000, MediaSizeName.ISO_B0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 358 */     public static final MediaSize B1 = new MediaSize(707, 1000, 1000, MediaSizeName.ISO_B1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 363 */     public static final MediaSize B2 = new MediaSize(500, 707, 1000, MediaSizeName.ISO_B2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 368 */     public static final MediaSize B3 = new MediaSize(353, 500, 1000, MediaSizeName.ISO_B3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 373 */     public static final MediaSize B4 = new MediaSize(250, 353, 1000, MediaSizeName.ISO_B4);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 378 */     public static final MediaSize B5 = new MediaSize(176, 250, 1000, MediaSizeName.ISO_B5);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 383 */     public static final MediaSize B6 = new MediaSize(125, 176, 1000, MediaSizeName.ISO_B6);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 388 */     public static final MediaSize B7 = new MediaSize(88, 125, 1000, MediaSizeName.ISO_B7);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 393 */     public static final MediaSize B8 = new MediaSize(62, 88, 1000, MediaSizeName.ISO_B8);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 398 */     public static final MediaSize B9 = new MediaSize(44, 62, 1000, MediaSizeName.ISO_B9);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 403 */     public static final MediaSize B10 = new MediaSize(31, 44, 1000, MediaSizeName.ISO_B10);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 408 */     public static final MediaSize C3 = new MediaSize(324, 458, 1000, MediaSizeName.ISO_C3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 413 */     public static final MediaSize C4 = new MediaSize(229, 324, 1000, MediaSizeName.ISO_C4);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 418 */     public static final MediaSize C5 = new MediaSize(162, 229, 1000, MediaSizeName.ISO_C5);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 423 */     public static final MediaSize C6 = new MediaSize(114, 162, 1000, MediaSizeName.ISO_C6);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 428 */     public static final MediaSize DESIGNATED_LONG = new MediaSize(110, 220, 1000, MediaSizeName.ISO_DESIGNATED_LONG);
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
/*     */   public static final class JIS
/*     */   {
/* 448 */     public static final MediaSize B0 = new MediaSize(1030, 1456, 1000, MediaSizeName.JIS_B0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 453 */     public static final MediaSize B1 = new MediaSize(728, 1030, 1000, MediaSizeName.JIS_B1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 458 */     public static final MediaSize B2 = new MediaSize(515, 728, 1000, MediaSizeName.JIS_B2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 463 */     public static final MediaSize B3 = new MediaSize(364, 515, 1000, MediaSizeName.JIS_B3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 468 */     public static final MediaSize B4 = new MediaSize(257, 364, 1000, MediaSizeName.JIS_B4);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 473 */     public static final MediaSize B5 = new MediaSize(182, 257, 1000, MediaSizeName.JIS_B5);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 478 */     public static final MediaSize B6 = new MediaSize(128, 182, 1000, MediaSizeName.JIS_B6);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 483 */     public static final MediaSize B7 = new MediaSize(91, 128, 1000, MediaSizeName.JIS_B7);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 488 */     public static final MediaSize B8 = new MediaSize(64, 91, 1000, MediaSizeName.JIS_B8);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 493 */     public static final MediaSize B9 = new MediaSize(45, 64, 1000, MediaSizeName.JIS_B9);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 498 */     public static final MediaSize B10 = new MediaSize(32, 45, 1000, MediaSizeName.JIS_B10);
/*     */ 
/*     */ 
/*     */     
/* 502 */     public static final MediaSize CHOU_1 = new MediaSize(142, 332, 1000);
/*     */ 
/*     */ 
/*     */     
/* 506 */     public static final MediaSize CHOU_2 = new MediaSize(119, 277, 1000);
/*     */ 
/*     */ 
/*     */     
/* 510 */     public static final MediaSize CHOU_3 = new MediaSize(120, 235, 1000);
/*     */ 
/*     */ 
/*     */     
/* 514 */     public static final MediaSize CHOU_4 = new MediaSize(90, 205, 1000);
/*     */ 
/*     */ 
/*     */     
/* 518 */     public static final MediaSize CHOU_30 = new MediaSize(92, 235, 1000);
/*     */ 
/*     */ 
/*     */     
/* 522 */     public static final MediaSize CHOU_40 = new MediaSize(90, 225, 1000);
/*     */ 
/*     */ 
/*     */     
/* 526 */     public static final MediaSize KAKU_0 = new MediaSize(287, 382, 1000);
/*     */ 
/*     */ 
/*     */     
/* 530 */     public static final MediaSize KAKU_1 = new MediaSize(270, 382, 1000);
/*     */ 
/*     */ 
/*     */     
/* 534 */     public static final MediaSize KAKU_2 = new MediaSize(240, 332, 1000);
/*     */ 
/*     */ 
/*     */     
/* 538 */     public static final MediaSize KAKU_3 = new MediaSize(216, 277, 1000);
/*     */ 
/*     */ 
/*     */     
/* 542 */     public static final MediaSize KAKU_4 = new MediaSize(197, 267, 1000);
/*     */ 
/*     */ 
/*     */     
/* 546 */     public static final MediaSize KAKU_5 = new MediaSize(190, 240, 1000);
/*     */ 
/*     */ 
/*     */     
/* 550 */     public static final MediaSize KAKU_6 = new MediaSize(162, 229, 1000);
/*     */ 
/*     */ 
/*     */     
/* 554 */     public static final MediaSize KAKU_7 = new MediaSize(142, 205, 1000);
/*     */ 
/*     */ 
/*     */     
/* 558 */     public static final MediaSize KAKU_8 = new MediaSize(119, 197, 1000);
/*     */ 
/*     */ 
/*     */     
/* 562 */     public static final MediaSize KAKU_20 = new MediaSize(229, 324, 1000);
/*     */ 
/*     */ 
/*     */     
/* 566 */     public static final MediaSize KAKU_A4 = new MediaSize(228, 312, 1000);
/*     */ 
/*     */ 
/*     */     
/* 570 */     public static final MediaSize YOU_1 = new MediaSize(120, 176, 1000);
/*     */ 
/*     */ 
/*     */     
/* 574 */     public static final MediaSize YOU_2 = new MediaSize(114, 162, 1000);
/*     */ 
/*     */ 
/*     */     
/* 578 */     public static final MediaSize YOU_3 = new MediaSize(98, 148, 1000);
/*     */ 
/*     */ 
/*     */     
/* 582 */     public static final MediaSize YOU_4 = new MediaSize(105, 235, 1000);
/*     */ 
/*     */ 
/*     */     
/* 586 */     public static final MediaSize YOU_5 = new MediaSize(95, 217, 1000);
/*     */ 
/*     */ 
/*     */     
/* 590 */     public static final MediaSize YOU_6 = new MediaSize(98, 190, 1000);
/*     */ 
/*     */ 
/*     */     
/* 594 */     public static final MediaSize YOU_7 = new MediaSize(92, 165, 1000);
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
/*     */   public static final class NA
/*     */   {
/* 612 */     public static final MediaSize LETTER = new MediaSize(8.5F, 11.0F, 25400, MediaSizeName.NA_LETTER);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 618 */     public static final MediaSize LEGAL = new MediaSize(8.5F, 14.0F, 25400, MediaSizeName.NA_LEGAL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 624 */     public static final MediaSize NA_5X7 = new MediaSize(5, 7, 25400, MediaSizeName.NA_5X7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 630 */     public static final MediaSize NA_8X10 = new MediaSize(8, 10, 25400, MediaSizeName.NA_8X10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 637 */     public static final MediaSize NA_NUMBER_9_ENVELOPE = new MediaSize(3.875F, 8.875F, 25400, MediaSizeName.NA_NUMBER_9_ENVELOPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 645 */     public static final MediaSize NA_NUMBER_10_ENVELOPE = new MediaSize(4.125F, 9.5F, 25400, MediaSizeName.NA_NUMBER_10_ENVELOPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 653 */     public static final MediaSize NA_NUMBER_11_ENVELOPE = new MediaSize(4.5F, 10.375F, 25400, MediaSizeName.NA_NUMBER_11_ENVELOPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 661 */     public static final MediaSize NA_NUMBER_12_ENVELOPE = new MediaSize(4.75F, 11.0F, 25400, MediaSizeName.NA_NUMBER_12_ENVELOPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 669 */     public static final MediaSize NA_NUMBER_14_ENVELOPE = new MediaSize(5.0F, 11.5F, 25400, MediaSizeName.NA_NUMBER_14_ENVELOPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 677 */     public static final MediaSize NA_6X9_ENVELOPE = new MediaSize(6.0F, 9.0F, 25400, MediaSizeName.NA_6X9_ENVELOPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 683 */     public static final MediaSize NA_7X9_ENVELOPE = new MediaSize(7.0F, 9.0F, 25400, MediaSizeName.NA_7X9_ENVELOPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 689 */     public static final MediaSize NA_9x11_ENVELOPE = new MediaSize(9.0F, 11.0F, 25400, MediaSizeName.NA_9X11_ENVELOPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 695 */     public static final MediaSize NA_9x12_ENVELOPE = new MediaSize(9.0F, 12.0F, 25400, MediaSizeName.NA_9X12_ENVELOPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 701 */     public static final MediaSize NA_10x13_ENVELOPE = new MediaSize(10.0F, 13.0F, 25400, MediaSizeName.NA_10X13_ENVELOPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 707 */     public static final MediaSize NA_10x14_ENVELOPE = new MediaSize(10.0F, 14.0F, 25400, MediaSizeName.NA_10X14_ENVELOPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 713 */     public static final MediaSize NA_10X15_ENVELOPE = new MediaSize(10.0F, 15.0F, 25400, MediaSizeName.NA_10X15_ENVELOPE);
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
/*     */   public static final class Engineering
/*     */   {
/* 732 */     public static final MediaSize A = new MediaSize(8.5F, 11.0F, 25400, MediaSizeName.A);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 738 */     public static final MediaSize B = new MediaSize(11.0F, 17.0F, 25400, MediaSizeName.B);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 744 */     public static final MediaSize C = new MediaSize(17.0F, 22.0F, 25400, MediaSizeName.C);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 750 */     public static final MediaSize D = new MediaSize(22.0F, 34.0F, 25400, MediaSizeName.D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 756 */     public static final MediaSize E = new MediaSize(34.0F, 44.0F, 25400, MediaSizeName.E);
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
/*     */   public static final class Other
/*     */   {
/* 774 */     public static final MediaSize EXECUTIVE = new MediaSize(7.25F, 10.5F, 25400, MediaSizeName.EXECUTIVE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 780 */     public static final MediaSize LEDGER = new MediaSize(11.0F, 17.0F, 25400, MediaSizeName.LEDGER);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 788 */     public static final MediaSize TABLOID = new MediaSize(11.0F, 17.0F, 25400, MediaSizeName.TABLOID);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 795 */     public static final MediaSize INVOICE = new MediaSize(5.5F, 8.5F, 25400, MediaSizeName.INVOICE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 801 */     public static final MediaSize FOLIO = new MediaSize(8.5F, 13.0F, 25400, MediaSizeName.FOLIO);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 807 */     public static final MediaSize QUARTO = new MediaSize(8.5F, 10.83F, 25400, MediaSizeName.QUARTO);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 813 */     public static final MediaSize ITALY_ENVELOPE = new MediaSize(110, 230, 1000, MediaSizeName.ITALY_ENVELOPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 819 */     public static final MediaSize MONARCH_ENVELOPE = new MediaSize(3.87F, 7.5F, 25400, MediaSizeName.MONARCH_ENVELOPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 825 */     public static final MediaSize PERSONAL_ENVELOPE = new MediaSize(3.625F, 6.5F, 25400, MediaSizeName.PERSONAL_ENVELOPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 831 */     public static final MediaSize JAPANESE_POSTCARD = new MediaSize(100, 148, 1000, MediaSizeName.JAPANESE_POSTCARD);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 837 */     public static final MediaSize JAPANESE_DOUBLE_POSTCARD = new MediaSize(148, 200, 1000, MediaSizeName.JAPANESE_DOUBLE_POSTCARD);
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
/*     */   static {
/* 850 */     MediaSize mediaSize1 = ISO.A4;
/* 851 */     MediaSize mediaSize2 = JIS.B5;
/* 852 */     MediaSize mediaSize3 = NA.LETTER;
/* 853 */     MediaSize mediaSize4 = Engineering.C;
/* 854 */     MediaSize mediaSize5 = Other.EXECUTIVE;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/standard/MediaSize.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */