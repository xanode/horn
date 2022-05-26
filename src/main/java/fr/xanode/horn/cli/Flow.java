package fr.xanode.horn.cli;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "flow",
        description = "Manage information flows."
)
@Slf4j
public class Flow implements Callable<Integer> {
    @Option(names = {"list", "-l", "--list"}, description = "List every flow Horn is listening to.")
    boolean list;

    @Override
    public Integer call() {
        log.warn("Only RSS flow are supported for now.");
        log.info(" -> CERT-FR : https://www.cert.ssi.gouv.fr/feed/");
        // TODO: Are we in daemon mode? What flow are we listening too/are in the properties file? State of flows?
        return 0;
    }
}
