/*     */ package com.sun.org.apache.xml.internal.security.keys.content.x509;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.RFC2253Parser;
/*     */ import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
/*     */ import java.security.cert.X509Certificate;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLX509SubjectName
/*     */   extends SignatureElementProxy
/*     */   implements XMLX509DataContent
/*     */ {
/*     */   public XMLX509SubjectName(Element paramElement, String paramString) throws XMLSecurityException {
/*  48 */     super(paramElement, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLX509SubjectName(Document paramDocument, String paramString) {
/*  58 */     super(paramDocument);
/*     */     
/*  60 */     addText(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLX509SubjectName(Document paramDocument, X509Certificate paramX509Certificate) {
/*  70 */     this(paramDocument, paramX509Certificate.getSubjectX500Principal().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSubjectName() {
/*  80 */     return RFC2253Parser.normalize(getTextFromTextChild());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  85 */     if (!(paramObject instanceof XMLX509SubjectName)) {
/*  86 */       return false;
/*     */     }
/*     */     
/*  89 */     XMLX509SubjectName xMLX509SubjectName = (XMLX509SubjectName)paramObject;
/*  90 */     String str1 = xMLX509SubjectName.getSubjectName();
/*  91 */     String str2 = getSubjectName();
/*     */     
/*  93 */     return str2.equals(str1);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  97 */     int i = 17;
/*  98 */     i = 31 * i + getSubjectName().hashCode();
/*  99 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 104 */     return "X509SubjectName";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/content/x509/XMLX509SubjectName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */