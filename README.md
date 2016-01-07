# Csse374
Software Design
Katie Lee and Trent Punt

Description of Project

Our design is similar to the car example from class. We used the Visitor Pattern. We have a Model object that gets created through ASM. Our code makes a first pass through the specified classes and generates our Model based on the classes, methods, and fields it finds. This is done using the DesignParser, and various ClassVisitor classes that we created in class. We then traverse our Model (making a second pass) and generate a .gv file titled "model.gv" by going through all of the classes, fields, and methods in our Model. This pass is made by the classes in all the problem.model.* that we created. We then run dot on our .gv file to generate a png file with the UML of the desired classes. The .png is called "graph1.png" and is located in the input_output folder. 

Who Did What:
Milestone 1
As for who did what, we were highly cooperative and pair programmed almost all of the time. All aspects of the project were done together, with an effort to regularly switch who was coding. To see who wrote specific line of code, please see git repository commits.

Milestone 2
Again, most of the work was done sitting together, trading off who would code and commit. We prefer to keep it that way so both partners know exactly how things are working for the most part. For other specific parts, please see git repository.

Instructions to use our code:
1.) go to the DesignParser.java class in problem.asm
2.) change the array of strings called CLASSES to reflect which classes you'd like to generate a UML for.
3.) run the Design parser

Evolution of our Code:
Milestone 1
Our relation was not designed the best in milestone 1, but it was a learning experience. We had different constructors for all of our different types of relations which made it somewhat difficult to keep track of them. We also had not implemented uses or association arrows at this time so our UML looked incomplete. 

Milestone 2
We refactored relation so that it takes in what class has the relation, what class it is being related to, and the type of relation. This has made our implementation much more simple in many ways. For instance, it much easier to write the logic of when to exclude arrows with this implementation of relation. We also had some unnecessary methods and fields in our project before that have been removed. Our design definitely got better in milestone 2. 

Locations and names of important documents:
- Generated .gv file: called "model.gv" and located in the input_output folder.
- Generated .png file: called "graph1.png" and located in the input_output folder.
- Our manually created UML: called "FinalProjectUML.uxf" (for the UMLet file) and "FinalProjectUML_M#.png" (for a .png version, with the M# representing the milestone for which it was created), both are located in the docs folder.

- Generated UML .png file for Lab 1-3: called Lab1-3_GeneratedUML.png and located in the docs folder.
- Generated UML .png file for FinalProject: called FinalProject_GeneratedUML_M#.png and located in the docs folder.
- Generated UML .png file for the Pizza project: called Pizza_GeneratedUML.png and located in the docs folder.