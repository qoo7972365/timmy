package javax.script;

public interface Invocable {
  Object invokeMethod(Object paramObject, String paramString, Object... paramVarArgs) throws ScriptException, NoSuchMethodException;
  
  Object invokeFunction(String paramString, Object... paramVarArgs) throws ScriptException, NoSuchMethodException;
  
  <T> T getInterface(Class<T> paramClass);
  
  <T> T getInterface(Object paramObject, Class<T> paramClass);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/script/Invocable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */