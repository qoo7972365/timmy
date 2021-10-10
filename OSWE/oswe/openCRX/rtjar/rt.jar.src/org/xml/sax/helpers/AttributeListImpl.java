/*     */ package org.xml.sax.helpers;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import org.xml.sax.AttributeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttributeListImpl
/*     */   implements AttributeList
/*     */ {
/*     */   public AttributeListImpl() {}
/*     */   
/*     */   public AttributeListImpl(AttributeList atts) {
/* 120 */     setAttributeList(atts);
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
/*     */   public void setAttributeList(AttributeList atts) {
/* 140 */     int count = atts.getLength();
/*     */     
/* 142 */     clear();
/*     */     
/* 144 */     for (int i = 0; i < count; i++) {
/* 145 */       addAttribute(atts.getName(i), atts.getType(i), atts.getValue(i));
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
/*     */   
/*     */   public void addAttribute(String name, String type, String value) {
/* 165 */     this.names.addElement(name);
/* 166 */     this.types.addElement(type);
/* 167 */     this.values.addElement(value);
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
/*     */   public void removeAttribute(String name) {
/* 187 */     int i = this.names.indexOf(name);
/*     */     
/* 189 */     if (i >= 0) {
/* 190 */       this.names.removeElementAt(i);
/* 191 */       this.types.removeElementAt(i);
/* 192 */       this.values.removeElementAt(i);
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
/*     */   public void clear() {
/* 209 */     this.names.removeAllElements();
/* 210 */     this.types.removeAllElements();
/* 211 */     this.values.removeAllElements();
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
/*     */   public int getLength() {
/* 229 */     return this.names.size();
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
/*     */   public String getName(int i) {
/* 243 */     if (i < 0) {
/* 244 */       return null;
/*     */     }
/*     */     try {
/* 247 */       return this.names.elementAt(i);
/* 248 */     } catch (ArrayIndexOutOfBoundsException e) {
/* 249 */       return null;
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
/*     */   public String getType(int i) {
/* 266 */     if (i < 0) {
/* 267 */       return null;
/*     */     }
/*     */     try {
/* 270 */       return this.types.elementAt(i);
/* 271 */     } catch (ArrayIndexOutOfBoundsException e) {
/* 272 */       return null;
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
/*     */   public String getValue(int i) {
/* 287 */     if (i < 0) {
/* 288 */       return null;
/*     */     }
/*     */     try {
/* 291 */       return this.values.elementAt(i);
/* 292 */     } catch (ArrayIndexOutOfBoundsException e) {
/* 293 */       return null;
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
/*     */   public String getType(String name) {
/* 309 */     return getType(this.names.indexOf(name));
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
/*     */   public String getValue(String name) {
/* 321 */     return getValue(this.names.indexOf(name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 330 */   Vector names = new Vector();
/* 331 */   Vector types = new Vector();
/* 332 */   Vector values = new Vector();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/xml/sax/helpers/AttributeListImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */