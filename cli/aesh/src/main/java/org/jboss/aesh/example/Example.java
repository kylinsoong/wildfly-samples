package org.jboss.aesh.example;

import java.io.IOException;
import java.util.List;

import org.jboss.aesh.cl.Arguments;
import org.jboss.aesh.cl.CommandDefinition;
import org.jboss.aesh.cl.Option;
import org.jboss.aesh.cl.builder.CommandBuilder;
import org.jboss.aesh.cl.internal.ProcessedOption;
import org.jboss.aesh.cl.internal.ProcessedOptionBuilder;
import org.jboss.aesh.cl.parser.OptionParserException;
import org.jboss.aesh.cl.renderer.OptionRenderer;
import org.jboss.aesh.console.AeshConsole;
import org.jboss.aesh.console.AeshConsoleBuilder;
import org.jboss.aesh.console.Prompt;
import org.jboss.aesh.console.command.Command;
import org.jboss.aesh.console.command.CommandResult;
import org.jboss.aesh.console.command.invocation.CommandInvocation;
import org.jboss.aesh.console.command.registry.AeshCommandRegistryBuilder;
import org.jboss.aesh.console.command.registry.CommandRegistry;
import org.jboss.aesh.console.settings.Settings;
import org.jboss.aesh.console.settings.SettingsBuilder;
import org.jboss.aesh.io.Resource;
import org.jboss.aesh.terminal.CharacterType;
import org.jboss.aesh.terminal.Color;
import org.jboss.aesh.terminal.TerminalColor;
import org.jboss.aesh.terminal.TerminalString;
import org.jboss.aesh.terminal.TerminalTextStyle;


/**
 * 
 *  Build
 * 		mvn clean install dependency:copy-dependencies
 * 
 *  Run
 *  	java -cp target/dependency/*:target/jboss-aesh-quickstart.jar org.jboss.aesh.example.Example
 * 
 * @author kylin
 *
 */
public class Example {

	public static void main(String[] args) throws IOException, OptionParserException {
		
		ProcessedOption bar = new ProcessedOptionBuilder()
				.name("bar")
				.addDefaultValue("10")
				.addDefaultValue("20")
				.fieldName("bar")
				.type(String.class)
				.create();
		
		ProcessedOption zoo = new ProcessedOptionBuilder()
				.name("zoo")
				.addDefaultValue("abcd")
				.fieldName("zoo")
				.type(String.class)
				.create();
				
		
		CommandBuilder fooCommand = new CommandBuilder()
				.name("foo")
				.description("fooing")
				.addOption(bar)
				.addOption(zoo)
				.command(FooCommand.class);
		
		Settings settings = new SettingsBuilder().logging(true)
				.enableMan(true)
				.readInputrc(true)
				.create();
		
		CommandRegistry registry = new AeshCommandRegistryBuilder()
				.command(ExitCommand.class)
				.command(fooCommand.create())
				.command(LsCommand.class)
				.create();
		
		AeshConsole aeshConsole = new AeshConsoleBuilder().settings(settings)
				.commandRegistry(registry)
				.settings(settings)
				.prompt(new Prompt(new TerminalString("[aesh@rules]$ ", new TerminalColor(Color.GREEN, Color.DEFAULT, Color.Intensity.BRIGHT))))
				.create();
		
		aeshConsole.start();
		
		
	}
	
	@CommandDefinition(name="exit", description = "exit the program")
	public static class ExitCommand implements Command {

		@Override
		public CommandResult execute(CommandInvocation commandInvocation) throws IOException, InterruptedException {
			commandInvocation.stop();
			return CommandResult.SUCCESS;
		}
		
	}
	
	public static class FooCommand implements Command {

        private String bar;

        private String zoo;

        @Override
        public CommandResult execute(CommandInvocation commandInvocation) throws IOException, InterruptedException {
        	
           commandInvocation.getShell().out().println("bar: " + bar);
           commandInvocation.getShell().out().println("zoo: " + zoo);
           
            return CommandResult.SUCCESS;
        }
    }
	
	@CommandDefinition(name="ls", description = "[OPTION]... [FILE]...")
    public static class LsCommand implements Command {

        @Option(shortName = 'f', hasValue = false, description = "set foo to true/false")
        private Boolean foo;

        @Option(hasValue = false, description = "set the bar", renderer = BlueBoldRenderer.class)
        private boolean bar;
//
//        @Option(shortName = 'l', completer = LessCompleter.class, defaultValue = {"MORE"}, argument = "SIZE")
//        private String less;
//
//        @OptionList(defaultValue = "/tmp", description = "file location", valueSeparator = ':',
//                validator = DirectoryValidator.class,
//                activator = BarActivator.class)
//        List<File> files;
//
        @Option(hasValue = false, description = "display this help and exit")
        private boolean help;

        @Arguments(description = "files or directories thats listed")
        private List<Resource> arguments;

        @Override
        public CommandResult execute(CommandInvocation commandInvocation) throws IOException, InterruptedException {
            if(help) {
                commandInvocation.getShell().out().println(commandInvocation.getHelpInfo("ls"));
            }
            else {
                if(foo)
                    commandInvocation.getShell().out().println("you set foo to: " + foo);
                if(bar)
                    commandInvocation.getShell().out().println("you set bar to: " + bar);
//                if(less != null)
//                    commandInvocation.getShell().out().println("you set less to: " + less);
//                if(files != null)
//                    commandInvocation.getShell().out().println("you set file to: " + files);

                if(arguments != null) {
                    for(Resource f : arguments)
                        commandInvocation.getShell().out().println(f.toString());
                }
            }
            return CommandResult.SUCCESS;
        }
    }
	
	public static class BlueBoldRenderer implements OptionRenderer {

        private static final TerminalTextStyle style = new TerminalTextStyle(CharacterType.UNDERLINE);
        private static final TerminalColor color = new TerminalColor(42, Color.DEFAULT);

        @Override
        public TerminalColor getColor() {
            return color;
        }

        @Override
        public TerminalTextStyle getTextType() {
            return style;
        }
    }

}
