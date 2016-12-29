/** @version $Id: ChangeEntryPermissions.java,v 1.12 2014/12/01 18:52:04 ist179027 Exp $ */
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
 * §2.2.10.
 */
public class ChangeEntryPermissions extends Command<Manager>{
	/**
	 * @param manager
	 */
	public ChangeEntryPermissions(Manager manager) {
		super(MenuEntry.CHMOD, manager);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
	   String ename = IO.readString(Message.nameRequest());
      String privacy = IO.readString(Message.publicAccess());
	   try {
   		_receiver.changePrivacy(ename, (privacy.equals("s")));
      }
	   catch (CoreEntryUnknownException e) {
	      throw new EntryUnknownException(e.getName());
	   }
      catch (CoreAccessDeniedException e) {
         throw new AccessDeniedException(e.getName());
      }
	}

}
