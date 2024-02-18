package com.example.sqrs.core.infrastructure;

import com.example.sqrs.core.commands.BaseCommand;
import com.example.sqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
