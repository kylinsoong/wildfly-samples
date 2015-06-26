import org.jboss.aesh.console.AeshConsole;
import org.jboss.aesh.console.AeshConsoleBuilder;
import org.jboss.aesh.console.Prompt;
import org.jboss.aesh.console.command.registry.AeshCommandRegistryBuilder;
import org.jboss.aesh.console.command.registry.CommandRegistry;
import org.jboss.aesh.console.settings.SettingsBuilder;
import org.jboss.aesh.extensions.cat.Cat;
import org.jboss.aesh.extensions.cd.Cd;
import org.jboss.aesh.extensions.clear.Clear;
import org.jboss.aesh.extensions.echo.Echo;
import org.jboss.aesh.extensions.exit.Exit;
import org.jboss.aesh.extensions.grep.Grep;
import org.jboss.aesh.extensions.less.aesh.Less;
import org.jboss.aesh.extensions.ls.Ls;
import org.jboss.aesh.extensions.matrix.Matrix;
import org.jboss.aesh.extensions.mkdir.Mkdir;
import org.jboss.aesh.extensions.more.aesh.More;
import org.jboss.aesh.extensions.mv.Mv;
import org.jboss.aesh.extensions.pushdpopd.Popd;
import org.jboss.aesh.extensions.pushdpopd.Pushd;
import org.jboss.aesh.extensions.pwd.Pwd;
import org.jboss.aesh.extensions.rm.Rm;
import org.jboss.aesh.extensions.touch.Touch;

/**
 * 
 *  mvn clean install dependency:copy-dependencies
 *  
 *  java -cp target/dependency/*:target/example.jar ExtensionExample
 * 
 * @author kylin
 *
 */
public class ExtensionExample {

	public static void main(String[] args) {

		SettingsBuilder settingsBuilder = new SettingsBuilder();
        settingsBuilder.readInputrc(false);
        settingsBuilder.logging(true);
        
        CommandRegistry registry = new AeshCommandRegistryBuilder()
        		.command(Exit.class)
        		.command(Ls.class)
        		.command(Cd.class)
        		.command(Pwd.class)
        		.command(Mkdir.class)
        		.command(Cat.class)
        		.command(Less.class)
        		.command(Clear.class)
        		.command(Echo.class)
        		.command(Grep.class)
        		.command(Matrix.class)
        		.command(More.class)
        		.command(Mv.class)
        		.command(Popd.class)
        		.command(Pushd.class)
        		.command(Rm.class)
        		.command(Touch.class)
        		.create();
        
        AeshConsole aeshConsole = new AeshConsoleBuilder()
        		.commandRegistry(registry)
        		.settings(settingsBuilder.create())
        		.prompt(new Prompt("[aesh@extensions]$ "))
        		.create();
        
        aeshConsole.start();
	}

}
