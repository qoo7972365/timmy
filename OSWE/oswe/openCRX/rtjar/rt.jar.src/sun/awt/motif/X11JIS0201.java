/*    */ package sun.awt.motif;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.CharBuffer;
/*    */ import java.nio.charset.Charset;
/*    */ import java.nio.charset.CharsetDecoder;
/*    */ import java.nio.charset.CharsetEncoder;
/*    */ import java.nio.charset.CoderResult;
/*    */ import sun.nio.cs.SingleByte;
/*    */ import sun.nio.cs.Surrogate;
/*    */ import sun.nio.cs.ext.JIS_X_0201;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class X11JIS0201
/*    */   extends Charset
/*    */ {
/* 37 */   private static Charset jis0201 = new JIS_X_0201();
/* 38 */   private static SingleByte.Encoder enc = (SingleByte.Encoder)jis0201
/* 39 */     .newEncoder();
/*    */   
/*    */   public X11JIS0201() {
/* 42 */     super("X11JIS0201", null);
/*    */   }
/*    */   
/*    */   public CharsetEncoder newEncoder() {
/* 46 */     return new Encoder(this);
/*    */   }
/*    */   
/*    */   public CharsetDecoder newDecoder() {
/* 50 */     return jis0201.newDecoder();
/*    */   }
/*    */   
/*    */   public boolean contains(Charset paramCharset) {
/* 54 */     return paramCharset instanceof X11JIS0201;
/*    */   }
/*    */   
/*    */   private class Encoder
/*    */     extends CharsetEncoder {
/*    */     public Encoder(Charset param1Charset) {
/* 60 */       super(param1Charset, 1.0F, 1.0F);
/*    */     }
/*    */     private Surrogate.Parser sgp;
/*    */     public boolean canEncode(char param1Char) {
/* 64 */       if ((param1Char >= '｡' && param1Char <= 'ﾟ') || param1Char == '‾' || param1Char == '¥')
/*    */       {
/*    */         
/* 67 */         return true;
/*    */       }
/* 69 */       return false;
/*    */     }
/*    */     
/*    */     protected CoderResult encodeLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) {
/*    */       // Byte code:
/*    */       //   0: aload_1
/*    */       //   1: invokevirtual array : ()[C
/*    */       //   4: astore_3
/*    */       //   5: aload_1
/*    */       //   6: invokevirtual arrayOffset : ()I
/*    */       //   9: aload_1
/*    */       //   10: invokevirtual position : ()I
/*    */       //   13: iadd
/*    */       //   14: istore #4
/*    */       //   16: aload_1
/*    */       //   17: invokevirtual arrayOffset : ()I
/*    */       //   20: aload_1
/*    */       //   21: invokevirtual limit : ()I
/*    */       //   24: iadd
/*    */       //   25: istore #5
/*    */       //   27: aload_2
/*    */       //   28: invokevirtual array : ()[B
/*    */       //   31: astore #6
/*    */       //   33: aload_2
/*    */       //   34: invokevirtual arrayOffset : ()I
/*    */       //   37: aload_2
/*    */       //   38: invokevirtual position : ()I
/*    */       //   41: iadd
/*    */       //   42: istore #7
/*    */       //   44: aload_2
/*    */       //   45: invokevirtual arrayOffset : ()I
/*    */       //   48: aload_2
/*    */       //   49: invokevirtual limit : ()I
/*    */       //   52: iadd
/*    */       //   53: istore #8
/*    */       //   55: getstatic java/nio/charset/CoderResult.UNDERFLOW : Ljava/nio/charset/CoderResult;
/*    */       //   58: astore #9
/*    */       //   60: iload #8
/*    */       //   62: iload #7
/*    */       //   64: isub
/*    */       //   65: iload #5
/*    */       //   67: iload #4
/*    */       //   69: isub
/*    */       //   70: if_icmpge -> 88
/*    */       //   73: iload #4
/*    */       //   75: iload #8
/*    */       //   77: iload #7
/*    */       //   79: isub
/*    */       //   80: iadd
/*    */       //   81: istore #5
/*    */       //   83: getstatic java/nio/charset/CoderResult.OVERFLOW : Ljava/nio/charset/CoderResult;
/*    */       //   86: astore #9
/*    */       //   88: iload #4
/*    */       //   90: iload #5
/*    */       //   92: if_icmpge -> 244
/*    */       //   95: aload_3
/*    */       //   96: iload #4
/*    */       //   98: caload
/*    */       //   99: istore #10
/*    */       //   101: invokestatic access$000 : ()Lsun/nio/cs/SingleByte$Encoder;
/*    */       //   104: iload #10
/*    */       //   106: invokevirtual encode : (C)I
/*    */       //   109: istore #11
/*    */       //   111: iload #11
/*    */       //   113: ldc 65533
/*    */       //   115: if_icmpne -> 227
/*    */       //   118: iload #10
/*    */       //   120: invokestatic isSurrogate : (C)Z
/*    */       //   123: ifeq -> 194
/*    */       //   126: aload_0
/*    */       //   127: getfield sgp : Lsun/nio/cs/Surrogate$Parser;
/*    */       //   130: ifnonnull -> 144
/*    */       //   133: aload_0
/*    */       //   134: new sun/nio/cs/Surrogate$Parser
/*    */       //   137: dup
/*    */       //   138: invokespecial <init> : ()V
/*    */       //   141: putfield sgp : Lsun/nio/cs/Surrogate$Parser;
/*    */       //   144: aload_0
/*    */       //   145: getfield sgp : Lsun/nio/cs/Surrogate$Parser;
/*    */       //   148: iload #10
/*    */       //   150: aload_3
/*    */       //   151: iload #4
/*    */       //   153: iload #5
/*    */       //   155: invokevirtual parse : (C[CII)I
/*    */       //   158: iflt -> 194
/*    */       //   161: iconst_2
/*    */       //   162: invokestatic unmappableForLength : (I)Ljava/nio/charset/CoderResult;
/*    */       //   165: astore #12
/*    */       //   167: aload_1
/*    */       //   168: iload #4
/*    */       //   170: aload_1
/*    */       //   171: invokevirtual arrayOffset : ()I
/*    */       //   174: isub
/*    */       //   175: invokevirtual position : (I)Ljava/nio/Buffer;
/*    */       //   178: pop
/*    */       //   179: aload_2
/*    */       //   180: iload #7
/*    */       //   182: aload_2
/*    */       //   183: invokevirtual arrayOffset : ()I
/*    */       //   186: isub
/*    */       //   187: invokevirtual position : (I)Ljava/nio/Buffer;
/*    */       //   190: pop
/*    */       //   191: aload #12
/*    */       //   193: areturn
/*    */       //   194: iconst_1
/*    */       //   195: invokestatic unmappableForLength : (I)Ljava/nio/charset/CoderResult;
/*    */       //   198: astore #12
/*    */       //   200: aload_1
/*    */       //   201: iload #4
/*    */       //   203: aload_1
/*    */       //   204: invokevirtual arrayOffset : ()I
/*    */       //   207: isub
/*    */       //   208: invokevirtual position : (I)Ljava/nio/Buffer;
/*    */       //   211: pop
/*    */       //   212: aload_2
/*    */       //   213: iload #7
/*    */       //   215: aload_2
/*    */       //   216: invokevirtual arrayOffset : ()I
/*    */       //   219: isub
/*    */       //   220: invokevirtual position : (I)Ljava/nio/Buffer;
/*    */       //   223: pop
/*    */       //   224: aload #12
/*    */       //   226: areturn
/*    */       //   227: aload #6
/*    */       //   229: iload #7
/*    */       //   231: iinc #7, 1
/*    */       //   234: iload #11
/*    */       //   236: i2b
/*    */       //   237: bastore
/*    */       //   238: iinc #4, 1
/*    */       //   241: goto -> 88
/*    */       //   244: aload #9
/*    */       //   246: astore #10
/*    */       //   248: aload_1
/*    */       //   249: iload #4
/*    */       //   251: aload_1
/*    */       //   252: invokevirtual arrayOffset : ()I
/*    */       //   255: isub
/*    */       //   256: invokevirtual position : (I)Ljava/nio/Buffer;
/*    */       //   259: pop
/*    */       //   260: aload_2
/*    */       //   261: iload #7
/*    */       //   263: aload_2
/*    */       //   264: invokevirtual arrayOffset : ()I
/*    */       //   267: isub
/*    */       //   268: invokevirtual position : (I)Ljava/nio/Buffer;
/*    */       //   271: pop
/*    */       //   272: aload #10
/*    */       //   274: areturn
/*    */       //   275: astore #13
/*    */       //   277: aload_1
/*    */       //   278: iload #4
/*    */       //   280: aload_1
/*    */       //   281: invokevirtual arrayOffset : ()I
/*    */       //   284: isub
/*    */       //   285: invokevirtual position : (I)Ljava/nio/Buffer;
/*    */       //   288: pop
/*    */       //   289: aload_2
/*    */       //   290: iload #7
/*    */       //   292: aload_2
/*    */       //   293: invokevirtual arrayOffset : ()I
/*    */       //   296: isub
/*    */       //   297: invokevirtual position : (I)Ljava/nio/Buffer;
/*    */       //   300: pop
/*    */       //   301: aload #13
/*    */       //   303: athrow
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #74	-> 0
/*    */       //   #75	-> 5
/*    */       //   #76	-> 16
/*    */       //   #78	-> 27
/*    */       //   #79	-> 33
/*    */       //   #80	-> 44
/*    */       //   #81	-> 55
/*    */       //   #82	-> 60
/*    */       //   #83	-> 73
/*    */       //   #84	-> 83
/*    */       //   #87	-> 88
/*    */       //   #88	-> 95
/*    */       //   #89	-> 101
/*    */       //   #90	-> 111
/*    */       //   #91	-> 118
/*    */       //   #92	-> 126
/*    */       //   #93	-> 133
/*    */       //   #94	-> 144
/*    */       //   #95	-> 161
/*    */       //   #104	-> 167
/*    */       //   #105	-> 179
/*    */       //   #95	-> 191
/*    */       //   #97	-> 194
/*    */       //   #104	-> 200
/*    */       //   #105	-> 212
/*    */       //   #97	-> 224
/*    */       //   #99	-> 227
/*    */       //   #100	-> 238
/*    */       //   #101	-> 241
/*    */       //   #102	-> 244
/*    */       //   #104	-> 248
/*    */       //   #105	-> 260
/*    */       //   #102	-> 272
/*    */       //   #104	-> 275
/*    */       //   #105	-> 289
/*    */       //   #106	-> 301
/*    */       // Exception table:
/*    */       //   from	to	target	type
/*    */       //   88	167	275	finally
/*    */       //   194	200	275	finally
/*    */       //   227	248	275	finally
/*    */       //   275	277	275	finally
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/motif/X11JIS0201.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */