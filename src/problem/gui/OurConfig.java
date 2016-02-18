package problem.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class OurConfig {
	private String inputClasses;
	private String outputDir;
	private String dotPath;
	private String sdPath;
	private String phases;
	private String adapterMethodDelegation;
	private String decoratorMethodDelegation;
	private String singletonRequireGetInstance;

	public OurConfig() {
		
	}

	public void setAdapterMethodDelegation(String adapterMethodDelegation) {
		this.adapterMethodDelegation = adapterMethodDelegation;
	}

	public void setDecoratorMethodDelegation(String decoratorMethodDelegation) {
		this.decoratorMethodDelegation = decoratorMethodDelegation;
	}

	public void setSingletonRequireGetInstance(String singletonRequireGetInstance) {
		this.singletonRequireGetInstance = singletonRequireGetInstance;
	}

	public void setInputClasses(String inputClasses) {
		this.inputClasses = inputClasses;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}


	public void setDotPath(String dotPath) {
		this.dotPath = dotPath;
	}

	public void setSDPath(String sdPath) {
		this.sdPath = sdPath;
	}


	public void setPhases(String phases) {
		this.phases = phases;
	}

	public void writeProperties() {
		try{
			Properties p = new Properties();
			
			p.setProperty("Input-Classes", this.inputClasses);
			p.setProperty("Output-Directory", this.outputDir);
			p.setProperty("Dot-Path", this.dotPath);
			p.setProperty("Phases", this.phases);
			p.setProperty("Adapter-MethodDelegation", this.adapterMethodDelegation);
			p.setProperty("Decorator-MethodDelegation", this.decoratorMethodDelegation);
			p.setProperty("Singleton-RequireGetInstance", this.singletonRequireGetInstance);
			
			File file = new File("resources/config.properties");
			FileOutputStream fos = new FileOutputStream(file);
			p.store(fos, "Properties");
			
			fos.close();
		} catch (FileNotFoundException e){
			
		} catch (IOException e){
			
		}
	}	

}
