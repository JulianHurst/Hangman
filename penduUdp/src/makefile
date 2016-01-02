JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Pendu.java \
	PenduServerUdp.java \
	PenduClientUdp.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class