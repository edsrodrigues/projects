/** @version $Id: New.java,v 1.7 2014/11/22 14:19:47 ist179027 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.system.Manager;

/**
 * Open a new file.
 */
public class New extends Command<Manager> {

	/**
	 * @param manager
	 */
	public New(Manager manager) {
		super(MenuEntry.NEW, manager);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
	   if (_receiver.checkStatus())
         if( IO.readBoolean(Message.saveBeforeExit()) )
            (new Save(_receiver)).execute();
		_receiver.newFileSystem();
	}

}
