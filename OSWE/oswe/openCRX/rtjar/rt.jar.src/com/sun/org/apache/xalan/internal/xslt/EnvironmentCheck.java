/*      */ package com.sun.org.apache.xalan.internal.xslt;
/*      */ 
/*      */ import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
/*      */ import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
/*      */ import java.io.File;
/*      */ import java.io.FileWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import org.xml.sax.Attributes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class EnvironmentCheck
/*      */ {
/*      */   public static final String ERROR = "ERROR.";
/*      */   public static final String WARNING = "WARNING.";
/*      */   public static final String ERROR_FOUND = "At least one error was found!";
/*      */   public static final String VERSION = "version.";
/*      */   public static final String FOUNDCLASSES = "foundclasses.";
/*      */   public static final String CLASS_PRESENT = "present-unknown-version";
/*      */   public static final String CLASS_NOTPRESENT = "not-present";
/*      */   
/*      */   public static void main(String[] args) {
/*  109 */     PrintWriter sendOutputTo = new PrintWriter(System.out, true);
/*      */ 
/*      */     
/*  112 */     for (int i = 0; i < args.length; i++) {
/*      */       
/*  114 */       if ("-out".equalsIgnoreCase(args[i])) {
/*      */         
/*  116 */         i++;
/*      */         
/*  118 */         if (i < args.length) {
/*      */           
/*      */           try
/*      */           {
/*  122 */             sendOutputTo = new PrintWriter(new FileWriter(args[i], true));
/*      */           }
/*  124 */           catch (Exception e)
/*      */           {
/*  126 */             System.err.println("# WARNING: -out " + args[i] + " threw " + e
/*  127 */                 .toString());
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  132 */           System.err.println("# WARNING: -out argument should have a filename, output sent to console");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  138 */     EnvironmentCheck app = new EnvironmentCheck();
/*  139 */     app.checkEnvironment(sendOutputTo);
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
/*      */   public boolean checkEnvironment(PrintWriter pw) {
/*  170 */     if (null != pw) {
/*  171 */       this.outWriter = pw;
/*      */     }
/*      */     
/*  174 */     Map<String, Object> hash = getEnvironmentHash();
/*      */ 
/*      */     
/*  177 */     boolean environmentHasErrors = writeEnvironmentReport(hash);
/*      */     
/*  179 */     if (environmentHasErrors) {
/*      */ 
/*      */ 
/*      */       
/*  183 */       logMsg("# WARNING: Potential problems found in your environment!");
/*  184 */       logMsg("#    Check any 'ERROR' items above against the Xalan FAQs");
/*  185 */       logMsg("#    to correct potential problems with your classes/jars");
/*  186 */       logMsg("#    http://xml.apache.org/xalan-j/faq.html");
/*  187 */       if (null != this.outWriter)
/*  188 */         this.outWriter.flush(); 
/*  189 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  193 */     logMsg("# YAHOO! Your environment seems to be OK.");
/*  194 */     if (null != this.outWriter)
/*  195 */       this.outWriter.flush(); 
/*  196 */     return true;
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
/*      */   public Map<String, Object> getEnvironmentHash() {
/*  223 */     Map<String, Object> hash = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  228 */     checkJAXPVersion(hash);
/*  229 */     checkProcessorVersion(hash);
/*  230 */     checkParserVersion(hash);
/*  231 */     checkAntVersion(hash);
/*  232 */     if (!checkDOML3(hash)) {
/*  233 */       checkDOMVersion(hash);
/*      */     }
/*  235 */     checkSAXVersion(hash);
/*  236 */     checkSystemProperties(hash);
/*      */     
/*  238 */     return hash;
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
/*      */   protected boolean writeEnvironmentReport(Map<String, Object> h) {
/*  258 */     if (null == h) {
/*      */       
/*  260 */       logMsg("# ERROR: writeEnvironmentReport called with null Map");
/*  261 */       return false;
/*      */     } 
/*      */     
/*  264 */     boolean errors = false;
/*      */     
/*  266 */     logMsg("#---- BEGIN writeEnvironmentReport($Revision: 1.10 $): Useful stuff found: ----");
/*      */ 
/*      */ 
/*      */     
/*  270 */     for (Map.Entry<String, Object> entry : h.entrySet()) {
/*  271 */       String keyStr = entry.getKey();
/*      */       
/*      */       try {
/*  274 */         if (keyStr.startsWith("foundclasses.")) {
/*  275 */           List<Map> v = (ArrayList)entry.getValue();
/*  276 */           errors |= logFoundJars(v, keyStr);
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */         
/*  284 */         if (keyStr.startsWith("ERROR.")) {
/*  285 */           errors = true;
/*      */         }
/*  287 */         logMsg(keyStr + "=" + h.get(keyStr));
/*      */       }
/*  289 */       catch (Exception e) {
/*  290 */         logMsg("Reading-" + keyStr + "= threw: " + e.toString());
/*      */       } 
/*      */     } 
/*      */     
/*  294 */     logMsg("#----- END writeEnvironmentReport: Useful properties found: -----");
/*      */ 
/*      */     
/*  297 */     return errors;
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
/*  322 */   public String[] jarNames = new String[] { "xalan.jar", "xalansamples.jar", "xalanj1compat.jar", "xalanservlet.jar", "serializer.jar", "xerces.jar", "xercesImpl.jar", "testxsl.jar", "crimson.jar", "lotusxsl.jar", "jaxp.jar", "parser.jar", "dom.jar", "sax.jar", "xml.jar", "xml-apis.jar", "xsltc.jar" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Map<Long, String> JARVERSIONS;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean logFoundJars(List<Map> v, String desc) {
/*  352 */     if (null == v || v.size() < 1) {
/*  353 */       return false;
/*      */     }
/*  355 */     boolean errors = false;
/*      */     
/*  357 */     logMsg("#---- BEGIN Listing XML-related jars in: " + desc + " ----");
/*      */     
/*  359 */     for (Map<String, String> v1 : v) {
/*  360 */       for (Map.Entry<String, String> entry : v1.entrySet()) {
/*  361 */         String keyStr = entry.getKey();
/*      */         try {
/*  363 */           if (keyStr.startsWith("ERROR.")) {
/*  364 */             errors = true;
/*      */           }
/*  366 */           logMsg(keyStr + "=" + (String)entry.getValue());
/*      */         }
/*  368 */         catch (Exception e) {
/*  369 */           errors = true;
/*  370 */           logMsg("Reading-" + keyStr + "= threw: " + e.toString());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  375 */     logMsg("#----- END Listing XML-related jars in: " + desc + " -----");
/*      */     
/*  377 */     return errors;
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
/*      */   public void appendEnvironmentReport(Node container, Document factory, Map<String, Object> h) {
/*  395 */     if (null == container || null == factory) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  402 */       Element envCheckNode = factory.createElement("EnvironmentCheck");
/*  403 */       envCheckNode.setAttribute("version", "$Revision: 1.10 $");
/*  404 */       container.appendChild(envCheckNode);
/*      */       
/*  406 */       if (null == h) {
/*      */         
/*  408 */         Element element = factory.createElement("status");
/*  409 */         element.setAttribute("result", "ERROR");
/*  410 */         element.appendChild(factory.createTextNode("appendEnvironmentReport called with null Map!"));
/*  411 */         envCheckNode.appendChild(element);
/*      */         
/*      */         return;
/*      */       } 
/*  415 */       boolean errors = false;
/*      */       
/*  417 */       Element hashNode = factory.createElement("environment");
/*  418 */       envCheckNode.appendChild(hashNode);
/*      */       
/*  420 */       for (Map.Entry<String, Object> entry : h.entrySet()) {
/*  421 */         String keyStr = entry.getKey();
/*      */         
/*      */         try {
/*  424 */           if (keyStr.startsWith("foundclasses.")) {
/*  425 */             List<Map> v = (List<Map>)entry.getValue();
/*      */             
/*  427 */             errors |= appendFoundJars(hashNode, factory, v, keyStr);
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */           
/*  434 */           if (keyStr.startsWith("ERROR.")) {
/*  435 */             errors = true;
/*      */           }
/*  437 */           Element node = factory.createElement("item");
/*  438 */           node.setAttribute("key", keyStr);
/*  439 */           node.appendChild(factory.createTextNode((String)h.get(keyStr)));
/*  440 */           hashNode.appendChild(node);
/*      */         }
/*  442 */         catch (Exception e) {
/*  443 */           errors = true;
/*  444 */           Element node = factory.createElement("item");
/*  445 */           node.setAttribute("key", keyStr);
/*  446 */           node.appendChild(factory.createTextNode("ERROR. Reading " + keyStr + " threw: " + e.toString()));
/*  447 */           hashNode.appendChild(node);
/*      */         } 
/*      */       } 
/*      */       
/*  451 */       Element statusNode = factory.createElement("status");
/*  452 */       statusNode.setAttribute("result", errors ? "ERROR" : "OK");
/*  453 */       envCheckNode.appendChild(statusNode);
/*      */     }
/*  455 */     catch (Exception e2) {
/*      */       
/*  457 */       System.err.println("appendEnvironmentReport threw: " + e2.toString());
/*  458 */       e2.printStackTrace();
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
/*      */   protected boolean appendFoundJars(Node container, Document factory, List<Map> v, String desc) {
/*  481 */     if (null == v || v.size() < 1) {
/*  482 */       return false;
/*      */     }
/*  484 */     boolean errors = false;
/*      */     
/*  486 */     for (Map<String, String> v1 : v) {
/*  487 */       for (Map.Entry<String, String> entry : v1.entrySet()) {
/*  488 */         String keyStr = entry.getKey();
/*      */         try {
/*  490 */           if (keyStr.startsWith("ERROR.")) {
/*  491 */             errors = true;
/*      */           }
/*  493 */           Element node = factory.createElement("foundJar");
/*  494 */           node.setAttribute("name", keyStr.substring(0, keyStr.indexOf("-")));
/*  495 */           node.setAttribute("desc", keyStr.substring(keyStr.indexOf("-") + 1));
/*  496 */           node.appendChild(factory.createTextNode(entry.getValue()));
/*  497 */           container.appendChild(node);
/*  498 */         } catch (Exception e) {
/*  499 */           errors = true;
/*  500 */           Element node = factory.createElement("foundJar");
/*  501 */           node.appendChild(factory.createTextNode("ERROR. Reading " + keyStr + " threw: " + e.toString()));
/*  502 */           container.appendChild(node);
/*      */         } 
/*      */       } 
/*      */     } 
/*  506 */     return errors;
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
/*      */   protected void checkSystemProperties(Map<String, Object> h) {
/*  525 */     if (null == h) {
/*  526 */       h = new HashMap<>();
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  531 */       String javaVersion = SecuritySupport.getSystemProperty("java.version");
/*      */       
/*  533 */       h.put("java.version", javaVersion);
/*      */     }
/*  535 */     catch (SecurityException se) {
/*      */ 
/*      */ 
/*      */       
/*  539 */       h.put("java.version", "WARNING: SecurityException thrown accessing system version properties");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  550 */       String cp = SecuritySupport.getSystemProperty("java.class.path");
/*      */       
/*  552 */       h.put("java.class.path", cp);
/*      */       
/*  554 */       List<Map> classpathJars = checkPathForJars(cp, this.jarNames);
/*      */       
/*  556 */       if (null != classpathJars) {
/*  557 */         h.put("foundclasses.java.class.path", classpathJars);
/*      */       }
/*      */ 
/*      */       
/*  561 */       String othercp = SecuritySupport.getSystemProperty("sun.boot.class.path");
/*      */       
/*  563 */       if (null != othercp) {
/*  564 */         h.put("sun.boot.class.path", othercp);
/*  565 */         classpathJars = checkPathForJars(othercp, this.jarNames);
/*      */         
/*  567 */         if (null != classpathJars) {
/*  568 */           h.put("foundclasses.sun.boot.class.path", classpathJars);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  574 */       othercp = SecuritySupport.getSystemProperty("java.ext.dirs");
/*      */       
/*  576 */       if (null != othercp)
/*      */       {
/*  578 */         h.put("java.ext.dirs", othercp);
/*      */         
/*  580 */         classpathJars = checkPathForJars(othercp, this.jarNames);
/*      */         
/*  582 */         if (null != classpathJars) {
/*  583 */           h.put("foundclasses.java.ext.dirs", classpathJars);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  590 */     catch (SecurityException se2) {
/*      */ 
/*      */       
/*  593 */       h.put("java.class.path", "WARNING: SecurityException thrown accessing system classpath properties");
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
/*      */   protected List<Map> checkPathForJars(String cp, String[] jars) {
/*  619 */     if (null == cp || null == jars || 0 == cp.length() || 0 == jars.length)
/*      */     {
/*  621 */       return null;
/*      */     }
/*  623 */     List<Map> v = new ArrayList<>();
/*  624 */     StringTokenizer st = new StringTokenizer(cp, File.pathSeparator);
/*      */     
/*  626 */     while (st.hasMoreTokens()) {
/*      */ 
/*      */ 
/*      */       
/*  630 */       String filename = st.nextToken();
/*      */       
/*  632 */       for (int i = 0; i < jars.length; i++) {
/*      */         
/*  634 */         if (filename.indexOf(jars[i]) > -1) {
/*      */           
/*  636 */           File f = new File(filename);
/*      */           
/*  638 */           if (f.exists()) {
/*      */ 
/*      */             
/*      */             try {
/*      */ 
/*      */               
/*  644 */               Map<String, String> h = new HashMap<>(2);
/*      */               
/*  646 */               h.put(jars[i] + "-path", f.getAbsolutePath());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  653 */               if (!"xalan.jar".equalsIgnoreCase(jars[i])) {
/*  654 */                 h.put(jars[i] + "-apparent.version", 
/*  655 */                     getApparentVersion(jars[i], f.length()));
/*      */               }
/*  657 */               v.add(h);
/*  658 */             } catch (Exception exception) {}
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  663 */             Map<String, String> h = new HashMap<>(2);
/*      */             
/*  665 */             h.put(jars[i] + "-path", "WARNING. Classpath entry: " + filename + " does not exist");
/*      */             
/*  667 */             h.put(jars[i] + "-apparent.version", "not-present");
/*  668 */             v.add(h);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  674 */     return v;
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
/*      */   protected String getApparentVersion(String jarName, long jarSize) {
/*  700 */     String foundSize = JARVERSIONS.get(new Long(jarSize));
/*      */     
/*  702 */     if (null != foundSize && foundSize.startsWith(jarName))
/*      */     {
/*  704 */       return foundSize;
/*      */     }
/*      */ 
/*      */     
/*  708 */     if ("xerces.jar".equalsIgnoreCase(jarName) || "xercesImpl.jar"
/*  709 */       .equalsIgnoreCase(jarName))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  716 */       return jarName + " " + "WARNING." + "present-unknown-version";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  722 */     return jarName + " " + "present-unknown-version";
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
/*      */   protected void checkJAXPVersion(Map<String, Object> h) {
/*  739 */     if (null == h) {
/*  740 */       h = new HashMap<>();
/*      */     }
/*  742 */     Class<?> clazz = null;
/*      */ 
/*      */     
/*      */     try {
/*  746 */       String JAXP1_CLASS = "javax.xml.stream.XMLStreamConstants";
/*      */       
/*  748 */       clazz = ObjectFactory.findProviderClass("javax.xml.stream.XMLStreamConstants", true);
/*      */ 
/*      */       
/*  751 */       h.put("version.JAXP", "1.4");
/*      */     }
/*  753 */     catch (Exception e) {
/*      */       
/*  755 */       h.put("ERROR.version.JAXP", "1.3");
/*  756 */       h.put("ERROR.", "At least one error was found!");
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
/*      */   protected void checkProcessorVersion(Map<String, Object> h) {
/*  770 */     if (null == h) {
/*  771 */       h = new HashMap<>();
/*      */     }
/*      */     
/*      */     try {
/*  775 */       String XALAN1_VERSION_CLASS = "com.sun.org.apache.xalan.internal.xslt.XSLProcessorVersion";
/*      */ 
/*      */       
/*  778 */       Class<?> clazz = ObjectFactory.findProviderClass("com.sun.org.apache.xalan.internal.xslt.XSLProcessorVersion", true);
/*      */ 
/*      */       
/*  781 */       StringBuffer buf = new StringBuffer();
/*  782 */       Field f = clazz.getField("PRODUCT");
/*      */       
/*  784 */       buf.append(f.get(null));
/*  785 */       buf.append(';');
/*      */       
/*  787 */       f = clazz.getField("LANGUAGE");
/*      */       
/*  789 */       buf.append(f.get(null));
/*  790 */       buf.append(';');
/*      */       
/*  792 */       f = clazz.getField("S_VERSION");
/*      */       
/*  794 */       buf.append(f.get(null));
/*  795 */       buf.append(';');
/*  796 */       h.put("version.xalan1", buf.toString());
/*      */     }
/*  798 */     catch (Exception e1) {
/*      */       
/*  800 */       h.put("version.xalan1", "not-present");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  807 */       String XALAN2_VERSION_CLASS = "com.sun.org.apache.xalan.internal.processor.XSLProcessorVersion";
/*      */ 
/*      */       
/*  810 */       Class<?> clazz = ObjectFactory.findProviderClass("com.sun.org.apache.xalan.internal.processor.XSLProcessorVersion", true);
/*      */ 
/*      */       
/*  813 */       StringBuffer buf = new StringBuffer();
/*  814 */       Field f = clazz.getField("S_VERSION");
/*  815 */       buf.append(f.get(null));
/*      */       
/*  817 */       h.put("version.xalan2x", buf.toString());
/*      */     }
/*  819 */     catch (Exception e2) {
/*      */       
/*  821 */       h.put("version.xalan2x", "not-present");
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  826 */       String XALAN2_2_VERSION_CLASS = "com.sun.org.apache.xalan.internal.Version";
/*      */       
/*  828 */       String XALAN2_2_VERSION_METHOD = "getVersion";
/*  829 */       Class[] noArgs = new Class[0];
/*      */       
/*  831 */       Class<?> clazz = ObjectFactory.findProviderClass("com.sun.org.apache.xalan.internal.Version", true);
/*      */       
/*  833 */       Method method = clazz.getMethod("getVersion", noArgs);
/*  834 */       Object returnValue = method.invoke(null, new Object[0]);
/*      */       
/*  836 */       h.put("version.xalan2_2", returnValue);
/*      */     }
/*  838 */     catch (Exception e2) {
/*      */       
/*  840 */       h.put("version.xalan2_2", "not-present");
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
/*      */   protected void checkParserVersion(Map<String, Object> h) {
/*  856 */     if (null == h) {
/*  857 */       h = new HashMap<>();
/*      */     }
/*      */     
/*      */     try {
/*  861 */       String XERCES1_VERSION_CLASS = "com.sun.org.apache.xerces.internal.framework.Version";
/*      */       
/*  863 */       Class<?> clazz = ObjectFactory.findProviderClass("com.sun.org.apache.xerces.internal.framework.Version", true);
/*      */ 
/*      */       
/*  866 */       Field f = clazz.getField("fVersion");
/*  867 */       String parserVersion = (String)f.get(null);
/*      */       
/*  869 */       h.put("version.xerces1", parserVersion);
/*      */     }
/*  871 */     catch (Exception e) {
/*      */       
/*  873 */       h.put("version.xerces1", "not-present");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  879 */       String XERCES2_VERSION_CLASS = "com.sun.org.apache.xerces.internal.impl.Version";
/*      */       
/*  881 */       Class<?> clazz = ObjectFactory.findProviderClass("com.sun.org.apache.xerces.internal.impl.Version", true);
/*      */ 
/*      */       
/*  884 */       Field f = clazz.getField("fVersion");
/*  885 */       String parserVersion = (String)f.get(null);
/*      */       
/*  887 */       h.put("version.xerces2", parserVersion);
/*      */     }
/*  889 */     catch (Exception e) {
/*      */       
/*  891 */       h.put("version.xerces2", "not-present");
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  896 */       String CRIMSON_CLASS = "org.apache.crimson.parser.Parser2";
/*      */       
/*  898 */       Class<?> clazz = ObjectFactory.findProviderClass("org.apache.crimson.parser.Parser2", true);
/*      */ 
/*      */       
/*  901 */       h.put("version.crimson", "present-unknown-version");
/*      */     }
/*  903 */     catch (Exception e) {
/*      */       
/*  905 */       h.put("version.crimson", "not-present");
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
/*      */   protected void checkAntVersion(Map<String, Object> h) {
/*  917 */     if (null == h) {
/*  918 */       h = new HashMap<>();
/*      */     }
/*      */     
/*      */     try {
/*  922 */       String ANT_VERSION_CLASS = "org.apache.tools.ant.Main";
/*  923 */       String ANT_VERSION_METHOD = "getAntVersion";
/*  924 */       Class[] noArgs = new Class[0];
/*      */       
/*  926 */       Class<?> clazz = ObjectFactory.findProviderClass("org.apache.tools.ant.Main", true);
/*      */       
/*  928 */       Method method = clazz.getMethod("getAntVersion", noArgs);
/*  929 */       Object returnValue = method.invoke(null, new Object[0]);
/*      */       
/*  931 */       h.put("version.ant", returnValue);
/*      */     }
/*  933 */     catch (Exception e) {
/*      */       
/*  935 */       h.put("version.ant", "not-present");
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
/*      */   protected boolean checkDOML3(Map<String, Object> h) {
/*  947 */     if (null == h) {
/*  948 */       h = new HashMap<>();
/*      */     }
/*  950 */     String DOM_CLASS = "org.w3c.dom.Document";
/*  951 */     String DOM_LEVEL3_METHOD = "getDoctype";
/*      */ 
/*      */     
/*      */     try {
/*  955 */       Class<?> clazz = ObjectFactory.findProviderClass("org.w3c.dom.Document", true);
/*      */       
/*  957 */       Method method = clazz.getMethod("getDoctype", (Class[])null);
/*      */ 
/*      */ 
/*      */       
/*  961 */       h.put("version.DOM", "3.0");
/*  962 */       return true;
/*      */     }
/*  964 */     catch (Exception e) {
/*      */       
/*  966 */       return false;
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
/*      */   protected void checkDOMVersion(Map<String, Object> h) {
/*  982 */     if (null == h) {
/*  983 */       h = new HashMap<>();
/*      */     }
/*  985 */     String DOM_LEVEL2_CLASS = "org.w3c.dom.Document";
/*  986 */     String DOM_LEVEL2_METHOD = "createElementNS";
/*  987 */     String DOM_LEVEL3_METHOD = "getDoctype";
/*  988 */     String DOM_LEVEL2WD_CLASS = "org.w3c.dom.Node";
/*  989 */     String DOM_LEVEL2WD_METHOD = "supported";
/*  990 */     String DOM_LEVEL2FD_CLASS = "org.w3c.dom.Node";
/*  991 */     String DOM_LEVEL2FD_METHOD = "isSupported";
/*  992 */     Class[] twoStringArgs = { String.class, String.class };
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  997 */       Class<?> clazz = ObjectFactory.findProviderClass("org.w3c.dom.Document", true);
/*      */       
/*  999 */       Method method = clazz.getMethod("createElementNS", twoStringArgs);
/*      */ 
/*      */ 
/*      */       
/* 1003 */       h.put("version.DOM", "2.0");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1009 */         clazz = ObjectFactory.findProviderClass("org.w3c.dom.Node", true);
/*      */         
/* 1011 */         method = clazz.getMethod("supported", twoStringArgs);
/*      */         
/* 1013 */         h.put("ERROR.version.DOM.draftlevel", "2.0wd");
/* 1014 */         h.put("ERROR.", "At least one error was found!");
/*      */       }
/* 1016 */       catch (Exception e2) {
/*      */         
/*      */         try
/*      */         {
/*      */           
/* 1021 */           clazz = ObjectFactory.findProviderClass("org.w3c.dom.Node", true);
/*      */           
/* 1023 */           method = clazz.getMethod("isSupported", twoStringArgs);
/*      */           
/* 1025 */           h.put("version.DOM.draftlevel", "2.0fd");
/*      */         }
/* 1027 */         catch (Exception e3)
/*      */         {
/* 1029 */           h.put("ERROR.version.DOM.draftlevel", "2.0unknown");
/* 1030 */           h.put("ERROR.", "At least one error was found!");
/*      */         }
/*      */       
/*      */       } 
/* 1034 */     } catch (Exception e) {
/*      */       
/* 1036 */       h.put("ERROR.version.DOM", "ERROR attempting to load DOM level 2 class: " + e
/* 1037 */           .toString());
/* 1038 */       h.put("ERROR.", "At least one error was found!");
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
/*      */   protected void checkSAXVersion(Map<String, Object> h) {
/* 1058 */     if (null == h) {
/* 1059 */       h = new HashMap<>();
/*      */     }
/* 1061 */     String SAX_VERSION1_CLASS = "org.xml.sax.Parser";
/* 1062 */     String SAX_VERSION1_METHOD = "parse";
/* 1063 */     String SAX_VERSION2_CLASS = "org.xml.sax.XMLReader";
/* 1064 */     String SAX_VERSION2_METHOD = "parse";
/* 1065 */     String SAX_VERSION2BETA_CLASSNF = "org.xml.sax.helpers.AttributesImpl";
/* 1066 */     String SAX_VERSION2BETA_METHODNF = "setAttributes";
/* 1067 */     Class[] oneStringArg = { String.class };
/*      */     
/* 1069 */     Class[] attributesArg = { Attributes.class };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1075 */       Class<?> clazz = ObjectFactory.findProviderClass("org.xml.sax.helpers.AttributesImpl", true);
/*      */       
/* 1077 */       Method method = clazz.getMethod("setAttributes", attributesArg);
/*      */ 
/*      */ 
/*      */       
/* 1081 */       h.put("version.SAX", "2.0");
/*      */     }
/* 1083 */     catch (Exception e) {
/*      */ 
/*      */       
/* 1086 */       h.put("ERROR.version.SAX", "ERROR attempting to load SAX version 2 class: " + e
/* 1087 */           .toString());
/* 1088 */       h.put("ERROR.", "At least one error was found!");
/*      */ 
/*      */       
/*      */       try {
/* 1092 */         Class<?> clazz = ObjectFactory.findProviderClass("org.xml.sax.XMLReader", true);
/*      */         
/* 1094 */         Method method = clazz.getMethod("parse", oneStringArg);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1099 */         h.put("version.SAX-backlevel", "2.0beta2-or-earlier");
/*      */       }
/* 1101 */       catch (Exception e2) {
/*      */ 
/*      */         
/* 1104 */         h.put("ERROR.version.SAX", "ERROR attempting to load SAX version 2 class: " + e
/* 1105 */             .toString());
/* 1106 */         h.put("ERROR.", "At least one error was found!");
/*      */ 
/*      */         
/*      */         try {
/* 1110 */           Class<?> clazz = ObjectFactory.findProviderClass("org.xml.sax.Parser", true);
/*      */           
/* 1112 */           Method method = clazz.getMethod("parse", oneStringArg);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1117 */           h.put("version.SAX-backlevel", "1.0");
/*      */         }
/* 1119 */         catch (Exception e3) {
/*      */ 
/*      */ 
/*      */           
/* 1123 */           h.put("ERROR.version.SAX-backlevel", "ERROR attempting to load SAX version 1 class: " + e3
/* 1124 */               .toString());
/*      */         } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/* 1149 */     Map<Long, String> jarVersions = new HashMap<>();
/* 1150 */     jarVersions.put(new Long(857192L), "xalan.jar from xalan-j_1_1");
/* 1151 */     jarVersions.put(new Long(440237L), "xalan.jar from xalan-j_1_2");
/* 1152 */     jarVersions.put(new Long(436094L), "xalan.jar from xalan-j_1_2_1");
/* 1153 */     jarVersions.put(new Long(426249L), "xalan.jar from xalan-j_1_2_2");
/* 1154 */     jarVersions.put(new Long(702536L), "xalan.jar from xalan-j_2_0_0");
/* 1155 */     jarVersions.put(new Long(720930L), "xalan.jar from xalan-j_2_0_1");
/* 1156 */     jarVersions.put(new Long(732330L), "xalan.jar from xalan-j_2_1_0");
/* 1157 */     jarVersions.put(new Long(872241L), "xalan.jar from xalan-j_2_2_D10");
/* 1158 */     jarVersions.put(new Long(882739L), "xalan.jar from xalan-j_2_2_D11");
/* 1159 */     jarVersions.put(new Long(923866L), "xalan.jar from xalan-j_2_2_0");
/* 1160 */     jarVersions.put(new Long(905872L), "xalan.jar from xalan-j_2_3_D1");
/* 1161 */     jarVersions.put(new Long(906122L), "xalan.jar from xalan-j_2_3_0");
/* 1162 */     jarVersions.put(new Long(906248L), "xalan.jar from xalan-j_2_3_1");
/* 1163 */     jarVersions.put(new Long(983377L), "xalan.jar from xalan-j_2_4_D1");
/* 1164 */     jarVersions.put(new Long(997276L), "xalan.jar from xalan-j_2_4_0");
/* 1165 */     jarVersions.put(new Long(1031036L), "xalan.jar from xalan-j_2_4_1");
/*      */ 
/*      */     
/* 1168 */     jarVersions.put(new Long(596540L), "xsltc.jar from xalan-j_2_2_0");
/* 1169 */     jarVersions.put(new Long(590247L), "xsltc.jar from xalan-j_2_3_D1");
/* 1170 */     jarVersions.put(new Long(589914L), "xsltc.jar from xalan-j_2_3_0");
/* 1171 */     jarVersions.put(new Long(589915L), "xsltc.jar from xalan-j_2_3_1");
/* 1172 */     jarVersions.put(new Long(1306667L), "xsltc.jar from xalan-j_2_4_D1");
/* 1173 */     jarVersions.put(new Long(1328227L), "xsltc.jar from xalan-j_2_4_0");
/* 1174 */     jarVersions.put(new Long(1344009L), "xsltc.jar from xalan-j_2_4_1");
/* 1175 */     jarVersions.put(new Long(1348361L), "xsltc.jar from xalan-j_2_5_D1");
/*      */ 
/*      */     
/* 1178 */     jarVersions.put(new Long(1268634L), "xsltc.jar-bundled from xalan-j_2_3_0");
/*      */     
/* 1180 */     jarVersions.put(new Long(100196L), "xml-apis.jar from xalan-j_2_2_0 or xalan-j_2_3_D1");
/* 1181 */     jarVersions.put(new Long(108484L), "xml-apis.jar from xalan-j_2_3_0, or xalan-j_2_3_1 from xml-commons-1.0.b2");
/* 1182 */     jarVersions.put(new Long(109049L), "xml-apis.jar from xalan-j_2_4_0 from xml-commons RIVERCOURT1 branch");
/* 1183 */     jarVersions.put(new Long(113749L), "xml-apis.jar from xalan-j_2_4_1 from factoryfinder-build of xml-commons RIVERCOURT1");
/* 1184 */     jarVersions.put(new Long(124704L), "xml-apis.jar from tck-jaxp-1_2_0 branch of xml-commons");
/* 1185 */     jarVersions.put(new Long(124724L), "xml-apis.jar from tck-jaxp-1_2_0 branch of xml-commons, tag: xml-commons-external_1_2_01");
/* 1186 */     jarVersions.put(new Long(194205L), "xml-apis.jar from head branch of xml-commons, tag: xml-commons-external_1_3_02");
/*      */ 
/*      */ 
/*      */     
/* 1190 */     jarVersions.put(new Long(424490L), "xalan.jar from Xerces Tools releases - ERROR:DO NOT USE!");
/*      */     
/* 1192 */     jarVersions.put(new Long(1591855L), "xerces.jar from xalan-j_1_1 from xerces-1...");
/* 1193 */     jarVersions.put(new Long(1498679L), "xerces.jar from xalan-j_1_2 from xerces-1_2_0.bin");
/* 1194 */     jarVersions.put(new Long(1484896L), "xerces.jar from xalan-j_1_2_1 from xerces-1_2_1.bin");
/* 1195 */     jarVersions.put(new Long(804460L), "xerces.jar from xalan-j_1_2_2 from xerces-1_2_2.bin");
/* 1196 */     jarVersions.put(new Long(1499244L), "xerces.jar from xalan-j_2_0_0 from xerces-1_2_3.bin");
/* 1197 */     jarVersions.put(new Long(1605266L), "xerces.jar from xalan-j_2_0_1 from xerces-1_3_0.bin");
/* 1198 */     jarVersions.put(new Long(904030L), "xerces.jar from xalan-j_2_1_0 from xerces-1_4.bin");
/* 1199 */     jarVersions.put(new Long(904030L), "xerces.jar from xerces-1_4_0.bin");
/* 1200 */     jarVersions.put(new Long(1802885L), "xerces.jar from xerces-1_4_2.bin");
/* 1201 */     jarVersions.put(new Long(1734594L), "xerces.jar from Xerces-J-bin.2.0.0.beta3");
/* 1202 */     jarVersions.put(new Long(1808883L), "xerces.jar from xalan-j_2_2_D10,D11,D12 or xerces-1_4_3.bin");
/* 1203 */     jarVersions.put(new Long(1812019L), "xerces.jar from xalan-j_2_2_0");
/* 1204 */     jarVersions.put(new Long(1720292L), "xercesImpl.jar from xalan-j_2_3_D1");
/* 1205 */     jarVersions.put(new Long(1730053L), "xercesImpl.jar from xalan-j_2_3_0 or xalan-j_2_3_1 from xerces-2_0_0");
/* 1206 */     jarVersions.put(new Long(1728861L), "xercesImpl.jar from xalan-j_2_4_D1 from xerces-2_0_1");
/* 1207 */     jarVersions.put(new Long(972027L), "xercesImpl.jar from xalan-j_2_4_0 from xerces-2_1");
/* 1208 */     jarVersions.put(new Long(831587L), "xercesImpl.jar from xalan-j_2_4_1 from xerces-2_2");
/* 1209 */     jarVersions.put(new Long(891817L), "xercesImpl.jar from xalan-j_2_5_D1 from xerces-2_3");
/* 1210 */     jarVersions.put(new Long(895924L), "xercesImpl.jar from xerces-2_4");
/* 1211 */     jarVersions.put(new Long(1010806L), "xercesImpl.jar from Xerces-J-bin.2.6.2");
/* 1212 */     jarVersions.put(new Long(1203860L), "xercesImpl.jar from Xerces-J-bin.2.7.1");
/*      */     
/* 1214 */     jarVersions.put(new Long(37485L), "xalanj1compat.jar from xalan-j_2_0_0");
/* 1215 */     jarVersions.put(new Long(38100L), "xalanj1compat.jar from xalan-j_2_0_1");
/*      */     
/* 1217 */     jarVersions.put(new Long(18779L), "xalanservlet.jar from xalan-j_2_0_0");
/* 1218 */     jarVersions.put(new Long(21453L), "xalanservlet.jar from xalan-j_2_0_1");
/* 1219 */     jarVersions.put(new Long(24826L), "xalanservlet.jar from xalan-j_2_3_1 or xalan-j_2_4_1");
/* 1220 */     jarVersions.put(new Long(24831L), "xalanservlet.jar from xalan-j_2_4_1");
/*      */ 
/*      */ 
/*      */     
/* 1224 */     jarVersions.put(new Long(5618L), "jaxp.jar from jaxp1.0.1");
/* 1225 */     jarVersions.put(new Long(136133L), "parser.jar from jaxp1.0.1");
/* 1226 */     jarVersions.put(new Long(28404L), "jaxp.jar from jaxp-1.1");
/* 1227 */     jarVersions.put(new Long(187162L), "crimson.jar from jaxp-1.1");
/* 1228 */     jarVersions.put(new Long(801714L), "xalan.jar from jaxp-1.1");
/* 1229 */     jarVersions.put(new Long(196399L), "crimson.jar from crimson-1.1.1");
/* 1230 */     jarVersions.put(new Long(33323L), "jaxp.jar from crimson-1.1.1 or jakarta-ant-1.4.1b1");
/* 1231 */     jarVersions.put(new Long(152717L), "crimson.jar from crimson-1.1.2beta2");
/* 1232 */     jarVersions.put(new Long(88143L), "xml-apis.jar from crimson-1.1.2beta2");
/* 1233 */     jarVersions.put(new Long(206384L), "crimson.jar from crimson-1.1.3 or jakarta-ant-1.4.1b1");
/*      */ 
/*      */     
/* 1236 */     jarVersions.put(new Long(136198L), "parser.jar from jakarta-ant-1.3 or 1.2");
/* 1237 */     jarVersions.put(new Long(5537L), "jaxp.jar from jakarta-ant-1.3 or 1.2");
/*      */     
/* 1239 */     JARVERSIONS = Collections.unmodifiableMap(jarVersions);
/*      */   }
/*      */ 
/*      */   
/* 1243 */   protected PrintWriter outWriter = new PrintWriter(System.out, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void logMsg(String s) {
/* 1251 */     this.outWriter.println(s);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xslt/EnvironmentCheck.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */