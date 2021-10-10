package com.sun.org.apache.xalan.internal.xsltc.compiler;

public interface Closure {
  boolean inInnerClass();
  
  Closure getParentClosure();
  
  String getInnerClassName();
  
  void addVariable(VariableRefBase paramVariableRefBase);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/Closure.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */