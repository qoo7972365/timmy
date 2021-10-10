/*    */ package com.sun.xml.internal.ws.binding;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.istack.internal.Nullable;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FeatureListUtil
/*    */ {
/*    */   @NotNull
/*    */   public static WebServiceFeatureList mergeList(WebServiceFeatureList... lists) {
/* 49 */     WebServiceFeatureList result = new WebServiceFeatureList();
/* 50 */     for (WebServiceFeatureList list : lists) {
/* 51 */       result.addAll((Iterable<WebServiceFeature>)list);
/*    */     }
/* 53 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static <F extends WebServiceFeature> F mergeFeature(@NotNull Class<F> featureType, @Nullable WebServiceFeatureList list1, @Nullable WebServiceFeatureList list2) throws WebServiceException {
/* 59 */     F feature1 = (list1 != null) ? list1.<F>get(featureType) : null;
/* 60 */     F feature2 = (list2 != null) ? list2.<F>get(featureType) : null;
/* 61 */     if (feature1 == null) {
/* 62 */       return feature2;
/*    */     }
/* 64 */     if (feature2 == null) {
/* 65 */       return feature1;
/*    */     }
/*    */     
/* 68 */     if (feature1.equals(feature2)) {
/* 69 */       return feature1;
/*    */     }
/*    */ 
/*    */     
/* 73 */     throw new WebServiceException((new StringBuilder()).append(feature1).append(", ").append(feature2).toString());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isFeatureEnabled(@NotNull Class<? extends WebServiceFeature> featureType, @Nullable WebServiceFeatureList list1, @Nullable WebServiceFeatureList list2) throws WebServiceException {
/* 81 */     WebServiceFeature mergedFeature = mergeFeature((Class)featureType, list1, list2);
/* 82 */     return (mergedFeature != null && mergedFeature.isEnabled());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/binding/FeatureListUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */