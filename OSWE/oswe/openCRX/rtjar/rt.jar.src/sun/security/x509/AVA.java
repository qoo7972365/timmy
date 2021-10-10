/*      */ package sun.security.x509;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Reader;
/*      */ import java.security.AccessController;
/*      */ import java.text.Normalizer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import sun.security.action.GetBooleanAction;
/*      */ import sun.security.pkcs.PKCS9Attribute;
/*      */ import sun.security.util.Debug;
/*      */ import sun.security.util.DerEncoder;
/*      */ import sun.security.util.DerInputStream;
/*      */ import sun.security.util.DerOutputStream;
/*      */ import sun.security.util.DerValue;
/*      */ import sun.security.util.ObjectIdentifier;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class AVA
/*      */   implements DerEncoder
/*      */ {
/*   63 */   private static final Debug debug = Debug.getInstance("x509", "\t[AVA]");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   68 */   private static final boolean PRESERVE_OLD_DC_ENCODING = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("com.sun.security.preserveOldDCEncoding"))).booleanValue();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int DEFAULT = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int RFC1779 = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int RFC2253 = 3;
/*      */ 
/*      */ 
/*      */   
/*      */   final ObjectIdentifier oid;
/*      */ 
/*      */ 
/*      */   
/*      */   final DerValue value;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String specialChars1779 = ",=\n+<>#;\\\"";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String specialChars2253 = ",=+<>#;\\\"";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String specialCharsDefault = ",=\n+<>#;\\\" ";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String escapedDefault = ",+<>;\"";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String hexDigits = "0123456789ABCDEF";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AVA(ObjectIdentifier paramObjectIdentifier, DerValue paramDerValue) {
/*  117 */     if (paramObjectIdentifier == null || paramDerValue == null) {
/*  118 */       throw new NullPointerException();
/*      */     }
/*  120 */     this.oid = paramObjectIdentifier;
/*  121 */     this.value = paramDerValue;
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
/*      */   AVA(Reader paramReader) throws IOException {
/*  134 */     this(paramReader, 1);
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
/*      */   AVA(Reader paramReader, Map<String, String> paramMap) throws IOException {
/*  147 */     this(paramReader, 1, paramMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   AVA(Reader paramReader, int paramInt) throws IOException {
/*  154 */     this(paramReader, paramInt, Collections.emptyMap());
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
/*      */   AVA(Reader paramReader, int paramInt, Map<String, String> paramMap) throws IOException {
/*      */     int i;
/*  173 */     StringBuilder stringBuilder = new StringBuilder();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  181 */       i = readChar(paramReader, "Incorrect AVA format");
/*  182 */       if (i == 61) {
/*      */         break;
/*      */       }
/*  185 */       stringBuilder.append((char)i);
/*      */     } 
/*      */     
/*  188 */     this.oid = AVAKeyword.getOID(stringBuilder.toString(), paramInt, paramMap);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  195 */     stringBuilder.setLength(0);
/*  196 */     if (paramInt == 3) {
/*      */       
/*  198 */       i = paramReader.read();
/*  199 */       if (i == 32) {
/*  200 */         throw new IOException("Incorrect AVA RFC2253 format - leading space must be escaped");
/*      */       }
/*      */     } else {
/*      */ 
/*      */       
/*      */       do {
/*  206 */         i = paramReader.read();
/*  207 */       } while (i == 32 || i == 10);
/*      */     } 
/*  209 */     if (i == -1) {
/*      */       
/*  211 */       this.value = new DerValue("");
/*      */       
/*      */       return;
/*      */     } 
/*  215 */     if (i == 35) {
/*  216 */       this.value = parseHexString(paramReader, paramInt);
/*  217 */     } else if (i == 34 && paramInt != 3) {
/*  218 */       this.value = parseQuotedString(paramReader, stringBuilder);
/*      */     } else {
/*  220 */       this.value = parseString(paramReader, i, paramInt, stringBuilder);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectIdentifier getObjectIdentifier() {
/*  228 */     return this.oid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerValue getDerValue() {
/*  235 */     return this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getValueString() {
/*      */     try {
/*  246 */       String str = this.value.getAsString();
/*  247 */       if (str == null) {
/*  248 */         throw new RuntimeException("AVA string is null");
/*      */       }
/*  250 */       return str;
/*  251 */     } catch (IOException iOException) {
/*      */       
/*  253 */       throw new RuntimeException("AVA error: " + iOException, iOException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static DerValue parseHexString(Reader paramReader, int paramInt) throws IOException {
/*  261 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*  262 */     byte b = 0;
/*  263 */     byte b1 = 0;
/*      */     while (true) {
/*  265 */       int i = paramReader.read();
/*      */       
/*  267 */       if (isTerminator(i, paramInt)) {
/*      */         break;
/*      */       }
/*      */       
/*  271 */       int j = "0123456789ABCDEF".indexOf(Character.toUpperCase((char)i));
/*      */       
/*  273 */       if (j == -1) {
/*  274 */         throw new IOException("AVA parse, invalid hex digit: " + (char)i);
/*      */       }
/*      */ 
/*      */       
/*  278 */       if (b1 % 2 == 1) {
/*  279 */         b = (byte)(b * 16 + (byte)j);
/*  280 */         byteArrayOutputStream.write(b);
/*      */       } else {
/*  282 */         b = (byte)j;
/*      */       } 
/*  284 */       b1++;
/*      */     } 
/*      */ 
/*      */     
/*  288 */     if (b1 == 0) {
/*  289 */       throw new IOException("AVA parse, zero hex digits");
/*      */     }
/*      */ 
/*      */     
/*  293 */     if (b1 % 2 == 1) {
/*  294 */       throw new IOException("AVA parse, odd number of hex digits");
/*      */     }
/*      */     
/*  297 */     return new DerValue(byteArrayOutputStream.toByteArray());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DerValue parseQuotedString(Reader paramReader, StringBuilder paramStringBuilder) throws IOException {
/*  308 */     int i = readChar(paramReader, "Quoted string did not end in quote");
/*      */     
/*  310 */     ArrayList<Byte> arrayList = new ArrayList();
/*  311 */     boolean bool = true;
/*  312 */     while (i != 34) {
/*  313 */       if (i == 92) {
/*  314 */         i = readChar(paramReader, "Quoted string did not end in quote");
/*      */ 
/*      */         
/*  317 */         Byte byte_ = null;
/*  318 */         if ((byte_ = getEmbeddedHexPair(i, paramReader)) != null) {
/*      */ 
/*      */           
/*  321 */           bool = false;
/*      */ 
/*      */ 
/*      */           
/*  325 */           arrayList.add(byte_);
/*  326 */           i = paramReader.read();
/*      */           
/*      */           continue;
/*      */         } 
/*  330 */         if (",=\n+<>#;\\\"".indexOf((char)i) < 0) {
/*  331 */           throw new IOException("Invalid escaped character in AVA: " + (char)i);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  338 */       if (arrayList.size() > 0) {
/*  339 */         String str = getEmbeddedHexString(arrayList);
/*  340 */         paramStringBuilder.append(str);
/*  341 */         arrayList.clear();
/*      */       } 
/*      */ 
/*      */       
/*  345 */       bool &= DerValue.isPrintableStringChar((char)i);
/*  346 */       paramStringBuilder.append((char)i);
/*  347 */       i = readChar(paramReader, "Quoted string did not end in quote");
/*      */     } 
/*      */ 
/*      */     
/*  351 */     if (arrayList.size() > 0) {
/*  352 */       String str = getEmbeddedHexString(arrayList);
/*  353 */       paramStringBuilder.append(str);
/*  354 */       arrayList.clear();
/*      */     } 
/*      */     
/*      */     while (true) {
/*  358 */       i = paramReader.read();
/*  359 */       if (i != 10 && i != 32) {
/*  360 */         if (i != -1) {
/*  361 */           throw new IOException("AVA had characters other than whitespace after terminating quote");
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  367 */         if (this.oid.equals(PKCS9Attribute.EMAIL_ADDRESS_OID) || (this.oid
/*  368 */           .equals(X500Name.DOMAIN_COMPONENT_OID) && !PRESERVE_OLD_DC_ENCODING))
/*      */         {
/*      */           
/*  371 */           return new DerValue((byte)22, paramStringBuilder
/*  372 */               .toString().trim()); } 
/*  373 */         if (bool) {
/*  374 */           return new DerValue(paramStringBuilder.toString().trim());
/*      */         }
/*  376 */         return new DerValue((byte)12, paramStringBuilder
/*  377 */             .toString().trim());
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DerValue parseString(Reader paramReader, int paramInt1, int paramInt2, StringBuilder paramStringBuilder) throws IOException {
/*      */     // Byte code:
/*      */     //   0: new java/util/ArrayList
/*      */     //   3: dup
/*      */     //   4: invokespecial <init> : ()V
/*      */     //   7: astore #5
/*      */     //   9: iconst_1
/*      */     //   10: istore #6
/*      */     //   12: iconst_0
/*      */     //   13: istore #7
/*      */     //   15: iconst_1
/*      */     //   16: istore #8
/*      */     //   18: iconst_0
/*      */     //   19: istore #9
/*      */     //   21: iconst_0
/*      */     //   22: istore #7
/*      */     //   24: iload_2
/*      */     //   25: bipush #92
/*      */     //   27: if_icmpne -> 228
/*      */     //   30: iconst_1
/*      */     //   31: istore #7
/*      */     //   33: aload_1
/*      */     //   34: ldc 'Invalid trailing backslash'
/*      */     //   36: invokestatic readChar : (Ljava/io/Reader;Ljava/lang/String;)I
/*      */     //   39: istore_2
/*      */     //   40: aconst_null
/*      */     //   41: astore #10
/*      */     //   43: iload_2
/*      */     //   44: aload_1
/*      */     //   45: invokestatic getEmbeddedHexPair : (ILjava/io/Reader;)Ljava/lang/Byte;
/*      */     //   48: dup
/*      */     //   49: astore #10
/*      */     //   51: ifnull -> 78
/*      */     //   54: iconst_0
/*      */     //   55: istore #6
/*      */     //   57: aload #5
/*      */     //   59: aload #10
/*      */     //   61: invokeinterface add : (Ljava/lang/Object;)Z
/*      */     //   66: pop
/*      */     //   67: aload_1
/*      */     //   68: invokevirtual read : ()I
/*      */     //   71: istore_2
/*      */     //   72: iconst_0
/*      */     //   73: istore #8
/*      */     //   75: goto -> 450
/*      */     //   78: iload_3
/*      */     //   79: iconst_1
/*      */     //   80: if_icmpne -> 127
/*      */     //   83: ldc ',=\\n+<>#;\" '
/*      */     //   85: iload_2
/*      */     //   86: i2c
/*      */     //   87: invokevirtual indexOf : (I)I
/*      */     //   90: iconst_m1
/*      */     //   91: if_icmpne -> 127
/*      */     //   94: new java/io/IOException
/*      */     //   97: dup
/*      */     //   98: new java/lang/StringBuilder
/*      */     //   101: dup
/*      */     //   102: invokespecial <init> : ()V
/*      */     //   105: ldc 'Invalid escaped character in AVA: ''
/*      */     //   107: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   110: iload_2
/*      */     //   111: i2c
/*      */     //   112: invokevirtual append : (C)Ljava/lang/StringBuilder;
/*      */     //   115: ldc '''
/*      */     //   117: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   120: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   123: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   126: athrow
/*      */     //   127: iload_3
/*      */     //   128: iconst_3
/*      */     //   129: if_icmpne -> 225
/*      */     //   132: iload_2
/*      */     //   133: bipush #32
/*      */     //   135: if_icmpne -> 160
/*      */     //   138: iload #8
/*      */     //   140: ifne -> 225
/*      */     //   143: aload_1
/*      */     //   144: invokestatic trailingSpace : (Ljava/io/Reader;)Z
/*      */     //   147: ifne -> 225
/*      */     //   150: new java/io/IOException
/*      */     //   153: dup
/*      */     //   154: ldc 'Invalid escaped space character in AVA.  Only a leading or trailing space character can be escaped.'
/*      */     //   156: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   159: athrow
/*      */     //   160: iload_2
/*      */     //   161: bipush #35
/*      */     //   163: if_icmpne -> 181
/*      */     //   166: iload #8
/*      */     //   168: ifne -> 225
/*      */     //   171: new java/io/IOException
/*      */     //   174: dup
/*      */     //   175: ldc 'Invalid escaped '#' character in AVA.  Only a leading '#' can be escaped.'
/*      */     //   177: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   180: athrow
/*      */     //   181: ldc ',=+<>#;\"'
/*      */     //   183: iload_2
/*      */     //   184: i2c
/*      */     //   185: invokevirtual indexOf : (I)I
/*      */     //   188: iconst_m1
/*      */     //   189: if_icmpne -> 225
/*      */     //   192: new java/io/IOException
/*      */     //   195: dup
/*      */     //   196: new java/lang/StringBuilder
/*      */     //   199: dup
/*      */     //   200: invokespecial <init> : ()V
/*      */     //   203: ldc 'Invalid escaped character in AVA: ''
/*      */     //   205: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   208: iload_2
/*      */     //   209: i2c
/*      */     //   210: invokevirtual append : (C)Ljava/lang/StringBuilder;
/*      */     //   213: ldc '''
/*      */     //   215: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   218: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   221: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   224: athrow
/*      */     //   225: goto -> 321
/*      */     //   228: iload_3
/*      */     //   229: iconst_3
/*      */     //   230: if_icmpne -> 277
/*      */     //   233: ldc ',=+<>#;\"'
/*      */     //   235: iload_2
/*      */     //   236: i2c
/*      */     //   237: invokevirtual indexOf : (I)I
/*      */     //   240: iconst_m1
/*      */     //   241: if_icmpeq -> 321
/*      */     //   244: new java/io/IOException
/*      */     //   247: dup
/*      */     //   248: new java/lang/StringBuilder
/*      */     //   251: dup
/*      */     //   252: invokespecial <init> : ()V
/*      */     //   255: ldc 'Character ''
/*      */     //   257: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   260: iload_2
/*      */     //   261: i2c
/*      */     //   262: invokevirtual append : (C)Ljava/lang/StringBuilder;
/*      */     //   265: ldc '' in AVA appears without escape'
/*      */     //   267: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   270: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   273: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   276: athrow
/*      */     //   277: ldc ',+<>;"'
/*      */     //   279: iload_2
/*      */     //   280: i2c
/*      */     //   281: invokevirtual indexOf : (I)I
/*      */     //   284: iconst_m1
/*      */     //   285: if_icmpeq -> 321
/*      */     //   288: new java/io/IOException
/*      */     //   291: dup
/*      */     //   292: new java/lang/StringBuilder
/*      */     //   295: dup
/*      */     //   296: invokespecial <init> : ()V
/*      */     //   299: ldc 'Character ''
/*      */     //   301: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   304: iload_2
/*      */     //   305: i2c
/*      */     //   306: invokevirtual append : (C)Ljava/lang/StringBuilder;
/*      */     //   309: ldc '' in AVA appears without escape'
/*      */     //   311: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   314: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   317: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   320: athrow
/*      */     //   321: aload #5
/*      */     //   323: invokeinterface size : ()I
/*      */     //   328: ifle -> 380
/*      */     //   331: iconst_0
/*      */     //   332: istore #10
/*      */     //   334: iload #10
/*      */     //   336: iload #9
/*      */     //   338: if_icmpge -> 355
/*      */     //   341: aload #4
/*      */     //   343: ldc ' '
/*      */     //   345: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   348: pop
/*      */     //   349: iinc #10, 1
/*      */     //   352: goto -> 334
/*      */     //   355: iconst_0
/*      */     //   356: istore #9
/*      */     //   358: aload #5
/*      */     //   360: invokestatic getEmbeddedHexString : (Ljava/util/List;)Ljava/lang/String;
/*      */     //   363: astore #10
/*      */     //   365: aload #4
/*      */     //   367: aload #10
/*      */     //   369: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   372: pop
/*      */     //   373: aload #5
/*      */     //   375: invokeinterface clear : ()V
/*      */     //   380: iload #6
/*      */     //   382: iload_2
/*      */     //   383: i2c
/*      */     //   384: invokestatic isPrintableStringChar : (C)Z
/*      */     //   387: iand
/*      */     //   388: istore #6
/*      */     //   390: iload_2
/*      */     //   391: bipush #32
/*      */     //   393: if_icmpne -> 407
/*      */     //   396: iload #7
/*      */     //   398: ifne -> 407
/*      */     //   401: iinc #9, 1
/*      */     //   404: goto -> 442
/*      */     //   407: iconst_0
/*      */     //   408: istore #10
/*      */     //   410: iload #10
/*      */     //   412: iload #9
/*      */     //   414: if_icmpge -> 431
/*      */     //   417: aload #4
/*      */     //   419: ldc ' '
/*      */     //   421: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   424: pop
/*      */     //   425: iinc #10, 1
/*      */     //   428: goto -> 410
/*      */     //   431: iconst_0
/*      */     //   432: istore #9
/*      */     //   434: aload #4
/*      */     //   436: iload_2
/*      */     //   437: i2c
/*      */     //   438: invokevirtual append : (C)Ljava/lang/StringBuilder;
/*      */     //   441: pop
/*      */     //   442: aload_1
/*      */     //   443: invokevirtual read : ()I
/*      */     //   446: istore_2
/*      */     //   447: iconst_0
/*      */     //   448: istore #8
/*      */     //   450: iload_2
/*      */     //   451: iload_3
/*      */     //   452: invokestatic isTerminator : (II)Z
/*      */     //   455: ifeq -> 21
/*      */     //   458: iload_3
/*      */     //   459: iconst_3
/*      */     //   460: if_icmpne -> 478
/*      */     //   463: iload #9
/*      */     //   465: ifle -> 478
/*      */     //   468: new java/io/IOException
/*      */     //   471: dup
/*      */     //   472: ldc 'Incorrect AVA RFC2253 format - trailing space must be escaped'
/*      */     //   474: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   477: athrow
/*      */     //   478: aload #5
/*      */     //   480: invokeinterface size : ()I
/*      */     //   485: ifle -> 510
/*      */     //   488: aload #5
/*      */     //   490: invokestatic getEmbeddedHexString : (Ljava/util/List;)Ljava/lang/String;
/*      */     //   493: astore #10
/*      */     //   495: aload #4
/*      */     //   497: aload #10
/*      */     //   499: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   502: pop
/*      */     //   503: aload #5
/*      */     //   505: invokeinterface clear : ()V
/*      */     //   510: aload_0
/*      */     //   511: getfield oid : Lsun/security/util/ObjectIdentifier;
/*      */     //   514: getstatic sun/security/pkcs/PKCS9Attribute.EMAIL_ADDRESS_OID : Lsun/security/util/ObjectIdentifier;
/*      */     //   517: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   520: ifne -> 542
/*      */     //   523: aload_0
/*      */     //   524: getfield oid : Lsun/security/util/ObjectIdentifier;
/*      */     //   527: getstatic sun/security/x509/X500Name.DOMAIN_COMPONENT_OID : Lsun/security/util/ObjectIdentifier;
/*      */     //   530: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   533: ifeq -> 557
/*      */     //   536: getstatic sun/security/x509/AVA.PRESERVE_OLD_DC_ENCODING : Z
/*      */     //   539: ifne -> 557
/*      */     //   542: new sun/security/util/DerValue
/*      */     //   545: dup
/*      */     //   546: bipush #22
/*      */     //   548: aload #4
/*      */     //   550: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   553: invokespecial <init> : (BLjava/lang/String;)V
/*      */     //   556: areturn
/*      */     //   557: iload #6
/*      */     //   559: ifeq -> 575
/*      */     //   562: new sun/security/util/DerValue
/*      */     //   565: dup
/*      */     //   566: aload #4
/*      */     //   568: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   571: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   574: areturn
/*      */     //   575: new sun/security/util/DerValue
/*      */     //   578: dup
/*      */     //   579: bipush #12
/*      */     //   581: aload #4
/*      */     //   583: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   586: invokespecial <init> : (BLjava/lang/String;)V
/*      */     //   589: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #384	-> 0
/*      */     //   #385	-> 9
/*      */     //   #386	-> 12
/*      */     //   #387	-> 15
/*      */     //   #388	-> 18
/*      */     //   #390	-> 21
/*      */     //   #391	-> 24
/*      */     //   #392	-> 30
/*      */     //   #393	-> 33
/*      */     //   #396	-> 40
/*      */     //   #397	-> 43
/*      */     //   #400	-> 54
/*      */     //   #404	-> 57
/*      */     //   #405	-> 67
/*      */     //   #406	-> 72
/*      */     //   #407	-> 75
/*      */     //   #411	-> 78
/*      */     //   #412	-> 87
/*      */     //   #413	-> 94
/*      */     //   #416	-> 127
/*      */     //   #417	-> 132
/*      */     //   #419	-> 138
/*      */     //   #420	-> 150
/*      */     //   #425	-> 160
/*      */     //   #427	-> 166
/*      */     //   #428	-> 171
/*      */     //   #432	-> 181
/*      */     //   #433	-> 192
/*      */     //   #438	-> 225
/*      */     //   #440	-> 228
/*      */     //   #441	-> 233
/*      */     //   #442	-> 244
/*      */     //   #446	-> 277
/*      */     //   #447	-> 288
/*      */     //   #454	-> 321
/*      */     //   #456	-> 331
/*      */     //   #457	-> 341
/*      */     //   #456	-> 349
/*      */     //   #459	-> 355
/*      */     //   #461	-> 358
/*      */     //   #462	-> 365
/*      */     //   #463	-> 373
/*      */     //   #467	-> 380
/*      */     //   #468	-> 390
/*      */     //   #471	-> 401
/*      */     //   #474	-> 407
/*      */     //   #475	-> 417
/*      */     //   #474	-> 425
/*      */     //   #477	-> 431
/*      */     //   #478	-> 434
/*      */     //   #480	-> 442
/*      */     //   #481	-> 447
/*      */     //   #482	-> 450
/*      */     //   #484	-> 458
/*      */     //   #485	-> 468
/*      */     //   #490	-> 478
/*      */     //   #491	-> 488
/*      */     //   #492	-> 495
/*      */     //   #493	-> 503
/*      */     //   #498	-> 510
/*      */     //   #499	-> 530
/*      */     //   #502	-> 542
/*      */     //   #503	-> 557
/*      */     //   #504	-> 562
/*      */     //   #506	-> 575
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Byte getEmbeddedHexPair(int paramInt, Reader paramReader) throws IOException {
/*  513 */     if ("0123456789ABCDEF".indexOf(Character.toUpperCase((char)paramInt)) >= 0) {
/*  514 */       int i = readChar(paramReader, "unexpected EOF - escaped hex value must include two valid digits");
/*      */ 
/*      */       
/*  517 */       if ("0123456789ABCDEF".indexOf(Character.toUpperCase((char)i)) >= 0) {
/*  518 */         int j = Character.digit((char)paramInt, 16);
/*  519 */         int k = Character.digit((char)i, 16);
/*  520 */         return new Byte((byte)((j << 4) + k));
/*      */       } 
/*  522 */       throw new IOException("escaped hex value must include two valid digits");
/*      */     } 
/*      */ 
/*      */     
/*  526 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getEmbeddedHexString(List<Byte> paramList) throws IOException {
/*  531 */     int i = paramList.size();
/*  532 */     byte[] arrayOfByte = new byte[i];
/*  533 */     for (byte b = 0; b < i; b++) {
/*  534 */       arrayOfByte[b] = ((Byte)paramList.get(b)).byteValue();
/*      */     }
/*  536 */     return new String(arrayOfByte, "UTF8");
/*      */   }
/*      */   
/*      */   private static boolean isTerminator(int paramInt1, int paramInt2) {
/*  540 */     switch (paramInt1) {
/*      */       case -1:
/*      */       case 43:
/*      */       case 44:
/*  544 */         return true;
/*      */       case 59:
/*  546 */         return (paramInt2 != 3);
/*      */     } 
/*  548 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int readChar(Reader paramReader, String paramString) throws IOException {
/*  553 */     int i = paramReader.read();
/*  554 */     if (i == -1) {
/*  555 */       throw new IOException(paramString);
/*      */     }
/*  557 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean trailingSpace(Reader paramReader) throws IOException {
/*  562 */     boolean bool = false;
/*      */     
/*  564 */     if (!paramReader.markSupported())
/*      */     {
/*  566 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  571 */     paramReader.mark(9999);
/*      */     while (true) {
/*  573 */       int i = paramReader.read();
/*  574 */       if (i == -1) {
/*  575 */         bool = true; break;
/*      */       } 
/*  577 */       if (i == 32)
/*      */         continue; 
/*  579 */       if (i == 92) {
/*  580 */         int j = paramReader.read();
/*  581 */         if (j != 32) {
/*  582 */           bool = false; break;
/*      */         } 
/*      */         continue;
/*      */       } 
/*  586 */       bool = false;
/*      */       
/*      */       break;
/*      */     } 
/*      */     
/*  591 */     paramReader.reset();
/*  592 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   AVA(DerValue paramDerValue) throws IOException {
/*  599 */     if (paramDerValue.tag != 48) {
/*  600 */       throw new IOException("AVA not a sequence");
/*      */     }
/*  602 */     this.oid = paramDerValue.data.getOID();
/*  603 */     this.value = paramDerValue.data.getDerValue();
/*      */     
/*  605 */     if (paramDerValue.data.available() != 0) {
/*  606 */       throw new IOException("AVA, extra bytes = " + paramDerValue.data
/*  607 */           .available());
/*      */     }
/*      */   }
/*      */   
/*      */   AVA(DerInputStream paramDerInputStream) throws IOException {
/*  612 */     this(paramDerInputStream.getDerValue());
/*      */   }
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*  616 */     if (this == paramObject) {
/*  617 */       return true;
/*      */     }
/*  619 */     if (!(paramObject instanceof AVA)) {
/*  620 */       return false;
/*      */     }
/*  622 */     AVA aVA = (AVA)paramObject;
/*  623 */     return toRFC2253CanonicalString()
/*  624 */       .equals(aVA.toRFC2253CanonicalString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  633 */     return toRFC2253CanonicalString().hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/*  640 */     derEncode(paramDerOutputStream);
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
/*      */   public void derEncode(OutputStream paramOutputStream) throws IOException {
/*  653 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/*  654 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*      */     
/*  656 */     derOutputStream1.putOID(this.oid);
/*  657 */     this.value.encode(derOutputStream1);
/*  658 */     derOutputStream2.write((byte)48, derOutputStream1);
/*  659 */     paramOutputStream.write(derOutputStream2.toByteArray());
/*      */   }
/*      */   
/*      */   private String toKeyword(int paramInt, Map<String, String> paramMap) {
/*  663 */     return AVAKeyword.getKeyword(this.oid, paramInt, paramMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  671 */     return 
/*  672 */       toKeywordValueString(toKeyword(1, Collections.emptyMap()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toRFC1779String() {
/*  681 */     return toRFC1779String(Collections.emptyMap());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toRFC1779String(Map<String, String> paramMap) {
/*  691 */     return toKeywordValueString(toKeyword(2, paramMap));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toRFC2253String() {
/*  700 */     return toRFC2253String(Collections.emptyMap());
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
/*      */   public String toRFC2253String(Map<String, String> paramMap) {
/*  717 */     StringBuilder stringBuilder = new StringBuilder(100);
/*  718 */     stringBuilder.append(toKeyword(3, paramMap));
/*  719 */     stringBuilder.append('=');
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  730 */     if ((stringBuilder.charAt(0) >= '0' && stringBuilder.charAt(0) <= '9') || 
/*  731 */       !isDerString(this.value, false)) {
/*      */       
/*  733 */       byte[] arrayOfByte = null;
/*      */       try {
/*  735 */         arrayOfByte = this.value.toByteArray();
/*  736 */       } catch (IOException iOException) {
/*  737 */         throw new IllegalArgumentException("DER Value conversion");
/*      */       } 
/*  739 */       stringBuilder.append('#');
/*  740 */       for (byte b = 0; b < arrayOfByte.length; b++) {
/*  741 */         byte b1 = arrayOfByte[b];
/*  742 */         stringBuilder.append(Character.forDigit(0xF & b1 >>> 4, 16));
/*  743 */         stringBuilder.append(Character.forDigit(0xF & b1, 16));
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  754 */       String str = null;
/*      */       try {
/*  756 */         str = new String(this.value.getDataBytes(), "UTF8");
/*  757 */       } catch (IOException iOException) {
/*  758 */         throw new IllegalArgumentException("DER Value conversion");
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  785 */       StringBuilder stringBuilder1 = new StringBuilder();
/*      */       
/*  787 */       for (byte b1 = 0; b1 < str.length(); b1++) {
/*  788 */         char c = str.charAt(b1);
/*  789 */         if (DerValue.isPrintableStringChar(c) || ",=+<>#;\"\\"
/*  790 */           .indexOf(c) >= 0) {
/*      */ 
/*      */           
/*  793 */           if (",=+<>#;\"\\".indexOf(c) >= 0) {
/*  794 */             stringBuilder1.append('\\');
/*      */           }
/*      */ 
/*      */           
/*  798 */           stringBuilder1.append(c);
/*      */         }
/*  800 */         else if (c == '\000') {
/*      */           
/*  802 */           stringBuilder1.append("\\00");
/*      */         }
/*  804 */         else if (debug != null && Debug.isOn("ava")) {
/*      */ 
/*      */ 
/*      */           
/*  808 */           byte[] arrayOfByte = null;
/*      */           try {
/*  810 */             arrayOfByte = Character.toString(c).getBytes("UTF8");
/*  811 */           } catch (IOException iOException) {
/*  812 */             throw new IllegalArgumentException("DER Value conversion");
/*      */           } 
/*      */           
/*  815 */           for (byte b = 0; b < arrayOfByte.length; b++) {
/*  816 */             stringBuilder1.append('\\');
/*      */             
/*  818 */             char c1 = Character.forDigit(0xF & arrayOfByte[b] >>> 4, 16);
/*  819 */             stringBuilder1.append(Character.toUpperCase(c1));
/*      */             
/*  821 */             c1 = Character.forDigit(0xF & arrayOfByte[b], 16);
/*  822 */             stringBuilder1.append(Character.toUpperCase(c1));
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  827 */           stringBuilder1.append(c);
/*      */         } 
/*      */       } 
/*      */       
/*  831 */       char[] arrayOfChar = stringBuilder1.toString().toCharArray();
/*  832 */       stringBuilder1 = new StringBuilder();
/*      */       
/*      */       byte b2;
/*      */       
/*  836 */       for (b2 = 0; b2 < arrayOfChar.length && (
/*  837 */         arrayOfChar[b2] == ' ' || arrayOfChar[b2] == '\r'); b2++);
/*      */ 
/*      */       
/*      */       int i;
/*      */       
/*  842 */       for (i = arrayOfChar.length - 1; i >= 0 && (
/*  843 */         arrayOfChar[i] == ' ' || arrayOfChar[i] == '\r'); i--);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  849 */       for (byte b3 = 0; b3 < arrayOfChar.length; b3++) {
/*  850 */         char c = arrayOfChar[b3];
/*  851 */         if (b3 < b2 || b3 > i) {
/*  852 */           stringBuilder1.append('\\');
/*      */         }
/*  854 */         stringBuilder1.append(c);
/*      */       } 
/*  856 */       stringBuilder.append(stringBuilder1.toString());
/*      */     } 
/*  858 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toRFC2253CanonicalString() {
/*  869 */     StringBuilder stringBuilder = new StringBuilder(40);
/*  870 */     stringBuilder
/*  871 */       .append(toKeyword(3, Collections.emptyMap()));
/*  872 */     stringBuilder.append('=');
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  883 */     if ((stringBuilder.charAt(0) >= '0' && stringBuilder.charAt(0) <= '9') || 
/*  884 */       !isDerString(this.value, true)) {
/*      */       
/*  886 */       byte[] arrayOfByte = null;
/*      */       try {
/*  888 */         arrayOfByte = this.value.toByteArray();
/*  889 */       } catch (IOException iOException) {
/*  890 */         throw new IllegalArgumentException("DER Value conversion");
/*      */       } 
/*  892 */       stringBuilder.append('#');
/*  893 */       for (byte b = 0; b < arrayOfByte.length; b++) {
/*  894 */         byte b1 = arrayOfByte[b];
/*  895 */         stringBuilder.append(Character.forDigit(0xF & b1 >>> 4, 16));
/*  896 */         stringBuilder.append(Character.forDigit(0xF & b1, 16));
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  907 */       String str1 = null;
/*      */       try {
/*  909 */         str1 = new String(this.value.getDataBytes(), "UTF8");
/*  910 */       } catch (IOException iOException) {
/*  911 */         throw new IllegalArgumentException("DER Value conversion");
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  933 */       StringBuilder stringBuilder1 = new StringBuilder();
/*  934 */       boolean bool = false;
/*      */       
/*  936 */       for (byte b = 0; b < str1.length(); b++) {
/*  937 */         char c = str1.charAt(b);
/*      */         
/*  939 */         if (DerValue.isPrintableStringChar(c) || ",+<>;\"\\"
/*  940 */           .indexOf(c) >= 0 || (b == 0 && c == '#')) {
/*      */ 
/*      */ 
/*      */           
/*  944 */           if ((b == 0 && c == '#') || ",+<>;\"\\".indexOf(c) >= 0) {
/*  945 */             stringBuilder1.append('\\');
/*      */           }
/*      */ 
/*      */           
/*  949 */           if (!Character.isWhitespace(c)) {
/*  950 */             bool = false;
/*  951 */             stringBuilder1.append(c);
/*      */           }
/*  953 */           else if (!bool) {
/*      */             
/*  955 */             bool = true;
/*  956 */             stringBuilder1.append(c);
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*  963 */         else if (debug != null && Debug.isOn("ava")) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  968 */           bool = false;
/*      */           
/*  970 */           byte[] arrayOfByte = null;
/*      */           try {
/*  972 */             arrayOfByte = Character.toString(c).getBytes("UTF8");
/*  973 */           } catch (IOException iOException) {
/*  974 */             throw new IllegalArgumentException("DER Value conversion");
/*      */           } 
/*      */           
/*  977 */           for (byte b1 = 0; b1 < arrayOfByte.length; b1++) {
/*  978 */             stringBuilder1.append('\\');
/*  979 */             stringBuilder1.append(
/*  980 */                 Character.forDigit(0xF & arrayOfByte[b1] >>> 4, 16));
/*  981 */             stringBuilder1.append(
/*  982 */                 Character.forDigit(0xF & arrayOfByte[b1], 16));
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  988 */           bool = false;
/*  989 */           stringBuilder1.append(c);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  994 */       stringBuilder.append(stringBuilder1.toString().trim());
/*      */     } 
/*      */     
/*  997 */     String str = stringBuilder.toString();
/*  998 */     str = str.toUpperCase(Locale.US).toLowerCase(Locale.US);
/*  999 */     return Normalizer.normalize(str, Normalizer.Form.NFKD);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isDerString(DerValue paramDerValue, boolean paramBoolean) {
/* 1006 */     if (paramBoolean) {
/* 1007 */       switch (paramDerValue.tag) {
/*      */         case 12:
/*      */         case 19:
/* 1010 */           return true;
/*      */       } 
/* 1012 */       return false;
/*      */     } 
/*      */     
/* 1015 */     switch (paramDerValue.tag) {
/*      */       case 12:
/*      */       case 19:
/*      */       case 20:
/*      */       case 22:
/*      */       case 27:
/*      */       case 30:
/* 1022 */         return true;
/*      */     } 
/* 1024 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   boolean hasRFC2253Keyword() {
/* 1030 */     return AVAKeyword.hasKeyword(this.oid, 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String toKeywordValueString(String paramString) {
/* 1039 */     StringBuilder stringBuilder = new StringBuilder(40);
/*      */     
/* 1041 */     stringBuilder.append(paramString);
/* 1042 */     stringBuilder.append("=");
/*      */     
/*      */     try {
/* 1045 */       String str = this.value.getAsString();
/*      */       
/* 1047 */       if (str == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1054 */         byte[] arrayOfByte = this.value.toByteArray();
/*      */         
/* 1056 */         stringBuilder.append('#');
/* 1057 */         for (byte b = 0; b < arrayOfByte.length; b++) {
/* 1058 */           stringBuilder.append("0123456789ABCDEF".charAt(arrayOfByte[b] >> 4 & 0xF));
/* 1059 */           stringBuilder.append("0123456789ABCDEF".charAt(arrayOfByte[b] & 0xF));
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1064 */         boolean bool1 = false;
/* 1065 */         StringBuilder stringBuilder1 = new StringBuilder();
/* 1066 */         boolean bool2 = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1074 */         int i = str.length();
/*      */ 
/*      */         
/* 1077 */         boolean bool3 = (i > 1 && str.charAt(0) == '"' && str.charAt(i - 1) == '"') ? true : false;
/*      */         char c;
/* 1079 */         for (c = Character.MIN_VALUE; c < i; c++) {
/* 1080 */           char c1 = str.charAt(c);
/* 1081 */           if (bool3 && (c == Character.MIN_VALUE || c == i - 1)) {
/* 1082 */             stringBuilder1.append(c1);
/*      */           
/*      */           }
/* 1085 */           else if (DerValue.isPrintableStringChar(c1) || ",+=\n<>#;\\\""
/* 1086 */             .indexOf(c1) >= 0) {
/*      */ 
/*      */             
/* 1089 */             if (!bool1 && ((c == Character.MIN_VALUE && (c1 == ' ' || c1 == '\n')) || ",+=\n<>#;\\\""
/*      */               
/* 1091 */               .indexOf(c1) >= 0)) {
/* 1092 */               bool1 = true;
/*      */             }
/*      */ 
/*      */             
/* 1096 */             if (c1 != ' ' && c1 != '\n') {
/*      */               
/* 1098 */               if (c1 == '"' || c1 == '\\') {
/* 1099 */                 stringBuilder1.append('\\');
/*      */               }
/* 1101 */               bool2 = false;
/*      */             } else {
/* 1103 */               if (!bool1 && bool2) {
/* 1104 */                 bool1 = true;
/*      */               }
/* 1106 */               bool2 = true;
/*      */             } 
/*      */             
/* 1109 */             stringBuilder1.append(c1);
/*      */           }
/* 1111 */           else if (debug != null && Debug.isOn("ava")) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1116 */             bool2 = false;
/*      */ 
/*      */ 
/*      */             
/* 1120 */             byte[] arrayOfByte = Character.toString(c1).getBytes("UTF8");
/* 1121 */             for (byte b = 0; b < arrayOfByte.length; b++) {
/* 1122 */               stringBuilder1.append('\\');
/*      */               
/* 1124 */               char c2 = Character.forDigit(0xF & arrayOfByte[b] >>> 4, 16);
/* 1125 */               stringBuilder1.append(Character.toUpperCase(c2));
/*      */               
/* 1127 */               c2 = Character.forDigit(0xF & arrayOfByte[b], 16);
/* 1128 */               stringBuilder1.append(Character.toUpperCase(c2));
/*      */             }
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 1134 */             bool2 = false;
/* 1135 */             stringBuilder1.append(c1);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1140 */         if (stringBuilder1.length() > 0) {
/* 1141 */           c = stringBuilder1.charAt(stringBuilder1.length() - 1);
/* 1142 */           if (c == ' ' || c == '\n') {
/* 1143 */             bool1 = true;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1149 */         if (!bool3 && bool1) {
/* 1150 */           stringBuilder.append("\"" + stringBuilder1.toString() + "\"");
/*      */         } else {
/* 1152 */           stringBuilder.append(stringBuilder1.toString());
/*      */         } 
/*      */       } 
/* 1155 */     } catch (IOException iOException) {
/* 1156 */       throw new IllegalArgumentException("DER Value conversion");
/*      */     } 
/*      */     
/* 1159 */     return stringBuilder.toString();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/AVA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */