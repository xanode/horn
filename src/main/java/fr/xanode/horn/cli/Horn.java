package fr.xanode.horn.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "horn",
        subcommands = {
                Check.class,
                Daemon.class,
                Flow.class,
                HelpCommand.class,
        },
        mixinStandardHelpOptions = true,
        version = "Horn 0.0",
        description = "Notify publicly disclosed cybersecurity vulnerabilities."
)
public class Horn implements Callable<Integer> {

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Print help.")
    boolean helpRequested;

    @Option(names = {"-v", "--version"}, versionHelp = true, description = "Print version.")
    boolean versionRequested;

    @Override
    public Integer call() {
        // TODO : do some stuff
        return 0;
    }

    public static void main(String... args) {
        System.exit(new CommandLine(new Horn()).execute(args));
    }
}
