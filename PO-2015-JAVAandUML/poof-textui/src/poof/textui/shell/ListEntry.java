/** @version $Id: ListEntry.java,v 1.10 2014/12/01 18:52:04 ist179027 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.system.Manager;
import poof.exception.*;

import poof.textui.EntryUnknownException;

/**
 * §2.2.2.
 */
public class ListEntry extends Command<Manager>{
	/**
	 * @param manager
	 */
	public ListEntry(Manager manager) {
		super(MenuEntry.LS_ENTRY, manager);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
      String ename = IO.readString(Message.nameRequest());
      try{
         IO.println(_receiver.showEntryInfo(ename));
	   }
	   catch (CoreEntryUnknownException e) {
	      throw new EntryUnknownException(e.getName());
	   }
	}

}
