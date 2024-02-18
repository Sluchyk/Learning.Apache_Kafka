package com.example.accounts.queries.infrustructe.consumers;

import com.example.account.common.events.AccountClosedEvent;
import com.example.account.common.events.AccountOpenedEvent;
import com.example.account.common.events.FundsDepositEvent;
import com.example.account.common.events.FundsWithdrawEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent event, Acknowledgment acknowledgment);
    void consume(@Payload FundsDepositEvent event, Acknowledgment acknowledgment);
    void consume(@Payload FundsWithdrawEvent event, Acknowledgment acknowledgment);
    void consume(@Payload AccountClosedEvent event, Acknowledgment acknowledgment);
}
