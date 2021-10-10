/*     */ package com.sun.xml.internal.bind.api.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.lang.model.SourceVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface NameConverter
/*     */ {
/*  94 */   public static final NameConverter standard = new Standard();
/*     */   
/*     */   public static class Standard extends NameUtil implements NameConverter {
/*     */     public String toClassName(String s) {
/*  98 */       return toMixedCaseName(toWordList(s), true);
/*     */     }
/*     */     public String toVariableName(String s) {
/* 101 */       return toMixedCaseName(toWordList(s), false);
/*     */     }
/*     */     public String toInterfaceName(String token) {
/* 104 */       return toClassName(token);
/*     */     }
/*     */     public String toPropertyName(String s) {
/* 107 */       String prop = toClassName(s);
/*     */ 
/*     */       
/* 110 */       if (prop.equals("Class"))
/* 111 */         prop = "Clazz"; 
/* 112 */       return prop;
/*     */     }
/*     */     public String toConstantName(String token) {
/* 115 */       return super.toConstantName(token);
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
/*     */     public String toPackageName(String nsUri) {
/* 127 */       int idx = nsUri.indexOf(':');
/* 128 */       String scheme = "";
/* 129 */       if (idx >= 0) {
/* 130 */         scheme = nsUri.substring(0, idx);
/* 131 */         if (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("urn")) {
/* 132 */           nsUri = nsUri.substring(idx + 1);
/*     */         }
/*     */       } 
/*     */       
/* 136 */       ArrayList<String> tokens = tokenize(nsUri, "/: ");
/* 137 */       if (tokens.size() == 0) {
/* 138 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 142 */       if (tokens.size() > 1) {
/*     */ 
/*     */ 
/*     */         
/* 146 */         String lastToken = tokens.get(tokens.size() - 1);
/* 147 */         idx = lastToken.lastIndexOf('.');
/* 148 */         if (idx > 0) {
/* 149 */           lastToken = lastToken.substring(0, idx);
/* 150 */           tokens.set(tokens.size() - 1, lastToken);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 155 */       String domain = tokens.get(0);
/* 156 */       idx = domain.indexOf(':');
/* 157 */       if (idx >= 0) domain = domain.substring(0, idx); 
/* 158 */       ArrayList<String> r = reverse(tokenize(domain, scheme.equals("urn") ? ".-" : "."));
/* 159 */       if (((String)r.get(r.size() - 1)).equalsIgnoreCase("www"))
/*     */       {
/* 161 */         r.remove(r.size() - 1);
/*     */       }
/*     */ 
/*     */       
/* 165 */       tokens.addAll(1, r);
/* 166 */       tokens.remove(0);
/*     */ 
/*     */       
/* 169 */       for (int i = 0; i < tokens.size(); i++) {
/*     */ 
/*     */         
/* 172 */         String token = tokens.get(i);
/* 173 */         token = removeIllegalIdentifierChars(token);
/*     */ 
/*     */         
/* 176 */         if (SourceVersion.isKeyword(token.toLowerCase())) {
/* 177 */           token = '_' + token;
/*     */         }
/*     */         
/* 180 */         tokens.set(i, token.toLowerCase());
/*     */       } 
/*     */ 
/*     */       
/* 184 */       return combine(tokens, '.');
/*     */     }
/*     */ 
/*     */     
/*     */     private static String removeIllegalIdentifierChars(String token) {
/* 189 */       StringBuilder newToken = new StringBuilder(token.length() + 1);
/* 190 */       for (int i = 0; i < token.length(); i++) {
/* 191 */         char c = token.charAt(i);
/* 192 */         if (i == 0 && !Character.isJavaIdentifierStart(c)) {
/* 193 */           newToken.append('_');
/*     */         }
/* 195 */         if (!Character.isJavaIdentifierPart(c)) {
/* 196 */           newToken.append('_');
/*     */         } else {
/* 198 */           newToken.append(c);
/*     */         } 
/*     */       } 
/* 201 */       return newToken.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     private static ArrayList<String> tokenize(String str, String sep) {
/* 206 */       StringTokenizer tokens = new StringTokenizer(str, sep);
/* 207 */       ArrayList<String> r = new ArrayList<>();
/*     */       
/* 209 */       while (tokens.hasMoreTokens()) {
/* 210 */         r.add(tokens.nextToken());
/*     */       }
/* 212 */       return r;
/*     */     }
/*     */     
/*     */     private static <T> ArrayList<T> reverse(List<T> a) {
/* 216 */       ArrayList<T> r = new ArrayList<>();
/*     */       
/* 218 */       for (int i = a.size() - 1; i >= 0; i--) {
/* 219 */         r.add(a.get(i));
/*     */       }
/* 221 */       return r;
/*     */     }
/*     */     
/*     */     private static String combine(List<E> r, char sep) {
/* 225 */       StringBuilder buf = new StringBuilder(r.get(0).toString());
/*     */       
/* 227 */       for (int i = 1; i < r.size(); i++) {
/* 228 */         buf.append(sep);
/* 229 */         buf.append(r.get(i));
/*     */       } 
/*     */       
/* 232 */       return buf.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 242 */   public static final NameConverter jaxrpcCompatible = new Standard() {
/*     */       protected boolean isPunct(char c) {
/* 244 */         return (c == '.' || c == '-' || c == ';' || c == '·' || c == '·' || c == '۝' || c == '۞');
/*     */       }
/*     */       
/*     */       protected boolean isLetter(char c) {
/* 248 */         return (super.isLetter(c) || c == '_');
/*     */       }
/*     */       
/*     */       protected int classify(char c0) {
/* 252 */         if (c0 == '_') return 2; 
/* 253 */         return super.classify(c0);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   public static final NameConverter smart = new Standard() {
/*     */       public String toConstantName(String token) {
/* 262 */         String name = super.toConstantName(token);
/* 263 */         if (!SourceVersion.isKeyword(name)) {
/* 264 */           return name;
/*     */         }
/* 266 */         return '_' + name;
/*     */       }
/*     */     };
/*     */   
/*     */   String toClassName(String paramString);
/*     */   
/*     */   String toInterfaceName(String paramString);
/*     */   
/*     */   String toPropertyName(String paramString);
/*     */   
/*     */   String toConstantName(String paramString);
/*     */   
/*     */   String toVariableName(String paramString);
/*     */   
/*     */   String toPackageName(String paramString);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/api/impl/NameConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */