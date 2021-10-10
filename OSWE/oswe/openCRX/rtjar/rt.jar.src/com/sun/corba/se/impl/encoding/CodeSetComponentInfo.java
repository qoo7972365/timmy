/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CodeSetComponentInfo
/*     */ {
/*     */   private CodeSetComponent forCharData;
/*     */   private CodeSetComponent forWCharData;
/*     */   public static final CodeSetComponentInfo JAVASOFT_DEFAULT_CODESETS;
/*     */   
/*     */   public static final class CodeSetComponent
/*     */   {
/*     */     int nativeCodeSet;
/*     */     int[] conversionCodeSets;
/*     */     
/*     */     public boolean equals(Object param1Object) {
/*  58 */       if (this == param1Object) {
/*  59 */         return true;
/*     */       }
/*  61 */       if (!(param1Object instanceof CodeSetComponent)) {
/*  62 */         return false;
/*     */       }
/*  64 */       CodeSetComponent codeSetComponent = (CodeSetComponent)param1Object;
/*     */       
/*  66 */       return (this.nativeCodeSet == codeSetComponent.nativeCodeSet && 
/*  67 */         Arrays.equals(this.conversionCodeSets, codeSetComponent.conversionCodeSets));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  72 */       int i = this.nativeCodeSet;
/*  73 */       for (byte b = 0; b < this.conversionCodeSets.length; b++)
/*  74 */         i = 37 * i + this.conversionCodeSets[b]; 
/*  75 */       return i;
/*     */     }
/*     */     
/*     */     public CodeSetComponent() {}
/*     */     
/*     */     public CodeSetComponent(int param1Int, int[] param1ArrayOfint) {
/*  81 */       this.nativeCodeSet = param1Int;
/*  82 */       if (param1ArrayOfint == null) {
/*  83 */         this.conversionCodeSets = new int[0];
/*     */       } else {
/*  85 */         this.conversionCodeSets = param1ArrayOfint;
/*     */       } 
/*     */     }
/*     */     public void read(MarshalInputStream param1MarshalInputStream) {
/*  89 */       this.nativeCodeSet = param1MarshalInputStream.read_ulong();
/*  90 */       int i = param1MarshalInputStream.read_long();
/*  91 */       this.conversionCodeSets = new int[i];
/*  92 */       param1MarshalInputStream.read_ulong_array(this.conversionCodeSets, 0, i);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(MarshalOutputStream param1MarshalOutputStream) {
/*  97 */       param1MarshalOutputStream.write_ulong(this.nativeCodeSet);
/*  98 */       param1MarshalOutputStream.write_long(this.conversionCodeSets.length);
/*  99 */       param1MarshalOutputStream.write_ulong_array(this.conversionCodeSets, 0, this.conversionCodeSets.length);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 103 */       StringBuffer stringBuffer = new StringBuffer("CodeSetComponent(");
/*     */       
/* 105 */       stringBuffer.append("native:");
/* 106 */       stringBuffer.append(Integer.toHexString(this.nativeCodeSet));
/* 107 */       stringBuffer.append(" conversion:");
/* 108 */       if (this.conversionCodeSets == null) {
/* 109 */         stringBuffer.append("null");
/*     */       } else {
/* 111 */         for (byte b = 0; b < this.conversionCodeSets.length; b++) {
/* 112 */           stringBuffer.append(Integer.toHexString(this.conversionCodeSets[b]));
/* 113 */           stringBuffer.append(' ');
/*     */         } 
/*     */       } 
/* 116 */       stringBuffer.append(")");
/*     */       
/* 118 */       return stringBuffer.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 127 */     if (this == paramObject) {
/* 128 */       return true;
/*     */     }
/* 130 */     if (!(paramObject instanceof CodeSetComponentInfo)) {
/* 131 */       return false;
/*     */     }
/* 133 */     CodeSetComponentInfo codeSetComponentInfo = (CodeSetComponentInfo)paramObject;
/* 134 */     return (this.forCharData.equals(codeSetComponentInfo.forCharData) && this.forWCharData
/* 135 */       .equals(codeSetComponentInfo.forWCharData));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 140 */     return this.forCharData.hashCode() ^ this.forWCharData.hashCode();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 144 */     StringBuffer stringBuffer = new StringBuffer("CodeSetComponentInfo(");
/*     */     
/* 146 */     stringBuffer.append("char_data:");
/* 147 */     stringBuffer.append(this.forCharData.toString());
/* 148 */     stringBuffer.append(" wchar_data:");
/* 149 */     stringBuffer.append(this.forWCharData.toString());
/* 150 */     stringBuffer.append(")");
/*     */     
/* 152 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public CodeSetComponentInfo() {
/* 156 */     this.forCharData = JAVASOFT_DEFAULT_CODESETS.forCharData;
/* 157 */     this.forWCharData = JAVASOFT_DEFAULT_CODESETS.forWCharData;
/*     */   }
/*     */ 
/*     */   
/*     */   public CodeSetComponentInfo(CodeSetComponent paramCodeSetComponent1, CodeSetComponent paramCodeSetComponent2) {
/* 162 */     this.forCharData = paramCodeSetComponent1;
/* 163 */     this.forWCharData = paramCodeSetComponent2;
/*     */   }
/*     */   
/*     */   public void read(MarshalInputStream paramMarshalInputStream) {
/* 167 */     this.forCharData = new CodeSetComponent();
/* 168 */     this.forCharData.read(paramMarshalInputStream);
/* 169 */     this.forWCharData = new CodeSetComponent();
/* 170 */     this.forWCharData.read(paramMarshalInputStream);
/*     */   }
/*     */   
/*     */   public void write(MarshalOutputStream paramMarshalOutputStream) {
/* 174 */     this.forCharData.write(paramMarshalOutputStream);
/* 175 */     this.forWCharData.write(paramMarshalOutputStream);
/*     */   }
/*     */   
/*     */   public CodeSetComponent getCharComponent() {
/* 179 */     return this.forCharData;
/*     */   }
/*     */   
/*     */   public CodeSetComponent getWCharComponent() {
/* 183 */     return this.forWCharData;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class CodeSetContext
/*     */   {
/*     */     private int char_data;
/*     */     
/*     */     private int wchar_data;
/*     */     
/*     */     public CodeSetContext() {}
/*     */     
/*     */     public CodeSetContext(int param1Int1, int param1Int2) {
/* 196 */       this.char_data = param1Int1;
/* 197 */       this.wchar_data = param1Int2;
/*     */     }
/*     */     
/*     */     public void read(MarshalInputStream param1MarshalInputStream) {
/* 201 */       this.char_data = param1MarshalInputStream.read_ulong();
/* 202 */       this.wchar_data = param1MarshalInputStream.read_ulong();
/*     */     }
/*     */     
/*     */     public void write(MarshalOutputStream param1MarshalOutputStream) {
/* 206 */       param1MarshalOutputStream.write_ulong(this.char_data);
/* 207 */       param1MarshalOutputStream.write_ulong(this.wchar_data);
/*     */     }
/*     */     
/*     */     public int getCharCodeSet() {
/* 211 */       return this.char_data;
/*     */     }
/*     */     
/*     */     public int getWCharCodeSet() {
/* 215 */       return this.wchar_data;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 219 */       StringBuffer stringBuffer = new StringBuffer();
/* 220 */       stringBuffer.append("CodeSetContext char set: ");
/* 221 */       stringBuffer.append(Integer.toHexString(this.char_data));
/* 222 */       stringBuffer.append(" wchar set: ");
/* 223 */       stringBuffer.append(Integer.toHexString(this.wchar_data));
/* 224 */       return stringBuffer.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 260 */     CodeSetComponent codeSetComponent1 = new CodeSetComponent(OSFCodeSetRegistry.ISO_8859_1.getNumber(), new int[] { OSFCodeSetRegistry.UTF_8.getNumber(), OSFCodeSetRegistry.ISO_646.getNumber() });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 267 */     CodeSetComponent codeSetComponent2 = new CodeSetComponent(OSFCodeSetRegistry.UTF_16.getNumber(), new int[] { OSFCodeSetRegistry.UCS_2.getNumber() });
/*     */ 
/*     */     
/* 270 */     JAVASOFT_DEFAULT_CODESETS = new CodeSetComponentInfo(codeSetComponent1, codeSetComponent2);
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
/*     */   public static CodeSetComponent createFromString(String paramString) {
/* 285 */     ORBUtilSystemException oRBUtilSystemException = ORBUtilSystemException.get("rpc.encoding");
/*     */ 
/*     */     
/* 288 */     if (paramString == null || paramString.length() == 0) {
/* 289 */       throw oRBUtilSystemException.badCodeSetString();
/*     */     }
/* 291 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, ", ", false);
/* 292 */     int i = 0;
/* 293 */     int[] arrayOfInt = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 298 */       i = Integer.decode(stringTokenizer.nextToken()).intValue();
/*     */       
/* 300 */       if (OSFCodeSetRegistry.lookupEntry(i) == null) {
/* 301 */         throw oRBUtilSystemException.unknownNativeCodeset(new Integer(i));
/*     */       }
/* 303 */       ArrayList<Integer> arrayList = new ArrayList(10);
/*     */ 
/*     */ 
/*     */       
/* 307 */       while (stringTokenizer.hasMoreTokens()) {
/*     */ 
/*     */         
/* 310 */         Integer integer = Integer.decode(stringTokenizer.nextToken());
/*     */         
/* 312 */         if (OSFCodeSetRegistry.lookupEntry(integer.intValue()) == null) {
/* 313 */           throw oRBUtilSystemException.unknownConversionCodeSet(integer);
/*     */         }
/* 315 */         arrayList.add(integer);
/*     */       } 
/*     */       
/* 318 */       arrayOfInt = new int[arrayList.size()];
/*     */       
/* 320 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/* 321 */         arrayOfInt[b] = ((Integer)arrayList.get(b)).intValue();
/*     */       }
/* 323 */     } catch (NumberFormatException numberFormatException) {
/* 324 */       throw oRBUtilSystemException.invalidCodeSetNumber(numberFormatException);
/* 325 */     } catch (NoSuchElementException noSuchElementException) {
/* 326 */       throw oRBUtilSystemException.invalidCodeSetString(noSuchElementException, paramString);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 331 */     return new CodeSetComponent(i, arrayOfInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 337 */   public static final CodeSetContext LOCAL_CODE_SETS = new CodeSetContext(OSFCodeSetRegistry.ISO_8859_1
/* 338 */       .getNumber(), OSFCodeSetRegistry.UTF_16
/* 339 */       .getNumber());
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/CodeSetComponentInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */