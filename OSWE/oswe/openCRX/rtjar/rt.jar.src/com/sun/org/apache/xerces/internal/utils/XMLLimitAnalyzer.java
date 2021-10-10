/*     */ package com.sun.org.apache.xerces.internal.utils;
/*     */ 
/*     */ import java.util.Formatter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XMLLimitAnalyzer
/*     */ {
/*     */   private final int[] values;
/*     */   private final String[] names;
/*     */   private final int[] totalValue;
/*     */   private final Map[] caches;
/*     */   private String entityStart;
/*     */   private String entityEnd;
/*     */   
/*     */   public enum NameMap
/*     */   {
/*  60 */     ENTITY_EXPANSION_LIMIT("jdk.xml.entityExpansionLimit", "entityExpansionLimit"),
/*  61 */     MAX_OCCUR_NODE_LIMIT("jdk.xml.maxOccurLimit", "maxOccurLimit"),
/*  62 */     ELEMENT_ATTRIBUTE_LIMIT("jdk.xml.elementAttributeLimit", "elementAttributeLimit");
/*     */     
/*     */     final String newName;
/*     */     final String oldName;
/*     */     
/*     */     NameMap(String newName, String oldName) {
/*  68 */       this.newName = newName;
/*  69 */       this.oldName = oldName;
/*     */     }
/*     */     
/*     */     String getOldName(String newName) {
/*  73 */       if (newName.equals(this.newName)) {
/*  74 */         return this.oldName;
/*     */       }
/*  76 */       return null;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLLimitAnalyzer() {
/* 104 */     this.values = new int[(XMLSecurityManager.Limit.values()).length];
/* 105 */     this.totalValue = new int[(XMLSecurityManager.Limit.values()).length];
/* 106 */     this.names = new String[(XMLSecurityManager.Limit.values()).length];
/* 107 */     this.caches = new Map[(XMLSecurityManager.Limit.values()).length];
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
/*     */   public void addValue(XMLSecurityManager.Limit limit, String entityName, int value) {
/* 119 */     addValue(limit.ordinal(), entityName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addValue(int index, String entityName, int value) {
/*     */     Map<String, Integer> cache;
/* 129 */     if (index == XMLSecurityManager.Limit.ENTITY_EXPANSION_LIMIT.ordinal() || index == XMLSecurityManager.Limit.MAX_OCCUR_NODE_LIMIT
/* 130 */       .ordinal() || index == XMLSecurityManager.Limit.ELEMENT_ATTRIBUTE_LIMIT
/* 131 */       .ordinal() || index == XMLSecurityManager.Limit.TOTAL_ENTITY_SIZE_LIMIT
/* 132 */       .ordinal() || index == XMLSecurityManager.Limit.ENTITY_REPLACEMENT_LIMIT
/* 133 */       .ordinal()) {
/*     */       
/* 135 */       this.totalValue[index] = this.totalValue[index] + value;
/*     */       return;
/*     */     } 
/* 138 */     if (index == XMLSecurityManager.Limit.MAX_ELEMENT_DEPTH_LIMIT.ordinal() || index == XMLSecurityManager.Limit.MAX_NAME_LIMIT
/* 139 */       .ordinal()) {
/* 140 */       this.values[index] = value;
/* 141 */       this.totalValue[index] = value;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 146 */     if (this.caches[index] == null) {
/* 147 */       cache = new HashMap<>(10);
/* 148 */       this.caches[index] = cache;
/*     */     } else {
/* 150 */       cache = this.caches[index];
/*     */     } 
/*     */     
/* 153 */     int accumulatedValue = value;
/* 154 */     if (cache.containsKey(entityName)) {
/* 155 */       accumulatedValue += ((Integer)cache.get(entityName)).intValue();
/* 156 */       cache.put(entityName, Integer.valueOf(accumulatedValue));
/*     */     } else {
/* 158 */       cache.put(entityName, Integer.valueOf(value));
/*     */     } 
/*     */     
/* 161 */     if (accumulatedValue > this.values[index]) {
/* 162 */       this.values[index] = accumulatedValue;
/* 163 */       this.names[index] = entityName;
/*     */     } 
/*     */ 
/*     */     
/* 167 */     if (index == XMLSecurityManager.Limit.GENERAL_ENTITY_SIZE_LIMIT.ordinal() || index == XMLSecurityManager.Limit.PARAMETER_ENTITY_SIZE_LIMIT
/* 168 */       .ordinal()) {
/* 169 */       this.totalValue[XMLSecurityManager.Limit.TOTAL_ENTITY_SIZE_LIMIT.ordinal()] = this.totalValue[XMLSecurityManager.Limit.TOTAL_ENTITY_SIZE_LIMIT.ordinal()] + value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValue(XMLSecurityManager.Limit limit) {
/* 180 */     return getValue(limit.ordinal());
/*     */   }
/*     */   
/*     */   public int getValue(int index) {
/* 184 */     if (index == XMLSecurityManager.Limit.ENTITY_REPLACEMENT_LIMIT.ordinal()) {
/* 185 */       return this.totalValue[index];
/*     */     }
/* 187 */     return this.values[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalValue(XMLSecurityManager.Limit limit) {
/* 196 */     return this.totalValue[limit.ordinal()];
/*     */   }
/*     */   
/*     */   public int getTotalValue(int index) {
/* 200 */     return this.totalValue[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValueByIndex(int index) {
/* 208 */     return this.values[index];
/*     */   }
/*     */   
/*     */   public void startEntity(String name) {
/* 212 */     this.entityStart = name;
/*     */   }
/*     */   
/*     */   public boolean isTracking(String name) {
/* 216 */     if (this.entityStart == null) {
/* 217 */       return false;
/*     */     }
/* 219 */     return this.entityStart.equals(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEntity(XMLSecurityManager.Limit limit, String name) {
/* 227 */     this.entityStart = "";
/* 228 */     Map<String, Integer> cache = this.caches[limit.ordinal()];
/* 229 */     if (cache != null) {
/* 230 */       cache.remove(name);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset(XMLSecurityManager.Limit limit) {
/* 239 */     if (limit.ordinal() == XMLSecurityManager.Limit.TOTAL_ENTITY_SIZE_LIMIT.ordinal()) {
/* 240 */       this.totalValue[limit.ordinal()] = 0;
/* 241 */     } else if (limit.ordinal() == XMLSecurityManager.Limit.GENERAL_ENTITY_SIZE_LIMIT.ordinal()) {
/* 242 */       this.names[limit.ordinal()] = null;
/* 243 */       this.values[limit.ordinal()] = 0;
/* 244 */       this.caches[limit.ordinal()] = null;
/* 245 */       this.totalValue[limit.ordinal()] = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void debugPrint(XMLSecurityManager securityManager) {
/* 250 */     Formatter formatter = new Formatter();
/* 251 */     System.out.println(formatter.format("%30s %15s %15s %15s %30s", new Object[] { "Property", "Limit", "Total size", "Size", "Entity Name" }));
/*     */ 
/*     */     
/* 254 */     for (XMLSecurityManager.Limit limit : XMLSecurityManager.Limit.values()) {
/* 255 */       formatter = new Formatter();
/* 256 */       System.out.println(formatter.format("%30s %15d %15d %15d %30s", new Object[] { limit
/* 257 */               .name(), 
/* 258 */               Integer.valueOf(securityManager.getLimit(limit)), 
/* 259 */               Integer.valueOf(this.totalValue[limit.ordinal()]), 
/* 260 */               Integer.valueOf(this.values[limit.ordinal()]), this.names[limit
/* 261 */                 .ordinal()] }));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/utils/XMLLimitAnalyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */