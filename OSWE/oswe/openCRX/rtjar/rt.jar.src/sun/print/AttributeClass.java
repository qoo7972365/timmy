/*     */ package sun.print;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttributeClass
/*     */ {
/*     */   private String myName;
/*     */   private int myType;
/*     */   private int nameLen;
/*     */   private Object myValue;
/*     */   public static final int TAG_UNSUPPORTED_VALUE = 16;
/*     */   public static final int TAG_INT = 33;
/*     */   public static final int TAG_BOOL = 34;
/*     */   public static final int TAG_ENUM = 35;
/*     */   public static final int TAG_OCTET = 48;
/*     */   public static final int TAG_DATE = 49;
/*     */   public static final int TAG_RESOLUTION = 50;
/*     */   public static final int TAG_RANGE_INTEGER = 51;
/*     */   public static final int TAG_TEXT_LANGUAGE = 53;
/*     */   public static final int TAG_NAME_LANGUAGE = 54;
/*     */   public static final int TAG_TEXT_WO_LANGUAGE = 65;
/*     */   public static final int TAG_NAME_WO_LANGUAGE = 66;
/*     */   public static final int TAG_KEYWORD = 68;
/*     */   public static final int TAG_URI = 69;
/*     */   public static final int TAG_CHARSET = 71;
/*     */   public static final int TAG_NATURALLANGUAGE = 72;
/*     */   public static final int TAG_MIME_MEDIATYPE = 73;
/*     */   public static final int TAG_MEMBER_ATTRNAME = 74;
/*  58 */   public static final AttributeClass ATTRIBUTES_CHARSET = new AttributeClass("attributes-charset", 71, "utf-8");
/*     */ 
/*     */   
/*  61 */   public static final AttributeClass ATTRIBUTES_NATURAL_LANGUAGE = new AttributeClass("attributes-natural-language", 72, "en");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AttributeClass(String paramString, int paramInt, Object paramObject) {
/*  73 */     this.myName = paramString;
/*  74 */     this.myType = paramInt;
/*  75 */     this.nameLen = paramString.length();
/*  76 */     this.myValue = paramObject;
/*     */   }
/*     */   
/*     */   public byte getType() {
/*  80 */     return (byte)this.myType;
/*     */   }
/*     */   
/*     */   public char[] getLenChars() {
/*  84 */     char[] arrayOfChar = new char[2];
/*  85 */     arrayOfChar[0] = Character.MIN_VALUE;
/*  86 */     arrayOfChar[1] = (char)this.nameLen;
/*  87 */     return arrayOfChar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getObjectValue() {
/*  94 */     return this.myValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIntValue() {
/* 101 */     byte[] arrayOfByte = (byte[])this.myValue;
/*     */     
/* 103 */     if (arrayOfByte != null) {
/* 104 */       byte[] arrayOfByte1 = new byte[4];
/* 105 */       for (byte b = 0; b < 4; b++) {
/* 106 */         arrayOfByte1[b] = arrayOfByte[b + 1];
/*     */       }
/*     */       
/* 109 */       return convertToInt(arrayOfByte1);
/*     */     } 
/* 111 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getArrayOfIntValues() {
/* 119 */     byte[] arrayOfByte = (byte[])this.myValue;
/* 120 */     if (arrayOfByte != null) {
/*     */ 
/*     */       
/* 123 */       ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/*     */       
/* 125 */       int i = byteArrayInputStream.available();
/*     */ 
/*     */       
/* 128 */       byteArrayInputStream.mark(i);
/* 129 */       byteArrayInputStream.skip((i - 1));
/* 130 */       int j = byteArrayInputStream.read();
/* 131 */       byteArrayInputStream.reset();
/*     */       
/* 133 */       int[] arrayOfInt = new int[j];
/* 134 */       for (byte b = 0; b < j; b++) {
/*     */         
/* 136 */         int k = byteArrayInputStream.read();
/* 137 */         if (k != 4)
/*     */         {
/* 139 */           return null;
/*     */         }
/*     */         
/* 142 */         byte[] arrayOfByte1 = new byte[k];
/* 143 */         byteArrayInputStream.read(arrayOfByte1, 0, k);
/* 144 */         arrayOfInt[b] = convertToInt(arrayOfByte1);
/*     */       } 
/*     */       
/* 147 */       return arrayOfInt;
/*     */     } 
/* 149 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getIntRangeValue() {
/* 156 */     int[] arrayOfInt = { 0, 0 };
/* 157 */     byte[] arrayOfByte = (byte[])this.myValue;
/* 158 */     if (arrayOfByte != null) {
/* 159 */       byte b1 = 4;
/* 160 */       for (byte b2 = 0; b2 < 2; b2++) {
/* 161 */         byte[] arrayOfByte1 = new byte[b1];
/*     */         
/* 163 */         for (byte b = 0; b < b1; b++)
/*     */         {
/* 165 */           arrayOfByte1[b] = arrayOfByte[b + 4 * b2 + 1];
/*     */         }
/* 167 */         arrayOfInt[b2] = convertToInt(arrayOfByte1);
/*     */       } 
/*     */     } 
/* 170 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStringValue() {
/* 180 */     String str = null;
/* 181 */     byte[] arrayOfByte = (byte[])this.myValue;
/* 182 */     if (arrayOfByte != null) {
/* 183 */       ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/*     */ 
/*     */       
/* 186 */       int i = byteArrayInputStream.read();
/*     */       
/* 188 */       byte[] arrayOfByte1 = new byte[i];
/* 189 */       byteArrayInputStream.read(arrayOfByte1, 0, i);
/*     */       try {
/* 191 */         str = new String(arrayOfByte1, "UTF-8");
/* 192 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */     } 
/*     */     
/* 195 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getArrayOfStringValues() {
/* 204 */     byte[] arrayOfByte = (byte[])this.myValue;
/* 205 */     if (arrayOfByte != null) {
/* 206 */       ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/*     */       
/* 208 */       int i = byteArrayInputStream.available();
/*     */ 
/*     */       
/* 211 */       byteArrayInputStream.mark(i);
/* 212 */       byteArrayInputStream.skip((i - 1));
/* 213 */       int j = byteArrayInputStream.read();
/* 214 */       byteArrayInputStream.reset();
/*     */       
/* 216 */       String[] arrayOfString = new String[j];
/* 217 */       for (byte b = 0; b < j; b++) {
/*     */         
/* 219 */         int k = byteArrayInputStream.read();
/* 220 */         byte[] arrayOfByte1 = new byte[k];
/* 221 */         byteArrayInputStream.read(arrayOfByte1, 0, k);
/*     */         try {
/* 223 */           arrayOfString[b] = new String(arrayOfByte1, "UTF-8");
/* 224 */         } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */       } 
/*     */       
/* 227 */       return arrayOfString;
/*     */     } 
/* 229 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getByteValue() {
/* 237 */     byte[] arrayOfByte = (byte[])this.myValue;
/*     */     
/* 239 */     if (arrayOfByte != null && arrayOfByte.length >= 2) {
/* 240 */       return arrayOfByte[1];
/*     */     }
/* 242 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 249 */     return this.myName;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 254 */     if (!(paramObject instanceof AttributeClass)) {
/* 255 */       return false;
/*     */     }
/* 257 */     if (this == paramObject) {
/* 258 */       return true;
/*     */     }
/*     */     
/* 261 */     AttributeClass attributeClass = (AttributeClass)paramObject;
/* 262 */     return (this.myType == attributeClass.getType() && 
/* 263 */       Objects.equals(this.myName, attributeClass.getName()) && 
/* 264 */       Objects.equals(this.myValue, attributeClass.getObjectValue()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 269 */     return Objects.hash(new Object[] { Integer.valueOf(this.myType), this.myName, this.myValue });
/*     */   }
/*     */   
/*     */   public String toString() {
/* 273 */     return this.myName;
/*     */   }
/*     */   
/*     */   private int unsignedByteToInt(byte paramByte) {
/* 277 */     return paramByte & 0xFF;
/*     */   }
/*     */   
/*     */   private int convertToInt(byte[] paramArrayOfbyte) {
/* 281 */     int i = 0;
/* 282 */     byte b = 0;
/* 283 */     i += unsignedByteToInt(paramArrayOfbyte[b++]) << 24;
/* 284 */     i += unsignedByteToInt(paramArrayOfbyte[b++]) << 16;
/* 285 */     i += unsignedByteToInt(paramArrayOfbyte[b++]) << 8;
/* 286 */     i += unsignedByteToInt(paramArrayOfbyte[b++]) << 0;
/* 287 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/AttributeClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */