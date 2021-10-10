/*     */ package com.sun.jndi.cosnaming;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.CompoundName;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameParser;
/*     */ import javax.naming.NamingException;
/*     */ import org.omg.CosNaming.NameComponent;
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
/*     */ public final class CNNameParser
/*     */   implements NameParser
/*     */ {
/*  48 */   private static final Properties mySyntax = new Properties();
/*     */   
/*     */   private static final char kindSeparator = '.';
/*     */   
/*     */   static {
/*  53 */     mySyntax.put("jndi.syntax.direction", "left_to_right");
/*  54 */     mySyntax.put("jndi.syntax.separator", "/");
/*  55 */     mySyntax.put("jndi.syntax.escape", "\\");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final char compSeparator = '/';
/*     */ 
/*     */ 
/*     */   
/*     */   private static final char escapeChar = '\\';
/*     */ 
/*     */ 
/*     */   
/*     */   public Name parse(String paramString) throws NamingException {
/*  70 */     Vector<String> vector = insStringToStringifiedComps(paramString);
/*  71 */     return new CNCompoundName(vector.elements());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static NameComponent[] nameToCosName(Name paramName) throws InvalidNameException {
/*  82 */     int i = paramName.size();
/*  83 */     if (i == 0) {
/*  84 */       return new NameComponent[0];
/*     */     }
/*     */     
/*  87 */     NameComponent[] arrayOfNameComponent = new NameComponent[i];
/*  88 */     for (byte b = 0; b < i; b++) {
/*  89 */       arrayOfNameComponent[b] = parseComponent(paramName.get(b));
/*     */     }
/*  91 */     return arrayOfNameComponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String cosNameToInsString(NameComponent[] paramArrayOfNameComponent) {
/*  99 */     StringBuffer stringBuffer = new StringBuffer();
/* 100 */     for (byte b = 0; b < paramArrayOfNameComponent.length; b++) {
/* 101 */       if (b > 0) {
/* 102 */         stringBuffer.append('/');
/*     */       }
/* 104 */       stringBuffer.append(stringifyComponent(paramArrayOfNameComponent[b]));
/*     */     } 
/* 106 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Name cosNameToName(NameComponent[] paramArrayOfNameComponent) {
/* 115 */     CompositeName compositeName = new CompositeName();
/* 116 */     for (byte b = 0; paramArrayOfNameComponent != null && b < paramArrayOfNameComponent.length; b++) {
/*     */       try {
/* 118 */         compositeName.add(stringifyComponent(paramArrayOfNameComponent[b]));
/* 119 */       } catch (InvalidNameException invalidNameException) {}
/*     */     } 
/*     */ 
/*     */     
/* 123 */     return compositeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Vector<String> insStringToStringifiedComps(String paramString) throws InvalidNameException {
/* 134 */     int i = paramString.length();
/* 135 */     Vector<String> vector = new Vector(10);
/* 136 */     char[] arrayOfChar1 = new char[i];
/* 137 */     char[] arrayOfChar2 = new char[i];
/*     */ 
/*     */     
/* 140 */     for (byte b = 0; b < i; ) {
/* 141 */       byte b2 = 0, b1 = b2;
/* 142 */       boolean bool = true;
/* 143 */       while (b < i && 
/* 144 */         paramString.charAt(b) != '/') {
/*     */ 
/*     */         
/* 147 */         if (paramString.charAt(b) == '\\') {
/* 148 */           if (b + 1 >= i) {
/* 149 */             throw new InvalidNameException(paramString + ": unescaped \\ at end of component");
/*     */           }
/* 151 */           if (isMeta(paramString.charAt(b + 1))) {
/* 152 */             b++;
/* 153 */             if (bool) {
/* 154 */               arrayOfChar1[b1++] = paramString.charAt(b++); continue;
/*     */             } 
/* 156 */             arrayOfChar2[b2++] = paramString.charAt(b++);
/*     */             continue;
/*     */           } 
/* 159 */           throw new InvalidNameException(paramString + ": invalid character being escaped");
/*     */         } 
/*     */ 
/*     */         
/* 163 */         if (bool && paramString.charAt(b) == '.') {
/*     */           
/* 165 */           b++;
/* 166 */           bool = false;
/*     */           continue;
/*     */         } 
/* 169 */         if (bool) {
/* 170 */           arrayOfChar1[b1++] = paramString.charAt(b++); continue;
/*     */         } 
/* 172 */         arrayOfChar2[b2++] = paramString.charAt(b++);
/*     */       } 
/*     */ 
/*     */       
/* 176 */       vector.addElement(stringifyComponent(new NameComponent(new String(arrayOfChar1, 0, b1), new String(arrayOfChar2, 0, b2))));
/*     */ 
/*     */ 
/*     */       
/* 180 */       if (b < i) {
/* 181 */         b++;
/*     */       }
/*     */     } 
/*     */     
/* 185 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static NameComponent parseComponent(String paramString) throws InvalidNameException {
/* 193 */     NameComponent nameComponent = new NameComponent();
/* 194 */     int i = -1;
/* 195 */     int j = paramString.length();
/*     */     
/* 197 */     byte b = 0;
/* 198 */     char[] arrayOfChar = new char[j];
/* 199 */     boolean bool = false;
/*     */     
/*     */     int k;
/* 202 */     for (k = 0; k < j && i < 0; k++) {
/* 203 */       if (bool) {
/* 204 */         arrayOfChar[b++] = paramString.charAt(k);
/* 205 */         bool = false;
/* 206 */       } else if (paramString.charAt(k) == '\\') {
/* 207 */         if (k + 1 >= j) {
/* 208 */           throw new InvalidNameException(paramString + ": unescaped \\ at end of component");
/*     */         }
/* 210 */         if (isMeta(paramString.charAt(k + 1))) {
/* 211 */           bool = true;
/*     */         } else {
/* 213 */           throw new InvalidNameException(paramString + ": invalid character being escaped");
/*     */         }
/*     */       
/* 216 */       } else if (paramString.charAt(k) == '.') {
/* 217 */         i = k;
/*     */       } else {
/* 219 */         arrayOfChar[b++] = paramString.charAt(k);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 224 */     nameComponent.id = new String(arrayOfChar, 0, b);
/*     */ 
/*     */     
/* 227 */     if (i < 0) {
/* 228 */       nameComponent.kind = "";
/*     */     } else {
/*     */       
/* 231 */       b = 0;
/* 232 */       bool = false;
/* 233 */       for (k = i + 1; k < j; k++) {
/* 234 */         if (bool) {
/* 235 */           arrayOfChar[b++] = paramString.charAt(k);
/* 236 */           bool = false;
/* 237 */         } else if (paramString.charAt(k) == '\\') {
/* 238 */           if (k + 1 >= j) {
/* 239 */             throw new InvalidNameException(paramString + ": unescaped \\ at end of component");
/*     */           }
/* 241 */           if (isMeta(paramString.charAt(k + 1))) {
/* 242 */             bool = true;
/*     */           } else {
/* 244 */             throw new InvalidNameException(paramString + ": invalid character being escaped");
/*     */           } 
/*     */         } else {
/*     */           
/* 248 */           arrayOfChar[b++] = paramString.charAt(k);
/*     */         } 
/*     */       } 
/* 251 */       nameComponent.kind = new String(arrayOfChar, 0, b);
/*     */     } 
/* 253 */     return nameComponent;
/*     */   }
/*     */   
/*     */   private static String stringifyComponent(NameComponent paramNameComponent) {
/* 257 */     StringBuffer stringBuffer = new StringBuffer(escape(paramNameComponent.id));
/* 258 */     if (paramNameComponent.kind != null && !paramNameComponent.kind.equals("")) {
/* 259 */       stringBuffer.append('.' + escape(paramNameComponent.kind));
/*     */     }
/* 261 */     if (stringBuffer.length() == 0) {
/* 262 */       return ".";
/*     */     }
/* 264 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String escape(String paramString) {
/* 273 */     if (paramString.indexOf('.') < 0 && paramString
/* 274 */       .indexOf('/') < 0 && paramString
/* 275 */       .indexOf('\\') < 0) {
/* 276 */       return paramString;
/*     */     }
/* 278 */     int i = paramString.length();
/* 279 */     byte b1 = 0;
/* 280 */     char[] arrayOfChar = new char[i + i];
/* 281 */     for (byte b2 = 0; b2 < i; b2++) {
/* 282 */       if (isMeta(paramString.charAt(b2))) {
/* 283 */         arrayOfChar[b1++] = '\\';
/*     */       }
/* 285 */       arrayOfChar[b1++] = paramString.charAt(b2);
/*     */     } 
/* 287 */     return new String(arrayOfChar, 0, b1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isMeta(char paramChar) {
/* 295 */     switch (paramChar) {
/*     */       case '.':
/*     */       case '/':
/*     */       case '\\':
/* 299 */         return true;
/*     */     } 
/* 301 */     return false;
/*     */   }
/*     */   
/*     */   static final class CNCompoundName
/*     */     extends CompoundName
/*     */   {
/*     */     private static final long serialVersionUID = -6599252802678482317L;
/*     */     
/*     */     CNCompoundName(Enumeration<String> param1Enumeration) {
/* 310 */       super(param1Enumeration, CNNameParser.mySyntax);
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 314 */       return new CNCompoundName(getAll());
/*     */     }
/*     */     
/*     */     public Name getPrefix(int param1Int) {
/* 318 */       Enumeration<String> enumeration = super.getPrefix(param1Int).getAll();
/* 319 */       return new CNCompoundName(enumeration);
/*     */     }
/*     */     
/*     */     public Name getSuffix(int param1Int) {
/* 323 */       Enumeration<String> enumeration = super.getSuffix(param1Int).getAll();
/* 324 */       return new CNCompoundName(enumeration);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*     */       try {
/* 330 */         return CNNameParser.cosNameToInsString(CNNameParser.nameToCosName(this));
/* 331 */       } catch (InvalidNameException invalidNameException) {
/* 332 */         return super.toString();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/cosnaming/CNNameParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */