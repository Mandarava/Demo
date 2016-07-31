package kettle;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.Repository;

public class KettleJavaClassExecutinJob {

	public static void main(String[] args) {

		String file = "src/config/demo.kjb";

		/**
		 * Unlike the ktr java code, for executing a kjb, you will need to
		 * define a repository path too. Since i haven’t used any repository, it
		 * is null for me. But in case you are using a repo, you might need to
		 * define it.
		 */
		Repository repository = null;

		try {
			KettleEnvironment.init();

			JobMeta jobmeta = new JobMeta(file, repository);
			Job job = new Job(repository, jobmeta);

			job.start();
			job.waitUntilFinished();

			if (job.getErrors() > 0) {
				System.out.println("Error Executing Job");
			}

		} catch (KettleException e) {
			e.printStackTrace();
		}
		
		/*try {
            KettleEnvironment.init();
            
            JobMeta jobmeta=new JobMeta(file,repository);
            Job job=new Job(repository, jobmeta);
            
             Doing the basic initialization for executing a job 
            job.initializeVariablesFrom(null);
            job.getJobMeta().setInternalKettleVariables(job);
            job.copyParametersFrom(job.getJobMeta());
            

             Setting the Parameters to the job object
             * @Parameter Names: PARAM_ID and PARAM_NAME
             
            job.setParameterValue("PARAM_ID", "1");
            job.setParameterValue("PARAM_NAME", "Rishu");
            

             Activating the Parameters and executing the Job   
            job.activateParameters();
            job.execute(0, null);
            job.waitUntilFinished();
            
            if(job.getErrors()>0){
                System.out.println("Erroruting Job");
            }
            
            job.setFinished(true);
            
                        
        } catch (KettleException e) {
           e.printStackTrace();
        }*/

	}

}
