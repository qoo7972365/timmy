/*     */ package javax.swing.text.html.parser;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.BitSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import sun.awt.AppContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DTD
/*     */   implements DTDConstants
/*     */ {
/*     */   public String name;
/*  61 */   public Vector<Element> elements = new Vector<>();
/*  62 */   public Hashtable<String, Element> elementHash = new Hashtable<>();
/*     */   
/*  64 */   public Hashtable<Object, Entity> entityHash = new Hashtable<>();
/*     */   
/*  66 */   public final Element pcdata = getElement("#pcdata");
/*  67 */   public final Element html = getElement("html");
/*  68 */   public final Element meta = getElement("meta");
/*  69 */   public final Element base = getElement("base");
/*  70 */   public final Element isindex = getElement("isindex");
/*  71 */   public final Element head = getElement("head");
/*  72 */   public final Element body = getElement("body");
/*  73 */   public final Element applet = getElement("applet");
/*  74 */   public final Element param = getElement("param");
/*  75 */   public final Element p = getElement("p");
/*  76 */   public final Element title = getElement("title");
/*  77 */   final Element style = getElement("style");
/*  78 */   final Element link = getElement("link");
/*  79 */   final Element script = getElement("script");
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FILE_VERSION = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   protected DTD(String paramString) {
/*  88 */     this.name = paramString;
/*  89 */     defEntity("#RE", 65536, 13);
/*  90 */     defEntity("#RS", 65536, 10);
/*  91 */     defEntity("#SPACE", 65536, 32);
/*  92 */     defineElement("unknown", 17, false, true, null, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 100 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity getEntity(String paramString) {
/* 109 */     return this.entityHash.get(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity getEntity(int paramInt) {
/* 118 */     return this.entityHash.get(Integer.valueOf(paramInt));
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
/*     */   boolean elementExists(String paramString) {
/* 130 */     return (!"unknown".equals(paramString) && this.elementHash.get(paramString) != null);
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
/*     */   public Element getElement(String paramString) {
/* 142 */     Element element = this.elementHash.get(paramString);
/* 143 */     if (element == null) {
/* 144 */       element = new Element(paramString, this.elements.size());
/* 145 */       this.elements.addElement(element);
/* 146 */       this.elementHash.put(paramString, element);
/*     */     } 
/* 148 */     return element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element getElement(int paramInt) {
/* 159 */     return this.elements.elementAt(paramInt);
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
/*     */   public Entity defineEntity(String paramString, int paramInt, char[] paramArrayOfchar) {
/* 175 */     Entity entity = this.entityHash.get(paramString);
/* 176 */     if (entity == null) {
/* 177 */       entity = new Entity(paramString, paramInt, paramArrayOfchar);
/* 178 */       this.entityHash.put(paramString, entity);
/* 179 */       if ((paramInt & 0x10000) != 0 && paramArrayOfchar.length == 1) {
/* 180 */         switch (paramInt & 0xFFFEFFFF) {
/*     */           case 1:
/*     */           case 11:
/* 183 */             this.entityHash.put(Integer.valueOf(paramArrayOfchar[0]), entity);
/*     */             break;
/*     */         } 
/*     */       }
/*     */     } 
/* 188 */     return entity;
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
/*     */   public Element defineElement(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2, ContentModel paramContentModel, BitSet paramBitSet1, BitSet paramBitSet2, AttributeList paramAttributeList) {
/* 208 */     Element element = getElement(paramString);
/* 209 */     element.type = paramInt;
/* 210 */     element.oStart = paramBoolean1;
/* 211 */     element.oEnd = paramBoolean2;
/* 212 */     element.content = paramContentModel;
/* 213 */     element.exclusions = paramBitSet1;
/* 214 */     element.inclusions = paramBitSet2;
/* 215 */     element.atts = paramAttributeList;
/* 216 */     return element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void defineAttributes(String paramString, AttributeList paramAttributeList) {
/* 227 */     Element element = getElement(paramString);
/* 228 */     element.atts = paramAttributeList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity defEntity(String paramString, int paramInt1, int paramInt2) {
/* 237 */     char[] arrayOfChar = { (char)paramInt2 };
/* 238 */     return defineEntity(paramString, paramInt1, arrayOfChar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Entity defEntity(String paramString1, int paramInt, String paramString2) {
/* 247 */     int i = paramString2.length();
/* 248 */     char[] arrayOfChar = new char[i];
/* 249 */     paramString2.getChars(0, i, arrayOfChar, 0);
/* 250 */     return defineEntity(paramString1, paramInt, arrayOfChar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Element defElement(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2, ContentModel paramContentModel, String[] paramArrayOfString1, String[] paramArrayOfString2, AttributeList paramAttributeList) {
/* 261 */     BitSet bitSet1 = null;
/* 262 */     if (paramArrayOfString1 != null && paramArrayOfString1.length > 0) {
/* 263 */       bitSet1 = new BitSet();
/* 264 */       for (String str : paramArrayOfString1) {
/* 265 */         if (str.length() > 0) {
/* 266 */           bitSet1.set(getElement(str).getIndex());
/*     */         }
/*     */       } 
/*     */     } 
/* 270 */     BitSet bitSet2 = null;
/* 271 */     if (paramArrayOfString2 != null && paramArrayOfString2.length > 0) {
/* 272 */       bitSet2 = new BitSet();
/* 273 */       for (String str : paramArrayOfString2) {
/* 274 */         if (str.length() > 0) {
/* 275 */           bitSet2.set(getElement(str).getIndex());
/*     */         }
/*     */       } 
/*     */     } 
/* 279 */     return defineElement(paramString, paramInt, paramBoolean1, paramBoolean2, paramContentModel, bitSet1, bitSet2, paramAttributeList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AttributeList defAttributeList(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, AttributeList paramAttributeList) {
/* 288 */     Vector<String> vector = null;
/* 289 */     if (paramString3 != null) {
/* 290 */       vector = new Vector();
/* 291 */       for (StringTokenizer stringTokenizer = new StringTokenizer(paramString3, "|"); stringTokenizer.hasMoreTokens(); ) {
/* 292 */         String str = stringTokenizer.nextToken();
/* 293 */         if (str.length() > 0) {
/* 294 */           vector.addElement(str);
/*     */         }
/*     */       } 
/*     */     } 
/* 298 */     return new AttributeList(paramString1, paramInt1, paramInt2, paramString2, vector, paramAttributeList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ContentModel defContentModel(int paramInt, Object paramObject, ContentModel paramContentModel) {
/* 307 */     return new ContentModel(paramInt, paramObject, paramContentModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 315 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 321 */   private static final Object DTD_HASH_KEY = new Object();
/*     */   
/*     */   public static void putDTDHash(String paramString, DTD paramDTD) {
/* 324 */     getDtdHash().put(paramString, paramDTD);
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
/*     */   public static DTD getDTD(String paramString) throws IOException {
/* 337 */     paramString = paramString.toLowerCase();
/* 338 */     DTD dTD = getDtdHash().get(paramString);
/* 339 */     if (dTD == null) {
/* 340 */       dTD = new DTD(paramString);
/*     */     }
/* 342 */     return dTD;
/*     */   }
/*     */   
/*     */   private static Hashtable<String, DTD> getDtdHash() {
/* 346 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/* 348 */     Hashtable<Object, Object> hashtable = (Hashtable)appContext.get(DTD_HASH_KEY);
/*     */     
/* 350 */     if (hashtable == null) {
/* 351 */       hashtable = new Hashtable<>();
/*     */       
/* 353 */       appContext.put(DTD_HASH_KEY, hashtable);
/*     */     } 
/*     */     
/* 356 */     return (Hashtable)hashtable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(DataInputStream paramDataInputStream) throws IOException {
/* 364 */     if (paramDataInputStream.readInt() != 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 370 */     String[] arrayOfString = new String[paramDataInputStream.readShort()]; short s;
/* 371 */     for (s = 0; s < arrayOfString.length; s++) {
/* 372 */       arrayOfString[s] = paramDataInputStream.readUTF();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 379 */     s = paramDataInputStream.readShort(); byte b;
/* 380 */     for (b = 0; b < s; b++) {
/* 381 */       short s1 = paramDataInputStream.readShort();
/* 382 */       byte b1 = paramDataInputStream.readByte();
/* 383 */       String str = paramDataInputStream.readUTF();
/* 384 */       defEntity(arrayOfString[s1], b1 | 0x10000, str);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 389 */     s = paramDataInputStream.readShort();
/* 390 */     for (b = 0; b < s; b++) {
/* 391 */       short s1 = paramDataInputStream.readShort();
/* 392 */       byte b1 = paramDataInputStream.readByte();
/* 393 */       byte b2 = paramDataInputStream.readByte();
/* 394 */       ContentModel contentModel = readContentModel(paramDataInputStream, arrayOfString);
/* 395 */       String[] arrayOfString1 = readNameArray(paramDataInputStream, arrayOfString);
/* 396 */       String[] arrayOfString2 = readNameArray(paramDataInputStream, arrayOfString);
/* 397 */       AttributeList attributeList = readAttributeList(paramDataInputStream, arrayOfString);
/* 398 */       defElement(arrayOfString[s1], b1, ((b2 & 0x1) != 0), ((b2 & 0x2) != 0), contentModel, arrayOfString1, arrayOfString2, attributeList);
/*     */     } 
/*     */   }
/*     */   private ContentModel readContentModel(DataInputStream paramDataInputStream, String[] paramArrayOfString) throws IOException {
/*     */     byte b2;
/*     */     ContentModel contentModel1;
/*     */     Element element;
/*     */     ContentModel contentModel2;
/* 406 */     byte b1 = paramDataInputStream.readByte();
/* 407 */     switch (b1) {
/*     */       case 0:
/* 409 */         return null;
/*     */       case 1:
/* 411 */         b2 = paramDataInputStream.readByte();
/* 412 */         contentModel1 = readContentModel(paramDataInputStream, paramArrayOfString);
/* 413 */         contentModel2 = readContentModel(paramDataInputStream, paramArrayOfString);
/* 414 */         return defContentModel(b2, contentModel1, contentModel2);
/*     */       
/*     */       case 2:
/* 417 */         b2 = paramDataInputStream.readByte();
/* 418 */         element = getElement(paramArrayOfString[paramDataInputStream.readShort()]);
/* 419 */         contentModel2 = readContentModel(paramDataInputStream, paramArrayOfString);
/* 420 */         return defContentModel(b2, element, contentModel2);
/*     */     } 
/*     */     
/* 423 */     throw new IOException("bad bdtd");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] readNameArray(DataInputStream paramDataInputStream, String[] paramArrayOfString) throws IOException {
/* 429 */     short s = paramDataInputStream.readShort();
/* 430 */     if (s == 0) {
/* 431 */       return null;
/*     */     }
/* 433 */     String[] arrayOfString = new String[s];
/* 434 */     for (byte b = 0; b < s; b++) {
/* 435 */       arrayOfString[b] = paramArrayOfString[paramDataInputStream.readShort()];
/*     */     }
/* 437 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AttributeList readAttributeList(DataInputStream paramDataInputStream, String[] paramArrayOfString) throws IOException {
/* 443 */     AttributeList attributeList = null;
/* 444 */     for (byte b = paramDataInputStream.readByte(); b > 0; b--) {
/* 445 */       short s1 = paramDataInputStream.readShort();
/* 446 */       byte b1 = paramDataInputStream.readByte();
/* 447 */       byte b2 = paramDataInputStream.readByte();
/* 448 */       short s2 = paramDataInputStream.readShort();
/* 449 */       String str = (s2 == -1) ? null : paramArrayOfString[s2];
/* 450 */       Vector<String> vector = null;
/* 451 */       short s3 = paramDataInputStream.readShort();
/* 452 */       if (s3 > 0) {
/* 453 */         vector = new Vector(s3);
/* 454 */         for (byte b3 = 0; b3 < s3; b3++) {
/* 455 */           vector.addElement(paramArrayOfString[paramDataInputStream.readShort()]);
/*     */         }
/*     */       } 
/* 458 */       attributeList = new AttributeList(paramArrayOfString[s1], b1, b2, str, vector, attributeList);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 463 */     return attributeList;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/parser/DTD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */