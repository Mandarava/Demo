package kettle;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;

public class test {
	
	public static void main(String[] args) {
		 try {
			KettleEnvironment.init();
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
