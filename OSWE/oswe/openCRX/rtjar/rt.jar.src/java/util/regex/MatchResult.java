package java.util.regex;

public interface MatchResult {
  int start();
  
  int start(int paramInt);
  
  int end();
  
  int end(int paramInt);
  
  String group();
  
  String group(int paramInt);
  
  int groupCount();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/regex/MatchResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */