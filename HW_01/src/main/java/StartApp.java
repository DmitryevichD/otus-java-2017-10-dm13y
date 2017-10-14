import org.apache.commons.cli.*;

public class StartApp {
    private final static Options options = new Options();

    static {
        Option arg = Option.builder("a")
                .longOpt("arg")
                .hasArg()
                .argName("any string")
                .desc("print this string")
                .type(String.class)
                .build();

        Option help = Option.builder("h")
                .longOpt("help")
                .hasArg(false)
                .desc("print help message")
                .build();

        options.addOption(arg);
        options.addOption(help);
    }

    private static void printHelp(){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("HW_01 [OPTION]", options);
    }

    private static void showMsg(String msg){
        System.out.println(msg);
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            printHelp();
        } else {
            final CommandLine cmd = new DefaultParser().parse(options, args);
            if (cmd.hasOption("a")) {
                showMsg(cmd.getOptionValue("a"));
            } else {
                printHelp();
            }
        }
    }

}
