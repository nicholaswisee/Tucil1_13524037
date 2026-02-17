package queens;

import queens.controller.CLIController;
import queens.view.QueensGUI;

public class Main {

  public static void main(String[] args) {
    // Check for GUI mode
    if (args.length == 0 || (args.length > 0 && args[0].equals("--gui"))) {
      System.out.println("Launching GUI mode...");
      QueensGUI.main(args);
      return;
    }

    // CLI mode - delegate to CLIController
    CLIController cliController = new CLIController();
    cliController.run(args);
  }
}
