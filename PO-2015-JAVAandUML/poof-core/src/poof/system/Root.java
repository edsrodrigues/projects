/* Project - POOF */
package poof.system;

import java.io.Serializable;

/**
 * class Root
 *    Has access to all content and operations available in the file system.
 */
public class Root extends User implements Serializable { 
   public Root() {
      super("root", "Super User");
   }
   
   /**
    * @return true, since this is a Root user
	 */
	@Override
	public boolean isRoot(){
		return true;
	}	
}                                   
