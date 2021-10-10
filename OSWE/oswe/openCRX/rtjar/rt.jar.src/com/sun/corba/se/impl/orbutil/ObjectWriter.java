/*     */ package com.sun.corba.se.impl.orbutil;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ObjectWriter
/*     */ {
/*     */   protected StringBuffer result;
/*     */   
/*     */   public static ObjectWriter make(boolean paramBoolean, int paramInt1, int paramInt2) {
/*  34 */     if (paramBoolean) {
/*  35 */       return new IndentingObjectWriter(paramInt1, paramInt2);
/*     */     }
/*  37 */     return new SimpleObjectWriter();
/*     */   }
/*     */   public abstract void startObject(Object paramObject);
/*     */   
/*     */   public abstract void startElement();
/*     */   
/*     */   public abstract void endElement();
/*     */   
/*     */   public abstract void endObject(String paramString);
/*     */   
/*     */   public abstract void endObject();
/*     */   
/*     */   public String toString() {
/*  50 */     return this.result.toString();
/*     */   } public void append(boolean paramBoolean) {
/*  52 */     this.result.append(paramBoolean);
/*     */   } public void append(char paramChar) {
/*  54 */     this.result.append(paramChar);
/*     */   } public void append(short paramShort) {
/*  56 */     this.result.append(paramShort);
/*     */   } public void append(int paramInt) {
/*  58 */     this.result.append(paramInt);
/*     */   } public void append(long paramLong) {
/*  60 */     this.result.append(paramLong);
/*     */   } public void append(float paramFloat) {
/*  62 */     this.result.append(paramFloat);
/*     */   } public void append(double paramDouble) {
/*  64 */     this.result.append(paramDouble);
/*     */   } public void append(String paramString) {
/*  66 */     this.result.append(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObjectWriter() {
/*  76 */     this.result = new StringBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void appendObjectHeader(Object paramObject) {
/*  81 */     this.result.append(paramObject.getClass().getName());
/*  82 */     this.result.append("<");
/*  83 */     this.result.append(System.identityHashCode(paramObject));
/*  84 */     this.result.append(">");
/*  85 */     Class<?> clazz = paramObject.getClass().getComponentType();
/*     */     
/*  87 */     if (clazz != null) {
/*  88 */       this.result.append("[");
/*  89 */       if (clazz == boolean.class) {
/*  90 */         boolean[] arrayOfBoolean = (boolean[])paramObject;
/*  91 */         this.result.append(arrayOfBoolean.length);
/*  92 */         this.result.append("]");
/*  93 */       } else if (clazz == byte.class) {
/*  94 */         byte[] arrayOfByte = (byte[])paramObject;
/*  95 */         this.result.append(arrayOfByte.length);
/*  96 */         this.result.append("]");
/*  97 */       } else if (clazz == short.class) {
/*  98 */         short[] arrayOfShort = (short[])paramObject;
/*  99 */         this.result.append(arrayOfShort.length);
/* 100 */         this.result.append("]");
/* 101 */       } else if (clazz == int.class) {
/* 102 */         int[] arrayOfInt = (int[])paramObject;
/* 103 */         this.result.append(arrayOfInt.length);
/* 104 */         this.result.append("]");
/* 105 */       } else if (clazz == long.class) {
/* 106 */         long[] arrayOfLong = (long[])paramObject;
/* 107 */         this.result.append(arrayOfLong.length);
/* 108 */         this.result.append("]");
/* 109 */       } else if (clazz == char.class) {
/* 110 */         char[] arrayOfChar = (char[])paramObject;
/* 111 */         this.result.append(arrayOfChar.length);
/* 112 */         this.result.append("]");
/* 113 */       } else if (clazz == float.class) {
/* 114 */         float[] arrayOfFloat = (float[])paramObject;
/* 115 */         this.result.append(arrayOfFloat.length);
/* 116 */         this.result.append("]");
/* 117 */       } else if (clazz == double.class) {
/* 118 */         double[] arrayOfDouble = (double[])paramObject;
/* 119 */         this.result.append(arrayOfDouble.length);
/* 120 */         this.result.append("]");
/*     */       } else {
/* 122 */         Object[] arrayOfObject = (Object[])paramObject;
/* 123 */         this.result.append(arrayOfObject.length);
/* 124 */         this.result.append("]");
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     this.result.append("(");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class IndentingObjectWriter
/*     */     extends ObjectWriter
/*     */   {
/*     */     private int level;
/*     */ 
/*     */     
/*     */     private int increment;
/*     */ 
/*     */ 
/*     */     
/*     */     public IndentingObjectWriter(int param1Int1, int param1Int2) {
/* 145 */       this.level = param1Int1;
/* 146 */       this.increment = param1Int2;
/* 147 */       startLine();
/*     */     }
/*     */ 
/*     */     
/*     */     private void startLine() {
/* 152 */       char[] arrayOfChar = new char[this.level * this.increment];
/* 153 */       Arrays.fill(arrayOfChar, ' ');
/* 154 */       this.result.append(arrayOfChar);
/*     */     }
/*     */ 
/*     */     
/*     */     public void startObject(Object param1Object) {
/* 159 */       appendObjectHeader(param1Object);
/* 160 */       this.level++;
/*     */     }
/*     */ 
/*     */     
/*     */     public void startElement() {
/* 165 */       this.result.append("\n");
/* 166 */       startLine();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void endElement() {}
/*     */ 
/*     */     
/*     */     public void endObject(String param1String) {
/* 175 */       this.level--;
/* 176 */       this.result.append(param1String);
/* 177 */       this.result.append(")");
/*     */     }
/*     */ 
/*     */     
/*     */     public void endObject() {
/* 182 */       this.level--;
/* 183 */       this.result.append("\n");
/* 184 */       startLine();
/* 185 */       this.result.append(")");
/*     */     } }
/*     */   
/*     */   private static class SimpleObjectWriter extends ObjectWriter {
/*     */     private SimpleObjectWriter() {}
/*     */     
/*     */     public void startObject(Object param1Object) {
/* 192 */       appendObjectHeader(param1Object);
/* 193 */       this.result.append(" ");
/*     */     }
/*     */ 
/*     */     
/*     */     public void startElement() {
/* 198 */       this.result.append(" ");
/*     */     }
/*     */ 
/*     */     
/*     */     public void endObject(String param1String) {
/* 203 */       this.result.append(param1String);
/* 204 */       this.result.append(")");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void endElement() {}
/*     */ 
/*     */     
/*     */     public void endObject() {
/* 213 */       this.result.append(")");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/ObjectWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */