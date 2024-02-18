package com.example.sqrs.core.commands;

import com.example.sqrs.core.messages.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseCommand extends Message {
    public BaseCommand(String id){
        super(id);
    }

}
