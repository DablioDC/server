package com.dabliodc.server.controller;

import com.dabliodc.server.messagingstompwebsocket.Greeting;
import com.dabliodc.server.model.Artefact;
import com.dabliodc.server.model.StartMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greet(StartMessage message) throws InterruptedException {
        Thread.sleep(1000);

        saveArtefact(message.getId(), message.getDate(), message.getName(), message.getState(), message.getBravery(), message.getLow());

        return new Greeting("Data receivet, thanks " + HtmlUtils.htmlEscape(message.getName()));
    }

    @RequestMapping(value="/artefacts", method= RequestMethod.POST)
    public void saveArtefact(@RequestParam("id") String id,
                             @RequestParam("date") String date,
                             @RequestParam("name") String name,
                             @RequestParam("state") String state,
                             @RequestParam("bravery") String bravery,
                             @RequestParam("low") String low) {


        Artefact artefactJson = new Artefact(id,date,name,state,bravery,low);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Artefact> request = new HttpEntity<Artefact>(artefactJson);
        ResponseEntity<String> response = restTemplate
                .exchange("http://localhost:8081/artefacts", HttpMethod.POST, request, String.class);

        ResponseEntity.ok().build();
    }
}
