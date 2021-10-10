/*      */ package javax.management.modelmbean;
/*      */ 
/*      */ import com.sun.jmx.defaults.JmxProperties;
/*      */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*      */ import com.sun.jmx.mbeanserver.Util;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.security.AccessController;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeMap;
/*      */ import java.util.logging.Level;
/*      */ import javax.management.Descriptor;
/*      */ import javax.management.ImmutableDescriptor;
/*      */ import javax.management.MBeanException;
/*      */ import javax.management.RuntimeOperationsException;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DescriptorSupport
/*      */   implements Descriptor
/*      */ {
/*      */   private static final long oldSerialVersionUID = 8071560848919417985L;
/*      */   private static final long newSerialVersionUID = -6292969195866300415L;
/*  101 */   private static final ObjectStreamField[] oldSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("descriptor", HashMap.class), new ObjectStreamField("currClass", String.class) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  108 */   private static final ObjectStreamField[] newSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("descriptor", HashMap.class) };
/*      */   
/*      */   private static final long serialVersionUID;
/*      */   
/*      */   private static final ObjectStreamField[] serialPersistentFields;
/*      */   
/*      */   private static final String serialForm;
/*      */   
/*      */   private transient SortedMap<String, Object> descriptorMap;
/*      */   
/*      */   private static final String currClass = "DescriptorSupport";
/*      */   
/*      */   static {
/*  121 */     String str = null;
/*  122 */     boolean bool = false;
/*      */     try {
/*  124 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/*  125 */       str = AccessController.<String>doPrivileged(getPropertyAction);
/*  126 */       bool = "1.0".equals(str);
/*  127 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  130 */     serialForm = str;
/*  131 */     if (bool) {
/*  132 */       serialPersistentFields = oldSerialPersistentFields;
/*  133 */       serialVersionUID = 8071560848919417985L;
/*      */     } else {
/*  135 */       serialPersistentFields = newSerialPersistentFields;
/*  136 */       serialVersionUID = -6292969195866300415L;
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
/*      */   public DescriptorSupport() {
/*  166 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  167 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  168 */           .getName(), "DescriptorSupport()", "Constructor");
/*      */     }
/*      */     
/*  171 */     init(null);
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
/*      */   public DescriptorSupport(int paramInt) throws MBeanException, RuntimeOperationsException {
/*  190 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  191 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  192 */           .getName(), "Descriptor(initNumFields = " + paramInt + ")", "Constructor");
/*      */     }
/*      */ 
/*      */     
/*  196 */     if (paramInt <= 0) {
/*  197 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  198 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  199 */             .getName(), "Descriptor(initNumFields)", "Illegal arguments: initNumFields <= 0");
/*      */       }
/*      */ 
/*      */       
/*  203 */       String str = "Descriptor field limit invalid: " + paramInt;
/*      */       
/*  205 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException(str);
/*  206 */       throw new RuntimeOperationsException(illegalArgumentException, str);
/*      */     } 
/*  208 */     init(null);
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
/*      */   public DescriptorSupport(DescriptorSupport paramDescriptorSupport) {
/*  221 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  222 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  223 */           .getName(), "Descriptor(Descriptor)", "Constructor");
/*      */     }
/*      */     
/*  226 */     if (paramDescriptorSupport == null) {
/*  227 */       init(null);
/*      */     } else {
/*  229 */       init(paramDescriptorSupport.descriptorMap);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DescriptorSupport(String paramString) throws MBeanException, RuntimeOperationsException, XMLParseException {
/*  270 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  271 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  272 */           .getName(), "Descriptor(String = '" + paramString + "')", "Constructor");
/*      */     }
/*      */     
/*  275 */     if (paramString == null) {
/*  276 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  277 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  278 */             .getName(), "Descriptor(String = null)", "Illegal arguments");
/*      */       }
/*      */ 
/*      */       
/*  282 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("String in parameter is null");
/*  283 */       throw new RuntimeOperationsException(illegalArgumentException, "String in parameter is null");
/*      */     } 
/*      */     
/*  286 */     String str1 = paramString.toLowerCase();
/*  287 */     if (!str1.startsWith("<descriptor>") || 
/*  288 */       !str1.endsWith("</descriptor>")) {
/*  289 */       throw new XMLParseException("No <descriptor>, </descriptor> pair");
/*      */     }
/*      */ 
/*      */     
/*  293 */     init(null);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  298 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, "<> \t\n\r\f");
/*      */     
/*  300 */     boolean bool1 = false;
/*  301 */     boolean bool2 = false;
/*  302 */     String str2 = null;
/*  303 */     String str3 = null;
/*      */ 
/*      */     
/*  306 */     while (stringTokenizer.hasMoreTokens()) {
/*  307 */       String str = stringTokenizer.nextToken();
/*      */       
/*  309 */       if (str.equalsIgnoreCase("FIELD")) {
/*  310 */         bool1 = true; continue;
/*  311 */       }  if (str.equalsIgnoreCase("/FIELD")) {
/*  312 */         if (str2 != null && str3 != null) {
/*      */           
/*  314 */           str2 = str2.substring(str2.indexOf('"') + 1, str2
/*  315 */               .lastIndexOf('"'));
/*      */           
/*  317 */           Object object = parseQuotedFieldValue(str3);
/*  318 */           setField(str2, object);
/*      */         } 
/*  320 */         str2 = null;
/*  321 */         str3 = null;
/*  322 */         bool1 = false; continue;
/*  323 */       }  if (str.equalsIgnoreCase("DESCRIPTOR")) {
/*  324 */         bool2 = true; continue;
/*  325 */       }  if (str.equalsIgnoreCase("/DESCRIPTOR")) {
/*  326 */         bool2 = false;
/*  327 */         str2 = null;
/*  328 */         str3 = null;
/*  329 */         bool1 = false; continue;
/*  330 */       }  if (bool1 && bool2) {
/*      */         
/*  332 */         int i = str.indexOf("=");
/*  333 */         if (i > 0) {
/*  334 */           String str5 = str.substring(0, i);
/*  335 */           String str6 = str.substring(i + 1);
/*  336 */           if (str5.equalsIgnoreCase("NAME")) {
/*  337 */             str2 = str6; continue;
/*  338 */           }  if (str5.equalsIgnoreCase("VALUE")) {
/*  339 */             str3 = str6; continue;
/*      */           } 
/*  341 */           String str7 = "Expected `name' or `value', got `" + str + "'";
/*      */           
/*  343 */           throw new XMLParseException(str7);
/*      */         } 
/*      */         
/*  346 */         String str4 = "Expected `keyword=value', got `" + str + "'";
/*      */         
/*  348 */         throw new XMLParseException(str4);
/*      */       } 
/*      */     } 
/*      */     
/*  352 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  353 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  354 */           .getName(), "Descriptor(XMLString)", "Exit");
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
/*      */   public DescriptorSupport(String[] paramArrayOfString, Object[] paramArrayOfObject) throws RuntimeOperationsException {
/*  382 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  383 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  384 */           .getName(), "Descriptor(fieldNames,fieldObjects)", "Constructor");
/*      */     }
/*      */ 
/*      */     
/*  388 */     if (paramArrayOfString == null || paramArrayOfObject == null || paramArrayOfString.length != paramArrayOfObject.length) {
/*      */       
/*  390 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  391 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  392 */             .getName(), "Descriptor(fieldNames,fieldObjects)", "Illegal arguments");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  399 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Null or invalid fieldNames or fieldValues");
/*  400 */       throw new RuntimeOperationsException(illegalArgumentException, "Null or invalid fieldNames or fieldValues");
/*      */     } 
/*      */ 
/*      */     
/*  404 */     init(null);
/*  405 */     for (byte b = 0; b < paramArrayOfString.length; b++)
/*      */     {
/*      */       
/*  408 */       setField(paramArrayOfString[b], paramArrayOfObject[b]);
/*      */     }
/*  410 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  411 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  412 */           .getName(), "Descriptor(fieldNames,fieldObjects)", "Exit");
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
/*      */   public DescriptorSupport(String... paramVarArgs) {
/*  446 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  447 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  448 */           .getName(), "Descriptor(String... fields)", "Constructor");
/*      */     }
/*      */     
/*  451 */     init(null);
/*  452 */     if (paramVarArgs == null || paramVarArgs.length == 0) {
/*      */       return;
/*      */     }
/*  455 */     init(null);
/*      */     
/*  457 */     for (byte b = 0; b < paramVarArgs.length; b++) {
/*  458 */       if (paramVarArgs[b] != null && !paramVarArgs[b].equals("")) {
/*      */ 
/*      */         
/*  461 */         int i = paramVarArgs[b].indexOf("=");
/*  462 */         if (i < 0) {
/*      */           
/*  464 */           if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  465 */             JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  466 */                 .getName(), "Descriptor(String... fields)", "Illegal arguments: field does not have '=' as a name and value separator");
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  472 */           IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Field in invalid format: no equals sign");
/*  473 */           throw new RuntimeOperationsException(illegalArgumentException, "Field in invalid format: no equals sign");
/*      */         } 
/*      */         
/*  476 */         String str1 = paramVarArgs[b].substring(0, i);
/*  477 */         String str2 = null;
/*  478 */         if (i < paramVarArgs[b].length())
/*      */         {
/*  480 */           str2 = paramVarArgs[b].substring(i + 1);
/*      */         }
/*      */         
/*  483 */         if (str1.equals("")) {
/*  484 */           if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  485 */             JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  486 */                 .getName(), "Descriptor(String... fields)", "Illegal arguments: fieldName is empty");
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  492 */           IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Field in invalid format: no fieldName");
/*  493 */           throw new RuntimeOperationsException(illegalArgumentException, "Field in invalid format: no fieldName");
/*      */         } 
/*      */         
/*  496 */         setField(str1, str2);
/*      */       } 
/*  498 */     }  if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  499 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  500 */           .getName(), "Descriptor(String... fields)", "Exit");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void init(Map<String, ?> paramMap) {
/*  506 */     this.descriptorMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
/*      */     
/*  508 */     if (paramMap != null) {
/*  509 */       this.descriptorMap.putAll(paramMap);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Object getFieldValue(String paramString) throws RuntimeOperationsException {
/*  518 */     if (paramString == null || paramString.equals("")) {
/*  519 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  520 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  521 */             .getName(), "getFieldValue(String fieldName)", "Illegal arguments: null field name");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  526 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Fieldname requested is null");
/*  527 */       throw new RuntimeOperationsException(illegalArgumentException, "Fieldname requested is null");
/*      */     } 
/*  529 */     Object object = this.descriptorMap.get(paramString);
/*  530 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  531 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  532 */           .getName(), "getFieldValue(String fieldName = " + paramString + ")", "Returns '" + object + "'");
/*      */     }
/*      */ 
/*      */     
/*  536 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setField(String paramString, Object paramObject) throws RuntimeOperationsException {
/*  543 */     if (paramString == null || paramString.equals("")) {
/*  544 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  545 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  546 */             .getName(), "setField(fieldName,fieldValue)", "Illegal arguments: null or empty field name");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  552 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Field name to be set is null or empty");
/*  553 */       throw new RuntimeOperationsException(illegalArgumentException, "Field name to be set is null or empty");
/*      */     } 
/*      */     
/*  556 */     if (!validateField(paramString, paramObject)) {
/*  557 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  558 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  559 */             .getName(), "setField(fieldName,fieldValue)", "Illegal arguments");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  564 */       String str = "Field value invalid: " + paramString + "=" + paramObject;
/*      */       
/*  566 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException(str);
/*  567 */       throw new RuntimeOperationsException(illegalArgumentException, str);
/*      */     } 
/*      */     
/*  570 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  571 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  572 */           .getName(), "setField(fieldName,fieldValue)", "Entry: setting '" + paramString + "' to '" + paramObject + "'");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  580 */     this.descriptorMap.put(paramString, paramObject);
/*      */   }
/*      */   
/*      */   public synchronized String[] getFields() {
/*  584 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  585 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  586 */           .getName(), "getFields()", "Entry");
/*      */     }
/*      */     
/*  589 */     int i = this.descriptorMap.size();
/*      */     
/*  591 */     String[] arrayOfString = new String[i];
/*  592 */     Set<Map.Entry<String, Object>> set = this.descriptorMap.entrySet();
/*      */     
/*  594 */     byte b = 0;
/*      */     
/*  596 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  597 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  598 */           .getName(), "getFields()", "Returning " + i + " fields");
/*      */     }
/*      */     
/*  601 */     Iterator<Map.Entry<String, Object>> iterator = set.iterator();
/*  602 */     for (; iterator.hasNext(); b++) {
/*  603 */       Map.Entry entry = iterator.next();
/*      */       
/*  605 */       if (entry == null) {
/*  606 */         if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  607 */           JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  608 */               .getName(), "getFields()", "Element is null");
/*      */         }
/*      */       } else {
/*      */         
/*  612 */         Object object = entry.getValue();
/*  613 */         if (object == null) {
/*  614 */           arrayOfString[b] = (String)entry.getKey() + "=";
/*      */         }
/*  616 */         else if (object instanceof String) {
/*  617 */           arrayOfString[b] = (String)entry
/*  618 */             .getKey() + "=" + object.toString();
/*      */         } else {
/*  620 */           arrayOfString[b] = (String)entry
/*  621 */             .getKey() + "=(" + object
/*  622 */             .toString() + ")";
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  628 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  629 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  630 */           .getName(), "getFields()", "Exit");
/*      */     }
/*      */ 
/*      */     
/*  634 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public synchronized String[] getFieldNames() {
/*  638 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  639 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  640 */           .getName(), "getFieldNames()", "Entry");
/*      */     }
/*      */     
/*  643 */     int i = this.descriptorMap.size();
/*      */     
/*  645 */     String[] arrayOfString = new String[i];
/*  646 */     Set<Map.Entry<String, Object>> set = this.descriptorMap.entrySet();
/*      */     
/*  648 */     byte b = 0;
/*      */     
/*  650 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  651 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  652 */           .getName(), "getFieldNames()", "Returning " + i + " fields");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  657 */     Iterator<Map.Entry<String, Object>> iterator = set.iterator();
/*  658 */     for (; iterator.hasNext(); b++) {
/*  659 */       Map.Entry entry = iterator.next();
/*      */       
/*  661 */       if (entry == null || entry.getKey() == null) {
/*  662 */         if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  663 */           JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  664 */               .getName(), "getFieldNames()", "Field is null");
/*      */         }
/*      */       } else {
/*      */         
/*  668 */         arrayOfString[b] = ((String)entry.getKey()).toString();
/*      */       } 
/*      */     } 
/*      */     
/*  672 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  673 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  674 */           .getName(), "getFieldNames()", "Exit");
/*      */     }
/*      */ 
/*      */     
/*  678 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized Object[] getFieldValues(String... paramVarArgs) {
/*  683 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  684 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  685 */           .getName(), "getFieldValues(String... fieldNames)", "Entry");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  692 */     int i = (paramVarArgs == null) ? this.descriptorMap.size() : paramVarArgs.length;
/*  693 */     Object[] arrayOfObject = new Object[i];
/*      */     
/*  695 */     byte b = 0;
/*      */     
/*  697 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  698 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  699 */           .getName(), "getFieldValues(String... fieldNames)", "Returning " + i + " fields");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  704 */     if (paramVarArgs == null) {
/*  705 */       for (Object object : this.descriptorMap.values())
/*  706 */         arrayOfObject[b++] = object; 
/*      */     } else {
/*  708 */       for (b = 0; b < paramVarArgs.length; b++) {
/*  709 */         if (paramVarArgs[b] == null || paramVarArgs[b].equals("")) {
/*  710 */           arrayOfObject[b] = null;
/*      */         } else {
/*  712 */           arrayOfObject[b] = getFieldValue(paramVarArgs[b]);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  717 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  718 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  719 */           .getName(), "getFieldValues(String... fieldNames)", "Exit");
/*      */     }
/*      */ 
/*      */     
/*  723 */     return arrayOfObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setFields(String[] paramArrayOfString, Object[] paramArrayOfObject) throws RuntimeOperationsException {
/*  730 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  731 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  732 */           .getName(), "setFields(fieldNames,fieldValues)", "Entry");
/*      */     }
/*      */ 
/*      */     
/*  736 */     if (paramArrayOfString == null || paramArrayOfObject == null || paramArrayOfString.length != paramArrayOfObject.length) {
/*      */       
/*  738 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  739 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  740 */             .getName(), "setFields(fieldNames,fieldValues)", "Illegal arguments");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  746 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("fieldNames and fieldValues are null or invalid");
/*  747 */       throw new RuntimeOperationsException(illegalArgumentException, "fieldNames and fieldValues are null or invalid");
/*      */     } 
/*      */     
/*  750 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  751 */       if (paramArrayOfString[b] == null || paramArrayOfString[b].equals("")) {
/*  752 */         if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  753 */           JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  754 */               .getName(), "setFields(fieldNames,fieldValues)", "Null field name encountered at element " + b);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  759 */         IllegalArgumentException illegalArgumentException = new IllegalArgumentException("fieldNames is null or invalid");
/*  760 */         throw new RuntimeOperationsException(illegalArgumentException, "fieldNames is null or invalid");
/*      */       } 
/*  762 */       setField(paramArrayOfString[b], paramArrayOfObject[b]);
/*      */     } 
/*  764 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  765 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  766 */           .getName(), "setFields(fieldNames,fieldValues)", "Exit");
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
/*      */   public synchronized Object clone() throws RuntimeOperationsException {
/*  781 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  782 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  783 */           .getName(), "clone()", "Entry");
/*      */     }
/*      */     
/*  786 */     return new DescriptorSupport(this);
/*      */   }
/*      */   
/*      */   public synchronized void removeField(String paramString) {
/*  790 */     if (paramString == null || paramString.equals("")) {
/*      */       return;
/*      */     }
/*      */     
/*  794 */     this.descriptorMap.remove(paramString);
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
/*      */   public synchronized boolean equals(Object paramObject) {
/*  824 */     if (paramObject == this)
/*  825 */       return true; 
/*  826 */     if (!(paramObject instanceof Descriptor))
/*  827 */       return false; 
/*  828 */     if (paramObject instanceof ImmutableDescriptor)
/*  829 */       return paramObject.equals(this); 
/*  830 */     return (new ImmutableDescriptor(this.descriptorMap)).equals(paramObject);
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
/*      */   public synchronized int hashCode() {
/*  857 */     int i = this.descriptorMap.size();
/*      */ 
/*      */     
/*  860 */     return Util.hashCode((String[])this.descriptorMap
/*  861 */         .keySet().toArray((Object[])new String[i]), this.descriptorMap
/*  862 */         .values().toArray(new Object[i]));
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
/*      */   public synchronized boolean isValid() throws RuntimeOperationsException {
/*  900 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  901 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  902 */           .getName(), "isValid()", "Entry");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  907 */     Set<Map.Entry<String, Object>> set = this.descriptorMap.entrySet();
/*      */     
/*  909 */     if (set == null) {
/*  910 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  911 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  912 */             .getName(), "isValid()", "Returns false (null set)");
/*      */       }
/*      */       
/*  915 */       return false;
/*      */     } 
/*      */     
/*  918 */     String str1 = (String)getFieldValue("name");
/*  919 */     String str2 = (String)getFieldValue("descriptorType");
/*      */     
/*  921 */     if (str1 == null || str2 == null || str1
/*  922 */       .equals("") || str2.equals("")) {
/*  923 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  928 */     for (Map.Entry<String, Object> entry : set) {
/*  929 */       if (entry != null && 
/*  930 */         entry.getValue() != null) {
/*      */         
/*  932 */         if (validateField(((String)entry.getKey()).toString(), entry
/*  933 */             .getValue().toString())) {
/*      */           continue;
/*      */         }
/*  936 */         if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  937 */           JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  938 */               .getName(), "isValid()", "Field " + (String)entry
/*      */               
/*  940 */               .getKey() + "=" + entry
/*  941 */               .getValue() + " is not valid");
/*      */         }
/*  943 */         return false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  950 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/*  951 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/*  952 */           .getName(), "isValid()", "Returns true");
/*      */     }
/*      */     
/*  955 */     return true;
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
/*      */   private boolean validateField(String paramString, Object paramObject) {
/*  975 */     if (paramString == null || paramString.equals(""))
/*  976 */       return false; 
/*  977 */     String str = "";
/*  978 */     boolean bool1 = false;
/*  979 */     if (paramObject != null && paramObject instanceof String) {
/*  980 */       str = (String)paramObject;
/*  981 */       bool1 = true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  986 */     boolean bool2 = (paramString.equalsIgnoreCase("Name") || paramString.equalsIgnoreCase("DescriptorType")) ? true : false;
/*  987 */     if (bool2 || paramString
/*  988 */       .equalsIgnoreCase("SetMethod") || paramString
/*  989 */       .equalsIgnoreCase("GetMethod") || paramString
/*  990 */       .equalsIgnoreCase("Role") || paramString
/*  991 */       .equalsIgnoreCase("Class")) {
/*  992 */       if (paramObject == null || !bool1)
/*  993 */         return false; 
/*  994 */       if (bool2 && str.equals(""))
/*  995 */         return false; 
/*  996 */       return true;
/*  997 */     }  if (paramString.equalsIgnoreCase("visibility")) {
/*      */       long l;
/*  999 */       if (paramObject != null && bool1)
/* 1000 */       { l = toNumeric(str); }
/* 1001 */       else if (paramObject instanceof Integer)
/* 1002 */       { l = ((Integer)paramObject).intValue(); }
/* 1003 */       else { return false; }
/*      */       
/* 1005 */       if (l >= 1L && l <= 4L) {
/* 1006 */         return true;
/*      */       }
/* 1008 */       return false;
/* 1009 */     }  if (paramString.equalsIgnoreCase("severity")) {
/*      */       long l;
/*      */       
/* 1012 */       if (paramObject != null && bool1)
/* 1013 */       { l = toNumeric(str); }
/* 1014 */       else if (paramObject instanceof Integer)
/* 1015 */       { l = ((Integer)paramObject).intValue(); }
/* 1016 */       else { return false; }
/*      */       
/* 1018 */       return (l >= 0L && l <= 6L);
/* 1019 */     }  if (paramString.equalsIgnoreCase("PersistPolicy"))
/* 1020 */       return (paramObject != null && bool1 && (str
/* 1021 */         .equalsIgnoreCase("OnUpdate") || str
/* 1022 */         .equalsIgnoreCase("OnTimer") || str
/* 1023 */         .equalsIgnoreCase("NoMoreOftenThan") || str
/* 1024 */         .equalsIgnoreCase("Always") || str
/* 1025 */         .equalsIgnoreCase("Never") || str
/* 1026 */         .equalsIgnoreCase("OnUnregister"))); 
/* 1027 */     if (paramString.equalsIgnoreCase("PersistPeriod") || paramString
/* 1028 */       .equalsIgnoreCase("CurrencyTimeLimit") || paramString
/* 1029 */       .equalsIgnoreCase("LastUpdatedTimeStamp") || paramString
/* 1030 */       .equalsIgnoreCase("LastReturnedTimeStamp")) {
/*      */       long l;
/*      */       
/* 1033 */       if (paramObject != null && bool1)
/* 1034 */       { l = toNumeric(str); }
/* 1035 */       else if (paramObject instanceof Number)
/* 1036 */       { l = ((Number)paramObject).longValue(); }
/* 1037 */       else { return false; }
/*      */       
/* 1039 */       return (l >= -1L);
/* 1040 */     }  if (paramString.equalsIgnoreCase("log")) {
/* 1041 */       return (paramObject instanceof Boolean || (bool1 && (str
/*      */         
/* 1043 */         .equalsIgnoreCase("T") || str
/* 1044 */         .equalsIgnoreCase("true") || str
/* 1045 */         .equalsIgnoreCase("F") || str
/* 1046 */         .equalsIgnoreCase("false"))));
/*      */     }
/*      */ 
/*      */     
/* 1050 */     return true;
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
/*      */   public synchronized String toXMLString() {
/* 1083 */     StringBuilder stringBuilder = new StringBuilder("<Descriptor>");
/* 1084 */     Set<Map.Entry<String, Object>> set = this.descriptorMap.entrySet();
/* 1085 */     for (Map.Entry<String, Object> entry : set) {
/* 1086 */       String str1 = (String)entry.getKey();
/* 1087 */       Object object = entry.getValue();
/* 1088 */       String str2 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1094 */       if (object instanceof String) {
/* 1095 */         String str = (String)object;
/* 1096 */         if (!str.startsWith("(") || !str.endsWith(")"))
/* 1097 */           str2 = quote(str); 
/*      */       } 
/* 1099 */       if (str2 == null)
/* 1100 */         str2 = makeFieldValue(object); 
/* 1101 */       stringBuilder.append("<field name=\"").append(str1).append("\" value=\"")
/* 1102 */         .append(str2).append("\"></field>");
/*      */     } 
/* 1104 */     stringBuilder.append("</Descriptor>");
/* 1105 */     return stringBuilder.toString();
/*      */   }
/*      */   
/* 1108 */   private static final String[] entities = new String[] { " &#32;", "\"&quot;", "<&lt;", ">&gt;", "&&amp;", "\r&#13;", "\t&#9;", "\n&#10;", "\f&#12;" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1119 */   private static final Map<String, Character> entityToCharMap = new HashMap<>();
/*      */   
/*      */   private static final String[] charToEntityMap;
/*      */   
/*      */   static {
/* 1124 */     char c = Character.MIN_VALUE;
/* 1125 */     for (bool = false; bool < entities.length; bool++) {
/* 1126 */       char c1 = entities[bool].charAt(0);
/* 1127 */       if (c1 > c)
/* 1128 */         c = c1; 
/*      */     } 
/* 1130 */     charToEntityMap = new String[c + 1];
/* 1131 */     for (bool = false; bool < entities.length; bool++) {
/* 1132 */       char c1 = entities[bool].charAt(0);
/* 1133 */       String str1 = entities[bool].substring(1);
/* 1134 */       charToEntityMap[c1] = str1;
/* 1135 */       entityToCharMap.put(str1, Character.valueOf(c1));
/*      */     } 
/*      */   }
/*      */   
/*      */   private static boolean isMagic(char paramChar) {
/* 1140 */     return (paramChar < charToEntityMap.length && charToEntityMap[paramChar] != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String quote(String paramString) {
/* 1151 */     boolean bool = false;
/* 1152 */     for (byte b1 = 0; b1 < paramString.length(); b1++) {
/* 1153 */       if (isMagic(paramString.charAt(b1))) {
/* 1154 */         bool = true;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1158 */     if (!bool)
/* 1159 */       return paramString; 
/* 1160 */     StringBuilder stringBuilder = new StringBuilder();
/* 1161 */     for (byte b2 = 0; b2 < paramString.length(); b2++) {
/* 1162 */       char c = paramString.charAt(b2);
/* 1163 */       if (isMagic(c)) {
/* 1164 */         stringBuilder.append(charToEntityMap[c]);
/*      */       } else {
/* 1166 */         stringBuilder.append(c);
/*      */       } 
/* 1168 */     }  return stringBuilder.toString();
/*      */   }
/*      */   
/*      */   private static String unquote(String paramString) throws XMLParseException {
/* 1172 */     if (!paramString.startsWith("\"") || !paramString.endsWith("\""))
/* 1173 */       throw new XMLParseException("Value must be quoted: <" + paramString + ">"); 
/* 1174 */     StringBuilder stringBuilder = new StringBuilder();
/* 1175 */     int i = paramString.length() - 1;
/* 1176 */     for (int j = 1; j < i; j++) {
/* 1177 */       char c = paramString.charAt(j);
/*      */       int k;
/*      */       Character character;
/* 1180 */       if (c == '&' && (
/* 1181 */         k = paramString.indexOf(';', j + 1)) >= 0 && (
/* 1182 */         character = entityToCharMap.get(paramString.substring(j, k + 1))) != null) {
/*      */         
/* 1184 */         stringBuilder.append(character);
/* 1185 */         j = k;
/*      */       } else {
/* 1187 */         stringBuilder.append(c);
/*      */       } 
/* 1189 */     }  return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String makeFieldValue(Object paramObject) {
/* 1198 */     if (paramObject == null) {
/* 1199 */       return "(null)";
/*      */     }
/* 1201 */     Class<?> clazz = paramObject.getClass();
/*      */     try {
/* 1203 */       clazz.getConstructor(new Class[] { String.class });
/* 1204 */     } catch (NoSuchMethodException noSuchMethodException) {
/* 1205 */       String str1 = "Class " + clazz + " does not have a public constructor with a single string arg";
/*      */ 
/*      */       
/* 1208 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException(str1);
/* 1209 */       throw new RuntimeOperationsException(illegalArgumentException, "Cannot make XML descriptor");
/*      */     }
/* 1211 */     catch (SecurityException securityException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1217 */     String str = quote(paramObject.toString());
/*      */     
/* 1219 */     return "(" + clazz.getName() + "/" + str + ")";
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
/*      */   private static Object parseQuotedFieldValue(String paramString) throws XMLParseException {
/*      */     Constructor<?> constructor;
/* 1236 */     paramString = unquote(paramString);
/* 1237 */     if (paramString.equalsIgnoreCase("(null)"))
/* 1238 */       return null; 
/* 1239 */     if (!paramString.startsWith("(") || !paramString.endsWith(")"))
/* 1240 */       return paramString; 
/* 1241 */     int i = paramString.indexOf('/');
/* 1242 */     if (i < 0)
/*      */     {
/* 1244 */       return paramString.substring(1, paramString.length() - 1);
/*      */     }
/* 1246 */     String str1 = paramString.substring(1, i);
/*      */ 
/*      */     
/*      */     try {
/* 1250 */       ReflectUtil.checkPackageAccess(str1);
/*      */       
/* 1252 */       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*      */       
/* 1254 */       Class<?> clazz = Class.forName(str1, false, classLoader);
/* 1255 */       constructor = clazz.getConstructor(new Class[] { String.class });
/* 1256 */     } catch (Exception exception) {
/* 1257 */       throw new XMLParseException(exception, "Cannot parse value: <" + paramString + ">");
/*      */     } 
/*      */     
/* 1260 */     String str2 = paramString.substring(i + 1, paramString.length() - 1);
/*      */     try {
/* 1262 */       return constructor.newInstance(new Object[] { str2 });
/* 1263 */     } catch (Exception exception) {
/* 1264 */       String str = "Cannot construct instance of " + str1 + " with arg: <" + paramString + ">";
/*      */ 
/*      */       
/* 1267 */       throw new XMLParseException(exception, str);
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
/*      */   public synchronized String toString() {
/* 1289 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/* 1290 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/* 1291 */           .getName(), "toString()", "Entry");
/*      */     }
/*      */ 
/*      */     
/* 1295 */     String str = "";
/* 1296 */     String[] arrayOfString = getFields();
/*      */     
/* 1298 */     if (arrayOfString == null || arrayOfString.length == 0) {
/* 1299 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/* 1300 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/* 1301 */             .getName(), "toString()", "Empty Descriptor");
/*      */       }
/*      */       
/* 1304 */       return str;
/*      */     } 
/*      */     
/* 1307 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/* 1308 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/* 1309 */           .getName(), "toString()", "Printing " + arrayOfString.length + " fields");
/*      */     }
/*      */ 
/*      */     
/* 1313 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 1314 */       if (b == arrayOfString.length - 1) {
/* 1315 */         str = str.concat(arrayOfString[b]);
/*      */       } else {
/* 1317 */         str = str.concat(arrayOfString[b] + ", ");
/*      */       } 
/*      */     } 
/*      */     
/* 1321 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINEST)) {
/* 1322 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINEST, DescriptorSupport.class
/* 1323 */           .getName(), "toString()", "Exit returning " + str);
/*      */     }
/*      */ 
/*      */     
/* 1327 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private long toNumeric(String paramString) {
/*      */     try {
/* 1334 */       return Long.parseLong(paramString);
/* 1335 */     } catch (Exception exception) {
/* 1336 */       return -2L;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1347 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 1348 */     Map<? extends String, ?> map = Util.<Map>cast(getField.get("descriptor", (Object)null));
/* 1349 */     init(null);
/* 1350 */     if (map != null) {
/* 1351 */       this.descriptorMap.putAll(map);
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*      */     HashMap<String, Object> hashMap;
/* 1371 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 1372 */     boolean bool = "1.0".equals(serialForm);
/* 1373 */     if (bool) {
/* 1374 */       putField.put("currClass", "DescriptorSupport");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1382 */     SortedMap<String, Object> sortedMap = this.descriptorMap;
/* 1383 */     if (sortedMap.containsKey("targetObject")) {
/* 1384 */       sortedMap = new TreeMap<>(this.descriptorMap);
/* 1385 */       sortedMap.remove("targetObject");
/*      */     } 
/*      */ 
/*      */     
/* 1389 */     if (bool || "1.2.0".equals(serialForm) || "1.2.1"
/* 1390 */       .equals(serialForm)) {
/* 1391 */       HashMap<Object, Object> hashMap1 = new HashMap<>();
/* 1392 */       for (Map.Entry<String, Object> entry : sortedMap.entrySet())
/* 1393 */         hashMap1.put(((String)entry.getKey()).toLowerCase(), entry.getValue()); 
/*      */     } else {
/* 1395 */       hashMap = new HashMap<>(sortedMap);
/*      */     } 
/* 1397 */     putField.put("descriptor", hashMap);
/* 1398 */     paramObjectOutputStream.writeFields();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/modelmbean/DescriptorSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */