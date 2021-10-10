/*      */ package javax.imageio.metadata;
/*      */ 
/*      */ import com.sun.imageio.plugins.common.StandardMetadataFormat;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.ResourceBundle;
/*      */ import javax.imageio.ImageTypeSpecifier;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class IIOMetadataFormatImpl
/*      */   implements IIOMetadataFormat
/*      */ {
/*      */   public static final String standardMetadataFormatName = "javax_imageio_1.0";
/*   86 */   private static IIOMetadataFormat standardFormat = null;
/*      */   
/*   88 */   private String resourceBaseName = getClass().getName() + "Resources";
/*      */ 
/*      */   
/*      */   private String rootName;
/*      */   
/*   93 */   private HashMap elementMap = new HashMap<>();
/*      */   
/*      */   class Element
/*      */   {
/*      */     String elementName;
/*      */     int childPolicy;
/*   99 */     int minChildren = 0;
/*  100 */     int maxChildren = 0;
/*      */ 
/*      */     
/*  103 */     List childList = new ArrayList();
/*      */ 
/*      */     
/*  106 */     List parentList = new ArrayList();
/*      */ 
/*      */     
/*  109 */     List attrList = new ArrayList();
/*      */     
/*  111 */     Map attrMap = new HashMap<>();
/*      */     
/*      */     IIOMetadataFormatImpl.ObjectValue objectValue;
/*      */   }
/*      */   
/*      */   class Attribute
/*      */   {
/*      */     String attrName;
/*  119 */     int valueType = 1;
/*      */     int dataType;
/*      */     boolean required;
/*  122 */     String defaultValue = null;
/*      */     
/*      */     List enumeratedValues;
/*      */     
/*      */     String minValue;
/*      */     
/*      */     String maxValue;
/*      */     
/*      */     int listMinLength;
/*      */     
/*      */     int listMaxLength;
/*      */   }
/*      */   
/*      */   class ObjectValue
/*      */   {
/*  137 */     int valueType = 0;
/*  138 */     Class classType = null;
/*  139 */     Object defaultValue = null;
/*      */ 
/*      */     
/*  142 */     List enumeratedValues = null;
/*      */ 
/*      */     
/*  145 */     Comparable minValue = null;
/*  146 */     Comparable maxValue = null;
/*      */ 
/*      */     
/*  149 */     int arrayMinLength = 0;
/*  150 */     int arrayMaxLength = 0;
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
/*      */   public IIOMetadataFormatImpl(String paramString, int paramInt) {
/*  171 */     if (paramString == null) {
/*  172 */       throw new IllegalArgumentException("rootName == null!");
/*      */     }
/*  174 */     if (paramInt < 0 || paramInt > 5 || paramInt == 5)
/*      */     {
/*      */       
/*  177 */       throw new IllegalArgumentException("Invalid value for childPolicy!");
/*      */     }
/*      */     
/*  180 */     this.rootName = paramString;
/*      */     
/*  182 */     Element element = new Element();
/*  183 */     element.elementName = paramString;
/*  184 */     element.childPolicy = paramInt;
/*      */     
/*  186 */     this.elementMap.put(paramString, element);
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
/*      */   public IIOMetadataFormatImpl(String paramString, int paramInt1, int paramInt2) {
/*  208 */     if (paramString == null) {
/*  209 */       throw new IllegalArgumentException("rootName == null!");
/*      */     }
/*  211 */     if (paramInt1 < 0) {
/*  212 */       throw new IllegalArgumentException("minChildren < 0!");
/*      */     }
/*  214 */     if (paramInt1 > paramInt2) {
/*  215 */       throw new IllegalArgumentException("minChildren > maxChildren!");
/*      */     }
/*      */     
/*  218 */     Element element = new Element();
/*  219 */     element.elementName = paramString;
/*  220 */     element.childPolicy = 5;
/*  221 */     element.minChildren = paramInt1;
/*  222 */     element.maxChildren = paramInt2;
/*      */     
/*  224 */     this.rootName = paramString;
/*  225 */     this.elementMap.put(paramString, element);
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
/*      */   protected void setResourceBaseName(String paramString) {
/*  246 */     if (paramString == null) {
/*  247 */       throw new IllegalArgumentException("resourceBaseName == null!");
/*      */     }
/*  249 */     this.resourceBaseName = paramString;
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
/*      */   protected String getResourceBaseName() {
/*  261 */     return this.resourceBaseName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Element getElement(String paramString, boolean paramBoolean) {
/*  272 */     if (paramBoolean && paramString == null) {
/*  273 */       throw new IllegalArgumentException("element name is null!");
/*      */     }
/*  275 */     Element element = (Element)this.elementMap.get(paramString);
/*  276 */     if (paramBoolean && element == null) {
/*  277 */       throw new IllegalArgumentException("No such element: " + paramString);
/*      */     }
/*      */     
/*  280 */     return element;
/*      */   }
/*      */   
/*      */   private Element getElement(String paramString) {
/*  284 */     return getElement(paramString, true);
/*      */   }
/*      */ 
/*      */   
/*      */   private Attribute getAttribute(String paramString1, String paramString2) {
/*  289 */     Element element = getElement(paramString1);
/*  290 */     Attribute attribute = (Attribute)element.attrMap.get(paramString2);
/*  291 */     if (attribute == null) {
/*  292 */       throw new IllegalArgumentException("No such attribute \"" + paramString2 + "\"!");
/*      */     }
/*      */     
/*  295 */     return attribute;
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
/*      */   protected void addElement(String paramString1, String paramString2, int paramInt) {
/*  320 */     Element element1 = getElement(paramString2);
/*  321 */     if (paramInt < 0 || paramInt > 5 || paramInt == 5)
/*      */     {
/*      */       
/*  324 */       throw new IllegalArgumentException("Invalid value for childPolicy!");
/*      */     }
/*      */ 
/*      */     
/*  328 */     Element element2 = new Element();
/*  329 */     element2.elementName = paramString1;
/*  330 */     element2.childPolicy = paramInt;
/*      */     
/*  332 */     element1.childList.add(paramString1);
/*  333 */     element2.parentList.add(paramString2);
/*      */     
/*  335 */     this.elementMap.put(paramString1, element2);
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
/*      */   protected void addElement(String paramString1, String paramString2, int paramInt1, int paramInt2) {
/*  358 */     Element element1 = getElement(paramString2);
/*  359 */     if (paramInt1 < 0) {
/*  360 */       throw new IllegalArgumentException("minChildren < 0!");
/*      */     }
/*  362 */     if (paramInt1 > paramInt2) {
/*  363 */       throw new IllegalArgumentException("minChildren > maxChildren!");
/*      */     }
/*      */     
/*  366 */     Element element2 = new Element();
/*  367 */     element2.elementName = paramString1;
/*  368 */     element2.childPolicy = 5;
/*  369 */     element2.minChildren = paramInt1;
/*  370 */     element2.maxChildren = paramInt2;
/*      */     
/*  372 */     element1.childList.add(paramString1);
/*  373 */     element2.parentList.add(paramString2);
/*      */     
/*  375 */     this.elementMap.put(paramString1, element2);
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
/*      */   protected void addChildElement(String paramString1, String paramString2) {
/*  395 */     Element element1 = getElement(paramString2);
/*  396 */     Element element2 = getElement(paramString1);
/*  397 */     element1.childList.add(paramString1);
/*  398 */     element2.parentList.add(paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeElement(String paramString) {
/*  409 */     Element element = getElement(paramString, false);
/*  410 */     if (element != null) {
/*  411 */       Iterator<String> iterator = element.parentList.iterator();
/*  412 */       while (iterator.hasNext()) {
/*  413 */         String str = iterator.next();
/*  414 */         Element element1 = getElement(str, false);
/*  415 */         if (element1 != null) {
/*  416 */           element1.childList.remove(paramString);
/*      */         }
/*      */       } 
/*  419 */       this.elementMap.remove(paramString);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addAttribute(String paramString1, String paramString2, int paramInt, boolean paramBoolean, String paramString3) {
/*  448 */     Element element = getElement(paramString1);
/*  449 */     if (paramString2 == null) {
/*  450 */       throw new IllegalArgumentException("attrName == null!");
/*      */     }
/*  452 */     if (paramInt < 0 || paramInt > 4) {
/*  453 */       throw new IllegalArgumentException("Invalid value for dataType!");
/*      */     }
/*      */     
/*  456 */     Attribute attribute = new Attribute();
/*  457 */     attribute.attrName = paramString2;
/*  458 */     attribute.valueType = 1;
/*  459 */     attribute.dataType = paramInt;
/*  460 */     attribute.required = paramBoolean;
/*  461 */     attribute.defaultValue = paramString3;
/*      */     
/*  463 */     element.attrList.add(paramString2);
/*  464 */     element.attrMap.put(paramString2, attribute);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addAttribute(String paramString1, String paramString2, int paramInt, boolean paramBoolean, String paramString3, List<String> paramList) {
/*  504 */     Element element = getElement(paramString1);
/*  505 */     if (paramString2 == null) {
/*  506 */       throw new IllegalArgumentException("attrName == null!");
/*      */     }
/*  508 */     if (paramInt < 0 || paramInt > 4) {
/*  509 */       throw new IllegalArgumentException("Invalid value for dataType!");
/*      */     }
/*  511 */     if (paramList == null) {
/*  512 */       throw new IllegalArgumentException("enumeratedValues == null!");
/*      */     }
/*  514 */     if (paramList.size() == 0) {
/*  515 */       throw new IllegalArgumentException("enumeratedValues is empty!");
/*      */     }
/*  517 */     Iterator<String> iterator = paramList.iterator();
/*  518 */     while (iterator.hasNext()) {
/*  519 */       Object object = iterator.next();
/*  520 */       if (object == null) {
/*  521 */         throw new IllegalArgumentException("enumeratedValues contains a null!");
/*      */       }
/*      */       
/*  524 */       if (!(object instanceof String)) {
/*  525 */         throw new IllegalArgumentException("enumeratedValues contains a non-String value!");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  530 */     Attribute attribute = new Attribute();
/*  531 */     attribute.attrName = paramString2;
/*  532 */     attribute.valueType = 16;
/*  533 */     attribute.dataType = paramInt;
/*  534 */     attribute.required = paramBoolean;
/*  535 */     attribute.defaultValue = paramString3;
/*  536 */     attribute.enumeratedValues = paramList;
/*      */     
/*  538 */     element.attrList.add(paramString2);
/*  539 */     element.attrMap.put(paramString2, attribute);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addAttribute(String paramString1, String paramString2, int paramInt, boolean paramBoolean1, String paramString3, String paramString4, String paramString5, boolean paramBoolean2, boolean paramBoolean3) {
/*  581 */     Element element = getElement(paramString1);
/*  582 */     if (paramString2 == null) {
/*  583 */       throw new IllegalArgumentException("attrName == null!");
/*      */     }
/*  585 */     if (paramInt < 0 || paramInt > 4) {
/*  586 */       throw new IllegalArgumentException("Invalid value for dataType!");
/*      */     }
/*      */     
/*  589 */     Attribute attribute = new Attribute();
/*  590 */     attribute.attrName = paramString2;
/*  591 */     attribute.valueType = 2;
/*  592 */     if (paramBoolean2) {
/*  593 */       attribute.valueType |= 0x4;
/*      */     }
/*  595 */     if (paramBoolean3) {
/*  596 */       attribute.valueType |= 0x8;
/*      */     }
/*  598 */     attribute.dataType = paramInt;
/*  599 */     attribute.required = paramBoolean1;
/*  600 */     attribute.defaultValue = paramString3;
/*  601 */     attribute.minValue = paramString4;
/*  602 */     attribute.maxValue = paramString5;
/*      */     
/*  604 */     element.attrList.add(paramString2);
/*  605 */     element.attrMap.put(paramString2, attribute);
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
/*      */   protected void addAttribute(String paramString1, String paramString2, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3) {
/*  637 */     Element element = getElement(paramString1);
/*  638 */     if (paramString2 == null) {
/*  639 */       throw new IllegalArgumentException("attrName == null!");
/*      */     }
/*  641 */     if (paramInt1 < 0 || paramInt1 > 4) {
/*  642 */       throw new IllegalArgumentException("Invalid value for dataType!");
/*      */     }
/*  644 */     if (paramInt2 < 0 || paramInt2 > paramInt3) {
/*  645 */       throw new IllegalArgumentException("Invalid list bounds!");
/*      */     }
/*      */     
/*  648 */     Attribute attribute = new Attribute();
/*  649 */     attribute.attrName = paramString2;
/*  650 */     attribute.valueType = 32;
/*  651 */     attribute.dataType = paramInt1;
/*  652 */     attribute.required = paramBoolean;
/*  653 */     attribute.listMinLength = paramInt2;
/*  654 */     attribute.listMaxLength = paramInt3;
/*      */     
/*  656 */     element.attrList.add(paramString2);
/*  657 */     element.attrMap.put(paramString2, attribute);
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
/*      */   protected void addBooleanAttribute(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2) {
/*  684 */     ArrayList<String> arrayList = new ArrayList();
/*  685 */     arrayList.add("TRUE");
/*  686 */     arrayList.add("FALSE");
/*      */     
/*  688 */     String str = null;
/*  689 */     if (paramBoolean1) {
/*  690 */       str = paramBoolean2 ? "TRUE" : "FALSE";
/*      */     }
/*  692 */     addAttribute(paramString1, paramString2, 1, true, str, arrayList);
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
/*      */   protected void removeAttribute(String paramString1, String paramString2) {
/*  712 */     Element element = getElement(paramString1);
/*  713 */     element.attrList.remove(paramString2);
/*  714 */     element.attrMap.remove(paramString2);
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
/*      */   protected <T> void addObjectValue(String paramString, Class<T> paramClass, boolean paramBoolean, T paramT) {
/*  742 */     Element element = getElement(paramString);
/*  743 */     ObjectValue objectValue = new ObjectValue();
/*  744 */     objectValue.valueType = 1;
/*  745 */     objectValue.classType = paramClass;
/*  746 */     objectValue.defaultValue = paramT;
/*      */     
/*  748 */     element.objectValue = objectValue;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected <T> void addObjectValue(String paramString, Class<T> paramClass, boolean paramBoolean, T paramT, List<? extends T> paramList) {
/*  789 */     Element element = getElement(paramString);
/*  790 */     if (paramList == null) {
/*  791 */       throw new IllegalArgumentException("enumeratedValues == null!");
/*      */     }
/*  793 */     if (paramList.size() == 0) {
/*  794 */       throw new IllegalArgumentException("enumeratedValues is empty!");
/*      */     }
/*  796 */     Iterator<? extends T> iterator = paramList.iterator();
/*  797 */     while (iterator.hasNext()) {
/*  798 */       T t = iterator.next();
/*  799 */       if (t == null) {
/*  800 */         throw new IllegalArgumentException("enumeratedValues contains a null!");
/*      */       }
/*  802 */       if (!paramClass.isInstance(t)) {
/*  803 */         throw new IllegalArgumentException("enumeratedValues contains a value not of class classType!");
/*      */       }
/*      */     } 
/*      */     
/*  807 */     ObjectValue objectValue = new ObjectValue();
/*  808 */     objectValue.valueType = 16;
/*  809 */     objectValue.classType = paramClass;
/*  810 */     objectValue.defaultValue = paramT;
/*  811 */     objectValue.enumeratedValues = paramList;
/*      */     
/*  813 */     element.objectValue = objectValue;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected <T extends Comparable<? super T>> void addObjectValue(String paramString, Class<T> paramClass, T paramT, Comparable<? super T> paramComparable1, Comparable<? super T> paramComparable2, boolean paramBoolean1, boolean paramBoolean2) {
/*  856 */     Element element = getElement(paramString);
/*  857 */     ObjectValue objectValue = new ObjectValue();
/*  858 */     objectValue.valueType = 2;
/*  859 */     if (paramBoolean1) {
/*  860 */       objectValue.valueType |= 0x4;
/*      */     }
/*  862 */     if (paramBoolean2) {
/*  863 */       objectValue.valueType |= 0x8;
/*      */     }
/*  865 */     objectValue.classType = paramClass;
/*  866 */     objectValue.defaultValue = paramT;
/*  867 */     objectValue.minValue = paramComparable1;
/*  868 */     objectValue.maxValue = paramComparable2;
/*      */     
/*  870 */     element.objectValue = objectValue;
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
/*      */   protected void addObjectValue(String paramString, Class<?> paramClass, int paramInt1, int paramInt2) {
/*  897 */     Element element = getElement(paramString);
/*  898 */     ObjectValue objectValue = new ObjectValue();
/*  899 */     objectValue.valueType = 32;
/*  900 */     objectValue.classType = paramClass;
/*  901 */     objectValue.arrayMinLength = paramInt1;
/*  902 */     objectValue.arrayMaxLength = paramInt2;
/*      */     
/*  904 */     element.objectValue = objectValue;
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
/*      */   protected void removeObjectValue(String paramString) {
/*  917 */     Element element = getElement(paramString);
/*  918 */     element.objectValue = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRootName() {
/*  928 */     return this.rootName;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean canNodeAppear(String paramString, ImageTypeSpecifier paramImageTypeSpecifier);
/*      */ 
/*      */   
/*      */   public int getElementMinChildren(String paramString) {
/*  937 */     Element element = getElement(paramString);
/*  938 */     if (element.childPolicy != 5) {
/*  939 */       throw new IllegalArgumentException("Child policy not CHILD_POLICY_REPEAT!");
/*      */     }
/*  941 */     return element.minChildren;
/*      */   }
/*      */   
/*      */   public int getElementMaxChildren(String paramString) {
/*  945 */     Element element = getElement(paramString);
/*  946 */     if (element.childPolicy != 5) {
/*  947 */       throw new IllegalArgumentException("Child policy not CHILD_POLICY_REPEAT!");
/*      */     }
/*  949 */     return element.maxChildren;
/*      */   }
/*      */   
/*      */   private String getResource(String paramString, Locale paramLocale) {
/*  953 */     if (paramLocale == null) {
/*  954 */       paramLocale = Locale.getDefault();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  966 */     ClassLoader classLoader = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*      */         {
/*      */           public Object run() {
/*  969 */             return Thread.currentThread().getContextClassLoader();
/*      */           }
/*      */         });
/*      */     
/*  973 */     ResourceBundle resourceBundle = null;
/*      */     try {
/*  975 */       resourceBundle = ResourceBundle.getBundle(this.resourceBaseName, paramLocale, classLoader);
/*      */     }
/*  977 */     catch (MissingResourceException missingResourceException) {
/*      */       try {
/*  979 */         resourceBundle = ResourceBundle.getBundle(this.resourceBaseName, paramLocale);
/*  980 */       } catch (MissingResourceException missingResourceException1) {
/*  981 */         return null;
/*      */       } 
/*      */     } 
/*      */     
/*      */     try {
/*  986 */       return resourceBundle.getString(paramString);
/*  987 */     } catch (MissingResourceException missingResourceException) {
/*  988 */       return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getElementDescription(String paramString, Locale paramLocale) {
/* 1024 */     Element element = getElement(paramString);
/* 1025 */     return getResource(paramString, paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getChildPolicy(String paramString) {
/* 1031 */     Element element = getElement(paramString);
/* 1032 */     return element.childPolicy;
/*      */   }
/*      */   
/*      */   public String[] getChildNames(String paramString) {
/* 1036 */     Element element = getElement(paramString);
/* 1037 */     if (element.childPolicy == 0) {
/* 1038 */       return null;
/*      */     }
/* 1040 */     return (String[])element.childList.toArray((Object[])new String[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getAttributeNames(String paramString) {
/* 1046 */     Element element = getElement(paramString);
/* 1047 */     List list = element.attrList;
/*      */     
/* 1049 */     String[] arrayOfString = new String[list.size()];
/* 1050 */     return (String[])list.toArray((Object[])arrayOfString);
/*      */   }
/*      */   
/*      */   public int getAttributeValueType(String paramString1, String paramString2) {
/* 1054 */     Attribute attribute = getAttribute(paramString1, paramString2);
/* 1055 */     return attribute.valueType;
/*      */   }
/*      */   
/*      */   public int getAttributeDataType(String paramString1, String paramString2) {
/* 1059 */     Attribute attribute = getAttribute(paramString1, paramString2);
/* 1060 */     return attribute.dataType;
/*      */   }
/*      */   
/*      */   public boolean isAttributeRequired(String paramString1, String paramString2) {
/* 1064 */     Attribute attribute = getAttribute(paramString1, paramString2);
/* 1065 */     return attribute.required;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getAttributeDefaultValue(String paramString1, String paramString2) {
/* 1070 */     Attribute attribute = getAttribute(paramString1, paramString2);
/* 1071 */     return attribute.defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public String[] getAttributeEnumerations(String paramString1, String paramString2) {
/* 1076 */     Attribute attribute = getAttribute(paramString1, paramString2);
/* 1077 */     if (attribute.valueType != 16) {
/* 1078 */       throw new IllegalArgumentException("Attribute not an enumeration!");
/*      */     }
/*      */ 
/*      */     
/* 1082 */     List list = attribute.enumeratedValues;
/* 1083 */     Iterator iterator = list.iterator();
/* 1084 */     String[] arrayOfString = new String[list.size()];
/* 1085 */     return (String[])list.toArray((Object[])arrayOfString);
/*      */   }
/*      */   
/*      */   public String getAttributeMinValue(String paramString1, String paramString2) {
/* 1089 */     Attribute attribute = getAttribute(paramString1, paramString2);
/* 1090 */     if (attribute.valueType != 2 && attribute.valueType != 6 && attribute.valueType != 10 && attribute.valueType != 14)
/*      */     {
/*      */ 
/*      */       
/* 1094 */       throw new IllegalArgumentException("Attribute not a range!");
/*      */     }
/*      */     
/* 1097 */     return attribute.minValue;
/*      */   }
/*      */   
/*      */   public String getAttributeMaxValue(String paramString1, String paramString2) {
/* 1101 */     Attribute attribute = getAttribute(paramString1, paramString2);
/* 1102 */     if (attribute.valueType != 2 && attribute.valueType != 6 && attribute.valueType != 10 && attribute.valueType != 14)
/*      */     {
/*      */ 
/*      */       
/* 1106 */       throw new IllegalArgumentException("Attribute not a range!");
/*      */     }
/*      */     
/* 1109 */     return attribute.maxValue;
/*      */   }
/*      */   
/*      */   public int getAttributeListMinLength(String paramString1, String paramString2) {
/* 1113 */     Attribute attribute = getAttribute(paramString1, paramString2);
/* 1114 */     if (attribute.valueType != 32) {
/* 1115 */       throw new IllegalArgumentException("Attribute not a list!");
/*      */     }
/*      */     
/* 1118 */     return attribute.listMinLength;
/*      */   }
/*      */   
/*      */   public int getAttributeListMaxLength(String paramString1, String paramString2) {
/* 1122 */     Attribute attribute = getAttribute(paramString1, paramString2);
/* 1123 */     if (attribute.valueType != 32) {
/* 1124 */       throw new IllegalArgumentException("Attribute not a list!");
/*      */     }
/*      */     
/* 1127 */     return attribute.listMaxLength;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeDescription(String paramString1, String paramString2, Locale paramLocale) {
/* 1169 */     Element element = getElement(paramString1);
/* 1170 */     if (paramString2 == null) {
/* 1171 */       throw new IllegalArgumentException("attrName == null!");
/*      */     }
/* 1173 */     Attribute attribute = (Attribute)element.attrMap.get(paramString2);
/* 1174 */     if (attribute == null) {
/* 1175 */       throw new IllegalArgumentException("No such attribute!");
/*      */     }
/*      */     
/* 1178 */     String str = paramString1 + "/" + paramString2;
/* 1179 */     return getResource(str, paramLocale);
/*      */   }
/*      */   
/*      */   private ObjectValue getObjectValue(String paramString) {
/* 1183 */     Element element = getElement(paramString);
/* 1184 */     ObjectValue objectValue = element.objectValue;
/* 1185 */     if (objectValue == null) {
/* 1186 */       throw new IllegalArgumentException("No object within element " + paramString + "!");
/*      */     }
/*      */     
/* 1189 */     return objectValue;
/*      */   }
/*      */   
/*      */   public int getObjectValueType(String paramString) {
/* 1193 */     Element element = getElement(paramString);
/* 1194 */     ObjectValue objectValue = element.objectValue;
/* 1195 */     if (objectValue == null) {
/* 1196 */       return 0;
/*      */     }
/* 1198 */     return objectValue.valueType;
/*      */   }
/*      */   
/*      */   public Class<?> getObjectClass(String paramString) {
/* 1202 */     ObjectValue objectValue = getObjectValue(paramString);
/* 1203 */     return objectValue.classType;
/*      */   }
/*      */   
/*      */   public Object getObjectDefaultValue(String paramString) {
/* 1207 */     ObjectValue objectValue = getObjectValue(paramString);
/* 1208 */     return objectValue.defaultValue;
/*      */   }
/*      */   
/*      */   public Object[] getObjectEnumerations(String paramString) {
/* 1212 */     ObjectValue objectValue = getObjectValue(paramString);
/* 1213 */     if (objectValue.valueType != 16) {
/* 1214 */       throw new IllegalArgumentException("Not an enumeration!");
/*      */     }
/* 1216 */     List list = objectValue.enumeratedValues;
/* 1217 */     Object[] arrayOfObject = new Object[list.size()];
/* 1218 */     return list.toArray(arrayOfObject);
/*      */   }
/*      */   
/*      */   public Comparable<?> getObjectMinValue(String paramString) {
/* 1222 */     ObjectValue objectValue = getObjectValue(paramString);
/* 1223 */     if ((objectValue.valueType & 0x2) != 2) {
/* 1224 */       throw new IllegalArgumentException("Not a range!");
/*      */     }
/* 1226 */     return objectValue.minValue;
/*      */   }
/*      */   
/*      */   public Comparable<?> getObjectMaxValue(String paramString) {
/* 1230 */     ObjectValue objectValue = getObjectValue(paramString);
/* 1231 */     if ((objectValue.valueType & 0x2) != 2) {
/* 1232 */       throw new IllegalArgumentException("Not a range!");
/*      */     }
/* 1234 */     return objectValue.maxValue;
/*      */   }
/*      */   
/*      */   public int getObjectArrayMinLength(String paramString) {
/* 1238 */     ObjectValue objectValue = getObjectValue(paramString);
/* 1239 */     if (objectValue.valueType != 32) {
/* 1240 */       throw new IllegalArgumentException("Not a list!");
/*      */     }
/* 1242 */     return objectValue.arrayMinLength;
/*      */   }
/*      */   
/*      */   public int getObjectArrayMaxLength(String paramString) {
/* 1246 */     ObjectValue objectValue = getObjectValue(paramString);
/* 1247 */     if (objectValue.valueType != 32) {
/* 1248 */       throw new IllegalArgumentException("Not a list!");
/*      */     }
/* 1250 */     return objectValue.arrayMaxLength;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static synchronized void createStandardFormat() {
/* 1256 */     if (standardFormat == null) {
/* 1257 */       standardFormat = new StandardMetadataFormat();
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
/*      */   public static IIOMetadataFormat getStandardFormatInstance() {
/* 1270 */     createStandardFormat();
/* 1271 */     return standardFormat;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/metadata/IIOMetadataFormatImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */