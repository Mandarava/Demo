package kettle;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;


/**
 * 执行Kettle工具类
 * 
 */
public class KettleUtil {

	/** 目录 */
	private static final String subPath = "kjb";

	/**
	 * 批量执行kettle文件
	 * 
	 * @throws KettleException
	 */
	public static void runKettle() throws Exception {
		String path = KettleUtil.class.getResource("/").getPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF")) + subPath;
		} else {
			throw new Exception("Failed to get WebRoot Path.");
		}
		File[] files = new File(path).listFiles();
		for (File file : files) {
			if (file.isFile()) {
				if (file.getPath().endsWith(".ktr")) {
					/* 执行转换 */
					KettleUtil.executeTrasformation(file.getPath(), null);
				} else if (file.getPath().endsWith(".kjb")) {
					/* 执行作业 */
					KettleUtil.executeJob(file.getPath(), null);
				}
			} else if (file.isDirectory()) {
				KettleUtil.runKettle(file.getPath());
			}
		}

	}

	/**
	 * 批量执行kettle文件
	 * 
	 * @param path
	 *            文件路径
	 * @throws KettleException
	 */
	public static void runKettle(String path) throws Exception {
		File[] files = new File(path).listFiles();
		for (File file : files) {
			if (file.isFile()) {
				if (file.getPath().endsWith(".ktr")) {
					/* 执行转换 */
					KettleUtil.executeTrasformation(file.getPath(), null);
				} else if (file.getPath().endsWith(".kjb")) {
					/* 执行作业 */
					KettleUtil.executeJob(file.getPath(), null);
				}
			} else if (file.isDirectory()) {
				/* 递归调用 */
				runKettle(file.getPath());
			}
		}
	}

	/**
	 * 执行kettle转换
	 * 
	 * @param ktrPath
	 *            转换文件的路径，后缀ktr
	 * @param variables
	 *            脚本参数
	 * @throws KettleException
	 */
	public static void executeTrasformation(String ktrPath, Map<String, String> variables) throws Exception {
		if (StringUtils.isEmpty(ktrPath)) {
			return;
		}
		try {
			/* 初始化 */
			KettleEnvironment.init();
			/* 转换元对象 */
			TransMeta metadata = new TransMeta(ktrPath);
			/* 转换 */
			Trans trans = new Trans(metadata);
			/* Setting the variable Values */
			if (null != variables) {
				Set<Map.Entry<String, String>> set = variables.entrySet();
				Iterator<Map.Entry<String, String>> iterator = set.iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, String> entry = iterator.next();
					trans.setVariable(entry.getKey(), entry.getValue());
				}
			}
			/* 执行转换 */
			trans.execute(null);
			/* 等待转换执行结束 */
			trans.waitUntilFinished();
			if (trans.getErrors() > 0) {
				throw new Exception("Error Executing Transformation");
			}
		} catch (KettleException ex) {
			throw ex;
		}
	}

	/**
	 * 执行kettle作业
	 * 
	 * @param kjbPath
	 *            作业文件的路径，后缀kjb
	 * @param variables
	 *            脚本参数
	 * @throws KettleException
	 */
	public static void executeJob(String kjbPath, Map<String, String> variables) throws Exception {
		if (StringUtils.isEmpty(kjbPath)) {
			return;
		}
		Repository repository = null;
		try {
			/* 初始化 */
			KettleEnvironment.init();
			JobMeta jobmeta = new JobMeta(kjbPath, repository);
			Job job = new Job(repository, jobmeta);
			/* 向Job 脚本传递参数，脚本中获取参数值：${参数名} */
			if (null != variables) {
				Set<Map.Entry<String, String>> set = variables.entrySet();
				Iterator<Map.Entry<String, String>> iterator = set.iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, String> entry = iterator.next();
					job.setVariable(entry.getKey(), entry.getValue());
				}
			}
			job.start();
			/* 等待作业执行结束 */
			job.waitUntilFinished();
			if (job.getErrors() > 0) {
				throw new Exception("Error Executing Job");
			}
		} catch (KettleException ex) {
			throw ex;
		}
	}
}
