/*     */ package sun.security.pkcs10;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import sun.security.util.DerEncoder;
/*     */ import sun.security.util.DerInputStream;
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
/*     */ public class PKCS10Attributes
/*     */   implements DerEncoder
/*     */ {
/*  52 */   private Hashtable<String, PKCS10Attribute> map = new Hashtable<>(3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS10Attributes() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS10Attributes(PKCS10Attribute[] paramArrayOfPKCS10Attribute) {
/*  66 */     for (byte b = 0; b < paramArrayOfPKCS10Attribute.length; b++) {
/*  67 */       this.map.put(paramArrayOfPKCS10Attribute[b].getAttributeId().toString(), paramArrayOfPKCS10Attribute[b]);
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
/*     */   public PKCS10Attributes(DerInputStream paramDerInputStream) throws IOException {
/*  79 */     DerValue[] arrayOfDerValue = paramDerInputStream.getSet(3, true);
/*     */     
/*  81 */     if (arrayOfDerValue == null)
/*  82 */       throw new IOException("Illegal encoding of attributes"); 
/*  83 */     for (byte b = 0; b < arrayOfDerValue.length; b++) {
/*  84 */       PKCS10Attribute pKCS10Attribute = new PKCS10Attribute(arrayOfDerValue[b]);
/*  85 */       this.map.put(pKCS10Attribute.getAttributeId().toString(), pKCS10Attribute);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/*  96 */     derEncode(paramOutputStream);
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
/*     */   public void derEncode(OutputStream paramOutputStream) throws IOException {
/* 108 */     Collection<PKCS10Attribute> collection = this.map.values();
/*     */     
/* 110 */     PKCS10Attribute[] arrayOfPKCS10Attribute = collection.<PKCS10Attribute>toArray(new PKCS10Attribute[this.map.size()]);
/*     */     
/* 112 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 113 */     derOutputStream.putOrderedSetOf(DerValue.createTag(-128, true, (byte)0), (DerEncoder[])arrayOfPKCS10Attribute);
/*     */ 
/*     */     
/* 116 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(String paramString, Object paramObject) {
/* 123 */     if (paramObject instanceof PKCS10Attribute) {
/* 124 */       this.map.put(paramString, (PKCS10Attribute)paramObject);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getAttribute(String paramString) {
/* 132 */     return this.map.get(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteAttribute(String paramString) {
/* 139 */     this.map.remove(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<PKCS10Attribute> getElements() {
/* 147 */     return this.map.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PKCS10Attribute> getAttributes() {
/* 155 */     return Collections.unmodifiableCollection(this.map.values());
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
/*     */   public boolean equals(Object paramObject) {
/* 169 */     if (this == paramObject)
/* 170 */       return true; 
/* 171 */     if (!(paramObject instanceof PKCS10Attributes)) {
/* 172 */       return false;
/*     */     }
/*     */     
/* 175 */     Collection<PKCS10Attribute> collection = ((PKCS10Attributes)paramObject).getAttributes();
/*     */     
/* 177 */     PKCS10Attribute[] arrayOfPKCS10Attribute = collection.<PKCS10Attribute>toArray(new PKCS10Attribute[collection.size()]);
/* 178 */     int i = arrayOfPKCS10Attribute.length;
/* 179 */     if (i != this.map.size()) {
/* 180 */       return false;
/*     */     }
/* 182 */     String str = null;
/* 183 */     for (byte b = 0; b < i; b++) {
/* 184 */       PKCS10Attribute pKCS10Attribute2 = arrayOfPKCS10Attribute[b];
/* 185 */       str = pKCS10Attribute2.getAttributeId().toString();
/*     */       
/* 187 */       if (str == null)
/* 188 */         return false; 
/* 189 */       PKCS10Attribute pKCS10Attribute1 = this.map.get(str);
/* 190 */       if (pKCS10Attribute1 == null)
/* 191 */         return false; 
/* 192 */       if (!pKCS10Attribute1.equals(pKCS10Attribute2))
/* 193 */         return false; 
/*     */     } 
/* 195 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 204 */     return this.map.hashCode();
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
/*     */   public String toString() {
/* 216 */     return this.map.size() + "\n" + this.map.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/pkcs10/PKCS10Attributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */