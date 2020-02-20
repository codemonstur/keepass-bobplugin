package keepass;

import jcli.annotations.CliOption;

public class CliKeepass {

    @CliOption(name = 'f', longName = "file", isMandatory = true)
    public String file;
    @CliOption(name = 'p', longName = "password")
    public String password;

    @CliOption(name = 'u', longName = "uppercase")
    public boolean uppercase;

}
