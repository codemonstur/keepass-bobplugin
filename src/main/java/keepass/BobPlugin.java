package keepass;

import bobthebuildtool.pojos.buildfile.Project;
import bobthebuildtool.pojos.error.InvalidInput;
import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.KeePassFile;
import jcli.errors.InvalidCommandLine;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static bobthebuildtool.services.Functions.isNullOrEmpty;
import static java.nio.file.Files.isRegularFile;
import static jcli.CliParserBuilder.newCliParser;

public enum BobPlugin {;

    public static void installPlugin(final Project project) {
        project.addCommand("keepass", "Loads variables from a keepass archive", BobPlugin::loadKeepassArchive);
    }

    private static int loadKeepassArchive(final Project project, final Map<String, String> env, final String[] args)
            throws InvalidCommandLine, InvalidInput {
        final CliKeepass arguments = newCliParser(CliKeepass::new).parse(args);

        final Path archive = Paths.get(arguments.file).normalize().toAbsolutePath();
        if (!isRegularFile(archive)) throw new InvalidInput("Keepass archive " + archive + " does not exist.");

        final String password = isNullOrEmpty(arguments.password) ? env.get("KEEPASS_MASTER_PASSWORD") : arguments.password;
        if (isNullOrEmpty(password)) throw new InvalidInput("Could not find a password to use for the keepass archive");

        final KeePassFile database = KeePassDatabase
            .getInstance(archive.toFile())
            .openDatabase(password);

        for (final var entry : database.getEntries()) {
            final String name = arguments.uppercase ? entry.getUsername().toUpperCase() : entry.getUsername();
            env.put(name, entry.getPassword());
        }

        return 0;
    }

}
