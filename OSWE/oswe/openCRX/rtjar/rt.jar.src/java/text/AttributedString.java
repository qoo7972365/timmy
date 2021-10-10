/*      */ package java.text;
/*      */ 
/*      */ import java.util.AbstractMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
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
/*      */ public class AttributedString
/*      */ {
/*      */   private static final int ARRAY_SIZE_INCREMENT = 10;
/*      */   String text;
/*      */   int runArraySize;
/*      */   int runCount;
/*      */   int[] runStarts;
/*      */   Vector<AttributedCharacterIterator.Attribute>[] runAttributes;
/*      */   Vector<Object>[] runAttributeValues;
/*      */   
/*      */   AttributedString(AttributedCharacterIterator[] paramArrayOfAttributedCharacterIterator) {
/*   76 */     if (paramArrayOfAttributedCharacterIterator == null) {
/*   77 */       throw new NullPointerException("Iterators must not be null");
/*      */     }
/*   79 */     if (paramArrayOfAttributedCharacterIterator.length == 0) {
/*   80 */       this.text = "";
/*      */     }
/*      */     else {
/*      */       
/*   84 */       StringBuffer stringBuffer = new StringBuffer(); int i;
/*   85 */       for (i = 0; i < paramArrayOfAttributedCharacterIterator.length; i++) {
/*   86 */         appendContents(stringBuffer, paramArrayOfAttributedCharacterIterator[i]);
/*      */       }
/*      */       
/*   89 */       this.text = stringBuffer.toString();
/*      */       
/*   91 */       if (this.text.length() > 0) {
/*      */ 
/*      */         
/*   94 */         i = 0;
/*   95 */         Map<AttributedCharacterIterator.Attribute, Object> map = null;
/*      */         
/*   97 */         for (byte b = 0; b < paramArrayOfAttributedCharacterIterator.length; b++) {
/*   98 */           AttributedCharacterIterator attributedCharacterIterator = paramArrayOfAttributedCharacterIterator[b];
/*   99 */           int j = attributedCharacterIterator.getBeginIndex();
/*  100 */           int k = attributedCharacterIterator.getEndIndex();
/*  101 */           int m = j;
/*      */           
/*  103 */           while (m < k) {
/*  104 */             attributedCharacterIterator.setIndex(m);
/*      */             
/*  106 */             Map<AttributedCharacterIterator.Attribute, Object> map1 = attributedCharacterIterator.getAttributes();
/*      */             
/*  108 */             if (mapsDiffer(map, map1)) {
/*  109 */               setAttributes(map1, m - j + i);
/*      */             }
/*  111 */             map = map1;
/*  112 */             m = attributedCharacterIterator.getRunLimit();
/*      */           } 
/*  114 */           i += k - j;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributedString(String paramString) {
/*  126 */     if (paramString == null) {
/*  127 */       throw new NullPointerException();
/*      */     }
/*  129 */     this.text = paramString;
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
/*      */   public AttributedString(String paramString, Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap) {
/*  145 */     if (paramString == null || paramMap == null) {
/*  146 */       throw new NullPointerException();
/*      */     }
/*  148 */     this.text = paramString;
/*      */     
/*  150 */     if (paramString.length() == 0) {
/*  151 */       if (paramMap.isEmpty())
/*      */         return; 
/*  153 */       throw new IllegalArgumentException("Can't add attribute to 0-length text");
/*      */     } 
/*      */     
/*  156 */     int i = paramMap.size();
/*  157 */     if (i > 0) {
/*  158 */       createRunAttributeDataVectors();
/*  159 */       Vector<AttributedCharacterIterator.Attribute> vector = new Vector(i);
/*  160 */       Vector<Object> vector1 = new Vector(i);
/*  161 */       this.runAttributes[0] = vector;
/*  162 */       this.runAttributeValues[0] = vector1;
/*      */       
/*  164 */       Iterator<Map.Entry> iterator = paramMap.entrySet().iterator();
/*  165 */       while (iterator.hasNext()) {
/*  166 */         Map.Entry entry = iterator.next();
/*  167 */         vector.addElement((AttributedCharacterIterator.Attribute)entry.getKey());
/*  168 */         vector1.addElement(entry.getValue());
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
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributedString(AttributedCharacterIterator paramAttributedCharacterIterator) {
/*  183 */     this(paramAttributedCharacterIterator, paramAttributedCharacterIterator.getBeginIndex(), paramAttributedCharacterIterator.getEndIndex(), null);
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
/*      */   public AttributedString(AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt1, int paramInt2) {
/*  206 */     this(paramAttributedCharacterIterator, paramInt1, paramInt2, null);
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
/*      */   public AttributedString(AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt1, int paramInt2, AttributedCharacterIterator.Attribute[] paramArrayOfAttribute) {
/*  235 */     if (paramAttributedCharacterIterator == null) {
/*  236 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*      */     
/*  240 */     int i = paramAttributedCharacterIterator.getBeginIndex();
/*  241 */     int j = paramAttributedCharacterIterator.getEndIndex();
/*  242 */     if (paramInt1 < i || paramInt2 > j || paramInt1 > paramInt2) {
/*  243 */       throw new IllegalArgumentException("Invalid substring range");
/*      */     }
/*      */     
/*  246 */     StringBuffer stringBuffer = new StringBuffer();
/*  247 */     paramAttributedCharacterIterator.setIndex(paramInt1); char c;
/*  248 */     for (c = paramAttributedCharacterIterator.current(); paramAttributedCharacterIterator.getIndex() < paramInt2; c = paramAttributedCharacterIterator.next())
/*  249 */       stringBuffer.append(c); 
/*  250 */     this.text = stringBuffer.toString();
/*      */     
/*  252 */     if (paramInt1 == paramInt2) {
/*      */       return;
/*      */     }
/*      */     
/*  256 */     HashSet<AttributedCharacterIterator.Attribute> hashSet = new HashSet();
/*  257 */     if (paramArrayOfAttribute == null) {
/*  258 */       hashSet.addAll(paramAttributedCharacterIterator.getAllAttributeKeys());
/*      */     } else {
/*  260 */       for (byte b = 0; b < paramArrayOfAttribute.length; b++)
/*  261 */         hashSet.add(paramArrayOfAttribute[b]); 
/*  262 */       hashSet.retainAll(paramAttributedCharacterIterator.getAllAttributeKeys());
/*      */     } 
/*  264 */     if (hashSet.isEmpty()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  270 */     Iterator<AttributedCharacterIterator.Attribute> iterator = hashSet.iterator();
/*  271 */     while (iterator.hasNext()) {
/*  272 */       AttributedCharacterIterator.Attribute attribute = iterator.next();
/*  273 */       paramAttributedCharacterIterator.setIndex(i);
/*  274 */       while (paramAttributedCharacterIterator.getIndex() < paramInt2) {
/*  275 */         int k = paramAttributedCharacterIterator.getRunStart(attribute);
/*  276 */         int m = paramAttributedCharacterIterator.getRunLimit(attribute);
/*  277 */         Object object = paramAttributedCharacterIterator.getAttribute(attribute);
/*      */         
/*  279 */         if (object != null) {
/*  280 */           if (object instanceof Annotation) {
/*  281 */             if (k >= paramInt1 && m <= paramInt2) {
/*  282 */               addAttribute(attribute, object, k - paramInt1, m - paramInt1);
/*      */             }
/*  284 */             else if (m > paramInt2) {
/*      */               
/*      */               break;
/*      */             } 
/*      */           } else {
/*      */             
/*  290 */             if (k >= paramInt2)
/*      */               break; 
/*  292 */             if (m > paramInt1) {
/*      */               
/*  294 */               if (k < paramInt1)
/*  295 */                 k = paramInt1; 
/*  296 */               if (m > paramInt2)
/*  297 */                 m = paramInt2; 
/*  298 */               if (k != m) {
/*  299 */                 addAttribute(attribute, object, k - paramInt1, m - paramInt1);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         }
/*  304 */         paramAttributedCharacterIterator.setIndex(m);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAttribute(AttributedCharacterIterator.Attribute paramAttribute, Object paramObject) {
/*  319 */     if (paramAttribute == null) {
/*  320 */       throw new NullPointerException();
/*      */     }
/*      */     
/*  323 */     int i = length();
/*  324 */     if (i == 0) {
/*  325 */       throw new IllegalArgumentException("Can't add attribute to 0-length text");
/*      */     }
/*      */     
/*  328 */     addAttributeImpl(paramAttribute, paramObject, 0, i);
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
/*      */   public void addAttribute(AttributedCharacterIterator.Attribute paramAttribute, Object paramObject, int paramInt1, int paramInt2) {
/*  345 */     if (paramAttribute == null) {
/*  346 */       throw new NullPointerException();
/*      */     }
/*      */     
/*  349 */     if (paramInt1 < 0 || paramInt2 > length() || paramInt1 >= paramInt2) {
/*  350 */       throw new IllegalArgumentException("Invalid substring range");
/*      */     }
/*      */     
/*  353 */     addAttributeImpl(paramAttribute, paramObject, paramInt1, paramInt2);
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
/*      */   public void addAttributes(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap, int paramInt1, int paramInt2) {
/*  372 */     if (paramMap == null) {
/*  373 */       throw new NullPointerException();
/*      */     }
/*      */     
/*  376 */     if (paramInt1 < 0 || paramInt2 > length() || paramInt1 > paramInt2) {
/*  377 */       throw new IllegalArgumentException("Invalid substring range");
/*      */     }
/*  379 */     if (paramInt1 == paramInt2) {
/*  380 */       if (paramMap.isEmpty())
/*      */         return; 
/*  382 */       throw new IllegalArgumentException("Can't add attribute to 0-length text");
/*      */     } 
/*      */ 
/*      */     
/*  386 */     if (this.runCount == 0) {
/*  387 */       createRunAttributeDataVectors();
/*      */     }
/*      */ 
/*      */     
/*  391 */     int i = ensureRunBreak(paramInt1);
/*  392 */     int j = ensureRunBreak(paramInt2);
/*      */ 
/*      */     
/*  395 */     Iterator<Map.Entry> iterator = paramMap.entrySet().iterator();
/*  396 */     while (iterator.hasNext()) {
/*  397 */       Map.Entry entry = iterator.next();
/*  398 */       addAttributeRunData((AttributedCharacterIterator.Attribute)entry.getKey(), entry.getValue(), i, j);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void addAttributeImpl(AttributedCharacterIterator.Attribute paramAttribute, Object paramObject, int paramInt1, int paramInt2) {
/*  406 */     if (this.runCount == 0) {
/*  407 */       createRunAttributeDataVectors();
/*      */     }
/*      */ 
/*      */     
/*  411 */     int i = ensureRunBreak(paramInt1);
/*  412 */     int j = ensureRunBreak(paramInt2);
/*      */     
/*  414 */     addAttributeRunData(paramAttribute, paramObject, i, j);
/*      */   }
/*      */ 
/*      */   
/*      */   private final void createRunAttributeDataVectors() {
/*  419 */     int[] arrayOfInt = new int[10];
/*      */ 
/*      */     
/*  422 */     Vector[] arrayOfVector1 = new Vector[10];
/*      */ 
/*      */     
/*  425 */     Vector[] arrayOfVector2 = new Vector[10];
/*      */     
/*  427 */     this.runStarts = arrayOfInt;
/*  428 */     this.runAttributes = (Vector<AttributedCharacterIterator.Attribute>[])arrayOfVector1;
/*  429 */     this.runAttributeValues = (Vector<Object>[])arrayOfVector2;
/*  430 */     this.runArraySize = 10;
/*  431 */     this.runCount = 1;
/*      */   }
/*      */ 
/*      */   
/*      */   private final int ensureRunBreak(int paramInt) {
/*  436 */     return ensureRunBreak(paramInt, true);
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
/*      */   private final int ensureRunBreak(int paramInt, boolean paramBoolean) {
/*  451 */     if (paramInt == length()) {
/*  452 */       return this.runCount;
/*      */     }
/*      */ 
/*      */     
/*  456 */     byte b = 0;
/*  457 */     while (b < this.runCount && this.runStarts[b] < paramInt) {
/*  458 */       b++;
/*      */     }
/*      */ 
/*      */     
/*  462 */     if (b < this.runCount && this.runStarts[b] == paramInt) {
/*  463 */       return b;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  468 */     if (this.runCount == this.runArraySize) {
/*  469 */       int j = this.runArraySize + 10;
/*  470 */       int[] arrayOfInt = new int[j];
/*      */ 
/*      */       
/*  473 */       Vector[] arrayOfVector1 = new Vector[j];
/*      */ 
/*      */       
/*  476 */       Vector[] arrayOfVector2 = new Vector[j];
/*      */       
/*  478 */       for (byte b1 = 0; b1 < this.runArraySize; b1++) {
/*  479 */         arrayOfInt[b1] = this.runStarts[b1];
/*  480 */         arrayOfVector1[b1] = this.runAttributes[b1];
/*  481 */         arrayOfVector2[b1] = this.runAttributeValues[b1];
/*      */       } 
/*  483 */       this.runStarts = arrayOfInt;
/*  484 */       this.runAttributes = (Vector<AttributedCharacterIterator.Attribute>[])arrayOfVector1;
/*  485 */       this.runAttributeValues = (Vector<Object>[])arrayOfVector2;
/*  486 */       this.runArraySize = j;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  491 */     Vector<AttributedCharacterIterator.Attribute> vector = null;
/*  492 */     Vector<Object> vector1 = null;
/*      */     
/*  494 */     if (paramBoolean) {
/*  495 */       Vector<AttributedCharacterIterator.Attribute> vector2 = this.runAttributes[b - 1];
/*  496 */       Vector<Object> vector3 = this.runAttributeValues[b - 1];
/*  497 */       if (vector2 != null) {
/*  498 */         vector = new Vector<>(vector2);
/*      */       }
/*  500 */       if (vector3 != null) {
/*  501 */         vector1 = new Vector(vector3);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  506 */     this.runCount++;
/*  507 */     for (int i = this.runCount - 1; i > b; i--) {
/*  508 */       this.runStarts[i] = this.runStarts[i - 1];
/*  509 */       this.runAttributes[i] = this.runAttributes[i - 1];
/*  510 */       this.runAttributeValues[i] = this.runAttributeValues[i - 1];
/*      */     } 
/*  512 */     this.runStarts[b] = paramInt;
/*  513 */     this.runAttributes[b] = vector;
/*  514 */     this.runAttributeValues[b] = vector1;
/*      */     
/*  516 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addAttributeRunData(AttributedCharacterIterator.Attribute paramAttribute, Object paramObject, int paramInt1, int paramInt2) {
/*  523 */     for (int i = paramInt1; i < paramInt2; i++) {
/*  524 */       int j = -1;
/*  525 */       if (this.runAttributes[i] == null) {
/*  526 */         Vector<AttributedCharacterIterator.Attribute> vector = new Vector();
/*  527 */         Vector<Object> vector1 = new Vector();
/*  528 */         this.runAttributes[i] = vector;
/*  529 */         this.runAttributeValues[i] = vector1;
/*      */       } else {
/*      */         
/*  532 */         j = this.runAttributes[i].indexOf(paramAttribute);
/*      */       } 
/*      */       
/*  535 */       if (j == -1) {
/*      */         
/*  537 */         int k = this.runAttributes[i].size();
/*  538 */         this.runAttributes[i].addElement(paramAttribute);
/*      */         try {
/*  540 */           this.runAttributeValues[i].addElement(paramObject);
/*      */         }
/*  542 */         catch (Exception exception) {
/*  543 */           this.runAttributes[i].setSize(k);
/*  544 */           this.runAttributeValues[i].setSize(k);
/*      */         } 
/*      */       } else {
/*      */         
/*  548 */         this.runAttributeValues[i].set(j, paramObject);
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
/*      */   public AttributedCharacterIterator getIterator() {
/*  560 */     return getIterator(null, 0, length());
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
/*      */   public AttributedCharacterIterator getIterator(AttributedCharacterIterator.Attribute[] paramArrayOfAttribute) {
/*  575 */     return getIterator(paramArrayOfAttribute, 0, length());
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
/*      */   public AttributedCharacterIterator getIterator(AttributedCharacterIterator.Attribute[] paramArrayOfAttribute, int paramInt1, int paramInt2) {
/*  595 */     return new AttributedStringIterator(paramArrayOfAttribute, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int length() {
/*  604 */     return this.text.length();
/*      */   }
/*      */   
/*      */   private char charAt(int paramInt) {
/*  608 */     return this.text.charAt(paramInt);
/*      */   }
/*      */   
/*      */   private synchronized Object getAttribute(AttributedCharacterIterator.Attribute paramAttribute, int paramInt) {
/*  612 */     Vector<AttributedCharacterIterator.Attribute> vector = this.runAttributes[paramInt];
/*  613 */     Vector<Object> vector1 = this.runAttributeValues[paramInt];
/*  614 */     if (vector == null) {
/*  615 */       return null;
/*      */     }
/*  617 */     int i = vector.indexOf(paramAttribute);
/*  618 */     if (i != -1) {
/*  619 */       return vector1.elementAt(i);
/*      */     }
/*      */     
/*  622 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Object getAttributeCheckRange(AttributedCharacterIterator.Attribute paramAttribute, int paramInt1, int paramInt2, int paramInt3) {
/*  628 */     Object object = getAttribute(paramAttribute, paramInt1);
/*  629 */     if (object instanceof Annotation) {
/*      */       
/*  631 */       if (paramInt2 > 0) {
/*  632 */         int j = paramInt1;
/*  633 */         int k = this.runStarts[j];
/*  634 */         while (k >= paramInt2 && 
/*  635 */           valuesMatch(object, getAttribute(paramAttribute, j - 1))) {
/*  636 */           j--;
/*  637 */           k = this.runStarts[j];
/*      */         } 
/*  639 */         if (k < paramInt2)
/*      */         {
/*  641 */           return null;
/*      */         }
/*      */       } 
/*  644 */       int i = length();
/*  645 */       if (paramInt3 < i) {
/*  646 */         int j = paramInt1;
/*  647 */         int k = (j < this.runCount - 1) ? this.runStarts[j + 1] : i;
/*  648 */         while (k <= paramInt3 && 
/*  649 */           valuesMatch(object, getAttribute(paramAttribute, j + 1))) {
/*  650 */           j++;
/*  651 */           k = (j < this.runCount - 1) ? this.runStarts[j + 1] : i;
/*      */         } 
/*  653 */         if (k > paramInt3)
/*      */         {
/*  655 */           return null;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  661 */     return object;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean attributeValuesMatch(Set<? extends AttributedCharacterIterator.Attribute> paramSet, int paramInt1, int paramInt2) {
/*  666 */     Iterator<? extends AttributedCharacterIterator.Attribute> iterator = paramSet.iterator();
/*  667 */     while (iterator.hasNext()) {
/*  668 */       AttributedCharacterIterator.Attribute attribute = iterator.next();
/*  669 */       if (!valuesMatch(getAttribute(attribute, paramInt1), getAttribute(attribute, paramInt2))) {
/*  670 */         return false;
/*      */       }
/*      */     } 
/*  673 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static final boolean valuesMatch(Object paramObject1, Object paramObject2) {
/*  678 */     if (paramObject1 == null) {
/*  679 */       return (paramObject2 == null);
/*      */     }
/*  681 */     return paramObject1.equals(paramObject2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void appendContents(StringBuffer paramStringBuffer, CharacterIterator paramCharacterIterator) {
/*  691 */     int i = paramCharacterIterator.getBeginIndex();
/*  692 */     int j = paramCharacterIterator.getEndIndex();
/*      */     
/*  694 */     while (i < j) {
/*  695 */       paramCharacterIterator.setIndex(i++);
/*  696 */       paramStringBuffer.append(paramCharacterIterator.current());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setAttributes(Map<AttributedCharacterIterator.Attribute, Object> paramMap, int paramInt) {
/*  706 */     if (this.runCount == 0) {
/*  707 */       createRunAttributeDataVectors();
/*      */     }
/*      */     
/*  710 */     int i = ensureRunBreak(paramInt, false);
/*      */     
/*      */     int j;
/*  713 */     if (paramMap != null && (j = paramMap.size()) > 0) {
/*  714 */       Vector<AttributedCharacterIterator.Attribute> vector = new Vector(j);
/*  715 */       Vector<Object> vector1 = new Vector(j);
/*  716 */       Iterator<Map.Entry> iterator = paramMap.entrySet().iterator();
/*      */       
/*  718 */       while (iterator.hasNext()) {
/*  719 */         Map.Entry entry = iterator.next();
/*      */         
/*  721 */         vector.add(entry.getKey());
/*  722 */         vector1.add(entry.getValue());
/*      */       } 
/*  724 */       this.runAttributes[i] = vector;
/*  725 */       this.runAttributeValues[i] = vector1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <K, V> boolean mapsDiffer(Map<K, V> paramMap1, Map<K, V> paramMap2) {
/*  733 */     if (paramMap1 == null) {
/*  734 */       return (paramMap2 != null && paramMap2.size() > 0);
/*      */     }
/*  736 */     return !paramMap1.equals(paramMap2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final class AttributedStringIterator
/*      */     implements AttributedCharacterIterator
/*      */   {
/*      */     private int beginIndex;
/*      */ 
/*      */     
/*      */     private int endIndex;
/*      */ 
/*      */     
/*      */     private AttributedCharacterIterator.Attribute[] relevantAttributes;
/*      */ 
/*      */     
/*      */     private int currentIndex;
/*      */ 
/*      */     
/*      */     private int currentRunIndex;
/*      */ 
/*      */     
/*      */     private int currentRunStart;
/*      */ 
/*      */     
/*      */     private int currentRunLimit;
/*      */ 
/*      */ 
/*      */     
/*      */     AttributedStringIterator(AttributedCharacterIterator.Attribute[] param1ArrayOfAttribute, int param1Int1, int param1Int2) {
/*  767 */       if (param1Int1 < 0 || param1Int1 > param1Int2 || param1Int2 > AttributedString.this.length()) {
/*  768 */         throw new IllegalArgumentException("Invalid substring range");
/*      */       }
/*      */       
/*  771 */       this.beginIndex = param1Int1;
/*  772 */       this.endIndex = param1Int2;
/*  773 */       this.currentIndex = param1Int1;
/*  774 */       updateRunInfo();
/*  775 */       if (param1ArrayOfAttribute != null) {
/*  776 */         this.relevantAttributes = (AttributedCharacterIterator.Attribute[])param1ArrayOfAttribute.clone();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/*  783 */       if (this == param1Object) {
/*  784 */         return true;
/*      */       }
/*  786 */       if (!(param1Object instanceof AttributedStringIterator)) {
/*  787 */         return false;
/*      */       }
/*      */       
/*  790 */       AttributedStringIterator attributedStringIterator = (AttributedStringIterator)param1Object;
/*      */       
/*  792 */       if (AttributedString.this != attributedStringIterator.getString())
/*  793 */         return false; 
/*  794 */       if (this.currentIndex != attributedStringIterator.currentIndex || this.beginIndex != attributedStringIterator.beginIndex || this.endIndex != attributedStringIterator.endIndex)
/*  795 */         return false; 
/*  796 */       return true;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  800 */       return AttributedString.this.text.hashCode() ^ this.currentIndex ^ this.beginIndex ^ this.endIndex;
/*      */     }
/*      */     
/*      */     public Object clone() {
/*      */       try {
/*  805 */         return super.clone();
/*      */       
/*      */       }
/*  808 */       catch (CloneNotSupportedException cloneNotSupportedException) {
/*  809 */         throw new InternalError(cloneNotSupportedException);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public char first() {
/*  816 */       return internalSetIndex(this.beginIndex);
/*      */     }
/*      */     
/*      */     public char last() {
/*  820 */       if (this.endIndex == this.beginIndex) {
/*  821 */         return internalSetIndex(this.endIndex);
/*      */       }
/*  823 */       return internalSetIndex(this.endIndex - 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public char current() {
/*  828 */       if (this.currentIndex == this.endIndex) {
/*  829 */         return Character.MAX_VALUE;
/*      */       }
/*  831 */       return AttributedString.this.charAt(this.currentIndex);
/*      */     }
/*      */ 
/*      */     
/*      */     public char next() {
/*  836 */       if (this.currentIndex < this.endIndex) {
/*  837 */         return internalSetIndex(this.currentIndex + 1);
/*      */       }
/*      */       
/*  840 */       return Character.MAX_VALUE;
/*      */     }
/*      */ 
/*      */     
/*      */     public char previous() {
/*  845 */       if (this.currentIndex > this.beginIndex) {
/*  846 */         return internalSetIndex(this.currentIndex - 1);
/*      */       }
/*      */       
/*  849 */       return Character.MAX_VALUE;
/*      */     }
/*      */ 
/*      */     
/*      */     public char setIndex(int param1Int) {
/*  854 */       if (param1Int < this.beginIndex || param1Int > this.endIndex)
/*  855 */         throw new IllegalArgumentException("Invalid index"); 
/*  856 */       return internalSetIndex(param1Int);
/*      */     }
/*      */     
/*      */     public int getBeginIndex() {
/*  860 */       return this.beginIndex;
/*      */     }
/*      */     
/*      */     public int getEndIndex() {
/*  864 */       return this.endIndex;
/*      */     }
/*      */     
/*      */     public int getIndex() {
/*  868 */       return this.currentIndex;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getRunStart() {
/*  874 */       return this.currentRunStart;
/*      */     }
/*      */     
/*      */     public int getRunStart(AttributedCharacterIterator.Attribute param1Attribute) {
/*  878 */       if (this.currentRunStart == this.beginIndex || this.currentRunIndex == -1) {
/*  879 */         return this.currentRunStart;
/*      */       }
/*  881 */       Object object = getAttribute(param1Attribute);
/*  882 */       int i = this.currentRunStart;
/*  883 */       int j = this.currentRunIndex;
/*  884 */       while (i > this.beginIndex && AttributedString
/*  885 */         .valuesMatch(object, AttributedString.this.getAttribute(param1Attribute, j - 1))) {
/*  886 */         j--;
/*  887 */         i = AttributedString.this.runStarts[j];
/*      */       } 
/*  889 */       if (i < this.beginIndex) {
/*  890 */         i = this.beginIndex;
/*      */       }
/*  892 */       return i;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getRunStart(Set<? extends AttributedCharacterIterator.Attribute> param1Set) {
/*  897 */       if (this.currentRunStart == this.beginIndex || this.currentRunIndex == -1) {
/*  898 */         return this.currentRunStart;
/*      */       }
/*  900 */       int i = this.currentRunStart;
/*  901 */       int j = this.currentRunIndex;
/*  902 */       while (i > this.beginIndex && AttributedString.this
/*  903 */         .attributeValuesMatch(param1Set, this.currentRunIndex, j - 1)) {
/*  904 */         j--;
/*  905 */         i = AttributedString.this.runStarts[j];
/*      */       } 
/*  907 */       if (i < this.beginIndex) {
/*  908 */         i = this.beginIndex;
/*      */       }
/*  910 */       return i;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getRunLimit() {
/*  915 */       return this.currentRunLimit;
/*      */     }
/*      */     
/*      */     public int getRunLimit(AttributedCharacterIterator.Attribute param1Attribute) {
/*  919 */       if (this.currentRunLimit == this.endIndex || this.currentRunIndex == -1) {
/*  920 */         return this.currentRunLimit;
/*      */       }
/*  922 */       Object object = getAttribute(param1Attribute);
/*  923 */       int i = this.currentRunLimit;
/*  924 */       int j = this.currentRunIndex;
/*  925 */       while (i < this.endIndex && AttributedString
/*  926 */         .valuesMatch(object, AttributedString.this.getAttribute(param1Attribute, j + 1))) {
/*  927 */         j++;
/*  928 */         i = (j < AttributedString.this.runCount - 1) ? AttributedString.this.runStarts[j + 1] : this.endIndex;
/*      */       } 
/*  930 */       if (i > this.endIndex) {
/*  931 */         i = this.endIndex;
/*      */       }
/*  933 */       return i;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getRunLimit(Set<? extends AttributedCharacterIterator.Attribute> param1Set) {
/*  938 */       if (this.currentRunLimit == this.endIndex || this.currentRunIndex == -1) {
/*  939 */         return this.currentRunLimit;
/*      */       }
/*  941 */       int i = this.currentRunLimit;
/*  942 */       int j = this.currentRunIndex;
/*  943 */       while (i < this.endIndex && AttributedString.this
/*  944 */         .attributeValuesMatch(param1Set, this.currentRunIndex, j + 1)) {
/*  945 */         j++;
/*  946 */         i = (j < AttributedString.this.runCount - 1) ? AttributedString.this.runStarts[j + 1] : this.endIndex;
/*      */       } 
/*  948 */       if (i > this.endIndex) {
/*  949 */         i = this.endIndex;
/*      */       }
/*  951 */       return i;
/*      */     }
/*      */ 
/*      */     
/*      */     public Map<AttributedCharacterIterator.Attribute, Object> getAttributes() {
/*  956 */       if (AttributedString.this.runAttributes == null || this.currentRunIndex == -1 || AttributedString.this.runAttributes[this.currentRunIndex] == null)
/*      */       {
/*      */         
/*  959 */         return new Hashtable<>();
/*      */       }
/*  961 */       return new AttributedString.AttributeMap(this.currentRunIndex, this.beginIndex, this.endIndex);
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<AttributedCharacterIterator.Attribute> getAllAttributeKeys() {
/*  966 */       if (AttributedString.this.runAttributes == null)
/*      */       {
/*      */         
/*  969 */         return new HashSet<>();
/*      */       }
/*  971 */       synchronized (AttributedString.this) {
/*      */ 
/*      */         
/*  974 */         HashSet<AttributedCharacterIterator.Attribute> hashSet = new HashSet();
/*  975 */         byte b = 0;
/*  976 */         while (b < AttributedString.this.runCount) {
/*  977 */           if (AttributedString.this.runStarts[b] < this.endIndex && (b == AttributedString.this.runCount - 1 || AttributedString.this.runStarts[b + 1] > this.beginIndex)) {
/*  978 */             Vector<AttributedCharacterIterator.Attribute> vector = AttributedString.this.runAttributes[b];
/*  979 */             if (vector != null) {
/*  980 */               int i = vector.size();
/*  981 */               while (i-- > 0) {
/*  982 */                 hashSet.add(vector.get(i));
/*      */               }
/*      */             } 
/*      */           } 
/*  986 */           b++;
/*      */         } 
/*  988 */         return hashSet;
/*      */       } 
/*      */     }
/*      */     
/*      */     public Object getAttribute(AttributedCharacterIterator.Attribute param1Attribute) {
/*  993 */       int i = this.currentRunIndex;
/*  994 */       if (i < 0) {
/*  995 */         return null;
/*      */       }
/*  997 */       return AttributedString.this.getAttributeCheckRange(param1Attribute, i, this.beginIndex, this.endIndex);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private AttributedString getString() {
/* 1003 */       return AttributedString.this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private char internalSetIndex(int param1Int) {
/* 1009 */       this.currentIndex = param1Int;
/* 1010 */       if (param1Int < this.currentRunStart || param1Int >= this.currentRunLimit) {
/* 1011 */         updateRunInfo();
/*      */       }
/* 1013 */       if (this.currentIndex == this.endIndex) {
/* 1014 */         return Character.MAX_VALUE;
/*      */       }
/* 1016 */       return AttributedString.this.charAt(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void updateRunInfo() {
/* 1022 */       if (this.currentIndex == this.endIndex) {
/* 1023 */         this.currentRunStart = this.currentRunLimit = this.endIndex;
/* 1024 */         this.currentRunIndex = -1;
/*      */       } else {
/* 1026 */         synchronized (AttributedString.this) {
/* 1027 */           byte b = -1;
/* 1028 */           while (b < AttributedString.this.runCount - 1 && AttributedString.this.runStarts[b + 1] <= this.currentIndex)
/* 1029 */             b++; 
/* 1030 */           this.currentRunIndex = b;
/* 1031 */           if (b >= 0) {
/* 1032 */             this.currentRunStart = AttributedString.this.runStarts[b];
/* 1033 */             if (this.currentRunStart < this.beginIndex) {
/* 1034 */               this.currentRunStart = this.beginIndex;
/*      */             }
/*      */           } else {
/* 1037 */             this.currentRunStart = this.beginIndex;
/*      */           } 
/* 1039 */           if (b < AttributedString.this.runCount - 1) {
/* 1040 */             this.currentRunLimit = AttributedString.this.runStarts[b + 1];
/* 1041 */             if (this.currentRunLimit > this.endIndex) {
/* 1042 */               this.currentRunLimit = this.endIndex;
/*      */             }
/*      */           } else {
/* 1045 */             this.currentRunLimit = this.endIndex;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private final class AttributeMap
/*      */     extends AbstractMap<AttributedCharacterIterator.Attribute, Object>
/*      */   {
/*      */     int runIndex;
/*      */     
/*      */     int beginIndex;
/*      */     int endIndex;
/*      */     
/*      */     AttributeMap(int param1Int1, int param1Int2, int param1Int3) {
/* 1062 */       this.runIndex = param1Int1;
/* 1063 */       this.beginIndex = param1Int2;
/* 1064 */       this.endIndex = param1Int3;
/*      */     }
/*      */     
/*      */     public Set<Map.Entry<AttributedCharacterIterator.Attribute, Object>> entrySet() {
/* 1068 */       HashSet<AttributeEntry> hashSet = new HashSet();
/* 1069 */       synchronized (AttributedString.this) {
/* 1070 */         int i = AttributedString.this.runAttributes[this.runIndex].size();
/* 1071 */         for (byte b = 0; b < i; b++) {
/* 1072 */           AttributedCharacterIterator.Attribute attribute = AttributedString.this.runAttributes[this.runIndex].get(b);
/* 1073 */           Object object = AttributedString.this.runAttributeValues[this.runIndex].get(b);
/* 1074 */           if (object instanceof Annotation) {
/* 1075 */             object = AttributedString.this.getAttributeCheckRange(attribute, this.runIndex, this.beginIndex, this.endIndex);
/*      */             
/* 1077 */             if (object == null) {
/*      */               continue;
/*      */             }
/*      */           } 
/*      */           
/* 1082 */           AttributeEntry attributeEntry = new AttributeEntry(attribute, object);
/* 1083 */           hashSet.add(attributeEntry); continue;
/*      */         } 
/*      */       } 
/* 1086 */       return (Set)hashSet;
/*      */     }
/*      */     
/*      */     public Object get(Object param1Object) {
/* 1090 */       return AttributedString.this.getAttributeCheckRange((AttributedCharacterIterator.Attribute)param1Object, this.runIndex, this.beginIndex, this.endIndex);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/AttributedString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */