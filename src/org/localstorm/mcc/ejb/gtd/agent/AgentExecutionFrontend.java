package org.localstorm.mcc.ejb.gtd.agent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.log4j.Logger;

/**
 * @author Alexey Kuznetsov
 */
public class AgentExecutionFrontend 
{
    // TODO: To configuration?
    public static final char COMMAND_PREFIX_CHAR = '@';

    private static final Logger log = Logger.getLogger(AgentExecutionFrontend.class);

    private final ConcurrentMap<String, CommandHandler> handlers;
    private CommandHandler defaultCh;

    public AgentExecutionFrontend()
    {
        this.handlers = new ConcurrentHashMap<String, CommandHandler>();
    }

    public String handle(int uid, String from, String to, String message)
    {
        message = message.trim();
        if (message.length()==0)
        {
            return null;
        }

        char prefix = message.charAt(0);
        if (prefix==COMMAND_PREFIX_CHAR)
        {
            String commandName = getCommand(message);
            String param = getParameter(message);
            return handleCommand(commandName, uid, from, to, param);
        } else {
            return handleDefault(uid, from, to, message);
        }
    }

    public void addCommandHandler(String commandName, CommandHandler ch)
    {
        handlers.put(commandName.toLowerCase(), ch);
    }

    public void setDefaultCommandHandler(CommandHandler ch)
    {
        this.defaultCh = ch;
    }

    private String getCommand(String message)
    {
        if (message.startsWith(""+COMMAND_PREFIX_CHAR)) {
            message = message.substring(1);
        }
        
        int spacePos = message.indexOf(" ");
        if (spacePos==-1) {
            return message.toLowerCase();
        } else {
            return message.substring(0, spacePos).trim().toLowerCase();
        }
    }

    private String handleCommand(String commandName, int uid, String from, String to, String message)
    {
        log.info("Trying to handle [" + commandName + "] from uid=" + uid);

        CommandHandler ch = this.handlers.get(commandName.toLowerCase());
        if (ch == null) {
            log.info("Handler not found for command: [" + commandName + "]. Using default.");
            return "Unrecognized command.";
        } else {
            return ch.handle(uid, from, to, message);
        }
    }

    private String handleDefault(int uid, String from, String to, String message)
    {
        if (this.defaultCh == null) {
            log.error("Unable to handle message: ["+message+"]. Default command handler not set.");
            return null;
        } else {
            return this.defaultCh.handle(uid, from, to, message);
        }
    }

    private String getParameter(String message) {
        if (message.startsWith(""+COMMAND_PREFIX_CHAR)) {
            message = message.substring(1);
        }

        int spacePos = message.indexOf(" ");
        if (spacePos==-1) {
            return null;
        } else {
            return message.substring(spacePos).trim();
        }
    }
}
