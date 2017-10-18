package commands;

public class ControlCommand extends AbstractCommand {

    public ControlCommand (Class thisClass, String methodToInvoke, Class[] parameters) throws NoSuchMethodException {
        super(thisClass, methodToInvoke, parameters);

    }
}
