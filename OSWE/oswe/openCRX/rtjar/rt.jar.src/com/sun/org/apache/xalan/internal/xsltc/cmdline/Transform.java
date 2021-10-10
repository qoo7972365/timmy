/*     */ package com.sun.org.apache.xalan.internal.xsltc.cmdline;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.DOMEnhancedForDTM;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.TransletException;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.dom.DOMWSFilter;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.dom.XSLTCDTMManager;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.output.TransletOutputHandlerFactory;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMWSFilter;
/*     */ import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.XMLReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Transform
/*     */ {
/*     */   private SerializationHandler _handler;
/*     */   private String _fileName;
/*     */   private String _className;
/*     */   private String _jarFileSrc;
/*     */   private boolean _isJarFileSpecified = false;
/*  64 */   private Vector _params = null;
/*     */   private boolean _uri;
/*     */   private boolean _debug;
/*     */   private int _iterations;
/*     */   
/*     */   public Transform(String className, String fileName, boolean uri, boolean debug, int iterations) {
/*  70 */     this._fileName = fileName;
/*  71 */     this._className = className;
/*  72 */     this._uri = uri;
/*  73 */     this._debug = debug;
/*  74 */     this._iterations = iterations;
/*     */   }
/*     */   
/*  77 */   public String getFileName() { return this._fileName; } public String getClassName() {
/*  78 */     return this._className;
/*     */   }
/*     */   public void setParameters(Vector params) {
/*  81 */     this._params = params;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setJarFileInputSrc(boolean flag, String jarFile) {
/*  90 */     this._isJarFileSpecified = flag;
/*     */     
/*  92 */     this._jarFileSrc = jarFile;
/*     */   }
/*     */   private void doTransform() {
/*     */     try {
/*     */       DTMWSFilter wsfilter;
/*  97 */       Class<?> clazz = ObjectFactory.findProviderClass(this._className, true);
/*  98 */       AbstractTranslet translet = (AbstractTranslet)clazz.newInstance();
/*  99 */       translet.postInitialization();
/*     */ 
/*     */       
/* 102 */       SAXParserFactory factory = SAXParserFactory.newInstance();
/*     */       try {
/* 104 */         factory.setFeature("http://xml.org/sax/features/namespaces", true);
/*     */       }
/* 106 */       catch (Exception e) {
/* 107 */         factory.setNamespaceAware(true);
/*     */       } 
/* 109 */       SAXParser parser = factory.newSAXParser();
/* 110 */       XMLReader reader = parser.getXMLReader();
/*     */ 
/*     */ 
/*     */       
/* 114 */       XSLTCDTMManager dtmManager = XSLTCDTMManager.createNewDTMManagerInstance();
/*     */ 
/*     */       
/* 117 */       if (translet != null && translet instanceof com.sun.org.apache.xalan.internal.xsltc.StripFilter) {
/* 118 */         wsfilter = new DOMWSFilter(translet);
/*     */       } else {
/* 120 */         wsfilter = null;
/*     */       } 
/*     */ 
/*     */       
/* 124 */       DOMEnhancedForDTM dom = (DOMEnhancedForDTM)dtmManager.getDTM(new SAXSource(reader, new InputSource(this._fileName)), false, wsfilter, true, false, translet
/*     */           
/* 126 */           .hasIdCall());
/*     */       
/* 128 */       dom.setDocumentURI(this._fileName);
/* 129 */       translet.prepassDocument(dom);
/*     */ 
/*     */       
/* 132 */       int n = this._params.size();
/* 133 */       for (int i = 0; i < n; i++) {
/* 134 */         Parameter param = this._params.elementAt(i);
/* 135 */         translet.addParameter(param._name, param._value);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 140 */       TransletOutputHandlerFactory tohFactory = TransletOutputHandlerFactory.newInstance();
/* 141 */       tohFactory.setOutputType(0);
/* 142 */       tohFactory.setEncoding(translet._encoding);
/* 143 */       tohFactory.setOutputMethod(translet._method);
/*     */       
/* 145 */       if (this._iterations == -1) {
/* 146 */         translet.transform(dom, tohFactory.getSerializationHandler());
/*     */       }
/* 148 */       else if (this._iterations > 0) {
/* 149 */         long mm = System.currentTimeMillis();
/* 150 */         for (int j = 0; j < this._iterations; j++) {
/* 151 */           translet.transform(dom, tohFactory
/* 152 */               .getSerializationHandler());
/*     */         }
/* 154 */         mm = System.currentTimeMillis() - mm;
/*     */         
/* 156 */         System.err.println("\n<!--");
/* 157 */         System.err.println("  transform  = " + (mm / this._iterations) + " ms");
/*     */ 
/*     */         
/* 160 */         System.err.println("  throughput = " + (1000.0D / mm / this._iterations) + " tps");
/*     */ 
/*     */ 
/*     */         
/* 164 */         System.err.println("-->");
/*     */       }
/*     */     
/* 167 */     } catch (TransletException e) {
/* 168 */       if (this._debug) e.printStackTrace(); 
/* 169 */       System.err.println(new ErrorMsg("RUNTIME_ERROR_KEY") + e
/* 170 */           .getMessage());
/*     */     }
/* 172 */     catch (RuntimeException e) {
/* 173 */       if (this._debug) e.printStackTrace(); 
/* 174 */       System.err.println(new ErrorMsg("RUNTIME_ERROR_KEY") + e
/* 175 */           .getMessage());
/*     */     }
/* 177 */     catch (FileNotFoundException e) {
/* 178 */       if (this._debug) e.printStackTrace(); 
/* 179 */       ErrorMsg err = new ErrorMsg("FILE_NOT_FOUND_ERR", this._fileName);
/* 180 */       System.err.println(new ErrorMsg("RUNTIME_ERROR_KEY") + err
/* 181 */           .toString());
/*     */     }
/* 183 */     catch (MalformedURLException e) {
/* 184 */       if (this._debug) e.printStackTrace(); 
/* 185 */       ErrorMsg err = new ErrorMsg("INVALID_URI_ERR", this._fileName);
/* 186 */       System.err.println(new ErrorMsg("RUNTIME_ERROR_KEY") + err
/* 187 */           .toString());
/*     */     }
/* 189 */     catch (ClassNotFoundException e) {
/* 190 */       if (this._debug) e.printStackTrace(); 
/* 191 */       ErrorMsg err = new ErrorMsg("CLASS_NOT_FOUND_ERR", this._className);
/* 192 */       System.err.println(new ErrorMsg("RUNTIME_ERROR_KEY") + err
/* 193 */           .toString());
/*     */     }
/* 195 */     catch (UnknownHostException e) {
/* 196 */       if (this._debug) e.printStackTrace(); 
/* 197 */       ErrorMsg err = new ErrorMsg("INVALID_URI_ERR", this._fileName);
/* 198 */       System.err.println(new ErrorMsg("RUNTIME_ERROR_KEY") + err
/* 199 */           .toString());
/*     */     }
/* 201 */     catch (SAXException e) {
/* 202 */       Exception ex = e.getException();
/* 203 */       if (this._debug) {
/* 204 */         if (ex != null) ex.printStackTrace(); 
/* 205 */         e.printStackTrace();
/*     */       } 
/* 207 */       System.err.print(new ErrorMsg("RUNTIME_ERROR_KEY"));
/* 208 */       if (ex != null) {
/* 209 */         System.err.println(ex.getMessage());
/*     */       } else {
/* 211 */         System.err.println(e.getMessage());
/*     */       } 
/* 213 */     } catch (Exception e) {
/* 214 */       if (this._debug) e.printStackTrace(); 
/* 215 */       System.err.println(new ErrorMsg("RUNTIME_ERROR_KEY") + e
/* 216 */           .getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void printUsage() {
/* 221 */     System.err.println(new ErrorMsg("TRANSFORM_USAGE_STR"));
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/*     */     try {
/* 226 */       if (args.length > 0) {
/*     */         
/* 228 */         int iterations = -1;
/* 229 */         boolean uri = false, debug = false;
/* 230 */         boolean isJarFileSpecified = false;
/* 231 */         String jarFile = null;
/*     */         
/*     */         int i;
/* 234 */         for (i = 0; i < args.length && args[i].charAt(0) == '-'; i++) {
/* 235 */           if (args[i].equals("-u")) {
/* 236 */             uri = true;
/*     */           }
/* 238 */           else if (args[i].equals("-x")) {
/* 239 */             debug = true;
/*     */           }
/* 241 */           else if (args[i].equals("-j")) {
/* 242 */             isJarFileSpecified = true;
/* 243 */             jarFile = args[++i];
/*     */           }
/* 245 */           else if (args[i].equals("-n")) {
/*     */             try {
/* 247 */               iterations = Integer.parseInt(args[++i]);
/*     */             }
/* 249 */             catch (NumberFormatException numberFormatException) {}
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 254 */             printUsage();
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 259 */         if (args.length - i < 2) printUsage();
/*     */ 
/*     */         
/* 262 */         Transform handler = new Transform(args[i + 1], args[i], uri, debug, iterations);
/*     */         
/* 264 */         handler.setJarFileInputSrc(isJarFileSpecified, jarFile);
/*     */ 
/*     */         
/* 267 */         Vector<Parameter> params = new Vector();
/* 268 */         for (i += 2; i < args.length; i++) {
/* 269 */           int equal = args[i].indexOf('=');
/* 270 */           if (equal > 0) {
/* 271 */             String name = args[i].substring(0, equal);
/* 272 */             String value = args[i].substring(equal + 1);
/* 273 */             params.addElement(new Parameter(name, value));
/*     */           } else {
/*     */             
/* 276 */             printUsage();
/*     */           } 
/*     */         } 
/*     */         
/* 280 */         if (i == args.length) {
/* 281 */           handler.setParameters(params);
/* 282 */           handler.doTransform();
/*     */         } 
/*     */       } else {
/* 285 */         printUsage();
/*     */       }
/*     */     
/* 288 */     } catch (Exception e) {
/* 289 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/cmdline/Transform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */