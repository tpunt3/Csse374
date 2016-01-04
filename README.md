# Csse374
Software Design
Katie Lee and Trent Punt

Our design is similar to the car example from class. We used the Visitor Pattern. We have a Model object that gets created through ASM. Our code makes a first pass through the specified classes and generates our Model based on the classes, methods, and fields it finds. This is done using the DesignParser, and various ClassVisitor classes that we created in class. We then traverse our Model (making a second pass) and generate a .gv file titled "model.gv" by going through all of the classes, fields, and methods in our Model. This pass is made by the classes in all the problem.model.* that we created. We then run dot on our .gv file to generate a png file with the UML of the desired classes. The .png is called "graph1.png" and is located in the input_output folder. 

As for who did what, we were highly cooperative and pair programmed almost all of the time. All aspects of the project were done together, with an effort to regularly switch who was coding. To see who wrote specific line of code, please see git repo commits.

Instructions to use our code:
1.) go to the DesignParser.java class in problem.asm
2.) change the array of strings called CLASSES to reflect which classes you'd like to generate a UML for.
3.) run the Design parser

Locations and names of important documents:
- Generated .gv file: called "model.gv" and located in the input_output folder.
- Generated .png file: called "graph1.png" and located in the input_output folder.
- Our manually created UML: called "FinalProjectUML.uxf" (for the UMLet file) and "FinalProjectUML.png" (for a .png version), both are located in the docs folder.
- Generated UML .png file for Lab 1-3: called Lab1-3_GeneratedUML.png and located in the docs folder.
- Generated UML .png file for FinalProject: called FinalProject_GeneratedUML.png and located in the docs folder.