/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class URIName
/*     */   implements GeneralNameInterface
/*     */ {
/*     */   private URI uri;
/*     */   private String host;
/*     */   private DNSName hostDNS;
/*     */   private IPAddressName hostIP;
/*     */   
/*     */   public URIName(DerValue paramDerValue) throws IOException {
/*  96 */     this(paramDerValue.getIA5String());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URIName(String paramString) throws IOException {
/*     */     try {
/* 107 */       this.uri = new URI(paramString);
/* 108 */     } catch (URISyntaxException uRISyntaxException) {
/* 109 */       throw new IOException("invalid URI name:" + paramString, uRISyntaxException);
/*     */     } 
/* 111 */     if (this.uri.getScheme() == null) {
/* 112 */       throw new IOException("URI name must include scheme:" + paramString);
/*     */     }
/*     */     
/* 115 */     this.host = this.uri.getHost();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     if (this.host != null) {
/* 121 */       if (this.host.charAt(0) == '[') {
/*     */         
/* 123 */         String str = this.host.substring(1, this.host.length() - 1);
/*     */         try {
/* 125 */           this.hostIP = new IPAddressName(str);
/* 126 */         } catch (IOException iOException) {
/* 127 */           throw new IOException("invalid URI name (host portion is not a valid IPv6 address):" + paramString);
/*     */         } 
/*     */       } else {
/*     */         
/*     */         try {
/* 132 */           this.hostDNS = new DNSName(this.host);
/* 133 */         } catch (IOException iOException) {
/*     */ 
/*     */           
/*     */           try {
/* 137 */             this.hostIP = new IPAddressName(this.host);
/* 138 */           } catch (Exception exception) {
/* 139 */             throw new IOException("invalid URI name (host portion is not a valid DNSName, IPv4 address, or IPv6 address):" + paramString);
/*     */           } 
/*     */         } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static URIName nameConstraint(DerValue paramDerValue) throws IOException {
/*     */     URI uRI;
/* 158 */     String str = paramDerValue.getIA5String();
/*     */     try {
/* 160 */       uRI = new URI(str);
/* 161 */     } catch (URISyntaxException uRISyntaxException) {
/* 162 */       throw new IOException("invalid URI name constraint:" + str, uRISyntaxException);
/*     */     } 
/* 164 */     if (uRI.getScheme() == null) {
/* 165 */       String str1 = uRI.getSchemeSpecificPart();
/*     */       try {
/*     */         DNSName dNSName;
/* 168 */         if (str1.startsWith(".")) {
/* 169 */           dNSName = new DNSName(str1.substring(1));
/*     */         } else {
/* 171 */           dNSName = new DNSName(str1);
/*     */         } 
/* 173 */         return new URIName(uRI, str1, dNSName);
/* 174 */       } catch (IOException iOException) {
/* 175 */         throw new IOException("invalid URI name constraint:" + str, iOException);
/*     */       } 
/*     */     } 
/* 178 */     throw new IOException("invalid URI name constraint (should not include scheme):" + str);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   URIName(URI paramURI, String paramString, DNSName paramDNSName) {
/* 184 */     this.uri = paramURI;
/* 185 */     this.host = paramString;
/* 186 */     this.hostDNS = paramDNSName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 193 */     return 6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 203 */     paramDerOutputStream.putIA5String(this.uri.toASCIIString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 210 */     return "URIName: " + this.uri.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 219 */     if (this == paramObject) {
/* 220 */       return true;
/*     */     }
/*     */     
/* 223 */     if (!(paramObject instanceof URIName)) {
/* 224 */       return false;
/*     */     }
/*     */     
/* 227 */     URIName uRIName = (URIName)paramObject;
/*     */     
/* 229 */     return this.uri.equals(uRIName.getURI());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URI getURI() {
/* 236 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 243 */     return this.uri.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getScheme() {
/* 252 */     return this.uri.getScheme();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHost() {
/* 261 */     return this.host;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getHostObject() {
/* 272 */     if (this.hostIP != null) {
/* 273 */       return this.hostIP;
/*     */     }
/* 275 */     return this.hostDNS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 285 */     return this.uri.hashCode();
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
/*     */   public int constrains(GeneralNameInterface paramGeneralNameInterface) throws UnsupportedOperationException {
/*     */     int i;
/* 320 */     if (paramGeneralNameInterface == null) {
/* 321 */       i = -1;
/* 322 */     } else if (paramGeneralNameInterface.getType() != 6) {
/* 323 */       i = -1;
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 329 */       String str = ((URIName)paramGeneralNameInterface).getHost();
/*     */ 
/*     */       
/* 332 */       if (str.equalsIgnoreCase(this.host)) {
/* 333 */         i = 0;
/*     */       } else {
/* 335 */         Object object = ((URIName)paramGeneralNameInterface).getHostObject();
/*     */         
/* 337 */         if (this.hostDNS == null || !(object instanceof DNSName)) {
/*     */ 
/*     */           
/* 340 */           i = 3;
/*     */         } else {
/*     */           
/* 343 */           boolean bool1 = (this.host.charAt(0) == '.') ? true : false;
/* 344 */           boolean bool2 = (str.charAt(0) == '.') ? true : false;
/* 345 */           DNSName dNSName = (DNSName)object;
/*     */ 
/*     */           
/* 348 */           i = this.hostDNS.constrains(dNSName);
/*     */ 
/*     */           
/* 351 */           if (!bool1 && !bool2 && (i == 2 || i == 1))
/*     */           {
/*     */             
/* 354 */             i = 3;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 361 */           if (bool1 != bool2 && i == 0)
/*     */           {
/* 363 */             if (bool1) {
/* 364 */               i = 2;
/*     */             } else {
/* 366 */               i = 1;
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 372 */     return i;
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
/*     */   public int subtreeDepth() throws UnsupportedOperationException {
/* 384 */     DNSName dNSName = null;
/*     */     try {
/* 386 */       dNSName = new DNSName(this.host);
/* 387 */     } catch (IOException iOException) {
/* 388 */       throw new UnsupportedOperationException(iOException.getMessage());
/*     */     } 
/* 390 */     return dNSName.subtreeDepth();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/URIName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */