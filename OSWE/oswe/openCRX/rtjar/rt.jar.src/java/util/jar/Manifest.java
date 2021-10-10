/*     */ package java.util.jar;
/*     */ 
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Manifest
/*     */   implements Cloneable
/*     */ {
/*  51 */   private final Attributes attr = new Attributes();
/*     */ 
/*     */   
/*  54 */   private final Map<String, Attributes> entries = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private final JarVerifier jv;
/*     */ 
/*     */ 
/*     */   
/*     */   public Manifest() {
/*  63 */     this.jv = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Manifest(InputStream paramInputStream) throws IOException {
/*  73 */     this(null, paramInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Manifest(JarVerifier paramJarVerifier, InputStream paramInputStream) throws IOException {
/*  81 */     read(paramInputStream);
/*  82 */     this.jv = paramJarVerifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Manifest(Manifest paramManifest) {
/*  91 */     this.attr.putAll(paramManifest.getMainAttributes());
/*  92 */     this.entries.putAll(paramManifest.getEntries());
/*  93 */     this.jv = paramManifest.jv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attributes getMainAttributes() {
/* 101 */     return this.attr;
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
/*     */   public Map<String, Attributes> getEntries() {
/* 114 */     return this.entries;
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
/*     */   public Attributes getAttributes(String paramString) {
/* 140 */     return getEntries().get(paramString);
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
/*     */   Attributes getTrustedAttributes(String paramString) {
/* 157 */     Attributes attributes = getAttributes(paramString);
/* 158 */     if (attributes != null && this.jv != null && !this.jv.isTrustedManifestEntry(paramString)) {
/* 159 */       throw new SecurityException("Untrusted manifest entry: " + paramString);
/*     */     }
/* 161 */     return attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 168 */     this.attr.clear();
/* 169 */     this.entries.clear();
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
/*     */   public void write(OutputStream paramOutputStream) throws IOException {
/* 182 */     DataOutputStream dataOutputStream = new DataOutputStream(paramOutputStream);
/*     */     
/* 184 */     this.attr.writeMain(dataOutputStream);
/*     */     
/* 186 */     Iterator<Map.Entry> iterator = this.entries.entrySet().iterator();
/* 187 */     while (iterator.hasNext()) {
/* 188 */       Map.Entry entry = iterator.next();
/* 189 */       StringBuffer stringBuffer = new StringBuffer("Name: ");
/* 190 */       String str = (String)entry.getKey();
/* 191 */       if (str != null) {
/* 192 */         byte[] arrayOfByte = str.getBytes("UTF8");
/* 193 */         str = new String(arrayOfByte, 0, 0, arrayOfByte.length);
/*     */       } 
/* 195 */       stringBuffer.append(str);
/* 196 */       stringBuffer.append("\r\n");
/* 197 */       make72Safe(stringBuffer);
/* 198 */       dataOutputStream.writeBytes(stringBuffer.toString());
/* 199 */       ((Attributes)entry.getValue()).write(dataOutputStream);
/*     */     } 
/* 201 */     dataOutputStream.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void make72Safe(StringBuffer paramStringBuffer) {
/* 208 */     int i = paramStringBuffer.length();
/* 209 */     if (i > 72) {
/* 210 */       byte b = 70;
/* 211 */       while (b < i - 2) {
/* 212 */         paramStringBuffer.insert(b, "\r\n ");
/* 213 */         b += 72;
/* 214 */         i += 3;
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
/*     */   public void read(InputStream paramInputStream) throws IOException {
/* 230 */     FastInputStream fastInputStream = new FastInputStream(paramInputStream);
/*     */     
/* 232 */     byte[] arrayOfByte1 = new byte[512];
/*     */     
/* 234 */     this.attr.read(fastInputStream, arrayOfByte1);
/*     */     
/* 236 */     byte b = 0; int i = 0;
/*     */     
/* 238 */     int j = 2;
/*     */ 
/*     */     
/* 241 */     String str = null;
/* 242 */     boolean bool = true;
/* 243 */     byte[] arrayOfByte2 = null;
/*     */     int k;
/* 245 */     while ((k = fastInputStream.readLine(arrayOfByte1)) != -1) {
/* 246 */       if (arrayOfByte1[--k] != 10) {
/* 247 */         throw new IOException("manifest line too long");
/*     */       }
/* 249 */       if (k > 0 && arrayOfByte1[k - 1] == 13) {
/* 250 */         k--;
/*     */       }
/* 252 */       if (k == 0 && bool) {
/*     */         continue;
/*     */       }
/* 255 */       bool = false;
/*     */       
/* 257 */       if (str == null) {
/* 258 */         str = parseName(arrayOfByte1, k);
/* 259 */         if (str == null) {
/* 260 */           throw new IOException("invalid manifest format");
/*     */         }
/* 262 */         if (fastInputStream.peek() == 32) {
/*     */           
/* 264 */           arrayOfByte2 = new byte[k - 6];
/* 265 */           System.arraycopy(arrayOfByte1, 6, arrayOfByte2, 0, k - 6);
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } else {
/* 270 */         byte[] arrayOfByte = new byte[arrayOfByte2.length + k - 1];
/* 271 */         System.arraycopy(arrayOfByte2, 0, arrayOfByte, 0, arrayOfByte2.length);
/* 272 */         System.arraycopy(arrayOfByte1, 1, arrayOfByte, arrayOfByte2.length, k - 1);
/* 273 */         if (fastInputStream.peek() == 32) {
/*     */           
/* 275 */           arrayOfByte2 = arrayOfByte;
/*     */           continue;
/*     */         } 
/* 278 */         str = new String(arrayOfByte, 0, arrayOfByte.length, "UTF8");
/* 279 */         arrayOfByte2 = null;
/*     */       } 
/* 281 */       Attributes attributes = getAttributes(str);
/* 282 */       if (attributes == null) {
/* 283 */         attributes = new Attributes(j);
/* 284 */         this.entries.put(str, attributes);
/*     */       } 
/* 286 */       attributes.read(fastInputStream, arrayOfByte1);
/* 287 */       b++;
/* 288 */       i += attributes.size();
/*     */ 
/*     */ 
/*     */       
/* 292 */       j = Math.max(2, i / b);
/*     */       
/* 294 */       str = null;
/* 295 */       bool = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String parseName(byte[] paramArrayOfbyte, int paramInt) {
/* 300 */     if (toLower(paramArrayOfbyte[0]) == 110 && toLower(paramArrayOfbyte[1]) == 97 && 
/* 301 */       toLower(paramArrayOfbyte[2]) == 109 && toLower(paramArrayOfbyte[3]) == 101 && paramArrayOfbyte[4] == 58 && paramArrayOfbyte[5] == 32) {
/*     */       
/*     */       try {
/* 304 */         return new String(paramArrayOfbyte, 6, paramInt - 6, "UTF8");
/*     */       }
/* 306 */       catch (Exception exception) {}
/*     */     }
/*     */     
/* 309 */     return null;
/*     */   }
/*     */   
/*     */   private int toLower(int paramInt) {
/* 313 */     return (paramInt >= 65 && paramInt <= 90) ? (97 + paramInt - 65) : paramInt;
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
/*     */   public boolean equals(Object paramObject) {
/* 325 */     if (paramObject instanceof Manifest) {
/* 326 */       Manifest manifest = (Manifest)paramObject;
/* 327 */       return (this.attr.equals(manifest.getMainAttributes()) && this.entries
/* 328 */         .equals(manifest.getEntries()));
/*     */     } 
/* 330 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 338 */     return this.attr.hashCode() + this.entries.hashCode();
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
/*     */   public Object clone() {
/* 350 */     return new Manifest(this);
/*     */   }
/*     */ 
/*     */   
/*     */   static class FastInputStream
/*     */     extends FilterInputStream
/*     */   {
/*     */     private byte[] buf;
/* 358 */     private int count = 0;
/* 359 */     private int pos = 0;
/*     */     
/*     */     FastInputStream(InputStream param1InputStream) {
/* 362 */       this(param1InputStream, 8192);
/*     */     }
/*     */     
/*     */     FastInputStream(InputStream param1InputStream, int param1Int) {
/* 366 */       super(param1InputStream);
/* 367 */       this.buf = new byte[param1Int];
/*     */     }
/*     */     
/*     */     public int read() throws IOException {
/* 371 */       if (this.pos >= this.count) {
/* 372 */         fill();
/* 373 */         if (this.pos >= this.count) {
/* 374 */           return -1;
/*     */         }
/*     */       } 
/* 377 */       return Byte.toUnsignedInt(this.buf[this.pos++]);
/*     */     }
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 381 */       int i = this.count - this.pos;
/* 382 */       if (i <= 0) {
/* 383 */         if (param1Int2 >= this.buf.length) {
/* 384 */           return this.in.read(param1ArrayOfbyte, param1Int1, param1Int2);
/*     */         }
/* 386 */         fill();
/* 387 */         i = this.count - this.pos;
/* 388 */         if (i <= 0) {
/* 389 */           return -1;
/*     */         }
/*     */       } 
/* 392 */       if (param1Int2 > i) {
/* 393 */         param1Int2 = i;
/*     */       }
/* 395 */       System.arraycopy(this.buf, this.pos, param1ArrayOfbyte, param1Int1, param1Int2);
/* 396 */       this.pos += param1Int2;
/* 397 */       return param1Int2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int readLine(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 405 */       byte[] arrayOfByte = this.buf;
/* 406 */       int i = 0;
/* 407 */       while (i < param1Int2) {
/* 408 */         int j = this.count - this.pos;
/* 409 */         if (j <= 0) {
/* 410 */           fill();
/* 411 */           j = this.count - this.pos;
/* 412 */           if (j <= 0) {
/* 413 */             return -1;
/*     */           }
/*     */         } 
/* 416 */         int k = param1Int2 - i;
/* 417 */         if (k > j) {
/* 418 */           k = j;
/*     */         }
/* 420 */         int m = this.pos;
/* 421 */         int n = m + k;
/* 422 */         while (m < n && arrayOfByte[m++] != 10);
/* 423 */         k = m - this.pos;
/* 424 */         System.arraycopy(arrayOfByte, this.pos, param1ArrayOfbyte, param1Int1, k);
/* 425 */         param1Int1 += k;
/* 426 */         i += k;
/* 427 */         this.pos = m;
/* 428 */         if (arrayOfByte[m - 1] == 10) {
/*     */           break;
/*     */         }
/*     */       } 
/* 432 */       return i;
/*     */     }
/*     */     
/*     */     public byte peek() throws IOException {
/* 436 */       if (this.pos == this.count)
/* 437 */         fill(); 
/* 438 */       if (this.pos == this.count)
/* 439 */         return -1; 
/* 440 */       return this.buf[this.pos];
/*     */     }
/*     */     
/*     */     public int readLine(byte[] param1ArrayOfbyte) throws IOException {
/* 444 */       return readLine(param1ArrayOfbyte, 0, param1ArrayOfbyte.length);
/*     */     }
/*     */     
/*     */     public long skip(long param1Long) throws IOException {
/* 448 */       if (param1Long <= 0L) {
/* 449 */         return 0L;
/*     */       }
/* 451 */       long l = (this.count - this.pos);
/* 452 */       if (l <= 0L) {
/* 453 */         return this.in.skip(param1Long);
/*     */       }
/* 455 */       if (param1Long > l) {
/* 456 */         param1Long = l;
/*     */       }
/* 458 */       this.pos = (int)(this.pos + param1Long);
/* 459 */       return param1Long;
/*     */     }
/*     */     
/*     */     public int available() throws IOException {
/* 463 */       return this.count - this.pos + this.in.available();
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 467 */       if (this.in != null) {
/* 468 */         this.in.close();
/* 469 */         this.in = null;
/* 470 */         this.buf = null;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void fill() throws IOException {
/* 475 */       this.count = this.pos = 0;
/* 476 */       int i = this.in.read(this.buf, 0, this.buf.length);
/* 477 */       if (i > 0)
/* 478 */         this.count = i; 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/jar/Manifest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */