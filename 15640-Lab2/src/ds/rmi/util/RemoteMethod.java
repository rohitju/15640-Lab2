package ds.rmi.util;

import java.io.Serializable;
import java.util.ArrayList;

public class RemoteMethod implements Serializable {
	private static final long serialVersionUID = 1L;
	private String className;
	private String methodName;
	private ArrayList<Object> parameters;
	
	public RemoteMethod(String cName, String mName, ArrayList<Object> params) {
		className = cName;
		methodName = mName;
		parameters = params;
	}
	
	public String getClassName() {
		return className;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public ArrayList<Object> getParams() {
		return parameters;
	}
}
