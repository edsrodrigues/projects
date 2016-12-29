/** @version $Id: ShowFileData.java,v 1.11 2014/12/01 18:52:04 ist179027 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.system.Manager;
import poof.exception.*;

import poof.textui.*;

/**
 * §2.2.9.
 */
public class ShowFileData extends Command<Manager>{
	/**
	 * @param manager
	 */
	public ShowFileData(Manager manager) {
		super(MenuEntry.CAT, manager);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
      String fname = IO.readString(Message.fileRequest());
      try {
         String content = _receiver.readFromFile(fname);
         if (!(content.equals("")))
            IO.println(content);
	   }
	   catch (CoreEntryUnknownException e) {
         throw new EntryUnknownException(e.getName());
      }
      catch (CoreIsNotFileException e) {
         throw new IsNotFileException(e.getName());
      }
	}
}
