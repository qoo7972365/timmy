/*     */ package javax.lang.model.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ElementKind;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.PackageElement;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ElementFilter
/*     */ {
/*  70 */   private static final Set<ElementKind> CONSTRUCTOR_KIND = Collections.unmodifiableSet(EnumSet.of(ElementKind.CONSTRUCTOR));
/*     */ 
/*     */   
/*  73 */   private static final Set<ElementKind> FIELD_KINDS = Collections.unmodifiableSet(EnumSet.of(ElementKind.FIELD, ElementKind.ENUM_CONSTANT));
/*     */ 
/*     */   
/*  76 */   private static final Set<ElementKind> METHOD_KIND = Collections.unmodifiableSet(EnumSet.of(ElementKind.METHOD));
/*     */ 
/*     */   
/*  79 */   private static final Set<ElementKind> PACKAGE_KIND = Collections.unmodifiableSet(EnumSet.of(ElementKind.PACKAGE));
/*     */ 
/*     */   
/*  82 */   private static final Set<ElementKind> TYPE_KINDS = Collections.unmodifiableSet(EnumSet.of(ElementKind.CLASS, ElementKind.ENUM, ElementKind.INTERFACE, ElementKind.ANNOTATION_TYPE));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<VariableElement> fieldsIn(Iterable<? extends Element> paramIterable) {
/*  93 */     return listFilter(paramIterable, FIELD_KINDS, VariableElement.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<VariableElement> fieldsIn(Set<? extends Element> paramSet) {
/* 103 */     return setFilter(paramSet, FIELD_KINDS, VariableElement.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ExecutableElement> constructorsIn(Iterable<? extends Element> paramIterable) {
/* 113 */     return listFilter(paramIterable, CONSTRUCTOR_KIND, ExecutableElement.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<ExecutableElement> constructorsIn(Set<? extends Element> paramSet) {
/* 123 */     return setFilter(paramSet, CONSTRUCTOR_KIND, ExecutableElement.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ExecutableElement> methodsIn(Iterable<? extends Element> paramIterable) {
/* 133 */     return listFilter(paramIterable, METHOD_KIND, ExecutableElement.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<ExecutableElement> methodsIn(Set<? extends Element> paramSet) {
/* 143 */     return setFilter(paramSet, METHOD_KIND, ExecutableElement.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<TypeElement> typesIn(Iterable<? extends Element> paramIterable) {
/* 153 */     return listFilter(paramIterable, TYPE_KINDS, TypeElement.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<TypeElement> typesIn(Set<? extends Element> paramSet) {
/* 163 */     return setFilter(paramSet, TYPE_KINDS, TypeElement.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<PackageElement> packagesIn(Iterable<? extends Element> paramIterable) {
/* 173 */     return listFilter(paramIterable, PACKAGE_KIND, PackageElement.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<PackageElement> packagesIn(Set<? extends Element> paramSet) {
/* 183 */     return setFilter(paramSet, PACKAGE_KIND, PackageElement.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E extends Element> List<E> listFilter(Iterable<? extends Element> paramIterable, Set<ElementKind> paramSet, Class<E> paramClass) {
/* 190 */     ArrayList<E> arrayList = new ArrayList();
/* 191 */     for (Element element : paramIterable) {
/* 192 */       if (paramSet.contains(element.getKind()))
/* 193 */         arrayList.add(paramClass.cast(element)); 
/*     */     } 
/* 195 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E extends Element> Set<E> setFilter(Set<? extends Element> paramSet, Set<ElementKind> paramSet1, Class<E> paramClass) {
/* 203 */     LinkedHashSet<E> linkedHashSet = new LinkedHashSet();
/* 204 */     for (Element element : paramSet) {
/* 205 */       if (paramSet1.contains(element.getKind()))
/* 206 */         linkedHashSet.add(paramClass.cast(element)); 
/*     */     } 
/* 208 */     return linkedHashSet;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/util/ElementFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */