/*      */ package com.sun.org.apache.xalan.internal.xslt;
/*      */ 
/*      */ import com.sun.org.apache.xalan.internal.Version;
/*      */ import com.sun.org.apache.xalan.internal.res.XSLMessages;
/*      */ import com.sun.org.apache.xalan.internal.utils.ConfigurationError;
/*      */ import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
/*      */ import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
/*      */ import com.sun.org.apache.xml.internal.utils.DefaultErrorHandler;
/*      */ import com.sun.org.apache.xml.internal.utils.WrappedRuntimeException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringReader;
/*      */ import java.io.Writer;
/*      */ import java.util.Properties;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Vector;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.parsers.FactoryConfigurationError;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import javax.xml.parsers.SAXParser;
/*      */ import javax.xml.parsers.SAXParserFactory;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.Templates;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerConfigurationException;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import javax.xml.transform.TransformerFactory;
/*      */ import javax.xml.transform.TransformerFactoryConfigurationError;
/*      */ import javax.xml.transform.URIResolver;
/*      */ import javax.xml.transform.dom.DOMResult;
/*      */ import javax.xml.transform.dom.DOMSource;
/*      */ import javax.xml.transform.sax.SAXResult;
/*      */ import javax.xml.transform.sax.SAXSource;
/*      */ import javax.xml.transform.sax.SAXTransformerFactory;
/*      */ import javax.xml.transform.sax.TransformerHandler;
/*      */ import javax.xml.transform.stream.StreamResult;
/*      */ import javax.xml.transform.stream.StreamSource;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.DocumentFragment;
/*      */ import org.w3c.dom.Node;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.EntityResolver;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXNotRecognizedException;
/*      */ import org.xml.sax.SAXNotSupportedException;
/*      */ import org.xml.sax.XMLReader;
/*      */ import org.xml.sax.helpers.XMLReaderFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Process
/*      */ {
/*      */   protected static void printArgOptions(ResourceBundle resbundle) {
/*   93 */     System.out.println(resbundle.getString("xslProc_option"));
/*   94 */     System.out.println("\n\t\t\t" + resbundle.getString("xslProc_common_options") + "\n");
/*   95 */     System.out.println(resbundle.getString("optionXSLTC"));
/*   96 */     System.out.println(resbundle.getString("optionIN"));
/*   97 */     System.out.println(resbundle.getString("optionXSL"));
/*   98 */     System.out.println(resbundle.getString("optionOUT"));
/*      */ 
/*      */     
/*  101 */     System.out.println(resbundle.getString("optionV"));
/*      */ 
/*      */     
/*  104 */     System.out.println(resbundle.getString("optionEDUMP"));
/*  105 */     System.out.println(resbundle.getString("optionXML"));
/*  106 */     System.out.println(resbundle.getString("optionTEXT"));
/*  107 */     System.out.println(resbundle.getString("optionHTML"));
/*  108 */     System.out.println(resbundle.getString("optionPARAM"));
/*      */     
/*  110 */     System.out.println(resbundle.getString("optionMEDIA"));
/*  111 */     System.out.println(resbundle.getString("optionFLAVOR"));
/*  112 */     System.out.println(resbundle.getString("optionDIAG"));
/*  113 */     System.out.println(resbundle.getString("optionURIRESOLVER"));
/*  114 */     System.out.println(resbundle.getString("optionENTITYRESOLVER"));
/*  115 */     waitForReturnKey(resbundle);
/*  116 */     System.out.println(resbundle.getString("optionCONTENTHANDLER"));
/*  117 */     System.out.println(resbundle.getString("optionSECUREPROCESSING"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  137 */     System.out.println("\n\t\t\t" + resbundle.getString("xslProc_xsltc_options") + "\n");
/*  138 */     System.out.println(resbundle.getString("optionXO"));
/*  139 */     waitForReturnKey(resbundle);
/*  140 */     System.out.println(resbundle.getString("optionXD"));
/*  141 */     System.out.println(resbundle.getString("optionXJ"));
/*  142 */     System.out.println(resbundle.getString("optionXP"));
/*  143 */     System.out.println(resbundle.getString("optionXN"));
/*  144 */     System.out.println(resbundle.getString("optionXX"));
/*  145 */     System.out.println(resbundle.getString("optionXT"));
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
/*      */   public static void _main(String[] argv) {
/*  169 */     boolean doStackDumpOnError = false;
/*  170 */     boolean setQuietMode = false;
/*  171 */     boolean doDiag = false;
/*  172 */     String msg = null;
/*  173 */     boolean isSecureProcessing = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  181 */     PrintWriter diagnosticsWriter = new PrintWriter(System.err, true);
/*  182 */     PrintWriter dumpWriter = diagnosticsWriter;
/*      */     
/*  184 */     ResourceBundle resbundle = SecuritySupport.getResourceBundle("com.sun.org.apache.xalan.internal.res.XSLTErrorResources");
/*      */     
/*  186 */     String flavor = "s2s";
/*      */     
/*  188 */     if (argv.length < 1) {
/*      */       
/*  190 */       printArgOptions(resbundle);
/*      */     } else {
/*      */       TransformerFactory tfactory;
/*      */ 
/*      */ 
/*      */       
/*  196 */       boolean useXSLTC = true;
/*  197 */       for (int i = 0; i < argv.length; i++) {
/*      */         
/*  199 */         if ("-XSLTC".equalsIgnoreCase(argv[i]))
/*      */         {
/*  201 */           useXSLTC = true;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  206 */       if (useXSLTC) {
/*      */         
/*  208 */         String key = "javax.xml.transform.TransformerFactory";
/*  209 */         String value = "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl";
/*  210 */         Properties props = System.getProperties();
/*  211 */         props.put(key, value);
/*  212 */         System.setProperties(props);
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  217 */         tfactory = TransformerFactory.newInstance();
/*  218 */         tfactory.setErrorListener(new DefaultErrorHandler());
/*      */       }
/*  220 */       catch (TransformerFactoryConfigurationError pfe) {
/*      */         
/*  222 */         pfe.printStackTrace(dumpWriter);
/*      */         
/*  224 */         msg = XSLMessages.createMessage("ER_NOT_SUCCESSFUL", null);
/*      */         
/*  226 */         diagnosticsWriter.println(msg);
/*      */         
/*  228 */         tfactory = null;
/*      */         
/*  230 */         doExit(msg);
/*      */       } 
/*      */       
/*  233 */       boolean formatOutput = false;
/*  234 */       boolean useSourceLocation = false;
/*  235 */       String inFileName = null;
/*  236 */       String outFileName = null;
/*  237 */       String dumpFileName = null;
/*  238 */       String xslFileName = null;
/*  239 */       String treedumpFileName = null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  244 */       String outputType = null;
/*  245 */       String media = null;
/*  246 */       Vector<String> params = new Vector();
/*  247 */       boolean quietConflictWarnings = false;
/*  248 */       URIResolver uriResolver = null;
/*  249 */       EntityResolver entityResolver = null;
/*  250 */       ContentHandler contentHandler = null;
/*  251 */       int recursionLimit = -1;
/*      */       
/*  253 */       for (int j = 0; j < argv.length; j++) {
/*      */         
/*  255 */         if (!"-XSLTC".equalsIgnoreCase(argv[j]))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  318 */           if ("-INDENT".equalsIgnoreCase(argv[j])) {
/*      */ 
/*      */ 
/*      */             
/*  322 */             if (j + 1 < argv.length && argv[j + 1].charAt(0) != '-')
/*      */             {
/*  324 */               int indentAmount = Integer.parseInt(argv[++j]);
/*      */             }
/*      */             else
/*      */             {
/*  328 */               boolean bool = false;
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*  334 */           else if ("-IN".equalsIgnoreCase(argv[j])) {
/*      */             
/*  336 */             if (j + 1 < argv.length && argv[j + 1].charAt(0) != '-') {
/*  337 */               inFileName = argv[++j];
/*      */             } else {
/*  339 */               System.err.println(
/*  340 */                   XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-IN" }));
/*      */             }
/*      */           
/*      */           }
/*  344 */           else if ("-MEDIA".equalsIgnoreCase(argv[j])) {
/*      */             
/*  346 */             if (j + 1 < argv.length) {
/*  347 */               media = argv[++j];
/*      */             } else {
/*  349 */               System.err.println(
/*  350 */                   XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-MEDIA" }));
/*      */             }
/*      */           
/*      */           }
/*  354 */           else if ("-OUT".equalsIgnoreCase(argv[j])) {
/*      */             
/*  356 */             if (j + 1 < argv.length && argv[j + 1].charAt(0) != '-') {
/*  357 */               outFileName = argv[++j];
/*      */             } else {
/*  359 */               System.err.println(
/*  360 */                   XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-OUT" }));
/*      */             }
/*      */           
/*      */           }
/*  364 */           else if ("-XSL".equalsIgnoreCase(argv[j])) {
/*      */             
/*  366 */             if (j + 1 < argv.length && argv[j + 1].charAt(0) != '-') {
/*  367 */               xslFileName = argv[++j];
/*      */             } else {
/*  369 */               System.err.println(
/*  370 */                   XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-XSL" }));
/*      */             }
/*      */           
/*      */           }
/*  374 */           else if ("-FLAVOR".equalsIgnoreCase(argv[j])) {
/*      */             
/*  376 */             if (j + 1 < argv.length) {
/*      */               
/*  378 */               flavor = argv[++j];
/*      */             } else {
/*      */               
/*  381 */               System.err.println(
/*  382 */                   XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-FLAVOR" }));
/*      */             }
/*      */           
/*      */           }
/*  386 */           else if ("-PARAM".equalsIgnoreCase(argv[j])) {
/*      */             
/*  388 */             if (j + 2 < argv.length) {
/*      */               
/*  390 */               String name = argv[++j];
/*      */               
/*  392 */               params.addElement(name);
/*      */               
/*  394 */               String expression = argv[++j];
/*      */               
/*  396 */               params.addElement(expression);
/*      */             } else {
/*      */               
/*  399 */               System.err.println(
/*  400 */                   XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-PARAM" }));
/*      */             }
/*      */           
/*      */           }
/*  404 */           else if (!"-E".equalsIgnoreCase(argv[j])) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  410 */             if ("-V".equalsIgnoreCase(argv[j])) {
/*      */               
/*  412 */               diagnosticsWriter.println(resbundle.getString("version") + 
/*  413 */                   Version.getVersion() + ", " + resbundle
/*      */ 
/*      */                   
/*  416 */                   .getString("version2"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*  428 */             else if ("-Q".equalsIgnoreCase(argv[j])) {
/*      */               
/*  430 */               setQuietMode = true;
/*      */             }
/*  432 */             else if ("-DIAG".equalsIgnoreCase(argv[j])) {
/*      */               
/*  434 */               doDiag = true;
/*      */             }
/*  436 */             else if ("-XML".equalsIgnoreCase(argv[j])) {
/*      */               
/*  438 */               outputType = "xml";
/*      */             }
/*  440 */             else if ("-TEXT".equalsIgnoreCase(argv[j])) {
/*      */               
/*  442 */               outputType = "text";
/*      */             }
/*  444 */             else if ("-HTML".equalsIgnoreCase(argv[j])) {
/*      */               
/*  446 */               outputType = "html";
/*      */             }
/*  448 */             else if ("-EDUMP".equalsIgnoreCase(argv[j])) {
/*      */               
/*  450 */               doStackDumpOnError = true;
/*      */               
/*  452 */               if (j + 1 < argv.length && argv[j + 1].charAt(0) != '-')
/*      */               {
/*  454 */                 dumpFileName = argv[++j];
/*      */               }
/*      */             }
/*  457 */             else if ("-URIRESOLVER".equalsIgnoreCase(argv[j])) {
/*      */               
/*  459 */               if (j + 1 < argv.length) {
/*      */                 
/*      */                 try
/*      */                 {
/*  463 */                   uriResolver = (URIResolver)ObjectFactory.newInstance(argv[++j], true);
/*      */                   
/*  465 */                   tfactory.setURIResolver(uriResolver);
/*      */                 }
/*  467 */                 catch (ConfigurationError cnfe)
/*      */                 {
/*  469 */                   msg = XSLMessages.createMessage("ER_CLASS_NOT_FOUND_FOR_OPTION", new Object[] { "-URIResolver" });
/*      */ 
/*      */                   
/*  472 */                   System.err.println(msg);
/*  473 */                   doExit(msg);
/*      */                 }
/*      */               
/*      */               } else {
/*      */                 
/*  478 */                 msg = XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-URIResolver" });
/*      */ 
/*      */                 
/*  481 */                 System.err.println(msg);
/*  482 */                 doExit(msg);
/*      */               }
/*      */             
/*  485 */             } else if ("-ENTITYRESOLVER".equalsIgnoreCase(argv[j])) {
/*      */               
/*  487 */               if (j + 1 < argv.length)
/*      */               {
/*      */                 try
/*      */                 {
/*  491 */                   entityResolver = (EntityResolver)ObjectFactory.newInstance(argv[++j], true);
/*      */                 }
/*  493 */                 catch (ConfigurationError cnfe)
/*      */                 {
/*  495 */                   msg = XSLMessages.createMessage("ER_CLASS_NOT_FOUND_FOR_OPTION", new Object[] { "-EntityResolver" });
/*      */ 
/*      */                   
/*  498 */                   System.err.println(msg);
/*  499 */                   doExit(msg);
/*      */                 }
/*      */               
/*      */               }
/*      */               else
/*      */               {
/*  505 */                 msg = XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-EntityResolver" });
/*      */ 
/*      */                 
/*  508 */                 System.err.println(msg);
/*  509 */                 doExit(msg);
/*      */               }
/*      */             
/*  512 */             } else if ("-CONTENTHANDLER".equalsIgnoreCase(argv[j])) {
/*      */               
/*  514 */               if (j + 1 < argv.length)
/*      */               {
/*      */                 try
/*      */                 {
/*  518 */                   contentHandler = (ContentHandler)ObjectFactory.newInstance(argv[++j], true);
/*      */                 }
/*  520 */                 catch (ConfigurationError cnfe)
/*      */                 {
/*  522 */                   msg = XSLMessages.createMessage("ER_CLASS_NOT_FOUND_FOR_OPTION", new Object[] { "-ContentHandler" });
/*      */ 
/*      */                   
/*  525 */                   System.err.println(msg);
/*  526 */                   doExit(msg);
/*      */                 }
/*      */               
/*      */               }
/*      */               else
/*      */               {
/*  532 */                 msg = XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-ContentHandler" });
/*      */ 
/*      */                 
/*  535 */                 System.err.println(msg);
/*  536 */                 doExit(msg);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*  594 */             else if ("-XO".equalsIgnoreCase(argv[j])) {
/*      */               
/*  596 */               if (useXSLTC) {
/*      */                 
/*  598 */                 if (j + 1 < argv.length && argv[j + 1].charAt(0) != '-') {
/*      */                   
/*  600 */                   tfactory.setAttribute("generate-translet", "true");
/*  601 */                   tfactory.setAttribute("translet-name", argv[++j]);
/*      */                 } else {
/*      */                   
/*  604 */                   tfactory.setAttribute("generate-translet", "true");
/*      */                 } 
/*      */               } else {
/*      */                 
/*  608 */                 if (j + 1 < argv.length && argv[j + 1].charAt(0) != '-')
/*  609 */                   j++; 
/*  610 */                 printInvalidXalanOption("-XO");
/*      */               }
/*      */             
/*      */             }
/*  614 */             else if ("-XD".equalsIgnoreCase(argv[j])) {
/*      */               
/*  616 */               if (useXSLTC)
/*      */               {
/*  618 */                 if (j + 1 < argv.length && argv[j + 1].charAt(0) != '-') {
/*  619 */                   tfactory.setAttribute("destination-directory", argv[++j]);
/*      */                 } else {
/*  621 */                   System.err.println(
/*  622 */                       XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-XD" }));
/*      */                 
/*      */                 }
/*      */               
/*      */               }
/*      */               else
/*      */               {
/*  629 */                 if (j + 1 < argv.length && argv[j + 1].charAt(0) != '-') {
/*  630 */                   j++;
/*      */                 }
/*  632 */                 printInvalidXalanOption("-XD");
/*      */               }
/*      */             
/*      */             }
/*  636 */             else if ("-XJ".equalsIgnoreCase(argv[j])) {
/*      */               
/*  638 */               if (useXSLTC)
/*      */               {
/*  640 */                 if (j + 1 < argv.length && argv[j + 1].charAt(0) != '-') {
/*      */                   
/*  642 */                   tfactory.setAttribute("generate-translet", "true");
/*  643 */                   tfactory.setAttribute("jar-name", argv[++j]);
/*      */                 } else {
/*      */                   
/*  646 */                   System.err.println(
/*  647 */                       XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-XJ" }));
/*      */                 }
/*      */               
/*      */               }
/*      */               else
/*      */               {
/*  653 */                 if (j + 1 < argv.length && argv[j + 1].charAt(0) != '-') {
/*  654 */                   j++;
/*      */                 }
/*  656 */                 printInvalidXalanOption("-XJ");
/*      */               
/*      */               }
/*      */             
/*      */             }
/*  661 */             else if ("-XP".equalsIgnoreCase(argv[j])) {
/*      */               
/*  663 */               if (useXSLTC)
/*      */               {
/*  665 */                 if (j + 1 < argv.length && argv[j + 1].charAt(0) != '-') {
/*  666 */                   tfactory.setAttribute("package-name", argv[++j]);
/*      */                 } else {
/*  668 */                   System.err.println(
/*  669 */                       XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-XP" }));
/*      */                 }
/*      */               
/*      */               }
/*      */               else
/*      */               {
/*  675 */                 if (j + 1 < argv.length && argv[j + 1].charAt(0) != '-') {
/*  676 */                   j++;
/*      */                 }
/*  678 */                 printInvalidXalanOption("-XP");
/*      */               
/*      */               }
/*      */             
/*      */             }
/*  683 */             else if ("-XN".equalsIgnoreCase(argv[j])) {
/*      */               
/*  685 */               if (useXSLTC) {
/*      */                 
/*  687 */                 tfactory.setAttribute("enable-inlining", "true");
/*      */               } else {
/*      */                 
/*  690 */                 printInvalidXalanOption("-XN");
/*      */               }
/*      */             
/*  693 */             } else if ("-XX".equalsIgnoreCase(argv[j])) {
/*      */               
/*  695 */               if (useXSLTC) {
/*      */                 
/*  697 */                 tfactory.setAttribute("debug", "true");
/*      */               } else {
/*      */                 
/*  700 */                 printInvalidXalanOption("-XX");
/*      */               }
/*      */             
/*      */             }
/*  704 */             else if ("-XT".equalsIgnoreCase(argv[j])) {
/*      */               
/*  706 */               if (useXSLTC) {
/*      */                 
/*  708 */                 tfactory.setAttribute("auto-translet", "true");
/*      */               } else {
/*      */                 
/*  711 */                 printInvalidXalanOption("-XT");
/*      */               } 
/*  713 */             } else if ("-SECURE".equalsIgnoreCase(argv[j])) {
/*      */               
/*  715 */               isSecureProcessing = true;
/*      */               
/*      */               try {
/*  718 */                 tfactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*      */               }
/*  720 */               catch (TransformerConfigurationException transformerConfigurationException) {}
/*      */             } else {
/*      */               
/*  723 */               System.err.println(
/*  724 */                   XSLMessages.createMessage("ER_INVALID_OPTION", new Object[] { argv[j] }));
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*  729 */       if (inFileName == null && xslFileName == null) {
/*      */         
/*  731 */         msg = resbundle.getString("xslProc_no_input");
/*  732 */         System.err.println(msg);
/*  733 */         doExit(msg);
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*      */         StreamResult strResult;
/*      */         
/*  740 */         long start = System.currentTimeMillis();
/*      */         
/*  742 */         if (null != dumpFileName)
/*      */         {
/*  744 */           dumpWriter = new PrintWriter(new FileWriter(dumpFileName));
/*      */         }
/*      */         
/*  747 */         Templates stylesheet = null;
/*      */         
/*  749 */         if (null != xslFileName)
/*      */         {
/*  751 */           if (flavor.equals("d2d")) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  756 */             DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
/*      */             
/*  758 */             dfactory.setNamespaceAware(true);
/*      */             
/*  760 */             if (isSecureProcessing) {
/*      */               
/*      */               try {
/*      */                 
/*  764 */                 dfactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*      */               }
/*  766 */               catch (ParserConfigurationException parserConfigurationException) {}
/*      */             }
/*      */             
/*  769 */             DocumentBuilder docBuilder = dfactory.newDocumentBuilder();
/*  770 */             Node xslDOM = docBuilder.parse(new InputSource(xslFileName));
/*      */             
/*  772 */             stylesheet = tfactory.newTemplates(new DOMSource(xslDOM, xslFileName));
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/*  778 */             stylesheet = tfactory.newTemplates(new StreamSource(xslFileName));
/*      */           } 
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  786 */         if (null != outFileName) {
/*      */           
/*  788 */           strResult = new StreamResult(new FileOutputStream(outFileName));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  793 */           strResult.setSystemId(outFileName);
/*      */         }
/*      */         else {
/*      */           
/*  797 */           strResult = new StreamResult(System.out);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  804 */         SAXTransformerFactory stf = (SAXTransformerFactory)tfactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  815 */         if (null == stylesheet) {
/*      */ 
/*      */           
/*  818 */           Source source = stf.getAssociatedStylesheet(new StreamSource(inFileName), media, null, null);
/*      */ 
/*      */           
/*  821 */           if (null != source) {
/*  822 */             stylesheet = tfactory.newTemplates(source);
/*      */           } else {
/*      */             
/*  825 */             if (null != media) {
/*  826 */               throw new TransformerException(XSLMessages.createMessage("ER_NO_STYLESHEET_IN_MEDIA", new Object[] { inFileName, media }));
/*      */             }
/*      */ 
/*      */             
/*  830 */             throw new TransformerException(XSLMessages.createMessage("ER_NO_STYLESHEET_PI", new Object[] { inFileName }));
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  835 */         if (null != stylesheet) {
/*      */           
/*  837 */           Transformer transformer = flavor.equals("th") ? null : stylesheet.newTransformer();
/*  838 */           transformer.setErrorListener(new DefaultErrorHandler());
/*      */ 
/*      */           
/*  841 */           if (null != outputType)
/*      */           {
/*  843 */             transformer.setOutputProperty("method", outputType);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  870 */           int nParams = params.size();
/*      */           int k;
/*  872 */           for (k = 0; k < nParams; k += 2)
/*      */           {
/*  874 */             transformer.setParameter(params.elementAt(k), params
/*  875 */                 .elementAt(k + 1));
/*      */           }
/*      */           
/*  878 */           if (uriResolver != null) {
/*  879 */             transformer.setURIResolver(uriResolver);
/*      */           }
/*  881 */           if (null != inFileName)
/*      */           {
/*  883 */             if (flavor.equals("d2d")) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  888 */               DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
/*      */               
/*  890 */               dfactory.setCoalescing(true);
/*  891 */               dfactory.setNamespaceAware(true);
/*      */               
/*  893 */               if (isSecureProcessing) {
/*      */                 
/*      */                 try {
/*      */                   
/*  897 */                   dfactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*      */                 }
/*  899 */                 catch (ParserConfigurationException parserConfigurationException) {}
/*      */               }
/*      */               
/*  902 */               DocumentBuilder docBuilder = dfactory.newDocumentBuilder();
/*      */               
/*  904 */               if (entityResolver != null) {
/*  905 */                 docBuilder.setEntityResolver(entityResolver);
/*      */               }
/*  907 */               Node xmlDoc = docBuilder.parse(new InputSource(inFileName));
/*  908 */               Document doc = docBuilder.newDocument();
/*      */               
/*  910 */               DocumentFragment outNode = doc.createDocumentFragment();
/*      */               
/*  912 */               transformer.transform(new DOMSource(xmlDoc, inFileName), new DOMResult(outNode));
/*      */ 
/*      */ 
/*      */               
/*  916 */               Transformer serializer = stf.newTransformer();
/*  917 */               serializer.setErrorListener(new DefaultErrorHandler());
/*      */ 
/*      */               
/*  920 */               Properties serializationProps = stylesheet.getOutputProperties();
/*      */               
/*  922 */               serializer.setOutputProperties(serializationProps);
/*      */               
/*  924 */               if (contentHandler != null) {
/*      */                 
/*  926 */                 SAXResult result = new SAXResult(contentHandler);
/*      */                 
/*  928 */                 serializer.transform(new DOMSource(outNode), result);
/*      */               } else {
/*      */                 
/*  931 */                 serializer.transform(new DOMSource(outNode), strResult);
/*      */               } 
/*  933 */             } else if (flavor.equals("th")) {
/*      */               
/*  935 */               for (k = 0; k < 1; k++)
/*      */               {
/*      */ 
/*      */                 
/*  939 */                 XMLReader reader = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  945 */                 try { SAXParserFactory factory = SAXParserFactory.newInstance();
/*      */                   
/*  947 */                   factory.setNamespaceAware(true);
/*      */                   
/*  949 */                   if (isSecureProcessing) {
/*      */                     
/*      */                     try {
/*      */                       
/*  953 */                       factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*      */                     }
/*  955 */                     catch (SAXException sAXException) {}
/*      */                   }
/*      */ 
/*      */                   
/*  959 */                   SAXParser jaxpParser = factory.newSAXParser();
/*      */                   
/*  961 */                   reader = jaxpParser.getXMLReader(); }
/*      */                 
/*  963 */                 catch (ParserConfigurationException ex)
/*      */                 
/*  965 */                 { throw new SAXException(ex); }
/*      */                 
/*  967 */                 catch (FactoryConfigurationError ex1)
/*      */                 
/*  969 */                 { throw new SAXException(ex1.toString()); }
/*      */                 
/*  971 */                 catch (NoSuchMethodError noSuchMethodError) {  }
/*  972 */                 catch (AbstractMethodError abstractMethodError) {}
/*      */                 
/*  974 */                 if (null == reader)
/*      */                 {
/*  976 */                   reader = XMLReaderFactory.createXMLReader();
/*      */                 }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  986 */                 TransformerHandler th = stf.newTransformerHandler(stylesheet);
/*      */                 
/*  988 */                 reader.setContentHandler(th);
/*  989 */                 reader.setDTDHandler(th);
/*      */                 
/*  991 */                 if (th instanceof ErrorHandler) {
/*  992 */                   reader.setErrorHandler((ErrorHandler)th);
/*      */                 }
/*      */ 
/*      */                 
/*  996 */                 try { reader.setProperty("http://xml.org/sax/properties/lexical-handler", th);
/*      */                    }
/*      */                 
/*  999 */                 catch (SAXNotRecognizedException sAXNotRecognizedException) {  }
/* 1000 */                 catch (SAXNotSupportedException sAXNotSupportedException) {}
/*      */                 
/*      */                 try {
/* 1003 */                   reader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
/*      */                 }
/* 1005 */                 catch (SAXException sAXException) {}
/*      */                 
/* 1007 */                 th.setResult(strResult);
/*      */                 
/* 1009 */                 reader.parse(new InputSource(inFileName));
/*      */               
/*      */               }
/*      */             
/*      */             }
/* 1014 */             else if (entityResolver != null) {
/*      */               
/* 1016 */               XMLReader reader = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1022 */               try { SAXParserFactory factory = SAXParserFactory.newInstance();
/*      */                 
/* 1024 */                 factory.setNamespaceAware(true);
/*      */                 
/* 1026 */                 if (isSecureProcessing) {
/*      */                   
/*      */                   try {
/*      */                     
/* 1030 */                     factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*      */                   }
/* 1032 */                   catch (SAXException sAXException) {}
/*      */                 }
/*      */ 
/*      */                 
/* 1036 */                 SAXParser jaxpParser = factory.newSAXParser();
/*      */                 
/* 1038 */                 reader = jaxpParser.getXMLReader(); }
/*      */               
/* 1040 */               catch (ParserConfigurationException ex)
/*      */               
/* 1042 */               { throw new SAXException(ex); }
/*      */               
/* 1044 */               catch (FactoryConfigurationError ex1)
/*      */               
/* 1046 */               { throw new SAXException(ex1.toString()); }
/*      */               
/* 1048 */               catch (NoSuchMethodError noSuchMethodError) {  }
/* 1049 */               catch (AbstractMethodError abstractMethodError) {}
/*      */               
/* 1051 */               if (null == reader)
/*      */               {
/* 1053 */                 reader = XMLReaderFactory.createXMLReader();
/*      */               }
/*      */               
/* 1056 */               reader.setEntityResolver(entityResolver);
/*      */               
/* 1058 */               if (contentHandler != null)
/*      */               {
/* 1060 */                 SAXResult result = new SAXResult(contentHandler);
/*      */                 
/* 1062 */                 transformer.transform(new SAXSource(reader, new InputSource(inFileName)), result);
/*      */               
/*      */               }
/*      */               else
/*      */               {
/*      */                 
/* 1068 */                 transformer.transform(new SAXSource(reader, new InputSource(inFileName)), strResult);
/*      */               
/*      */               }
/*      */             
/*      */             }
/* 1073 */             else if (contentHandler != null) {
/*      */               
/* 1075 */               SAXResult result = new SAXResult(contentHandler);
/*      */               
/* 1077 */               transformer.transform(new StreamSource(inFileName), result);
/*      */             
/*      */             }
/*      */             else {
/*      */               
/* 1082 */               transformer.transform(new StreamSource(inFileName), strResult);
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */           else
/*      */           {
/* 1090 */             StringReader reader = new StringReader("<?xml version=\"1.0\"?> <doc/>");
/*      */ 
/*      */             
/* 1093 */             transformer.transform(new StreamSource(reader), strResult);
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1099 */           msg = XSLMessages.createMessage("ER_NOT_SUCCESSFUL", null);
/*      */           
/* 1101 */           diagnosticsWriter.println(msg);
/* 1102 */           doExit(msg);
/*      */         } 
/*      */ 
/*      */         
/* 1106 */         if (null != outFileName && strResult != null) {
/*      */           
/* 1108 */           OutputStream out = strResult.getOutputStream();
/* 1109 */           Writer writer = strResult.getWriter();
/*      */           
/*      */           try {
/* 1112 */             if (out != null) out.close(); 
/* 1113 */             if (writer != null) writer.close();
/*      */           
/* 1115 */           } catch (IOException iOException) {}
/*      */         } 
/*      */         
/* 1118 */         long stop = System.currentTimeMillis();
/* 1119 */         long millisecondsDuration = stop - start;
/*      */         
/* 1121 */         if (doDiag)
/*      */         {
/* 1123 */           Object[] msgArgs = { inFileName, xslFileName, new Long(millisecondsDuration) };
/* 1124 */           msg = XSLMessages.createMessage("diagTiming", msgArgs);
/* 1125 */           diagnosticsWriter.println('\n');
/* 1126 */           diagnosticsWriter.println(msg);
/*      */         }
/*      */       
/*      */       }
/* 1130 */       catch (Throwable throwable) {
/*      */         
/* 1132 */         while (throwable instanceof WrappedRuntimeException)
/*      */         {
/*      */ 
/*      */           
/* 1136 */           throwable = ((WrappedRuntimeException)throwable).getException();
/*      */         }
/*      */         
/* 1139 */         if (throwable instanceof NullPointerException || throwable instanceof ClassCastException)
/*      */         {
/* 1141 */           doStackDumpOnError = true;
/*      */         }
/* 1143 */         diagnosticsWriter.println();
/*      */         
/* 1145 */         if (doStackDumpOnError) {
/* 1146 */           throwable.printStackTrace(dumpWriter);
/*      */         } else {
/*      */           
/* 1149 */           DefaultErrorHandler.printLocation(diagnosticsWriter, throwable);
/* 1150 */           diagnosticsWriter.println(
/* 1151 */               XSLMessages.createMessage("ER_XSLT_ERROR", null) + " (" + throwable
/* 1152 */               .getClass().getName() + "): " + throwable
/* 1153 */               .getMessage());
/*      */         } 
/*      */ 
/*      */         
/* 1157 */         if (null != dumpFileName)
/*      */         {
/* 1159 */           dumpWriter.close();
/*      */         }
/*      */         
/* 1162 */         doExit(throwable.getMessage());
/*      */       } 
/*      */       
/* 1165 */       if (null != dumpFileName)
/*      */       {
/* 1167 */         dumpWriter.close();
/*      */       }
/*      */       
/* 1170 */       if (null != diagnosticsWriter);
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
/*      */   static void doExit(String msg) {
/* 1189 */     throw new RuntimeException(msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void waitForReturnKey(ResourceBundle resbundle) {
/* 1199 */     System.out.println(resbundle.getString("xslProc_return_to_continue"));
/*      */     
/*      */     try {
/* 1202 */       while (System.in.read() != 10);
/*      */     }
/* 1204 */     catch (IOException iOException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void printInvalidXSLTCOption(String option) {
/* 1214 */     System.err.println(XSLMessages.createMessage("xslProc_invalid_xsltc_option", new Object[] { option }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void printInvalidXalanOption(String option) {
/* 1224 */     System.err.println(XSLMessages.createMessage("xslProc_invalid_xalan_option", new Object[] { option }));
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xslt/Process.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */