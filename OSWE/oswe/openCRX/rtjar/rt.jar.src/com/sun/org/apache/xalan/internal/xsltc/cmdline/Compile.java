/*     */ package com.sun.org.apache.xalan.internal.xsltc.cmdline;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOpt;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.XSLTC;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import java.io.File;
/*     */ import java.net.URL;
/*     */ import java.util.Vector;
/*     */ import jdk.xml.internal.JdkXmlFeatures;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Compile
/*     */ {
/*  44 */   private static int VERSION_MAJOR = 1;
/*  45 */   private static int VERSION_MINOR = 4;
/*  46 */   private static int VERSION_DELTA = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean _allowExit = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void printUsage() {
/*  58 */     System.err.println("XSLTC version " + VERSION_MAJOR + "." + VERSION_MINOR + ((VERSION_DELTA > 0) ? ("." + VERSION_DELTA) : "") + "\n" + new ErrorMsg("COMPILE_USAGE_STR"));
/*     */ 
/*     */ 
/*     */     
/*  62 */     if (_allowExit) System.exit(-1);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/*     */     try {
/*  74 */       boolean compileOK, inputIsURL = false;
/*  75 */       boolean useStdIn = false;
/*  76 */       boolean classNameSet = false;
/*  77 */       GetOpt getopt = new GetOpt(args, "o:d:j:p:uxhsinv");
/*  78 */       if (args.length < 1) printUsage();
/*     */       
/*  80 */       XSLTC xsltc = new XSLTC(new JdkXmlFeatures(false));
/*  81 */       xsltc.init();
/*     */       
/*     */       int c;
/*  84 */       while ((c = getopt.getNextOption()) != -1) {
/*  85 */         switch (c) {
/*     */           case 105:
/*  87 */             useStdIn = true;
/*     */             continue;
/*     */           case 111:
/*  90 */             xsltc.setClassName(getopt.getOptionArg());
/*  91 */             classNameSet = true;
/*     */             continue;
/*     */           case 100:
/*  94 */             xsltc.setDestDirectory(getopt.getOptionArg());
/*     */             continue;
/*     */           case 112:
/*  97 */             xsltc.setPackageName(getopt.getOptionArg());
/*     */             continue;
/*     */           case 106:
/* 100 */             xsltc.setJarFileName(getopt.getOptionArg());
/*     */             continue;
/*     */           case 120:
/* 103 */             xsltc.setDebug(true);
/*     */             continue;
/*     */           case 117:
/* 106 */             inputIsURL = true;
/*     */             continue;
/*     */           case 115:
/* 109 */             _allowExit = false;
/*     */             continue;
/*     */           case 110:
/* 112 */             xsltc.setTemplateInlining(true);
/*     */             continue;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 118 */         printUsage();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 125 */       if (useStdIn) {
/* 126 */         if (!classNameSet) {
/* 127 */           System.err.println(new ErrorMsg("COMPILE_STDIN_ERR"));
/* 128 */           if (_allowExit) System.exit(-1); 
/*     */         } 
/* 130 */         compileOK = xsltc.compile(System.in, xsltc.getClassName());
/*     */       }
/*     */       else {
/*     */         
/* 134 */         String[] stylesheetNames = getopt.getCmdArgs();
/* 135 */         Vector<URL> stylesheetVector = new Vector();
/* 136 */         for (int i = 0; i < stylesheetNames.length; i++) {
/* 137 */           URL url; String name = stylesheetNames[i];
/*     */           
/* 139 */           if (inputIsURL) {
/* 140 */             url = new URL(name);
/*     */           } else {
/* 142 */             url = (new File(name)).toURI().toURL();
/* 143 */           }  stylesheetVector.addElement(url);
/*     */         } 
/* 145 */         compileOK = xsltc.compile(stylesheetVector);
/*     */       } 
/*     */ 
/*     */       
/* 149 */       if (compileOK) {
/* 150 */         xsltc.printWarnings();
/* 151 */         if (xsltc.getJarFileName() != null) xsltc.outputToJar(); 
/* 152 */         if (_allowExit) System.exit(0);
/*     */       
/*     */       } else {
/* 155 */         xsltc.printWarnings();
/* 156 */         xsltc.printErrors();
/* 157 */         if (_allowExit) System.exit(-1);
/*     */       
/*     */       } 
/* 160 */     } catch (GetOptsException ex) {
/* 161 */       System.err.println(ex);
/* 162 */       printUsage();
/*     */     }
/* 164 */     catch (Exception e) {
/* 165 */       e.printStackTrace();
/* 166 */       if (_allowExit) System.exit(-1); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/cmdline/Compile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */