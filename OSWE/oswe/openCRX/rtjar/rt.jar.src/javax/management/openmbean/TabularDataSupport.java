/*     */ package javax.management.openmbean;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessController;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TabularDataSupport
/*     */   implements TabularData, Map<Object, Object>, Cloneable, Serializable
/*     */ {
/*     */   static final long serialVersionUID = 5720150593236309827L;
/*     */   private Map<Object, CompositeData> dataMap;
/*     */   private final TabularType tabularType;
/*     */   private transient String[] indexNamesArray;
/*     */   
/*     */   public TabularDataSupport(TabularType paramTabularType) {
/* 118 */     this(paramTabularType, 16, 0.75F);
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
/*     */   public TabularDataSupport(TabularType paramTabularType, int paramInt, float paramFloat) {
/* 140 */     if (paramTabularType == null) {
/* 141 */       throw new IllegalArgumentException("Argument tabularType cannot be null.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 146 */     this.tabularType = paramTabularType;
/* 147 */     List<String> list = paramTabularType.getIndexNames();
/* 148 */     this.indexNamesArray = list.<String>toArray(new String[list.size()]);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("jmx.tabular.data.hash.map"));
/*     */     
/* 155 */     boolean bool = "true".equalsIgnoreCase(str);
/*     */ 
/*     */ 
/*     */     
/* 159 */     this.dataMap = bool ? new HashMap<>(paramInt, paramFloat) : new LinkedHashMap<>(paramInt, paramFloat);
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
/*     */   public TabularType getTabularType() {
/* 175 */     return this.tabularType;
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
/*     */   public Object[] calculateIndex(CompositeData paramCompositeData) {
/* 200 */     checkValueType(paramCompositeData);
/*     */ 
/*     */ 
/*     */     
/* 204 */     return internalCalculateIndex(paramCompositeData).toArray();
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
/*     */   public boolean containsKey(Object paramObject) {
/*     */     Object[] arrayOfObject;
/*     */     try {
/* 229 */       arrayOfObject = (Object[])paramObject;
/* 230 */     } catch (ClassCastException classCastException) {
/* 231 */       return false;
/*     */     } 
/*     */     
/* 234 */     return containsKey(arrayOfObject);
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
/*     */   public boolean containsKey(Object[] paramArrayOfObject) {
/* 248 */     return (paramArrayOfObject == null) ? false : this.dataMap.containsKey(Arrays.asList(paramArrayOfObject));
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
/*     */   public boolean containsValue(CompositeData paramCompositeData) {
/* 262 */     return this.dataMap.containsValue(paramCompositeData);
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
/*     */   public boolean containsValue(Object paramObject) {
/* 275 */     return this.dataMap.containsValue(paramObject);
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
/*     */   public Object get(Object paramObject) {
/* 288 */     return get((Object[])paramObject);
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
/*     */   public CompositeData get(Object[] paramArrayOfObject) {
/* 312 */     checkKeyType(paramArrayOfObject);
/*     */ 
/*     */ 
/*     */     
/* 316 */     return this.dataMap.get(Arrays.asList(paramArrayOfObject));
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
/*     */ 
/*     */ 
/*     */   
/*     */   public Object put(Object paramObject1, Object paramObject2) {
/* 346 */     internalPut((CompositeData)paramObject2);
/* 347 */     return paramObject2;
/*     */   }
/*     */   
/*     */   public void put(CompositeData paramCompositeData) {
/* 351 */     internalPut(paramCompositeData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CompositeData internalPut(CompositeData paramCompositeData) {
/* 359 */     List<?> list = checkValueAndIndex(paramCompositeData);
/*     */ 
/*     */ 
/*     */     
/* 363 */     return this.dataMap.put(list, paramCompositeData);
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
/*     */   public Object remove(Object paramObject) {
/* 381 */     return remove((Object[])paramObject);
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
/*     */   public CompositeData remove(Object[] paramArrayOfObject) {
/* 404 */     checkKeyType(paramArrayOfObject);
/*     */ 
/*     */ 
/*     */     
/* 408 */     return this.dataMap.remove(Arrays.asList(paramArrayOfObject));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<?, ?> paramMap) {
/*     */     CompositeData[] arrayOfCompositeData;
/* 449 */     if (paramMap == null || paramMap.size() == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 458 */       arrayOfCompositeData = (CompositeData[])paramMap.values().toArray((Object[])new CompositeData[paramMap.size()]);
/* 459 */     } catch (ArrayStoreException arrayStoreException) {
/* 460 */       throw new ClassCastException("Map argument t contains values which are not instances of <tt>CompositeData</tt>");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 465 */     putAll(arrayOfCompositeData);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(CompositeData[] paramArrayOfCompositeData) {
/* 501 */     if (paramArrayOfCompositeData == null || paramArrayOfCompositeData.length == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 506 */     ArrayList<List<?>> arrayList = new ArrayList(paramArrayOfCompositeData.length + 1);
/*     */ 
/*     */     
/*     */     byte b;
/*     */ 
/*     */     
/* 512 */     for (b = 0; b < paramArrayOfCompositeData.length; b++) {
/*     */       
/* 514 */       List<?> list = checkValueAndIndex(paramArrayOfCompositeData[b]);
/*     */       
/* 516 */       if (arrayList.contains(list)) {
/* 517 */         throw new KeyAlreadyExistsException("Argument elements values[" + b + "] and values[" + arrayList.indexOf(list) + "] have the same indexes, calculated according to this TabularData instance's tabularType.");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 522 */       arrayList.add(list);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 527 */     for (b = 0; b < paramArrayOfCompositeData.length; b++) {
/* 528 */       this.dataMap.put(arrayList.get(b), paramArrayOfCompositeData[b]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 537 */     this.dataMap.clear();
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
/*     */   public int size() {
/* 551 */     return this.dataMap.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 561 */     return (size() == 0);
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
/*     */ 
/*     */   
/*     */   public Set<Object> keySet() {
/* 590 */     return this.dataMap.keySet();
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
/*     */   public Collection<Object> values() {
/* 616 */     return Util.<Collection<Object>>cast(this.dataMap.values());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<Object, Object>> entrySet() {
/* 652 */     return Util.<Set<Map.Entry<Object, Object>>>cast(this.dataMap.entrySet());
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
/*     */   public Object clone() {
/*     */     try {
/* 670 */       TabularDataSupport tabularDataSupport = (TabularDataSupport)super.clone();
/* 671 */       tabularDataSupport.dataMap = new HashMap<>(tabularDataSupport.dataMap);
/* 672 */       return tabularDataSupport;
/*     */     }
/* 674 */     catch (CloneNotSupportedException cloneNotSupportedException) {
/* 675 */       throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*     */     TabularData tabularData;
/* 701 */     if (paramObject == null) {
/* 702 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 709 */       tabularData = (TabularData)paramObject;
/* 710 */     } catch (ClassCastException classCastException) {
/* 711 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 718 */     if (!getTabularType().equals(tabularData.getTabularType())) {
/* 719 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 727 */     if (size() != tabularData.size()) {
/* 728 */       return false;
/*     */     }
/* 730 */     for (CompositeData compositeData : this.dataMap.values()) {
/* 731 */       if (!tabularData.containsValue(compositeData)) {
/* 732 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 738 */     return true;
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
/*     */   public int hashCode() {
/* 761 */     int i = 0;
/*     */     
/* 763 */     i += this.tabularType.hashCode();
/* 764 */     for (Object object : values()) {
/* 765 */       i += object.hashCode();
/*     */     }
/* 767 */     return i;
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
/*     */   public String toString() {
/* 783 */     return 
/* 784 */       getClass().getName() + "(tabularType=" + 
/* 785 */       this.tabularType
/* 786 */       .toString() + ",contents=" + 
/* 787 */       this.dataMap
/* 788 */       .toString() + ")";
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
/*     */   private List<?> internalCalculateIndex(CompositeData paramCompositeData) {
/* 811 */     return Collections.unmodifiableList(Arrays.asList(paramCompositeData.getAll(this.indexNamesArray)));
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
/*     */   private void checkKeyType(Object[] paramArrayOfObject) {
/* 824 */     if (paramArrayOfObject == null || paramArrayOfObject.length == 0) {
/* 825 */       throw new NullPointerException("Argument key cannot be null or empty.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 832 */     if (paramArrayOfObject.length != this.indexNamesArray.length) {
/* 833 */       throw new InvalidKeyException("Argument key's length=" + paramArrayOfObject.length + " is different from the number of item values, which is " + this.indexNamesArray.length + ", specified for the indexing rows in this TabularData instance.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 841 */     for (byte b = 0; b < paramArrayOfObject.length; b++) {
/* 842 */       OpenType<?> openType = this.tabularType.getRowType().getType(this.indexNamesArray[b]);
/* 843 */       if (paramArrayOfObject[b] != null && !openType.isValue(paramArrayOfObject[b])) {
/* 844 */         throw new InvalidKeyException("Argument element key[" + b + "] is not a value for the open type expected for this element of the index, whose name is \"" + this.indexNamesArray[b] + "\" and whose open type is " + openType);
/*     */       }
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
/*     */   private void checkValueType(CompositeData paramCompositeData) {
/* 862 */     if (paramCompositeData == null) {
/* 863 */       throw new NullPointerException("Argument value cannot be null.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 868 */     if (!this.tabularType.getRowType().isValue(paramCompositeData)) {
/* 869 */       throw new InvalidOpenTypeException("Argument value's composite type [" + paramCompositeData.getCompositeType() + "] is not assignable to this TabularData instance's row type [" + this.tabularType
/*     */           
/* 871 */           .getRowType() + "].");
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
/*     */   private List<?> checkValueAndIndex(CompositeData paramCompositeData) {
/* 891 */     checkValueType(paramCompositeData);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 896 */     List<?> list = internalCalculateIndex(paramCompositeData);
/*     */     
/* 898 */     if (this.dataMap.containsKey(list)) {
/* 899 */       throw new KeyAlreadyExistsException("Argument value's index, calculated according to this TabularData instance's tabularType, already refers to a value in this table.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 905 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 913 */     paramObjectInputStream.defaultReadObject();
/* 914 */     List<String> list = this.tabularType.getIndexNames();
/* 915 */     int i = list.size();
/* 916 */     SharedSecrets.getJavaOISAccess().checkArray(paramObjectInputStream, String[].class, i);
/* 917 */     this.indexNamesArray = list.<String>toArray(new String[i]);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/openmbean/TabularDataSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */