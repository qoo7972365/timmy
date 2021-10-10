/*      */ package com.sun.org.apache.xalan.internal.xsltc.compiler.util;
/*      */ 
/*      */ import com.sun.org.apache.bcel.internal.classfile.Field;
/*      */ import com.sun.org.apache.bcel.internal.classfile.Method;
/*      */ import com.sun.org.apache.bcel.internal.generic.ALOAD;
/*      */ import com.sun.org.apache.bcel.internal.generic.ASTORE;
/*      */ import com.sun.org.apache.bcel.internal.generic.BranchHandle;
/*      */ import com.sun.org.apache.bcel.internal.generic.BranchInstruction;
/*      */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*      */ import com.sun.org.apache.bcel.internal.generic.DLOAD;
/*      */ import com.sun.org.apache.bcel.internal.generic.DSTORE;
/*      */ import com.sun.org.apache.bcel.internal.generic.FLOAD;
/*      */ import com.sun.org.apache.bcel.internal.generic.FSTORE;
/*      */ import com.sun.org.apache.bcel.internal.generic.GETFIELD;
/*      */ import com.sun.org.apache.bcel.internal.generic.GOTO;
/*      */ import com.sun.org.apache.bcel.internal.generic.ICONST;
/*      */ import com.sun.org.apache.bcel.internal.generic.ILOAD;
/*      */ import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
/*      */ import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
/*      */ import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
/*      */ import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
/*      */ import com.sun.org.apache.bcel.internal.generic.ISTORE;
/*      */ import com.sun.org.apache.bcel.internal.generic.IfInstruction;
/*      */ import com.sun.org.apache.bcel.internal.generic.IndexedInstruction;
/*      */ import com.sun.org.apache.bcel.internal.generic.Instruction;
/*      */ import com.sun.org.apache.bcel.internal.generic.InstructionConstants;
/*      */ import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
/*      */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*      */ import com.sun.org.apache.bcel.internal.generic.InstructionTargeter;
/*      */ import com.sun.org.apache.bcel.internal.generic.LLOAD;
/*      */ import com.sun.org.apache.bcel.internal.generic.LSTORE;
/*      */ import com.sun.org.apache.bcel.internal.generic.LocalVariableGen;
/*      */ import com.sun.org.apache.bcel.internal.generic.MethodGen;
/*      */ import com.sun.org.apache.bcel.internal.generic.NEW;
/*      */ import com.sun.org.apache.bcel.internal.generic.PUTFIELD;
/*      */ import com.sun.org.apache.bcel.internal.generic.Select;
/*      */ import com.sun.org.apache.bcel.internal.generic.TargetLostException;
/*      */ import com.sun.org.apache.bcel.internal.generic.Type;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.Constants;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.Stylesheet;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.XSLTC;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Stack;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MethodGenerator
/*      */   extends MethodGen
/*      */   implements Constants
/*      */ {
/*      */   protected static final int INVALID_INDEX = -1;
/*      */   private static final String START_ELEMENT_SIG = "(Ljava/lang/String;)V";
/*      */   private static final String END_ELEMENT_SIG = "(Ljava/lang/String;)V";
/*      */   private InstructionList _mapTypeSub;
/*      */   private static final int DOM_INDEX = 1;
/*      */   private static final int ITERATOR_INDEX = 2;
/*      */   private static final int HANDLER_INDEX = 3;
/*      */   private static final int MAX_METHOD_SIZE = 65535;
/*      */   private static final int MAX_BRANCH_TARGET_OFFSET = 32767;
/*      */   private static final int MIN_BRANCH_TARGET_OFFSET = -32768;
/*      */   private static final int TARGET_METHOD_SIZE = 60000;
/*      */   private static final int MINIMUM_OUTLINEABLE_CHUNK_SIZE = 1000;
/*      */   private Instruction _iloadCurrent;
/*      */   private Instruction _istoreCurrent;
/*      */   private final Instruction _astoreHandler;
/*      */   private final Instruction _aloadHandler;
/*      */   private final Instruction _astoreIterator;
/*      */   private final Instruction _aloadIterator;
/*      */   private final Instruction _aloadDom;
/*  130 */   private Map<Pattern, InstructionList> _preCompiled = new HashMap<>();
/*      */   private final Instruction _astoreDom;
/*      */   private final Instruction _startElement;
/*      */   private final Instruction _endElement;
/*      */   private final Instruction _startDocument;
/*      */   private final Instruction _endDocument;
/*      */   private final Instruction _attribute; private final Instruction _uniqueAttribute; private final Instruction _namespace; private final Instruction _setStartNode; private final Instruction _reset; private final Instruction _nextNode; private SlotAllocator _slotAllocator; private boolean _allocatorInit = false; private LocalVariableRegistry _localVariableRegistry; private int m_totalChunks; private int m_openChunks; public LocalVariableGen addLocalVariable(String name, Type type, InstructionHandle start, InstructionHandle end) { LocalVariableGen lvg; if (this._allocatorInit) { lvg = addLocalVariable2(name, type, start); } else { lvg = super.addLocalVariable(name, type, start, end); getLocalVariableRegistry().registerLocalVariable(lvg); }  return lvg; } public LocalVariableGen addLocalVariable2(String name, Type type, InstructionHandle start) { LocalVariableGen lvg = addLocalVariable(name, type, this._slotAllocator.allocateSlot(type), start, (InstructionHandle)null); getLocalVariableRegistry().registerLocalVariable(lvg); return lvg; } private LocalVariableRegistry getLocalVariableRegistry() { if (this._localVariableRegistry == null) this._localVariableRegistry = new LocalVariableRegistry();  return this._localVariableRegistry; } protected class LocalVariableRegistry {
/*  137 */     protected ArrayList _variables = new ArrayList(); protected HashMap _nameToLVGMap = new HashMap<>(); protected void registerLocalVariable(LocalVariableGen lvg) { int slot = lvg.getIndex(); int registrySize = this._variables.size(); if (slot >= registrySize) { for (int i = registrySize; i < slot; i++) this._variables.add(null);  this._variables.add(lvg); } else { Object localsInSlot = this._variables.get(slot); if (localsInSlot != null) { if (localsInSlot instanceof LocalVariableGen) { ArrayList<Object> listOfLocalsInSlot = new ArrayList(); listOfLocalsInSlot.add(localsInSlot); listOfLocalsInSlot.add(lvg); this._variables.set(slot, listOfLocalsInSlot); } else { ((ArrayList<LocalVariableGen>)localsInSlot).add(lvg); }  } else { this._variables.set(slot, lvg); }  }  registerByName(lvg); } protected LocalVariableGen lookupRegisteredLocalVariable(int slot, int offset) { Object localsInSlot = (this._variables != null) ? this._variables.get(slot) : null; if (localsInSlot != null) if (localsInSlot instanceof LocalVariableGen) { LocalVariableGen lvg = (LocalVariableGen)localsInSlot; if (MethodGenerator.this.offsetInLocalVariableGenRange(lvg, offset)) return lvg;  } else { ArrayList<LocalVariableGen> listOfLocalsInSlot = (ArrayList)localsInSlot; int size = listOfLocalsInSlot.size(); for (int i = 0; i < size; i++) { LocalVariableGen lvg = listOfLocalsInSlot.get(i); if (MethodGenerator.this.offsetInLocalVariableGenRange(lvg, offset)) return lvg;  }  }   return null; } protected void registerByName(LocalVariableGen lvg) { Object duplicateNameEntry = this._nameToLVGMap.get(lvg.getName()); if (duplicateNameEntry == null) { this._nameToLVGMap.put(lvg.getName(), lvg); } else { ArrayList<Object> sameNameList; if (duplicateNameEntry instanceof ArrayList) { ArrayList<LocalVariableGen> arrayList = (ArrayList)duplicateNameEntry; arrayList.add(lvg); } else { sameNameList = new ArrayList(); sameNameList.add(duplicateNameEntry); sameNameList.add(lvg); }  this._nameToLVGMap.put(lvg.getName(), sameNameList); }  } protected void removeByNameTracking(LocalVariableGen lvg) { Object duplicateNameEntry = this._nameToLVGMap.get(lvg.getName()); if (duplicateNameEntry instanceof ArrayList) { ArrayList<LocalVariableGen> sameNameList = (ArrayList)duplicateNameEntry; for (int i = 0; i < sameNameList.size(); i++) { if (sameNameList.get(i) == lvg) { sameNameList.remove(i); break; }  }  } else { this._nameToLVGMap.remove(lvg); }  } protected LocalVariableGen lookUpByName(String name) { LocalVariableGen lvg = null; Object duplicateNameEntry = this._nameToLVGMap.get(name); if (duplicateNameEntry instanceof ArrayList) { ArrayList<LocalVariableGen> sameNameList = (ArrayList)duplicateNameEntry; for (int i = 0; i < sameNameList.size(); i++) { lvg = sameNameList.get(i); if (lvg.getName() == name) break;  }  } else { lvg = (LocalVariableGen)duplicateNameEntry; }  return lvg; } protected LocalVariableGen[] getLocals(boolean includeRemoved) { LocalVariableGen[] locals = null; ArrayList<Object> allVarsEverDeclared = new ArrayList(); if (includeRemoved) { int slotCount = allVarsEverDeclared.size(); for (int i = 0; i < slotCount; i++) { Object slotEntries = this._variables.get(i); if (slotEntries != null) if (slotEntries instanceof ArrayList) { ArrayList slotList = (ArrayList)slotEntries; for (int j = 0; j < slotList.size(); j++) allVarsEverDeclared.add(slotList.get(i));  } else { allVarsEverDeclared.add(slotEntries); }   }  } else { Iterator<Map.Entry> nameVarsPairsIter = this._nameToLVGMap.entrySet().iterator(); while (nameVarsPairsIter.hasNext()) { Map.Entry nameVarsPair = nameVarsPairsIter.next(); Object vars = nameVarsPair.getValue(); if (vars != null) { if (vars instanceof ArrayList) { ArrayList varsList = (ArrayList)vars; for (int i = 0; i < varsList.size(); i++) allVarsEverDeclared.add(varsList.get(i));  continue; }  allVarsEverDeclared.add(vars); }  }  }  locals = new LocalVariableGen[allVarsEverDeclared.size()]; allVarsEverDeclared.toArray(locals); return locals; } } boolean offsetInLocalVariableGenRange(LocalVariableGen lvg, int offset) { InstructionHandle lvgStart = lvg.getStart(); InstructionHandle lvgEnd = lvg.getEnd(); if (lvgStart == null) lvgStart = getInstructionList().getStart();  if (lvgEnd == null) lvgEnd = getInstructionList().getEnd();  return (lvgStart.getPosition() <= offset && lvgEnd.getPosition() + lvgEnd.getInstruction().getLength() >= offset); } public void removeLocalVariable(LocalVariableGen lvg) { this._slotAllocator.releaseSlot(lvg); getLocalVariableRegistry().removeByNameTracking(lvg); super.removeLocalVariable(lvg); } public Instruction loadDOM() { return this._aloadDom; } public Instruction storeDOM() { return this._astoreDom; } public Instruction storeHandler() { return this._astoreHandler; } public Instruction loadHandler() { return this._aloadHandler; } public MethodGenerator(int access_flags, Type return_type, Type[] arg_types, String[] arg_names, String method_name, String class_name, InstructionList il, ConstantPoolGen cpg) { super(access_flags, return_type, arg_types, arg_names, method_name, class_name, il, cpg);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1821 */     this.m_totalChunks = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1827 */     this.m_openChunks = 0; this._astoreHandler = new ASTORE(3); this._aloadHandler = new ALOAD(3); this._astoreIterator = new ASTORE(2); this._aloadIterator = new ALOAD(2); this._aloadDom = new ALOAD(1); this._astoreDom = new ASTORE(1); int startElement = cpg.addInterfaceMethodref("com.sun.org.apache.xml.internal.serializer.SerializationHandler", "startElement", "(Ljava/lang/String;)V"); this._startElement = new INVOKEINTERFACE(startElement, 2); int endElement = cpg.addInterfaceMethodref("com.sun.org.apache.xml.internal.serializer.SerializationHandler", "endElement", "(Ljava/lang/String;)V"); this._endElement = new INVOKEINTERFACE(endElement, 2); int attribute = cpg.addInterfaceMethodref("com.sun.org.apache.xml.internal.serializer.SerializationHandler", "addAttribute", "(Ljava/lang/String;Ljava/lang/String;)V"); this._attribute = new INVOKEINTERFACE(attribute, 3); int uniqueAttribute = cpg.addInterfaceMethodref("com.sun.org.apache.xml.internal.serializer.SerializationHandler", "addUniqueAttribute", "(Ljava/lang/String;Ljava/lang/String;I)V"); this._uniqueAttribute = new INVOKEINTERFACE(uniqueAttribute, 4); int namespace = cpg.addInterfaceMethodref("com.sun.org.apache.xml.internal.serializer.SerializationHandler", "namespaceAfterStartElement", "(Ljava/lang/String;Ljava/lang/String;)V"); this._namespace = new INVOKEINTERFACE(namespace, 3); int index = cpg.addInterfaceMethodref("com.sun.org.apache.xml.internal.serializer.SerializationHandler", "startDocument", "()V"); this._startDocument = new INVOKEINTERFACE(index, 1); index = cpg.addInterfaceMethodref("com.sun.org.apache.xml.internal.serializer.SerializationHandler", "endDocument", "()V"); this._endDocument = new INVOKEINTERFACE(index, 1); index = cpg.addInterfaceMethodref("com.sun.org.apache.xml.internal.dtm.DTMAxisIterator", "setStartNode", "(I)Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;"); this._setStartNode = new INVOKEINTERFACE(index, 2); index = cpg.addInterfaceMethodref("com.sun.org.apache.xml.internal.dtm.DTMAxisIterator", "reset", "()Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;"); this._reset = new INVOKEINTERFACE(index, 1); index = cpg.addInterfaceMethodref("com.sun.org.apache.xml.internal.dtm.DTMAxisIterator", "next", "()I"); this._nextNode = new INVOKEINTERFACE(index, 1);
/*      */     this._slotAllocator = new SlotAllocator();
/*      */     this._slotAllocator.initialize(getLocalVariableRegistry().getLocals(false));
/*      */     this._allocatorInit = true; } public Instruction storeIterator() { return this._astoreIterator; }
/*      */   public Instruction loadIterator() { return this._aloadIterator; }
/*      */   public final Instruction setStartNode() { return this._setStartNode; }
/*      */   public final Instruction reset() { return this._reset; }
/*      */   public final Instruction nextNode() { return this._nextNode; }
/*      */   public final Instruction startElement() { return this._startElement; }
/*      */   public final Instruction endElement() { return this._endElement; }
/*      */   public final Instruction startDocument() { return this._startDocument; }
/*      */   public final Instruction endDocument() { return this._endDocument; }
/* 1839 */   public void markChunkStart() { getInstructionList()
/* 1840 */       .append(OutlineableChunkStart.OUTLINEABLECHUNKSTART);
/* 1841 */     this.m_totalChunks++;
/* 1842 */     this.m_openChunks++; } public final Instruction attribute() { return this._attribute; }
/*      */   public final Instruction uniqueAttribute() { return this._uniqueAttribute; }
/*      */   public final Instruction namespace() { return this._namespace; }
/*      */   public Instruction loadCurrentNode() { if (this._iloadCurrent == null) { int idx = getLocalIndex("current"); if (idx > 0) { this._iloadCurrent = new ILOAD(idx); } else { this._iloadCurrent = new ICONST(0); }  }  return this._iloadCurrent; }
/*      */   public Instruction storeCurrentNode() { return (this._istoreCurrent != null) ? this._istoreCurrent : (this._istoreCurrent = new ISTORE(getLocalIndex("current"))); }
/*      */   public Instruction loadContextNode() { return loadCurrentNode(); }
/*      */   public Instruction storeContextNode() { return storeCurrentNode(); }
/*      */   public int getLocalIndex(String name) { return getLocalVariable(name).getIndex(); }
/*      */   public LocalVariableGen getLocalVariable(String name) { return getLocalVariableRegistry().lookUpByName(name); }
/* 1851 */   public void markChunkEnd() { getInstructionList()
/* 1852 */       .append(OutlineableChunkEnd.OUTLINEABLECHUNKEND);
/* 1853 */     this.m_openChunks--;
/* 1854 */     if (this.m_openChunks < 0)
/*      */     
/* 1856 */     { String msg = (new ErrorMsg("OUTLINE_ERR_UNBALANCED_MARKERS")).toString();
/* 1857 */       throw new InternalError(msg); }  } public void setMaxLocals() { int maxLocals = getMaxLocals(); int prevLocals = maxLocals; LocalVariableGen[] localVars = getLocalVariables(); if (localVars != null && localVars.length > maxLocals)
/*      */       maxLocals = localVars.length;  if (maxLocals < 5)
/*      */       maxLocals = 5;  setMaxLocals(maxLocals); } public void addInstructionList(Pattern pattern, InstructionList ilist) { this._preCompiled.put(pattern, ilist); }
/*      */   public InstructionList getInstructionList(Pattern pattern) { return this._preCompiled.get(pattern); }
/*      */   private class Chunk implements Comparable {
/*      */     private InstructionHandle m_start; private InstructionHandle m_end; private int m_size;
/*      */     Chunk(InstructionHandle start, InstructionHandle end) { this.m_start = start; this.m_end = end; this.m_size = end.getPosition() - start.getPosition(); }
/*      */     boolean isAdjacentTo(Chunk neighbour) { return (getChunkEnd().getNext() == neighbour.getChunkStart()); }
/*      */     InstructionHandle getChunkStart() { return this.m_start; }
/*      */     InstructionHandle getChunkEnd() { return this.m_end; }
/*      */     int getChunkSize() { return this.m_size; }
/*      */     public int compareTo(Object comparand) { return getChunkSize() - ((Chunk)comparand).getChunkSize(); } }
/*      */   private ArrayList getCandidateChunks(ClassGenerator classGen, int totalMethodSize) { InstructionHandle currentHandle; Iterator<InstructionHandle> instructions = getInstructionList().iterator(); ArrayList<Chunk> candidateChunks = new ArrayList(); ArrayList<InstructionHandle> currLevelChunks = new ArrayList(); Stack<ArrayList<InstructionHandle>> subChunkStack = new Stack(); boolean openChunkAtCurrLevel = false; boolean firstInstruction = true; if (this.m_openChunks != 0) { String msg = (new ErrorMsg("OUTLINE_ERR_UNBALANCED_MARKERS")).toString(); throw new InternalError(msg); }  do { currentHandle = instructions.hasNext() ? instructions.next() : null; Instruction inst = (currentHandle != null) ? currentHandle.getInstruction() : null; if (firstInstruction) { openChunkAtCurrLevel = true; currLevelChunks.add(currentHandle); firstInstruction = false; }  if (inst instanceof OutlineableChunkStart) { if (openChunkAtCurrLevel) { subChunkStack.push(currLevelChunks); currLevelChunks = new ArrayList<>(); }  openChunkAtCurrLevel = true; currLevelChunks.add(currentHandle); } else if (currentHandle == null || inst instanceof OutlineableChunkEnd) { ArrayList<InstructionHandle> nestedSubChunks = null; if (!openChunkAtCurrLevel) { nestedSubChunks = currLevelChunks; currLevelChunks = subChunkStack.pop(); }  InstructionHandle chunkStart = currLevelChunks.get(currLevelChunks.size() - 1); int chunkEndPosition = (currentHandle != null) ? currentHandle.getPosition() : totalMethodSize; int chunkSize = chunkEndPosition - chunkStart.getPosition(); if (chunkSize <= 60000) { currLevelChunks.add(currentHandle); } else { if (!openChunkAtCurrLevel) { int childChunkCount = nestedSubChunks.size() / 2; if (childChunkCount > 0) { Chunk[] childChunks = new Chunk[childChunkCount]; for (int i = 0; i < childChunkCount; i++) { InstructionHandle start = nestedSubChunks.get(i * 2); InstructionHandle end = nestedSubChunks.get(i * 2 + 1); childChunks[i] = new Chunk(start, end); }  ArrayList<Chunk> mergedChildChunks = mergeAdjacentChunks(childChunks); for (int j = 0; j < mergedChildChunks.size(); j++) { Chunk mergedChunk = mergedChildChunks.get(j); int mergedSize = mergedChunk.getChunkSize(); if (mergedSize >= 1000 && mergedSize <= 60000)
/*      */                   candidateChunks.add(mergedChunk);  }
/*      */                }
/*      */              }
/*      */            currLevelChunks.remove(currLevelChunks.size() - 1); }
/*      */          openChunkAtCurrLevel = ((currLevelChunks.size() & 0x1) == 1); }
/*      */        }
/*      */     while (currentHandle != null); return candidateChunks; }
/* 1877 */   Method[] getGeneratedMethods(ClassGenerator classGen) { Method[] generatedMethods; InstructionList il = getInstructionList();
/* 1878 */     InstructionHandle last = il.getEnd();
/*      */     
/* 1880 */     il.setPositions();
/*      */ 
/*      */     
/* 1883 */     int instructionListSize = last.getPosition() + last.getInstruction().getLength();
/*      */ 
/*      */ 
/*      */     
/* 1887 */     if (instructionListSize > 32767) {
/* 1888 */       boolean ilChanged = widenConditionalBranchTargetOffsets();
/*      */ 
/*      */ 
/*      */       
/* 1892 */       if (ilChanged) {
/* 1893 */         il.setPositions();
/* 1894 */         last = il.getEnd();
/*      */         
/* 1896 */         instructionListSize = last.getPosition() + last.getInstruction().getLength();
/*      */       } 
/*      */     } 
/*      */     
/* 1900 */     if (instructionListSize > 65535) {
/* 1901 */       generatedMethods = outlineChunks(classGen, instructionListSize);
/*      */     } else {
/* 1903 */       generatedMethods = new Method[] { getThisMethod() };
/*      */     } 
/* 1905 */     return generatedMethods; } private ArrayList mergeAdjacentChunks(Chunk[] chunks) { int[] adjacencyRunStart = new int[chunks.length]; int[] adjacencyRunLength = new int[chunks.length]; boolean[] chunkWasMerged = new boolean[chunks.length]; int maximumRunOfChunks = 0; int numAdjacentRuns = 0; ArrayList<Chunk> mergedChunks = new ArrayList(); int startOfCurrentRun = 0; for (int j = 1; j < chunks.length; j++) { if (!chunks[j - 1].isAdjacentTo(chunks[j])) { int lengthOfRun = j - startOfCurrentRun; if (maximumRunOfChunks < lengthOfRun) maximumRunOfChunks = lengthOfRun;  if (lengthOfRun > 1) { adjacencyRunLength[numAdjacentRuns] = lengthOfRun; adjacencyRunStart[numAdjacentRuns] = startOfCurrentRun; numAdjacentRuns++; }  startOfCurrentRun = j; }  }  if (chunks.length - startOfCurrentRun > 1) { int lengthOfRun = chunks.length - startOfCurrentRun; if (maximumRunOfChunks < lengthOfRun) maximumRunOfChunks = lengthOfRun;  adjacencyRunLength[numAdjacentRuns] = chunks.length - startOfCurrentRun; adjacencyRunStart[numAdjacentRuns] = startOfCurrentRun; numAdjacentRuns++; }  for (int numToMerge = maximumRunOfChunks; numToMerge > 1; numToMerge--) { for (int run = 0; run < numAdjacentRuns; run++) { int runStart = adjacencyRunStart[run]; int runEnd = runStart + adjacencyRunLength[run] - 1; boolean foundChunksToMerge = false; int mergeStart = runStart; for (; mergeStart + numToMerge - 1 <= runEnd && !foundChunksToMerge; mergeStart++) { int mergeEnd = mergeStart + numToMerge - 1; int mergeSize = 0; int k; for (k = mergeStart; k <= mergeEnd; k++) mergeSize += chunks[k].getChunkSize();  if (mergeSize <= 60000) { foundChunksToMerge = true; for (k = mergeStart; k <= mergeEnd; k++)
/*      */               chunkWasMerged[k] = true;  mergedChunks.add(new Chunk(chunks[mergeStart].getChunkStart(), chunks[mergeEnd].getChunkEnd())); adjacencyRunLength[run] = adjacencyRunStart[run] - mergeStart; int trailingRunLength = runEnd - mergeEnd; if (trailingRunLength >= 2) { adjacencyRunStart[numAdjacentRuns] = mergeEnd + 1; adjacencyRunLength[numAdjacentRuns] = trailingRunLength; numAdjacentRuns++; }  }  }  }  }  for (int i = 0; i < chunks.length; i++) { if (!chunkWasMerged[i])
/*      */         mergedChunks.add(chunks[i]);  }  return mergedChunks; }
/*      */   public Method[] outlineChunks(ClassGenerator classGen, int originalMethodSize) { boolean moreMethodsOutlined; ArrayList<Method> methodsOutlined = new ArrayList(); int currentMethodSize = originalMethodSize; int outlinedCount = 0; String originalMethodName = getName(); if (originalMethodName.equals("<init>")) { originalMethodName = "$lt$init$gt$"; } else if (originalMethodName.equals("<clinit>")) { originalMethodName = "$lt$clinit$gt$"; }  do { ArrayList<Comparable> candidateChunks = getCandidateChunks(classGen, currentMethodSize); Collections.sort(candidateChunks); moreMethodsOutlined = false; int i = candidateChunks.size() - 1; for (; i >= 0 && currentMethodSize > 60000; i--) { Chunk chunkToOutline = (Chunk)candidateChunks.get(i); methodsOutlined.add(outline(chunkToOutline.getChunkStart(), chunkToOutline.getChunkEnd(), originalMethodName + "$outline$" + outlinedCount, classGen)); outlinedCount++; moreMethodsOutlined = true; InstructionList il = getInstructionList(); InstructionHandle lastInst = il.getEnd(); il.setPositions(); currentMethodSize = lastInst.getPosition() + lastInst.getInstruction().getLength(); }  } while (moreMethodsOutlined && currentMethodSize > 60000); if (currentMethodSize > 65535) { String msg = (new ErrorMsg("OUTLINE_ERR_METHOD_TOO_BIG")).toString(); throw new InternalError(msg); }  Method[] methodsArr = new Method[methodsOutlined.size() + 1]; methodsOutlined.toArray(methodsArr); methodsArr[methodsOutlined.size()] = getThisMethod(); return methodsArr; }
/* 1909 */   protected Method getThisMethod() { stripAttributes(true);
/* 1910 */     setMaxLocals();
/* 1911 */     setMaxStack();
/* 1912 */     removeNOPs();
/*      */     
/* 1914 */     return getMethod(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean widenConditionalBranchTargetOffsets() {
/* 1977 */     boolean ilChanged = false;
/* 1978 */     int maxOffsetChange = 0;
/* 1979 */     InstructionList il = getInstructionList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1991 */     InstructionHandle ih = il.getStart();
/* 1992 */     for (; ih != null; 
/* 1993 */       ih = ih.getNext()) {
/* 1994 */       Instruction inst = ih.getInstruction();
/*      */       
/* 1996 */       switch (inst.getOpcode()) {
/*      */ 
/*      */         
/*      */         case 167:
/*      */         case 168:
/* 2001 */           maxOffsetChange += 2;
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 170:
/*      */         case 171:
/* 2010 */           maxOffsetChange += 3;
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 153:
/*      */         case 154:
/*      */         case 155:
/*      */         case 156:
/*      */         case 157:
/*      */         case 158:
/*      */         case 159:
/*      */         case 160:
/*      */         case 161:
/*      */         case 162:
/*      */         case 163:
/*      */         case 164:
/*      */         case 165:
/*      */         case 166:
/*      */         case 198:
/*      */         case 199:
/* 2031 */           maxOffsetChange += 5;
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/* 2039 */     ih = il.getStart();
/* 2040 */     for (; ih != null; 
/* 2041 */       ih = ih.getNext()) {
/* 2042 */       Instruction inst = ih.getInstruction();
/*      */       
/* 2044 */       if (inst instanceof IfInstruction) {
/* 2045 */         IfInstruction oldIfInst = (IfInstruction)inst;
/* 2046 */         BranchHandle oldIfHandle = (BranchHandle)ih;
/* 2047 */         InstructionHandle target = oldIfInst.getTarget();
/*      */         
/* 2049 */         int relativeTargetOffset = target.getPosition() - oldIfHandle.getPosition();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2056 */         if (relativeTargetOffset - maxOffsetChange < -32768 || relativeTargetOffset + maxOffsetChange > 32767) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2063 */           InstructionHandle nextHandle = oldIfHandle.getNext();
/* 2064 */           IfInstruction invertedIfInst = oldIfInst.negate();
/* 2065 */           BranchHandle invertedIfHandle = il.append(oldIfHandle, invertedIfInst);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2070 */           BranchHandle gotoHandle = il.append(invertedIfHandle, new GOTO(target));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2076 */           if (nextHandle == null) {
/* 2077 */             nextHandle = il.append(gotoHandle, NOP);
/*      */           }
/*      */ 
/*      */           
/* 2081 */           invertedIfHandle.updateTarget(target, nextHandle);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2086 */           if (oldIfHandle.hasTargeters()) {
/*      */             
/* 2088 */             InstructionTargeter[] targeters = oldIfHandle.getTargeters();
/*      */             
/* 2090 */             for (int i = 0; i < targeters.length; i++) {
/* 2091 */               InstructionTargeter targeter = targeters[i];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2105 */               if (targeter instanceof LocalVariableGen) {
/* 2106 */                 LocalVariableGen lvg = (LocalVariableGen)targeter;
/*      */                 
/* 2108 */                 if (lvg.getStart() == oldIfHandle) {
/* 2109 */                   lvg.setStart(invertedIfHandle);
/* 2110 */                 } else if (lvg.getEnd() == oldIfHandle) {
/* 2111 */                   lvg.setEnd(gotoHandle);
/*      */                 } 
/*      */               } else {
/* 2114 */                 targeter.updateTarget(oldIfHandle, invertedIfHandle);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*      */           try {
/* 2121 */             il.delete(oldIfHandle);
/* 2122 */           } catch (TargetLostException tle) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2128 */             String msg = (new ErrorMsg("OUTLINE_ERR_DELETED_TARGET", tle.getMessage())).toString();
/* 2129 */             throw new InternalError(msg);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 2134 */           ih = gotoHandle;
/*      */ 
/*      */           
/* 2137 */           ilChanged = true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2143 */     return ilChanged;
/*      */   }
/*      */   
/*      */   private Method outline(InstructionHandle first, InstructionHandle last, String outlinedMethodName, ClassGenerator classGen) {
/*      */     InstructionHandle outlinedMethodRef;
/*      */     if ((getExceptionHandlers()).length != 0) {
/*      */       String msg = (new ErrorMsg("OUTLINE_ERR_TRY_CATCH")).toString();
/*      */       throw new InternalError(msg);
/*      */     } 
/*      */     int outlineChunkStartOffset = first.getPosition();
/*      */     int outlineChunkEndOffset = last.getPosition() + last.getInstruction().getLength();
/*      */     ConstantPoolGen cpg = getConstantPool();
/*      */     InstructionList newIL = new InstructionList();
/*      */     XSLTC xsltc = classGen.getParser().getXSLTC();
/*      */     String argTypeName = xsltc.getHelperClassName();
/*      */     Type[] argTypes = { (new ObjectType(argTypeName)).toJCType() };
/*      */     String argName = "copyLocals";
/*      */     String[] argNames = { "copyLocals" };
/*      */     int methodAttributes = 18;
/*      */     boolean isStaticMethod = ((getAccessFlags() & 0x8) != 0);
/*      */     if (isStaticMethod)
/*      */       methodAttributes |= 0x8; 
/*      */     MethodGenerator outlinedMethodGen = new MethodGenerator(methodAttributes, Type.VOID, argTypes, argNames, outlinedMethodName, getClassName(), newIL, cpg);
/*      */     ClassGenerator copyAreaCG = new ClassGenerator(argTypeName, "java.lang.Object", argTypeName + ".java", 49, null, classGen.getStylesheet()) {
/*      */         public boolean isExternal() {
/*      */           return true;
/*      */         }
/*      */       };
/*      */     ConstantPoolGen copyAreaCPG = copyAreaCG.getConstantPool();
/*      */     copyAreaCG.addEmptyConstructor(1);
/*      */     int copyAreaFieldCount = 0;
/*      */     InstructionHandle limit = last.getNext();
/*      */     InstructionList oldMethCopyInIL = new InstructionList();
/*      */     InstructionList oldMethCopyOutIL = new InstructionList();
/*      */     InstructionList newMethCopyInIL = new InstructionList();
/*      */     InstructionList newMethCopyOutIL = new InstructionList();
/*      */     InstructionHandle outlinedMethodCallSetup = oldMethCopyInIL.append(new NEW(cpg.addClass(argTypeName)));
/*      */     oldMethCopyInIL.append(InstructionConstants.DUP);
/*      */     oldMethCopyInIL.append(InstructionConstants.DUP);
/*      */     oldMethCopyInIL.append(new INVOKESPECIAL(cpg.addMethodref(argTypeName, "<init>", "()V")));
/*      */     if (isStaticMethod) {
/*      */       outlinedMethodRef = oldMethCopyOutIL.append(new INVOKESTATIC(cpg.addMethodref(classGen.getClassName(), outlinedMethodName, outlinedMethodGen.getSignature())));
/*      */     } else {
/*      */       oldMethCopyOutIL.append(InstructionConstants.THIS);
/*      */       oldMethCopyOutIL.append(InstructionConstants.SWAP);
/*      */       outlinedMethodRef = oldMethCopyOutIL.append(new INVOKEVIRTUAL(cpg.addMethodref(classGen.getClassName(), outlinedMethodName, outlinedMethodGen.getSignature())));
/*      */     } 
/*      */     boolean chunkStartTargetMappingsPending = false;
/*      */     InstructionHandle pendingTargetMappingHandle = null;
/*      */     InstructionHandle lastCopyHandle = null;
/*      */     HashMap<Object, Object> targetMap = new HashMap<>();
/*      */     HashMap<Object, Object> localVarMap = new HashMap<>();
/*      */     HashMap<Object, Object> revisedLocalVarStart = new HashMap<>();
/*      */     HashMap<Object, Object> revisedLocalVarEnd = new HashMap<>();
/*      */     InstructionHandle ih;
/*      */     for (ih = first; ih != limit; ih = ih.getNext()) {
/*      */       Instruction inst = ih.getInstruction();
/*      */       if (inst instanceof MarkerInstruction) {
/*      */         if (ih.hasTargeters())
/*      */           if (inst instanceof OutlineableChunkEnd) {
/*      */             targetMap.put(ih, lastCopyHandle);
/*      */           } else if (!chunkStartTargetMappingsPending) {
/*      */             chunkStartTargetMappingsPending = true;
/*      */             pendingTargetMappingHandle = ih;
/*      */           }  
/*      */       } else {
/*      */         Instruction c = inst.copy();
/*      */         if (c instanceof BranchInstruction) {
/*      */           lastCopyHandle = newIL.append((BranchInstruction)c);
/*      */         } else {
/*      */           lastCopyHandle = newIL.append(c);
/*      */         } 
/*      */         if (c instanceof com.sun.org.apache.bcel.internal.generic.LocalVariableInstruction || c instanceof com.sun.org.apache.bcel.internal.generic.RET) {
/*      */           IndexedInstruction lvi = (IndexedInstruction)c;
/*      */           int oldLocalVarIndex = lvi.getIndex();
/*      */           LocalVariableGen oldLVG = getLocalVariableRegistry().lookupRegisteredLocalVariable(oldLocalVarIndex, ih.getPosition());
/*      */           LocalVariableGen newLVG = (LocalVariableGen)localVarMap.get(oldLVG);
/*      */           if (localVarMap.get(oldLVG) == null) {
/*      */             boolean copyInLocalValue = offsetInLocalVariableGenRange(oldLVG, (outlineChunkStartOffset != 0) ? (outlineChunkStartOffset - 1) : 0);
/*      */             boolean copyOutLocalValue = offsetInLocalVariableGenRange(oldLVG, outlineChunkEndOffset + 1);
/*      */             if (copyInLocalValue || copyOutLocalValue) {
/*      */               String varName = oldLVG.getName();
/*      */               Type varType = oldLVG.getType();
/*      */               newLVG = outlinedMethodGen.addLocalVariable(varName, varType, (InstructionHandle)null, (InstructionHandle)null);
/*      */               int newLocalVarIndex = newLVG.getIndex();
/*      */               String varSignature = varType.getSignature();
/*      */               localVarMap.put(oldLVG, newLVG);
/*      */               copyAreaFieldCount++;
/*      */               String copyAreaFieldName = "field" + copyAreaFieldCount;
/*      */               copyAreaCG.addField(new Field(1, copyAreaCPG.addUtf8(copyAreaFieldName), copyAreaCPG.addUtf8(varSignature), null, copyAreaCPG.getConstantPool()));
/*      */               int fieldRef = cpg.addFieldref(argTypeName, copyAreaFieldName, varSignature);
/*      */               if (copyInLocalValue) {
/*      */                 oldMethCopyInIL.append(InstructionConstants.DUP);
/*      */                 InstructionHandle copyInLoad = oldMethCopyInIL.append(loadLocal(oldLocalVarIndex, varType));
/*      */                 oldMethCopyInIL.append(new PUTFIELD(fieldRef));
/*      */                 if (!copyOutLocalValue)
/*      */                   revisedLocalVarEnd.put(oldLVG, copyInLoad); 
/*      */                 newMethCopyInIL.append(InstructionConstants.ALOAD_1);
/*      */                 newMethCopyInIL.append(new GETFIELD(fieldRef));
/*      */                 newMethCopyInIL.append(storeLocal(newLocalVarIndex, varType));
/*      */               } 
/*      */               if (copyOutLocalValue) {
/*      */                 newMethCopyOutIL.append(InstructionConstants.ALOAD_1);
/*      */                 newMethCopyOutIL.append(loadLocal(newLocalVarIndex, varType));
/*      */                 newMethCopyOutIL.append(new PUTFIELD(fieldRef));
/*      */                 oldMethCopyOutIL.append(InstructionConstants.DUP);
/*      */                 oldMethCopyOutIL.append(new GETFIELD(fieldRef));
/*      */                 InstructionHandle copyOutStore = oldMethCopyOutIL.append(storeLocal(oldLocalVarIndex, varType));
/*      */                 if (!copyInLocalValue)
/*      */                   revisedLocalVarStart.put(oldLVG, copyOutStore); 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         if (ih.hasTargeters())
/*      */           targetMap.put(ih, lastCopyHandle); 
/*      */         if (chunkStartTargetMappingsPending) {
/*      */           do {
/*      */             targetMap.put(pendingTargetMappingHandle, lastCopyHandle);
/*      */             pendingTargetMappingHandle = pendingTargetMappingHandle.getNext();
/*      */           } while (pendingTargetMappingHandle != ih);
/*      */           chunkStartTargetMappingsPending = false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     ih = first;
/*      */     InstructionHandle ch = newIL.getStart();
/*      */     while (ch != null) {
/*      */       Instruction instruction1 = ih.getInstruction();
/*      */       Instruction c = ch.getInstruction();
/*      */       if (instruction1 instanceof BranchInstruction) {
/*      */         BranchInstruction bc = (BranchInstruction)c;
/*      */         BranchInstruction bi = (BranchInstruction)instruction1;
/*      */         InstructionHandle itarget = bi.getTarget();
/*      */         InstructionHandle newTarget = (InstructionHandle)targetMap.get(itarget);
/*      */         bc.setTarget(newTarget);
/*      */         if (bi instanceof Select) {
/*      */           InstructionHandle[] itargets = ((Select)bi).getTargets();
/*      */           InstructionHandle[] ctargets = ((Select)bc).getTargets();
/*      */           for (int j = 0; j < itargets.length; j++)
/*      */             ctargets[j] = (InstructionHandle)targetMap.get(itargets[j]); 
/*      */         } 
/*      */       } else if (instruction1 instanceof com.sun.org.apache.bcel.internal.generic.LocalVariableInstruction || instruction1 instanceof com.sun.org.apache.bcel.internal.generic.RET) {
/*      */         int newLocalVarIndex;
/*      */         IndexedInstruction lvi = (IndexedInstruction)c;
/*      */         int oldLocalVarIndex = lvi.getIndex();
/*      */         LocalVariableGen oldLVG = getLocalVariableRegistry().lookupRegisteredLocalVariable(oldLocalVarIndex, ih.getPosition());
/*      */         LocalVariableGen newLVG = (LocalVariableGen)localVarMap.get(oldLVG);
/*      */         if (newLVG == null) {
/*      */           String varName = oldLVG.getName();
/*      */           Type varType = oldLVG.getType();
/*      */           newLVG = outlinedMethodGen.addLocalVariable(varName, varType, (InstructionHandle)null, (InstructionHandle)null);
/*      */           newLocalVarIndex = newLVG.getIndex();
/*      */           localVarMap.put(oldLVG, newLVG);
/*      */           revisedLocalVarStart.put(oldLVG, outlinedMethodRef);
/*      */           revisedLocalVarEnd.put(oldLVG, outlinedMethodRef);
/*      */         } else {
/*      */           newLocalVarIndex = newLVG.getIndex();
/*      */         } 
/*      */         lvi.setIndex(newLocalVarIndex);
/*      */       } 
/*      */       if (ih.hasTargeters()) {
/*      */         InstructionTargeter[] targeters = ih.getTargeters();
/*      */         for (int idx = 0; idx < targeters.length; idx++) {
/*      */           InstructionTargeter targeter = targeters[idx];
/*      */           if (targeter instanceof LocalVariableGen && ((LocalVariableGen)targeter).getEnd() == ih) {
/*      */             Object newLVG = localVarMap.get(targeter);
/*      */             if (newLVG != null)
/*      */               outlinedMethodGen.removeLocalVariable((LocalVariableGen)newLVG); 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       if (!(instruction1 instanceof MarkerInstruction))
/*      */         ch = ch.getNext(); 
/*      */       ih = ih.getNext();
/*      */     } 
/*      */     oldMethCopyOutIL.append(InstructionConstants.POP);
/*      */     Iterator<Map.Entry> revisedLocalVarStartPairIter = revisedLocalVarStart.entrySet().iterator();
/*      */     while (revisedLocalVarStartPairIter.hasNext()) {
/*      */       Map.Entry lvgRangeStartPair = revisedLocalVarStartPairIter.next();
/*      */       LocalVariableGen lvg = (LocalVariableGen)lvgRangeStartPair.getKey();
/*      */       InstructionHandle startInst = (InstructionHandle)lvgRangeStartPair.getValue();
/*      */       lvg.setStart(startInst);
/*      */     } 
/*      */     Iterator<Map.Entry> revisedLocalVarEndPairIter = revisedLocalVarEnd.entrySet().iterator();
/*      */     while (revisedLocalVarEndPairIter.hasNext()) {
/*      */       Map.Entry lvgRangeEndPair = revisedLocalVarEndPairIter.next();
/*      */       LocalVariableGen lvg = (LocalVariableGen)lvgRangeEndPair.getKey();
/*      */       InstructionHandle endInst = (InstructionHandle)lvgRangeEndPair.getValue();
/*      */       lvg.setEnd(endInst);
/*      */     } 
/*      */     xsltc.dumpClass(copyAreaCG.getJavaClass());
/*      */     InstructionList oldMethodIL = getInstructionList();
/*      */     oldMethodIL.insert(first, oldMethCopyInIL);
/*      */     oldMethodIL.insert(first, oldMethCopyOutIL);
/*      */     newIL.insert(newMethCopyInIL);
/*      */     newIL.append(newMethCopyOutIL);
/*      */     newIL.append(InstructionConstants.RETURN);
/*      */     try {
/*      */       oldMethodIL.delete(first, last);
/*      */     } catch (TargetLostException e) {
/*      */       InstructionHandle[] targets = e.getTargets();
/*      */       for (int j = 0; j < targets.length; j++) {
/*      */         InstructionHandle lostTarget = targets[j];
/*      */         InstructionTargeter[] targeters = lostTarget.getTargeters();
/*      */         for (int k = 0; k < targeters.length; k++) {
/*      */           if (targeters[k] instanceof LocalVariableGen) {
/*      */             LocalVariableGen lvgTargeter = (LocalVariableGen)targeters[k];
/*      */             if (lvgTargeter.getStart() == lostTarget)
/*      */               lvgTargeter.setStart(outlinedMethodRef); 
/*      */             if (lvgTargeter.getEnd() == lostTarget)
/*      */               lvgTargeter.setEnd(outlinedMethodRef); 
/*      */           } else {
/*      */             targeters[k].updateTarget(lostTarget, outlinedMethodCallSetup);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     String[] exceptions = getExceptions();
/*      */     for (int i = 0; i < exceptions.length; i++)
/*      */       outlinedMethodGen.addException(exceptions[i]); 
/*      */     return outlinedMethodGen.getThisMethod();
/*      */   }
/*      */   
/*      */   private static Instruction loadLocal(int index, Type type) {
/*      */     if (type == Type.BOOLEAN)
/*      */       return new ILOAD(index); 
/*      */     if (type == Type.INT)
/*      */       return new ILOAD(index); 
/*      */     if (type == Type.SHORT)
/*      */       return new ILOAD(index); 
/*      */     if (type == Type.LONG)
/*      */       return new LLOAD(index); 
/*      */     if (type == Type.BYTE)
/*      */       return new ILOAD(index); 
/*      */     if (type == Type.CHAR)
/*      */       return new ILOAD(index); 
/*      */     if (type == Type.FLOAT)
/*      */       return new FLOAD(index); 
/*      */     if (type == Type.DOUBLE)
/*      */       return new DLOAD(index); 
/*      */     return new ALOAD(index);
/*      */   }
/*      */   
/*      */   private static Instruction storeLocal(int index, Type type) {
/*      */     if (type == Type.BOOLEAN)
/*      */       return new ISTORE(index); 
/*      */     if (type == Type.INT)
/*      */       return new ISTORE(index); 
/*      */     if (type == Type.SHORT)
/*      */       return new ISTORE(index); 
/*      */     if (type == Type.LONG)
/*      */       return new LSTORE(index); 
/*      */     if (type == Type.BYTE)
/*      */       return new ISTORE(index); 
/*      */     if (type == Type.CHAR)
/*      */       return new ISTORE(index); 
/*      */     if (type == Type.FLOAT)
/*      */       return new FSTORE(index); 
/*      */     if (type == Type.DOUBLE)
/*      */       return new DSTORE(index); 
/*      */     return new ASTORE(index);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/util/MethodGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */