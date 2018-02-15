JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	LeafKey.java \
	BranchNode.java \
	treesearch.java \
	BranchKey.java \
	LeafNode.java \
	Search.java

MAIN = treesearch

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class