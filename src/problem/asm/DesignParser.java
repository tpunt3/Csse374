package problem.asm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.model.detectors.AdapterDetector;
import problem.model.detectors.CompositeDetector;
import problem.model.detectors.DecoratorDetector;
import problem.model.detectors.IPatternDetector;
import problem.model.detectors.SingletonDetector;
import problem.model.patternvisitor.ModelPatternVisitor;
import problem.model.visitor.ITraverser;
import problem.model.visitor.IVisitor;
import problem.models.api.ISubMethod;
import problem.models.impl.Model;
import problem.models.impl.ModelGVOutputStream;
import problem.models.impl.ModelSDOutputStream;
import problem.models.impl.SubMethod;

public class DesignParser {

	public static final String[] CLASSES = {
			// "headfirst.factory.pizzaaf.BlackOlives",
			// "headfirst.factory.pizzaaf.Cheese",
			// "headfirst.factory.pizzaaf.CheesePizza",
			// "headfirst.factory.pizzaaf.ChicagoPizzaIngredientFactory",
			// "headfirst.factory.pizzaaf.ChicagoPizzaStore",
			// "headfirst.factory.pizzaaf.ClamPizza",
			// "headfirst.factory.pizzaaf.Clams",
			// "headfirst.factory.pizzaaf.Dough",
			// "headfirst.factory.pizzaaf.Eggplant",
			// "headfirst.factory.pizzaaf.FreshClams",
			// "headfirst.factory.pizzaaf.FrozenClams",
			// "headfirst.factory.pizzaaf.Garlic",
			// "headfirst.factory.pizzaaf.MarinaraSauce",
			// "headfirst.factory.pizzaaf.MozzarellaCheese",
			// "headfirst.factory.pizzaaf.Mushroom",
			// "headfirst.factory.pizzaaf.NYPizzaIngredientFactory",
			// "headfirst.factory.pizzaaf.NYPizzaStore",
			// "headfirst.factory.pizzaaf.Onion",
			// "headfirst.factory.pizzaaf.ParmesanCheese",
			// "headfirst.factory.pizzaaf.Pepperoni",
			// "headfirst.factory.pizzaaf.PepperoniPizza",
			// "headfirst.factory.pizzaaf.Pizza",
			// "headfirst.factory.pizzaaf.PizzaIngredientFactory",
			// "headfirst.factory.pizzaaf.PizzaStore",
			// "headfirst.factory.pizzaaf.PizzaTestDrive",
			// "headfirst.factory.pizzaaf.PlumTomatoSauce",
			// "headfirst.factory.pizzaaf.RedPepper",
			// "headfirst.factory.pizzaaf.ReggianoCheese",
			// "headfirst.factory.pizzaaf.Sauce",
			// "headfirst.factory.pizzaaf.SlicedPepperoni",
			// "headfirst.factory.pizzaaf.Spinach",
			// "headfirst.factory.pizzaaf.ThickCrustDough",
			// "headfirst.factory.pizzaaf.ThinCrustDough",
			// "headfirst.factory.pizzaaf.VeggiePizza",
			// "headfirst.factory.pizzaaf.Veggies",
			// "headfirst.factory.pizzafm.ChicagoPizzaStore",
			// "headfirst.factory.pizzafm.ChicagoStyleCheesePizza",
			// "headfirst.factory.pizzafm.ChicagoStyleClamPizza",
			// "headfirst.factory.pizzafm.ChicagoStylePepperoniPizza",
			// "headfirst.factory.pizzafm.ChicagoStyleVeggiePizza",
			// "headfirst.factory.pizzafm.DependentPizzaStore",
			// "headfirst.factory.pizzafm.NYPizzaStore",
			// "headfirst.factory.pizzafm.NYStyleCheesePizza",
			// "headfirst.factory.pizzafm.NYStyleClamPizza",
			// "headfirst.factory.pizzafm.NYStylePepperoniPizza",
			// "headfirst.factory.pizzafm.NYStyleVeggiePizza",
			// "headfirst.factory.pizzafm.Pizza",
			// "headfirst.factory.pizzafm.PizzaStore",
			// "headfirst.factory.pizzafm.PizzaTestDrive",
			// "headfirst.factory.pizzas.CheesePizza",
			// "headfirst.factory.pizzas.ClamPizza",
			// "headfirst.factory.pizzas.PepperoniPizza",
			// "headfirst.factory.pizzas.Pizza",
			// "headfirst.factory.pizzas.PizzaStore",
			// "headfirst.factory.pizzas.PizzaTestDrive",
			// "headfirst.factory.pizzas.SimplePizzaFactory",
			// "headfirst.factory.pizzas.VeggiePizza",
			//
			// "problem.asm.ClassDeclarationVisitor",
			// "problem.asm.ClassFieldVisitor",
			// "problem.asm.ClassMethodVisitor",
			//"problem.asm.DesignParser",
			// "problem.asm.IClazzGetter",
			// "problem.asm.MyMethodVisitor",
			// "problem.asm.DocType",
			// "problem.model.decorators.AdapteeDecorator",
			// "problem.model.decorators.AdapterDecorator",
			// "problem.model.decorators.ClassDecorator",
			// "problem.model.decorators.ComponentDecorator",
			// "problem.model.decorators.DecoratorDecorator",
			// "problem.model.decorators.SingletonDecorator",
			// "problem.model.decorators.TargetDecorator",
			// "problem.model.detectors.AdapterDetector",
			// "problem.model.detectors.DecoratorDetector",
			// "problem.model.detectors.IPatternDetector",
			// "problem.model.detectors.SingletonDetector",
			// "problem.model.patternvisitor.IPatternVisitMethod",
			// "problem.model.patternvisitor.IPatternVisitor",
			// "problem.model.patternvisitor.ModelPatternVisitor",
			// "problem.model.patternvisitor.PatternVisitor",
			// "problem.model.visitor.ITraverser",
			// "problem.model.visitor.IVisitor",
			// "problem.model.visitor.Visitor",
			// "problem.model.visitor.LookupKey",
			// "problem.model.visitor.VisitType",
			// "problem.model.visitor.IVisitMethod",
			// "problem.models.api.IClass",
			// "problem.models.api.IField",
			// "problem.models.api.IMethod",
			// "problem.models.api.IModel",
			// "problem.models.api.IRelation",
			// "problem.models.api.ISubMethod",
			// "problem.models.api.RelationType",
			// "problem.models.impl.Class",
			// "problem.models.impl.Field",
			// "problem.models.impl.Method",
			// "problem.models.impl.Model",
			// "problem.models.impl.ModelGVOutputStream",
			// "problem.models.impl.ModelSDOutputStream",
			// "problem.models.impl.Relation",
			// "problem.models.impl.SubMethod"

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

			// "problem.sprites.AbstractSprite",
			// "problem.sprites.CircleSprite",
			// "problem.sprites.CompositeCompositeSprite",
			// "problem.sprites.CompositeIterator",
			// "problem.sprites.CompositeSprite",
			// "problem.sprites.ISprite",
			// "problem.sprites.NullIterator",
			// "problem.sprites.PyramidSprite",
			// "problem.sprites.RectangleSprite",
			// "problem.sprites.SpriteFactory",
			// "problem.sprites.StackSprite",
			

			// "problem.test.patternClasses.AdapteeClass",
			// "problem.test.patternClasses.AdapterClass",
			// "problem.test.patternClasses.FalseAdapterClass",
			// "problem.test.patternClasses.TargetInterface"

			// "problem.test.patternClasses.Leaf",
			// "problem.test.patternClasses.MyCollectionComposite",
			// "problem.test.patternClasses.CompositeComponent"
	};

