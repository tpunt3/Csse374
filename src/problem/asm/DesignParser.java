package problem.asm;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.model.detectors.AdapterDetector;
import problem.model.detectors.CompositeDetector;
import problem.model.detectors.DecoratorDetector;
import problem.model.detectors.IPatternDetector;
import problem.model.detectors.SingletonDetector;
import problem.model.visitor.ITraverser;
import problem.models.api.IModel;
import problem.models.api.ISubMethod;
import problem.models.impl.Model;
import problem.models.impl.ModelGVOutputStream;
import problem.models.impl.ModelSDOutputStream;
import problem.models.impl.SubMethod;

public class DesignParser extends SwingWorker<Void, Void> {

	public static String[] CLASSES = {

			 "problem.asm.ClassDeclarationVisitor",
			 "problem.asm.ClassFieldVisitor",
			 "problem.asm.ClassMethodVisitor",
			 "problem.asm.DesignParser",
			 "problem.asm.IClazzGetter",
			 "problem.asm.MyMethodVisitor",
			 "problem.asm.DocType",
			 "problem.model.decorators.AdapteeDecorator",
			 "problem.model.decorators.AdapterDecorator",
			 "problem.model.decorators.ClassDecorator",
			 "problem.model.decorators.ComponentDecorator",
			 "problem.model.decorators.CompositeComponentDecorator",
			 "problem.model.decorators.DecoratorDecorator",
			 "problem.model.decorators.SingletonDecorator",
			 "problem.model.decorators.TargetDecorator",
			 "problem.model.decorators.LeafDecorator",
			 "problem.model.decorators.CompositeDecorator",
			 "problem.model.detectors.AdapterDetector",
			 "problem.model.detectors.DecoratorDetector",
			 "problem.model.detectors.IPatternDetector",
			 "problem.model.detectors.SingletonDetector",
			 "problem.model.detectors.CompositeDetector",
			 "problem.model.patternvisitor.IPatternVisitMethod",
			 "problem.model.patternvisitor.IPatternVisitor",
			 "problem.model.patternvisitor.ModelPatternVisitor",
			 "problem.model.patternvisitor.PatternVisitor",
			 "problem.model.visitor.ITraverser",
			 "problem.model.visitor.IVisitor",
			 "problem.model.visitor.Visitor",
			 "problem.model.visitor.LookupKey",
			 "problem.model.visitor.VisitType",
			 "problem.model.visitor.IVisitMethod",
			 "problem.models.api.IClass",
			 "problem.models.api.IField",
			 "problem.models.api.IMethod",
			 "problem.models.api.IModel",
			 "problem.models.api.IRelation",
			 "problem.models.api.ISubMethod",
			 "problem.models.api.RelationType",
			 "problem.models.impl.Class",
			 "problem.models.impl.Field",
			 "problem.models.impl.Method",
			 "problem.models.impl.Model",
			 "problem.models.impl.ModelGVOutputStream",
			 "problem.models.impl.ModelSDOutputStream",
			 "problem.models.impl.Relation",
			 "problem.models.impl.SubMethod",
			 "problem.gui.ConfigFrame",
			 "problem.gui.ImageProxy",
			 "problem.gui.MainGui",
			 "problem.gui.NewConfigFrame",
			 "problem.gui.OurConfig",
			 "problem.gui.ResultsGui",
			 "problem.gui.UMLLAMA"

			// "java.util.Collections",

			// "problem.AppLauncherApplication",
			// "problem.ApplicationLauncher",
			// "problem.DataFileRunner",
			// "problem.DirectoryChangeLogger",
			// "problem.DirectoryEvent",
			// "problem.DirectoryMonitorService",
			// "problem.ExecutableFileRunner",
			// "problem.IApplicationLauncher",
			// "problem.IDirectoryListener",
			// "problem.IDirectoryMonitorService",
			// "problem.ProcessRunner"

			// "headfirst.singleton.chocolate.ChocolateBoiler",
			// "headfirst.singleton.chocolate.ChocolateController"

			// "headfirst.decorator.starbuzz.Beverage",
			// "headfirst.decorator.starbuzz.CondimentDecorator",
			// "headfirst.decorator.starbuzz.DarkRoast",
			// "headfirst.decorator.starbuzz.Decaf",
			// "headfirst.decorator.starbuzz.Espresso",
			// "headfirst.decorator.starbuzz.HouseBlend",
			// "headfirst.decorator.starbuzz.Milk",
			// "headfirst.decorator.starbuzz.Mocha",
			// "headfirst.decorator.starbuzz.Soy",
			// "headfirst.decorator.starbuzz.StarbuzzCoffee",
			// "headfirst.decorator.starbuzz.Whip",

			// "problem.client.App",
			// "problem.client.IteratorToEnumerationAdapter",
			// "problem.lib.LinearTransformer",
			// "java.util.Iterator",
			// "java.util.Enumeration"

			// "java.lang.Runtime",
			// "java.awt.Desktop",
			// "java.util.Calendar",
			// "java.io.FilterInputStream"

			// "java.io.InputStreamReader",
			// "java.io.Reader",
			// "java.io.InputStream",
			// "java.io.OutputStreamWriter"

			// "headfirst.composite.menu.Menu",
			// "headfirst.composite.menu.MenuComponent",
			// "headfirst.composite.menu.MenuItem",
			// "headfirst.composite.menu.MenuTestDrive",
			// "headfirst.composite.menu.Waitress"

//			"problem.sprites.AbstractSprite", "problem.sprites.CircleSprite",
//			"problem.sprites.CompositeCompositeSprite", "problem.sprites.CompositeIterator",
//			"problem.sprites.CompositeSprite", "problem.sprites.ISprite", "problem.sprites.NullIterator",
//			"problem.sprites.PyramidSprite", "problem.sprites.RectangleSprite", "problem.sprites.SpriteFactory",
//			"problem.sprites.StackSprite",

//			 "problem.test.patternClasses.AdapteeClass",
//			 "problem.test.patternClasses.AdapterClass",
//			 "problem.test.patternClasses.FalseAdapterClass",
//			 "problem.test.patternClasses.TargetInterface"

			// "problem.test.patternClasses.Leaf",
			// "problem.test.patternClasses.MyCollectionComposite",
			// "problem.test.patternClasses.CompositeComponent"
	};

	private String pathToDot;
	private String pathToSDEdit;
	private String outputDir = "input_output/";
	private ArrayList<String> phases;
	private volatile int completedPhases;
	private String currentPhase = "";
	private ArrayList<String> patternsToDetect;
	private JProgressBar jpb;
	private int adapterDelegation;
	private int decoratorDelegation;
	private boolean getInstance;

	/**
	 * Reads in a list of Java Classes and reverse engineers their design.
	 *
	 * @param args:
	 *            the names of the classes, separated by spaces. For example:
	 *            java DesignParser java.lang.String
	 *            edu.rosehulman.csse374.ClassFieldVisitor java.lang.Math
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		 //DesignParser parser = new
		// DesignParser("\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\"",
		 //"\"C:\\Users\\punttj\\Desktop\\csse374\\finalProject\\sdedit-4.2-beta1.exe\"");
		DesignParser parser = new DesignParser("\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\"",
				"\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\sdedit-4.2-beta1.exe\"");
		// parser.generateDocuments(DocType.uml,
		parser.setDefaults();

		String methodSig = "problem.asm.DesignParser,DesignParser,generateSD,String; Model; ISubMethod; int";
		int depth = 2;
		DocType type = DocType.uml;

		ISubMethod sm = null;

		sm = parser.setUpSD(methodSig);

		Model model = parser.visitClasses(CLASSES);
		if (type.equals(DocType.uml) || type.equals(DocType.both)) {
			parser.generateUML(parser.getPathToDot(), model);
		}
		if (type.equals(DocType.sd) || type.equals(DocType.both)) {
			parser.generateSD(parser.getPathToSD(), model, sm, depth);
		}
	}

	public DesignParser(String pathToDot, String pathToSDEdit) {
		this.pathToDot = pathToDot;
		this.pathToSDEdit = pathToSDEdit;
		this.phases = new ArrayList<String>();
		this.patternsToDetect = new ArrayList<String>();
		this.currentPhase = "Preparing to visit classes...";
		this.completedPhases = 0;
		this.adapterDelegation = 1;
		this.getInstance = true;
	}

	public DesignParser(String dotPath, String string, JProgressBar jpb) {
		this.pathToDot = dotPath;
		this.pathToSDEdit = string;
		this.phases = new ArrayList<String>();
		this.patternsToDetect = new ArrayList<String>();
		this.currentPhase = "Preparing to visit classes...";
		this.completedPhases = 0;
		this.adapterDelegation = 1;
		this.jpb = jpb;
	}

	public ISubMethod setUpSD(String methodSig) {
		DocType type = DocType.sd;

		ISubMethod sm = null;
		String arguments;

		// sequence diagrams
		if (type.equals(DocType.sd) || type.equals(DocType.both)) {
			String[] sigSplit = methodSig.split(",");
			String qualifiedClassName = sigSplit[0];
			String clazzName = sigSplit[1];
			String methodName = sigSplit[2];
			if (sigSplit.length == 4) {
				arguments = sigSplit[3];
			} else {
				arguments = "";
			}

			sm = new SubMethod(qualifiedClassName, clazzName, methodName, arguments, "");
			return sm;
		}
		return null;

	}

	public Model visitClasses(String[] classes) throws IOException {

		Model model = Model.getInstance();

		for (String className : classes) {
			if (!className.contains("[")) {
				this.currentPhase = "Visiting " + className + "...";

				// ASM's ClassReader does the heavy lifting of parsing the
				// compiled
				// Java class
				ClassReader reader = new ClassReader(className);

				// make class declaration visitor to get superclass and
				// interfaces
				ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, model);

				// DECORATE declaration visitor with field visitor
				ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, model);

				// DECORATE field visitor with method visitor
				ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, model);

				reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
				this.completedPhases++;
			}
			updateProgress();
		}

		return model;
	}

	public void generateUML(String pathToDot, Model model) throws IOException {

		detectSingletonPattern(model);

		detectDecoratorPattern(model);

		detectAdapterPattern(model);

		detectCompositePattern(model);

		generateDot(model);

	}

	public void detectSingletonPattern(IModel model) {
		IPatternDetector singletonDetector = new SingletonDetector(model, getInstance);
		singletonDetector.detectPatterns();
		this.currentPhase = "Detecting Singleton Pattern";
		updateProgress();
	}

	public void detectDecoratorPattern(IModel model) {
		IPatternDetector decoratorDetector = new DecoratorDetector(model);
		decoratorDetector.detectPatterns();
		this.currentPhase = "Detecting Decorator Pattern";
		updateProgress();
	}

	public void detectAdapterPattern(IModel model) {
		IPatternDetector adapterDetector = new AdapterDetector(model,this.adapterDelegation);
		adapterDetector.detectPatterns();
		this.currentPhase = "Detecting Adapter Pattern";
		updateProgress();
	}

	public void detectCompositePattern(IModel model) {
		IPatternDetector compositeDetector = new CompositeDetector(model);
		compositeDetector.detectPatterns();
		this.currentPhase = "Detecting Composite Pattern";
		updateProgress();
	}

	public void generateDot(IModel model) throws IOException {
		this.currentPhase = "Generating Dot File...";
		ModelGVOutputStream gv = new ModelGVOutputStream(new FileOutputStream(this.outputDir + "model.gv"));
		gv.write(model);
		gv.close();

		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",
				pathToDot + " -Tpng input_output/model.gv > " + this.outputDir + "graph1.png");
		builder.redirectErrorStream(true);
		Process p = builder.start();
		String line;
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while (true) {
			line = r.readLine();
			if (line == null) {
				break;
			}
			System.out.println(line);
		}

		
		System.out.println("done");
		this.done();
	}

	public void generateSD(String pathToSDEdit, Model model, ISubMethod sm, int depth) throws IOException {
		String[] classes = null;
		for (int i = 0; i < depth; i++) {
			classes = model.findNewClasses(sm, depth);
			Set<String> mySet = new HashSet<String>(Arrays.asList(classes));

			try {
				String[] newArray = mySet.toArray(new String[mySet.size()]);
				visitClasses(newArray);

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("IO Exception");
			}
		}

		OutputStream out = new FileOutputStream("input_output/sequence.sd");
		ModelSDOutputStream sd = new ModelSDOutputStream(new FileOutputStream("input_output/sequence.sd"));
		model.clearSD();

		model.acceptSequence(sm, depth);
		sd.write(model);
		ITraverser traverser = (ITraverser) model;

		// model.writeFile(sdWriter);
		out.close();

		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",
				pathToSDEdit + " -o ./input_output/SD.png -t png ./input_output/sequence.sd");
		builder.redirectErrorStream(true);
		Process p = builder.start();
		String line;
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while (true) {
			line = r.readLine();
			if (line == null) {
				break;
			}
			System.out.println(line);
		}
	}

	public void regenerateModel() throws IOException {
		this.completedPhases = 0;

		Model model = Model.getInstance();

		generateDot(model);
	}

	private void setDefaults() {
		setOutputDir("input_output/");
		phases.add("visit");
		phases.add("decorator");
		phases.add("composite");
		phases.add("singleton");
		phases.add("adapter");
		phases.add("dot");
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public void setPhases(ArrayList<String> phases) {
		this.phases = phases;
		for (String s : phases) {
			s = s.toLowerCase();
		}
	}

	public void setClasses(String[] classes) {
		DesignParser.CLASSES = classes;
	}

	public ArrayList<String> getPhases() {
		return this.phases;
	}

	public String getCurrentPhase() {
		return currentPhase;
	}

	public int getMyProgress() {
		return completedPhases;
	}

	public String getPathToDot() {
		return pathToDot;
	}

	public String getPathToSD() {
		return this.pathToSDEdit;
	}

	public String getOutputDir() {
		return outputDir;
	}
	
	public void setAdapterDelegation(int delegation){
		this.adapterDelegation = delegation;
	}
	
	public void setDecoratorDelegation(int delegation){
		this.decoratorDelegation = delegation;
	}
	
	public void setSingletonGetInstance(boolean require){
		this.getInstance = require;
	}

	public void addPattern(String p) {
		if (!this.patternsToDetect.contains(p)) {
			this.patternsToDetect.add(p);
		}
	}

	public void removePattern(String p) {
		if (this.patternsToDetect.contains(p)) {
			this.patternsToDetect.remove(p);
		}
	}

	@Override
	protected Void doInBackground() throws Exception {

		IModel m = this.visitClasses(CLASSES);
		if (this.phases.contains("singleton")) {
			detectSingletonPattern(m);
		}
		if (this.phases.contains("adapter")) {
			detectAdapterPattern(m);
		}
		if (this.phases.contains("composite")) {
			detectCompositePattern(m);
		}
		if (this.phases.contains("decorator")) {
			detectDecoratorPattern(m);
		}
		
		done();

		return null;
	}

	@Override
	public void done() {
		if (jpb != null) {
			jpb.setValue(jpb.getMaximum());
			jpb.setString("finished!");
		}
		firePropertyChange("done", 0, 1);
	}
	
	private void updateProgress(){
		System.out.println(completedPhases);
		setProgress(completedPhases);
		firePropertyChange("progress", completedPhases, completedPhases);
		if (jpb != null) {
			jpb.setValue(completedPhases);
			jpb.setString(this.currentPhase);
		}
	}

}