package com.example.accounts.cmd.infrustructe;

import com.example.sqrs.core.commands.BaseCommand;
import com.example.sqrs.core.commands.CommandHandlerMethod;
import com.example.sqrs.core.infrastructure.CommandDispatcher;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
private final Map<Class <? extends BaseCommand>, List<CommandHandlerMethod>>  map = new HashMap<>();
    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = map.computeIfAbsent(type,c-> new LinkedList<>());
        handlers.add(handler);
    }
    @Override
    public void send(BaseCommand command) {
        var handlers = map.get(command.getClass());
        if(handlers == null || handlers.size() == 0){
            throw  new RuntimeException("No command handler was registered");
        }
        if(handlers.size() > 1 ){
            throw  new RuntimeException("Cannot send command to handler");
        }
         handlers.get(0).handle(command);
    }
}
