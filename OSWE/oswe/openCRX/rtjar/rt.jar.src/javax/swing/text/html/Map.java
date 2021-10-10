/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.awt.Polygon;
/*     */ import java.io.Serializable;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import javax.swing.text.AttributeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Map
/*     */   implements Serializable
/*     */ {
/*     */   private String name;
/*     */   private Vector<AttributeSet> areaAttributes;
/*     */   private Vector<RegionContainment> areas;
/*     */   
/*     */   public Map() {}
/*     */   
/*     */   public Map(String paramString) {
/*  53 */     this.name = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  60 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addArea(AttributeSet paramAttributeSet) {
/*  67 */     if (paramAttributeSet == null) {
/*     */       return;
/*     */     }
/*  70 */     if (this.areaAttributes == null) {
/*  71 */       this.areaAttributes = new Vector<>(2);
/*     */     }
/*  73 */     this.areaAttributes.addElement(paramAttributeSet.copyAttributes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeArea(AttributeSet paramAttributeSet) {
/*  80 */     if (paramAttributeSet != null && this.areaAttributes != null) {
/*  81 */       byte b = (this.areas != null) ? this.areas.size() : 0;
/*  82 */       for (int i = this.areaAttributes.size() - 1; i >= 0; 
/*  83 */         i--) {
/*  84 */         if (((AttributeSet)this.areaAttributes.elementAt(i)).isEqual(paramAttributeSet)) {
/*  85 */           this.areaAttributes.removeElementAt(i);
/*  86 */           if (i < b) {
/*  87 */             this.areas.removeElementAt(i);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeSet[] getAreas() {
/*  98 */     boolean bool = (this.areaAttributes != null) ? this.areaAttributes.size() : false;
/*     */     
/* 100 */     if (bool) {
/* 101 */       AttributeSet[] arrayOfAttributeSet = new AttributeSet[bool];
/*     */       
/* 103 */       this.areaAttributes.copyInto((Object[])arrayOfAttributeSet);
/* 104 */       return arrayOfAttributeSet;
/*     */     } 
/* 106 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeSet getArea(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 117 */     byte b = (this.areaAttributes != null) ? this.areaAttributes.size() : 0;
/*     */     
/* 119 */     if (b) {
/* 120 */       byte b1 = (this.areas != null) ? this.areas.size() : 0;
/*     */       
/* 122 */       if (this.areas == null) {
/* 123 */         this.areas = new Vector<>(b);
/*     */       }
/* 125 */       for (byte b2 = 0; b2 < b; b2++) {
/* 126 */         if (b2 >= b1) {
/* 127 */           this.areas.addElement(
/* 128 */               createRegionContainment(this.areaAttributes.elementAt(b2)));
/*     */         }
/* 130 */         RegionContainment regionContainment = this.areas.elementAt(b2);
/* 131 */         if (regionContainment != null && regionContainment.contains(paramInt1, paramInt2, paramInt3, paramInt4)) {
/* 132 */           return this.areaAttributes.elementAt(b2);
/*     */         }
/*     */       } 
/*     */     } 
/* 136 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RegionContainment createRegionContainment(AttributeSet paramAttributeSet) {
/* 145 */     Object object = paramAttributeSet.getAttribute(HTML.Attribute.SHAPE);
/*     */     
/* 147 */     if (object == null) {
/* 148 */       object = "rect";
/*     */     }
/* 150 */     if (object instanceof String) {
/* 151 */       String str = ((String)object).toLowerCase();
/* 152 */       RectangleRegionContainment rectangleRegionContainment = null;
/*     */       
/*     */       try {
/* 155 */         if (str.equals("rect")) {
/* 156 */           rectangleRegionContainment = new RectangleRegionContainment(paramAttributeSet);
/*     */         }
/* 158 */         else if (str.equals("circle")) {
/* 159 */           CircleRegionContainment circleRegionContainment = new CircleRegionContainment(paramAttributeSet);
/*     */         }
/* 161 */         else if (str.equals("poly")) {
/* 162 */           PolygonRegionContainment polygonRegionContainment = new PolygonRegionContainment(paramAttributeSet);
/*     */         }
/* 164 */         else if (str.equals("default")) {
/* 165 */           DefaultRegionContainment defaultRegionContainment = DefaultRegionContainment.sharedInstance();
/*     */         } 
/* 167 */       } catch (RuntimeException runtimeException) {
/*     */         
/* 169 */         rectangleRegionContainment = null;
/*     */       } 
/* 171 */       return rectangleRegionContainment;
/*     */     } 
/* 173 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int[] extractCoords(Object paramObject) {
/* 183 */     if (paramObject == null || !(paramObject instanceof String)) {
/* 184 */       return null;
/*     */     }
/*     */     
/* 187 */     StringTokenizer stringTokenizer = new StringTokenizer((String)paramObject, ", \t\n\r");
/*     */     
/* 189 */     int[] arrayOfInt = null;
/* 190 */     byte b = 0;
/*     */     
/* 192 */     while (stringTokenizer.hasMoreElements()) {
/* 193 */       byte b1; String str = stringTokenizer.nextToken();
/*     */ 
/*     */       
/* 196 */       if (str.endsWith("%")) {
/* 197 */         b1 = -1;
/* 198 */         str = str.substring(0, str.length() - 1);
/*     */       } else {
/*     */         
/* 201 */         b1 = 1;
/*     */       } 
/*     */       try {
/* 204 */         int i = Integer.parseInt(str);
/*     */         
/* 206 */         if (arrayOfInt == null) {
/* 207 */           arrayOfInt = new int[4];
/*     */         }
/* 209 */         else if (b == arrayOfInt.length) {
/* 210 */           int[] arrayOfInt1 = new int[arrayOfInt.length * 2];
/*     */           
/* 212 */           System.arraycopy(arrayOfInt, 0, arrayOfInt1, 0, arrayOfInt.length);
/* 213 */           arrayOfInt = arrayOfInt1;
/*     */         } 
/* 215 */         arrayOfInt[b++] = i * b1;
/* 216 */       } catch (NumberFormatException numberFormatException) {
/* 217 */         return null;
/*     */       } 
/*     */     } 
/* 220 */     if (b > 0 && b != arrayOfInt.length) {
/* 221 */       int[] arrayOfInt1 = new int[b];
/*     */       
/* 223 */       System.arraycopy(arrayOfInt, 0, arrayOfInt1, 0, b);
/* 224 */       arrayOfInt = arrayOfInt1;
/*     */     } 
/* 226 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static interface RegionContainment
/*     */   {
/*     */     boolean contains(int param1Int1, int param1Int2, int param1Int3, int param1Int4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class RectangleRegionContainment
/*     */     implements RegionContainment
/*     */   {
/*     */     float[] percents;
/*     */ 
/*     */     
/*     */     int lastWidth;
/*     */ 
/*     */     
/*     */     int lastHeight;
/*     */ 
/*     */     
/*     */     int x0;
/*     */ 
/*     */     
/*     */     int y0;
/*     */ 
/*     */     
/*     */     int x1;
/*     */ 
/*     */     
/*     */     int y1;
/*     */ 
/*     */ 
/*     */     
/*     */     public RectangleRegionContainment(AttributeSet param1AttributeSet) {
/* 265 */       int[] arrayOfInt = Map.extractCoords(param1AttributeSet.getAttribute(HTML.Attribute.COORDS));
/*     */ 
/*     */       
/* 268 */       this.percents = null;
/* 269 */       if (arrayOfInt == null || arrayOfInt.length != 4) {
/* 270 */         throw new RuntimeException("Unable to parse rectangular area");
/*     */       }
/*     */       
/* 273 */       this.x0 = arrayOfInt[0];
/* 274 */       this.y0 = arrayOfInt[1];
/* 275 */       this.x1 = arrayOfInt[2];
/* 276 */       this.y1 = arrayOfInt[3];
/* 277 */       if (this.x0 < 0 || this.y0 < 0 || this.x1 < 0 || this.y1 < 0) {
/* 278 */         this.percents = new float[4];
/* 279 */         this.lastWidth = this.lastHeight = -1;
/* 280 */         for (byte b = 0; b < 4; b++) {
/* 281 */           if (arrayOfInt[b] < 0) {
/* 282 */             this.percents[b] = 
/* 283 */               Math.abs(arrayOfInt[b]) / 100.0F;
/*     */           } else {
/*     */             
/* 286 */             this.percents[b] = -1.0F;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 294 */       if (this.percents == null) {
/* 295 */         return contains(param1Int1, param1Int2);
/*     */       }
/* 297 */       if (this.lastWidth != param1Int3 || this.lastHeight != param1Int4) {
/* 298 */         this.lastWidth = param1Int3;
/* 299 */         this.lastHeight = param1Int4;
/* 300 */         if (this.percents[0] != -1.0F) {
/* 301 */           this.x0 = (int)(this.percents[0] * param1Int3);
/*     */         }
/* 303 */         if (this.percents[1] != -1.0F) {
/* 304 */           this.y0 = (int)(this.percents[1] * param1Int4);
/*     */         }
/* 306 */         if (this.percents[2] != -1.0F) {
/* 307 */           this.x1 = (int)(this.percents[2] * param1Int3);
/*     */         }
/* 309 */         if (this.percents[3] != -1.0F) {
/* 310 */           this.y1 = (int)(this.percents[3] * param1Int4);
/*     */         }
/*     */       } 
/* 313 */       return contains(param1Int1, param1Int2);
/*     */     }
/*     */     
/*     */     public boolean contains(int param1Int1, int param1Int2) {
/* 317 */       return (param1Int1 >= this.x0 && param1Int1 <= this.x1 && param1Int2 >= this.y0 && param1Int2 <= this.y1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class PolygonRegionContainment
/*     */     extends Polygon
/*     */     implements RegionContainment
/*     */   {
/*     */     float[] percentValues;
/*     */ 
/*     */     
/*     */     int[] percentIndexs;
/*     */     
/*     */     int lastWidth;
/*     */     
/*     */     int lastHeight;
/*     */ 
/*     */     
/*     */     public PolygonRegionContainment(AttributeSet param1AttributeSet) {
/* 338 */       int[] arrayOfInt = Map.extractCoords(param1AttributeSet.getAttribute(HTML.Attribute.COORDS));
/*     */ 
/*     */       
/* 341 */       if (arrayOfInt == null || arrayOfInt.length == 0 || arrayOfInt.length % 2 != 0)
/*     */       {
/* 343 */         throw new RuntimeException("Unable to parse polygon area");
/*     */       }
/*     */       
/* 346 */       byte b = 0;
/*     */       
/* 348 */       this.lastWidth = this.lastHeight = -1; int i;
/* 349 */       for (i = arrayOfInt.length - 1; i >= 0; 
/* 350 */         i--) {
/* 351 */         if (arrayOfInt[i] < 0) {
/* 352 */           b++;
/*     */         }
/*     */       } 
/*     */       
/* 356 */       if (b > 0) {
/* 357 */         this.percentIndexs = new int[b];
/* 358 */         this.percentValues = new float[b];
/* 359 */         i = arrayOfInt.length - 1; byte b1 = 0;
/* 360 */         for (; i >= 0; i--) {
/* 361 */           if (arrayOfInt[i] < 0) {
/* 362 */             this.percentValues[b1] = arrayOfInt[i] / -100.0F;
/*     */             
/* 364 */             this.percentIndexs[b1] = i;
/* 365 */             b1++;
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 370 */         this.percentIndexs = null;
/* 371 */         this.percentValues = null;
/*     */       } 
/* 373 */       this.npoints = arrayOfInt.length / 2;
/* 374 */       this.xpoints = new int[this.npoints];
/* 375 */       this.ypoints = new int[this.npoints];
/*     */       
/* 377 */       for (i = 0; i < this.npoints; i++) {
/* 378 */         this.xpoints[i] = arrayOfInt[i + i];
/* 379 */         this.ypoints[i] = arrayOfInt[i + i + 1];
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 385 */       if (this.percentValues == null || (this.lastWidth == param1Int3 && this.lastHeight == param1Int4))
/*     */       {
/* 387 */         return contains(param1Int1, param1Int2);
/*     */       }
/*     */       
/* 390 */       this.bounds = null;
/* 391 */       this.lastWidth = param1Int3;
/* 392 */       this.lastHeight = param1Int4;
/* 393 */       float f1 = param1Int3;
/* 394 */       float f2 = param1Int4;
/* 395 */       for (int i = this.percentValues.length - 1; i >= 0; 
/* 396 */         i--) {
/* 397 */         if (this.percentIndexs[i] % 2 == 0) {
/*     */           
/* 399 */           this.xpoints[this.percentIndexs[i] / 2] = (int)(this.percentValues[i] * f1);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 404 */           this.ypoints[this.percentIndexs[i] / 2] = (int)(this.percentValues[i] * f2);
/*     */         } 
/*     */       } 
/*     */       
/* 408 */       return contains(param1Int1, param1Int2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class CircleRegionContainment
/*     */     implements RegionContainment
/*     */   {
/*     */     int x;
/*     */     
/*     */     int y;
/*     */     
/*     */     int radiusSquared;
/*     */     
/*     */     float[] percentValues;
/*     */     
/*     */     int lastWidth;
/*     */     
/*     */     int lastHeight;
/*     */ 
/*     */     
/*     */     public CircleRegionContainment(AttributeSet param1AttributeSet) {
/* 431 */       int[] arrayOfInt = Map.extractCoords(param1AttributeSet.getAttribute(HTML.Attribute.COORDS));
/*     */ 
/*     */       
/* 434 */       if (arrayOfInt == null || arrayOfInt.length != 3) {
/* 435 */         throw new RuntimeException("Unable to parse circular area");
/*     */       }
/* 437 */       this.x = arrayOfInt[0];
/* 438 */       this.y = arrayOfInt[1];
/* 439 */       this.radiusSquared = arrayOfInt[2] * arrayOfInt[2];
/* 440 */       if (arrayOfInt[0] < 0 || arrayOfInt[1] < 0 || arrayOfInt[2] < 0) {
/* 441 */         this.lastWidth = this.lastHeight = -1;
/* 442 */         this.percentValues = new float[3];
/* 443 */         for (byte b = 0; b < 3; b++) {
/* 444 */           if (arrayOfInt[b] < 0) {
/* 445 */             this.percentValues[b] = arrayOfInt[b] / -100.0F;
/*     */           }
/*     */           else {
/*     */             
/* 449 */             this.percentValues[b] = -1.0F;
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 454 */         this.percentValues = null;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean contains(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 459 */       if (this.percentValues != null && (this.lastWidth != param1Int3 || this.lastHeight != param1Int4)) {
/*     */         
/* 461 */         int i = Math.min(param1Int3, param1Int4) / 2;
/*     */         
/* 463 */         this.lastWidth = param1Int3;
/* 464 */         this.lastHeight = param1Int4;
/* 465 */         if (this.percentValues[0] != -1.0F) {
/* 466 */           this.x = (int)(this.percentValues[0] * param1Int3);
/*     */         }
/* 468 */         if (this.percentValues[1] != -1.0F) {
/* 469 */           this.y = (int)(this.percentValues[1] * param1Int4);
/*     */         }
/* 471 */         if (this.percentValues[2] != -1.0F) {
/* 472 */           this
/* 473 */             .radiusSquared = (int)(this.percentValues[2] * Math.min(param1Int3, param1Int4));
/* 474 */           this.radiusSquared *= this.radiusSquared;
/*     */         } 
/*     */       } 
/* 477 */       return ((param1Int1 - this.x) * (param1Int1 - this.x) + (param1Int2 - this.y) * (param1Int2 - this.y) <= this.radiusSquared);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class DefaultRegionContainment
/*     */     implements RegionContainment
/*     */   {
/* 490 */     static DefaultRegionContainment si = null;
/*     */     
/*     */     public static DefaultRegionContainment sharedInstance() {
/* 493 */       if (si == null) {
/* 494 */         si = new DefaultRegionContainment();
/*     */       }
/* 496 */       return si;
/*     */     }
/*     */     
/*     */     public boolean contains(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 500 */       return (param1Int1 <= param1Int3 && param1Int1 >= 0 && param1Int2 >= 0 && param1Int2 <= param1Int3);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/Map.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */