/*     */ package com.sun.org.apache.xerces.internal.dom;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.DocumentType;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.UserDataHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DocumentTypeImpl
/*     */   extends ParentNode
/*     */   implements DocumentType
/*     */ {
/*     */   static final long serialVersionUID = 7751299192316526485L;
/*     */   protected String name;
/*     */   protected NamedNodeMapImpl entities;
/*     */   protected NamedNodeMapImpl notations;
/*     */   protected NamedNodeMapImpl elements;
/*     */   protected String publicID;
/*     */   protected String systemID;
/*     */   protected String internalSubset;
/* 102 */   private int doctypeNumber = 0;
/*     */   
/* 104 */   private Map<String, ParentNode.UserDataRecord> userData = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("name", String.class), new ObjectStreamField("entities", NamedNodeMapImpl.class), new ObjectStreamField("notations", NamedNodeMapImpl.class), new ObjectStreamField("elements", NamedNodeMapImpl.class), new ObjectStreamField("publicID", String.class), new ObjectStreamField("systemID", String.class), new ObjectStreamField("internalSubset", String.class), new ObjectStreamField("doctypeNumber", int.class), new ObjectStreamField("userData", Hashtable.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentTypeImpl(CoreDocumentImpl ownerDocument, String name) {
/* 137 */     super(ownerDocument);
/*     */     
/* 139 */     this.name = name;
/*     */     
/* 141 */     this.entities = new NamedNodeMapImpl(this);
/* 142 */     this.notations = new NamedNodeMapImpl(this);
/*     */ 
/*     */     
/* 145 */     this.elements = new NamedNodeMapImpl(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentTypeImpl(CoreDocumentImpl ownerDocument, String qualifiedName, String publicID, String systemID) {
/* 153 */     this(ownerDocument, qualifiedName);
/* 154 */     this.publicID = publicID;
/* 155 */     this.systemID = systemID;
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
/*     */   public String getPublicId() {
/* 170 */     if (needsSyncData()) {
/* 171 */       synchronizeData();
/*     */     }
/* 173 */     return this.publicID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/* 182 */     if (needsSyncData()) {
/* 183 */       synchronizeData();
/*     */     }
/* 185 */     return this.systemID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInternalSubset(String internalSubset) {
/* 194 */     if (needsSyncData()) {
/* 195 */       synchronizeData();
/*     */     }
/* 197 */     this.internalSubset = internalSubset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInternalSubset() {
/* 207 */     if (needsSyncData()) {
/* 208 */       synchronizeData();
/*     */     }
/* 210 */     return this.internalSubset;
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
/*     */   public short getNodeType() {
/* 222 */     return 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNodeName() {
/* 229 */     if (needsSyncData()) {
/* 230 */       synchronizeData();
/*     */     }
/* 232 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Node cloneNode(boolean deep) {
/* 238 */     DocumentTypeImpl newnode = (DocumentTypeImpl)super.cloneNode(deep);
/*     */     
/* 240 */     newnode.entities = this.entities.cloneMap(newnode);
/* 241 */     newnode.notations = this.notations.cloneMap(newnode);
/* 242 */     newnode.elements = this.elements.cloneMap(newnode);
/*     */     
/* 244 */     return newnode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextContent() throws DOMException {
/* 253 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextContent(String textContent) throws DOMException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEqualNode(Node arg) {
/* 271 */     if (!super.isEqualNode(arg)) {
/* 272 */       return false;
/*     */     }
/*     */     
/* 275 */     if (needsSyncData()) {
/* 276 */       synchronizeData();
/*     */     }
/* 278 */     DocumentTypeImpl argDocType = (DocumentTypeImpl)arg;
/*     */ 
/*     */ 
/*     */     
/* 282 */     if ((getPublicId() == null && argDocType.getPublicId() != null) || (
/* 283 */       getPublicId() != null && argDocType.getPublicId() == null) || (
/* 284 */       getSystemId() == null && argDocType.getSystemId() != null) || (
/* 285 */       getSystemId() != null && argDocType.getSystemId() == null) || (
/* 286 */       getInternalSubset() == null && argDocType
/* 287 */       .getInternalSubset() != null) || (
/* 288 */       getInternalSubset() != null && argDocType
/* 289 */       .getInternalSubset() == null)) {
/* 290 */       return false;
/*     */     }
/*     */     
/* 293 */     if (getPublicId() != null && 
/* 294 */       !getPublicId().equals(argDocType.getPublicId())) {
/* 295 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 299 */     if (getSystemId() != null && 
/* 300 */       !getSystemId().equals(argDocType.getSystemId())) {
/* 301 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 305 */     if (getInternalSubset() != null && 
/* 306 */       !getInternalSubset().equals(argDocType.getInternalSubset())) {
/* 307 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 312 */     NamedNodeMapImpl argEntities = argDocType.entities;
/*     */     
/* 314 */     if ((this.entities == null && argEntities != null) || (this.entities != null && argEntities == null))
/*     */     {
/* 316 */       return false;
/*     */     }
/* 318 */     if (this.entities != null && argEntities != null) {
/* 319 */       if (this.entities.getLength() != argEntities.getLength()) {
/* 320 */         return false;
/*     */       }
/* 322 */       for (int index = 0; this.entities.item(index) != null; index++) {
/* 323 */         Node entNode1 = this.entities.item(index);
/*     */         
/* 325 */         Node entNode2 = argEntities.getNamedItem(entNode1.getNodeName());
/*     */         
/* 327 */         if (!((NodeImpl)entNode1).isEqualNode(entNode2)) {
/* 328 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 332 */     NamedNodeMapImpl argNotations = argDocType.notations;
/*     */     
/* 334 */     if ((this.notations == null && argNotations != null) || (this.notations != null && argNotations == null))
/*     */     {
/* 336 */       return false;
/*     */     }
/* 338 */     if (this.notations != null && argNotations != null) {
/* 339 */       if (this.notations.getLength() != argNotations.getLength()) {
/* 340 */         return false;
/*     */       }
/* 342 */       for (int index = 0; this.notations.item(index) != null; index++) {
/* 343 */         Node noteNode1 = this.notations.item(index);
/*     */         
/* 345 */         Node noteNode2 = argNotations.getNamedItem(noteNode1.getNodeName());
/*     */         
/* 347 */         if (!((NodeImpl)noteNode1).isEqualNode(noteNode2)) {
/* 348 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 352 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setOwnerDocument(CoreDocumentImpl doc) {
/* 361 */     super.setOwnerDocument(doc);
/* 362 */     this.entities.setOwnerDocument(doc);
/* 363 */     this.notations.setOwnerDocument(doc);
/* 364 */     this.elements.setOwnerDocument(doc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getNodeNumber() {
/* 373 */     if (getOwnerDocument() != null) {
/* 374 */       return super.getNodeNumber();
/*     */     }
/*     */ 
/*     */     
/* 378 */     if (this.doctypeNumber == 0) {
/*     */       
/* 380 */       CoreDOMImplementationImpl cd = (CoreDOMImplementationImpl)CoreDOMImplementationImpl.getDOMImplementation();
/* 381 */       this.doctypeNumber = cd.assignDocTypeNumber();
/*     */     } 
/* 383 */     return this.doctypeNumber;
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
/*     */   public String getName() {
/* 396 */     if (needsSyncData()) {
/* 397 */       synchronizeData();
/*     */     }
/* 399 */     return this.name;
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
/*     */   public NamedNodeMap getEntities() {
/* 426 */     if (needsSyncChildren()) {
/* 427 */       synchronizeChildren();
/*     */     }
/* 429 */     return this.entities;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamedNodeMap getNotations() {
/* 438 */     if (needsSyncChildren()) {
/* 439 */       synchronizeChildren();
/*     */     }
/* 441 */     return this.notations;
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
/*     */   public void setReadOnly(boolean readOnly, boolean deep) {
/* 455 */     if (needsSyncChildren()) {
/* 456 */       synchronizeChildren();
/*     */     }
/* 458 */     super.setReadOnly(readOnly, deep);
/*     */ 
/*     */     
/* 461 */     this.elements.setReadOnly(readOnly, true);
/* 462 */     this.entities.setReadOnly(readOnly, true);
/* 463 */     this.notations.setReadOnly(readOnly, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamedNodeMap getElements() {
/* 472 */     if (needsSyncChildren()) {
/* 473 */       synchronizeChildren();
/*     */     }
/* 475 */     return this.elements;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object setUserData(String key, Object data, UserDataHandler handler) {
/* 480 */     if (this.userData == null)
/* 481 */       this.userData = new HashMap<>(); 
/* 482 */     if (data == null) {
/* 483 */       if (this.userData != null) {
/* 484 */         ParentNode.UserDataRecord userDataRecord = this.userData.remove(key);
/* 485 */         if (userDataRecord != null) {
/* 486 */           return userDataRecord.fData;
/*     */         }
/*     */       } 
/* 489 */       return null;
/*     */     } 
/*     */     
/* 492 */     ParentNode.UserDataRecord udr = this.userData.put(key, new ParentNode.UserDataRecord(this, data, handler));
/* 493 */     if (udr != null) {
/* 494 */       return udr.fData;
/*     */     }
/*     */     
/* 497 */     return null;
/*     */   }
/*     */   
/*     */   public Object getUserData(String key) {
/* 501 */     if (this.userData == null) {
/* 502 */       return null;
/*     */     }
/* 504 */     ParentNode.UserDataRecord udr = this.userData.get(key);
/* 505 */     if (udr != null) {
/* 506 */       return udr.fData;
/*     */     }
/* 508 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Map<String, ParentNode.UserDataRecord> getUserDataRecord() {
/* 513 */     return this.userData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 522 */     Hashtable<String, ParentNode.UserDataRecord> ud = (this.userData == null) ? null : new Hashtable<>(this.userData);
/*     */ 
/*     */     
/* 525 */     ObjectOutputStream.PutField pf = out.putFields();
/* 526 */     pf.put("name", this.name);
/* 527 */     pf.put("entities", this.entities);
/* 528 */     pf.put("notations", this.notations);
/* 529 */     pf.put("elements", this.elements);
/* 530 */     pf.put("publicID", this.publicID);
/* 531 */     pf.put("systemID", this.systemID);
/* 532 */     pf.put("internalSubset", this.internalSubset);
/* 533 */     pf.put("doctypeNumber", this.doctypeNumber);
/* 534 */     pf.put("userData", ud);
/* 535 */     out.writeFields();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 542 */     ObjectInputStream.GetField gf = in.readFields();
/* 543 */     this.name = (String)gf.get("name", (Object)null);
/* 544 */     this.entities = (NamedNodeMapImpl)gf.get("entities", (Object)null);
/* 545 */     this.notations = (NamedNodeMapImpl)gf.get("notations", (Object)null);
/* 546 */     this.elements = (NamedNodeMapImpl)gf.get("elements", (Object)null);
/* 547 */     this.publicID = (String)gf.get("publicID", (Object)null);
/* 548 */     this.systemID = (String)gf.get("systemID", (Object)null);
/* 549 */     this.internalSubset = (String)gf.get("internalSubset", (Object)null);
/* 550 */     this.doctypeNumber = gf.get("doctypeNumber", 0);
/*     */ 
/*     */     
/* 553 */     Hashtable<String, ParentNode.UserDataRecord> ud = (Hashtable<String, ParentNode.UserDataRecord>)gf.get("userData", (Object)null);
/*     */ 
/*     */     
/* 556 */     if (ud != null) this.userData = new HashMap<>(ud); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/DocumentTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */