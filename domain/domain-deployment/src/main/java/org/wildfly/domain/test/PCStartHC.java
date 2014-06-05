package org.wildfly.domain.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


public class PCStartHC {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		 List<String> command = new ArrayList<String>();
		 command.add("/usr/java/jdk1.7.0_21/bin/java");
		 command.add("-server");
		 command.add("-Xms64m");
		 command.add("-Xmx128m");
		 command.add("-Xrs");
		 command.add("-jar");
		 command.add("/home/kylin/work/project/wildfly-samples/domain/domain-deployment-inter/target/exterProcess.jar");
		 ProcessBuilder builder = new ProcessBuilder(command);
		 builder.directory(new File("/home/kylin/work/project/wildfly-samples/domain"));
		 Process process = builder.start();
		 final InputStream stdout = process.getInputStream();
		 Thread stdoutThread = new Thread(new ReadTask(stdout, System.out));
		 stdoutThread.start();
		 
		 int exitCode = process.waitFor();
		 System.out.println("Process Exit with exit code: " + exitCode);
	}
	
	private static class ReadTask implements Runnable {
        private final InputStream source;
        private final PrintStream target;

        public ReadTask(final InputStream source, final PrintStream target) {
            this.source = source;
            this.target = target;
        }

        public void run() {
            final InputStream source = this.source;
            final String processName = "Host Controller";
            try {
                final BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(source)));
                final OutputStreamWriter writer = new OutputStreamWriter(target);
                String s;
                String prevEscape = "";
                while ((s = reader.readLine()) != null) {
                    // Has ANSI?
                    int i = s.lastIndexOf('\033');
                    int j = i != -1 ? s.indexOf('m', i) : 0;

                    synchronized (target) {
                        writer.write('[');
                        writer.write(processName);
                        writer.write("] ");
                        writer.write(prevEscape);
                        writer.write(s);

                        // Reset if there was ANSI
                        if (j != 0 || prevEscape != "") {
                            writer.write("\033[0m");
                        }
                        writer.write('\n');
                        writer.flush();
                    }


                    // Remember escape code for the next line
                    if (j != 0) {
                        String escape = s.substring(i, j + 1);
                        if (!"\033[0m".equals(escape)) {
                            prevEscape = escape;
                        } else {
                            prevEscape = "";
                        }
                    }
                }
                source.close();
            } catch (IOException e) {
            	e.printStackTrace();
            } finally {
             
            }
        }
    }

}
