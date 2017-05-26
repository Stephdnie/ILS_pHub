package src.io;

import src.io.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author Angel A. Juan
 * date: 140926
 */
public class TestsManager {

	public static ArrayList<Test> getTestsList(String testsFilePath,
			String[] testsFiles, String fileExtension) {

		ArrayList<Test> list = new ArrayList<Test>();
		for (String testsFile : testsFiles) {

			// Construct complete path for the tests2run file
			String path = testsFilePath + testsFile + fileExtension;
			String instancePath = ExtractInstancePath(testsFile);

			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(path));
				Scanner in = new Scanner(reader);
				in.useLocale(Locale.US);

				while (in.hasNextLine() && in.hasNext()) {
					String s = in.next();
					if (s.charAt(0) == '#') // this is a comment line
					{
						in.nextLine(); // skip comment lines
					} else {
						// e.g.:MRConFacLocP_50_100_50_100_50_100_F20_pe.1_32_C100_pe.1_391
						String instanceName = s;
						// max computational time (in sec)
						double maxTime = in.nextDouble();
						// # of iterations (internal loop)
						int nIter = in.nextInt();
						// statistical distribution for candidate selection
						String distribCandidate = in.next();
						// distribution parameter
						double firstParamCandidate = in.nextDouble();
						// distribution parameter
						//double secondParamCandidate = in.nextDouble();
                                                //double thirdParamCandidate = in.nextDouble();
                                                //double fourthParamCandidate = in.nextDouble();
						int seed = in.nextInt(); // seed for the RNG
                                                double pHubRemove = in.nextDouble();
						list.add(new Test(instanceName, instancePath, maxTime,
								nIter, distribCandidate, 
                                                        firstParamCandidate, seed, pHubRemove));
					}
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return list;
	}

	private static String ExtractInstancePath(String testsFile) {
		String subPath = "";

		// Extract directory structure from tests2run file:
		// test2run_subdir_subdir_....txt
		String[] temp = testsFile.split("_");
		Boolean first = true;
		for (String subDir : temp) {
			if (first) {
				first = false;
			} else {
				subPath += subDir + File.separator;
			}
		}
		return subPath;
	}
}
