package problem.asm;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.model.visitor.IModelTraverser;
import problem.model.visitor.IModelVisitor;
import problem.models.impl.Model;
import problem.models.impl.ModelGVOutputStream;
import problem.models.impl.ModelSDOutputStream;

public class DesignParser {
	
	public static void main(String[] args) throws IOException{
		DesignParser parser = new DesignParser();
		parser.generateDocuments(DocType.uml, "");
	}

	public static final String[] CLASSES = {
//			 "headfirst.factory.pizzaaf.BlackOlives",
//			 "headfirst.factory.pizzaaf.Cheese",
//			 "headfirst.factory.pizzaaf.CheesePizza",
//			 "headfirst.factory.pizzaaf.ChicagoPizzaIngredientFactory",
//			 "headfirst.factory.pizzaaf.ChicagoPizzaStore",
//			 "headfirst.factory.pizzaaf.ClamPizza",
//			 "headfirst.factory.pizzaaf.Clams",
//			 "headfirst.factory.pizzaaf.Dough",
//			 "headfirst.factory.pizzaaf.Eggplant",
//			 "headfirst.factory.pizzaaf.FreshClams",
//			 "headfirst.factory.pizzaaf.FrozenClams",
//			 "headfirst.factory.pizzaaf.Garlic",
//			 "headfirst.factory.pizzaaf.MarinaraSauce",
//			 "headfirst.factory.pizzaaf.MozzarellaCheese",
//			 "headfirst.factory.pizzaaf.Mushroom",
//			 "headfirst.factory.pizzaaf.NYPizzaIngredientFactory",
//			 "headfirst.factory.pizzaaf.NYPizzaStore",
//			 "headfirst.factory.pizzaaf.Onion",
//			 "headfirst.factory.pizzaaf.ParmesanCheese",
//			 "headfirst.factory.pizzaaf.Pepperoni",
//			 "headfirst.factory.pizzaaf.PepperoniPizza",
//			 "headfirst.factory.pizzaaf.Pizza",
//			 "headfirst.factory.pizzaaf.PizzaIngredientFactory",
//			 "headfirst.factory.pizzaaf.PizzaStore",
//			 "headfirst.factory.pizzaaf.PizzaTestDrive",
//			 "headfirst.factory.pizzaaf.PlumTomatoSauce",
//			 "headfirst.factory.pizzaaf.RedPepper",
//			 "headfirst.factory.pizzaaf.ReggianoCheese",
//			 "headfirst.factory.pizzaaf.Sauce",
//			 "headfirst.factory.pizzaaf.SlicedPepperoni",
//			 "headfirst.factory.pizzaaf.Spinach",
//			 "headfirst.factory.pizzaaf.ThickCrustDough",
//			 "headfirst.factory.pizzaaf.ThinCrustDough",
//			 "headfirst.factory.pizzaaf.VeggiePizza",
//			 "headfirst.factory.pizzaaf.Veggies",
//			 "headfirst.factory.pizzafm.ChicagoPizzaStore",
//			 "headfirst.factory.pizzafm.ChicagoStyleCheesePizza",
//			 "headfirst.factory.pizzafm.ChicagoStyleClamPizza",
//			 "headfirst.factory.pizzafm.ChicagoStylePepperoniPizza",
//			 "headfirst.factory.pizzafm.ChicagoStyleVeggiePizza",
//			 "headfirst.factory.pizzafm.DependentPizzaStore",
//			 "headfirst.factory.pizzafm.NYPizzaStore",
//			 "headfirst.factory.pizzafm.NYStyleCheesePizza",
//			 "headfirst.factory.pizzafm.NYStyleClamPizza",
//			 "headfirst.factory.pizzafm.NYStylePepperoniPizza",
//			 "headfirst.factory.pizzafm.NYStyleVeggiePizza",
//			 "headfirst.factory.pizzafm.Pizza",
//			 "headfirst.factory.pizzafm.PizzaStore",
//			 "headfirst.factory.pizzafm.PizzaTestDrive",
//			 "headfirst.factory.pizzas.CheesePizza",
//			 "headfirst.factory.pizzas.ClamPizza",
//			 "headfirst.factory.pizzas.PepperoniPizza",
//			 "headfirst.factory.pizzas.Pizza",
//			 "headfirst.factory.pizzas.PizzaStore",
//			 "headfirst.factory.pizzas.PizzaTestDrive",
//			 "headfirst.factory.pizzas.SimplePizzaFactory",
//			 "headfirst.factory.pizzas.VeggiePizza",

			"problem.asm.ClassDeclarationVisitor", 
			"problem.asm.ClassFieldVisitor", 
			"problem.asm.ClassMethodVisitor",
			"problem.asm.DesignParser", 
			"problem.asm.IClazzGetter", 
			"problem.asm.MyMethodVisitor",
			"problem.model.visitor.IModelTraverser", 
			"problem.model.visitor.IModelVisitor",
			"problem.model.visitor.ModelVisitorAdapter", 
			"problem.models.api.IClass", 
			"problem.models.api.IField",
			"problem.models.api.IMethod", 
			"problem.models.api.IModel", 
			"problem.models.api.IRelation",
			"problem.models.api.RelationType", 
			"problem.models.impl.Class", 
			"problem.models.impl.Field",
			"problem.models.impl.Method", 
			"problem.models.impl.Model", 
			"problem.models.impl.ModelGVOutputStream",
			"problem.models.impl.Relation"

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
	};

	/**
	 * Reads in a list of Java Classes and reverse engineers their design.
	 *
	 * @param args:
	 *            the names of the classes, separated by spaces. For example:
	 *            java DesignParser java.lang.String
	 *            edu.rosehulman.csse374.ClassFieldVisitor java.lang.Math
	 * @throws IOException
	 */
	public void generateDocuments(DocType type, String methodSig) throws IOException {

		Model model = new Model();
		model.setMethodSignature(methodSig);

		for (String className : CLASSES) {
			// ASM's ClassReader does the heavy lifting of parsing the compiled
			// Java class
			ClassReader reader = new ClassReader(className);

			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, model);

			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, model);

			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, model);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

			if (type.equals(DocType.uml) || type.equals(DocType.both)) {

				OutputStream out = new FileOutputStream("input_output/model.gv");
				IModelVisitor gvWriter = new ModelGVOutputStream(out);
				IModelTraverser traverser = (IModelTraverser) model;
				traverser.accept(gvWriter);
				out.close();

				// runs DOT on our .gv file. This might need to be moved?
				//ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",
						//"\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\" -Tpng input_output/model.gv > input_output/graph1.png");
				 ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", 
						 "\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\" -Tpng input_output/model.gv > input_output/graph1.png");
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
			
			if(type.equals(DocType.sd) || type.equals(DocType.both)){
				OutputStream out = new FileOutputStream("input_output/sequence.sd");
				IModelVisitor sdWriter = new ModelSDOutputStream(out);
				IModelTraverser traverser = (IModelTraverser) model;
				traverser.acceptSequence(sdWriter);
				out.close();

				ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",
						"\"C:\\Users\\leekf\\Documents\\JUNIOR\\CSSE374\\release\\bin\\dot\" -Tpng input_output/model.gv > input_output/graph1.png");
				// ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",
				// "\"C:\\Users\\punttj\\Desktop\\csse374\\release\\bin\\dot\"
				// -Tpng input_output/model.gv > input_output/graph1.png");
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
		System.out.println("done");
	}
}