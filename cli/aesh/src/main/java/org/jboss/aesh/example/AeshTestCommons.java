package org.jboss.aesh.example;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermissions;

import org.jboss.aesh.console.AeshConsole;
import org.jboss.aesh.console.command.registry.CommandRegistry;
import org.jboss.aesh.console.settings.Settings;

public class AeshTestCommons {
	
	private PipedOutputStream pos;
    private PipedInputStream pis;
    private ByteArrayOutputStream stream;
    private Settings settings;
    private AeshConsole aeshConsole;
    private CommandRegistry registry;
    
    private FileAttribute fileAttribute = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxrwx"));

    public AeshTestCommons() {
    	pos = new PipedOutputStream();
    	try {
            pis = new PipedInputStream(pos);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    	stream = new ByteArrayOutputStream();
    }

}
