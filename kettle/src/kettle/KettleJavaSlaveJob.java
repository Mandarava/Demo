package kettle;

import org.pentaho.di.cluster.SlaveServer;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogWriter;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.job.JobConfiguration;
import org.pentaho.di.job.JobExecutionConfiguration;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.TransConfiguration;
import org.pentaho.di.trans.TransExecutionConfiguration;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.www.AddJobServlet;
import org.pentaho.di.www.AddTransServlet;
import org.pentaho.di.www.Carte;
import org.pentaho.di.www.SlaveServerConfig;
import org.pentaho.di.www.WebResult;

public class KettleJavaSlaveJob {

	public static void main(String[] args) {

		String file = "src/config/test1.ktr";
		Repository repository = null;

		try {
			EnvUtil.environmentInit();
			LogWriter log = LogWriter.getInstance();

			KettleEnvironment.init();
			TransMeta metadata = new TransMeta(file);
			// Trans trans=new Trans(metadata);

			TransExecutionConfiguration tec = new TransExecutionConfiguration();
			TransConfiguration tconf = new TransConfiguration(metadata, tec);

			JobMeta jobmeta = new JobMeta(file, repository);
			JobExecutionConfiguration jec = new JobExecutionConfiguration();
			// System.out.println(jec.toString());
			JobConfiguration jConf = new JobConfiguration(jobmeta, jec);
			// System.out.println(jConf.getJobExecutionConfiguration());

			// Job job=new Job(repository, jobmeta);

			SlaveServer ss = new SlaveServer("Slave B", "localhost", "8084", "cluster", "cluster");
			SlaveServerConfig sc = new SlaveServerConfig(ss);

			System.out.println(sc.getSlaveServer() + ">>>");

			try {
				ss.sendXML(jConf.getXML(), AddJobServlet.CONTEXT_PATH + "/?xml=Y");
				// System.out.println(jConf.getXML()+" ----
				// "+AddJobServlet.CONTEXT_PATH);
				// Job.sendToSlaveServer(jobmeta,jec,null,null);
				WebResult wr = ss.startJob(jobmeta.getName(), "A");

				tec.setRemoteServer(ss);
				Carte.runCarte(sc);
				System.out.println(">>>>>");

				ss.sendXML(tconf.getXML(), AddTransServlet.CONTEXT_PATH + "/?xml=Y");
				System.out.println(metadata.getName() + "----" + metadata.getStepNames()[1]);
				System.out.println(AddTransServlet.CONTEXT_PATH);

				// Trans.sendToSlaveServer(metadata,tec, repository, null);
				ss.startTransformation(metadata.getName(), "vvvv");

				System.out.println("----");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println(ss.getName());

			// System.out.println("---"+wr.getResult());
			// job.start();
			// job.waitUntilFinished();

			// if(job.getErrors()>0){
			// System.out.println("Error Executing Job");
			// }

		} catch (KettleException e) {
			e.printStackTrace();
		}

	}

}
