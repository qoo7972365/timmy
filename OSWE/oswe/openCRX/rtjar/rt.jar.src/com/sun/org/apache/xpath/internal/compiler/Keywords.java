/*     */ package com.sun.org.apache.xpath.internal.compiler;
/*     */ 
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Keywords
/*     */ {
/*     */   private static final Map<String, Integer> m_keywords;
/*     */   private static final Map<String, Integer> m_axisnames;
/*     */   private static final Map<String, Integer> m_nodetests;
/*     */   private static final Map<String, Integer> m_nodetypes;
/*     */   private static final String FROM_ANCESTORS_STRING = "ancestor";
/*     */   private static final String FROM_ANCESTORS_OR_SELF_STRING = "ancestor-or-self";
/*     */   private static final String FROM_ATTRIBUTES_STRING = "attribute";
/*     */   private static final String FROM_CHILDREN_STRING = "child";
/*     */   private static final String FROM_DESCENDANTS_STRING = "descendant";
/*     */   private static final String FROM_DESCENDANTS_OR_SELF_STRING = "descendant-or-self";
/*     */   private static final String FROM_FOLLOWING_STRING = "following";
/*     */   private static final String FROM_FOLLOWING_SIBLINGS_STRING = "following-sibling";
/*     */   private static final String FROM_PARENT_STRING = "parent";
/*     */   private static final String FROM_PRECEDING_STRING = "preceding";
/*     */   private static final String FROM_PRECEDING_SIBLINGS_STRING = "preceding-sibling";
/*     */   private static final String FROM_SELF_STRING = "self";
/*     */   private static final String FROM_NAMESPACE_STRING = "namespace";
/*     */   private static final String FROM_SELF_ABBREVIATED_STRING = ".";
/*     */   private static final String NODETYPE_COMMENT_STRING = "comment";
/*     */   private static final String NODETYPE_TEXT_STRING = "text";
/*     */   private static final String NODETYPE_PI_STRING = "processing-instruction";
/*     */   private static final String NODETYPE_NODE_STRING = "node";
/*     */   private static final String NODETYPE_ANYELEMENT_STRING = "*";
/*     */   public static final String FUNC_CURRENT_STRING = "current";
/*     */   public static final String FUNC_LAST_STRING = "last";
/*     */   public static final String FUNC_POSITION_STRING = "position";
/*     */   public static final String FUNC_COUNT_STRING = "count";
/*     */   static final String FUNC_ID_STRING = "id";
/*     */   public static final String FUNC_KEY_STRING = "key";
/*     */   public static final String FUNC_LOCAL_PART_STRING = "local-name";
/*     */   public static final String FUNC_NAMESPACE_STRING = "namespace-uri";
/*     */   public static final String FUNC_NAME_STRING = "name";
/*     */   public static final String FUNC_GENERATE_ID_STRING = "generate-id";
/*     */   public static final String FUNC_NOT_STRING = "not";
/*     */   public static final String FUNC_TRUE_STRING = "true";
/*     */   public static final String FUNC_FALSE_STRING = "false";
/*     */   public static final String FUNC_BOOLEAN_STRING = "boolean";
/*     */   public static final String FUNC_LANG_STRING = "lang";
/*     */   public static final String FUNC_NUMBER_STRING = "number";
/*     */   public static final String FUNC_FLOOR_STRING = "floor";
/*     */   public static final String FUNC_CEILING_STRING = "ceiling";
/*     */   public static final String FUNC_ROUND_STRING = "round";
/*     */   public static final String FUNC_SUM_STRING = "sum";
/*     */   public static final String FUNC_STRING_STRING = "string";
/*     */   public static final String FUNC_STARTS_WITH_STRING = "starts-with";
/*     */   public static final String FUNC_CONTAINS_STRING = "contains";
/*     */   public static final String FUNC_SUBSTRING_BEFORE_STRING = "substring-before";
/*     */   public static final String FUNC_SUBSTRING_AFTER_STRING = "substring-after";
/*     */   public static final String FUNC_NORMALIZE_SPACE_STRING = "normalize-space";
/*     */   public static final String FUNC_TRANSLATE_STRING = "translate";
/*     */   public static final String FUNC_CONCAT_STRING = "concat";
/*     */   public static final String FUNC_SYSTEM_PROPERTY_STRING = "system-property";
/*     */   public static final String FUNC_EXT_FUNCTION_AVAILABLE_STRING = "function-available";
/*     */   public static final String FUNC_EXT_ELEM_AVAILABLE_STRING = "element-available";
/*     */   public static final String FUNC_SUBSTRING_STRING = "substring";
/*     */   public static final String FUNC_STRING_LENGTH_STRING = "string-length";
/*     */   public static final String FUNC_UNPARSED_ENTITY_URI_STRING = "unparsed-entity-uri";
/*     */   public static final String FUNC_DOCLOCATION_STRING = "document-location";
/*     */   
/*     */   static {
/* 336 */     Map<String, Integer> keywords = new HashMap<>();
/* 337 */     Map<String, Integer> axisnames = new HashMap<>();
/* 338 */     Map<String, Integer> nodetests = new HashMap<>();
/* 339 */     Map<String, Integer> nodetypes = new HashMap<>();
/*     */     
/* 341 */     axisnames.put("ancestor", Integer.valueOf(37));
/* 342 */     axisnames.put("ancestor-or-self", Integer.valueOf(38));
/* 343 */     axisnames.put("attribute", Integer.valueOf(39));
/* 344 */     axisnames.put("child", Integer.valueOf(40));
/* 345 */     axisnames.put("descendant", Integer.valueOf(41));
/* 346 */     axisnames.put("descendant-or-self", Integer.valueOf(42));
/* 347 */     axisnames.put("following", Integer.valueOf(43));
/* 348 */     axisnames.put("following-sibling", Integer.valueOf(44));
/* 349 */     axisnames.put("parent", Integer.valueOf(45));
/* 350 */     axisnames.put("preceding", Integer.valueOf(46));
/* 351 */     axisnames.put("preceding-sibling", Integer.valueOf(47));
/* 352 */     axisnames.put("self", Integer.valueOf(48));
/* 353 */     axisnames.put("namespace", Integer.valueOf(49));
/* 354 */     m_axisnames = Collections.unmodifiableMap(axisnames);
/*     */     
/* 356 */     nodetypes.put("comment", Integer.valueOf(1030));
/* 357 */     nodetypes.put("text", Integer.valueOf(1031));
/* 358 */     nodetypes.put("processing-instruction", Integer.valueOf(1032));
/* 359 */     nodetypes.put("node", Integer.valueOf(1033));
/* 360 */     nodetypes.put("*", Integer.valueOf(36));
/* 361 */     m_nodetypes = Collections.unmodifiableMap(nodetypes);
/*     */     
/* 363 */     keywords.put(".", Integer.valueOf(48));
/* 364 */     keywords.put("id", Integer.valueOf(4));
/* 365 */     keywords.put("key", Integer.valueOf(5));
/* 366 */     m_keywords = Collections.unmodifiableMap(keywords);
/*     */     
/* 368 */     nodetests.put("comment", Integer.valueOf(1030));
/* 369 */     nodetests.put("text", Integer.valueOf(1031));
/* 370 */     nodetests.put("processing-instruction", Integer.valueOf(1032));
/* 371 */     nodetests.put("node", Integer.valueOf(1033));
/* 372 */     m_nodetests = Collections.unmodifiableMap(nodetests);
/*     */   }
/*     */   
/*     */   static Integer getAxisName(String key) {
/* 376 */     return m_axisnames.get(key);
/*     */   }
/*     */   
/*     */   static Integer lookupNodeTest(String key) {
/* 380 */     return m_nodetests.get(key);
/*     */   }
/*     */   
/*     */   static Integer getKeyWord(String key) {
/* 384 */     return m_keywords.get(key);
/*     */   }
/*     */   
/*     */   static Integer getNodeType(String key) {
/* 388 */     return m_nodetypes.get(key);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/compiler/Keywords.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */