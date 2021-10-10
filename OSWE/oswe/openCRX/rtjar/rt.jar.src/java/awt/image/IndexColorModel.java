/*      */ package java.awt.image;
/*      */ 
/*      */ import java.awt.Point;
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.math.BigInteger;
/*      */ import java.util.Arrays;
/*      */ import sun.awt.image.BufImgSurfaceData;
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
/*      */ public class IndexColorModel
/*      */   extends ColorModel
/*      */ {
/*      */   private int[] rgb;
/*      */   private int map_size;
/*      */   private int pixel_mask;
/*  129 */   private int transparent_index = -1;
/*      */   
/*      */   private boolean allgrayopaque;
/*      */   private BigInteger validBits;
/*  133 */   private BufImgSurfaceData.ICMColorData colorData = null;
/*      */   
/*  135 */   private static int[] opaqueBits = new int[] { 8, 8, 8 };
/*  136 */   private static int[] alphaBits = new int[] { 8, 8, 8, 8 }; private static final int CACHESIZE = 40;
/*      */   private int[] lookupcache;
/*      */   
/*      */   static {
/*  140 */     ColorModel.loadLibraries();
/*  141 */     initIDs();
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
/*      */   public IndexColorModel(int paramInt1, int paramInt2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3)
/*      */   {
/*  168 */     super(paramInt1, opaqueBits, 
/*  169 */         ColorSpace.getInstance(1000), false, false, 1, 
/*      */         
/*  171 */         ColorModel.getDefaultTransferType(paramInt1));
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
/*  842 */     this.lookupcache = new int[40]; if (paramInt1 < 1 || paramInt1 > 16) throw new IllegalArgumentException("Number of bits must be between 1 and 16.");  setRGBs(paramInt2, paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3, (byte[])null); calculatePixelMask(); } public IndexColorModel(int paramInt1, int paramInt2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt3) { super(paramInt1, opaqueBits, ColorSpace.getInstance(1000), false, false, 1, ColorModel.getDefaultTransferType(paramInt1)); this.lookupcache = new int[40]; if (paramInt1 < 1 || paramInt1 > 16) throw new IllegalArgumentException("Number of bits must be between 1 and 16.");  setRGBs(paramInt2, paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3, (byte[])null); setTransparentPixel(paramInt3); calculatePixelMask(); } public IndexColorModel(int paramInt1, int paramInt2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4) { super(paramInt1, alphaBits, ColorSpace.getInstance(1000), true, false, 3, ColorModel.getDefaultTransferType(paramInt1)); this.lookupcache = new int[40]; if (paramInt1 < 1 || paramInt1 > 16) throw new IllegalArgumentException("Number of bits must be between 1 and 16.");  setRGBs(paramInt2, paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3, paramArrayOfbyte4); calculatePixelMask(); } public IndexColorModel(int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, boolean paramBoolean) { this(paramInt1, paramInt2, paramArrayOfbyte, paramInt3, paramBoolean, -1); if (paramInt1 < 1 || paramInt1 > 16) throw new IllegalArgumentException("Number of bits must be between 1 and 16.");  } public IndexColorModel(int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, boolean paramBoolean, int paramInt4) { super(paramInt1, opaqueBits, ColorSpace.getInstance(1000), false, false, 1, ColorModel.getDefaultTransferType(paramInt1)); this.lookupcache = new int[40]; if (paramInt1 < 1 || paramInt1 > 16) throw new IllegalArgumentException("Number of bits must be between 1 and 16.");  if (paramInt2 < 1) throw new IllegalArgumentException("Map size (" + paramInt2 + ") must be >= 1");  this.map_size = paramInt2; this.rgb = new int[calcRealMapSize(paramInt1, paramInt2)]; int i = paramInt3; int j = 255; boolean bool = true; byte b1 = 1; for (byte b2 = 0; b2 < paramInt2; b2++) { int k = paramArrayOfbyte[i++] & 0xFF; int m = paramArrayOfbyte[i++] & 0xFF; int n = paramArrayOfbyte[i++] & 0xFF; bool = (bool && k == m && m == n) ? true : false; if (paramBoolean) { j = paramArrayOfbyte[i++] & 0xFF; if (j != 255) { if (j == 0) { if (b1 == 1) b1 = 2;  if (this.transparent_index < 0) this.transparent_index = b2;  } else { b1 = 3; }  bool = false; }  }  this.rgb[b2] = j << 24 | k << 16 | m << 8 | n; }  this.allgrayopaque = bool; setTransparency(b1); setTransparentPixel(paramInt4); calculatePixelMask(); } public IndexColorModel(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3, boolean paramBoolean, int paramInt4, int paramInt5) { super(paramInt1, opaqueBits, ColorSpace.getInstance(1000), false, false, 1, paramInt5); this.lookupcache = new int[40]; if (paramInt1 < 1 || paramInt1 > 16) throw new IllegalArgumentException("Number of bits must be between 1 and 16.");  if (paramInt2 < 1) throw new IllegalArgumentException("Map size (" + paramInt2 + ") must be >= 1");  if (paramInt5 != 0 && paramInt5 != 1) throw new IllegalArgumentException("transferType must be eitherDataBuffer.TYPE_BYTE or DataBuffer.TYPE_USHORT");  setRGBs(paramInt2, paramArrayOfint, paramInt3, paramBoolean); setTransparentPixel(paramInt4); calculatePixelMask(); } public IndexColorModel(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3, int paramInt4, BigInteger paramBigInteger) { super(paramInt1, alphaBits, ColorSpace.getInstance(1000), true, false, 3, paramInt4); this.lookupcache = new int[40]; if (paramInt1 < 1 || paramInt1 > 16)
/*      */       throw new IllegalArgumentException("Number of bits must be between 1 and 16.");  if (paramInt2 < 1)
/*      */       throw new IllegalArgumentException("Map size (" + paramInt2 + ") must be >= 1");  if (paramInt4 != 0 && paramInt4 != 1)
/*      */       throw new IllegalArgumentException("transferType must be eitherDataBuffer.TYPE_BYTE or DataBuffer.TYPE_USHORT");  if (paramBigInteger != null)
/*      */       for (byte b = 0; b < paramInt2; b++) { if (!paramBigInteger.testBit(b)) { this.validBits = paramBigInteger; break; }  }
/*      */         setRGBs(paramInt2, paramArrayOfint, paramInt3, true); calculatePixelMask(); }
/*      */   private void setRGBs(int paramInt, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4) { if (paramInt < 1)
/*      */       throw new IllegalArgumentException("Map size (" + paramInt + ") must be >= 1");  this.map_size = paramInt; this.rgb = new int[calcRealMapSize(this.pixel_bits, paramInt)]; int i = 255; byte b1 = 1; boolean bool = true; for (byte b2 = 0; b2 < paramInt; b2++) { int j = paramArrayOfbyte1[b2] & 0xFF; int k = paramArrayOfbyte2[b2] & 0xFF; int m = paramArrayOfbyte3[b2] & 0xFF; bool = (bool && j == k && k == m) ? true : false; if (paramArrayOfbyte4 != null) { i = paramArrayOfbyte4[b2] & 0xFF; if (i != 255) { if (i == 0) { if (b1 == 1)
/*      */               b1 = 2;  if (this.transparent_index < 0)
/*      */               this.transparent_index = b2;  }
/*      */           else { b1 = 3; }
/*      */            bool = false; }
/*      */          }
/*      */        this.rgb[b2] = i << 24 | j << 16 | k << 8 | m; }
/*      */      this.allgrayopaque = bool; setTransparency(b1); }
/*      */   private void setRGBs(int paramInt1, int[] paramArrayOfint, int paramInt2, boolean paramBoolean) { this.map_size = paramInt1; this.rgb = new int[calcRealMapSize(this.pixel_bits, paramInt1)]; int i = paramInt2; byte b1 = 1; boolean bool = true; BigInteger bigInteger = this.validBits; for (byte b2 = 0; b2 < paramInt1; b2++, i++) { if (bigInteger == null || bigInteger.testBit(b2)) { int j = paramArrayOfint[i]; int k = j >> 16 & 0xFF; int m = j >> 8 & 0xFF; int n = j & 0xFF; bool = (bool && k == m && m == n) ? true : false; if (paramBoolean) { int i1 = j >>> 24; if (i1 != 255) { if (i1 == 0) { if (b1 == 1)
/*      */                 b1 = 2;  if (this.transparent_index < 0)
/*      */                 this.transparent_index = b2;  }
/*      */             else { b1 = 3; }
/*      */              bool = false; }
/*      */            }
/*      */         else { j |= 0xFF000000; }
/*      */          this.rgb[b2] = j; }
/*      */        }
/*      */      this.allgrayopaque = bool; setTransparency(b1); }
/*      */   private int calcRealMapSize(int paramInt1, int paramInt2) { int i = Math.max(1 << paramInt1, paramInt2); return Math.max(i, 256); }
/*      */   private BigInteger getAllValid() { int i = (this.map_size + 7) / 8; byte[] arrayOfByte = new byte[i]; Arrays.fill(arrayOfByte, (byte)-1); arrayOfByte[0] = (byte)(255 >>> i * 8 - this.map_size); return new BigInteger(1, arrayOfByte); }
/*      */   public int getTransparency() { return this.transparency; }
/*      */   public int[] getComponentSize() { if (this.nBits == null) { if (this.supportsAlpha) { this.nBits = new int[4]; this.nBits[3] = 8; }
/*      */       else { this.nBits = new int[3]; }
/*      */        this.nBits[2] = 8; this.nBits[1] = 8; this.nBits[0] = 8; }
/*      */      return (int[])this.nBits.clone(); }
/*      */   public final int getMapSize() { return this.map_size; }
/*      */   public final int getTransparentPixel() { return this.transparent_index; }
/*      */   public final void getReds(byte[] paramArrayOfbyte) { for (byte b = 0; b < this.map_size; b++)
/*      */       paramArrayOfbyte[b] = (byte)(this.rgb[b] >> 16);  }
/*      */   public final void getGreens(byte[] paramArrayOfbyte) { for (byte b = 0; b < this.map_size; b++)
/*  879 */       paramArrayOfbyte[b] = (byte)(this.rgb[b] >> 8);  } public synchronized Object getDataElements(int paramInt, Object paramObject) { int i = paramInt >> 16 & 0xFF;
/*  880 */     int j = paramInt >> 8 & 0xFF;
/*  881 */     int k = paramInt & 0xFF;
/*  882 */     int m = paramInt >>> 24;
/*  883 */     int n = 0;
/*      */ 
/*      */ 
/*      */     
/*      */     int i1;
/*      */ 
/*      */     
/*  890 */     for (i1 = 38; i1 >= 0 && (
/*  891 */       n = this.lookupcache[i1]) != 0; i1 -= 2) {
/*      */ 
/*      */       
/*  894 */       if (paramInt == this.lookupcache[i1 + 1]) {
/*  895 */         return installpixel(paramObject, n ^ 0xFFFFFFFF);
/*      */       }
/*      */     } 
/*      */     
/*  899 */     if (this.allgrayopaque) {
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
/*  910 */       i1 = 256;
/*      */       
/*  912 */       int i2 = (i * 77 + j * 150 + k * 29 + 128) / 256;
/*      */       
/*  914 */       for (byte b = 0; b < this.map_size; b++) {
/*  915 */         if (this.rgb[b] != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  921 */           int i3 = (this.rgb[b] & 0xFF) - i2;
/*  922 */           if (i3 < 0) i3 = -i3; 
/*  923 */           if (i3 < i1)
/*  924 */           { n = b;
/*  925 */             if (i3 == 0) {
/*      */               break;
/*      */             }
/*  928 */             i1 = i3; } 
/*      */         } 
/*      */       } 
/*  931 */     } else if (this.transparency == 1) {
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
/*  945 */       i1 = Integer.MAX_VALUE;
/*  946 */       int[] arrayOfInt = this.rgb;
/*      */       byte b;
/*  948 */       for (b = 0; b < this.map_size; b++) {
/*  949 */         int i2 = arrayOfInt[b];
/*  950 */         if (i2 == paramInt && i2 != 0) {
/*  951 */           n = b;
/*  952 */           i1 = 0;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  957 */       if (i1 != 0)
/*  958 */         for (b = 0; b < this.map_size; b++) {
/*  959 */           int i2 = arrayOfInt[b];
/*  960 */           if (i2 != 0) {
/*      */ 
/*      */ 
/*      */             
/*  964 */             int i3 = (i2 >> 16 & 0xFF) - i;
/*  965 */             int i4 = i3 * i3;
/*  966 */             if (i4 < i1) {
/*  967 */               i3 = (i2 >> 8 & 0xFF) - j;
/*  968 */               i4 += i3 * i3;
/*  969 */               if (i4 < i1) {
/*  970 */                 i3 = (i2 & 0xFF) - k;
/*  971 */                 i4 += i3 * i3;
/*  972 */                 if (i4 < i1) {
/*  973 */                   n = b;
/*  974 */                   i1 = i4;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }  
/*  980 */     } else if (m == 0 && this.transparent_index >= 0) {
/*      */ 
/*      */ 
/*      */       
/*  984 */       n = this.transparent_index;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/*  992 */       i1 = Integer.MAX_VALUE;
/*  993 */       int[] arrayOfInt = this.rgb;
/*  994 */       for (byte b = 0; b < this.map_size; b++) {
/*  995 */         int i2 = arrayOfInt[b];
/*  996 */         if (i2 == paramInt) {
/*  997 */           if (this.validBits == null || this.validBits.testBit(b)) {
/*      */ 
/*      */             
/* 1000 */             n = b;
/*      */             break;
/*      */           } 
/*      */         } else {
/* 1004 */           int i3 = (i2 >> 16 & 0xFF) - i;
/* 1005 */           int i4 = i3 * i3;
/* 1006 */           if (i4 < i1) {
/* 1007 */             i3 = (i2 >> 8 & 0xFF) - j;
/* 1008 */             i4 += i3 * i3;
/* 1009 */             if (i4 < i1) {
/* 1010 */               i3 = (i2 & 0xFF) - k;
/* 1011 */               i4 += i3 * i3;
/* 1012 */               if (i4 < i1) {
/* 1013 */                 i3 = (i2 >>> 24) - m;
/* 1014 */                 i4 += i3 * i3;
/* 1015 */                 if (i4 < i1 && (this.validBits == null || this.validBits
/* 1016 */                   .testBit(b))) {
/*      */                   
/* 1018 */                   n = b;
/* 1019 */                   i1 = i4;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1026 */     }  System.arraycopy(this.lookupcache, 2, this.lookupcache, 0, 38);
/* 1027 */     this.lookupcache[39] = paramInt;
/* 1028 */     this.lookupcache[38] = n ^ 0xFFFFFFFF;
/* 1029 */     return installpixel(paramObject, n); }
/*      */   public final void getBlues(byte[] paramArrayOfbyte) { for (byte b = 0; b < this.map_size; b++) paramArrayOfbyte[b] = (byte)this.rgb[b];  }
/*      */   public final void getAlphas(byte[] paramArrayOfbyte) { for (byte b = 0; b < this.map_size; b++) paramArrayOfbyte[b] = (byte)(this.rgb[b] >> 24);  }
/*      */   public final void getRGBs(int[] paramArrayOfint) { System.arraycopy(this.rgb, 0, paramArrayOfint, 0, this.map_size); }
/* 1033 */   private void setTransparentPixel(int paramInt) { if (paramInt >= 0 && paramInt < this.map_size) { this.rgb[paramInt] = this.rgb[paramInt] & 0xFFFFFF; this.transparent_index = paramInt; this.allgrayopaque = false; if (this.transparency == 1) setTransparency(2);  }  } private void setTransparency(int paramInt) { if (this.transparency != paramInt) { this.transparency = paramInt; if (paramInt == 1) { this.supportsAlpha = false; this.numComponents = 3; this.nBits = opaqueBits; } else { this.supportsAlpha = true; this.numComponents = 4; this.nBits = alphaBits; }  }  } private final void calculatePixelMask() { int i = this.pixel_bits; if (i == 3) { i = 4; } else if (i > 4 && i < 8) { i = 8; }  this.pixel_mask = (1 << i) - 1; } public final int getRed(int paramInt) { return this.rgb[paramInt & this.pixel_mask] >> 16 & 0xFF; } public final int getGreen(int paramInt) { return this.rgb[paramInt & this.pixel_mask] >> 8 & 0xFF; } public final int getBlue(int paramInt) { return this.rgb[paramInt & this.pixel_mask] & 0xFF; } public final int getAlpha(int paramInt) { return this.rgb[paramInt & this.pixel_mask] >> 24 & 0xFF; } public final int getRGB(int paramInt) { return this.rgb[paramInt & this.pixel_mask]; } private Object installpixel(Object paramObject, int paramInt) { int[] arrayOfInt; byte[] arrayOfByte; short[] arrayOfShort; switch (this.transferType) {
/*      */       
/*      */       case 3:
/* 1036 */         if (paramObject == null) {
/* 1037 */           paramObject = arrayOfInt = new int[1];
/*      */         } else {
/* 1039 */           arrayOfInt = (int[])paramObject;
/*      */         } 
/* 1041 */         arrayOfInt[0] = paramInt;
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
/* 1065 */         return paramObject;case 0: if (paramObject == null) { paramObject = arrayOfByte = new byte[1]; } else { arrayOfByte = (byte[])paramObject; }  arrayOfByte[0] = (byte)paramInt; return paramObject;case 1: if (paramObject == null) { paramObject = arrayOfShort = new short[1]; } else { arrayOfShort = (short[])paramObject; }  arrayOfShort[0] = (short)paramInt; return paramObject;
/*      */     } 
/*      */     throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType); }
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
/*      */   public int[] getComponents(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 1094 */     if (paramArrayOfint == null) {
/* 1095 */       paramArrayOfint = new int[paramInt2 + this.numComponents];
/*      */     }
/*      */ 
/*      */     
/* 1099 */     paramArrayOfint[paramInt2 + 0] = getRed(paramInt1);
/* 1100 */     paramArrayOfint[paramInt2 + 1] = getGreen(paramInt1);
/* 1101 */     paramArrayOfint[paramInt2 + 2] = getBlue(paramInt1);
/* 1102 */     if (this.supportsAlpha && paramArrayOfint.length - paramInt2 > 3) {
/* 1103 */       paramArrayOfint[paramInt2 + 3] = getAlpha(paramInt1);
/*      */     }
/*      */     
/* 1106 */     return paramArrayOfint;
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
/*      */   public int[] getComponents(Object paramObject, int[] paramArrayOfint, int paramInt) {
/*      */     int i;
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/*      */     int[] arrayOfInt;
/* 1161 */     switch (this.transferType) {
/*      */       case 0:
/* 1163 */         arrayOfByte = (byte[])paramObject;
/* 1164 */         i = arrayOfByte[0] & 0xFF;
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
/* 1178 */         return getComponents(i, paramArrayOfint, paramInt);case 1: arrayOfShort = (short[])paramObject; i = arrayOfShort[0] & 0xFFFF; return getComponents(i, paramArrayOfint, paramInt);case 3: arrayOfInt = (int[])paramObject; i = arrayOfInt[0]; return getComponents(i, paramArrayOfint, paramInt);
/*      */     } 
/*      */     throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
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
/*      */   public int getDataElement(int[] paramArrayOfint, int paramInt) {
/*      */     int j;
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/* 1206 */     int arrayOfInt[], i = paramArrayOfint[paramInt + 0] << 16 | paramArrayOfint[paramInt + 1] << 8 | paramArrayOfint[paramInt + 2];
/*      */     
/* 1208 */     if (this.supportsAlpha) {
/* 1209 */       i |= paramArrayOfint[paramInt + 3] << 24;
/*      */     } else {
/*      */       
/* 1212 */       i |= 0xFF000000;
/*      */     } 
/* 1214 */     Object object = getDataElements(i, (Object)null);
/*      */     
/* 1216 */     switch (this.transferType) {
/*      */       case 0:
/* 1218 */         arrayOfByte = (byte[])object;
/* 1219 */         j = arrayOfByte[0] & 0xFF;
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
/* 1233 */         return j;case 1: arrayOfShort = (short[])object; j = arrayOfShort[0]; return j;case 3: arrayOfInt = (int[])object; j = arrayOfInt[0]; return j;
/*      */     } 
/*      */     throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
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
/*      */   public Object getDataElements(int[] paramArrayOfint, int paramInt, Object paramObject) {
/* 1279 */     int i = paramArrayOfint[paramInt + 0] << 16 | paramArrayOfint[paramInt + 1] << 8 | paramArrayOfint[paramInt + 2];
/*      */     
/* 1281 */     if (this.supportsAlpha) {
/* 1282 */       i |= paramArrayOfint[paramInt + 3] << 24;
/*      */     } else {
/*      */       
/* 1285 */       i &= 0xFF000000;
/*      */     } 
/* 1287 */     return getDataElements(i, paramObject);
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
/*      */   public WritableRaster createCompatibleWritableRaster(int paramInt1, int paramInt2) {
/*      */     WritableRaster writableRaster;
/* 1312 */     if (this.pixel_bits == 1 || this.pixel_bits == 2 || this.pixel_bits == 4) {
/*      */       
/* 1314 */       writableRaster = Raster.createPackedRaster(0, paramInt1, paramInt2, 1, this.pixel_bits, (Point)null);
/*      */     
/*      */     }
/* 1317 */     else if (this.pixel_bits <= 8) {
/* 1318 */       writableRaster = Raster.createInterleavedRaster(0, paramInt1, paramInt2, 1, null);
/*      */     
/*      */     }
/* 1321 */     else if (this.pixel_bits <= 16) {
/* 1322 */       writableRaster = Raster.createInterleavedRaster(1, paramInt1, paramInt2, 1, null);
/*      */     }
/*      */     else {
/*      */       
/* 1326 */       throw new UnsupportedOperationException("This method is not supported  for pixel bits > 16.");
/*      */     } 
/*      */ 
/*      */     
/* 1330 */     return writableRaster;
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
/*      */   public boolean isCompatibleRaster(Raster paramRaster) {
/* 1344 */     int i = paramRaster.getSampleModel().getSampleSize(0);
/* 1345 */     return (paramRaster.getTransferType() == this.transferType && paramRaster
/* 1346 */       .getNumBands() == 1 && 1 << i >= this.map_size);
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
/*      */   public SampleModel createCompatibleSampleModel(int paramInt1, int paramInt2) {
/* 1362 */     int[] arrayOfInt = new int[1];
/* 1363 */     arrayOfInt[0] = 0;
/* 1364 */     if (this.pixel_bits == 1 || this.pixel_bits == 2 || this.pixel_bits == 4) {
/* 1365 */       return new MultiPixelPackedSampleModel(this.transferType, paramInt1, paramInt2, this.pixel_bits);
/*      */     }
/*      */ 
/*      */     
/* 1369 */     return new ComponentSampleModel(this.transferType, paramInt1, paramInt2, 1, paramInt1, arrayOfInt);
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
/*      */   public boolean isCompatibleSampleModel(SampleModel paramSampleModel) {
/* 1387 */     if (!(paramSampleModel instanceof ComponentSampleModel) && !(paramSampleModel instanceof MultiPixelPackedSampleModel))
/*      */     {
/* 1389 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1393 */     if (paramSampleModel.getTransferType() != this.transferType) {
/* 1394 */       return false;
/*      */     }
/*      */     
/* 1397 */     if (paramSampleModel.getNumBands() != 1) {
/* 1398 */       return false;
/*      */     }
/*      */     
/* 1401 */     return true;
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
/*      */   public BufferedImage convertToIntDiscrete(Raster paramRaster, boolean paramBoolean) {
/*      */     ColorModel colorModel;
/* 1429 */     if (!isCompatibleRaster(paramRaster)) {
/* 1430 */       throw new IllegalArgumentException("This raster is not compatiblewith this IndexColorModel.");
/*      */     }
/*      */     
/* 1433 */     if (paramBoolean || this.transparency == 3) {
/* 1434 */       colorModel = ColorModel.getRGBdefault();
/*      */     }
/* 1436 */     else if (this.transparency == 2) {
/* 1437 */       colorModel = new DirectColorModel(25, 16711680, 65280, 255, 16777216);
/*      */     }
/*      */     else {
/*      */       
/* 1441 */       colorModel = new DirectColorModel(24, 16711680, 65280, 255);
/*      */     } 
/*      */     
/* 1444 */     int i = paramRaster.getWidth();
/* 1445 */     int j = paramRaster.getHeight();
/*      */     
/* 1447 */     WritableRaster writableRaster = colorModel.createCompatibleWritableRaster(i, j);
/* 1448 */     Object object = null;
/* 1449 */     int[] arrayOfInt = null;
/*      */     
/* 1451 */     int k = paramRaster.getMinX();
/* 1452 */     int m = paramRaster.getMinY();
/*      */     
/* 1454 */     for (byte b = 0; b < j; b++, m++) {
/* 1455 */       object = paramRaster.getDataElements(k, m, i, 1, object);
/* 1456 */       if (object instanceof int[]) {
/* 1457 */         arrayOfInt = (int[])object;
/*      */       } else {
/* 1459 */         arrayOfInt = DataBuffer.toIntArray(object);
/*      */       } 
/* 1461 */       for (byte b1 = 0; b1 < i; b1++) {
/* 1462 */         arrayOfInt[b1] = this.rgb[arrayOfInt[b1] & this.pixel_mask];
/*      */       }
/* 1464 */       writableRaster.setDataElements(0, b, i, 1, arrayOfInt);
/*      */     } 
/*      */     
/* 1467 */     return new BufferedImage(colorModel, writableRaster, false, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValid(int paramInt) {
/* 1478 */     return (paramInt >= 0 && paramInt < this.map_size && (this.validBits == null || this.validBits
/* 1479 */       .testBit(paramInt)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValid() {
/* 1489 */     return (this.validBits == null);
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
/*      */   public BigInteger getValidPixels() {
/* 1503 */     if (this.validBits == null) {
/* 1504 */       return getAllValid();
/*      */     }
/*      */     
/* 1507 */     return this.validBits;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void finalize() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1526 */     return new String("IndexColorModel: #pixelBits = " + this.pixel_bits + " numComponents = " + this.numComponents + " color space = " + this.colorSpace + " transparency = " + this.transparency + " transIndex   = " + this.transparent_index + " has alpha = " + this.supportsAlpha + " isAlphaPre = " + this.isAlphaPremultiplied);
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/IndexColorModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */