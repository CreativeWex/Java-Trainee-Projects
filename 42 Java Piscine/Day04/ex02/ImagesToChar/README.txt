#java - source folder

# deleting the directory
rm -rf target

# creating the new dir (java -d doesn't create it)
mkdir target

# deleting the directory
rm -rf lib

# creating the new dir (java -d doesn't create it)
mkdir lib

# downloading libs
curl -o lib/jcommander-1.82.jar https://repo1.maven.org/maven2/com/beust/jcommander/1.82/jcommander-1.82.jar
curl -o lib/JCDP-4.0.2.jar https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.2/JCDP-4.0.2.jar

# set the destination directory
javac -cp .:./lib/JCDP-4.0.2.jar:./lib/jcommander-1.82.jar -d ./target/ src/java/edu/school21/printer/*/*.java

cd target

# uzip jars
jar xf ../lib/jcommander-1.82.jar
jar xf ../lib/JCDP-4.0.2.jar

cd ..

# copy folder to target
cp -R src/resources target/.

# 'cmf' - "create archive" "path to manifest.txt" "output file name"
# '-C ./target' - root directory for archive sources is ./target
# '.' everything in this folder will be added
jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C target .

# specify where to find user class files
# '-jar' - run jar archive
# './target/images-to-chars-printer.jar' - archive path
# '. 0' -command line arguments
java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN
