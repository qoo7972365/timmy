/*     */ package sun.net.www.protocol.http;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import sun.net.www.HeaderParser;
/*     */ import sun.net.www.MessageHeader;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuthenticationHeader
/*     */ {
/*     */   MessageHeader rsp;
/*     */   HeaderParser preferred;
/*     */   String preferred_r;
/*     */   private final HttpCallerInfo hci;
/*     */   boolean dontUseNegotiate = false;
/*  91 */   static String authPref = null; String hdrname;
/*     */   
/*     */   public String toString() {
/*  94 */     return "AuthenticationHeader: prefer " + this.preferred_r;
/*     */   }
/*     */   HashMap<String, SchemeMapValue> schemes;
/*     */   static {
/*  98 */     authPref = AccessController.<String>doPrivileged(new GetPropertyAction("http.auth.preference"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     if (authPref != null) {
/* 108 */       authPref = authPref.toLowerCase();
/* 109 */       if (authPref.equals("spnego") || authPref.equals("kerberos")) {
/* 110 */         authPref = "negotiate";
/*     */       }
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
/*     */   public AuthenticationHeader(String paramString, MessageHeader paramMessageHeader, HttpCallerInfo paramHttpCallerInfo, boolean paramBoolean) {
/* 123 */     this(paramString, paramMessageHeader, paramHttpCallerInfo, paramBoolean, Collections.emptySet());
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
/*     */   public AuthenticationHeader(String paramString, MessageHeader paramMessageHeader, HttpCallerInfo paramHttpCallerInfo, boolean paramBoolean, Set<String> paramSet) {
/* 138 */     this.hci = paramHttpCallerInfo;
/* 139 */     this.dontUseNegotiate = paramBoolean;
/* 140 */     this.rsp = paramMessageHeader;
/* 141 */     this.hdrname = paramString;
/* 142 */     this.schemes = new HashMap<>();
/* 143 */     parse(paramSet);
/*     */   }
/*     */   
/*     */   public HttpCallerInfo getHttpCallerInfo() {
/* 147 */     return this.hci;
/*     */   }
/*     */   
/*     */   static class SchemeMapValue { SchemeMapValue(HeaderParser param1HeaderParser, String param1String) {
/* 151 */       this.raw = param1String; this.parser = param1HeaderParser;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     String raw;
/*     */ 
/*     */     
/*     */     HeaderParser parser; }
/*     */ 
/*     */ 
/*     */   
/*     */   private void parse(Set<String> paramSet) {
/* 164 */     Iterator<String> iterator = this.rsp.multiValueIterator(this.hdrname);
/* 165 */     while (iterator.hasNext()) {
/* 166 */       String str = iterator.next();
/*     */       
/* 168 */       HeaderParser headerParser = new HeaderParser(str);
/* 169 */       Iterator<String> iterator1 = headerParser.keys(); byte b;
/*     */       byte b1;
/* 171 */       for (b = 0, b1 = -1; iterator1.hasNext(); b++) {
/* 172 */         iterator1.next();
/* 173 */         if (headerParser.findValue(b) == null) {
/* 174 */           if (b1 != -1) {
/* 175 */             HeaderParser headerParser1 = headerParser.subsequence(b1, b);
/* 176 */             String str1 = headerParser1.findKey(0);
/* 177 */             if (!paramSet.contains(str1))
/* 178 */               this.schemes.put(str1, new SchemeMapValue(headerParser1, str)); 
/*     */           } 
/* 180 */           b1 = b;
/*     */         } 
/*     */       } 
/* 183 */       if (b > b1) {
/* 184 */         HeaderParser headerParser1 = headerParser.subsequence(b1, b);
/* 185 */         String str1 = headerParser1.findKey(0);
/* 186 */         if (!paramSet.contains(str1)) {
/* 187 */           this.schemes.put(str1, new SchemeMapValue(headerParser1, str));
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 194 */     SchemeMapValue schemeMapValue = null;
/* 195 */     if (authPref == null || (schemeMapValue = this.schemes.get(authPref)) == null) {
/*     */       
/* 197 */       if (schemeMapValue == null && !this.dontUseNegotiate) {
/* 198 */         SchemeMapValue schemeMapValue1 = this.schemes.get("negotiate");
/* 199 */         if (schemeMapValue1 != null) {
/* 200 */           if (this.hci == null || !NegotiateAuthentication.isSupported(new HttpCallerInfo(this.hci, "Negotiate"))) {
/* 201 */             schemeMapValue1 = null;
/*     */           }
/* 203 */           schemeMapValue = schemeMapValue1;
/*     */         } 
/*     */       } 
/*     */       
/* 207 */       if (schemeMapValue == null && !this.dontUseNegotiate) {
/* 208 */         SchemeMapValue schemeMapValue1 = this.schemes.get("kerberos");
/* 209 */         if (schemeMapValue1 != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 220 */           if (this.hci == null || !NegotiateAuthentication.isSupported(new HttpCallerInfo(this.hci, "Kerberos"))) {
/* 221 */             schemeMapValue1 = null;
/*     */           }
/* 223 */           schemeMapValue = schemeMapValue1;
/*     */         } 
/*     */       } 
/*     */       
/* 227 */       if (schemeMapValue == null && (
/* 228 */         schemeMapValue = this.schemes.get("digest")) == null && (
/* 229 */         !NTLMAuthenticationProxy.supported || (
/* 230 */         schemeMapValue = this.schemes.get("ntlm")) == null)) {
/* 231 */         schemeMapValue = this.schemes.get("basic");
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 236 */     else if (this.dontUseNegotiate && authPref.equals("negotiate")) {
/* 237 */       schemeMapValue = null;
/*     */     } 
/*     */ 
/*     */     
/* 241 */     if (schemeMapValue != null) {
/* 242 */       this.preferred = schemeMapValue.parser;
/* 243 */       this.preferred_r = schemeMapValue.raw;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderParser headerParser() {
/* 253 */     return this.preferred;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String scheme() {
/* 260 */     if (this.preferred != null) {
/* 261 */       return this.preferred.findKey(0);
/*     */     }
/* 263 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String raw() {
/* 270 */     return this.preferred_r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPresent() {
/* 277 */     return (this.preferred != null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/http/AuthenticationHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */