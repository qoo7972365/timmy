/*     */ package com.sun.corba.se.impl.dynamicany;
/*     */ 
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.TypeCodePackage.BadKind;
/*     */ import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
/*     */ import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
/*     */ import org.omg.DynamicAny.DynFixed;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DynFixedImpl
/*     */   extends DynAnyBasicImpl
/*     */   implements DynFixed
/*     */ {
/*     */   private DynFixedImpl() {
/*  48 */     this((ORB)null, (Any)null, false);
/*     */   }
/*     */   
/*     */   protected DynFixedImpl(ORB paramORB, Any paramAny, boolean paramBoolean) {
/*  52 */     super(paramORB, paramAny, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   protected DynFixedImpl(ORB paramORB, TypeCode paramTypeCode) {
/*  57 */     super(paramORB, paramTypeCode);
/*  58 */     this.index = -1;
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
/*     */   public String get_value() {
/*  74 */     if (this.status == 2) {
/*  75 */       throw this.wrapper.dynAnyDestroyed();
/*     */     }
/*  77 */     return this.any.extract_fixed().toString();
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
/*     */   public boolean set_value(String paramString) throws TypeMismatch, InvalidValue {
/*     */     String str3, str4;
/*     */     int j;
/*     */     BigDecimal bigDecimal;
/* 101 */     if (this.status == 2) {
/* 102 */       throw this.wrapper.dynAnyDestroyed();
/*     */     }
/* 104 */     short s1 = 0;
/* 105 */     short s2 = 0;
/* 106 */     boolean bool = true;
/*     */     try {
/* 108 */       s1 = this.any.type().fixed_digits();
/* 109 */       s2 = this.any.type().fixed_scale();
/* 110 */     } catch (BadKind badKind) {}
/*     */ 
/*     */     
/* 113 */     String str1 = paramString.trim();
/* 114 */     if (str1.length() == 0) {
/* 115 */       throw new TypeMismatch();
/*     */     }
/* 117 */     String str2 = "";
/* 118 */     if (str1.charAt(0) == '-') {
/* 119 */       str2 = "-";
/* 120 */       str1 = str1.substring(1);
/* 121 */     } else if (str1.charAt(0) == '+') {
/* 122 */       str2 = "+";
/* 123 */       str1 = str1.substring(1);
/*     */     } 
/*     */     
/* 126 */     int i = str1.indexOf('d');
/* 127 */     if (i == -1) {
/* 128 */       i = str1.indexOf('D');
/*     */     }
/* 130 */     if (i != -1) {
/* 131 */       str1 = str1.substring(0, i);
/*     */     }
/*     */     
/* 134 */     if (str1.length() == 0) {
/* 135 */       throw new TypeMismatch();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     int k = str1.indexOf('.');
/* 142 */     if (k == -1) {
/* 143 */       str3 = str1;
/* 144 */       str4 = null;
/* 145 */       boolean bool1 = false;
/* 146 */       j = str3.length();
/* 147 */     } else if (k == 0) {
/* 148 */       str3 = null;
/* 149 */       str4 = str1;
/* 150 */       int m = str4.length();
/* 151 */       j = m;
/*     */     } else {
/* 153 */       str3 = str1.substring(0, k);
/* 154 */       str4 = str1.substring(k + 1);
/* 155 */       int m = str4.length();
/* 156 */       j = str3.length() + m;
/*     */     } 
/*     */     
/* 159 */     if (j > s1) {
/* 160 */       bool = false;
/*     */       
/* 162 */       if (str3.length() < s1) {
/* 163 */         str4 = str4.substring(0, s1 - str3.length());
/* 164 */       } else if (str3.length() == s1) {
/*     */ 
/*     */         
/* 167 */         str4 = null;
/*     */       }
/*     */       else {
/*     */         
/* 171 */         throw new InvalidValue();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 186 */       new BigInteger(str3);
/* 187 */       if (str4 == null) {
/* 188 */         bigDecimal = new BigDecimal(str2 + str3);
/*     */       } else {
/* 190 */         new BigInteger(str4);
/* 191 */         bigDecimal = new BigDecimal(str2 + str3 + "." + str4);
/*     */       } 
/* 193 */     } catch (NumberFormatException numberFormatException) {
/* 194 */       throw new TypeMismatch();
/*     */     } 
/* 196 */     this.any.insert_fixed(bigDecimal, this.any.type());
/* 197 */     return bool;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 201 */     short s1 = 0;
/* 202 */     short s2 = 0;
/*     */     try {
/* 204 */       s1 = this.any.type().fixed_digits();
/* 205 */       s2 = this.any.type().fixed_scale();
/* 206 */     } catch (BadKind badKind) {}
/*     */     
/* 208 */     return "DynFixed with value=" + get_value() + ", digits=" + s1 + ", scale=" + s2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/dynamicany/DynFixedImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */