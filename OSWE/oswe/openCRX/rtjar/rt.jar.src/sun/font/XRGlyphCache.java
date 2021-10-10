/*     */ package sun.font;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.java2d.xr.GrowableIntArray;
/*     */ import sun.java2d.xr.MutableInteger;
/*     */ import sun.java2d.xr.XRBackend;
/*     */ import sun.java2d.xr.XRCompositeManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XRGlyphCache
/*     */   implements GlyphDisposedListener
/*     */ {
/*     */   XRBackend con;
/*     */   XRCompositeManager maskBuffer;
/*  43 */   HashMap<MutableInteger, XRGlyphCacheEntry> cacheMap = new HashMap<>(256);
/*     */   
/*  45 */   int nextID = 1;
/*  46 */   MutableInteger tmp = new MutableInteger(0);
/*     */   
/*     */   int grayGlyphSet;
/*     */   
/*     */   int lcdGlyphSet;
/*  51 */   int time = 0;
/*  52 */   int cachedPixels = 0;
/*     */   
/*     */   static final int MAX_CACHED_PIXELS = 100000;
/*  55 */   ArrayList<Integer> freeGlyphIDs = new ArrayList<>(255);
/*     */   
/*     */   static final boolean batchGlyphUpload = true;
/*     */   
/*     */   public XRGlyphCache(XRCompositeManager paramXRCompositeManager) {
/*  60 */     this.con = paramXRCompositeManager.getBackend();
/*  61 */     this.maskBuffer = paramXRCompositeManager;
/*     */     
/*  63 */     this.grayGlyphSet = this.con.XRenderCreateGlyphSet(2);
/*  64 */     this.lcdGlyphSet = this.con.XRenderCreateGlyphSet(0);
/*     */     
/*  66 */     StrikeCache.addGlyphDisposedListener(this);
/*     */   }
/*     */   
/*     */   public void glyphDisposed(ArrayList<Long> paramArrayList) {
/*     */     try {
/*  71 */       SunToolkit.awtLock();
/*     */       
/*  73 */       GrowableIntArray growableIntArray = new GrowableIntArray(1, paramArrayList.size());
/*  74 */       for (Iterator<Long> iterator = paramArrayList.iterator(); iterator.hasNext(); ) { long l = ((Long)iterator.next()).longValue();
/*  75 */         int i = XRGlyphCacheEntry.getGlyphID(l);
/*     */ 
/*     */         
/*  78 */         if (i != 0) {
/*  79 */           growableIntArray.addInt(i);
/*     */         } }
/*     */       
/*  82 */       freeGlyphs(growableIntArray);
/*     */     } finally {
/*  84 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected int getFreeGlyphID() {
/*  89 */     if (this.freeGlyphIDs.size() > 0) {
/*  90 */       return ((Integer)this.freeGlyphIDs.remove(this.freeGlyphIDs.size() - 1)).intValue();
/*     */     }
/*     */     
/*  93 */     return this.nextID++;
/*     */   }
/*     */   
/*     */   protected XRGlyphCacheEntry getEntryForPointer(long paramLong) {
/*  97 */     int i = XRGlyphCacheEntry.getGlyphID(paramLong);
/*     */     
/*  99 */     if (i == 0) {
/* 100 */       return null;
/*     */     }
/*     */     
/* 103 */     this.tmp.setValue(i);
/* 104 */     return this.cacheMap.get(this.tmp);
/*     */   }
/*     */   
/*     */   public XRGlyphCacheEntry[] cacheGlyphs(GlyphList paramGlyphList) {
/* 108 */     this.time++;
/*     */     
/* 110 */     XRGlyphCacheEntry[] arrayOfXRGlyphCacheEntry = new XRGlyphCacheEntry[paramGlyphList.getNumGlyphs()];
/* 111 */     long[] arrayOfLong = paramGlyphList.getImages();
/* 112 */     ArrayList<XRGlyphCacheEntry> arrayList = null;
/*     */     
/* 114 */     for (byte b = 0; b < paramGlyphList.getNumGlyphs(); b++) {
/*     */ 
/*     */       
/* 117 */       if (arrayOfLong[b] != 0L) {
/*     */         XRGlyphCacheEntry xRGlyphCacheEntry;
/*     */ 
/*     */         
/* 121 */         if ((xRGlyphCacheEntry = getEntryForPointer(arrayOfLong[b])) == null) {
/* 122 */           xRGlyphCacheEntry = new XRGlyphCacheEntry(arrayOfLong[b], paramGlyphList);
/* 123 */           xRGlyphCacheEntry.setGlyphID(getFreeGlyphID());
/* 124 */           this.cacheMap.put(new MutableInteger(xRGlyphCacheEntry.getGlyphID()), xRGlyphCacheEntry);
/*     */           
/* 126 */           if (arrayList == null) {
/* 127 */             arrayList = new ArrayList();
/*     */           }
/* 129 */           arrayList.add(xRGlyphCacheEntry);
/*     */         } 
/* 131 */         xRGlyphCacheEntry.setLastUsed(this.time);
/* 132 */         arrayOfXRGlyphCacheEntry[b] = xRGlyphCacheEntry;
/*     */       } 
/*     */     } 
/*     */     
/* 136 */     if (arrayList != null) {
/* 137 */       uploadGlyphs(arrayOfXRGlyphCacheEntry, arrayList, paramGlyphList, null);
/*     */     }
/*     */     
/* 140 */     return arrayOfXRGlyphCacheEntry;
/*     */   }
/*     */   
/*     */   protected void uploadGlyphs(XRGlyphCacheEntry[] paramArrayOfXRGlyphCacheEntry, ArrayList<XRGlyphCacheEntry> paramArrayList, GlyphList paramGlyphList, int[] paramArrayOfint) {
/* 144 */     for (XRGlyphCacheEntry xRGlyphCacheEntry : paramArrayList) {
/* 145 */       this.cachedPixels += xRGlyphCacheEntry.getPixelCnt();
/*     */     }
/*     */     
/* 148 */     if (this.cachedPixels > 100000) {
/* 149 */       clearCache(paramArrayOfXRGlyphCacheEntry);
/*     */     }
/*     */     
/* 152 */     boolean bool = containsLCDGlyphs(paramArrayList);
/* 153 */     List[] arrayOfList = (List[])seperateGlyphTypes(paramArrayList, bool);
/* 154 */     List<XRGlyphCacheEntry> list1 = arrayOfList[0];
/* 155 */     List<XRGlyphCacheEntry> list2 = arrayOfList[1];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     if (list1 != null && list1.size() > 0) {
/* 164 */       this.con.XRenderAddGlyphs(this.grayGlyphSet, paramGlyphList, list1, generateGlyphImageStream(list1));
/*     */     }
/* 166 */     if (list2 != null && list2.size() > 0) {
/* 167 */       this.con.XRenderAddGlyphs(this.lcdGlyphSet, paramGlyphList, list2, generateGlyphImageStream(list2));
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
/*     */   protected List<XRGlyphCacheEntry>[] seperateGlyphTypes(List<XRGlyphCacheEntry> paramList, boolean paramBoolean) {
/* 190 */     ArrayList<XRGlyphCacheEntry> arrayList1 = null;
/* 191 */     ArrayList<XRGlyphCacheEntry> arrayList2 = null;
/*     */     
/* 193 */     for (XRGlyphCacheEntry xRGlyphCacheEntry : paramList) {
/* 194 */       if (xRGlyphCacheEntry.isGrayscale(paramBoolean)) {
/* 195 */         if (arrayList2 == null) {
/* 196 */           arrayList2 = new ArrayList(paramList.size());
/*     */         }
/* 198 */         xRGlyphCacheEntry.setGlyphSet(this.grayGlyphSet);
/* 199 */         arrayList2.add(xRGlyphCacheEntry); continue;
/*     */       } 
/* 201 */       if (arrayList1 == null) {
/* 202 */         arrayList1 = new ArrayList(paramList.size());
/*     */       }
/* 204 */       xRGlyphCacheEntry.setGlyphSet(this.lcdGlyphSet);
/* 205 */       arrayList1.add(xRGlyphCacheEntry);
/*     */     } 
/*     */ 
/*     */     
/* 209 */     return (List<XRGlyphCacheEntry>[])new List[] { arrayList2, arrayList1 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] generateGlyphImageStream(List<XRGlyphCacheEntry> paramList) {
/* 216 */     boolean bool = (((XRGlyphCacheEntry)paramList.get(0)).getGlyphSet() == this.lcdGlyphSet) ? true : false;
/*     */     
/* 218 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((bool ? 4 : 1) * 48 * paramList.size());
/* 219 */     for (XRGlyphCacheEntry xRGlyphCacheEntry : paramList) {
/* 220 */       xRGlyphCacheEntry.writePixelData(byteArrayOutputStream, bool);
/*     */     }
/*     */     
/* 223 */     return byteArrayOutputStream.toByteArray();
/*     */   }
/*     */   
/*     */   protected boolean containsLCDGlyphs(List<XRGlyphCacheEntry> paramList) {
/* 227 */     boolean bool = false;
/*     */     
/* 229 */     for (XRGlyphCacheEntry xRGlyphCacheEntry : paramList) {
/* 230 */       bool = (xRGlyphCacheEntry.getSourceRowBytes() != xRGlyphCacheEntry.getWidth()) ? true : false;
/*     */       
/* 232 */       if (bool) {
/* 233 */         return true;
/*     */       }
/*     */     } 
/* 236 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clearCache(XRGlyphCacheEntry[] paramArrayOfXRGlyphCacheEntry) {
/* 245 */     ArrayList<?> arrayList = new ArrayList(this.cacheMap.values());
/* 246 */     Collections.sort(arrayList, new Comparator<XRGlyphCacheEntry>() {
/*     */           public int compare(XRGlyphCacheEntry param1XRGlyphCacheEntry1, XRGlyphCacheEntry param1XRGlyphCacheEntry2) {
/* 248 */             return param1XRGlyphCacheEntry2.getLastUsed() - param1XRGlyphCacheEntry1.getLastUsed();
/*     */           }
/*     */         });
/*     */     
/* 252 */     for (XRGlyphCacheEntry xRGlyphCacheEntry : paramArrayOfXRGlyphCacheEntry) {
/* 253 */       xRGlyphCacheEntry.setPinned();
/*     */     }
/*     */     
/* 256 */     GrowableIntArray growableIntArray = new GrowableIntArray(1, 10);
/* 257 */     int i = this.cachedPixels - 100000;
/*     */     
/* 259 */     for (int j = arrayList.size() - 1; j >= 0 && i > 0; j--) {
/* 260 */       XRGlyphCacheEntry xRGlyphCacheEntry = (XRGlyphCacheEntry)arrayList.get(j);
/*     */       
/* 262 */       if (!xRGlyphCacheEntry.isPinned()) {
/* 263 */         i -= xRGlyphCacheEntry.getPixelCnt();
/* 264 */         growableIntArray.addInt(xRGlyphCacheEntry.getGlyphID());
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     for (XRGlyphCacheEntry xRGlyphCacheEntry : paramArrayOfXRGlyphCacheEntry) {
/* 269 */       xRGlyphCacheEntry.setUnpinned();
/*     */     }
/*     */     
/* 272 */     freeGlyphs(growableIntArray);
/*     */   }
/*     */   
/*     */   private void freeGlyphs(GrowableIntArray paramGrowableIntArray) {
/* 276 */     GrowableIntArray growableIntArray1 = new GrowableIntArray(1, 10);
/* 277 */     GrowableIntArray growableIntArray2 = new GrowableIntArray(1, 10);
/*     */     
/* 279 */     for (byte b = 0; b < paramGrowableIntArray.getSize(); b++) {
/* 280 */       int i = paramGrowableIntArray.getInt(b);
/* 281 */       this.freeGlyphIDs.add(Integer.valueOf(i));
/*     */       
/* 283 */       this.tmp.setValue(i);
/* 284 */       XRGlyphCacheEntry xRGlyphCacheEntry = this.cacheMap.get(this.tmp);
/* 285 */       this.cachedPixels -= xRGlyphCacheEntry.getPixelCnt();
/* 286 */       this.cacheMap.remove(this.tmp);
/*     */       
/* 288 */       if (xRGlyphCacheEntry.getGlyphSet() == this.grayGlyphSet) {
/* 289 */         growableIntArray2.addInt(i);
/*     */       } else {
/* 291 */         growableIntArray1.addInt(i);
/*     */       } 
/*     */       
/* 294 */       xRGlyphCacheEntry.setGlyphID(0);
/*     */     } 
/*     */     
/* 297 */     if (growableIntArray2.getSize() > 0) {
/* 298 */       this.con.XRenderFreeGlyphs(this.grayGlyphSet, growableIntArray2.getSizedArray());
/*     */     }
/*     */     
/* 301 */     if (growableIntArray1.getSize() > 0)
/* 302 */       this.con.XRenderFreeGlyphs(this.lcdGlyphSet, growableIntArray1.getSizedArray()); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/XRGlyphCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */