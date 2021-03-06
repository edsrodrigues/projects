/* Project - POOF */
package poof.system;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.*;

import poof.exception.*;

/**
 * class Builder
 *    Builds a file system from an import file.
 */
public class Builder {
   /* the builder's file system */
	private FileSystem _filesystem = new FileSystem();
	
	/**
	 * @return the file system
	 */
	public FileSystem getFileSystem() {
	   return _filesystem;
	}
	
	/**
    * Read a file and turn it into a file system
    *
	 * @param name
	 *             the name of the data file
	 */
	public void readImportFile(String name) throws IOException, CoreException {
      BufferedReader data = new BufferedReader(new FileReader(name));
      String line;
      while ((line = data.readLine()) != null) {
         String[] fields = line.split("\\|");
	      registerFromFields(fields);
      }
      data.close();
   }
   
   /**
    * Turns one of the file's lines into a file system object
    *
	 * @param fields
	 *             the information needed to create the object
	 */
   public void registerFromFields(String[] fields) throws CoreException {
      // Regular expression pattern to match an entry
      Pattern patEntry = Pattern.compile("^(FILE|DIRECTORY)");
      
      // Regular expression pattern to match a user
      Pattern patUser = Pattern.compile("^(USER)"); 
      
      if (patEntry.matcher(fields[0]).matches()) {
         registerEntry(fields);
      } else if (patUser.matcher(fields[0]).matches()) {
         registerUser(fields);
      } else {
         // never actually happens
         System.out.println("Error: line does not match any data type.");
      }
   }
   
   /**
    * Turns one of the file's lines into a file or directory
    *
	 * @param fields
	 *             the information needed to create the entry
	 */
   public void registerEntry(String[] fields) throws CoreException {
      User r = getFileSystem().getRoot();
      String[] names = fields[1].split("/");
      Directory[] dirs = new Directory[names.length-1];
      dirs[0] = getFileSystem().getInit();
      String entryName = names[names.length-1];
      boolean privacy = fields[3].equals("public");
      
      for(int i=0; i<names.length-2; i++){     // since the index length-1 pertains to the new entry, cycles only until the previous index
         // if needed, creates the directories that lead to the new entry
         if (!(dirs[i].checkEntry(names[i+1])))
            getFileSystem().newDirectory(names[i+1], dirs[i], dirs[i].getOwner(), false);
         dirs[i+1] = (Directory) dirs[i].getEntry(names[i+1]);
      }
      
      Directory parentDir = dirs[dirs.length-1];
      
      if (fields[0].equals("FILE")) {
         getFileSystem().newFile(entryName, parentDir, r, privacy);
         getFileSystem().writeToFile(fields[4], entryName, parentDir, r);
         getFileSystem().changeOwner(entryName, fields[2], parentDir, r);
         
      } else if (fields[0].equals("DIRECTORY")) {
			getFileSystem().newDirectory(entryName, parentDir, r, privacy);
			getFileSystem().changeOwner(entryName, fields[2], parentDir, r);
      } else {
         // never actually happens
         System.out.println("Error: line does not match any data type.");
      }
   }
   
   /**
    * Turns one of the file's lines into a user
    *
	 * @param fields
	 *             the information needed to create the user
	 */
   public void registerUser(String[] fields) throws CoreException {
      if (fields[0].equals("USER")) {
         getFileSystem().addUser(fields[1], fields[2], getFileSystem().getUser("root"));
      } else {
         // never actually happens
         System.out.println("Error: line does not match any data type.");
      }
   }

}
