/*     */ package com.sun.org.apache.bcel.internal.generic;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;
/*     */ import com.sun.org.apache.bcel.internal.classfile.Utility;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Type
/*     */   implements Serializable
/*     */ {
/*     */   protected byte type;
/*     */   protected String signature;
/*  77 */   public static final BasicType VOID = new BasicType((byte)12);
/*  78 */   public static final BasicType BOOLEAN = new BasicType((byte)4);
/*  79 */   public static final BasicType INT = new BasicType((byte)10);
/*  80 */   public static final BasicType SHORT = new BasicType((byte)9);
/*  81 */   public static final BasicType BYTE = new BasicType((byte)8);
/*  82 */   public static final BasicType LONG = new BasicType((byte)11);
/*  83 */   public static final BasicType DOUBLE = new BasicType((byte)7);
/*  84 */   public static final BasicType FLOAT = new BasicType((byte)6);
/*  85 */   public static final BasicType CHAR = new BasicType((byte)5);
/*  86 */   public static final ObjectType OBJECT = new ObjectType("java.lang.Object");
/*  87 */   public static final ObjectType STRING = new ObjectType("java.lang.String");
/*  88 */   public static final ObjectType STRINGBUFFER = new ObjectType("java.lang.StringBuffer");
/*  89 */   public static final ObjectType THROWABLE = new ObjectType("java.lang.Throwable");
/*  90 */   public static final Type[] NO_ARGS = new Type[0];
/*  91 */   public static final ReferenceType NULL = new ReferenceType() {  }
/*  92 */   ; public static final Type UNKNOWN = new Type((byte)15, "<unknown object>") {  }
/*     */   ;
/*     */   
/*     */   protected Type(byte t, String s) {
/*  96 */     this.type = t;
/*  97 */     this.signature = s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSignature() {
/* 103 */     return this.signature;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getType() {
/* 108 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 114 */     switch (this.type) { case 7:
/*     */       case 11:
/* 116 */         return 2;
/* 117 */       case 12: return 0; }
/* 118 */      return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 126 */     return (equals(NULL) || this.type >= 15) ? this.signature : 
/* 127 */       Utility.signatureToString(this.signature, false);
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
/*     */   public static String getMethodSignature(Type return_type, Type[] arg_types) {
/* 139 */     StringBuffer buf = new StringBuffer("(");
/* 140 */     int length = (arg_types == null) ? 0 : arg_types.length;
/*     */     
/* 142 */     for (int i = 0; i < length; i++) {
/* 143 */       buf.append(arg_types[i].getSignature());
/*     */     }
/* 145 */     buf.append(')');
/* 146 */     buf.append(return_type.getSignature());
/*     */     
/* 148 */     return buf.toString();
/*     */   }
/*     */   
/* 151 */   private static int consumed_chars = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final Type getType(String signature) throws StringIndexOutOfBoundsException {
/* 161 */     byte type = Utility.typeOfSignature(signature);
/*     */     
/* 163 */     if (type <= 12) {
/* 164 */       consumed_chars = 1;
/* 165 */       return BasicType.getType(type);
/* 166 */     }  if (type == 13) {
/* 167 */       int dim = 0;
/*     */       do {
/* 169 */         dim++;
/* 170 */       } while (signature.charAt(dim) == '[');
/*     */ 
/*     */       
/* 173 */       Type t = getType(signature.substring(dim));
/*     */       
/* 175 */       consumed_chars += dim;
/*     */       
/* 177 */       return new ArrayType(t, dim);
/*     */     } 
/* 179 */     int index = signature.indexOf(';');
/*     */     
/* 181 */     if (index < 0) {
/* 182 */       throw new ClassFormatException("Invalid signature: " + signature);
/*     */     }
/* 184 */     consumed_chars = index + 1;
/*     */     
/* 186 */     return new ObjectType(signature.substring(1, index).replace('/', '.'));
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
/*     */   public static Type getReturnType(String signature) {
/*     */     try {
/* 199 */       int index = signature.lastIndexOf(')') + 1;
/* 200 */       return getType(signature.substring(index));
/* 201 */     } catch (StringIndexOutOfBoundsException e) {
/* 202 */       throw new ClassFormatException("Invalid method signature: " + signature);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type[] getArgumentTypes(String signature) {
/* 212 */     ArrayList<Type> vec = new ArrayList();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 217 */       if (signature.charAt(0) != '(') {
/* 218 */         throw new ClassFormatException("Invalid method signature: " + signature);
/*     */       }
/* 220 */       int index = 1;
/*     */       
/* 222 */       while (signature.charAt(index) != ')') {
/* 223 */         vec.add(getType(signature.substring(index)));
/* 224 */         index += consumed_chars;
/*     */       } 
/* 226 */     } catch (StringIndexOutOfBoundsException e) {
/* 227 */       throw new ClassFormatException("Invalid method signature: " + signature);
/*     */     } 
/*     */     
/* 230 */     Type[] types = new Type[vec.size()];
/* 231 */     vec.toArray(types);
/* 232 */     return types;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getType(Class<int> cl) {
/* 240 */     if (cl == null) {
/* 241 */       throw new IllegalArgumentException("Class must not be null");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 247 */     if (cl.isArray())
/* 248 */       return getType(cl.getName()); 
/* 249 */     if (cl.isPrimitive()) {
/* 250 */       if (cl == int.class)
/* 251 */         return INT; 
/* 252 */       if (cl == void.class)
/* 253 */         return VOID; 
/* 254 */       if (cl == double.class)
/* 255 */         return DOUBLE; 
/* 256 */       if (cl == float.class)
/* 257 */         return FLOAT; 
/* 258 */       if (cl == boolean.class)
/* 259 */         return BOOLEAN; 
/* 260 */       if (cl == byte.class)
/* 261 */         return BYTE; 
/* 262 */       if (cl == short.class)
/* 263 */         return SHORT; 
/* 264 */       if (cl == byte.class)
/* 265 */         return BYTE; 
/* 266 */       if (cl == long.class)
/* 267 */         return LONG; 
/* 268 */       if (cl == char.class) {
/* 269 */         return CHAR;
/*     */       }
/* 271 */       throw new IllegalStateException("Ooops, what primitive type is " + cl);
/*     */     } 
/*     */     
/* 274 */     return new ObjectType(cl.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getSignature(Method meth) {
/* 279 */     StringBuffer sb = new StringBuffer("(");
/* 280 */     Class[] params = meth.getParameterTypes();
/*     */     
/* 282 */     for (int j = 0; j < params.length; j++) {
/* 283 */       sb.append(getType(params[j]).getSignature());
/*     */     }
/*     */     
/* 286 */     sb.append(")");
/* 287 */     sb.append(getType(meth.getReturnType()).getSignature());
/* 288 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/generic/Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */