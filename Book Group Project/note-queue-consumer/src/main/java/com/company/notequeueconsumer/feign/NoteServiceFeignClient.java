package com.company.notequeueconsumer.feign;

import com.company.notequeueconsumer.util.messages.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "note-service")
public interface NoteServiceFeignClient {

        @RequestMapping(value = "/notes", method = RequestMethod.POST)
        Note createNote(@RequestBody Note note);

}
