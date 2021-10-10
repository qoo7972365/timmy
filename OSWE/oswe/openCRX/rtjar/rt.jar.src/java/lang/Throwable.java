/*      */ package java.lang;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Serializable;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Throwable
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -3042686055658047285L;
/*      */   private transient Object backtrace;
/*      */   private String detailMessage;
/*      */   
/*      */   private static class SentinelHolder
/*      */   {
/*  146 */     public static final StackTraceElement STACK_TRACE_ELEMENT_SENTINEL = new StackTraceElement("", "", null, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  153 */     public static final StackTraceElement[] STACK_TRACE_SENTINEL = new StackTraceElement[] { STACK_TRACE_ELEMENT_SENTINEL };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  160 */   private static final StackTraceElement[] UNASSIGNED_STACK = new StackTraceElement[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  198 */   private Throwable cause = this;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  211 */   private StackTraceElement[] stackTrace = UNASSIGNED_STACK;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  216 */   private static final List<Throwable> SUPPRESSED_SENTINEL = Collections.unmodifiableList(new ArrayList<>(0));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  228 */   private List<Throwable> suppressedExceptions = SUPPRESSED_SENTINEL;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String NULL_CAUSE_MESSAGE = "Cannot suppress a null exception.";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String SELF_SUPPRESSION_MESSAGE = "Self-suppression not permitted";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String CAUSE_CAPTION = "Caused by: ";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String SUPPRESSED_CAPTION = "Suppressed: ";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Throwable() {
/*  251 */     fillInStackTrace();
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
/*      */   public Throwable(String paramString) {
/*  266 */     fillInStackTrace();
/*  267 */     this.detailMessage = paramString;
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
/*      */   public Throwable(String paramString, Throwable paramThrowable) {
/*  288 */     fillInStackTrace();
/*  289 */     this.detailMessage = paramString;
/*  290 */     this.cause = paramThrowable;
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
/*      */   public Throwable(Throwable paramThrowable) {
/*  311 */     fillInStackTrace();
/*  312 */     this.detailMessage = (paramThrowable == null) ? null : paramThrowable.toString();
/*  313 */     this.cause = paramThrowable;
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
/*      */   protected Throwable(String paramString, Throwable paramThrowable, boolean paramBoolean1, boolean paramBoolean2) {
/*  360 */     if (paramBoolean2) {
/*  361 */       fillInStackTrace();
/*      */     } else {
/*  363 */       this.stackTrace = null;
/*      */     } 
/*  365 */     this.detailMessage = paramString;
/*  366 */     this.cause = paramThrowable;
/*  367 */     if (!paramBoolean1) {
/*  368 */       this.suppressedExceptions = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMessage() {
/*  378 */     return this.detailMessage;
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
/*      */   public String getLocalizedMessage() {
/*  392 */     return getMessage();
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
/*      */   public synchronized Throwable getCause() {
/*  416 */     return (this.cause == this) ? null : this.cause;
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
/*      */   public synchronized Throwable initCause(Throwable paramThrowable) {
/*  456 */     if (this.cause != this)
/*  457 */       throw new IllegalStateException("Can't overwrite cause with " + 
/*  458 */           Objects.toString(paramThrowable, "a null"), this); 
/*  459 */     if (paramThrowable == this)
/*  460 */       throw new IllegalArgumentException("Self-causation not permitted", this); 
/*  461 */     this.cause = paramThrowable;
/*  462 */     return this;
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
/*      */   public String toString() {
/*  480 */     String str1 = getClass().getName();
/*  481 */     String str2 = getLocalizedMessage();
/*  482 */     return (str2 != null) ? (str1 + ": " + str2) : str1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printStackTrace() {
/*  635 */     printStackTrace(System.err);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printStackTrace(PrintStream paramPrintStream) {
/*  644 */     printStackTrace(new WrappedPrintStream(paramPrintStream));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printStackTrace(PrintStreamOrWriter paramPrintStreamOrWriter) {
/*  651 */     Set<?> set = Collections.newSetFromMap(new IdentityHashMap<>());
/*  652 */     set.add(this);
/*      */     
/*  654 */     synchronized (paramPrintStreamOrWriter.lock()) {
/*      */       
/*  656 */       paramPrintStreamOrWriter.println(this);
/*  657 */       StackTraceElement[] arrayOfStackTraceElement = getOurStackTrace();
/*  658 */       for (StackTraceElement stackTraceElement : arrayOfStackTraceElement) {
/*  659 */         paramPrintStreamOrWriter.println("\tat " + stackTraceElement);
/*      */       }
/*      */       
/*  662 */       for (Throwable throwable1 : getSuppressed()) {
/*  663 */         throwable1.printEnclosedStackTrace(paramPrintStreamOrWriter, arrayOfStackTraceElement, "Suppressed: ", "\t", (Set)set);
/*      */       }
/*      */       
/*  666 */       Throwable throwable = getCause();
/*  667 */       if (throwable != null) {
/*  668 */         throwable.printEnclosedStackTrace(paramPrintStreamOrWriter, arrayOfStackTraceElement, "Caused by: ", "", (Set)set);
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
/*      */   private void printEnclosedStackTrace(PrintStreamOrWriter paramPrintStreamOrWriter, StackTraceElement[] paramArrayOfStackTraceElement, String paramString1, String paramString2, Set<Throwable> paramSet) {
/*  681 */     assert Thread.holdsLock(paramPrintStreamOrWriter.lock());
/*  682 */     if (paramSet.contains(this)) {
/*  683 */       paramPrintStreamOrWriter.println("\t[CIRCULAR REFERENCE:" + this + "]");
/*      */     } else {
/*  685 */       paramSet.add(this);
/*      */       
/*  687 */       StackTraceElement[] arrayOfStackTraceElement = getOurStackTrace();
/*  688 */       int i = arrayOfStackTraceElement.length - 1;
/*  689 */       int j = paramArrayOfStackTraceElement.length - 1;
/*  690 */       while (i >= 0 && j >= 0 && arrayOfStackTraceElement[i].equals(paramArrayOfStackTraceElement[j])) {
/*  691 */         i--; j--;
/*      */       } 
/*  693 */       int k = arrayOfStackTraceElement.length - 1 - i;
/*      */ 
/*      */       
/*  696 */       paramPrintStreamOrWriter.println(paramString2 + paramString1 + this);
/*  697 */       for (byte b = 0; b <= i; b++)
/*  698 */         paramPrintStreamOrWriter.println(paramString2 + "\tat " + arrayOfStackTraceElement[b]); 
/*  699 */       if (k != 0) {
/*  700 */         paramPrintStreamOrWriter.println(paramString2 + "\t... " + k + " more");
/*      */       }
/*      */       
/*  703 */       for (Throwable throwable1 : getSuppressed()) {
/*  704 */         throwable1.printEnclosedStackTrace(paramPrintStreamOrWriter, arrayOfStackTraceElement, "Suppressed: ", paramString2 + "\t", paramSet);
/*      */       }
/*      */ 
/*      */       
/*  708 */       Throwable throwable = getCause();
/*  709 */       if (throwable != null) {
/*  710 */         throwable.printEnclosedStackTrace(paramPrintStreamOrWriter, arrayOfStackTraceElement, "Caused by: ", paramString2, paramSet);
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
/*      */   public void printStackTrace(PrintWriter paramPrintWriter) {
/*  722 */     printStackTrace(new WrappedPrintWriter(paramPrintWriter));
/*      */   }
/*      */ 
/*      */   
/*      */   private static abstract class PrintStreamOrWriter
/*      */   {
/*      */     private PrintStreamOrWriter() {}
/*      */     
/*      */     abstract Object lock();
/*      */     
/*      */     abstract void println(Object param1Object);
/*      */   }
/*      */   
/*      */   private static class WrappedPrintStream
/*      */     extends PrintStreamOrWriter
/*      */   {
/*      */     private final PrintStream printStream;
/*      */     
/*      */     WrappedPrintStream(PrintStream param1PrintStream) {
/*  741 */       this.printStream = param1PrintStream;
/*      */     }
/*      */     
/*      */     Object lock() {
/*  745 */       return this.printStream;
/*      */     }
/*      */     
/*      */     void println(Object param1Object) {
/*  749 */       this.printStream.println(param1Object);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class WrappedPrintWriter extends PrintStreamOrWriter {
/*      */     private final PrintWriter printWriter;
/*      */     
/*      */     WrappedPrintWriter(PrintWriter param1PrintWriter) {
/*  757 */       this.printWriter = param1PrintWriter;
/*      */     }
/*      */     
/*      */     Object lock() {
/*  761 */       return this.printWriter;
/*      */     }
/*      */     
/*      */     void println(Object param1Object) {
/*  765 */       this.printWriter.println(param1Object);
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
/*      */   public synchronized Throwable fillInStackTrace() {
/*  782 */     if (this.stackTrace != null || this.backtrace != null) {
/*      */       
/*  784 */       fillInStackTrace(0);
/*  785 */       this.stackTrace = UNASSIGNED_STACK;
/*      */     } 
/*  787 */     return this;
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
/*      */   private native Throwable fillInStackTrace(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StackTraceElement[] getStackTrace() {
/*  817 */     return (StackTraceElement[])getOurStackTrace().clone();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized StackTraceElement[] getOurStackTrace() {
/*  823 */     if (this.stackTrace == UNASSIGNED_STACK || (this.stackTrace == null && this.backtrace != null)) {
/*      */       
/*  825 */       int i = getStackTraceDepth();
/*  826 */       this.stackTrace = new StackTraceElement[i];
/*  827 */       for (byte b = 0; b < i; b++)
/*  828 */         this.stackTrace[b] = getStackTraceElement(b); 
/*  829 */     } else if (this.stackTrace == null) {
/*  830 */       return UNASSIGNED_STACK;
/*      */     } 
/*  832 */     return this.stackTrace;
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
/*      */   public void setStackTrace(StackTraceElement[] paramArrayOfStackTraceElement) {
/*  865 */     StackTraceElement[] arrayOfStackTraceElement = (StackTraceElement[])paramArrayOfStackTraceElement.clone();
/*  866 */     for (byte b = 0; b < arrayOfStackTraceElement.length; b++) {
/*  867 */       if (arrayOfStackTraceElement[b] == null) {
/*  868 */         throw new NullPointerException("stackTrace[" + b + "]");
/*      */       }
/*      */     } 
/*  871 */     synchronized (this) {
/*  872 */       if (this.stackTrace == null && this.backtrace == null) {
/*      */         return;
/*      */       }
/*  875 */       this.stackTrace = arrayOfStackTraceElement;
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
/*      */   native int getStackTraceDepth();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   native StackTraceElement getStackTraceElement(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*  915 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  920 */     List<Throwable> list = this.suppressedExceptions;
/*  921 */     this.suppressedExceptions = SUPPRESSED_SENTINEL;
/*      */     
/*  923 */     StackTraceElement[] arrayOfStackTraceElement = this.stackTrace;
/*  924 */     this.stackTrace = (StackTraceElement[])UNASSIGNED_STACK.clone();
/*      */     
/*  926 */     if (list != null) {
/*  927 */       int i = validateSuppressedExceptionsList(list);
/*  928 */       if (i > 0) {
/*  929 */         ArrayList<Throwable> arrayList = new ArrayList(Math.min(100, i));
/*      */         
/*  931 */         for (Throwable throwable : list) {
/*      */ 
/*      */           
/*  934 */           if (throwable == null)
/*  935 */             throw new NullPointerException("Cannot suppress a null exception."); 
/*  936 */           if (throwable == this)
/*  937 */             throw new IllegalArgumentException("Self-suppression not permitted"); 
/*  938 */           arrayList.add(throwable);
/*      */         } 
/*      */ 
/*      */         
/*  942 */         this.suppressedExceptions = arrayList;
/*      */       } 
/*      */     } else {
/*  945 */       this.suppressedExceptions = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  957 */     if (arrayOfStackTraceElement != null) {
/*      */ 
/*      */       
/*  960 */       arrayOfStackTraceElement = (StackTraceElement[])arrayOfStackTraceElement.clone();
/*  961 */       if (arrayOfStackTraceElement.length >= 1) {
/*  962 */         if (arrayOfStackTraceElement.length == 1 && SentinelHolder.STACK_TRACE_ELEMENT_SENTINEL
/*      */           
/*  964 */           .equals(arrayOfStackTraceElement[0])) {
/*  965 */           this.stackTrace = null;
/*      */         } else {
/*  967 */           for (StackTraceElement stackTraceElement : arrayOfStackTraceElement) {
/*  968 */             if (stackTraceElement == null)
/*  969 */               throw new NullPointerException("null StackTraceElement in serial stream."); 
/*      */           } 
/*  971 */           this.stackTrace = arrayOfStackTraceElement;
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
/*      */   private int validateSuppressedExceptionsList(List<Throwable> paramList) throws IOException {
/*  984 */     if (Object.class.getClassLoader() != paramList.getClass().getClassLoader()) {
/*  985 */       throw new StreamCorruptedException("List implementation not on the bootclasspath.");
/*      */     }
/*  987 */     int i = paramList.size();
/*  988 */     if (i < 0) {
/*  989 */       throw new StreamCorruptedException("Negative list size reported.");
/*      */     }
/*  991 */     return i;
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
/*      */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1008 */     getOurStackTrace();
/*      */     
/* 1010 */     StackTraceElement[] arrayOfStackTraceElement = this.stackTrace;
/*      */     try {
/* 1012 */       if (this.stackTrace == null)
/* 1013 */         this.stackTrace = SentinelHolder.STACK_TRACE_SENTINEL; 
/* 1014 */       paramObjectOutputStream.defaultWriteObject();
/*      */     } finally {
/* 1016 */       this.stackTrace = arrayOfStackTraceElement;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized void addSuppressed(Throwable paramThrowable) {
/* 1071 */     if (paramThrowable == this) {
/* 1072 */       throw new IllegalArgumentException("Self-suppression not permitted", paramThrowable);
/*      */     }
/* 1074 */     if (paramThrowable == null) {
/* 1075 */       throw new NullPointerException("Cannot suppress a null exception.");
/*      */     }
/* 1077 */     if (this.suppressedExceptions == null) {
/*      */       return;
/*      */     }
/* 1080 */     if (this.suppressedExceptions == SUPPRESSED_SENTINEL) {
/* 1081 */       this.suppressedExceptions = new ArrayList<>(1);
/*      */     }
/* 1083 */     this.suppressedExceptions.add(paramThrowable);
/*      */   }
/*      */   
/* 1086 */   private static final Throwable[] EMPTY_THROWABLE_ARRAY = new Throwable[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized Throwable[] getSuppressed() {
/* 1104 */     if (this.suppressedExceptions == SUPPRESSED_SENTINEL || this.suppressedExceptions == null)
/*      */     {
/* 1106 */       return EMPTY_THROWABLE_ARRAY;
/*      */     }
/* 1108 */     return this.suppressedExceptions.<Throwable>toArray(EMPTY_THROWABLE_ARRAY);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/Throwable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */