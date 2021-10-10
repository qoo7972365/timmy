/*     */ package javax.xml.crypto.dom;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.crypto.KeySelector;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.XMLCryptoContext;
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
/*     */ 
/*     */ 
/*     */ public class DOMCryptoContext
/*     */   implements XMLCryptoContext
/*     */ {
/*  50 */   private HashMap<String, String> nsMap = new HashMap<>();
/*  51 */   private HashMap<String, Element> idMap = new HashMap<>();
/*  52 */   private HashMap<Object, Object> objMap = new HashMap<>();
/*     */   private String baseURI;
/*     */   private KeySelector ks;
/*     */   private URIDereferencer dereferencer;
/*  56 */   private HashMap<String, Object> propMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String defaultPrefix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespacePrefix(String paramString1, String paramString2) {
/*  73 */     if (paramString1 == null) {
/*  74 */       throw new NullPointerException("namespaceURI cannot be null");
/*     */     }
/*  76 */     String str = this.nsMap.get(paramString1);
/*  77 */     return (str != null) ? str : paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String putNamespacePrefix(String paramString1, String paramString2) {
/*  87 */     if (paramString1 == null) {
/*  88 */       throw new NullPointerException("namespaceURI is null");
/*     */     }
/*  90 */     return this.nsMap.put(paramString1, paramString2);
/*     */   }
/*     */   
/*     */   public String getDefaultNamespacePrefix() {
/*  94 */     return this.defaultPrefix;
/*     */   }
/*     */   
/*     */   public void setDefaultNamespacePrefix(String paramString) {
/*  98 */     this.defaultPrefix = paramString;
/*     */   }
/*     */   
/*     */   public String getBaseURI() {
/* 102 */     return this.baseURI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseURI(String paramString) {
/* 109 */     if (paramString != null) {
/* 110 */       URI.create(paramString);
/*     */     }
/* 112 */     this.baseURI = paramString;
/*     */   }
/*     */   
/*     */   public URIDereferencer getURIDereferencer() {
/* 116 */     return this.dereferencer;
/*     */   }
/*     */   
/*     */   public void setURIDereferencer(URIDereferencer paramURIDereferencer) {
/* 120 */     this.dereferencer = paramURIDereferencer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String paramString) {
/* 130 */     if (paramString == null) {
/* 131 */       throw new NullPointerException("name is null");
/*     */     }
/* 133 */     return this.propMap.get(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object setProperty(String paramString, Object paramObject) {
/* 143 */     if (paramString == null) {
/* 144 */       throw new NullPointerException("name is null");
/*     */     }
/* 146 */     return this.propMap.put(paramString, paramObject);
/*     */   }
/*     */   
/*     */   public KeySelector getKeySelector() {
/* 150 */     return this.ks;
/*     */   }
/*     */   
/*     */   public void setKeySelector(KeySelector paramKeySelector) {
/* 154 */     this.ks = paramKeySelector;
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
/*     */   public Element getElementById(String paramString) {
/* 170 */     if (paramString == null) {
/* 171 */       throw new NullPointerException("idValue is null");
/*     */     }
/* 173 */     return this.idMap.get(paramString);
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
/*     */   public void setIdAttributeNS(Element paramElement, String paramString1, String paramString2) {
/* 196 */     if (paramElement == null) {
/* 197 */       throw new NullPointerException("element is null");
/*     */     }
/* 199 */     if (paramString2 == null) {
/* 200 */       throw new NullPointerException("localName is null");
/*     */     }
/* 202 */     String str = paramElement.getAttributeNS(paramString1, paramString2);
/* 203 */     if (str == null || str.length() == 0) {
/* 204 */       throw new IllegalArgumentException(paramString2 + " is not an attribute");
/*     */     }
/*     */     
/* 207 */     this.idMap.put(str, paramElement);
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
/*     */   public Iterator iterator() {
/* 224 */     return Collections.<String, Element>unmodifiableMap(this.idMap).entrySet().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Object paramObject) {
/* 232 */     return this.objMap.get(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object put(Object paramObject1, Object paramObject2) {
/* 242 */     return this.objMap.put(paramObject1, paramObject2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/crypto/dom/DOMCryptoContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */