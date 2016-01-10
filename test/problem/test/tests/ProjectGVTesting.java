package problem.test.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ProjectGVTesting {
	
	private TestDesignParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new TestDesignParser();
	}

	@Test
	public final void testModel() throws IOException {
		String[] classes = { "problem.models.impl.Model", "problem.models.api.IModel", "problem.models.api.IClass",
				"problem.models.api.IRelation",};
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}

		String gv = "digraph model{rankdir = BT;Model [shape=\"record\",label = \"{Model| - classes: IClass\\"+"l- relations: IRelation\\"+"l|+ init() : void\\"+"l+ init(IClass) : IClass\\"+"l+ getRelations() : IRelation\\"+"l+ setRelations(IRelation) : IRelation\\"+"l+ addRelation(IRelation) : void\\"+"l+ getClasses() : IClass\\"+"l+ accept(IModelVisitor) : void\\"+"l+ toString() : String\\"+"l+ addClazz(IClass) : void\\"+"l+ getClazz(String) : IClass\\"+"l}\"];IModel [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIModel| + getClasses() : IClass\\"+"l+ addClazz(IClass) : void\\"+"l+ getClazz(String) : IClass\\"+"l+ getRelations() : IRelation\\"+"l}\"];IClass [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIClass| + getName() : String\\"+"l+ getMethods() : IMethod\\"+"l+ getFields() : IField\\"+"l+ addMethod(IMethod) : void\\"+"l+ addField(IField) : void\\"+"l+ getIsClass() : boolean\\"+"l}\"];IRelation [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIRelation| + setName(String) : void\\"+"l+ getName() : String\\"+"l+ setRelatedClass(String) : void\\"+"l+ getRelatedClass() : String\\"+"l+ setType(RelationType) : void\\"+"l+ getType() : RelationType\\"+"l}\"];//writing relations between classes nowModel -> IModel [arrowhead = \"empty\", style = \"dashed\"];Model -> IRelation [arrowhead = \"vee\"];Model -> IModelVisitor [arrowhead = \"vee\", style = \"dashed\"];Model -> IClass [arrowhead = \"vee\"];IModel -> IModelTraverser [arrowhead = \"empty\"];IModel -> IClass [arrowhead = \"vee\", style = \"dashed\"];IClass -> IModelTraverser [arrowhead = \"empty\"];IClass -> IMethod [arrowhead = \"vee\", style = \"dashed\"];IClass -> IField [arrowhead = \"vee\", style = \"dashed\"];IRelation -> IModelTraverser [arrowhead = \"empty\"];IRelation -> RelationType [arrowhead = \"vee\", style = \"dashed\"];}";
		assertEquals(gv, file);

		buffer.close();
		reader.close();
	}
	
	@Test
	public final void testIModelVisitor() throws IOException {
		String[] classes = { "problem.model.visitor.IModelVisitor", "problem.models.api.IModel", "problem.models.api.IClass",
				"problem.models.api.IRelation", "problem.models.api.IField", "problem.models.api.IMethod"};
		parser.parse(classes);
		FileReader reader = new FileReader("input_output/test.gv");
		BufferedReader buffer = new BufferedReader(reader);

		String file = "";
		String line = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		System.out.println(file);

		String gv = "digraph model{rankdir = BT;IModelVisitor [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIModelVisitor| + preVisit(IModel) : void\\"+"l+ preVisit(IClass) : void\\"+"l+ preVisit(IMethod) : void\\"+"l+ preVisit(IField) : void\\"+"l+ preVisit(IRelation) : void\\"+"l+ visit(IModel) : void\\"+"l+ visit(IClass) : void\\"+"l+ visit(IMethod) : void\\"+"l+ visit(IField) : void\\"+"l+ visitSuperClasses(IRelation) : void\\"+"l+ visitInterfaces(IRelation) : void\\"+"l+ postVisit(IModel) : void\\"+"l+ postVisit(IClass) : void\\"+"l+ postVisit(IMethod) : void\\"+"l+ postVisit(IField) : void\\"+"l+ postVisit(IRelation) : void\\"+"l+ visitRelations(IModel) : void\\"+"l+ intermediateVisit(IClass) : void\\"+"l}\"];IModel [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIModel| + getClasses() : IClass\\"+"l+ addClazz(IClass) : void\\"+"l+ getClazz(String) : IClass\\"+"l+ getRelations() : IRelation\\"+"l}\"];IClass [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIClass| + getName() : String\\"+"l+ getMethods() : IMethod\\"+"l+ getFields() : IField\\"+"l+ addMethod(IMethod) : void\\"+"l+ addField(IField) : void\\"+"l+ getIsClass() : boolean\\"+"l}\"];IRelation [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIRelation| + setName(String) : void\\"+"l+ getName() : String\\"+"l+ setRelatedClass(String) : void\\"+"l+ getRelatedClass() : String\\"+"l+ setType(RelationType) : void\\"+"l+ getType() : RelationType\\"+"l}\"];IField [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIField| + getType() : String\\"+"l+ getAccess() : String\\"+"l+ getName() : String\\"+"l}\"];IMethod [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIMethod| + getAccess() : String\\"+"l+ getName() : String\\"+"l+ getArgs() : String\\"+"l+ getReturnType() : String\\"+"l}\"];//writing relations between classes nowIModelVisitor -> IModel [arrowhead = \"vee\", style = \"dashed\"];IModelVisitor -> IClass [arrowhead = \"vee\", style = \"dashed\"];IModelVisitor -> IMethod [arrowhead = \"vee\", style = \"dashed\"];IModelVisitor -> IField [arrowhead = \"vee\", style = \"dashed\"];IModelVisitor -> IRelation [arrowhead = \"vee\", style = \"dashed\"];IModel -> IModelTraverser [arrowhead = \"empty\"];IModel -> IClass [arrowhead = \"vee\", style = \"dashed\"];IClass -> IModelTraverser [arrowhead = \"empty\"];IClass -> IMethod [arrowhead = \"vee\", style = \"dashed\"];IClass -> IField [arrowhead = \"vee\", style = \"dashed\"];IRelation -> IModelTraverser [arrowhead = \"empty\"];IRelation -> RelationType [arrowhead = \"vee\", style = \"dashed\"];IField -> IModelTraverser [arrowhead = \"empty\"];IMethod -> IModelTraverser [arrowhead = \"empty\"];}";
		assertEquals(gv, file);

		buffer.close();
		reader.close();
	}

}
