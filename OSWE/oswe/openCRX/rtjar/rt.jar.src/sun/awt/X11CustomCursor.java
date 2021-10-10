/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class X11CustomCursor
/*     */   extends CustomCursor
/*     */ {
/*     */   public X11CustomCursor(Image paramImage, Point paramPoint, String paramString) throws IndexOutOfBoundsException {
/*  43 */     super(paramImage, paramPoint, paramString);
/*     */   }
/*     */   
/*     */   protected void createNativeCursor(Image paramImage, int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*     */     class CCount
/*     */       implements Comparable
/*     */     {
/*     */       int color;
/*     */       int count;
/*     */       
/*     */       public CCount(int param1Int1, int param1Int2) {
/*  54 */         this.color = param1Int1;
/*  55 */         this.count = param1Int2;
/*     */       }
/*     */       
/*     */       public int compareTo(Object param1Object) {
/*  59 */         return ((CCount)param1Object).count - this.count;
/*     */       }
/*     */     };
/*     */     
/*  63 */     int[] arrayOfInt = new int[paramArrayOfint.length]; int i;
/*  64 */     for (i = 0; i < paramArrayOfint.length; i++) {
/*  65 */       if ((paramArrayOfint[i] & 0xFF000000) == 0) {
/*  66 */         arrayOfInt[i] = -1;
/*     */       } else {
/*  68 */         arrayOfInt[i] = paramArrayOfint[i] & 0xFFFFFF;
/*     */       } 
/*     */     } 
/*  71 */     Arrays.sort(arrayOfInt);
/*     */     
/*  73 */     i = 0;
/*  74 */     int j = 16777215;
/*  75 */     CCount[] arrayOfCCount = new CCount[paramArrayOfint.length];
/*     */     
/*  77 */     byte b1 = 0;
/*  78 */     byte b2 = 0;
/*  79 */     while (b1 < paramArrayOfint.length) {
/*  80 */       if (arrayOfInt[b1] != -1) {
/*  81 */         arrayOfCCount[b2++] = new CCount(arrayOfInt[b1], 1);
/*     */         break;
/*     */       } 
/*  84 */       b1++;
/*     */     } 
/*     */     int k;
/*  87 */     for (k = b1 + 1; k < paramArrayOfint.length; k++) {
/*  88 */       if (arrayOfInt[k] != (arrayOfCCount[b2 - 1]).color) {
/*  89 */         arrayOfCCount[b2++] = new CCount(this, arrayOfInt[k], 1);
/*     */       } else {
/*  91 */         (arrayOfCCount[b2 - 1]).count++;
/*     */       } 
/*     */     } 
/*  94 */     Arrays.sort((Object[])arrayOfCCount, 0, b2);
/*     */     
/*  96 */     if (b2 > 0) i = (arrayOfCCount[0]).color; 
/*  97 */     k = i >> 16 & 0xFF;
/*  98 */     int m = i >> 8 & 0xFF;
/*  99 */     int n = i >> 0 & 0xFF;
/*     */     
/* 101 */     int i1 = 0;
/* 102 */     int i2 = 0;
/* 103 */     int i3 = 0; int i4;
/* 104 */     for (i4 = 1; i4 < b2; i4++) {
/* 105 */       int i10 = (arrayOfCCount[i4]).color >> 16 & 0xFF;
/* 106 */       int i11 = (arrayOfCCount[i4]).color >> 8 & 0xFF;
/* 107 */       int i12 = (arrayOfCCount[i4]).color >> 0 & 0xFF;
/* 108 */       i1 += (arrayOfCCount[i4]).count * i10;
/* 109 */       i2 += (arrayOfCCount[i4]).count * i11;
/* 110 */       i3 += (arrayOfCCount[i4]).count * i12;
/*     */     } 
/* 112 */     i4 = paramArrayOfint.length - ((b2 > 0) ? (arrayOfCCount[0]).count : 0);
/*     */     
/* 114 */     if (i4 > 0) {
/* 115 */       i1 = i1 / i4 - k;
/* 116 */       i2 = i2 / i4 - m;
/* 117 */       i3 = i3 / i4 - n;
/*     */     } 
/* 119 */     i1 = (i1 * i1 + i2 * i2 + i3 * i3) / 2;
/*     */     
/*     */     int i5;
/* 122 */     for (i5 = 1; i5 < b2; i5++) {
/* 123 */       int i10 = (arrayOfCCount[i5]).color >> 16 & 0xFF;
/* 124 */       int i11 = (arrayOfCCount[i5]).color >> 8 & 0xFF;
/* 125 */       int i12 = (arrayOfCCount[i5]).color >> 0 & 0xFF;
/*     */       
/* 127 */       if ((i10 - k) * (i10 - k) + (i11 - m) * (i11 - m) + (i12 - n) * (i12 - n) >= i1) {
/*     */         
/* 129 */         j = (arrayOfCCount[i5]).color;
/*     */         break;
/*     */       } 
/*     */     } 
/* 133 */     i5 = j >> 16 & 0xFF;
/* 134 */     int i6 = j >> 8 & 0xFF;
/* 135 */     int i7 = j >> 0 & 0xFF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     int i8 = (paramInt1 + 7) / 8;
/* 145 */     int i9 = i8 * paramInt2;
/* 146 */     byte[] arrayOfByte1 = new byte[i9];
/* 147 */     byte[] arrayOfByte2 = new byte[i9];
/*     */     
/* 149 */     for (byte b3 = 0; b3 < paramInt1; b3++) {
/* 150 */       int i10 = 1 << b3 % 8;
/* 151 */       for (byte b = 0; b < paramInt2; b++) {
/* 152 */         int i11 = b * paramInt1 + b3;
/* 153 */         int i12 = b * i8 + b3 / 8;
/*     */         
/* 155 */         if ((paramArrayOfint[i11] & 0xFF000000) != 0) {
/* 156 */           arrayOfByte2[i12] = (byte)(arrayOfByte2[i12] | i10);
/*     */         }
/*     */         
/* 159 */         int i13 = paramArrayOfint[i11] >> 16 & 0xFF;
/* 160 */         int i14 = paramArrayOfint[i11] >> 8 & 0xFF;
/* 161 */         int i15 = paramArrayOfint[i11] >> 0 & 0xFF;
/* 162 */         if ((i13 - k) * (i13 - k) + (i14 - m) * (i14 - m) + (i15 - n) * (i15 - n) <= (i13 - i5) * (i13 - i5) + (i14 - i6) * (i14 - i6) + (i15 - i7) * (i15 - i7))
/*     */         {
/*     */           
/* 165 */           arrayOfByte1[i12] = (byte)(arrayOfByte1[i12] | i10);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 170 */     createCursor(arrayOfByte1, arrayOfByte2, 8 * i8, paramInt2, i, j, paramInt3, paramInt4);
/*     */   }
/*     */   
/*     */   protected abstract void createCursor(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11CustomCursor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */