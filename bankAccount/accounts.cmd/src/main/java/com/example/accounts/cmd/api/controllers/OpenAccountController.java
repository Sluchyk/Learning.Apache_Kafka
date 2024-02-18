package com.example.accounts.cmd.api.controllers;

import com.example.account.common.dto.BaseResponse;
import com.example.accounts.cmd.api.commands.OpenAccountCommand;
import com.example.accounts.cmd.api.dto.OpenAccountResponse;
import com.example.sqrs.core.infrastructure.CommandDispatcher;
import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/openBankAccount")
public class OpenAccountController {
private final Logger logger = Logger.getLogger(OpenAccountController.class.getName());
private final CommandDispatcher commandDispatcher;

    public OpenAccountController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }
    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command){
         var id = UUID.randomUUID().toString();
         command.setId(id);
         try {
           commandDispatcher.send(command);
           return new ResponseEntity<>(new OpenAccountResponse("Bank account creation request completed successfully",id), HttpStatus.CREATED);
         }
         catch (IllegalStateException e){
             logger.log(Level.WARNING, MessageFormat.format("Client made bad request - {0}",e.toString()));
             return  new ResponseEntity<>(new BaseResponse(e.toString()),HttpStatus.BAD_REQUEST);
         } catch (Exception e){
             var safeErrorMessage = MessageFormat.format("Error while processing request to new bank account for id - {0}.",id);
             logger.log(Level.SEVERE,safeErrorMessage,e);
             return  new ResponseEntity<>(new OpenAccountResponse(safeErrorMessage,id),HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }
}
