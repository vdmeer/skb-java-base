package de.vandermeer.skb.base.shell;

import org.junit.Test;

public class Test_Shell {

	@Test
	public void testMe(){

		SkbShell skbsh = SkbShellFactory.newShell();
		skbsh.addCommandInterpreter(new ExitInterpreter());
		skbsh.addCommandInterpreter(new HelpInterpreter());
		skbsh.addCommandInterpreter(new WaitInterpreter());
		skbsh.addCommandInterpreter(new ScRunInterpreter());
//		skbsh.runShell();
System.err.println("#");
//		skbsh.runShell();
System.err.println("##");
//		Object notify = new Object();
//		Thread th = skbsh.start(notify);
//		Skb_ThreadUtils.sleep(1);
//		synchronized(notify){
//			try {
//				while(skbsh.isRunning()){
//					notify.wait(2000);
//					System.err.println("@");
//					skbsh.stop();
//					break;
//				}
//			}
//			catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
System.err.println("###");
System.err.println(skbsh.isRunning());

	}

}
