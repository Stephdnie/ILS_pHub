package src.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import src.algorithm.ILS;
import src.structures.Solution;
import src.io.GenOutputs;



public final class AlgorithmTester 
{
    private final static Logger LOGGER = Logger.getLogger("");
    private final static String INPUT_FOLDER = "inputs";
    private final static String OUTPUT_FOLDER = "outputs";
    private final static String TEST_FOLDER = "tests";
    private final static String FILE_NAME_EXT = ".txt";
    private final static String[] TEST_TO_RUN_LIST = new String[]{

            //"testhlp102_Creadas"
            //"testhlp103_Creadas"
            "testhlp104_Creadas"
            //"testhlp105_Creadas"
            //"testhlp202_Creadas"
            //"testhlp203_Creadas"
            //"testhlp204_Creadas"
            //"testhlp205_Creadas"
            //"testhlp252_Creadas"
            //"testhlp253_Creadas"
            //"testhlp254_Creadas"
            //"testhlp255_Creadas"
            //"testhlp402_Creadas"
            //"testhlp403_Creadas"
            //"testhlp404_Creadas"
            //"testhlp405_Creadas"
            //"testhlp502_Creadas"
            //"testhlp503_Creadas"
            //"testhlp504_Creadas"
            //"testhlp505_Creadas"
            //"testhlp1005_Creadas"
            //"testhlp10010_Creadas"
            //"testhlp10015_Creadas"
            //"testhlp10020_Creadas"
            //"testhlp2003_Creadas"
            //"testhlp2005_Creadas"
            //"testhlp20010_Creadas"
            //"testhlp20015_Creadas"
            //"testhlp20020_Creadas" 
    };

    public static void main(final String[] args) throws IOException 
    {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        final long programStart = ElapsedTime.systemTime();
        LOGGER.setLevel(Level.ALL);

        /* 1. GET THE LIST OF TESTS TO RUN */
        final ArrayList<Test> testsList = TestsManager.getTestsList(TEST_FOLDER + 
                File.separator, TEST_TO_RUN_LIST, FILE_NAME_EXT);
        System.out.println("GG - Heuristic");
        /* 2. FOR EACH TEST IN THE LIST... */
        
        FWriterCheckTests.open(System.currentTimeMillis() + "_");
		
        for( int k = 0; k < testsList.size(); k++ ) 
        {
            Test aTest = testsList.get(k);
            LOGGER.log(Level.INFO, "[{0}] Test for instance {1} prepared ({2}/{3})", 
                    new Object[]{ElapsedTime.calcElapsed(programStart, 
                    ElapsedTime.systemTime()), aTest.getInstanceName(), k + 1, 
                    testsList.size()});


            // 2. GET THE INSTANCE INPUTS
            LinkedList<GenOutputs> allTests = new LinkedList<GenOutputs>();            
            Inputs someInputs = InputsManager.readInputs(INPUT_FOLDER + 
                    File.separator + aTest.getInstanceFullPath());
            InputsManager.createConnections(someInputs);
            Random rng = new Random(aTest.getSeed());
            Outputs someOutputs = ILS.solver(aTest, someInputs, rng); // create the outputs
//            out.printGMLOutpute(someInputs,aTest);
            allTests.add(new GenOutputs(aTest.getInstanceName(),someOutputs.getBestSol().getCosts()));
            

        }
        FWriterCheckTests.finish();
        /* 3. END OF PROGRAM */
        LOGGER.log(Level.INFO, "[{0}] All tests ended correctly, saving data", 
                ElapsedTime.calcElapsed(programStart, ElapsedTime.systemTime()));
        LOGGER.log(Level.WARNING, "[{0}] Full program execution ended correctly "
                + "in {1}", new Object[]{ElapsedTime.calcElapsed(programStart, 
                ElapsedTime.systemTime()), ElapsedTime.calcElapsedHMS(programStart, 
                ElapsedTime.systemTime())});        
    }
}
