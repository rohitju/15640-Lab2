package ds.rmi.util;

import java.io.Serializable;

public class RemoteMethod implements Serializable {
	private static final long serialVersionUID = 1L;
	private String className;
	private String methodName;
	private String objectId;
	private Class<?>[] parameterTypes;
	private Object[] parameters;
	
	public RemoteMethod(String cName, String mName, String id, Class<?>[] paramTypes, Object[] params) {
		className = cName;
		methodName = mName;
		objectId = id;
		parameterTypes = paramTypes;
		parameters = params;
	}
	
	public String getClassName() {
		return className;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public String getObjectId() {
		return objectId;
	}
	
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}
	
	public Object[] getParams() {
		return parameters;
	}
}
