/*    */ package com.sun.xml.internal.ws.api;
/*    */ 
/*    */ import com.sun.xml.internal.ws.binding.WebServiceFeatureList;
/*    */ import java.lang.annotation.Annotation;
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
/*    */ 
/*    */ 
/*    */ public class WebServiceFeatureFactory
/*    */ {
/*    */   public static WSFeatureList getWSFeatureList(Iterable<Annotation> ann) {
/* 49 */     WebServiceFeatureList list = new WebServiceFeatureList();
/* 50 */     list.parseAnnotations(ann);
/* 51 */     return (WSFeatureList)list;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static WebServiceFeature getWebServiceFeature(Annotation ann) {
/* 63 */     return WebServiceFeatureList.getFeature(ann);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/WebServiceFeatureFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */