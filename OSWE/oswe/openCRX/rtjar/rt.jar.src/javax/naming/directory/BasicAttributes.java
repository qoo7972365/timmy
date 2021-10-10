/*     */ package javax.naming.directory;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicAttributes
/*     */   implements Attributes
/*     */ {
/*     */   private boolean ignoreCase = false;
/*  82 */   transient Hashtable<String, Attribute> attrs = new Hashtable<>(11);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 4980164073184639448L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicAttributes() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicAttributes(boolean paramBoolean) {
/* 102 */     this.ignoreCase = paramBoolean;
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
/*     */   public BasicAttributes(String paramString, Object paramObject) {
/* 116 */     this();
/* 117 */     put(new BasicAttribute(paramString, paramObject));
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
/*     */   public BasicAttributes(String paramString, Object paramObject, boolean paramBoolean) {
/* 138 */     this(paramBoolean);
/* 139 */     put(new BasicAttribute(paramString, paramObject));
/*     */   }
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     BasicAttributes basicAttributes;
/*     */     try {
/* 146 */       basicAttributes = (BasicAttributes)super.clone();
/* 147 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 148 */       basicAttributes = new BasicAttributes(this.ignoreCase);
/*     */     } 
/* 150 */     basicAttributes.attrs = (Hashtable<String, Attribute>)this.attrs.clone();
/* 151 */     return basicAttributes;
/*     */   }
/*     */   
/*     */   public boolean isCaseIgnored() {
/* 155 */     return this.ignoreCase;
/*     */   }
/*     */   
/*     */   public int size() {
/* 159 */     return this.attrs.size();
/*     */   }
/*     */   
/*     */   public Attribute get(String paramString) {
/* 163 */     return this.attrs.get(this.ignoreCase ? paramString
/* 164 */         .toLowerCase(Locale.ENGLISH) : paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingEnumeration<Attribute> getAll() {
/* 169 */     return new AttrEnumImpl();
/*     */   }
/*     */   
/*     */   public NamingEnumeration<String> getIDs() {
/* 173 */     return new IDEnumImpl();
/*     */   }
/*     */   
/*     */   public Attribute put(String paramString, Object paramObject) {
/* 177 */     return put(new BasicAttribute(paramString, paramObject));
/*     */   }
/*     */   
/*     */   public Attribute put(Attribute paramAttribute) {
/* 181 */     String str = paramAttribute.getID();
/* 182 */     if (this.ignoreCase) {
/* 183 */       str = str.toLowerCase(Locale.ENGLISH);
/*     */     }
/* 185 */     return this.attrs.put(str, paramAttribute);
/*     */   }
/*     */   
/*     */   public Attribute remove(String paramString) {
/* 189 */     String str = this.ignoreCase ? paramString.toLowerCase(Locale.ENGLISH) : paramString;
/* 190 */     return this.attrs.remove(str);
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
/* 202 */     if (this.attrs.size() == 0) {
/* 203 */       return "No attributes";
/*     */     }
/* 205 */     return this.attrs.toString();
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
/*     */   public boolean equals(Object paramObject) {
/* 228 */     if (paramObject != null && paramObject instanceof Attributes) {
/* 229 */       Attributes attributes = (Attributes)paramObject;
/*     */ 
/*     */       
/* 232 */       if (this.ignoreCase != attributes.isCaseIgnored()) {
/* 233 */         return false;
/*     */       }
/*     */       
/* 236 */       if (size() == attributes.size()) {
/*     */         
/*     */         try {
/* 239 */           NamingEnumeration<? extends Attribute> namingEnumeration = attributes.getAll();
/* 240 */           while (namingEnumeration.hasMore()) {
/* 241 */             Attribute attribute1 = namingEnumeration.next();
/* 242 */             Attribute attribute2 = get(attribute1.getID());
/* 243 */             if (!attribute1.equals(attribute2)) {
/* 244 */               return false;
/*     */             }
/*     */           } 
/* 247 */         } catch (NamingException namingException) {
/* 248 */           return false;
/*     */         } 
/* 250 */         return true;
/*     */       } 
/*     */     } 
/* 253 */     return false;
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
/*     */   public int hashCode() {
/* 271 */     int i = this.ignoreCase ? 1 : 0;
/*     */     try {
/* 273 */       NamingEnumeration<Attribute> namingEnumeration = getAll();
/* 274 */       while (namingEnumeration.hasMore()) {
/* 275 */         i += namingEnumeration.next().hashCode();
/*     */       }
/* 277 */     } catch (NamingException namingException) {}
/* 278 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 289 */     paramObjectOutputStream.defaultWriteObject();
/* 290 */     paramObjectOutputStream.writeInt(this.attrs.size());
/* 291 */     Enumeration<Attribute> enumeration = this.attrs.elements();
/* 292 */     while (enumeration.hasMoreElements()) {
/* 293 */       paramObjectOutputStream.writeObject(enumeration.nextElement());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 302 */     paramObjectInputStream.defaultReadObject();
/* 303 */     int i = paramObjectInputStream.readInt();
/* 304 */     this
/* 305 */       .attrs = (i >= 1) ? new Hashtable<>(1 + (int)(Math.min(768, i) / 0.75F)) : new Hashtable<>(2);
/*     */     
/* 307 */     while (--i >= 0) {
/* 308 */       put((Attribute)paramObjectInputStream.readObject());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class AttrEnumImpl
/*     */     implements NamingEnumeration<Attribute>
/*     */   {
/* 318 */     Enumeration<Attribute> elements = BasicAttributes.this.attrs.elements();
/*     */ 
/*     */     
/*     */     public boolean hasMoreElements() {
/* 322 */       return this.elements.hasMoreElements();
/*     */     }
/*     */     
/*     */     public Attribute nextElement() {
/* 326 */       return this.elements.nextElement();
/*     */     }
/*     */     
/*     */     public boolean hasMore() throws NamingException {
/* 330 */       return hasMoreElements();
/*     */     }
/*     */     
/*     */     public Attribute next() throws NamingException {
/* 334 */       return nextElement();
/*     */     }
/*     */     
/*     */     public void close() throws NamingException {
/* 338 */       this.elements = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class IDEnumImpl
/*     */     implements NamingEnumeration<String>
/*     */   {
/* 349 */     Enumeration<Attribute> elements = BasicAttributes.this.attrs.elements();
/*     */ 
/*     */     
/*     */     public boolean hasMoreElements() {
/* 353 */       return this.elements.hasMoreElements();
/*     */     }
/*     */     
/*     */     public String nextElement() {
/* 357 */       Attribute attribute = this.elements.nextElement();
/* 358 */       return attribute.getID();
/*     */     }
/*     */     
/*     */     public boolean hasMore() throws NamingException {
/* 362 */       return hasMoreElements();
/*     */     }
/*     */     
/*     */     public String next() throws NamingException {
/* 366 */       return nextElement();
/*     */     }
/*     */     
/*     */     public void close() throws NamingException {
/* 370 */       this.elements = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/directory/BasicAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */