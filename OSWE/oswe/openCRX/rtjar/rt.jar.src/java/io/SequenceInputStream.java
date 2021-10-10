/*     */ package java.io;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SequenceInputStream
/*     */   extends InputStream
/*     */ {
/*     */   Enumeration<? extends InputStream> e;
/*     */   InputStream in;
/*     */   
/*     */   public SequenceInputStream(Enumeration<? extends InputStream> paramEnumeration) {
/*  67 */     this.e = paramEnumeration;
/*     */     try {
/*  69 */       nextStream();
/*  70 */     } catch (IOException iOException) {
/*     */       
/*  72 */       throw new Error("panic");
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
/*     */   public SequenceInputStream(InputStream paramInputStream1, InputStream paramInputStream2) {
/*  88 */     Vector<InputStream> vector = new Vector(2);
/*     */     
/*  90 */     vector.addElement(paramInputStream1);
/*  91 */     vector.addElement(paramInputStream2);
/*  92 */     this.e = vector.elements();
/*     */     try {
/*  94 */       nextStream();
/*  95 */     } catch (IOException iOException) {
/*     */       
/*  97 */       throw new Error("panic");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void nextStream() throws IOException {
/* 105 */     if (this.in != null) {
/* 106 */       this.in.close();
/*     */     }
/*     */     
/* 109 */     if (this.e.hasMoreElements()) {
/* 110 */       this.in = this.e.nextElement();
/* 111 */       if (this.in == null)
/* 112 */         throw new NullPointerException(); 
/*     */     } else {
/* 114 */       this.in = null;
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
/*     */   
/*     */   public int available() throws IOException {
/* 138 */     if (this.in == null) {
/* 139 */       return 0;
/*     */     }
/* 141 */     return this.in.available();
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
/*     */   public int read() throws IOException {
/* 163 */     while (this.in != null) {
/* 164 */       int i = this.in.read();
/* 165 */       if (i != -1) {
/* 166 */         return i;
/*     */       }
/* 168 */       nextStream();
/*     */     } 
/* 170 */     return -1;
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
/*     */   
/*     */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 197 */     if (this.in == null)
/* 198 */       return -1; 
/* 199 */     if (paramArrayOfbyte == null)
/* 200 */       throw new NullPointerException(); 
/* 201 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt2 > paramArrayOfbyte.length - paramInt1)
/* 202 */       throw new IndexOutOfBoundsException(); 
/* 203 */     if (paramInt2 == 0) {
/* 204 */       return 0;
/*     */     }
/*     */     while (true) {
/* 207 */       int i = this.in.read(paramArrayOfbyte, paramInt1, paramInt2);
/* 208 */       if (i > 0) {
/* 209 */         return i;
/*     */       }
/* 211 */       nextStream();
/* 212 */       if (this.in == null) {
/* 213 */         return -1;
/*     */       }
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
/*     */   public void close() throws IOException {
/*     */     do {
/* 232 */       nextStream();
/* 233 */     } while (this.in != null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/SequenceInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */