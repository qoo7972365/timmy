/*     */ package sun.awt.geom;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AreaOp
/*     */ {
/*     */   public static final int CTAG_LEFT = 0;
/*     */   public static final int CTAG_RIGHT = 1;
/*     */   public static final int ETAG_IGNORE = 0;
/*     */   public static final int ETAG_ENTER = 1;
/*     */   public static final int ETAG_EXIT = -1;
/*     */   public static final int RSTAG_INSIDE = 1;
/*     */   public static final int RSTAG_OUTSIDE = -1;
/*     */   
/*     */   public static abstract class CAGOp
/*     */     extends AreaOp
/*     */   {
/*     */     boolean inLeft;
/*     */     boolean inRight;
/*     */     boolean inResult;
/*     */     
/*     */     public void newRow() {
/*  40 */       this.inLeft = false;
/*  41 */       this.inRight = false;
/*  42 */       this.inResult = false;
/*     */     }
/*     */     
/*     */     public int classify(Edge param1Edge) {
/*  46 */       if (param1Edge.getCurveTag() == 0) {
/*  47 */         this.inLeft = !this.inLeft;
/*     */       } else {
/*  49 */         this.inRight = !this.inRight;
/*     */       } 
/*  51 */       boolean bool = newClassification(this.inLeft, this.inRight);
/*  52 */       if (this.inResult == bool) {
/*  53 */         return 0;
/*     */       }
/*  55 */       this.inResult = bool;
/*  56 */       return bool ? 1 : -1;
/*     */     }
/*     */     
/*     */     public int getState() {
/*  60 */       return this.inResult ? 1 : -1;
/*     */     }
/*     */     
/*     */     public abstract boolean newClassification(boolean param1Boolean1, boolean param1Boolean2);
/*     */   }
/*     */   
/*     */   public static class AddOp
/*     */     extends CAGOp {
/*     */     public boolean newClassification(boolean param1Boolean1, boolean param1Boolean2) {
/*  69 */       return (param1Boolean1 || param1Boolean2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SubOp extends CAGOp {
/*     */     public boolean newClassification(boolean param1Boolean1, boolean param1Boolean2) {
/*  75 */       return (param1Boolean1 && !param1Boolean2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class IntOp extends CAGOp {
/*     */     public boolean newClassification(boolean param1Boolean1, boolean param1Boolean2) {
/*  81 */       return (param1Boolean1 && param1Boolean2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class XorOp extends CAGOp {
/*     */     public boolean newClassification(boolean param1Boolean1, boolean param1Boolean2) {
/*  87 */       return (param1Boolean1 != param1Boolean2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class NZWindOp extends AreaOp {
/*     */     private int count;
/*     */     
/*     */     public void newRow() {
/*  95 */       this.count = 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int classify(Edge param1Edge) {
/* 101 */       int i = this.count;
/* 102 */       boolean bool = (i == 0) ? true : false;
/* 103 */       i += param1Edge.getCurve().getDirection();
/* 104 */       this.count = i;
/* 105 */       return (i == 0) ? -1 : bool;
/*     */     }
/*     */     
/*     */     public int getState() {
/* 109 */       return (this.count == 0) ? -1 : 1;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class EOWindOp extends AreaOp {
/*     */     private boolean inside;
/*     */     
/*     */     public void newRow() {
/* 117 */       this.inside = false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int classify(Edge param1Edge) {
/* 123 */       boolean bool = !this.inside ? true : false;
/* 124 */       this.inside = bool;
/* 125 */       return bool ? 1 : -1;
/*     */     }
/*     */     
/*     */     public int getState() {
/* 129 */       return this.inside ? 1 : -1;
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
/*     */   private AreaOp() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector calculate(Vector paramVector1, Vector paramVector2) {
/* 156 */     Vector vector = new Vector();
/* 157 */     addEdges(vector, paramVector1, 0);
/* 158 */     addEdges(vector, paramVector2, 1);
/* 159 */     vector = pruneEdges(vector);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     return vector;
/*     */   }
/*     */   
/*     */   private static void addEdges(Vector<Edge> paramVector1, Vector paramVector2, int paramInt) {
/* 172 */     Enumeration<Curve> enumeration = paramVector2.elements();
/* 173 */     while (enumeration.hasMoreElements()) {
/* 174 */       Curve curve = enumeration.nextElement();
/* 175 */       if (curve.getOrder() > 0) {
/* 176 */         paramVector1.add(new Edge(curve, paramInt));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/* 181 */   private static Comparator YXTopComparator = new Comparator() {
/*     */       public int compare(Object param1Object1, Object param1Object2) {
/* 183 */         Curve curve1 = ((Edge)param1Object1).getCurve();
/* 184 */         Curve curve2 = ((Edge)param1Object2).getCurve();
/*     */         double d1, d2;
/* 186 */         if ((d1 = curve1.getYTop()) == (d2 = curve2.getYTop()) && (
/* 187 */           d1 = curve1.getXTop()) == (d2 = curve2.getXTop())) {
/* 188 */           return 0;
/*     */         }
/*     */         
/* 191 */         if (d1 < d2) {
/* 192 */           return -1;
/*     */         }
/* 194 */         return 1;
/*     */       }
/*     */     };
/*     */   
/*     */   private Vector pruneEdges(Vector paramVector) {
/* 199 */     int i = paramVector.size();
/* 200 */     if (i < 2) {
/* 201 */       return paramVector;
/*     */     }
/* 203 */     Edge[] arrayOfEdge = (Edge[])paramVector.toArray((Object[])new Edge[i]);
/* 204 */     Arrays.sort(arrayOfEdge, YXTopComparator);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     int j = 0;
/* 213 */     byte b = 0;
/* 214 */     int k = 0;
/* 215 */     int m = 0;
/* 216 */     double[] arrayOfDouble = new double[2];
/* 217 */     Vector vector1 = new Vector();
/* 218 */     Vector vector2 = new Vector();
/* 219 */     Vector<CurveLink> vector = new Vector();
/*     */     
/* 221 */     while (j < i) {
/* 222 */       double d1 = arrayOfDouble[0];
/*     */       
/* 224 */       for (k = m = b - 1; k >= j; k--) {
/* 225 */         Edge edge = arrayOfEdge[k];
/* 226 */         if (edge.getCurve().getYBot() > d1) {
/* 227 */           if (m > k) {
/* 228 */             arrayOfEdge[m] = edge;
/*     */           }
/* 230 */           m--;
/*     */         } 
/*     */       } 
/* 233 */       j = m + 1;
/*     */       
/* 235 */       if (j >= b) {
/* 236 */         if (b >= i) {
/*     */           break;
/*     */         }
/* 239 */         d1 = arrayOfEdge[b].getCurve().getYTop();
/* 240 */         if (d1 > arrayOfDouble[0]) {
/* 241 */           finalizeSubCurves(vector1, vector2);
/*     */         }
/* 243 */         arrayOfDouble[0] = d1;
/*     */       } 
/*     */       
/* 246 */       while (b < i) {
/* 247 */         Edge edge = arrayOfEdge[b];
/* 248 */         if (edge.getCurve().getYTop() > d1) {
/*     */           break;
/*     */         }
/* 251 */         b++;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 256 */       arrayOfDouble[1] = arrayOfEdge[j].getCurve().getYBot();
/* 257 */       if (b < i) {
/* 258 */         d1 = arrayOfEdge[b].getCurve().getYTop();
/* 259 */         if (arrayOfDouble[1] > d1) {
/* 260 */           arrayOfDouble[1] = d1;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 272 */       byte b1 = 1;
/* 273 */       for (k = j; k < b; k++) {
/* 274 */         Edge edge = arrayOfEdge[k];
/* 275 */         edge.setEquivalence(0);
/* 276 */         for (m = k; m > j; m--) {
/* 277 */           Edge edge1 = arrayOfEdge[m - 1];
/* 278 */           int n = edge.compareTo(edge1, arrayOfDouble);
/* 279 */           if (arrayOfDouble[1] <= arrayOfDouble[0]) {
/* 280 */             throw new InternalError("backstepping to " + arrayOfDouble[1] + " from " + arrayOfDouble[0]);
/*     */           }
/*     */           
/* 283 */           if (n >= 0) {
/* 284 */             if (n == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 289 */               int i1 = edge1.getEquivalence();
/* 290 */               if (i1 == 0) {
/* 291 */                 i1 = b1++;
/* 292 */                 edge1.setEquivalence(i1);
/*     */               } 
/* 294 */               edge.setEquivalence(i1);
/*     */             } 
/*     */             break;
/*     */           } 
/* 298 */           arrayOfEdge[m] = edge1;
/*     */         } 
/* 300 */         arrayOfEdge[m] = edge;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 314 */       newRow();
/* 315 */       double d2 = arrayOfDouble[0];
/* 316 */       double d3 = arrayOfDouble[1];
/* 317 */       for (k = j; k < b; k++) {
/* 318 */         int n; Edge edge = arrayOfEdge[k];
/*     */         
/* 320 */         int i1 = edge.getEquivalence();
/* 321 */         if (i1 != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 327 */           int i2 = getState();
/* 328 */           n = (i2 == 1) ? -1 : 1;
/*     */ 
/*     */           
/* 331 */           Edge edge1 = null;
/* 332 */           Edge edge2 = edge;
/* 333 */           double d = d3;
/*     */ 
/*     */           
/*     */           do {
/* 337 */             classify(edge);
/* 338 */             if (edge1 == null && edge
/* 339 */               .isActiveFor(d2, n))
/*     */             {
/* 341 */               edge1 = edge;
/*     */             }
/* 343 */             d1 = edge.getCurve().getYBot();
/* 344 */             if (d1 <= d)
/* 345 */               continue;  edge2 = edge;
/* 346 */             d = d1;
/*     */           }
/* 348 */           while (++k < b && (edge = arrayOfEdge[k])
/* 349 */             .getEquivalence() == i1);
/* 350 */           k--;
/* 351 */           if (getState() == i2) {
/* 352 */             n = 0;
/*     */           } else {
/* 354 */             edge = (edge1 != null) ? edge1 : edge2;
/*     */           } 
/*     */         } else {
/* 357 */           n = classify(edge);
/*     */         } 
/* 359 */         if (n != 0) {
/* 360 */           edge.record(d3, n);
/* 361 */           vector.add(new CurveLink(edge.getCurve(), d2, d3, n));
/*     */         } 
/*     */       } 
/*     */       
/* 365 */       if (getState() != -1) {
/* 366 */         System.out.println("Still inside at end of active edge list!");
/* 367 */         System.out.println("num curves = " + (b - j));
/* 368 */         System.out.println("num links = " + vector.size());
/* 369 */         System.out.println("y top = " + arrayOfDouble[0]);
/* 370 */         if (b < i) {
/* 371 */           System.out.println("y top of next curve = " + arrayOfEdge[b]
/* 372 */               .getCurve().getYTop());
/*     */         } else {
/* 374 */           System.out.println("no more curves");
/*     */         } 
/* 376 */         for (k = j; k < b; k++) {
/* 377 */           Edge edge = arrayOfEdge[k];
/* 378 */           System.out.println(edge);
/* 379 */           int n = edge.getEquivalence();
/* 380 */           if (n != 0) {
/* 381 */             System.out.println("  was equal to " + n + "...");
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 392 */       resolveLinks(vector1, vector2, vector);
/* 393 */       vector.clear();
/*     */ 
/*     */       
/* 396 */       arrayOfDouble[0] = d3;
/*     */     } 
/* 398 */     finalizeSubCurves(vector1, vector2);
/* 399 */     Vector<Curve> vector3 = new Vector();
/* 400 */     Enumeration<CurveLink> enumeration = vector1.elements();
/* 401 */     while (enumeration.hasMoreElements()) {
/* 402 */       CurveLink curveLink1 = enumeration.nextElement();
/* 403 */       vector3.add(curveLink1.getMoveto());
/* 404 */       CurveLink curveLink2 = curveLink1;
/* 405 */       while ((curveLink2 = curveLink2.getNext()) != null) {
/* 406 */         if (!curveLink1.absorb(curveLink2)) {
/* 407 */           vector3.add(curveLink1.getSubCurve());
/* 408 */           curveLink1 = curveLink2;
/*     */         } 
/*     */       } 
/* 411 */       vector3.add(curveLink1.getSubCurve());
/*     */     } 
/* 413 */     return vector3;
/*     */   }
/*     */   
/*     */   public static void finalizeSubCurves(Vector<CurveLink> paramVector1, Vector paramVector2) {
/* 417 */     int i = paramVector2.size();
/* 418 */     if (i == 0) {
/*     */       return;
/*     */     }
/* 421 */     if ((i & 0x1) != 0) {
/* 422 */       throw new InternalError("Odd number of chains!");
/*     */     }
/* 424 */     ChainEnd[] arrayOfChainEnd = new ChainEnd[i];
/* 425 */     paramVector2.toArray((Object[])arrayOfChainEnd);
/* 426 */     for (byte b = 1; b < i; b += 2) {
/* 427 */       ChainEnd chainEnd1 = arrayOfChainEnd[b - 1];
/* 428 */       ChainEnd chainEnd2 = arrayOfChainEnd[b];
/* 429 */       CurveLink curveLink = chainEnd1.linkTo(chainEnd2);
/* 430 */       if (curveLink != null) {
/* 431 */         paramVector1.add(curveLink);
/*     */       }
/*     */     } 
/* 434 */     paramVector2.clear();
/*     */   }
/*     */   
/* 437 */   private static CurveLink[] EmptyLinkList = new CurveLink[2];
/* 438 */   private static ChainEnd[] EmptyChainList = new ChainEnd[2];
/*     */ 
/*     */   
/*     */   public static void resolveLinks(Vector<CurveLink> paramVector1, Vector<ChainEnd> paramVector2, Vector paramVector3) {
/*     */     CurveLink[] arrayOfCurveLink;
/*     */     ChainEnd[] arrayOfChainEnd;
/* 444 */     int i = paramVector3.size();
/*     */     
/* 446 */     if (i == 0) {
/* 447 */       arrayOfCurveLink = EmptyLinkList;
/*     */     } else {
/* 449 */       if ((i & 0x1) != 0) {
/* 450 */         throw new InternalError("Odd number of new curves!");
/*     */       }
/* 452 */       arrayOfCurveLink = new CurveLink[i + 2];
/* 453 */       paramVector3.toArray((Object[])arrayOfCurveLink);
/*     */     } 
/* 455 */     int j = paramVector2.size();
/*     */     
/* 457 */     if (j == 0) {
/* 458 */       arrayOfChainEnd = EmptyChainList;
/*     */     } else {
/* 460 */       if ((j & 0x1) != 0) {
/* 461 */         throw new InternalError("Odd number of chains!");
/*     */       }
/* 463 */       arrayOfChainEnd = new ChainEnd[j + 2];
/* 464 */       paramVector2.toArray((Object[])arrayOfChainEnd);
/*     */     } 
/* 466 */     byte b1 = 0;
/* 467 */     byte b2 = 0;
/* 468 */     paramVector2.clear();
/* 469 */     ChainEnd chainEnd1 = arrayOfChainEnd[0];
/* 470 */     ChainEnd chainEnd2 = arrayOfChainEnd[1];
/* 471 */     CurveLink curveLink1 = arrayOfCurveLink[0];
/* 472 */     CurveLink curveLink2 = arrayOfCurveLink[1];
/* 473 */     while (chainEnd1 != null || curveLink1 != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 478 */       boolean bool1 = (curveLink1 == null) ? true : false;
/* 479 */       boolean bool2 = (chainEnd1 == null) ? true : false;
/*     */       
/* 481 */       if (!bool1 && !bool2) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 488 */         bool1 = ((b1 & 0x1) == 0 && chainEnd1.getX() == chainEnd2.getX()) ? true : false;
/*     */         
/* 490 */         bool2 = ((b2 & 0x1) == 0 && curveLink1.getX() == curveLink2.getX()) ? true : false;
/*     */         
/* 492 */         if (!bool1 && !bool2) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 498 */           double d1 = chainEnd1.getX();
/* 499 */           double d2 = curveLink1.getX();
/*     */ 
/*     */           
/* 502 */           bool1 = (chainEnd2 != null && d1 < d2 && obstructs(chainEnd2.getX(), d2, b1)) ? true : false;
/*     */ 
/*     */           
/* 505 */           bool2 = (curveLink2 != null && d2 < d1 && obstructs(curveLink2.getX(), d1, b2)) ? true : false;
/*     */         } 
/*     */       } 
/* 508 */       if (bool1) {
/* 509 */         CurveLink curveLink = chainEnd1.linkTo(chainEnd2);
/* 510 */         if (curveLink != null) {
/* 511 */           paramVector1.add(curveLink);
/*     */         }
/* 513 */         b1 += true;
/* 514 */         chainEnd1 = arrayOfChainEnd[b1];
/* 515 */         chainEnd2 = arrayOfChainEnd[b1 + 1];
/*     */       } 
/* 517 */       if (bool2) {
/* 518 */         ChainEnd chainEnd3 = new ChainEnd(curveLink1, null);
/* 519 */         ChainEnd chainEnd4 = new ChainEnd(curveLink2, chainEnd3);
/* 520 */         chainEnd3.setOtherEnd(chainEnd4);
/* 521 */         paramVector2.add(chainEnd3);
/* 522 */         paramVector2.add(chainEnd4);
/* 523 */         b2 += true;
/* 524 */         curveLink1 = arrayOfCurveLink[b2];
/* 525 */         curveLink2 = arrayOfCurveLink[b2 + 1];
/*     */       } 
/* 527 */       if (!bool1 && !bool2) {
/*     */ 
/*     */ 
/*     */         
/* 531 */         chainEnd1.addLink(curveLink1);
/* 532 */         paramVector2.add(chainEnd1);
/* 533 */         b1++;
/* 534 */         chainEnd1 = chainEnd2;
/* 535 */         chainEnd2 = arrayOfChainEnd[b1 + 1];
/* 536 */         b2++;
/* 537 */         curveLink1 = curveLink2;
/* 538 */         curveLink2 = arrayOfCurveLink[b2 + 1];
/*     */       } 
/*     */     } 
/* 541 */     if ((paramVector2.size() & 0x1) != 0) {
/* 542 */       System.out.println("Odd number of chains!");
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
/*     */   public static boolean obstructs(double paramDouble1, double paramDouble2, int paramInt) {
/* 559 */     return ((paramInt & 0x1) == 0) ? ((paramDouble1 <= paramDouble2)) : ((paramDouble1 < paramDouble2));
/*     */   }
/*     */   
/*     */   public abstract void newRow();
/*     */   
/*     */   public abstract int classify(Edge paramEdge);
/*     */   
/*     */   public abstract int getState();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/geom/AreaOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */