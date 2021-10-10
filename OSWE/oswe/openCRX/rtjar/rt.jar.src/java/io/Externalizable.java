package java.io;

public interface Externalizable extends Serializable {
  void writeExternal(ObjectOutput paramObjectOutput) throws IOException;
  
  void readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/Externalizable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */