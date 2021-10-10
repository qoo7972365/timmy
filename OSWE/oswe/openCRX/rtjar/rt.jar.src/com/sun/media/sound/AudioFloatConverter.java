/*      */ package com.sun.media.sound;
/*      */ 
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.DoubleBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import javax.sound.sampled.AudioFormat;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AudioFloatConverter
/*      */ {
/*      */   private AudioFormat format;
/*      */   
/*      */   private static class AudioFloatLSBFilter
/*      */     extends AudioFloatConverter
/*      */   {
/*      */     private final AudioFloatConverter converter;
/*      */     private final int offset;
/*      */     private final int stepsize;
/*      */     private final byte mask;
/*      */     private byte[] mask_buffer;
/*      */     
/*      */     AudioFloatLSBFilter(AudioFloatConverter param1AudioFloatConverter, AudioFormat param1AudioFormat) {
/*   65 */       int i = param1AudioFormat.getSampleSizeInBits();
/*   66 */       boolean bool = param1AudioFormat.isBigEndian();
/*   67 */       this.converter = param1AudioFloatConverter;
/*   68 */       this.stepsize = (i + 7) / 8;
/*   69 */       this.offset = bool ? (this.stepsize - 1) : 0;
/*   70 */       int j = i % 8;
/*   71 */       if (j == 0) {
/*   72 */         this.mask = 0;
/*   73 */       } else if (j == 1) {
/*   74 */         this.mask = Byte.MIN_VALUE;
/*   75 */       } else if (j == 2) {
/*   76 */         this.mask = -64;
/*   77 */       } else if (j == 3) {
/*   78 */         this.mask = -32;
/*   79 */       } else if (j == 4) {
/*   80 */         this.mask = -16;
/*   81 */       } else if (j == 5) {
/*   82 */         this.mask = -8;
/*   83 */       } else if (j == 6) {
/*   84 */         this.mask = -4;
/*   85 */       } else if (j == 7) {
/*   86 */         this.mask = -2;
/*      */       } else {
/*   88 */         this.mask = -1;
/*      */       } 
/*      */     }
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*   93 */       byte[] arrayOfByte = this.converter.toByteArray(param1ArrayOffloat, param1Int1, param1Int2, param1ArrayOfbyte, param1Int3);
/*      */ 
/*      */       
/*   96 */       int i = param1Int2 * this.stepsize; int j;
/*   97 */       for (j = param1Int3 + this.offset; j < i; j += this.stepsize) {
/*   98 */         param1ArrayOfbyte[j] = (byte)(param1ArrayOfbyte[j] & this.mask);
/*      */       }
/*      */       
/*  101 */       return arrayOfByte;
/*      */     }
/*      */ 
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  106 */       if (this.mask_buffer == null || this.mask_buffer.length < param1ArrayOfbyte.length)
/*  107 */         this.mask_buffer = new byte[param1ArrayOfbyte.length]; 
/*  108 */       System.arraycopy(param1ArrayOfbyte, 0, this.mask_buffer, 0, param1ArrayOfbyte.length);
/*  109 */       int i = param1Int3 * this.stepsize; int j;
/*  110 */       for (j = param1Int1 + this.offset; j < i; j += this.stepsize) {
/*  111 */         this.mask_buffer[j] = (byte)(this.mask_buffer[j] & this.mask);
/*      */       }
/*  113 */       return this.converter.toFloatArray(this.mask_buffer, param1Int1, param1ArrayOffloat, param1Int2, param1Int3);
/*      */     }
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
/*      */   private static class AudioFloatConversion64L
/*      */     extends AudioFloatConverter
/*      */   {
/*  128 */     ByteBuffer bytebuffer = null;
/*      */     
/*  130 */     DoubleBuffer floatbuffer = null;
/*      */     
/*  132 */     double[] double_buff = null;
/*      */ 
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  136 */       int i = param1Int3 * 8;
/*  137 */       if (this.bytebuffer == null || this.bytebuffer.capacity() < i) {
/*  138 */         this.bytebuffer = ByteBuffer.allocate(i).order(ByteOrder.LITTLE_ENDIAN);
/*      */         
/*  140 */         this.floatbuffer = this.bytebuffer.asDoubleBuffer();
/*      */       } 
/*  142 */       this.bytebuffer.position(0);
/*  143 */       this.floatbuffer.position(0);
/*  144 */       this.bytebuffer.put(param1ArrayOfbyte, param1Int1, i);
/*  145 */       if (this.double_buff == null || this.double_buff.length < param1Int3 + param1Int2)
/*      */       {
/*  147 */         this.double_buff = new double[param1Int3 + param1Int2]; } 
/*  148 */       this.floatbuffer.get(this.double_buff, param1Int2, param1Int3);
/*  149 */       int j = param1Int2 + param1Int3;
/*  150 */       for (int k = param1Int2; k < j; k++) {
/*  151 */         param1ArrayOffloat[k] = (float)this.double_buff[k];
/*      */       }
/*  153 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  158 */       int i = param1Int2 * 8;
/*  159 */       if (this.bytebuffer == null || this.bytebuffer.capacity() < i) {
/*  160 */         this.bytebuffer = ByteBuffer.allocate(i).order(ByteOrder.LITTLE_ENDIAN);
/*      */         
/*  162 */         this.floatbuffer = this.bytebuffer.asDoubleBuffer();
/*      */       } 
/*  164 */       this.floatbuffer.position(0);
/*  165 */       this.bytebuffer.position(0);
/*  166 */       if (this.double_buff == null || this.double_buff.length < param1Int1 + param1Int2)
/*  167 */         this.double_buff = new double[param1Int1 + param1Int2]; 
/*  168 */       int j = param1Int1 + param1Int2;
/*  169 */       for (int k = param1Int1; k < j; k++) {
/*  170 */         this.double_buff[k] = param1ArrayOffloat[k];
/*      */       }
/*  172 */       this.floatbuffer.put(this.double_buff, param1Int1, param1Int2);
/*  173 */       this.bytebuffer.get(param1ArrayOfbyte, param1Int3, i);
/*  174 */       return param1ArrayOfbyte;
/*      */     }
/*      */     
/*      */     private AudioFloatConversion64L() {} }
/*      */   
/*      */   private static class AudioFloatConversion64B extends AudioFloatConverter {
/*  180 */     ByteBuffer bytebuffer = null;
/*      */     
/*  182 */     DoubleBuffer floatbuffer = null;
/*      */     
/*  184 */     double[] double_buff = null;
/*      */ 
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  188 */       int i = param1Int3 * 8;
/*  189 */       if (this.bytebuffer == null || this.bytebuffer.capacity() < i) {
/*  190 */         this.bytebuffer = ByteBuffer.allocate(i).order(ByteOrder.BIG_ENDIAN);
/*      */         
/*  192 */         this.floatbuffer = this.bytebuffer.asDoubleBuffer();
/*      */       } 
/*  194 */       this.bytebuffer.position(0);
/*  195 */       this.floatbuffer.position(0);
/*  196 */       this.bytebuffer.put(param1ArrayOfbyte, param1Int1, i);
/*  197 */       if (this.double_buff == null || this.double_buff.length < param1Int3 + param1Int2)
/*      */       {
/*  199 */         this.double_buff = new double[param1Int3 + param1Int2]; } 
/*  200 */       this.floatbuffer.get(this.double_buff, param1Int2, param1Int3);
/*  201 */       int j = param1Int2 + param1Int3;
/*  202 */       for (int k = param1Int2; k < j; k++) {
/*  203 */         param1ArrayOffloat[k] = (float)this.double_buff[k];
/*      */       }
/*  205 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  210 */       int i = param1Int2 * 8;
/*  211 */       if (this.bytebuffer == null || this.bytebuffer.capacity() < i) {
/*  212 */         this.bytebuffer = ByteBuffer.allocate(i).order(ByteOrder.BIG_ENDIAN);
/*      */         
/*  214 */         this.floatbuffer = this.bytebuffer.asDoubleBuffer();
/*      */       } 
/*  216 */       this.floatbuffer.position(0);
/*  217 */       this.bytebuffer.position(0);
/*  218 */       if (this.double_buff == null || this.double_buff.length < param1Int1 + param1Int2)
/*  219 */         this.double_buff = new double[param1Int1 + param1Int2]; 
/*  220 */       int j = param1Int1 + param1Int2;
/*  221 */       for (int k = param1Int1; k < j; k++) {
/*  222 */         this.double_buff[k] = param1ArrayOffloat[k];
/*      */       }
/*  224 */       this.floatbuffer.put(this.double_buff, param1Int1, param1Int2);
/*  225 */       this.bytebuffer.get(param1ArrayOfbyte, param1Int3, i);
/*  226 */       return param1ArrayOfbyte;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private AudioFloatConversion64B() {}
/*      */   }
/*      */ 
/*      */   
/*      */   private static class AudioFloatConversion32L
/*      */     extends AudioFloatConverter
/*      */   {
/*  238 */     ByteBuffer bytebuffer = null;
/*      */     
/*  240 */     FloatBuffer floatbuffer = null;
/*      */ 
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  244 */       int i = param1Int3 * 4;
/*  245 */       if (this.bytebuffer == null || this.bytebuffer.capacity() < i) {
/*  246 */         this.bytebuffer = ByteBuffer.allocate(i).order(ByteOrder.LITTLE_ENDIAN);
/*      */         
/*  248 */         this.floatbuffer = this.bytebuffer.asFloatBuffer();
/*      */       } 
/*  250 */       this.bytebuffer.position(0);
/*  251 */       this.floatbuffer.position(0);
/*  252 */       this.bytebuffer.put(param1ArrayOfbyte, param1Int1, i);
/*  253 */       this.floatbuffer.get(param1ArrayOffloat, param1Int2, param1Int3);
/*  254 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  259 */       int i = param1Int2 * 4;
/*  260 */       if (this.bytebuffer == null || this.bytebuffer.capacity() < i) {
/*  261 */         this.bytebuffer = ByteBuffer.allocate(i).order(ByteOrder.LITTLE_ENDIAN);
/*      */         
/*  263 */         this.floatbuffer = this.bytebuffer.asFloatBuffer();
/*      */       } 
/*  265 */       this.floatbuffer.position(0);
/*  266 */       this.bytebuffer.position(0);
/*  267 */       this.floatbuffer.put(param1ArrayOffloat, param1Int1, param1Int2);
/*  268 */       this.bytebuffer.get(param1ArrayOfbyte, param1Int3, i);
/*  269 */       return param1ArrayOfbyte;
/*      */     }
/*      */     
/*      */     private AudioFloatConversion32L() {} }
/*      */   
/*      */   private static class AudioFloatConversion32B extends AudioFloatConverter {
/*  275 */     ByteBuffer bytebuffer = null;
/*      */     
/*  277 */     FloatBuffer floatbuffer = null;
/*      */ 
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  281 */       int i = param1Int3 * 4;
/*  282 */       if (this.bytebuffer == null || this.bytebuffer.capacity() < i) {
/*  283 */         this.bytebuffer = ByteBuffer.allocate(i).order(ByteOrder.BIG_ENDIAN);
/*      */         
/*  285 */         this.floatbuffer = this.bytebuffer.asFloatBuffer();
/*      */       } 
/*  287 */       this.bytebuffer.position(0);
/*  288 */       this.floatbuffer.position(0);
/*  289 */       this.bytebuffer.put(param1ArrayOfbyte, param1Int1, i);
/*  290 */       this.floatbuffer.get(param1ArrayOffloat, param1Int2, param1Int3);
/*  291 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  296 */       int i = param1Int2 * 4;
/*  297 */       if (this.bytebuffer == null || this.bytebuffer.capacity() < i) {
/*  298 */         this.bytebuffer = ByteBuffer.allocate(i).order(ByteOrder.BIG_ENDIAN);
/*      */         
/*  300 */         this.floatbuffer = this.bytebuffer.asFloatBuffer();
/*      */       } 
/*  302 */       this.floatbuffer.position(0);
/*  303 */       this.bytebuffer.position(0);
/*  304 */       this.floatbuffer.put(param1ArrayOffloat, param1Int1, param1Int2);
/*  305 */       this.bytebuffer.get(param1ArrayOfbyte, param1Int3, i);
/*  306 */       return param1ArrayOfbyte;
/*      */     }
/*      */ 
/*      */     
/*      */     private AudioFloatConversion32B() {}
/*      */   }
/*      */ 
/*      */   
/*      */   private static class AudioFloatConversion8S
/*      */     extends AudioFloatConverter
/*      */   {
/*      */     private AudioFloatConversion8S() {}
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  320 */       int i = param1Int1;
/*  321 */       int j = param1Int2;
/*  322 */       for (byte b = 0; b < param1Int3; b++)
/*  323 */         param1ArrayOffloat[j++] = param1ArrayOfbyte[i++] * 0.007874016F; 
/*  324 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  329 */       int i = param1Int1;
/*  330 */       int j = param1Int3;
/*  331 */       for (byte b = 0; b < param1Int2; b++)
/*  332 */         param1ArrayOfbyte[j++] = (byte)(int)(param1ArrayOffloat[i++] * 127.0F); 
/*  333 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class AudioFloatConversion8U extends AudioFloatConverter {
/*      */     private AudioFloatConversion8U() {}
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  341 */       int i = param1Int1;
/*  342 */       int j = param1Int2;
/*  343 */       for (byte b = 0; b < param1Int3; b++) {
/*  344 */         param1ArrayOffloat[j++] = ((param1ArrayOfbyte[i++] & 0xFF) - 127) * 0.007874016F;
/*      */       }
/*  346 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  351 */       int i = param1Int1;
/*  352 */       int j = param1Int3;
/*  353 */       for (byte b = 0; b < param1Int2; b++)
/*  354 */         param1ArrayOfbyte[j++] = (byte)(int)(127.0F + param1ArrayOffloat[i++] * 127.0F); 
/*  355 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class AudioFloatConversion16SL
/*      */     extends AudioFloatConverter
/*      */   {
/*      */     private AudioFloatConversion16SL() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  369 */       int i = param1Int1;
/*  370 */       int j = param1Int2 + param1Int3;
/*  371 */       for (int k = param1Int2; k < j; k++) {
/*  372 */         param1ArrayOffloat[k] = (short)(param1ArrayOfbyte[i++] & 0xFF | param1ArrayOfbyte[i++] << 8) * 3.051851E-5F;
/*      */       }
/*      */ 
/*      */       
/*  376 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  381 */       int i = param1Int3;
/*  382 */       int j = param1Int1 + param1Int2;
/*  383 */       for (int k = param1Int1; k < j; k++) {
/*  384 */         int m = (int)(param1ArrayOffloat[k] * 32767.0D);
/*  385 */         param1ArrayOfbyte[i++] = (byte)m;
/*  386 */         param1ArrayOfbyte[i++] = (byte)(m >>> 8);
/*      */       } 
/*  388 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class AudioFloatConversion16SB extends AudioFloatConverter {
/*      */     private AudioFloatConversion16SB() {}
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  396 */       int i = param1Int1;
/*  397 */       int j = param1Int2;
/*  398 */       for (byte b = 0; b < param1Int3; b++) {
/*  399 */         param1ArrayOffloat[j++] = (short)(param1ArrayOfbyte[i++] << 8 | param1ArrayOfbyte[i++] & 0xFF) * 3.051851E-5F;
/*      */       }
/*      */       
/*  402 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  407 */       int i = param1Int1;
/*  408 */       int j = param1Int3;
/*  409 */       for (byte b = 0; b < param1Int2; b++) {
/*  410 */         int k = (int)(param1ArrayOffloat[i++] * 32767.0D);
/*  411 */         param1ArrayOfbyte[j++] = (byte)(k >>> 8);
/*  412 */         param1ArrayOfbyte[j++] = (byte)k;
/*      */       } 
/*  414 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class AudioFloatConversion16UL extends AudioFloatConverter {
/*      */     private AudioFloatConversion16UL() {}
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  422 */       int i = param1Int1;
/*  423 */       int j = param1Int2;
/*  424 */       for (byte b = 0; b < param1Int3; b++) {
/*  425 */         int k = param1ArrayOfbyte[i++] & 0xFF | (param1ArrayOfbyte[i++] & 0xFF) << 8;
/*  426 */         param1ArrayOffloat[j++] = (k - 32767) * 3.051851E-5F;
/*      */       } 
/*  428 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  433 */       int i = param1Int1;
/*  434 */       int j = param1Int3;
/*  435 */       for (byte b = 0; b < param1Int2; b++) {
/*  436 */         int k = 32767 + (int)(param1ArrayOffloat[i++] * 32767.0D);
/*  437 */         param1ArrayOfbyte[j++] = (byte)k;
/*  438 */         param1ArrayOfbyte[j++] = (byte)(k >>> 8);
/*      */       } 
/*  440 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class AudioFloatConversion16UB extends AudioFloatConverter {
/*      */     private AudioFloatConversion16UB() {}
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  448 */       int i = param1Int1;
/*  449 */       int j = param1Int2;
/*  450 */       for (byte b = 0; b < param1Int3; b++) {
/*  451 */         int k = (param1ArrayOfbyte[i++] & 0xFF) << 8 | param1ArrayOfbyte[i++] & 0xFF;
/*  452 */         param1ArrayOffloat[j++] = (k - 32767) * 3.051851E-5F;
/*      */       } 
/*  454 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  459 */       int i = param1Int1;
/*  460 */       int j = param1Int3;
/*  461 */       for (byte b = 0; b < param1Int2; b++) {
/*  462 */         int k = 32767 + (int)(param1ArrayOffloat[i++] * 32767.0D);
/*  463 */         param1ArrayOfbyte[j++] = (byte)(k >>> 8);
/*  464 */         param1ArrayOfbyte[j++] = (byte)k;
/*      */       } 
/*  466 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class AudioFloatConversion24SL
/*      */     extends AudioFloatConverter
/*      */   {
/*      */     private AudioFloatConversion24SL() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  480 */       int i = param1Int1;
/*  481 */       int j = param1Int2;
/*  482 */       for (byte b = 0; b < param1Int3; b++) {
/*  483 */         int k = param1ArrayOfbyte[i++] & 0xFF | (param1ArrayOfbyte[i++] & 0xFF) << 8 | (param1ArrayOfbyte[i++] & 0xFF) << 16;
/*      */         
/*  485 */         if (k > 8388607)
/*  486 */           k -= 16777216; 
/*  487 */         param1ArrayOffloat[j++] = k * 1.192093E-7F;
/*      */       } 
/*  489 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  494 */       int i = param1Int1;
/*  495 */       int j = param1Int3;
/*  496 */       for (byte b = 0; b < param1Int2; b++) {
/*  497 */         int k = (int)(param1ArrayOffloat[i++] * 8388607.0F);
/*  498 */         if (k < 0)
/*  499 */           k += 16777216; 
/*  500 */         param1ArrayOfbyte[j++] = (byte)k;
/*  501 */         param1ArrayOfbyte[j++] = (byte)(k >>> 8);
/*  502 */         param1ArrayOfbyte[j++] = (byte)(k >>> 16);
/*      */       } 
/*  504 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class AudioFloatConversion24SB extends AudioFloatConverter {
/*      */     private AudioFloatConversion24SB() {}
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  512 */       int i = param1Int1;
/*  513 */       int j = param1Int2;
/*  514 */       for (byte b = 0; b < param1Int3; b++) {
/*  515 */         int k = (param1ArrayOfbyte[i++] & 0xFF) << 16 | (param1ArrayOfbyte[i++] & 0xFF) << 8 | param1ArrayOfbyte[i++] & 0xFF;
/*      */         
/*  517 */         if (k > 8388607)
/*  518 */           k -= 16777216; 
/*  519 */         param1ArrayOffloat[j++] = k * 1.192093E-7F;
/*      */       } 
/*  521 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  526 */       int i = param1Int1;
/*  527 */       int j = param1Int3;
/*  528 */       for (byte b = 0; b < param1Int2; b++) {
/*  529 */         int k = (int)(param1ArrayOffloat[i++] * 8388607.0F);
/*  530 */         if (k < 0)
/*  531 */           k += 16777216; 
/*  532 */         param1ArrayOfbyte[j++] = (byte)(k >>> 16);
/*  533 */         param1ArrayOfbyte[j++] = (byte)(k >>> 8);
/*  534 */         param1ArrayOfbyte[j++] = (byte)k;
/*      */       } 
/*  536 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class AudioFloatConversion24UL extends AudioFloatConverter {
/*      */     private AudioFloatConversion24UL() {}
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  544 */       int i = param1Int1;
/*  545 */       int j = param1Int2;
/*  546 */       for (byte b = 0; b < param1Int3; b++) {
/*  547 */         int k = param1ArrayOfbyte[i++] & 0xFF | (param1ArrayOfbyte[i++] & 0xFF) << 8 | (param1ArrayOfbyte[i++] & 0xFF) << 16;
/*      */         
/*  549 */         k -= 8388607;
/*  550 */         param1ArrayOffloat[j++] = k * 1.192093E-7F;
/*      */       } 
/*  552 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  557 */       int i = param1Int1;
/*  558 */       int j = param1Int3;
/*  559 */       for (byte b = 0; b < param1Int2; b++) {
/*  560 */         int k = (int)(param1ArrayOffloat[i++] * 8388607.0F);
/*  561 */         k += 8388607;
/*  562 */         param1ArrayOfbyte[j++] = (byte)k;
/*  563 */         param1ArrayOfbyte[j++] = (byte)(k >>> 8);
/*  564 */         param1ArrayOfbyte[j++] = (byte)(k >>> 16);
/*      */       } 
/*  566 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class AudioFloatConversion24UB extends AudioFloatConverter {
/*      */     private AudioFloatConversion24UB() {}
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  574 */       int i = param1Int1;
/*  575 */       int j = param1Int2;
/*  576 */       for (byte b = 0; b < param1Int3; b++) {
/*  577 */         int k = (param1ArrayOfbyte[i++] & 0xFF) << 16 | (param1ArrayOfbyte[i++] & 0xFF) << 8 | param1ArrayOfbyte[i++] & 0xFF;
/*      */         
/*  579 */         k -= 8388607;
/*  580 */         param1ArrayOffloat[j++] = k * 1.192093E-7F;
/*      */       } 
/*  582 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  587 */       int i = param1Int1;
/*  588 */       int j = param1Int3;
/*  589 */       for (byte b = 0; b < param1Int2; b++) {
/*  590 */         int k = (int)(param1ArrayOffloat[i++] * 8388607.0F);
/*  591 */         k += 8388607;
/*  592 */         param1ArrayOfbyte[j++] = (byte)(k >>> 16);
/*  593 */         param1ArrayOfbyte[j++] = (byte)(k >>> 8);
/*  594 */         param1ArrayOfbyte[j++] = (byte)k;
/*      */       } 
/*  596 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class AudioFloatConversion32SL
/*      */     extends AudioFloatConverter
/*      */   {
/*      */     private AudioFloatConversion32SL() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  610 */       int i = param1Int1;
/*  611 */       int j = param1Int2;
/*  612 */       for (byte b = 0; b < param1Int3; b++) {
/*  613 */         int k = param1ArrayOfbyte[i++] & 0xFF | (param1ArrayOfbyte[i++] & 0xFF) << 8 | (param1ArrayOfbyte[i++] & 0xFF) << 16 | (param1ArrayOfbyte[i++] & 0xFF) << 24;
/*      */ 
/*      */         
/*  616 */         param1ArrayOffloat[j++] = k * 4.656613E-10F;
/*      */       } 
/*  618 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  623 */       int i = param1Int1;
/*  624 */       int j = param1Int3;
/*  625 */       for (byte b = 0; b < param1Int2; b++) {
/*  626 */         int k = (int)(param1ArrayOffloat[i++] * 2.14748365E9F);
/*  627 */         param1ArrayOfbyte[j++] = (byte)k;
/*  628 */         param1ArrayOfbyte[j++] = (byte)(k >>> 8);
/*  629 */         param1ArrayOfbyte[j++] = (byte)(k >>> 16);
/*  630 */         param1ArrayOfbyte[j++] = (byte)(k >>> 24);
/*      */       } 
/*  632 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class AudioFloatConversion32SB extends AudioFloatConverter {
/*      */     private AudioFloatConversion32SB() {}
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  640 */       int i = param1Int1;
/*  641 */       int j = param1Int2;
/*  642 */       for (byte b = 0; b < param1Int3; b++) {
/*  643 */         int k = (param1ArrayOfbyte[i++] & 0xFF) << 24 | (param1ArrayOfbyte[i++] & 0xFF) << 16 | (param1ArrayOfbyte[i++] & 0xFF) << 8 | param1ArrayOfbyte[i++] & 0xFF;
/*      */ 
/*      */         
/*  646 */         param1ArrayOffloat[j++] = k * 4.656613E-10F;
/*      */       } 
/*  648 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  653 */       int i = param1Int1;
/*  654 */       int j = param1Int3;
/*  655 */       for (byte b = 0; b < param1Int2; b++) {
/*  656 */         int k = (int)(param1ArrayOffloat[i++] * 2.14748365E9F);
/*  657 */         param1ArrayOfbyte[j++] = (byte)(k >>> 24);
/*  658 */         param1ArrayOfbyte[j++] = (byte)(k >>> 16);
/*  659 */         param1ArrayOfbyte[j++] = (byte)(k >>> 8);
/*  660 */         param1ArrayOfbyte[j++] = (byte)k;
/*      */       } 
/*  662 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class AudioFloatConversion32UL extends AudioFloatConverter {
/*      */     private AudioFloatConversion32UL() {}
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  670 */       int i = param1Int1;
/*  671 */       int j = param1Int2;
/*  672 */       for (byte b = 0; b < param1Int3; b++) {
/*  673 */         int k = param1ArrayOfbyte[i++] & 0xFF | (param1ArrayOfbyte[i++] & 0xFF) << 8 | (param1ArrayOfbyte[i++] & 0xFF) << 16 | (param1ArrayOfbyte[i++] & 0xFF) << 24;
/*      */ 
/*      */         
/*  676 */         k -= Integer.MAX_VALUE;
/*  677 */         param1ArrayOffloat[j++] = k * 4.656613E-10F;
/*      */       } 
/*  679 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  684 */       int i = param1Int1;
/*  685 */       int j = param1Int3;
/*  686 */       for (byte b = 0; b < param1Int2; b++) {
/*  687 */         int k = (int)(param1ArrayOffloat[i++] * 2.14748365E9F);
/*  688 */         k += Integer.MAX_VALUE;
/*  689 */         param1ArrayOfbyte[j++] = (byte)k;
/*  690 */         param1ArrayOfbyte[j++] = (byte)(k >>> 8);
/*  691 */         param1ArrayOfbyte[j++] = (byte)(k >>> 16);
/*  692 */         param1ArrayOfbyte[j++] = (byte)(k >>> 24);
/*      */       } 
/*  694 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class AudioFloatConversion32UB
/*      */     extends AudioFloatConverter {
/*      */     private AudioFloatConversion32UB() {}
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  703 */       int i = param1Int1;
/*  704 */       int j = param1Int2;
/*  705 */       for (byte b = 0; b < param1Int3; b++) {
/*  706 */         int k = (param1ArrayOfbyte[i++] & 0xFF) << 24 | (param1ArrayOfbyte[i++] & 0xFF) << 16 | (param1ArrayOfbyte[i++] & 0xFF) << 8 | param1ArrayOfbyte[i++] & 0xFF;
/*      */ 
/*      */         
/*  709 */         k -= Integer.MAX_VALUE;
/*  710 */         param1ArrayOffloat[j++] = k * 4.656613E-10F;
/*      */       } 
/*  712 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  717 */       int i = param1Int1;
/*  718 */       int j = param1Int3;
/*  719 */       for (byte b = 0; b < param1Int2; b++) {
/*  720 */         int k = (int)(param1ArrayOffloat[i++] * 2.14748365E9F);
/*  721 */         k += Integer.MAX_VALUE;
/*  722 */         param1ArrayOfbyte[j++] = (byte)(k >>> 24);
/*  723 */         param1ArrayOfbyte[j++] = (byte)(k >>> 16);
/*  724 */         param1ArrayOfbyte[j++] = (byte)(k >>> 8);
/*  725 */         param1ArrayOfbyte[j++] = (byte)k;
/*      */       } 
/*  727 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class AudioFloatConversion32xSL
/*      */     extends AudioFloatConverter
/*      */   {
/*      */     final int xbytes;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     AudioFloatConversion32xSL(int param1Int) {
/*  743 */       this.xbytes = param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  748 */       int i = param1Int1;
/*  749 */       int j = param1Int2;
/*  750 */       for (byte b = 0; b < param1Int3; b++) {
/*  751 */         i += this.xbytes;
/*  752 */         int k = param1ArrayOfbyte[i++] & 0xFF | (param1ArrayOfbyte[i++] & 0xFF) << 8 | (param1ArrayOfbyte[i++] & 0xFF) << 16 | (param1ArrayOfbyte[i++] & 0xFF) << 24;
/*      */ 
/*      */         
/*  755 */         param1ArrayOffloat[j++] = k * 4.656613E-10F;
/*      */       } 
/*  757 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  762 */       int i = param1Int1;
/*  763 */       int j = param1Int3;
/*  764 */       for (byte b = 0; b < param1Int2; b++) {
/*  765 */         int k = (int)(param1ArrayOffloat[i++] * 2.14748365E9F);
/*  766 */         for (byte b1 = 0; b1 < this.xbytes; b1++) {
/*  767 */           param1ArrayOfbyte[j++] = 0;
/*      */         }
/*  769 */         param1ArrayOfbyte[j++] = (byte)k;
/*  770 */         param1ArrayOfbyte[j++] = (byte)(k >>> 8);
/*  771 */         param1ArrayOfbyte[j++] = (byte)(k >>> 16);
/*  772 */         param1ArrayOfbyte[j++] = (byte)(k >>> 24);
/*      */       } 
/*  774 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class AudioFloatConversion32xSB
/*      */     extends AudioFloatConverter
/*      */   {
/*      */     final int xbytes;
/*      */     
/*      */     AudioFloatConversion32xSB(int param1Int) {
/*  784 */       this.xbytes = param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  789 */       int i = param1Int1;
/*  790 */       int j = param1Int2;
/*  791 */       for (byte b = 0; b < param1Int3; b++) {
/*  792 */         int k = (param1ArrayOfbyte[i++] & 0xFF) << 24 | (param1ArrayOfbyte[i++] & 0xFF) << 16 | (param1ArrayOfbyte[i++] & 0xFF) << 8 | param1ArrayOfbyte[i++] & 0xFF;
/*      */ 
/*      */ 
/*      */         
/*  796 */         i += this.xbytes;
/*  797 */         param1ArrayOffloat[j++] = k * 4.656613E-10F;
/*      */       } 
/*  799 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  804 */       int i = param1Int1;
/*  805 */       int j = param1Int3;
/*  806 */       for (byte b = 0; b < param1Int2; b++) {
/*  807 */         int k = (int)(param1ArrayOffloat[i++] * 2.14748365E9F);
/*  808 */         param1ArrayOfbyte[j++] = (byte)(k >>> 24);
/*  809 */         param1ArrayOfbyte[j++] = (byte)(k >>> 16);
/*  810 */         param1ArrayOfbyte[j++] = (byte)(k >>> 8);
/*  811 */         param1ArrayOfbyte[j++] = (byte)k;
/*  812 */         for (byte b1 = 0; b1 < this.xbytes; b1++) {
/*  813 */           param1ArrayOfbyte[j++] = 0;
/*      */         }
/*      */       } 
/*  816 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class AudioFloatConversion32xUL
/*      */     extends AudioFloatConverter
/*      */   {
/*      */     final int xbytes;
/*      */     
/*      */     AudioFloatConversion32xUL(int param1Int) {
/*  826 */       this.xbytes = param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  831 */       int i = param1Int1;
/*  832 */       int j = param1Int2;
/*  833 */       for (byte b = 0; b < param1Int3; b++) {
/*  834 */         i += this.xbytes;
/*  835 */         int k = param1ArrayOfbyte[i++] & 0xFF | (param1ArrayOfbyte[i++] & 0xFF) << 8 | (param1ArrayOfbyte[i++] & 0xFF) << 16 | (param1ArrayOfbyte[i++] & 0xFF) << 24;
/*      */ 
/*      */         
/*  838 */         k -= Integer.MAX_VALUE;
/*  839 */         param1ArrayOffloat[j++] = k * 4.656613E-10F;
/*      */       } 
/*  841 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  846 */       int i = param1Int1;
/*  847 */       int j = param1Int3;
/*  848 */       for (byte b = 0; b < param1Int2; b++) {
/*  849 */         int k = (int)(param1ArrayOffloat[i++] * 2.14748365E9F);
/*  850 */         k += Integer.MAX_VALUE;
/*  851 */         for (byte b1 = 0; b1 < this.xbytes; b1++) {
/*  852 */           param1ArrayOfbyte[j++] = 0;
/*      */         }
/*  854 */         param1ArrayOfbyte[j++] = (byte)k;
/*  855 */         param1ArrayOfbyte[j++] = (byte)(k >>> 8);
/*  856 */         param1ArrayOfbyte[j++] = (byte)(k >>> 16);
/*  857 */         param1ArrayOfbyte[j++] = (byte)(k >>> 24);
/*      */       } 
/*  859 */       return param1ArrayOfbyte;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class AudioFloatConversion32xUB
/*      */     extends AudioFloatConverter
/*      */   {
/*      */     final int xbytes;
/*      */     
/*      */     AudioFloatConversion32xUB(int param1Int) {
/*  869 */       this.xbytes = param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     public float[] toFloatArray(byte[] param1ArrayOfbyte, int param1Int1, float[] param1ArrayOffloat, int param1Int2, int param1Int3) {
/*  874 */       int i = param1Int1;
/*  875 */       int j = param1Int2;
/*  876 */       for (byte b = 0; b < param1Int3; b++) {
/*  877 */         int k = (param1ArrayOfbyte[i++] & 0xFF) << 24 | (param1ArrayOfbyte[i++] & 0xFF) << 16 | (param1ArrayOfbyte[i++] & 0xFF) << 8 | param1ArrayOfbyte[i++] & 0xFF;
/*      */ 
/*      */         
/*  880 */         i += this.xbytes;
/*  881 */         k -= Integer.MAX_VALUE;
/*  882 */         param1ArrayOffloat[j++] = k * 4.656613E-10F;
/*      */       } 
/*  884 */       return param1ArrayOffloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] toByteArray(float[] param1ArrayOffloat, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte, int param1Int3) {
/*  889 */       int i = param1Int1;
/*  890 */       int j = param1Int3;
/*  891 */       for (byte b = 0; b < param1Int2; b++) {
/*  892 */         int k = (int)(param1ArrayOffloat[i++] * 2.147483647E9D);
/*  893 */         k += Integer.MAX_VALUE;
/*  894 */         param1ArrayOfbyte[j++] = (byte)(k >>> 24);
/*  895 */         param1ArrayOfbyte[j++] = (byte)(k >>> 16);
/*  896 */         param1ArrayOfbyte[j++] = (byte)(k >>> 8);
/*  897 */         param1ArrayOfbyte[j++] = (byte)k;
/*  898 */         for (byte b1 = 0; b1 < this.xbytes; b1++) {
/*  899 */           param1ArrayOfbyte[j++] = 0;
/*      */         }
/*      */       } 
/*  902 */       return param1ArrayOfbyte;
/*      */     } }
/*      */   public static AudioFloatConverter getConverter(AudioFormat paramAudioFormat) {
/*      */     AudioFloatConversion64L audioFloatConversion64L;
/*      */     AudioFloatLSBFilter audioFloatLSBFilter;
/*  907 */     AudioFloatConversion8S audioFloatConversion8S = null;
/*  908 */     if (paramAudioFormat.getFrameSize() == 0)
/*  909 */       return null; 
/*  910 */     if (paramAudioFormat.getFrameSize() != (paramAudioFormat
/*  911 */       .getSampleSizeInBits() + 7) / 8 * paramAudioFormat.getChannels()) {
/*  912 */       return null;
/*      */     }
/*  914 */     if (paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED)) {
/*  915 */       if (paramAudioFormat.isBigEndian()) {
/*  916 */         if (paramAudioFormat.getSampleSizeInBits() <= 8) {
/*  917 */           audioFloatConversion8S = new AudioFloatConversion8S();
/*  918 */         } else if (paramAudioFormat.getSampleSizeInBits() > 8 && paramAudioFormat
/*  919 */           .getSampleSizeInBits() <= 16) {
/*  920 */           AudioFloatConversion16SB audioFloatConversion16SB = new AudioFloatConversion16SB();
/*  921 */         } else if (paramAudioFormat.getSampleSizeInBits() > 16 && paramAudioFormat
/*  922 */           .getSampleSizeInBits() <= 24) {
/*  923 */           AudioFloatConversion24SB audioFloatConversion24SB = new AudioFloatConversion24SB();
/*  924 */         } else if (paramAudioFormat.getSampleSizeInBits() > 24 && paramAudioFormat
/*  925 */           .getSampleSizeInBits() <= 32) {
/*  926 */           AudioFloatConversion32SB audioFloatConversion32SB = new AudioFloatConversion32SB();
/*  927 */         } else if (paramAudioFormat.getSampleSizeInBits() > 32) {
/*      */           
/*  929 */           AudioFloatConversion32xSB audioFloatConversion32xSB = new AudioFloatConversion32xSB((paramAudioFormat.getSampleSizeInBits() + 7) / 8 - 4);
/*      */         }
/*      */       
/*  932 */       } else if (paramAudioFormat.getSampleSizeInBits() <= 8) {
/*  933 */         audioFloatConversion8S = new AudioFloatConversion8S();
/*  934 */       } else if (paramAudioFormat.getSampleSizeInBits() > 8 && paramAudioFormat
/*  935 */         .getSampleSizeInBits() <= 16) {
/*  936 */         AudioFloatConversion16SL audioFloatConversion16SL = new AudioFloatConversion16SL();
/*  937 */       } else if (paramAudioFormat.getSampleSizeInBits() > 16 && paramAudioFormat
/*  938 */         .getSampleSizeInBits() <= 24) {
/*  939 */         AudioFloatConversion24SL audioFloatConversion24SL = new AudioFloatConversion24SL();
/*  940 */       } else if (paramAudioFormat.getSampleSizeInBits() > 24 && paramAudioFormat
/*  941 */         .getSampleSizeInBits() <= 32) {
/*  942 */         AudioFloatConversion32SL audioFloatConversion32SL = new AudioFloatConversion32SL();
/*  943 */       } else if (paramAudioFormat.getSampleSizeInBits() > 32) {
/*      */         
/*  945 */         AudioFloatConversion32xSL audioFloatConversion32xSL = new AudioFloatConversion32xSL((paramAudioFormat.getSampleSizeInBits() + 7) / 8 - 4);
/*      */       }
/*      */     
/*  948 */     } else if (paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED)) {
/*  949 */       if (paramAudioFormat.isBigEndian()) {
/*  950 */         if (paramAudioFormat.getSampleSizeInBits() <= 8) {
/*  951 */           AudioFloatConversion8U audioFloatConversion8U = new AudioFloatConversion8U();
/*  952 */         } else if (paramAudioFormat.getSampleSizeInBits() > 8 && paramAudioFormat
/*  953 */           .getSampleSizeInBits() <= 16) {
/*  954 */           AudioFloatConversion16UB audioFloatConversion16UB = new AudioFloatConversion16UB();
/*  955 */         } else if (paramAudioFormat.getSampleSizeInBits() > 16 && paramAudioFormat
/*  956 */           .getSampleSizeInBits() <= 24) {
/*  957 */           AudioFloatConversion24UB audioFloatConversion24UB = new AudioFloatConversion24UB();
/*  958 */         } else if (paramAudioFormat.getSampleSizeInBits() > 24 && paramAudioFormat
/*  959 */           .getSampleSizeInBits() <= 32) {
/*  960 */           AudioFloatConversion32UB audioFloatConversion32UB = new AudioFloatConversion32UB();
/*  961 */         } else if (paramAudioFormat.getSampleSizeInBits() > 32) {
/*      */           
/*  963 */           AudioFloatConversion32xUB audioFloatConversion32xUB = new AudioFloatConversion32xUB((paramAudioFormat.getSampleSizeInBits() + 7) / 8 - 4);
/*      */         }
/*      */       
/*  966 */       } else if (paramAudioFormat.getSampleSizeInBits() <= 8) {
/*  967 */         AudioFloatConversion8U audioFloatConversion8U = new AudioFloatConversion8U();
/*  968 */       } else if (paramAudioFormat.getSampleSizeInBits() > 8 && paramAudioFormat
/*  969 */         .getSampleSizeInBits() <= 16) {
/*  970 */         AudioFloatConversion16UL audioFloatConversion16UL = new AudioFloatConversion16UL();
/*  971 */       } else if (paramAudioFormat.getSampleSizeInBits() > 16 && paramAudioFormat
/*  972 */         .getSampleSizeInBits() <= 24) {
/*  973 */         AudioFloatConversion24UL audioFloatConversion24UL = new AudioFloatConversion24UL();
/*  974 */       } else if (paramAudioFormat.getSampleSizeInBits() > 24 && paramAudioFormat
/*  975 */         .getSampleSizeInBits() <= 32) {
/*  976 */         AudioFloatConversion32UL audioFloatConversion32UL = new AudioFloatConversion32UL();
/*  977 */       } else if (paramAudioFormat.getSampleSizeInBits() > 32) {
/*      */         
/*  979 */         AudioFloatConversion32xUL audioFloatConversion32xUL = new AudioFloatConversion32xUL((paramAudioFormat.getSampleSizeInBits() + 7) / 8 - 4);
/*      */       }
/*      */     
/*  982 */     } else if (paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_FLOAT)) {
/*  983 */       if (paramAudioFormat.getSampleSizeInBits() == 32) {
/*  984 */         if (paramAudioFormat.isBigEndian())
/*  985 */         { AudioFloatConversion32B audioFloatConversion32B = new AudioFloatConversion32B(); }
/*      */         else
/*  987 */         { AudioFloatConversion32L audioFloatConversion32L = new AudioFloatConversion32L(); } 
/*  988 */       } else if (paramAudioFormat.getSampleSizeInBits() == 64) {
/*  989 */         if (paramAudioFormat.isBigEndian()) {
/*  990 */           AudioFloatConversion64B audioFloatConversion64B = new AudioFloatConversion64B();
/*      */         } else {
/*  992 */           audioFloatConversion64L = new AudioFloatConversion64L();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  997 */     if ((paramAudioFormat.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED) || paramAudioFormat
/*  998 */       .getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED)) && paramAudioFormat
/*  999 */       .getSampleSizeInBits() % 8 != 0) {
/* 1000 */       audioFloatLSBFilter = new AudioFloatLSBFilter(audioFloatConversion64L, paramAudioFormat);
/*      */     }
/*      */     
/* 1003 */     if (audioFloatLSBFilter != null)
/* 1004 */       audioFloatLSBFilter.format = paramAudioFormat; 
/* 1005 */     return audioFloatLSBFilter;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final AudioFormat getFormat() {
/* 1011 */     return this.format;
/*      */   }
/*      */ 
/*      */   
/*      */   public abstract float[] toFloatArray(byte[] paramArrayOfbyte, int paramInt1, float[] paramArrayOffloat, int paramInt2, int paramInt3);
/*      */ 
/*      */   
/*      */   public final float[] toFloatArray(byte[] paramArrayOfbyte, float[] paramArrayOffloat, int paramInt1, int paramInt2) {
/* 1019 */     return toFloatArray(paramArrayOfbyte, 0, paramArrayOffloat, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public final float[] toFloatArray(byte[] paramArrayOfbyte, int paramInt1, float[] paramArrayOffloat, int paramInt2) {
/* 1024 */     return toFloatArray(paramArrayOfbyte, paramInt1, paramArrayOffloat, 0, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public final float[] toFloatArray(byte[] paramArrayOfbyte, float[] paramArrayOffloat, int paramInt) {
/* 1029 */     return toFloatArray(paramArrayOfbyte, 0, paramArrayOffloat, 0, paramInt);
/*      */   }
/*      */   
/*      */   public final float[] toFloatArray(byte[] paramArrayOfbyte, float[] paramArrayOffloat) {
/* 1033 */     return toFloatArray(paramArrayOfbyte, 0, paramArrayOffloat, 0, paramArrayOffloat.length);
/*      */   }
/*      */ 
/*      */   
/*      */   public abstract byte[] toByteArray(float[] paramArrayOffloat, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3);
/*      */ 
/*      */   
/*      */   public final byte[] toByteArray(float[] paramArrayOffloat, int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
/* 1041 */     return toByteArray(paramArrayOffloat, 0, paramInt1, paramArrayOfbyte, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public final byte[] toByteArray(float[] paramArrayOffloat, int paramInt1, int paramInt2, byte[] paramArrayOfbyte) {
/* 1046 */     return toByteArray(paramArrayOffloat, paramInt1, paramInt2, paramArrayOfbyte, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public final byte[] toByteArray(float[] paramArrayOffloat, int paramInt, byte[] paramArrayOfbyte) {
/* 1051 */     return toByteArray(paramArrayOffloat, 0, paramInt, paramArrayOfbyte, 0);
/*      */   }
/*      */   
/*      */   public final byte[] toByteArray(float[] paramArrayOffloat, byte[] paramArrayOfbyte) {
/* 1055 */     return toByteArray(paramArrayOffloat, 0, paramArrayOffloat.length, paramArrayOfbyte, 0);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/AudioFloatConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */