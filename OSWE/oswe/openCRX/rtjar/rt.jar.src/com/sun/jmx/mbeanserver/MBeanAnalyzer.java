/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MBeanAnalyzer<M>
/*     */ {
/*     */   void visit(MBeanVisitor<M> paramMBeanVisitor) {
/*  65 */     for (Map.Entry<String, AttrMethods<M>> entry : this.attrMap.entrySet()) {
/*  66 */       String str = (String)entry.getKey();
/*  67 */       AttrMethods attrMethods = (AttrMethods)entry.getValue();
/*  68 */       paramMBeanVisitor.visitAttribute(str, attrMethods.getter, attrMethods.setter);
/*     */     } 
/*     */ 
/*     */     
/*  72 */     for (Map.Entry<String, List<M>> entry : this.opMap.entrySet()) {
/*  73 */       for (M m : entry.getValue()) {
/*  74 */         paramMBeanVisitor.visitOperation((String)entry.getKey(), m);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*  79 */   private Map<String, List<M>> opMap = Util.newInsertionOrderMap();
/*     */   
/*  81 */   private Map<String, AttrMethods<M>> attrMap = Util.newInsertionOrderMap();
/*     */ 
/*     */   
/*     */   static interface MBeanVisitor<M>
/*     */   {
/*     */     void visitAttribute(String param1String, M param1M1, M param1M2);
/*     */ 
/*     */     
/*     */     void visitOperation(String param1String, M param1M);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class AttrMethods<M>
/*     */   {
/*     */     M getter;
/*     */     M setter;
/*     */     
/*     */     private AttrMethods() {}
/*     */   }
/*     */   
/*     */   static <M> MBeanAnalyzer<M> analyzer(Class<?> paramClass, MBeanIntrospector<M> paramMBeanIntrospector) throws NotCompliantMBeanException {
/* 102 */     return new MBeanAnalyzer<>(paramClass, paramMBeanIntrospector);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private MBeanAnalyzer(Class<?> paramClass, MBeanIntrospector<M> paramMBeanIntrospector) throws NotCompliantMBeanException {
/* 108 */     if (!paramClass.isInterface())
/* 109 */       throw new NotCompliantMBeanException("Not an interface: " + paramClass
/* 110 */           .getName()); 
/* 111 */     if (!Modifier.isPublic(paramClass.getModifiers()) && !Introspector.ALLOW_NONPUBLIC_MBEAN)
/*     */     {
/* 113 */       throw new NotCompliantMBeanException("Interface is not public: " + paramClass
/* 114 */           .getName());
/*     */     }
/*     */     
/*     */     try {
/* 118 */       initMaps(paramClass, paramMBeanIntrospector);
/* 119 */     } catch (Exception exception) {
/* 120 */       throw Introspector.throwException(paramClass, exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initMaps(Class<?> paramClass, MBeanIntrospector<M> paramMBeanIntrospector) throws Exception {
/* 128 */     List<Method> list1 = paramMBeanIntrospector.getMethods(paramClass);
/* 129 */     List<Method> list2 = eliminateCovariantMethods(list1);
/*     */ 
/*     */ 
/*     */     
/* 133 */     for (Method method : list2) {
/* 134 */       String str1 = method.getName();
/* 135 */       int i = (method.getParameterTypes()).length;
/*     */       
/* 137 */       M m = paramMBeanIntrospector.mFrom(method);
/*     */       
/* 139 */       String str2 = "";
/* 140 */       if (str1.startsWith("get")) {
/* 141 */         str2 = str1.substring(3);
/* 142 */       } else if (str1.startsWith("is") && method
/* 143 */         .getReturnType() == boolean.class) {
/* 144 */         str2 = str1.substring(2);
/*     */       } 
/* 146 */       if (str2.length() != 0 && i == 0 && method
/* 147 */         .getReturnType() != void.class) {
/*     */ 
/*     */         
/* 150 */         AttrMethods<M> attrMethods = this.attrMap.get(str2);
/* 151 */         if (attrMethods == null) {
/* 152 */           attrMethods = new AttrMethods();
/*     */         }
/* 154 */         else if (attrMethods.getter != null) {
/* 155 */           String str = "Attribute " + str2 + " has more than one getter";
/*     */           
/* 157 */           throw new NotCompliantMBeanException(str);
/*     */         } 
/*     */         
/* 160 */         attrMethods.getter = m;
/* 161 */         this.attrMap.put(str2, attrMethods); continue;
/* 162 */       }  if (str1.startsWith("set") && str1.length() > 3 && i == 1 && method
/*     */         
/* 164 */         .getReturnType() == void.class) {
/*     */         
/* 166 */         str2 = str1.substring(3);
/* 167 */         AttrMethods<M> attrMethods = this.attrMap.get(str2);
/* 168 */         if (attrMethods == null) {
/* 169 */           attrMethods = new AttrMethods();
/* 170 */         } else if (attrMethods.setter != null) {
/* 171 */           String str = "Attribute " + str2 + " has more than one setter";
/*     */           
/* 173 */           throw new NotCompliantMBeanException(str);
/*     */         } 
/* 175 */         attrMethods.setter = m;
/* 176 */         this.attrMap.put(str2, attrMethods);
/*     */         continue;
/*     */       } 
/* 179 */       List<?> list = this.opMap.get(str1);
/* 180 */       if (list == null)
/* 181 */         list = Util.newList(); 
/* 182 */       list.add(m);
/* 183 */       this.opMap.put(str1, list);
/*     */     } 
/*     */ 
/*     */     
/* 187 */     for (Map.Entry<String, AttrMethods<M>> entry : this.attrMap.entrySet()) {
/* 188 */       AttrMethods attrMethods = (AttrMethods)entry.getValue();
/* 189 */       if (!paramMBeanIntrospector.consistent(attrMethods.getter, attrMethods.setter)) {
/* 190 */         String str = "Getter and setter for " + (String)entry.getKey() + " have inconsistent types";
/*     */         
/* 192 */         throw new NotCompliantMBeanException(str);
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
/*     */   private static class MethodOrder
/*     */     implements Comparator<Method>
/*     */   {
/*     */     public int compare(Method param1Method1, Method param1Method2) {
/* 209 */       int i = param1Method1.getName().compareTo(param1Method2.getName());
/* 210 */       if (i != 0) return i; 
/* 211 */       Class[] arrayOfClass1 = param1Method1.getParameterTypes();
/* 212 */       Class[] arrayOfClass2 = param1Method2.getParameterTypes();
/* 213 */       if (arrayOfClass1.length != arrayOfClass2.length)
/* 214 */         return arrayOfClass1.length - arrayOfClass2.length; 
/* 215 */       if (!Arrays.equals((Object[])arrayOfClass1, (Object[])arrayOfClass2)) {
/* 216 */         return Arrays.toString((Object[])arrayOfClass1)
/* 217 */           .compareTo(Arrays.toString((Object[])arrayOfClass2));
/*     */       }
/* 219 */       Class<?> clazz1 = param1Method1.getReturnType();
/* 220 */       Class<?> clazz2 = param1Method2.getReturnType();
/* 221 */       if (clazz1 == clazz2) return 0;
/*     */ 
/*     */       
/* 224 */       if (clazz1.isAssignableFrom(clazz2))
/* 225 */         return -1; 
/* 226 */       return 1;
/*     */     }
/* 228 */     public static final MethodOrder instance = new MethodOrder();
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
/*     */   static List<Method> eliminateCovariantMethods(List<Method> paramList) {
/* 248 */     int i = paramList.size();
/* 249 */     Method[] arrayOfMethod = paramList.<Method>toArray(new Method[i]);
/* 250 */     Arrays.sort(arrayOfMethod, MethodOrder.instance);
/* 251 */     Set<?> set = Util.newSet();
/* 252 */     for (byte b = 1; b < i; b++) {
/* 253 */       Method method1 = arrayOfMethod[b - 1];
/* 254 */       Method method2 = arrayOfMethod[b];
/*     */ 
/*     */       
/* 257 */       if (method1.getName().equals(method2.getName()))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 262 */         if (Arrays.equals((Object[])method1.getParameterTypes(), (Object[])method2
/* 263 */             .getParameterTypes()))
/* 264 */           if (!set.add(method1)) {
/* 265 */             throw new RuntimeException("Internal error: duplicate Method");
/*     */           } 
/*     */       }
/*     */     } 
/* 269 */     List<Method> list = Util.newList(paramList);
/* 270 */     list.removeAll(set);
/* 271 */     return list;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/MBeanAnalyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */