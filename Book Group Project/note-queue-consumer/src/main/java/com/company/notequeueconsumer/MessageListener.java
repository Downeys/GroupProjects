package com.company.notequeueconsumer;

import com.company.notequeueconsumer.NoteQueueConsumerApplication;
import com.company.notequeueconsumer.feign.NoteServiceFeignClient;
import com.company.notequeueconsumer.util.messages.Note;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    NoteServiceFeignClient client;

    @Autowired
    public MessageListener(NoteServiceFeignClient client){
        this.client = client;
    }

    @RabbitListener(queues = NoteQueueConsumerApplication.QUEUE_NAME)
    public void receiveMessage(Note msg) {
        client.createNote(msg);
    }

}
