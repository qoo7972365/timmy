/*      */ package com.sun.jndi.ldap;
/*      */ 
/*      */ import java.util.Vector;
/*      */ import javax.naming.ConfigurationException;
/*      */ import javax.naming.NamingEnumeration;
/*      */ import javax.naming.NamingException;
/*      */ import javax.naming.directory.Attribute;
/*      */ import javax.naming.directory.Attributes;
/*      */ import javax.naming.directory.BasicAttribute;
/*      */ import javax.naming.directory.BasicAttributes;
/*      */ import javax.naming.directory.DirContext;
/*      */ import javax.naming.directory.InvalidAttributeIdentifierException;
/*      */ import javax.naming.directory.InvalidAttributeValueException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class LdapSchemaParser
/*      */ {
/*      */   private static final boolean debug = false;
/*      */   static final String OBJECTCLASSDESC_ATTR_ID = "objectClasses";
/*      */   static final String ATTRIBUTEDESC_ATTR_ID = "attributeTypes";
/*      */   static final String SYNTAXDESC_ATTR_ID = "ldapSyntaxes";
/*      */   static final String MATCHRULEDESC_ATTR_ID = "matchingRules";
/*      */   static final String OBJECTCLASS_DEFINITION_NAME = "ClassDefinition";
/*   54 */   private static final String[] CLASS_DEF_ATTRS = new String[] { "objectclass", "ClassDefinition" };
/*      */   
/*      */   static final String ATTRIBUTE_DEFINITION_NAME = "AttributeDefinition";
/*      */   
/*   58 */   private static final String[] ATTR_DEF_ATTRS = new String[] { "objectclass", "AttributeDefinition" };
/*      */   
/*      */   static final String SYNTAX_DEFINITION_NAME = "SyntaxDefinition";
/*      */   
/*   62 */   private static final String[] SYNTAX_DEF_ATTRS = new String[] { "objectclass", "SyntaxDefinition" };
/*      */   
/*      */   static final String MATCHRULE_DEFINITION_NAME = "MatchingRule";
/*      */   
/*   66 */   private static final String[] MATCHRULE_DEF_ATTRS = new String[] { "objectclass", "MatchingRule" };
/*      */   
/*      */   private static final char SINGLE_QUOTE = '\'';
/*      */   
/*      */   private static final char WHSP = ' ';
/*      */   
/*      */   private static final char OID_LIST_BEGIN = '(';
/*      */   
/*      */   private static final char OID_LIST_END = ')';
/*      */   
/*      */   private static final char OID_SEPARATOR = '$';
/*      */   
/*      */   private static final String NUMERICOID_ID = "NUMERICOID";
/*      */   
/*      */   private static final String NAME_ID = "NAME";
/*      */   
/*      */   private static final String DESC_ID = "DESC";
/*      */   
/*      */   private static final String OBSOLETE_ID = "OBSOLETE";
/*      */   
/*      */   private static final String SUP_ID = "SUP";
/*      */   
/*      */   private static final String PRIVATE_ID = "X-";
/*      */   
/*      */   private static final String ABSTRACT_ID = "ABSTRACT";
/*      */   
/*      */   private static final String STRUCTURAL_ID = "STRUCTURAL";
/*      */   private static final String AUXILARY_ID = "AUXILIARY";
/*      */   private static final String MUST_ID = "MUST";
/*      */   private static final String MAY_ID = "MAY";
/*      */   private static final String EQUALITY_ID = "EQUALITY";
/*      */   private static final String ORDERING_ID = "ORDERING";
/*      */   private static final String SUBSTR_ID = "SUBSTR";
/*      */   private static final String SYNTAX_ID = "SYNTAX";
/*      */   private static final String SINGLE_VAL_ID = "SINGLE-VALUE";
/*      */   private static final String COLLECTIVE_ID = "COLLECTIVE";
/*      */   private static final String NO_USER_MOD_ID = "NO-USER-MODIFICATION";
/*      */   private static final String USAGE_ID = "USAGE";
/*      */   private static final String SCHEMA_TRUE_VALUE = "true";
/*      */   private boolean netscapeBug;
/*      */   
/*      */   LdapSchemaParser(boolean paramBoolean) {
/*  108 */     this.netscapeBug = paramBoolean;
/*      */   }
/*      */ 
/*      */   
/*      */   static final void LDAP2JNDISchema(Attributes paramAttributes, LdapSchemaCtx paramLdapSchemaCtx) throws NamingException {
/*  113 */     Attribute attribute1 = null;
/*  114 */     Attribute attribute2 = null;
/*  115 */     Attribute attribute3 = null;
/*  116 */     Attribute attribute4 = null;
/*      */     
/*  118 */     attribute1 = paramAttributes.get("objectClasses");
/*  119 */     if (attribute1 != null) {
/*  120 */       objectDescs2ClassDefs(attribute1, paramLdapSchemaCtx);
/*      */     }
/*      */     
/*  123 */     attribute2 = paramAttributes.get("attributeTypes");
/*  124 */     if (attribute2 != null) {
/*  125 */       attrDescs2AttrDefs(attribute2, paramLdapSchemaCtx);
/*      */     }
/*      */     
/*  128 */     attribute3 = paramAttributes.get("ldapSyntaxes");
/*  129 */     if (attribute3 != null) {
/*  130 */       syntaxDescs2SyntaxDefs(attribute3, paramLdapSchemaCtx);
/*      */     }
/*      */     
/*  133 */     attribute4 = paramAttributes.get("matchingRules");
/*  134 */     if (attribute4 != null) {
/*  135 */       matchRuleDescs2MatchRuleDefs(attribute4, paramLdapSchemaCtx);
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
/*      */   private static final DirContext objectDescs2ClassDefs(Attribute paramAttribute, LdapSchemaCtx paramLdapSchemaCtx) throws NamingException {
/*  148 */     BasicAttributes basicAttributes = new BasicAttributes(true);
/*  149 */     basicAttributes.put(CLASS_DEF_ATTRS[0], CLASS_DEF_ATTRS[1]);
/*  150 */     LdapSchemaCtx ldapSchemaCtx = paramLdapSchemaCtx.setup(2, "ClassDefinition", basicAttributes);
/*      */ 
/*      */     
/*  153 */     NamingEnumeration<?> namingEnumeration = paramAttribute.getAll();
/*      */     
/*  155 */     while (namingEnumeration.hasMore()) {
/*  156 */       String str = (String)namingEnumeration.next();
/*      */       try {
/*  158 */         Object[] arrayOfObject = desc2Def(str);
/*  159 */         String str1 = (String)arrayOfObject[0];
/*  160 */         Attributes attributes = (Attributes)arrayOfObject[1];
/*  161 */         ldapSchemaCtx.setup(6, str1, attributes);
/*      */       }
/*  163 */       catch (NamingException namingException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  168 */     return ldapSchemaCtx;
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
/*      */   private static final DirContext attrDescs2AttrDefs(Attribute paramAttribute, LdapSchemaCtx paramLdapSchemaCtx) throws NamingException {
/*  180 */     BasicAttributes basicAttributes = new BasicAttributes(true);
/*  181 */     basicAttributes.put(ATTR_DEF_ATTRS[0], ATTR_DEF_ATTRS[1]);
/*  182 */     LdapSchemaCtx ldapSchemaCtx = paramLdapSchemaCtx.setup(3, "AttributeDefinition", basicAttributes);
/*      */ 
/*      */     
/*  185 */     NamingEnumeration<?> namingEnumeration = paramAttribute.getAll();
/*      */     
/*  187 */     while (namingEnumeration.hasMore()) {
/*  188 */       String str = (String)namingEnumeration.next();
/*      */       try {
/*  190 */         Object[] arrayOfObject = desc2Def(str);
/*  191 */         String str1 = (String)arrayOfObject[0];
/*  192 */         Attributes attributes = (Attributes)arrayOfObject[1];
/*  193 */         ldapSchemaCtx.setup(7, str1, attributes);
/*      */       }
/*  195 */       catch (NamingException namingException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  200 */     return ldapSchemaCtx;
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
/*      */   private static final DirContext syntaxDescs2SyntaxDefs(Attribute paramAttribute, LdapSchemaCtx paramLdapSchemaCtx) throws NamingException {
/*  213 */     BasicAttributes basicAttributes = new BasicAttributes(true);
/*  214 */     basicAttributes.put(SYNTAX_DEF_ATTRS[0], SYNTAX_DEF_ATTRS[1]);
/*  215 */     LdapSchemaCtx ldapSchemaCtx = paramLdapSchemaCtx.setup(4, "SyntaxDefinition", basicAttributes);
/*      */ 
/*      */     
/*  218 */     NamingEnumeration<?> namingEnumeration = paramAttribute.getAll();
/*      */     
/*  220 */     while (namingEnumeration.hasMore()) {
/*  221 */       String str = (String)namingEnumeration.next();
/*      */       try {
/*  223 */         Object[] arrayOfObject = desc2Def(str);
/*  224 */         String str1 = (String)arrayOfObject[0];
/*  225 */         Attributes attributes = (Attributes)arrayOfObject[1];
/*  226 */         ldapSchemaCtx.setup(8, str1, attributes);
/*      */       }
/*  228 */       catch (NamingException namingException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  233 */     return ldapSchemaCtx;
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
/*      */   private static final DirContext matchRuleDescs2MatchRuleDefs(Attribute paramAttribute, LdapSchemaCtx paramLdapSchemaCtx) throws NamingException {
/*  246 */     BasicAttributes basicAttributes = new BasicAttributes(true);
/*  247 */     basicAttributes.put(MATCHRULE_DEF_ATTRS[0], MATCHRULE_DEF_ATTRS[1]);
/*  248 */     LdapSchemaCtx ldapSchemaCtx = paramLdapSchemaCtx.setup(5, "MatchingRule", basicAttributes);
/*      */ 
/*      */     
/*  251 */     NamingEnumeration<?> namingEnumeration = paramAttribute.getAll();
/*      */     
/*  253 */     while (namingEnumeration.hasMore()) {
/*  254 */       String str = (String)namingEnumeration.next();
/*      */       try {
/*  256 */         Object[] arrayOfObject = desc2Def(str);
/*  257 */         String str1 = (String)arrayOfObject[0];
/*  258 */         Attributes attributes = (Attributes)arrayOfObject[1];
/*  259 */         ldapSchemaCtx.setup(9, str1, attributes);
/*      */       }
/*  261 */       catch (NamingException namingException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  266 */     return ldapSchemaCtx;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Object[] desc2Def(String paramString) throws NamingException {
/*  273 */     BasicAttributes basicAttributes = new BasicAttributes(true);
/*  274 */     Attribute attribute = null;
/*  275 */     int[] arrayOfInt = { 1 };
/*  276 */     boolean bool = true;
/*      */ 
/*      */     
/*  279 */     attribute = readNumericOID(paramString, arrayOfInt);
/*  280 */     String str = (String)attribute.get(0);
/*  281 */     basicAttributes.put(attribute);
/*      */     
/*  283 */     skipWhitespace(paramString, arrayOfInt);
/*      */     
/*  285 */     while (bool) {
/*  286 */       attribute = readNextTag(paramString, arrayOfInt);
/*  287 */       basicAttributes.put(attribute);
/*      */       
/*  289 */       if (attribute.getID().equals("NAME")) {
/*  290 */         str = (String)attribute.get(0);
/*      */       }
/*      */       
/*  293 */       skipWhitespace(paramString, arrayOfInt);
/*      */       
/*  295 */       if (arrayOfInt[0] >= paramString.length() - 1) {
/*  296 */         bool = false;
/*      */       }
/*      */     } 
/*      */     
/*  300 */     return new Object[] { str, basicAttributes };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int findTrailingWhitespace(String paramString, int paramInt) {
/*  306 */     for (int i = paramInt; i > 0; i--) {
/*  307 */       if (paramString.charAt(i) != ' ') {
/*  308 */         return i + 1;
/*      */       }
/*      */     } 
/*  311 */     return 0;
/*      */   }
/*      */   
/*      */   private static final void skipWhitespace(String paramString, int[] paramArrayOfint) {
/*  315 */     for (int i = paramArrayOfint[0]; i < paramString.length(); i++) {
/*  316 */       if (paramString.charAt(i) != ' ') {
/*  317 */         paramArrayOfint[0] = i;
/*      */         return;
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
/*      */   
/*      */   private static final Attribute readNumericOID(String paramString, int[] paramArrayOfint) throws NamingException {
/*  334 */     String str = null;
/*      */     
/*  336 */     skipWhitespace(paramString, paramArrayOfint);
/*      */     
/*  338 */     int i = paramArrayOfint[0];
/*  339 */     int j = paramString.indexOf(' ', i);
/*      */     
/*  341 */     if (j == -1 || j - i < 1) {
/*  342 */       throw new InvalidAttributeValueException("no numericoid found: " + paramString);
/*      */     }
/*      */ 
/*      */     
/*  346 */     str = paramString.substring(i, j);
/*      */     
/*  348 */     paramArrayOfint[0] = paramArrayOfint[0] + str.length();
/*      */     
/*  350 */     return new BasicAttribute("NUMERICOID", str);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Attribute readNextTag(String paramString, int[] paramArrayOfint) throws NamingException {
/*  356 */     BasicAttribute basicAttribute = null;
/*  357 */     String str = null;
/*  358 */     String[] arrayOfString = null;
/*      */     
/*  360 */     skipWhitespace(paramString, paramArrayOfint);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  367 */     int i = paramString.indexOf(' ', paramArrayOfint[0]);
/*      */ 
/*      */     
/*  370 */     if (i < 0) {
/*  371 */       str = paramString.substring(paramArrayOfint[0], paramString.length() - 1);
/*      */     } else {
/*  373 */       str = paramString.substring(paramArrayOfint[0], i);
/*      */     } 
/*      */     
/*  376 */     arrayOfString = readTag(str, paramString, paramArrayOfint);
/*      */ 
/*      */     
/*  379 */     if (arrayOfString.length < 0) {
/*  380 */       throw new InvalidAttributeValueException("no values for attribute \"" + str + "\"");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  386 */     basicAttribute = new BasicAttribute(str, arrayOfString[0]);
/*      */ 
/*      */     
/*  389 */     for (byte b = 1; b < arrayOfString.length; b++) {
/*  390 */       basicAttribute.add(arrayOfString[b]);
/*      */     }
/*      */     
/*  393 */     return basicAttribute;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String[] readTag(String paramString1, String paramString2, int[] paramArrayOfint) throws NamingException {
/*  404 */     paramArrayOfint[0] = paramArrayOfint[0] + paramString1.length();
/*  405 */     skipWhitespace(paramString2, paramArrayOfint);
/*      */     
/*  407 */     if (paramString1.equals("NAME")) {
/*  408 */       return readQDescrs(paramString2, paramArrayOfint);
/*      */     }
/*      */     
/*  411 */     if (paramString1.equals("DESC")) {
/*  412 */       return readQDString(paramString2, paramArrayOfint);
/*      */     }
/*      */     
/*  415 */     if (paramString1
/*  416 */       .equals("EQUALITY") || paramString1
/*  417 */       .equals("ORDERING") || paramString1
/*  418 */       .equals("SUBSTR") || paramString1
/*  419 */       .equals("SYNTAX")) {
/*  420 */       return readWOID(paramString2, paramArrayOfint);
/*      */     }
/*      */     
/*  423 */     if (paramString1.equals("OBSOLETE") || paramString1
/*  424 */       .equals("ABSTRACT") || paramString1
/*  425 */       .equals("STRUCTURAL") || paramString1
/*  426 */       .equals("AUXILIARY") || paramString1
/*  427 */       .equals("SINGLE-VALUE") || paramString1
/*  428 */       .equals("COLLECTIVE") || paramString1
/*  429 */       .equals("NO-USER-MODIFICATION")) {
/*  430 */       return new String[] { "true" };
/*      */     }
/*      */     
/*  433 */     if (paramString1.equals("SUP") || paramString1
/*  434 */       .equals("MUST") || paramString1
/*  435 */       .equals("MAY") || paramString1
/*  436 */       .equals("USAGE")) {
/*  437 */       return readOIDs(paramString2, paramArrayOfint);
/*      */     }
/*      */ 
/*      */     
/*  441 */     return readQDStrings(paramString2, paramArrayOfint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String[] readQDString(String paramString, int[] paramArrayOfint) throws NamingException {
/*  449 */     int i = paramString.indexOf('\'', paramArrayOfint[0]) + 1;
/*  450 */     int j = paramString.indexOf('\'', i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  457 */     if (i == -1 || j == -1 || i == j) {
/*  458 */       throw new InvalidAttributeIdentifierException("malformed QDString: " + paramString);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  464 */     if (paramString.charAt(i - 1) != '\'') {
/*  465 */       throw new InvalidAttributeIdentifierException("qdstring has no end mark: " + paramString);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  470 */     paramArrayOfint[0] = j + 1;
/*  471 */     return new String[] { paramString.substring(i, j) };
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
/*      */   private static final String[] readQDStrings(String paramString, int[] paramArrayOfint) throws NamingException {
/*  483 */     return readQDescrs(paramString, paramArrayOfint);
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
/*      */   private static final String[] readQDescrs(String paramString, int[] paramArrayOfint) throws NamingException {
/*  500 */     skipWhitespace(paramString, paramArrayOfint);
/*      */     
/*  502 */     switch (paramString.charAt(paramArrayOfint[0])) {
/*      */       case '(':
/*  504 */         return readQDescrList(paramString, paramArrayOfint);
/*      */       case '\'':
/*  506 */         return readQDString(paramString, paramArrayOfint);
/*      */     } 
/*  508 */     throw new InvalidAttributeValueException("unexpected oids string: " + paramString);
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
/*      */   private static final String[] readQDescrList(String paramString, int[] paramArrayOfint) throws NamingException {
/*  522 */     Vector<String> vector = new Vector(5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  528 */     paramArrayOfint[0] = paramArrayOfint[0] + 1;
/*  529 */     skipWhitespace(paramString, paramArrayOfint);
/*  530 */     int i = paramArrayOfint[0];
/*  531 */     int j = paramString.indexOf(')', i);
/*      */     
/*  533 */     if (j == -1) {
/*  534 */       throw new InvalidAttributeValueException("oidlist has no end mark: " + paramString);
/*      */     }
/*      */ 
/*      */     
/*  538 */     while (i < j) {
/*  539 */       String[] arrayOfString1 = readQDString(paramString, paramArrayOfint);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  546 */       vector.addElement(arrayOfString1[0]);
/*  547 */       skipWhitespace(paramString, paramArrayOfint);
/*  548 */       i = paramArrayOfint[0];
/*      */     } 
/*      */     
/*  551 */     paramArrayOfint[0] = j + 1;
/*      */     
/*  553 */     String[] arrayOfString = new String[vector.size()];
/*  554 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*  555 */       arrayOfString[b] = vector.elementAt(b);
/*      */     }
/*  557 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String[] readWOID(String paramString, int[] paramArrayOfint) throws NamingException {
/*  567 */     skipWhitespace(paramString, paramArrayOfint);
/*      */     
/*  569 */     if (paramString.charAt(paramArrayOfint[0]) == '\'')
/*      */     {
/*  571 */       return readQDString(paramString, paramArrayOfint);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  576 */     int i = paramArrayOfint[0];
/*  577 */     int j = paramString.indexOf(' ', i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  584 */     if (j == -1 || i == j) {
/*  585 */       throw new InvalidAttributeIdentifierException("malformed OID: " + paramString);
/*      */     }
/*      */ 
/*      */     
/*  589 */     paramArrayOfint[0] = j + 1;
/*      */     
/*  591 */     return new String[] { paramString.substring(i, j) };
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
/*      */   private static final String[] readOIDs(String paramString, int[] paramArrayOfint) throws NamingException {
/*  605 */     skipWhitespace(paramString, paramArrayOfint);
/*      */ 
/*      */     
/*  608 */     if (paramString.charAt(paramArrayOfint[0]) != '(') {
/*  609 */       return readWOID(paramString, paramArrayOfint);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  615 */     String str = null;
/*  616 */     Vector<String> vector = new Vector(5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  622 */     paramArrayOfint[0] = paramArrayOfint[0] + 1;
/*  623 */     skipWhitespace(paramString, paramArrayOfint);
/*  624 */     int i = paramArrayOfint[0];
/*  625 */     int k = paramString.indexOf(')', i);
/*  626 */     int j = paramString.indexOf('$', i);
/*      */     
/*  628 */     if (k == -1) {
/*  629 */       throw new InvalidAttributeValueException("oidlist has no end mark: " + paramString);
/*      */     }
/*      */ 
/*      */     
/*  633 */     if (j == -1 || k < j) {
/*  634 */       j = k;
/*      */     }
/*      */     
/*  637 */     while (j < k && j > 0) {
/*  638 */       int n = findTrailingWhitespace(paramString, j - 1);
/*  639 */       str = paramString.substring(i, n);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  644 */       vector.addElement(str);
/*  645 */       paramArrayOfint[0] = j + 1;
/*  646 */       skipWhitespace(paramString, paramArrayOfint);
/*  647 */       i = paramArrayOfint[0];
/*  648 */       j = paramString.indexOf('$', i);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  657 */     int m = findTrailingWhitespace(paramString, k - 1);
/*  658 */     str = paramString.substring(i, m);
/*  659 */     vector.addElement(str);
/*      */     
/*  661 */     paramArrayOfint[0] = k + 1;
/*      */     
/*  663 */     String[] arrayOfString = new String[vector.size()];
/*  664 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*  665 */       arrayOfString[b] = vector.elementAt(b);
/*      */     }
/*  667 */     return arrayOfString;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String classDef2ObjectDesc(Attributes paramAttributes) throws NamingException {
/*  770 */     StringBuffer stringBuffer = new StringBuffer("( ");
/*      */     
/*  772 */     Attribute attribute = null;
/*  773 */     byte b = 0;
/*      */ 
/*      */ 
/*      */     
/*  777 */     attribute = paramAttributes.get("NUMERICOID");
/*  778 */     if (attribute != null) {
/*  779 */       stringBuffer.append(writeNumericOID(attribute));
/*  780 */       b++;
/*      */     } else {
/*  782 */       throw new ConfigurationException("Class definition doesn'thave a numeric OID");
/*      */     } 
/*      */ 
/*      */     
/*  786 */     attribute = paramAttributes.get("NAME");
/*  787 */     if (attribute != null) {
/*  788 */       stringBuffer.append(writeQDescrs(attribute));
/*  789 */       b++;
/*      */     } 
/*      */     
/*  792 */     attribute = paramAttributes.get("DESC");
/*  793 */     if (attribute != null) {
/*  794 */       stringBuffer.append(writeQDString(attribute));
/*  795 */       b++;
/*      */     } 
/*      */     
/*  798 */     attribute = paramAttributes.get("OBSOLETE");
/*  799 */     if (attribute != null) {
/*  800 */       stringBuffer.append(writeBoolean(attribute));
/*  801 */       b++;
/*      */     } 
/*      */     
/*  804 */     attribute = paramAttributes.get("SUP");
/*  805 */     if (attribute != null) {
/*  806 */       stringBuffer.append(writeOIDs(attribute));
/*  807 */       b++;
/*      */     } 
/*      */     
/*  810 */     attribute = paramAttributes.get("ABSTRACT");
/*  811 */     if (attribute != null) {
/*  812 */       stringBuffer.append(writeBoolean(attribute));
/*  813 */       b++;
/*      */     } 
/*      */     
/*  816 */     attribute = paramAttributes.get("STRUCTURAL");
/*  817 */     if (attribute != null) {
/*  818 */       stringBuffer.append(writeBoolean(attribute));
/*  819 */       b++;
/*      */     } 
/*      */     
/*  822 */     attribute = paramAttributes.get("AUXILIARY");
/*  823 */     if (attribute != null) {
/*  824 */       stringBuffer.append(writeBoolean(attribute));
/*  825 */       b++;
/*      */     } 
/*      */     
/*  828 */     attribute = paramAttributes.get("MUST");
/*  829 */     if (attribute != null) {
/*  830 */       stringBuffer.append(writeOIDs(attribute));
/*  831 */       b++;
/*      */     } 
/*      */     
/*  834 */     attribute = paramAttributes.get("MAY");
/*  835 */     if (attribute != null) {
/*  836 */       stringBuffer.append(writeOIDs(attribute));
/*  837 */       b++;
/*      */     } 
/*      */ 
/*      */     
/*  841 */     if (b < paramAttributes.size()) {
/*  842 */       String str = null;
/*      */ 
/*      */       
/*  845 */       NamingEnumeration<? extends Attribute> namingEnumeration = paramAttributes.getAll();
/*  846 */       while (namingEnumeration.hasMoreElements()) {
/*      */         
/*  848 */         attribute = namingEnumeration.next();
/*  849 */         str = attribute.getID();
/*      */ 
/*      */         
/*  852 */         if (str.equals("NUMERICOID") || str
/*  853 */           .equals("NAME") || str
/*  854 */           .equals("SUP") || str
/*  855 */           .equals("MAY") || str
/*  856 */           .equals("MUST") || str
/*  857 */           .equals("STRUCTURAL") || str
/*  858 */           .equals("DESC") || str
/*  859 */           .equals("AUXILIARY") || str
/*  860 */           .equals("ABSTRACT") || str
/*  861 */           .equals("OBSOLETE")) {
/*      */           continue;
/*      */         }
/*      */         
/*  865 */         stringBuffer.append(writeQDStrings(attribute));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  870 */     stringBuffer.append(")");
/*      */     
/*  872 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String attrDef2AttrDesc(Attributes paramAttributes) throws NamingException {
/*  882 */     StringBuffer stringBuffer = new StringBuffer("( ");
/*      */     
/*  884 */     Attribute attribute = null;
/*  885 */     byte b = 0;
/*      */ 
/*      */ 
/*      */     
/*  889 */     attribute = paramAttributes.get("NUMERICOID");
/*  890 */     if (attribute != null) {
/*  891 */       stringBuffer.append(writeNumericOID(attribute));
/*  892 */       b++;
/*      */     } else {
/*  894 */       throw new ConfigurationException("Attribute type doesn'thave a numeric OID");
/*      */     } 
/*      */ 
/*      */     
/*  898 */     attribute = paramAttributes.get("NAME");
/*  899 */     if (attribute != null) {
/*  900 */       stringBuffer.append(writeQDescrs(attribute));
/*  901 */       b++;
/*      */     } 
/*      */     
/*  904 */     attribute = paramAttributes.get("DESC");
/*  905 */     if (attribute != null) {
/*  906 */       stringBuffer.append(writeQDString(attribute));
/*  907 */       b++;
/*      */     } 
/*      */     
/*  910 */     attribute = paramAttributes.get("OBSOLETE");
/*  911 */     if (attribute != null) {
/*  912 */       stringBuffer.append(writeBoolean(attribute));
/*  913 */       b++;
/*      */     } 
/*      */     
/*  916 */     attribute = paramAttributes.get("SUP");
/*  917 */     if (attribute != null) {
/*  918 */       stringBuffer.append(writeWOID(attribute));
/*  919 */       b++;
/*      */     } 
/*      */     
/*  922 */     attribute = paramAttributes.get("EQUALITY");
/*  923 */     if (attribute != null) {
/*  924 */       stringBuffer.append(writeWOID(attribute));
/*  925 */       b++;
/*      */     } 
/*      */     
/*  928 */     attribute = paramAttributes.get("ORDERING");
/*  929 */     if (attribute != null) {
/*  930 */       stringBuffer.append(writeWOID(attribute));
/*  931 */       b++;
/*      */     } 
/*      */     
/*  934 */     attribute = paramAttributes.get("SUBSTR");
/*  935 */     if (attribute != null) {
/*  936 */       stringBuffer.append(writeWOID(attribute));
/*  937 */       b++;
/*      */     } 
/*      */     
/*  940 */     attribute = paramAttributes.get("SYNTAX");
/*  941 */     if (attribute != null) {
/*  942 */       stringBuffer.append(writeWOID(attribute));
/*  943 */       b++;
/*      */     } 
/*      */     
/*  946 */     attribute = paramAttributes.get("SINGLE-VALUE");
/*  947 */     if (attribute != null) {
/*  948 */       stringBuffer.append(writeBoolean(attribute));
/*  949 */       b++;
/*      */     } 
/*      */     
/*  952 */     attribute = paramAttributes.get("COLLECTIVE");
/*  953 */     if (attribute != null) {
/*  954 */       stringBuffer.append(writeBoolean(attribute));
/*  955 */       b++;
/*      */     } 
/*      */     
/*  958 */     attribute = paramAttributes.get("NO-USER-MODIFICATION");
/*  959 */     if (attribute != null) {
/*  960 */       stringBuffer.append(writeBoolean(attribute));
/*  961 */       b++;
/*      */     } 
/*      */     
/*  964 */     attribute = paramAttributes.get("USAGE");
/*  965 */     if (attribute != null) {
/*  966 */       stringBuffer.append(writeQDString(attribute));
/*  967 */       b++;
/*      */     } 
/*      */ 
/*      */     
/*  971 */     if (b < paramAttributes.size()) {
/*  972 */       String str = null;
/*      */ 
/*      */       
/*  975 */       NamingEnumeration<? extends Attribute> namingEnumeration = paramAttributes.getAll();
/*  976 */       while (namingEnumeration.hasMoreElements()) {
/*      */         
/*  978 */         attribute = namingEnumeration.next();
/*  979 */         str = attribute.getID();
/*      */ 
/*      */         
/*  982 */         if (str.equals("NUMERICOID") || str
/*  983 */           .equals("NAME") || str
/*  984 */           .equals("SYNTAX") || str
/*  985 */           .equals("DESC") || str
/*  986 */           .equals("SINGLE-VALUE") || str
/*  987 */           .equals("EQUALITY") || str
/*  988 */           .equals("ORDERING") || str
/*  989 */           .equals("SUBSTR") || str
/*  990 */           .equals("NO-USER-MODIFICATION") || str
/*  991 */           .equals("USAGE") || str
/*  992 */           .equals("SUP") || str
/*  993 */           .equals("COLLECTIVE") || str
/*  994 */           .equals("OBSOLETE")) {
/*      */           continue;
/*      */         }
/*      */         
/*  998 */         stringBuffer.append(writeQDStrings(attribute));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1003 */     stringBuffer.append(")");
/*      */     
/* 1005 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String syntaxDef2SyntaxDesc(Attributes paramAttributes) throws NamingException {
/* 1015 */     StringBuffer stringBuffer = new StringBuffer("( ");
/*      */     
/* 1017 */     Attribute attribute = null;
/* 1018 */     byte b = 0;
/*      */ 
/*      */ 
/*      */     
/* 1022 */     attribute = paramAttributes.get("NUMERICOID");
/* 1023 */     if (attribute != null) {
/* 1024 */       stringBuffer.append(writeNumericOID(attribute));
/* 1025 */       b++;
/*      */     } else {
/* 1027 */       throw new ConfigurationException("Attribute type doesn'thave a numeric OID");
/*      */     } 
/*      */ 
/*      */     
/* 1031 */     attribute = paramAttributes.get("DESC");
/* 1032 */     if (attribute != null) {
/* 1033 */       stringBuffer.append(writeQDString(attribute));
/* 1034 */       b++;
/*      */     } 
/*      */ 
/*      */     
/* 1038 */     if (b < paramAttributes.size()) {
/* 1039 */       String str = null;
/*      */ 
/*      */       
/* 1042 */       NamingEnumeration<? extends Attribute> namingEnumeration = paramAttributes.getAll();
/* 1043 */       while (namingEnumeration.hasMoreElements()) {
/*      */         
/* 1045 */         attribute = namingEnumeration.next();
/* 1046 */         str = attribute.getID();
/*      */ 
/*      */         
/* 1049 */         if (str.equals("NUMERICOID") || str
/* 1050 */           .equals("DESC")) {
/*      */           continue;
/*      */         }
/*      */         
/* 1054 */         stringBuffer.append(writeQDStrings(attribute));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1059 */     stringBuffer.append(")");
/*      */     
/* 1061 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String matchRuleDef2MatchRuleDesc(Attributes paramAttributes) throws NamingException {
/* 1071 */     StringBuffer stringBuffer = new StringBuffer("( ");
/*      */     
/* 1073 */     Attribute attribute = null;
/* 1074 */     byte b = 0;
/*      */ 
/*      */ 
/*      */     
/* 1078 */     attribute = paramAttributes.get("NUMERICOID");
/* 1079 */     if (attribute != null) {
/* 1080 */       stringBuffer.append(writeNumericOID(attribute));
/* 1081 */       b++;
/*      */     } else {
/* 1083 */       throw new ConfigurationException("Attribute type doesn'thave a numeric OID");
/*      */     } 
/*      */ 
/*      */     
/* 1087 */     attribute = paramAttributes.get("NAME");
/* 1088 */     if (attribute != null) {
/* 1089 */       stringBuffer.append(writeQDescrs(attribute));
/* 1090 */       b++;
/*      */     } 
/*      */     
/* 1093 */     attribute = paramAttributes.get("DESC");
/* 1094 */     if (attribute != null) {
/* 1095 */       stringBuffer.append(writeQDString(attribute));
/* 1096 */       b++;
/*      */     } 
/*      */     
/* 1099 */     attribute = paramAttributes.get("OBSOLETE");
/* 1100 */     if (attribute != null) {
/* 1101 */       stringBuffer.append(writeBoolean(attribute));
/* 1102 */       b++;
/*      */     } 
/*      */     
/* 1105 */     attribute = paramAttributes.get("SYNTAX");
/* 1106 */     if (attribute != null) {
/* 1107 */       stringBuffer.append(writeWOID(attribute));
/* 1108 */       b++;
/*      */     } else {
/* 1110 */       throw new ConfigurationException("Attribute type doesn'thave a syntax OID");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1115 */     if (b < paramAttributes.size()) {
/* 1116 */       String str = null;
/*      */ 
/*      */       
/* 1119 */       NamingEnumeration<? extends Attribute> namingEnumeration = paramAttributes.getAll();
/* 1120 */       while (namingEnumeration.hasMoreElements()) {
/*      */         
/* 1122 */         attribute = namingEnumeration.next();
/* 1123 */         str = attribute.getID();
/*      */ 
/*      */         
/* 1126 */         if (str.equals("NUMERICOID") || str
/* 1127 */           .equals("NAME") || str
/* 1128 */           .equals("SYNTAX") || str
/* 1129 */           .equals("DESC") || str
/* 1130 */           .equals("OBSOLETE")) {
/*      */           continue;
/*      */         }
/*      */         
/* 1134 */         stringBuffer.append(writeQDStrings(attribute));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1139 */     stringBuffer.append(")");
/*      */     
/* 1141 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private final String writeNumericOID(Attribute paramAttribute) throws NamingException {
/* 1146 */     if (paramAttribute.size() != 1) {
/* 1147 */       throw new InvalidAttributeValueException("A class definition must have exactly one numeric OID");
/*      */     }
/*      */     
/* 1150 */     return (String)paramAttribute.get() + ' ';
/*      */   }
/*      */   
/*      */   private final String writeWOID(Attribute paramAttribute) throws NamingException {
/* 1154 */     if (this.netscapeBug) {
/* 1155 */       return writeQDString(paramAttribute);
/*      */     }
/* 1157 */     return paramAttribute.getID() + ' ' + paramAttribute.get() + ' ';
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final String writeQDString(Attribute paramAttribute) throws NamingException {
/* 1163 */     if (paramAttribute.size() != 1) {
/* 1164 */       throw new InvalidAttributeValueException(paramAttribute
/* 1165 */           .getID() + " must have exactly one value");
/*      */     }
/*      */     
/* 1168 */     return paramAttribute.getID() + ' ' + '\'' + paramAttribute
/* 1169 */       .get() + '\'' + ' ';
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String writeQDStrings(Attribute paramAttribute) throws NamingException {
/* 1179 */     return writeQDescrs(paramAttribute);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String writeQDescrs(Attribute paramAttribute) throws NamingException {
/* 1189 */     switch (paramAttribute.size()) {
/*      */       case 0:
/* 1191 */         throw new InvalidAttributeValueException(paramAttribute
/* 1192 */             .getID() + "has no values");
/*      */       case 1:
/* 1194 */         return writeQDString(paramAttribute);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1199 */     StringBuffer stringBuffer = new StringBuffer(paramAttribute.getID());
/* 1200 */     stringBuffer.append(' ');
/* 1201 */     stringBuffer.append('(');
/*      */     
/* 1203 */     NamingEnumeration<?> namingEnumeration = paramAttribute.getAll();
/*      */     
/* 1205 */     while (namingEnumeration.hasMore()) {
/* 1206 */       stringBuffer.append(' ');
/* 1207 */       stringBuffer.append('\'');
/* 1208 */       stringBuffer.append((String)namingEnumeration.next());
/* 1209 */       stringBuffer.append('\'');
/* 1210 */       stringBuffer.append(' ');
/*      */     } 
/*      */     
/* 1213 */     stringBuffer.append(')');
/* 1214 */     stringBuffer.append(' ');
/*      */     
/* 1216 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final String writeOIDs(Attribute paramAttribute) throws NamingException {
/* 1222 */     switch (paramAttribute.size()) {
/*      */       case 0:
/* 1224 */         throw new InvalidAttributeValueException(paramAttribute
/* 1225 */             .getID() + "has no values");
/*      */       
/*      */       case 1:
/* 1228 */         if (this.netscapeBug) {
/*      */           break;
/*      */         }
/* 1231 */         return writeWOID(paramAttribute);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1236 */     StringBuffer stringBuffer = new StringBuffer(paramAttribute.getID());
/* 1237 */     stringBuffer.append(' ');
/* 1238 */     stringBuffer.append('(');
/*      */     
/* 1240 */     NamingEnumeration<?> namingEnumeration = paramAttribute.getAll();
/* 1241 */     stringBuffer.append(' ');
/* 1242 */     stringBuffer.append(namingEnumeration.next());
/*      */     
/* 1244 */     while (namingEnumeration.hasMore()) {
/* 1245 */       stringBuffer.append(' ');
/* 1246 */       stringBuffer.append('$');
/* 1247 */       stringBuffer.append(' ');
/* 1248 */       stringBuffer.append((String)namingEnumeration.next());
/*      */     } 
/*      */     
/* 1251 */     stringBuffer.append(' ');
/* 1252 */     stringBuffer.append(')');
/* 1253 */     stringBuffer.append(' ');
/*      */     
/* 1255 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private final String writeBoolean(Attribute paramAttribute) throws NamingException {
/* 1260 */     return paramAttribute.getID() + ' ';
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Attribute stringifyObjDesc(Attributes paramAttributes) throws NamingException {
/* 1269 */     BasicAttribute basicAttribute = new BasicAttribute("objectClasses");
/* 1270 */     basicAttribute.add(classDef2ObjectDesc(paramAttributes));
/* 1271 */     return basicAttribute;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Attribute stringifyAttrDesc(Attributes paramAttributes) throws NamingException {
/* 1279 */     BasicAttribute basicAttribute = new BasicAttribute("attributeTypes");
/* 1280 */     basicAttribute.add(attrDef2AttrDesc(paramAttributes));
/* 1281 */     return basicAttribute;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Attribute stringifySyntaxDesc(Attributes paramAttributes) throws NamingException {
/* 1289 */     BasicAttribute basicAttribute = new BasicAttribute("ldapSyntaxes");
/* 1290 */     basicAttribute.add(syntaxDef2SyntaxDesc(paramAttributes));
/* 1291 */     return basicAttribute;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Attribute stringifyMatchRuleDesc(Attributes paramAttributes) throws NamingException {
/* 1299 */     BasicAttribute basicAttribute = new BasicAttribute("matchingRules");
/* 1300 */     basicAttribute.add(matchRuleDef2MatchRuleDesc(paramAttributes));
/* 1301 */     return basicAttribute;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapSchemaParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */