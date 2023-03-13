package request.sender.controller;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import request.sender.dto.RequestDto;

@RestController
public class RequestController {
    /*
     *   the ip address of request sending, number of requests, entity that will be as a result, async processing of request
     *   when we need result, user calls join() method
     *
     * */
    public void startRequestSending(@RequestBody RequestDto requestDto) {

    }
}
