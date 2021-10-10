/*      */ package com.sun.org.apache.xerces.internal.util;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*      */ import com.sun.org.apache.xerces.internal.xni.QName;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*      */ import com.sun.xml.internal.stream.XMLBufferListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLAttributesImpl
/*      */   implements XMLAttributes, XMLBufferListener
/*      */ {
/*      */   protected static final int TABLE_SIZE = 101;
/*      */   protected static final int MAX_HASH_COLLISIONS = 40;
/*      */   protected static final int MULTIPLIERS_SIZE = 32;
/*      */   protected static final int MULTIPLIERS_MASK = 31;
/*      */   protected static final int SIZE_LIMIT = 20;
/*      */   protected boolean fNamespaces = true;
/*   85 */   protected int fLargeCount = 1;
/*      */ 
/*      */   
/*      */   protected int fLength;
/*      */ 
/*      */   
/*   91 */   protected Attribute[] fAttributes = new Attribute[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Attribute[] fAttributeTableView;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int[] fAttributeTableViewChainState;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int fTableViewBuckets;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fIsTableViewConsistent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int[] fHashMultipliers;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLAttributesImpl() {
/*  128 */     this(101);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLAttributesImpl(int tableSize) {
/*  135 */     this.fTableViewBuckets = tableSize;
/*  136 */     for (int i = 0; i < this.fAttributes.length; i++) {
/*  137 */       this.fAttributes[i] = new Attribute();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNamespaces(boolean namespaces) {
/*  154 */     this.fNamespaces = namespaces;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int addAttribute(QName name, String type, String value) {
/*  190 */     return addAttribute(name, type, value, null);
/*      */   }
/*      */   
/*      */   public int addAttribute(QName name, String type, String value, XMLString valueCache) {
/*      */     int index;
/*  195 */     if (this.fLength < 20) {
/*      */ 
/*      */       
/*  198 */       index = (name.uri != null && !name.uri.equals("")) ? getIndexFast(name.uri, name.localpart) : getIndexFast(name.rawname);
/*      */ 
/*      */       
/*  201 */       index = this.fLength;
/*  202 */       if (index == -1 && this.fLength++ == this.fAttributes.length) {
/*  203 */         Attribute[] attributes = new Attribute[this.fAttributes.length + 4];
/*  204 */         System.arraycopy(this.fAttributes, 0, attributes, 0, this.fAttributes.length);
/*  205 */         for (int i = this.fAttributes.length; i < attributes.length; i++) {
/*  206 */           attributes[i] = new Attribute();
/*      */         }
/*  208 */         this.fAttributes = attributes;
/*      */       }
/*      */     
/*      */     }
/*  212 */     else if (name.uri == null || name.uri
/*  213 */       .length() == 0 || (
/*  214 */       index = getIndexFast(name.uri, name.localpart)) == -1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  224 */       if (!this.fIsTableViewConsistent || this.fLength == 20 || (this.fLength > 20 && this.fLength > this.fTableViewBuckets)) {
/*      */         
/*  226 */         prepareAndPopulateTableView();
/*  227 */         this.fIsTableViewConsistent = true;
/*      */       } 
/*      */       
/*  230 */       int bucket = getTableViewBucket(name.rawname);
/*      */ 
/*      */ 
/*      */       
/*  234 */       if (this.fAttributeTableViewChainState[bucket] != this.fLargeCount) {
/*  235 */         index = this.fLength;
/*  236 */         if (this.fLength++ == this.fAttributes.length) {
/*  237 */           Attribute[] attributes = new Attribute[this.fAttributes.length << 1];
/*  238 */           System.arraycopy(this.fAttributes, 0, attributes, 0, this.fAttributes.length);
/*  239 */           for (int i = this.fAttributes.length; i < attributes.length; i++) {
/*  240 */             attributes[i] = new Attribute();
/*      */           }
/*  242 */           this.fAttributes = attributes;
/*      */         } 
/*      */ 
/*      */         
/*  246 */         this.fAttributeTableViewChainState[bucket] = this.fLargeCount;
/*  247 */         (this.fAttributes[index]).next = null;
/*  248 */         this.fAttributeTableView[bucket] = this.fAttributes[index];
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  254 */         int collisionCount = 0;
/*  255 */         Attribute found = this.fAttributeTableView[bucket];
/*  256 */         while (found != null && 
/*  257 */           found.name.rawname != name.rawname) {
/*      */ 
/*      */           
/*  260 */           found = found.next;
/*  261 */           collisionCount++;
/*      */         } 
/*      */         
/*  264 */         if (found == null) {
/*  265 */           index = this.fLength;
/*  266 */           if (this.fLength++ == this.fAttributes.length) {
/*  267 */             Attribute[] attributes = new Attribute[this.fAttributes.length << 1];
/*  268 */             System.arraycopy(this.fAttributes, 0, attributes, 0, this.fAttributes.length);
/*  269 */             for (int i = this.fAttributes.length; i < attributes.length; i++) {
/*  270 */               attributes[i] = new Attribute();
/*      */             }
/*  272 */             this.fAttributes = attributes;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  277 */           if (collisionCount >= 40)
/*      */           {
/*      */             
/*  280 */             (this.fAttributes[index]).name.setValues(name);
/*  281 */             rebalanceTableView(this.fLength);
/*      */           }
/*      */           else
/*      */           {
/*  285 */             (this.fAttributes[index]).next = this.fAttributeTableView[bucket];
/*  286 */             this.fAttributeTableView[bucket] = this.fAttributes[index];
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  291 */           index = getIndexFast(name.rawname);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  297 */     Attribute attribute = this.fAttributes[index];
/*  298 */     attribute.name.setValues(name);
/*  299 */     attribute.type = type;
/*  300 */     attribute.value = value;
/*  301 */     attribute.xmlValue = valueCache;
/*  302 */     attribute.nonNormalizedValue = value;
/*  303 */     attribute.specified = false;
/*      */ 
/*      */     
/*  306 */     if (attribute.augs != null) {
/*  307 */       attribute.augs.removeAllItems();
/*      */     }
/*  309 */     return index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAllAttributes() {
/*  318 */     this.fLength = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAttributeAt(int attrIndex) {
/*  330 */     this.fIsTableViewConsistent = false;
/*  331 */     if (attrIndex < this.fLength - 1) {
/*  332 */       Attribute removedAttr = this.fAttributes[attrIndex];
/*  333 */       System.arraycopy(this.fAttributes, attrIndex + 1, this.fAttributes, attrIndex, this.fLength - attrIndex - 1);
/*      */ 
/*      */ 
/*      */       
/*  337 */       this.fAttributes[this.fLength - 1] = removedAttr;
/*      */     } 
/*  339 */     this.fLength--;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setName(int attrIndex, QName attrName) {
/*  349 */     (this.fAttributes[attrIndex]).name.setValues(attrName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getName(int attrIndex, QName attrName) {
/*  360 */     attrName.setValues((this.fAttributes[attrIndex]).name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setType(int attrIndex, String attrType) {
/*  377 */     (this.fAttributes[attrIndex]).type = attrType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValue(int attrIndex, String attrValue) {
/*  390 */     setValue(attrIndex, attrValue, null);
/*      */   }
/*      */   
/*      */   public void setValue(int attrIndex, String attrValue, XMLString value) {
/*  394 */     Attribute attribute = this.fAttributes[attrIndex];
/*  395 */     attribute.value = attrValue;
/*  396 */     attribute.nonNormalizedValue = attrValue;
/*  397 */     attribute.xmlValue = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNonNormalizedValue(int attrIndex, String attrValue) {
/*  408 */     if (attrValue == null) {
/*  409 */       attrValue = (this.fAttributes[attrIndex]).value;
/*      */     }
/*  411 */     (this.fAttributes[attrIndex]).nonNormalizedValue = attrValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNonNormalizedValue(int attrIndex) {
/*  422 */     String value = (this.fAttributes[attrIndex]).nonNormalizedValue;
/*  423 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSpecified(int attrIndex, boolean specified) {
/*  435 */     (this.fAttributes[attrIndex]).specified = specified;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSpecified(int attrIndex) {
/*  444 */     return (this.fAttributes[attrIndex]).specified;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLength() {
/*  460 */     return this.fLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getType(int index) {
/*  484 */     if (index < 0 || index >= this.fLength) {
/*  485 */       return null;
/*      */     }
/*  487 */     return getReportableType((this.fAttributes[index]).type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getType(String qname) {
/*  502 */     int index = getIndex(qname);
/*  503 */     return (index != -1) ? getReportableType((this.fAttributes[index]).type) : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getValue(int index) {
/*  520 */     if (index < 0 || index >= this.fLength) {
/*  521 */       return null;
/*      */     }
/*  523 */     if ((this.fAttributes[index]).value == null && (this.fAttributes[index]).xmlValue != null)
/*  524 */       (this.fAttributes[index]).value = (this.fAttributes[index]).xmlValue.toString(); 
/*  525 */     return (this.fAttributes[index]).value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getValue(String qname) {
/*  540 */     int index = getIndex(qname);
/*  541 */     if (index == -1)
/*  542 */       return null; 
/*  543 */     if ((this.fAttributes[index]).value == null)
/*  544 */       (this.fAttributes[index]).value = (this.fAttributes[index]).xmlValue.toString(); 
/*  545 */     return (this.fAttributes[index]).value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName(int index) {
/*  569 */     if (index < 0 || index >= this.fLength) {
/*  570 */       return null;
/*      */     }
/*  572 */     return (this.fAttributes[index]).name.rawname;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getIndex(String qName) {
/*  587 */     for (int i = 0; i < this.fLength; i++) {
/*  588 */       Attribute attribute = this.fAttributes[i];
/*  589 */       if (attribute.name.rawname != null && attribute.name.rawname
/*  590 */         .equals(qName)) {
/*  591 */         return i;
/*      */       }
/*      */     } 
/*  594 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getIndex(String uri, String localPart) {
/*  607 */     for (int i = 0; i < this.fLength; i++) {
/*  608 */       Attribute attribute = this.fAttributes[i];
/*  609 */       if (attribute.name.localpart != null && attribute.name.localpart
/*  610 */         .equals(localPart) && (uri == attribute.name.uri || (uri != null && attribute.name.uri != null && attribute.name.uri
/*      */         
/*  612 */         .equals(uri)))) {
/*  613 */         return i;
/*      */       }
/*      */     } 
/*  616 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getIndexByLocalName(String localPart) {
/*  628 */     for (int i = 0; i < this.fLength; i++) {
/*  629 */       Attribute attribute = this.fAttributes[i];
/*  630 */       if (attribute.name.localpart != null && attribute.name.localpart
/*  631 */         .equals(localPart)) {
/*  632 */         return i;
/*      */       }
/*      */     } 
/*  635 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLocalName(int index) {
/*  648 */     if (!this.fNamespaces) {
/*  649 */       return "";
/*      */     }
/*  651 */     if (index < 0 || index >= this.fLength) {
/*  652 */       return null;
/*      */     }
/*  654 */     return (this.fAttributes[index]).name.localpart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getQName(int index) {
/*  667 */     if (index < 0 || index >= this.fLength) {
/*  668 */       return null;
/*      */     }
/*  670 */     String rawname = (this.fAttributes[index]).name.rawname;
/*  671 */     return (rawname != null) ? rawname : "";
/*      */   }
/*      */   
/*      */   public QName getQualifiedName(int index) {
/*  675 */     if (index < 0 || index >= this.fLength) {
/*  676 */       return null;
/*      */     }
/*  678 */     return (this.fAttributes[index]).name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getType(String uri, String localName) {
/*  695 */     if (!this.fNamespaces) {
/*  696 */       return null;
/*      */     }
/*  698 */     int index = getIndex(uri, localName);
/*  699 */     return (index != -1) ? getType(index) : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getIndexFast(String qName) {
/*  714 */     for (int i = 0; i < this.fLength; i++) {
/*  715 */       Attribute attribute = this.fAttributes[i];
/*  716 */       if (attribute.name.rawname == qName) {
/*  717 */         return i;
/*      */       }
/*      */     } 
/*  720 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAttributeNS(QName name, String type, String value) {
/*  751 */     int index = this.fLength;
/*  752 */     if (this.fLength++ == this.fAttributes.length) {
/*      */       Attribute[] attributes;
/*  754 */       if (this.fLength < 20) {
/*  755 */         attributes = new Attribute[this.fAttributes.length + 4];
/*      */       } else {
/*      */         
/*  758 */         attributes = new Attribute[this.fAttributes.length << 1];
/*      */       } 
/*  760 */       System.arraycopy(this.fAttributes, 0, attributes, 0, this.fAttributes.length);
/*  761 */       for (int i = this.fAttributes.length; i < attributes.length; i++) {
/*  762 */         attributes[i] = new Attribute();
/*      */       }
/*  764 */       this.fAttributes = attributes;
/*      */     } 
/*      */ 
/*      */     
/*  768 */     Attribute attribute = this.fAttributes[index];
/*  769 */     attribute.name.setValues(name);
/*  770 */     attribute.type = type;
/*  771 */     attribute.value = value;
/*  772 */     attribute.nonNormalizedValue = value;
/*  773 */     attribute.specified = false;
/*      */ 
/*      */     
/*  776 */     attribute.augs.removeAllItems();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QName checkDuplicatesNS() {
/*  793 */     int length = this.fLength;
/*  794 */     if (length <= 20) {
/*  795 */       Attribute[] attributes = this.fAttributes;
/*  796 */       for (int i = 0; i < length - 1; i++) {
/*  797 */         Attribute att1 = attributes[i];
/*  798 */         for (int j = i + 1; j < length; j++) {
/*  799 */           Attribute att2 = attributes[j];
/*  800 */           if (att1.name.localpart == att2.name.localpart && att1.name.uri == att2.name.uri)
/*      */           {
/*  802 */             return att2.name;
/*      */           }
/*      */         } 
/*      */       } 
/*  806 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  810 */     return checkManyDuplicatesNS();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private QName checkManyDuplicatesNS() {
/*  817 */     this.fIsTableViewConsistent = false;
/*      */     
/*  819 */     prepareTableView();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  824 */     int length = this.fLength;
/*  825 */     Attribute[] attributes = this.fAttributes;
/*  826 */     Attribute[] attributeTableView = this.fAttributeTableView;
/*  827 */     int[] attributeTableViewChainState = this.fAttributeTableViewChainState;
/*  828 */     int largeCount = this.fLargeCount;
/*      */     
/*  830 */     for (int i = 0; i < length; i++) {
/*  831 */       Attribute attr = attributes[i];
/*  832 */       int bucket = getTableViewBucket(attr.name.localpart, attr.name.uri);
/*      */ 
/*      */ 
/*      */       
/*  836 */       if (attributeTableViewChainState[bucket] != largeCount) {
/*  837 */         attributeTableViewChainState[bucket] = largeCount;
/*  838 */         attr.next = null;
/*  839 */         attributeTableView[bucket] = attr;
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  845 */         int collisionCount = 0;
/*  846 */         Attribute found = attributeTableView[bucket];
/*  847 */         while (found != null) {
/*  848 */           if (found.name.localpart == attr.name.localpart && found.name.uri == attr.name.uri)
/*      */           {
/*  850 */             return attr.name;
/*      */           }
/*  852 */           found = found.next;
/*  853 */           collisionCount++;
/*      */         } 
/*      */ 
/*      */         
/*  857 */         if (collisionCount >= 40) {
/*      */           
/*  859 */           rebalanceTableViewNS(i + 1);
/*  860 */           largeCount = this.fLargeCount;
/*      */         }
/*      */         else {
/*      */           
/*  864 */           attr.next = attributeTableView[bucket];
/*  865 */           attributeTableView[bucket] = attr;
/*      */         } 
/*      */       } 
/*      */     } 
/*  869 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getIndexFast(String uri, String localPart) {
/*  887 */     for (int i = 0; i < this.fLength; i++) {
/*  888 */       Attribute attribute = this.fAttributes[i];
/*  889 */       if (attribute.name.localpart == localPart && attribute.name.uri == uri)
/*      */       {
/*  891 */         return i;
/*      */       }
/*      */     } 
/*  894 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getReportableType(String type) {
/*  905 */     if (type.charAt(0) == '(') {
/*  906 */       return "NMTOKEN";
/*      */     }
/*  908 */     return type;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getTableViewBucket(String qname) {
/*  920 */     return (hash(qname) & Integer.MAX_VALUE) % this.fTableViewBuckets;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getTableViewBucket(String localpart, String uri) {
/*  933 */     if (uri == null) {
/*  934 */       return (hash(localpart) & Integer.MAX_VALUE) % this.fTableViewBuckets;
/*      */     }
/*      */     
/*  937 */     return (hash(localpart, uri) & Integer.MAX_VALUE) % this.fTableViewBuckets;
/*      */   }
/*      */ 
/*      */   
/*      */   private int hash(String localpart) {
/*  942 */     if (this.fHashMultipliers == null) {
/*  943 */       return localpart.hashCode();
/*      */     }
/*  945 */     return hash0(localpart);
/*      */   }
/*      */   
/*      */   private int hash(String localpart, String uri) {
/*  949 */     if (this.fHashMultipliers == null) {
/*  950 */       return localpart.hashCode() + uri.hashCode() * 31;
/*      */     }
/*  952 */     return hash0(localpart) + hash0(uri) * this.fHashMultipliers[32];
/*      */   }
/*      */   
/*      */   private int hash0(String symbol) {
/*  956 */     int code = 0;
/*  957 */     int length = symbol.length();
/*  958 */     int[] multipliers = this.fHashMultipliers;
/*  959 */     for (int i = 0; i < length; i++) {
/*  960 */       code = code * multipliers[i & 0x1F] + symbol.charAt(i);
/*      */     }
/*  962 */     return code;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void cleanTableView() {
/*  969 */     if (++this.fLargeCount < 0) {
/*      */       
/*  971 */       if (this.fAttributeTableViewChainState != null) {
/*  972 */         for (int i = this.fTableViewBuckets - 1; i >= 0; i--) {
/*  973 */           this.fAttributeTableViewChainState[i] = 0;
/*      */         }
/*      */       }
/*  976 */       this.fLargeCount = 1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void growTableView() {
/*  984 */     int length = this.fLength;
/*  985 */     int tableViewBuckets = this.fTableViewBuckets;
/*      */     do {
/*  987 */       tableViewBuckets = (tableViewBuckets << 1) + 1;
/*  988 */       if (tableViewBuckets < 0) {
/*  989 */         tableViewBuckets = Integer.MAX_VALUE;
/*      */         
/*      */         break;
/*      */       } 
/*  993 */     } while (length > tableViewBuckets);
/*  994 */     this.fTableViewBuckets = tableViewBuckets;
/*  995 */     this.fAttributeTableView = null;
/*  996 */     this.fLargeCount = 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void prepareTableView() {
/* 1003 */     if (this.fLength > this.fTableViewBuckets) {
/* 1004 */       growTableView();
/*      */     }
/* 1006 */     if (this.fAttributeTableView == null) {
/* 1007 */       this.fAttributeTableView = new Attribute[this.fTableViewBuckets];
/* 1008 */       this.fAttributeTableViewChainState = new int[this.fTableViewBuckets];
/*      */     } else {
/*      */       
/* 1011 */       cleanTableView();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void prepareAndPopulateTableView() {
/* 1021 */     prepareAndPopulateTableView(this.fLength);
/*      */   }
/*      */   
/*      */   private void prepareAndPopulateTableView(int count) {
/* 1025 */     prepareTableView();
/*      */ 
/*      */ 
/*      */     
/* 1029 */     for (int i = 0; i < count; i++) {
/* 1030 */       Attribute attr = this.fAttributes[i];
/* 1031 */       int bucket = getTableViewBucket(attr.name.rawname);
/* 1032 */       if (this.fAttributeTableViewChainState[bucket] != this.fLargeCount) {
/* 1033 */         this.fAttributeTableViewChainState[bucket] = this.fLargeCount;
/* 1034 */         attr.next = null;
/* 1035 */         this.fAttributeTableView[bucket] = attr;
/*      */       }
/*      */       else {
/*      */         
/* 1039 */         attr.next = this.fAttributeTableView[bucket];
/* 1040 */         this.fAttributeTableView[bucket] = attr;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPrefix(int index) {
/* 1052 */     if (index < 0 || index >= this.fLength) {
/* 1053 */       return null;
/*      */     }
/* 1055 */     String prefix = (this.fAttributes[index]).name.prefix;
/*      */     
/* 1057 */     return (prefix != null) ? prefix : "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getURI(int index) {
/* 1068 */     if (index < 0 || index >= this.fLength) {
/* 1069 */       return null;
/*      */     }
/* 1071 */     String uri = (this.fAttributes[index]).name.uri;
/* 1072 */     return uri;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getValue(String uri, String localName) {
/* 1090 */     int index = getIndex(uri, localName);
/* 1091 */     return (index != -1) ? getValue(index) : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Augmentations getAugmentations(String uri, String localName) {
/* 1102 */     int index = getIndex(uri, localName);
/* 1103 */     return (index != -1) ? (this.fAttributes[index]).augs : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Augmentations getAugmentations(String qName) {
/* 1116 */     int index = getIndex(qName);
/* 1117 */     return (index != -1) ? (this.fAttributes[index]).augs : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Augmentations getAugmentations(int attributeIndex) {
/* 1129 */     if (attributeIndex < 0 || attributeIndex >= this.fLength) {
/* 1130 */       return null;
/*      */     }
/* 1132 */     return (this.fAttributes[attributeIndex]).augs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAugmentations(int attrIndex, Augmentations augs) {
/* 1142 */     (this.fAttributes[attrIndex]).augs = augs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setURI(int attrIndex, String uri) {
/* 1152 */     (this.fAttributes[attrIndex]).name.uri = uri;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSchemaId(int attrIndex, boolean schemaId) {
/* 1157 */     (this.fAttributes[attrIndex]).schemaId = schemaId;
/*      */   }
/*      */   
/*      */   public boolean getSchemaId(int index) {
/* 1161 */     if (index < 0 || index >= this.fLength) {
/* 1162 */       return false;
/*      */     }
/* 1164 */     return (this.fAttributes[index]).schemaId;
/*      */   }
/*      */   
/*      */   public boolean getSchemaId(String qname) {
/* 1168 */     int index = getIndex(qname);
/* 1169 */     return (index != -1) ? (this.fAttributes[index]).schemaId : false;
/*      */   }
/*      */   
/*      */   public boolean getSchemaId(String uri, String localName) {
/* 1173 */     if (!this.fNamespaces) {
/* 1174 */       return false;
/*      */     }
/* 1176 */     int index = getIndex(uri, localName);
/* 1177 */     return (index != -1) ? (this.fAttributes[index]).schemaId : false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void refresh() {
/* 1186 */     if (this.fLength > 0) {
/* 1187 */       for (int i = 0; i < this.fLength; i++) {
/* 1188 */         getValue(i);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void refresh(int pos) {}
/*      */   
/*      */   private void prepareAndPopulateTableViewNS(int count) {
/* 1196 */     prepareTableView();
/*      */ 
/*      */ 
/*      */     
/* 1200 */     for (int i = 0; i < count; i++) {
/* 1201 */       Attribute attr = this.fAttributes[i];
/* 1202 */       int bucket = getTableViewBucket(attr.name.localpart, attr.name.uri);
/* 1203 */       if (this.fAttributeTableViewChainState[bucket] != this.fLargeCount) {
/* 1204 */         this.fAttributeTableViewChainState[bucket] = this.fLargeCount;
/* 1205 */         attr.next = null;
/* 1206 */         this.fAttributeTableView[bucket] = attr;
/*      */       }
/*      */       else {
/*      */         
/* 1210 */         attr.next = this.fAttributeTableView[bucket];
/* 1211 */         this.fAttributeTableView[bucket] = attr;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void rebalanceTableView(int count) {
/* 1223 */     if (this.fHashMultipliers == null) {
/* 1224 */       this.fHashMultipliers = new int[33];
/*      */     }
/* 1226 */     PrimeNumberSequenceGenerator.generateSequence(this.fHashMultipliers);
/* 1227 */     prepareAndPopulateTableView(count);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void rebalanceTableViewNS(int count) {
/* 1237 */     if (this.fHashMultipliers == null) {
/* 1238 */       this.fHashMultipliers = new int[33];
/*      */     }
/* 1240 */     PrimeNumberSequenceGenerator.generateSequence(this.fHashMultipliers);
/* 1241 */     prepareAndPopulateTableViewNS(count);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class Attribute
/*      */   {
/* 1262 */     public QName name = new QName();
/*      */ 
/*      */ 
/*      */     
/*      */     public String type;
/*      */ 
/*      */ 
/*      */     
/*      */     public String value;
/*      */ 
/*      */ 
/*      */     
/*      */     public XMLString xmlValue;
/*      */ 
/*      */ 
/*      */     
/*      */     public String nonNormalizedValue;
/*      */ 
/*      */     
/*      */     public boolean specified;
/*      */ 
/*      */     
/*      */     public boolean schemaId;
/*      */ 
/*      */     
/* 1287 */     public Augmentations augs = new AugmentationsImpl();
/*      */     public Attribute next;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/XMLAttributesImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */