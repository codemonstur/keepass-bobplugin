import org.linguafranca.pwdb.Database;
import org.linguafranca.pwdb.kdbx.KdbxCreds;
import org.linguafranca.pwdb.kdbx.simple.SimpleDatabase;

import java.io.IOException;
import java.io.InputStream;

public class TestLoading {

    public static void main(final String... args) throws Exception {
        final var credentials = new KdbxCreds("123456".getBytes());
        try (final var inputStream = TestLoading.class.getResourceAsStream("local.kdbx")) {
            final var database = SimpleDatabase.load(credentials, inputStream);
            System.out.println(database);
        }
    }

}
