JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java

SERVERCLASSES = \
	        PenduServeurTCP.java 
        
CLIENTCLASSES = \
		PenduClientTCP.java

default: serverclasses clientclasses

serverclasses: $(SERVERCLASSES:.java=.class)

clientclasses: $(CLIENTCLASSES:.java=.class)

clean:
	$(RM) *.class
