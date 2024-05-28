
JC = javac

JR = java

MAIN = gatorTaxi.java



compile:
	$(JC) $(MAIN)
clean:
	$(RM) *.class