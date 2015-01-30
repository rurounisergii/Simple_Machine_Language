package smlTests;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddInstructionTest.class, BnzInstructionTest.class, DivideInstructionTest.class
	, IntegratedInstructionTest.class, LinInstructionTest.class, MultiplyInstructionTest.class
	, SubtractInstructionTest.class})
public class AllTests {

} 


