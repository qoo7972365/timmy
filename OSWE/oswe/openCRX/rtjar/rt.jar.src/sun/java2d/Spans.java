/*     */ package sun.java2d;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Spans
/*     */ {
/*     */   private static final int kMaxAddsSinceSort = 256;
/*  52 */   private List mSpans = new Vector(256);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private int mAddsSinceSort = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(float paramFloat1, float paramFloat2) {
/*  74 */     this.mSpans.add(new Span(paramFloat1, paramFloat2));
/*     */     
/*  76 */     if (this.mSpans != null && ++this.mAddsSinceSort >= 256) {
/*  77 */       sortAndCollapse();
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
/*     */   public void addInfinite() {
/*  92 */     this.mSpans = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersects(float paramFloat1, float paramFloat2) {
/*     */     boolean bool;
/* 104 */     if (this.mSpans != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       if (this.mAddsSinceSort > 0) {
/* 111 */         sortAndCollapse();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 119 */       int i = Collections.binarySearch(this.mSpans, new Span(paramFloat1, paramFloat2), SpanIntersection.instance);
/*     */ 
/*     */ 
/*     */       
/* 123 */       bool = (i >= 0) ? true : false;
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 129 */       bool = true;
/*     */     } 
/*     */     
/* 132 */     return bool;
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
/*     */   private void sortAndCollapse() {
/* 144 */     Collections.sort(this.mSpans);
/* 145 */     this.mAddsSinceSort = 0;
/*     */     
/* 147 */     Iterator<Span> iterator = this.mSpans.iterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     Span span = null;
/* 154 */     if (iterator.hasNext()) {
/* 155 */       span = iterator.next();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     while (iterator.hasNext()) {
/*     */       
/* 163 */       Span span1 = iterator.next();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 185 */       if (span.subsume(span1)) {
/* 186 */         iterator.remove();
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 194 */       span = span1;
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
/*     */   static class Span
/*     */     implements Comparable
/*     */   {
/*     */     private float mStart;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private float mEnd;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Span(float param1Float1, float param1Float2) {
/* 238 */       this.mStart = param1Float1;
/* 239 */       this.mEnd = param1Float2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final float getStart() {
/* 248 */       return this.mStart;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final float getEnd() {
/* 257 */       return this.mEnd;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final void setStart(float param1Float) {
/* 265 */       this.mStart = param1Float;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final void setEnd(float param1Float) {
/* 273 */       this.mEnd = param1Float;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean subsume(Span param1Span) {
/* 290 */       boolean bool = contains(param1Span.mStart);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 297 */       if (bool && param1Span.mEnd > this.mEnd) {
/* 298 */         this.mEnd = param1Span.mEnd;
/*     */       }
/*     */       
/* 301 */       return bool;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean contains(float param1Float) {
/* 310 */       return (this.mStart <= param1Float && param1Float < this.mEnd);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(Object param1Object) {
/*     */       boolean bool;
/* 319 */       Span span = (Span)param1Object;
/* 320 */       float f = span.getStart();
/*     */ 
/*     */       
/* 323 */       if (this.mStart < f) {
/* 324 */         bool = true;
/* 325 */       } else if (this.mStart > f) {
/* 326 */         bool = true;
/*     */       } else {
/* 328 */         bool = false;
/*     */       } 
/*     */       
/* 331 */       return bool;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 335 */       return "Span: " + this.mStart + " to " + this.mEnd;
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
/*     */   static class SpanIntersection
/*     */     implements Comparator
/*     */   {
/* 354 */     static final SpanIntersection instance = new SpanIntersection();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compare(Object param1Object1, Object param1Object2) {
/*     */       boolean bool;
/* 366 */       Spans.Span span1 = (Spans.Span)param1Object1;
/* 367 */       Spans.Span span2 = (Spans.Span)param1Object2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 373 */       if (span1.getEnd() <= span2.getStart()) {
/* 374 */         bool = true;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 380 */       else if (span1.getStart() >= span2.getEnd()) {
/* 381 */         bool = true;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 386 */         bool = false;
/*     */       } 
/*     */       
/* 389 */       return bool;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/Spans.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */