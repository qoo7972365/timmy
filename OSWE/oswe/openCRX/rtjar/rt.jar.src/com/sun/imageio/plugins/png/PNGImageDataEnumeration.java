/*     */ package com.sun.imageio.plugins.png;
/*     */ 
/*     */ import com.sun.imageio.plugins.common.InputStreamAdapter;
/*     */ import com.sun.imageio.plugins.common.SubImageInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Enumeration;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PNGImageDataEnumeration
/*     */   implements Enumeration<InputStream>
/*     */ {
/*     */   boolean firstTime = true;
/*     */   ImageInputStream stream;
/*     */   int length;
/*     */   
/*     */   public PNGImageDataEnumeration(ImageInputStream paramImageInputStream) throws IOException {
/*  71 */     this.stream = paramImageInputStream;
/*  72 */     this.length = paramImageInputStream.readInt();
/*  73 */     int i = paramImageInputStream.readInt();
/*     */   }
/*     */   
/*     */   public InputStream nextElement() {
/*     */     try {
/*  78 */       this.firstTime = false;
/*  79 */       SubImageInputStream subImageInputStream = new SubImageInputStream(this.stream, this.length);
/*  80 */       return new InputStreamAdapter(subImageInputStream);
/*  81 */     } catch (IOException iOException) {
/*  82 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasMoreElements() {
/*  87 */     if (this.firstTime) {
/*  88 */       return true;
/*     */     }
/*     */     
/*     */     try {
/*  92 */       int i = this.stream.readInt();
/*  93 */       this.length = this.stream.readInt();
/*  94 */       int j = this.stream.readInt();
/*  95 */       if (j == 1229209940) {
/*  96 */         return true;
/*     */       }
/*  98 */       return false;
/*     */     }
/* 100 */     catch (IOException iOException) {
/* 101 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/png/PNGImageDataEnumeration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */