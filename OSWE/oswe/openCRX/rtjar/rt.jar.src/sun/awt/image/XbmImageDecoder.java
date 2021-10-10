/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XbmImageDecoder
/*     */   extends ImageDecoder
/*     */ {
/*  47 */   private static byte[] XbmColormap = new byte[] { -1, -1, -1, 0, 0, 0 };
/*     */   
/*  49 */   private static int XbmHints = 30;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XbmImageDecoder(InputStreamImageSource paramInputStreamImageSource, InputStream paramInputStream) {
/*  55 */     super(paramInputStreamImageSource, paramInputStream);
/*  56 */     if (!(this.input instanceof BufferedInputStream))
/*     */     {
/*     */       
/*  59 */       this.input = new BufferedInputStream(this.input, 80);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void error(String paramString) throws ImageFormatException {
/*  68 */     throw new ImageFormatException(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void produceImage() throws IOException, ImageFormatException {
/*  75 */     char[] arrayOfChar = new char[80];
/*     */     
/*  77 */     byte b1 = 0;
/*  78 */     byte b = 0;
/*  79 */     int j = 0;
/*  80 */     int k = 0;
/*  81 */     byte b2 = 0;
/*  82 */     byte b3 = 0;
/*  83 */     boolean bool = true;
/*  84 */     byte[] arrayOfByte = null;
/*  85 */     ColorModel colorModel = null; int i;
/*  86 */     while (!this.aborted && (i = this.input.read()) != -1) {
/*  87 */       if ((97 <= i && i <= 122) || (65 <= i && i <= 90) || (48 <= i && i <= 57) || i == 35 || i == 95) {
/*     */ 
/*     */         
/*  90 */         if (b1 < 78)
/*  91 */           arrayOfChar[b1++] = (char)i;  continue;
/*  92 */       }  if (b1 > 0) {
/*  93 */         byte b4 = b1;
/*  94 */         b1 = 0;
/*  95 */         if (bool) {
/*  96 */           if (b4 != 7 || arrayOfChar[0] != '#' || arrayOfChar[1] != 'd' || arrayOfChar[2] != 'e' || arrayOfChar[3] != 'f' || arrayOfChar[4] != 'i' || arrayOfChar[5] != 'n' || arrayOfChar[6] != 'e')
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 105 */             error("Not an XBM file");
/*     */           }
/* 107 */           bool = false;
/*     */         } 
/* 109 */         if (arrayOfChar[b4 - 1] == 'h') {
/* 110 */           b = 1; continue;
/* 111 */         }  if (arrayOfChar[b4 - 1] == 't' && b4 > 1 && arrayOfChar[b4 - 2] == 'h') {
/* 112 */           b = 2; continue;
/* 113 */         }  if (b4 > 2 && b < 0 && arrayOfChar[0] == '0' && arrayOfChar[1] == 'x') {
/* 114 */           int n = 0; int i1;
/* 115 */           for (i1 = 2; i1 < b4; i1++) {
/* 116 */             i = arrayOfChar[i1];
/* 117 */             if (48 <= i && i <= 57) {
/* 118 */               i -= 48;
/* 119 */             } else if (65 <= i && i <= 90) {
/* 120 */               i = i - 65 + 10;
/* 121 */             } else if (97 <= i && i <= 122) {
/* 122 */               i = i - 97 + 10;
/*     */             } else {
/* 124 */               i = 0;
/* 125 */             }  n = n * 16 + i;
/*     */           } 
/* 127 */           for (i1 = 1; i1 <= 128; i1 <<= 1) {
/* 128 */             if (b2 < k)
/* 129 */               if ((n & i1) != 0) {
/* 130 */                 arrayOfByte[b2] = true;
/*     */               } else {
/* 132 */                 arrayOfByte[b2] = false;
/*     */               }  
/* 134 */             b2++;
/*     */           } 
/* 136 */           if (b2 >= k) {
/* 137 */             if (setPixels(0, b3, k, 1, colorModel, arrayOfByte, 0, k) <= 0) {
/*     */               return;
/*     */             }
/* 140 */             b2 = 0;
/* 141 */             if (b3++ >= j)
/*     */               break; 
/*     */           } 
/*     */           continue;
/*     */         } 
/* 146 */         int m = 0;
/* 147 */         for (byte b5 = 0; b5 < b4; b5++) {
/* 148 */           if (48 <= (i = arrayOfChar[b5]) && i <= 57) {
/* 149 */             m = m * 10 + i - 48;
/*     */           } else {
/* 151 */             m = -1; break;
/*     */           } 
/*     */         } 
/* 154 */         if (m > 0 && b > 0) {
/* 155 */           if (b == 1) {
/* 156 */             k = m;
/*     */           } else {
/* 158 */             j = m;
/* 159 */           }  if (k == 0 || j == 0) {
/* 160 */             b = 0; continue;
/*     */           } 
/* 162 */           colorModel = new IndexColorModel(8, 2, XbmColormap, 0, false, 0);
/*     */           
/* 164 */           setDimensions(k, j);
/* 165 */           setColorModel(colorModel);
/* 166 */           setHints(XbmHints);
/* 167 */           headerComplete();
/* 168 */           arrayOfByte = new byte[k];
/* 169 */           b = -1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 175 */     this.input.close();
/* 176 */     imageComplete(3, true);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/XbmImageDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */