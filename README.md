# Csse374
Software Design

Our design somewhat mirrors the car example from class. We have a model that gets created through ASM. This model looks through the specified classes and generates our model based on the classes, methods, and fields it finds. We then traverse our model and generate a .gv file titled "model.gv" by going through all of the classes, fields, and methods in our model. We then run dot on our .gv file to generate a png file with the UML of the desired classes. The .png is called "graph1.png". 

As for who did what, we were highly cooperative and pair programmed almost all of the time. 

Instructions to use our code:
1.) go to the DesignParser.java class in problem.asm
2.) change the array of strings called CLASSES to reflect which classes you'd like to generate a UML for.
3.) run the Design parser