	private String pathToDot;
	private String pathToSDEdit;
	private String outputDir = "input_output/";
	private ArrayList<String> phases;
	private volatile int completedPhases = 0;
	private String currentPhase = "";

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
		// DesignParser parser = new
		// DesignParser("\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\"",
		// "\"C:\\Users\\punttj\\Desktop\\csse374\\finalProject\\sdedit-4.2-beta1.exe\"");
		DesignParser parser2 = new DesignParser("\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\"",
				"\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\sdedit-4.2-beta1.exe\"");
		// parser.generateDocuments(DocType.uml,
		parser2.setDefaults();
		parser2.generateDocuments(DocType.sd,
				// "problem.asm.DesignParser,DesignParser,generateDocuments,DocType;
				// String; int; String[]", 5, CLASSES);
				// "problem.asm.Class,Class,accept,IModelVisitor", 5, CLASSES);
				// "java.util.Collections,Collections,shuffle,List", 5,
				// CLASSES);
				"problem.asm.DesignParser,DesignParser,generateSD,String; Model; ISubMethod; int", 2, CLASSES);
	}

	public DesignParser(String pathToDot, String pathToSDEdit) {
		this.pathToDot = pathToDot;
		this.pathToSDEdit = pathToSDEdit;
		this.phases = new ArrayList<String>();
	}

	public void generateDocuments(DocType type, String methodSig, int depth, String[] classes) throws IOException {

		Model model = Model.getInstance();
		ISubMethod sm = null;
		String args;

		if (type.equals(DocType.sd) || type.equals(DocType.both)) {
			String[] sigSplit = methodSig.split(",");
			String qualifiedClassName = sigSplit[0];
			String clazzName = sigSplit[1];
			String methodName = sigSplit[2];
			if (sigSplit.length == 4) {
				args = sigSplit[3];
			} else {
				args = "";
			}

			sm = new SubMethod(qualifiedClassName, clazzName, methodName, args, "");
		}

		if (phases.contains("visit")) {
			System.out.println(completedPhases);
			visitClasses(classes, model);
			this.currentPhase = "Visiting Classes...";
			completedPhases++;
			System.out.println(completedPhases);
			if (type.equals(DocType.uml) || type.equals(DocType.both)) {
				generateUML(pathToDot, model);
			}

			if (type.equals(DocType.sd) || type.equals(DocType.both)) {
				generateSD(pathToSDEdit, model, sm, depth);
			}
		}
		
		this.currentPhase = "finished";
		
		System.out.println("done");
	}

	public void visitClasses(String[] classes, Model model) throws IOException {

		for (String className : classes) {
			if (!className.contains("[")) {

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
			}
		}
	}

	public void generateUML(String pathToDot, Model model) throws IOException {

		if (phases.contains("singleton")) {
			this.currentPhase = "Detecting Singleton Pattern...";
			IPatternDetector singletonDetector = new SingletonDetector(model);
			singletonDetector.detectPatterns();
			this.completedPhases++;
			System.out.println(completedPhases);
		}

		if (phases.contains("decorator")) {
			this.currentPhase = "Detecting Decorator Pattern...";
			IPatternDetector decoratorDetector = new DecoratorDetector(model);
			decoratorDetector.detectPatterns();
			this.completedPhases++;
			System.out.println(completedPhases);
		}

		if (phases.contains("adapter")) {
			this.currentPhase = "Detecting Adapter Pattern...";
			IPatternDetector adapterDetector = new AdapterDetector(model);
			adapterDetector.detectPatterns();
			this.completedPhases++;
			System.out.println(completedPhases);
		}

		if (phases.contains("composite")) {
			this.currentPhase = "Detecting Composite Pattern...";
			IPatternDetector compositeDetector = new CompositeDetector(model);
			compositeDetector.detectPatterns();
			this.completedPhases++;
			System.out.println(completedPhases);
		}
		// ModelPatternVisitor mpv = new ModelPatternVisitor();
		// mpv.detect(model);

		if (phases.contains("dot")) {
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
		}
	}

	public void generateSD(String pathToSDEdit, Model model, ISubMethod sm, int depth) throws IOException {
		String[] classes = null;
		for (int i = 0; i < depth; i++) {
			classes = model.findNewClasses(sm, depth);
			Set<String> mySet = new HashSet<String>(Arrays.asList(classes));

			try {
				String[] newArray = mySet.toArray(new String[mySet.size()]);
				visitClasses(newArray, model);

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

	private void setDefaults() {
		setOutputDir("input_output");
		phases.add("Decorator");
		phases.add("Composite");
		phases.add("Singleton");
		phases.add("Adapter");
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
	
	public String getCurrentPhase(){
		return currentPhase;
	}

	public int getProgress() {
		return completedPhases;
	}

}