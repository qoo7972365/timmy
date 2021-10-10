/*    */ package com.sun.corba.se.spi.orb;
/*    */ 
/*    */ import com.sun.corba.se.impl.orb.ParserAction;
/*    */ import com.sun.corba.se.impl.orb.ParserActionFactory;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Properties;
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
/*    */ public class PropertyParser
/*    */ {
/* 42 */   private List actions = new LinkedList();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PropertyParser add(String paramString1, Operation paramOperation, String paramString2) {
/* 48 */     this.actions.add(ParserActionFactory.makeNormalAction(paramString1, paramOperation, paramString2));
/*    */     
/* 50 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public PropertyParser addPrefix(String paramString1, Operation paramOperation, String paramString2, Class paramClass) {
/* 56 */     this.actions.add(ParserActionFactory.makePrefixAction(paramString1, paramOperation, paramString2, paramClass));
/*    */     
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map parse(Properties paramProperties) {
/* 65 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 66 */     Iterator<ParserAction> iterator = this.actions.iterator();
/* 67 */     while (iterator.hasNext()) {
/* 68 */       ParserAction parserAction = iterator.next();
/*    */       
/* 70 */       Object object = parserAction.apply(paramProperties);
/*    */ 
/*    */ 
/*    */       
/* 74 */       if (object != null) {
/* 75 */         hashMap.put(parserAction.getFieldName(), object);
/*    */       }
/*    */     } 
/* 78 */     return hashMap;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator iterator() {
/* 83 */     return this.actions.iterator();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orb/PropertyParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */