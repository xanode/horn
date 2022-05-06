package fr.xanode.horn.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "flow",
        description = "Manage information flows."
)
public class Flow implements Callable<Integer> {
    @Option(names = {"list", "-l", "--list"}, description = "List every flow Horn is listening to.")
    boolean list;

    @Override
    public Integer call() {
        System.out.println("Only RSS flow are supported for now.");
        System.out.println(" -> CERT-FR : https://www.cert.ssi.gouv.fr/feed/");
        // TODO: Use proper logging system
        // TODO: Are we in daemon mode? What flow are we listening too/are in the properties file? State of flows?
        return 0;
    }
}
