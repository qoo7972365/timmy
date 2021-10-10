/*     */ package com.sun.org.apache.xerces.internal.impl.dv.xs;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
/*     */ import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
/*     */ import com.sun.org.apache.xerces.internal.xs.datatypes.XSDouble;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoubleDV
/*     */   extends TypeValidator
/*     */ {
/*     */   public short getAllowedFacets() {
/*  40 */     return 2552;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
/*     */     try {
/*  46 */       return new XDouble(content);
/*  47 */     } catch (NumberFormatException ex) {
/*  48 */       throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { content, "double" });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int compare(Object value1, Object value2) {
/*  54 */     return ((XDouble)value1).compareTo((XDouble)value2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIdentical(Object value1, Object value2) {
/*  60 */     if (value2 instanceof XDouble) {
/*  61 */       return ((XDouble)value1).isIdentical((XDouble)value2);
/*     */     }
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isPossibleFP(String val) {
/*  72 */     int length = val.length();
/*  73 */     for (int i = 0; i < length; i++) {
/*  74 */       char c = val.charAt(i);
/*  75 */       if ((c < '0' || c > '9') && c != '.' && c != '-' && c != '+' && c != 'E' && c != 'e')
/*     */       {
/*  77 */         return false;
/*     */       }
/*     */     } 
/*  80 */     return true;
/*     */   }
/*     */   
/*     */   private static final class XDouble implements XSDouble { private final double value;
/*     */     
/*     */     public XDouble(String s) throws NumberFormatException {
/*  86 */       if (DoubleDV.isPossibleFP(s)) {
/*  87 */         this.value = Double.parseDouble(s);
/*     */       }
/*  89 */       else if (s.equals("INF")) {
/*  90 */         this.value = Double.POSITIVE_INFINITY;
/*     */       }
/*  92 */       else if (s.equals("-INF")) {
/*  93 */         this.value = Double.NEGATIVE_INFINITY;
/*     */       }
/*  95 */       else if (s.equals("NaN")) {
/*  96 */         this.value = Double.NaN;
/*     */       } else {
/*     */         
/*  99 */         throw new NumberFormatException(s);
/*     */       } 
/*     */     }
/*     */     private String canonical;
/*     */     public boolean equals(Object val) {
/* 104 */       if (val == this) {
/* 105 */         return true;
/*     */       }
/* 107 */       if (!(val instanceof XDouble))
/* 108 */         return false; 
/* 109 */       XDouble oval = (XDouble)val;
/*     */ 
/*     */       
/* 112 */       if (this.value == oval.value) {
/* 113 */         return true;
/*     */       }
/* 115 */       if (this.value != this.value && oval.value != oval.value) {
/* 116 */         return true;
/*     */       }
/* 118 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 123 */       if (this.value == 0.0D) {
/* 124 */         return 0;
/*     */       }
/* 126 */       long v = Double.doubleToLongBits(this.value);
/* 127 */       return (int)(v ^ v >>> 32L);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isIdentical(XDouble val) {
/* 132 */       if (val == this) {
/* 133 */         return true;
/*     */       }
/*     */       
/* 136 */       if (this.value == val.value) {
/* 137 */         return (this.value != 0.0D || 
/* 138 */           Double.doubleToLongBits(this.value) == Double.doubleToLongBits(val.value));
/*     */       }
/*     */       
/* 141 */       if (this.value != this.value && val.value != val.value) {
/* 142 */         return true;
/*     */       }
/* 144 */       return false;
/*     */     }
/*     */     
/*     */     private int compareTo(XDouble val) {
/* 148 */       double oval = val.value;
/*     */ 
/*     */       
/* 151 */       if (this.value < oval) {
/* 152 */         return -1;
/*     */       }
/* 154 */       if (this.value > oval) {
/* 155 */         return 1;
/*     */       }
/*     */       
/* 158 */       if (this.value == oval) {
/* 159 */         return 0;
/*     */       }
/*     */ 
/*     */       
/* 163 */       if (this.value != this.value) {
/*     */         
/* 165 */         if (oval != oval) {
/* 166 */           return 0;
/*     */         }
/* 168 */         return 2;
/*     */       } 
/*     */ 
/*     */       
/* 172 */       return 2;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized String toString() {
/* 177 */       if (this.canonical == null) {
/* 178 */         if (this.value == Double.POSITIVE_INFINITY) {
/* 179 */           this.canonical = "INF";
/* 180 */         } else if (this.value == Double.NEGATIVE_INFINITY) {
/* 181 */           this.canonical = "-INF";
/* 182 */         } else if (this.value != this.value) {
/* 183 */           this.canonical = "NaN";
/*     */         }
/* 185 */         else if (this.value == 0.0D) {
/* 186 */           this.canonical = "0.0E1";
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 191 */           this.canonical = Double.toString(this.value);
/*     */ 
/*     */           
/* 194 */           if (this.canonical.indexOf('E') == -1) {
/* 195 */             int len = this.canonical.length();
/*     */             
/* 197 */             char[] chars = new char[len + 3];
/* 198 */             this.canonical.getChars(0, len, chars, 0);
/*     */             
/* 200 */             int edp = (chars[0] == '-') ? 2 : 1;
/*     */             
/* 202 */             if (this.value >= 1.0D || this.value <= -1.0D) {
/*     */               
/* 204 */               int dp = this.canonical.indexOf('.');
/*     */               
/* 206 */               for (int i = dp; i > edp; i--) {
/* 207 */                 chars[i] = chars[i - 1];
/*     */               }
/* 209 */               chars[edp] = '.';
/*     */               
/* 211 */               while (chars[len - 1] == '0') {
/* 212 */                 len--;
/*     */               }
/* 214 */               if (chars[len - 1] == '.') {
/* 215 */                 len++;
/*     */               }
/* 217 */               chars[len++] = 'E';
/*     */               
/* 219 */               int shift = dp - edp;
/*     */ 
/*     */               
/* 222 */               chars[len++] = (char)(shift + 48);
/*     */             }
/*     */             else {
/*     */               
/* 226 */               int nzp = edp + 1;
/*     */               
/* 228 */               while (chars[nzp] == '0') {
/* 229 */                 nzp++;
/*     */               }
/* 231 */               chars[edp - 1] = chars[nzp];
/* 232 */               chars[edp] = '.';
/*     */               
/* 234 */               for (int i = nzp + 1, j = edp + 1; i < len; i++, j++) {
/* 235 */                 chars[j] = chars[i];
/*     */               }
/* 237 */               len -= nzp - edp;
/*     */               
/* 239 */               if (len == edp + 1) {
/* 240 */                 chars[len++] = '0';
/*     */               }
/* 242 */               chars[len++] = 'E';
/* 243 */               chars[len++] = '-';
/*     */               
/* 245 */               int shift = nzp - edp;
/*     */ 
/*     */               
/* 248 */               chars[len++] = (char)(shift + 48);
/*     */             } 
/* 250 */             this.canonical = new String(chars, 0, len);
/*     */           } 
/*     */         } 
/*     */       }
/* 254 */       return this.canonical;
/*     */     }
/*     */     public double getValue() {
/* 257 */       return this.value;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dv/xs/DoubleDV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */