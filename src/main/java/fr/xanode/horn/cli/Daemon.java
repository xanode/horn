package fr.xanode.horn.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "daemon",
        description = "Manage Horn in daemon mode."
)
public class Daemon implements Runnable {
    @Option(names = "start", description = "Start daemon")
    boolean start;

    @Option(names = "stop", description = "Stop daemon")
    boolean stop;

    @Option(names = "status", description = "Status of Horn daemon")
    boolean status;

    @Override
    public void run() {
        // TODO: do some stuff
    }
}
