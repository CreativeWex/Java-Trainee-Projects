#download it.bmp at move it to Downloads folder

# deleting the directory
rm -rf target

# creating the new dir (java -d doesn't create it)
mkdir target

# set the destination directory
javac src/java/edu/school21/printer/*/*.java -d ./target

# specify where to find user class files
java -classpath target edu.school21.printer.app.Program . 0 /Users/jnidorin/Downloads/it.bmp