package ds.rmi.util;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class RemoteObject implements Serializable{
	private static final long serialVersionUID = 1L;
	String host;
	int port;
	String objKey;
	String interfaceName;
	
	public RemoteObject(String h, int p, String o, String i)
	{
		host = h;
		port = p;
		objKey = o;
		interfaceName = i;
	}
	
	public String getServiceName() {
		return interfaceName;
	}
	
	public String getHost(){
		return host;
	}
	
	public int getPort(){
		return port;
	}
	
	public Object localize() 
	{
		Class<?> c = null;
		Constructor<?> con = null;
		Object o = null;
		try {
			c = Class.forName(interfaceName + "_stub");
			con = c.getConstructor(String.class, Integer.class);
			o = con.newInstance(host, port);
			System.out.println("Class name is " + c.getName());
		} catch (NoSuchMethodException e) {
			System.out.println("Remote Object: No such method: " + e.getMessage());
		} catch (SecurityException e) {
			System.out.println("Remote Object: Security exception: " + e.getMessage());
		}  catch (InvocationTargetException e) {
			System.out.println("Remote Object: Invocation error: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Remote Object: Class not found error: " + e.getMessage());
		} catch (IllegalAccessException e) {
			System.out.println("Remote Object: Illegal access error: " + e.getMessage());
		} catch (InstantiationException e) {
			System.out.println("Remote Object: Instantiation error: " + e.getMessage());
		}
		return o;
	}
}
