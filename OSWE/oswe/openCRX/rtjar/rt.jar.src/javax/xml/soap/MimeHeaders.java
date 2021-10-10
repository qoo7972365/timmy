/*     */ package javax.xml.soap;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MimeHeaders
/*     */ {
/*  51 */   private Vector headers = new Vector();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getHeader(String name) {
/*  64 */     Vector<String> values = new Vector();
/*     */     
/*  66 */     for (int i = 0; i < this.headers.size(); i++) {
/*  67 */       MimeHeader hdr = this.headers.elementAt(i);
/*  68 */       if (hdr.getName().equalsIgnoreCase(name) && hdr
/*  69 */         .getValue() != null) {
/*  70 */         values.addElement(hdr.getValue());
/*     */       }
/*     */     } 
/*  73 */     if (values.size() == 0) {
/*  74 */       return null;
/*     */     }
/*  76 */     String[] r = new String[values.size()];
/*  77 */     values.copyInto((Object[])r);
/*  78 */     return r;
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
/*     */   public void setHeader(String name, String value) {
/*  99 */     boolean found = false;
/*     */     
/* 101 */     if (name == null || name.equals("")) {
/* 102 */       throw new IllegalArgumentException("Illegal MimeHeader name");
/*     */     }
/* 104 */     for (int i = 0; i < this.headers.size(); i++) {
/* 105 */       MimeHeader hdr = this.headers.elementAt(i);
/* 106 */       if (hdr.getName().equalsIgnoreCase(name)) {
/* 107 */         if (!found) {
/* 108 */           this.headers.setElementAt(new MimeHeader(hdr.getName(), value), i);
/*     */           
/* 110 */           found = true;
/*     */         } else {
/*     */           
/* 113 */           this.headers.removeElementAt(i--);
/*     */         } 
/*     */       }
/*     */     } 
/* 117 */     if (!found) {
/* 118 */       addHeader(name, value);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addHeader(String name, String value) {
/* 137 */     if (name == null || name.equals("")) {
/* 138 */       throw new IllegalArgumentException("Illegal MimeHeader name");
/*     */     }
/* 140 */     int pos = this.headers.size();
/*     */     
/* 142 */     for (int i = pos - 1; i >= 0; i--) {
/* 143 */       MimeHeader hdr = this.headers.elementAt(i);
/* 144 */       if (hdr.getName().equalsIgnoreCase(name)) {
/* 145 */         this.headers.insertElementAt(new MimeHeader(name, value), i + 1);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 150 */     this.headers.addElement(new MimeHeader(name, value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeHeader(String name) {
/* 161 */     for (int i = 0; i < this.headers.size(); i++) {
/* 162 */       MimeHeader hdr = this.headers.elementAt(i);
/* 163 */       if (hdr.getName().equalsIgnoreCase(name)) {
/* 164 */         this.headers.removeElementAt(i--);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllHeaders() {
/* 172 */     this.headers.removeAllElements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getAllHeaders() {
/* 183 */     return this.headers.iterator();
/*     */   }
/*     */   
/*     */   class MatchingIterator implements Iterator {
/*     */     private boolean match;
/*     */     private Iterator iterator;
/*     */     private String[] names;
/*     */     private Object nextHeader;
/*     */     
/*     */     MatchingIterator(String[] names, boolean match) {
/* 193 */       this.match = match;
/* 194 */       this.names = names;
/* 195 */       this.iterator = MimeHeaders.this.headers.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     private Object nextMatch() {
/* 200 */       label21: while (this.iterator.hasNext()) {
/* 201 */         MimeHeader hdr = this.iterator.next();
/*     */         
/* 203 */         if (this.names == null) {
/* 204 */           return this.match ? null : hdr;
/*     */         }
/* 206 */         for (int i = 0; i < this.names.length; i++) {
/* 207 */           if (hdr.getName().equalsIgnoreCase(this.names[i])) {
/* 208 */             if (this.match)
/* 209 */               return hdr;  continue label21;
/*     */           } 
/*     */         } 
/* 212 */         if (!this.match)
/* 213 */           return hdr; 
/*     */       } 
/* 215 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 220 */       if (this.nextHeader == null)
/* 221 */         this.nextHeader = nextMatch(); 
/* 222 */       return (this.nextHeader != null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object next() {
/* 228 */       if (this.nextHeader != null) {
/* 229 */         Object ret = this.nextHeader;
/* 230 */         this.nextHeader = null;
/* 231 */         return ret;
/*     */       } 
/* 233 */       if (hasNext())
/* 234 */         return this.nextHeader; 
/* 235 */       return null;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 239 */       this.iterator.remove();
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
/*     */   public Iterator getMatchingHeaders(String[] names) {
/* 254 */     return new MatchingIterator(names, true);
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
/*     */   public Iterator getNonMatchingHeaders(String[] names) {
/* 267 */     return new MatchingIterator(names, false);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/soap/MimeHeaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */